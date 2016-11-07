import java.util.ArrayList;

public class Tree implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private Person root = null;
	private String name;
	final static String FILE_EXTENSION = ".ft";
	private Person currPerson;
	private ArrayList<Person> personList = null; 
	
	public Tree(String name)
	{
		this.name = name;
		personList = new ArrayList<Person>();
		currPerson = null;
	}
	
	public void insertPerson(Person p) 
	{
		personList.add(p);
		currPerson = p; 
		if(root == null)
		{
			root = p;
		}
		System.out.println(p.getName()+" has been added to "+this.name);
		TreeWriter.writeTreeToFile(this, name);
	}
	
	public void updatePerson(Person oldPerson, Person newPerson)
	{
		if(isPersonThere(oldPerson))
		{
			int i = personList.indexOf(oldPerson);
			personList.set(i, newPerson);
		System.out.println(oldPerson.getName()+" has been replaced with "+newPerson.getName()+" in "+this.name);
		}
		else
			System.out.println(oldPerson.getName() + "does not exist in this tree ("+this.name+")");
	}
	
	public void removePerson(Person p)
	{
		if(isPersonThere(p))
		{
			int i = personList.indexOf(p);
			personList.remove(i);
			System.out.println(p.getName()+" has been removed");
		}
		else
			System.out.println(p.getName() + "does not exist in this tree ("+this.name+")");

	}
	
	public boolean isPersonThere(Person p)
	{
		return personList.contains(p);
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
	
	public Person getCurrPerson()
	{
		return currPerson;
	}
	
	public void setCurrPerson(int personIndex)
	{
		currPerson = personList.get(personIndex); 
	}
	
	public static ArrayList<Tree> getListOfTrees()
	{
		ArrayList<Tree> listOfTrees = new ArrayList<Tree>();
		ArrayList<String> listOfTreeNames = TreeReader.getTreeListNames();
		for (String string : listOfTreeNames) {
			
		}
		
		return listOfTrees;
	}
	
	public ArrayList<Person> getPersonList()
	{
		return personList;
	}
}
