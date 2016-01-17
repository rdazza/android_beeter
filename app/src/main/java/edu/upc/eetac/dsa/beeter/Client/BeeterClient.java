package edu.upc.eetac.dsa.beeter.Client;

import android.util.Log;

import com.google.gson.Gson;

import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.upc.eetac.dsa.beeter.Client.entity.AuthToken;

/**
 * Created by ruben on 15/01/16.
 */
public class BeeterClient {
    private final static String BASE_URI = "http://192.168.1.43:8080/beeter";
    private static BeeterClient instance;
    private AuthToken authToken = null;
    private Root root;
    private ClientConfig clientConfig = null;
    private Client client = null;
    private final static String TAG = BeeterClient.class.toString();


    private BeeterClient() {
        clientConfig = new ClientConfig();
        client = ClientBuilder.newClient(clientConfig);
        loadRoot();
    }

    public static BeeterClient getInstance() {
        if (instance == null)
            instance = new BeeterClient();
        return instance;
    }

    private void loadRoot() {
        //WebTarget target = client.target(BASE_URI);
        WebTarget target = client.target(BASE_URI);
        Response response = target.request().get();

        String json = response.readEntity(String.class);
        root = (new Gson()).fromJson(json, Root.class);
    }

    public Root getRoot() {
        return root;
    }

    public final static Link getLink(List<Link> links, String rel){
        for(Link link : links){
            if(link.getRels().contains(rel)) {
                return link;
            }
        }
        return null;
    }

    public boolean login(String userid, String password) {
        String loginUri = getLink(root.getLinks(), "login").getUri().toString();
        WebTarget target = client.target(loginUri);
        Form form = new Form();
        form.param("login", "spongebob");
        form.param("password", "1234");
        String json = target.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
        authToken = (new Gson()).fromJson(json, AuthToken.class);
        Log.d(TAG, json);
        return true;
    }
    public String getStings(String uri) throws BeeterClientException {
        if(uri==null){
            uri = getLink(authToken.getLinks(), "current-stings").getUri().toString();
        }
        WebTarget target = client.target(uri);
        Response response = target.request().get();
        if (response.getStatus() == Response.Status.OK.getStatusCode())
            return response.readEntity(String.class);
        else
            throw new BeeterClientException(response.readEntity(String.class));
    }
    public String getSting(String uri) throws BeeterClientException {
        return null;
    }


}