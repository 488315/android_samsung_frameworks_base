package com.android.systemui.keyguard;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.wallpaper.WallpaperUtils;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardEditModeControllerImpl$$ExternalSyntheticLambda2 implements Function2 {
    public final /* synthetic */ KeyguardEditModeControllerImpl f$0;
    public final /* synthetic */ View f$1;
    public final /* synthetic */ ImageView f$2;
    public final /* synthetic */ ImageView f$3;
    public final /* synthetic */ FrameLayout f$4;

    public /* synthetic */ KeyguardEditModeControllerImpl$$ExternalSyntheticLambda2(KeyguardEditModeControllerImpl keyguardEditModeControllerImpl, View view, ImageView imageView, ImageView imageView2, FrameLayout frameLayout) {
        this.f$0 = keyguardEditModeControllerImpl;
        this.f$1 = view;
        this.f$2 = imageView;
        this.f$3 = imageView2;
        this.f$4 = frameLayout;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int min;
        int max;
        final View view = this.f$1;
        final ImageView imageView = this.f$2;
        final ImageView imageView2 = this.f$3;
        final FrameLayout frameLayout = this.f$4;
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        int i = KeyguardEditModeControllerImpl.$r8$clinit;
        Log.d("KeyguardEditModeController", "updateViews SA=" + booleanValue + " enterVI=" + booleanValue2);
        final KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = this.f$0;
        if (booleanValue) {
            final Bitmap wallpaperBitmap = keyguardEditModeControllerImpl.getWallpaperBitmap(view.getContext(), booleanValue2);
            if (wallpaperBitmap != null) {
                if (booleanValue2) {
                    keyguardEditModeControllerImpl.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$bind$1$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardEditModeControllerImpl.access$saveWallpaperBitmap(KeyguardEditModeControllerImpl.this, view.getContext(), wallpaperBitmap);
                        }
                    });
                }
                imageView2.setImageBitmap(wallpaperBitmap);
                Context context = view.getContext();
                Point realSize = keyguardEditModeControllerImpl.displayLifecycle.getRealSize();
                if (context.getResources().getConfiguration().orientation == 2) {
                    min = Math.max(realSize.x, realSize.y);
                    max = Math.min(realSize.x, realSize.y);
                } else {
                    min = Math.min(realSize.x, realSize.y);
                    max = Math.max(realSize.x, realSize.y);
                }
                int i2 = min / 2;
                int i3 = max / 2;
                boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
                Bitmap.Config config = wallpaperBitmap.getConfig();
                Bitmap.Config config2 = Bitmap.Config.ARGB_8888;
                if (config != config2) {
                    wallpaperBitmap = wallpaperBitmap.copy(config2, true);
                }
                int width = wallpaperBitmap.getWidth();
                int height = wallpaperBitmap.getHeight();
                int round = Math.round(i2 * 0.1f);
                int round2 = Math.round(i3 * 0.1f);
                if (width > round || height > round2) {
                    wallpaperBitmap = Bitmap.createScaledBitmap(wallpaperBitmap, round, round2, true);
                }
                try {
                    RenderScript create = RenderScript.create(context);
                    Allocation createFromBitmap = Allocation.createFromBitmap(create, wallpaperBitmap, Allocation.MipmapControl.MIPMAP_NONE, 1);
                    Allocation createTyped = Allocation.createTyped(create, createFromBitmap.getType());
                    ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
                    create2.setRadius(25.0f);
                    create2.setInput(createFromBitmap);
                    create2.forEach(createTyped);
                    createTyped.copyTo(wallpaperBitmap);
                    create.destroy();
                    createFromBitmap.destroy();
                    createTyped.destroy();
                    create2.destroy();
                } catch (RSRuntimeException e) {
                    e.printStackTrace();
                }
                Log.d("getBlurBitmap ", (wallpaperBitmap.getByteCount() / 1024) + "KB");
                imageView.setImageBitmap(wallpaperBitmap);
                imageView.setVisibility(0);
                frameLayout.setVisibility(0);
            }
        } else {
            imageView.postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$bind$1$2
                @Override // java.lang.Runnable
                public final void run() {
                    Log.d("KeyguardEditModeController", "updateViews() request to hide view.");
                    ImageView imageView3 = imageView;
                    imageView3.setImageBitmap(null);
                    imageView3.setVisibility(8);
                    imageView2.setImageBitmap(null);
                    frameLayout.setVisibility(8);
                }
            }, 100L);
            Log.d("KeyguardEditModeController", "updateViews() call semSendWallpaperCommand.");
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(view.getContext());
            Bundle bundle = new Bundle();
            bundle.putString("stateBackupRequestId", keyguardEditModeControllerImpl.backupWallpaperRequestId);
            wallpaperManager.semSendWallpaperCommand(2, "samsung.android.wallpaper.restorerunningstate", bundle);
        }
        return Unit.INSTANCE;
    }
}
