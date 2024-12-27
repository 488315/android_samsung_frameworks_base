package com.android.systemui.qs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.app.animation.Interpolators;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.indexsearch.SystemUIIndexMediator;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.qs.QSContainerController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSBackupRestoreManager;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.bar.BarBackUpRestoreHelper;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda0;
import com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda1;
import com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda3;
import com.android.systemui.qs.bar.BarController.AnonymousClass3;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.qs.buttons.QSButtonsContainerController;
import com.android.systemui.qs.customize.QSCustomizerController;
import com.android.systemui.qs.customize.viewcontroller.QSCMainViewController;
import com.android.systemui.qs.dagger.QSComponent;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.transition.LargeScreenShadeInterpolator;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.disableflags.DisableFlagsLogger;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.util.ViewUtil;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.StateFlowImpl;

public final class QSImpl implements QS, CommandQueue.Callbacks, StatusBarStateController.StateListener, Dumpable {
    public final KeyguardBypassController mBypassController;
    public final CommandQueue mCommandQueue;
    public QSContainerImpl mContainer;
    public final DumpManager mDumpManager;
    public final FooterActionsController mFooterActionsController;
    public final FooterActionsViewModel.Factory mFooterActionsViewModelFactory;
    public SecQuickStatusBarHeader mHeader;
    public boolean mHeaderAnimating;
    public boolean mInSplitShade;
    public boolean mIsSmallScreen;
    public boolean mLastKeyguardAndExpanded;
    public float mLastPanelFraction;
    public int mLastViewHeight;
    public int mLayoutDirection;
    public boolean mListening;
    public float mLockscreenToShadeProgress;
    public boolean mOverScrolling;
    public QS.HeightListener mPanelView;
    public QSContainerImplController mQSContainerImplController;
    public QSCustomizerController mQSCustomizerController;
    public FooterActionsViewModel mQSFooterActionsViewModel;
    public SecQSPanelController mQSPanelController;
    public NonInterceptingScrollView mQSPanelScrollView;
    public QSSquishinessController mQSSquishinessController;
    public final QSDisableFlagsLogger mQsDisableFlagsLogger;
    public boolean mQsDisabled;
    public boolean mQsExpanded;
    public final MediaHost mQsMediaHost;
    public boolean mQsVisible;
    public SecQuickQSPanelController mQuickQSPanelController;
    public final RemoteInputQuickSettingsDisabler mRemoteInputQuickSettingsDisabler;
    public View mRootView;
    public QS.ScrollListener mScrollListener;
    public final SecQSImpl mSecQSImpl;
    public boolean mShouldUpdateMediaSquishiness;
    public boolean mShowCollapsedOnKeyguard;
    public boolean mStackScrollerOverscrolling;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public boolean mTransitioningToFullShade;
    public final Rect mQsBounds = new Rect();
    public float mLastQSExpansion = -1.0f;
    public float mSquishinessFraction = 1.0f;
    public final int[] mLocationTemp = new int[2];
    public int mStatusBarState = -1;
    public final int[] mTmpLocation = new int[2];
    public final ListeningAndVisibilityLifecycleOwner mListeningAndVisibilityLifecycleOwner = new ListeningAndVisibilityLifecycleOwner();

    class ListeningAndVisibilityLifecycleOwner implements LifecycleOwner {
        public final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
        public boolean mDestroyed = false;

        public ListeningAndVisibilityLifecycleOwner() {
            updateState();
        }

        @Override // androidx.lifecycle.LifecycleOwner
        public final Lifecycle getLifecycle() {
            return this.mLifecycleRegistry;
        }

        public final void updateState() {
            boolean z = this.mDestroyed;
            LifecycleRegistry lifecycleRegistry = this.mLifecycleRegistry;
            if (z) {
                lifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
                return;
            }
            QSImpl qSImpl = QSImpl.this;
            if (!qSImpl.mListening) {
                lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
            } else if (qSImpl.mQsVisible) {
                lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
            } else {
                lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
            }
        }
    }

