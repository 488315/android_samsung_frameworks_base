package com.android.systemui.statusbar.phone;

import android.provider.Settings;
import com.android.systemui.statusbar.phone.LegacyNotificationIconAreaControllerImpl;
import com.android.systemui.util.SettingsHelper;

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
                ((LegacyNotificationIconAreaControllerImpl.AnonymousClass3) obj).this$0.mSettingsCallback.onChanged(Settings.System.getUriFor(SettingsHelper.INDEX_STATUSBAR_NOTIFICATION_STYLE));
                break;
        }
    }
}
