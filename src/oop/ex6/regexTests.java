package oop.ex6;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.fileprocessing.MemberFactory;
import oop.ex6.variables.Member;

public class regexTests {
	public static void main(String args[]) {
		String line = "boolean a, b ,c , d = true, e, f = 5;";
		try {
			Member[] members = MemberFactory.createMembers(line);
			for (Member member : members){
				System.out.println(member.name);
				System.out.println(member.hasValue);
			}
		} catch (IllegalCodeException e) {
			System.out.println("ERROR");
		}
		
		String Memb = "\\s*\\w+\\s*";
		String Var = "\\s*((-?\\d+(.\\d+)?)|true|false)\\s*";
		Pattern p = Pattern.compile("("+Memb+"|"+Var+")(((\\|\\|)|(\\&\\&))("+Memb+"|"+Var+"))*");
		Matcher m = p.matcher(" a || && 0.2");
		System.out.println(m.matches());
	}
}