package com.example.photoeditor;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ячсчяс on 31.07.2017.
 */
public class ChangePWRequest extends StringRequest {

    private static final String CHANGEPW_REQUEST_URL = "https://assiyakhuzyakhmetova.000webhostapp.com/ChangePassword.php";
    private Map<String, String> params;

    public ChangePWRequest( String username, String password, Response.Listener<String> listener) {
        super(Method.POST, CHANGEPW_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}