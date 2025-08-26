package com.android.settingslib.users;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.google.common.util.concurrent.AbstractListeningExecutorService;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class EditUserPhotoController {
    public final Activity mActivity;
    public final ActivityStarter mActivityStarter;
    public String mCachedDrawablePath;
    public final ListeningExecutorService mExecutorService;
    public final String mFileAuthority;
    public final ImageView mImageView;
    public final File mImagesDir;
    public Bitmap mNewUserPhotoBitmap;
    public Drawable mNewUserPhotoDrawable;

    /* renamed from: -$$Nest$monPhotoProcessed, reason: not valid java name */
    public static void m978$$Nest$monPhotoProcessed(final EditUserPhotoController editUserPhotoController, Bitmap bitmap) {
        if (bitmap == null) {
            editUserPhotoController.getClass();
            return;
        }
        editUserPhotoController.mNewUserPhotoBitmap = bitmap;
        ((AbstractListeningExecutorService) editUserPhotoController.mExecutorService).submit(new Runnable() { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                EditUserPhotoController editUserPhotoController2 = this.f$0;
                File file = null;
                if (editUserPhotoController2.mNewUserPhotoBitmap != null) {
                    try {
                        File file2 = new File(editUserPhotoController2.mImagesDir, "NewUserPhoto.png");
                        FileOutputStream fileOutputStream = new FileOutputStream(file2);
                        editUserPhotoController2.mNewUserPhotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        file = file2;
                    } catch (IOException e) {
                        Log.e("EditUserPhotoController", "Cannot create temp file", e);
                    }
                }
                editUserPhotoController2.mCachedDrawablePath = file.getPath();
            }
        });
        CircleFramedDrawable circleFramedDrawable = new CircleFramedDrawable(editUserPhotoController.mNewUserPhotoBitmap, editUserPhotoController.mImageView.getContext().getResources().getDimensionPixelSize(R.dimen.user_photo_size_in_user_info_dialog));
        editUserPhotoController.mNewUserPhotoDrawable = circleFramedDrawable;
        editUserPhotoController.mImageView.setImageDrawable(circleFramedDrawable);
    }

    public EditUserPhotoController(Activity activity, ActivityStarter activityStarter, ImageView imageView, Bitmap bitmap, Drawable drawable, String str) {
        this(activity, activityStarter, imageView, bitmap, drawable, str, true);
    }

    public EditUserPhotoController(Activity activity, ActivityStarter activityStarter, ImageView imageView, Bitmap bitmap, Drawable drawable, String str, final boolean z) {
        this.mActivity = activity;
        this.mActivityStarter = activityStarter;
        this.mFileAuthority = str;
        File file = new File(activity.getCacheDir(), "multi_user");
        this.mImagesDir = file;
        file.mkdir();
        this.mImageView = imageView;
        imageView.setOnClickListener(new View.OnClickListener(z) { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EditUserPhotoController editUserPhotoController = this.f$0;
                Intent intent = new Intent("com.android.avatarpicker.FULL_SCREEN_ACTIVITY");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setPackage(editUserPhotoController.mImageView.getContext().getApplicationContext().getPackageName());
                intent.putExtra("file_authority", editUserPhotoController.mFileAuthority);
                editUserPhotoController.mActivityStarter.startActivityForResult(intent);
            }
        });
        this.mNewUserPhotoBitmap = bitmap;
        this.mNewUserPhotoDrawable = drawable;
        this.mExecutorService = ThreadUtils.getBackgroundExecutor();
    }
}
