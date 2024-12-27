package com.android.server.notification;

import android.app.ActivityManager;
import android.os.UserManager;
import android.provider.Settings;
import android.widget.Toast;
import com.android.internal.util.FunctionalUtils;
import com.android.server.notification.NotificationManagerService;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationManagerService$$ExternalSyntheticLambda10 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ NotificationManagerService$$ExternalSyntheticLambda10(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                NotificationManagerService notificationManagerService = (NotificationManagerService) this.f$0;
                CharSequence charSequence = (CharSequence) this.f$1;
                if (Settings.Global.getInt(notificationManagerService.getContext().getContentResolver(), "show_notification_channel_warnings", 0) != 0) {
                    Toast.makeText(notificationManagerService.getContext(), notificationManagerService.mHandler.getLooper(), charSequence, 0).show();
                    break;
                }
                break;
            case 1:
                NotificationManagerService.AnonymousClass16 anonymousClass16 = (NotificationManagerService.AnonymousClass16) this.f$0;
                ArrayList arrayList = (ArrayList) this.f$1;
                for (int i : anonymousClass16.this$0.mUm.getProfileIds(ActivityManager.getCurrentUser(), false)) {
                    arrayList.add(Integer.valueOf(i));
                }
                break;
            default:
                UserManager userManager = (UserManager) this.f$0;
                ArrayList arrayList2 = (ArrayList) this.f$1;
                for (int i2 : userManager.getProfileIds(ActivityManager.getCurrentUser(), false)) {
                    arrayList2.add(Integer.valueOf(i2));
                }
                break;
        }
    }
}
