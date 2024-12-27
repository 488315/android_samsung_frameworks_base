package com.android.server.textclassifier;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.Pair;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class IconsContentProvider extends ContentProvider {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final IconsContentProvider$$ExternalSyntheticLambda0 mWriter =
            new ContentProvider
                    .PipeDataWriter() { // from class:
                                        // com.android.server.textclassifier.IconsContentProvider$$ExternalSyntheticLambda0
                @Override // android.content.ContentProvider.PipeDataWriter
                public final void writeDataToPipe(
                        ParcelFileDescriptor parcelFileDescriptor,
                        Uri uri,
                        String str,
                        Bundle bundle,
                        Object obj) {
                    IconsContentProvider iconsContentProvider = IconsContentProvider.this;
                    Pair pair = (Pair) obj;
                    int i = IconsContentProvider.$r8$clinit;
                    iconsContentProvider.getClass();
                    try {
                        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream =
                                new ParcelFileDescriptor.AutoCloseOutputStream(
                                        parcelFileDescriptor);
                        try {
                            IconsUriHelper.ResourceInfo resourceInfo =
                                    (IconsUriHelper.ResourceInfo) pair.first;
                            IconsContentProvider.getBitmap(
                                            Icon.createWithResource(
                                                            resourceInfo.packageName,
                                                            resourceInfo.id)
                                                    .loadDrawableAsUser(
                                                            iconsContentProvider.getContext(),
                                                            ((Integer) pair.second).intValue()))
                                    .compress(
                                            Bitmap.CompressFormat.PNG, 100, autoCloseOutputStream);
                            autoCloseOutputStream.close();
                        } finally {
                        }
                    } catch (Exception e) {
                        Log.e("IconsContentProvider", "Error retrieving icon for uri: " + uri, e);
                    }
                }
            };

    public static Bitmap getBitmap(Drawable drawable) {
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            throw new IllegalStateException("The icon is zero-sized");
        }
        Bitmap createBitmap =
                Bitmap.createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    public static boolean sameIcon(Drawable drawable, Drawable drawable2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = getBitmap(drawable);
        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;
        bitmap.compress(compressFormat, 100, byteArrayOutputStream);
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        getBitmap(drawable2).compress(compressFormat, 100, byteArrayOutputStream2);
        return Arrays.equals(
                byteArrayOutputStream.toByteArray(), byteArrayOutputStream2.toByteArray());
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return "image/png";
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x009d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.ParcelFileDescriptor openFile(
            android.net.Uri r9, java.lang.String r10) {
        /*
            r8 = this;
            com.android.server.textclassifier.IconsUriHelper r10 = com.android.server.textclassifier.IconsUriHelper.sSingleton
            r10.getClass()
            java.lang.String r0 = "content"
            java.lang.String r1 = r9.getScheme()
            boolean r0 = r0.equals(r1)
            r1 = 0
            if (r0 != 0) goto L15
        L12:
            r10 = r1
            goto L87
        L15:
            java.lang.String r0 = "com.android.textclassifier.icons"
            java.lang.String r2 = r9.getAuthority()
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L22
            goto L12
        L22:
            java.util.List r0 = r9.getPathSegments()
            java.util.Map r2 = r10.mPackageIds     // Catch: java.lang.Exception -> L6e
            monitor-enter(r2)     // Catch: java.lang.Exception -> L6e
            r3 = 0
            java.lang.Object r3 = r0.get(r3)     // Catch: java.lang.Throwable -> L68
            java.lang.String r3 = (java.lang.String) r3     // Catch: java.lang.Throwable -> L68
            r4 = 1
            java.lang.Object r0 = r0.get(r4)     // Catch: java.lang.Throwable -> L68
            java.lang.String r0 = (java.lang.String) r0     // Catch: java.lang.Throwable -> L68
            int r0 = java.lang.Integer.parseInt(r0)     // Catch: java.lang.Throwable -> L68
            java.util.Map r4 = r10.mPackageIds     // Catch: java.lang.Throwable -> L68
            android.util.ArrayMap r4 = (android.util.ArrayMap) r4     // Catch: java.lang.Throwable -> L68
            java.util.Set r4 = r4.keySet()     // Catch: java.lang.Throwable -> L68
            java.util.Iterator r4 = r4.iterator()     // Catch: java.lang.Throwable -> L68
        L47:
            boolean r5 = r4.hasNext()     // Catch: java.lang.Throwable -> L68
            if (r5 == 0) goto L6a
            java.lang.Object r5 = r4.next()     // Catch: java.lang.Throwable -> L68
            java.lang.String r5 = (java.lang.String) r5     // Catch: java.lang.Throwable -> L68
            java.util.Map r6 = r10.mPackageIds     // Catch: java.lang.Throwable -> L68
            android.util.ArrayMap r6 = (android.util.ArrayMap) r6     // Catch: java.lang.Throwable -> L68
            java.lang.Object r6 = r6.get(r5)     // Catch: java.lang.Throwable -> L68
            boolean r6 = r3.equals(r6)     // Catch: java.lang.Throwable -> L68
            if (r6 == 0) goto L47
            com.android.server.textclassifier.IconsUriHelper$ResourceInfo r10 = new com.android.server.textclassifier.IconsUriHelper$ResourceInfo     // Catch: java.lang.Throwable -> L68
            r10.<init>(r5, r0)     // Catch: java.lang.Throwable -> L68
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L68
            goto L87
        L68:
            r10 = move-exception
            goto L6c
        L6a:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L68
            goto L12
        L6c:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L68
            throw r10     // Catch: java.lang.Exception -> L6e
        L6e:
            r10 = move-exception
            java.lang.String r0 = "IconsUriHelper"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Could not get resource info. Reason: "
            r2.<init>(r3)
            java.lang.String r10 = r10.getMessage()
            r2.append(r10)
            java.lang.String r10 = r2.toString()
            android.util.Log.v(r0, r10)
            goto L12
        L87:
            if (r10 != 0) goto L9d
            java.lang.String r8 = "IconsContentProvider"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r0 = "No icon found for uri: "
            r10.<init>(r0)
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            android.util.Log.e(r8, r9)
            return r1
        L9d:
            android.util.Pair r6 = new android.util.Pair     // Catch: java.io.IOException -> Lb6
            int r0 = android.os.UserHandle.getCallingUserId()     // Catch: java.io.IOException -> Lb6
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch: java.io.IOException -> Lb6
            r6.<init>(r10, r0)     // Catch: java.io.IOException -> Lb6
            java.lang.String r4 = "image/png"
            com.android.server.textclassifier.IconsContentProvider$$ExternalSyntheticLambda0 r7 = r8.mWriter     // Catch: java.io.IOException -> Lb6
            r5 = 0
            r2 = r8
            r3 = r9
            android.os.ParcelFileDescriptor r8 = r2.openPipeHelper(r3, r4, r5, r6, r7)     // Catch: java.io.IOException -> Lb6
            return r8
        Lb6:
            r8 = move-exception
            java.lang.String r10 = "IconsContentProvider"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "Error opening pipe helper for icon at uri: "
            r0.<init>(r2)
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            android.util.Log.e(r10, r9, r8)
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.textclassifier.IconsContentProvider.openFile(android.net.Uri,"
                    + " java.lang.String):android.os.ParcelFileDescriptor");
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
