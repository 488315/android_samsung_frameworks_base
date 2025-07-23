package com.android.systemui.qs.animator;

import android.content.res.Configuration;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.animator.QSAnimViewProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.PanelSlideEventHandler;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.PanelTransitionStateListener;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import kotlin.Triple;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SecQSImplAnimatorManager extends SecQSImplAnimatorBase implements Dumpable, LockscreenShadeTransitionController.Callback, PanelTransitionStateListener {
    public final SecQSDetailController mDetailController;
    public final DumpManager mDumpManager;
    public final LegacyQsExpandAnimator mExpandAnimator;
    public final ImmersiveScrollAnimator mImmersiveScrollAnimator;
    public final ImmersiveScrollPopOverAnimator mImmersiveScrollPopOverAnimator;
    public final ArrayList mList;
    public final LockscreenShadeTransitionController mLockscreenShadeTransitionController;
    public final AnonymousClass1 mNotificationScrolled;
    public boolean mOnPanelOpenedCalledAlready;
    public final LegacyQsOpenAnimator mOpenAnimator;
    public final SecPanelSplitHelper mPanelSplitHelper;
    public final PanelSplitOpenAnimator mPanelSplitOpenAnimator;
    public final PanelTransitionAnimator mPanelTransitionAnimator;
    public final QsDetailPopupAnimator mQsDetailPopupAnimator;
    public final ShadeExpansionStateManager mShadeExpansionStateManager;
    public final QsTransitionAnimator mTransitionAnimator;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.animator.SecQSImplAnimatorManager$1, reason: invalid class name */
    public class AnonymousClass1 implements Consumer {
        public AnonymousClass1() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            SecQSImplAnimatorManager.this.onNotificationScrolled(((Integer) obj).intValue());
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.animator.SecQSImplAnimatorManager$2, reason: invalid class name */
    public class AnonymousClass2 {
        public AnonymousClass2() {
        }

        public static void setCustomizerShowing(boolean z) {
            QsAnimatorState.INSTANCE.getClass();
            SecQSExpansionStateInteractor secQSExpansionStateInteractor = (SecQSExpansionStateInteractor) QsAnimatorState.qsExpansionStateInteractor$delegate.getValue();
            secQSExpansionStateInteractor.getRepository()._isCustomizerShowing.updateState(null, Boolean.valueOf(z));
            PanelAffordanceAnimator$secQSStateListener$1 panelAffordanceAnimator$secQSStateListener$1 = secQSExpansionStateInteractor.qsStateListener;
            if (panelAffordanceAnimator$secQSStateListener$1 != null) {
                PanelAffordanceAnimator panelAffordanceAnimator = panelAffordanceAnimator$secQSStateListener$1.this$0;
                if (!panelAffordanceAnimator.isThereNoView() && z && !panelAffordanceAnimator.isNotVisible()) {
                    panelAffordanceAnimator.hiding(200);
                }
            }
            QsAnimatorState.isCustomizerShowing = z;
        }

        public static void setDetailClosing(boolean z) {
            QsAnimatorState.INSTANCE.getClass();
            ((SecQSExpansionStateInteractor) QsAnimatorState.qsExpansionStateInteractor$delegate.getValue()).getRepository()._isDetailClosing.updateState(null, Boolean.valueOf(z));
            QsAnimatorState.isDetailClosing = z;
        }

        public static void setDetailOpening(boolean z) {
            QsAnimatorState.INSTANCE.getClass();
            SecQSExpansionStateInteractor secQSExpansionStateInteractor = (SecQSExpansionStateInteractor) QsAnimatorState.qsExpansionStateInteractor$delegate.getValue();
            secQSExpansionStateInteractor.getRepository()._isDetailOpening.updateState(null, Boolean.valueOf(z));
            PanelAffordanceAnimator$secQSStateListener$1 panelAffordanceAnimator$secQSStateListener$1 = secQSExpansionStateInteractor.qsStateListener;
            if (panelAffordanceAnimator$secQSStateListener$1 != null) {
                PanelAffordanceAnimator panelAffordanceAnimator = panelAffordanceAnimator$secQSStateListener$1.this$0;
                if (!panelAffordanceAnimator.isThereNoView() && z && !panelAffordanceAnimator.isNotVisible()) {
                    panelAffordanceAnimator.hiding(200);
                }
            }
            QsAnimatorState.isDetailOpening = z;
        }
    }

    public SecQSImplAnimatorManager(LegacyQsExpandAnimator legacyQsExpandAnimator, LegacyQsOpenAnimator legacyQsOpenAnimator, QsTransitionAnimator qsTransitionAnimator, PanelTransitionAnimator panelTransitionAnimator, PanelSplitOpenAnimator panelSplitOpenAnimator, ImmersiveScrollAnimator immersiveScrollAnimator, ImmersiveScrollPopOverAnimator immersiveScrollPopOverAnimator, QsDetailPopupAnimator qsDetailPopupAnimator, PanelAffordanceAnimator panelAffordanceAnimator, ShadeExpansionStateManager shadeExpansionStateManager, DumpManager dumpManager, ShadeRepository shadeRepository, SecQSDetailController secQSDetailController, SecPanelSplitHelper secPanelSplitHelper, LockscreenShadeTransitionController lockscreenShadeTransitionController) {
        ArrayList arrayList = new ArrayList();
        this.mList = arrayList;
        int i = 0;
        this.mOnPanelOpenedCalledAlready = false;
        this.mNotificationScrolled = new AnonymousClass1();
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        this.mDumpManager = dumpManager;
        this.mShadeExpansionStateManager = shadeExpansionStateManager;
        this.mExpandAnimator = legacyQsExpandAnimator;
        this.mOpenAnimator = legacyQsOpenAnimator;
        this.mTransitionAnimator = qsTransitionAnimator;
        qsTransitionAnimator.animStateCallback = anonymousClass2;
        this.mQsDetailPopupAnimator = qsDetailPopupAnimator;
        qsDetailPopupAnimator.animStateCallback = anonymousClass2;
        this.mPanelTransitionAnimator = panelTransitionAnimator;
        this.mPanelSplitOpenAnimator = panelSplitOpenAnimator;
        this.mImmersiveScrollAnimator = immersiveScrollAnimator;
        this.mImmersiveScrollPopOverAnimator = immersiveScrollPopOverAnimator;
        Log.d("SecQSImplAnimatorManager", "updateAnimatorList");
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((SecQSImplAnimatorBase) obj).clearAnimationState();
        }
        this.mList.clear();
        this.mList.add(this.mExpandAnimator);
        this.mList.add(this.mOpenAnimator);
        this.mList.add(this.mPanelTransitionAnimator);
        this.mList.add(this.mPanelSplitOpenAnimator);
        this.mList.add(this.mTransitionAnimator);
        this.mList.add(this.mImmersiveScrollAnimator);
        this.mList.add(this.mImmersiveScrollPopOverAnimator);
        this.mList.add(this.mQsDetailPopupAnimator);
        setShadeRepository(shadeRepository);
        this.mDetailController = secQSDetailController;
        this.mPanelSplitHelper = secPanelSplitHelper;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        secQSDetailController.setQsAnimatorManager(this);
        secPanelSplitHelper.qsAnimatorManager = this;
        PanelScreenShotLogger.INSTANCE.addLogProvider("SecQSImplAnimatorManager", this);
        DumpManager dumpManager2 = this.mDumpManager;
        dumpManager2.getClass();
        DumpManager.registerDumpable$default(dumpManager2, "SecQSImplAnimatorManager", this);
        this.mShadeExpansionStateManager.addExpansionListener(this);
        secPanelSplitHelper.addListener(this);
        ((UserTrackerImpl) secPanelSplitHelper.userTracker).addCallback(secPanelSplitHelper.userChanged, secPanelSplitHelper.executor);
        lockscreenShadeTransitionController.addCallback(this);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void destroyQSViews() {
        Log.d("SecQSImplAnimatorManager", "destroyQSViews");
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda6(0));
        this.mDetailController.setQsAnimatorManager(null);
        SecPanelSplitHelper secPanelSplitHelper = this.mPanelSplitHelper;
        secPanelSplitHelper.qsAnimatorManager = null;
        synchronized (PanelScreenShotLogger.INSTANCE) {
            PanelScreenShotLogger.providers.remove("SecQSImplAnimatorManager");
        }
        this.mDumpManager.unregisterDumpable("SecQSImplAnimatorManager");
        this.mShadeExpansionStateManager.removeExpansionListener(this);
        secPanelSplitHelper.removeListener(this);
        ((UserTrackerImpl) secPanelSplitHelper.userTracker).removeCallback(secPanelSplitHelper.userChanged);
        this.mLockscreenShadeTransitionController.removeCallback(this);
        this.mTransitionAnimator.animStateCallback = null;
        this.mQsDetailPopupAnimator.animStateCallback = null;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("Quick Panel Animation State =============================================== ");
        getAnimationState().forEach(new SecQSImplAnimatorManager$$ExternalSyntheticLambda0(printWriter, 0));
        printWriter.println("=========================================================================== ");
    }

    public final void executeConsumer(Consumer consumer) {
        this.mList.stream().filter(new SecQSImplAnimatorManager$$ExternalSyntheticLambda16(0)).forEach(consumer);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        return getAnimationState();
    }

    public final ArrayList getAnimationState() {
        final ArrayList arrayList = new ArrayList();
        arrayList.add("SecQSImplAnimatorManager ============================================= ");
        arrayList.add("  mQsExpanded = " + QsAnimatorState.qsExpanded + "  mQsFullyExpanded = false  mPanelExpanded = " + QsAnimatorState.panelExpanded);
        StringBuilder sb = new StringBuilder("  mIsExpanding = false  mState = ");
        sb.append(QsAnimatorState.state);
        sb.append("  mQsExpandImmediate = false mQsCollapsingWhilePanelClosing = false");
        arrayList.add(sb.toString());
        arrayList.add("  isCustomizerShowing = " + QsAnimatorState.isCustomizerShowing + "  isDetailOpening = " + QsAnimatorState.isDetailOpening + "  isDetailShowing = " + QsAnimatorState.isDetailShowing + " isDetailClosing = " + QsAnimatorState.isDetailClosing + " isDetailPopupShowing = " + QsAnimatorState.isDetailPopupShowing);
        this.mList.stream().filter(new SecQSImplAnimatorManager$$ExternalSyntheticLambda16(0)).map(new SecQSImplAnimatorManager$$ExternalSyntheticLambda17()).filter(new SecQSImplAnimatorManager$$ExternalSyntheticLambda16(1)).forEach(new SecQSImplAnimatorManager$$ExternalSyntheticLambda0(arrayList, 1));
        arrayList.add("QSAnimViewState =============================================");
        QsAnimatorState.animViewStateMap.forEach(new BiConsumer() { // from class: com.android.systemui.qs.animator.SecQSImplAnimatorManager$$ExternalSyntheticLambda20
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Triple triple = (Triple) obj2;
                arrayList.add("  [" + ((QSAnimViewProvider.ViewType) obj) + "] alpha: " + triple.getFirst() + " visibility: " + triple.getSecond() + " | caller: " + ((String) triple.getThird()));
            }
        });
        arrayList.add("============================================================== ");
        return arrayList;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onConfigurationChanged(Configuration configuration) {
        Log.d("SecQSImplAnimatorManager", "onConfigurationChanged");
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda0(configuration, 4));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onNotificationScrolled(int i) {
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda2(i, 0));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelClosed$1() {
        Log.d("SecQSImplAnimatorManager", "onPanelClosed");
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda6(4));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda0(shadeExpansionChangeEvent, 3));
        if (shadeExpansionChangeEvent.fraction == 0.0f) {
            if (QsAnimatorState.panelExpanded) {
                updatePanelExpanded(false);
            }
            this.mOnPanelOpenedCalledAlready = false;
        } else {
            if (!QsAnimatorState.panelExpanded) {
                updatePanelExpanded(true);
            }
            if (this.mOnPanelOpenedCalledAlready) {
                return;
            }
            executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda6(3));
            this.mOnPanelOpenedCalledAlready = true;
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelOpened() {
        Log.d("SecQSImplAnimatorManager", "onPanelOpened");
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda6(3));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
        super.onPanelTransitionStateChanged(panelTransitionStateChangeEvent);
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda0(panelTransitionStateChangeEvent, 7));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onRtlChanged() {
        Log.d("SecQSImplAnimatorManager", "onRtlChanged");
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda6(5));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onStateChanged(int i) {
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda2(i, 1));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onUserSwitched(int i) {
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda6(1));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setFancyClipping(final int i, final int i2, final int i3, final int i4, final int i5, final boolean z, final boolean z2) {
        executeConsumer(new Consumer() { // from class: com.android.systemui.qs.animator.SecQSImplAnimatorManager$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SecQSImplAnimatorBase) obj).setFancyClipping(i, i2, i3, i4, i5, z, z2);
            }
        });
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setNotificationStackScrollerController(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.mStackScrollerController = notificationStackScrollLayoutController;
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda0(notificationStackScrollLayoutController, 5));
        notificationStackScrollLayoutController.mView.mScrollChangedConsumer = this.mNotificationScrolled;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setOverDragAmount(float f) {
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda10(f, 2));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setOverScrollAmount(float f) {
        this.mOverScrollAmount = f;
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda10(f, 1));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQs(QS qs) {
        Log.d("SecQSImplAnimatorManager", "setQs");
        QSImpl qSImpl = (QSImpl) qs;
        this.mQs = qSImpl;
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda0(qSImpl, 6));
        if (qs != null) {
            updateAnimators();
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQsExpanded(boolean z) {
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda5(z, 1));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQsExpansionPosition(float f) {
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda10(f, 0));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setShadeRepository(ShadeRepository shadeRepository) {
        this.mShadeRepository = shadeRepository;
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda0(shadeRepository, 2));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setStackScrollerOverscrolling(boolean z) {
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda5(z, 0));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setTransitionToFullShadeAmount(float f) {
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda10(f, 3));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void slide(final float f, final float f2, final PanelSlideEventHandler.Direction direction, final int i) {
        if (f > 0.0f && this.mOverScrollAmount > 0.0f) {
            setOverScrollAmount(0.0f);
        }
        executeConsumer(new Consumer() { // from class: com.android.systemui.qs.animator.SecQSImplAnimatorManager$$ExternalSyntheticLambda27
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SecQSImplAnimatorBase) obj).slide(f, f2, direction, i);
            }
        });
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updateAnimators() {
        Log.d("SecQSImplAnimatorManager", "updateAnimators");
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda6(2));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updatePanelExpanded(boolean z) {
        QsAnimatorState.panelExpanded = z;
        executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda5(z, 2));
    }
}
