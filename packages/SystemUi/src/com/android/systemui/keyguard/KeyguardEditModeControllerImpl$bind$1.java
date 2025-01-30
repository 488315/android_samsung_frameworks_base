package com.android.systemui.keyguard;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.systemui.keyguardimage.ImageOptionCreator;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;
import java.io.File;
import java.io.FileOutputStream;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
final class KeyguardEditModeControllerImpl$bind$1 extends Lambda implements Function2 {
    final /* synthetic */ ImageView $blurView;
    final /* synthetic */ FrameLayout $editModeContainer;
    final /* synthetic */ ImageView $editModeWallpaperView;
    final /* synthetic */ View $root;
    final /* synthetic */ KeyguardEditModeControllerImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardEditModeControllerImpl$bind$1(KeyguardEditModeControllerImpl keyguardEditModeControllerImpl, View view, ImageView imageView, ImageView imageView2, FrameLayout frameLayout) {
        super(2);
        this.this$0 = keyguardEditModeControllerImpl;
        this.$root = view;
        this.$blurView = imageView;
        this.$editModeWallpaperView = imageView2;
        this.$editModeContainer = frameLayout;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        long j;
        int min;
        int max;
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        Log.m138d("KeyguardEditModeController", "updateViews SA=" + booleanValue + " enterVI=" + booleanValue2);
        if (booleanValue) {
            KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = this.this$0;
            Context context = this.$root.getContext();
            final Bitmap bitmap = null;
            if (!keyguardEditModeControllerImpl.settingsHelper.isUltraPowerSavingMode()) {
                if (booleanValue2) {
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    keyguardEditModeControllerImpl.wallpaperRequestID = String.valueOf(elapsedRealtime);
                    Bundle bundle = new Bundle();
                    bundle.putString(KnoxContainerManager.CONTAINER_CREATION_REQUEST_ID, keyguardEditModeControllerImpl.wallpaperRequestID);
                    bundle.putLong("requestTime", elapsedRealtime);
                    wallpaperManager.semSendWallpaperCommand(2, "samsung.android.wallpaper.backuprunningstate", bundle);
                }
                ImageOptionCreator.ImageOption imageOption = new ImageOptionCreator.ImageOption();
                Point realSize = keyguardEditModeControllerImpl.displayLifecycle.getRealSize();
                int i = context.getResources().getConfiguration().orientation;
                if (i == 2) {
                    imageOption.width = Math.max(realSize.x, realSize.y);
                    imageOption.height = Math.min(realSize.x, realSize.y);
                } else {
                    imageOption.width = Math.min(realSize.x, realSize.y);
                    imageOption.height = Math.max(realSize.x, realSize.y);
                }
                int i2 = imageOption.width;
                int i3 = imageOption.height;
                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("getWallpaperBitmap, orientation=", i, ", w=", i2, ", h=");
                m45m.append(i3);
                Log.m138d("KeyguardEditModeController", m45m.toString());
                imageOption.rotation = 0;
                if (!booleanValue2) {
                    Log.m141i("KeyguardEditModeController", "getWallpaperBitmap, parcelFileDescriptor : " + keyguardEditModeControllerImpl.backupWallpaperPreviewPFD + ", requestId : " + keyguardEditModeControllerImpl.backupWallpaperRequestId);
                    try {
                        ParcelFileDescriptor parcelFileDescriptor = keyguardEditModeControllerImpl.backupWallpaperPreviewPFD;
                        Bitmap decodeFileDescriptor = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor != null ? parcelFileDescriptor.getFileDescriptor() : null);
                        ParcelFileDescriptor parcelFileDescriptor2 = keyguardEditModeControllerImpl.backupWallpaperPreviewPFD;
                        if (parcelFileDescriptor2 != null) {
                            parcelFileDescriptor2.close();
                        }
                        bitmap = decodeFileDescriptor;
                    } catch (Exception e) {
                        Log.m140e("KeyguardEditModeController", String.valueOf(e));
                        imageOption.useThumbnail = true;
                    }
                }
                bitmap = keyguardEditModeControllerImpl.wallpaperImageCreator.createImage(imageOption, null);
            }
            if (bitmap != null) {
                final KeyguardEditModeControllerImpl keyguardEditModeControllerImpl2 = this.this$0;
                ImageView imageView = this.$editModeWallpaperView;
                ImageView imageView2 = this.$blurView;
                FrameLayout frameLayout = this.$editModeContainer;
                final View view = this.$root;
                if (booleanValue2) {
                    keyguardEditModeControllerImpl2.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$bind$1$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardEditModeControllerImpl keyguardEditModeControllerImpl3 = KeyguardEditModeControllerImpl.this;
                            Context context2 = view.getContext();
                            Bitmap bitmap2 = bitmap;
                            int i4 = KeyguardEditModeControllerImpl.$r8$clinit;
                            keyguardEditModeControllerImpl3.getClass();
                            try {
                                File file = new File(new File(context2.getFilesDir().getAbsolutePath(), "keyguard_edit.jpg").getAbsolutePath());
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                                fileOutputStream.flush();
                                fileOutputStream.close();
                                Uri uriForFile = FileProvider.getUriForFile(context2, "com.android.systemui.fileprovider", file);
                                keyguardEditModeControllerImpl3.wallpaperBitmapUri = uriForFile;
                                context2.grantUriPermission("com.samsung.android.app.dressroom", uriForFile, 1);
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    });
                }
                imageView.setImageBitmap(bitmap);
                Context context2 = view.getContext();
                Point realSize2 = keyguardEditModeControllerImpl2.displayLifecycle.getRealSize();
                if (context2.getResources().getConfiguration().orientation == 2) {
                    min = Math.max(realSize2.x, realSize2.y);
                    max = Math.min(realSize2.x, realSize2.y);
                } else {
                    min = Math.min(realSize2.x, realSize2.y);
                    max = Math.max(realSize2.x, realSize2.y);
                }
                Bitmap blurredBitmap = WallpaperUtils.getBlurredBitmap(context2, bitmap, min / 2, max / 2);
                Log.m138d("getBlurBitmap ", (blurredBitmap.getByteCount() / 1024) + "KB");
                imageView2.setImageBitmap(blurredBitmap);
                imageView2.setVisibility(0);
                frameLayout.setVisibility(0);
            }
        } else {
            if (WallpaperUtils.isLiveWallpaperAppliedOnLock(this.$blurView.getContext())) {
                Context context3 = this.$blurView.getContext();
                if ("infinity".equals(new SemWallpaperProperties(context3, (WallpaperUtils.isSubDisplay() ? 16 : 4) | 2, context3.getUserId()).getContentType())) {
                    j = 100;
                    final ImageView imageView3 = this.$blurView;
                    final ImageView imageView4 = this.$editModeWallpaperView;
                    final FrameLayout frameLayout2 = this.$editModeContainer;
                    imageView3.postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$bind$1.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Log.m138d("KeyguardEditModeController", "updateViews() request to hide view.");
                            ImageView imageView5 = imageView3;
                            imageView5.setImageBitmap(null);
                            imageView5.setVisibility(8);
                            imageView4.setImageBitmap(null);
                            frameLayout2.setVisibility(8);
                        }
                    }, j);
                    Log.m138d("KeyguardEditModeController", "updateViews() call semSendWallpaperCommand.");
                    WallpaperManager wallpaperManager2 = WallpaperManager.getInstance(this.$root.getContext());
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("stateBackupRequestId", this.this$0.backupWallpaperRequestId);
                    wallpaperManager2.semSendWallpaperCommand(2, "samsung.android.wallpaper.restorerunningstate", bundle2);
                }
            }
            j = 0;
            final ImageView imageView32 = this.$blurView;
            final ImageView imageView42 = this.$editModeWallpaperView;
            final FrameLayout frameLayout22 = this.$editModeContainer;
            imageView32.postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$bind$1.2
                @Override // java.lang.Runnable
                public final void run() {
                    Log.m138d("KeyguardEditModeController", "updateViews() request to hide view.");
                    ImageView imageView5 = imageView32;
                    imageView5.setImageBitmap(null);
                    imageView5.setVisibility(8);
                    imageView42.setImageBitmap(null);
                    frameLayout22.setVisibility(8);
                }
            }, j);
            Log.m138d("KeyguardEditModeController", "updateViews() call semSendWallpaperCommand.");
            WallpaperManager wallpaperManager22 = WallpaperManager.getInstance(this.$root.getContext());
            Bundle bundle22 = new Bundle();
            bundle22.putString("stateBackupRequestId", this.this$0.backupWallpaperRequestId);
            wallpaperManager22.semSendWallpaperCommand(2, "samsung.android.wallpaper.restorerunningstate", bundle22);
        }
        return Unit.INSTANCE;
    }
}
