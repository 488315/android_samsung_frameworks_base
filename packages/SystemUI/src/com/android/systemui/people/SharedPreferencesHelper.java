package com.android.systemui.people;

import android.content.SharedPreferences;
import com.android.systemui.people.widget.PeopleTileKey;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SharedPreferencesHelper {
    public static void setPeopleTileKey(SharedPreferences sharedPreferences, PeopleTileKey peopleTileKey) {
        String str = peopleTileKey.mShortcutId;
        int i = peopleTileKey.mUserId;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("shortcut_id", str);
        edit.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, i);
        edit.putString("package_name", peopleTileKey.mPackageName);
        edit.apply();
    }
}
