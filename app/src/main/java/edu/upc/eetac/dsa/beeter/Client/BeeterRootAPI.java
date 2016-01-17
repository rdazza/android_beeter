package edu.upc.eetac.dsa.beeter.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.upc.eetac.dsa.beeter.Client.entity.Sting;

/**
 * Created by root on 25/12/15.
 */
public class BeeterRootAPI {

    private List<Sting> stings;

    public BeeterRootAPI() {
        stings = new ArrayList<>();
    }

    public List<Sting> getStings() {
        return stings;
    }


}