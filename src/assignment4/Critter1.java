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
 * a fat critter that moves very seldom. It only walks and fights when it's hungry
 *  (energy level is below 100). It may walk when it isn't hungry in case it gets bored.
 *  It is also too lazy to reproduce and only does so when energy level is
 *  above 200. Also, it is too lazy to evolve.
 */

public class Critter1 extends Critter{


	@Override
	public String toString() { return "1"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public Critter1() {
		for (int k = 0; k < 8; k += 1) {
			genes[getRandomInt(8)] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String not_used) { 
		if(getEnergy() < 100 || not_used.equals("@"))//only fight if it's algae or hungry
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
			Critter1 child = new Critter1();
			reproduce(child, Critter.getRandomInt(8));
		}
				
		dir = Critter.getRandomInt(8);
	}

	public static void runStats(java.util.List<Critter> Critter1s) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : Critter1s) {
			Critter1 c = (Critter1) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
		}
		System.out.print("" + Critter1s.size() + " total Critter1s    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * Critter1s.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * Critter1s.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * Critter1s.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * Critter1s.size()) + "% left   ");
		System.out.println();
	}

}
