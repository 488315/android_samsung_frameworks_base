package com.android.settingslib.users;

import android.content.Intent;
import android.net.Uri;
import com.android.settingslib.users.AvatarPhotoController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class AvatarPhotoController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AvatarPhotoController f$0;

    public /* synthetic */ AvatarPhotoController$$ExternalSyntheticLambda2(AvatarPhotoController avatarPhotoController, int i) {
        this.$r8$classId = i;
        this.f$0 = avatarPhotoController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AvatarPhotoController avatarPhotoController = this.f$0;
                Uri uri = avatarPhotoController.mCropPictureUri;
                AvatarPickerActivity avatarPickerActivity = ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController.mAvatarUi).mActivity;
                avatarPickerActivity.getClass();
                Intent intent = new Intent();
                intent.setData(uri);
                avatarPickerActivity.setResult(-1, intent);
                avatarPickerActivity.finish();
                break;
            default:
                AvatarPhotoController avatarPhotoController2 = this.f$0;
                AvatarPickerActivity avatarPickerActivity2 = ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController2.mAvatarUi).mActivity;
                if (!(avatarPickerActivity2.isFinishing() || avatarPickerActivity2.isDestroyed())) {
                    avatarPhotoController2.cropPhoto(avatarPhotoController2.mPreCropPictureUri);
                    break;
                }
                break;
        }
    }
}
