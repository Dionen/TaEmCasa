package com.usp.icmc.taemcasa.Wishlist.WishlistResponse;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joao on 19/06/2017.
 */

public class WishlistRequest_GETALL extends StringRequest {
    private static final String GET_REQUEST_URL = "https://dionen.000webhostapp.com/Wishlist/getWishlist.php";

    private Map<String, String> params;

    public WishlistRequest_GETALL(String user_id, Response.Listener<String> listener){
        super(Request.Method.POST, GET_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_id", user_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }


}
