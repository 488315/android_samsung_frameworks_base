package com.android.settingslib.avatarpicker;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import androidx.core.content.FileProvider;
import com.android.settingslib.avatarpicker.AvatarPhotoController;
import com.android.systemui.R;
import com.google.common.util.concurrent.AbstractListeningExecutorService;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public class AvatarPhotoController {
    public final AvatarUi mAvatarUi;
    public final ContextInjector mContextInjector;
    public final Uri mCropPictureUri;
    public final File mImagesDir;
    public final int mPhotoSize;
    public final Uri mPreCropPictureUri;
    public final Uri mTakePictureUri;

    /* renamed from: com.android.settingslib.avatarpicker.AvatarPhotoController$1, reason: invalid class name */
    public class AnonymousClass1 implements FutureCallback {
        public final /* synthetic */ boolean val$delayBeforeCrop;

        public AnonymousClass1(boolean z) {
            this.val$delayBeforeCrop = z;
        }

        @Override // com.google.common.util.concurrent.FutureCallback
        public final void onFailure(Throwable th) {
            Log.e("AvatarPhotoController", "Error performing copy-and-crop", th);
        }

        @Override // com.google.common.util.concurrent.FutureCallback
        public final void onSuccess(Object obj) {
            if (((Uri) obj) == null) {
                return;
            }
            Runnable runnable = new Runnable() { // from class: com.android.settingslib.avatarpicker.AvatarPhotoController$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AvatarPhotoController avatarPhotoController = AvatarPhotoController.this;
                    AvatarPickerActivity avatarPickerActivity = ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController.mAvatarUi).mActivity;
                    if (avatarPickerActivity.isFinishing() || avatarPickerActivity.isDestroyed()) {
                        return;
                    }
                    avatarPhotoController.cropPhoto(avatarPhotoController.mPreCropPictureUri);
                }
            };
            if (this.val$delayBeforeCrop) {
                ((ContextInjectorImpl) AvatarPhotoController.this.mContextInjector).mContext.getMainThreadHandler().postDelayed(runnable, 150L);
            } else {
                runnable.run();
            }
        }
    }

    public interface AvatarUi {
    }

    public class AvatarUiImpl implements AvatarUi {
        public final AvatarPickerActivity mActivity;

        public AvatarUiImpl(AvatarPickerActivity avatarPickerActivity) {
            this.mActivity = avatarPickerActivity;
        }
    }

    public interface ContextInjector {
    }

    public class ContextInjectorImpl implements ContextInjector {
        public final Context mContext;
        public final String mFileAuthority;

        public ContextInjectorImpl(Context context, String str) {
            this.mContext = context;
            this.mFileAuthority = str;
        }

        public final Uri createTempImageUri(String str, File file, boolean z) {
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
        ContextInjectorImpl contextInjectorImpl = (ContextInjectorImpl) contextInjector;
        File file = new File(contextInjectorImpl.mContext.getCacheDir(), "multi_user");
        this.mImagesDir = file;
        file.mkdir();
        boolean z2 = !z;
        this.mPreCropPictureUri = contextInjectorImpl.createTempImageUri("PreCropEditUserPhoto.jpg", file, z2);
        this.mCropPictureUri = contextInjectorImpl.createTempImageUri("CropEditUserPhoto.jpg", file, z2);
        this.mTakePictureUri = contextInjectorImpl.createTempImageUri("TakeEditUserPhoto.jpg", file, z2);
        this.mPhotoSize = ((AvatarUiImpl) avatarUi).mActivity.getResources().getDimensionPixelSize(R.dimen.sec_avatar_picker_photo_popup_min_width);
    }

    public final void copyAndCropPhoto(Uri uri, boolean z) {
        ListenableFuture listenableFutureSubmit = ((AbstractListeningExecutorService) ThreadUtils.getBackgroundExecutor()).submit((Callable) new AvatarPhotoController$$ExternalSyntheticLambda0(this, uri, 0));
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(z);
        listenableFutureSubmit.addListener(new Futures.CallbackListener(listenableFutureSubmit, anonymousClass1), ((ContextInjectorImpl) this.mContextInjector).mContext.getMainExecutor());
    }

    public final void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        Uri uri2 = this.mCropPictureUri;
        intent.putExtra("output", uri2);
        intent.addFlags(3);
        intent.setClipData(ClipData.newRawUri("output", uri2));
        intent.putExtra("crop", "true");
        boolean z = true;
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        int i = this.mPhotoSize;
        intent.putExtra("outputX", i);
        intent.putExtra("outputY", i);
        try {
            StrictMode.disableDeathOnFileUriExposure();
            AvatarPickerActivity avatarPickerActivity = ((AvatarUiImpl) this.mAvatarUi).mActivity;
            List<ResolveInfo> listQueryIntentActivities = avatarPickerActivity.getPackageManager().queryIntentActivities(intent, 1048576);
            if (listQueryIntentActivities.isEmpty()) {
                Log.w("AvatarPhotoController", "No system package activity could be found for code 1003");
                z = false;
            } else {
                intent.setPackage(listQueryIntentActivities.get(0).activityInfo.packageName);
                avatarPickerActivity.startActivityForResult(intent, 1003);
            }
            if (z) {
                return;
            }
            StrictMode.enableDeathOnFileUriExposure();
            ListenableFuture listenableFutureSubmit = ((AbstractListeningExecutorService) ThreadUtils.getBackgroundExecutor()).submit((Callable) new AvatarPhotoController$$ExternalSyntheticLambda0(this, uri, 1));
            listenableFutureSubmit.addListener(new Futures.CallbackListener(listenableFutureSubmit, new FutureCallback() { // from class: com.android.settingslib.avatarpicker.AvatarPhotoController.2
                @Override // com.google.common.util.concurrent.FutureCallback
                public final void onFailure(Throwable th) {
                    Log.e("AvatarPhotoController", "Error performing internal crop", th);
                }

                @Override // com.google.common.util.concurrent.FutureCallback
                public final void onSuccess(Object obj) {
                    if (((Bitmap) obj) != null) {
                        AvatarPhotoController avatarPhotoController = AvatarPhotoController.this;
                        AvatarUi avatarUi = avatarPhotoController.mAvatarUi;
                        Uri uri3 = avatarPhotoController.mCropPictureUri;
                        AvatarPickerActivity avatarPickerActivity2 = ((AvatarUiImpl) avatarUi).mActivity;
                        avatarPickerActivity2.getClass();
                        Intent intent2 = new Intent();
                        intent2.setData(uri3);
                        avatarPickerActivity2.setResult(-1, intent2);
                        avatarPickerActivity2.finish();
                    }
                }
            }), ((ContextInjectorImpl) this.mContextInjector).mContext.getMainExecutor());
        } finally {
            StrictMode.enableDeathOnFileUriExposure();
        }
    }
}
