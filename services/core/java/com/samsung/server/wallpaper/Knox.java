package com.samsung.server.wallpaper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public final class Knox {
    public final Context mContext;

    public Knox(Context context) {
        Log.d("Knox", "Knox");
        this.mContext = context;
    }

    public final boolean isWallpaperChangeAllowed() {
        boolean z = true;
        Cursor query =
                this.mContext
                        .getContentResolver()
                        .query(
                                Uri.parse("content://com.sec.knox.provider/RestrictionPolicy4"),
                                null,
                                "isWallpaperChangeAllowed",
                                new String[] {"true"},
                                null);
        if (query != null) {
            try {
                query.moveToFirst();
                z =
                        true
                                ^ "false"
                                        .equals(
                                                query.getString(
                                                        query.getColumnIndex(
                                                                "isWallpaperChangeAllowed")));
            } catch (Exception unused) {
            } catch (Throwable th) {
                query.close();
                throw th;
            }
            query.close();
        }
        Log.d("Knox", "isWallpaperChangeAllowed " + z);
        return z;
    }
}
