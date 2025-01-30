package com.android.server.sepunion.eventdelegator;

import java.util.HashMap;

/* loaded from: classes3.dex */
public class ListenerContainer {
    public final int mUserId;
    public final HashMap mUriEventMap = new HashMap();
    public final HashMap mIntentActionMap = new HashMap();
    public final HashMap mCustomEventMap = new HashMap();

    public ListenerContainer(int i) {
        this.mUserId = i;
    }
}
