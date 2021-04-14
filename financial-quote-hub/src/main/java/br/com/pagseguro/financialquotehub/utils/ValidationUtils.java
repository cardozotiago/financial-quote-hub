/**
 * 
 */
package br.com.pagseguro.financialquotehub.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tiago
 * @date 2021-04-12
 */
public class ValidationUtils {

	public static void main(String[] args) {
		String st0 = "VALE3";
		String st1 = "VALE ON NM";
		String st2 = "2837320141";
		String st3 = "0.&13656";

		if (isNotContainsSpecialCharacter(st0)) {
			System.out.println(st0 + "nao tem especial");
		} else {
			System.out.println(st0 + "CONTEM especial");
		}

		if (isNotContainsSpecialCharacter(st1)) {
			System.out.println(st1 + "nao tem especial");
		} else {
			System.out.println(st1 + "CONTEM especial");
		}

		if (isOnlyNumber(st2)) {
			System.out.println(st2 + "so tem numero");
		} else {
			System.out.println(st2 + "NAO tem SOMENTE NUMEROS");
		}

		if (isNumeric(st3)) {
			System.out.println(st3 + "É numérico");
			System.out.println(new BigDecimal(st3) + " É numérico");
		} else {
			System.out.println(st3 + "não é numérico");
		}

	}

	/**
	 * Return true if data parameter contains only numbers
	 * 
	 * @param data
	 * @return
	 */
	public static Boolean isOnlyNumber(String data) {
		String regex = "[0-9]+";

		// Compile the ReGex
		Pattern p = Pattern.compile(regex);

		if (data == null) {
			return false;
		}

		Matcher m = p.matcher(data);
		return m.matches();
	}

	/**
	 * Return true if data parameter is not contains special character
	 * 
	 * @param data
	 * @return
	 */
	public static Boolean isNotContainsSpecialCharacter(String data) {
		Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher match = pattern.matcher(data);
		if (data == null) {
			return true;
		} else {
			return !match.find();
		}
	}

	/**
	 * Return true if data parameter is not numeric
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isNumeric(String data) {
		if (data == null) {
			return false;
		}
		try {
			Long.parseLong(data);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
