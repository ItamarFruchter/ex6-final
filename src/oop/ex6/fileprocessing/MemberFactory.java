package oop.ex6.fileprocessing;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oop.ex6.error.IllegalCodeException;
import oop.ex6.variables.Member;


public class MemberFactory {

	private static final String EMPTY_STRING = "";
	private static final char SPACE_CHAR = ' ';
	private static final Pattern SPACES = Pattern.compile("\\s+");
	private static final Pattern WORD = Pattern.compile("\\w+");
	private static final Pattern WITH_MODIFIER = Pattern.compile("\\w+\\s+\\w+\\s+\\w+");
	private static final int MEMBER_VALUE = 1;
	private static final int MEMBER_NAME = 0;
	
	
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
	
	public static Member[] createMembers(String line) throws IllegalCodeException{
		String tempString = new String(line);
		clearSpaces(tempString);
		Matcher withModifireMatcher = WITH_MODIFIER.matcher(tempString);
		if (withModifireMatcher.lookingAt()){
			return createMembersWithModifire(tempString);
		}
		else {
			return createMembersWithoutModifire(tempString);
		}
	}	
	
	private static Member[] createMembersWithoutModifire(String tempString) throws IllegalCodeException {
		LinkedList<Member> listOfMembers = new LinkedList<Member>();
		Matcher wordMatcher = WORD.matcher(tempString);
		wordMatcher.find();
		String type = tempString.substring(wordMatcher.start(), wordMatcher.end());
		wordMatcher.replaceFirst(EMPTY_STRING);
		MemberParameters[] members = extractMembers(tempString);
		for (MemberParameters member : members ){
			listOfMembers.add(new Member(member.name, type, member.value));
		}
		return listOfMembers.toArray(new Member[listOfMembers.size()]);
	}

	private static Member[] createMembersWithModifire(String tempString) throws IllegalCodeException {
		LinkedList<Member> listOfMembers = new LinkedList<Member>();
		Matcher wordMatcher = WORD.matcher(tempString);
		wordMatcher.find();
		String modifier = tempString.substring(wordMatcher.start(), wordMatcher.end());
		wordMatcher.replaceFirst(EMPTY_STRING);
		wordMatcher.reset(tempString);
		String type = tempString.substring(wordMatcher.start(), wordMatcher.end());
		wordMatcher.replaceFirst(EMPTY_STRING);
		MemberParameters[] members = extractMembers(tempString);
		for (MemberParameters member : members ){
			listOfMembers.add(new Member(member.name, type, member.value, modifier));
		}
		return listOfMembers.toArray(new Member[listOfMembers.size()]);
	}
	
	private static MemberParameters[] extractMembers(String tempString) {
		LinkedList<MemberParameters> members = new LinkedList<MemberParameters>();
		String[] membersString = tempString.split(",");
		for (int i = 0; i < membersString.length; i++){
			if (i == membersString.length-1){
				membersString[i].replace(';', SPACE_CHAR);
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


	private static void clearSpaces(String stringToWork){
		Matcher spacesMatcher = SPACES.matcher(stringToWork);
		if (spacesMatcher.lookingAt()){
			spacesMatcher.replaceFirst(EMPTY_STRING);
		}
	}
}
