package com.android.server.am;

import android.util.AndroidException;

/* loaded from: classes.dex */
public class BroadcastDeliveryFailedException extends AndroidException {
    public BroadcastDeliveryFailedException(String str) {
        super(str);
    }

    public BroadcastDeliveryFailedException(Exception exc) {
        super(exc);
    }
}
