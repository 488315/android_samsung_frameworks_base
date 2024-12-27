package com.android.server.remoteappmode;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;

import com.android.internal.os.BackgroundThread;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RFCommServiceLauncher {
    public final Context mContext;
    public final Handler mHandler = BackgroundThread.getHandler();
    public boolean mBounded = false;
    public final AnonymousClass1 mConnection = new AnonymousClass1();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.remoteappmode.RFCommServiceLauncher$1, reason: invalid class name */
    public final class AnonymousClass1 implements ServiceConnection {
        public AnonymousClass1() {}

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("RFCommServiceLauncher", "HotspotRFCommService is connected");
            RFCommServiceLauncher.this.mBounded = true;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Log.i("RFCommServiceLauncher", "HotspotRFCommService is disconnected");
            RFCommServiceLauncher rFCommServiceLauncher = RFCommServiceLauncher.this;
            rFCommServiceLauncher.mBounded = false;
            rFCommServiceLauncher.mHandler.postDelayed(
                    new Runnable() { // from class:
                        // com.android.server.remoteappmode.RFCommServiceLauncher$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            RFCommServiceLauncher.AnonymousClass1 anonymousClass1 =
                                    RFCommServiceLauncher.AnonymousClass1.this;
                            anonymousClass1.getClass();
                            Intent intent =
                                    new Intent(
                                            "com.samsung.android.mdx.instanthotspot.action.RFCOMM_SERVICE_DISCONNECTED");
                            Context context = RFCommServiceLauncher.this.mContext;
                            for (ResolveInfo resolveInfo :
                                    context.getPackageManager()
                                            .queryBroadcastReceivers(intent, 0)) {
                                if (resolveInfo.activityInfo.packageName.equals(
                                        "com.samsung.android.mdx")) {
                                    ActivityInfo activityInfo = resolveInfo.activityInfo;
                                    intent.setComponent(
                                            new ComponentName(
                                                    activityInfo.packageName, activityInfo.name));
                                    context.sendBroadcast(intent);
                                }
                            }
                        }
                    },
                    3000L);
        }
    }

    public RFCommServiceLauncher(Context context) {
        this.mContext = context;
    }

    public final void bindService(Context context) {
        Log.i("RFCommServiceLauncher", "bindService - mBounded : " + this.mBounded);
        if (this.mBounded) {
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setComponent(
                    new ComponentName(
                            "com.samsung.android.mdx",
                            "com.samsung.android.mdx.windowslink.interactor.instanthotspot.HotspotRFCommService"));
            context.bindService(intent, this.mConnection, 1);
        } catch (RuntimeException e) {
            Log.e("RFCommServiceLauncher", "bindService failed," + e);
        }
    }
}
