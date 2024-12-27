package com.android.server.backup.transport;

import android.util.AndroidException;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;

public class TransportNotRegisteredException extends AndroidException {
    public TransportNotRegisteredException(String str) {
        super(XmlUtils$$ExternalSyntheticOutline0.m("Transport ", str, " not registered"));
    }
}
