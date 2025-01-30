package com.android.systemui.qs.animator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.animation.PathInterpolator;
import androidx.constraintlayout.motion.widget.MotionLayout;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.SecQuickQSPanelController;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.qs.bar.BrightnessBar;
import com.android.systemui.qs.bar.BrightnessMediaDevicesBar;
import com.android.systemui.qs.bar.MediaDevicesBar;
import com.android.systemui.qs.bar.QSMediaPlayerBar;
import com.android.systemui.plugins.qs.InterfaceC1922QS;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationsQuickSettingsContainer;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BooleanSupplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QsOpenAnimator extends SecQSFragmentAnimatorBase implements OnHeadsUpChangedListener {
    public final BarController mBarController;
    public TouchAnimator mBrightnessBarAnimator;
    public BrightnessMediaDevicesBar mBrightnessMediaDevicesBar;
    public MotionLayout mCarrierAndSysteIconContainer;
    public TouchAnimator mCarrierIconAnimator;
    public final Context mContext;
    public float mFadingSpan;
    public View mHeader;
    public TouchAnimator mHeaderDateSettingAnimator;
    public View mHeaderDateSettingContainer;
    public ValueAnimator mHeaderLockShadeAnimator;
    public final HeadsUpManager mHeadsUpManager;
    public TouchAnimator mMediaDeviceBarAnimator;
    public TouchAnimator mMediaPlayerBarAnimator;
    public TouchAnimator mQQSAnimator;
    public final SecQuickQSPanelController mQuickQsPanelController;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final ShadeHeaderController mShadeHeaderController;
    public final int mYDiff;
    public final ArrayList mAnimContents = new ArrayList();
    public final PathInterpolator mInterpolator = new PathInterpolator(0.42f, 0.0f, 0.58f, 1.0f);
    public final int[] mLoc = new int[2];
    public float mPanelExpansion = 1.0f;
    public boolean mHeadsUpPinned = false;

    public QsOpenAnimator(Context context, BarController barController, HeadsUpManager headsUpManager, SecQSPanelResourcePicker secQSPanelResourcePicker, SecQuickQSPanelController secQuickQSPanelController, ShadeHeaderController shadeHeaderController) {
        this.mContext = context;
        this.mBarController = barController;
        this.mYDiff = context.getResources().getDimensionPixelSize(R.dimen.qs_open_anim_y_diff);
        this.mHeadsUpManager = headsUpManager;
        this.mResourcePicker = secQSPanelResourcePicker;
        this.mQuickQsPanelController = secQuickQSPanelController;
        this.mShadeHeaderController = shadeHeaderController;
    }

    public static void getRelativePositionInt(View view, int[] iArr) {
        if ((view instanceof NotificationsQuickSettingsContainer) || view == null) {
            return;
        }
        iArr[0] = view.getLeft() + iArr[0];
        iArr[1] = view.getTop() + iArr[1];
        getRelativePositionInt((View) view.getParent(), iArr);
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void destroyQSViews() {
        this.mHeadsUpManager.mListeners.remove(this);
        this.mAnimContents.clear();
        this.mQs = null;
        this.mCarrierAndSysteIconContainer = null;
        this.mHeader = null;
        this.mHeaderDateSettingContainer = null;
        this.mBrightnessMediaDevicesBar = null;
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase, com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("QsOpenAnimator ============================================= ");
        Iterator it = this.mAnimContents.iterator();
        while (it.hasNext()) {
            View view = (View) it.next();
            arrayList.add("  " + view.getClass().getSimpleName() + " : alpha = " + view.getAlpha() + " translationY = " + view.getTranslationY() + " visibility = " + view.getVisibility());
        }
        arrayList.add("============================================================== ");
        return arrayList;
    }

    public final float getEndDelay(View view, int i) {
        float f;
        if (this.mPanelViewController != null) {
            int width = view.getWidth() / 2;
            int[] iArr = this.mLoc;
            iArr[0] = width;
            iArr[1] = 0;
            getRelativePositionInt(view, iArr);
            int i2 = iArr[1] + i;
            float maxPanelHeight = this.mPanelViewController.getMaxPanelHeight();
            f = ((maxPanelHeight - i2) - this.mFadingSpan) / maxPanelHeight;
        } else {
            f = 0.0f;
        }
        return Math.max(0.0f, f);
    }

    public final float getStartDelay(View view) {
        float f;
        if (this.mPanelViewController != null) {
            int width = view.getWidth() / 2;
            int[] iArr = this.mLoc;
            iArr[0] = width;
            iArr[1] = 0;
            getRelativePositionInt(view, iArr);
            int i = iArr[1];
            f = (i - this.mYDiff) / this.mPanelViewController.getMaxPanelHeight();
        } else {
            f = 0.0f;
        }
        return Math.max(0.0f, f - 0.1f);
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final boolean isThereNoView() {
        return super.isThereNoView() || this.mPanelViewController == null;
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void onConfigurationChanged(Configuration configuration) {
        updateAnimators();
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public final void onHeadsUpPinnedModeChanged(boolean z) {
        this.mHeadsUpPinned = z;
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void onPanelClosed() {
        this.mPanelExpanded = false;
        if (isThereNoView()) {
            return;
        }
        if (!isThereNoView() && this.mHeaderLockShadeAnimator == null) {
            Iterator it = this.mAnimContents.iterator();
            while (it.hasNext()) {
                View view = (View) it.next();
                view.setAlpha(1.0f);
                view.setTranslationY(0.0f);
                view.setVisibility(0);
            }
        }
        updateAnimators();
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase, com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        BooleanSupplier booleanSupplier;
        if (isThereNoView()) {
            return;
        }
        float f = shadeExpansionChangeEvent.fraction;
        if (this.mHeaderDateSettingContainer != null && (booleanSupplier = this.mExpandImmediateSupplier) != null && booleanSupplier.getAsBoolean()) {
            this.mHeaderDateSettingContainer.setAlpha(1.0f);
            this.mHeaderDateSettingContainer.setTranslationY(0.0f);
        }
        float f2 = this.mPanelExpansion;
        if (f2 > 0.0f && this.mHeadsUpPinned) {
            this.mHeadsUpPinned = false;
        }
        if (f2 != f || f == 0.0f || f == 1.0f) {
            onQsClipBoundChanged(f);
            this.mPanelExpansion = f;
        }
    }

    public final void onQsClipBoundChanged(float f) {
        BooleanSupplier booleanSupplier;
        if (isThereNoView() || !this.mAnimatorsInitialiezed || Float.isNaN(f) || this.mHeadsUpPinned || this.mState == 1 || (((booleanSupplier = this.mExpandImmediateSupplier) != null && booleanSupplier.getAsBoolean()) || this.mHeaderLockShadeAnimator != null)) {
            StringBuilder sb = new StringBuilder();
            if (isThereNoView()) {
                sb.append("isThereNoView(), ");
            }
            if (!this.mAnimatorsInitialiezed) {
                sb.append("!checkIfAnimatorsInitialized(), ");
            }
            if (Float.isNaN(f)) {
                sb.append("Float.isNaN(expansion), ");
            }
            if (this.mHeadsUpPinned) {
                sb.append("mHeadsUpPinned, ");
            }
            BooleanSupplier booleanSupplier2 = this.mExpandImmediateSupplier;
            if (booleanSupplier2 != null) {
                booleanSupplier2.getAsBoolean();
                return;
            }
            return;
        }
        NotificationPanelViewController notificationPanelViewController = this.mPanelViewController;
        if (notificationPanelViewController != null && !notificationPanelViewController.isExpanded()) {
            f = 0.0f;
        }
        if (this.mState == 1) {
            f = 1.0f;
        }
        float f2 = f >= 0.0f ? f : 0.0f;
        float f3 = f2 <= 1.0f ? f2 : 1.0f;
        this.mCarrierIconAnimator.setPosition(f3);
        this.mHeaderDateSettingAnimator.setPosition(f3);
        this.mQQSAnimator.setPosition(f3);
        TouchAnimator touchAnimator = this.mBrightnessBarAnimator;
        if (touchAnimator != null) {
            touchAnimator.setPosition(f3);
        }
        TouchAnimator touchAnimator2 = this.mMediaDeviceBarAnimator;
        if (touchAnimator2 != null) {
            touchAnimator2.setPosition(f3);
        }
        TouchAnimator touchAnimator3 = this.mMediaPlayerBarAnimator;
        if (touchAnimator3 != null) {
            touchAnimator3.setPosition(f3);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void onStateChanged(int i) {
        ValueAnimator valueAnimator;
        View view;
        this.mState = i;
        if (i == 1 && (view = this.mHeaderDateSettingContainer) != null) {
            view.setAlpha(1.0f);
            this.mHeaderDateSettingContainer.setTranslationY(0.0f);
        } else if (i == 2) {
            if (isThereNoView()) {
                return;
            }
            onQsClipBoundChanged(1.0f);
            final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat.setInterpolator(this.mInterpolator);
            ofFloat.setDuration(300L);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.animator.QsOpenAnimator$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    QsOpenAnimator qsOpenAnimator = QsOpenAnimator.this;
                    ValueAnimator valueAnimator3 = ofFloat;
                    qsOpenAnimator.getClass();
                    float animatedFraction = valueAnimator3.getAnimatedFraction();
                    float animatedFraction2 = (1.0f - valueAnimator3.getAnimatedFraction()) * (-60.0f);
                    qsOpenAnimator.mHeader.setAlpha(animatedFraction);
                    qsOpenAnimator.mHeader.setTranslationY(animatedFraction2);
                    ShadeHeaderController shadeHeaderController = qsOpenAnimator.mShadeHeaderController;
                    shadeHeaderController.header.setAlpha(animatedFraction);
                    shadeHeaderController.header.setTranslationY(animatedFraction2);
                }
            });
            ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.qs.animator.QsOpenAnimator.1
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    QsOpenAnimator qsOpenAnimator = QsOpenAnimator.this;
                    qsOpenAnimator.mHeaderLockShadeAnimator = null;
                    qsOpenAnimator.mHeader.setAlpha(1.0f);
                    QsOpenAnimator.this.mHeader.setTranslationY(0.0f);
                    QsOpenAnimator.this.mShadeHeaderController.header.setAlpha(1.0f);
                    QsOpenAnimator.this.mShadeHeaderController.header.setTranslationY(0.0f);
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                }
            });
            this.mHeaderLockShadeAnimator = ofFloat;
            ofFloat.start();
            return;
        }
        if (i != 1 || (valueAnimator = this.mHeaderLockShadeAnimator) == null) {
            updateAnimators();
        } else {
            valueAnimator.cancel();
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void setPanelViewController(NotificationPanelViewController notificationPanelViewController) {
        this.mPanelViewController = notificationPanelViewController;
        updateAnimators();
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void setQs(InterfaceC1922QS interfaceC1922QS) {
        if (interfaceC1922QS == null) {
            destroyQSViews();
        } else {
            this.mQs = interfaceC1922QS;
            this.mHeadsUpManager.addListener(this);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void setQsExpansionPosition(float f) {
        if (isThereNoView() || f != 1.0f || this.mPanelExpansion == 1.0f) {
            return;
        }
        onQsClipBoundChanged(1.0f);
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void updateAnimators() {
        View view;
        MediaDevicesBar mediaDevicesBar;
        View view2;
        BrightnessBar brightnessBar;
        View view3;
        if (isThereNoView()) {
            return;
        }
        ArrayList arrayList = this.mAnimContents;
        arrayList.clear();
        this.mResourcePicker.getClass();
        this.mFadingSpan = SecQSPanelResourcePicker.getTileIconSize(this.mContext) * 3;
        ShadeHeaderController shadeHeaderController = this.mShadeHeaderController;
        this.mCarrierAndSysteIconContainer = shadeHeaderController.header;
        View findViewById = this.mQs.getView().findViewById(R.id.header);
        this.mHeader = findViewById;
        this.mHeaderDateSettingContainer = findViewById.findViewById(R.id.quick_qs_date_buttons).findViewWithTag("open_anim");
        View findViewWithTag = ((View) this.mQuickQsPanelController.mTileLayout).findViewWithTag("open_anim");
        BarType barType = BarType.BRIGHTNESS_MEDIA_DEVICES;
        BarController barController = this.mBarController;
        BrightnessMediaDevicesBar brightnessMediaDevicesBar = (BrightnessMediaDevicesBar) barController.getBarInCollapsed(barType);
        this.mBrightnessMediaDevicesBar = brightnessMediaDevicesBar;
        View view4 = null;
        View findViewWithTag2 = (brightnessMediaDevicesBar == null || (brightnessBar = brightnessMediaDevicesBar.mBrightnessBar) == null || (view3 = brightnessBar.mBarRootView) == null) ? null : view3.findViewWithTag("open_anim");
        BrightnessMediaDevicesBar brightnessMediaDevicesBar2 = this.mBrightnessMediaDevicesBar;
        View findViewWithTag3 = (brightnessMediaDevicesBar2 == null || (mediaDevicesBar = brightnessMediaDevicesBar2.mMediaDevicesBar) == null || (view2 = mediaDevicesBar.mBarRootView) == null) ? null : view2.findViewWithTag("open_anim");
        QSMediaPlayerBar qSMediaPlayerBar = (QSMediaPlayerBar) barController.getBarInCollapsed(BarType.QS_MEDIA_PLAYER);
        if (qSMediaPlayerBar != null && (view = qSMediaPlayerBar.mBarRootView) != null) {
            view4 = view.findViewWithTag("open_anim");
        }
        arrayList.add(this.mHeader);
        arrayList.add(shadeHeaderController.header);
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        builder.addFloat(this.mCarrierAndSysteIconContainer, "alpha", 0.0f, 1.0f);
        MotionLayout motionLayout = this.mCarrierAndSysteIconContainer;
        float f = -this.mYDiff;
        builder.addFloat(motionLayout, "translationY", f, 0.0f);
        builder.mStartDelay = getStartDelay(this.mCarrierAndSysteIconContainer);
        MotionLayout motionLayout2 = this.mCarrierAndSysteIconContainer;
        builder.mEndDelay = getEndDelay(motionLayout2, motionLayout2.getHeight());
        PathInterpolator pathInterpolator = this.mInterpolator;
        builder.mInterpolator = pathInterpolator;
        this.mCarrierIconAnimator = builder.build();
        arrayList.add(this.mCarrierAndSysteIconContainer);
        TouchAnimator.Builder builder2 = new TouchAnimator.Builder();
        builder2.addFloat(this.mHeaderDateSettingContainer, "alpha", 0.0f, 1.0f);
        builder2.addFloat(this.mHeaderDateSettingContainer, "translationY", f, 0.0f);
        builder2.mStartDelay = getStartDelay(this.mHeaderDateSettingContainer);
        View view5 = this.mHeaderDateSettingContainer;
        builder2.mEndDelay = getEndDelay(view5, view5.getHeight());
        builder2.mInterpolator = pathInterpolator;
        this.mHeaderDateSettingAnimator = builder2.build();
        arrayList.add(this.mHeaderDateSettingContainer);
        TouchAnimator.Builder builder3 = new TouchAnimator.Builder();
        builder3.addFloat(findViewWithTag, "alpha", 0.0f, 1.0f);
        builder3.addFloat(findViewWithTag, "translationY", f, 0.0f);
        builder3.mStartDelay = getStartDelay(findViewWithTag);
        builder3.mEndDelay = getEndDelay(findViewWithTag, findViewWithTag.getHeight());
        builder3.mInterpolator = pathInterpolator;
        this.mQQSAnimator = builder3.build();
        if (findViewWithTag2 != null) {
            TouchAnimator.Builder builder4 = new TouchAnimator.Builder();
            builder4.addFloat(findViewWithTag2, "alpha", 0.0f, 1.0f);
            builder4.addFloat(findViewWithTag2, "translationY", f, 0.0f);
            builder4.mStartDelay = getStartDelay(findViewWithTag2);
            builder4.mEndDelay = getEndDelay(findViewWithTag2, findViewWithTag2.getHeight());
            builder4.mInterpolator = pathInterpolator;
            this.mBrightnessBarAnimator = builder4.build();
            arrayList.add(findViewWithTag2);
        }
        if (findViewWithTag3 != null) {
            TouchAnimator.Builder builder5 = new TouchAnimator.Builder();
            builder5.addFloat(findViewWithTag3, "alpha", 0.0f, 1.0f);
            builder5.addFloat(findViewWithTag3, "translationY", f, 0.0f);
            builder5.mStartDelay = getStartDelay(findViewWithTag3);
            builder5.mEndDelay = getEndDelay(findViewWithTag3, findViewWithTag3.getHeight());
            builder5.mInterpolator = pathInterpolator;
            this.mMediaDeviceBarAnimator = builder5.build();
            arrayList.add(findViewWithTag3);
        }
        if (view4 != null) {
            TouchAnimator.Builder builder6 = new TouchAnimator.Builder();
            builder6.addFloat(view4, "alpha", 0.0f, 1.0f);
            builder6.addFloat(view4, "translationY", f, 0.0f);
            builder6.mStartDelay = getStartDelay(view4);
            builder6.mEndDelay = getEndDelay(view4, view4.getHeight());
            builder6.mInterpolator = pathInterpolator;
            this.mMediaPlayerBarAnimator = builder6.build();
            arrayList.add(view4);
        }
        this.mAnimatorsInitialiezed = true;
        onQsClipBoundChanged(this.mPanelExpansion);
    }
}
