package com.samsung.android.server.audio.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.UserHandle;
import android.util.Log;

import com.samsung.android.server.audio.AudioSettingsHelper;

import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class KaraokeUtils {
    public static void checkAndBroadcastKaraokeInstalled(
            Context context, AudioSettingsHelper audioSettingsHelper, String str, boolean z) {
        if (audioSettingsHelper.checkAppCategory(str, "karaoke_allow")) {
            Log.i("KaraokeUtils", "Karaoke app is ".concat(z ? "installed" : "removed"));
            Intent intent = new Intent("com.samsung.android.intent.karaoke");
            intent.putExtra("installed", z ? 1 : 0);
            intent.putExtra("package", str);
            intent.setPackage("com.sec.android.app.soundalive");
            UserHandle userHandle = UserHandle.ALL;
            Set set = AudioUtils.DEVICE_OUT_WIRED_DEVICE_SET;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                context.sendBroadcastAsUser(intent, userHandle, null);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }
}
