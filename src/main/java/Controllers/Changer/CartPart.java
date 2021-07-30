package Controllers.Changer;



public class CartPart {
	private int id;
	private String name;
	private int count;
	

	/**
	 * @param name
	 * @param count
	 */

	/**
	 * @param j 
	 * @param string 
	 * @param i 
	 * @param name
	 * @param count
	 */
	public CartPart(int i, String string, int j) {
		this.id = i;
		this.name = string;
		this.count = j;
	}
	
	
	
	@Override
	public String toString() {
		return "CartPart [id=" + id + ", name=" + name + ", count=" + count + "]";
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
}
