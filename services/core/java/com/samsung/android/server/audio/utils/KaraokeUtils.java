package com.samsung.android.server.audio.utils;

import android.content.Context;
import android.content.Intent;
import android.media.AudioSystem;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.server.audio.AudioSettingsHelper;

/* loaded from: classes2.dex */
public abstract class KaraokeUtils {
    public static void checkAndBroadcastKaraokeInstalled(Context context, AudioSettingsHelper audioSettingsHelper, String str, boolean z) {
        if (audioSettingsHelper.checkAppCategory(str, "karaoke_allow")) {
            StringBuilder sb = new StringBuilder();
            sb.append("Karaoke app is ");
            sb.append(z ? "installed" : "removed");
            Log.i("KaraokeUtils", sb.toString());
            Intent intent = new Intent("com.samsung.android.intent.karaoke");
            intent.putExtra("installed", z ? 1 : 0);
            intent.putExtra("package", str);
            intent.setPackage("com.sec.android.app.soundalive");
            AudioUtils.sendBroadcastToUser(context, intent, UserHandle.ALL, null);
        }
    }

    public static void setKaraokeListenback(int i) {
        AudioSystem.setParameters("l_effect_listenback_key;state=" + i);
    }
}
