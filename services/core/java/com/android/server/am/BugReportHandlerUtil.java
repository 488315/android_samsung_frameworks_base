package com.android.server.am;

import android.app.BroadcastOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.BugreportManager;
import android.os.BugreportParams;
import android.os.UserHandle;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BugReportHandlerUtil {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BugreportHandlerResponseBroadcastReceiver extends BroadcastReceiver {
        public final String handlerApp;
        public final int handlerUser;

        public BugreportHandlerResponseBroadcastReceiver(String str, int i) {
            this.handlerApp = str;
            this.handlerUser = i;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (getResultCode() == -1) {
                BugReportHandlerUtil.launchBugReportHandlerApp(
                        context, this.handlerApp, this.handlerUser);
            } else {
                Slog.w(
                        "ActivityManager",
                        "Request bug report because no response from handler app.");
                ((BugreportManager) context.getSystemService(BugreportManager.class))
                        .requestBugreport(new BugreportParams(1), null, null);
            }
        }
    }

    public static void launchBugReportHandlerApp(Context context, String str, int i) {
        Slog.i("ActivityManager", "Launching bug report handler app: " + str);
        Intent intent = new Intent("com.android.internal.intent.action.BUGREPORT_REQUESTED");
        intent.setPackage(str);
        intent.addFlags(268435456);
        intent.addFlags(16777216);
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setBackgroundActivityStartsAllowed(true);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                context.sendBroadcastAsUser(
                        intent, UserHandle.of(i), "android.permission.DUMP", makeBasic.toBundle());
            } catch (RuntimeException e) {
                Slog.e("ActivityManager", "Error while trying to launch bugreport handler app.", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
