import java.util.Scanner;

import Exceptions.BorrowingException;
import Exceptions.IDException;
import Exceptions.ReturnException;
import Exceptions.SeedException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
public class MovieMaster {
	Scanner scanner = new Scanner(System.in);
	private boolean inConversation = true;
	private boolean currentMenuSelection = true;
	PrintWriter itemDataFile;
	PrintWriter itemDataFileBCK;
	Scanner loadData = null; 
	Scanner loadBackup = null; 
	// array storing all rental items created by the employees
	int length = 0; // will be initalising variable for array once it is variable length 
	private Item[] rentalItems = new Item[100]; 
	private int nextIndex = 0; //index counter for next space to store a rental item 
 
	public void loadData() {
	//	if ( itemDataFile != null && itemDataFileBCK != null) {
		
		// f = file("data");
		// if (!f.exists()) {
		//      f = file("data_backup")
		//      if (!f.exists()) {
		//           return
		try {
			loadData = new Scanner(new File("itemData.txt"));
		} 
		catch (FileNotFoundException e1) {
			System.out.println("file not found"); 
			//e1.printStackTrace();
				try {
					loadData = new Scanner(new File("itemData_backup.txt"));
					System.out.print("data was loaded from a backup file.\n");
				} catch (FileNotFoundException e) {
					System.out.println("backup file not found"); 
					System.out.print("no Item data was loaded\n");
					return;
				}				
		}

			while (loadData.hasNextLine()) {
				
				String item = loadData.nextLine();
				if (item != "none") {
					String[] itemInfo = item.split(":");
					

					if ((itemInfo[0].substring(0, 1)).equalsIgnoreCase("M")) {
					
						boolean release = true;
						if (itemInfo[4].equalsIgnoreCase("3.0")) {
							release = false;
						}

						Movie movie = new Movie(itemInfo[0], itemInfo[1], itemInfo[2], itemInfo[3], release);
						
						rentalItems[nextIndex] = movie;
						nextIndex++;
						readHiringRecord(movie); 

					} else if (itemInfo[0].substring(0, 1).equalsIgnoreCase("G")) {
						
						String platforms = itemInfo[5];
						String[] platforms4 = platforms.split(",");
						
						
						Game game = new Game(itemInfo[0], itemInfo[1], itemInfo[2], itemInfo[3], platforms4);
						rentalItems[nextIndex] = game;
						nextIndex++;
						readHiringRecord(game); 
						

					}
			}}
		 } 
	
