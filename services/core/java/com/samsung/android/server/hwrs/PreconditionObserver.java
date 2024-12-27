package com.samsung.android.server.hwrs;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PreconditionObserver {
    public final AbstractPreconditionObserver$3 mAccountChangeReceiver;
    public boolean mCameraShareInstalled;
    public final Context mContext;
    public UserHandle mCurrentUserHandle;
    public final AnonymousClass1 mHandler;
    public boolean mIsAddedAccountListener;
    public boolean mIsRegisteredCameraShareObserver;
    public boolean mIsRegisteredStorageShareObserver;
    public boolean mIsValidState;
    public AnonymousClass2 mListener;
    public final AbstractPreconditionObserver$2 mOnAccountsUpdateListener;
    public final AbstractPreconditionObserver$1 mSettingObserver;
    public int mState;
    public boolean mStorageShareInstalled;
    public List settingList;
    public final Uri URI_CAMERASHARE = Settings.System.getUriFor("hwrs_camerashare_setting");
    public final Uri URI_STORAGESHARE = Settings.System.getUriFor("hwrs_storageshare_setting");
    public int mUserId = -10000;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.android.server.hwrs.PreconditionObserver$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.samsung.android.server.hwrs.AbstractPreconditionObserver$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.samsung.android.server.hwrs.AbstractPreconditionObserver$2] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.samsung.android.server.hwrs.AbstractPreconditionObserver$3] */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.samsung.android.server.hwrs.PreconditionObserver$1] */
    public PreconditionObserver(Context context) {
        final Handler handler = new Handler(Looper.getMainLooper());
        this.mSettingObserver = new ContentObserver(handler) { // from class: com.samsung.android.server.hwrs.AbstractPreconditionObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                if (uri == null) {
                    return;
                }
                if (PreconditionObserver.this.URI_CAMERASHARE.equals(uri)) {
                    PreconditionObserver preconditionObserver = PreconditionObserver.this;
                    int i = preconditionObserver.getValues("hwrs_camerashare_setting") > 0 ? 4 : 5;
                    preconditionObserver.updateFlag(2, preconditionObserver.isSettingEnabled$1());
                    preconditionObserver.notifyChanged(i);
                    return;
                }
                if (PreconditionObserver.this.URI_STORAGESHARE.equals(uri)) {
                    PreconditionObserver preconditionObserver2 = PreconditionObserver.this;
                    int i2 = preconditionObserver2.getValues("hwrs_storageshare_setting") > 0 ? 6 : 7;
                    preconditionObserver2.updateFlag(2, preconditionObserver2.isSettingEnabled$1());
                    preconditionObserver2.notifyChanged(i2);
                }
            }
        };
        this.mOnAccountsUpdateListener = new OnAccountsUpdateListener() { // from class: com.samsung.android.server.hwrs.AbstractPreconditionObserver$2
            @Override // android.accounts.OnAccountsUpdateListener
            public final void onAccountsUpdated(Account[] accountArr) {
                GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("onAccountsUpdated - "), accountArr.length, "[HWRS_SYS]PreconditionObserver");
                PreconditionObserver preconditionObserver = PreconditionObserver.this;
                preconditionObserver.getClass();
                Log.d("[HWRS_SYS]PreconditionObserver", "handleSamsungAccountUpdate");
                preconditionObserver.updateFlag(1, preconditionObserver.isSamsungAccountLogin());
                preconditionObserver.notifyChanged(2);
            }
        };
        this.mAccountChangeReceiver = new BroadcastReceiver() { // from class: com.samsung.android.server.hwrs.AbstractPreconditionObserver$3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("android.accounts.LOGIN_ACCOUNTS_CHANGED".equals(intent.getAction())) {
                    Log.d("[HWRS_SYS]PreconditionObserver", "mAccountChangeReceiver.onReceive - LOGIN_ACCOUNTS_CHANGED_ACTION");
                    PreconditionObserver preconditionObserver = PreconditionObserver.this;
                    preconditionObserver.getClass();
                    Log.d("[HWRS_SYS]PreconditionObserver", "handleSamsungAccountUpdate");
                    preconditionObserver.updateFlag(1, preconditionObserver.isSamsungAccountLogin());
                    preconditionObserver.notifyChanged(2);
                }
            }
        };
        this.mContext = context;
        Log.d("[HWRS_SYS]PreconditionObserver", "PreconditionObserver entered");
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.server.hwrs.PreconditionObserver.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                PreconditionObserver preconditionObserver = PreconditionObserver.this;
                preconditionObserver.getClass();
                if (i != 0) {
                    if (i != 1) {
                        return;
                    }
                    preconditionObserver.stopUser();
                    return;
                }
                Log.i("[HWRS_SYS]PreconditionObserver", "start entered");
                UserHandle userHandle = preconditionObserver.mCurrentUserHandle;
                int semGetIdentifier = userHandle == null ? -10000 : userHandle.semGetIdentifier();
                AnonymousClass2 anonymousClass2 = preconditionObserver.new AnonymousClass2();
                preconditionObserver.mUserId = semGetIdentifier;
                preconditionObserver.mListener = anonymousClass2;
                preconditionObserver.mCameraShareInstalled = preconditionObserver.isPackageInstalled("com.samsung.android.hwresourceshare");
                preconditionObserver.mStorageShareInstalled = preconditionObserver.isPackageInstalled("com.samsung.android.hwresourceshare.storage");
                Log.d("[HWRS_SYS]PreconditionObserver", "initCheck");
                ArrayList arrayList = new ArrayList();
                preconditionObserver.settingList = arrayList;
                if (preconditionObserver.mCameraShareInstalled) {
                    arrayList.add("hwrs_camerashare_setting");
                }
                if (preconditionObserver.mStorageShareInstalled) {
                    ((ArrayList) preconditionObserver.settingList).add("hwrs_storageshare_setting");
                }
                preconditionObserver.updateFlag(1, preconditionObserver.isSamsungAccountLogin());
                preconditionObserver.updateFlag(2, preconditionObserver.isSettingEnabled$1());
                boolean z = preconditionObserver.mIsRegisteredCameraShareObserver;
                AbstractPreconditionObserver$1 abstractPreconditionObserver$1 = preconditionObserver.mSettingObserver;
                if (!z && preconditionObserver.mCameraShareInstalled) {
                    preconditionObserver.mContext.getContentResolver().registerContentObserver(preconditionObserver.URI_CAMERASHARE, false, abstractPreconditionObserver$1, preconditionObserver.mUserId);
                    preconditionObserver.mIsRegisteredCameraShareObserver = true;
                }
                if (!preconditionObserver.mIsRegisteredStorageShareObserver && preconditionObserver.mStorageShareInstalled) {
                    preconditionObserver.mContext.getContentResolver().registerContentObserver(preconditionObserver.URI_STORAGESHARE, false, abstractPreconditionObserver$1, preconditionObserver.mUserId);
                    preconditionObserver.mIsRegisteredStorageShareObserver = true;
                }
                if (preconditionObserver.mIsAddedAccountListener) {
                    Log.w("[HWRS_SYS]PreconditionObserver", "addOnAccountsUpdatedListener - already added");
                } else {
                    Log.d("[HWRS_SYS]PreconditionObserver", "addOnAccountsUpdatedListener");
                    AccountManager.get(preconditionObserver.mContext).addOnAccountsUpdatedListener(preconditionObserver.mOnAccountsUpdateListener, null, true, new String[]{"com.osp.app.signin"});
                    if (preconditionObserver.mUserId != 0) {
                        preconditionObserver.mContext.semRegisterReceiverAsUser(preconditionObserver.mAccountChangeReceiver, new UserHandle(preconditionObserver.mUserId), BatteryService$$ExternalSyntheticOutline0.m("android.accounts.LOGIN_ACCOUNTS_CHANGED"), null, null);
                    }
                    preconditionObserver.mIsAddedAccountListener = true;
                }
                preconditionObserver.mIsValidState = preconditionObserver.meetConditions();
                FlashNotificationsController$$ExternalSyntheticOutline0.m("[HWRS_SYS]PreconditionObserver", new StringBuilder("mIsValidState : "), preconditionObserver.mIsValidState);
            }
        };
    }

    public final int getValues(String str) {
        int semGetIntForUser = Settings.System.semGetIntForUser(this.mContext.getContentResolver(), str, -1, this.mUserId);
        Log.i("[HWRS_SYS]PreconditionObserver", "getValues ID : " + str + ", value : " + semGetIntForUser);
        return semGetIntForUser;
    }

    public final boolean isPackageInstalled(String str) {
        try {
            Log.i("[HWRS_SYS]PreconditionObserver", XmlUtils$$ExternalSyntheticOutline0.m("Package : ", str, "(", this.mContext.getPackageManager().getPackageInfo(str, 1).versionName, ") installed"));
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            AudioDeviceInventory$$ExternalSyntheticOutline0.m("Package : ", str, " not installed", "[HWRS_SYS]PreconditionObserver");
            return false;
        }
    }

    public final boolean isSamsungAccountLogin() {
        if (AccountManager.get(this.mContext).getAccountsByTypeAsUser("com.osp.app.signin", new UserHandle(this.mUserId)).length > 0) {
            Log.i("[HWRS_SYS]PreconditionObserver", "SamsungAccount login");
            return true;
        }
        Log.i("[HWRS_SYS]PreconditionObserver", "SamsungAccount not login");
        return false;
    }

    public final boolean isSettingEnabled$1() {
        Iterator it = ((ArrayList) this.settingList).iterator();
        while (true) {
            boolean z = false;
            while (it.hasNext()) {
                String str = (String) it.next();
                if (!z) {
                    boolean z2 = Settings.System.semGetIntForUser(this.mContext.getContentResolver(), str, 0, this.mUserId) == 1;
                    Log.i("[HWRS_SYS]PreconditionObserver", "isSettingEnabled - " + str + " : " + z2);
                    if (z2) {
                    }
                }
                z = true;
            }
            return z;
        }
    }

    public final boolean meetConditions() {
        Log.i("[HWRS_SYS]PreconditionObserver", "meetConditions - current state : " + Integer.toHexString(this.mState));
        return this.mState == 3;
    }

    public final void notifyChanged(int i) {
        AnonymousClass2 anonymousClass2 = this.mListener;
        if (anonymousClass2 != null) {
            PreconditionObserver preconditionObserver = PreconditionObserver.this;
            boolean meetConditions = preconditionObserver.meetConditions();
            Log.i("[HWRS_SYS]PreconditionObserver", "onChanged - isValid : " + meetConditions + ", reason : " + i);
            preconditionObserver.mIsValidState = meetConditions;
            if (meetConditions) {
                preconditionObserver.setValues(1);
            } else {
                preconditionObserver.setValues(0);
            }
        }
    }

    public void removeAndSendMessageDelayed(int i, int i2, long j) {
        AnonymousClass1 anonymousClass1 = this.mHandler;
        if (anonymousClass1.hasMessages(i)) {
            anonymousClass1.removeMessages(i);
        }
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        anonymousClass1.sendMessageDelayed(obtain, j);
    }

    public void removeAndSendMessageDelayed(int i, long j) {
        AnonymousClass1 anonymousClass1 = this.mHandler;
        if (anonymousClass1.hasMessages(i)) {
            anonymousClass1.removeMessages(i);
        }
        anonymousClass1.sendEmptyMessageDelayed(i, j);
    }

    public final void setValues(int i) {
        DirEncryptService$$ExternalSyntheticOutline0.m(i, "setValues ID : hwrs_service, value : ", "[HWRS_SYS]PreconditionObserver");
        if (Settings.System.semGetIntForUser(this.mContext.getContentResolver(), "hwrs_service", -1, this.mUserId) != i) {
            Settings.System.semPutIntForUser(this.mContext.getContentResolver(), "hwrs_service", i, this.mUserId);
        } else {
            Log.i("[HWRS_SYS]PreconditionObserver", "setValues ID : hwrs_service, same value");
        }
    }

    public final void startUser(UserHandle userHandle) {
        Log.d("[HWRS_SYS]PreconditionObserver", "startUser entered");
        UserHandle userHandle2 = this.mCurrentUserHandle;
        if ((userHandle2 == null ? -10000 : userHandle2.semGetIdentifier()) != -10000) {
            Log.e("[HWRS_SYS]PreconditionObserver", "startUser - invalid request!");
            return;
        }
        Log.d("[HWRS_SYS]PreconditionObserver", "startUser : " + userHandle.semGetIdentifier());
        this.mCurrentUserHandle = userHandle;
        removeAndSendMessageDelayed(0, 0L);
    }

    public final void stopUser() {
        this.mCurrentUserHandle = null;
        if (this.mIsRegisteredCameraShareObserver && this.mCameraShareInstalled) {
            this.mIsRegisteredCameraShareObserver = false;
        }
        if (this.mIsRegisteredStorageShareObserver && this.mStorageShareInstalled) {
            this.mIsRegisteredStorageShareObserver = false;
        }
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingObserver);
        if (this.mIsAddedAccountListener) {
            Log.d("[HWRS_SYS]PreconditionObserver", "removeOnAccountsUpdatedListener");
            AccountManager.get(this.mContext).removeOnAccountsUpdatedListener(this.mOnAccountsUpdateListener);
            if (this.mUserId != 0) {
                this.mContext.unregisterReceiver(this.mAccountChangeReceiver);
            }
            this.mIsAddedAccountListener = false;
        } else {
            Log.w("[HWRS_SYS]PreconditionObserver", "removeOnAccountsUpdatedListener - already added");
        }
        this.mState = 0;
        this.mUserId = -10000;
    }

    public final void updateFlag(int i, boolean z) {
        Log.i("[HWRS_SYS]PreconditionObserver", "updateFlag - " + i + ", " + z);
        if (z) {
            this.mState = i | this.mState;
        } else {
            this.mState = (~i) & this.mState;
        }
    }
}
