import Exceptions.ReturnException;

public class HiringRecord {

	private String id;
	private double rentalFee;
	private double lateFee;
	private DateTime borrowDate;
	private DateTime returnDate;
	private String memberId; // ?
	private double totalReturnFee; // ?

	public HiringRecord(String id, String memberId, DateTime borrowDate, double rentalFee) {
		this.id = id + "_" + memberId + "_" + borrowDate.getEightDigitDate();
		this.memberId = memberId;
		this.borrowDate = borrowDate;
		this.rentalFee = rentalFee;
	}

	public String getBorrowingDetails() { // check if I need this
		return this.id;
	}

	public DateTime getBorrowDate() {
		return this.borrowDate;
	}

	public double returnItem(DateTime returnDate, double lateFee) throws ReturnException { 

		this.returnDate = returnDate; 
		this.lateFee = lateFee; 
	if (lateFee >= 0 && borrowDate != null) {
			totalReturnFee = lateFee + rentalFee;	
		} else {
			throw new ReturnException("not returned"); //exception that needs to be handled 
		}
		return totalReturnFee;
			}
	

	public String getDetails() {
		String HireId = String.format("%25s%-20s %s\n","", "Hire Id:", this.id);
		String BorrowDate = String.format("%25s%-20s %s\n","", "Borrow Date:", borrowDate.getFormattedDate());

		if (returnDate != null) {
			String ReturnDate = String.format("%25s%-20s %s\n", "","Return Date:", returnDate.getEightDigitDate());
			String Fee = String.format("%25s%-20s %s\n", "","Fee:", rentalFee);
			String LateFee = String.format("%25s%-20s %s\n","", "Late Fee:", lateFee);
			String TotalFee = String.format("%25s%-20s %s\n", "", "Total Fee:", totalReturnFee);
			return HireId + BorrowDate + ReturnDate + Fee + LateFee + TotalFee;
		} 
		else {
		return HireId + BorrowDate;
		}

	}

	public String toString() { // only load items first //after loop over the array and break up the id //associate the id with the movie and store the hiring record 
		if (returnDate != null) {
			return this.id + ":" + borrowDate + ":" + returnDate + ":" + this.rentalFee + ":"+ lateFee;
			
		} else {
			return this.id + ":" + borrowDate + ":" + this.rentalFee + ":none:none";			
		}
	}
}
