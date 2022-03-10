package base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Folder implements Comparable<Folder> {
    private ArrayList<Note> notes;
    private String name;

    public Folder(String name) {
        this.name = name;
        this.notes = new ArrayList<Note>();
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void sortNotes() {
        Collections.sort(this.notes);
        // System.out.println("gooo");
    }

    public List<Note> searchNotes2(String keywords) {

        List<Note> noteReturn = new ArrayList<Note>(); // for the return result
        keywords = keywords.toLowerCase();
        String[] orFilter = keywords.split(" ");
        List<String> andArr = new ArrayList<String>(); // and list
        List<String> orArr = new ArrayList<String>(); // or list

        // add to or lists
        int i = 0;
        while (i < orFilter.length - 1) {
            if (orFilter[i + 1].equals("or")) {
                orArr.add(orFilter[i]);
                orArr.add(orFilter[i + 2]);
                i = i + 3;
            } else {
                //andArr.add(orFilter[i]);
                i++;
            }
        }
        //// add to AND lists
        i = 0;
        while (i < orFilter.length - 1) {
        if (!(orFilter[i + 1].equals("or")) && !(orFilter[i].equals("or"))) {
        andArr.add(orFilter[i]);
        andArr.add(orFilter[i + 1]);

        } 
        i++;

        }

       
        for (Note loopNote : notes) {
            // ImageNote
            if (loopNote instanceof ImageNote) {
            	boolean orPass = false;
            	//System.out.println("andArr size:"+ andArr.size() );
            	//System.out.println("ordArr size:"+ orArr.size() );
            	
            	//if no AND case
            	if(andArr.size() ==0) {
            		for(int k =0; k < orArr.size();k++) {
                		if( ((ImageNote)loopNote).getTitle().toLowerCase().contains(orArr.get(k))) {
                			//System.out.println("time:"+ k);
                			orPass = true;
                			break;
                		}
                	}
            	}
            	
            	//if there are AND case
                for(int j = 0; j< andArr.size();j++) {
                	//System.out.println("time:"+ j);
                	for(int k =0; k < orArr.size();k+=2) {
                		if( ((ImageNote)loopNote).getTitle().toLowerCase().contains(orArr.get(k)) 
                			||((ImageNote)loopNote).getTitle().toLowerCase().contains(orArr.get(k+1))) {
                			//System.out.println("time:"+ k);
                			orPass = true;
                		}else {
							orPass = false;
						}
                	}
                	
                }
                if(orPass == true)
            		noteReturn.add(loopNote);

            } else if (loopNote instanceof TextNote) { // TextNote
            	boolean orPass = false;
            	//System.out.println("andArr size:"+ andArr.size() );
//            	System.out.println("ordArr size:"+ orArr.size() );
            	//if no AND case
            	if(andArr.size() ==0) {
            		for(int k =0; k < orArr.size();k++) {
                		if( ((TextNote)loopNote).getTitle().toLowerCase().contains(orArr.get(k))
                				||((TextNote)loopNote).getContent().toLowerCase().contains(orArr.get(k))) {
                			//System.out.println("time:"+ k);
                			orPass = true;
                			break;
                		}
                	}
            	}
            	
            	//if there are AND case
                for(int j = 0; j< andArr.size();j++) {
                	//System.out.println("time:"+ j);
                	for(int k =0; k < orArr.size();k+=2) {
                		if( ((TextNote)loopNote).getTitle().toLowerCase().contains(orArr.get(k)) 
                			||((TextNote)loopNote).getTitle().toLowerCase().contains(orArr.get(k+1))
                			||((TextNote)loopNote).getContent().toLowerCase().contains(orArr.get(k))
                			||((TextNote)loopNote).getContent().toLowerCase().contains(orArr.get(k+1))) {
                			//System.out.println("time:"+ k);
                			orPass = true;
                		}else {
							orPass = false;
						}
                	}
                	
                }
                if(orPass == true)
            		noteReturn.add(loopNote);
                
            }

        }

        return noteReturn;

    }

    @Override
    public String toString() {
        int nText = 0;
        int nImage = 0;

        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i) instanceof TextNote)
                nText++;
            if (notes.get(i) instanceof ImageNote)
                nImage++;
        }
        return name + ":" + nText + ":" + nImage;
    }

    public boolean equals(Object obj) {
        if (this.name.equals(((Folder) obj).getName())) {
            return true;
        } else
            return false;
    }

    @Override
    public int compareTo(Folder o) {
        // TODO Auto-generated method stub
        int tempCompare = this.name.compareTo(o.getName());
        if (tempCompare == 0)
            return 0;
        else if (tempCompare > 0)
            return 1;
        else
            return -1;
    }

}
