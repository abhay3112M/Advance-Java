package in.conceptarchitect.animals.mammals;

import in.conceptarchitect.interfaces.Domestic;

public class Dog extends Mammal implements Domestic{
	
	@Override	
	public String eat() {
		// TODO Auto-generated method stub
		return this+" is a flesh eater";
	}
	@Override
	public String move() {
		// TODO Auto-generated method stub
		return this+" moves on ground";
	}

	public String hunt() {
		return this+" hunts";
	}

}
