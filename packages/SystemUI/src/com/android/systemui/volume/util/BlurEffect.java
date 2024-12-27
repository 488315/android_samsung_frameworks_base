package com.android.systemui.volume.util;

import android.content.Context;
import android.view.SemBlurInfo;
import android.view.View;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.VolumeDependencyBase;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BlurEffect {
    public final Context context;
    public final SemWindowManagerWrapper semWindowManagerWrapper;
    public final StatusBarWrapper statusBarWrapper;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            SemBlurInfo build = new SemBlurInfo.Builder(0).setRadius(256).setBackgroundColor(i).setColorCurvePreset(i2).setBackgroundCornerRadius(f).build();
            ViewVisibilityUtil.INSTANCE.getClass();
            view.setVisibility(0);
            view.getForeground().setAlpha(30);
            view.semSetBlurInfo(build);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x016f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setCapturedBlur(android.widget.ImageView r32, int r33, java.util.function.Supplier r34) {
        /*
            Method dump skipped, instructions count: 416
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.util.BlurEffect.setCapturedBlur(android.widget.ImageView, int, java.util.function.Supplier):void");
    }
}
