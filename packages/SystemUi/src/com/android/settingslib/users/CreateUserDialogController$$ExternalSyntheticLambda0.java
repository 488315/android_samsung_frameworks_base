package com.android.settingslib.users;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class CreateUserDialogController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CreateUserDialogController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CreateUserDialogController$$ExternalSyntheticLambda0(CreateUserDialogController createUserDialogController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = createUserDialogController;
        this.f$1 = obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0055  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        File file;
        switch (this.$r8$classId) {
            case 0:
                CreateUserDialogController createUserDialogController = this.f$0;
                String str = (String) this.f$1;
                createUserDialogController.getClass();
                createUserDialogController.mSavedPhoto = BitmapFactory.decodeFile(new File(str).getAbsolutePath());
                break;
            default:
                CreateUserDialogController createUserDialogController2 = this.f$0;
                Bundle bundle = (Bundle) this.f$1;
                EditUserPhotoController editUserPhotoController = createUserDialogController2.mEditUserPhotoController;
                if (editUserPhotoController.mNewUserPhotoBitmap != null) {
                    try {
                        file = new File(editUserPhotoController.mImagesDir, "NewUserPhoto.png");
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        editUserPhotoController.mNewUserPhotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        Log.e("EditUserPhotoController", "Cannot create temp file", e);
                    }
                    if (file == null) {
                        bundle.putString("pending_photo", file.getPath());
                        break;
                    }
                }
                file = null;
                if (file == null) {
                }
                break;
        }
    }
}