	//create method to add hiring record to game or movie 
	public void readHiringRecord (Item x) { 
		for (int i = 0; i < 10; i++) {
			String hiringRecord = loadData.nextLine();
			if (hiringRecord.equals("none")) {
				continue;

			} else {
				String[] splitHiringRecord = hiringRecord.split(":");
				String[] hrIDcomp = splitHiringRecord[1].split("-");
				DateTime borrow = new DateTime(Integer.parseInt(hrIDcomp[2]),
						Integer.parseInt((hrIDcomp[1])), Integer.parseInt((hrIDcomp[0])));
				double rentalFee = 3.0;
				if (splitHiringRecord[4].equalsIgnoreCase("3.0")) {
					rentalFee = 5.0;
				}
				HiringRecord record = new HiringRecord(hrIDcomp[0], hrIDcomp[1], borrow, rentalFee);
				x.addHiringRecord(record);
			}
			
	}
	}
	public void run() throws IDException {

		String MovieMaster = "*** Movie Master System Menu ***\n";
		String A = String.format("%-20s %s\n", "Add Item:", "A");
		String B = String.format("%-20s %s\n", "Borrow Item:", "B");
		String C = String.format("%-20s %s\n", "Return Item:", "C");
		String D = String.format("%-20s %s\n", "Display Details", "D");
		String E = String.format("%-20s %s\n", "Seed Data", "E");
		String X = String.format("%-20s %s\n", "Exit Program", "X");
		String Select = "Enter Selection:";
		String Menu = MovieMaster + A + B + C + D + E + X + Select;

		
//main loop that will keeps displaying the menu after error or after one selection from menu is completed 
		loadData();
		while (inConversation) { //condition for staying in main conversation 
			
			
			System.out.print(Menu); //print the menu 
			String response = scanner.nextLine().toUpperCase();//user input for menu option 		
			switch (response) {
			case "\n":
				currentMenuSelection = false;
			case "A": //if the response is A 
				currentMenuSelection = true; //make loop for staying in the menu option a loop 
				this.addItem(); //run add movie method 
				break;
			case "B":
				this.borrowItem();
				break; 
			case "C":
				this.returnItem();
				break; 
			case "D":
				this.displayDetails();
				break; 
			case "E":
				this.seedData();
				break; 
			case "X": // if user enters X 
				if (itemDataFileBCK == null && itemDataFile == null) {
					try {
						itemDataFile = new PrintWriter (new FileOutputStream("itemData.txt"));
						itemDataFileBCK = new PrintWriter (new FileOutputStream("itemData_backup.txt"));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				writeDetailsToFile(itemDataFile);
				writeDetailsToFile(itemDataFileBCK);
				itemDataFile.close(); 
				itemDataFileBCK.close();
				inConversation = false;// exit the main menu loop 
				break; 
			}
		}
	}
	public void writeDetailsToFile(PrintWriter x) {
		//print hiring record on each line 
		//if instnac eof hisring record write null 
		//tehn split string 
		//if positon of split is null then 
		for (int i = 0; i < rentalItems.length; i++) {
			if (rentalItems[i] != null && x != null) {
			x.write(rentalItems[i].toString());
			x.write("\n");
			x.write(rentalItems[i].itemDetailsToString());
			
			
			}
		}
	}
	public void wrtieHistorytoFIle (PrintWriter x) {
		for (int i = 0; i < rentalItems.length; i++) {
			if (rentalItems[i] != null && x != null) {
				x.write(rentalItems[i].itemDetailsToString());
				
			}
		}
	}
	public void  setItemsArrayLength() { // will be menu to set array length 
		rentalItems = new Item[length++];	
	}
	// method for adding a movie when person calls A to main menu options
	public void addItem() throws IDException {
		while (currentMenuSelection) {// asking user to enter id for movie
			System.out.print("Enter item id: ");
			// getting string value for the id entered by user
			String idInput = scanner.nextLine().toUpperCase(); 
			
			try {
				if (idInput.length() != 3) {
					throw new IDException("Item id must be 3 letters long");
					}	
				for (int i = 0; i < rentalItems.length; i++) {
					// search for an id of a rental item that would match the input of the user
					if (rentalItems[i] != null && rentalItems[i].getItemId().equals("M_" + idInput) ) {
						// if found then error message item already exists
						throw new IDException("Error - Id for M_" + idInput + " already exists in the system!");
						}
				}
				
			String id = idInput;

			System.out.print("Enter title:");
			String title = scanner.nextLine();

			System.out.print("Enter genre:");
			String genre = scanner.nextLine();

			System.out.print("Enter description:");
			String description = scanner.nextLine();

			System.out.print("Movie or Game (M/G)?");
			String itemType = scanner.nextLine(); 
			if (itemType.equalsIgnoreCase("M")) {
			
			System.out.print("Enter new release (Y/N):");
			String release = scanner.nextLine().toUpperCase();

			boolean isNewRelease = true; 
			int isNewReleaseErrorMsg = 0;
			
			//look how I would do for here 
			while (isNewReleaseErrorMsg < 2 && !release.equalsIgnoreCase("Y") && !release.equalsIgnoreCase("N")) {
				System.out.print(release + isNewRelease);
				System.out.println("Error: You must enter 'Y' or 'N'");
				release = scanner.next();
				isNewReleaseErrorMsg++;
			}
			if (isNewReleaseErrorMsg == 2) {
				System.out.print("Exiting to Main Menu");
				currentMenuSelection = false;
			}
			else { 
				if (release.equalsIgnoreCase("Y")) {
				isNewRelease = true;
			} else if (release.equalsIgnoreCase("N")) {
				isNewRelease = false;
			} 
			
			// creating new movie object with constructor that is information gathered by
			// employee
			Movie movie = new Movie(id, title, genre, description, isNewRelease);
			// adding the new movie object to the next available position in rentalItems
			//this.setItemsArrayLength(); 
			rentalItems[nextIndex] = movie;
			nextIndex++;
			System.out.println("New movie added successfully for movie entitled: " + title);
			}
			}
		
			else if (itemType.equalsIgnoreCase("G")) {
				System.out.println("Enter game platforms:"); 
				String gamePlatforms = scanner.nextLine().toUpperCase();
				String[] platforms = gamePlatforms.split(",");
				Game game = new Game(id, title,genre, description,  platforms );
				rentalItems[nextIndex] = game; 
				nextIndex++;
				
				System.out.println("New game added successfully for the game entitled: " + title);
				currentMenuSelection = false;
			}
			currentMenuSelection = false;
		
			}
		catch (IDException x )	{
			String exception = x.getMessage();
			System.out.println(exception);
			currentMenuSelection = false;
		}
				}
					}	
	
	public void borrowItem() { //method to borrow a movie 
		System.out.print("Please enter the id of the item you wish to borrow: "); //asking user for movie id 
		String search = scanner.nextLine().toUpperCase();
		String itemType = search.substring(0,1);
		System.out.print(itemType);
		//search = "M_" + search; 
		//is it assumed that the user can just enter if g or m ?
		int i = 0; 
		try {
		for ( i = 0; i < rentalItems.length; i++) {

			if (rentalItems[i] != null && rentalItems[i].getItemId().equals(search) &&  rentalItems[i].getRentalStatus() == false ) { 
																										
				System.out.print("Enter member id: ");
				String memberId = scanner.nextLine().toUpperCase();
				System.out.print("Advance borrow days: ");
				int advanceBorrow = scanner.nextInt();
		if (itemType.equals("M")) {
					rentalItems[i].borrow(memberId, advanceBorrow); 
					}	
		else if (itemType.equals("G")) {
			boolean extended = true; 
			System.out.print("Extended Hire (Y/N): ");
			String extendedHire = scanner.nextLine().toUpperCase();
			if (extendedHire.equals("Y")) {
				extended = true; 
			}
			else if (extendedHire.equals("N")) {
				extended = false; 
			}
			((Game) rentalItems[i]).setExtended(extended);
			//do i need exception and error message here 
			rentalItems[i].borrow(memberId, advanceBorrow); 
		}
			}
			 else if (rentalItems[i] != null && rentalItems[i].getItemId().equals(search) && rentalItems[i].getRentalStatus() == true) {
				 throw new BorrowingException("The Item with id " + rentalItems[i].getItemId() + " is currently on loan");
			} else {
				throw new BorrowingException("The item with id number: " + search + ", not found"); 
			}
	
			i = 	rentalItems.length +1;
			}}
		catch (BorrowingException x )	{
			String exception = x.getMessage();
			System.out.println(exception);
			i = 	rentalItems.length +1; //check if this works when exceptions caught in other classes 
			}
	}

	public void returnItem() {
		System.out.print("Please enter the id of the item you wish to return: ");
		String search = scanner.nextLine().toUpperCase();
		//search = "M_" + search; 
		int i = 0; 
			try {
				for ( i = 0; i < rentalItems.length; i++) {
			if (rentalItems[i] != null && rentalItems[i].getItemId().equalsIgnoreCase(search) && rentalItems[i].getRentalStatus() == true) {
				System.out.print("Enter number of days on loan: ");
				int daysBorrowed = scanner.nextInt();

				rentalItems[i].returnItem(new DateTime(daysBorrowed));
				i = 	rentalItems.length +1;		
			} else if (rentalItems[i] != null && rentalItems[i].getItemId().equals(search) && rentalItems[i].getRentalStatus() == false) {
				throw new ReturnException("The Item with id " + rentalItems[i].getItemId() + " is NOT currently on loan");
				
			} else {
				throw new ReturnException("The item with id number: " + search + ", not found");			
			}
		}
		}
		catch (ReturnException x) {
			String exception = x.getMessage();
			System.out.println(exception);
			i = 	rentalItems.length +1;
			}
		}
	

	public void displayDetails() {
		
		for (int i = 0; i < rentalItems.length; i++) {
			if (rentalItems[i] != null) {
			System.out.println(rentalItems[i].getDetails());
			System.out.println(rentalItems[i].getItemHistoryDetails());
			System.out.println("----------------------------------");
			
			}
			
		}
		
		
	}

	public void addMovie(String id, String title, String genre, String description, boolean isNewRelease) {
		Movie movie = new Movie(id, title, genre, description, isNewRelease);
		rentalItems[nextIndex] = movie; 
		nextIndex++;
		
	}
public void addGame(String id, String title, String genre, String description, String [] platforms ) {
	
	Game game = new Game(id, title, genre, description,platforms);
	rentalItems[nextIndex] = game;
	nextIndex++;
}
	public void borrowItem(Item item, String memberId, int advanceBorrow )  {
		try {
			item.borrow(memberId, advanceBorrow);
		} catch (BorrowingException e) {
			String exception = e.getMessage();
			System.out.println(exception);
		} 
							
	}
	public void returnItem(Item item, int daysBorrowed) {
		try {
			item.returnItem(new DateTime(daysBorrowed));
		} catch (ReturnException e) {
			
				String exception = e.getMessage();
				System.out.println(exception);
			}
		}
	
	public void seedData() {
		int i= 0;
		for (i= 0; i < rentalItems.length; i++) {
		if (rentalItems[i] != null) {
			System.out.println("Error - there are already items in the system");
			i = rentalItems.length +1;
		} 
		else {
		addMovie("MFC", "Fight Club", "drama","An insomniac office worker, looking ... ",false);
		
		addMovie("SPV", "Scott Pilgrim vs. the World", "action","A young man (Michael Cera) must fight ...", false);
		borrowItem(rentalItems[1], "SEED", 0);
		
		addMovie("MFH", "Frances Ha", "drama","Directed by Noah Baumbach, Frances Ha ...",false);
		borrowItem(rentalItems[2], "SEED", 0 );
		returnItem(rentalItems[2], 5); 
		
		addMovie("MAM", "Amy", "documentary","Amy presents the story of British singer ...",false);
		borrowItem(rentalItems[3], "SEED", 0 );
		returnItem(rentalItems[3], 10); 
		
		addMovie("MJU", "Juno", "comedy","When precocious teen Juno MacGuff becomes pregnant,..", false);
		borrowItem(rentalItems[4], "SEED", 0 );
		returnItem(rentalItems[4], 10); 
		borrowItem(rentalItems[4], "SEED2", 0 );
		
		addMovie("MFP", "The Florida Project", "drama","A single mother (Bria Vinaite) lives ...",	true);
		
		addMovie("CMC", "Call me By Your Name", "drama",	"In 1983 Italy, a 17-year-old boy (Timothee Chalamet) ...", true);
		borrowItem(rentalItems[6], "SEED", 0 );
		
		addMovie("AQP", "A Quiet Place", "horror","A family is forced to live in silence ...", true);
		borrowItem(rentalItems[7], "SEED", 0 );
		returnItem(rentalItems[7], 1); 
	
		addMovie("MBP", "Black Panther", "action", "T'Challa, the King of Wakanda, rises to the throne ...", true);
		borrowItem(rentalItems[8], "SEED", 0 );
		returnItem(rentalItems[8], 3); 
		
		addMovie("AAW", "Avengers Infinity War", "action","The Avengers and their allies must be willing ...", true); 
		borrowItem(rentalItems[9], "SEED", 0 );
		returnItem(rentalItems[9], 3); 
		borrowItem(rentalItems[9], "SEED2", 0 );
		
		addGame("GTA", "Grand Theft Auto V", "action-adventure", 
				" Set within the fictional state of San Andreas,...",  new String[]{"XBOX 360","PS4", "PS3" }); // rental item 10 
		addGame("COD", "Call of Duty", "First-person shooter", 
				" Call of Duty is a first-person shooter video game... ",  new String[]{"XBOX 360","PS4", "PS3", "XBOX ONE" }); 
		borrowItem(rentalItems[11], "SEED", 0);
		addGame("BF4", "Battlefield 4", "first-person shooter", 
				" developed by video game developer EA DICE,...",  new String[]{"XBOX 360","PS4", "PS3", "PC" });
		borrowItem(rentalItems[12], "SEED", 0);
		returnItem(rentalItems[12], 19); 
		addGame("AC4", "Assassin's Creed IV Black Flag", "action-adventure", 
				"historical time frame precedes that of Assassin's Creed III,...",  new String[]{"Wii","XBOX 360","PS4", "PS3", "PC" });
		borrowItem(rentalItems[13], "SEED", 0);
		returnItem(rentalItems[13], 32);
		
		i = rentalItems.length +1;}}
		}
		
		}
	

