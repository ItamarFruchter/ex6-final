package oop.ex6.variables;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oop.ex6.error.IllegalCodeException;

/**
 * this class creates samples of members from a given line
 */
public class MemberFactory {

	private static final char SPACE = ' ';
	private static final char SEMICOLON = ';';
	private static final Pattern WORD = Pattern.compile("\\w+");
	private static final Pattern WITH_MODIFIER = Pattern.compile("\\w+\\s+\\w+\\s+\\w+");
	private static final int MEMBER_VALUE = 1;
	private static final int MEMBER_NAME = 0;
	
	/*
	 * this class represents an object that holds the member name and value
	 */
	private static class MemberParameters{
		
		private String name;
		private String value;
		
		/**
		 * constructs the member parameters
		 * @param name - the name of the object
		 * @param value - the value of the object
		 */
		private MemberParameters(String name, String value){
			this.name = name;
			this.value = value;
		}
	}
	
	private MemberFactory(){}
	
	/**
	 * @param line - the line we want generate members from
	 * @return an array of the members
	 * @throws IllegalCodeException
	 */
	public static Member[] createMembers(String line) throws IllegalCodeException{
		String tempString = new String(line);
		tempString = tempString.trim();
		Matcher withModifireMatcher = WITH_MODIFIER.matcher(tempString);
		if (withModifireMatcher.lookingAt()){
			return createMembersWithModifire(tempString);
		}
		else {
			return createMembersWithoutModifire(tempString);
		}
	}	
	
	/*
	 * handles the case there is a modifier to the objects
	 */
	private static Member[] createMembersWithoutModifire(String tempString) throws IllegalCodeException {
		LinkedList<Member> listOfMembers = new LinkedList<Member>();
		Matcher wordMatcher = WORD.matcher(tempString);
		wordMatcher.find();
		String type = tempString.substring(wordMatcher.start(), wordMatcher.end());
		tempString = tempString.substring(wordMatcher.end());
		MemberParameters[] members = extractMembers(tempString);
		for (MemberParameters member : members ){
			listOfMembers.add(new Member(member.name, type, member.value));
		}
		return listOfMembers.toArray(new Member[listOfMembers.size()]);
	}

	/*
	 * handles the case there is no modifier
	 */
	private static Member[] createMembersWithModifire(String tempString) throws IllegalCodeException {
		LinkedList<Member> listOfMembers = new LinkedList<Member>();
		Matcher wordMatcher = WORD.matcher(tempString);
		wordMatcher.find();
		String modifier = tempString.substring(wordMatcher.start(), wordMatcher.end());
		tempString = tempString.substring(wordMatcher.end());
		wordMatcher.reset(tempString);
		wordMatcher.find();
		String type = tempString.substring(wordMatcher.start(), wordMatcher.end());
		tempString = tempString.substring(wordMatcher.end());
		MemberParameters[] members = extractMembers(tempString);
		for (MemberParameters member : members ){
			listOfMembers.add(new Member(member.name, type, member.value, modifier));
		}
		return listOfMembers.toArray(new Member[listOfMembers.size()]);
	}
	
	
	/*
	 * generate an array of MemberParameter from the line given
	 */
	private static MemberParameters[] extractMembers(String tempString) {
		LinkedList<MemberParameters> members = new LinkedList<MemberParameters>();
		String[] membersString = tempString.split(",");
		for (int i = 0; i < membersString.length; i++){
			if (i == membersString.length-1){
				// removes the semicolon from the end of the line
				membersString[i] = membersString[i].replace(SEMICOLON, SPACE);
			}
			String[] parameters = membersString[i].split("=");
			if (parameters.length == 2){
				members.add(new MemberParameters(parameters[MEMBER_NAME], parameters[MEMBER_VALUE]));
			}
			else {
				members.add(new MemberParameters(parameters[MEMBER_NAME], null));
			}
		}
		return members.toArray(new MemberParameters[members.size()]);
	}
}
