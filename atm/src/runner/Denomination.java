package runner;

import inputcenter.InputCenter;

public class Denomination {
	public static void main(String[] args)
	{
		InputCenter input = new InputCenter();
		
		int n = input.getInt("Rupees : ");
		int t,f,h ;
		t=f=h=0;
		if(n>3000)
		{
			t = n/2000;
		}
		if(n>1000)
		{
			f = (n-(t*2000))/500;
		}
			h = (n-((t*2000)+(f*500)))/100;
			System.out.println(t+"- Thousand "+f+" - Five Hundreds and "+h+" - Hundreds\n");
		
	}
}
