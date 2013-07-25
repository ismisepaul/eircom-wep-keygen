/*
 * Copyright (C) 2009 Paul McCann
 * Contact ismisepaul@gmail.com
 *    
 * Program to generate an Eircom WEP password from an Eircom SSID
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.beag.eircomwep.calc;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;



public class CalcWEP {

	static int FIRST_EIGHT = 4044;
	static int START_SERIAL = 16777216;

	String ssid;
	StringBuilder strbldr = new StringBuilder();
	
	public CalcWEP(String s) {
		
			ssid = s;
	}
	
	//method to calc the wep key
	//firstly gets the serial number from the SSID (int provided by user)
	public void calc() {
				
		int decSsid = 0;
		int serial;
		int macAddr;
		String serialString = null;
		
		try{
		//convert the octal ssid to a decimal
	    decSsid = Integer.parseInt(ssid,8);
		}
		catch(NumberFormatException numE){
			
		}
		

		
		// get the router MAC address
		// xor with 1st 8 bit of MAC address from Netopia router
		// = 0x000fcc or 4044 in decimal
		macAddr = decSsid ^ FIRST_EIGHT;
						
		// to get the serial number from MAC address
		// Netopia serial starts at 0x01000000 (16777216 in decimal)
		// add this to MAC to get full serial
		serial = macAddr + START_SERIAL;

		// convert serial to String
		serialString = Integer.toString(serial);
		
		numToString(serialString);

	}
	

	// method to convert a number string to e.g 54637 to
	// a word representation of that string e.g. FiveFourSixThreeSeven
	public void numToString(String s) {
		
		//String array to hold serial number that will be split up
		//into single numbers e.g. "3325" will be "3" "3" "2" "5"
		String characters[] = null;
		characters = s.split("");
		
		//String array which will be reference and used to convert the 
		//number version e.g. "3" "3" "2" "5" to it's word representation
		//to "Three" "Three" "Two" "Five"
		String numNames[] = new String[] { "Zero", "One", "Two", "Three",
				"Four", "Five", "Six", "Seven", "Eight", "Nine" };
		
		//StringBuilder will take the broken string e.g. "Three" "Three" "Two" "Five"
		//and it will build it up into one string
		StringBuilder plainText = new StringBuilder();
		
		//for loop to loop through character array
		for (int i = 1; i < characters.length; i++) {
			//parse the character array elements e.g element 2 = "3" to an int
			int toint = Integer.parseInt(characters[i]);
			//reference the array with the ints e.g. int "3" will refernce
			//the third element in the numNames[] array giving the result "Three"
			//All these strings from numNames will be added together using the 
			//StringBuilder plainText
			plainText.append(numNames[toint]);
		}
		
		
		String numString  = plainText.toString();
		appendLyrics(numString);

	}

	//a method to add lyrics to from Jimi Hendrix 'Third Stone From the Sun'
	//to a string
	public void appendLyrics(String s){
		
						
		String[] lyrics = {s+"Although your world wonders me, ", 
				s+"with your superior cackling hen,",
				s+"Your people I do not understand,",
				s+"So to you I shall put an end and",
				s+"You'll never hear surf music aga",
				s+"Strange beautiful grassy green, ",
				s+"With your majestic silver seas, ",
				s+"Your mysterious mountains I wish"};	
		
		sha1Hash(lyrics);
	}
	
	
	public StringBuilder sha1Hash(String[] s){
		
		int line = 0;
		
		System.out.println("");
 
        try {
        	for(int i = 0; i < s.length; i++){
        		line++;
                strbldr.append(StringToSHA1.SHA1(s[i]));
        	}
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        return strbldr;
	}
	
	public String format(){
		
		String hashed;
		hashed = strbldr.toString();
		
		/*
		String key1 = hashed.substring(0, 26);
		String key2 = hashed.substring(26, 52);
		String key3 = hashed.substring(52, 78);
		String key4 = hashed.substring(78, 104);
		*/
		
		return hashed;
	}

}
