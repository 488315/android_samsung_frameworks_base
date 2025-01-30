package com.android.internal.app;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.ActivityOptions;
import android.app.AppLockCoreState;
import android.app.IUserSwitchObserver;
import android.app.WindowConfiguration;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.p009os.Handler;
import android.p009os.IRemoteCallback;
import android.p009os.RemoteException;
import android.p009os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.C4337R;
import com.samsung.android.rune.CoreRune;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class AppLockPolicy {
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
    public static final String SETTINGS_KEY_NEW_DEX = "new_dex";
    public static final String START_SERVICE_WITH_NO_ANIMATION = "START_SERVICE_WITH_NO_ANIMATION";
    private static final String TAG = "AppLockPolicy";
    private static volatile AppLockPolicy mInstance;
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
    private HashMap<String, ArrayList<String>> mAppLockedRelatedPackageMap = new HashMap<>();
    private HashMap<String, ArrayList<String>> mAppLockedRelatedClassMap = new HashMap<>();
    private ArrayList<String> mAppLockedVerifyingList = new ArrayList<>();
    private ArrayList<String> mAppLockLaunchingExcpetionList = new ArrayList<>();
    private ArrayList<String> mApplockCallingExceptionList = new ArrayList<>();
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.internal.app.AppLockPolicy.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            AppLockPolicy.this.mAppLockSharedPref.initializeSharedPreference();
            AppLockPolicy.this.reloadFromSettings();
            AppLockPolicy.this.updateSettings();
            AppLockPolicy.this.updateLockedApps();
        }
    };
    private String mLockedPackages = null;
    private String mLockedClasses = null;

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
        this.mAppLockSharedPref = new AppLockCoreState(context);
        init();
        getAppLockLaunchingExceptionList();
        getCallingExceptionList();
    }

    private void getAppLockLaunchingExceptionList() {
        String[] activities = this.mContext.getResources().getStringArray(C4337R.array.app_lock_exception_activity_list);
        this.mAppLockLaunchingExcpetionList.addAll(Arrays.asList(activities));
    }

    private void getCallingExceptionList() {
        String[] activities = this.mContext.getResources().getStringArray(C4337R.array.app_lock_calling_bypass);
        this.mApplockCallingExceptionList.addAll(Arrays.asList(activities));
    }

    public boolean isActivityInExceptionList(String activityName) {
        if (TextUtils.isEmpty(activityName)) {
            return false;
        }
        Iterator<String> it = this.mAppLockLaunchingExcpetionList.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (activityName.equals(s)) {
                return true;
            }
        }
        Log.m94d(TAG, "isActivityInExceptionList: ");
        return false;
    }

    public boolean isAppLockBypassList(String activityName) {
        if (TextUtils.isEmpty(activityName)) {
            return false;
        }
        Iterator<String> it = this.mApplockCallingExceptionList.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (activityName.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public String getAppLockedLockType() {
        return this.mLockedType;
    }

    public String getAppLockedCheckAction() {
        String checkAction = null;
        if (PATTERN_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_PATTERN_ACTION;
        } else if (PASSWORD_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_PASSWORD_ACTION;
        } else if (PINCODE_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_PINCODE_ACTION;
        } else if (FINGERPRINT_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_FINGERPRINT_ACTION;
        } else if (FINGERPRINT_PATTERN_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_FINGERPRINT_PATTERN_ACTION;
        } else if (FINGERPRINT_PINCODE_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_FINGERPRINT_PINCODE_ACTION;
        } else if (FINGERPRINT_PASSWORD_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_FINGERPRINT_PASSWORD_ACTION;
        } else if (IRIS_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_IRISES_ACTION;
        } else if (IRIS_PATTERN_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_PATTERN_IRISES_ACTION;
        } else if (IRIS_PINCODE_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_PINCODE_IRISES_ACTION;
        } else if (IRIS_PASSWORD_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_PASSWORD_IRISES_ACTION;
        } else if (BIOMETRICS_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_BIOMETRICS_ACTION;
        } else if (BIOMETRICS_PATTERN_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_PATTERN_BIOMETRICS_ACTION;
        } else if (BIOMETRICS_PINCODE_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_PINCODE_BIOMETRICS_ACTION;
        } else if (BIOMETRICS_PASSWORD_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_PASSWORD_BIOMETRICS_ACTION;
        } else if (FACE_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_FACE_ACTION;
        } else if (FACE_PATTERN_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_PATTERN_FACE_ACTION;
        } else if (FACE_PINCODE_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_PINCODE_FACE_ACTION;
        } else if (FACE_PASSWORD_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_PASSWORD_FACE_ACTION;
        } else if (FACE_SPASS_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_FACE_SPASS_ACTION;
        } else if (FACE_SPASS_PATTERN_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_PATTERN_FACE_SPASS_ACTION;
        } else if (FACE_SPASS_PINCODE_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_PINCODE_FACE_SPASS_ACTION;
        } else if (FACE_SPASS_PASSWORD_TYPE.equals(this.mLockedType)) {
            checkAction = CHECK_APPLOCK_PASSWORD_FACE_SPASS_ACTION;
        }
        Log.m94d(TAG, "getAppLockedCheckAction:" + checkAction);
        return checkAction;
    }

    public ArrayList<String> getAppLockedPackageList() {
        return new ArrayList<>(this.mAppLockedPackageList);
    }

    public void setApplockLockedAppsPackage(String packages) {
        this.mAppLockSharedPref.setApplockLockedAppsPackage(packages);
        updateSettings();
        updateLockedApps();
    }

    public void setApplockLockedAppsClass(String classes) {
        this.mAppLockSharedPref.setApplockLockedAppsClass(classes);
        updateSettings();
        updateLockedApps();
    }

    public void setApplockType(int type) {
        this.mAppLockSharedPref.setApplockType(type);
        updateSettings();
        updateLockedApps();
    }

    public void setApplockEnabled(boolean enable) {
        this.mAppLockSharedPref.setApplockEnabled(enable);
        updateSettings();
        updateLockedApps();
    }

    public void setSsecureHiddenAppsPackages(String packages) {
        this.mAppLockSharedPref.setSsecureHiddenAppsPackages(packages);
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

    public void setAppLockedUnLockPackage(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return;
        }
        synchronized (this.mAppLockedLock) {
            if (!this.mAppLockedHasUnLockedPackageList.contains(packageName)) {
                this.mAppLockedHasUnLockedPackageList.add(packageName);
                if (this.mAppLockedRelatedPackageMap.containsKey(packageName)) {
                    List<String> related = this.mAppLockedRelatedPackageMap.get(packageName);
                    for (String relatedPackage : related) {
                        if (!this.mAppLockedHasUnLockedPackageList.contains(relatedPackage)) {
                            this.mAppLockedHasUnLockedPackageList.add(relatedPackage);
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
        }
    }

    public boolean isAppLockedPackage(String packageName) {
        if (isSupportSSecure() && !mIsAppLockEnabled) {
            return false;
        }
        synchronized (this.mAppLockedLock) {
            if (this.mAppLockedHasUnLockedPackageList.contains(packageName)) {
                return false;
            }
            return (packageName == null || packageName.isEmpty() || !this.mAppLockedPackageList.contains(packageName)) ? false : true;
        }
    }

    public ArrayList<String> getAppLockedClassList() {
        ArrayList<String> lockedist = new ArrayList<>(this.mAppLockedClassList);
        synchronized (this.mAppLockedLock) {
            Iterator<String> it = this.mAppLockedHasUnLockedClassList.iterator();
            while (it.hasNext()) {
                String st = it.next();
                lockedist.remove(st);
            }
        }
        return lockedist;
    }

    public void setAppLockedUnLockClass(String className) {
        if (TextUtils.isEmpty(className)) {
            return;
        }
        synchronized (this.mAppLockedLock) {
            if (!this.mAppLockedHasUnLockedClassList.contains(className)) {
                this.mAppLockedHasUnLockedClassList.add(className);
                if (this.mAppLockedRelatedClassMap.containsKey(className)) {
                    List<String> related = this.mAppLockedRelatedClassMap.get(className);
                    for (String relatedClass : related) {
                        if (!this.mAppLockedHasUnLockedClassList.contains(relatedClass)) {
                            this.mAppLockedHasUnLockedClassList.add(relatedClass);
                        }
                    }
                }
            }
        }
    }

    public boolean isAppLockedClass(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        synchronized (this.mAppLockedLock) {
            if (this.mAppLockedHasUnLockedClassList.contains(packageName)) {
                return false;
            }
            return this.mAppLockedClassList.contains(packageName);
        }
    }

    public void setAppLockedVerifying(String packageName, boolean verifying) {
        if (TextUtils.isEmpty(packageName)) {
            return;
        }
        synchronized (this.mAppLockedLock) {
            if (verifying) {
                if (!this.mAppLockedVerifyingList.contains(packageName)) {
                    this.mAppLockedVerifyingList.add(packageName);
                }
            } else if (this.mAppLockedVerifyingList.contains(packageName)) {
                this.mAppLockedVerifyingList.remove(packageName);
            }
        }
    }

    public boolean isAppLockedVerifying(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        synchronized (this.mAppLockedLock) {
            return this.mAppLockedVerifyingList.contains(packageName);
        }
    }

    private void init() {
        try {
            ActivityManagerNative.getDefault().registerUserSwitchObserver(new IUserSwitchObserver.Stub() { // from class: com.android.internal.app.AppLockPolicy.1
                @Override // android.app.IUserSwitchObserver
                public void onBeforeUserSwitching(int newUserId) {
                }

                @Override // android.app.IUserSwitchObserver
                public void onUserSwitching(int newUserId, IRemoteCallback reply) {
                }

                @Override // android.app.IUserSwitchObserver
                public void onUserSwitchComplete(int newUserId) throws RemoteException {
                    Log.m94d(AppLockPolicy.TAG, "onUserSwitchComplete getLockedApps");
                    AppLockPolicy.this.updateLockedApps();
                }

                @Override // android.app.IUserSwitchObserver
                public void onForegroundProfileSwitch(int newProfileId) {
                }

                @Override // android.app.IUserSwitchObserver
                public void onLockedBootComplete(int newUserId) {
                }
            }, AppLockPolicy.class.getName());
        } catch (Exception e) {
            Log.m95d(TAG, "onUserSwitch, observe()", e);
        }
        IntentFilter filter = new IntentFilter(Intent.ACTION_USER_UNLOCKED);
        this.mContext.registerReceiver(this.mReceiver, filter);
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
                String[] lockedPackageArray = str.split(",");
                ArrayList<String> list = new ArrayList<>();
                for (String packageName : lockedPackageArray) {
                    list.add(packageName);
                    if (this.mAppLockedRelatedPackageMap.containsKey(packageName)) {
                        List<String> related = this.mAppLockedRelatedPackageMap.get(packageName);
                        for (String relatedPackage : related) {
                            if (!list.contains(relatedPackage)) {
                                list.add(relatedPackage);
                            }
                        }
                    }
                }
                this.mAppLockedPackageList = list;
            }
            String str2 = this.mLockedClasses;
            if (str2 != null) {
                String[] lockedClassArray = str2.split(",");
                ArrayList<String> list2 = new ArrayList<>();
                for (String className : lockedClassArray) {
                    list2.add(className);
                    if (this.mAppLockedRelatedClassMap.containsKey(className)) {
                        for (String relatedClass : this.mAppLockedRelatedClassMap.get(className)) {
                            if (!list2.contains(relatedClass)) {
                                list2.add(relatedClass);
                            }
                        }
                    }
                }
                this.mAppLockedClassList = list2;
            }
        }
    }

    public void reloadFromSettings() {
        ContentResolver resolver = this.mContext.getContentResolver();
        String lockedPackages = Settings.Secure.getStringForUser(resolver, "applock_locked_apps_packages", -2);
        String lockedClasses = Settings.Secure.getStringForUser(resolver, "applock_locked_apps_classes", -2);
        String hiddenPkgs = Settings.Secure.getStringForUser(resolver, "ssecure_hidden_apps_packages", -2);
        int lockedTypeInt = Settings.Secure.getIntForUser(resolver, "applock_lock_type", 0, -2);
        boolean applockEnabled = Settings.Secure.getInt(resolver, "app_lock_enabled", -2) == 1;
        String applockLockedAppsPackage = this.mAppLockSharedPref.getApplockLockedAppsPackage();
        this.mLockedPackages = applockLockedAppsPackage;
        if (lockedPackages != null && applockLockedAppsPackage.isEmpty() && lockedTypeInt != 0) {
            this.mAppLockSharedPref.setApplockLockedAppsPackage(lockedPackages);
            this.mAppLockSharedPref.setApplockLockedAppsClass(lockedClasses);
            this.mAppLockSharedPref.setApplockType(lockedTypeInt);
            this.mAppLockSharedPref.setApplockEnabled(applockEnabled);
            this.mAppLockSharedPref.setSsecureHiddenAppsPackages(hiddenPkgs);
        }
    }

    public void updateSettings() {
        this.mLockedPackages = this.mAppLockSharedPref.getApplockLockedAppsPackage();
        this.mLockedClasses = this.mAppLockSharedPref.getApplockLockedAppsClass();
        this.mLockedTypeInt = this.mAppLockSharedPref.getApplockType();
        mIsAppLockEnabled = this.mAppLockSharedPref.isApplockEnabled();
    }

    public boolean dumpAppLockPolicyLocked(FileDescriptor fd, PrintWriter pw) {
        pw.print("AppLockPolicy dump start");
        pw.println();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LockedPackage[");
        Iterator<String> it = this.mAppLockedPackageList.iterator();
        while (it.hasNext()) {
            String str = it.next();
            stringBuilder.append(str);
            stringBuilder.append(",");
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append("]\n");
        stringBuilder.append("LockedClass[");
        Iterator<String> it2 = this.mAppLockedClassList.iterator();
        while (it2.hasNext()) {
            String str2 = it2.next();
            stringBuilder.append(str2);
            stringBuilder.append(",");
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append("]\n");
        stringBuilder.append("HasUnLockedPackage[");
        Iterator<String> it3 = this.mAppLockedHasUnLockedPackageList.iterator();
        while (it3.hasNext()) {
            String str3 = it3.next();
            stringBuilder.append(str3);
            stringBuilder.append(",");
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append("]\n");
        stringBuilder.append("HasUnLockedClass[");
        Iterator<String> it4 = this.mAppLockedHasUnLockedClassList.iterator();
        while (it4.hasNext()) {
            String str4 = it4.next();
            stringBuilder.append(str4);
            stringBuilder.append(",");
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append("]\n");
        stringBuilder.append("mAppLockedVerifyingList[");
        Iterator<String> it5 = this.mAppLockedVerifyingList.iterator();
        while (it5.hasNext()) {
            String str5 = it5.next();
            stringBuilder.append(str5);
            stringBuilder.append(",");
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append("]\n");
        pw.print(stringBuilder.toString());
        pw.print("AppLockPolicy dump end");
        pw.println();
        return true;
    }

    public static boolean isSupportAppLock() {
        return CoreRune.FW_APPLOCK;
    }

    public static boolean skipLockWhenStart(Context context, String targetPackage, Intent intent, ActivityOptions options, String callingPackage) {
        boolean isNewDexMode = Settings.System.getInt(context.getContentResolver(), "new_dex", 0) == 1;
        if (CoreRune.FW_APPLOCK && isSupportSSecure()) {
            Log.m94d(TAG, "intent is starting with S secure, skip");
            return true;
        }
        if (options != null && options.getLaunchDisplayId() == 2) {
            Log.m94d(TAG, "intent is starting in dex display, skip");
            return true;
        }
        if (CoreRune.MT_NEW_DEX && isNewDexMode) {
            Log.m94d(TAG, "intent is starting in new dex display, skip");
            return true;
        }
        if (options != null && (WindowConfiguration.inMultiWindowMode(options.getLaunchWindowingMode()) || WindowConfiguration.inMultiWindowMode(options.getForceLaunchWindowingMode()))) {
            Log.m94d(TAG, "intent is starting in multi WindowingMode, skip");
            return true;
        }
        if (intent.hasExtra(LAUNCH_FROM_WECHAT_HUN)) {
            Log.m94d(TAG, "starting from WeChat HeadsUp Notification");
            return true;
        }
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        List<ActivityManager.RunningTaskInfo> runningTaskList = am.getRunningTasks(7);
        for (ActivityManager.RunningTaskInfo infor : runningTaskList) {
            if (infor.configuration.windowConfiguration.getWindowingMode() != 1) {
                Log.m94d(TAG, "hasMultiWindowRunning, skip");
                return true;
            }
        }
        return false;
    }

    private static boolean fileUriMayExposed(Uri uri) {
        if (uri != null && "file".equals(uri.getScheme()) && !uri.getPath().startsWith("/system/")) {
            return true;
        }
        return false;
    }

    private UserManager getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = UserManager.get(this.mContext);
        }
        return this.mUserManager;
    }

    public boolean isManagedProfileUserId(int userId) {
        return userId >= 10 && userId <= 94;
    }

    public static boolean isSupportSSecure() {
        return CoreRune.FW_SUPPORT_SPROTECT;
    }
}
