package com.android.systemui.people;

import android.content.SharedPreferences;
import com.android.systemui.people.widget.PeopleTileKey;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SharedPreferencesHelper {
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
