package controller.state;

import controller.Controller;
import java.awt.Dimension;
import model.fileLoader.MapLoaderXML;
import model.map.MapI;
import view.Console;
import view.Window;

/**
 * State loaded on the opening of the application, only authorize the loading of a map
 * @author jerome
 */
public class InitState extends DefaultState {
	
    /**
     * the margin around the map after the loading
     */
    protected static final int MARGIN = 50;
    
    @Override
    public MapI loadXMLMap(Controller ctrl, Window view, MapI previousMap) {      

        MapLoaderXML mapLoaderXML = new MapLoaderXML();

        MapI map;
        try {
                map = mapLoaderXML.loadMap(view.getFileName());
        } catch (Throwable e) {

                if(e.getLocalizedMessage() != null) {
                        Console.getConsole().createText(e.getLocalizedMessage());
                }
                else {
                        Console.getConsole().createText(Console.LOAD_MAP_FAILURE);
                }
                return previousMap;
        }

        Dimension mapDimensions = map.getSize();

        view.setMap(map, (int)(mapDimensions.getWidth() + MARGIN), (int) (mapDimensions.getHeight() + MARGIN));
        ctrl.setCurrentState(Controller.MLS);	
        Console.getConsole().createText(Console.LOAD_MAP_SUCCESS);

        return map;

    }
	

    @Override
    public void accept(StateVisitor visitor) {
        visitor.visit(this);
    }
}
