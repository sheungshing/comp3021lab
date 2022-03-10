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

    // public List<Note> searchNotes2(String keywords) {
    //
    // List<Note> noteReturn = new ArrayList<Note>(); // for the return result
    // keywords = keywords.toLowerCase();
    // String[] orFilter = keywords.split(" ");
    // List<String> andArr = new ArrayList<String>(); // and list
    // List<String> orArr = new ArrayList<String>(); // or list
    //
    // // add to or lists
    // int i = 0;
    // while (i < orFilter.length - 1) {
    // if (orFilter[i + 1].equals("or")) {
    // orArr.add(orFilter[i]);
    // orArr.add(orFilter[i + 2]);
    // i = i + 3;
    // } else {
    // andArr.add(orFilter[i]);
    // i++;
    // }
    // }
    // //// add to and lists
    // // i = 0;
    // // while (i < orFilter.length - 1) {
    // // if (!(orFilter[i + 1].equals("or")) && !(orFilter[i].equals("or"))) {
    // // andArr.add(orFilter[i]);
    // // andArr.add(orFilter[i + 1]);
    //
    // // } else {
    //
    // // }
    // // i++;
    //
    // // }
    //
    // System.out.println("And: ");
    // for (String and : andArr) {
    // System.out.println(and);
    // }
    //
    // System.out.println("Or: ");
    // for (String or : orArr) {
    // System.out.println(or);
    // }
    //
    // for (Note loopNote : notes) {
    // // ImageNote
    // if (loopNote instanceof ImageNote) {
    // boolean andState = false;
    // for (String and : andArr) {
    // if (!(((ImageNote) loopNote).getTitle().toLowerCase().contains(and))) {
    // andState = true;
    // break;
    // }
    // }
    // if (andState == false) {
    // for (String or : orArr) {
    // if (((ImageNote) loopNote).getTitle().toLowerCase().contains(or)) {
    //
    // noteReturn.add(loopNote);
    // break;
    //
    // }
    // }
    // }
    //
    // } else if (loopNote instanceof TextNote) { // TextNote
    // boolean andState = false;
    // for (String and : andArr) {
    // if (!((TextNote) loopNote).getContent().toLowerCase().contains(and)
    // || !((TextNote) loopNote).getTitle().toLowerCase().contains(and)) {
    // andState = true;
    // break;
    // }
    // }
    // if (andState == false) {
    // for (String or : orArr) {
    // if (((TextNote) loopNote).getContent().toLowerCase().contains(or)
    // || ((TextNote) loopNote).getTitle().toLowerCase().contains(or)) {
    // System.out.println("now: " + or +"\t" + orArr.get(orArr.size()-1));
    // noteReturn.add(loopNote);
    // break;
    //
    // }
    // }
    //
    // }
    // }
    //
    // }
    //
    // return noteReturn;
    //
    // }

    public List<Note> searchNotes(String keywords) {
        List<Note> returnNote = new ArrayList<Note>();
        keywords = keywords.toLowerCase();
        String[] wordsFilter = keywords.split(" ");
        ArrayList<String> wordlist = new ArrayList<String>();

        int i = 0;
        while (i < wordsFilter.length) {
            if (wordsFilter[i + 1].equals("or")) {
                wordlist.add(wordsFilter[i] + " " + wordsFilter[i + 2]); // add OR to
                i = i + 3;
            } else {
                wordlist.add(wordsFilter[i]); // add And to words
                i = i + 1;
            }
        }

        // Search
        for (Note temp : notes) {
            for (String word : wordlist) {
                // OR
                if (word.contains(" ")) {
                    String word1 = word.substring(0, word.indexOf(" "));
                    String word2 = word.substring(word.indexOf(" ") + 1);
                    if (temp instanceof TextNote) {
                        if (temp.getTitle().toLowerCase().contains(word1)
                                || temp.getTitle().toLowerCase().contains(word2)
                                || ((TextNote) temp).getContent().toLowerCase().contains(word1)
                                || ((TextNote) temp).getContent().toLowerCase().contains(word2)) {
//                            for (String t : wordlist) {
//                                System.out.println(t);
//                            }
                            //System.out.println(word + "////" + wordlist.get(wordlist.size() - 1));
                            if (word.compareTo(wordlist.get(wordlist.size() - 1)) == 0) {
                                // System.out.println(word + "////" + wordlist.get(wordlist.size() - 1));
                                returnNote.add(temp);
                            }
                        }

                    } else if (temp.getTitle().toLowerCase().contains(word1)
                            || temp.getTitle().toLowerCase().contains(word2)) {
                        if (word.compareTo(wordlist.get(wordlist.size() - 1)) == 0)
                            returnNote.add(temp);
                    }

                }

                // AND
                else {
                    if (temp instanceof TextNote) {
                        if (temp.getTitle().toLowerCase().contains(word)
                                || ((TextNote) temp).getContent().toLowerCase().contains(word)) {
                            if (word.compareTo(wordlist.get(wordlist.size() - 1)) == 0)
                                returnNote.add(temp);
                        }

                    } else {
                        if (temp.getTitle().toUpperCase().contains(word)) {
                            if (word.compareTo(wordlist.get(wordlist.size() - 1)) == 0)
                                returnNote.add(temp);
                        }
                    }
                }
            }
        }
        return returnNote;
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
