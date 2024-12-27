package com.samsung.server.wallpaper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Knox {
    public final Context mContext;

    public Knox(Context context) {
        Log.d("Knox", "Knox");
        this.mContext = context;
    }

    public final boolean isWallpaperChangeAllowed() {
        boolean z = true;
        Cursor query = this.mContext.getContentResolver().query(Uri.parse("content://com.sec.knox.provider/RestrictionPolicy4"), null, "isWallpaperChangeAllowed", new String[]{"true"}, null);
        if (query != null) {
            try {
                query.moveToFirst();
                z = true ^ "false".equals(query.getString(query.getColumnIndex("isWallpaperChangeAllowed")));
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
