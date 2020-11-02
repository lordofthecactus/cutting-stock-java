package org.optimization;

import java.util.*;

public class CuttingStock {
	
	private int block[],qty[],comb[],tempcomb[],limit[];
	@SuppressWarnings("unused")
	private int max,total,counter=0,waste=0;
	private List<Map<Integer,Integer>> mapList=new ArrayList<Map<Integer, Integer>>();
	private List<Integer> store=new ArrayList<Integer>();
	private int count=0;
	
	public boolean hasMoreCombinations()
	{
		if(count<counter)
			return true;
		else
			return false;
	}
	public synchronized Map<Integer, Integer> nextCombination()
	{
		Map<Integer, Integer> map=mapList.get(count);
		count++;
		return map;
	}
	
	public CuttingStock(int max,int block[],int quantity[]) throws InvalidLegthException,InvalidParameterException
	{
		Arrays.stream(block).forEach((p) ->{
			if (p > max) {
				throw new InvalidLegthException();
			}
		});

	    if(block.length!=quantity.length)
	    {
	    	throw new InvalidLegthException();
	    }
		this.total=block.length;
	    this.max=max;
	    this.block=block;
		this.qty=quantity;
		this.doIt();
	  }
	private void doIt()
	    {
	      this.initialize();
	      /*for(int i=0;i<stock.size();i++)
			{
	    	  for(int j=0;j<stock.get(i).comb.length;j++)
	    	  {
	    		if(stock.get(i).comb[j]>0)
				System.out.println(block[j]+"  *  "+this.stock.get(i).comb[j]);
	    	  }
			}*/
	    }
	private void initialize()
	  {
	    store=new ArrayList<Integer>();
	    waste=0;
	    counter=0;
	    this.sort();
	    this.calculate(store);
	    /*wast_array=store.toArray();
	    if(wast_array.length>0)
	    {
	      System.out.println("Consider reusing the following remains");    
	      for(int i=wast_array.length-1;i>=0;i--)
	      {
	        System.out.println((this.counter+i-wast_array.length+1)+" "+wast_array[i]);
	      }
	      //out.println("</table><br><br>");
	    }
	    System.out.println("No of pieces req = "+this.counter);    
	    System.out.println("Waste = "+this.waste);*/
	  }
	  private void sort()
	  {
	    int tmp;
	    boolean swap;
	    do
	    {
	      swap=false;
	      for(int j=0;j<total-1;j++)
	      {
	        if(block[j+1]>block[j])
	        {
	          tmp=block[j];
	          block[j]=block[j+1];
	          block[j+1]=tmp;

	          tmp=qty[j];
	          qty[j]=qty[j+1];
	          qty[j+1]=tmp;
	          swap=true;
	        }
	      }
	    }while(swap);
	  }
	  private void calculate(List<Integer> store)
	  {
	    initLimit();
	    boolean start=true,chaloo=true;
	    int best=0,sum=0;
	    comb=new int[total];
	    while(start)
	    {
	      ////out.println("At start again");                            // DELETE IT
	      this.combinations();
	      
	     /* for(int i=0;i<total;i++)                                            //CHECK.......
	      {
	        //out.println(block[i]+"&nbsp;"+comb[i]);
	      }*/
	      sum=0;
	      for(int i=0;i<total;i++)
	      {
	        sum+=block[i]*comb[i];
	        if(sum>max)
	        {
	          sum=0;
	          break;
	        }
	      }

	      ////out.println("sum = "+sum);                                    // CHECK............

	      if(sum>0) //if a comb suited
	      {
	        if(sum==max) // if best comb found
	        {
	          ////out.println("Sum = "+sum);                                  //  DELETEc IT
	          this.showComb(0,store);  //print comb
	          resetComb();
			  updateLimit();
	          best=0;
	          sum=0;
	        }
	        else
	          if(sum>best)
	          {
	            best=sum;
	            tempcomb =new int[total];
	            for(int i=0;i<total;i++) // storing best comb in tempComb[]
	              tempcomb[i]=comb[i];
	            sum=0;
	          }
	      }
	      for(int i=0;i<total;i++)  //to check whether all comb done
	      {
	        if(comb[i]!=limit[i])
	        {
	          chaloo=true;
	          break;
	        }
	        chaloo=false;
	      }
	      if(!chaloo) // when all comb completed
	      {
	        //for(int i=0;i<total;i++) // storing best comb in tempComb[] ...Testing
	          //    //out.print(tempcomb[i]);
	        //for(int i=0;i<total;i++) // storing best comb in tempComb[] ...Testing
	          //    //out.print(comb[i]);
	        this.showComb(best,store);
	        updateLimit();
	        resetComb();
	        best=0;
	      }////out.println("B4 start loop");                            // DELETE IT
	      for(int i=0;i<total;i++) // To end while loop when no more pieces left
	      {
	        if(qty[i]==0 && i!=total-1)
	          continue;
	        else if(i==total-1 && qty[i]==0)
	          start=false;
	        break;
	      }/*//out.println("After start loop");                            // DELETE IT
	     for(int i=0;i<total;i++)                                         ////////
	          //out.print(qty[i]);                                 /////////
	     //out.println(); */                                            //////////
	    }
	  }
	  private void showComb(int a, List<Integer>store )
	  {
		counter++;
		
	    boolean flag=false;
	    //out.println("<font color=\"brown\">=====================================</font><br>Piece no "+counter+"<br>----------<br>");
	    if(a==0)
	    {
	    	Map<Integer, Integer> tempMap = new HashMap<Integer, Integer>();
	      for(int i=0;i<total;i++)
	        if(comb[i]!=0)
	        {
	            tempMap.put(new Integer(block[i]), new Integer(comb[i]));
	        	//System.out.println(block[i]+"  *  "+comb[i]);
	        	qty[i]=qty[i]-comb[i]; //  deduct samples from stock(qty) which are already printed
	        	if((qty[i]-comb[i])<0)
	        	{
	        		flag=true; // to return and not recursively call.
	        	}
	        }
	      
	            
			  if(flag)
			  {
				  mapList.add(tempMap);
				  return;
			  }
			  showComb(0,store);
	    }
	    else
	    {
	    	Map<Integer, Integer> tempMap = new HashMap<Integer, Integer>();
	      for(int i=0;i<total;i++)
	        if(tempcomb[i]!=0)
	        {
		         tempMap.put(new Integer(block[i]), new Integer(tempcomb[i]));
	        	 //System.out.println(block[i]+"  ggg  "+tempcomb[i]);
	        }
	      mapList.add(tempMap); 
	      //out.println("----------");
		  //System.out.println("\nThis piece remains = "+(max-a));
		  waste+=max-a;
	      store.add(max-a);
	        for(int i=0;i<total;i++)
	          qty[i]=qty[i]-tempcomb[i];
			 
	        for(int i=0;i<total;i++)
			  {
			    if((qty[i]-comb[i])<0)
				{
			    	return;			
				}
			  }
			  showComb(a,store);
	    }
	  }
	  private void combinations()
	  {
	      for(int i=total-1;;)
	      {
	        if(comb[i]!=limit[i])
	        {
	          comb[i]++;
	          break;
	        }
	        else
	        {
	          if(i==0 && comb[0]!=limit[0])
	            i=total-1;
	          else
	          {
	            comb[i]=0;
	            i--;
	          }
	        }
	      }
	  }
	  private void initLimit()
	  {
	    int div;
		limit=new int[total];
	    for(int i=0;i<total;i++)
		{
		  div=max/block[i];
		  if(qty[i]>div)
		    limit[i]=div;
		  else
		    limit[i]=qty[i];
	    }
	  }
	  private void updateLimit()
	  {
	    for(int i=0;i<total;i++)
		{
		  if(qty[i]<limit[i])
		    limit[i]=qty[i];
		}
	  }
	  private void resetComb()
	  {
	    for(int i=0;i<total;i++) // reset comb[]
	      comb[i]=0;
	  }

}
