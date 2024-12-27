package com.android.keyguard.punchhole;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieAnimationView;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController;
import com.android.keyguard.punchhole.VIDirectorFactory;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class KeyguardPunchHoleVIView extends FrameLayout {
    public String TAG;
    public String mAppliedVIFileName;
    public Rect mBoundingRect;
    public int mCurrentAnimation;
    public final Handler mHandler;
    public boolean mIsAnimationPlaying;
    public boolean mIsConfigUpdateNecessary;
    public int mLastDisplayDeviceType;
    public boolean mLastUpdatedFolderOpened;
    public int mLastUpdatedRotation;
    public int mLastUpdatedScreenHeight;
    public int mLastUpdatedScreenWidth;
    public FrameLayout mLockStarVIView;
    public int mPreparedState;
    public KeyguardPunchHoleVIViewController.AnonymousClass1 mPunchHoleCallback;
    public VIDirector mVIDirector;
    public LottieAnimationView mVIView;
    public Rect mViViewLocation;
    public final KeyguardPunchHoleVIView$$ExternalSyntheticLambda0 updateVILocationRunnable;

    public KeyguardPunchHoleVIView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        VIDirector vIDirector = this.mVIDirector;
        if (vIDirector == null) {
            String str = this.TAG;
            StringBuilder sb = new StringBuilder("onApplyWindowInsets() return - mVIDirector is null (");
            VIDirectorFactory.Companion.getClass();
            sb.append(VIDirectorFactory.vendorName);
            sb.append(")");
            Log.e(str, sb.toString());
            return super.onApplyWindowInsets(windowInsets);
        }
        if (!vIDirector.mIsBasedOnType || !vIDirector.mVIType.equals("circle")) {
            return super.onApplyWindowInsets(windowInsets);
        }
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout != null) {
            for (Rect rect : displayCutout.getBoundingRects()) {
                Log.d(this.TAG, "BoundingRect = " + rect);
                this.mBoundingRect = rect;
                VIDirector vIDirector2 = this.mVIDirector;
                boolean z = true;
                if (getLayoutDirection() != 1) {
                    z = false;
                }
                this.mViViewLocation = vIDirector2.getVIViewLocation(rect, z);
            }
            this.mHandler.removeCallbacks(this.updateVILocationRunnable);
            this.mHandler.post(this.updateVILocationRunnable);
        }
        return WindowInsets.CONSUMED;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            this.mLastUpdatedFolderOpened = ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened;
            Context context = getContext();
            boolean z = this.mLastUpdatedFolderOpened;
            VIDirectorFactory.Companion.getClass();
            VIDirector vIDirector = new VIDirector(context);
            vIDirector.mIsFolderOpened = z;
            this.mVIDirector = vIDirector.initialize() ? vIDirector : null;
        } else {
            VIDirectorFactory.Companion companion = VIDirectorFactory.Companion;
            Context context2 = getContext();
            companion.getClass();
            VIDirector vIDirector2 = new VIDirector(context2);
            this.mVIDirector = vIDirector2.initialize() ? vIDirector2 : null;
        }
        if (this.mVIDirector != null) {
            setVisibility(0);
            setLayoutDirection(0);
            this.mVIView = (LottieAnimationView) findViewById(R.id.keyguard_punch_hole_vi_animation_view);
            this.mLockStarVIView = (FrameLayout) findViewById(R.id.lock_star_punch_hole_vi_animation_view);
            return;
        }
        String str = this.TAG;
        StringBuilder sb = new StringBuilder("onFinishInflate() return - mVIDirector is null (");
        VIDirectorFactory.Companion.getClass();
        sb.append(VIDirectorFactory.vendorName);
        sb.append(")");
        Log.e(str, sb.toString());
        setVisibility(8);
    }

    public final void setPrepareState(int i) {
        if (this.mPreparedState == i) {
            return;
        }
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("setPrepareState() "), this.mPreparedState, " -> ", i, this.TAG);
        this.mPreparedState = i;
    }

    public final void updateScreenConfig() {
        VIDirector vIDirector = this.mVIDirector;
        if (vIDirector == null) {
            String str = this.TAG;
            StringBuilder sb = new StringBuilder("updateScreenConfig() return - mVIDirector is null (");
            VIDirectorFactory.Companion.getClass();
            sb.append(VIDirectorFactory.vendorName);
            sb.append(")");
            Log.e(str, sb.toString());
            return;
        }
        int screenRotation = vIDirector.getScreenRotation();
        int screenWidth = this.mVIDirector.getScreenWidth();
        int screenHeight = this.mVIDirector.getScreenHeight();
        boolean z = ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened || this.mLastDisplayDeviceType == 0;
        boolean z2 = LsRune.SECURITY_SUB_DISPLAY_LOCK && this.mLastUpdatedFolderOpened != z;
        if (this.mLastUpdatedRotation != screenRotation || this.mLastUpdatedScreenWidth != screenWidth || this.mLastUpdatedScreenHeight != screenHeight || z2) {
            String str2 = this.TAG;
            StringBuilder sb2 = new StringBuilder("updateScreenConfig() rotation ");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(sb2, this.mLastUpdatedRotation, " -> ", screenRotation, ", screen width ");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(sb2, this.mLastUpdatedScreenWidth, " -> ", screenWidth, ", screen height ");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb2, this.mLastUpdatedScreenHeight, " -> ", screenHeight, str2);
            this.mLastUpdatedRotation = screenRotation;
            this.mLastUpdatedScreenWidth = screenWidth;
            this.mLastUpdatedScreenHeight = screenHeight;
            if (z2) {
                KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(new StringBuilder("updateScreenConfig() isFolderOpened "), this.mLastUpdatedFolderOpened, " -> ", z, this.TAG);
                this.mLastUpdatedFolderOpened = z;
                KeyguardPunchHoleVIViewController.AnonymousClass1 anonymousClass1 = this.mPunchHoleCallback;
                if (anonymousClass1 != null) {
                    KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                    PluginLockStar pluginLockStar = keyguardPunchHoleVIViewController.mPluginLockStarManager.mPluginLockStar;
                    boolean z3 = pluginLockStar != null && pluginLockStar.isLockStarEnabled();
                    if (keyguardPunchHoleVIViewController.mIsLockStarEnabled != z3) {
                        keyguardPunchHoleVIViewController.stopVI();
                        keyguardPunchHoleVIViewController.mIsLockStarEnabled = z3;
                    }
                }
                Context context = getContext();
                VIDirectorFactory.Companion.getClass();
                VIDirector vIDirector2 = new VIDirector(context);
                vIDirector2.mIsFolderOpened = z;
                if (!vIDirector2.initialize()) {
                    vIDirector2 = null;
                }
                this.mVIDirector = vIDirector2;
            }
            this.mHandler.removeCallbacks(this.updateVILocationRunnable);
            this.mHandler.post(this.updateVILocationRunnable);
            this.mIsConfigUpdateNecessary = false;
        }
        KeyguardPunchHoleVIViewController.AnonymousClass1 anonymousClass12 = this.mPunchHoleCallback;
        if (anonymousClass12 != null) {
            KeyguardPunchHoleVIViewController.this.startVI();
        }
    }

    public final void updateVILocation() {
        Rect rect;
        VIDirector vIDirector = this.mVIDirector;
        if (vIDirector == null) {
            String str = this.TAG;
            StringBuilder sb = new StringBuilder("updateVILocation() return - mVIDirector is null (");
            VIDirectorFactory.Companion.getClass();
            sb.append(VIDirectorFactory.vendorName);
            sb.append(")");
            Log.e(str, sb.toString());
            return;
        }
        boolean z = vIDirector.mIsBasedOnType;
        int i = 0;
        if (z) {
            if (!vIDirector.mVIType.equals("circle")) {
                this.mViViewLocation = this.mVIDirector.getVIViewLocation(this.mBoundingRect, getLayoutDirection() == 1);
            } else if (this.mViViewLocation.isEmpty() || !this.mBoundingRect.isEmpty()) {
                this.mViViewLocation = this.mVIDirector.getVIViewLocation(this.mBoundingRect, getLayoutDirection() == 1);
            }
            rect = this.mViViewLocation;
        } else {
            int i2 = this.mCurrentAnimation;
            boolean z2 = getLayoutDirection() == 1;
            Rect rect2 = new Rect();
            PointF pointF = vIDirector.mCameraLocPercent;
            PointF pointF2 = i2 == 1 ? vIDirector.mFaceVISizePercent : null;
            if (pointF2 == null) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "getVIViewLocation() - return; vi size is not supported, animation = ", "KeyguardPunchHoleVIView_VIDirector");
            } else {
                vIDirector.setViViewLocation(rect2, pointF, pointF2);
                int screenWidth = vIDirector.getScreenWidth();
                if (z2) {
                    int i3 = rect2.left;
                    rect2.left = rect2.right - screenWidth;
                    rect2.right = i3 - screenWidth;
                }
            }
            rect = rect2;
        }
        int screenRotation = this.mVIDirector.getScreenRotation();
        String str2 = this.TAG;
        StringBuilder sb2 = new StringBuilder("updateVILocation() ");
        sb2.append(rect);
        sb2.append(" isBasedOnType = ");
        sb2.append(z);
        sb2.append(" rotation = ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb2, this.mLastUpdatedRotation, " -> ", screenRotation, str2);
        this.mLastUpdatedRotation = screenRotation;
        LottieAnimationView lottieAnimationView = this.mVIView;
        int screenRotation2 = this.mVIDirector.getScreenRotation();
        lottieAnimationView.setRotation(screenRotation2 != 1 ? screenRotation2 != 3 ? 0 : 90 : 270);
        this.mVIView.setTranslationX(rect.left);
        this.mVIView.setTranslationY(rect.top);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mVIView.getLayoutParams();
        layoutParams.width = rect.width() < 0 ? -rect.width() : rect.width();
        layoutParams.height = rect.height();
        this.mVIView.setLayoutParams(layoutParams);
        FrameLayout frameLayout = this.mLockStarVIView;
        int screenRotation3 = this.mVIDirector.getScreenRotation();
        if (screenRotation3 == 1) {
            i = 270;
        } else if (screenRotation3 == 3) {
            i = 90;
        }
        frameLayout.setRotation(i);
        this.mLockStarVIView.setTranslationX(rect.left);
        this.mLockStarVIView.setTranslationY(rect.top);
        this.mLockStarVIView.setLayoutParams(layoutParams);
    }

    public KeyguardPunchHoleVIView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.keyguard.punchhole.KeyguardPunchHoleVIView$$ExternalSyntheticLambda0] */
    public KeyguardPunchHoleVIView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "KeyguardPunchHoleVIView";
        this.updateVILocationRunnable = new Runnable() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardPunchHoleVIView.this.updateVILocation();
            }
        };
        this.mViViewLocation = new Rect();
        this.mBoundingRect = new Rect();
        this.mPreparedState = 0;
        this.mCurrentAnimation = 0;
        this.mIsAnimationPlaying = false;
        this.mIsConfigUpdateNecessary = false;
        this.mAppliedVIFileName = null;
        this.mLastUpdatedRotation = 0;
        this.mLastUpdatedScreenWidth = 0;
        this.mLastUpdatedScreenHeight = 0;
        this.mLastDisplayDeviceType = -1;
        this.mHandler = (Handler) Dependency.sDependency.getDependencyInner(Dependency.MAIN_HANDLER);
    }
}
