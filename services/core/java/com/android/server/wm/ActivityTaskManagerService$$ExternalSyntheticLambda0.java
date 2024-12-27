package com.android.server.wm;

import android.app.ActivityManagerInternal;
import android.app.INotificationManager;
import android.app.NotificationManager;
import android.os.RemoteException;
import android.util.Slog;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ActivityTaskManagerService$$ExternalSyntheticLambda0
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((ActivityManagerInternal) obj)
                        .reportCurKeyguardUsageEvent(((Boolean) obj2).booleanValue());
                break;
            case 1:
                ((ActivityManagerInternal) obj)
                        .updateOomLevelsForDisplay(((Integer) obj2).intValue());
                break;
            default:
                int intValue = ((Integer) obj2).intValue();
                ((ActivityTaskManagerService) obj).getClass();
                INotificationManager service = NotificationManager.getService();
                if (service != null) {
                    try {
                        service.cancelNotificationWithTag(
                                "android", "android", (String) null, 11, intValue);
                        break;
                    } catch (RemoteException unused) {
                        return;
                    } catch (RuntimeException e) {
                        Slog.w(
                                "ActivityTaskManager",
                                "Error canceling notification for service",
                                e);
                    }
                }
                break;
        }
    }
}
