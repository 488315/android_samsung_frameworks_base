package com.android.server.am.mars.filter.filter;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.telecom.DefaultDialerManager;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.telephony.SmsApplication;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.filter.IFilter;
import com.samsung.android.knox.SemPersonaManager;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class DefaultAppFilter implements IFilter {
  public final String TAG;
  public Context mContext;
  public BroadcastReceiver mDefaultAppChangedReceiver;
  public boolean mDefaultAppChangedReceiverRegistered;
  public String mDefaultDialerPackage;
  public String mDefaultHomePackage;
  public ArrayMap mDefaultIMEList;
  public String mDefaultSmsPackage;
  public ContentObserver mInputMethodObserver;
  public boolean mInputMethodObserverRegistered;
  public boolean mSecureFolderEnabled;
  public int mSecureFolderUserId;

  public abstract class DefaultAppFilterHolder {
    public static final DefaultAppFilter INSTANCE = new DefaultAppFilter();
  }

  public DefaultAppFilter() {
    this.TAG = "MARs:" + DefaultAppFilter.class.getSimpleName();
    this.mDefaultIMEList = new ArrayMap();
    this.mDefaultDialerPackage = null;
    this.mDefaultSmsPackage = null;
    this.mDefaultHomePackage = null;
    this.mContext = null;
    this.mSecureFolderEnabled = false;
    this.mInputMethodObserver = null;
    this.mInputMethodObserverRegistered = false;
    this.mDefaultAppChangedReceiver = null;
    this.mDefaultAppChangedReceiverRegistered = false;
  }

  public static DefaultAppFilter getInstance() {
    return DefaultAppFilterHolder.INSTANCE;
  }

  public final void setContext(Context context) {
    this.mContext = context;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void init(Context context) {
    setContext(context);
    int userId = this.mContext.getUserId();
    getDefaultDialerPackage();
    getDefaultSmsPackage();
    getDefaultIMEPackage(userId);
    setDefaultHomePackage();
    SemPersonaManager semPersonaManager =
        (SemPersonaManager) this.mContext.getSystemService("persona");
    if (semPersonaManager != null) {
      int knoxId = semPersonaManager.getKnoxId(2, true);
      this.mSecureFolderUserId = knoxId;
      if (userId == 0 && knoxId >= 150 && knoxId <= 160) {
        this.mSecureFolderEnabled = true;
        getDefaultIMEPackage(knoxId);
      } else {
        this.mSecureFolderEnabled = false;
      }
    }
    registerDefaultIMEContentObserver();
    registerDefaultAppChangedReceiver(userId);
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void deInit() {
    unregisterDefaultIMEContentObserver();
    unregisterDefaultAppChangedReceiver();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public int filter(String str, int i, int i2, int i3) {
    String str2;
    Context context = this.mContext;
    if (context != null && i == context.getUserId()) {
      if (str != null && str.equals(this.mDefaultDialerPackage)) {
        return 11;
      }
      if (str != null && str.equals(this.mDefaultSmsPackage)) {
        return 11;
      }
      if (str != null && str.equals(this.mDefaultHomePackage)) {
        return 11;
      }
    }
    ArrayMap arrayMap = this.mDefaultIMEList;
    return (arrayMap == null
            || (str2 = (String) arrayMap.get(Integer.valueOf(i))) == null
            || !str2.equals(str))
        ? 0
        : 11;
  }

  public final void getDefaultDialerPackage() {
    this.mDefaultDialerPackage = DefaultDialerManager.getDefaultDialerApplication(this.mContext);
  }

  public final void getDefaultSmsPackage() {
    ComponentName defaultSmsApplication =
        SmsApplication.getDefaultSmsApplication(this.mContext, true);
    this.mDefaultSmsPackage =
        defaultSmsApplication != null ? defaultSmsApplication.getPackageName() : null;
  }

  public final void setDefaultHomePackage() {
    ComponentName homeActivities =
        this.mContext.getPackageManager().getHomeActivities(new ArrayList());
    this.mDefaultHomePackage = homeActivities != null ? homeActivities.getPackageName() : null;
  }

  public String getDefaultHomePackage() {
    return this.mDefaultHomePackage;
  }

  public final void getDefaultIMEPackage(int i) {
    ArrayMap arrayMap;
    if (i < 0) {
      Slog.e(this.TAG, "getDefaultIMEPackage called with invalid userId : " + i);
      return;
    }
    String stringForUser =
        Settings.Secure.getStringForUser(
            this.mContext.getContentResolver(), "default_input_method", i);
    if (stringForUser != null && stringForUser.contains("/")) {
      String[] split = stringForUser.split("/");
      if (split[0].length() > 0) {
        stringForUser = split[0];
      }
    }
    if (stringForUser == null || (arrayMap = this.mDefaultIMEList) == null) {
      return;
    }
    String str = (String) arrayMap.get(Integer.valueOf(i));
    if (str == null) {
      this.mDefaultIMEList.put(Integer.valueOf(i), stringForUser);
    } else if (!str.equals(stringForUser)) {
      this.mDefaultIMEList.put(Integer.valueOf(i), stringForUser);
    }
    MARsPolicyManager.getInstance().cancelDisablePolicy(stringForUser, i, 0);
  }

  public final void registerDefaultIMEContentObserver() {
    if (this.mInputMethodObserverRegistered) {
      return;
    }
    this.mInputMethodObserver =
        new ContentObserver(
            new Handler()) { // from class:
                             // com.android.server.am.mars.filter.filter.DefaultAppFilter.1
          @Override // android.database.ContentObserver
          public void onChange(boolean z, Uri uri) {
            if (MARsDebugConfig.DEBUG_FILTER) {
              Slog.d(DefaultAppFilter.this.TAG, "onChange - DEFAULT_INPUT_METHOD!");
            }
            DefaultAppFilter defaultAppFilter = DefaultAppFilter.this;
            defaultAppFilter.getDefaultIMEPackage(defaultAppFilter.mContext.getUserId());
            if (DefaultAppFilter.this.mContext.getUserId() != 0
                || !DefaultAppFilter.this.mSecureFolderEnabled
                || DefaultAppFilter.this.mSecureFolderUserId < 150
                || DefaultAppFilter.this.mSecureFolderUserId > 160) {
              return;
            }
            DefaultAppFilter defaultAppFilter2 = DefaultAppFilter.this;
            defaultAppFilter2.getDefaultIMEPackage(defaultAppFilter2.mSecureFolderUserId);
          }
        };
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Settings.Secure.getUriFor("default_input_method"),
            false,
            this.mInputMethodObserver,
            this.mContext.getUserId());
    this.mInputMethodObserverRegistered = true;
  }

  public final void unregisterDefaultIMEContentObserver() {
    try {
      if (this.mInputMethodObserverRegistered) {
        this.mContext.getContentResolver().unregisterContentObserver(this.mInputMethodObserver);
        this.mInputMethodObserverRegistered = false;
      }
    } catch (IllegalArgumentException unused) {
      Slog.e(
          this.TAG, "IllegalArgumentException occurred in unregisterDefaultIMEContentObserver()");
    }
  }

  public void registerDefaultAppChangedReceiver(int i) {
    if (this.mDefaultAppChangedReceiverRegistered) {
      return;
    }
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.telecom.action.DEFAULT_DIALER_CHANGED");
    intentFilter.addAction("android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL");
    intentFilter.addAction("android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED");
    DefaultAppChangedReceiver defaultAppChangedReceiver = new DefaultAppChangedReceiver();
    this.mDefaultAppChangedReceiver = defaultAppChangedReceiver;
    this.mContext.registerReceiverAsUser(
        defaultAppChangedReceiver, UserHandle.of(i), intentFilter, null, null);
    this.mDefaultAppChangedReceiverRegistered = true;
  }

  public void unregisterDefaultAppChangedReceiver() {
    if (this.mDefaultAppChangedReceiverRegistered) {
      try {
        this.mContext.unregisterReceiver(this.mDefaultAppChangedReceiver);
        this.mDefaultAppChangedReceiver = null;
        this.mDefaultAppChangedReceiverRegistered = false;
      } catch (IllegalArgumentException unused) {
        Slog.e(
            this.TAG, "IllegalArgumentException occurred in unregisterDefaultAppChangedReceiver()");
      }
    }
  }

  public class DefaultAppChangedReceiver extends BroadcastReceiver {
    public DefaultAppChangedReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      if (intent == null || intent.getAction() == null) {
        return;
      }
      String action = intent.getAction();
      if ("android.telecom.action.DEFAULT_DIALER_CHANGED".equals(action)) {
        DefaultAppFilter.this.mDefaultDialerPackage =
            intent.getStringExtra("android.telecom.extra.CHANGE_DEFAULT_DIALER_PACKAGE_NAME");
        return;
      }
      if ("android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL".equals(action)) {
        ComponentName defaultSmsApplicationAsUser =
            SmsApplication.getDefaultSmsApplicationAsUser(context, false, (UserHandle) null);
        DefaultAppFilter.this.mDefaultSmsPackage =
            defaultSmsApplicationAsUser != null
                ? defaultSmsApplicationAsUser.getPackageName()
                : null;
      } else if ("android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED".equals(action)) {
        DefaultAppFilter.this.setDefaultHomePackage();
      }
    }
  }
}
