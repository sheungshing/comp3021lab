package base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.io.Serializable;

public class Folder implements Comparable<Folder>,Serializable {
    private static final long serialVersionUID = 1L;
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

//     public List<Note> searchNotes(String keywords) {

//         List<Note> noteReturn = new ArrayList<Note>(); // for the return result
//         keywords = keywords.toLowerCase();
//         String[] orFilter = keywords.split(" ");
//         List<String> andArr = new ArrayList<String>(); // and list
//         List<String> orArr = new ArrayList<String>(); // or list

//         // add to or lists
//         int i = 0;
//         while (i < orFilter.length - 1) {
//             if (orFilter[i + 1].equals("or")) {
//                 orArr.add(orFilter[i]);
//                 orArr.add(orFilter[i + 2]);
//                 i = i + 3;
//             } else {
//                 andArr.add(orFilter[i]);
//                 i++;
//             }
//         }
//         //// add to AND lists
//         // i = 0;
//         // while (i < orFilter.length - 1) {
//         // if (!(orFilter[i + 1].equals("or")) && !(orFilter[i].equals("or"))) {
//         // andArr.add(orFilter[i]);
//         // andArr.add(orFilter[i + 1]);
//         // } 
//         // i++;

//         // }

       
//         for (Note loopNote : notes) {
//             // ImageNote
//             if (loopNote instanceof ImageNote) {
//             	boolean orPass = false;
//             	System.out.println("andArr size:"+ andArr.size() );
//             	//System.out.println("ordArr size:"+ orArr.size() );
            	
//             	//if no AND case
//             	if(andArr.size() ==0) {
//             		for(int k =0; k < orArr.size();k++) {
//                 		if( ((ImageNote)loopNote).getTitle().toLowerCase().contains(orArr.get(k))) {
//                 			//System.out.println("time:"+ k);
//                 			orPass = true;
//                 			break;
//                 		}
//                 	}
//             	}
            	
//             	//if there are AND case
//                 for(int j = 0; j< andArr.size();j++) {
//                 	//System.out.println("time:"+ j);
//                 	for(int k =0; k < orArr.size();k+=2) {
//                 		if( ((ImageNote)loopNote).getTitle().toLowerCase().contains(orArr.get(k)) 
//                 			||((ImageNote)loopNote).getTitle().toLowerCase().contains(orArr.get(k+1))) {
//                 			//System.out.println("time:"+ k);
//                 			orPass = true;
//                 		}else {
// 							orPass = false;
// 						}
//                 	}
                	
//                 }
//                 if(orPass == true)
//             		noteReturn.add(loopNote);

//             } else if (loopNote instanceof TextNote) { // TextNote
//             	boolean orPass = false;
//             	//System.out.println("andArr size:"+ andArr.size() );
// //            	System.out.println("ordArr size:"+ orArr.size() );
//             	//if no AND case
//             	if(andArr.size() ==0) {
//             		for(int k =0; k < orArr.size();k++) {
//                 		if( ((TextNote)loopNote).getTitle().toLowerCase().contains(orArr.get(k))
//                 				||((TextNote)loopNote).getContent().toLowerCase().contains(orArr.get(k))) {
//                 			//System.out.println("time:"+ k);
//                 			orPass = true;
//                 			break;
//                 		}
//                 	}
//             	}
            	
//             	//if there are AND case
//                 for(int j = 0; j< andArr.size();j++) {
//                 	//System.out.println("time:"+ j);
//                 	for(int k =0; k < orArr.size();k+=2) {
//                 		if( ((TextNote)loopNote).getTitle().toLowerCase().contains(orArr.get(k)) 
//                 			||((TextNote)loopNote).getTitle().toLowerCase().contains(orArr.get(k+1))
//                 			||((TextNote)loopNote).getContent().toLowerCase().contains(orArr.get(k))
//                 			||((TextNote)loopNote).getContent().toLowerCase().contains(orArr.get(k+1))) {
//                 			//System.out.println("time:"+ k);
//                 			orPass = true;
//                 		}else {
// 							orPass = false;
// 						}
//                 	}
                	
//                 }
//                 if(orPass == true)
//             		noteReturn.add(loopNote);
                
//             }

//         }

//         return noteReturn;

//     }

    public boolean removeNotes(String title) {
		   // TODO
		   // Given the title of the note, delete it from the folder.
		   // Return true if it is deleted successfully, otherwise return false. 
		for(Note n : notes){
			if(n.getTitle().equals(title)){
				notes.remove(n);
				return true;
			}
		}
		return false;
	}


        //helper method
	public boolean matching(String content, String[] keywords) {
		boolean found = false;
		for(int i = 0; i<keywords.length; i++) {
			//System.out.println("Finding a keyword " + keywords[i] + " in " + content);
			if(i+1<keywords.length && keywords[i+1].contains("or")) {
				if(content.contains(keywords[i]) || content.contains(keywords[i+2])) {
					//skip "or" and the next letter for continue matching
					//System.out.println("Found a keyword " + keywords[i] + " or " + keywords[i+2] + " in " + content);
					found = true;
					i+=2;
				}
				else {
					//System.out.println("Unable to find a keyword " + keywords[i] + " or  " + keywords[i+2] + " in " + content);
					found = false;
				}
			}
			// no "or" next to the keyword
			else {
				if(content.contains(keywords[i])) {
					//System.out.println("Found a keyword " + keywords[i] + " in " + content);
					found = true;
				}
				else {
					//System.out.println("Unable to find a keyword " + keywords[i] + " in " + content);
					found = false;
				}
			}
			if(!found) {
				return found;
			}
		}
		return found;
	}
	
	public List<Note> searchNotes(String keywords){
		//create result list
		ArrayList<Note> result_list = new ArrayList<Note>();
		// create a word list removing all spaces, just words and change all to lower case
		String s_keywords = keywords.toLowerCase();
		String[] words = s_keywords.split("\\W+");
		//System.out.println(words.toString());
		// going through all the notes
		for(Note n : notes) {
			if (n instanceof TextNote) {
				//casing n back to textnote
				TextNote text = (TextNote)n;
				// combine title and content into a single string
				String title = text.getTitle().toLowerCase();
				String content = text.getContent().toLowerCase();
				String search_pool = title + " " + content;
				// call matching function
				if(matching(search_pool,words)) {
					//if match add to result_list
					result_list.add(n);
				}
				
			}
			else if (n instanceof ImageNote) {
				String title = n.getTitle().toLowerCase();
				if(matching(title,words)) {
					//if match add to result_list
					result_list.add(n);
				}
				
			}
		}
		//result
		return result_list;
		
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
