package com.android.server.sepunion.cover;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import com.samsung.android.sepunion.Log;

import java.io.PrintWriter;

public final class AutoScreenOn {
    public final boolean mAutoScreenOnFeature;
    public final ContentResolver mContentResolver;
    public final boolean mNfcAuthEnabled;
    public final AutoScreenOnObserver mObserver;
    public final Object mLock = new Object();
    public boolean mIsAutoScreenOn = true;

    public final class AutoScreenOnObserver extends ContentObserver {
        public AutoScreenOnObserver(Handler handler) {
            super(handler);
            Log.v("CoverManager_AutoScreenOn", "AutoScreenOnObserver");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            Log.v(
                    "CoverManager_AutoScreenOn",
                    "AutoScreenOnObserver.onChange(boolean selfChange : " + z + ")");
            AutoScreenOn.this.update();
        }
    }

    public AutoScreenOn(Looper looper, Context context) {
        Feature.getInstance(context).getClass();
        this.mNfcAuthEnabled = Feature.sIsNfcAuthSystemFeatureEnabled;
        this.mAutoScreenOnFeature =
                context.getPackageManager().hasSystemFeature("com.sec.feature.cover.autoscreenon");
        if (support()) {
            this.mContentResolver = context.getContentResolver();
            this.mObserver = new AutoScreenOnObserver(new Handler(looper));
            this.mContentResolver.registerContentObserver(
                    Settings.System.getUriFor("auto_screen_on"), false, this.mObserver, -1);
            update();
        }
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println(" Current AutoScreenOn State:");
        synchronized (this.mLock) {
            printWriter.println("  " + this.mNfcAuthEnabled + " " + this.mAutoScreenOnFeature);
            StringBuilder sb = new StringBuilder("  mAutoScreenOn = ");
            sb.append(this.mIsAutoScreenOn);
            printWriter.println(sb.toString());
            printWriter.println("  ");
        }
    }

    public final boolean off() {
        boolean z;
        synchronized (this.mLock) {
            z = !this.mIsAutoScreenOn;
        }
        return z;
    }

    public final boolean support() {
        return !this.mNfcAuthEnabled || this.mAutoScreenOnFeature;
    }

    public final void update() {
        synchronized (this.mLock) {
            boolean z = true;
            if (Settings.System.getIntForUser(this.mContentResolver, "auto_screen_on", 1, -2)
                    != 1) {
                z = false;
            }
            this.mIsAutoScreenOn = z;
        }
    }
}
