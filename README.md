## Utilized Tools:
Java & SQLite

## Description of Housing Application Project:
Allow housing’s staff to keep track of who’s currently living in a spaces on campus. Currently the application controls 2 residential building on campus which are Ivy (aka: IV) and Aspen (aka: AS).
Both of these building consist of 4 bedrooms apartment which example (5101-A … 5101-D) and 2 bedrooms apartment (5102-A … 5102-B). If the room number starts of as ‘5’, it means it belong to IV’s building and if it’s ‘6’ it belong to AS’s building. The second number in the room number combination is the floor, 1 = 1st floor … 4 = 4th floor. Both building have 4 floors. And the rest indicate which apartment and which bedroom.

# Functions includes: search residents, check-in residents, check-out residents & view building information…
1. Search Residents page allow housing’s staff to search by…
•	Last Name
•	First Name
•	UMass ID
•	Room Number (Example: 5101-B or 5101)

2. Check-in a Resident page allow housing’s staff to…
•	View all room current status “Reserved” 
•	Check-in a resident by entering the full name 
(NOTE* full name format… first name followed by last name, example: Nina Nguyen)
(NOTE* housing’s staff is only allow to check-in a resident if they reserved a room)
(NOTE* when a resident is checked-in, today date is recorded as check-in date)

3. Check-out a Resident page allow housing’s staff to…
•	View all room current status “In Room”
•	Check-in a resident by entering the full name 
(NOTE* full name format… first name followed by last name, example: Nina Nguyen)
(NOTE* housing’s staff is only allow to check-out a resident if they are currently occupied)
(NOTE* when a resident is checked-out, a record is stored with the individual check-in date and today date as check-out date)

4. Building's Information page allow housing’s staff to…
•	View all occupied & reserved rooms
•	View all unoccupied rooms
•	View mailbox information by entering room number (Example: 5101-B or 5101)
•	View Occupancy History

# UML Diagram
![ScreenShot](/documentation/Diagram1.jpeg)

# Database Relational Schema:
people(umassID, fname, lname, gender, umassEmail, dob)<br />
rooms(roomNum, building, type, notes)<br />
mailbox(roomNum, mailboxNum, mailboxCombo)<br />
occupy(umassID, roomNum, status, checkInDate)<br />
occupyHistory(umassID, roomNum, checkInDate, checkOutDate)


# User Manual Guide:
To utilize UMassD Housing Application requires to have Eclipse.
When the Eclipse program is fully loaded and open
1.	Right click on the “Package Explorer” window
2.	Navigate to “Import” and click on it 
3.	A “Import” window will pop up and navigate to “General” -> “Existing Projects into Workspace”, and select the field and click “Next”
4.	A "Import Projects" windows will pop up... and choose “select archive file” in order to browse the zip file that's provided in the submission. (NOTE* Make sure under options "Searh for nested projects" & "Copy projects into workspace" are both checked)
5.	Click "Finish"
6.	Inside the newly created projects, navigate to src -> HousingMain.java
7.	Right click on "HousingMain.java" and choose "Run As" -> "Java Application"

## Who do I talk to?
The application function inside the console shell.<br />
<b>Repo owner or admin: Sing Yang Cheng</b>
