package com.predictionmarketing.RecommenderApp.basic;

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

public class SampleRecommender {

	public static void main(String[] args) throws IOException, TasteException {
		// TODO Auto-generated method stub
		// 0. dataset.csv : userID, itemID, value
		// 1. 데이터 로딩 (DataModel 인터페이스 사용) 
		DataModel model = new FileDataModel(new File("data/dataset.csv"));
		// 2. 비슷한 값들을 찾기 위해 상호 작용 비교(피어슨 상관계수 사용)
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		// 3. 0.1보다 유사성이 큰 것을 사용 : ThresholdUserNeighborhood(비교값, similarity, model)
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
		// 4. 세팅한 요소들로 추천 
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		// 5. userID가 2인 사용자에게 3가지 항목 추천
		List<RecommendedItem> recommendations = recommender.recommend(2, 3);
		for(RecommendedItem recommendation : recommendations) {
			System.out.println(recommendation);
//			결과값
//			RecommendedItem[item:12, value:4.8328104]
//			RecommendedItem[item:13, value:4.6656213]
//			RecommendedItem[item:14, value:4.331242]
		}
	}

}
