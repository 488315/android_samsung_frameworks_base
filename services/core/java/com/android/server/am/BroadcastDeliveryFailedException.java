package com.android.server.am;

import android.util.AndroidException;

public class BroadcastDeliveryFailedException extends AndroidException {
    public BroadcastDeliveryFailedException(String str) {
        super(str);
    }
}
