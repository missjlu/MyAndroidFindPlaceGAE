package ds.findplacegae;


import ds.findplacegae.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * TargetedPlace is used to record the thread reference and monitor the activity upon creation, as well as
 * providing user with result upon finish 
 * 
 * @author Jelena
 *
 */
public class TargetedPlace extends Activity {
	/**
	 * Record the onclick object's reference and add a listener to submit button upon creation of the Activity
	 * 
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final TargetedPlace ip = this;
        
        Button submitButton = (Button)findViewById(R.id.submit);
        submitButton.setOnClickListener(new OnClickListener(){
        	/**
        	 * Add a listener to submit button
        	 * 
        	 */
        	@Override
			public void onClick(View viewParam) {
        		String searchTerm = ((EditText)findViewById(R.id.searchTerm)).getText().toString();
        		GetPlace gp = new GetPlace();
        		gp.search(searchTerm, ip); // Done asynchronously in another thread.  It calls ip.pictureReady() in this thread when complete.
        	}
        });
    }
    

    /**
     * This is called by the GetPlace object when the place is ready.
     * Text view will display one of the places match user's place search.
     * 
     * @param place details of one of the places match user's place search
     */
    public void placeReady(String place) {
		
		TextView searchView = (EditText)findViewById(R.id.searchTerm);
		TextView searchResult = (TextView)findViewById(R.id.feedBack);
		TextView placeDetails = (TextView)findViewById(R.id.place);
		if (place != null) {   		
    		placeDetails.setText(place);
    		placeDetails.setVisibility(View.VISIBLE);
    		searchResult.setText("One of the places related to " + ((EditText)findViewById(R.id.searchTerm)).getText().toString()+ " is:");
    		searchResult.setVisibility(View.VISIBLE);
    	} else {  		
    		searchResult.setText("Sorry, I could not find a place related to " + ((EditText)findViewById(R.id.searchTerm)).getText().toString());
    		searchResult.setVisibility(View.VISIBLE);
    		placeDetails.setText("");
    		placeDetails.setVisibility(View.VISIBLE);
    	}
		searchView.setText("");
		
    }
    
    
    
}