import java.util.ArrayList;

public class MyStrategy2 extends YahtzeeComputerStrategy {

	String goFor = "";

	public void reroll(int[] dice, int rollNumber, PlayerRecord record, boolean[] reroll) {

		if (AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination
				.combinationIndex("SmallStraightCombination")].score(dice) == 0) {
			for (int i = 0; i < dice.length; i++) {
				for (int j = 0; j < dice.length; j++) {
					if (dice[j] == dice[i]) {
						reroll[i] = false;
					} else {
						reroll[i] = true;
					}
				}
			}
		}
	}

	public int chooseCombination(int[] dice, PlayerRecord record) {
		AbstractYahtzeeCombination[] availableCombinations = record.availableCombinations();
		int maxScore = 0;
		int indexOfMaxScore = -1;
		for (int i = 0; i < (availableCombinations.length); i++) {
			int score = availableCombinations[i].score(dice);

			if (availableCombinations[i].name().equals("AcesCombination")) {
				score = score / 1;
			} else if (availableCombinations[i].name().equals("TwosCombination")) {
				score = score / 2;
			} else if (availableCombinations[i].name().equals("ThreesCombination")) {
				score = score / 3;
			} else if (availableCombinations[i].name().equals("FoursCombination")) {
				score = score / 4;
			} else if (availableCombinations[i].name().equals("FivesCombination")) {
				score = score / 5;
			} else if (availableCombinations[i].name().equals("SixesCombination")) {
				score = score / 6;
			} else if (availableCombinations[i].name().equals("ChanceCombination")) {
				score = score / 7;
			} else if (availableCombinations[i].name().equals("ThreeOfAKindCombination")) {
				score = score / 6;
			} else if (availableCombinations[i].name().equals("FourOfAKindCombination")) {
				score = score / 4;
			} else if (availableCombinations[i].name().equals("FullHouseCombination")) {
				score = score * 3;
			} else if (availableCombinations[i].name().equals("SmallStraightCombination")) {
				score = score * 3;
			} else if (availableCombinations[i].name().equals("LargeStraightCombination")) {
				score = score * 3;
			} else if (availableCombinations[i].name().equals("YahtzeeCombination")) {
				score = score * 20;
			}

			if (score > maxScore) {
				maxScore = score;
				indexOfMaxScore = i;
			}
		}
		if (maxScore == 0) {
			if (availableCombinations[availableCombinations.length - 1].upperSection()) {
				return 0;
			} else {
				return availableCombinations.length - 1;
			}
		} else {
			return indexOfMaxScore;
		}
	}

	public String author() {
		return "Jacob Heuvel & Ethan Jones";
	}

	public String playerName() {
		return "Sans the Skeleton";
	}

	public void finalResults(int[] dice, PlayerRecord record) {

	}

}
