package com.syedmuzani.umbrella.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Used for storing timestamps in the sample Firebase
 */

@IgnoreExtraProperties
@SuppressWarnings("WeakerAccess")
public class FirebaseTimestamp {
    public long start;
    public long end;

    public FirebaseTimestamp() {

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("start", start);
        result.put("end", end);
        return result;
    }

}
