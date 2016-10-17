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
 * The Jock critter is a more athletic critter. Since it is 
 * healthier than the average critter, it runs everywhere it goes. It also fights everything, as it 
 * believes it is better than everything. It also reproduces a lot more often than the average critter
 * as jocks tend to partake in more inappropriate acts than the average critter and tends to not
 * be as smart about it. Since it is not the sharpest tool in the shed, it also can't seem to figure 
 * out how to move diagonally.It is a critter that thinks itself as so perfect that evolution isn't necessary
 */
public class Critter2 extends Critter{

	@Override
	public String toString() { return "2"; }
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public Critter2() {
		for (int k = 0; k < 8; k += 2) {
			genes[k] = GENE_TOTAL / 4;
		}
		
		dir = ((Critter.getRandomInt(8) / 2) * 2);//cannot move diagonally
	}
	public boolean fight(String not_used) { 
		return true;
		
	}

	@Override
	public void doTimeStep() {
		/* run two step forward */
		run(dir);
		
		if (getEnergy() > 2*Params.min_reproduce_energy) {
			Critter2 child = new Critter2();
			
			reproduce(child, Critter.getRandomInt(8));
		}
		
		dir = ((Critter.getRandomInt(8) / 2) * 2);//cannot move diagonally
	}

	public static void runStats(java.util.List<Critter> Jocks) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : Jocks) {
			Critter2 c = (Critter2) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
		}
		System.out.print("" + Jocks.size() + " total Critter2s    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * Jocks.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * Jocks.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * Jocks.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * Jocks.size()) + "% left   ");
		System.out.println();
	}
}
