package base;

public class TextNote extends Note {

	private String content;

	public TextNote(String title, String content) {
		// TODO Auto-generated constructor stub
		super(title);
		this.content = content;
	}

	public String getContent() {
		return content;
	}

}
