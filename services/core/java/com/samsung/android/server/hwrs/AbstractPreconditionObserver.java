package com.samsung.android.server.hwrs;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

/* loaded from: classes2.dex */
public abstract class AbstractPreconditionObserver {
    public final Context mContext;
    public boolean mIsAddedAccountListener;
    public boolean mIsRegisteredCameraShareObserver;
    public StateChangedListener mListener;
    public int mState;
    public final Uri URI_CAMERASHARE = Settings.System.getUriFor("hwrs_camerashare_setting");
    public int mUserId = -10000;
    public final ContentObserver mSettingObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.samsung.android.server.hwrs.AbstractPreconditionObserver.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            if (uri != null && AbstractPreconditionObserver.this.URI_CAMERASHARE.equals(uri)) {
                AbstractPreconditionObserver.this.handleSettingUpdate(3);
            }
        }
    };
    public final OnAccountsUpdateListener mOnAccountsUpdateListener = new OnAccountsUpdateListener() { // from class: com.samsung.android.server.hwrs.AbstractPreconditionObserver.2
        @Override // android.accounts.OnAccountsUpdateListener
        public void onAccountsUpdated(Account[] accountArr) {
            Log.d("[HWRS_SYS]PreconditionObserver", "onAccountsUpdated - " + accountArr.length);
            AbstractPreconditionObserver.this.handleSamsungAccountUpdate();
        }
    };
    public final BroadcastReceiver mAccountChangeReceiver = new BroadcastReceiver() { // from class: com.samsung.android.server.hwrs.AbstractPreconditionObserver.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.accounts.LOGIN_ACCOUNTS_CHANGED".equals(intent.getAction())) {
                Log.i("[HWRS_SYS]PreconditionObserver", "mAccountChangeReceiver.onReceive - LOGIN_ACCOUNTS_CHANGED_ACTION");
                AbstractPreconditionObserver.this.handleSamsungAccountUpdate();
            }
        }
    };

    public interface StateChangedListener {
        void onChanged(int i, int i2);
    }

    public AbstractPreconditionObserver(Context context) {
        this.mContext = context;
    }

    public void start(int i, StateChangedListener stateChangedListener) {
        this.mUserId = i;
        this.mListener = stateChangedListener;
        updateFlag(1, isSamsungAccountLogin());
        updateFlag(2, isPackageInstalled("com.samsung.android.hwresourceshare"));
        updateFlag(4, isSettingEnabled());
        initCheck();
        registerSettingsObserver();
        addOnAccountsUpdatedListener();
    }

    public void stop() {
        unregisterSettingsObserver();
        removeOnAccountsUpdatedListener();
        this.mState = 0;
        this.mUserId = -10000;
    }

    public boolean meetConditions() {
        Log.d("[HWRS_SYS]PreconditionObserver", "meetConditions - current state : " + Integer.toHexString(this.mState));
        return this.mState == 7;
    }

    public void updateFlag(int i, boolean z) {
        Log.d("[HWRS_SYS]PreconditionObserver", "updateFlag - " + i + ", " + z);
        if (z) {
            setFlag(i);
        } else {
            unsetFlag(i);
        }
    }

    public void setFlag(int i) {
        this.mState = i | this.mState;
    }

    public void unsetFlag(int i) {
        this.mState = (~i) & this.mState;
    }

    public boolean isFlag(int i) {
        return (this.mState & i) == i;
    }

    public final void notifyChanged(int i) {
        StateChangedListener stateChangedListener = this.mListener;
        if (stateChangedListener != null) {
            stateChangedListener.onChanged(this.mState, i);
        }
    }

    public boolean isHWRSEnable() {
        return Settings.System.semGetIntForUser(this.mContext.getContentResolver(), "hwrs_service", 0, this.mUserId) == 1;
    }

    public void initCheck() {
        Log.d("[HWRS_SYS]PreconditionObserver", "initCheck");
        if (isFlag(2) && Settings.System.semGetIntForUser(this.mContext.getContentResolver(), "hwrs_camerashare_setting", -1, this.mUserId) == -1) {
            Log.w("[HWRS_SYS]PreconditionObserver", "hwrs_camerashare_setting is not set");
        }
    }

    public final boolean isPackageInstalled(String str) {
        try {
            this.mContext.getPackageManager().getPackageInfo(str, 1);
            Log.d("[HWRS_SYS]PreconditionObserver", "Package : " + str + " installed");
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d("[HWRS_SYS]PreconditionObserver", "Package : " + str + " not installed");
            return false;
        }
    }

    public void registerCameraShareObserver() {
        this.mContext.getContentResolver().registerContentObserver(this.URI_CAMERASHARE, false, this.mSettingObserver, this.mUserId);
    }

    public final boolean isSettingEnabled() {
        boolean isSettingEnabled = isSettingEnabled("hwrs_camerashare_setting");
        Log.d("[HWRS_SYS]PreconditionObserver", "isSettingEnabled - camerashareSetting : " + isSettingEnabled);
        return isSettingEnabled;
    }

    public boolean isSettingEnabled(String str) {
        return Settings.System.semGetIntForUser(this.mContext.getContentResolver(), str, 0, this.mUserId) == 1;
    }

    public final void registerSettingsObserver() {
        if (this.mIsRegisteredCameraShareObserver) {
            return;
        }
        if (isFlag(2)) {
            registerCameraShareObserver();
        }
        this.mIsRegisteredCameraShareObserver = true;
    }

    public final void unregisterSettingsObserver() {
        if (this.mIsRegisteredCameraShareObserver) {
            if (isFlag(2)) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mSettingObserver);
            }
            this.mIsRegisteredCameraShareObserver = false;
        }
    }

    public final void handleSettingUpdate(int i) {
        boolean isSettingEnabled = isSettingEnabled();
        if (isSettingEnabled == isFlag(4)) {
            return;
        }
        updateFlag(4, isSettingEnabled);
        notifyChanged(i);
    }

    public final void addOnAccountsUpdatedListener() {
        if (this.mIsAddedAccountListener) {
            Log.w("[HWRS_SYS]PreconditionObserver", "addOnAccountsUpdatedListener - already added");
            return;
        }
        Log.d("[HWRS_SYS]PreconditionObserver", "addOnAccountsUpdatedListener");
        AccountManager.get(this.mContext).addOnAccountsUpdatedListener(this.mOnAccountsUpdateListener, null, true, new String[]{"com.osp.app.signin"});
        if (this.mUserId != 0) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.accounts.LOGIN_ACCOUNTS_CHANGED");
            this.mContext.semRegisterReceiverAsUser(this.mAccountChangeReceiver, new UserHandle(this.mUserId), intentFilter, null, null);
        }
        this.mIsAddedAccountListener = true;
    }

    public final void removeOnAccountsUpdatedListener() {
        if (!this.mIsAddedAccountListener) {
            Log.w("[HWRS_SYS]PreconditionObserver", "removeOnAccountsUpdatedListener - already added");
            return;
        }
        Log.d("[HWRS_SYS]PreconditionObserver", "removeOnAccountsUpdatedListener");
        AccountManager.get(this.mContext).removeOnAccountsUpdatedListener(this.mOnAccountsUpdateListener);
        if (this.mUserId != 0) {
            this.mContext.unregisterReceiver(this.mAccountChangeReceiver);
        }
        this.mIsAddedAccountListener = false;
    }

    public final boolean isSamsungAccountLogin() {
        if (AccountManager.get(this.mContext).getAccountsByTypeAsUser("com.osp.app.signin", new UserHandle(this.mUserId)).length > 0) {
            Log.d("[HWRS_SYS]PreconditionObserver", "SamsungAccount login");
            return true;
        }
        Log.d("[HWRS_SYS]PreconditionObserver", "SamsungAccount not login");
        return false;
    }

    public final void handleSamsungAccountUpdate() {
        Log.d("[HWRS_SYS]PreconditionObserver", "handleSamsungAccountUpdate");
        updateFlag(1, isSamsungAccountLogin());
        notifyChanged(2);
    }
}
