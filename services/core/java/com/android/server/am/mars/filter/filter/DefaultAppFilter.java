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
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.IFilter;
import com.samsung.android.knox.SemPersonaManager;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DefaultAppFilter implements IFilter {
    public int mSecureFolderUserId;
    public final ArrayMap mDefaultIMEList = new ArrayMap();
    public String mDefaultDialerPackage = null;
    public String mDefaultSmsPackage = null;
    public String mDefaultHomePackage = null;
    public Context mContext = null;
    public boolean mSecureFolderEnabled = false;
    public AnonymousClass1 mInputMethodObserver = null;
    public boolean mInputMethodObserverRegistered = false;
    public DefaultAppChangedReceiver mDefaultAppChangedReceiver = null;
    public boolean mDefaultAppChangedReceiverRegistered = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DefaultAppChangedReceiver extends BroadcastReceiver {
        public DefaultAppChangedReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                return;
            }
            String action = intent.getAction();
            if ("android.telecom.action.DEFAULT_DIALER_CHANGED".equals(action)) {
                DefaultAppFilter.this.mDefaultDialerPackage = intent.getStringExtra("android.telecom.extra.CHANGE_DEFAULT_DIALER_PACKAGE_NAME");
                return;
            }
            if ("android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL".equals(action)) {
                ComponentName defaultSmsApplicationAsUser = SmsApplication.getDefaultSmsApplicationAsUser(context, false, (UserHandle) null);
                DefaultAppFilter.this.mDefaultSmsPackage = defaultSmsApplicationAsUser != null ? defaultSmsApplicationAsUser.getPackageName() : null;
            } else if ("android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED".equals(action)) {
                DefaultAppFilter defaultAppFilter = DefaultAppFilter.this;
                ComponentName homeActivities = defaultAppFilter.mContext.getPackageManager().getHomeActivities(new ArrayList());
                defaultAppFilter.mDefaultHomePackage = homeActivities != null ? homeActivities.getPackageName() : null;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class DefaultAppFilterHolder {
        public static final DefaultAppFilter INSTANCE = new DefaultAppFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
        try {
            if (this.mInputMethodObserverRegistered) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mInputMethodObserver);
                this.mInputMethodObserverRegistered = false;
            }
        } catch (IllegalArgumentException unused) {
            Slog.e("MARs:DefaultAppFilter", "IllegalArgumentException occurred in unregisterDefaultIMEContentObserver()");
        }
        if (this.mDefaultAppChangedReceiverRegistered) {
            try {
                this.mContext.unregisterReceiver(this.mDefaultAppChangedReceiver);
                this.mDefaultAppChangedReceiver = null;
                this.mDefaultAppChangedReceiverRegistered = false;
            } catch (IllegalArgumentException unused2) {
                Slog.e("MARs:DefaultAppFilter", "IllegalArgumentException occurred in unregisterDefaultAppChangedReceiver()");
            }
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
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
        if (this.mDefaultIMEList != null) {
            boolean z = MARsPolicyManager.MARs_ENABLE;
            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
            if ((!MARsPolicyManager.isChinaModel || MARsUtils.getScreenOnState()) && (str2 = (String) this.mDefaultIMEList.get(Integer.valueOf(i))) != null && str2.equals(str)) {
                return 11;
            }
        }
        return 0;
    }

    public final void getDefaultIMEPackage(int i) {
        ArrayMap arrayMap;
        if (i < 0) {
            NandswapManager$$ExternalSyntheticOutline0.m(i, "getDefaultIMEPackage called with invalid userId : ", "MARs:DefaultAppFilter");
            return;
        }
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "default_input_method", i);
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
        boolean z = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
        boolean z2 = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(stringForUser, i, 0);
    }

    /* JADX WARN: Type inference failed for: r0v15, types: [com.android.server.am.mars.filter.filter.DefaultAppFilter$1] */
    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        this.mContext = context;
        int userId = context.getUserId();
        this.mDefaultDialerPackage = DefaultDialerManager.getDefaultDialerApplication(this.mContext);
        ComponentName defaultSmsApplication = SmsApplication.getDefaultSmsApplication(this.mContext, true);
        this.mDefaultSmsPackage = defaultSmsApplication != null ? defaultSmsApplication.getPackageName() : null;
        getDefaultIMEPackage(userId);
        ComponentName homeActivities = this.mContext.getPackageManager().getHomeActivities(new ArrayList());
        this.mDefaultHomePackage = homeActivities != null ? homeActivities.getPackageName() : null;
        SemPersonaManager semPersonaManager = (SemPersonaManager) this.mContext.getSystemService("persona");
        if (semPersonaManager != null) {
            int knoxId = semPersonaManager.getKnoxId(2, true);
            this.mSecureFolderUserId = knoxId;
            if (userId != 0 || knoxId < 150 || knoxId > 160) {
                this.mSecureFolderEnabled = false;
            } else {
                this.mSecureFolderEnabled = true;
                getDefaultIMEPackage(knoxId);
            }
        }
        if (!this.mInputMethodObserverRegistered) {
            this.mInputMethodObserver = new ContentObserver(new Handler()) { // from class: com.android.server.am.mars.filter.filter.DefaultAppFilter.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    int i;
                    if (MARsDebugConfig.DEBUG_FILTER) {
                        DefaultAppFilter.this.getClass();
                        Slog.d("MARs:DefaultAppFilter", "onChange - DEFAULT_INPUT_METHOD!");
                    }
                    DefaultAppFilter defaultAppFilter = DefaultAppFilter.this;
                    defaultAppFilter.getDefaultIMEPackage(defaultAppFilter.mContext.getUserId());
                    if (DefaultAppFilter.this.mContext.getUserId() == 0) {
                        DefaultAppFilter defaultAppFilter2 = DefaultAppFilter.this;
                        if (!defaultAppFilter2.mSecureFolderEnabled || (i = defaultAppFilter2.mSecureFolderUserId) < 150 || i > 160) {
                            return;
                        }
                        defaultAppFilter2.getDefaultIMEPackage(i);
                    }
                }
            };
            this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("default_input_method"), false, this.mInputMethodObserver, this.mContext.getUserId());
            this.mInputMethodObserverRegistered = true;
        }
        if (this.mDefaultAppChangedReceiverRegistered) {
            return;
        }
        IntentFilter m = GmsAlarmManager$$ExternalSyntheticOutline0.m("android.telecom.action.DEFAULT_DIALER_CHANGED", "android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL", "android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED");
        DefaultAppChangedReceiver defaultAppChangedReceiver = new DefaultAppChangedReceiver();
        this.mDefaultAppChangedReceiver = defaultAppChangedReceiver;
        this.mContext.registerReceiverAsUser(defaultAppChangedReceiver, UserHandle.of(userId), m, null, null, 2);
        this.mDefaultAppChangedReceiverRegistered = true;
    }
}
