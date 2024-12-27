package com.android.server.am;

public class BroadcastRetryException extends BroadcastDeliveryFailedException {
    public BroadcastRetryException(String str) {
        super(str);
    }
}
