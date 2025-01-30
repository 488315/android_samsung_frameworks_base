package com.android.systemui.p016qs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Trace;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.appops.AppOpItem$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.indexsearch.SystemUIIndexMediator;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.p016qs.QSBackupRestoreManager;
import com.android.systemui.p016qs.SecQSDetail;
import com.android.systemui.p016qs.SecQSDetail.C20715;
import com.android.systemui.p016qs.animator.QsTransitionAnimator;
import com.android.systemui.p016qs.animator.SecQSFragmentAnimatorManager;
import com.android.systemui.p016qs.bar.BarBackUpRestoreHelper;
import com.android.systemui.p016qs.bar.BarController;
import com.android.systemui.p016qs.bar.BarController$$ExternalSyntheticLambda1;
import com.android.systemui.p016qs.bar.BarController$$ExternalSyntheticLambda3;
import com.android.systemui.p016qs.bar.BarController$$ExternalSyntheticLambda8;
import com.android.systemui.p016qs.bar.BarController.C20863;
import com.android.systemui.p016qs.buttons.QSButtonsContainerController;
import com.android.systemui.p016qs.cinema.QSCinemaCompany;
import com.android.systemui.p016qs.cinema.QSCinemaProvider;
import com.android.systemui.p016qs.customize.QSCustomizer;
import com.android.systemui.p016qs.customize.QSCustomizerController;
import com.android.systemui.p016qs.dagger.QSFragmentComponent;
import com.android.systemui.p016qs.footer.p019ui.binder.FooterActionsViewBinder;
import com.android.systemui.p016qs.footer.p019ui.viewmodel.FooterActionsViewModel$Factory;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p013qs.InterfaceC1922QS;
import com.android.systemui.plugins.p013qs.QSContainerController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.transition.LargeScreenShadeInterpolator;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.disableflags.DisableFlagsLogger;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.LifecycleFragment;
import com.sec.ims.configuration.DATA;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSFragment extends LifecycleFragment implements InterfaceC1922QS, CommandQueue.Callbacks, StatusBarStateController.StateListener, Dumpable {
    public final C20493 mAnimateHeaderSlidingInListener;
    public final KeyguardBypassController mBypassController;
    public QSContainerImpl mContainer;
    public final DumpManager mDumpManager;
    public SecQuickStatusBarHeader mHeader;
    public boolean mHeaderAnimating;
    public boolean mInSplitShade;
    public boolean mIsSmallScreen;
    public boolean mLastKeyguardAndExpanded;
    public float mLastPanelFraction;
    public int mLastViewHeight;
    public int mLayoutDirection;
    public boolean mListening;
    public final ListeningAndVisibilityLifecycleOwner mListeningAndVisibilityLifecycleOwner;
    public float mLockscreenToShadeProgress;
    public boolean mOverScrolling;
    public InterfaceC1922QS.HeightListener mPanelView;
    public QSContainerImplController mQSContainerImplController;
    public QSCustomizerController mQSCustomizerController;
    public SecQSPanelController mQSPanelController;
    public NonInterceptingScrollView mQSPanelScrollView;
    public QSSquishinessController mQSSquishinessController;
    public final QSFragmentComponent.Factory mQsComponentFactory;
    public boolean mQsDisabled;
    public boolean mQsExpanded;
    public final QSFragmentDisableFlagsLogger mQsFragmentDisableFlagsLogger;
    public boolean mQsVisible;
    public SecQuickQSPanelController mQuickQSPanelController;
    public final RemoteInputQuickSettingsDisabler mRemoteInputQuickSettingsDisabler;
    public InterfaceC1922QS.ScrollListener mScrollListener;
    public final SecQSFragment mSecQSFragment;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    class ListeningAndVisibilityLifecycleOwner implements LifecycleOwner {
        public final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
        public boolean mDestroyed = false;

        public ListeningAndVisibilityLifecycleOwner() {
            updateState();
        }

        @Override // androidx.lifecycle.LifecycleOwner
        public final LifecycleRegistry getLifecycle() {
            return this.mLifecycleRegistry;
        }

        public final void updateState() {
            boolean z = this.mDestroyed;
            LifecycleRegistry lifecycleRegistry = this.mLifecycleRegistry;
            if (z) {
                lifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
                return;
            }
            QSFragment qSFragment = QSFragment.this;
            if (!qSFragment.mListening) {
                lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
            } else if (qSFragment.mQsVisible) {
                lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
            } else {
                lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.qs.QSFragment$3] */
    public QSFragment(RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler, SysuiStatusBarStateController sysuiStatusBarStateController, CommandQueue commandQueue, KeyguardBypassController keyguardBypassController, QSFragmentComponent.Factory factory, QSFragmentDisableFlagsLogger qSFragmentDisableFlagsLogger, DumpManager dumpManager, QSLogger qSLogger, FooterActionsController footerActionsController, FooterActionsViewModel$Factory footerActionsViewModel$Factory, FooterActionsViewBinder footerActionsViewBinder, LargeScreenShadeInterpolator largeScreenShadeInterpolator, FeatureFlags featureFlags, FalsingManager falsingManager, SecQSDetailDisplayer secQSDetailDisplayer, ShadeHeaderController shadeHeaderController) {
        new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.qs.QSFragment.2
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                QSFragment.this.getView().getViewTreeObserver().removeOnPreDrawListener(this);
                QSFragment.this.getView().animate().translationY(0.0f).setDuration(448L).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setListener(QSFragment.this.mAnimateHeaderSlidingInListener).start();
                return true;
            }
        };
        this.mAnimateHeaderSlidingInListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.QSFragment.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                QSFragment qSFragment = QSFragment.this;
                qSFragment.mHeaderAnimating = false;
                qSFragment.updateQsState();
                QSFragment.this.getView().animate().setListener(null);
            }
        };
        this.mRemoteInputQuickSettingsDisabler = remoteInputQuickSettingsDisabler;
        this.mQsComponentFactory = factory;
        this.mQsFragmentDisableFlagsLogger = qSFragmentDisableFlagsLogger;
        commandQueue.observe(this.mLifecycle, this);
        this.mBypassController = keyguardBypassController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mDumpManager = dumpManager;
        this.mListeningAndVisibilityLifecycleOwner = new ListeningAndVisibilityLifecycleOwner();
        this.mSecQSFragment = new SecQSFragment(falsingManager, new BooleanSupplier() { // from class: com.android.systemui.qs.QSFragment$$ExternalSyntheticLambda1
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return QSFragment.this.mQsExpanded;
            }
        }, secQSDetailDisplayer, new Supplier() { // from class: com.android.systemui.qs.QSFragment$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return QSFragment.this.getView();
            }
        }, shadeHeaderController);
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void animateHeaderSlidingOut() {
        if (getView().getY() == (-this.mHeader.getHeight())) {
            return;
        }
        this.mHeaderAnimating = true;
        getView().animate().y(-this.mHeader.getHeight()).setStartDelay(0L).setDuration(360L).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.QSFragment.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (QSFragment.this.getView() != null) {
                    QSFragment.this.getView().animate().setListener(null);
                }
                QSFragment qSFragment = QSFragment.this;
                qSFragment.mHeaderAnimating = false;
                qSFragment.updateQsState();
            }
        }).start();
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void closeCustomizer() {
        this.mQSCustomizerController.hide();
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void closeDetail() {
        SecQSPanelController secQSPanelController = this.mQSPanelController;
        secQSPanelController.showDetail(secQSPanelController.mDetailRecord, false);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        if (i != getContext().getDisplayId()) {
            return;
        }
        this.mRemoteInputQuickSettingsDisabler.getClass();
        final QSFragmentDisableFlagsLogger qSFragmentDisableFlagsLogger = this.mQsFragmentDisableFlagsLogger;
        DisableFlagsLogger.DisableState disableState = new DisableFlagsLogger.DisableState(i2, i3);
        DisableFlagsLogger.DisableState disableState2 = new DisableFlagsLogger.DisableState(i2, i3);
        qSFragmentDisableFlagsLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.QSFragmentDisableFlagsLogger$logDisableFlagChange$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return QSFragmentDisableFlagsLogger.this.disableFlagsLogger.getDisableFlagsString(null, new DisableFlagsLogger.DisableState(logMessage.getInt1(), logMessage.getInt2()), new DisableFlagsLogger.DisableState((int) logMessage.getLong1(), (int) logMessage.getLong2()));
            }
        };
        LogBuffer logBuffer = qSFragmentDisableFlagsLogger.buffer;
        LogMessage obtain = logBuffer.obtain("QSFragmentDisableFlagsLog", logLevel, function1, null);
        obtain.setInt1(disableState.disable1);
        obtain.setInt2(disableState.disable2);
        obtain.setLong1(disableState2.disable1);
        obtain.setLong2(disableState2.disable2);
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
            if (QpRune.QUICK_TABLET_BG) {
                qSContainerImpl.mOpaqueBgHelper.mBackground.setVisibility(z3 ? 8 : 0);
            }
        }
        SecQuickStatusBarHeader secQuickStatusBarHeader = this.mHeader;
        secQuickStatusBarHeader.getClass();
        boolean z4 = i4 != 0;
        if (z4 != secQuickStatusBarHeader.mQsDisabled) {
            secQuickStatusBarHeader.mQsDisabled = z4;
            SecQuickQSPanel secQuickQSPanel = secQuickStatusBarHeader.mHeaderQsPanel;
            if (z4 != secQuickQSPanel.mDisabledByPolicy) {
                secQuickQSPanel.mDisabledByPolicy = z4;
                secQuickQSPanel.setVisibility(z4 ? 8 : 0);
            }
            secQuickStatusBarHeader.mDateButtonContainer.setVisibility(secQuickStatusBarHeader.mQsDisabled ? 8 : 0);
            ViewGroup.LayoutParams layoutParams = secQuickStatusBarHeader.getLayoutParams();
            if (!secQuickStatusBarHeader.mQsDisabled) {
                i5 = -2;
            } else if (QpRune.QUICK_TABLET) {
                i5 = ((ShadeHeaderController) secQuickStatusBarHeader.mResourcePicker.mShadeHeaderControllerLazy.get()).getViewHeight();
            }
            layoutParams.height = i5;
            secQuickStatusBarHeader.setLayoutParams(layoutParams);
        }
        updateQsState();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("QSFragment:");
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
        indentingPrintWriter.println("isCustomizing: " + ((QSCustomizer) this.mQSCustomizerController.mView).mCustomizing);
        View view = getView();
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

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final int getDesiredHeight() {
        if (((QSCustomizer) this.mQSCustomizerController.mView).mCustomizing) {
            return getView().getHeight();
        }
        return this.mSecQSFragment.shadeHeaderController.getViewHeight() + getView().getMeasuredHeight();
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final View getHeader() {
        return this.mHeader;
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final int getHeightDiff() {
        return this.mHeader.getPaddingBottom() + (this.mQSPanelScrollView.getBottom() - this.mHeader.getBottom());
    }

    public ListeningAndVisibilityLifecycleOwner getListeningAndVisibilityLifecycleOwner() {
        return this.mListeningAndVisibilityLifecycleOwner;
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final int getQsMinExpansionHeight() {
        if (!this.mInSplitShade) {
            return this.mHeader.getHeight() + (!QpRune.QUICK_TABLET_BG ? this.mSecQSFragment.shadeHeaderController.getViewHeight() : 0);
        }
        getView().getLocationOnScreen(this.mLocationTemp);
        return getView().getHeight() + ((int) (this.mLocationTemp[1] - getView().getTranslationY()));
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void hideImmediately() {
        getView().animate().cancel();
        getView().setY(-getQsMinExpansionHeight());
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final boolean isCustomizing() {
        return ((QSCustomizer) this.mQSCustomizerController.mView).mCustomizing;
    }

    public boolean isExpanded() {
        return this.mQsExpanded;
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final boolean isFullyCollapsed() {
        float f = this.mLastQSExpansion;
        return f == 0.0f || f == -1.0f;
    }

    public boolean isListening() {
        return this.mListening;
    }

    public boolean isQsVisible() {
        return this.mQsVisible;
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final boolean isShowingDetail() {
        if (((QSCustomizer) this.mQSCustomizerController.mView).mCustomizing) {
            return true;
        }
        SecQSDetail secQSDetail = this.mSecQSFragment.quickTile.secQSDetail;
        return secQSDetail != null && secQSDetail.mQsPanelController.mDetailRecord != null;
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void notifyCustomizeChanged() {
        this.mContainer.updateExpansion();
        boolean isCustomizing = isCustomizing();
        this.mQSPanelScrollView.setVisibility(!isCustomizing ? 0 : 4);
        this.mHeader.setVisibility(isCustomizing ? 4 : 0);
        this.mPanelView.onQsHeightChanged();
    }

    @Override // android.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (configuration.getLayoutDirection() != this.mLayoutDirection) {
            this.mLayoutDirection = configuration.getLayoutDirection();
            SecQSFragmentAnimatorManager secQSFragmentAnimatorManager = this.mSecQSFragment.quickAnimation.secQSFragmentAnimatorManager;
            if (secQSFragmentAnimatorManager != null) {
                secQSFragmentAnimatorManager.onRtlChanged();
            }
        }
        SecQSFragment secQSFragment = this.mSecQSFragment;
        QSCinemaCompany qSCinemaCompany = secQSFragment.qsCinemaFragmentAdapter;
        if (qSCinemaCompany != null) {
            QSCinemaProvider qSCinemaProvider = qSCinemaCompany.mProvider;
            if (qSCinemaProvider.mCurrentOrientation != configuration.orientation) {
                StringBuilder sb = new StringBuilder("orientation is changed ! ");
                sb.append(qSCinemaProvider.mCurrentOrientation);
                sb.append(" >> ");
                RecyclerView$$ExternalSyntheticOutline0.m46m(sb, configuration.orientation, "QSCinemaProvider");
                qSCinemaProvider.mCurrentOrientation = configuration.orientation;
            }
            if (qSCinemaProvider.mCurrentLayoutDirection != configuration.getLayoutDirection()) {
                Log.d("QSCinemaProvider", "LayoutDirection is changed ! " + qSCinemaProvider.mCurrentLayoutDirection + " >> " + configuration.getLayoutDirection());
                qSCinemaProvider.mCurrentLayoutDirection = configuration.getLayoutDirection();
            }
        }
        SecQSFragmentAnimatorManager secQSFragmentAnimatorManager2 = secQSFragment.quickAnimation.secQSFragmentAnimatorManager;
        if (secQSFragmentAnimatorManager2 != null) {
            secQSFragmentAnimatorManager2.onConfigurationChanged(configuration);
        }
        BarController barController = secQSFragment.quickBar.barController;
        if (barController != null && barController.mOrientation != configuration.orientation) {
            Runnable runnable = barController.mQSLastExpansionInitializer;
            if (runnable != null) {
                runnable.run();
            }
            barController.mOrientation = configuration.orientation;
        }
        updateQsState();
    }

    @Override // com.android.systemui.util.LifecycleFragment, android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mDumpManager.registerDumpable(QSFragment.class.getName(), this);
    }

    @Override // android.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        try {
            Trace.beginSection("QSFragment#onCreateView");
            return layoutInflater.cloneInContext(new ContextThemeWrapper(getContext(), 2132018541)).inflate(R.layout.qs_panel, viewGroup, false);
        } finally {
            Trace.endSection();
        }
    }

    @Override // com.android.systemui.util.LifecycleFragment, android.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        ((StatusBarStateControllerImpl) this.mStatusBarStateController).removeCallback((StatusBarStateController.StateListener) this);
        if (this.mListening) {
            setListening(false);
        }
        QSCustomizerController qSCustomizerController = this.mQSCustomizerController;
        if (qSCustomizerController != null) {
            ((QSCustomizer) qSCustomizerController.mView).mQs = null;
        }
        this.mScrollListener = null;
        QSContainerImpl qSContainerImpl = this.mContainer;
        if (qSContainerImpl != null) {
            this.mDumpManager.unregisterDumpable(qSContainerImpl.getClass().getName());
        }
        this.mDumpManager.unregisterDumpable(QSFragment.class.getName());
        ListeningAndVisibilityLifecycleOwner listeningAndVisibilityLifecycleOwner = this.mListeningAndVisibilityLifecycleOwner;
        listeningAndVisibilityLifecycleOwner.mDestroyed = true;
        listeningAndVisibilityLifecycleOwner.updateState();
        SecQSFragment secQSFragment = this.mSecQSFragment;
        SecQSFragmentAnimatorManager secQSFragmentAnimatorManager = secQSFragment.quickAnimation.secQSFragmentAnimatorManager;
        if (secQSFragmentAnimatorManager != null) {
            secQSFragmentAnimatorManager.setQs(null);
            secQSFragmentAnimatorManager.destroyQSViews();
        }
        BarController barController = secQSFragment.quickBar.barController;
        if (barController != null) {
            barController.mBarListener = null;
            barController.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda1(0));
            synchronized (PanelScreenShotLogger.INSTANCE) {
                PanelScreenShotLogger.providers.remove("BarController");
            }
            ((KnoxStateMonitorImpl) barController.mKnoxStateMonitor).removeCallback(barController.mKnoxStateMonitorCallback);
            LinkedHashMap linkedHashMap = barController.mBarBackUpRestoreHelper.qsBackupRestoreManager.mQSBnRMap;
            if (linkedHashMap.keySet().contains("QuickPanelLayout")) {
                linkedHashMap.remove("QuickPanelLayout");
            }
        }
    }

    @Override // android.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
    }

    @Override // android.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("expanded", this.mQsExpanded);
        bundle.putBoolean("listening", this.mListening);
        bundle.putBoolean("visible", this.mQsVisible);
        if (this.mQsExpanded) {
            this.mQSPanelController.mTileLayout.saveInstanceState(bundle);
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        if (i == this.mStatusBarState) {
            return;
        }
        this.mStatusBarState = i;
        this.mLastQSExpansion = -1.0f;
        SecQSFragment secQSFragment = this.mSecQSFragment;
        QSFragment$$ExternalSyntheticLambda0 qSFragment$$ExternalSyntheticLambda0 = new QSFragment$$ExternalSyntheticLambda0(this, 0);
        QSCinemaCompany qSCinemaCompany = secQSFragment.qsCinemaFragmentAdapter;
        if (qSCinemaCompany != null) {
            qSCinemaCompany.mProvider.getClass();
        }
        secQSFragment.quickBar.getClass();
        qSFragment$$ExternalSyntheticLambda0.run();
        updateShowCollapsedOnKeyguard();
        SecQSFragment secQSFragment2 = this.mSecQSFragment;
        QSContainerImplController qSContainerImplController = this.mQSContainerImplController;
        SecQSFragmentAnimatorManager secQSFragmentAnimatorManager = secQSFragment2.quickAnimation.secQSFragmentAnimatorManager;
        if (secQSFragmentAnimatorManager != null) {
            secQSFragmentAnimatorManager.onStateChanged(i);
        }
        ((QSContainerImpl) qSContainerImplController.mView).mKeyguardShowing = i == 1;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onUpcomingStateChanged(int i) {
        if (i == 1) {
            onStateChanged(i);
        }
    }

    @Override // android.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        QSFragmentComponent create = this.mQsComponentFactory.create(this);
        this.mQSPanelController = create.getSecQSPanelController();
        this.mQuickQSPanelController = create.getSecQuickQSPanelController();
        this.mQSPanelController.init();
        this.mQuickQSPanelController.init();
        NonInterceptingScrollView nonInterceptingScrollView = (NonInterceptingScrollView) view.findViewById(R.id.expanded_qs_scroll_view);
        this.mQSPanelScrollView = nonInterceptingScrollView;
        final int i = 0;
        nonInterceptingScrollView.addOnLayoutChangeListener(new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.qs.QSFragment$$ExternalSyntheticLambda3
            public final /* synthetic */ QSFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                switch (i) {
                    case 0:
                        this.f$0.updateQsBounds();
                        break;
                    default:
                        QSFragment qSFragment = this.f$0;
                        qSFragment.getClass();
                        if (i7 - i9 != i3 - i5) {
                            qSFragment.setQsExpansion(qSFragment.mLastQSExpansion, qSFragment.mLastPanelFraction, 0.0f, qSFragment.mSquishinessFraction);
                            break;
                        }
                        break;
                }
            }
        });
        this.mQSPanelScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.android.systemui.qs.QSFragment$$ExternalSyntheticLambda4
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view2, int i2, int i3, int i4, int i5) {
                InterfaceC1922QS.ScrollListener scrollListener = QSFragment.this.mScrollListener;
                if (scrollListener != null) {
                    scrollListener.onQsPanelScrollChanged(i3);
                }
            }
        });
        this.mHeader = (SecQuickStatusBarHeader) view.findViewById(R.id.header);
        QSContainerImplController qSContainerImplController = create.getQSContainerImplController();
        this.mQSContainerImplController = qSContainerImplController;
        qSContainerImplController.init();
        QSContainerImpl qSContainerImpl = (QSContainerImpl) this.mQSContainerImplController.mView;
        this.mContainer = qSContainerImpl;
        DumpManager dumpManager = this.mDumpManager;
        String name = qSContainerImpl.getClass().getName();
        QSContainerImpl qSContainerImpl2 = this.mContainer;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, name, qSContainerImpl2);
        this.mQSSquishinessController = create.getQSSquishinessController();
        QSCustomizerController qSCustomizerController = create.getQSCustomizerController();
        this.mQSCustomizerController = qSCustomizerController;
        qSCustomizerController.init();
        ((QSCustomizer) this.mQSCustomizerController.mView).mQs = this;
        if (bundle != null) {
            setQsVisible(bundle.getBoolean("visible"));
            setExpanded(bundle.getBoolean("expanded"));
            setListening(bundle.getBoolean("listening"));
            if (this.mQsExpanded) {
                this.mQSPanelController.mTileLayout.restoreInstanceState(bundle);
            }
        }
        final SecQSFragment secQSFragment = this.mSecQSFragment;
        final int i2 = 1;
        QSFragment$$ExternalSyntheticLambda0 qSFragment$$ExternalSyntheticLambda0 = new QSFragment$$ExternalSyntheticLambda0(this, 1);
        QSFragment$$ExternalSyntheticLambda0 qSFragment$$ExternalSyntheticLambda02 = new QSFragment$$ExternalSyntheticLambda0(this, 2);
        SecQSPanelController secQSPanelController = this.mQSPanelController;
        SecQuickQSPanelController secQuickQSPanelController = this.mQuickQSPanelController;
        SecQuickStatusBarHeader secQuickStatusBarHeader = this.mHeader;
        NonInterceptingScrollView nonInterceptingScrollView2 = this.mQSPanelScrollView;
        Objects.requireNonNull(nonInterceptingScrollView2);
        QuickAnimation quickAnimation = secQSFragment.quickAnimation;
        quickAnimation.getClass();
        SecQSFragmentAnimatorManager secQSFragmentAnimatorManager = create.getSecQSFragmentAnimatorManager();
        secQSFragmentAnimatorManager.setQs(this);
        if (bundle != null && bundle.getBoolean("expanded")) {
            secQSFragmentAnimatorManager.updatePanelExpanded(true);
        }
        secQSPanelController.mSecAnimatorManager = secQSFragmentAnimatorManager;
        quickAnimation.secQSFragmentAnimatorManager = secQSFragmentAnimatorManager;
        Runnable runnable = new Runnable() { // from class: com.android.systemui.qs.SecQSFragment$onViewCreated$1
            @Override // java.lang.Runnable
            public final void run() {
                SecQSFragmentAnimatorManager secQSFragmentAnimatorManager2 = SecQSFragment.this.quickAnimation.secQSFragmentAnimatorManager;
                if (secQSFragmentAnimatorManager2 != null) {
                    secQSFragmentAnimatorManager2.updateAnimators();
                }
            }
        };
        QuickBar quickBar = secQSFragment.quickBar;
        quickBar.getClass();
        BarController barController = create.getBarController();
        barController.mQSLastExpansionInitializer = qSFragment$$ExternalSyntheticLambda02;
        barController.mBarListener = barController.new C20863(runnable, qSFragment$$ExternalSyntheticLambda0);
        SecQSPanel secQSPanel = (SecQSPanel) getView().findViewById(R.id.quick_settings_panel);
        barController.mQsPanel = secQSPanel;
        ((ArrayList) secQSPanel.mOnConfigurationChangedListeners).add(barController.mOnConfigurationChangedListener);
        barController.mQsPanel.setOnApplyWindowInsetsListener(new BarController.OnApplyWindowInsetsListener(barController, 0));
        barController.updateBarUnderneathQqs();
        barController.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda8(barController, 0));
        PanelScreenShotLogger.INSTANCE.addLogProvider("BarController", barController);
        ((KnoxStateMonitorImpl) barController.mKnoxStateMonitor).registerCallback(barController.mKnoxStateMonitorCallback);
        final BarBackUpRestoreHelper barBackUpRestoreHelper = barController.mBarBackUpRestoreHelper;
        barBackUpRestoreHelper.getClass();
        barBackUpRestoreHelper.qsBackupRestoreManager.addCallback("QuickPanelLayout", new QSBackupRestoreManager.Callback() { // from class: com.android.systemui.qs.bar.BarBackUpRestoreHelper$initialize$1
            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final boolean isValidDB() {
                int i3 = BarBackUpRestoreHelper.$r8$clinit;
                BarBackUpRestoreHelper.this.getClass();
                return true;
            }

            /* JADX WARN: Code restructure failed: missing block: B:22:0x0054, code lost:
            
                if (r7 != false) goto L28;
             */
            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final String onBackup(boolean z) {
                String str;
                String str2;
                String str3;
                String str4;
                BarBackUpRestoreHelper barBackUpRestoreHelper2 = BarBackUpRestoreHelper.this;
                TunerService tunerService = barBackUpRestoreHelper2.tunerService;
                if (z) {
                    str = String.valueOf(tunerService.getValue(1, "brightness_on_top") != 0);
                } else {
                    str = null;
                }
                if (z) {
                    int i3 = 2;
                    if (tunerService.getValue(2, "qspanel_media_quickcontrol_bar_available") != 0) {
                        if (tunerService.getValue(-1, "qspanel_media_quickcontrol_bar_available_on_top") != 0) {
                            Resources resources = barBackUpRestoreHelper2.context.getResources();
                            boolean z2 = resources.getConfiguration().orientation == 2;
                            if (QpRune.QUICK_TABLET) {
                                z2 = z2 && resources.getBoolean(R.bool.hide_media_device_bar_on_landscape);
                            }
                        }
                        i3 = 1;
                    } else {
                        i3 = 0;
                    }
                    str2 = String.valueOf(i3);
                } else {
                    str2 = null;
                }
                if (z) {
                    str3 = String.valueOf(tunerService.getValue(1, "multi_sim_bar_show_on_qspanel") != 0);
                } else {
                    str3 = null;
                }
                if (z) {
                    str4 = String.valueOf(tunerService.getValue(0, "hide_smart_view_large_tile_on_panel") == 0);
                } else {
                    str4 = null;
                }
                String valueOf = z ? String.valueOf(barBackUpRestoreHelper2.settingsHelper.isExpandQsAtOnceEnabled()) : null;
                StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("TAG::qplayout_brightnessbar::", str, "::TAG::qplayout_mediadevices::", str2, "::TAG::qplayout_multisim::");
                AppOpItem$$ExternalSyntheticOutline0.m97m(m87m, str3, "::TAG::hide_smart_view_large_tile_on_panel::", str4, "::TAG::qplayout_expand_qs_at_once::");
                m87m.append(valueOf);
                String sb = m87m.toString();
                Log.d("BarBackUpRestoreManager", " getBackupData: ".concat(sb));
                return sb;
            }

            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
            java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
             */
            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final void onRestore(String str) {
                int i3 = BarBackUpRestoreHelper.$r8$clinit;
                BarBackUpRestoreHelper barBackUpRestoreHelper2 = BarBackUpRestoreHelper.this;
                barBackUpRestoreHelper2.getClass();
                List split$default = StringsKt__StringsKt.split$default(str, new String[]{"::"}, 0, 6);
                Log.d("BarBackUpRestoreManager", " setRestoreData: ".concat(str));
                Iterator it = split$default.iterator();
                while (it.hasNext()) {
                    AbstractC0000x2c234b15.m3m("setRestoreData: string: ", (String) it.next(), "BarBackUpRestoreManager");
                }
                if (split$default.size() <= 1) {
                    return;
                }
                String str2 = (String) split$default.get(0);
                int hashCode = str2.hashCode();
                TunerService tunerService = barBackUpRestoreHelper2.tunerService;
                switch (hashCode) {
                    case -1719643190:
                        if (str2.equals("hide_smart_view_large_tile_on_panel")) {
                            String str3 = (String) split$default.get(1);
                            if (Intrinsics.areEqual(str3, "null")) {
                                Log.w("BarBackUpRestoreManager", "restored hide_smart_view_large_tile_on_panel is null");
                                return;
                            } else if (QpRune.QUICK_HIDE_TILE_FROM_BAR) {
                                tunerService.setValue(!Intrinsics.areEqual(str3, "true") ? 1 : 0, "hide_smart_view_large_tile_on_panel");
                                return;
                            } else {
                                MotionLayout$$ExternalSyntheticOutline0.m23m("restored hide_smart_view_large_tile_on_panel, device has QpRune.QUICK_HIDE_TILE_FROM_BAR is false. value:", str3, "BarBackUpRestoreManager");
                                return;
                            }
                        }
                        break;
                    case -997857676:
                        if (str2.equals("qplayout_multisim")) {
                            String str4 = (String) split$default.get(1);
                            if (Intrinsics.areEqual(str4, "null")) {
                                Log.w("BarBackUpRestoreManager", "restored qplayout_multisim is null");
                                return;
                            } else {
                                tunerService.setValue(Intrinsics.areEqual(str4, "true") ? 1 : 0, "multi_sim_bar_show_on_qspanel");
                                return;
                            }
                        }
                        break;
                    case -552835476:
                        if (str2.equals("qplayout_brightnessbar")) {
                            String str5 = (String) split$default.get(1);
                            if (Intrinsics.areEqual(str5, "null")) {
                                Log.w("BarBackUpRestoreManager", "restored qplayout_brightnessbar is null");
                                return;
                            } else {
                                tunerService.setValue(Intrinsics.areEqual(str5, "true") ? 1 : 0, "brightness_on_top");
                                return;
                            }
                        }
                        break;
                    case -78389521:
                        if (str2.equals("qplayout_mediadevices")) {
                            String str6 = (String) split$default.get(1);
                            if (Intrinsics.areEqual(str6, "null")) {
                                Log.w("BarBackUpRestoreManager", "restored qplayout_mediadevices is null");
                                return;
                            }
                            switch (str6.hashCode()) {
                                case 48:
                                    if (str6.equals(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN)) {
                                        tunerService.setValue(0, "qspanel_media_quickcontrol_bar_available");
                                        tunerService.setValue(0, "qspanel_media_quickcontrol_bar_available_on_top");
                                        return;
                                    }
                                    break;
                                case 49:
                                    if (str6.equals("1")) {
                                        tunerService.setValue(1, "qspanel_media_quickcontrol_bar_available");
                                        tunerService.setValue(0, "qspanel_media_quickcontrol_bar_available_on_top");
                                        return;
                                    }
                                    break;
                                case 50:
                                    if (str6.equals("2")) {
                                        tunerService.setValue(1, "qspanel_media_quickcontrol_bar_available");
                                        tunerService.setValue(1, "qspanel_media_quickcontrol_bar_available_on_top");
                                        return;
                                    }
                                    break;
                            }
                            Log.w("BarBackUpRestoreManager", "updateMediaDevices: " + str6 + " is unknown");
                            return;
                        }
                        break;
                    case 615163903:
                        if (str2.equals("qplayout_expand_qs_at_once")) {
                            String str7 = (String) split$default.get(1);
                            if (Intrinsics.areEqual(str7, "null")) {
                                Log.w("BarBackUpRestoreManager", "restored qplayout_expand_qs_at_once is null");
                                return;
                            } else {
                                Settings.Global.putInt(barBackUpRestoreHelper2.settingsHelper.mContext.getContentResolver(), "swipe_directly_to_quick_setting", Intrinsics.areEqual(str7, "true") ? 1 : 0);
                                return;
                            }
                        }
                        break;
                }
                Log.w("BarBackUpRestoreManager", "setRestoreData: " + split$default.get(0) + " is unknown");
            }
        });
        quickBar.barController = barController;
        QSCinemaCompany qSCinemaCompany = create.getQSCinemaCompany();
        qSCinemaCompany.getClass();
        secQSFragment.qsCinemaFragmentAdapter = qSCinemaCompany;
        QuickPanel quickPanel = secQSFragment.quickPanel;
        nonInterceptingScrollView2.mQsExpandSupplier = quickPanel.qsExpandedSupplier;
        QSButtonsContainerController qSButtonsContainerController = create.getQSButtonsContainerController();
        quickPanel.qsButtonsContainerController = qSButtonsContainerController;
        if (qSButtonsContainerController != null) {
            qSButtonsContainerController.init();
        } else {
            Log.d("QuickPanel", "onViewCreated: qsButtonsContainerController is null");
        }
        QuickTile quickTile = secQSFragment.quickTile;
        quickTile.getClass();
        SecQSDetail secQSDetail = (SecQSDetail) view.findViewById(R.id.qs_detail);
        quickTile.secQSDetail = secQSDetail;
        if (secQSDetail != null) {
            SecQSFragmentAnimatorManager secQSFragmentAnimatorManager2 = quickAnimation.secQSFragmentAnimatorManager;
            if (secQSFragmentAnimatorManager2 != null) {
                QsTransitionAnimator qsTransitionAnimator = secQSFragmentAnimatorManager2.mTransitionAnimator;
                secQSDetail.mTransitionAnimator = qsTransitionAnimator;
                qsTransitionAnimator.mDetailCallback = secQSDetail.new C20715();
            }
            secQSDetail.mQsPanelController = secQSPanelController;
            secQSDetail.mHeader = secQuickStatusBarHeader;
            SecQSDetail.C20682 c20682 = secQSDetail.mQsPanelCallback;
            secQSPanelController.mDetailCallback = c20682;
            secQuickQSPanelController.mDetailCallback = c20682;
            secQSDetail.mFalsingManager = quickTile.falsingManager;
            SecQSDetailContentView secQSDetailContentView = secQSDetail.mDetailContentParent;
            if (secQSDetailContentView != null) {
                secQSDetailContentView.mQsPanelController = secQSPanelController;
            }
        }
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.SecQSFragment$onViewCreated$3
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return SecQSFragment.this.qsExpandedSupplier.getAsBoolean();
            }
        });
        ((SystemUIIndexMediator) Dependency.get(SystemUIIndexMediator.class)).mQsPanelController = secQSPanelController;
        ((StatusBarStateControllerImpl) this.mStatusBarStateController).addCallback((StatusBarStateController.StateListener) this);
        onStateChanged(((StatusBarStateControllerImpl) this.mStatusBarStateController).mState);
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.qs.QSFragment$$ExternalSyntheticLambda3
            public final /* synthetic */ QSFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view2, int i22, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                switch (i2) {
                    case 0:
                        this.f$0.updateQsBounds();
                        break;
                    default:
                        QSFragment qSFragment = this.f$0;
                        qSFragment.getClass();
                        if (i7 - i9 != i3 - i5) {
                            qSFragment.setQsExpansion(qSFragment.mLastQSExpansion, qSFragment.mLastPanelFraction, 0.0f, qSFragment.mSquishinessFraction);
                            break;
                        }
                        break;
                }
            }
        });
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void setCollapseExpandAction(Runnable runnable) {
        SecQSPanelController secQSPanelController = this.mQSPanelController;
        secQSPanelController.mCollapseExpandAction = runnable;
        ((SecQSPanel) secQSPanelController.mView).mCollapseExpandAction = runnable;
        SecQuickQSPanelController secQuickQSPanelController = this.mQuickQSPanelController;
        secQuickQSPanelController.mCollapseExpandAction = runnable;
        ((SecQSPanel) secQuickQSPanelController.mView).mCollapseExpandAction = runnable;
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void setContainerController(QSContainerController qSContainerController) {
        ((QSCustomizer) this.mQSCustomizerController.mView).mQsContainerController = qSContainerController;
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void setExpanded(boolean z) {
        this.mQsExpanded = z;
        if (this.mInSplitShade && z) {
            setListening(true);
        } else {
            updateQsPanelControllerListening();
        }
        updateQsState();
        SecQSFragment secQSFragment = this.mSecQSFragment;
        boolean z2 = this.mListening;
        BarController barController = secQSFragment.quickBar.barController;
        if (barController != null) {
            barController.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda3(z, 2));
        }
        QSButtonsContainerController qSButtonsContainerController = secQSFragment.quickPanel.qsButtonsContainerController;
        if (qSButtonsContainerController != null) {
            qSButtonsContainerController.setListening(z2, z);
        }
        SecQSDetail secQSDetail = secQSFragment.quickTile.secQSDetail;
        if (secQSDetail != null) {
            secQSDetail.mQsExpanded = z;
        }
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void setFancyClipping(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
        boolean z3;
        if (getView() instanceof QSContainerImpl) {
            QSContainerImpl qSContainerImpl = (QSContainerImpl) getView();
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
        SecQSFragmentAnimatorManager secQSFragmentAnimatorManager = this.mSecQSFragment.quickAnimation.secQSFragmentAnimatorManager;
        if (secQSFragmentAnimatorManager != null) {
            secQSFragmentAnimatorManager.setFancyClipping(i, i2, i3, i4, i5, z, z2);
        }
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void setHeaderListening(boolean z) {
        SecQuickStatusBarHeaderController secQuickStatusBarHeaderController = this.mQSContainerImplController.mQuickStatusBarHeaderController;
        if (z != secQuickStatusBarHeaderController.mListening) {
            secQuickStatusBarHeaderController.mListening = z;
            secQuickStatusBarHeaderController.mQuickQSPanelController.setListening(z);
        }
        SecQSFragment secQSFragment = this.mSecQSFragment;
        boolean z2 = this.mQsExpanded;
        BarController barController = secQSFragment.quickBar.barController;
        if (barController != null) {
            barController.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda3(z, 1));
        }
        QSButtonsContainerController qSButtonsContainerController = secQSFragment.quickPanel.qsButtonsContainerController;
        if (qSButtonsContainerController != null) {
            qSButtonsContainerController.setListening(z, z2);
        }
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void setHeightOverride(int i) {
        this.mContainer.updateExpansion();
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void setInSplitShade(boolean z) {
        this.mInSplitShade = z;
        updateShowCollapsedOnKeyguard();
        updateQsState();
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void setIsNotificationPanelFullWidth(boolean z) {
        this.mIsSmallScreen = z;
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void setListening(boolean z) {
        this.mListening = z;
        QSContainerImplController qSContainerImplController = this.mQSContainerImplController;
        boolean z2 = z && this.mQsVisible;
        SecQuickStatusBarHeaderController secQuickStatusBarHeaderController = qSContainerImplController.mQuickStatusBarHeaderController;
        if (z2 != secQuickStatusBarHeaderController.mListening) {
            secQuickStatusBarHeaderController.mListening = z2;
            secQuickStatusBarHeaderController.mQuickQSPanelController.setListening(z2);
        }
        this.mListeningAndVisibilityLifecycleOwner.updateState();
        updateQsPanelControllerListening();
        SecQSFragment secQSFragment = this.mSecQSFragment;
        boolean z3 = this.mQsExpanded;
        BarController barController = secQSFragment.quickBar.barController;
        if (barController != null) {
            barController.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda3(z, 1));
        }
        QSButtonsContainerController qSButtonsContainerController = secQSFragment.quickPanel.qsButtonsContainerController;
        if (qSButtonsContainerController != null) {
            qSButtonsContainerController.setListening(z, z3);
        }
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void setOverScrollAmount(int i) {
        this.mOverScrolling = i != 0;
        View view = getView();
        if (view != null) {
            view.setTranslationY(i);
        }
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void setOverscrolling(boolean z) {
        this.mStackScrollerOverscrolling = z;
        SecQSFragment secQSFragment = this.mSecQSFragment;
        secQSFragment.stackScrollerOverscrolling = z;
        SecQSFragmentAnimatorManager secQSFragmentAnimatorManager = secQSFragment.quickAnimation.secQSFragmentAnimatorManager;
        if (secQSFragmentAnimatorManager != null) {
            secQSFragmentAnimatorManager.setStackScrollerOverscrolling(z);
        }
        updateQsState();
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void setPanelView(InterfaceC1922QS.HeightListener heightListener) {
        this.mPanelView = heightListener;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x011f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0120  */
    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setQsExpansion(final float f, float f2, float f3, float f4) {
        boolean z;
        boolean z2;
        BooleanSupplier booleanSupplier;
        if (!this.mIsSmallScreen && this.mInSplitShade && !this.mTransitioningToFullShade) {
            int i = ((StatusBarStateControllerImpl) this.mStatusBarStateController).mUpcomingState;
        }
        QSContainerImpl qSContainerImpl = this.mContainer;
        qSContainerImpl.mQsExpansion = f;
        qSContainerImpl.updateExpansion();
        float f5 = (f - 1.0f) * (this.mInSplitShade ? 1.0f : 0.1f);
        SysuiStatusBarStateController sysuiStatusBarStateController = this.mStatusBarStateController;
        boolean z3 = (((StatusBarStateControllerImpl) sysuiStatusBarStateController).mUpcomingState == 1) && !this.mShowCollapsedOnKeyguard;
        if (!this.mHeaderAnimating) {
            if (this.mStatusBarState == 1 && this.mShowCollapsedOnKeyguard) {
                if (!(((StatusBarStateControllerImpl) sysuiStatusBarStateController).mUpcomingState == 1)) {
                    z2 = true;
                    if (!z2 && !this.mOverScrolling && (booleanSupplier = this.mSecQSFragment.expandImmediate) != null && !booleanSupplier.getAsBoolean()) {
                        getView().setTranslationY(!z3 ? f5 * this.mHeader.getHeight() : 0.0f);
                    }
                }
            }
            z2 = false;
            if (!z2) {
                getView().setTranslationY(!z3 ? f5 * this.mHeader.getHeight() : 0.0f);
            }
        }
        int height = getView().getHeight();
        if (f == this.mLastQSExpansion && this.mLastKeyguardAndExpanded == z3 && this.mLastViewHeight == height && this.mSquishinessFraction == f4 && this.mLastPanelFraction == f2) {
            return;
        }
        this.mLastPanelFraction = f2;
        this.mSquishinessFraction = f4;
        this.mLastQSExpansion = f;
        this.mLastKeyguardAndExpanded = z3;
        this.mLastViewHeight = height;
        boolean z4 = f == 1.0f;
        boolean z5 = f == 0.0f;
        int heightDiff = getHeightDiff();
        float f6 = heightDiff;
        SecQSFragment secQSFragment = this.mSecQSFragment;
        SecQSFragmentAnimatorManager secQSFragmentAnimatorManager = secQSFragment.quickAnimation.secQSFragmentAnimatorManager;
        if (secQSFragmentAnimatorManager != null) {
            secQSFragmentAnimatorManager.setFullyExpanded(z4);
        }
        BarController barController = secQSFragment.quickBar.barController;
        if (barController != null) {
            if (barController.mQsFullyExpanded != z4) {
                KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(new StringBuilder("setQsFullyExpanded(): mQsFullyExpanded: "), barController.mQsFullyExpanded, "fullyExpanded: ", z4, "BarController");
                barController.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda3(z4, 0));
                barController.mQsFullyExpanded = z4;
            }
            barController.mAllBarItems.forEach(new Consumer() { // from class: com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((BarItemImpl) obj).setPosition(f);
                }
            });
            if (0.0f <= f && f < 1.0f) {
                final int i2 = (int) (f6 * f);
                barController.mAllBarItems.forEach(new Consumer(i2) { // from class: com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((BarItemImpl) obj).getClass();
                    }
                });
            }
            if (heightDiff < 0) {
                Runnable runnable = barController.mQSLastExpansionInitializer;
                if (runnable != null) {
                    runnable.run();
                }
                z = true;
                if (z) {
                    this.mQSPanelController.getClass();
                    this.mQSPanelController.mTileLayout.setExpansion(f);
                    this.mQuickQSPanelController.mTileLayout.setExpansion(f);
                    if (z5) {
                        this.mQSPanelScrollView.setScrollY(0);
                    }
                    SecQSDetail secQSDetail = this.mSecQSFragment.quickTile.secQSDetail;
                    if (secQSDetail != null) {
                        secQSDetail.invalidate();
                    }
                    if (!z4) {
                        this.mQsBounds.top = (int) (-this.mQSPanelScrollView.getTranslationY());
                        this.mQsBounds.right = this.mQSPanelScrollView.getWidth();
                        this.mQsBounds.bottom = this.mQSPanelScrollView.getHeight();
                    }
                    updateQsBounds();
                    QSSquishinessController qSSquishinessController = this.mQSSquishinessController;
                    if (qSSquishinessController != null) {
                        float f7 = this.mSquishinessFraction;
                        if (!(qSSquishinessController.squishiness == f7)) {
                            qSSquishinessController.squishiness = f7;
                        }
                    }
                    SecQSFragment secQSFragment2 = this.mSecQSFragment;
                    SecQSFragmentAnimatorManager secQSFragmentAnimatorManager2 = secQSFragment2.quickAnimation.secQSFragmentAnimatorManager;
                    if (secQSFragmentAnimatorManager2 != null) {
                        secQSFragmentAnimatorManager2.setQsExpansionPosition(f);
                    }
                    QSCinemaCompany qSCinemaCompany = secQSFragment2.qsCinemaFragmentAdapter;
                    if (qSCinemaCompany != null) {
                        qSCinemaCompany.mDirector.getClass();
                        return;
                    }
                    return;
                }
                return;
            }
        }
        z = false;
        if (z) {
        }
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void setQsVisible(boolean z) {
        if (this.mQsVisible == z) {
            return;
        }
        this.mQsVisible = z;
        setListening(this.mListening);
        this.mListeningAndVisibilityLifecycleOwner.updateState();
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void setScrollListener(InterfaceC1922QS.ScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
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
        if (this.mLastQSExpansion == 1.0f) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_tiles_page_horizontal_margin) * 2;
            this.mQsBounds.set(-dimensionPixelSize, 0, this.mQSPanelScrollView.getWidth() + dimensionPixelSize, this.mQSPanelScrollView.getHeight());
        }
        this.mQSPanelScrollView.setClipBounds(this.mQsBounds);
        this.mQSPanelScrollView.getLocationOnScreen(this.mLocationTemp);
    }

    public final void updateQsPanelControllerListening() {
        SecQSPanelController secQSPanelController = this.mQSPanelController;
        boolean z = this.mListening && this.mQsVisible;
        boolean z2 = this.mQsExpanded;
        secQSPanelController.getClass();
        secQSPanelController.setListening(z && z2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0030, code lost:
    
        if (((r0 == null || r0.mQsPanelController.mDetailRecord == null) ? false : true) != false) goto L24;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateQsState() {
        boolean z;
        SecQuickStatusBarHeader secQuickStatusBarHeader;
        boolean z2;
        boolean z3;
        SecQSFragmentAnimatorManager secQSFragmentAnimatorManager;
        BarController barController;
        SecQSDetail secQSDetail;
        if (getView() == null) {
            return;
        }
        if (this.mQsExpanded || this.mStackScrollerOverscrolling || this.mHeaderAnimating) {
            SecQSFragment secQSFragment = this.mSecQSFragment;
            if (!secQSFragment.stackScrollerOverscrolling) {
                SecQSDetail secQSDetail2 = secQSFragment.quickTile.secQSDetail;
            }
        }
        BooleanSupplier booleanSupplier = this.mSecQSFragment.expandImmediate;
        if (booleanSupplier == null || !booleanSupplier.getAsBoolean()) {
            z = false;
            this.mQSPanelController.setExpanded(this.mQsExpanded);
            boolean z4 = ((StatusBarStateControllerImpl) this.mStatusBarStateController).mUpcomingState != 1;
            this.mHeader.setVisibility((!this.mQsExpanded || !z4 || this.mHeaderAnimating || this.mShowCollapsedOnKeyguard) ? 0 : 4);
            secQuickStatusBarHeader = this.mHeader;
            z2 = !(z4 || this.mHeaderAnimating || this.mShowCollapsedOnKeyguard) || (this.mQsExpanded && !this.mStackScrollerOverscrolling);
            SecQuickQSPanelController secQuickQSPanelController = this.mQuickQSPanelController;
            if (secQuickStatusBarHeader.mExpanded != z2) {
                secQuickStatusBarHeader.mExpanded = z2;
                secQuickQSPanelController.setExpanded(z2);
            }
            z3 = this.mQsDisabled && z;
            if (z3) {
                boolean z5 = this.mQsExpanded;
            }
            ((SecQSPanel) this.mQSPanelController.mView).setVisibility(z3 ? 0 : 4);
            SecQSFragment secQSFragment2 = this.mSecQSFragment;
            boolean z6 = this.mQsExpanded;
            QuickAnimation quickAnimation = secQSFragment2.quickAnimation;
            secQSFragmentAnimatorManager = quickAnimation.secQSFragmentAnimatorManager;
            if (secQSFragmentAnimatorManager != null) {
                secQSFragmentAnimatorManager.setQsExpanded(quickAnimation.qsExpandedSupplier.getAsBoolean());
            }
            QuickBar quickBar = secQSFragment2.quickBar;
            barController = quickBar.barController;
            if (barController != null) {
                barController.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda3(quickBar.qsExpandedSupplier.getAsBoolean(), 2));
            }
            secQSDetail = secQSFragment2.quickTile.secQSDetail;
            if (secQSDetail != null || z6) {
            }
            secQSDetail.mTriggeredExpand = false;
            return;
        }
        z = true;
        this.mQSPanelController.setExpanded(this.mQsExpanded);
        if (((StatusBarStateControllerImpl) this.mStatusBarStateController).mUpcomingState != 1) {
        }
        this.mHeader.setVisibility((!this.mQsExpanded || !z4 || this.mHeaderAnimating || this.mShowCollapsedOnKeyguard) ? 0 : 4);
        secQuickStatusBarHeader = this.mHeader;
        if (z4) {
        }
        SecQuickQSPanelController secQuickQSPanelController2 = this.mQuickQSPanelController;
        if (secQuickStatusBarHeader.mExpanded != z2) {
        }
        if (this.mQsDisabled) {
        }
        if (z3) {
        }
        ((SecQSPanel) this.mQSPanelController.mView).setVisibility(z3 ? 0 : 4);
        SecQSFragment secQSFragment22 = this.mSecQSFragment;
        boolean z62 = this.mQsExpanded;
        QuickAnimation quickAnimation2 = secQSFragment22.quickAnimation;
        secQSFragmentAnimatorManager = quickAnimation2.secQSFragmentAnimatorManager;
        if (secQSFragmentAnimatorManager != null) {
        }
        QuickBar quickBar2 = secQSFragment22.quickBar;
        barController = quickBar2.barController;
        if (barController != null) {
        }
        secQSDetail = secQSFragment22.quickTile.secQSDetail;
        if (secQSDetail != null) {
        }
    }

    public final void updateShowCollapsedOnKeyguard() {
        boolean z = this.mBypassController.getBypassEnabled() || (this.mTransitioningToFullShade && !this.mInSplitShade);
        if (z != this.mShowCollapsedOnKeyguard) {
            this.mShowCollapsedOnKeyguard = z;
            updateQsState();
            if (z) {
                return;
            }
            if (((StatusBarStateControllerImpl) this.mStatusBarStateController).mUpcomingState == 1) {
                setQsExpansion(this.mLastQSExpansion, this.mLastPanelFraction, 0.0f, this.mSquishinessFraction);
            }
        }
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void setHasNotifications(boolean z) {
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS
    public final void setHeaderClickable(boolean z) {
    }
}
