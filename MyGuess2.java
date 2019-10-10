import java.util.HashSet;

public class MyGuess2 extends YahtzeeComputerStrategy {

	@Override
	public String playerName() {
		// TODO Auto-generated method stub
		return "oof";
	}

	@Override
	public void reroll(int[] dice, int rollNumber, PlayerRecord record, boolean[] reroll) {
		if (AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination
				.combinationIndex("YahtzeeCombination")].score(dice) == 0
				&& AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination
						.combinationIndex("FullHouseCombination")].score(dice) == 0
				&& AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination
						.combinationIndex("LargeStraightCombination")].score(dice) == 0)
		// AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("FourOfAKindCombination")].score(dice)==0)
		{
			int numberToKeep = 0;
			HashSet<Integer> diceNum = new HashSet<Integer>();

			for (int x = 0; x < dice.length; x++) {
				if (!diceNum.add(dice[x])) {
					numberToKeep = dice[x];
				}

			}
			
			for (int x = 0; x < dice.length; x++) {
				if(dice[x] <=3) {
					reroll[x] = true;
				}
			}
			for (int x = 0; x < reroll.length; x++) {
				if (dice[x] != numberToKeep)
					reroll[x] = true;
			}
			
			
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
		for (int x = 0; x < availableCombinations.length; x++) {
			if (availableCombinations[x].name().equals("SixesCombination")) {
				if (availableCombinations[x].score(dice) >=24)
					return x;
			}
		}
		
		for (int x = 0; x < availableCombinations.length; x++) {
			if (availableCombinations[x].name().equals("FivesCombination")) {
				if (availableCombinations[x].score(dice) >=20)
					return x;
			}
		}
		
		
	

//		if ((AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination
//				.combinationIndex("FourOfAKindCombination")].score(dice) >= 20)) {
//			if (availableCombinations[availableCombinations.length - 1].upperSection()) {
//				return availableCombinations.length;
//			}
//		} else {
//			return indexOfMaxScore;
//		}
//
//		if ((AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination
//				.combinationIndex("SixesCombination")].score(dice) >= 24)) {
//			if (availableCombinations[availableCombinations.length - 1].upperSection()) {
//				return availableCombinations.length;
//			}
//		} else {
//			return indexOfMaxScore;
//		}
		return indexOfMaxScore;
	}

	@Override
	public void finalResults(int[] dice, PlayerRecord record) {
		// TODO Auto-generated method stub

	}

	@Override
	public String author() {
		// TODO Auto-generated method stub
		return "tyrone and ken";
	}

}
