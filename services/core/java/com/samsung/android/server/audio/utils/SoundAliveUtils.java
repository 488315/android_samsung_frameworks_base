package com.samsung.android.server.audio.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.media.AudioSystem;
import android.net.Uri;
import android.util.Log;

/* loaded from: classes2.dex */
public abstract class SoundAliveUtils {
    public static boolean sIsSoundAliveExist = false;

    public static boolean isSoundAliveExist(Context context) {
        if (sIsSoundAliveExist) {
            return true;
        }
        boolean z = context.getPackageManager().resolveContentProvider("com.sec.android.app.soundalive.compatibility.SAContentProvider", 0) != null;
        sIsSoundAliveExist = z;
        return z;
    }

    public static void notifyDVFSToSoundAlive(Context context, int i, boolean z) {
        if ((i == 2 || wasStreamActiveRecently(3, 0)) && isSoundAliveExist(context)) {
            Log.d("AS.SoundAliveUtils", "notify dvfs state : " + i + ", screen : " + z);
            try {
                context.getContentResolver().getType(Uri.parse("content://com.sec.android.app.soundalive.compatibility.SAContentProvider").buildUpon().appendQueryParameter("SCREEN_STATE", "" + z).build());
            } catch (Exception e) {
                Log.e("AS.SoundAliveUtils", "notifyDVFSToSoundAlive", e);
            }
        }
    }

    public static boolean wasStreamActiveRecently(int i, int i2) {
        return AudioSystem.isStreamActive(i, i2) || AudioSystem.isStreamActiveRemotely(i, i2);
    }

    public static void resetConcertHall(Context context) {
        if (isSoundAliveExist(context)) {
            Log.d("AS.SoundAliveUtils", "reset concert hall from sound assistant data cleared");
            try {
                ContentResolver contentResolver = context.getContentResolver();
                Uri parse = Uri.parse("content://com.sec.android.app.soundalive.compatibility.SAContentProvider");
                ContentValues contentValues = new ContentValues();
                contentValues.put("CONCERT_HALL_EFFECT", (Integer) 0);
                contentResolver.insert(parse, contentValues);
            } catch (Exception e) {
                Log.e("AS.SoundAliveUtils", "resetConcertHall", e);
            }
        }
    }
}