    public QSImpl(RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler, SysuiStatusBarStateController sysuiStatusBarStateController, CommandQueue commandQueue, MediaHost mediaHost, MediaHost mediaHost2, KeyguardBypassController keyguardBypassController, QSDisableFlagsLogger qSDisableFlagsLogger, DumpManager dumpManager, QSLogger qSLogger, FooterActionsController footerActionsController, FooterActionsViewModel.Factory factory, LargeScreenShadeInterpolator largeScreenShadeInterpolator, SecPanelSplitHelper secPanelSplitHelper, LockscreenShadeTransitionController lockscreenShadeTransitionController) {
        this.mRemoteInputQuickSettingsDisabler = remoteInputQuickSettingsDisabler;
        this.mQsMediaHost = mediaHost;
        this.mQsDisableFlagsLogger = qSDisableFlagsLogger;
        this.mCommandQueue = commandQueue;
        this.mBypassController = keyguardBypassController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mDumpManager = dumpManager;
        this.mFooterActionsController = footerActionsController;
        this.mFooterActionsViewModelFactory = factory;
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        this.mSecQSImpl = new SecQSImpl(new Function0() { // from class: com.android.systemui.qs.QSImpl$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(QSImpl.this.mQsExpanded);
            }
        }, secPanelSplitHelper, new QSImpl$$ExternalSyntheticLambda2(this, 2), lockscreenShadeTransitionController);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void animateHeaderSlidingOut() {
        if (this.mRootView.getY() == (-this.mHeader.getHeight())) {
            return;
        }
        this.mHeaderAnimating = true;
        this.mRootView.animate().y(-this.mHeader.getHeight()).setStartDelay(0L).setDuration(360L).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.QSImpl.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                View view = QSImpl.this.mRootView;
                if (view != null) {
                    view.animate().setListener(null);
                }
                QSImpl qSImpl = QSImpl.this;
                qSImpl.mHeaderAnimating = false;
                qSImpl.updateQsState();
            }
        }).start();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void closeCustomizer() {
        this.mQSCustomizerController.hide(true);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void closeDetail() {
        SecQSImpl secQSImpl = this.mSecQSImpl;
        ((SecQSDetailController) secQSImpl.detailController$delegate.getValue()).closeDetail();
        QSCMainViewController qSCMainViewController = secQSImpl.qscMainViewController;
        if (qSCMainViewController != null) {
            qSCMainViewController.close();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        if (i != this.mRootView.getContext().getDisplayId()) {
            return;
        }
        this.mRemoteInputQuickSettingsDisabler.getClass();
        DisableFlagsLogger.DisableState disableState = new DisableFlagsLogger.DisableState(i2, i3);
        DisableFlagsLogger.DisableState disableState2 = new DisableFlagsLogger.DisableState(i2, i3);
        final QSDisableFlagsLogger qSDisableFlagsLogger = this.mQsDisableFlagsLogger;
        qSDisableFlagsLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.QSDisableFlagsLogger$logDisableFlagChange$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return QSDisableFlagsLogger.this.disableFlagsLogger.getDisableFlagsString(new DisableFlagsLogger.DisableState(logMessage.getInt1(), logMessage.getInt2()), new DisableFlagsLogger.DisableState((int) logMessage.getLong1(), (int) logMessage.getLong2()));
            }
        };
        LogBuffer logBuffer = qSDisableFlagsLogger.buffer;
        LogMessage obtain = logBuffer.obtain("QSDisableFlagsLog", logLevel, function1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = disableState.disable1;
        logMessageImpl.int2 = disableState.disable2;
        logMessageImpl.long1 = disableState2.disable1;
        logMessageImpl.long2 = disableState2.disable2;
        logBuffer.commit(obtain);
        int i4 = i3 & 1;
        int i5 = 0;
        boolean z2 = i4 != 0;
        if (z2 == this.mQsDisabled) {
            return;
        }
        this.mQsDisabled = z2;
        QSContainerImpl qSContainerImpl = this.mContainer;
        qSContainerImpl.getClass();
        boolean z3 = i4 != 0;
        if (z3 != qSContainerImpl.mQsDisabled) {
            qSContainerImpl.mQsDisabled = z3;
        }
        SecQuickStatusBarHeader secQuickStatusBarHeader = this.mHeader;
        secQuickStatusBarHeader.getClass();
        boolean z4 = i4 != 0;
        if (z4 != secQuickStatusBarHeader.mQsDisabled) {
            secQuickStatusBarHeader.mQsDisabled = z4;
            SecQuickQSPanel secQuickQSPanel = secQuickStatusBarHeader.mHeaderQsPanel;
            if (z4 != secQuickQSPanel.mDisabledByPolicy) {
                secQuickQSPanel.mDisabledByPolicy = z4;
                secQuickQSPanel.setVisibility((z4 || SecPanelSplitHelper.isEnabled()) ? 8 : 0);
            }
            secQuickStatusBarHeader.mDateButtonContainer.setVisibility(secQuickStatusBarHeader.mQsDisabled ? 8 : 0);
            ViewGroup.LayoutParams layoutParams = secQuickStatusBarHeader.getLayoutParams();
            if (!secQuickStatusBarHeader.mQsDisabled) {
                i5 = -2;
            } else if (QpRune.QUICK_TABLET) {
                i5 = ((ShadeHeaderController) secQuickStatusBarHeader.mResourcePicker.resourcePickHelper.getTargetPicker().common.shadeHeaderController$delegate.getValue()).header.getHeight();
            }
            layoutParams.height = i5;
            secQuickStatusBarHeader.setLayoutParams(layoutParams);
        }
        updateQsState();
    }

    @Override // com.android.systemui.Dumpable
    @NeverCompile
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("QSImpl:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mQsBounds: " + this.mQsBounds);
        indentingPrintWriter.println("mQsExpanded: " + this.mQsExpanded);
        indentingPrintWriter.println("mHeaderAnimating: " + this.mHeaderAnimating);
        indentingPrintWriter.println("mStackScrollerOverscrolling: " + this.mStackScrollerOverscrolling);
        indentingPrintWriter.println("mListening: " + this.mListening);
        indentingPrintWriter.println("mQsVisible: " + this.mQsVisible);
        indentingPrintWriter.println("mLayoutDirection: " + this.mLayoutDirection);
        indentingPrintWriter.println("mLastQSExpansion: " + this.mLastQSExpansion);
        indentingPrintWriter.println("mLastPanelFraction: " + this.mLastPanelFraction);
        indentingPrintWriter.println("mSquishinessFraction: " + this.mSquishinessFraction);
        indentingPrintWriter.println("mQsDisabled: " + this.mQsDisabled);
        indentingPrintWriter.println("mTemp: " + Arrays.toString(this.mLocationTemp));
        indentingPrintWriter.println("mShowCollapsedOnKeyguard: " + this.mShowCollapsedOnKeyguard);
        indentingPrintWriter.println("mLastKeyguardAndExpanded: " + this.mLastKeyguardAndExpanded);
        indentingPrintWriter.println("mStatusBarState: " + StatusBarState.toString(this.mStatusBarState));
        indentingPrintWriter.println("mTmpLocation: " + Arrays.toString(this.mTmpLocation));
        indentingPrintWriter.println("mLastViewHeight: " + this.mLastViewHeight);
        indentingPrintWriter.println("mLastHeaderTranslation: 0.0");
        indentingPrintWriter.println("mInSplitShade: " + this.mInSplitShade);
        indentingPrintWriter.println("mTransitioningToFullShade: " + this.mTransitioningToFullShade);
        indentingPrintWriter.println("mLockscreenToShadeProgress: " + this.mLockscreenToShadeProgress);
        indentingPrintWriter.println("mOverScrolling: " + this.mOverScrolling);
        indentingPrintWriter.println("mShouldUpdateMediaSquishiness: " + this.mShouldUpdateMediaSquishiness);
        indentingPrintWriter.println("isCustomizing: " + this.mQSCustomizerController.isCustomizing());
        View view = this.mRootView;
        if (view != null) {
            indentingPrintWriter.println("top: " + view.getTop());
            indentingPrintWriter.println("y: " + view.getY());
            indentingPrintWriter.println("translationY: " + view.getTranslationY());
            indentingPrintWriter.println("alpha: " + view.getAlpha());
            indentingPrintWriter.println("height: " + view.getHeight());
            indentingPrintWriter.println("measuredHeight: " + view.getMeasuredHeight());
            indentingPrintWriter.println("clipBounds: " + view.getClipBounds());
        } else {
            indentingPrintWriter.println("getView(): null");
        }
        SecQuickStatusBarHeader secQuickStatusBarHeader = this.mHeader;
        if (secQuickStatusBarHeader == null) {
            indentingPrintWriter.println("mHeader: null");
            return;
        }
        indentingPrintWriter.println("headerHeight: " + secQuickStatusBarHeader.getHeight());
        int visibility = secQuickStatusBarHeader.getVisibility();
        indentingPrintWriter.println("Header visibility: ".concat(visibility == 0 ? "VISIBLE" : visibility == 4 ? "INVISIBLE" : "GONE"));
    }

    @Override // com.android.systemui.plugins.FragmentBase
    public final Context getContext() {
        return this.mRootView.getContext();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final int getDesiredHeight() {
        if (this.mQSCustomizerController.isCustomizing()) {
            return this.mRootView.getHeight();
        }
        SecQSImpl secQSImpl = this.mSecQSImpl;
        if (secQSImpl == null) {
            return this.mRootView.getMeasuredHeight();
        }
        return ((ShadeHeaderController) secQSImpl.shadeHeaderController$delegate.getValue()).header.getHeight() + this.mRootView.getMeasuredHeight();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final View getHeader() {
        return this.mHeader;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final int getHeightDiff() {
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        return this.mHeader.getPaddingBottom() + (this.mQSPanelScrollView.getBottom() - this.mHeader.getBottom());
    }

    public ListeningAndVisibilityLifecycleOwner getListeningAndVisibilityLifecycleOwner() {
        return this.mListeningAndVisibilityLifecycleOwner;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final int getQsMinExpansionHeight() {
        if (this.mInSplitShade) {
            this.mRootView.getLocationOnScreen(this.mLocationTemp);
            return this.mRootView.getHeight() + ((int) (r1[1] - this.mRootView.getTranslationY()));
        }
        SecQSImpl secQSImpl = this.mSecQSImpl;
        if (secQSImpl != null) {
            return this.mHeader.getHeight() + (QpRune.QUICK_TABLET_BG ? 0 : ((ShadeHeaderController) secQSImpl.shadeHeaderController$delegate.getValue()).header.getHeight());
        }
        return this.mHeader.getHeight();
    }

    public int getStatusBarState() {
        return this.mStatusBarState;
    }

    @Override // com.android.systemui.plugins.FragmentBase
    public final View getView() {
        return this.mRootView;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void hideImmediately() {
        this.mRootView.animate().cancel();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final boolean isCustomizing() {
        return this.mQSCustomizerController.isCustomizing();
    }

    public boolean isExpanded() {
        return this.mQsExpanded;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final boolean isFullyCollapsed() {
        float f = this.mLastQSExpansion;
        return f == 0.0f || f == -1.0f;
    }

    public boolean isKeyguardState() {
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        return ((StatusBarStateControllerImpl) this.mStatusBarStateController).mUpcomingState == 1;
    }

    public boolean isListening() {
        return this.mListening;
    }

    public boolean isQsVisible() {
        return this.mQsVisible;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final boolean isShowingDetail() {
        SecQSImpl secQSImpl = this.mSecQSImpl;
        if (secQSImpl == null) {
            return this.mQSCustomizerController.isCustomizing();
        }
        secQSImpl.getClass();
        return QsAnimatorState.isDetailOpening || QsAnimatorState.isDetailShowing || QsAnimatorState.isDetailClosing || QsAnimatorState.isCustomizerShowing;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void notifyCustomizeChanged() {
        this.mContainer.updateExpansion();
        boolean isCustomizing = this.mQSCustomizerController.isCustomizing();
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        this.mQSPanelScrollView.setVisibility(!isCustomizing ? 0 : 4);
        this.mHeader.setVisibility(isCustomizing ? 4 : 0);
        QS.HeightListener heightListener = this.mPanelView;
        if (heightListener != null) {
            heightListener.onQsHeightChanged();
        }
    }

    public final void onComponentCreated(QSComponent qSComponent, Bundle bundle) {
        final int i = 0;
        final int i2 = 1;
        this.mRootView = qSComponent.getRootView();
        this.mQSPanelController = qSComponent.getQSPanelController();
        this.mQuickQSPanelController = qSComponent.getQuickQSPanelController();
        this.mQSPanelController.init();
        this.mQuickQSPanelController.init();
        int i3 = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        this.mQSFooterActionsViewModel = this.mFooterActionsViewModelFactory.create(this.mListeningAndVisibilityLifecycleOwner);
        this.mFooterActionsController.getClass();
        NonInterceptingScrollView nonInterceptingScrollView = (NonInterceptingScrollView) this.mRootView.findViewById(R.id.expanded_qs_scroll_view);
        this.mQSPanelScrollView = nonInterceptingScrollView;
        nonInterceptingScrollView.addOnLayoutChangeListener(new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.qs.QSImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ QSImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                int i12 = i;
                QSImpl qSImpl = this.f$0;
                switch (i12) {
                    case 0:
                        qSImpl.updateQsBounds();
                        break;
                    default:
                        if (i9 - i11 == i5 - i7) {
                            qSImpl.getClass();
                            break;
                        } else {
                            qSImpl.setQsExpansion(qSImpl.mLastQSExpansion, qSImpl.mLastPanelFraction, 0.0f, qSImpl.mSquishinessFraction);
                            break;
                        }
                }
            }
        });
        this.mQSPanelScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.android.systemui.qs.QSImpl$$ExternalSyntheticLambda1
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i4, int i5, int i6, int i7) {
                QS.ScrollListener scrollListener = QSImpl.this.mScrollListener;
                if (scrollListener != null) {
                    scrollListener.onQsPanelScrollChanged(i5);
                }
            }
        });
        NonInterceptingScrollView nonInterceptingScrollView2 = this.mQSPanelScrollView;
        Flags.sceneContainer();
        nonInterceptingScrollView2.mScrollEnabled = true;
        this.mHeader = ViewUtil.getSecQuickStatusBarHeader((ViewGroup) this.mRootView);
        QSContainerImplController qSContainerImplController = qSComponent.getQSContainerImplController();
        this.mQSContainerImplController = qSContainerImplController;
        qSContainerImplController.init();
        QSContainerImpl view = this.mQSContainerImplController.getView();
        this.mContainer = view;
        String simpleName = view.getClass().getSimpleName();
        QSContainerImpl qSContainerImpl = this.mContainer;
        DumpManager dumpManager = this.mDumpManager;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, simpleName, qSContainerImpl);
        this.mQSSquishinessController = qSComponent.getQSSquishinessController();
        QSCustomizerController qSCustomizerController = qSComponent.getQSCustomizerController();
        this.mQSCustomizerController = qSCustomizerController;
        qSCustomizerController.init();
        this.mQSCustomizerController.setQs(this);
        if (bundle != null) {
            setQsVisible(bundle.getBoolean("visible"));
            setExpanded(bundle.getBoolean("expanded"));
            setListening(bundle.getBoolean("listening"));
            if (this.mQsExpanded) {
                this.mQSPanelController.mTileLayout.restoreInstanceState(bundle);
            }
        }
        final SecQSImpl secQSImpl = this.mSecQSImpl;
        if (secQSImpl != null) {
            QSImpl$$ExternalSyntheticLambda2 qSImpl$$ExternalSyntheticLambda2 = new QSImpl$$ExternalSyntheticLambda2(this, i);
            QSImpl$$ExternalSyntheticLambda2 qSImpl$$ExternalSyntheticLambda22 = new QSImpl$$ExternalSyntheticLambda2(this, i2);
            SecQSPanelController secQSPanelController = this.mQSPanelController;
            NonInterceptingScrollView nonInterceptingScrollView3 = this.mQSPanelScrollView;
            SecQSImplAnimatorManager secQSImplAnimatorManager = qSComponent.getSecQSImplAnimatorManager();
            secQSImplAnimatorManager.setQs(this);
            if (bundle != null && bundle.getBoolean("expanded")) {
                secQSImplAnimatorManager.updatePanelExpanded(true);
            }
            secQSPanelController.mSecAnimatorManager = secQSImplAnimatorManager;
            secQSPanelController.mQSCMainViewController.transitionAnimator = secQSImplAnimatorManager.mTransitionAnimator;
            secQSImpl.secQSImplAnimatorManager = secQSImplAnimatorManager;
            BarController barController = qSComponent.getBarController();
            Runnable runnable = new Runnable() { // from class: com.android.systemui.qs.SecQSImpl$onComponentCreated$3$1
                @Override // java.lang.Runnable
                public final void run() {
                    SecQSImplAnimatorManager secQSImplAnimatorManager2 = SecQSImpl.this.secQSImplAnimatorManager;
                    if (secQSImplAnimatorManager2 != null) {
                        secQSImplAnimatorManager2.updateAnimators();
                        Unit unit = Unit.INSTANCE;
                    }
                }
            };
            barController.mQSLastExpansionInitializer = qSImpl$$ExternalSyntheticLambda22;
            barController.mUpdateAnimatorsRunner = runnable;
            barController.mBarListener = barController.new AnonymousClass3(runnable, qSImpl$$ExternalSyntheticLambda2);
            SecQSPanel secQSPanel = (SecQSPanel) this.mRootView.findViewById(R.id.quick_settings_panel);
            barController.mQsPanel = secQSPanel;
            ((ArrayList) secQSPanel.mOnConfigurationChangedListeners).add(barController.mOnConfigurationChangedListener);
            barController.mQsPanel.setOnApplyWindowInsetsListener(new BarController.OnApplyWindowInsetsListener(barController, i));
            barController.updateBarUnderneathQqs();
            barController.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda0(barController, i2));
            PanelScreenShotLogger.INSTANCE.addLogProvider("BarController", barController);
            ((KnoxStateMonitorImpl) barController.mKnoxStateMonitor).registerCallback(barController.mKnoxStateMonitorCallback);
            final BarBackUpRestoreHelper barBackUpRestoreHelper = barController.mBarBackUpRestoreHelper;
            barBackUpRestoreHelper.getClass();
            ((QSBackupRestoreManager) barBackUpRestoreHelper.qsBackupRestoreManager$delegate.getValue()).addCallback("QuickPanelLayout", new QSBackupRestoreManager.Callback() { // from class: com.android.systemui.qs.bar.BarBackUpRestoreHelper$initialize$1
                @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
                public final boolean isValidDB() {
                    int i4 = BarBackUpRestoreHelper.$r8$clinit;
                    BarBackUpRestoreHelper.this.getClass();
                    return true;
                }

                @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
                public final String onBackup(boolean z) {
                    return BarBackUpRestoreHelper.access$getBackupData(BarBackUpRestoreHelper.this, z);
                }

                @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
                public final void onRestore(String str) {
                    BarBackUpRestoreHelper.access$setRestoreData(BarBackUpRestoreHelper.this, str);
                }
            });
            ((SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class)).addListener(barController);
            secQSImpl.barController = barController;
            QSCMainViewController qscMainViewController = qSComponent.getQscMainViewController();
            qscMainViewController.init();
            secQSImpl.qscMainViewController = qscMainViewController;
            SecNonInterceptingScrollView secNonInterceptingScrollView = nonInterceptingScrollView3.mSecNonInterceptingScrollView;
            if (secNonInterceptingScrollView != null) {
                secNonInterceptingScrollView.qsExpanded = secQSImpl.qsExpanded;
            }
            secQSImpl.qsButtonsContainerController = qSComponent.getQsButtonsContainerController();
            qSComponent.getQsGradationDrawableController().init();
            ((SystemUIIndexMediator) Dependency.sDependency.getDependencyInner(SystemUIIndexMediator.class)).getClass();
            secQSImpl.qsPanelController = secQSPanelController;
            secQSImpl.panelSplitHelper.addListener(secQSImpl);
            secQSImpl.lockscreenShadeTransitionController.addCallback(secQSImpl);
            ((ColoredBGHelper) secQSImpl.coloredBGHelper$delegate.getValue()).initialize();
        }
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
        statusBarStateControllerImpl.addCallback((StatusBarStateController.StateListener) this);
        onStateChanged(statusBarStateControllerImpl.mState);
        this.mRootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.qs.QSImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ QSImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view2, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                int i12 = i2;
                QSImpl qSImpl = this.f$0;
                switch (i12) {
                    case 0:
                        qSImpl.updateQsBounds();
                        break;
                    default:
                        if (i9 - i11 == i5 - i7) {
                            qSImpl.getClass();
                            break;
                        } else {
                            qSImpl.setQsExpansion(qSImpl.mLastQSExpansion, qSImpl.mLastPanelFraction, 0.0f, qSImpl.mSquishinessFraction);
                            break;
                        }
                }
            }
        });
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        SecQSImplAnimatorManager secQSImplAnimatorManager;
        int layoutDirection = configuration.getLayoutDirection();
        int i = this.mLayoutDirection;
        SecQSImpl secQSImpl = this.mSecQSImpl;
        if (layoutDirection != i) {
            this.mLayoutDirection = configuration.getLayoutDirection();
            if (secQSImpl != null && (secQSImplAnimatorManager = secQSImpl.secQSImplAnimatorManager) != null) {
                secQSImplAnimatorManager.onRtlChanged();
                Unit unit = Unit.INSTANCE;
            }
        }
        if (secQSImpl != null) {
            BarController barController = secQSImpl.barController;
            if (barController != null && barController.mOrientation != configuration.orientation) {
                Runnable runnable = barController.mQSLastExpansionInitializer;
                if (runnable != null) {
                    runnable.run();
                }
                barController.mOrientation = configuration.orientation;
            }
            ColoredBGHelper coloredBGHelper = (ColoredBGHelper) secQSImpl.coloredBGHelper$delegate.getValue();
            if (coloredBGHelper != null) {
                int i2 = configuration.uiMode & 48;
                if (i2 != coloredBGHelper.uiMode) {
                    Log.d("ColoredBGHelper", "onUiModeChanged");
                    coloredBGHelper.uiMode = i2;
                    coloredBGHelper.updateBGColor(true);
                }
                Unit unit2 = Unit.INSTANCE;
            }
        }
        updateQsState();
    }

    public final void onDestroy() {
        this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this);
        ((StatusBarStateControllerImpl) this.mStatusBarStateController).removeCallback((StatusBarStateController.StateListener) this);
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        if (this.mListening) {
            setListening(false);
        }
        QSCustomizerController qSCustomizerController = this.mQSCustomizerController;
        if (qSCustomizerController != null) {
            qSCustomizerController.setQs(null);
            this.mQSCustomizerController.setContainerController(null);
        }
        this.mScrollListener = null;
        QSContainerImpl qSContainerImpl = this.mContainer;
        DumpManager dumpManager = this.mDumpManager;
        if (qSContainerImpl != null) {
            dumpManager.unregisterDumpable(qSContainerImpl.getClass().getSimpleName());
        }
        dumpManager.unregisterDumpable("QSImpl");
        ListeningAndVisibilityLifecycleOwner listeningAndVisibilityLifecycleOwner = this.mListeningAndVisibilityLifecycleOwner;
        listeningAndVisibilityLifecycleOwner.mDestroyed = true;
        listeningAndVisibilityLifecycleOwner.updateState();
        ViewGroup viewGroup = (ViewGroup) this.mRootView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this.mRootView);
        }
        SecQSImpl secQSImpl = this.mSecQSImpl;
        if (secQSImpl != null) {
            SecQSImplAnimatorManager secQSImplAnimatorManager = secQSImpl.secQSImplAnimatorManager;
            if (secQSImplAnimatorManager != null) {
                secQSImplAnimatorManager.setQs(null);
                secQSImplAnimatorManager.destroyQSViews();
            }
            BarController barController = secQSImpl.barController;
            if (barController != null) {
                barController.mBarListener = null;
                barController.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda3(5));
                synchronized (PanelScreenShotLogger.INSTANCE) {
                    PanelScreenShotLogger.providers.remove("BarController");
                }
                ((KnoxStateMonitorImpl) barController.mKnoxStateMonitor).removeCallback(barController.mKnoxStateMonitorCallback);
                ((QSBackupRestoreManager) barController.mBarBackUpRestoreHelper.qsBackupRestoreManager$delegate.getValue()).removeCallback("QuickPanelLayout");
                ((SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class)).removeListener(barController);
            }
            secQSImpl.panelSplitHelper.removeListener(secQSImpl);
            secQSImpl.lockscreenShadeTransitionController.removeCallback(secQSImpl);
            ((ColoredBGHelper) secQSImpl.coloredBGHelper$delegate.getValue()).onDestroy();
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("expanded", this.mQsExpanded);
        bundle.putBoolean("listening", this.mListening);
        bundle.putBoolean("visible", this.mQsVisible);
        QSCustomizerController qSCustomizerController = this.mQSCustomizerController;
        if (qSCustomizerController != null) {
            qSCustomizerController.saveInstanceState(bundle);
        }
        if (this.mQsExpanded) {
            this.mQSPanelController.mTileLayout.saveInstanceState(bundle);
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        int i2 = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        if (i == this.mStatusBarState) {
            return;
        }
        this.mStatusBarState = i;
        Flags.sceneContainer();
        this.mLastQSExpansion = -1.0f;
        SecQSImpl secQSImpl = this.mSecQSImpl;
        if (secQSImpl != null) {
            updateQsState();
        }
        updateShowCollapsedOnKeyguard();
        if (secQSImpl != null) {
            QSContainerImplController qSContainerImplController = this.mQSContainerImplController;
            SecQSImplAnimatorManager secQSImplAnimatorManager = secQSImpl.secQSImplAnimatorManager;
            if (secQSImplAnimatorManager != null) {
                secQSImplAnimatorManager.onStateChanged(i);
            }
            SecQSContainerImpl secQSContainerImpl = qSContainerImplController.getView().mSecQSContainerImpl;
            if (secQSContainerImpl != null) {
                secQSContainerImpl.keyguardShowing = i == 1;
            }
            secQSImpl.barState = i;
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onUpcomingStateChanged(int i) {
        if (i == 1) {
            onStateChanged(i);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setCollapseExpandAction(Runnable runnable) {
        this.mQSPanelController.setCollapseExpandAction(runnable);
        this.mQuickQSPanelController.setCollapseExpandAction(runnable);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setContainerController(QSContainerController qSContainerController) {
        this.mQSCustomizerController.setContainerController(qSContainerController);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setExpanded(boolean z) {
        this.mQsExpanded = z;
        if (this.mInSplitShade && z) {
            setListening(true);
        } else {
            updateQsPanelControllerListening();
        }
        updateQsState();
        SecQSImpl secQSImpl = this.mSecQSImpl;
        if (secQSImpl != null) {
            boolean z2 = this.mListening;
            BarController barController = secQSImpl.barController;
            if (barController != null) {
                barController.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda1(z, 1));
            }
            QSButtonsContainerController qSButtonsContainerController = secQSImpl.qsButtonsContainerController;
            if (qSButtonsContainerController != null) {
                qSButtonsContainerController.setListening(z2, z);
            }
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setFancyClipping(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
        SecQSImplAnimatorManager secQSImplAnimatorManager;
        boolean z3;
        View view = this.mRootView;
        if (view instanceof QSContainerImpl) {
            QSContainerImpl qSContainerImpl = (QSContainerImpl) view;
            boolean z4 = true;
            if (qSContainerImpl.mFancyClippingLeftInset != i) {
                qSContainerImpl.mFancyClippingLeftInset = i;
                z3 = true;
            } else {
                z3 = false;
            }
            if (qSContainerImpl.mFancyClippingTop != i2) {
                qSContainerImpl.mFancyClippingTop = i2;
                z3 = true;
            }
            if (qSContainerImpl.mFancyClippingRightInset != i3) {
                qSContainerImpl.mFancyClippingRightInset = i3;
                z3 = true;
            }
            if (qSContainerImpl.mFancyClippingBottom != i4) {
                qSContainerImpl.mFancyClippingBottom = i4;
                z3 = true;
            }
            if (qSContainerImpl.mClippingEnabled != z) {
                qSContainerImpl.mClippingEnabled = z;
                z3 = true;
            }
            if (qSContainerImpl.mIsFullWidth != z2) {
                qSContainerImpl.mIsFullWidth = z2;
            } else {
                z4 = z3;
            }
            if (z4) {
                qSContainerImpl.updateClippingPath();
            }
        }
        SecQSImpl secQSImpl = this.mSecQSImpl;
        if (secQSImpl == null || (secQSImplAnimatorManager = secQSImpl.secQSImplAnimatorManager) == null) {
            return;
        }
        secQSImplAnimatorManager.setFancyClipping(i, i2, i3, i4, i5, z, z2);
        Unit unit = Unit.INSTANCE;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setHeaderListening(boolean z) {
        this.mQSContainerImplController.mQuickStatusBarHeaderController.setListening(z);
        SecQSImpl secQSImpl = this.mSecQSImpl;
        if (secQSImpl != null) {
            secQSImpl.setListening(z, this.mQsExpanded);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setHeightOverride(int i) {
        this.mContainer.updateExpansion();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setInSplitShade(boolean z) {
        this.mInSplitShade = z;
        updateShowCollapsedOnKeyguard();
        updateQsState();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setIsNotificationPanelFullWidth(boolean z) {
        this.mIsSmallScreen = z;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setListening(boolean z) {
        this.mListening = z;
        boolean z2 = false;
        this.mQSContainerImplController.mQuickStatusBarHeaderController.setListening(z && this.mQsVisible);
        this.mListeningAndVisibilityLifecycleOwner.updateState();
        updateQsPanelControllerListening();
        SecQSImpl secQSImpl = this.mSecQSImpl;
        if (secQSImpl != null) {
            if (z && this.mQsVisible) {
                z2 = true;
            }
            secQSImpl.setListening(z2, this.mQsExpanded);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setOverScrollAmount(int i) {
        this.mOverScrolling = i != 0;
        View view = this.mRootView;
        SecQSImpl secQSImpl = this.mSecQSImpl;
        if (secQSImpl == null) {
            if (view != null) {
                view.setTranslationY(i);
            }
        } else {
            float f = i;
            SecQSImplAnimatorManager secQSImplAnimatorManager = secQSImpl.secQSImplAnimatorManager;
            if (secQSImplAnimatorManager != null) {
                secQSImplAnimatorManager.setOverScrollAmount(f);
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setOverscrolling(boolean z) {
        this.mStackScrollerOverscrolling = z;
        SecQSImpl secQSImpl = this.mSecQSImpl;
        if (secQSImpl != null) {
            secQSImpl.stackScrollerOverscrolling = z;
            SecQSImplAnimatorManager secQSImplAnimatorManager = secQSImpl.secQSImplAnimatorManager;
            if (secQSImplAnimatorManager != null) {
                secQSImplAnimatorManager.setStackScrollerOverscrolling(z);
            }
        }
        updateQsState();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setPanelView(QS.HeightListener heightListener) {
        this.mPanelView = heightListener;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setQsExpansion(float f, float f2, float f3, float f4) {
        float f5;
        SecQSImplAnimatorManager secQSImplAnimatorManager;
        if (this.mIsSmallScreen) {
            f5 = 1.0f;
        } else if (this.mInSplitShade) {
            if (this.mTransitioningToFullShade || ((StatusBarStateControllerImpl) this.mStatusBarStateController).mUpcomingState == 1) {
                f5 = this.mLockscreenToShadeProgress;
            }
            f5 = f2;
        } else {
            if (this.mTransitioningToFullShade) {
                f5 = this.mLockscreenToShadeProgress;
            }
            f5 = f2;
        }
        QSContainerImpl qSContainerImpl = this.mContainer;
        qSContainerImpl.mQsExpansion = f;
        NonInterceptingScrollView nonInterceptingScrollView = qSContainerImpl.mQSPanelContainer;
        if (nonInterceptingScrollView != null) {
            nonInterceptingScrollView.mScrollEnabled = f > 0.0f;
        }
        qSContainerImpl.updateExpansion();
        boolean z = isKeyguardState() && !this.mShowCollapsedOnKeyguard;
        if (!this.mHeaderAnimating && this.mStatusBarState == 1 && this.mShowCollapsedOnKeyguard) {
            isKeyguardState();
        }
        int height = this.mRootView.getHeight();
        if (f == this.mLastQSExpansion && this.mLastKeyguardAndExpanded == z && this.mLastViewHeight == height && this.mSquishinessFraction == f4 && this.mLastPanelFraction == f2) {
            return;
        }
        this.mLastPanelFraction = f2;
        this.mSquishinessFraction = f4;
        this.mLastQSExpansion = f;
        this.mLastKeyguardAndExpanded = z;
        this.mLastViewHeight = height;
        boolean z2 = f == 1.0f;
        boolean z3 = f == 0.0f;
        getHeightDiff();
        this.mQSPanelController.getClass();
        if (z) {
            f5 = 1.0f;
        } else if (!this.mInSplitShade) {
            f5 = f;
        }
        FooterActionsViewModel footerActionsViewModel = this.mQSFooterActionsViewModel;
        if (footerActionsViewModel != null) {
            boolean z4 = this.mInSplitShade;
            StateFlowImpl stateFlowImpl = footerActionsViewModel._backgroundAlpha;
            StateFlowImpl stateFlowImpl2 = footerActionsViewModel._alpha;
            if (z4) {
                stateFlowImpl2.updateState(null, Float.valueOf(f5));
                stateFlowImpl.updateState(null, Float.valueOf(Math.max(0.0f, f5 - 0.15f) / 0.85f));
            } else {
                stateFlowImpl2.updateState(null, Float.valueOf(Math.max(0.0f, f5 - 0.9f) / (1 - 0.9f)));
                stateFlowImpl.updateState(null, Float.valueOf(1.0f));
            }
        }
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        if (z3) {
            this.mQSPanelScrollView.setScrollY(0);
        }
        if (!z2) {
            this.mQsBounds.top = (int) (-this.mQSPanelScrollView.getTranslationY());
            this.mQsBounds.right = this.mQSPanelScrollView.getWidth();
            this.mQsBounds.bottom = this.mQSPanelScrollView.getHeight();
        }
        updateQsBounds();
        QSSquishinessController qSSquishinessController = this.mQSSquishinessController;
        if (qSSquishinessController != null) {
            float f6 = this.mSquishinessFraction;
            if (qSSquishinessController.squishiness != f6) {
                qSSquishinessController.squishiness = f6;
            }
        }
        SecQSImpl secQSImpl = this.mSecQSImpl;
        if (secQSImpl == null || (secQSImplAnimatorManager = secQSImpl.secQSImplAnimatorManager) == null) {
            return;
        }
        secQSImplAnimatorManager.setQsExpansionPosition(f);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setQsVisible(boolean z) {
        if (this.mQsVisible == z) {
            return;
        }
        this.mQsVisible = z;
        setListening(this.mListening);
        this.mListeningAndVisibilityLifecycleOwner.updateState();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setScrollListener(QS.ScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setShouldUpdateSquishinessOnMedia(boolean z) {
        this.mShouldUpdateMediaSquishiness = z;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setTransitionToFullShadeProgress(boolean z, float f, float f2) {
        if (z != this.mTransitioningToFullShade) {
            this.mTransitioningToFullShade = z;
            updateShowCollapsedOnKeyguard();
        }
        this.mLockscreenToShadeProgress = f;
        float f3 = this.mLastQSExpansion;
        float f4 = this.mLastPanelFraction;
        if (!z) {
            f2 = this.mSquishinessFraction;
        }
        setQsExpansion(f3, f4, 0.0f, f2);
    }

    public void updateQsBounds() {
        if (this.mStatusBarState == 1 || this.mLastQSExpansion == 1.0f) {
            int dimensionPixelSize = this.mRootView.getContext().getResources().getDimensionPixelSize(R.dimen.qs_tiles_page_horizontal_margin) * 2;
            this.mQsBounds.set(-dimensionPixelSize, 0, this.mQSPanelScrollView.getWidth() + dimensionPixelSize, this.mQSPanelScrollView.getHeight());
        }
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        this.mQSPanelScrollView.setClipBounds(this.mQsBounds);
        NonInterceptingScrollView nonInterceptingScrollView = this.mQSPanelScrollView;
        int[] iArr = this.mLocationTemp;
        nonInterceptingScrollView.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        int i3 = iArr[1];
        this.mQsMediaHost.currentClipping.set(i2, i3, this.mRootView.getMeasuredWidth() + i2, (this.mQSPanelScrollView.getMeasuredHeight() + i3) - this.mQSPanelController.getPaddingBottom());
    }

    public final void updateQsPanelControllerListening() {
        SecQSPanelController secQSPanelController = this.mQSPanelController;
        boolean z = false;
        boolean z2 = this.mListening && this.mQsVisible;
        boolean z3 = this.mQsExpanded;
        secQSPanelController.getClass();
        if (z2 && z3) {
            z = true;
        }
        secQSPanelController.setListening(z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0053, code lost:
    
        if (r3.panelSplitHelper.isQSState() == false) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateQsState() {
        /*
            Method dump skipped, instructions count: 222
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.QSImpl.updateQsState():void");
    }

    public final void updateShowCollapsedOnKeyguard() {
        boolean z = this.mBypassController.getBypassEnabled() || (this.mTransitioningToFullShade && !this.mInSplitShade);
        if (z != this.mShowCollapsedOnKeyguard) {
            this.mShowCollapsedOnKeyguard = z;
            updateQsState();
            if (z || !isKeyguardState()) {
                return;
            }
            setQsExpansion(this.mLastQSExpansion, this.mLastPanelFraction, 0.0f, this.mSquishinessFraction);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setCollapsedMediaVisibilityChangedListener(Consumer consumer) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setHasNotifications(boolean z) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setHeaderClickable(boolean z) {
    }
}
