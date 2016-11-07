import java.util.ArrayList;

public class Relationship {

	public enum ExtendedRelation {
		MGRANDFATHER, MGRANDMOTHER, FGRANDFATHER, FGRANDMOTHER, GRANDSON, GRANDDAUGHTER
	};
	
	static boolean isMother(Person me, Person them)
	{
//		if(me.getMom() == them)
//			return true;
//		else return false;
		

		for(int i = 0;i<me.getRelationName().size();i++)
		{
			if(me.getRelationName().get(i).equals(BasicRelation.MOTHER)
					&& me.getRelationPerson().get(i)==them)
				return true;
		}
		return false;
	}
	
	static boolean isFather(Person me, Person them)
	{

		for(int i = 0;i<me.getRelationName().size();i++)
		{
			if(me.getRelationName().get(i).equals(BasicRelation.FATHER)
					&& me.getRelationPerson().get(i)==them)
				return true;
		}
		return false;
	}
	
	static boolean isBrother(Person me, Person them)
	{

		for(int i = 0;i<me.getRelationName().size();i++)
		{
			if(me.getRelationName().get(i).equals(BasicRelation.BROTHER)
					&& me.getRelationPerson().get(i)==them)
				return true;
		}
		return false;
	}
	
	static boolean isSister(Person me, Person them)
	{

		for(int i = 0;i<me.getRelationName().size();i++)
		{
			if(me.getRelationName().get(i).equals(BasicRelation.SISTER)
					&& me.getRelationPerson().get(i)==them)
				return true;
		}
		return false;
	}
	
	static boolean isSpouse(Person me, Person them)
	{

		for(int i = 0;i<me.getRelationName().size();i++)
		{
			if(me.getRelationName().get(i).equals(BasicRelation.SPOUSE)
					&& me.getRelationPerson().get(i)==them)
				return true;
		}
		return false;
	}
	
	static boolean isSon(Person me, Person them)
	{

		for(int i = 0;i<me.getRelationName().size();i++)
		{
			if(me.getRelationName().get(i).equals(BasicRelation.SON)
					&& me.getRelationPerson().get(i)==them)
				return true;
		}
		return false;
	}
	
	static boolean isDaughter(Person me, Person them)
	{

		for(int i = 0;i<me.getRelationName().size();i++)
		{
			if(me.getRelationName().get(i).equals(BasicRelation.DAUGHTER)
					&& me.getRelationPerson().get(i)==them)
				return true;
		}
		return false;
	}
	
	static boolean isBasicRelationOf(Person me, Person them, BasicRelation relation)
	{
		for(int i = 0;i<me.getRelationName().size();i++)
		{
			if(me.getRelationName().get(i).equals(relation)
					&& me.getRelationPerson().get(i)==them)
				return true;
		}
		return false;
	}
	
	static boolean isMomGrandFather(Person me, Person them)
	{
//		ArrayList<Person> possibleRelation = me.getFamilyMembers(BasicRelation.MOTHER);
//		if(them.getGender()==Gender.MALE)
//		{
//			System.out.println("OK");
//			for(Person i : possibleRelation)
//			{
//				if(isBasicRelationOf(i, them, BasicRelation.FATHER))
//					return true;
//			}
//		}
//		return false;
		return isLevelTwoRelation(me, them, BasicRelation.MOTHER, BasicRelation.FATHER);
	}
	
	static boolean isDadGrandFather(Person me, Person them)
	{
		return isLevelTwoRelation(me, them, BasicRelation.FATHER, BasicRelation.FATHER);
	}
	
	static boolean isGrandSon(Person me, Person them)
	{
		if(isLevelTwoRelation(me, them, BasicRelation.SON, BasicRelation.SON))
			return true;
		else
			return isLevelTwoRelation(me, them, BasicRelation.DAUGHTER, BasicRelation.SON);
	}
	
	static boolean isGrandDaughter(Person me, Person them)
	{
		if(isLevelTwoRelation(me, them, BasicRelation.DAUGHTER, BasicRelation.DAUGHTER))
				return true;
		else
			return isLevelTwoRelation(me, them, BasicRelation.SON, BasicRelation.DAUGHTER);
	}
	
	static boolean isGrandChild(Person me, Person them)
	{
		if(!(isGrandSon(me, them)))
			if(!(isGrandDaughter(me, them)))
				return false;
		return true;
	}
	
	protected static boolean isLevelTwoRelation(Person me, Person them, BasicRelation level1, BasicRelation level2)
	{
		ArrayList<Person> possibleRelation = me.getFamilyMembers(level1);
		for(Person i : possibleRelation)
		{
			if(isBasicRelationOf(i, them, level2))
				return true;
		}
		return false;
	}
	
	protected static boolean isLevelThreeRelation(Person me, Person them, BasicRelation level1, BasicRelation level2, BasicRelation level3)
	{
		ArrayList<Person> possibleRelation = me.getFamilyMembers(level1);
		for(Person i: possibleRelation)
		{
			if(isLevelTwoRelation(i, them, level2, level3))
				return true;
		}
		return false;
	}
}
