package com.android.settingslib.avatarpicker;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import com.android.settingslib.avatarpicker.AvatarPhotoController;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import libcore.io.Streams;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AvatarPhotoController$$ExternalSyntheticLambda0 implements Callable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AvatarPhotoController f$0;
    public final /* synthetic */ Uri f$1;

    public /* synthetic */ AvatarPhotoController$$ExternalSyntheticLambda0(AvatarPhotoController avatarPhotoController, Uri uri, int i) {
        this.$r8$classId = i;
        this.f$0 = avatarPhotoController;
        this.f$1 = uri;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        switch (this.$r8$classId) {
            case 0:
                Uri uri = this.f$1;
                AvatarPhotoController avatarPhotoController = this.f$0;
                ContentResolver contentResolver = ((AvatarPhotoController.ContextInjectorImpl) avatarPhotoController.mContextInjector).mContext.getContentResolver();
                try {
                    Streams.copy(contentResolver.openInputStream(uri), contentResolver.openOutputStream(avatarPhotoController.mPreCropPictureUri));
                    return avatarPhotoController.mPreCropPictureUri;
                } catch (IOException e) {
                    Log.w("AvatarPhotoController", "Failed to copy photo", e);
                    return null;
                }
            default:
                Uri uri2 = this.f$1;
                AvatarPhotoController avatarPhotoController2 = this.f$0;
                avatarPhotoController2.getClass();
                Bitmap.Config config = Bitmap.Config.ARGB_8888;
                int i = avatarPhotoController2.mPhotoSize;
                Bitmap createBitmap = Bitmap.createBitmap(i, i, config);
                Canvas canvas = new Canvas(createBitmap);
                AvatarPhotoController.ContextInjector contextInjector = avatarPhotoController2.mContextInjector;
                InputStream openInputStream = ((AvatarPhotoController.ContextInjectorImpl) contextInjector).mContext.getContentResolver().openInputStream(uri2);
                try {
                    Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream);
                    if (openInputStream != null) {
                        openInputStream.close();
                    }
                    if (decodeStream == null) {
                        Log.e("AvatarPhotoController", "Image data could not be decoded");
                        return null;
                    }
                    int i2 = -1;
                    try {
                        i2 = new ExifInterface(((AvatarPhotoController.ContextInjectorImpl) contextInjector).mContext.getContentResolver().openInputStream(uri2)).getAttributeInt("Orientation", -1);
                    } catch (IOException e2) {
                        Log.e("AvatarPhotoController", "Error while getting rotation", e2);
                    }
                    int i3 = i2 != 3 ? i2 != 6 ? i2 != 8 ? 0 : 270 : 90 : 180;
                    int min = Math.min(decodeStream.getWidth(), decodeStream.getHeight());
                    int width = (decodeStream.getWidth() - min) / 2;
                    int height = (decodeStream.getHeight() - min) / 2;
                    Matrix matrix = new Matrix();
                    float f = i;
                    matrix.setRectToRect(new RectF(width, height, width + min, height + min), new RectF(0.0f, 0.0f, f, f), Matrix.ScaleToFit.CENTER);
                    float f2 = f / 2.0f;
                    matrix.postRotate(i3, f2, f2);
                    canvas.drawBitmap(decodeStream, matrix, new Paint());
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(new File(avatarPhotoController2.mImagesDir, "CropEditUserPhoto.jpg"));
                        createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        return createBitmap;
                    } catch (IOException e3) {
                        Log.e("AvatarPhotoController", "Cannot create temp file", e3);
                        return createBitmap;
                    }
                } catch (Throwable th) {
                    if (openInputStream != null) {
                        try {
                            openInputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
        }
    }
}
