package com.usp.icmc.taemcasa.Utils;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * Created by Juliana on 07/06/2017.
 */

public class MessagesToUser extends FragmentActivity{

    // Precisa passar o context
    // user getContext dentro de um fragmento não funciona

    public void ToastMessage(CharSequence text, Context context){
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
