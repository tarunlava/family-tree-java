import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Person implements java.io.Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**may to be used in future for more accurate db? */
	private UUID personId; 
	private String name;
	private Date DOB;
	private Gender gender;
	private boolean alive;
//	private Person mom;
//	private Person dad;
//	private ArrayList<Person> siblings;
//	private ArrayList<Person> spouse;
	private ArrayList<Person> relationPerson;
	private ArrayList<BasicRelation> relationName;

	public Person(String name){
		this.name= name;
		relationPerson = new ArrayList<>();
		relationName = new ArrayList<>();
	}
	
	public Person(String name, Date DOB){
		this.name = name;
		this.DOB = DOB;
		relationPerson = new ArrayList<>();
		relationName = new ArrayList<>();
	}
	
	public Person(String name, Gender gender)
	{
		this.name = name;
		this.gender = gender;
		relationPerson = new ArrayList<>();
		relationName = new ArrayList<>();	
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDOB() {
		return DOB;
	}

	public void setDOB(Date dOB) {
		DOB = dOB;
	}

//	public Person getMom() {
//		return mom;
//	}
//
//	public void setMom(Person mom) {
//		this.mom = mom;
//	}
//
//	public Person getDad() {
//		return dad;
//	}
//
//	public void setDad(Person dad) {
//		this.dad = dad;
//	}
//
//	public ArrayList<Person> getSiblings() {
//		return siblings;
//	}
//
//	public void setSiblings(ArrayList<Person> siblings) {
//		this.siblings = siblings;
//	}
//
//	public ArrayList<Person> getSpouse() {
//		return spouse;
//	}
//
//	public void setSpouse(ArrayList<Person> spouse) {
//		this.spouse = spouse;
//	}
	
	public void printData()
	{
		try{
		System.out.println("Name  : " + name);
		System.out.println("DOB   : " + DOB);
		System.out.println("Gender: " + gender);
		System.out.println("Alive?: " + alive);
		}catch(NullPointerException e)
		{
			System.out.println("Data incomplete");
		}
	}
	
	public void printRelationships()
	{
		System.out.println(name + "'s relationships are: ");
		if(checkRelationLists() == 0 )
		{
//			ArrayList<BasicRelation> tempBR = currTree.getCurrPerson().getRelationName();
//			ArrayList<Person> tempName = currTree.getCurrPerson().getRelationPerson();
			for(int i = 0 ; i <relationName.size(); i++)
			{
				System.out.println(relationPerson.get(i).getName() + ":" + relationName.get(i));
			}
		}
		else
			System.err.println("Relations are mismatched");
	}

	public ArrayList<Person> getRelationPerson() {
		return relationPerson;
	}

	public void setRelationPerson(ArrayList<Person> relationPerson) {
		this.relationPerson = relationPerson;
	}

	public ArrayList<BasicRelation> getRelationName() {
		return relationName;
	}

	public void setRelationName(ArrayList<BasicRelation> relationName) {
		this.relationName = relationName;
	}
	
	// need to do check  if the person is already in the arraylist
	// possible for a person to have multiple relations with someone incase of inbreeding BUT not the same type
	public void addRelation(Person newRelation, BasicRelation relationType)
	{
		if(checkRelationLists()==0)
		{
			if (newRelation != this)
				if(doesRelationAlreadyExists(newRelation, relationType)==false)
				{
					relationName.add(relationType);
					relationPerson.add(newRelation);
				}
				else
					System.err.println("Relation already exists");
			else
				System.err.println("Cannot assign an relation to yourself");
		}
		else
			System.err.println("List mismatch");
	}
	/**
	 * checks if the relation is already added, if so, then the reverse relation will be added 
	 *  ex. if john is added as a son to jack, then jack will be added as a father to john, if it does not already exist
	 */
	public void addReverseRelation(Person newRelation, BasicRelation relationType)
	{
		if(doesRelationAlreadyExists(newRelation, relationType))
		{
			BasicRelation reverseRelation = getReverseRelation(gender, relationType);
			newRelation.addRelation(this, reverseRelation);
		}
	}
	
	public BasicRelation getReverseRelation(Gender gen, BasicRelation relationType)
	{
		switch(relationType)
		{
		case SON:
		case DAUGHTER:
			if(gen == Gender.FEMALE)
				return BasicRelation.MOTHER;
			if(gen == Gender.MALE)
				return BasicRelation.FATHER;
			break;
		case MOTHER:
		case FATHER:
			if(gen == Gender.FEMALE)
				return BasicRelation.DAUGHTER;
			if(gen == Gender.MALE)
				return BasicRelation.SON;
			break;
		case SISTER:
		case BROTHER:
			if(gen == Gender.FEMALE)
				return BasicRelation.SISTER;
			if(gen == Gender.MALE)
				return BasicRelation.BROTHER;
			break;
		case SPOUSE:
			return BasicRelation.SPOUSE;
		default:
			break;
		}
		return BasicRelation.OTHER;
	}
	
	public boolean doesRelationAlreadyExists(Person newRelation, BasicRelation relationType)
	{
		for(int i = 0; i < relationName.size(); i++)
		{
			if(relationPerson.contains(newRelation))
				if(relationName.get(relationPerson.indexOf(newRelation))==relationType)
					return true;
		}
		return false;
	}
	
	/**
	 * checks if the relationship arraylists are the same size, for integrity 
	 * @return 0  if valid
	 * @return -1 if the relationName arraylist is bigger
	 * @return 1  sif the relationPerson arraylist is bigger
	 */
	public int checkRelationLists()
	{
		if(relationName.size() == relationPerson.size())
			return 0;
		else if(relationName.size()> relationPerson.size())
			return -1;
		else
			return 1;
	}

	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public  ArrayList<Person> getMothers()
	{
		ArrayList<Person> temp = new ArrayList<>();
//		for (Person person : relationPerson) {
//			if(relationName.get(relationPerson.indexOf(person))==BasicRelation.MOTHER)
//				temp.add(person);
//		}
//		
		for(int i = 0; i<relationPerson.size();i++)
		{
			if(relationName.get(i)== BasicRelation.MOTHER)
				temp.add(relationPerson.get(i));
		}
		return temp;
	}
	
	//make this private?? and use it as a wrapper 
	public ArrayList<Person> getFamilyMembers(BasicRelation relation)
	{
		ArrayList<Person> temp = new ArrayList<>();
		for(int i = 0; i<relationPerson.size();i++)
		{
			if(relationName.get(i)== relation)
				temp.add(relationPerson.get(i));
		}
		return temp;
	}

	/**
	 * @return the personId
	 */
	public UUID getPersonId() {
		return personId;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(UUID personId) {
		this.personId = personId;
	}

	/**
	 * @return the alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * @param alive the alive to set
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
