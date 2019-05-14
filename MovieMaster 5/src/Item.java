import Exceptions.BorrowingException;
import Exceptions.ReturnException;

public  abstract class Item {
	protected String id; 
	protected String title; 
	protected String description; 
	protected String genre; 
	protected double fee; 
	protected HiringRecord currentlyBorrowed; 
	protected  HiringRecord[] hireHistory = new HiringRecord[10];
	protected int rentalPeriod; 
	
	
	public  String getItemId() {
		return this.id;
	}
	public  boolean getRentalStatus() {
		//if the item has a currently borrowed record it has been borrowed 
			if (currentlyBorrowed != null) {
				return true;
			}
			else {
				return false; 
			}
		
	}
	
	int index = 0; // do i need a diff index in the classes for movies and games?
		public void addHiringRecord(HiringRecord record) {
			
			if (hireHistory != null) {
			index %= hireHistory.length;
			hireHistory[index] = record;
			index++;
			
		}}

	public  double borrow(String memberId, int advanceBorrow) throws BorrowingException {
		 if (currentlyBorrowed != null) {
			throw new BorrowingException("item is unavailable and is already checked out by another customer");
		
		} 
		 //ask if this is allowed 
		else	 { //should this be in movie master part 
			DateTime borrowDate = new DateTime();
		
			 if (advanceBorrow >0) {
				DateTime now = new DateTime(); 
				 borrowDate = new DateTime(now, advanceBorrow);
			}
	//String substring(int beginIndex, int endIndex)
			 DateTime dueDate;
						
			dueDate = new DateTime(borrowDate, rentalPeriod);
				
			HiringRecord record = new HiringRecord(this.id, memberId, borrowDate, this.fee);
			currentlyBorrowed = record;


			this.addHiringRecord(record);
			System.out.println("The Item " + this.title + " costs $" + this.fee + " and is due on: "
					+ dueDate.getFormattedDate());
			return this.fee;
		} 
		}
		
			
	
	public  abstract double returnItem(DateTime returnDate) throws ReturnException;
		
	public  String getDetails() {
		String Id = String.format("%-25s %s\n", "Id:", this.id);
		String Title = String.format("%-25s %s\n", "Title:", this.title);
		String Genre = String.format("%-25s %s\n", "Genre:", this.genre);
		String Description = String.format("%-25s %s\n", "Description:", this.description);
		String StandardFee = String.format("%-25s %s\n", "Standard Fee:", "$" +this.fee);
		String rentalPeriod = String.format("%-25s %s\n", "Rental Period:", this.rentalPeriod + " days");
		return Id + Title + Genre + Description + StandardFee + rentalPeriod; 
	}
	public  String toString() {
		return  this.id + ":" + this.title + ":"  + this.genre + ":" + this.description + ":" + this.fee  ; 
	}
	public String getItemHistoryDetails() {
		//beginning to build string format of a movies hiring record 
		int temp = index-1; //wont work if nothing is stored in hire history 
		String hireRecord = ""; 
		String line = String.format( "%-25s %s\n", "", "----------------------------------");
		String hireRecordList = "\n"+ String.format( "%25s%-20s %s\n","","BORROWING RECORD", "") + line; 
		String MovieDetails = ""; 
		
		for ( int i = 0; i < hireHistory.length; i++) { 

	if (temp >=0 && hireHistory[temp] != null) {
			hireRecord = hireHistory[temp].getDetails(); 
		hireRecordList +=  String.format("%s\n",hireRecord); 
			temp--; 
			if (temp == -1) { 
				temp += hireHistory.length;
			}MovieDetails +=  hireRecordList + line;
		}
		}return MovieDetails;
		}
	public String itemDetailsToString() {
		int temp = index - 1;
		String hireRecord = "";
		String hireRecordList = "";
		for (int i = 0; i < hireHistory.length; i++) {
			if (temp >= 0 && hireHistory[temp] != null) {
				hireRecord = hireHistory[temp].toString();}
			else {
				hireRecord = "none"; } 
				hireRecordList +=  hireRecord + "\n";
				temp--;
				if (temp == -1) {
					temp += hireHistory.length;
				}
				
			}
		
		return hireRecordList;
	}
}

	



