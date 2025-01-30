package com.samsung.android.server.continuity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper;
import com.samsung.android.server.continuity.common.Utils;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public abstract class AbstractPreconditionObserver {
    public static final int FEATURE_CONTINUITY;
    public static final String SAMSUNG_ACCOUNT_TYPE;
    public final AutoSwitchSettingHelper mAutoSwitchSettingHelper;
    public final Context mContext;
    public Account mCurrentAccount;
    public boolean mIsAddedAccountListener;
    public boolean mIsPkgObserverRegistered;
    public StateChangedListener mListener;
    public boolean mRegisterSettingsObserver;
    public int mState;
    public int mUserId = -10000;
    public final ContentObserver mSettingObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.samsung.android.server.continuity.AbstractPreconditionObserver.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            if (uri == null) {
                return;
            }
            try {
                if (AbstractPreconditionObserver.this.URI_CONTINUITY.equals(uri)) {
                    AbstractPreconditionObserver.this.onChangeSettings(3);
                } else if (AbstractPreconditionObserver.this.URI_MULTI_CONTROL.equals(uri)) {
                    AbstractPreconditionObserver.this.onChangeSettings(7);
                } else if (AbstractPreconditionObserver.this.URI_VIDEO_CALL_CONTINUITY.equals(uri)) {
                    AbstractPreconditionObserver.this.onChangeSettings(8);
                } else if (AbstractPreconditionObserver.this.URI_HW_SHARING.equals(uri)) {
                    AbstractPreconditionObserver.this.onChangeSettings(9);
                }
            } catch (NullPointerException unused) {
            }
        }
    };
    public final BroadcastReceiver mPkgReceiver = new BroadcastReceiver() { // from class: com.samsung.android.server.continuity.AbstractPreconditionObserver.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Uri data;
            String schemeSpecificPart;
            String action = intent.getAction();
            if (action == null || (data = intent.getData()) == null) {
                return;
            }
            schemeSpecificPart = data.getSchemeSpecificPart();
            switch (action) {
                case "android.intent.action.PACKAGE_REPLACED":
                    handlePackageReplaced(schemeSpecificPart);
                    break;
                case "android.intent.action.PACKAGE_RESTARTED":
                    handlePackageRestarted();
                    break;
                case "android.intent.action.PACKAGE_REMOVED":
                    handlePackageRemoved(schemeSpecificPart);
                    break;
                case "android.intent.action.PACKAGE_ADDED":
                    handlePackageAdded(schemeSpecificPart, intent.getBooleanExtra("android.intent.extra.REPLACING", false));
                    break;
            }
        }

        public final void handlePackageAdded(String str, boolean z) {
            AbstractPreconditionObserver.this.setInstalledFlag(str);
            if (z) {
                Log.i("[MCF_DS_SYS]_PreconditionObserver", "handlePackageAdded - with EXTRA_REPLACING");
                AbstractPreconditionObserver.this.notifyReplacedPackage(true);
            } else {
                Log.i("[MCF_DS_SYS]_PreconditionObserver", "handlePackageAdded - without EXTRA_REPLACING");
                AbstractPreconditionObserver.this.notifyChanged(5);
            }
        }

        public final void handlePackageReplaced(String str) {
            AbstractPreconditionObserver.this.setInstalledFlag(str);
            AbstractPreconditionObserver.this.notifyReplacedPackage(false);
        }

        public final void handlePackageRemoved(String str) {
            AbstractPreconditionObserver.this.unsetInstalledFlag(str);
            AbstractPreconditionObserver.this.notifyChanged(5);
        }

        public final void handlePackageRestarted() {
            AbstractPreconditionObserver.this.notifyRestartedPackage();
        }
    };
    public final OnAccountsUpdateListener mOnAccountsUpdateListener = new OnAccountsUpdateListener() { // from class: com.samsung.android.server.continuity.AbstractPreconditionObserver.3
        @Override // android.accounts.OnAccountsUpdateListener
        public void onAccountsUpdated(Account[] accountArr) {
            Log.d("[MCF_DS_SYS]_PreconditionObserver", "onAccountsUpdated - " + accountArr.length);
            AbstractPreconditionObserver.this.checkAccountUpdated();
        }
    };
    public final BroadcastReceiver mAccountChangeReceiver = new BroadcastReceiver() { // from class: com.samsung.android.server.continuity.AbstractPreconditionObserver.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.accounts.LOGIN_ACCOUNTS_CHANGED".equals(intent.getAction())) {
                Log.i("[MCF_DS_SYS]_PreconditionObserver", "mAccountChangeReceiver.onReceive - LOGIN_ACCOUNTS_CHANGED_ACTION");
                AbstractPreconditionObserver.this.checkAccountUpdated();
            }
        }
    };
    public final Uri URI_CONTINUITY = Settings.System.getUriFor("mcf_continuity");
    public final Uri URI_MULTI_CONTROL = Settings.System.getUriFor("multi_control_enabled");
    public final Uri URI_VIDEO_CALL_CONTINUITY = Settings.System.getUriFor("vcc_continuity");
    public final Uri URI_HW_SHARING = Settings.System.getUriFor("hwrs_service");

    public interface StateChangedListener {
        void onChanged(int i, int i2);

        void onPackageReplaced(boolean z);

        void onPackageRestarted();
    }

    public abstract Account[] getAccountsByType();

    public abstract void initContinuitySetting();

    public abstract boolean isEnableSettings(String str);

    public abstract void registerContinuityObserver();

    public abstract void registerHwSharingObserver();

    public abstract void registerMultiControlObserver();

    public abstract void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter);

    public abstract void registerVideoCallContinuityObserver();

    public abstract void setContinuitySetting(int i);

    public AbstractPreconditionObserver(Context context) {
        this.mContext = context;
        this.mAutoSwitchSettingHelper = new AutoSwitchSettingHelper(context, new Consumer() { // from class: com.samsung.android.server.continuity.AbstractPreconditionObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractPreconditionObserver.this.handleAutoSwitchModeChanged(((Boolean) obj).booleanValue());
            }
        });
    }

    public boolean meetConditions() {
        Log.i("[MCF_DS_SYS]_PreconditionObserver", "meetConditions - state : " + Integer.toHexString(this.mState));
        return this.mState == 65535;
    }

    public void start(int i, StateChangedListener stateChangedListener) {
        this.mUserId = i;
        this.mListener = stateChangedListener;
        registerPackageObserver();
        if (Utils.isPackageInstalled(this.mContext, "com.samsung.android.scloud")) {
            setFlag(240);
        }
        if (Utils.isPackageInstalled(this.mContext, "com.samsung.android.mcfds")) {
            setFlag(3840);
        }
        this.mCurrentAccount = getSamsungAccount();
        initContinuitySetting();
        updateSettings(isEnableSettings());
        registerSettingsObserver();
        addOnAccountsUpdatedListener();
        if (this.mCurrentAccount != null) {
            setFlag(15);
        }
    }

    public void stop() {
        unregisterPackageObserver();
        unregisterSettingsObserver();
        removeOnAccountsUpdatedListener();
        this.mCurrentAccount = null;
        this.mState = 0;
        this.mUserId = -10000;
    }

    public final void setInstalledFlag(String str) {
        if ("com.samsung.android.scloud".equals(str)) {
            Log.i("[MCF_DS_SYS]_PreconditionObserver", "setInstalledFlag - Scloud");
            setFlag(240);
        } else if ("com.samsung.android.mcfds".equals(str)) {
            Log.i("[MCF_DS_SYS]_PreconditionObserver", "setInstalledFlag - MCFDS");
            setFlag(3840);
        }
    }

    public final void unsetInstalledFlag(String str) {
        if ("com.samsung.android.scloud".equals(str)) {
            Log.i("[MCF_DS_SYS]_PreconditionObserver", "unsetInstalledFlag - Scloud");
            unsetFlag(240);
        } else if ("com.samsung.android.mcfds".equals(str)) {
            Log.i("[MCF_DS_SYS]_PreconditionObserver", "unsetInstalledFlag - MCFDS");
            unsetFlag(3840);
        }
    }

    public final void setFlag(int i) {
        this.mState = i | this.mState;
    }

    public final void unsetFlag(int i) {
        this.mState = (~i) & this.mState;
    }

    public final boolean isFlag(int i) {
        return (this.mState & i) == i;
    }

    public final void notifyChanged(int i) {
        StateChangedListener stateChangedListener = this.mListener;
        if (stateChangedListener != null) {
            stateChangedListener.onChanged(this.mState, i);
        }
    }

    public final void notifyReplacedPackage(boolean z) {
        StateChangedListener stateChangedListener = this.mListener;
        if (stateChangedListener != null) {
            stateChangedListener.onPackageReplaced(z);
        }
    }

    public final void notifyRestartedPackage() {
        StateChangedListener stateChangedListener = this.mListener;
        if (stateChangedListener != null) {
            stateChangedListener.onPackageRestarted();
        }
    }

    static {
        FEATURE_CONTINUITY = Utils.isWatch() ? 2 : SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_MCF_SUPPORT_CONTINUITY");
        SAMSUNG_ACCOUNT_TYPE = Utils.isWatch() ? "com.samsung.android.wearable.samsungaccount" : "com.osp.app.signin";
    }

    public static boolean isSupported() {
        return FEATURE_CONTINUITY > 0;
    }

    public static boolean isSupported(int i) {
        int i2 = FEATURE_CONTINUITY;
        return i2 > 0 && (i2 & i) == i;
    }

    public final boolean isAutoSwitchOn() {
        return this.mAutoSwitchSettingHelper.isAutoSwitchModeEnabled();
    }

    public void setAutoSwitchOff() {
        Set<BluetoothDevice> bondedDevices;
        Log.d("[MCF_DS_SYS]_PreconditionObserver", "setAutoSwitchOff");
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || !defaultAdapter.isEnabled() || (bondedDevices = defaultAdapter.getBondedDevices()) == null || bondedDevices.isEmpty()) {
            return;
        }
        this.mAutoSwitchSettingHelper.disableAutoSwitchDevices();
    }

    public String getAutoSwitchDeviceAddress() {
        return this.mAutoSwitchSettingHelper.getAutoSwitchDeviceAddress();
    }

    public void logContinuitySettingEnable() {
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.mcfds.LOG_SAMSUNG_ANALYTICS");
        intent.putExtra("type", "event");
        intent.putExtra("screenId", "101");
        intent.putExtra("eventId", "10100");
        intent.putExtra("detail", "auto");
        intent.setPackage("com.samsung.android.mcfds");
        this.mContext.sendBroadcast(intent);
    }

    public final boolean isEnableSettings() {
        boolean z = isSupported(2) && isAutoSwitchOn();
        boolean z2 = isSupportedContinuity() && isEnableSettings("mcf_continuity");
        boolean z3 = isSupportedContinuity() && isEnableSettings("multi_control_enabled");
        boolean z4 = isSupportedContinuity() && isEnableSettings("vcc_continuity");
        boolean z5 = isSupportedContinuity() && isEnableSettings("hwrs_service");
        Log.d("[MCF_DS_SYS]_PreconditionObserver", "isEnableSettings - " + z + ", " + z2 + ", " + z3 + ", " + z4 + ", " + z5);
        return z || z2 || z3 || z4 || z5;
    }

    public static boolean isSupportedContinuity() {
        return isSupported(1) || isSupported(4) || isSupported(8);
    }

    public final void updateSettings(boolean z) {
        Log.i("[MCF_DS_SYS]_PreconditionObserver", "updateSettings - " + z);
        if (z) {
            setFlag(61440);
        } else {
            unsetFlag(61440);
        }
    }

    public final void registerSettingsObserver() {
        if (FEATURE_CONTINUITY <= 0) {
            Log.e("[MCF_DS_SYS]_PreconditionObserver", "registerSettingsObserver - invalid feature value");
            return;
        }
        if (this.mRegisterSettingsObserver) {
            return;
        }
        if (isSupportedContinuity()) {
            registerContinuityObserver();
            registerMultiControlObserver();
            registerVideoCallContinuityObserver();
            registerHwSharingObserver();
        }
        if (isSupported(2)) {
            this.mAutoSwitchSettingHelper.start();
        }
        this.mRegisterSettingsObserver = true;
    }

    public final void handleAutoSwitchModeChanged(boolean z) {
        onChangeSettings(4);
    }

    public final void unregisterSettingsObserver() {
        if (this.mRegisterSettingsObserver) {
            if (isSupportedContinuity()) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mSettingObserver);
            }
            if (isSupported(2)) {
                this.mAutoSwitchSettingHelper.stop();
            }
            this.mRegisterSettingsObserver = false;
        }
    }

    public final void onChangeSettings(int i) {
        boolean isEnableSettings = isEnableSettings();
        if (isEnableSettings == isFlag(61440)) {
            return;
        }
        updateSettings(isEnableSettings);
        notifyChanged(i);
    }

    public final void registerPackageObserver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart("com.samsung.android.scloud", 0);
        intentFilter.addDataSchemeSpecificPart("com.samsung.android.mcfds", 0);
        registerReceiver(this.mPkgReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter2.addDataScheme("package");
        intentFilter2.addDataSchemeSpecificPart("com.samsung.android.mcfds", 0);
        registerReceiver(this.mPkgReceiver, intentFilter2);
        this.mIsPkgObserverRegistered = true;
    }

    public final void unregisterPackageObserver() {
        if (this.mIsPkgObserverRegistered) {
            this.mContext.unregisterReceiver(this.mPkgReceiver);
            this.mIsPkgObserverRegistered = false;
        }
    }

    public final Account getSamsungAccount() {
        Account[] accountsByType = getAccountsByType();
        Log.d("[MCF_DS_SYS]_PreconditionObserver", "getSamsungAccount - " + accountsByType.length);
        Account account = accountsByType.length > 0 ? accountsByType[0] : null;
        if (account != null && Utils.DEBUG) {
            Log.d("[MCF_DS_SYS]_PreconditionObserver", "getSamsungAccount - " + account.toString());
        }
        return account;
    }

    public static boolean isChangedAccount(Account account, Account account2) {
        if (account == null || account2 == null) {
            return (account == null && account2 == null) ? false : true;
        }
        return !account.name.equals(account2.name);
    }

    public final void addOnAccountsUpdatedListener() {
        if (this.mIsAddedAccountListener) {
            Log.w("[MCF_DS_SYS]_PreconditionObserver", "addOnAccountsUpdatedListener - already added");
            return;
        }
        Log.d("[MCF_DS_SYS]_PreconditionObserver", "addOnAccountsUpdatedListener");
        AccountManager.get(this.mContext).addOnAccountsUpdatedListener(this.mOnAccountsUpdateListener, null, true, new String[]{SAMSUNG_ACCOUNT_TYPE});
        if (this.mUserId != 0) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.accounts.LOGIN_ACCOUNTS_CHANGED");
            this.mContext.semRegisterReceiverAsUser(this.mAccountChangeReceiver, new UserHandle(this.mUserId), intentFilter, null, null);
        }
        this.mIsAddedAccountListener = true;
    }

    public final void removeOnAccountsUpdatedListener() {
        if (!this.mIsAddedAccountListener) {
            Log.w("[MCF_DS_SYS]_PreconditionObserver", "removeOnAccountsUpdatedListener - already added");
            return;
        }
        Log.d("[MCF_DS_SYS]_PreconditionObserver", "removeOnAccountsUpdatedListener");
        AccountManager.get(this.mContext).removeOnAccountsUpdatedListener(this.mOnAccountsUpdateListener);
        if (this.mUserId != 0) {
            this.mContext.unregisterReceiver(this.mAccountChangeReceiver);
        }
        this.mIsAddedAccountListener = false;
    }

    public final void checkAccountUpdated() {
        Account samsungAccount = getSamsungAccount();
        if (samsungAccount != null && Utils.DEBUG) {
            Log.d("[MCF_DS_SYS]_PreconditionObserver", "checkAccountUpdated - " + samsungAccount.toString());
        }
        if (!isChangedAccount(this.mCurrentAccount, samsungAccount)) {
            Log.d("[MCF_DS_SYS]_PreconditionObserver", "checkAccountUpdated - is not changed");
            return;
        }
        this.mCurrentAccount = samsungAccount;
        if (samsungAccount != null) {
            if (!Utils.isHighPowerConsumptionChipset()) {
                setContinuitySetting(1);
                updateSettings(isEnableSettings());
            }
            setFlag(15);
        } else {
            setContinuitySetting(0);
            unsetFlag(15);
        }
        notifyChanged(2);
    }
}
