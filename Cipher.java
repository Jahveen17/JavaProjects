
import java.util.Scanner;

/**
 * Cipher.java
 * Encrypts messages based on a generated 26x26 character matrix and an input key,
 * can decrypt the same message.
 * 
 * CS 1131
 * Assignment #2: Cipher
 * 
 * @Author Javen Zamojcin
 */
public class Prog010Cipher {
	
   // INSTANCE VARIABLES
   char [ ] keyList; // Char array which references the key, and then the modifed key.
   char [ ][ ] cipherTable; // Char array which references to the 27x27 matrix holding the ciphertable.

   // METHODS
   
   String encode( String message ) {
	  
 	  message = message.toUpperCase(); 
 	   
      char[] charResult = new char[message.length()];
      char[] newKey = new char[message.length()];
      int colcoord = 0;
      int rowcoord = 0;
      int letterCount = 0;
      
      //Generates new key
      
      if ( keyList.length < message.length()) 
      {
    	  for ( int i = 0; i < message.length(); i++) {
    	    
    		  if ( letterCount == keyList.length )
    		  { 
    			  letterCount = 0;
    		  }
    		  
    		  newKey[i] = keyList[letterCount];
    		  
    		  letterCount++;   		 	  
    	  }
    	  
    	  keyList = newKey;
    	  
      } else 
      	{
    	  	newKey = keyList;
      	}
      
      // Generates matrix coordinates; converts plaintext message to encrypted message
      
      for ( int i = 0; i < message.length(); i++ ) {    
    	  
    	  boolean isSpace = false;

    	  // Linear Search 1: Finding column coordinate of message
    	  
    	  for ( int ls1 = 1; ls1 < cipherTable[0].length; ls1++ ) {
    	  
    		  if ( cipherTable[0][ls1] == message.charAt(i) ) 
    		  {
    			  colcoord = ls1;
    		  } 
    		  
        	  if ( message.charAt(i) == ' ' ) 
    		  {
    			  charResult[i] = 32;
    			  isSpace = true;	 
    		  }	 
    	  }
    	  
    	  //runs only if message(i) is not equal to ' '
	  
    	  if ( !isSpace ) {
        	
        	 // Linear Search 2: Finding row coordinate of key
    		  
    		  for ( int ls2 = 1; ls2 < cipherTable.length; ls2++ ) {
        	  
    			  if ( cipherTable[ls2][0] == newKey[i] ) 
    			  		{
    				  		rowcoord = ls2;
    			  		}
    		  		}
        	  
        	  	charResult[i] = cipherTable[rowcoord][colcoord];
        	  	
    	  		}
    	  }
      
	  String result = new String(charResult);
      return result;
      
      }
      
   String decode( String message ) {
	   
	   message = message.toUpperCase();
	   char[] decode = new char[message.length()];
	   char[] newKey = new char[message.length()];
	   boolean isSpace = false;
	   int rowCoord = 0;
	   int letterCount2 = 0;
	   
	   if ( keyList.length < message.length()) 
	      {
	    	  for ( int i = 0; i < message.length(); i++) {
	    	    
	    		  if ( letterCount2 == keyList.length )
	    		  { 
	    			  letterCount2 = 0;
	    		  }
	    		  
	    		  newKey[i] = keyList[letterCount2];
	    		  
	    		  letterCount2++;   		 	  
	    	  }
	    	  
	    	  keyList = newKey;
	    	  
	      } else 
	      	{
	    	  	newKey = keyList;
	      	}
	   
	   for ( int letterCount = 0; letterCount < keyList.length; letterCount++ ) {
		     
		   if ( message.charAt(letterCount) == 32 ) {
			   
			   decode[letterCount] = 32;
			   isSpace = true;
			   
		   }
		   
		   if ( !isSpace ) {
			   
		   	for ( int i = 1; i < cipherTable.length; i++) {
		   
	   			if ( keyList[letterCount] 
	   					== cipherTable[i][0] )
	   				{
	   					rowCoord = i;
	   				}
		   		}
	   
	   		for ( int i = 1; i < cipherTable[0].length; i++ ) {
		   
	   			if ( cipherTable[rowCoord][i] == message.charAt(letterCount) )
	   				{
	   					decode[letterCount] = 
	   							cipherTable[0][i];
	   				}
	   			}
		   }
		   
		   isSpace = false;
		  
	   	}
	   
	  String result = new String(decode);
      return result;
	  
   }

   // CONSTRUCTORS
   
   public Prog010Cipher( char code, String key ) {
 	   
	   key = key.replaceAll("\\W", "");
	   key = key.toUpperCase();
	   code = Character.toUpperCase(code);
	   
	   char[ ] lkeyList = new char[key.length()];
	   char[ ][ ] lcipherTable = new char[27][27];
	   
	   int count = 65;
	   for ( int i = 1; i < lcipherTable[0].length; i++ ) {
		   
		  lcipherTable[0][i] = (char) count; 
		  count++; 
	   }
	   
	   count = 65;
	   
	   for ( int i = 1; i < lcipherTable.length; i++ ) {
		   
			  lcipherTable[i][0] = (char) count; 
			  count++; 
		   }
	   
	   for ( int row = 1; row < lcipherTable.length; row++ ) {
		   
		   for ( int col = 1; col < lcipherTable[0].length; col++) {
			   
			   lcipherTable[row][col] = code;
			   
			   if ( code < 90 ) 
			   {   
				   code++;
			   }   
			    else 
			   {   
				   code = 65;
			   }  
		   }
		   
		   if ( code < 90 ) 
		   {   
			   code++;
		   }   
		    else 
		   {   
			   code = 65;
		   }		   
	   }
	   
	   cipherTable = lcipherTable;
	   
	   //Storing key
	   for ( int i = 0; i < key.length(); i++ ) {
		   
		   lkeyList[i] = key.charAt(i);
		   
	   }
	   
	   keyList = lkeyList; 
   }
   
   public static void main( String[ ] args ) {
	  
	  Scanner input = new Scanner(System.in);
	  
	  System.out.println("Enter initial table character: ");
	  char code = input.next().charAt(0);
	  
	  System.out.println("Enter key: ");
	  String key = input.next();
	  
      Prog010Cipher self = new Prog010Cipher( code, key );

      //Enter Message Here
      String eMessage = "Test";
      String dMessage = self.encode(eMessage);
      
      System.out.println("Encrypted message: " + self.encode(eMessage));
      System.out.println("Decrypted message: " + self.decode(dMessage));
      
//       assert "PHXXF MQYBPKNJ".equals( self.encode( "Happy Birthday" ) );
//       assert "HAPPY BIRTHDAY".equals( self.decode( "PHXXF MQYBPKNJ" ) );
      
      input.close();
   }
} 
