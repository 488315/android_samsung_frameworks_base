package com.android.server.biometrics.sensors.fingerprint;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.provider.Settings;
import com.android.server.biometrics.Utils;

/* loaded from: classes.dex */
public class SemFpOneHandModeMonitor implements SemFpEventListener {
  ContentObserver mContentObserver;
  public final Context mContext;
  public final Injector mInjector;
  public final ServiceProvider mServiceProvider;

  public class Injector {
    public void registerContentObserver(Context context, Uri uri, ContentObserver contentObserver) {
      context.getContentResolver().registerContentObserver(uri, false, contentObserver, -2);
    }

    public boolean isOneHandModeRunning(Context context) {
      return Utils.isOneHandMode(context);
    }
  }

  public SemFpOneHandModeMonitor(Context context, ServiceProvider serviceProvider) {
    this(context, serviceProvider, new Injector());
  }

  public SemFpOneHandModeMonitor(
      Context context, ServiceProvider serviceProvider, Injector injector) {
    this.mContext = context;
    this.mServiceProvider = serviceProvider;
    this.mInjector = injector;
  }

  public void start() {
    this.mServiceProvider.semAddEventListener(this);
    observe();
  }

  public final void observe() {
    this.mContentObserver =
        new ContentObserver(
            SemFpMainThread.get()
                .getHandler()) { // from class:
                                 // com.android.server.biometrics.sensors.fingerprint.SemFpOneHandModeMonitor.1
          @Override // android.database.ContentObserver
          public void onChange(boolean z) {
            SemFpOneHandModeMonitor.this.handleContentChanged();
          }
        };
    this.mInjector.registerContentObserver(
        this.mContext, Settings.System.getUriFor("any_screen_running"), this.mContentObserver);
  }

  public final void handleContentChanged() {
    if (this.mInjector.isOneHandModeRunning(this.mContext)) {
      this.mServiceProvider.onOneHandModeEnabled();
    }
  }
}
