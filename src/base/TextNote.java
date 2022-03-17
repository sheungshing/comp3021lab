package base;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class TextNote extends Note {
	//private static final long serialVersionUID = 1L;

	private String content;

	public TextNote(String title, String content) {
		// TODO Auto-generated constructor stub
		super(title);
		this.content = content;
	}

	public TextNote(File f){
		super(f.getName());
		this.content = getTextFromFile(f.getAbsolutePath());

	}

	public String getContent() {
		return content;
	}

	private String getTextFromFile(String absolutePath){
		String result = "";
		
		try{
			File file = new File(absolutePath);
			FileInputStream fips = new FileInputStream(file);
			InputStreamReader ipsr = new InputStreamReader(fips);
			BufferedReader br = new BufferedReader(ipsr);

			String temp ;
			while((temp = br.readLine()) != null){
				result += temp + "\n";
			}
			br.close();
			
			System.out.println(result);
			return result;
		}catch(FileNotFoundException e1){
			System.out.println(e1.toString());
		}catch(IOException e2){
			System.out.println(e2.toString());
		}

		return result;

		
	}

	public void exportTextToFile(String  pathFolder){
		if(pathFolder == ""){
			pathFolder = ".";
		}
		
		
		
		try {
			File  file = new File(pathFolder + File.separator + this.getTitle().replace(" ", "_") + ".txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getName()));
			bw.write(this.getContent());
			
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("exportTextToFile error");
			e.printStackTrace();
		}

		

	}


}
