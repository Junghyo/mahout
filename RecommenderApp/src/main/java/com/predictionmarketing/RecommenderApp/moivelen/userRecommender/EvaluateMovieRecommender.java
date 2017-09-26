package com.predictionmarketing.RecommenderApp.moivelen.userRecommender;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class EvaluateMovieRecommender {

	public static void main(String[] args) throws IOException, TasteException {
		// TODO Auto-generated method stub
		DataModel model = new FileDataModel(new File("data/movie.csv"));
		
		// 1. 품질 평가 : RecommenderEvaluator
		// AverageAbsoluteDifferenceRecommenderEvaluator 
		// 유저의 예상 등급과 실제 등급 간의 평균 절대 차이를 계산(mean average error)
		RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		
		// 2. 평가 모델 설정
		// 2-1. 추천 모델 : Pearson
		RecommenderBuilder builderP = new PearsonBuilder();
		// 2-2. 추천 모델 : Tanimoto
		RecommenderBuilder builderT = new TanimotoBuilder();
		// 2-3. 추천 모델 : Spearman
		RecommenderBuilder builderS = new SpearmanBuilder();
		// 2-4. 추천 모델 : Euclidean
		RecommenderBuilder builderE = new EuclideanBuilder();
		
//		evaluate(RecommenderBuilder recommenderBuilder,
//        DataModelBuilder dataModelBuilder,
//        DataModel dataModel,
//        double trainingPercentage,
//        double evaluationPercentage)
		
		// 3. 품질 평가. 낮은 값일수록 좋은 모델인듯 ?
		// 3-1. Pearson
		double resultP = evaluator.evaluate(builderP, null, model, 0.7, 1.0);
		// 3-2. Tanimoto
		double resultT = evaluator.evaluate(builderT, null, model, 0.7, 1.0);
		// 3-3. Spearman
		double resultS = evaluator.evaluate(builderS, null, model, 0.7, 1.0);
		// 3-4. Euclidean
		double resultE = evaluator.evaluate(builderE, null, model, 0.7, 1.0);
		
		System.out.println("Pearson Correlation : " + resultP);
		System.out.println("Tanimoto Coefficient : " + resultT);
		System.out.println("Spearman Correlation : " + resultS);
		System.out.println("Euclidean Distance : " + resultE);
	}

}

// Pearson
class PearsonBuilder implements RecommenderBuilder {
	
	public Recommender buildRecommender(DataModel model) throws TasteException {
		
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.5, similarity, model);
		return new GenericUserBasedRecommender(model, neighborhood, similarity);
	}
}

// Tanimoto
class TanimotoBuilder implements RecommenderBuilder {
	
	public Recommender buildRecommender(DataModel model) throws TasteException {
		
		UserSimilarity similarity = new TanimotoCoefficientSimilarity(model);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
		return new GenericUserBasedRecommender(model, neighborhood, similarity);
	}
}

// Spearman
class SpearmanBuilder implements RecommenderBuilder {
	
	public Recommender buildRecommender(DataModel model) {
		UserSimilarity similarity = new SpearmanCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.5, similarity, model);
		return new GenericUserBasedRecommender(model, neighborhood, similarity);
	}
}

// Euclidean
class EuclideanBuilder implements RecommenderBuilder {
	
	public Recommender buildRecommender(DataModel model) throws TasteException {
		UserSimilarity similarity = new EuclideanDistanceSimilarity(model);
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.5, similarity, model);
		return new GenericUserBasedRecommender(model, neighborhood, similarity);
	}
}