package com.predictionmarketing.RecommenderApp.moivelen.itemRecommend;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

//GenericUserBasedRecommender
//- 사용자 유사도 측정
//- 이웃 정의 및 크기 
//- 평범한 구현법
//- 사용자 수가 적을 때는 상대적으로 빠름
//GenericItemBasedRecommender
//- 아이템 유사도 측정
//- 아이템 수가 적을 때는 빠름
//- 외부에 아이템 유사도 개념이 있을 때 유용함
//SlopeOneRecommender
//- 차이값 저장소 전략
//- 실행 중에 추천과 업데이트가 빠름
//- 많은 선행 계산 필요
//- 아이템 수가 상대적으로 적을 때 적합함
//SVDRecommender
//- 특성의 수
//- 양호한 결과
//- 많은 선행 계산 필요
//KnnItemBasedRecommender
//- 평균치('K')의 수
//- 아이템 유사도 측정
//- 아이템 수가 상대적으로 적을 때 좋은
//TreeClusteringRecommender
//- 이웃의 크기
//- 클러스터의 수
//-클러스터 유사도 정의
//- 사용자 유사도 측정
//- 실행 중에 추천이 빠름
//- 많은 선행 계산 필요

public class ItemRecommenderUsingTanimotoCoefficient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 1. 데이터 로딩
		try {
			DataModel model = new FileDataModel(new File("data/movie.csv"));
			System.out.println("data loading success");
			
			// 2. 유사성(Similarity : TanimotoCoefficient)
			TanimotoCoefficientSimilarity similarity = new TanimotoCoefficientSimilarity(model);
			
			// 3. 추천기(Recommender)
			ItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
			
			// 4. for loop 실행. hasNext가 false값이 나올 때까지
			for(LongPrimitiveIterator items = model.getItemIDs(); items.hasNext();) {
				long itemId = items.nextLong();
				// 5. 각 itemId당 추천 아이템 5개 씩
				List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemId, 5);
				
				for(RecommendedItem recommendation : recommendations) {
					System.out.println(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue());
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("There was an IOException");
			e.printStackTrace();
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			System.out.println("There was a TasteException");
			e.printStackTrace();
		}
	}

}
