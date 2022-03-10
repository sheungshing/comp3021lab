package base;

import java.util.List;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;

public class NoteBook {
	private ArrayList<Folder> folders;

	public NoteBook() {
		this.folders = new ArrayList<Folder>();
	}

	public boolean createTextNote(String folderName, String title, String content) {
		TextNote note = new TextNote(title, content);
		return insertNote(folderName, note);
	}

	public boolean createImageNote(String folderName, String title) {
		ImageNote note = new ImageNote(title);
		return insertNote(folderName, note);
	}

	public ArrayList<Folder> getFolders() {
		return folders;
	}

	public boolean insertNote(String folderName, Note note) {
		// Check if an object Folder with name folderName already exists in the NoteBook
		boolean folderIsExist = false;
		int indexOfFolder = 0;
		for (int i = 0; i < folders.size(); i++) {
			if (folders.get(i).getName().equals(folderName)) {
				folderIsExist = true;
				indexOfFolder = i;
				break;
			}
		}
		if (folderIsExist == false) {
			Folder newFolder = new Folder(folderName);
			// newFolder.addNote(note);
			this.folders.add(newFolder);
			indexOfFolder = folders.size() - 1;
		}

		// Check if among the notes contained in the folder there is one
		// with the same title of the Note in input.
		for (int j = 0; j < folders.get(indexOfFolder).getNotes().size(); j++) {
			if (folders.get(indexOfFolder).getNotes().get(j).equals(note)) {
				System.out.println("Creating note " + note.getTitle() +
						"under folder " + folderName + " failed");
				return false;
			}
		}
		this.folders.get(indexOfFolder).getNotes().add(note);
		return true;

	}

	public void sortFolders() {
		for (Folder f : folders)
			f.sortNotes();
		Collections.sort(this.folders);
	}

	public List<Note> searchNotes(String keywords) {
		List<Note> tempNotes = new ArrayList<Note>();
		for (Folder tempFolder : folders) {

			List<Note> callList = tempFolder.searchNotes(keywords);
			for(Note temp: callList) {
				tempNotes.add(temp);
			}
			
		}
		return tempNotes;

	}

}
