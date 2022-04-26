package base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import base.Folder;
import base.Note;
import base.NoteBook;
import base.TextNote;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * NoteBook GUI with JAVAFX
 *
 * COMP 3021
 *
 *
 * @author valerio
 *
 */
public class NoteBookWindow extends Application {

	/**
	 * TextArea containing the note
	 */
	final TextArea textAreaNote = new TextArea("");
	/**
	 * list view showing the titles of the current folder
	 */
	final ListView<String> titleslistView = new ListView<String>();
	/**
	 *
	 * Combobox for selecting the folder
	 *
	 */
	final ComboBox<String> foldersComboBox = new ComboBox<String>();
	/**
	 * This is our Notebook object
	 */
	NoteBook noteBook = null;
	/**
	 * current folder selected by the user
	 */
	String currentFolder = "";

    /**
     * current note selected by the user
     */

    String currentNote = "";

	/**
	 * current search string
	 */
	String currentSearch = "";

	/**
	 * the stage of this display
	 * */
	Stage stage;

	public static void main(String[] args) {
		launch(NoteBookWindow.class, args);
	}

	@Override
	public void start(Stage stage) {
		//loadNoteBook();
		this.stage = stage;

		// Use a border pane as the root for scene
		BorderPane border = new BorderPane();
		// add top, left and center
		border.setTop(addHBox());
		border.setLeft(addVBox());
		border.setCenter(addGridPane());

		Scene scene = new Scene(border);
		stage.setScene(scene);
		stage.setTitle("NoteBook COMP 3021");
		stage.show();
	}

	/**
	 * This create the top section
	 *
	 * @return
	 */
	private HBox addHBox() { // modified

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10); // Gap between nodes

		Button buttonLoad = new Button("Load from File");
		buttonLoad.setPrefSize(100, 20);
		//buttonLoad.setDisable(true);
		Button buttonSave = new Button("Save to File");
		buttonSave.setPrefSize(100, 20);
		//buttonSave.setDisable(true);

		buttonLoad.setOnAction(new EventHandler <ActionEvent>(){ // load file (refer to slide)
			@Override
			public void handle(ActionEvent event){
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Please Choose An File Which Contains a Notebook Object!");

				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized Object File (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);

				File file = fileChooser.showOpenDialog(null);

				if (file != null){
					//todo (bugged)
					loadNoteBook(file);
					//updateListView(); //just update listview
				}
			}
		});

		buttonSave.setOnAction(new EventHandler <ActionEvent>(){ // save file
			@Override
			public void handle(ActionEvent event){
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Please Choose An File Which Contains a Notebook Object!");

				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized Object File (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);

				File file = fileChooser.showOpenDialog(null);

				if (file != null){
					//todo
					if(noteBook.save(file.toString())){ // seems works
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Successfully saved");
						alert.setContentText("You file has been saved to file " + file.getName());
						alert.showAndWait().ifPresent(rs -> {
						    if (rs == ButtonType.OK) {
						        System.out.println("Pressed OK.");
						    }
						});
					}
				}
			}
		});


		Label label = new Label("Search: "); // search label
		TextField textSearch = new TextField(); // search input field


		Button buttonSearch = new Button("Search"); // search button
		buttonLoad.setPrefSize(100, 20);
		buttonSearch.setOnAction(new EventHandler <ActionEvent>(){ // search listener
			@Override
			public void handle(ActionEvent event){
				currentSearch = textSearch.getText(); // get current search keyword from textfield of search
				//System.out.println(currentSearch);
				textAreaNote.setText(""); // clear textAreaNote area
				//TODO
				updateListView(); //just update listview
			}
		});

		Button buttonClearSearch = new Button("Clear Search"); // clear search button
		buttonSave.setPrefSize(100, 20);
		buttonClearSearch.setOnAction(new EventHandler <ActionEvent>(){ // clear search listener
			@Override
			public void handle(ActionEvent event){
				currentSearch= "";
				textSearch.setText("");
				textAreaNote.setText(""); // set current search keyword, input field and also textareanote to nothing
				//TODO
				updateListView(); //just update listview
			}
		});

