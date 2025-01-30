package com.android.keyguard.clock;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.io.FileNotFoundException;
import java.util.List;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ClockOptionsProvider extends ContentProvider {
    public Provider mClockInfosProvider;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MyWriter implements ContentProvider.PipeDataWriter {
        private MyWriter() {
        }

        public /* synthetic */ MyWriter(int i) {
            this();
        }

        @Override // android.content.ContentProvider.PipeDataWriter
        public final void writeDataToPipe(ParcelFileDescriptor parcelFileDescriptor, Uri uri, String str, Bundle bundle, Object obj) {
            Bitmap bitmap = (Bitmap) obj;
            try {
                ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, autoCloseOutputStream);
                    autoCloseOutputStream.close();
                } finally {
                }
            } catch (Exception e) {
                Log.w("ClockOptionsProvider", "fail to write to pipe", e);
            }
        }
    }

    public ClockOptionsProvider() {
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        return pathSegments.size() > 0 ? ("preview".equals(pathSegments.get(0)) || "thumbnail".equals(pathSegments.get(0))) ? "image/png" : "vnd.android.cursor.dir/clock_faces" : "vnd.android.cursor.dir/clock_faces";
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public final ParcelFileDescriptor openFile(Uri uri, String str) {
        ClockInfo clockInfo;
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() == 2) {
            int i = 0;
            if ("preview".equals(pathSegments.get(0)) || "thumbnail".equals(pathSegments.get(0))) {
                String str2 = pathSegments.get(1);
                if (TextUtils.isEmpty(str2)) {
                    throw new FileNotFoundException("Invalid preview url, missing id");
                }
                List list = (List) this.mClockInfosProvider.get();
                int i2 = 0;
                while (true) {
                    if (i2 >= list.size()) {
                        clockInfo = null;
                        break;
                    }
                    if (str2.equals(((ClockInfo) list.get(i2)).mId)) {
                        clockInfo = (ClockInfo) list.get(i2);
                        break;
                    }
                    i2++;
                }
                if (clockInfo != null) {
                    return openPipeHelper(uri, "image/png", null, "preview".equals(pathSegments.get(0)) ? (Bitmap) clockInfo.mPreview.get() : (Bitmap) clockInfo.mThumbnail.get(), new MyWriter(i));
                }
                throw new FileNotFoundException("Invalid preview url, id not found");
            }
        }
        throw new FileNotFoundException("Invalid preview url");
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        if (!"/list_options".equals(uri.getPath())) {
            return null;
        }
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"name", UniversalCredentialUtil.AGENT_TITLE, "id", "thumbnail", "preview"});
        List list = (List) this.mClockInfosProvider.get();
        for (int i = 0; i < list.size(); i++) {
            ClockInfo clockInfo = (ClockInfo) list.get(i);
            MatrixCursor.RowBuilder add = matrixCursor.newRow().add("name", clockInfo.mName).add(UniversalCredentialUtil.AGENT_TITLE, (String) clockInfo.mTitle.get());
            String str3 = clockInfo.mId;
            add.add("id", str3).add("thumbnail", new Uri.Builder().scheme("content").authority("com.android.keyguard.clock").appendPath("thumbnail").appendPath(str3).build()).add("preview", new Uri.Builder().scheme("content").authority("com.android.keyguard.clock").appendPath("preview").appendPath(str3).build());
        }
        return matrixCursor;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    public ClockOptionsProvider(Provider provider) {
        this.mClockInfosProvider = provider;
    }
}
