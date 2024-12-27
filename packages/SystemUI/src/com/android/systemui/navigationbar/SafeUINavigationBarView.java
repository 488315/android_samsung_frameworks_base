package com.android.systemui.navigationbar;

import android.R;
import android.content.Context;
import android.graphics.Insets;
import android.os.Binder;
import android.view.InsetsFrameProvider;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SafeUINavigationBarView {
    public final Context mContext;
    public final Binder mInsetsSourceOwner = new Binder();
    public View mView;
    public final WindowManager mWindowManager;

    public SafeUINavigationBarView(Context context, WindowManager windowManager) {
        this.mContext = context;
        this.mWindowManager = windowManager;
    }

    public final WindowManager.LayoutParams getBarLayoutParamsForRotation() {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.resolver_empty_state_container_padding_top);
        int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.resolver_empty_state_container_padding_top);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, dimensionPixelSize, 2019, 545521768, -3);
        layoutParams.gravity = 80;
        Context context = this.mContext;
        InsetsFrameProvider insetsSizeOverrides = new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.navigationBars()).setInsetsSizeOverrides(new InsetsFrameProvider.InsetsSizeOverride[]{new InsetsFrameProvider.InsetsSizeOverride(2011, (Insets) null)});
        if (dimensionPixelSize2 != -1) {
            insetsSizeOverrides.setInsetsSize(Insets.of(0, 0, 0, dimensionPixelSize2));
        }
        insetsSizeOverrides.setFlags(!context.getResources().getBoolean(R.bool.config_pauseWallpaperRenderWhenStateChangeEnabled) ? 1 : 0, 1);
        InsetsFrameProvider insetsFrameProvider = new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.tappableElement());
        if (context.getResources().getBoolean(R.bool.config_performantAuthDefault)) {
            insetsFrameProvider.setInsetsSize(Insets.NONE);
        }
        layoutParams.providedInsets = new InsetsFrameProvider[]{insetsSizeOverrides, insetsFrameProvider, new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.mandatorySystemGestures()), new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.systemGestures()).setSource(0).setInsetsSize(Insets.of(0, 0, 0, 0)).setMinimalInsetsSizeInDisplayCutoutSafe(Insets.of(0, 0, 0, 0)), new InsetsFrameProvider(this.mInsetsSourceOwner, 1, WindowInsets.Type.systemGestures()).setSource(0).setInsetsSize(Insets.of(0, 0, 0, 0)).setMinimalInsetsSizeInDisplayCutoutSafe(Insets.of(0, 0, 0, 0))};
        layoutParams.token = new Binder();
        layoutParams.accessibilityTitle = this.mContext.getString(com.android.systemui.R.string.nav_bar);
        layoutParams.privateFlags |= 16781312;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.windowAnimations = 0;
        layoutParams.setTitle("SafeUINavigationBar" + this.mContext.getDisplayId());
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTrustedOverlay();
        return layoutParams;
    }
}
