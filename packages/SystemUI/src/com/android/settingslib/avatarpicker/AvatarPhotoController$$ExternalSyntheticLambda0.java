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
    public final Object call() throws IOException {
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
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i, config);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                AvatarPhotoController.ContextInjector contextInjector = avatarPhotoController2.mContextInjector;
                InputStream inputStreamOpenInputStream = ((AvatarPhotoController.ContextInjectorImpl) contextInjector).mContext.getContentResolver().openInputStream(uri2);
                try {
                    Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream);
                    if (inputStreamOpenInputStream != null) {
                        inputStreamOpenInputStream.close();
                    }
                    if (bitmapDecodeStream == null) {
                        Log.e("AvatarPhotoController", "Image data could not be decoded");
                        return null;
                    }
                    int attributeInt = -1;
                    try {
                        attributeInt = new ExifInterface(((AvatarPhotoController.ContextInjectorImpl) contextInjector).mContext.getContentResolver().openInputStream(uri2)).getAttributeInt("Orientation", -1);
                    } catch (IOException e2) {
                        Log.e("AvatarPhotoController", "Error while getting rotation", e2);
                    }
                    int i2 = attributeInt != 3 ? attributeInt != 6 ? attributeInt != 8 ? 0 : 270 : 90 : 180;
                    int iMin = Math.min(bitmapDecodeStream.getWidth(), bitmapDecodeStream.getHeight());
                    int width = (bitmapDecodeStream.getWidth() - iMin) / 2;
                    int height = (bitmapDecodeStream.getHeight() - iMin) / 2;
                    Matrix matrix = new Matrix();
                    float f = i;
                    matrix.setRectToRect(new RectF(width, height, width + iMin, height + iMin), new RectF(0.0f, 0.0f, f, f), Matrix.ScaleToFit.CENTER);
                    float f2 = f / 2.0f;
                    matrix.postRotate(i2, f2, f2);
                    canvas.drawBitmap(bitmapDecodeStream, matrix, new Paint());
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(new File(avatarPhotoController2.mImagesDir, "CropEditUserPhoto.jpg"));
                        bitmapCreateBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        return bitmapCreateBitmap;
                    } catch (IOException e3) {
                        Log.e("AvatarPhotoController", "Cannot create temp file", e3);
                        return bitmapCreateBitmap;
                    }
                } catch (Throwable th) {
                    if (inputStreamOpenInputStream != null) {
                        try {
                            inputStreamOpenInputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
        }
    }
}
