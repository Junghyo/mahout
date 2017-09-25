package com.predictionmarketing.RecommenderApp.moivelen.userRecommender;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class MovieRecommenderUsingVariableMethod {

	public static void main(String[] args) throws IOException, TasteException {
		// TODO Auto-generated method stub
		// 0. u.data : user id | item id | rating | timestamp.
		
		// 1. 데이터 로딩 : IOException
		DataModel model = new FileDataModel(new File("data/movie.csv"));
		
		// 2. 비슷한 값들을 찾기 위해 상호 작용 비교(피어슨 상관계수 사용) : TaseteException
		// 유저 유사성은 사용자 간의 유사한 정도를 측정하고 이 것을 지표로 삼는 방식
		// 2-1. Pearson Correlation
		UserSimilarity similarityP = new PearsonCorrelationSimilarity(model);
		// 2-2. Tanimoto Coefficient
		UserSimilarity similarityT = new TanimotoCoefficientSimilarity(model);
		// 2-3. Spearman Correlation
		UserSimilarity similarityS = new SpearmanCorrelationSimilarity(model);
		// 2-4. Euclidean Distance(유클리디언 거리)
		UserSimilarity similarityE = new EuclideanDistanceSimilarity(model);
		
		// 3. 이웃한 N명의 유저 데이터로 추천 데이터 생성
		// 3-1. Pearson Correlation
		UserNeighborhood neighborhoodP = new NearestNUserNeighborhood(2, similarityP, model);
		// 3-2. Tanimoto Coefficient
		UserNeighborhood neighborhoodT = new NearestNUserNeighborhood(2, similarityT, model);
		// 3-3. Spearman Correlation
		UserNeighborhood neighborhoodS = new NearestNUserNeighborhood(2, similarityS, model);
		// 3-4. Euclidean Distance
		UserNeighborhood neighborhoodE = new NearestNUserNeighborhood(2, similarityE, model);
		
		// 4. 추천기(Recommender) : 유저 기반 추천기(User-based Recommender)
		// 데이터가 꼬여있거나 작은 볼륨의 데이터 셋에 적합. 빠른 추천 시스템이 필수적이지 않다면
		// 좋은 생산성을 보여줌.
		// ex) A 유저가 아이템 1, 2, 3을 선호하고 B는 2, 3을 선호할 경우 아이템 1을 B에게 추천
		
		// 4-1. Pearson Correlation
		UserBasedRecommender recommenderP = new GenericUserBasedRecommender(model, neighborhoodP, similarityP);
		// 4-2. Tanimoto Coefficient
		UserBasedRecommender recommenderT = new GenericUserBasedRecommender(model, neighborhoodT, similarityT);
		// 4-3. Spearman Correlation
		UserBasedRecommender recommenderS = new GenericUserBasedRecommender(model, neighborhoodS, similarityS);
		// 4-4. Euclidean Distance
		UserBasedRecommender recommenderE = new GenericUserBasedRecommender(model, neighborhoodE, similarityE);
		
		// 5. userID가 1인 사용자에게 아이템 3가지 추천
		System.out.println("5-1. Pearson Correlation");
		List<RecommendedItem> Precommendations = recommenderP.recommend(1, 3);
		for(RecommendedItem Precommendation : Precommendations) {
			System.out.println(Precommendation);
		}
		System.out.println("5-2. Tanimoto Coefficient");
		List<RecommendedItem> Trecommendations = recommenderT.recommend(1, 3);
		for(RecommendedItem Trecommendation : Trecommendations) {
			System.out.println(Trecommendation);
		}
		System.out.println("5-3. Spearman Correlation");
		List<RecommendedItem> Srecommendations = recommenderS.recommend(1, 3);
		for(RecommendedItem Srecommendation : Srecommendations) {
			System.out.println(Srecommendation);
		}
		System.out.println("5-4. Euclidean Distance");
		List<RecommendedItem> Erecommendations = recommenderE.recommend(1, 3);
		for(RecommendedItem Erecommendation : Erecommendations) {
			System.out.println(Erecommendation);
		}
	}

}
