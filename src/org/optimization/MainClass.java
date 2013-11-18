package org.optimization;

import java.util.Map;
import java.util.Map.Entry;
	
	public class MainClass {

		public static void main(String[] args) {

			int blocks[]={700,500,250,380};
			int quantities[]={4,3,6,5};
			int i=0,max_size=2000;
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