		hbox.getChildren().addAll(buttonLoad, buttonSave, label, textSearch, buttonSearch, buttonClearSearch);
		// add all stuff
		return hbox;
	}

	/**
	 * this create the section on the left
	 *
	 * @return
	 */
	private VBox addVBox() {

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10)); // Set all sides to 10
		vbox.setSpacing(8); // Gap between nodes

		// TODO: This line is a fake folder list. We should display the folders in noteBook variable! Replace this with your implementation
		//foldersComboBox.getItems().addAll("FOLDER NAME 1", "FOLDER NAME 2", "FOLDER NAME 3");
		if (noteBook != null){
			for(Folder e: noteBook.getFolders()){ // for all folders in Notebook
				foldersComboBox.getItems().add(e.getName()); // add name to foldersComboBox one by one for which one see first
			}
		}


		foldersComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				currentFolder = t1.toString();
				// this contains the name of the folder selected
				// TODO update listview
				updateListView();

			}

		});

		foldersComboBox.setValue("-----");

		titleslistView.setPrefHeight(100);

		titleslistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if (t1 == null)
					return;
				String title = t1.toString();
				// This is the selected title
				// TODO load the content of the selected note in
				// textAreNote
				String content = ""; // initially null content

				ArrayList<Note> test = new ArrayList<Note>(); // list of notes
				for(Folder e : noteBook.getFolders()){
					if (e.getName().equals(currentFolder)){
						test = e.getNotes(); // set that list of note to current folder's
						break;
					}
				}

				for(Note n : test){
					//System.out.println(n);
					if(n.getTitle().equals(title) && n instanceof TextNote){ // if note in the list is TextNote
						content = ((TextNote)n).content; // downcast note to textnote so to get content
						// assign target content with textnote's which has selected title
					}
				}

				currentNote = title;
				textAreaNote.setText(content); // set textAreaNote's content

			}
		});
		vbox.getChildren().add(new Label("Choose folder: "));
		//vbox.getChildren().add(foldersComboBox);

		HBox hbox2 = new HBox();
		Button buttonAddFolder = new Button("Add a Folder"); // clear search button
		//buttonAddFolder.setPrefSize(100, 20);
		buttonAddFolder.setOnAction(new EventHandler <ActionEvent>(){ // clear search listener
			@Override
			public void handle(ActionEvent event){
			    TextInputDialog dialog = new TextInputDialog("Add a Folder");
			    dialog.setTitle("Input");
			    dialog.setHeaderText("Add a new folder for your notebook:");
			    dialog.setContentText("Please enter the name you want to create:");

			    // Traditional way to get the response value.
			    Optional<String> result = dialog.showAndWait();
			    if (result.isPresent()){
			    	//System.out.println(result.get());
			        if(result.get().isEmpty()){
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						//alert.setContentText("You file has been saved to file " + file.getName());
						alert.setContentText("Please input a valid folder name");
						alert.showAndWait().ifPresent(rs -> {
						    if (rs == ButtonType.OK) {
						        System.out.println("Pressed OK.");
						    }
						});
			        }

			        else{
			        	boolean repeated = false;
			        	for(Folder e: noteBook.getFolders()){
			        		if(e.getName().equals(result.get())){
								Alert alert = new Alert(AlertType.WARNING);
								alert.setTitle("Warning");
								alert.setContentText("You already have a folder named with " + e.getName());
								alert.showAndWait().ifPresent(rs -> {
								    if (rs == ButtonType.OK) {
								        System.out.println("Pressed OK.");
								    }
								});
			        			repeated = true;
			        			break;
			        		}
			        	}
			        	if(!repeated){
			        		//System.out.println(noteBook.getFolders());
			        		noteBook.addFolder(result.get());
			        		//System.out.println(noteBook.getFolders());
			        		foldersComboBox.getItems().add(result.get());
			        	}
			        }

			    }

			}
		});
		//vbox.getChildren().add(buttonAddFolder);

		hbox2.getChildren().addAll(foldersComboBox, buttonAddFolder);
		vbox.getChildren().add(hbox2);

		vbox.getChildren().add(new Label("Choose note title"));
		vbox.getChildren().add(titleslistView);

		Button buttonAddNote = new Button("Add a Note"); // add a note to a folder
		//buttonAddNote.setPrefSize(100, 20);
		buttonAddNote.setOnAction(new EventHandler <ActionEvent>(){ // listener
			@Override
			public void handle(ActionEvent event){
				if(currentFolder.isEmpty()){
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					//alert.setContentText("You file has been saved to file " + file.getName());
					alert.setContentText("Please choose a folder first!");
					alert.showAndWait().ifPresent(rs -> {
					    if (rs == ButtonType.OK) {
					        System.out.println("Pressed OK.");
					    }
					});
				}
				else{
					TextInputDialog dialog = new TextInputDialog("Add a Note");
				    dialog.setTitle("Input");
				    dialog.setHeaderText("Add a new note to current folder:");
				    dialog.setContentText("Please enter the name of your note:");

				    // Traditional way to get the response value.
				    Optional<String> result = dialog.showAndWait();
				    if (result.isPresent()){
				    	//System.out.println(result.get());
				        if(result.get().isEmpty()){
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Warning");
							//alert.setContentText("You file has been saved to file " + file.getName());
							alert.setContentText("Please input a valid note name");
							alert.showAndWait().ifPresent(rs -> {
							    if (rs == ButtonType.OK) {
							        System.out.println("Pressed OK.");
							    }
							});
				        }

				        else{
				        	noteBook.createTextNote(currentFolder, result.get());
				        	updateListView();
				        }
				    }
			   }
			}
		});
		vbox.getChildren().add(buttonAddNote);

		return vbox;
	}

	private void updateListView() {
		ArrayList<String> list = new ArrayList<String>();

		// TODO populate the list object with all the TextNote titles of the
		// currentFolder

		// find current folder
		List<Note> test = new ArrayList<Note>(); // list of notes
		if (noteBook != null){
			for(Folder e : noteBook.getFolders()){
				if (e.getName().equals(currentFolder)){
					if(currentSearch.equals("")){// no search requirement
						test = e.getNotes(); // get entire note list
					}
					else{// has search requirement
						//System.out.println(currentSearch);
						test = e.searchNotes(currentSearch); // get notes match search result
						break;
					}

				}
			}
		}

		//System.out.println(test);

		for(Note n : test){
			if(n instanceof TextNote){ // if note in the list is TextNote
				list.add(n.getTitle()); // add its title to list for combobox
			}
		}

		ObservableList<String> combox2 = FXCollections.observableArrayList(list);
		titleslistView.setItems(combox2);
		textAreaNote.setText("");
	}

	/*
	 * Creates a grid for the center region with four columns and three rows
	 */
	private GridPane addGridPane() {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		textAreaNote.setEditable(true);
		textAreaNote.setMaxSize(450, 400);
		textAreaNote.setWrapText(true);
		textAreaNote.setPrefWidth(450);
		textAreaNote.setPrefHeight(400);

		ImageView saveView = new ImageView(new Image(new File("save.png").toURI().toString()));
		saveView.setFitHeight(18);
		saveView.setFitWidth(18);
		saveView.setPreserveRatio(true);
		//grid.add(saveView, 0, 0);

		Button buttonSaveNote = new Button("Save Note"); // clear search button
		//buttonSave.setPrefSize(100, 20);
		buttonSaveNote.setOnAction(new EventHandler <ActionEvent>(){ // clear search listener
			@Override
			public void handle(ActionEvent event){
				if(currentFolder.isEmpty() || currentNote.isEmpty()){
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					//alert.setContentText("You file has been saved to file " + file.getName());
					alert.setContentText("Please select a folder and a note");
					alert.showAndWait().ifPresent(rs -> {
					    if (rs == ButtonType.OK) {
					        System.out.println("Pressed OK.");
					    }
					});
				}
				else{
					for(Folder e: noteBook.getFolders()){
						if(e.getName().equals(currentFolder)){
							for(Note n: e.getNotes()){
								if(n.getTitle().equals(currentNote) && n instanceof TextNote){
									((TextNote)n).content = textAreaNote.getText();
									break;
								}
							}
							break;
						}
					}
				}
			}
		});

		ImageView deleteView = new ImageView(new Image(new File("delete.png").toURI().toString()));
		deleteView.setFitHeight(18);
		deleteView.setFitWidth(18);
		deleteView.setPreserveRatio(true);

		Button buttonDeleteNote = new Button("Delete Note"); // clear search button
		//buttonSave.setPrefSize(100, 20);
		buttonDeleteNote.setOnAction(new EventHandler <ActionEvent>(){ // clear search listener
			@Override
			public void handle(ActionEvent event){
				if(currentFolder.isEmpty() || currentNote.isEmpty()){
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					//alert.setContentText("You file has been saved to file " + file.getName());
					alert.setContentText("Please select a folder and a note");
					alert.showAndWait().ifPresent(rs -> {
					    if (rs == ButtonType.OK) {
					        System.out.println("Pressed OK.");
					    }
					});
				}
				else{
					for(Folder e: noteBook.getFolders()){
						if(e.getName().equals(currentFolder)){
							if(e.removeNotes(currentNote)){
								updateListView();
								Alert alert = new Alert(AlertType.CONFIRMATION);
								alert.setTitle("Succeed");
								alert.setContentText("Your note has been successfully removed");
								alert.showAndWait().ifPresent(rs -> {
								    if (rs == ButtonType.OK) {
								        System.out.println("Pressed OK.");
								    }
								    else if(rs == ButtonType.CANCEL){
								    	System.out.println("Pressed Cancel.");
								    }
								});
								break;
							}
						}
					}

				}
			}
		});

		
		HBox hbox3 = new HBox();
		hbox3.getChildren().addAll(saveView, buttonSaveNote, deleteView, buttonDeleteNote);
		grid.add(hbox3, 0, 0);
		grid.add(textAreaNote, 0, 1);




		return grid;
	}

	// bugged
	private void loadNoteBook(File file) {
		//System.out.println(file.toString());
		NoteBook nb = new NoteBook(file.getAbsolutePath());


		//System.out.println(nb.getFolders().toString());
		noteBook = nb;
		//System.out.println(nb.getFolders());
		if (noteBook != null){
			for(Folder e: noteBook.getFolders()){ // for all folders in Notebook
				foldersComboBox.getItems().add(e.getName()); // add name to foldersComboBox one by one for which one see first
			}
		}
	}

	private void loadNoteBook() {
		NoteBook nb = new NoteBook();
		nb.createTextNote("COMP3021", "COMP3021 syllabus", "Be able to implement object-oriented concepts in Java.");
		nb.createTextNote("COMP3021", "course information",
				"Introduction to Java Programming. Fundamentals include language syntax, object-oriented programming, inheritance, interface, polymorphism, exception handling, multithreading and lambdas.");
		nb.createTextNote("COMP3021", "Lab requirement",
				"Each lab has 2 credits, 1 for attendence and the other is based the completeness of your lab.");

		nb.createTextNote("Books", "The Throwback Special: A Novel",
				"Here is the absorbing story of twenty-two men who gather every fall to painstakingly reenact what ESPN called “the most shocking play in NFL history” and the Washington Redskins dubbed the “Throwback Special”: the November 1985 play in which the Redskins’ Joe Theismann had his leg horribly broken by Lawrence Taylor of the New York Giants live on Monday Night Football. With wit and great empathy, Chris Bachelder introduces us to Charles, a psychologist whose expertise is in high demand; George, a garrulous public librarian; Fat Michael, envied and despised by the others for being exquisitely fit; Jeff, a recently divorced man who has become a theorist of marriage; and many more. Over the course of a weekend, the men reveal their secret hopes, fears, and passions as they choose roles, spend a long night of the soul preparing for the play, and finally enact their bizarre ritual for what may be the last time. Along the way, mishaps, misunderstandings, and grievances pile up, and the comforting traditions holding the group together threaten to give way. The Throwback Special is a moving and comic tale filled with pitch-perfect observations about manhood, marriage, middle age, and the rituals we all enact as part of being alive.");
		nb.createTextNote("Books", "Another Brooklyn: A Novel",
				"The acclaimed New York Times bestselling and National Book Award–winning author of Brown Girl Dreaming delivers her first adult novel in twenty years. Running into a long-ago friend sets memory from the 1970s in motion for August, transporting her to a time and a place where friendship was everything—until it wasn’t. For August and her girls, sharing confidences as they ambled through neighborhood streets, Brooklyn was a place where they believed that they were beautiful, talented, brilliant—a part of a future that belonged to them. But beneath the hopeful veneer, there was another Brooklyn, a dangerous place where grown men reached for innocent girls in dark hallways, where ghosts haunted the night, where mothers disappeared. A world where madness was just a sunset away and fathers found hope in religion. Like Louise Meriwether’s Daddy Was a Number Runner and Dorothy Allison’s Bastard Out of Carolina, Jacqueline Woodson’s Another Brooklyn heartbreakingly illuminates the formative time when childhood gives way to adulthood—the promise and peril of growing up—and exquisitely renders a powerful, indelible, and fleeting friendship that united four young lives.");

		nb.createTextNote("Holiday", "Vietnam",
				"What I should Bring? When I should go? Ask Romina if she wants to come");
		nb.createTextNote("Holiday", "Los Angeles", "Peter said he wants to go next Agugust");
		nb.createTextNote("Holiday", "Christmas", "Possible destinations : Home, New York or Rome");
		noteBook = nb;

	}

}
