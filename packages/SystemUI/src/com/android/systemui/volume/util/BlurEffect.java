package com.android.systemui.volume.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.Display;
import android.view.SemBlurInfo;
import android.view.View;
import android.widget.ImageView;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.VolumeDependencyBase;
import com.samsung.android.view.SemWindowManager;
import java.util.function.Supplier;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class BlurEffect {
    public final Context context;
    public final SemWindowManagerWrapper semWindowManagerWrapper;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public BlurEffect(Context context, VolumeDependencyBase volumeDependencyBase) {
        this.context = context;
        VolumeDependency volumeDependency = (VolumeDependency) volumeDependencyBase;
        this.semWindowManagerWrapper = (SemWindowManagerWrapper) volumeDependency.get(SemWindowManagerWrapper.class);
    }

    public static void hideBlur(View view) {
        ViewVisibilityUtil.INSTANCE.getClass();
        view.setVisibility(4);
        view.semSetBlurInfo(null);
    }

    public static void setRealTimeBlur(View view, int i, float f, int i2) {
        if (!BasicRune.VOLUME_PARTIAL_BLUR || ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled()) {
            ViewVisibilityUtil.INSTANCE.getClass();
            view.setVisibility(4);
            view.semSetBlurInfo(null);
        } else {
            SemBlurInfo semBlurInfoBuild = new SemBlurInfo.Builder(0).setRadius(256).setBackgroundColor(i).setColorCurvePreset(i2).setBackgroundCornerRadius(f).build();
            ViewVisibilityUtil.INSTANCE.getClass();
            view.setVisibility(0);
            view.getForeground().setAlpha(30);
            view.semSetBlurInfo(semBlurInfoBuild);
        }
    }

    public final void setCapturedBlur(ImageView imageView, int i, Supplier supplier) {
        Bitmap bitmapScreenshot;
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled()) {
            ViewVisibilityUtil.INSTANCE.getClass();
            imageView.setVisibility(4);
            imageView.semSetBlurInfo(null);
            return;
        }
        int[] iArr = (int[]) supplier.get();
        int i2 = iArr[0];
        int i3 = iArr[1];
        int width = imageView.getWidth() + i2;
        int height = imageView.getHeight() + i3;
        Context context = this.context;
        Rect rect = new Rect(i2, i3, width, height);
        int height2 = ContextUtils.isLandscape(this.context) ? imageView.getHeight() : imageView.getWidth();
        int width2 = ContextUtils.isLandscape(this.context) ? imageView.getWidth() : imageView.getHeight();
        this.semWindowManagerWrapper.getClass();
        Display display = context.getDisplay();
        if (display == null || (bitmapScreenshot = SemWindowManager.getInstance().screenshot(display.getDisplayId(), 2036, true, rect, height2, width2, true, 0, true)) == null) {
            bitmapScreenshot = null;
        }
        if (bitmapScreenshot == null) {
            ViewVisibilityUtil.INSTANCE.getClass();
            imageView.setVisibility(4);
            imageView.semSetBlurInfo(null);
        } else {
            imageView.setClipToOutline(true);
            SemBlurInfo semBlurInfoBuild = new SemBlurInfo.Builder(1).setRadius(256).setColorCurvePreset(i).setBitmap(bitmapScreenshot).build();
            ViewVisibilityUtil.INSTANCE.getClass();
            imageView.setVisibility(0);
            imageView.getForeground().setAlpha(30);
            imageView.semSetBlurInfo(semBlurInfoBuild);
        }
    }
}
