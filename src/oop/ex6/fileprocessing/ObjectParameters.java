package oop.ex6.fileprocessing;

/**
 *this class represents the parameters that are needed when initializing a member
 */
public class ObjectParameters {
	
	private String modifier;
	private String type;
	private String name;
	private String value;
	
	/**
	 * constructs the object parameters
	 * @param modifier - true if it has the 'final' modifier, false otherwise 
	 * @param type - the type of the object
	 * @param name - the name of the object
	 * @param value - the value of the object
	 */
	public ObjectParameters(String modifier, String type, String name, String value){
		this.modifier = modifier;
		this.type = type;
		this.name = name;
		this.value = value;
	}
	
	public String getModifier(){
		return modifier;
	}
	
	public String getType(){
		return type;
	}
	
	public String getName(){
		return name;
	}
	
	public String getValue(){
		return value;
	}
}
