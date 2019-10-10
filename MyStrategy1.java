
public class MyStrategy1 extends YahtzeeComputerStrategy {

	public void reroll(int[] dice, int rollNumber, PlayerRecord record, boolean[] reroll) {
		int numDice=0;
		if (AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("YahtzeeCombination")].score(dice) != 0) {
		return;
		} else if (record.rawCombinations()[AbstractYahtzeeCombination.combinationIndex("SmallStraightCombination")] != null && AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("SmallStraightCombination")].score(dice) != 0) {
			return;
		} else if (record.rawCombinations()[AbstractYahtzeeCombination.combinationIndex("LargeStraightCombination")] != null && AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("LargeStraightCombination")].score(dice) != 0) {
			return;
		} else if (record.rawCombinations()[AbstractYahtzeeCombination.combinationIndex("FullHouseCombination")] != null && AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("FullHouseCombination")].score(dice) != 0) {
			return;
		}
		for(int x=0; x<dice.length; x++) {
			for(int y=0; y<dice.length;y++) {
				if(dice[y] == dice[x]) numDice++;
			}
			if (numDice == 3 || numDice == 4 || numDice == 5) {
				break;
			}
			numDice = 0;
		} 
		if (numDice == 3 && dice[2]==dice[3] && dice[3]==dice[4]) {
			reroll[0] = true;
			reroll[1] = true;
		} else if (numDice == 3 && dice[0]==dice[1] && dice[1]==dice[2]) {
			reroll[3] = true;
			reroll[4] = true;
		} else if (numDice == 3 && dice[1]==dice[2] && dice[2]==dice[3]) {
			reroll[0] = true;
			reroll[4] = true;
		} else if (numDice == 4 && dice[0]==dice[1] && dice[1]==dice[2] && dice[2]==dice[3]) {
			reroll[4] = true;
		} else if (numDice == 4 && dice[1]==dice[2] && dice[2]==dice[3] && dice[3]==dice[4]) {
			reroll[0] = true;
		} else if (numDice == 5) {
			return;
		} else if (record.rawCombinations()[AbstractYahtzeeCombination.combinationIndex("FullHouseCombination")] != null && dice[0]==dice[1] && dice[2]==dice[3] && dice[1]!=dice[2]) {
			reroll[4] = true;
		} else if (record.rawCombinations()[AbstractYahtzeeCombination.combinationIndex("FullHouseCombination")] != null && dice[3]==dice[4] && dice[1]==dice[2] && dice[2]!=dice[3]) {
			reroll[0] = true;
		} else {
			for (int x=0;x<dice.length;x++) {
				for(int y=0; y<dice.length;y++) {
					if(dice[y] == dice[x]) reroll[x] = false;
					else reroll[x] = true;
				}
			}
		}
	}

	public int chooseCombination(int[] dice, PlayerRecord record) {
		AbstractYahtzeeCombination[] availableCombinations;
		availableCombinations = record.availableCombinations();
		int maxScore = 0;
		int indexOfMaxScore = 0;
		int totScore = 0;
		for (int x = 0; x < availableCombinations.length; x++) {
			int score = availableCombinations[x].score(dice);
			if (x != availableCombinations.length-1) totScore += score;
			if (score > maxScore) {
				maxScore = score;
				indexOfMaxScore = x;
			}
		}
		if (totScore == 0) {
			return availableCombinations.length-1;
		}
		if (maxScore == 0) {
			if (availableCombinations[availableCombinations.length - 1].upperSection()) {
				return 0;
			} else {
				if (availableCombinations.length == 1) return availableCombinations.length - 1;
				else return availableCombinations.length-2;
			}
		} else {
			return indexOfMaxScore;
		}
	}

	public String author() {
		return "Big Boys";
	}

	public String playerName() {
		return "Isaac B, Natanial A, Cesar M";
	}

	public void finalResults(int[] dice, PlayerRecord record) {
		
	}
}
