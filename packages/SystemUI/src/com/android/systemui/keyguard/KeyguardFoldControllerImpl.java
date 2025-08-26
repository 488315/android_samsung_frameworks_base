package com.android.systemui.keyguard;

import android.content.Context;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.os.FactoryTest;
import android.os.Handler;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.View;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.LsRune;
import com.android.systemui.Rune;
import com.android.systemui.globalactions.presentation.features.FakeFeatures$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.uithreadmonitor.BinderCallMonitor;
import com.android.systemui.uithreadmonitor.BinderCallMonitorConstants;
import com.android.systemui.uithreadmonitor.BinderCallMonitorImpl;
import com.android.systemui.uithreadmonitor.LooperSlowLogController;
import com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.LogUtil;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class KeyguardFoldControllerImpl implements KeyguardFoldController {
    public final BinderCallMonitor binderCallMonitor;
    public final Context context;
    public final KeyguardFoldControllerDependency dependency;
    public final KeyguardFoldControllerConfig foldConfig;
    public int foldOpenState;
    public Handler handler;
    public volatile long initShowTime;
    public final LooperSlowLogController looperSlowLogController;
    public final SelectedUserInteractor selectedUserInteractor;
    public final KeyguardUpdateMonitor updateMonitor;
    public final Lazy viewControllerLazy;
    public final kotlin.Lazy viewMediator$delegate;
    public final kotlin.Lazy viewMediatorHelper$delegate;
    public final Lazy viewMediatorLazy;
    public int wakeReason;
    public int foldState = -1;
    public final List highRankedStateListeners = new ArrayList();
    public final List normalRankedStateListeners = new ArrayList();
    public final List foldOpenModeListeners = new ArrayList();

    public KeyguardFoldControllerImpl(Context context, KeyguardFoldControllerConfig keyguardFoldControllerConfig, KeyguardFoldControllerDependency keyguardFoldControllerDependency, WakefulnessLifecycle wakefulnessLifecycle, KeyguardUpdateMonitor keyguardUpdateMonitor, BinderCallMonitor binderCallMonitor, LooperSlowLogController looperSlowLogController, Lazy lazy, Lazy lazy2, SelectedUserInteractor selectedUserInteractor) {
        this.context = context;
        this.foldConfig = keyguardFoldControllerConfig;
        this.dependency = keyguardFoldControllerDependency;
        this.updateMonitor = keyguardUpdateMonitor;
        this.binderCallMonitor = binderCallMonitor;
        this.looperSlowLogController = looperSlowLogController;
        this.viewControllerLazy = lazy;
        this.viewMediatorLazy = lazy2;
        this.selectedUserInteractor = selectedUserInteractor;
        final int i = 0;
        this.viewMediator$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.keyguard.KeyguardFoldControllerImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ KeyguardFoldControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return (KeyguardViewMediator) this.f$0.viewMediatorLazy.get();
                    default:
                        return this.f$0.getViewMediator().mHelper;
                }
            }
        });
        final int i2 = 1;
        this.viewMediatorHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.keyguard.KeyguardFoldControllerImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ KeyguardFoldControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return (KeyguardViewMediator) this.f$0.viewMediatorLazy.get();
                    default:
                        return this.f$0.getViewMediator().mHelper;
                }
            }
        });
        ((KeyguardFoldControllerConfigImpl) keyguardFoldControllerConfig).getClass();
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK) {
            wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.keyguard.KeyguardFoldControllerImpl.1
                @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
                public final void onFinishedWakingUp() {
                    KeyguardFoldControllerImpl keyguardFoldControllerImpl = KeyguardFoldControllerImpl.this;
                    if (((KeyguardViewController) keyguardFoldControllerImpl.viewControllerLazy.get()).isBouncerShowing()) {
                        return;
                    }
                    keyguardFoldControllerImpl.setFoldOpenState(0);
                }
            });
        }
        new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.keyguard.KeyguardFoldControllerImpl$deviceStateCallback$1
            public final void onDeviceStateChanged(DeviceState deviceState) {
                int identifier = deviceState.getIdentifier();
                if (identifier == 0) {
                    this.this$0.notifyFoldStateChanged(false, false);
                } else {
                    if (identifier != 3) {
                        return;
                    }
                    this.this$0.notifyFoldStateChanged(true, false);
                }
            }
        };
    }

    public final boolean addCallback(KeyguardFoldController.StateListener stateListener, int i, boolean z) {
        RankedStateListener rankedStateListener;
        List list = i >= 1000 ? this.highRankedStateListeners : this.normalRankedStateListeners;
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                rankedStateListener = null;
                break;
            }
            rankedStateListener = (RankedStateListener) it.next();
            if (Intrinsics.areEqual(rankedStateListener.stateListener, stateListener)) {
                break;
            }
        }
        if (rankedStateListener != null) {
            return false;
        }
        list.add(new RankedStateListener(stateListener, i, z));
        if (list.size() > 1) {
            CollectionsKt__MutableCollectionsJVMKt.sortWith(list, new Comparator() { // from class: com.android.systemui.keyguard.KeyguardFoldControllerImpl$addCallback$lambda$11$$inlined$sortBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((RankedStateListener) obj).rank), Integer.valueOf(((RankedStateListener) obj2).rank));
                }
            });
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void changeFoldState(boolean z) {
        boolean z2 = !z;
        int i = this.foldState;
        boolean z3 = i == -1 || i != z2;
        boolean z4 = i == -1;
        KeyguardFoldControllerConfigImpl keyguardFoldControllerConfigImpl = (KeyguardFoldControllerConfigImpl) this.foldConfig;
        String strM = FakeFeatures$$ExternalSyntheticOutline0.m("changeFoldState: foldState=", keyguardFoldControllerConfigImpl.isDebug() ? z2 != -1 ? !z ? z2 != 1 ? "" : "FOLD_OPEN" : "FOLD_CLOSE" : "FOLD_NONE" : String.valueOf(z2 ? 1 : 0), ", changed=", z3);
        ((KeyguardFoldControllerDependencyImpl) this.dependency).getClass();
        Log.d("KeyguardFoldController", strM);
        if (z3) {
            this.foldState = z2 ? 1 : 0;
            if (keyguardFoldControllerConfigImpl.isDebug()) {
                ((LooperSlowLogControllerImpl) this.looperSlowLogController).enable(2, 10L, 20L, 3000L, false, null);
            }
            notifyFoldStateChanged(z2, z4);
        }
    }

    public final String getFoldOpenModeStr(int i) {
        return ((KeyguardFoldControllerConfigImpl) this.foldConfig).isDebug() ? i != 0 ? i != 1 ? i != 2 ? i != 3 ? "" : "MODE_WAKE_UNLOCK" : "MODE_UNLOCK" : "MODE_BOUNCER" : "MODE_RESET" : String.valueOf(i);
    }

    public final KeyguardViewMediator getViewMediator() {
        return (KeyguardViewMediator) this.viewMediator$delegate.getValue();
    }

    public final boolean isBouncerOnFoldOpened() {
        return this.foldOpenState == 1;
    }

    public final boolean isFoldOpened() {
        return (FactoryTest.isFactoryBinary() && LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) || this.foldState == 1;
    }

    public final boolean isUnlockOnFoldOpened() {
        int i = this.foldOpenState;
        return i == 2 || i == 3;
    }

    public final void notifyFoldStateChanged(boolean z, boolean z2) {
        ((KeyguardFoldControllerConfigImpl) this.foldConfig).getClass();
        if (Rune.SYSUI_BINDER_CALL_MONITOR) {
            BinderCallMonitorImpl binderCallMonitorImpl = (BinderCallMonitorImpl) this.binderCallMonitor;
            binderCallMonitorImpl.getClass();
            binderCallMonitorImpl.startMonitoring(4, BinderCallMonitorConstants.MAX_DURATION / 1000000, 3000L);
        }
        Handler handler = this.handler;
        if (handler == null) {
            handler = null;
        }
        if (handler.hasMessages(1003)) {
            ((KeyguardFoldControllerDependencyImpl) this.dependency).getClass();
            Log.d("KeyguardFoldController", "notifyFoldStateChanged remove previous msg");
            handler.removeMessages(1003);
        }
        handler.sendMessageAtFrontOfQueue(handler.obtainMessage(1003, z ? 1 : 0, !z2 ? 1 : 0));
        if (!z2 && z && this.initShowTime > 0 && (!this.updateMonitor.isSecure() || this.updateMonitor.getUserCanSkipBouncer(this.selectedUserInteractor.getSelectedUserId()))) {
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = (KeyguardViewMediatorHelperImpl) ((KeyguardViewMediatorHelper) this.viewMediatorHelper$delegate.getValue());
            ViewMediatorProvider viewMediatorProvider = keyguardViewMediatorHelperImpl.viewMediatorProvider;
            (viewMediatorProvider != null ? viewMediatorProvider : null).resetPendingLock.invoke();
            keyguardViewMediatorHelperImpl.removeShowMsg();
        }
        onFoldStateChanged(this.highRankedStateListeners, z, z2, false);
    }

    public final void onFoldStateChanged(List list, final boolean z, boolean z2, boolean z3) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!((RankedStateListener) obj).skipInitState || !z2) {
                arrayList.add(obj);
            }
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList.get(i);
            i++;
            final RankedStateListener rankedStateListener = (RankedStateListener) obj2;
            if (z3) {
                StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(rankedStateListener.rank, "onFoldStateChanged ", " ");
                sbM.append(rankedStateListener.stateListener);
                String string = sbM.toString();
                Runnable runnable = new Runnable() { // from class: com.android.systemui.keyguard.KeyguardFoldControllerImpl$onFoldStateChanged$2$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        rankedStateListener.stateListener.onFoldStateChanged(z);
                    }
                };
                ((KeyguardFoldControllerDependencyImpl) this.dependency).getClass();
                LogUtil.execTime(runnable, 10, "LooperSlow", string, new Object[0]);
            } else {
                rankedStateListener.stateListener.onFoldStateChanged(z);
            }
        }
    }

    public final void resetFoldOpenState$1() {
        if (!isBouncerOnFoldOpened() || (!getViewMediator().getViewMediatorCallback().isScreenOn() && !this.updateMonitor.isEarlyWakeUp())) {
            setFoldOpenState(0);
        } else {
            ((KeyguardFoldControllerDependencyImpl) this.dependency).getClass();
            Log.d("KeyguardFoldController", "skip resetFoldOpenState");
        }
    }

    public final void setFoldOpenState(int i) {
        int i2 = this.foldOpenState;
        KeyguardFoldControllerDependency keyguardFoldControllerDependency = this.dependency;
        if (i2 == i) {
            if (((KeyguardFoldControllerConfigImpl) this.foldConfig).isDebug()) {
                String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("already ", getFoldOpenModeStr(i));
                ((KeyguardFoldControllerDependencyImpl) keyguardFoldControllerDependency).getClass();
                Log.d("KeyguardFoldController", strM);
                return;
            }
            return;
        }
        String strM2 = AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("setFoldOpenState ", getFoldOpenModeStr(i2), " -> ", getFoldOpenModeStr(i));
        ((KeyguardFoldControllerDependencyImpl) keyguardFoldControllerDependency).getClass();
        Log.d("KeyguardFoldController", strM2);
        int i3 = this.foldOpenState;
        this.foldOpenState = i;
        ArrayList arrayList = (ArrayList) this.foldOpenModeListeners;
        int size = arrayList.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList.get(i4);
            i4++;
            NotificationStackScrollLayoutController.AnonymousClass2 anonymousClass2 = (NotificationStackScrollLayoutController.AnonymousClass2) obj;
            int i5 = this.foldOpenState;
            anonymousClass2.getClass();
            if (i5 == 0) {
                android.util.Log.d("StackScrollerController", "request stackScroller forceLayout");
                boolean z = i3 == 2;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController.mHasDelayedForceLayout = z;
                NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                NotificationStackScrollLayoutController.AnonymousClass1 anonymousClass1 = notificationStackScrollLayoutController.mForceLayoutTimeOutRunnable;
                if (z) {
                    notificationStackScrollLayout.postDelayed(anonymousClass1, 5000L);
                } else {
                    android.util.Log.d("StackScrollerController", "do stackScroller forceLayout");
                    notificationStackScrollLayout.removeCallbacks(anonymousClass1);
                    ((View) notificationStackScrollLayout.getParent()).forceLayout();
                    notificationStackScrollLayout.forceLayout();
                }
            }
        }
        if (i == 0) {
            this.wakeReason = 0;
        }
    }
}
