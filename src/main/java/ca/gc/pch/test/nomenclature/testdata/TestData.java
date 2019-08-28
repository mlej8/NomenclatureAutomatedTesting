package ca.gc.pch.test.nomenclature.testdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestData {
	
	public static HashMap<String,String> hLingVariant;
	public static HashMap<String,String>  hOrder;
	public static Map<String, List<String>> preferences;
	public static List<String> linguisticVariants; 
	
	static {
	/* BrowsePage Test Variables */
	hLingVariant = new HashMap<String, String>();
    hLingVariant.put("enInt", "International");
    hLingVariant.put("frInt", "International");
    hLingVariant.put("enCan", "Canadian");
    hLingVariant.put("frCan", "Canadien");

    hOrder = new HashMap<String, String>();
    hOrder.put("enInv", "Inverted");
    hOrder.put("frInv", "Inversé");
    hOrder.put("enNat", "Natural");
    hOrder.put("frNat", "Naturel");
    
    /* Search Test parameters */
    linguisticVariants = new ArrayList<String>(); // linguistic variants
	linguisticVariants.add("International");
	linguisticVariants.add("Canadian");
	
    preferences = new HashMap<String,List<String>>();
	preferences.put("Natural", linguisticVariants);
	preferences.put("Inverted", linguisticVariants);
	}	
	
}
	
