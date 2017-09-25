package com.predictionmarketing.RecommenderApp.moivelen;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class MovieRecommender {

	public static void main(String[] args) throws IOException, TasteException {
		// TODO Auto-generated method stub
		// 0. u.data : user id | item id | rating | timestamp.
		
		// 1. 데이터 로딩 : IOException
		DataModel model = new FileDataModel(new File("data/u.data"));
		
		// 2. 비슷한 값들을 찾기 위해 상호 작용 비교(피어슨 상관계수 사용) : TaseteException
		// 유저 유사성은 사용자 간의 유사한 정도를 측정하고 이 것을 지표로 삼는 방식
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		
		// 3. 0.1보다 유사성이 큰 것을 사용
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.7, similarity, model);
		
		// 4. 추천기(Recommender) : 유저 기반 추천기(User-based Recommender)
		// 데이터가 꼬여있거나 작은 볼륨의 데이터 셋에 적합. 빠른 추천 시스템이 필수적이지 않다면
		// 좋은 생산성을 보여줌.
		// ex) A 유저가 아이템 1, 2, 3을 선호하고 B는 2, 3을 선호할 경우 아이템 1을 B에게 추천
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		
		// 5. userID가 2인 사용자에게 3가지 추천
		List<RecommendedItem> recommendations = recommender.recommend(1, 10);
		for(RecommendedItem recommendation : recommendations) {
			System.out.println(recommendation);
		}
	}

}
