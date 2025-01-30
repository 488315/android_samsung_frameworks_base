package com.android.systemui.volume.util;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.Display;
import android.view.SemBlurInfo;
import android.view.View;
import android.widget.ImageView;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.VolumeDependencyBase;
import com.samsung.android.view.SemWindowManager;
import java.util.function.Supplier;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BlurEffect {
    public final Context context;
    public final SemWindowManagerWrapper semWindowManagerWrapper;
    public final StatusBarWrapper statusBarWrapper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public BlurEffect(Context context, VolumeDependencyBase volumeDependencyBase) {
        this.context = context;
        VolumeDependency volumeDependency = (VolumeDependency) volumeDependencyBase;
        this.statusBarWrapper = (StatusBarWrapper) volumeDependency.get(StatusBarWrapper.class);
        this.semWindowManagerWrapper = (SemWindowManagerWrapper) volumeDependency.get(SemWindowManagerWrapper.class);
    }

    public static void setRealTimeBlur(float f, int i, View view) {
        if (!BasicRune.VOLUME_PARTIAL_BLUR || ((SettingsHelper) Dependency.get(SettingsHelper.class)).isReduceTransparencyEnabled()) {
            ViewVisibilityUtil.INSTANCE.getClass();
            view.setVisibility(4);
            view.semSetBlurInfo(null);
        } else {
            SemBlurInfo build = new SemBlurInfo.Builder(0).setRadius(256).setBackgroundColor(i).setBackgroundCornerRadius(f).build();
            ViewVisibilityUtil.INSTANCE.getClass();
            view.setVisibility(0);
            view.getForeground().setAlpha(30);
            view.semSetBlurInfo(build);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0157  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setCapturedBlur(ImageView imageView, Supplier supplier) {
        Bitmap bitmap;
        int i;
        int i2;
        int width;
        int height;
        Bitmap screenshot;
        if (((SettingsHelper) Dependency.get(SettingsHelper.class)).isReduceTransparencyEnabled()) {
            ViewVisibilityUtil.INSTANCE.getClass();
            imageView.setVisibility(4);
            imageView.semSetBlurInfo(null);
            return;
        }
        int[] iArr = (int[]) supplier.get();
        Context context = this.context;
        Display display = context.getDisplay();
        if (display != null) {
            int dimenInt = ContextUtils.getDimenInt(R.dimen.notification_custom_view_max_image_height_low_ram, context);
            int dimenInt2 = ContextUtils.getDimenInt(com.android.systemui.R.dimen.volume_panel_captured_blur_space, context);
            int cutoutHeight = this.statusBarWrapper.getCutoutHeight();
            SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
            int i3 = SettingsHelperExt.$r8$clinit;
            boolean z = settingsHelper.isNavigationBarGestureHintEnabled() || settingsHelper.isNavigationBarGestureWhileHidden();
            boolean z2 = DeviceType.isTablet() || ContextUtils.isScreenWideMobileDevice(context);
            if (display.getRotation() == 3) {
                i = iArr[1];
                int displayWidth = ((ContextUtils.getDisplayWidth(context) - iArr[0]) - imageView.getWidth()) + cutoutHeight + dimenInt2;
                if (z2 || z) {
                    dimenInt = 0;
                }
                i2 = displayWidth + dimenInt;
                width = imageView.getHeight() + i;
                height = imageView.getWidth();
            } else if (display.getRotation() == 1) {
                int displayHeight = (ContextUtils.getDisplayHeight(context) - imageView.getHeight()) - iArr[1];
                if (!z2 && !z) {
                    dimenInt = 0;
                }
                i = displayHeight + dimenInt;
                i2 = iArr[0];
                width = imageView.getHeight() + i;
                height = imageView.getWidth();
            } else {
                i = iArr[0];
                i2 = iArr[1];
                width = imageView.getWidth() + i;
                height = imageView.getHeight();
            }
            Rect rect = new Rect(i, i2, width, height + i2);
            int height2 = ContextUtils.isLandscape(context) ? imageView.getHeight() : imageView.getWidth();
            int width2 = ContextUtils.isLandscape(context) ? imageView.getWidth() : imageView.getHeight();
            this.semWindowManagerWrapper.getClass();
            Display display2 = context.getDisplay();
            if (display2 != null && (screenshot = SemWindowManager.getInstance().screenshot(display2.getDisplayId(), 2036, true, rect, height2, width2, true, display2.getRotation() * 90, true)) != null) {
                int width3 = screenshot.getWidth();
                int height3 = screenshot.getHeight();
                Matrix matrix = new Matrix();
                Intrinsics.checkNotNull(context.getDisplay());
                matrix.postRotate(360 - (r2.getRotation() * 90));
                Unit unit = Unit.INSTANCE;
                bitmap = Bitmap.createBitmap(screenshot, 0, 0, width3, height3, matrix, true);
                if (bitmap != null) {
                    ViewVisibilityUtil.INSTANCE.getClass();
                    imageView.setVisibility(4);
                    imageView.semSetBlurInfo(null);
                    return;
                } else {
                    imageView.setClipToOutline(true);
                    SemBlurInfo build = new SemBlurInfo.Builder(1).setRadius(256).setBitmap(bitmap).build();
                    ViewVisibilityUtil.INSTANCE.getClass();
                    imageView.setVisibility(0);
                    imageView.getForeground().setAlpha(30);
                    imageView.semSetBlurInfo(build);
                    return;
                }
            }
        }
        bitmap = null;
        if (bitmap != null) {
        }
    }
}
