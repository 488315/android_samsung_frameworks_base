package com.android.server.broadcastradio;

import android.app.ActivityManager;
import android.os.Binder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class RadioServiceUserController {
    public static int getCurrentUser() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int currentUser = ActivityManager.getCurrentUser();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return currentUser;
        } catch (RuntimeException unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -10000;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static boolean isCurrentOrSystemUser() {
        int identifier = Binder.getCallingUserHandle().getIdentifier();
        return identifier == getCurrentUser() || identifier == 0;
    }
}
