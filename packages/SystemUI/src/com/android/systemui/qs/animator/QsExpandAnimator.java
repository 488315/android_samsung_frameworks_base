package com.android.systemui.qs.animator;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.SecQuickQSPanel;
import com.android.systemui.qs.SecQuickStatusBarHeader;
import com.android.systemui.qs.SecTileChunkLayout;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda6;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.qs.bar.BottomLargeTileBar;
import com.android.systemui.qs.bar.BrightnessMediaDevicesBar;
import com.android.systemui.qs.bar.BrightnessVolumeBar;
import com.android.systemui.qs.bar.MultiSIMPreferredSlotBar;
import com.android.systemui.qs.bar.QSMediaPlayerBar;
import com.android.systemui.qs.bar.QuickControlBar;
import com.android.systemui.qs.bar.SecurityFooterBar;
import com.android.systemui.qs.bar.SmartViewLargeTileBar;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import com.android.systemui.qs.bar.TopLargeTileBar;
import com.android.systemui.qs.bar.VideoCallMicModeBar;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.ConfigurationState;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QsExpandAnimator extends SecQSImplAnimatorBase implements QSHost.Callback, TouchAnimator.Listener, View.OnAttachStateChangeListener, TunerService.Tunable, View.OnLayoutChangeListener {
    public TouchAnimator mAnimatorForListener;
    public final BarController mBarController;
    public BottomLargeTileBar mBottomLargeTileBar;
    public BrightnessMediaDevicesBar mBrightnessMediaDevicesBar;
    public BrightnessVolumeBar mBrightnessVolumeBar;
    public View mClockDateContainer;
    public final Context mContext;
    public View mEditContainer;
    public QSMediaPlayerBar mExpandedMediaPlayerBar;
    public SecQuickStatusBarHeader mHeader;
    public TouchAnimator mHeaderAnimator;
    public TouchAnimator mHeaderBarAnimator;
    public final HeadsUpManager mHeadsUpManager;
    public QSHost mHost;
    public final KeyguardEditModeController mKeyguardEditModeController;
    public MultiSIMPreferredSlotBar mMultiSIMBar;
    public View mMumContainer;
    public View mNetworkSpeedContainer;
    public TouchAnimator mPanelAlphaAnimator;
    public TouchAnimator mPanelBarAnimator;
    public TouchAnimator mPanelYAnimator;
    public ViewGroup mPlmn;
    public View mPowerContainer;
    public View mPrivacyContainer;
    public TouchAnimator mQsButtonsAnimator;
    public SecQSPanel mQsPanel;
    public final SecQSPanelController mQsPanelController;
    public View mQsRootPanel;
    public QuickControlBar mQuickControlBar;
    public View mQuickQSPanelTileContainer;
    public TouchAnimator mQuickQsAnimator;
    public SecQuickQSPanel mQuickQsPanel;
    public final SecQSPanelResourcePicker mResourcePicker;
    public SecurityFooterBar mSecurityFooterBar;
    public View mSettingContainer;
    public MotionLayout mShadeHeader;
    public TouchAnimator mShadeHeaderAnimator;
    public final ShadeHeaderController mShadeHeaderController;
    public TouchAnimator mShadeHeaderExpandImmediateAnimator;
    public SmartViewLargeTileBar mSmartViewLargeTileBar;
    public TouchAnimator mStackScrollLayoutAnimator;
    public View mSystemIconContainer;
    public SecTileChunkLayout mTileChunkLayout;
    public TileChunkLayoutBar mTileChunkLayoutBar;
    public TopLargeTileBar mTopLargeTileBar;
    public VideoCallMicModeBar mVideoCallMicModeBar;
    public final ArrayList mAllViews = new ArrayList();
    public final ConfigurationState mLastConfigurationState = new ConfigurationState(List.of(ConfigurationState.ConfigurationField.ORIENTATION));
    public final ArrayList mTileAnimators = new ArrayList();
    public float mLastPosition = 0.0f;
    public boolean mForceUpdate = false;
    public boolean mOnExpandImmediate = false;
    public boolean isHeadsUpPinnedMode = false;
    public final AnonymousClass1 onHeadsUpChangedListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.qs.animator.QsExpandAnimator.1
        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public final void onHeadsUpPinnedModeChanged(boolean z) {
            QsExpandAnimator.this.isHeadsUpPinnedMode = z;
        }
    };
    public final QsExpandAnimator$$ExternalSyntheticLambda0 mUpdateAnimators = new QsExpandAnimator$$ExternalSyntheticLambda0(this);

    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.qs.animator.QsExpandAnimator$1] */
    public QsExpandAnimator(Context context, SecQSPanelController secQSPanelController, BarController barController, ShadeHeaderController shadeHeaderController, SecQSPanelResourcePicker secQSPanelResourcePicker, KeyguardEditModeController keyguardEditModeController, HeadsUpManager headsUpManager) {
        this.mContext = context;
        this.mBarController = barController;
        this.mQsPanelController = secQSPanelController;
        this.mShadeHeaderController = shadeHeaderController;
        this.mResourcePicker = secQSPanelResourcePicker;
        this.mKeyguardEditModeController = keyguardEditModeController;
        this.mHeadsUpManager = headsUpManager;
    }

    public static View getBarView$1(BarItemImpl barItemImpl) {
        View view;
        if (barItemImpl == null || (view = barItemImpl.mBarRootView) == null) {
            return null;
        }
        return view.findViewWithTag("expand_anim");
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void clearAnimationState() {
        if (((BaseHeadsUpManager) this.mHeadsUpManager).mHasPinnedNotification) {
            return;
        }
        boolean z = this.mUserChanged;
        this.mUserChanged = false;
        if (!isThereNoView() || z) {
            Log.d("QsExpandAnimator", "clearAnimationState ");
            if (SecQSImplAnimatorBase.isDetailVisible() || this.isHeadsUpPinnedMode) {
                return;
            }
            boolean z2 = true;
            if (!z && !QsAnimatorState.qsExpanded && QsAnimatorState.state != 1) {
                z2 = false;
            }
            int size = this.mAllViews.size();
            for (int i = 0; i < size; i++) {
                Pair pair = (Pair) this.mAllViews.get(i);
                if (pair.second != null) {
                    if (((String) pair.first).equals("view_visible_always")) {
                        ((View) pair.second).setAlpha(1.0f);
                    }
                    if (((String) pair.first).equals("view_visible_expanded_state")) {
                        ((View) pair.second).setAlpha(z2 ? 1.0f : 0.0f);
                    }
                    if (((String) pair.first).equals("view_visible_collapsed_state")) {
                        ((View) pair.second).setAlpha(z2 ? 0.0f : 1.0f);
                    }
                    Object obj = pair.second;
                    if (!(obj instanceof NotificationStackScrollLayout)) {
                        ((View) obj).setTranslationX(0.0f);
                    }
                    ((View) pair.second).setTranslationY(0.0f);
                }
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void destroyQSViews() {
        Log.d("QsExpandAnimator", "destroyQSViews ");
        SecQSPanel secQSPanel = this.mQsPanel;
        if (secQSPanel != null) {
            secQSPanel.removeOnAttachStateChangeListener(this);
            this.mQsPanel.removeOnLayoutChangeListener(this);
        }
        HeadsUpManager headsUpManager = this.mHeadsUpManager;
        ((BaseHeadsUpManager) headsUpManager).mListeners.remove(this.onHeadsUpChangedListener);
        this.mAllViews.clear();
        this.mTileAnimators.clear();
        this.mQs = null;
        this.mQuickQsPanel = null;
        this.mQsPanel = null;
        this.mHeader = null;
        this.mPlmn = null;
        this.mTileChunkLayout = null;
        this.mSettingContainer = null;
        this.mMumContainer = null;
        this.mEditContainer = null;
        this.mPowerContainer = null;
        this.mQuickQSPanelTileContainer = null;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("QsExpandAnimator ============================================= ");
        SecQSImplAnimatorBase.gatherStateOfViews(arrayList, "", (ArrayList) this.mAllViews.stream().map(new QsExpandAnimator$$ExternalSyntheticLambda1()).filter(new QsExpandAnimator$$ExternalSyntheticLambda2()).collect(Collectors.toList()), true);
        arrayList.add("============================================================== ");
        return arrayList;
    }

    public final boolean isPanelExpandImmediate() {
        ShadeRepository shadeRepository = this.mShadeRepository;
        return (shadeRepository != null && ((Boolean) ((ShadeRepositoryImpl) shadeRepository).legacyExpandImmediate.$$delegate_0.getValue()).booleanValue()) || this.mOnExpandImmediate;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final boolean isThereNoView() {
        return super.isThereNoView() || this.mTileChunkLayout == null || this.mQsPanelController.mHost == null || SecPanelSplitHelper.isEnabled();
    }

    @Override // com.android.systemui.qs.TouchAnimator.Listener
    public final void onAnimationAtEnd() {
        if (isThereNoView()) {
            return;
        }
        Log.d("QsExpandAnimator", "onAnimationAtEnd");
        this.mQuickQsPanel.setVisibility(4);
        if (this.mOnExpandImmediate) {
            this.mOnExpandImmediate = false;
        }
    }

    @Override // com.android.systemui.qs.TouchAnimator.Listener
    public final void onAnimationAtStart() {
        if (isThereNoView()) {
            return;
        }
        Log.d("QsExpandAnimator", "onAnimationAtStart");
        this.mQuickQsPanel.setVisibility(0);
        this.mClockDateContainer.setVisibility(0);
    }

    @Override // com.android.systemui.qs.TouchAnimator.Listener
    public final void onAnimationStarted() {
        if (isThereNoView()) {
            return;
        }
        Log.d("QsExpandAnimator", "onAnimationStarted");
        this.mQuickQsPanel.setVisibility(0);
        this.mClockDateContainer.setVisibility(0);
        ShadeRepository shadeRepository = this.mShadeRepository;
        if (shadeRepository == null || !((Boolean) ((ShadeRepositoryImpl) shadeRepository).legacyExpandImmediate.$$delegate_0.getValue()).booleanValue()) {
            return;
        }
        this.mOnExpandImmediate = true;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onConfigurationChanged(Configuration configuration) {
        if (this.mLastConfigurationState.needToUpdate(configuration)) {
            this.mQsRootPanel.post(new QsExpandAnimator$$ExternalSyntheticLambda0(this));
            if (SecQSImplAnimatorBase.isDetailVisible() && !QsAnimatorState.detailTriggeredExpand) {
                this.mForceUpdate = true;
                setQsExpansionPosition(this.mLastPosition);
            }
            this.mLastConfigurationState.update(configuration);
        }
    }

    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        if (secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getPanelHeight(this.mContext) != 0) {
            this.mQsPanel.post(this.mUpdateAnimators);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelClosed() {
        QsAnimatorState.panelExpanded = false;
        Log.d("QsExpandAnimator", "onPanelClosed ");
        setQsExpansionPosition(0.0f);
        if (this.mOnExpandImmediate) {
            this.mOnExpandImmediate = false;
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        if (!isThereNoView() && isPanelExpandImmediate()) {
            StringBuilder sb = new StringBuilder("expandImmediate ");
            float f = shadeExpansionChangeEvent.fraction;
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m(sb, f, "QsExpandAnimator");
            TouchAnimator touchAnimator = this.mPanelAlphaAnimator;
            if (touchAnimator != null) {
                touchAnimator.setPosition(f);
            }
            TouchAnimator touchAnimator2 = this.mPanelYAnimator;
            if (touchAnimator2 != null) {
                touchAnimator2.setPosition(f);
            }
            TouchAnimator touchAnimator3 = this.mShadeHeaderExpandImmediateAnimator;
            if (touchAnimator3 != null) {
                touchAnimator3.setPosition(f);
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelOpened() {
        QsAnimatorState.panelExpanded = true;
        if (isThereNoView()) {
            return;
        }
        Log.d("QsExpandAnimator", "onPanelOpened ");
        this.mQsPanel.post(this.mUpdateAnimators);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
        if (SecPanelSplitHelper.isEnabled()) {
            return;
        }
        updateAnimators();
        setQsExpansionPosition(1.0f);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onRtlChanged() {
        updateAnimators();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onStateChanged(int i) {
        QsAnimatorState.state = i;
        Log.d("QsExpandAnimator", "onStateChanged");
    }

    @Override // com.android.systemui.qs.QSHost.Callback
    public final void onTilesChanged() {
        if (isThereNoView()) {
            return;
        }
        this.mQsPanel.post(this.mUpdateAnimators);
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        if ("brightness_on_top".equals(str)) {
            updateAnimators();
        } else if ("qspanel_media_quickcontrol_bar_available".equals(str)) {
            updateAnimators();
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onUserSwitched(int i) {
        this.mUserChanged = true;
        if (SecPanelSplitHelper.isEnabled()) {
            return;
        }
        onPanelTransitionStateChanged(new PanelTransitionStateChangeEvent(false, -1.0f, -1));
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        Log.d("QsExpandAnimator", "onViewAttachedToWindow : " + view);
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        Log.d("QsExpandAnimator", "onViewDetachedFromWindow : " + view);
        QSHost qSHost = this.mHost;
        if (qSHost != null) {
            qSHost.removeCallback(this);
        }
        this.mQs = null;
        ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).removeTunable(this);
        this.mQsPanel.removeOnAttachStateChangeListener(this);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQs(QS qs) {
        if (qs == null) {
            destroyQSViews();
            return;
        }
        Log.d("QsExpandAnimator", "setQs : " + qs);
        this.mQs = qs;
        updateViews();
        QSHost qSHost = this.mQsPanelController.mHost;
        this.mHost = qSHost;
        if (qSHost != null) {
            qSHost.addCallback(this);
        }
        this.mQsPanel.post(this.mUpdateAnimators);
        if (this.mQsPanel.isAttachedToWindow()) {
            onViewAttachedToWindow(null);
        }
        ((BaseHeadsUpManager) this.mHeadsUpManager).addListener(this.onHeadsUpChangedListener);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQsExpansionPosition(float f) {
        TouchAnimator touchAnimator;
        if (isThereNoView() || (!this.mAnimatorsInitialiezed) || Float.isNaN(f) || QsAnimatorState.expandedByNotiOverScroll) {
            return;
        }
        Log.d("QsExpandAnimator", "setQsExpansionPosition " + f);
        this.mAnimatorForListener.setPosition(f);
        this.mLastPosition = f;
        float f2 = 1.0f;
        if (QsAnimatorState.state == 1) {
            if (QsAnimatorState.qsExpanded) {
                TouchAnimator touchAnimator2 = this.mPanelAlphaAnimator;
                if (touchAnimator2 != null) {
                    touchAnimator2.setPosition(f);
                }
                TouchAnimator touchAnimator3 = this.mShadeHeaderAnimator;
                if (touchAnimator3 != null) {
                    touchAnimator3.setPosition(f);
                }
                TouchAnimator touchAnimator4 = this.mHeaderAnimator;
                if (touchAnimator4 != null) {
                    touchAnimator4.setPosition(f);
                }
                f = 1.0f;
            } else {
                TouchAnimator touchAnimator5 = this.mShadeHeaderAnimator;
                if (touchAnimator5 != null) {
                    touchAnimator5.setPosition(0.0f);
                }
                TouchAnimator touchAnimator6 = this.mHeaderAnimator;
                if (touchAnimator6 != null) {
                    touchAnimator6.setPosition(f);
                }
            }
        } else if (!isPanelExpandImmediate()) {
            this.mQsRootPanel.setAlpha(1.0f);
        }
        ShadeRepository shadeRepository = this.mShadeRepository;
        if (shadeRepository != null && ((Boolean) ((ShadeRepositoryImpl) shadeRepository).legacyExpandImmediate.$$delegate_0.getValue()).booleanValue()) {
            f = 1.0f;
        }
        if (this.mQsPanel == null || !this.mQsPanelController.mExpandSettingsPanel) {
            if (!this.mForceUpdate && SecQSImplAnimatorBase.isDetailVisible()) {
                TouchAnimator touchAnimator7 = this.mHeaderAnimator;
                if (touchAnimator7 != null) {
                    touchAnimator7.setPosition(f);
                    return;
                }
                return;
            }
            f2 = f;
        }
        TouchAnimator touchAnimator8 = this.mQuickQsAnimator;
        if (touchAnimator8 != null) {
            touchAnimator8.setPosition(f2);
        }
        Iterator it = this.mTileAnimators.iterator();
        while (it.hasNext()) {
            ((TouchAnimator) it.next()).setPosition(f2);
        }
        TouchAnimator touchAnimator9 = this.mPanelBarAnimator;
        if (touchAnimator9 != null) {
            touchAnimator9.setPosition(f2);
        }
        TouchAnimator touchAnimator10 = this.mHeaderBarAnimator;
        if (touchAnimator10 != null) {
            touchAnimator10.setPosition(f2);
        }
        TouchAnimator touchAnimator11 = this.mQsButtonsAnimator;
        if (touchAnimator11 != null) {
            touchAnimator11.setPosition(f2);
        }
        if (this.mStackScrollerController != null && (touchAnimator = this.mHeaderAnimator) != null) {
            touchAnimator.setPosition(f2);
        }
        KeyguardEditModeController keyguardEditModeController = this.mKeyguardEditModeController;
        if (keyguardEditModeController == null || !((KeyguardEditModeControllerImpl) keyguardEditModeController).getVIRunning()) {
            TouchAnimator touchAnimator12 = this.mStackScrollLayoutAnimator;
            if (touchAnimator12 != null) {
                touchAnimator12.setPosition(f2);
            }
        } else {
            Log.d("QsExpandAnimator", "keyguard edit vi running");
        }
        this.mForceUpdate = false;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updateAnimators() {
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController;
        if (isThereNoView()) {
            return;
        }
        Log.d("QsExpandAnimator", "updateAnimators");
        clearAnimationState();
        this.mAllViews.clear();
        this.mTileAnimators.clear();
        updateViews();
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        builder.mListener = this;
        this.mAnimatorForListener = builder.build();
        Resources resources = this.mContext.getResources();
        if (!isThereNoView() && this.mQuickQSPanelTileContainer != null) {
            Log.d("QsExpandAnimator", "updateTileAnimator");
            TouchAnimator.Builder builder2 = new TouchAnimator.Builder();
            builder2.addFloat(this.mQuickQSPanelTileContainer, "alpha", 1.0f, 0.0f);
            builder2.addFloat(this.mQuickQSPanelTileContainer, "translationY", 0.0f, resources.getDimensionPixelSize(R.dimen.expand_animation_translation_y));
            builder2.mEndDelay = 0.5f;
            this.mQuickQsAnimator = builder2.build();
        }
        if (isThereNoView()) {
            obj = "view_visible_collapsed_state";
        } else {
            Log.d("QsExpandAnimator", "updateHeaderAnimator");
            this.mAllViews.add(new Pair("view_visible_collapsed_state", this.mQuickQSPanelTileContainer));
            TouchAnimator.Builder builder3 = new TouchAnimator.Builder();
            builder3.addFloat(this.mShadeHeader, "alpha", 0.0f, 1.0f);
            builder3.addFloat(this.mShadeHeader, "translationY", -30.0f, 0.0f);
            builder3.mEndDelay = 0.5f;
            this.mShadeHeaderAnimator = builder3.build();
            TouchAnimator.Builder builder4 = new TouchAnimator.Builder();
            builder4.addFloat(this.mShadeHeader, "alpha", 0.0f, 1.0f);
            builder4.mStartDelay = 0.3f;
            this.mShadeHeaderExpandImmediateAnimator = builder4.build();
            Configuration configuration = resources.getConfiguration();
            boolean z = configuration.orientation == 2;
            int headerTranslationXDiff = this.mResourcePicker.getHeaderTranslationXDiff(this.mContext);
            if (configuration.getLayoutDirection() == 1) {
                headerTranslationXDiff *= -1;
            }
            if (!z) {
                headerTranslationXDiff = 0;
            }
            int qQSPanelSidePadding = this.mResourcePicker.resourcePickHelper.getTargetPicker().getQQSPanelSidePadding(this.mContext);
            if (this.mNetworkSpeedContainer == null || this.mSystemIconContainer == null || this.mPrivacyContainer == null || this.mClockDateContainer == null || this.mSettingContainer == null || this.mPowerContainer == null || this.mEditContainer == null || this.mMumContainer == null) {
                obj = "view_visible_collapsed_state";
            } else {
                TouchAnimator.Builder builder5 = new TouchAnimator.Builder();
                float f = headerTranslationXDiff;
                obj = "view_visible_collapsed_state";
                builder5.addFloat(this.mNetworkSpeedContainer, "translationX", 0.0f, f);
                builder5.addFloat(this.mSystemIconContainer, "translationX", 0.0f, f);
                builder5.addFloat(this.mPrivacyContainer, "translationX", 0.0f, f);
                builder5.addFloat(this.mClockDateContainer, "translationX", 0.0f, -headerTranslationXDiff);
                builder5.addFloat(this.mClockDateContainer, "alpha", 1.0f, 0.0f);
                float f2 = -qQSPanelSidePadding;
                builder5.addFloat(this.mPowerContainer, "translationX", f2, 0.0f);
                builder5.addFloat(this.mEditContainer, "translationX", f2, 0.0f);
                builder5.addFloat(this.mMumContainer, "translationX", f2, 0.0f);
                this.mHeaderAnimator = builder5.build();
            }
            if (this.mPlmn != null) {
                TouchAnimator.Builder builder6 = new TouchAnimator.Builder();
                builder6.addFloat(this.mPlmn, "alpha", 0.0f, 1.0f);
                builder6.addFloat(this.mPlmn, "translationX", 0.0f, -headerTranslationXDiff);
                builder6.build();
            }
        }
        if (!isThereNoView()) {
            TouchAnimator.Builder builder7 = new TouchAnimator.Builder();
            builder7.addFloat(this.mQsRootPanel, "alpha", 0.0f, 1.0f);
            builder7.mStartDelay = 0.3f;
            this.mPanelAlphaAnimator = builder7.build();
            TouchAnimator.Builder builder8 = new TouchAnimator.Builder();
            builder8.addFloat(this.mQsRootPanel, "translationY", (-this.mHeader.getHeight()) * 0.2f, 0.0f);
            builder8.addFloat(this.mShadeHeader, "translationY", (-this.mHeader.getHeight()) * 0.2f, 0.0f);
            this.mPanelYAnimator = builder8.build();
            this.mAllViews.add(new Pair("view_visible_always", this.mTileChunkLayout));
            if (this.mMumContainer != null && this.mEditContainer != null && this.mPowerContainer != null) {
                TouchAnimator.Builder builder9 = new TouchAnimator.Builder();
                builder9.addFloat(this.mMumContainer, "alpha", 0.0f, 1.0f);
                builder9.addFloat(this.mEditContainer, "alpha", 0.0f, 1.0f);
                builder9.addFloat(this.mPowerContainer, "alpha", 0.0f, 1.0f);
                builder9.mStartDelay = 0.5f;
                this.mQsButtonsAnimator = builder9.build();
            }
            this.mAllViews.add(new Pair("view_visible_expanded_state", this.mMumContainer));
            this.mAllViews.add(new Pair("view_visible_expanded_state", this.mEditContainer));
            this.mAllViews.add(new Pair("view_visible_expanded_state", this.mPowerContainer));
            this.mAllViews.add(new Pair("view_visible_always", this.mQsRootPanel));
            this.mAllViews.add(new Pair("view_visible_always", this.mShadeHeader));
        }
        if (isThereNoView()) {
            obj2 = obj;
        } else {
            Log.d("QsExpandAnimator", "updateBarAnimator");
            BrightnessMediaDevicesBar brightnessMediaDevicesBar = this.mBrightnessMediaDevicesBar;
            View barView$1 = brightnessMediaDevicesBar != null ? getBarView$1(brightnessMediaDevicesBar.mMediaDevicesBar) : null;
            BrightnessMediaDevicesBar brightnessMediaDevicesBar2 = this.mBrightnessMediaDevicesBar;
            View barView$12 = brightnessMediaDevicesBar2 != null ? getBarView$1(brightnessMediaDevicesBar2.mBrightnessBar) : null;
            ArrayList arrayList = new ArrayList();
            arrayList.add(barView$1);
            arrayList.add(barView$12);
            View barView$13 = getBarView$1(this.mTopLargeTileBar);
            View barView$14 = getBarView$1(this.mTileChunkLayoutBar);
            View barView$15 = getBarView$1(this.mVideoCallMicModeBar);
            View barView$16 = getBarView$1(this.mMultiSIMBar);
            View barView$17 = getBarView$1(this.mBrightnessVolumeBar);
            View barView$18 = getBarView$1(this.mExpandedMediaPlayerBar);
            View barView$19 = getBarView$1(this.mQuickControlBar);
            View barView$110 = getBarView$1(this.mBottomLargeTileBar);
            View barView$111 = getBarView$1(this.mSmartViewLargeTileBar);
            View barView$112 = getBarView$1(this.mSecurityFooterBar);
            Object obj5 = "view_visible_expanded_state";
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(barView$13);
            arrayList2.add(barView$14);
            arrayList2.add(barView$15);
            arrayList2.add(barView$16);
            arrayList2.add(barView$17);
            arrayList2.add(barView$18);
            arrayList2.add(barView$19);
            arrayList2.add(barView$110);
            arrayList2.add(barView$111);
            arrayList2.add(barView$112);
            TouchAnimator.Builder builder10 = new TouchAnimator.Builder();
            TouchAnimator.Builder builder11 = new TouchAnimator.Builder();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                View view = (View) it.next();
                if (view != null) {
                    builder10.addFloat(view, "alpha", 1.0f, 0.0f);
                    builder10.addFloat(view, "translationY", 0.0f, resources.getDimensionPixelSize(R.dimen.expand_animation_translation_y));
                    builder10.mEndDelay = 0.5f;
                    obj4 = obj;
                    this.mAllViews.add(new Pair(obj4, view));
                } else {
                    obj4 = obj;
                }
                obj = obj4;
            }
            obj2 = obj;
            this.mHeaderBarAnimator = builder10.build();
            int i = 2;
            boolean z2 = resources.getConfiguration().orientation == 2;
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                View view2 = (View) it2.next();
                if (view2 != null) {
                    float[] fArr = new float[i];
                    // fill-array-data instruction
                    fArr[0] = 0.0f;
                    fArr[1] = 1.0f;
                    builder11.addFloat(view2, "alpha", fArr);
                    float[] fArr2 = new float[i];
                    fArr2[0] = z2 ? 10.0f : 20.0f;
                    fArr2[1] = 0.0f;
                    builder11.addFloat(view2, "translationY", fArr2);
                    float[] fArr3 = new float[i];
                    // fill-array-data instruction
                    fArr3[0] = 0.93f;
                    fArr3[1] = 1.0f;
                    builder11.addFloat(view2, "scaleX", fArr3);
                    float[] fArr4 = new float[i];
                    // fill-array-data instruction
                    fArr4[0] = 0.93f;
                    fArr4[1] = 1.0f;
                    builder11.addFloat(view2, "scaleY", fArr4);
                    builder11.mStartDelay = 0.5f;
                    builder11.build();
                    obj3 = obj5;
                    this.mAllViews.add(new Pair(obj3, view2));
                } else {
                    obj3 = obj5;
                }
                obj5 = obj3;
                i = 2;
            }
            this.mPanelBarAnimator = builder11.build();
        }
        if (!SecPanelSplitHelper.isEnabled() && (notificationStackScrollLayoutController = this.mStackScrollerController) != null && notificationStackScrollLayoutController.mView != null) {
            TouchAnimator.Builder builder12 = new TouchAnimator.Builder();
            builder12.addFloat(this.mStackScrollerController.mView, "alpha", 1.0f, 0.0f);
            builder12.mStartDelay = 0.93f;
            builder12.mEndDelay = 0.04f;
            this.mStackScrollLayoutAnimator = builder12.build();
            this.mAllViews.add(new Pair(obj2, this.mStackScrollerController.mView));
        }
        this.mAnimatorsInitialiezed = true;
        setQsExpansionPosition(this.mLastPosition);
    }

    public final void updateViews() {
        Log.d("QsExpandAnimator", "updateViews");
        SecQSPanel secQSPanel = this.mQsPanel;
        if (secQSPanel != null) {
            secQSPanel.removeOnLayoutChangeListener(this);
            this.mQsPanel.removeOnAttachStateChangeListener(this);
        }
        MotionLayout motionLayout = this.mShadeHeaderController.header;
        this.mShadeHeader = motionLayout;
        this.mPlmn = (ViewGroup) motionLayout.findViewById(R.id.anim_view);
        SecQuickStatusBarHeader header = getHeader();
        this.mHeader = header;
        if (header != null) {
            this.mSettingContainer = header.findViewById(R.id.settings_button_container);
            this.mMumContainer = this.mHeader.findViewById(R.id.mum_button_container);
            this.mPowerContainer = this.mHeader.findViewById(R.id.power_button_container);
            this.mEditContainer = this.mHeader.findViewById(R.id.edit_button_container);
            this.mQuickQsPanel = (SecQuickQSPanel) this.mHeader.findViewById(R.id.quick_qs_panel);
            this.mClockDateContainer = this.mHeader.findViewById(R.id.clock_parent);
        }
        this.mNetworkSpeedContainer = this.mShadeHeader.findViewById(R.id.quick_qs_network_speed_container);
        this.mSystemIconContainer = this.mShadeHeader.findViewById(R.id.shade_header_system_icons);
        this.mPrivacyContainer = this.mShadeHeader.findViewById(R.id.privacy_container);
        SecQuickQSPanel secQuickQSPanel = this.mQuickQsPanel;
        if (secQuickQSPanel != null) {
            this.mQuickQSPanelTileContainer = secQuickQSPanel.findViewWithTag("qqs_expand_anim");
        }
        if (this.mQuickQSPanelTileContainer == null) {
            Log.d("QsExpandAnimator", "qqs_expand_anim is not inflated yet.!!");
            this.mQuickQSPanelTileContainer = this.mQuickQsPanel;
        }
        this.mQsRootPanel = this.mQs.getView();
        SecQSPanel secQSPanel2 = (SecQSPanel) this.mQs.getView().findViewById(R.id.quick_settings_panel);
        this.mQsPanel = secQSPanel2;
        if (secQSPanel2 != null) {
            secQSPanel2.addOnAttachStateChangeListener(this);
            this.mQsPanel.addOnLayoutChangeListener(this);
        }
        this.mTileChunkLayout = (SecTileChunkLayout) this.mQsPanelController.mTileLayout;
        this.mBrightnessMediaDevicesBar = (BrightnessMediaDevicesBar) ((BarItemImpl) this.mBarController.mCollapsedBarItems.parallelStream().filter(new BarController$$ExternalSyntheticLambda6(BarType.BRIGHTNESS_MEDIA_DEVICES, 1)).findFirst().orElse(null));
        this.mBrightnessVolumeBar = (BrightnessVolumeBar) this.mBarController.getBarInExpanded(BarType.BRIGHTNESS_VOLUME);
        this.mTopLargeTileBar = (TopLargeTileBar) this.mBarController.getBarInExpanded(BarType.TOP_LARGE_TILE);
        this.mBottomLargeTileBar = (BottomLargeTileBar) this.mBarController.getBarInExpanded(BarType.BOTTOM_LARGE_TILE);
        this.mSmartViewLargeTileBar = (SmartViewLargeTileBar) this.mBarController.getBarInExpanded(BarType.SMARTVIEW_LARGE_TILE);
        this.mTileChunkLayoutBar = (TileChunkLayoutBar) this.mBarController.getBarInExpanded(BarType.TILE_CHUNK_LAYOUT);
        this.mVideoCallMicModeBar = (VideoCallMicModeBar) this.mBarController.getBarInExpanded(BarType.VIDEO_CALL_MIC_MODE);
        this.mMultiSIMBar = (MultiSIMPreferredSlotBar) this.mBarController.getBarInExpanded(BarType.MULTI_SIM_PREFERRED_SLOT);
        this.mSecurityFooterBar = (SecurityFooterBar) this.mBarController.getBarInExpanded(BarType.SECURITY_FOOTER);
        this.mExpandedMediaPlayerBar = (QSMediaPlayerBar) this.mBarController.getBarInExpanded(BarType.QS_MEDIA_PLAYER);
        this.mQuickControlBar = (QuickControlBar) this.mBarController.getBarInExpanded(BarType.QUICK_CONTROL);
    }
}
