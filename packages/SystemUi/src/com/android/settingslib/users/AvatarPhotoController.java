package com.android.settingslib.users;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import androidx.core.content.FileProvider;
import com.android.settingslib.users.AvatarPhotoController;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AvatarPhotoController {
    public final AvatarUi mAvatarUi;
    public final ContextInjector mContextInjector;
    public final Uri mCropPictureUri;
    public final File mImagesDir;
    public final int mPhotoSize;
    public final Uri mPreCropPictureUri;
    public final Uri mTakePictureUri;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface AvatarUi {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AvatarUiImpl implements AvatarUi {
        public final AvatarPickerActivity mActivity;

        public AvatarUiImpl(AvatarPickerActivity avatarPickerActivity) {
            this.mActivity = avatarPickerActivity;
        }

        public final boolean startSystemActivityForResult(Intent intent) {
            AvatarPickerActivity avatarPickerActivity = this.mActivity;
            List<ResolveInfo> queryIntentActivities = avatarPickerActivity.getPackageManager().queryIntentActivities(intent, QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING);
            if (queryIntentActivities.isEmpty()) {
                Log.w("AvatarPhotoController", "No system package activity could be found for code 1003");
                return false;
            }
            intent.setPackage(queryIntentActivities.get(0).activityInfo.packageName);
            avatarPickerActivity.startActivityForResult(intent, 1003);
            return true;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ContextInjector {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ContextInjectorImpl implements ContextInjector {
        public final Context mContext;
        public final String mFileAuthority;

        public ContextInjectorImpl(Context context, String str) {
            this.mContext = context;
            this.mFileAuthority = str;
        }

        public final Uri createTempImageUri(File file, String str, boolean z) {
            File file2 = new File(file, str);
            if (z) {
                file2.delete();
            }
            return FileProvider.getUriForFile(this.mContext, this.mFileAuthority, file2);
        }
    }

    public AvatarPhotoController(AvatarUi avatarUi, ContextInjector contextInjector, boolean z) {
        this.mAvatarUi = avatarUi;
        this.mContextInjector = contextInjector;
        File file = new File(((ContextInjectorImpl) contextInjector).mContext.getCacheDir(), "multi_user");
        this.mImagesDir = file;
        file.mkdir();
        this.mPreCropPictureUri = ((ContextInjectorImpl) contextInjector).createTempImageUri(file, "PreCropEditUserPhoto.jpg", !z);
        this.mCropPictureUri = ((ContextInjectorImpl) contextInjector).createTempImageUri(file, "CropEditUserPhoto.jpg", !z);
        this.mTakePictureUri = ((ContextInjectorImpl) contextInjector).createTempImageUri(file, "TakeEditUserPhoto.jpg", !z);
        this.mPhotoSize = ((AvatarUiImpl) avatarUi).mActivity.getResources().getDimensionPixelSize(R.dimen.update_user_photo_popup_min_width);
    }

    public final void cropPhoto(final Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        Uri uri2 = this.mCropPictureUri;
        intent.putExtra("output", uri2);
        intent.addFlags(3);
        intent.setClipData(ClipData.newRawUri("output", uri2));
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        int i = this.mPhotoSize;
        intent.putExtra("outputX", i);
        intent.putExtra("outputY", i);
        try {
            StrictMode.disableDeathOnFileUriExposure();
            if (((AvatarUiImpl) this.mAvatarUi).startSystemActivityForResult(intent)) {
                return;
            }
            try {
                ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.settingslib.users.AvatarPhotoController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AvatarPhotoController avatarPhotoController = AvatarPhotoController.this;
                        Uri uri3 = uri;
                        AvatarPhotoController.ContextInjector contextInjector = avatarPhotoController.mContextInjector;
                        Bitmap.Config config = Bitmap.Config.ARGB_8888;
                        int i2 = avatarPhotoController.mPhotoSize;
                        Bitmap createBitmap = Bitmap.createBitmap(i2, i2, config);
                        Canvas canvas = new Canvas(createBitmap);
                        try {
                            Bitmap decodeStream = BitmapFactory.decodeStream(((AvatarPhotoController.ContextInjectorImpl) contextInjector).mContext.getContentResolver().openInputStream(uri3));
                            if (decodeStream != null) {
                                int i3 = -1;
                                try {
                                    i3 = new ExifInterface(((AvatarPhotoController.ContextInjectorImpl) contextInjector).mContext.getContentResolver().openInputStream(uri3)).getAttributeInt("Orientation", -1);
                                } catch (IOException e) {
                                    Log.e("AvatarPhotoController", "Error while getting rotation", e);
                                }
                                int i4 = 0;
                                int i5 = i3 != 3 ? i3 != 6 ? i3 != 8 ? 0 : 270 : 90 : 180;
                                int min = Math.min(decodeStream.getWidth(), decodeStream.getHeight());
                                int width = (decodeStream.getWidth() - min) / 2;
                                int height = (decodeStream.getHeight() - min) / 2;
                                Matrix matrix = new Matrix();
                                float f = i2;
                                matrix.setRectToRect(new RectF(width, height, width + min, height + min), new RectF(0.0f, 0.0f, f, f), Matrix.ScaleToFit.CENTER);
                                float f2 = f / 2.0f;
                                matrix.postRotate(i5, f2, f2);
                                canvas.drawBitmap(decodeStream, matrix, new Paint());
                                try {
                                    FileOutputStream fileOutputStream = new FileOutputStream(new File(avatarPhotoController.mImagesDir, "CropEditUserPhoto.jpg"));
                                    createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                } catch (IOException e2) {
                                    Log.e("AvatarPhotoController", "Cannot create temp file", e2);
                                }
                                ThreadUtils.postOnMainThread(new AvatarPhotoController$$ExternalSyntheticLambda2(avatarPhotoController, i4));
                            }
                        } catch (FileNotFoundException unused) {
                        }
                    }
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                Log.e("AvatarPhotoController", "Error performing internal crop", e);
            }
        } finally {
            StrictMode.enableDeathOnFileUriExposure();
        }
    }
}
