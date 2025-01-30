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
import com.android.systemui.statusbar.KeyguardAffordanceView;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardSecAffordanceHelper {
    public final Callback callback;
    public final Context context;
    public boolean isShortcutPreviewSwipingInProgress;
    public FrameLayout mBlurPanelView;
    public LinearLayout mIndicationArea;
    public KeyguardIndicationTextView mIndicationText;
    public KeyguardSecAffordanceView mLeftIcon;
    public boolean mMotionCancelled;
    public boolean mPreviewAnimationStarted;
    public KeyguardSecAffordanceView mRightIcon;
    public KeyguardSecAffordanceView mTargetedView;
    public int mTouchTargetHeight;
    public int mTouchTargetWidth;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
    }

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
                    keyguardSecAffordanceView.mPanelDimView = frameLayout2.findViewById(R.id.panel_dim_layer);
                    keyguardSecAffordanceView.mPanelIcon = (ImageView) frameLayout2.findViewById(R.id.panel_icon);
                    PaintDrawable paintDrawable = new PaintDrawable(keyguardSecAffordanceView.mRectangleColor);
                    keyguardSecAffordanceView.mPanelBackgroundDrawable = paintDrawable;
                    paintDrawable.setCornerRadius(100.0f);
                }
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            arrayList.add(unit);
        }
        updateIcon(this.mLeftIcon, 1.0f, false, true);
        updateIcon(this.mRightIcon, 1.0f, false, true);
        initDimens();
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
        if (!keyguardSecAffordanceView.mIsTaskTypeShortcut && keyguardSecAffordanceView.mIsShortcutLaunching && ((keyguardSecAffordanceView.isSecure() && keyguardSecAffordanceView.mIsNoUnlockNeeded) || !keyguardSecAffordanceView.isSecure())) {
            this.mPreviewAnimationStarted = false;
        } else {
            KeyguardSecAffordanceView keyguardSecAffordanceView2 = this.mTargetedView;
            Intrinsics.checkNotNull(keyguardSecAffordanceView2);
            startPreviewAnimation(keyguardSecAffordanceView2, false);
        }
        this.mTargetedView = null;
    }

    public final void initDimens() {
        Context context = this.context;
        this.mTouchTargetWidth = context.getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_touch_target_width);
        this.mTouchTargetHeight = context.getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_touch_target_height);
    }

    public final boolean isOnIcon(KeyguardSecAffordanceView keyguardSecAffordanceView, float f, float f2) {
        keyguardSecAffordanceView.getLocationOnScreen(new int[2]);
        float width = (keyguardSecAffordanceView.getWidth() / 2.0f) + r0[0];
        float height = (keyguardSecAffordanceView.getHeight() / 2.0f) + r0[1];
        float f3 = this.mTouchTargetHeight / 2.0f;
        float f4 = height - f3;
        float f5 = f3 + height;
        float f6 = this.mTouchTargetWidth / 2.0f;
        if (width - f6 <= f && f <= f6 + width) {
            return (f4 > f2 ? 1 : (f4 == f2 ? 0 : -1)) <= 0 && (f2 > f5 ? 1 : (f2 == f5 ? 0 : -1)) <= 0;
        }
        return false;
    }

    public final void reset() {
        this.mPreviewAnimationStarted = false;
        ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).setShortcutLaunchInProgress(false);
        updateIcon(this.mLeftIcon, 1.0f, false, false);
        updateIcon(this.mRightIcon, 1.0f, false, false);
        KeyguardSecAffordanceView keyguardSecAffordanceView = this.mLeftIcon;
        Intrinsics.checkNotNull(keyguardSecAffordanceView);
        keyguardSecAffordanceView.reset();
        KeyguardSecAffordanceView keyguardSecAffordanceView2 = this.mRightIcon;
        Intrinsics.checkNotNull(keyguardSecAffordanceView2);
        keyguardSecAffordanceView2.reset();
        this.mMotionCancelled = true;
        this.mTargetedView = null;
        KeyguardIndicationTextView keyguardIndicationTextView = this.mIndicationText;
        if (keyguardIndicationTextView == null) {
            return;
        }
        keyguardIndicationTextView.setAlpha(1.0f);
    }

    public final void startPreviewAnimation(KeyguardAffordanceView keyguardAffordanceView, boolean z) {
        this.mPreviewAnimationStarted = z;
        Log.d("KeyguardSecAffordanceHelper", "startPreviewAnimation() show = " + z + ", target = " + keyguardAffordanceView);
        if (z) {
            return;
        }
        LinearLayout linearLayout = this.mIndicationArea;
        if (linearLayout != null) {
            linearLayout.animate().alpha(1.0f).setDuration(200L).setInterpolator(new LinearInterpolator());
        }
        KeyguardSecAffordanceView keyguardSecAffordanceView = this.mLeftIcon;
        if (keyguardAffordanceView == keyguardSecAffordanceView) {
            KeyguardSecAffordanceView keyguardSecAffordanceView2 = this.mRightIcon;
            Intrinsics.checkNotNull(keyguardSecAffordanceView2);
            keyguardSecAffordanceView2.setImageAlpha(1.0f, true);
        } else if (keyguardAffordanceView == this.mRightIcon) {
            Intrinsics.checkNotNull(keyguardSecAffordanceView);
            keyguardSecAffordanceView.setImageAlpha(1.0f, true);
        }
    }
}
