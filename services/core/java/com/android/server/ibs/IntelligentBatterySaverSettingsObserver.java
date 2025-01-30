package com.android.server.ibs;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;

/* loaded from: classes2.dex */
public class IntelligentBatterySaverSettingsObserver {
    public final String TAG = "IntelligentBatterySaverSettingsObserver";
    public Context mContext;
    public IntelligentBatterySaverService mIBSService;
    public SettingsObserver mSettingsObserver;

    public IntelligentBatterySaverSettingsObserver(Context context, IntelligentBatterySaverService intelligentBatterySaverService) {
        this.mContext = context;
        this.mIBSService = intelligentBatterySaverService;
    }

    public void init() {
        if (this.mSettingsObserver == null) {
            this.mSettingsObserver = new SettingsObserver();
            this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("ibs_switch"), false, this.mSettingsObserver, -1);
        }
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "ibs_switch", 1) == 0) {
            this.mIBSService.mIBSFastDrainPolicy.setIBSFastDrainActionEnable(false);
        } else {
            this.mIBSService.mIBSFastDrainPolicy.setIBSFastDrainActionEnable(true);
        }
    }

    public class SettingsObserver extends ContentObserver {
        public SettingsObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            if (Settings.Global.getInt(IntelligentBatterySaverSettingsObserver.this.mContext.getContentResolver(), "ibs_switch", 1) == 0) {
                IntelligentBatterySaverSettingsObserver.this.mIBSService.mIBSFastDrainPolicy.setIBSFastDrainActionEnable(false);
            } else {
                IntelligentBatterySaverSettingsObserver.this.mIBSService.mIBSFastDrainPolicy.setIBSFastDrainActionEnable(true);
            }
        }
    }
}
