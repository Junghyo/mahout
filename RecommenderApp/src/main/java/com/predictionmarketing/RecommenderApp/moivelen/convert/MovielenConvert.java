package com.predictionmarketing.RecommenderApp.moivelen.convert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MovielenConvert {
	
	/**
	 cat u.data | cut -f1, 2, 3 | tr "\\t" ","
	 * @throws IOException 
	 */
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// 0. u.data : user id | item id | rating | timestamp.
		// 1. 데이터 로딩(BufferedReader 사용 : 속도 향상) 
		BufferedReader br = new BufferedReader(new FileReader("data/u.data"));
		// 2. moive.csv로 데이터 파일 저장 세팅
		BufferedWriter bw = new BufferedWriter(new FileWriter("data/movie.csv"));
		
		// 3. 데이터 로딩 후 데이터를 한줄 씩 읽어서 userid(values[0]), itemid(values[1]), rating(values[2])
		// 로 파일 새로 쓰기
		String line;
		while((line = br.readLine()) != null) {
			String[] values = line.split("\\t", -1);
			bw.write(values[0] + "," + values[1] + "," + values[2] + "\n");
		}
		// 새로 쓰기 완료 후 닫기.
		br.close();
		bw.close();
		System.out.println("success");
	}

}
