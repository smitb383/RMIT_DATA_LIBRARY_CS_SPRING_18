import java.util.Arrays;

import Exceptions.ReturnException;

public class Game extends Item{
private boolean extended; 
private String [] platforms; 
private String platformsString; 

public  Game(String id, String title, String genre, String description, String [] platforms ) { 
	this.id = "G_" + id; 
	this.title = title; 
	this.genre = genre; 
	this.description = description; 
		
	this.platforms = platforms; 
	this.fee = 20.00; // CHNAGE THIS ?
	this.rentalPeriod = 14; 
	
	this.platformsString = "";
	for (int i = 0; i < platforms.length; i++) {
		platformsString += platforms[i];
		if (i != platforms.length - 1) {
			platformsString += ",";
		}
	}
	
}
public void setExtended (boolean extended) {
	this.extended = extended; 
}
public double returnItem(DateTime returnDate) throws ReturnException {
	
	int daysBorrowed = DateTime.diffDays(returnDate, currentlyBorrowed.getBorrowDate());
		if (currentlyBorrowed != null && daysBorrowed  >= 0) {
			double lateFee = 0; 
			double numberOfLateDays;
			double totalPrice = 0;
			numberOfLateDays = (daysBorrowed- this.rentalPeriod);
			if (numberOfLateDays> 0 && numberOfLateDays < 7) {
				lateFee = numberOfLateDays;
			}
			else if (numberOfLateDays> 0 && numberOfLateDays > 7)	 {
				lateFee = ((numberOfLateDays % 7) * 5.00) +numberOfLateDays; 
			}
				else {
					lateFee = 0;}
				
			if (extended) {
				lateFee /= .5; 
			}

			if (lateFee >= 0 && currentlyBorrowed != null) {
			 totalPrice = currentlyBorrowed.returnItem(returnDate, lateFee);
			
			System.out.println("The total fee payable is $" + totalPrice ); 
			currentlyBorrowed = null;
			} return totalPrice;}
		else {
			throw new ReturnException("not returned");
		}}
 //exception that need	
		
		
public String getDetails() {
	//getting information for one movie 
	String loanStatus = ""; 
	if (currentlyBorrowed != null && extended != false) {
		loanStatus = "YES"; 
	}
	else if (currentlyBorrowed != null && extended != true){
		loanStatus = "EXTENDED"; 
	}
	else {
		loanStatus = "NO"; 
	}
	String OnLoan = String.format("%-25s %s\n", "On Loan:", loanStatus); 
	String  Platforms= String.format("%-25s %s\n", "Platforms:",  platformsString);
	return super.getDetails()  + OnLoan + Platforms;
	}
		
	
public String toString() { //need to finish this 
	String loanStatus = ""; 
	if (currentlyBorrowed != null && extended != false) {
		loanStatus = "Y"; 
		
	}
	else if (currentlyBorrowed != null && extended != true){
		loanStatus = "E"; 
	}
	else {
		loanStatus = "N"; 
	}
	return super.toString() + ":" + platformsString + ":" + loanStatus ; 
	 
}
}


