package com.android.systemui.statusbar.notification.collection.provider;

import android.util.ArraySet;
import com.android.systemui.util.ListenerSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VisualStabilityProvider {
    public final ListenerSet allListeners = new ListenerSet();
    public final ArraySet temporaryListeners = new ArraySet();
    public final ListenerSet banListeners = new ListenerSet();
    public boolean isReorderingAllowed = true;
}
