package Controllers.Changer;



public class CartPart {
	private String name;
	private int count;
	

	/**
	 * @param name
	 * @param count
	 */
	public CartPart(String name, int count) {
		this.name = name;
		this.count = count;
	}

	/**
	 * @param name
	 * @param count
	 */
	public CartPart() {
		this.name = "";
		this.count = 0;
	}
	
	
	
	@Override
	public String toString() {
		return "CartPart [name=" + name + ", count=" + count + "]";
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
	
}
