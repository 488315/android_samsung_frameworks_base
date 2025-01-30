package com.samsung.android.server.continuity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.server.continuity.common.Utils;

/* loaded from: classes2.dex */
public class PreconditionObserver extends AbstractPreconditionObserver {
    public PreconditionObserver(Context context) {
        super(context);
    }

    @Override // com.samsung.android.server.continuity.AbstractPreconditionObserver
    public void initContinuitySetting() {
        if (Settings.System.semGetIntForUser(this.mContext.getContentResolver(), "mcf_continuity", -1, this.mUserId) == -1) {
            setContinuitySetting((this.mCurrentAccount == null || Utils.isHighPowerConsumptionChipset()) ? 0 : 1);
        }
    }

    @Override // com.samsung.android.server.continuity.AbstractPreconditionObserver
    public void setContinuitySetting(int i) {
        if (AbstractPreconditionObserver.isSupportedContinuity()) {
            Log.i("[MCF_DS_SYS]_PreconditionObserver", "setContinuitySetting : " + i);
            Settings.System.semPutIntForUser(this.mContext.getContentResolver(), "mcf_continuity", i, this.mUserId);
            if (i == 1) {
                logContinuitySettingEnable();
            }
        }
    }

    @Override // com.samsung.android.server.continuity.AbstractPreconditionObserver
    public boolean isEnableSettings(String str) {
        try {
            return Settings.System.semGetIntForUser(this.mContext.getContentResolver(), str, 0, this.mUserId) == 1;
        } catch (IllegalArgumentException | SecurityException e) {
            Log.w("[MCF_DS_SYS]_PreconditionObserver", "isEnableSettings - " + e.getMessage());
            return false;
        }
    }

    @Override // com.samsung.android.server.continuity.AbstractPreconditionObserver
    public void registerContinuityObserver() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("mcf_continuity"), false, this.mSettingObserver, this.mUserId);
    }

    @Override // com.samsung.android.server.continuity.AbstractPreconditionObserver
    public void registerMultiControlObserver() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("multi_control_enabled"), false, this.mSettingObserver, this.mUserId);
    }

    @Override // com.samsung.android.server.continuity.AbstractPreconditionObserver
    public void registerVideoCallContinuityObserver() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("vcc_continuity"), false, this.mSettingObserver, this.mUserId);
    }

    @Override // com.samsung.android.server.continuity.AbstractPreconditionObserver
    public void registerHwSharingObserver() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("hwrs_service"), false, this.mSettingObserver, this.mUserId);
    }

    @Override // com.samsung.android.server.continuity.AbstractPreconditionObserver
    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        this.mContext.semRegisterReceiverAsUser(broadcastReceiver, new UserHandle(this.mUserId), intentFilter, null, null);
    }

    @Override // com.samsung.android.server.continuity.AbstractPreconditionObserver
    public Account[] getAccountsByType() {
        return AccountManager.get(this.mContext).getAccountsByTypeAsUser(AbstractPreconditionObserver.SAMSUNG_ACCOUNT_TYPE, new UserHandle(this.mUserId));
    }
}
