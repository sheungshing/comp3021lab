package base;

import java.util.ArrayList;

public class Folder {
    private ArrayList<Note>notes;
    private String name;

    public Folder(String name){
        this.name = name;
        this.notes = new ArrayList<Note>();
    }

    public void addNote(Note note){
        notes.add(note);
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    @Override
	public String toString() {
		int nText = 0;
		int nImage = 0;
		
		
		for(int i=0; i<notes.size();i++) {
			if(notes.get(i) instanceof TextNote)
				nText++;
			if(notes.get(i) instanceof ImageNote)
				nImage++;
		}
		
		return name + ":" + nText+ ":" +nImage;
	}

    public boolean equals(Object obj){
        if (this.name.equals(((Folder) obj).getName())){
            return true;
        }else return false;
    }
    
    




	
}
