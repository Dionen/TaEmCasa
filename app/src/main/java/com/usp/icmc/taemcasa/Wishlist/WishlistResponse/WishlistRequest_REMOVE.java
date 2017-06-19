package com.usp.icmc.taemcasa.Wishlist.WishlistResponse;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joao on 19/06/2017.
 */

public class WishlistRequest_REMOVE extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://dionen.000webhostapp.com/Wishlist/removeWishlist.php";
    private Map<String, String> params;

    public WishlistRequest_REMOVE(String user_id, String id_vaga, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("id_vaga", id_vaga);
        params.put("user_id", user_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}