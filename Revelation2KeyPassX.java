import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Convert Revelation exported XML to KeyPassX's format's <br/>
 * UNSUPPORTED: description, email fields
 */
public class Revelation2KeyPassX {

	public static String convert(String input) {
		return input //
				.replaceAll("<\\?xml version=\"1.0\" encoding=\"utf-8\" \\?>", "<!DOCTYPE KEEPASSX_DATABASE>") //
				.replaceAll("<revelationdata[^>]*>", "<database><group><title>Internet</title><icon>1</icon>") //
				.replaceAll("</revelationdata>", "</group><group><title>eMail</title><icon>19</icon></group></database>") //
				//
				.replaceAll("<entry[^>]*>", "<entry>") //
				.replaceAll("</entry>", "<expire>Never</expire></entry>") //
				.replaceAll("<field id=\"generic-url\">([^<]+)</field>", "<url>$1</url>") //
				.replaceAll("<field id=\"generic-username\">([^<]+)</field>", "<username>$1</username>") //
				.replaceAll("<field id=\"generic-password\">([^<]+)</field>", "<password>$1</password>") //
				.replaceAll("<name>([^<]+)</name>", "<title>$1</title>") //
				.replaceAll("<notes>([^<]+)</notes>", "<comment>$1</comment>") //
				.replaceAll("<created>([^<]+)</created>", "") //
				.replaceAll("<updated>([^<]+)</updated>", "") //
				.replaceAll("<description>([^<]+)</description>", "") //
				;
	}

	static final String FILE_NAME = "/tmp/revel";

	public static void main(String[] args) throws IOException {
		final String input = new String(Files.readAllBytes(Paths.get(FILE_NAME + ".xml")));
		final String result = convert(input);
		Files.write(Paths.get(FILE_NAME + "-converted.xml"), result.getBytes());
		System.out.println("OK");
	}
}
