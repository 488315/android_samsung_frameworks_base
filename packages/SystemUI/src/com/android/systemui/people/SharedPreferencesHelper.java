package com.android.systemui.people;

import android.content.SharedPreferences;
import com.android.systemui.people.widget.PeopleTileKey;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
