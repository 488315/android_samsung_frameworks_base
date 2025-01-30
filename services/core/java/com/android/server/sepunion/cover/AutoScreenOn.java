package com.android.server.sepunion.cover;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import com.samsung.android.sepunion.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class AutoScreenOn {
  public static final String TAG = "CoverManager_" + AutoScreenOn.class.getSimpleName();
  public final boolean mAutoScreenOnFeature;
  public ContentResolver mContentResolver;
  public final Context mContext;
  public final boolean mNfcAuthEnabled;
  public AutoScreenOnObserver mObserver;
  public final Object mLock = new Object();
  public boolean mIsAutoScreenOn = true;

  public AutoScreenOn(Looper looper, Context context) {
    this.mContext = context;
    this.mNfcAuthEnabled = Feature.getInstance(context).isNfcAuthEnabled();
    this.mAutoScreenOnFeature =
        context.getPackageManager().hasSystemFeature("com.sec.feature.cover.autoscreenon");
    if (support()) {
      init(looper);
    }
  }

  public boolean support() {
    return !this.mNfcAuthEnabled || this.mAutoScreenOnFeature;
  }

  public boolean off() {
    boolean z;
    synchronized (this.mLock) {
      z = !this.mIsAutoScreenOn;
    }
    return z;
  }

  public void update() {
    synchronized (this.mLock) {
      boolean z = true;
      if (Settings.System.getIntForUser(this.mContentResolver, "auto_screen_on", 1, -2) != 1) {
        z = false;
      }
      this.mIsAutoScreenOn = z;
    }
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    printWriter.println(" Current AutoScreenOn State:");
    synchronized (this.mLock) {
      printWriter.println("  " + this.mNfcAuthEnabled + " " + this.mAutoScreenOnFeature);
      StringBuilder sb = new StringBuilder();
      sb.append("  mAutoScreenOn = ");
      sb.append(this.mIsAutoScreenOn);
      printWriter.println(sb.toString());
      printWriter.println("  ");
    }
  }

  public final void init(Looper looper) {
    this.mContentResolver = this.mContext.getContentResolver();
    this.mObserver = new AutoScreenOnObserver(new Handler(looper));
    this.mContentResolver.registerContentObserver(
        Settings.System.getUriFor("auto_screen_on"), false, this.mObserver, -1);
    update();
  }

  public class AutoScreenOnObserver extends ContentObserver {
    public AutoScreenOnObserver(Handler handler) {
      super(handler);
      Log.v(AutoScreenOn.TAG, "AutoScreenOnObserver");
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
      super.onChange(z);
      Log.v(AutoScreenOn.TAG, "AutoScreenOnObserver.onChange(boolean selfChange : " + z + ")");
      AutoScreenOn.this.update();
    }
  }
}
