package com.android.systemui.statusbar.phone;

import android.provider.Settings;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((LegacyNotificationIconAreaControllerImpl) obj).updateStatusBarIcons();
                break;
            default:
                LegacyNotificationIconAreaControllerImpl.this.mSettingsCallback.onChanged(Settings.System.getUriFor(SettingsHelper.INDEX_STATUSBAR_NOTIFICATION_STYLE));
                break;
        }
    }
}
