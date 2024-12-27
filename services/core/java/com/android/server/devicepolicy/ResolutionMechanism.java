package com.android.server.devicepolicy;

import android.app.admin.PolicyValue;

import java.util.LinkedHashMap;
import java.util.List;

public abstract class ResolutionMechanism {
    public abstract android.app.admin.ResolutionMechanism getParcelableResolutionMechanism();

    public abstract PolicyValue resolve(LinkedHashMap linkedHashMap);

    public PolicyValue resolve(List list) {
        throw new UnsupportedOperationException();
    }
}
