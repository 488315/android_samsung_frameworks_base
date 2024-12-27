package com.samsung.android.server.audio.utils;

import android.content.Context;
import android.media.AudioSystem;
import android.net.Uri;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class SoundAliveUtils {
    public static boolean sIsSoundAliveExist;

    public static void notifyDVFSToSoundAlive(Context context, int i, boolean z) {
        if (i == 2
                || AudioSystem.isStreamActive(3, 0)
                || AudioSystem.isStreamActiveRemotely(3, 0)) {
            boolean z2 = true;
            if (!sIsSoundAliveExist) {
                boolean z3 =
                        context.getPackageManager()
                                        .resolveContentProvider(
                                                "com.sec.android.app.soundalive.compatibility.SAContentProvider",
                                                0)
                                != null;
                sIsSoundAliveExist = z3;
                z2 = z3;
            }
            if (z2) {
                Log.d("AS.SoundAliveUtils", "notify dvfs state : " + i + ", screen : " + z);
                try {
                    context.getContentResolver()
                            .getType(
                                    Uri.parse(
                                                    "content://com.sec.android.app.soundalive.compatibility.SAContentProvider")
                                            .buildUpon()
                                            .appendQueryParameter("SCREEN_STATE", "" + z)
                                            .build());
                } catch (Exception e) {
                    Log.e("AS.SoundAliveUtils", "notifyDVFSToSoundAlive", e);
                }
            }
        }
    }
}
