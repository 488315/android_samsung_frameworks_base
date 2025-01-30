package com.samsung.android.server.audio;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/* loaded from: classes2.dex */
public abstract class SamsungMusicHelper {
    /* JADX WARN: Removed duplicated region for block: B:24:0x007a A[Catch: Exception -> 0x0089, TRY_ENTER, TRY_LEAVE, TryCatch #3 {Exception -> 0x0089, blocks: (B:20:0x0053, B:24:0x007a, B:29:0x0088, B:34:0x0085, B:36:0x0059, B:39:0x0060, B:22:0x0072, B:31:0x0080), top: B:19:0x0053, inners: #0, #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isScreenOffMusicEnabled(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        boolean z = false;
        boolean z2 = context.getPackageManager().resolveContentProvider("com.sec.android.app.music.shared", 0) != null;
        boolean z3 = context.getPackageManager().resolveContentProvider("com.samsung.android.app.music.chn.setting", 0) != null;
        if (!z2 && !z3) {
            Log.i("AS.SamsungMusicHelper", "ScreenOffMusicProvider does not exist");
        } else {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("content://");
                sb.append(z3 ? "com.samsung.android.app.music.chn.setting" : "com.sec.android.app.music.shared");
                try {
                    Cursor query = contentResolver.query(Uri.withAppendedPath(Uri.parse(sb.toString()), "setting/ready_screen_off_music"), null, null, null, null);
                    if (query != null) {
                        try {
                            if (query.getCount() > 0) {
                                query.moveToFirst();
                                z = "true".equals(query.getString(1));
                                if (query != null) {
                                    query.close();
                                }
                            }
                        } finally {
                        }
                    }
                    Log.e("AS.SamsungMusicHelper", "screen off music query failed");
                    if (query != null) {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (NullPointerException unused) {
            }
        }
        return z;
    }
}
