package roulette.bets;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import roulette.Bet;
import util.ConsoleReader;

public class BetFactory {
	private ResourceBundle betResource;
	private ResourceBundle betDescriptorResource;
	private ResourceBundle betNumberResource;
	private final String BET_PROPERTIES = "properties.bet";
	private final String BET_DESCRIPTOR = "properties.betDescriptor";
	private final String BET_NUMBER = "properties.betNumber";

	private String[] betList = {"OddEven", "RedBlack", "ThreeConsecutive"};
	public BetFactory(){
		betResource = ResourceBundle.getBundle(BET_PROPERTIES);
		betDescriptorResource = ResourceBundle.getBundle(BET_DESCRIPTOR);
		betNumberResource = ResourceBundle.getBundle(BET_NUMBER);

	}
	
	
	public Bet getBet(String s) throws ClassNotFoundException{
		String className = betResource.getString(s);
		Bet myBet = null;
		Constructor<?> betConstructor = null;

	
			Class<?> cls = Class.forName(className);
			try {
				betConstructor = cls.getDeclaredConstructor();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				myBet = (Bet)betConstructor.newInstance(betDescriptorResource.getString(s), betNumberResource.getString(s));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return myBet;
	}
	
	public Bet promptAndGetBet() throws ClassNotFoundException{
		System.out.println("You can make one of the following types of bets:");
		
		Enumeration<String> betKeys = betNumberResource.getKeys();
		List<String> betKeyList = new ArrayList<String>();
        while(betKeys.hasMoreElements()){
        	betKeyList.add(betKeys.nextElement());
        }
        int betIndex = 0;
        for (String key: betKeyList) {
        	String betDescription = betDescriptorResource.getString(key);
        	System.out.println(String.format("%d) %s", (betIndex + 1), betDescription));
        	betIndex++;
        }
        
        int response = ConsoleReader.promptRange("Please make a choice", 1, betKeyList.size());
        return getBet(betKeyList.get(response - 1));
	}
}
