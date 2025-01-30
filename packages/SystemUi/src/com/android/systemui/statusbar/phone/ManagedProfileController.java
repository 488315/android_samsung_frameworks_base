package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.policy.CallbackController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface ManagedProfileController extends CallbackController {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        void onManagedProfileChanged();

        void onManagedProfileRemoved();
    }
}
