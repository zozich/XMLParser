package test.ua.goit.alg.xmlparser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.goit.alg.xmlparser.input.StreamReader;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class StreamReaderTest {
  private File testXMLFile;

  @Before
  public void createTempResource() throws IOException {
    String fileSep = System.getProperty("file.separator");
    String dirPath = System.getProperty("user.dir") + fileSep + "resource";
    testXMLFile = File.createTempFile("temp", ".txt", new File(dirPath));
    testXMLFile.deleteOnExit();

    String xmlString = "<?xml version=\"1.0\"?><start atr1=\"3\" atr2 = \"4\"><tag>text</tag><tag2/></start>";

    PrintWriter out = null;
    try {
      out = new PrintWriter(testXMLFile);
      out.print(xmlString);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } finally {
      if (out != null) {
        out.close();
      }
    }
  }

  @After
  public void deleteTestFile() {
//    testXMLFile.delete();
  }

  @Test
  public void testStreamReader_read_String() throws IOException {
    String xmlString = "<?xml doctype=\"1\"><start atr1=\"3\" atr2 = \"4\"><tag>text</tag><tag2/></start>";
    StreamReader stream = new StreamReader(xmlString);
    String actualXML = readString_actualXML(stream);
    assertEquals(xmlString, actualXML);
  }

  @Test
  public void readFromInputStreamReader() throws IOException {
    String xmlString = "<?xml version=\"1.0\"?><start atr1=\"3\" atr2 = \"4\"><tag>text</tag><tag2/></start>";
    InputStream stream = new FileInputStream(testXMLFile);
    StreamReader reader = new StreamReader(stream);
    String actualXML = readString_actualXML(reader);
    assertEquals(xmlString, actualXML);
  }

  @Test
  public void testStreamReader_read_emptyString() throws IOException {
    String xmlString = "";
    StreamReader stream = new StreamReader(xmlString);
    String actualXML = readString_actualXML(stream);
    assertEquals(xmlString, actualXML);
  }

  @Test
  public void testStreamReader_read_File() throws IOException {
    String xmlString = "<?xml version=\"1.0\"?><start atr1=\"3\" atr2 = \"4\"><tag>text</tag><tag2/></start>";
    StreamReader stream = new StreamReader(testXMLFile);
    String actualXML = readString_actualXML(stream);
    assertEquals(xmlString, actualXML);
  }

  private String readString_actualXML(StreamReader stream) throws IOException {
    StringBuilder actualXML = new StringBuilder();
    int symbol;
    do {
      symbol = stream.read();
      if (symbol == -1) {
        break;
      }
      actualXML.append((char) symbol);
    } while (true);
    return actualXML.toString();
  }
}
