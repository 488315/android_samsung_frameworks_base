package com.sec.ims.settings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class UserConfiguration {
    public static final String ENABLE_CALL_WAITING = "enable_call_wait";
    public static final String RCS = "rcs";
    public static final String RCS_HOME_PREF = "rcs_home_pref";
    public static final String RCS_ROAMING_PREF = "rcs_roaming_pref";
    public static final String SS_CLIP_PREF = "ss_clip_pref";
    public static final String SS_CLIR_PREF = "ss_clir_pref";
    public static final String SS_VIDEO_CB_PREF = "ss_video_cb_pref";
    public static final String SS_VOICE_CB_PREF = "ss_volte_cb_pref";
    public static final Uri URI = Uri.parse("content://com.sec.ims.settings/userconfig");

    private UserConfiguration() {
    }

    @Deprecated
    public static String getUserConfig(Context context, String str, String str2) {
        return getUserConfig(context, 0, str, str2);
    }

    @Deprecated
    public static void setUserConfig(Context context, String str, String str2) {
        setUserConfig(context, 0, str, str2);
    }

    public static String getUserConfig(Context context, int i, String str, String str2) {
        Cursor query = context.getContentResolver().query(URI.buildUpon().fragment("simslot" + i).build(), new String[]{str}, null, null, null);
        if (query != null) {
            try {
                if (query.moveToFirst()) {
                    str2 = query.getString(0);
                }
            } catch (Throwable th) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (query != null) {
            query.close();
        }
        return str2;
    }

    public static void setUserConfig(Context context, int i, String str, String str2) {
        if (getUserConfig(context, i, str, "").equals(str2)) {
            return;
        }
        Uri build = URI.buildUpon().fragment("simslot" + i).build();
        ContentValues contentValues = new ContentValues();
        contentValues.put(str, str2);
        context.getContentResolver().insert(build, contentValues);
    }

    @Deprecated
    public static int getUserConfig(Context context, String str, int i) {
        return getUserConfig(context, 0, str, i);
    }

    public static void setUserConfig(Context context, String str, int i) {
        setUserConfig(context, 0, str, i);
    }

    public static int getUserConfig(Context context, int i, String str, int i2) {
        try {
            return Integer.parseInt(getUserConfig(context, i, str, ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return i2;
        }
    }

    public static void setUserConfig(Context context, int i, String str, int i2) {
        if (getUserConfig(context, i, str, -1) == i2) {
            return;
        }
        Uri build = URI.buildUpon().fragment("simslot" + i).build();
        ContentValues contentValues = new ContentValues();
        contentValues.put(str, Integer.valueOf(i2));
        context.getContentResolver().insert(build, contentValues);
    }

    @Deprecated
    public static boolean getUserConfig(Context context, String str, boolean z) {
        return getUserConfig(context, 0, str, z);
    }

    public static boolean getUserConfig(Context context, int i, String str, boolean z) {
        Cursor query = context.getContentResolver().query(URI.buildUpon().fragment("simslot" + i).build(), new String[]{str}, null, null, null);
        if (query != null) {
            try {
                if (query.moveToFirst()) {
                    z = "true".equals(query.getString(0));
                }
            } catch (Throwable th) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (query != null) {
            query.close();
        }
        return z;
    }

    @Deprecated
    public static void setUserConfig(Context context, String str, boolean z) {
        setUserConfig(context, 0, str, z);
    }

    public static void setUserConfig(Context context, int i, String str, boolean z) {
        if (getUserConfig(context, i, str, false) == z) {
            return;
        }
        Uri build = URI.buildUpon().fragment("simslot" + i).build();
        ContentValues contentValues = new ContentValues();
        contentValues.put(str, z ? "true" : "false");
        context.getContentResolver().insert(build, contentValues);
    }
}
