package com.samsung.server.wallpaper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/* loaded from: classes2.dex */
public class Knox {
    public final Context mContext;

    public Knox(Context context) {
        Log.m129d("Knox", "Knox");
        this.mContext = context;
    }

    public boolean isWallpaperChangeAllowed(boolean z) {
        Cursor query = this.mContext.getContentResolver().query(Uri.parse("content://com.sec.knox.provider/RestrictionPolicy4"), null, "isWallpaperChangeAllowed", z ? new String[]{"true"} : new String[]{"false"}, null);
        boolean z2 = true;
        if (query != null) {
            try {
                query.moveToFirst();
                z2 = true ^ "false".equals(query.getString(query.getColumnIndex("isWallpaperChangeAllowed")));
            } catch (Exception unused) {
            } catch (Throwable th) {
                query.close();
                throw th;
            }
            query.close();
        }
        Log.m129d("Knox", "isWallpaperChangeAllowed " + z2);
        return z2;
    }
}
