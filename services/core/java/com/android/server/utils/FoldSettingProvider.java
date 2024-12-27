package com.android.server.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;
import com.android.internal.foldables.FoldLockSettingAvailabilityProvider;
import com.android.internal.util.SettingsWrapper;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class FoldSettingProvider {
    public static final Set SETTING_VALUES = Set.of("stay_awake_on_fold_key", "selective_stay_awake_key", "sleep_on_fold_key");
    public final ContentResolver mContentResolver;
    public final FoldLockSettingAvailabilityProvider mFoldLockSettingAvailabilityProvider;
    public final SettingsWrapper mSettingsWrapper;

    public FoldSettingProvider(Context context, SettingsWrapper settingsWrapper, FoldLockSettingAvailabilityProvider foldLockSettingAvailabilityProvider) {
        this.mContentResolver = context.getContentResolver();
        this.mSettingsWrapper = settingsWrapper;
        this.mFoldLockSettingAvailabilityProvider = foldLockSettingAvailabilityProvider;
    }

    public final String getFoldSettingValue() {
        if (!this.mFoldLockSettingAvailabilityProvider.isFoldLockBehaviorAvailable()) {
            return "selective_stay_awake_key";
        }
        String stringForUser = this.mSettingsWrapper.getStringForUser(this.mContentResolver, "fold_lock_behavior_setting", -2);
        if (stringForUser == null) {
            stringForUser = "selective_stay_awake_key";
        }
        if (SETTING_VALUES.contains(stringForUser)) {
            return stringForUser;
        }
        Log.e("FoldSettingProvider", "getFoldSettingValue: Invalid setting value, returning default setting value");
        return "selective_stay_awake_key";
    }
}
