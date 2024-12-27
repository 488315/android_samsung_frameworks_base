package com.android.systemui.keyguardimage;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.android.systemui.keyguardimage.ImageOptionCreator;
import com.android.systemui.util.LogUtil;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class KeyguardImageProvider extends ContentProvider {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ImageCreator[] mClockImageCreator;
    public ImageCreator[] mCreatorsForFixedShortcut;
    public ImageCreator[] mCreatorsForWallpaper;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public boolean mWasShortcutEnabled = false;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class MyWriter implements ContentProvider.PipeDataWriter {
        private MyWriter() {
        }

        @Override // android.content.ContentProvider.PipeDataWriter
        public final void writeDataToPipe(ParcelFileDescriptor parcelFileDescriptor, Uri uri, String str, Bundle bundle, Object obj) {
            ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream;
            Bitmap bitmap = (Bitmap) obj;
            int startTime = LogUtil.startTime(-1);
            try {
                autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
            } catch (Exception e) {
                Log.w("KeyguardImageProvider", "MyWriter, fail to write to pipe", e);
            }
            try {
                Log.i("KeyguardImageProvider", "writer, mimeType: " + str);
                bitmap.compress("image/jpeg".equals(str) ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.PNG, 100, autoCloseOutputStream);
                autoCloseOutputStream.close();
                LogUtil.endTime(startTime, "KeyguardImageProvider", "writing done", new Object[0]);
            } catch (Throwable th) {
                try {
                    autoCloseOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }

        public /* synthetic */ MyWriter(int i) {
            this();
        }
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        ImageOptionCreator.ImageOption createImageOption = ImageOptionCreator.createImageOption(getContext(), uri, true);
        return (createImageOption == null || createImageOption.type != 1) ? "image/png" : "image/jpeg";
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:126:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01a1  */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.ParcelFileDescriptor openFile(android.net.Uri r17, java.lang.String r18) {
        /*
            Method dump skipped, instructions count: 566
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguardimage.KeyguardImageProvider.openFile(android.net.Uri, java.lang.String):android.os.ParcelFileDescriptor");
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
