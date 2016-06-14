package oop.ex6;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.fileprocessing.MemberFactory;
import oop.ex6.variables.Member;

public class regexTests {
	public static void main(String args[]) {
		String line = "    int a = 3, b, c=8;";
		try {
			Member[] members = MemberFactory.createMembers(line);
			for (Member member : members){
				System.out.println(member.name);
				System.out.println(member.hasValue);
				System.out.println(member.modifier);
			}
		} catch (IllegalCodeException e) {
			System.out.println("ERROR");
		}
	}
}