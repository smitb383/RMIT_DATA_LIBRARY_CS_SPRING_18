import Exceptions.ReturnException;

public class Movie extends Item{

private boolean isNewRelease; 

 
 // can i just change instances of record to currently borrowed ?



private double NEW_RELEASE_SURCHARGE = 2.00; // any hard coded value have a constant and call from constant
private double STANDARD_MOVIE_FEE = 3.00; 
public  Movie(String id, String title, String genre, String description, boolean isNewRelease) { //shoudl item have a construtor?
	this.id = "M_" + id; 
	this.title = title; 
	this.genre = genre; 
	this.description = description; 
	this.isNewRelease = isNewRelease;
	if (this.isNewRelease) {
		this.fee = STANDARD_MOVIE_FEE + NEW_RELEASE_SURCHARGE;
		this.rentalPeriod = 2;
	}
	else {
		this.fee = STANDARD_MOVIE_FEE;
		this.rentalPeriod = 7;
	}
	
}
		
	public double returnItem(DateTime returnDate) throws ReturnException {
	
	int daysBorrowed = DateTime.diffDays(returnDate, currentlyBorrowed.getBorrowDate());
	double lateFee = 0;  
	int numberOfLateDays;
	double totalPrice = 0;
	numberOfLateDays = Math.abs((daysBorrowed - this.rentalPeriod));

		if (currentlyBorrowed != null && daysBorrowed  >= 0) {
			if (numberOfLateDays> 0) {
			lateFee = (numberOfLateDays) * (this.fee * .5);}
			else {
				lateFee = 0;}
			if (lateFee >= 0 && currentlyBorrowed != null) {
			 totalPrice = currentlyBorrowed.returnItem(returnDate, lateFee);
			
			System.out.println("The total fee payable is $" + totalPrice ); 
			currentlyBorrowed = null;
			
		} }
		else {
			throw new ReturnException("not returned");
		}
		return totalPrice;
		}
 //exception that needs to be handled 
	
	public String getDetails() {
		String loanStatus = ""; 
		String movieType = ""; 
		String rentalPeriod = this.rentalPeriod + "days"; 
		if (currentlyBorrowed != null) {
			loanStatus = "YES"; 
		}
		else {
			loanStatus = "NO"; 
		}
		
		if (isNewRelease) {
			movieType = "New Release"; 	
		}
		else {
			movieType = "Weekly";
		}
		
		String OnLoan = String.format("%-25s %s\n","On Loan:", loanStatus); 
		String MovieType = String.format("%-25s %s\n", "Movie Type:", movieType); 
		
		return  super.getDetails() + OnLoan  + MovieType; 	
		}
	
	public String toString() { 
		String loanStatus; 
		String movieType; 
		if (currentlyBorrowed != null) {
			loanStatus = "Y"; 
		}
		else {
			loanStatus = "N"; 
		}
		if (isNewRelease) {
			movieType = "NR"; 	
		}
		else {
			movieType = "WK";
		}
		
		return super.toString() + ":" + movieType + ":" + loanStatus; 
	}
	
}
	


