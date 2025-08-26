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
import java.io.IOException;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

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
        int iMin;
        int iMax;
        final View view = this.f$1;
        final ImageView imageView = this.f$2;
        final ImageView imageView2 = this.f$3;
        final FrameLayout frameLayout = this.f$4;
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
        int i = KeyguardEditModeControllerImpl.$r8$clinit;
        Log.d("KeyguardEditModeController", "updateViews SA=" + zBooleanValue + " enterVI=" + zBooleanValue2);
        final KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = this.f$0;
        if (zBooleanValue) {
            final Bitmap wallpaperBitmap = keyguardEditModeControllerImpl.getWallpaperBitmap(view.getContext(), zBooleanValue2);
            if (wallpaperBitmap != null) {
                if (zBooleanValue2) {
                    keyguardEditModeControllerImpl.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$bind$1$1$1
                        @Override // java.lang.Runnable
                        public final void run() throws IOException {
                            KeyguardEditModeControllerImpl.access$saveWallpaperBitmap(keyguardEditModeControllerImpl, view.getContext(), wallpaperBitmap);
                        }
                    });
                }
                imageView2.setImageBitmap(wallpaperBitmap);
                Context context = view.getContext();
                Point realSize = keyguardEditModeControllerImpl.displayLifecycle.getRealSize();
                if (context.getResources().getConfiguration().orientation == 2) {
                    iMin = Math.max(realSize.x, realSize.y);
                    iMax = Math.min(realSize.x, realSize.y);
                } else {
                    iMin = Math.min(realSize.x, realSize.y);
                    iMax = Math.max(realSize.x, realSize.y);
                }
                int i2 = iMin / 2;
                int i3 = iMax / 2;
                boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
                Bitmap.Config config = wallpaperBitmap.getConfig();
                Bitmap.Config config2 = Bitmap.Config.ARGB_8888;
                if (config != config2) {
                    wallpaperBitmap = wallpaperBitmap.copy(config2, true);
                }
                int width = wallpaperBitmap.getWidth();
                int height = wallpaperBitmap.getHeight();
                int iRound = Math.round(i2 * 0.1f);
                int iRound2 = Math.round(i3 * 0.1f);
                if (width > iRound || height > iRound2) {
                    wallpaperBitmap = Bitmap.createScaledBitmap(wallpaperBitmap, iRound, iRound2, true);
                }
                try {
                    RenderScript renderScriptCreate = RenderScript.create(context);
                    Allocation allocationCreateFromBitmap = Allocation.createFromBitmap(renderScriptCreate, wallpaperBitmap, Allocation.MipmapControl.MIPMAP_NONE, 1);
                    Allocation allocationCreateTyped = Allocation.createTyped(renderScriptCreate, allocationCreateFromBitmap.getType());
                    ScriptIntrinsicBlur scriptIntrinsicBlurCreate = ScriptIntrinsicBlur.create(renderScriptCreate, Element.U8_4(renderScriptCreate));
                    scriptIntrinsicBlurCreate.setRadius(25.0f);
                    scriptIntrinsicBlurCreate.setInput(allocationCreateFromBitmap);
                    scriptIntrinsicBlurCreate.forEach(allocationCreateTyped);
                    allocationCreateTyped.copyTo(wallpaperBitmap);
                    renderScriptCreate.destroy();
                    allocationCreateFromBitmap.destroy();
                    allocationCreateTyped.destroy();
                    scriptIntrinsicBlurCreate.destroy();
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
