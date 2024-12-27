package com.android.systemui.statusbar.phone;

import android.util.Log;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SamsungLightBarControlHelper {
    public final KeyguardStateController keyguardStateController;
    public NavigationBarModel navigationBarModel;
    public StatusBarModel statusBarModel;

    public SamsungLightBarControlHelper(KeyguardStateController keyguardStateController) {
        this.keyguardStateController = keyguardStateController;
    }

    public final void updateStatusBarModel(String str, int i, ArrayList arrayList, int i2) {
        StatusBarModel statusBarModel = new StatusBarModel(str, i, arrayList, i2, "", 0, false, 96, null);
        StatusBarModel statusBarModel2 = this.statusBarModel;
        if (statusBarModel2 == null || !statusBarModel2.equals(statusBarModel)) {
            this.statusBarModel = statusBarModel;
            Log.d("SamsungLightBarControlHelper", "updateStatusBar " + statusBarModel);
        }
    }
}
