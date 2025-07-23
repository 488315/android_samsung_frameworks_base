package com.android.internal.app;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.ActivityOptions;
import android.app.AppLockCoreState;
import android.app.IUserSwitchObserver;
import android.app.WindowConfiguration;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.IRemoteCallback;
import android.os.RemoteException;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.R;
import com.samsung.android.rune.CoreRune;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class AppLockPolicy {
    public static final String ACTIVE_LOCKED_PACKAGES = "applock_locked_packages";
    private static final String APPLOCK_ENABLED = "app_lock_enabled";
    public static final int BINDER_ARRAY_DISPLAYID = 0;
    public static final int BINDER_ARRAY_EXCEPTIONLIST = 2;
    public static final int BINDER_ARRAY_LOCKED = 0;
    public static final int BINDER_ARRAY_MULTIWINDOW = 0;
    public static final int BINDER_ARRAY_NOTIFICATION = 1;
    public static final int BINDER_ARRAY_VERIFYING = 1;
    private static final String BIOMETRICS_PASSWORD_TYPE = "biometrics_password_type";
    private static final String BIOMETRICS_PATTERN_TYPE = "biometrics_pattern_type";
    private static final String BIOMETRICS_PINCODE_TYPE = "biometrics_pincode_type";
    private static final String BIOMETRICS_TYPE = "biometrics_type";
    private static final String CHECK_APPLOCK_BIOMETRICS_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_BIOMETRICS";
    private static final String CHECK_APPLOCK_FACE_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_FACE";
    private static final String CHECK_APPLOCK_FACE_SPASS_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_FACE_SPASS";
    private static final String CHECK_APPLOCK_FINGERPRINT_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_FINGERPRINT";
    private static final String CHECK_APPLOCK_FINGERPRINT_PASSWORD_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_FINGERPRINT_PASSWORD";
    private static final String CHECK_APPLOCK_FINGERPRINT_PATTERN_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_FINGERPRINT_PATTERN";
    private static final String CHECK_APPLOCK_FINGERPRINT_PINCODE_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_FINGERPRINT_PINCODE";
    private static final String CHECK_APPLOCK_IRISES_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_IRISES";
    private static final String CHECK_APPLOCK_PASSWORD_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_PASSWORD";
    private static final String CHECK_APPLOCK_PASSWORD_BIOMETRICS_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_PASSWORD_BIOMETRICS";
    private static final String CHECK_APPLOCK_PASSWORD_FACE_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_PASSWORD_FACE";
    private static final String CHECK_APPLOCK_PASSWORD_FACE_SPASS_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_PASSWORD_FACE_SPASS";
    private static final String CHECK_APPLOCK_PASSWORD_IRISES_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_PASSWORD_IRISES";
    private static final String CHECK_APPLOCK_PATTERN_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_PATTERN";
    private static final String CHECK_APPLOCK_PATTERN_BIOMETRICS_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_PATTERN_BIOMETRICS";
    private static final String CHECK_APPLOCK_PATTERN_FACE_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_PATTERN_FACE";
    private static final String CHECK_APPLOCK_PATTERN_FACE_SPASS_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_PATTERN_FACE_SPASS";
    private static final String CHECK_APPLOCK_PATTERN_IRISES_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_PATTERN_IRISES";
    private static final String CHECK_APPLOCK_PINCODE_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_PINCODE";
    private static final String CHECK_APPLOCK_PINCODE_BIOMETRICS_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_PINCODE_BIOMETRICS";
    private static final String CHECK_APPLOCK_PINCODE_FACE_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_PINCODE_FACE";
    private static final String CHECK_APPLOCK_PINCODE_FACE_SPASS_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_PINCODE_FACE_SPASS";
    private static final String CHECK_APPLOCK_PINCODE_IRISES_ACTION = "com.samsung.android.intent.action.CHECK_APPLOCK_PINCODE_IRISES";
    private static final String FACE_PASSWORD_TYPE = "face_password_type";
    private static final String FACE_PATTERN_TYPE = "face_pattern_type";
    private static final String FACE_PINCODE_TYPE = "face_pincode_type";
    private static final String FACE_SPASS_PASSWORD_TYPE = "face_spass_password_type";
    private static final String FACE_SPASS_PATTERN_TYPE = "face_spass_pattern_type";
    private static final String FACE_SPASS_PINCODE_TYPE = "face_spass_pincode_type";
    private static final String FACE_SPASS_TYPE = "face_spass_type";
    private static final String FACE_TYPE = "face_type";
    private static final String FINGERPRINT_PASSWORD_TYPE = "fingerprint_password_type";
    private static final String FINGERPRINT_PATTERN_TYPE = "fingerprint_pattern_type";
    private static final String FINGERPRINT_PINCODE_TYPE = "fingerprint_pincode_type";
    private static final String FINGERPRINT_TYPE = "fingerprint_type";
    public static final String FLOATING_MESSAGE_REQUEST = "FLOATING_MESSAGE_REQUEST";
    private static final String HIDDEN_PACKAGE = "ssecure_hidden_apps_packages";
    private static final String IRIS_PASSWORD_TYPE = "iris_password_type";
    private static final String IRIS_PATTERN_TYPE = "iris_pattern_type";
    private static final String IRIS_PINCODE_TYPE = "iris_pincode_type";
    private static final String IRIS_TYPE = "iris_type";
    public static final String LAUNCHER_REQUEST = "LAUNCHER_REQUEST";
    public static final String LAUNCH_FROM_NOTIFICATION = "LAUNCH_FROM_NOTIFICATION";
    public static final String LAUNCH_FROM_RESUME = "LAUNCH_FROM_RESUME";
    public static final String LAUNCH_FROM_SETTINGS = "APPLOCK_APPS_FROM_SETTINGS";
    public static final String LAUNCH_FROM_WECHAT_HUN = "nofification_type";
    public static final String LOCKED_APP_CALLING_UID = "LOCKED_APP_CALLING_UID";
    public static final String LOCKED_APP_CAN_SHOW_WHEN_LOCKED = "LOCKED_APP_CAN_SHOW_WHEN_LOCKED";
    private static final String LOCKED_CLASSES = "applock_locked_apps_classes";
    private static final String LOCKED_PACKAGE = "applock_locked_apps_packages";
    public static final String LOCKED_PACKAGE_ACTIVITY_OPTIONS = "LOCKED_PACKAGE_ACTIVITY_OPTIONS";
    public static final String LOCKED_PACKAGE_DISPLAYID = "LOCKED_PACKAGE_DISPLAYID";
    public static final String LOCKED_PACKAGE_ICON = "LOCKED_PACKAGE_ICON";
    public static final String LOCKED_PACKAGE_INTENT = "LOCKED_PACKAGE_INTENT";
    public static final String LOCKED_PACKAGE_LABEL = "LOCKED_PACKAGE_LABEL";
    public static final String LOCKED_PACKAGE_MULTIWINDOWSTYLE = "LOCKED_PACKAGE_MULTIWINDOWSTYLE";
    public static final String LOCKED_PACKAGE_NAME = "LOCKED_PACKAGE_NAME";
    public static final String LOCKED_PACKAGE_USERID = "LOCKED_PACKAGE_USERID";
    public static final String LOCKED_PACKAGE_WINDOW_ATTRIBUTES = "LOCKED_PACKAGE_WINDOW_ATTRIBUTES";
    private static final String LOCKED_TYPE = "applock_lock_type";
    private static final String PACKAGE_NAME_CONTACTS = "com.samsung.android.contacts";
    private static final String PASSWORD_TYPE = "password_type";
    private static final String PATTERN_TYPE = "pattern_type";
    private static final String PINCODE_TYPE = "pincode_type";
    public static final String REQUEST_VERIFY_FROM = "REQUEST_VERIFY_FROM";
    public static final String START_SERVICE_WITH_NO_ANIMATION = "START_SERVICE_WITH_NO_ANIMATION";
    private static final String TAG = "AppLockPolicy";
    private static volatile AppLockPolicy mInstance = null;
    private static boolean mIsAppLockEnabled = false;
    public AppLockCoreState mAppLockSharedPref;
    private Context mContext;
    private UserManager mUserManager;
    private Object mAppLockedLock = new Object();
    private String mLockedType = null;
    private int mLockedTypeInt = 0;
    private ArrayList<String> mAppLockedPackageList = new ArrayList<>();
    private ArrayList<String> mAppLockedClassList = new ArrayList<>();
    private ArrayList<String> mAppLockedHasUnLockedPackageList = new ArrayList<>();
    private ArrayList<String> mAppLockedHasUnLockedClassList = new ArrayList<>();
    private ArrayList<String> mAppLockActiveLockedPackages = new ArrayList<>();
    private HashMap<String, ArrayList<String>> mAppLockedRelatedPackageMap = new HashMap<>();
    private HashMap<String, ArrayList<String>> mAppLockedRelatedClassMap = new HashMap<>();
    private ArrayList<String> mAppLockedVerifyingList = new ArrayList<>();
    private ArrayList<String> mAppLockLaunchingExcpetionList = new ArrayList<>();
    private ArrayList<String> mApplockCallingExceptionList = new ArrayList<>();
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.internal.app.AppLockPolicy.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            AppLockPolicy.this.mAppLockSharedPref.initializeSharedPreference();
            AppLockPolicy.this.updateSettings();
            AppLockPolicy.this.updateLockedApps();
        }
    };
    private String mLockedPackages = null;
    private String mLockedClasses = null;

    public static boolean isSupportSSecure() {
        return false;
    }

    public boolean isManagedProfileUserId(int i) {
        return i >= 10 && i <= 94;
    }

    public static AppLockPolicy getInstance(Context context, Handler handler) {
        if (mInstance == null) {
            synchronized (AppLockPolicy.class) {
                mInstance = new AppLockPolicy(context, handler);
            }
        }
        return mInstance;
    }

    private AppLockPolicy(Context context, Handler handler) {
        this.mContext = context;
        this.mAppLockSharedPref = new AppLockCoreState(this.mContext);
        init();
        getAppLockLaunchingExceptionList();
        getCallingExceptionList();
    }

    private void getAppLockLaunchingExceptionList() {
        this.mAppLockLaunchingExcpetionList.addAll(Arrays.asList(this.mContext.getResources().getStringArray(R.array.app_lock_exception_activity_list)));
    }

    private void getCallingExceptionList() {
        this.mApplockCallingExceptionList.addAll(Arrays.asList(this.mContext.getResources().getStringArray(R.array.app_lock_calling_bypass)));
    }

    public boolean isActivityInExceptionList(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Iterator<String> it = this.mAppLockLaunchingExcpetionList.iterator();
        while (it.hasNext()) {
            if (str.equals(it.next())) {
                return true;
            }
        }
        Log.d(TAG, "isActivityInExceptionList: ");
        return false;
    }

    public boolean isAppLockBypassList(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Iterator<String> it = this.mApplockCallingExceptionList.iterator();
        while (it.hasNext()) {
            if (str.equals(it.next())) {
                return true;
            }
        }
        return false;
    }

    public String getAppLockedLockType() {
        return this.mLockedType;
    }

    public String getAppLockedCheckAction() {
        String str;
        if (PATTERN_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_PATTERN_ACTION;
        } else if (PASSWORD_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_PASSWORD_ACTION;
        } else if (PINCODE_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_PINCODE_ACTION;
        } else if (FINGERPRINT_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_FINGERPRINT_ACTION;
        } else if (FINGERPRINT_PATTERN_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_FINGERPRINT_PATTERN_ACTION;
        } else if (FINGERPRINT_PINCODE_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_FINGERPRINT_PINCODE_ACTION;
        } else if (FINGERPRINT_PASSWORD_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_FINGERPRINT_PASSWORD_ACTION;
        } else if (IRIS_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_IRISES_ACTION;
        } else if (IRIS_PATTERN_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_PATTERN_IRISES_ACTION;
        } else if (IRIS_PINCODE_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_PINCODE_IRISES_ACTION;
        } else if (IRIS_PASSWORD_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_PASSWORD_IRISES_ACTION;
        } else if (BIOMETRICS_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_BIOMETRICS_ACTION;
        } else if (BIOMETRICS_PATTERN_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_PATTERN_BIOMETRICS_ACTION;
        } else if (BIOMETRICS_PINCODE_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_PINCODE_BIOMETRICS_ACTION;
        } else if (BIOMETRICS_PASSWORD_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_PASSWORD_BIOMETRICS_ACTION;
        } else if (FACE_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_FACE_ACTION;
        } else if (FACE_PATTERN_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_PATTERN_FACE_ACTION;
        } else if (FACE_PINCODE_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_PINCODE_FACE_ACTION;
        } else if (FACE_PASSWORD_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_PASSWORD_FACE_ACTION;
        } else if (FACE_SPASS_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_FACE_SPASS_ACTION;
        } else if (FACE_SPASS_PATTERN_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_PATTERN_FACE_SPASS_ACTION;
        } else if (FACE_SPASS_PINCODE_TYPE.equals(this.mLockedType)) {
            str = CHECK_APPLOCK_PINCODE_FACE_SPASS_ACTION;
        } else {
            str = FACE_SPASS_PASSWORD_TYPE.equals(this.mLockedType) ? CHECK_APPLOCK_PASSWORD_FACE_SPASS_ACTION : null;
        }
        Log.d(TAG, "getAppLockedCheckAction:" + str);
        return str;
    }

    public ArrayList<String> getAppLockedPackageList() {
        return new ArrayList<>(this.mAppLockedPackageList);
    }

    public void setApplockLockedAppsPackage(String str) {
        this.mAppLockSharedPref.setApplockLockedAppsPackage(str);
        updateSettings();
        updateLockedApps();
    }

    public void setApplockLockedAppsClass(String str) {
        this.mAppLockSharedPref.setApplockLockedAppsClass(str);
        updateSettings();
        updateLockedApps();
    }

    public void setApplockType(int i) {
        this.mAppLockSharedPref.setApplockType(i);
        updateSettings();
        updateLockedApps();
    }

    public void setApplockEnabled(boolean z) {
        this.mAppLockSharedPref.setApplockEnabled(z);
        updateSettings();
        updateLockedApps();
    }

    public void setSsecureHiddenAppsPackages(String str) {
        this.mAppLockSharedPref.setSsecureHiddenAppsPackages(str);
        updateSettings();
        updateLockedApps();
    }

    public String getApplockLockedAppsPackage() {
        return this.mAppLockSharedPref.getApplockLockedAppsPackage();
    }

    public String getApplockLockedAppsClass() {
        return this.mAppLockSharedPref.getApplockLockedAppsClass();
    }

    public int getApplockType() {
        return this.mAppLockSharedPref.getApplockType();
    }

    public boolean isApplockEnabled() {
        return this.mAppLockSharedPref.isApplockEnabled();
    }

    public String getSsecureHiddenAppsPackages() {
        return this.mAppLockSharedPref.getSsecureHiddenAppsPackages();
    }

    public void setAppLockedUnLockPackage(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        synchronized (this.mAppLockedLock) {
            if (!this.mAppLockedHasUnLockedPackageList.contains(str)) {
                this.mAppLockedHasUnLockedPackageList.add(str);
                this.mAppLockActiveLockedPackages.remove(str);
                Settings.Secure.putString(this.mContext.getContentResolver(), "applock_locked_packages", this.mAppLockActiveLockedPackages.toString());
                if (this.mAppLockedRelatedPackageMap.containsKey(str)) {
                    for (String str2 : this.mAppLockedRelatedPackageMap.get(str)) {
                        if (!this.mAppLockedHasUnLockedPackageList.contains(str2)) {
                            this.mAppLockedHasUnLockedPackageList.add(str2);
                            this.mAppLockActiveLockedPackages.remove(str2);
                            Settings.Secure.putString(this.mContext.getContentResolver(), "applock_locked_packages", this.mAppLockActiveLockedPackages.toString());
                        }
                    }
                }
            }
        }
    }

    public void clearAppLockedUnLockedApp() {
        synchronized (this.mAppLockedLock) {
            this.mAppLockedHasUnLockedPackageList.clear();
            this.mAppLockedHasUnLockedClassList.clear();
            this.mAppLockedVerifyingList.clear();
            this.mAppLockActiveLockedPackages.clear();
            Iterator<String> it = this.mAppLockedPackageList.iterator();
            while (it.hasNext()) {
                this.mAppLockActiveLockedPackages.add(new String(it.next()));
            }
            Settings.Secure.putString(this.mContext.getContentResolver(), "applock_locked_packages", this.mAppLockActiveLockedPackages.toString());
        }
    }

    public boolean isAppLockedPackage(String str) {
        if (isSupportSSecure() && !mIsAppLockEnabled) {
            return false;
        }
        synchronized (this.mAppLockedLock) {
            if (this.mAppLockedHasUnLockedPackageList.contains(str)) {
                return false;
            }
            return (str == null || str.isEmpty() || !this.mAppLockedPackageList.contains(str)) ? false : true;
        }
    }

    public ArrayList<String> getAppLockedClassList() {
        ArrayList<String> arrayList = new ArrayList<>(this.mAppLockedClassList);
        synchronized (this.mAppLockedLock) {
            Iterator<String> it = this.mAppLockedHasUnLockedClassList.iterator();
            while (it.hasNext()) {
                arrayList.remove(it.next());
            }
        }
        return arrayList;
    }

    public void setAppLockedUnLockClass(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        synchronized (this.mAppLockedLock) {
            if (!this.mAppLockedHasUnLockedClassList.contains(str)) {
                this.mAppLockedHasUnLockedClassList.add(str);
                if (this.mAppLockedRelatedClassMap.containsKey(str)) {
                    for (String str2 : this.mAppLockedRelatedClassMap.get(str)) {
                        if (!this.mAppLockedHasUnLockedClassList.contains(str2)) {
                            this.mAppLockedHasUnLockedClassList.add(str2);
                        }
                    }
                }
            }
        }
    }

    public boolean isAppLockedClass(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        synchronized (this.mAppLockedLock) {
            if (this.mAppLockedHasUnLockedClassList.contains(str)) {
                return false;
            }
            return this.mAppLockedClassList.contains(str);
        }
    }

    public void setAppLockedVerifying(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        synchronized (this.mAppLockedLock) {
            if (z) {
                if (!this.mAppLockedVerifyingList.contains(str)) {
                    this.mAppLockedVerifyingList.add(str);
                }
            } else if (this.mAppLockedVerifyingList.contains(str)) {
                this.mAppLockedVerifyingList.remove(str);
            }
        }
    }

    public boolean isAppLockedVerifying(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        synchronized (this.mAppLockedLock) {
            return this.mAppLockedVerifyingList.contains(str);
        }
    }

    private void init() {
        try {
            ActivityManagerNative.getDefault().registerUserSwitchObserver(new IUserSwitchObserver.Stub() { // from class: com.android.internal.app.AppLockPolicy.1
                @Override // android.app.IUserSwitchObserver
                public void onBeforeUserSwitching(int i, IRemoteCallback iRemoteCallback) {
                }

                @Override // android.app.IUserSwitchObserver
                public void onForegroundProfileSwitch(int i) {
                }

                @Override // android.app.IUserSwitchObserver
                public void onLockedBootComplete(int i) {
                }

                @Override // android.app.IUserSwitchObserver
                public void onUserSwitching(int i, IRemoteCallback iRemoteCallback) {
                }

                @Override // android.app.IUserSwitchObserver
                public void onUserSwitchComplete(int i) throws RemoteException {
                    Log.d(AppLockPolicy.TAG, "onUserSwitchComplete getLockedApps");
                    AppLockPolicy.this.updateLockedApps();
                }
            }, AppLockPolicy.class.getName());
        } catch (Exception e) {
            Log.d(TAG, "onUserSwitch, observe()", e);
        }
        this.mContext.registerReceiver(this.mReceiver, new IntentFilter(Intent.ACTION_USER_UNLOCKED));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLockedApps() {
        switch (this.mLockedTypeInt) {
            case 1:
                this.mLockedType = PATTERN_TYPE;
                break;
            case 2:
                this.mLockedType = PINCODE_TYPE;
                break;
            case 3:
                this.mLockedType = PASSWORD_TYPE;
                break;
            case 4:
                this.mLockedType = FINGERPRINT_TYPE;
                break;
            case 5:
                this.mLockedType = FINGERPRINT_PATTERN_TYPE;
                break;
            case 6:
                this.mLockedType = FINGERPRINT_PINCODE_TYPE;
                break;
            case 7:
                this.mLockedType = FINGERPRINT_PASSWORD_TYPE;
                break;
            case 8:
                this.mLockedType = IRIS_TYPE;
                break;
            case 9:
                this.mLockedType = IRIS_PATTERN_TYPE;
                break;
            case 10:
                this.mLockedType = IRIS_PINCODE_TYPE;
                break;
            case 11:
                this.mLockedType = IRIS_PASSWORD_TYPE;
                break;
            case 12:
                this.mLockedType = BIOMETRICS_TYPE;
                break;
            case 13:
                this.mLockedType = BIOMETRICS_PATTERN_TYPE;
                break;
            case 14:
                this.mLockedType = BIOMETRICS_PINCODE_TYPE;
                break;
            case 15:
                this.mLockedType = BIOMETRICS_PASSWORD_TYPE;
                break;
            case 16:
                this.mLockedType = FACE_TYPE;
                break;
            case 17:
                this.mLockedType = FACE_PATTERN_TYPE;
                break;
            case 18:
                this.mLockedType = FACE_PINCODE_TYPE;
                break;
            case 19:
                this.mLockedType = FACE_PASSWORD_TYPE;
                break;
            case 20:
                this.mLockedType = FACE_SPASS_TYPE;
                break;
            case 21:
                this.mLockedType = FACE_SPASS_PATTERN_TYPE;
                break;
            case 22:
                this.mLockedType = FACE_SPASS_PINCODE_TYPE;
                break;
            case 23:
                this.mLockedType = FACE_SPASS_PASSWORD_TYPE;
                break;
            default:
                this.mLockedType = null;
                break;
        }
        synchronized (this.mAppLockedLock) {
            String str = this.mLockedPackages;
            if (str != null) {
                String[] split = str.split(",");
                ArrayList<String> arrayList = new ArrayList<>();
                for (String str2 : split) {
                    arrayList.add(str2);
                    if (this.mAppLockedRelatedPackageMap.containsKey(str2)) {
                        for (String str3 : this.mAppLockedRelatedPackageMap.get(str2)) {
                            if (!arrayList.contains(str3)) {
                                arrayList.add(str3);
                            }
                        }
                    }
                }
                this.mAppLockedPackageList = arrayList;
                this.mAppLockActiveLockedPackages.clear();
                Iterator<String> it = this.mAppLockedPackageList.iterator();
                while (it.hasNext()) {
                    this.mAppLockActiveLockedPackages.add(new String(it.next()));
                }
                Settings.Secure.putString(this.mContext.getContentResolver(), "applock_locked_packages", this.mAppLockActiveLockedPackages.toString());
            }
            String str4 = this.mLockedClasses;
            if (str4 != null) {
                String[] split2 = str4.split(",");
                ArrayList<String> arrayList2 = new ArrayList<>();
                for (String str5 : split2) {
                    arrayList2.add(str5);
                    if (this.mAppLockedRelatedClassMap.containsKey(str5)) {
                        for (String str6 : this.mAppLockedRelatedClassMap.get(str5)) {
                            if (!arrayList2.contains(str6)) {
                                arrayList2.add(str6);
                            }
                        }
                    }
                }
                this.mAppLockedClassList = arrayList2;
            }
        }
    }

    public void updateSettings() {
        this.mLockedPackages = this.mAppLockSharedPref.getApplockLockedAppsPackage();
        this.mLockedClasses = this.mAppLockSharedPref.getApplockLockedAppsClass();
        this.mLockedTypeInt = this.mAppLockSharedPref.getApplockType();
        mIsAppLockEnabled = this.mAppLockSharedPref.isApplockEnabled();
    }

    public boolean dumpAppLockPolicyLocked(FileDescriptor fileDescriptor, PrintWriter printWriter) {
        printWriter.print("AppLockPolicy dump start");
        printWriter.println();
        StringBuilder sb = new StringBuilder();
        sb.append("LockedPackage[");
        Iterator<String> it = this.mAppLockedPackageList.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append(",");
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]\n");
        sb.append("LockedClass[");
        Iterator<String> it2 = this.mAppLockedClassList.iterator();
        while (it2.hasNext()) {
            sb.append(it2.next());
            sb.append(",");
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]\n");
        sb.append("HasUnLockedPackage[");
        Iterator<String> it3 = this.mAppLockedHasUnLockedPackageList.iterator();
        while (it3.hasNext()) {
            sb.append(it3.next());
            sb.append(",");
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]\n");
        sb.append("HasUnLockedClass[");
        Iterator<String> it4 = this.mAppLockedHasUnLockedClassList.iterator();
        while (it4.hasNext()) {
            sb.append(it4.next());
            sb.append(",");
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]\n");
        sb.append("mAppLockedVerifyingList[");
        Iterator<String> it5 = this.mAppLockedVerifyingList.iterator();
        while (it5.hasNext()) {
            sb.append(it5.next());
            sb.append(",");
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]\n");
        printWriter.print(sb.toString());
        printWriter.print("AppLockPolicy dump end");
        printWriter.println();
        return true;
    }

    public static boolean isSupportAppLock() {
        return CoreRune.FW_APPLOCK;
    }

    public static boolean skipLockWhenStart(Context context, String str, Intent intent, ActivityOptions activityOptions, String str2) {
        if (CoreRune.FW_APPLOCK && isSupportSSecure()) {
            Log.d(TAG, "intent is starting with S secure, skip");
            return true;
        }
        if (activityOptions != null && (WindowConfiguration.inMultiWindowMode(activityOptions.getLaunchWindowingMode()) || WindowConfiguration.inMultiWindowMode(activityOptions.getForceLaunchWindowingMode()))) {
            Log.d(TAG, "intent is starting in multi WindowingMode, skip");
            return true;
        }
        if (intent.hasExtra(LAUNCH_FROM_WECHAT_HUN)) {
            Log.d(TAG, "starting from WeChat HeadsUp Notification");
            return true;
        }
        Iterator<ActivityManager.RunningTaskInfo> it = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(7).iterator();
        while (it.hasNext()) {
            if (it.next().configuration.windowConfiguration.getWindowingMode() != 1) {
                Log.d(TAG, "hasMultiWindowRunning, skip");
                return true;
            }
        }
        return false;
    }

    private static boolean fileUriMayExposed(Uri uri) {
        return (uri == null || !"file".equals(uri.getScheme()) || uri.getPath().startsWith("/system/")) ? false : true;
    }

    private UserManager getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = UserManager.get(this.mContext);
        }
        return this.mUserManager;
    }
}
