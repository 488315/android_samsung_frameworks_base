package com.android.systemui.highlight;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.android.systemui.highlight.database.InteractionDatabase;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public class HighlightContentProvider extends ContentProvider {
    public InteractionDatabase mDbHelper;

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        this.mDbHelper = new InteractionDatabase(getContext());
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        String path = uri.getPath();
        int i = StringUtils.$r8$clinit;
        if (path == null) {
            path = "";
        }
        String[] strArrSplit = path.split("/");
        String str3 = strArrSplit.length > 1 ? strArrSplit[strArrSplit.length - 1] : null;
        Log.d("InteractionContentProvider", "query uri : " + uri + ", table : " + str3);
        if (str3 == null) {
            return null;
        }
        return this.mDbHelper.getReadableDatabase().query(str3, null, str, strArr2, null, null, str2);
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
