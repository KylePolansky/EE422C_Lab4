package assignment4;
/*
 * The Jock critter is a more athletic critter. Since it is 
 * healthier than the average critter, it runs everywhere it goes. It also fights everything, as it 
 * believes it is better than everything. It also reproduces a lot more often than the average critter
 * as jocks tend to partake in more inappropriate acts than the average critter and tends to not
 * be as smart about it. Since it is not the sharpest tool in the shed, it also can't seem to figure 
 * out how to move diagonally.
 */
public class Jock extends Critter{

	@Override
	public String toString() { return "J"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public Jock() {
	
		for (int k = 0; k < 8; k += 2) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = ((Critter.getRandomInt(8) / 2) * 2);
	}
	public boolean fight(String not_used) { 
		return true;
		
	}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		run(dir);
		
		if (getEnergy() > 2*Params.min_reproduce_energy) {
			Jock child = new Jock();
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
		
		dir = (((dir + turn) % 8) / 2) * 2;//cannot go diagonal 
	}

	public static void runStats(java.util.List<Critter> Jocks) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : Jocks) {
			Jock c = (Jock) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
		}
		System.out.print("" + Jocks.size() + " total Craigs    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * Jocks.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * Jocks.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * Jocks.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * Jocks.size()) + "% left   ");
		System.out.println();
	}
}
