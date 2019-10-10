

public class YahtzeePlayerEvaluator {

	
	public static double evaluatePlayer(YahtzeePlayer player, int games)
	{
		double totalScore=0;
		int maxScore = 0;
		YahtzeeGame game = new YahtzeeGame();
		System.out.print(player.playerName() + "  ");
		for(int x=0; x<games;x++)
		{
			//This is just so people can see how much longer they have to wait lol
			if(x%(int)(games/20) == 0)
				//System.out.println((100*x/games) + "%");
				System.out.print(".");
			double score = game.yahtzeeGame(player);
			totalScore += score;
			if (score>maxScore) maxScore = (int) score;
		}
		//System.out.println(player.playerName());
		System.out.println();
		System.out.println("Max Score: " + maxScore);
		System.out.println("Average Score: " + totalScore / games);
		System.out.println();
		return (totalScore / games);
	}
}
