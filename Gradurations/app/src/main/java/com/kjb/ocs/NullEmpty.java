package com.kjb.ocs;

import android.text.TextUtils;

public class NullEmpty {

    public String NullEmpty(String... data){

        if(TextUtils.isEmpty(data[0])||TextUtils.isEmpty(data[1])||TextUtils.isEmpty(data[2])){

            return String.valueOf(false);

        }else{

            return String.valueOf(true);
        }
    };

}
