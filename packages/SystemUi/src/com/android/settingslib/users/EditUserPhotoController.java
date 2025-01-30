package com.android.settingslib.users;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.user.CreateUserActivity;
import com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda0;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.File;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EditUserPhotoController {
    public final Activity mActivity;
    public final ActivityStarter mActivityStarter;
    public final String mFileAuthority;
    public final ImageView mImageView;
    public final File mImagesDir;
    public Bitmap mNewUserPhotoBitmap;
    public Drawable mNewUserPhotoDrawable;

    public EditUserPhotoController(Activity activity, ActivityStarter activityStarter, ImageView imageView, Bitmap bitmap, Drawable drawable, String str) {
        this.mActivity = activity;
        this.mActivityStarter = activityStarter;
        this.mFileAuthority = str;
        File file = new File(activity.getCacheDir(), "multi_user");
        this.mImagesDir = file;
        file.mkdir();
        this.mImageView = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EditUserPhotoController editUserPhotoController = EditUserPhotoController.this;
                editUserPhotoController.getClass();
                final Intent intent = new Intent(editUserPhotoController.mImageView.getContext(), (Class<?>) AvatarPickerActivity.class);
                intent.putExtra("file_authority", editUserPhotoController.mFileAuthority);
                CreateUserActivity$$ExternalSyntheticLambda0 createUserActivity$$ExternalSyntheticLambda0 = (CreateUserActivity$$ExternalSyntheticLambda0) editUserPhotoController.mActivityStarter;
                createUserActivity$$ExternalSyntheticLambda0.getClass();
                int i = CreateUserActivity.$r8$clinit;
                final CreateUserActivity createUserActivity = createUserActivity$$ExternalSyntheticLambda0.f$0;
                createUserActivity.getClass();
                createUserActivity.mActivityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda3
                    public final /* synthetic */ int f$2 = VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI;

                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                    public final boolean onDismiss() {
                        CreateUserActivity createUserActivity2 = CreateUserActivity.this;
                        createUserActivity2.mCreateUserDialogController.mWaitingForActivityResult = true;
                        createUserActivity2.startActivityForResult(intent, this.f$2);
                        return true;
                    }
                }, null, true);
            }
        });
        this.mNewUserPhotoBitmap = bitmap;
        this.mNewUserPhotoDrawable = drawable;
    }

    public final void onPhotoProcessed(Bitmap bitmap) {
        if (bitmap != null) {
            this.mNewUserPhotoBitmap = bitmap;
            ImageView imageView = this.mImageView;
            CircleFramedDrawable circleFramedDrawable = new CircleFramedDrawable(bitmap, imageView.getContext().getResources().getDimensionPixelSize(R.dimen.user_photo_size_in_user_info_dialog));
            this.mNewUserPhotoDrawable = circleFramedDrawable;
            imageView.setImageDrawable(circleFramedDrawable);
        }
    }
}
