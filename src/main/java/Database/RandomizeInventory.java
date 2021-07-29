package Database;

import Database.Records.PartRecord;

public class RandomizeInventory {

	public static void main(String[] args) {
		
		DatabaseInterfacer db = new DatabaseInterfacer();
		
		for(PartRecord p : db.getAllPartRecords()) {
			p.setQuantity((int)(Math.random() * 1000));
			System.out.println("Updating " + p.getDescription() + " quantity to " +  p.getQuantity());
			db.update(p);
		}

	}

}
