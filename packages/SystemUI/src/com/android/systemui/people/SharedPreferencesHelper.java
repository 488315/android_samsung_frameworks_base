package com.android.systemui.people;

import android.content.SharedPreferences;
import com.android.systemui.people.widget.PeopleTileKey;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;

/* loaded from: classes2.dex */
public class SharedPreferencesHelper {
    public static void setPeopleTileKey(SharedPreferences sharedPreferences, PeopleTileKey peopleTileKey) {
        String str = peopleTileKey.mShortcutId;
        int i = peopleTileKey.mUserId;
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putString("shortcut_id", str);
        editorEdit.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, i);
        editorEdit.putString("package_name", peopleTileKey.mPackageName);
        editorEdit.apply();
    }
}
