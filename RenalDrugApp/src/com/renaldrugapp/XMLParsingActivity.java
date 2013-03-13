package com.renaldrugapp;


import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;



public class XMLParsingActivity extends ListActivity {

	// All static variables
	String URL;
	static final String URL0 = "http://feeds.bbci.co.uk/news/health/rss.xml"; 
	
	
	
	// XML node keys
    String KEY_DATE_GLOBAL;
    String KEY_LINK_GLOBAL;
	String KEY_INTENT_GLOBAL;
	
	// bbc source
	static final String KEY_ITEM0 = "item"; 
	static final String KEY_DATE0 = "pubDate";
    static final String KEY_LINK0 = "link";
	static final String KEY_DESC0 = "description";
	static final String KEY_INTENT0 = KEY_DESC0;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
		
		XMLParser parser = new XMLParser();
		
		String xml0;
		Document doc0;
		
		// getting XML
		
		
		xml0 = parser.getXmlFromUrl(URL0); //getting xml from URL
		
		
		
		// getting DOM element
		doc0 = parser.getDomElement(xml0); 
		 
		
		NodeList nl0;
		
		nl0 = doc0.getElementsByTagName("item");
		
		NodeList nl = nl0;
		
		
		// looping through all nodes <item>
		
		for (int i = 0; i<nl0.getLength(); i++) {

			genericXMLParser( menuItems, nl, i, parser, KEY_ITEM0, KEY_DATE0, KEY_LINK0, KEY_DESC0, KEY_INTENT0); //then its bbc tur
			
		}		
		
		
		
      
		
	}
		
		

		
	private void genericXMLParser(ArrayList<HashMap<String, String>> menuItems, NodeList nl, int i, XMLParser parser,
			String KEY_ITEM, String KEY_DATE, String KEY_LINK, String KEY_DESC, String KEY_INTENT) 
	{
		
		//tempNewsFeedObj = new NewsFeedObject();
		
		
		
		KEY_DATE_GLOBAL = KEY_DATE;
		KEY_LINK_GLOBAL = KEY_LINK;
		KEY_INTENT_GLOBAL = KEY_INTENT;
		
			
		
		    // creating new HashMap
		          HashMap<String, String> map = new HashMap<String, String>();
		          Element e = (Element) nl.item(i);
		          // adding each child node to HashMap key => value
		          //map.put(KEY_ID, parser.getValue(e, KEY_ID));
		          String checkDate = parser.getValue(e, KEY_DATE);
		          map.put(KEY_DATE, checkDate);
		          
		          String checkLink = parser.getValue(e, KEY_LINK);
		          map.put(KEY_LINK, checkLink);
		          
		          String checkDesc = parser.getValue(e, KEY_DESC);
		          map.put(KEY_DESC, checkDesc);
		          
		          map.put(KEY_INTENT, parser.getValue(e, KEY_INTENT));
		          // adding HashList to ArrayList
		          menuItems.add(map);
		        
		   
		        // Adding menuItems to ListView
		        ListAdapter adapter = new SimpleAdapter(this, menuItems,
		            R.layout.list_scroll,
		            new String[] { KEY_DATE, KEY_DESC, KEY_LINK }, new int[] {
		               R.id.date, R.id.desciption, R.id.link });
		    
		        setListAdapter(adapter);
		    
		       
		    
	
				
		// selecting single ListView item
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() { 
		
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				// getting values from selected ListItem to pass them onto details page
				String date = ((TextView) view.findViewById(R.id.date)).getText().toString();
				String link = ((TextView) view.findViewById(R.id.link)).getText().toString();
				String description = ((TextView) view.findViewById(R.id.desciption)).getText().toString();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), SingleMenuItem.class);
				in.putExtra(KEY_DATE_GLOBAL, date);
				in.putExtra(KEY_LINK_GLOBAL, link);
				in.putExtra(KEY_INTENT_GLOBAL, description);
				startActivity(in);

			}
		});
	}
	
	
	
}








