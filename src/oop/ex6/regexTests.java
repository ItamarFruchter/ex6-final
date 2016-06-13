package oop.ex6;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.variables.Modifier;

public class regexTests {
	public static void main(String args[]) {
		Pattern stringPattern = Pattern.compile("\\s*((-?\\d+(.\\d+)?)||true||false)\\s*");
		Matcher StringMatcher = stringPattern.matcher("   -true   ");
		if(StringMatcher.matches()) {
			System.out.println(StringMatcher.group(0));
		} else {
			System.out.println("NO");
		}
		
		System.out.println(Modifier.modifierFromString("final"));
	}
}