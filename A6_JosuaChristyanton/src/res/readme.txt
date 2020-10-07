==========================================
WINE COLLECTION APPLICATION DESCRIPTION
==========================================

When application is launched for the first time, it will show the interface. If the wine.dat file is missing or has no records in it, it will show empty text fields and an empty list view. The buttons for update and delete are disabled. They will be enabled if there are data in the table view and a row there is selected.

The first text field is not editable, because it is used to only display the wine id. Wine id is the primary key for the record (wine) and it must be unique. The value for it is generated sequentially starting from 0. The user can enter the other data in the text fields and press the add button to enter a new Wine object and store it in the file as well as load it onto the list view. The user can enter multiple wines and each time they press add, a new record will be added.
The user must enter the correct data types and values. If incorrect data are entered, an error alert is displayed with a message. The data types are int for year (value between 1980 and present year), int for quantity and double for the price. The values for all numeric data types must be positive.

If the user opens the application while data have been recorded before in wine.dat file, the record will be shown in the list view. At this point the user can enter more records or select a record form the list view. When the user selects the list view record, all the data for that record (including wine id) will be shown in the text fields on the left pane of the split pane. After selecting a record in the table view update and delete buttons will be enabled.

The user can choose to edit the data and press either one of the three buttons at this point. If the user pressed add, a new record with a different wine id will be added even in those cases when no changes are made in the record.
If the user presses the update button the changes to that record will be applied on the file as well as on the ObservableList which reflects that changes on the TableView.

If the user presses delete, the last record will be deleted. If a different record from the last is selected, the application temporary may display the selected record as deleted, but the actual deletion on the file will be the last record. (observable list is not synchronized with the actual record). However, if the application is closed and reopened, the last record will be missing, not the record that was selected.

The user can open this readme.txt file from the About menu.

The user can close this application by selecting File > Exit. The user will be prompted for a confirmation before the application is closed.  
