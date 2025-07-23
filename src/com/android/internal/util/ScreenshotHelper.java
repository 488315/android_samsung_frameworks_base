package com.android.internal.util;

import android.app.job.JobInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.util.ScreenshotRequest;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class ScreenshotHelper {
    public static final int SCREENSHOT_MSG_PROCESS_COMPLETE = 2;
    public static final int SCREENSHOT_MSG_URI = 1;
    private static final String TAG = "ScreenshotHelper";
    private final Context mContext;
    private final int SCREENSHOT_TIMEOUT_MS = 10000;
    private final Object mScreenshotLock = new Object();
    private IBinder mScreenshotService = null;
    private ServiceConnection mScreenshotConnection = null;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.internal.util.ScreenshotHelper.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            synchronized (ScreenshotHelper.this.mScreenshotLock) {
                if ("android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                    ScreenshotHelper.this.resetConnection();
                }
            }
        }
    };

    public ScreenshotHelper(Context context) {
        this.mContext = context;
    }

    public void takeScreenshot(int i, Handler handler, Consumer<Uri> consumer) {
        takeScreenshot(new ScreenshotRequest.Builder(1, i).build(), handler, consumer);
    }

    public void takeScreenshot(ScreenshotRequest screenshotRequest, Handler handler, Consumer<Uri> consumer) {
        takeScreenshotInternal(screenshotRequest, handler, consumer, JobInfo.MIN_BACKOFF_MILLIS);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0065, code lost:
    
        resetConnection();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void takeScreenshotInternal(com.android.internal.util.ScreenshotRequest r13, final android.os.Handler r14, final java.util.function.Consumer<android.net.Uri> r15, long r16) {
        /*
            r12 = this;
            r6 = r16
            java.lang.String r8 = "Couldn't take screenshot: "
            java.lang.Object r9 = r12.mScreenshotLock
            monitor-enter(r9)
            android.content.Context r0 = r12.mContext     // Catch: java.lang.Throwable -> La4
            android.content.BroadcastReceiver r2 = r12.mBroadcastReceiver     // Catch: java.lang.Throwable -> La4
            android.content.IntentFilter r4 = new android.content.IntentFilter     // Catch: java.lang.Throwable -> La4
            java.lang.String r5 = "android.intent.action.USER_SWITCHED"
            r4.<init>(r5)     // Catch: java.lang.Throwable -> La4
            r5 = 2
            r0.registerReceiver(r2, r4, r5)     // Catch: java.lang.Throwable -> La4
            com.android.internal.util.ScreenshotHelper$$ExternalSyntheticLambda0 r5 = new com.android.internal.util.ScreenshotHelper$$ExternalSyntheticLambda0     // Catch: java.lang.Throwable -> La4
            r5.<init>()     // Catch: java.lang.Throwable -> La4
            r0 = 0
            r10 = 0
            android.os.Message r11 = android.os.Message.obtain(r10, r0, r13)     // Catch: java.lang.Throwable -> La4
            com.android.internal.util.ScreenshotHelper$2 r0 = new com.android.internal.util.ScreenshotHelper$2     // Catch: java.lang.Throwable -> La4
            android.os.Looper r2 = r14.getLooper()     // Catch: java.lang.Throwable -> La4
            r1 = r12
            r4 = r14
            r3 = r15
            r0.<init>(r2)     // Catch: java.lang.Throwable -> La4
            android.os.Messenger r2 = new android.os.Messenger     // Catch: java.lang.Throwable -> La4
            r2.<init>(r0)     // Catch: java.lang.Throwable -> La4
            r11.replyTo = r2     // Catch: java.lang.Throwable -> La4
            android.content.ServiceConnection r0 = r12.mScreenshotConnection     // Catch: java.lang.Throwable -> La4
            if (r0 == 0) goto L63
            android.os.IBinder r2 = r12.mScreenshotService     // Catch: java.lang.Throwable -> La4
            if (r2 != 0) goto L3d
            goto L63
        L3d:
            android.os.Messenger r0 = new android.os.Messenger     // Catch: java.lang.Throwable -> La4
            android.os.IBinder r1 = r12.mScreenshotService     // Catch: java.lang.Throwable -> La4
            r0.<init>(r1)     // Catch: java.lang.Throwable -> La4
            r0.send(r11)     // Catch: android.os.RemoteException -> L48 java.lang.Throwable -> La4
            goto L5f
        L48:
            r0 = move-exception
            java.lang.String r1 = "ScreenshotHelper"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La4
            r2.<init>(r8)     // Catch: java.lang.Throwable -> La4
            r2.append(r0)     // Catch: java.lang.Throwable -> La4
            java.lang.String r0 = r2.toString()     // Catch: java.lang.Throwable -> La4
            android.util.Log.e(r1, r0)     // Catch: java.lang.Throwable -> La4
            if (r15 == 0) goto L5f
            r15.accept(r10)     // Catch: java.lang.Throwable -> La4
        L5f:
            r14.postDelayed(r5, r6)     // Catch: java.lang.Throwable -> La4
            goto La2
        L63:
            if (r0 == 0) goto L68
            r12.resetConnection()     // Catch: java.lang.Throwable -> La4
        L68:
            android.content.Context r0 = r12.mContext     // Catch: java.lang.Throwable -> La4
            android.content.res.Resources r0 = r0.getResources()     // Catch: java.lang.Throwable -> La4
            r2 = 17040328(0x10403c8, float:2.4247284E-38)
            java.lang.String r0 = r0.getString(r2)     // Catch: java.lang.Throwable -> La4
            android.content.ComponentName r0 = android.content.ComponentName.unflattenFromString(r0)     // Catch: java.lang.Throwable -> La4
            android.content.Intent r8 = new android.content.Intent     // Catch: java.lang.Throwable -> La4
            r8.<init>()     // Catch: java.lang.Throwable -> La4
            r8.setComponent(r0)     // Catch: java.lang.Throwable -> La4
            com.android.internal.util.ScreenshotHelper$3 r0 = new com.android.internal.util.ScreenshotHelper$3     // Catch: java.lang.Throwable -> La4
            r1 = r12
            r4 = r14
            r3 = r15
            r2 = r11
            r0.<init>()     // Catch: java.lang.Throwable -> La4
            android.content.Context r2 = r12.mContext     // Catch: java.lang.Throwable -> La4
            android.os.UserHandle r3 = android.os.UserHandle.CURRENT     // Catch: java.lang.Throwable -> La4
            r10 = 67108865(0x4000001, float:1.504633E-36)
            boolean r2 = r2.bindServiceAsUser(r8, r0, r10, r3)     // Catch: java.lang.Throwable -> La4
            if (r2 == 0) goto L9d
            r12.mScreenshotConnection = r0     // Catch: java.lang.Throwable -> La4
            r14.postDelayed(r5, r6)     // Catch: java.lang.Throwable -> La4
            goto La2
        L9d:
            android.content.Context r1 = r12.mContext     // Catch: java.lang.Throwable -> La4
            r1.unbindService(r0)     // Catch: java.lang.Throwable -> La4
        La2:
            monitor-exit(r9)     // Catch: java.lang.Throwable -> La4
            return
        La4:
            r0 = move-exception
            monitor-exit(r9)     // Catch: java.lang.Throwable -> La4
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.util.ScreenshotHelper.takeScreenshotInternal(com.android.internal.util.ScreenshotRequest, android.os.Handler, java.util.function.Consumer, long):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$takeScreenshotInternal$0(Consumer consumer) {
        synchronized (this.mScreenshotLock) {
            if (this.mScreenshotConnection != null) {
                Log.e(TAG, "Timed out before getting screenshot capture response");
                resetConnection();
                notifyScreenshotError();
            }
        }
        if (consumer != null) {
            consumer.accept(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetConnection() {
        ServiceConnection serviceConnection = this.mScreenshotConnection;
        if (serviceConnection != null) {
            this.mContext.unbindService(serviceConnection);
            this.mScreenshotConnection = null;
            this.mScreenshotService = null;
        }
        try {
            this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        } catch (IllegalArgumentException unused) {
            Log.w(TAG, "Attempted to remove broadcast receiver twice");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyScreenshotError() {
        ComponentName unflattenFromString = ComponentName.unflattenFromString(this.mContext.getResources().getString(R.string.config_screenshotErrorReceiverComponent));
        Intent intent = new Intent(Intent.ACTION_USER_PRESENT);
        intent.setComponent(unflattenFromString);
        intent.addFlags(335544320);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
    }
}
