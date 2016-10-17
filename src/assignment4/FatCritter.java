package assignment4;
/* CRITTERS <MyClass.java>
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Brian Madina>
 * <bjm3348>
 * <16460>
 * <Kyle Polansky>
 * <KPP446>
 * <16480>
 * Slip days used: <0>
 * Fall 2016
 */
/*
 * Critter that moves very seldom. It only walks and fights when it's hungry
 *  (energy level is below 100). It may walk when it isn't hungry in case it gets bored.
 *  It is also too lazy to reproduce and only does so when energy level is
 *  above 200.
 */

public class FatCritter extends Critter{


	@Override
	public String toString() { return "F"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public FatCritter() {
	
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String not_used) { 
		if(getEnergy() < 100 || not_used.equals("@"))//only fight if it's algae or not hungry
		{return true;}
		else{return false;}
		
	}

	@Override
	public void doTimeStep() {
		/* take one step forward only if hungry*/
		if(getEnergy() <= 100)
		walk(dir);
		else{
			if(Critter.getRandomInt(100) < 50)
			{
				walk(dir);
			}
		}
		if (getEnergy() > 200) {
			FatCritter child = new FatCritter();
			for (int k = 0; k < 8; k += 1) {
				child.genes[k] = this.genes[k];
			}
			int g = Critter.getRandomInt(8);
			while (child.genes[g] == 0) {
				g = Critter.getRandomInt(8);
			}
			child.genes[g] -= 1;
			g = Critter.getRandomInt(8);
			child.genes[g] += 1;
			reproduce(child, Critter.getRandomInt(8));
		}
		
		/* pick a new direction based on our genes */
		int roll = Critter.getRandomInt(GENE_TOTAL);
		int turn = 0;
		while (genes[turn] <= roll) {
			roll = roll - genes[turn];
			turn = turn + 1;
		}
		assert(turn < 8);
		
		dir = (dir + turn) % 8;
	}

	public static void runStats(java.util.List<Critter> fatCritters) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : fatCritters) {
			FatCritter c = (FatCritter) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
		}
		System.out.print("" + fatCritters.size() + " total fatCritters    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * fatCritters.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * fatCritters.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * fatCritters.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * fatCritters.size()) + "% left   ");
		System.out.println();
	}

}
