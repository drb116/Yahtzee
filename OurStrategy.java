
public class OurStrategy extends AbstractComputerYahtzeePlayer {
	@Override
	public String playerName() {
		return "Lise, Nathan, Amber";
	}
	
	@Override
	public void reroll(int[] dice, int rollNumber, PlayerRecord record, boolean[] reroll) {
		AbstractYahtzeeCombination[] availableCombinations;
		availableCombinations = record.availableCombinations();
		int[] numbers = new int[6];
		for(int i=0;i<dice.length;i++) {
			numbers[dice[i]-1]++;
		}
		boolean[] comboAvailable = new boolean[13];
		for(int i=0;i<comboAvailable.length;i++) {
			comboAvailable[i] = false;
			for(int j=0;j<availableCombinations.length;j++) {
				comboAvailable[AbstractYahtzeeCombination.combinationIndex(availableCombinations[j].name())] = true;
			}
		}
		boolean ones = comboAvailable[AbstractYahtzeeCombination.combinationIndex("AcesCombination")];
		boolean twos = comboAvailable[AbstractYahtzeeCombination.combinationIndex("TwosCombination")];
		boolean threes = comboAvailable[AbstractYahtzeeCombination.combinationIndex("ThreesCombination")];
		boolean fours = comboAvailable[AbstractYahtzeeCombination.combinationIndex("FoursCombination")];
		boolean fives = comboAvailable[AbstractYahtzeeCombination.combinationIndex("FivesCombination")];
		boolean sixes = comboAvailable[AbstractYahtzeeCombination.combinationIndex("SixesCombination")];
		boolean yahtzee = comboAvailable[AbstractYahtzeeCombination.combinationIndex("YahtzeeCombination")];
		boolean threeKind = comboAvailable[AbstractYahtzeeCombination.combinationIndex("ThreeOfAKindCombination")];
		boolean fourKind = comboAvailable[AbstractYahtzeeCombination.combinationIndex("FourOfAKindCombination")];
		boolean fullHouse = comboAvailable[AbstractYahtzeeCombination.combinationIndex("FullHouseCombination")];
		boolean smallStr = comboAvailable[AbstractYahtzeeCombination.combinationIndex("SmallStraightCombination")];
		boolean largeStr = comboAvailable[AbstractYahtzeeCombination.combinationIndex("LargeStraightCombination")];
		boolean chance = comboAvailable[AbstractYahtzeeCombination.combinationIndex("ChanceCombination")];
		
		int almostStr = -1;
		for(int i=0;i<3;i++) {
			int count = 0;
			for(int j=0;j<4;j++) {
				if(numbers[j+i]>0) {
					count++;
				}
			}
			if(count>=3) almostStr = i;
		}
		for(int i=0;i<almostStr;i++) {
			if(numbers[i]>0&&numbers[i+1]>0&&numbers[i+2]>0) {
				almostStr = i;
			}
		}
		
		if(dice[0]==dice[1] && dice[0]==dice[2] && dice[0]==dice[3] && dice[0]==dice[4]) {
			boolean taken = true;
			for(int i=0;i<availableCombinations.length;i++) {
				if(yahtzee || fourKind || threeKind || (ones && dice[0]==1) || (twos && dice[0]==2) || (threes && dice[0]==3) ||
						(fours && dice[0]==4) || (fives && dice[0]==5) || (sixes && dice[0]==6)) {
					taken = false;
				}
			}
			if(taken) {
				if(fullHouse) {
					reroll[0] = true;
					reroll[1] = true;
				}
				else {
					for(int i=0;i<reroll.length;i++) {
						reroll[i] = true;
					}
				}
			}
		}
		else if(numbers[0]==4||numbers[1]==4||numbers[2]==4||numbers[3]==4||numbers[4]==4||numbers[5]==4) {
			boolean numOfKind = (threeKind||fourKind);
			if(yahtzee) {
				if(dice[0]!=dice[1]) reroll[0] = true;
				else reroll[4] = true;
			}
			else if(ones&&numbers[0]==4) {
				reroll[4] = true;
			}
			else if(twos&&numbers[1]==4) {
				if(dice[0]==2) reroll[4] = true;
				else reroll[0] = true;
			}
			else if(threes&&numbers[2]==4) {
				if(dice[0]==3) reroll[4] = true;
				else reroll[0] = true;
			}
			else if((fours||numOfKind)&&numbers[3]==4) {
				if(fours) {
					if(dice[0]==4) reroll[4] = true;
					else reroll[0] = true;
				}
				else {
					if(dice[0]<4) reroll[0] = true;
				}
			}
			else if((fives||numOfKind)&&numbers[4]==4) {
				if(fives) {
					if(dice[0]==5) reroll[4] = true;
					else reroll[0] = true;
				}
				else {
					if(dice[0]<4) reroll[0] = true;
				}
			}
			else if(sixes) {
				reroll[0] = true;
			}
			else if(numOfKind&&numbers[5]==4) {
				if(dice[0]<4) reroll[0] = true;
			}
			else if(fullHouse) {
				reroll[1] = true;
			}
		}
		else if(numbers[0]==3) {
			if(ones) {
				reroll[3] = true;
				reroll[4] = true;
			}
			else if(fullHouse&&(numbers[1]==2||numbers[2]==2||numbers[3]==2||numbers[4]==2||numbers[5]==2)) {
				for(int i=0;i<reroll.length;i++) {
					reroll[i]=false;
				}
			}
			else if((smallStr||largeStr)&&((dice[3]==2&&(dice[4]==3||dice[3]==4))||(dice[3]==3&&dice[4]==4))) {
				reroll[0] = true;
				reroll[1] = true;
			}
			else if((numbers[1]==2&&twos)||(numbers[2]==2&&threes)||(numbers[3]==2&&(threeKind||fours))
					||(numbers[4]==2&&(threeKind||fives))||(numbers[5]==2&&(threeKind||sixes))) {
				for(int i=0;i<reroll.length;i++) {
					if(dice[i]==1) reroll[i] = true;
				}
			}
			else {
				for(int i=0;i<reroll.length;i++) {
					reroll[i] = true;
				}
			}
		}
		else if(numbers[1]==3) {
			if(twos) {
				for(int i=0;i<dice.length;i++) {
					if(dice[i]!=2) {
						reroll[i] = true;
					}
				}
			}
			else if(fullHouse&&(numbers[0]==2||numbers[2]==2||numbers[3]==2||numbers[4]==2||numbers[5]==2)) {
				for(int i=0;i<reroll.length;i++) {
					reroll[i]=false;
				}
			}
			else if((smallStr||largeStr)&&((dice[0]==1&&(dice[4]==3||dice[4]==4))
					||(dice[3]==3&&(dice[4]==4||dice[4]==5))||(dice[3]==4&&dice[4]==5))) {
				reroll[1] = true;
				reroll[2] = true;
			}
			else if((numbers[0]==2&&ones)||(numbers[2]==2&&threes)||(numbers[3]==2&&(threeKind||fours))
					||(numbers[4]==2&&(threeKind||fives))||(numbers[5]==2&&(threeKind||sixes))) {
				for(int i=0;i<reroll.length;i++) {
					if(dice[i]==2) reroll[i] = true;
				}
			}
			else {
				for(int i=0;i<reroll.length;i++) {
					reroll[i] = true;
				}
			}
		}
		else if(numbers[2]==3) {
			if(threes) {
				for(int i=0;i<dice.length;i++) {
					if(dice[i]!=3) {
						reroll[i] = true;
					}
				}
			}
			else if(fullHouse&&(numbers[0]==2||numbers[1]==2||numbers[3]==2||numbers[4]==2||numbers[5]==2)) {
				for(int i=0;i<reroll.length;i++) {
					reroll[i]=false;
				}
			}
			else if((smallStr||largeStr)&&((dice[3]==4&&(dice[4]==5||dice[4]==6))||(dice[3]==5&&dice[4]==6))) {
				reroll[0] = true;
				reroll[1] = true;
			}
			else if((smallStr||largeStr)&&((dice[0]==1&&(dice[1]==2||dice[4]==4))||(dice[0]==2&&(dice[4]==4||dice[4]==5)))) {
				reroll[2] = true;
				reroll[3] = true;
			}
			else if((numbers[0]==2&&ones)||(numbers[1]==2&&twos)||(numbers[3]==2&&(threeKind||fours))
					||(numbers[4]==2&&(threeKind||fives))||(numbers[5]==2&&(threeKind||sixes))) {
				for(int i=0;i<reroll.length;i++) {
					if(dice[i]==3) reroll[i] = true;
				}
			}
			else {
				for(int i=0;i<reroll.length;i++) {
					reroll[i] = true;
				}
			}
		}
		else if(numbers[3]==3) {
			if(fours) {
				for(int i=0;i<dice.length;i++) {
					if(dice[i]!=4) {
						reroll[i] = true;
					}
				}
			}
			else if(fullHouse&&(numbers[0]==2||numbers[1]==2||numbers[2]==2||numbers[4]==2||numbers[5]==2)) {
				for(int i=0;i<reroll.length;i++) {
					reroll[i]=false;
				}
			}
			else if((smallStr||largeStr)&&((dice[0]==1&&(dice[1]==2||dice[1]==3))||(dice[0]==2&&dice[1]==3))) {
				reroll[3] = true;
				reroll[4] = true;
			}
			else if((smallStr||largeStr)&&((dice[4]==6&&(dice[3]==5||dice[0]==3))||(dice[4]==5&&(dice[0]==2||dice[0]==3)))) {
				reroll[1] = true;
				reroll[2] = true;
			}
			else if((numbers[0]==2&&ones)||(numbers[1]==2&&twos)||(numbers[2]==2&&threes)
					||(numbers[4]==2&&fives)||(numbers[5]==2&&sixes)) {
				for(int i=0;i<reroll.length;i++) {
					if(dice[i]==4) reroll[i] = true;
				}
			}
			else if(threeKind||chance) {
				for(int i=0;i<reroll.length;i++) {
					if(dice[i]<4) reroll[i] = true;
				}
			}
			else {
				for(int i=0;i<reroll.length;i++) {
					reroll[i] = true;
				}
			}
		}
		else if(numbers[4]==3) {
			if(fives) {
				for(int i=0;i<dice.length;i++) {
					if(dice[i]!=5) {
						reroll[i] = true;
					}
				}
			}
			else if(fullHouse&&(numbers[0]==2||numbers[1]==2||numbers[2]==2||numbers[3]==2||numbers[5]==2)) {
				for(int i=0;i<reroll.length;i++) {
					reroll[i]=false;
				}
			}
			else if((smallStr||largeStr)&&((dice[4]==6&&(dice[0]==3||dice[0]==4))
					||(dice[0]==2&&(dice[1]==3||dice[1]==4))||(dice[0]==3&&dice[1]==4))) {
				reroll[2] = true;
				reroll[3] = true;
			}
			else if((numbers[0]==2&&ones)||(numbers[1]==2&&twos)||(numbers[2]==2&&threes)
					||(numbers[3]==2&&fours)||(numbers[5]==2&&sixes)) {
				for(int i=0;i<reroll.length;i++) {
					if(dice[i]==5) reroll[i] = true;
				}
			}
			else if(threeKind||chance) {
				for(int i=0;i<reroll.length;i++) {
					if(dice[i]<4) reroll[i] = true;
				}
			}
			else {
				for(int i=0;i<reroll.length;i++) {
					reroll[i] = true;
				}
			}
		}
		else if(numbers[5]==3) {
			if(sixes) {
				reroll[0] = true;
				reroll[1] = true;
			}
			else if(fullHouse&&(numbers[0]==2||numbers[1]==2||numbers[2]==2||numbers[3]==2||numbers[4]==2)) {
				for(int i=0;i<reroll.length;i++) {
					reroll[i]=false;
				}
			}
			else if((smallStr||largeStr)&&((dice[0]==3&&(dice[1]==4||dice[1]==5))||(dice[0]==4&&dice[1]==5))) {
				reroll[3] = true;
				reroll[4] = true;
			}
			else if((numbers[0]==2&&ones)||(numbers[1]==2&&twos)||(numbers[2]==2&&threes)
					||(numbers[3]==2&&fours)||(numbers[4]==2&&fives)) {
				for(int i=0;i<reroll.length;i++) {
					if(dice[i]==6) reroll[i] = true;
				}
			}
			else if(threeKind||chance) {
				for(int i=0;i<reroll.length;i++) {
					if(dice[i]<4) reroll[i] = true;
				}
			}
			else {
				for(int i=0;i<reroll.length;i++) {
					reroll[i] = true;
				}
			}
		}
		else if(AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("SmallStraightCombination")].score(dice)>0
					&&(smallStr||largeStr)) {
			for(int i=0;i<reroll.length-1;i++) {
				if(dice[i]==dice[i+1]) reroll[i] = true;
			}
			if(dice[1]-dice[0]>1) reroll[0] = true;
			if(dice[4]-dice[3]>1) reroll[4] = true;
		}
		else if(almostStr>=0 && smallStr) {
			for(int i=0;i<reroll.length;i++) {
				if(dice[i]>almostStr+4||(i>0&&dice[i]==dice[i-1])||dice[i]<almostStr+1) {
					reroll[i] = true;
				}
			}
		}
//		else if(numbers[5]==2) {
//			if(sixes) {
//				reroll[0] = true;
//				reroll[1] = true;
//				reroll[2] = true;
//			}
//		}
//		else if(numbers[4]==2) {
//			
//		}
//		else if(numbers[3]==2) {
//			
//		}
//		else if(numbers[2]==2) {
//			
//		}
//		else if(numbers[1]==2) {
//			
//		}
//		else if(numbers[0]==2) {
//			
//		}
		else {
			for(int x=0; x<reroll.length;x++)
			{
				int count=0;
				int j=0;
				while(j<dice.length)
				{
					if(dice[x]==dice[j])
						count++;
					j++;
				}
				if(count>1)
					reroll[x]=false;
				else
					reroll[x]=true;

			}
		}
	}
	
	
	//Yahtzee, Large, Small, 4ofKind, Chance
	public int chooseCombination(int[] dice, PlayerRecord record) {
		AbstractYahtzeeCombination[] availableCombinations;
		availableCombinations = record.availableCombinations();
		
		//Check Yahtzee
		for(int i=availableCombinations.length-1;i>=0;i--) {
			if(availableCombinations[i].name().equals("YahtzeeCombination")&&availableCombinations[i].score(dice)>0 ||
					availableCombinations[i].name().equals("LargeStraightCombination")&&availableCombinations[i].score(dice)>0 ||
					availableCombinations[i].name().equals("SmallStraightCombination")&&availableCombinations[i].score(dice)>0 ||
					availableCombinations[i].name().equals("FullHouseCombination")&&availableCombinations[i].score(dice)>0 ||
					availableCombinations[i].name().equals("FourOfAKindCombination")&&availableCombinations[i].score(dice)>0 ||
					availableCombinations[i].name().equals("ThreeOfAKindCombination")&&availableCombinations[i].score(dice)>0 ||
					availableCombinations[i].name().equals("SixesCombination")&&availableCombinations[i].score(dice)>=18 ||
					availableCombinations[i].name().equals("FivesCombination")&&availableCombinations[i].score(dice)>=15 ||
					availableCombinations[i].name().equals("FoursCombination")&&availableCombinations[i].score(dice)>=12 ||
					availableCombinations[i].name().equals("ThreesCombination")&&availableCombinations[i].score(dice)>=9 ||
					availableCombinations[i].name().equals("TwosCombination")&&availableCombinations[i].score(dice)>=6 ||
					availableCombinations[i].name().equals("AcesCombination")&&availableCombinations[i].score(dice)>=4) {
				return i;
			}
		}
		if(availableCombinations[availableCombinations.length-1].name().equals("ChanceCombination") &&
				availableCombinations[availableCombinations.length-1].score(dice)>=20)
		{
			return availableCombinations.length-1;
		}
		for(int i=0;i<availableCombinations.length;i++) {
			if(availableCombinations[i].name().equals("AcesCombination")&&availableCombinations[i].score(dice)>0) {
				return i;
			}
			else if(availableCombinations[i].name().equals("TwosCombination")&&availableCombinations[i].score(dice)>0) {
				return i;
			}
			else if(availableCombinations[i].name().equals("ThreesCombination")&&availableCombinations[i].score(dice)>0) {
				return i;
			}
			else if(availableCombinations[i].name().equals("FoursCombination")&&availableCombinations[i].score(dice)>0) {
				return i;
			}
			else if(availableCombinations[i].name().equals("FivesCombination")&&availableCombinations[i].score(dice)>0) {
				return i;
			}
			else if(availableCombinations[i].name().equals("SixesCombination")&&availableCombinations[i].score(dice)>0) {
				return i;
			}
		}
		
		return availableCombinations.length-1;
	}
	
	@Override
	public void finalResults(int[] dice, PlayerRecord record) {
		//dc
	}
	
	@Override
	public String author() {
		return "Lise C., Nathan G., Amber H.";
	}
}

