package com.android.settingslib.users;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import com.android.settingslib.utils.ThreadUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class EditUserPhotoController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EditUserPhotoController f$0;
    public final /* synthetic */ Parcelable f$1;

    public /* synthetic */ EditUserPhotoController$$ExternalSyntheticLambda2(EditUserPhotoController editUserPhotoController, Parcelable parcelable, int i) {
        this.$r8$classId = i;
        this.f$0 = editUserPhotoController;
        this.f$1 = parcelable;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0036, code lost:
    
        if (r6 != null) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0047, code lost:
    
        r6.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004b, code lost:
    
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004c, code lost:
    
        android.util.Log.w("EditUserPhotoController", "Cannot close image stream", r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0045, code lost:
    
        if (r6 == null) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0060 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        EditUserPhotoController editUserPhotoController;
        FileNotFoundException e;
        InputStream inputStream;
        Bitmap bitmap;
        switch (this.$r8$classId) {
            case 0:
                this.f$0.onPhotoProcessed((Bitmap) this.f$1);
                return;
            case 1:
                this.f$0.onPhotoProcessed((Bitmap) this.f$1);
                return;
            default:
                editUserPhotoController = this.f$0;
                Uri uri = (Uri) this.f$1;
                editUserPhotoController.getClass();
                InputStream inputStream2 = null;
                bitmap = null;
                bitmap = null;
                try {
                    inputStream = editUserPhotoController.mActivity.getContentResolver().openInputStream(uri);
                    try {
                        try {
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            break;
                        } catch (FileNotFoundException e2) {
                            e = e2;
                            Log.w("EditUserPhotoController", "Cannot find image file", e);
                            break;
                        }
                    } catch (Throwable th) {
                        inputStream2 = inputStream;
                        th = th;
                        if (inputStream2 != null) {
                            try {
                                inputStream2.close();
                            } catch (IOException e3) {
                                Log.w("EditUserPhotoController", "Cannot close image stream", e3);
                            }
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e4) {
                    e = e4;
                    inputStream = null;
                } catch (Throwable th2) {
                    th = th2;
                    if (inputStream2 != null) {
                    }
                    throw th;
                }
        }
        if (bitmap != null) {
            ThreadUtils.postOnMainThread(new EditUserPhotoController$$ExternalSyntheticLambda2(editUserPhotoController, bitmap, 1));
        }
    }
}
