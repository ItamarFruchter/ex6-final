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
	private static class MemberParameters {

		private String name;
		private String value;

		/**
		 * constructs the member parameters
		 * 
		 * @param name
		 *            - the name of the object
		 * @param value
		 *            - the value of the object
		 */
		private MemberParameters(String name, String value) {
			this.name = name;
			this.value = value;
		}
	}

	private MemberFactory() {
	}

	/**
	 * @param line
	 *            the line we want generate members from.
	 * @return an array of the members.
	 * @throws IllegalCodeException
	 */
	public static LinkedList<Member> createMembers(String line,
			LinkedList<Member> outerScopeMembers, LinkedList<Member> localMembers)
			throws IllegalCodeException {
		LinkedList<Member> listOfMembers = new LinkedList<Member>();
		String tempString = new String(line);
		tempString = tempString.trim();
		String modifier = null;
		Matcher withModifireMatcher = WITH_MODIFIER.matcher(tempString);
		Matcher wordMatcher = WORD.matcher(tempString);
		if (withModifireMatcher.lookingAt()) {
			wordMatcher.find();
			modifier = tempString.substring(wordMatcher.start(), wordMatcher.end());
			tempString = tempString.substring(wordMatcher.end());
		}
		wordMatcher.reset(tempString);
		wordMatcher.find();
		String type = tempString.substring(wordMatcher.start(), wordMatcher.end());
		tempString = tempString.substring(wordMatcher.end());
		MemberParameters[] members = extractMembers(tempString);
		for (MemberParameters memberParameters : members) {
			try {
				if (isNewMember(memberParameters.name, localMembers)) {
					listOfMembers.add(new Member(memberParameters.name, type,
							memberParameters.value, modifier));
				} else {
					throw new MemberAlreadyExistsException();
				}
			} catch (NonValidValueException error) {
				boolean fixed = false;
				LinkedList<Member> allRelevantMembers = new LinkedList<Member>();
				if(localMembers != null) {
					allRelevantMembers.addAll(localMembers);
				}
				if(outerScopeMembers != null) {
					allRelevantMembers.addAll(outerScopeMembers);
				}
				for (Member memberToCheck : allRelevantMembers) {
					if (error.getName().equals(memberToCheck.getName())
							&& Type.canBeCasted(Type.findType(type), error.getType())
							&& memberToCheck.isInitiallized()) {
						memberParameters.value = memberToCheck.getType().getDefaultValue();
						listOfMembers.add(new Member(memberParameters.name, type,
								memberParameters.value, modifier));
						fixed = true;
					}
				}
				if (!fixed) {
					throw error;
				}
			}
		}
		return listOfMembers;
	}

	/**
	 * @param argumentsDescription
	 *            array containing all the calls of the function
	 * @param relevantMembers
	 *            variables that can affect the validity of the given call
	 * @return an array containing all the types of the calls, in the right
	 *         order
	 * @throws IllegalCodeException
	 */
	public static Type[] createArgumentsType(String[] argumentsDescription,
			LinkedList<Member> relevantMembers) throws IllegalCodeException {
		Type[] arrayOfTypes = new Type[argumentsDescription.length];
		for (int i = 0; i < arrayOfTypes.length; i++) {
			boolean isName = false;
			String nameOrValueToCheck = argumentsDescription[i].trim();
			for (Member member : relevantMembers) {
				if (member.getName().equals(nameOrValueToCheck)) {
					isName = true;
					arrayOfTypes[i] = member.getType();
					break;
				}
			}
			if (!isName) {
				arrayOfTypes[i] = Type.typeOfValue(nameOrValueToCheck);
			}
		}
		return arrayOfTypes;
	}

	/*
	 * checking if the name already exists in the local scope
	 */
	private static boolean isNewMember(String name, LinkedList<Member> localMembers) {
		boolean isNew = true;
		if (localMembers != null) {
			for (Member member : localMembers) {
				if (member.getName().equals(name)) {
					isNew = false;
				}
			}
		}
		return isNew;
	}

	/*
	 * generate an array of MemberParameter from the line given
	 */
	private static MemberParameters[] extractMembers(String tempString) {
		LinkedList<MemberParameters> members = new LinkedList<MemberParameters>();
		String[] membersString = tempString.split(",");
		for (int i = 0; i < membersString.length; i++) {
			if (i == membersString.length - 1) {
				// removes the semicolon from the end of the line
				membersString[i] = membersString[i].replace(SEMICOLON, SPACE);
			}
			String[] parameters = membersString[i].split("=");
			if (parameters.length == 2) {
				members.add(
						new MemberParameters(parameters[MEMBER_NAME], parameters[MEMBER_VALUE]));
			} else {
				members.add(new MemberParameters(parameters[MEMBER_NAME], null));
			}
		}
		return members.toArray(new MemberParameters[members.size()]);
	}
}
