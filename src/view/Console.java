package view;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;

/**
 * The text console on the bottom of the application. It's a global
 * singleton accessible either for our debugging or to display information for the user
 * Use Console.getConsole().createText(String) to write a one line message
 * @author jliermann
 *
 */
public class Console extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8047292590187479823L;
	
	private static final String UPPERLINE_WRAPPER_BEGIN = "<html><font color='yellow'>> ";
	private static final String UPPERLINE_WRAPPER_END = "";
	private static final String BOTTOMLINE_WRAPPER_BEGIN = "<br/>> ";
	private static final String BOTTOMLINE_WRAPPER_END = "</font></html>";
	
	private static final Dimension MINIMUM_SIZE = new Dimension(300, 10);
	private static final Dimension MAXIMUM_SIZE = new Dimension(2000, 10);
	
	private String bottomText = "";
	private String upperText = "";

	private static Console singleton = null;
	
	public static final String SELECT_DELIVERY = "Select the delivery prior to the new one";
	public static final String SELECT_NEW_DELIVERY_LOCATION = "Select the location for the new delivery";
	public static final String FILL_DELIVERY_INFORMATION = "Complete the fields and validate";
	public static final String LOAD_MAP_SUCCESS = "Successfully loaded map";
	public static final String LOAD_DELIVERIES_SUCCESS = "Successfully loaded deliveries request";
	public static final String COMPUTE_SUCCESS = "Successfully computed journey";
	public static final String LOAD_MAP_FAILURE = "Unable to load map. Reason Unknown.";
	public static final String LOAD_DELIVERIES_FAILURE = "Unable to load delivery request. Reason Unknown.";
	public static final String COMPUTE_FAILURE = "Unable to compute. Reason Unknown.";
	public static final String ADD_DELIVERY_SUCCESS = "Successfully added delivery";
	public static final String ADD_DELIVERY_FAILURE = "Unable to add a delivery. Reason Unknown.";
	public static final String CREATE_ROADMAP_SUCCESS = "Roadmap.txt successfully created.";
	public static final String CREATE_ROADMAP_FAILURE = "Roadmap failed to compute :";
	public static final String MODIFY_SUCCESS = "Successfully modified delivery";
	public static final String MODIFY_FAILURE = "Unable to modify delivery";
	
	/**
	 * Constructor
	 * 
	 * @param text This is the text that will be displayed on the console.
	 */
	private Console(String text) {
		
		super(UPPERLINE_WRAPPER_BEGIN + text + UPPERLINE_WRAPPER_END + BOTTOMLINE_WRAPPER_BEGIN + BOTTOMLINE_WRAPPER_END);
		upperText = text;
		this.setBackground(Color.black);
		this.setOpaque(true);
		this.setAlignmentX(0.5f);
		this.setMinimumSize(MINIMUM_SIZE);
		this.setMaximumSize(MAXIMUM_SIZE);
	}
	
	/**
	 * Return the Console singleton
	 * 
	 * @return Console the singleton
	 */
	public static Console getConsole() {
		if(singleton == null) {
			singleton = new Console("");
		}
		
		return singleton;
		
	}
	
	/**
	 * Write a line of text on the bottom line of the console.
	 * Switch the previous bottom text on the upperline if there's one,
	 * otherwise put text directly on the upperline
	 * @param text the text to be displayed
	 */
	public void createText(String text) {
		if(bottomText != "") {
			upperText = bottomText;
		}
		bottomText = text;
		
		this.setText(UPPERLINE_WRAPPER_BEGIN + upperText + UPPERLINE_WRAPPER_END + BOTTOMLINE_WRAPPER_BEGIN + bottomText + BOTTOMLINE_WRAPPER_END);
	}

}
