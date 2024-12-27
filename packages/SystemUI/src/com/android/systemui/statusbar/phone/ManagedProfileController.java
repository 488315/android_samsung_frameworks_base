package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.policy.CallbackController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface ManagedProfileController extends CallbackController {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
        void onManagedProfileChanged();

        void onManagedProfileRemoved();
    }
}
