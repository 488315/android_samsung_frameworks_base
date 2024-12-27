package com.android.server.hdmi;

import android.hardware.hdmi.HdmiPortInfo;
import android.util.SparseArray;

public final class HdmiMhlControllerStub {
    public static final HdmiPortInfo[] EMPTY_PORT_INFO;

    static {
        new SparseArray();
        EMPTY_PORT_INFO = new HdmiPortInfo[0];
    }
}
