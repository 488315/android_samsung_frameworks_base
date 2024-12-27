package com.android.systemui.statusbar.notification.collection.provider;

import android.util.ArraySet;
import com.android.systemui.util.ListenerSet;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class VisualStabilityProvider {
    public final ListenerSet allListeners = new ListenerSet();
    public final ArraySet temporaryListeners = new ArraySet();
    public boolean isReorderingAllowed = true;
}
