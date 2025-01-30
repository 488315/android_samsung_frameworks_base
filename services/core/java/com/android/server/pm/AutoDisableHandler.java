package com.android.server.pm;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import com.android.server.am.MARsPolicyManager;

/* loaded from: classes3.dex */
public class AutoDisableHandler {
    public ADHandler mAutoDisableHandler;
    public Context mContext;
    public PackageManagerService mPkgMgrSvc;

    public AutoDisableHandler(Context context, PackageManagerService packageManagerService) {
        this.mContext = context;
        this.mPkgMgrSvc = packageManagerService;
        new AutoDisableThread("AutoDisableThread", 0).start();
    }

    public class AutoDisableThread extends Thread {
        public int mPriority;

        public AutoDisableThread(String str, int i) {
            super(str);
            this.mPriority = i;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Process.setThreadPriority(this.mPriority);
            Looper.prepare();
            AutoDisableHandler.this.mAutoDisableHandler = AutoDisableHandler.this.new ADHandler();
            Looper.loop();
        }
    }

    public class ADHandler extends Handler {
        public Bundle extras = null;

        public ADHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Bundle data;
            if (message.what == 1 && (data = message.getData()) != null) {
                String string = data.getString("packageName", null);
                int i = data.getInt("userId", -1);
                if (data.getBoolean("isGoogleChanged", false)) {
                    MARsPolicyManager.getInstance().setGoogleEnabled(string, i);
                } else {
                    MARsPolicyManager.getInstance().resetAutoDisabledAppState(string, i, true);
                }
            }
        }
    }

    public void sendAutoDisabledAppStateChanged(String str, int i, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putString("packageName", str);
        bundle.putInt("userId", i);
        bundle.putBoolean("isGoogleChanged", z);
        Message obtainMessage = this.mAutoDisableHandler.obtainMessage(1);
        obtainMessage.setData(bundle);
        this.mAutoDisableHandler.sendMessage(obtainMessage);
    }
}
