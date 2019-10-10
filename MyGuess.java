import java.lang.reflect.Array;
import java.util.HashSet;

public class MyGuess extends YahtzeeComputerStrategy {

	HashSet<Integer> numbers = new HashSet<Integer>();

	@Override
	public String playerName() {
		// TODO Auto-generated method stub
		return "b";
	}

	@Override
	public void reroll(int[] dice, int rollNumber, PlayerRecord record, boolean[] reroll) {
		int numToSave = 0;
		if (AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination
				.combinationIndex("YahtzeeCombination")].score(dice) == 0
				&& AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination
						.combinationIndex("FullHouseCombination")].score(dice) == 0
				&& AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination
						.combinationIndex("LargeStraightCombination")].score(dice) == 0
		// AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("ThreeOfAKindCombination")].score(dice)==0&&
		// &&AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("FourOfAKindCombination")].score(dice)==0)
		// AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("ThreesCombination")].score(dice)==0)
		// AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("SmallStraightCombination")].score(dice)==0)
		) {
			// for(int x=0; x<reroll.length;x++)
			// {
			// //if (dice[x] != number to save)
			// reroll[x]=true;
			//
			// }
			// }
			
			if (AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination
			                                 				.combinationIndex("YahtzeeCombination")].score(dice) == 0){
				if(dice[0]==1&&dice[1]==1&&dice[2]==1&&dice[3]==1) {
					reroll[4]=true;
				}
				if(dice[0]==2&&dice[1]==2&&dice[2]==2&&dice[3]==2) {
					reroll[4]=true;
				}
				if(dice[0]==3&&dice[1]==3&&dice[2]==3&&dice[3]==3) {
					reroll[4]=true;
				}
				if(dice[0]==4&&dice[1]==4&&dice[2]==4&&dice[3]==4) {
					reroll[4]=true;
				}
				if(dice[0]==5&&dice[1]==5&&dice[2]==5&&dice[3]==5) {
					reroll[4]=true;
				}
				if(dice[0]==6&&dice[1]==6&&dice[2]==6&&dice[3]==6) {
					reroll[4]=true;
				}
			}

			if (AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination
					.combinationIndex("SmallStraightCombination")].score(dice) != 0) {
				if(dice[0]==1&&dice[1]==2&&dice[2]==3&&dice[3]==4) {
					reroll[4]=true;
				}
				
				if(dice[0]==2&&dice[1]==3&&dice[2]==4&&dice[3]==5) {
					reroll[4]=true;
				}
				if(dice[0]==3&&dice[1]==4&&dice[2]==5&&dice[3]==6) {
					reroll[4]=true;
				}
			}

			else {
				for (int x = 0; x < reroll.length; x++) {
					if (numbers.add(dice[x]) == false) {
						numToSave = dice[x];
					}
				}

				for (int x = 0; x < reroll.length; x++) {
					if (dice[x] != numToSave) {
						reroll[x] = true;
					}
				}
			}

			// for(int x=0;x<reroll.length;x++) {
			// numbers.add(dice[x]);
			// if(numbers.size()==2) {
			// reroll[x]=true;
			// }
			// }
			//
			// for(int x=0;x<reroll.length;x++) {
			// numbers.add(dice[x]);
			// if(numbers.size()==2);
			//
			// }

		}
	}

	@Override
	public int chooseCombination(int[] dice, PlayerRecord record) {
		AbstractYahtzeeCombination[] availableCombinations;
		availableCombinations = record.availableCombinations();
		int maxScore = 0;
		int indexOfMaxScore = -1;
		for (int x = 0; x < availableCombinations.length; x++) {
			int score = availableCombinations[x].score(dice);
			if (score > maxScore) {
				maxScore = score;
				indexOfMaxScore = x;
			}
		}
		if (maxScore == 0) {
			{
				if (availableCombinations[availableCombinations.length - 1].upperSection()) {
					return 0;
				} else {
					return availableCombinations.length - 1;
				}
			}
		}
		

		return indexOfMaxScore;
	}

	@Override
	public void finalResults(int[] dice, PlayerRecord record) {
		// TODO Auto-generated method stub

	}

	@Override
	public String author() {
		// TODO Auto-generated method stub
		return "ben&nitins";
	}

}
