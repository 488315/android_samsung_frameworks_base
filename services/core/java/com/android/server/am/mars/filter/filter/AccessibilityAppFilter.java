package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.filter.IFilter;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class AccessibilityAppFilter implements IFilter {
  public final String TAG;
  public ContentObserver mAccessibilityContentObserver;
  public Context mContext;
  public ArrayList mEnabledAccessibilityPackages;
  public boolean mRegisteredAccessibilityContentObserver;

  public abstract class AccessibilityAppFilterHolder {
    public static final AccessibilityAppFilter INSTANCE = new AccessibilityAppFilter();
  }

  public AccessibilityAppFilter() {
    this.TAG = "MARs:" + AccessibilityAppFilter.class.getSimpleName();
    this.mContext = null;
    this.mAccessibilityContentObserver = null;
    this.mRegisteredAccessibilityContentObserver = false;
    this.mEnabledAccessibilityPackages = new ArrayList();
  }

  public static AccessibilityAppFilter getInstance() {
    return AccessibilityAppFilterHolder.INSTANCE;
  }

  public final void setContext(Context context) {
    this.mContext = context;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void init(Context context) {
    setContext(context);
    getEnabledAccessibilityPackage();
    registerContentObserver();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void deInit() {
    synchronized (this.mEnabledAccessibilityPackages) {
      this.mEnabledAccessibilityPackages.clear();
    }
    unregisterContentObserver();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public int filter(String str, int i, int i2, int i3) {
    ArrayList arrayList;
    Context context = this.mContext;
    if (context == null
        || i != context.getUserId()
        || str == null
        || (arrayList = this.mEnabledAccessibilityPackages) == null) {
      return 0;
    }
    synchronized (arrayList) {
      return this.mEnabledAccessibilityPackages.contains(str) ? 19 : 0;
    }
  }

  public final void getEnabledAccessibilityPackage() {
    synchronized (this.mEnabledAccessibilityPackages) {
      this.mEnabledAccessibilityPackages.clear();
    }
    String string =
        Settings.Secure.getString(
            this.mContext.getContentResolver(), "enabled_accessibility_services");
    if (string == null) {
      return;
    }
    if (string.contains(XmlUtils.STRING_ARRAY_SEPARATOR)) {
      for (String str : string.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
        if (str != null && str.contains("/")) {
          addPackages(str);
        }
      }
      return;
    }
    if (string.contains("/")) {
      addPackages(string);
    }
  }

  public final void addPackages(String str) {
    String[] split = str.split("/");
    if (split[0].length() > 0) {
      String str2 = split[0];
      synchronized (this.mEnabledAccessibilityPackages) {
        if (!this.mEnabledAccessibilityPackages.contains(str2)) {
          this.mEnabledAccessibilityPackages.add(str2);
          if (MARsDebugConfig.DEBUG_FILTER) {
            Slog.d(
                this.TAG,
                "getEnabledAccessibilityPackage: add mEnabledAccessibilityPackages " + str2);
          }
        }
      }
    }
  }

  public final void registerContentObserver() {
    if (this.mRegisteredAccessibilityContentObserver) {
      return;
    }
    this.mAccessibilityContentObserver =
        new ContentObserver(
            new Handler()) { // from class:
                             // com.android.server.am.mars.filter.filter.AccessibilityAppFilter.1
          @Override // android.database.ContentObserver
          public void onChange(boolean z, Uri uri) {
            AccessibilityAppFilter.this.getEnabledAccessibilityPackage();
          }
        };
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Settings.Secure.getUriFor("enabled_accessibility_services"),
            false,
            this.mAccessibilityContentObserver,
            this.mContext.getUserId());
    this.mRegisteredAccessibilityContentObserver = true;
  }

  public final void unregisterContentObserver() {
    try {
      if (this.mRegisteredAccessibilityContentObserver) {
        this.mContext
            .getContentResolver()
            .unregisterContentObserver(this.mAccessibilityContentObserver);
        this.mRegisteredAccessibilityContentObserver = false;
      }
    } catch (IllegalArgumentException unused) {
      Slog.e(this.TAG, "IllegalArgumentException occurred in unregisterContentObserver()");
    }
  }

  public boolean isEnabledAccessibilityApp(String str) {
    synchronized (this.mEnabledAccessibilityPackages) {
      return this.mEnabledAccessibilityPackages.contains(str);
    }
  }
}
