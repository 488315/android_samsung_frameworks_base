package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class KeyguardSecAffordanceHelper {
    public final Callback callback;
    public final Context context;
    public boolean isShortcutPreviewSwipingInProgress;
    public final FrameLayout mBlurPanelView;
    public final LinearLayout mIndicationArea;
    public final KeyguardIndicationTextView mIndicationText;
    public final KeyguardSecAffordanceView mLeftIcon;
    public boolean mMotionCancelled;
    public boolean mPreviewAnimationStarted;
    public final KeyguardSecAffordanceView mRightIcon;
    public KeyguardSecAffordanceView mTargetedView;
    public final int mTouchTargetHeight;
    public final int mTouchTargetWidth;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
    }

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

    public KeyguardSecAffordanceHelper(Callback callback, Context context, KeyguardSecBottomAreaView keyguardSecBottomAreaView) {
        Unit unit;
        this.callback = callback;
        this.context = context;
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.keyguard_sec_affordance_blur_window, (ViewGroup) null);
        this.mBlurPanelView = frameLayout;
        frameLayout.setVisibility(8);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2415, 1336, -3);
        layoutParams.flags |= 16777216;
        layoutParams.gravity = 48;
        layoutParams.privateFlags |= 16;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setTitle("LockscreenShortcutBlur");
        layoutParams.packageName = context.getPackageName();
        ((WindowManager) context.getSystemService("window")).addView(this.mBlurPanelView, layoutParams);
        if (keyguardSecBottomAreaView.getLayoutDirection() == 1) {
            this.mRightIcon = (KeyguardSecAffordanceView) keyguardSecBottomAreaView.leftView$delegate.getValue();
            this.mLeftIcon = keyguardSecBottomAreaView.getRightView();
        } else {
            this.mLeftIcon = (KeyguardSecAffordanceView) keyguardSecBottomAreaView.leftView$delegate.getValue();
            this.mRightIcon = keyguardSecBottomAreaView.getRightView();
        }
        this.mIndicationArea = (LinearLayout) ((ViewGroup) keyguardSecBottomAreaView.indicationArea$delegate.getValue());
        this.mIndicationText = (KeyguardIndicationTextView) ((TextView) keyguardSecBottomAreaView.indicationText$delegate.getValue());
        List<KeyguardSecAffordanceView> listOf = CollectionsKt__CollectionsKt.listOf(this.mLeftIcon, this.mRightIcon);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listOf, 10));
        for (KeyguardSecAffordanceView keyguardSecAffordanceView : listOf) {
            if (keyguardSecAffordanceView != null) {
                keyguardSecAffordanceView.mHelperCallback = this.callback;
            }
            if (keyguardSecAffordanceView != null) {
                FrameLayout frameLayout2 = this.mBlurPanelView;
                if (frameLayout2 != null) {
                    keyguardSecAffordanceView.mBlurPanelRoot = frameLayout2;
                    keyguardSecAffordanceView.mPanelBackground = frameLayout2.findViewById(R.id.panel_background);
                    View findViewById = frameLayout2.findViewById(R.id.panel_blur);
                    keyguardSecAffordanceView.mBlurPanelView = findViewById;
                    findViewById.setClipToOutline(false);
                    keyguardSecAffordanceView.mPanelIcon = (ImageView) frameLayout2.findViewById(R.id.panel_icon);
                    keyguardSecAffordanceView.mPanelBackgroundDrawable = new PaintDrawable(keyguardSecAffordanceView.mRectangleColor);
                }
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            arrayList.add(unit);
        }
        updateIcon(this.mLeftIcon, 1.0f, false, true);
        updateIcon(this.mRightIcon, 1.0f, false, true);
        this.mTouchTargetWidth = this.context.getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_touch_target_width);
        this.mTouchTargetHeight = this.context.getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_touch_target_height);
    }

    public static void updateIcon(KeyguardSecAffordanceView keyguardSecAffordanceView, float f, boolean z, boolean z2) {
        Intrinsics.checkNotNull(keyguardSecAffordanceView);
        if (keyguardSecAffordanceView.getVisibility() == 0 || z2) {
            keyguardSecAffordanceView.setImageAlpha(Math.min(1.0f, f), z);
            keyguardSecAffordanceView.setImageScale(1.0f, z);
        }
    }

    public final void endMotion() {
        KeyguardSecAffordanceView keyguardSecAffordanceView = this.mTargetedView;
        Intrinsics.checkNotNull(keyguardSecAffordanceView);
        if (keyguardSecAffordanceView.mIsTaskTypeShortcut || !keyguardSecAffordanceView.mIsShortcutLaunching || (keyguardSecAffordanceView.isSecure$1() && !keyguardSecAffordanceView.mIsNoUnlockNeeded)) {
            KeyguardSecAffordanceView keyguardSecAffordanceView2 = this.mTargetedView;
            Intrinsics.checkNotNull(keyguardSecAffordanceView2);
            startPreviewAnimation(keyguardSecAffordanceView2, false);
        } else {
            this.mPreviewAnimationStarted = false;
        }
        this.mTargetedView = null;
    }

    public final boolean isOnIcon(View view, float f, float f2) {
        view.getLocationOnScreen(new int[2]);
        float width = (view.getWidth() / 2.0f) + r0[0];
        float height = (view.getHeight() / 2.0f) + r0[1];
        int i = this.mTouchTargetHeight;
        float f3 = height - (i / 2.0f);
        float f4 = (i / 2.0f) + height;
        int i2 = this.mTouchTargetWidth;
        return width - (((float) i2) / 2.0f) <= f && f <= (((float) i2) / 2.0f) + width && f3 <= f2 && f2 <= f4;
    }

    public final void reset(boolean z) {
        this.mPreviewAnimationStarted = false;
        ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).setShortcutLaunchInProgress(false);
        updateIcon(this.mLeftIcon, 1.0f, false, false);
        updateIcon(this.mRightIcon, 1.0f, false, false);
        KeyguardSecAffordanceView keyguardSecAffordanceView = this.mLeftIcon;
        Intrinsics.checkNotNull(keyguardSecAffordanceView);
        keyguardSecAffordanceView.reset(z);
        KeyguardSecAffordanceView keyguardSecAffordanceView2 = this.mRightIcon;
        Intrinsics.checkNotNull(keyguardSecAffordanceView2);
        keyguardSecAffordanceView2.reset(z);
        this.mMotionCancelled = true;
        this.mTargetedView = null;
        KeyguardIndicationTextView keyguardIndicationTextView = this.mIndicationText;
        if (keyguardIndicationTextView == null) {
            return;
        }
        keyguardIndicationTextView.setAlpha(1.0f);
    }

    public final void startPreviewAnimation(View view, boolean z) {
        this.mPreviewAnimationStarted = z;
        Log.d("KeyguardSecAffordanceHelper", "startPreviewAnimation() show = " + z + ", target = " + view);
        if (z) {
            return;
        }
        LinearLayout linearLayout = this.mIndicationArea;
        if (linearLayout != null) {
            linearLayout.animate().alpha(1.0f).setDuration(200L).setInterpolator(new LinearInterpolator());
        }
        KeyguardSecAffordanceView keyguardSecAffordanceView = this.mLeftIcon;
        if (view == keyguardSecAffordanceView) {
            KeyguardSecAffordanceView keyguardSecAffordanceView2 = this.mRightIcon;
            Intrinsics.checkNotNull(keyguardSecAffordanceView2);
            keyguardSecAffordanceView2.setImageAlpha(1.0f, true);
        } else if (view == this.mRightIcon) {
            Intrinsics.checkNotNull(keyguardSecAffordanceView);
            keyguardSecAffordanceView.setImageAlpha(1.0f, true);
        }
    }
}
