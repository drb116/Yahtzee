import java.util.HashSet;

public class BurnhamStrategy extends YahtzeeComputerStrategy {

	public void reroll(int[] dice, int rollNumber, PlayerRecord record,
			boolean[] reroll) {
		
		AbstractYahtzeeCombination[] availableCombinations;
		availableCombinations = record.availableCombinations();
		//Reroll starts as false for all
		
//		
		//leave as false for yahtzee, full house, and large straight
		if(AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("YahtzeeCombination")].score(dice)==0&&
				(AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("FullHouseCombination")].score(dice)==0||
				(AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("FullHouseCombination")].score(dice)!=0&&
				rollNumber<2))&&
				AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("LargeStraightCombination")].score(dice)==0)
		{
			//If small straight exists, go for large straight, otherwise keep pairs and and reroll
			if(AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("SmallStraightCombination")].score(dice)==30) {
				for(int x=1; x<dice.length;x++)
				{
					//12346,33456,13456
					if (dice[x]-dice[x-1]!=1) {
						if (x==1&&dice[1]==3) {
							reroll[x-1]=true;
						}
						else reroll[x]=true;
					}
				}
			}
				//My only combos are not straights
			else if (!((availableCombinations.length==2&&
					availableCombinations[0].name().equals("SmallStraightCombination")&&
					availableCombinations[1].name().equals("LargeStraightCombination"))
					||
					(availableCombinations.length==1&&(
					availableCombinations[0].name().equals("SmallStraightCombination")||
					availableCombinations[0].name().equals("LargeStraightCombination"))))){
				
				HashSet<Integer> myDice = new HashSet<Integer>();
				//Start at the top since I would rather keep larger pairs
				int numberToSave = 0;
				for(int i=dice.length-1; i>=0;i--) {
					if(!myDice.add(dice[i])) {
						//save the first duplicate we find that we need
						numberToSave = dice[i];
						//If I get a high pair, check to see if there is a low three of a kind
						if(i==3&&dice[2]==dice[1]&&dice[1]==dice[0])
							numberToSave = dice[2];
						i=-1;
					}
				}
				
				if (numberToSave==1&&rollNumber<=2) numberToSave=0;
				//Now loop through and reroll all others
				if (numberToSave>0) {
					for (int i=0;i<dice.length;i++) {
						if (dice[i]!=numberToSave) reroll[i]=true;
					}
				}
//				
				else //just reroll all (keep 5s and 6s)
				{
					for (int i=0;i<dice.length;i++) {
						if(!(dice[i]==6||dice[i]==5))
						reroll[i]=true;
					}
				}
			}
			else { //have to go for a straight, just reroll duplicates, or 1 and 6 (extremes)
				HashSet<Integer> myDice = new HashSet<Integer>();
				for (int i=0;i<dice.length;i++) {
					if(!myDice.add(dice[i])||
							dice[i]==1||
							dice[i]==6) 
						reroll[i]=true;
				}
			}
		}

	}

	public int chooseCombination(int[] dice, PlayerRecord record) {
		AbstractYahtzeeCombination[] availableCombinations;
		availableCombinations = record.availableCombinations();
		int maxScore=0;
		int indexOfMaxScore=-1;
		int threeOfKind=-1;
		
		//Don't include chance
		for(int x=0; x<availableCombinations.length-1;x++)
		{
			
			//Take 4 sixes if available
			if(availableCombinations[x].name().equals("SixesCombination")) {
				
				if (availableCombinations[x].score(dice)>20) {
					return x;
				}
			}
			
			//Otherwise, find the high score.
			int score = availableCombinations[x].score(dice);
			if(score >= maxScore)
		{
				
				maxScore = score;
				indexOfMaxScore = x;
			}
		}
		
		//If score is less than 25, check to see if you have 4 of a number.
		if (maxScore <25) {
			for(int x=0; x<availableCombinations.length-1;x++)
				 {
					if(availableCombinations[x].name().equals("SixesCombination")) {
						if (availableCombinations[x].score(dice)==12) {
							if(x<availableCombinations.length) {
								if(availableCombinations[x+1].score(dice)>0) {
									return x+1;
								}
							}
						}
					}
					if(availableCombinations[x].name().equals("FivesCombination")) {
						
						if (availableCombinations[x].score(dice)>=15) {
							return x;
						}
						else if (availableCombinations[x].score(dice)==10) {
							if(x<availableCombinations.length) {
								if(availableCombinations[x+1].score(dice)>0) {
									if(!availableCombinations[x+1].name().equals("SixesCombination"))
									return x+1;
								}
							}
							if(x<availableCombinations.length-2) {
								if(availableCombinations[x+2].score(dice)>0) {
									return x+2;
								}
							}
						}
					}
					if(availableCombinations[x].name().equals("FoursCombination")) {
						if (availableCombinations[x].score(dice)>=12) {
							return x;
						}
					}
					if(availableCombinations[x].name().equals("ThreesCombination")) {
						if (availableCombinations[x].score(dice)>=9) {
							return x;
						}
					}
					if(availableCombinations[x].name().equals("TwosCombination")) {
						if (availableCombinations[x].score(dice)>=6) {
							return x;
						}
					}
			}
			//Now check if you are better taking a low score on a low number
			if(indexOfMaxScore>0) {
				if(availableCombinations[indexOfMaxScore].name().equals("SixesCombination")) {
					if (maxScore<18) {
						for (int i=0;i<indexOfMaxScore;i++) {
							if(availableCombinations[i].name().equals("AcesCombination")){
								if (availableCombinations[i].score(dice)>=0) return i;
							}
							else if (availableCombinations[i].name().equals("TwosCombination")){
								if (availableCombinations[i].score(dice)>3) return i;
							}
							else if (availableCombinations[i].name().equals("ThreesCombination")){
								if (availableCombinations[i].score(dice)>6) return i;
							}
							else if (availableCombinations[i].name().equals("FoursCombination")){
								if (availableCombinations[i].score(dice)>10) return i;
							}
							else if (availableCombinations[i].name().equals("FivesCombination")){
								if (availableCombinations[i].score(dice)>10) return i;
							}
						}
						
					}
					
					
				}
				else if(availableCombinations[indexOfMaxScore].name().equals("FivesCombination")) {
					if (maxScore<15) {
						for (int i=0;i<indexOfMaxScore;i++) {
							if(availableCombinations[i].name().equals("AcesCombination")){
								if (availableCombinations[i].score(dice)>=0) return i;
							}
							else if (availableCombinations[i].name().equals("TwosCombination")){
								if (availableCombinations[i].score(dice)>=4) return i;
							}
							else if (availableCombinations[i].name().equals("ThreesCombination")){
								if (availableCombinations[i].score(dice)>=6) return i;
							}
						}
						
					}
				}
				else if(availableCombinations[indexOfMaxScore].name().equals("FoursCombination")) {
					if (maxScore<12) {
						for (int i=0;i<indexOfMaxScore;i++) {
							if(availableCombinations[i].name().equals("AcesCombination")){
								if (availableCombinations[i].score(dice)>2) return i;
							}
							else if (availableCombinations[i].name().equals("TwosCombination")){
								if (availableCombinations[i].score(dice)>4) return i;
							}
						}
						
					}
				}		
			}
		}
	
		if(maxScore == 0)
		{
			if (availableCombinations[availableCombinations.length-1].score(dice)>0) {
				return availableCombinations.length-1;
			}
			else if(availableCombinations[availableCombinations.length - 1].upperSection())
			{
				return 0;
			}
			else
			{
				return availableCombinations.length - 1;
			}
		}
		else
		{
			return indexOfMaxScore;
		}
		
	}

	public String author() {
		return "Mr. Burnham";
	}

	public String playerName() {
		return "Mr. Burnham";
	}

	public void finalResults(int[] dice, PlayerRecord record) {
		
	}
}
