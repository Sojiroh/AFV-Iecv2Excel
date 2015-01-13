package cl.facele.docele.sii.iecv.excel;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App {
	Logger logger = Logger.getLogger(App.class.getCanonicalName());
	private String dirPath = "C:\\Users\\Sojiroh\\Downloads\\jc2";
	
    public static void main( String[] args ) throws Exception {
        System.out.println( "Hello World!" );
        App app = new App();
        app.doit();
    }
	private void doit() throws Exception {
		logger.info("Start....");
		Path p = Paths.get(dirPath);
		logger.debug("Path: " + p.toAbsolutePath().toString());
		if (Files.notExists(p.toAbsolutePath()))
			throw new Exception("No existe directorio: " + p.toAbsolutePath().toString());
		
		ReadXML r = new ReadXML();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(p)) {
			for (Path file: stream) {
				logger.info("File: " + file.toString());
				if (Files.isDirectory(file))
					continue;
				
				if (!file.getFileName().toString().toLowerCase().endsWith(".xml"))
					continue;
				
				Bean b = r.read(file);
				Files.write(file.getParent().resolve(b.getRut() + "_" + b.getOperacion() + "_" + b.getPeriodo() + "_" + System.currentTimeMillis() + ".xls"), b.getContenido().getBytes(), StandardOpenOption.CREATE_NEW);
				
			}
		}
		logger.info("...End");
	}
}
