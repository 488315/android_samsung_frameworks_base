package com.android.systemui.wallpaper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.wallpaper.KeyguardWallpaperPreviewActivity;
import com.android.systemui.wallpaper.view.KeyguardAnimatedWallpaper;
import com.android.systemui.wallpaper.view.KeyguardMotionWallpaper;
import com.android.systemui.wallpaper.view.SystemUIWallpaper;
import java.io.ByteArrayOutputStream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardWallpaperPreviewActivity$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardWallpaperPreviewActivity f$0;

    public /* synthetic */ KeyguardWallpaperPreviewActivity$$ExternalSyntheticLambda0(KeyguardWallpaperPreviewActivity keyguardWallpaperPreviewActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardWallpaperPreviewActivity;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00b4  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardWallpaperPreviewActivity keyguardWallpaperPreviewActivity = this.f$0;
                int i = keyguardWallpaperPreviewActivity.mWallpaperType;
                KeyguardWallpaperPreviewActivity.C36741 c36741 = keyguardWallpaperPreviewActivity.mWallpaperResultCallback;
                boolean z = true;
                if (i != 1) {
                    if (i != 2) {
                        if (i == 4) {
                            if (TextUtils.isEmpty(keyguardWallpaperPreviewActivity.mPackageName)) {
                                Log.e("KeyguardWallpaperPreviewActivity", "initPreviewArea(): mPackageName is empty");
                                z = false;
                            } else {
                                keyguardWallpaperPreviewActivity.mWallpaperView = new KeyguardAnimatedWallpaper(keyguardWallpaperPreviewActivity.mContext, keyguardWallpaperPreviewActivity.mPackageName, true, keyguardWallpaperPreviewActivity.mPreviewArea.getWidth(), keyguardWallpaperPreviewActivity.mPreviewArea.getHeight(), null, keyguardWallpaperPreviewActivity.mExecutor, null, null, keyguardWallpaperPreviewActivity.mCurrentWhich);
                            }
                        }
                        if (!z) {
                            Log.d("KeyguardWallpaperPreviewActivity", "initPreviewArea, fail.");
                            keyguardWallpaperPreviewActivity.finish();
                            break;
                        } else {
                            SystemUIWallpaper systemUIWallpaper = keyguardWallpaperPreviewActivity.mWallpaperView;
                            if (systemUIWallpaper != null) {
                                keyguardWallpaperPreviewActivity.mRoundContainer.addView(systemUIWallpaper);
                                keyguardWallpaperPreviewActivity.mPreviewArea.addView(keyguardWallpaperPreviewActivity.mRoundContainer);
                                keyguardWallpaperPreviewActivity.mWallpaperView.onResume();
                                break;
                            }
                        }
                    } else if (TextUtils.isEmpty(keyguardWallpaperPreviewActivity.mColorInfo)) {
                        Log.e("KeyguardWallpaperPreviewActivity", "initPreviewArea(): mColorInfo is empty");
                        z = false;
                        if (!z) {
                        }
                    } else {
                        keyguardWallpaperPreviewActivity.mWallpaperView = new KeyguardMotionWallpaper(keyguardWallpaperPreviewActivity.mContext, null, c36741, keyguardWallpaperPreviewActivity.mExecutor, null, null, keyguardWallpaperPreviewActivity.mColorInfo, true, keyguardWallpaperPreviewActivity.mCurrentWhich);
                        if (!z) {
                        }
                    }
                } else if (TextUtils.isEmpty(keyguardWallpaperPreviewActivity.mPackageName)) {
                    Log.e("KeyguardWallpaperPreviewActivity", "initPreviewArea(): mPackageName is empty");
                    z = false;
                    if (!z) {
                    }
                } else {
                    keyguardWallpaperPreviewActivity.mWallpaperView = new KeyguardMotionWallpaper(keyguardWallpaperPreviewActivity.mContext, null, c36741, keyguardWallpaperPreviewActivity.mExecutor, null, keyguardWallpaperPreviewActivity.mPackageName, null, true, keyguardWallpaperPreviewActivity.mCurrentWhich);
                    if (!z) {
                    }
                }
                break;
            case 1:
                KeyguardWallpaperPreviewActivity keyguardWallpaperPreviewActivity2 = this.f$0;
                if (!KeyguardWallpaperPreviewActivity.sIsActivityAlive) {
                    keyguardWallpaperPreviewActivity2.finish();
                    break;
                } else {
                    keyguardWallpaperPreviewActivity2.getClass();
                    break;
                }
            default:
                KeyguardWallpaperPreviewActivity keyguardWallpaperPreviewActivity3 = this.f$0;
                boolean z2 = KeyguardWallpaperPreviewActivity.sIsActivityAlive;
                keyguardWallpaperPreviewActivity3.getClass();
                Intent intent = new Intent();
                intent.setAction("com.samsung.intent.action.COLOR_THEME_SETUP_WIZARD");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ((BitmapDrawable) keyguardWallpaperPreviewActivity3.mBackgroundImageView.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
                intent.putExtra("blur_bitmap", byteArrayOutputStream.toByteArray());
                intent.putExtra("wallpaper_type", 2);
                keyguardWallpaperPreviewActivity3.startActivity(intent);
                keyguardWallpaperPreviewActivity3.finish();
                break;
        }
    }
}
