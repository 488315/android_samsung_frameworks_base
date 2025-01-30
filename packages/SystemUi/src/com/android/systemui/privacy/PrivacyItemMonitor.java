package com.android.systemui.privacy;

import com.android.systemui.Dumpable;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface PrivacyItemMonitor extends Dumpable {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
    }

    List getActivePrivacyItems();

    void startListening(PrivacyItemController$privacyItemMonitorCallback$1 privacyItemController$privacyItemMonitorCallback$1);

    void stopListening();
}
