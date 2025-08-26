package com.android.systemui.statusbar.notification.collection.provider;

import android.util.ArraySet;
import com.android.systemui.util.ListenerSet;

/* loaded from: classes3.dex */
public final class VisualStabilityProvider {
    public final ListenerSet allListeners = new ListenerSet();
    public final ArraySet temporaryListeners = new ArraySet();
    public final ListenerSet banListeners = new ListenerSet();
    public boolean isReorderingAllowed = true;
}
