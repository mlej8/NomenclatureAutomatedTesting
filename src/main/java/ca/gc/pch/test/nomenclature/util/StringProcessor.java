package ca.gc.pch.test.nomenclature.util;

import java.text.Normalizer;
import java.util.ArrayList;

public class StringProcessor {
	
	public static ArrayList<String> stripAccents(ArrayList<String> listOfCategories){
		/**
		 * Method that strips the accents from a string using a Normalizer 
		 */
		for(int i = 0;i < listOfCategories.size();i++) {
			String unaccent = Normalizer.normalize(listOfCategories.get(i), Normalizer.Form.NFD);
			unaccent = unaccent.replaceAll("[^\\p{ASCII}]", "");
			listOfCategories.set(i, unaccent);
		}
		return listOfCategories;		
	}
	
	public static String capitalizeString(String original) {
		/**
		 * Method that capitalizes the first String
		 */
		if(original == null || original.length() == 0) {
			return original;
		}
		return original.substring(0,1).toUpperCase() + original.substring(1);
	}
}
