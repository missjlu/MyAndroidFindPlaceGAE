package ds.findplacegae;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.os.AsyncTask;


/**
 * GetPlace class is used to search for a related place on via Google Search Place API/Google Maps given a searchTerm.  
 *
 * @author Jelena
 *
 */
public class GetPlace {
	TargetedPlace ip = null;

	/**
	 * Get the requested place and call back to the thread/object which
	 * initiated the request
	 * 
	 * @param searchTerm
	 *            the place to be searched
	 * @param ip
	 *            the TargetedPlace object initiating the search
	 */
	public void search(String searchTerm, TargetedPlace ip) {
		this.ip = ip;
		new AsyncGooglePlaceSearch().execute(searchTerm);
	}

	/*
	 * AsyncTask provides a way to use a separate helper thread to do network
	 * operation doInBackground runs in the helper thread. onPostExecute runs in
	 * the UI thread.
	 */
	private class AsyncGooglePlaceSearch extends
			AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			return search(urls[0]);
		}

		@Override
		protected void onPostExecute(String place) {
			ip.placeReady(place);
		}

		/*
		 * Use Google Place Search API to get a place related to the searchTerm,
		 * and return a place details that can be put in an TextView
		 */
		private String search(String searchTerm) {

			Document doc = getRemoteXML("http://1-dot-findplaceyunjial.appspot.com//myandroidgooglemapsgaeserver?query="
					+ searchTerm.replaceAll(" ", "+"));
			if (doc!=null){
			doc.getDocumentElement().normalize();
			NodeList nl = doc.getElementsByTagName("result");
			if (nl.getLength() == 0) {
				return null; // no places found
			} else {
				Node n = nl.item(0);
				Element e = (Element) n;
				String name = e.getElementsByTagName("name").item(0)
						.getTextContent();
				String formatted_address = e
						.getElementsByTagName("formatted_address").item(0)
						.getTextContent();
				String rating = e.getElementsByTagName("rating").item(0)
						.getTextContent();
				String open_now = e.getElementsByTagName("open_now").item(0)
						.getTextContent();
				return "Name: " + name + "\n" + "Address: " + formatted_address
						+ "\n" + "Rating: " + rating + "\n"
						+ "Is it open now? " + open_now + "\n";
			}
			}
			else return null;
		}

		/*
		 * Return a document representation of a URL which generate XML
		 * response, else return null
		 */
		private Document getRemoteXML(String url) {
			try {
				DocumentBuilderFactory bf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = bf.newDocumentBuilder();
				InputSource i = new InputSource(url);
				return db.parse(i);
			} catch (Exception e) {
				System.out.print("Error: " + e);
				return null;
			}
		}

	}
}