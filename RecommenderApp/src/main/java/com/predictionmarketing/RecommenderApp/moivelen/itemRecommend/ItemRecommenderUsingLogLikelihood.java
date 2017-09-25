package com.predictionmarketing.RecommenderApp.moivelen.itemRecommend;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class ItemRecommenderUsingLogLikelihood {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 0. moive.csv : user id | item id | rating 
		try {
			// 1. 데이터 로딩 : IOException
			DataModel model = new FileDataModel(new File("data/movie.csv"));
			System.out.println("Data loading success");
			
			// 2. 유사성(Similarity) : 아이템 유사성은 아이템 간의 유사한 정도를 측정해 지표로 삼는 방식
			ItemSimilarity similarity = new LogLikelihoodSimilarity(model);
			
			// 3. 추천기(Recommender) : 아이템 기반 추천기(Item-based Recommender)
			// 아이템의 유사성 정도를 지표로 삼는 추천기. 큰 볼륨의 데이터 셋에 적합하고 미리 계산 가능.
			// ex) A~D 아이템 중 A-B 동일 카테고리, C-D 동일 카테고리 가정한다면
			// 어떤 사용자가 A를 좋아할 경우 B를 추천
			GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
			
			// 개수 제한
//			int x = 1;
			// hasNext() : boolean값. 다음 값이 있으면 true, 없으면 false
			for(LongPrimitiveIterator items = model.getItemIDs(); items.hasNext(); ) {
				long itemId = items.nextLong();
				
				// 4. 해당하는 itemId마다 아이템 3개씩 추천
				List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemId, 5);
				
				for(RecommendedItem recommendation : recommendations) {
					System.out.println(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue());
				}
				// loop 완료마다 1씩 증가
//				x++;
//				// 10개 이상이면 loop문 종료
//				if(x > 10) System.exit(1);
				
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
