package base;

import java.util.Date;
import java.util.Objects;

public class Note {
    public Date date;
    public  String title;

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if(!(obj instanceof Note))
			return false;
		Note other = (Note) obj;
		return Objects.equals(title, other.title);
		
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Note other = (Note) obj;
//		if (title == null) {
//			if (other.title != null)
//				return false;
//		} else if (!title.equals(other.title))
//			return false;
//		return true;
	}

	public Note(String title){
		this.title = title;
        this.date = new Date(System.currentTimeMillis());
    }

    public String getTitle(){
        return this.title; 
    }

    
	

}
