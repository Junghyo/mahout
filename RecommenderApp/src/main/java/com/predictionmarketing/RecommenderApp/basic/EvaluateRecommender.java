package com.predictionmarketing.RecommenderApp.basic;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/*
Evaluation(평가)
recommender가 좋은 결과를 반환하는지 확인하는 방법 : **hold-out** test
1. 데이터를 훈련 데이터 9 : 테스트 데이터 1로 분할
2. 트레이닝 세트를 사용하여 recommender 훈련 
3. 테스트 세트에서 알려지지 않은 상호 작용을 얼마나 잘 예측하는지 확인

*/
public class EvaluateRecommender {

	public static void main(String[] args) throws IOException, TasteException {
		// TODO Auto-generated method stub
		DataModel model = new FileDataModel(new File("data/dataset.csv"));
		RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		RecommenderBuilder builder = new MyRecommenderBuilder();
		double result = evaluator.evaluate(builder, null, model, 0.9, 1.0);
		System.out.println(result);
	}

}

class MyRecommenderBuilder implements RecommenderBuilder {
	
	public Recommender buildRecommender(DataModel dataModel) throws TasteException {
		
		UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, dataModel);
		return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
		
	}		
}