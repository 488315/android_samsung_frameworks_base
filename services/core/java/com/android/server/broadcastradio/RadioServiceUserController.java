package com.android.server.broadcastradio;

import android.app.ActivityManager;
import android.os.Binder;

/* loaded from: classes.dex */
public abstract class RadioServiceUserController {
    public static boolean isCurrentOrSystemUser() {
        int identifier = Binder.getCallingUserHandle().getIdentifier();
        return identifier == getCurrentUser() || identifier == 0;
    }

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
}
