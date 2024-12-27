package com.android.systemui.keyguard;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.content.FileProvider;
import com.android.systemui.util.leak.LeakReporter;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.io.File;
import java.io.FileOutputStream;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

final class KeyguardEditModeControllerImpl$bind$1 extends Lambda implements Function2 {
    final /* synthetic */ ImageView $blurView;
    final /* synthetic */ FrameLayout $editModeContainer;
    final /* synthetic */ ImageView $editModeWallpaperView;
    final /* synthetic */ View $root;
    final /* synthetic */ KeyguardEditModeControllerImpl this$0;

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
        int min;
        int max;
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        Log.d("KeyguardEditModeController", "updateViews SA=" + booleanValue + " enterVI=" + booleanValue2);
        if (booleanValue) {
            final Bitmap access$getWallpaperBitmap = KeyguardEditModeControllerImpl.access$getWallpaperBitmap(this.this$0, booleanValue2, this.$root.getContext());
            if (access$getWallpaperBitmap != null) {
                final KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = this.this$0;
                ImageView imageView = this.$editModeWallpaperView;
                ImageView imageView2 = this.$blurView;
                FrameLayout frameLayout = this.$editModeContainer;
                final View view = this.$root;
                if (booleanValue2) {
                    keyguardEditModeControllerImpl.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$bind$1$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardEditModeControllerImpl keyguardEditModeControllerImpl2 = KeyguardEditModeControllerImpl.this;
                            Context context = view.getContext();
                            Bitmap bitmap = access$getWallpaperBitmap;
                            int i = KeyguardEditModeControllerImpl.$r8$clinit;
                            keyguardEditModeControllerImpl2.getClass();
                            try {
                                File file = new File(new File(context.getFilesDir().getAbsolutePath(), "keyguard_edit.jpg").getAbsolutePath());
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                try {
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                                    fileOutputStream.flush();
                                    Unit unit = Unit.INSTANCE;
                                    CloseableKt.closeFinally(fileOutputStream, null);
                                    Uri uriForFile = FileProvider.getUriForFile(context, LeakReporter.FILEPROVIDER_AUTHORITY, file);
                                    keyguardEditModeControllerImpl2.wallpaperBitmapUri = uriForFile;
                                    context.grantUriPermission("com.samsung.android.app.dressroom", uriForFile, 1);
                                } finally {
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                imageView.setImageBitmap(access$getWallpaperBitmap);
                Context context = view.getContext();
                Point realSize = keyguardEditModeControllerImpl.displayLifecycle.getRealSize();
                if (context.getResources().getConfiguration().orientation == 2) {
                    min = Math.max(realSize.x, realSize.y);
                    max = Math.min(realSize.x, realSize.y);
                } else {
                    min = Math.min(realSize.x, realSize.y);
                    max = Math.max(realSize.x, realSize.y);
                }
                int i = min / 2;
                int i2 = max / 2;
                boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
                Bitmap.Config config = access$getWallpaperBitmap.getConfig();
                Bitmap.Config config2 = Bitmap.Config.ARGB_8888;
                if (config != config2) {
                    access$getWallpaperBitmap = access$getWallpaperBitmap.copy(config2, true);
                }
                int width = access$getWallpaperBitmap.getWidth();
                int height = access$getWallpaperBitmap.getHeight();
                int round = Math.round(i * 0.1f);
                int round2 = Math.round(i2 * 0.1f);
                if (width > round || height > round2) {
                    access$getWallpaperBitmap = Bitmap.createScaledBitmap(access$getWallpaperBitmap, round, round2, true);
                }
                try {
                    RenderScript create = RenderScript.create(context);
                    Allocation createFromBitmap = Allocation.createFromBitmap(create, access$getWallpaperBitmap, Allocation.MipmapControl.MIPMAP_NONE, 1);
                    Allocation createTyped = Allocation.createTyped(create, createFromBitmap.getType());
                    ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
                    create2.setRadius(25.0f);
                    create2.setInput(createFromBitmap);
                    create2.forEach(createTyped);
                    createTyped.copyTo(access$getWallpaperBitmap);
                    create.destroy();
                    createFromBitmap.destroy();
                    createTyped.destroy();
                    create2.destroy();
                } catch (RSRuntimeException e) {
                    e.printStackTrace();
                }
                Log.d("getBlurBitmap ", (access$getWallpaperBitmap.getByteCount() / 1024) + "KB");
                imageView2.setImageBitmap(access$getWallpaperBitmap);
                imageView2.setVisibility(0);
                frameLayout.setVisibility(0);
            }
        } else {
            long j = (WallpaperUtils.isLiveWallpaper() && WallpaperUtils.mIsInfinityLiveWallpaper) ? 100L : 0L;
            final ImageView imageView3 = this.$blurView;
            final ImageView imageView4 = this.$editModeWallpaperView;
            final FrameLayout frameLayout2 = this.$editModeContainer;
            imageView3.postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$bind$1.2
                @Override // java.lang.Runnable
                public final void run() {
                    Log.d("KeyguardEditModeController", "updateViews() request to hide view.");
                    ImageView imageView5 = imageView3;
                    imageView5.setImageBitmap(null);
                    imageView5.setVisibility(8);
                    imageView4.setImageBitmap(null);
                    frameLayout2.setVisibility(8);
                }
            }, j);
            Log.d("KeyguardEditModeController", "updateViews() call semSendWallpaperCommand.");
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.$root.getContext());
            Bundle bundle = new Bundle();
            bundle.putString("stateBackupRequestId", this.this$0.backupWallpaperRequestId);
            wallpaperManager.semSendWallpaperCommand(2, "samsung.android.wallpaper.restorerunningstate", bundle);
        }
        return Unit.INSTANCE;
    }
}
