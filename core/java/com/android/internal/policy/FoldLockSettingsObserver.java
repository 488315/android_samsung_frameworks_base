package com.android.internal.policy;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;

import java.util.Set;

public class FoldLockSettingsObserver extends ContentObserver {
    public static final String SETTING_VALUE_DEFAULT = "selective_stay_awake_key";
    public static final String SETTING_VALUE_SELECTIVE_STAY_AWAKE = "selective_stay_awake_key";
    private final Context mContext;
    String mFoldLockSetting;
    public static final String SETTING_VALUE_STAY_AWAKE_ON_FOLD = "stay_awake_on_fold_key";
    public static final String SETTING_VALUE_SLEEP_ON_FOLD = "sleep_on_fold_key";
    private static final Set<String> SETTING_VALUES =
            Set.of(
                    SETTING_VALUE_STAY_AWAKE_ON_FOLD,
                    "selective_stay_awake_key",
                    SETTING_VALUE_SLEEP_ON_FOLD);

    public FoldLockSettingsObserver(Handler handler, Context context) {
        super(handler);
        this.mContext = context;
    }

    public void register() {
        ContentResolver r = this.mContext.getContentResolver();
        r.registerContentObserver(
                Settings.System.getUriFor(Settings.System.FOLD_LOCK_BEHAVIOR), false, this, -1);
        requestAndCacheFoldLockSetting();
    }

    public void unregister() {
        this.mContext.getContentResolver().unregisterContentObserver(this);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean selfChange) {
        requestAndCacheFoldLockSetting();
    }

    void requestAndCacheFoldLockSetting() {
        String currentSetting = request();
        if (currentSetting == null || !SETTING_VALUES.contains(currentSetting)) {
            currentSetting = "selective_stay_awake_key";
        }
        setCurrentFoldSetting(currentSetting);
    }

    String request() {
        return Settings.System.getStringForUser(
                this.mContext.getContentResolver(), Settings.System.FOLD_LOCK_BEHAVIOR, -2);
    }

    void setCurrentFoldSetting(String newSetting) {
        this.mFoldLockSetting = newSetting;
    }

    public boolean isStayAwakeOnFold() {
        return this.mFoldLockSetting.equals(SETTING_VALUE_STAY_AWAKE_ON_FOLD);
    }

    public boolean isSelectiveStayAwake() {
        return this.mFoldLockSetting.equals("selective_stay_awake_key");
    }

    public boolean isSleepOnFold() {
        return this.mFoldLockSetting.equals(SETTING_VALUE_SLEEP_ON_FOLD);
    }
}
