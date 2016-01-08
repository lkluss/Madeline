package pl.ftims.sc.Madeline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class WeightsGenerator {

	public static List<Double> getRandomList(int size){
		
		List<Double> randomList = new ArrayList<Double>();
		
		for(int i=0; i<size; i++){
			Date date = new Date();
			Random random = new Random(date.getTime());
			randomList.add(1.0 * random.nextDouble());
		}
		
		return randomList;
		
	}
	
}
