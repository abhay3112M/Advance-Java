package in.conceptarchitect.animals.mammals;

import in.conceptarchitect.interfaces.Domestic;

public class Camel extends Mammal implements Domestic{
	
	@Override	
	public String eat() {
		// TODO Auto-generated method stub
		return this+" is a grass eater";
	}
	@Override
	public String move() {
		// TODO Auto-generated method stub
		return this+" moves on ground";
	}

	public String ride() {
		return this+" is a great desert ride";
	}

}
