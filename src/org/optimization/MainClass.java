package org.optimization;

import java.util.Map;
import java.util.Map.Entry;
	
	public class MainClass {

		public static void main(String[] args) {

			int blocks[]={225,175, 20,60, 25, 420, 50, 950};
			int quantities[]={2,2,2,2, 4, 2, 4, 2};
			int i=0,max_size=1000;
			Map<Integer, Integer> map;
		    CuttingStock cuttingStock= new CuttingStock(max_size,blocks,quantities);
			while(cuttingStock.hasMoreCombinations())
			{
				System.out.println("\nCombination no "+(++i));
				map=cuttingStock.nextCombination();
				for (Entry<Integer, Integer> entry : map.entrySet()) 
				{
					  Integer key = entry.getKey();
					  Integer value = entry.getValue();
					  System.out.println(key+"  *  "+value);
				}
			}
		}
	}

