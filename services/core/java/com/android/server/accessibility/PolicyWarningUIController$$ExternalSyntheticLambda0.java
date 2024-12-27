package com.android.server.accessibility;

import android.content.ComponentName;
import com.android.server.accessibility.PolicyWarningUIController;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PolicyWarningUIController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PolicyWarningUIController$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((PolicyWarningUIController) obj2).mNotificationController.mSendNotification = ((Boolean) obj).booleanValue();
                break;
            default:
                ((PolicyWarningUIController.NotificationController) obj2).mNotificationManager.cancel(((ComponentName) obj).flattenToShortString(), 1005);
                break;
        }
    }
}
