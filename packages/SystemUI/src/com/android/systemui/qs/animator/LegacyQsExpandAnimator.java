package com.android.systemui.qs.animator;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.widget.MotionLayout;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSImpl;
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
import com.android.systemui.qs.bar.DataUsageBar;
import com.android.systemui.qs.bar.MultiSIMPreferredSlotBar;
import com.android.systemui.qs.bar.QSMediaPlayerBar;
import com.android.systemui.qs.bar.QuickControlBar;
import com.android.systemui.qs.bar.SecurityFooterBar;
import com.android.systemui.qs.bar.SmartViewLargeTileBar;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import com.android.systemui.qs.bar.TopLargeTileBar;
import com.android.systemui.qs.bar.VideoCallMicModeBar;
import com.android.systemui.samsung.quicksetting.SecQSPanelComposeAdapter;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.ViewUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class LegacyQsExpandAnimator extends SecQSImplAnimatorBase implements QSHost.Callback, TouchAnimator.Listener, View.OnAttachStateChangeListener, TunerService.Tunable, View.OnLayoutChangeListener {
    public TouchAnimator mAnimatorForListener;
    public final BarController mBarController;
    public BottomLargeTileBar mBottomLargeTileBar;
    public BrightnessMediaDevicesBar mBrightnessMediaDevicesBar;
    public BrightnessVolumeBar mBrightnessVolumeBar;
    public View mButtonsContainer;
    public View mClockDateContainer;
    public final Context mContext;
    public DataUsageBar mDataUsageBar;
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
    public final AnonymousClass1 onHeadsUpChangedListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.qs.animator.LegacyQsExpandAnimator.1
        @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
        public final void onHeadsUpPinnedModeChanged(boolean z) {
            LegacyQsExpandAnimator.this.isHeadsUpPinnedMode = z;
        }
    };
    public final LegacyQsExpandAnimator$$ExternalSyntheticLambda0 mUpdateAnimators = new LegacyQsExpandAnimator$$ExternalSyntheticLambda0(this);

    /* JADX WARN: Type inference failed for: r9v6, types: [com.android.systemui.qs.animator.LegacyQsExpandAnimator$1] */
    public LegacyQsExpandAnimator(Context context, SecQSPanelController secQSPanelController, BarController barController, ShadeHeaderController shadeHeaderController, SecQSPanelResourcePicker secQSPanelResourcePicker, KeyguardEditModeController keyguardEditModeController, HeadsUpManager headsUpManager, SecQSPanelComposeAdapter secQSPanelComposeAdapter) {
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
        if (((HeadsUpManagerImpl) this.mHeadsUpManager).mHasPinnedNotification) {
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
        ((HeadsUpManagerImpl) this.mHeadsUpManager).removeListener(this.onHeadsUpChangedListener);
        this.mAllViews.clear();
        this.mTileAnimators.clear();
        this.mQs = null;
        this.mQuickQsPanel = null;
        this.mQsPanel = null;
        this.mHeader = null;
        this.mTileChunkLayout = null;
        this.mButtonsContainer = null;
        this.mMumContainer = null;
        this.mEditContainer = null;
        this.mPowerContainer = null;
        this.mQuickQSPanelTileContainer = null;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("QsExpandAnimator ============================================= ");
        SecQSImplAnimatorBase.gatherStateOfViews(arrayList, (ArrayList) this.mAllViews.stream().map(new LegacyQsExpandAnimator$$ExternalSyntheticLambda1()).filter(new LegacyQsExpandAnimator$$ExternalSyntheticLambda2()).collect(Collectors.toList()));
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
            this.mQsRootPanel.post(new LegacyQsExpandAnimator$$ExternalSyntheticLambda0(this));
            if (SecQSImplAnimatorBase.isDetailVisible()) {
                QsAnimatorState qsAnimatorState = QsAnimatorState.INSTANCE;
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
    public final void onPanelClosed$1() {
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
            setPanelImmediatePosition(shadeExpansionChangeEvent.fraction);
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

    public final void setPanelImmediatePosition(float f) {
        Log.d("QsExpandAnimator", "expandImmediate " + f);
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

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQs(QS qs) {
        if (qs == null) {
            destroyQSViews();
            return;
        }
        Log.d("QsExpandAnimator", "setQs : " + qs);
        this.mQs = (QSImpl) qs;
        updateViews$2();
        QSHost qSHost = this.mQsPanelController.mHost;
        this.mHost = qSHost;
        if (qSHost != null) {
            qSHost.addCallback(this);
        }
        this.mQsPanel.post(this.mUpdateAnimators);
        if (this.mQsPanel.isAttachedToWindow()) {
            onViewAttachedToWindow(null);
        }
        ((HeadsUpManagerImpl) this.mHeadsUpManager).addListener(this.onHeadsUpChangedListener);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQsExpansionPosition(float f) {
        TouchAnimator touchAnimator;
        if (isThereNoView() || !this.mAnimatorsInitialiezed || Float.isNaN(f) || QsAnimatorState.expandedByNotiOverScroll) {
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
        if (shadeRepository == null || !((Boolean) ((ShadeRepositoryImpl) shadeRepository).legacyExpandImmediate.$$delegate_0.getValue()).booleanValue()) {
            f2 = f;
        } else {
            setPanelImmediatePosition(1.0f);
        }
        if (!this.mForceUpdate && SecQSImplAnimatorBase.isDetailVisible()) {
            TouchAnimator touchAnimator7 = this.mHeaderAnimator;
            if (touchAnimator7 != null) {
                touchAnimator7.setPosition(f2);
                return;
            }
            return;
        }
        TouchAnimator touchAnimator8 = this.mQuickQsAnimator;
        if (touchAnimator8 != null) {
            touchAnimator8.setPosition(f2);
        }
        ArrayList arrayList = this.mTileAnimators;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((TouchAnimator) obj).setPosition(f2);
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updateAnimators() {
        int i;
        float f;
        float f2;
        int qQSPanelSidePadding;
        int i2;
        LegacyQsExpandAnimator legacyQsExpandAnimator;
        Object obj;
        String str;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController;
        if (isThereNoView()) {
            return;
        }
        Log.d("QsExpandAnimator", "updateAnimators");
        if (QsAnimatorState.qsExpanded || QsAnimatorState.panelExpanded) {
            clearAnimationState();
        }
        this.mAllViews.clear();
        this.mTileAnimators.clear();
        updateViews$2();
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
        String str2 = "view_visible_collapsed_state";
        if (isThereNoView()) {
            i = 0;
            i2 = 1;
            f2 = 0.0f;
            f = 1.0f;
        } else {
            Log.d("QsExpandAnimator", "updateHeaderAnimator");
            i = 0;
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
            SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
            f = 1.0f;
            Context context = this.mContext;
            int qQSPanelSidePadding2 = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getQQSPanelSidePadding(context);
            if (context.getResources().getConfiguration().orientation == 2) {
                qQSPanelSidePadding2 = 0;
            }
            if (configuration.getLayoutDirection() == 1) {
                qQSPanelSidePadding2 *= -1;
            }
            if (!z) {
                qQSPanelSidePadding2 = 0;
            }
            boolean z2 = ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isFoldWide() && resources.getConfiguration().orientation == 1;
            if (z2) {
                f2 = 0.0f;
                qQSPanelSidePadding = 0;
            } else {
                f2 = 0.0f;
                qQSPanelSidePadding = this.mResourcePicker.getQQSPanelSidePadding(this.mContext);
            }
            int qQSPanelSidePadding3 = z2 ? this.mResourcePicker.getQQSPanelSidePadding(this.mContext) : 0;
            if (QpRune.QUICK_TABLET || ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
                qQSPanelSidePadding3 = this.mResourcePicker.getPanelSidePadding(this.mContext) * (-1);
                qQSPanelSidePadding = 0;
            }
            if (resources.getConfiguration().getLayoutDirection() == 1) {
                qQSPanelSidePadding *= -1;
                qQSPanelSidePadding3 *= -1;
            }
            if (this.mNetworkSpeedContainer == null || this.mSystemIconContainer == null || this.mPrivacyContainer == null || this.mClockDateContainer == null || this.mButtonsContainer == null) {
                i2 = 1;
            } else {
                TouchAnimator.Builder builder5 = new TouchAnimator.Builder();
                i2 = 1;
                float f3 = qQSPanelSidePadding2;
                builder5.addFloat(this.mNetworkSpeedContainer, "translationX", f2, f3);
                builder5.addFloat(this.mSystemIconContainer, "translationX", f2, f3);
                builder5.addFloat(this.mPrivacyContainer, "translationX", f2, f3);
                builder5.addFloat(this.mClockDateContainer, "translationX", f2, -qQSPanelSidePadding2);
                builder5.addFloat(this.mClockDateContainer, "alpha", 1.0f, 0.0f);
                builder5.addFloat(this.mButtonsContainer, "translationX", -qQSPanelSidePadding, qQSPanelSidePadding3);
                this.mHeaderAnimator = builder5.build();
            }
        }
        if (!isThereNoView()) {
            TouchAnimator.Builder builder6 = new TouchAnimator.Builder();
            builder6.addFloat(this.mQsRootPanel, "alpha", 0.0f, 1.0f);
            builder6.mStartDelay = 0.3f;
            this.mPanelAlphaAnimator = builder6.build();
            TouchAnimator.Builder builder7 = new TouchAnimator.Builder();
            View view = this.mQsRootPanel;
            float[] fArr = new float[2];
            fArr[i] = (-this.mHeader.getHeight()) * 0.2f;
            fArr[i2] = f2;
            builder7.addFloat(view, "translationY", fArr);
            MotionLayout motionLayout = this.mShadeHeader;
            float[] fArr2 = new float[2];
            fArr2[i] = (-this.mHeader.getHeight()) * 0.2f;
            fArr2[i2] = f2;
            builder7.addFloat(motionLayout, "translationY", fArr2);
            this.mPanelYAnimator = builder7.build();
            this.mAllViews.add(new Pair("view_visible_always", this.mTileChunkLayout));
            if (this.mMumContainer != null && this.mEditContainer != null && this.mPowerContainer != null) {
                TouchAnimator.Builder builder8 = new TouchAnimator.Builder();
                builder8.addFloat(this.mMumContainer, "alpha", 0.0f, 1.0f);
                builder8.addFloat(this.mEditContainer, "alpha", 0.0f, 1.0f);
                builder8.addFloat(this.mPowerContainer, "alpha", 0.0f, 1.0f);
                builder8.mStartDelay = 0.5f;
                this.mQsButtonsAnimator = builder8.build();
            }
            this.mAllViews.add(new Pair("view_visible_expanded_state", this.mMumContainer));
            this.mAllViews.add(new Pair("view_visible_expanded_state", this.mEditContainer));
            this.mAllViews.add(new Pair("view_visible_expanded_state", this.mPowerContainer));
            this.mAllViews.add(new Pair("view_visible_always", this.mQsRootPanel));
            this.mAllViews.add(new Pair("view_visible_always", this.mShadeHeader));
        }
        if (isThereNoView()) {
            legacyQsExpandAnimator = this;
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
            Object obj2 = "view_visible_expanded_state";
            View barView$112 = getBarView$1(this.mSecurityFooterBar);
            String str3 = "view_visible_collapsed_state";
            View barView$113 = getBarView$1(this.mDataUsageBar);
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
            arrayList2.add(barView$113);
            TouchAnimator.Builder builder9 = new TouchAnimator.Builder();
            TouchAnimator.Builder builder10 = new TouchAnimator.Builder();
            int size = arrayList.size();
            int i3 = i;
            while (i3 < size) {
                Object obj3 = arrayList.get(i3);
                i3++;
                View view2 = (View) obj3;
                if (view2 != null) {
                    builder9.addFloat(view2, "alpha", 1.0f, 0.0f);
                    float dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.expand_animation_translation_y);
                    float[] fArr3 = new float[2];
                    fArr3[i] = f2;
                    fArr3[i2] = dimensionPixelSize;
                    builder9.addFloat(view2, "translationY", fArr3);
                    builder9.mEndDelay = 0.5f;
                    str = str3;
                    this.mAllViews.add(new Pair(str, view2));
                } else {
                    str = str3;
                }
                str3 = str;
            }
            legacyQsExpandAnimator = this;
            str2 = str3;
            legacyQsExpandAnimator.mHeaderBarAnimator = builder9.build();
            int i4 = 2;
            int i5 = resources.getConfiguration().orientation == 2 ? i2 : i;
            int size2 = arrayList2.size();
            int i6 = i;
            while (i6 < size2) {
                Object obj4 = arrayList2.get(i6);
                i6++;
                View view3 = (View) obj4;
                if (view3 != null) {
                    float[] fArr4 = new float[i4];
                    // fill-array-data instruction
                    fArr4[0] = 0.0f;
                    fArr4[1] = 1.0f;
                    builder10.addFloat(view3, "alpha", fArr4);
                    float[] fArr5 = new float[i4];
                    fArr5[i] = i5 != 0 ? 10.0f : 20.0f;
                    fArr5[i2] = f2;
                    builder10.addFloat(view3, "translationY", fArr5);
                    float f4 = legacyQsExpandAnimator.SCALE_DOWN_RATIO;
                    float[] fArr6 = new float[i4];
                    fArr6[i] = f4;
                    fArr6[i2] = f;
                    builder10.addFloat(view3, "scaleX", fArr6);
                    float[] fArr7 = new float[i4];
                    fArr7[i] = f4;
                    fArr7[i2] = f;
                    builder10.addFloat(view3, "scaleY", fArr7);
                    builder10.mStartDelay = 0.5f;
                    builder10.build();
                    obj = obj2;
                    legacyQsExpandAnimator.mAllViews.add(new Pair(obj, view3));
                } else {
                    obj = obj2;
                }
                obj2 = obj;
                i4 = 2;
            }
            legacyQsExpandAnimator.mPanelBarAnimator = builder10.build();
        }
        if (!SecPanelSplitHelper.isEnabled() && (notificationStackScrollLayoutController = legacyQsExpandAnimator.mStackScrollerController) != null && notificationStackScrollLayoutController.mView != null) {
            TouchAnimator.Builder builder11 = new TouchAnimator.Builder();
            builder11.addFloat(legacyQsExpandAnimator.mStackScrollerController.mView, "alpha", 1.0f, 0.0f);
            builder11.mStartDelay = 0.93f;
            builder11.mEndDelay = 0.04f;
            legacyQsExpandAnimator.mStackScrollLayoutAnimator = builder11.build();
            legacyQsExpandAnimator.mAllViews.add(new Pair(str2, legacyQsExpandAnimator.mStackScrollerController.mView));
        }
        boolean z3 = i2;
        legacyQsExpandAnimator.mAnimatorsInitialiezed = z3;
        if (legacyQsExpandAnimator.mLastPosition == f && QsAnimatorState.state == z3) {
            legacyQsExpandAnimator.mLastPosition = f2;
        }
        legacyQsExpandAnimator.setQsExpansionPosition(legacyQsExpandAnimator.mLastPosition);
    }

    public final void updateViews$2() {
        Log.d("QsExpandAnimator", "updateViews");
        SecQSPanel secQSPanel = this.mQsPanel;
        if (secQSPanel != null) {
            secQSPanel.removeOnLayoutChangeListener(this);
            this.mQsPanel.removeOnAttachStateChangeListener(this);
        }
        this.mShadeHeader = this.mShadeHeaderController.header;
        SecQuickStatusBarHeader secQuickStatusBarHeader = this.mQs.getView() instanceof ViewGroup ? ViewUtil.getSecQuickStatusBarHeader((ViewGroup) this.mQs.getView()) : null;
        this.mHeader = secQuickStatusBarHeader;
        if (secQuickStatusBarHeader != null) {
            this.mButtonsContainer = secQuickStatusBarHeader.findViewById(R.id.header_settings_container);
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
        this.mDataUsageBar = (DataUsageBar) this.mBarController.getBarInExpanded(BarType.DATAUSAGE);
    }
}
