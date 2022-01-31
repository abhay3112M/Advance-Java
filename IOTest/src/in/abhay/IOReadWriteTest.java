package in.abhay;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class IOReadWriteTest {
	String inputPath = "C:\\Advance-Java\\my-work\\IOTest\\sample.txt";
	String outputPath = "C:\\Advance-Java\\my-work\\IOTest\\sampleoutput.txt";
	File inputfile;
	File outputfile;
	

	@Before
	public void setUp() throws Exception {
		inputfile = new File(inputPath);
		outputfile = new File(outputPath);
	}

	@Test
	public void shouldReadAllTheContentFromFile() throws IOException{
		var reader = new FileReader(inputfile); 
		var writer = new FileWriter(outputfile); 
		String data="";
		while(true) {
			int ch = reader.read();
			if(ch==-1) break;
			writer.write(ch);
		}
		assertEquals(inputfile.length(),outputfile.length());
	}

}
