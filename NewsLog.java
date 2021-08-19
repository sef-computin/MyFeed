package MyFeed;
import java.net.URL;
import java.net.*;

public class NewsLog{

	private String header;
	private URL link;


	public String getHeader(){
		return header;
	}

	public void setHeader(String header){
		String[] words = header.split(" ");
		if (words.length<8)	this.header = "<html>"+header+"</html>";
		else{
			this.header = "<html>";
			for (int i =0; i<8;i++)	{this.header = this.header+words[i]+" ";}
			this.header += "<br>";
			for (int i = 8; i<words.length;i++){this.header = this.header+words[i]+" ";}
		}

	}
	public void setLink(URL link){
		this.link = link;
	}
	public String getLink(){
		return link.toString();
	}
	//public URI getURI(){
	//	return new URI(link.toString());
	//}

	public NewsLog(){

	}

	public NewsLog(String header, URL link){
		this.link = link;
		this.header = header;
	}
	public NewsLog(String header) throws MalformedURLException{
		this(header, new URL("https://www.rt.com/"));
	}
}
