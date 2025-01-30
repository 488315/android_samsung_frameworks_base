package com.android.systemui.statusbar.notification.collection.provider;

import android.util.ArraySet;
import com.android.systemui.util.ListenerSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VisualStabilityProvider {
    public final ListenerSet allListeners = new ListenerSet();
    public final ArraySet temporaryListeners = new ArraySet();
    public boolean isReorderingAllowed = true;
}
