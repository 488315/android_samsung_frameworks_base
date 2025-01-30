package com.android.systemui.keyguard;

import android.os.SystemClock;
import android.view.View;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$initView$1;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeStateListener;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardVisibilityMonitor implements Runnable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ExecutorImpl.ExecutionToken cancelExecToken;
    public final DelayableExecutor executor;
    public final KeyguardTransitionInteractor interactor;
    public final Lazy keyguardStateController$delegate;
    public Consumer listener;
    public boolean needsExpand;
    public ShadeExpansionChangeEvent panelExpansionChangeEvent;
    public int panelState;
    public final CoroutineScope scope;
    public final dagger.Lazy shadeExpansionStateManagerLazy;
    public int curVisibility = -1;
    public final List visibilityChangedListeners = new ArrayList();
    public final List isExpandedChangedListeners = new ArrayList();
    public final List panelStateChangedListeners = new ArrayList();

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

    public KeyguardVisibilityMonitor(CoroutineScope coroutineScope, KeyguardTransitionInteractor keyguardTransitionInteractor, DelayableExecutor delayableExecutor, final dagger.Lazy lazy, dagger.Lazy lazy2) {
        this.scope = coroutineScope;
        this.interactor = keyguardTransitionInteractor;
        this.executor = delayableExecutor;
        this.shadeExpansionStateManagerLazy = lazy2;
        this.keyguardStateController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardVisibilityMonitor$keyguardStateController$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (KeyguardStateController) dagger.Lazy.this.get();
            }
        });
    }

    public void addVisibilityChangedListener(IntConsumer intConsumer) {
        ArrayList arrayList = (ArrayList) this.visibilityChangedListeners;
        if (arrayList.contains(intConsumer)) {
            return;
        }
        arrayList.add(intConsumer);
    }

    public final void cancelExecToken(boolean z) {
        ExecutorImpl.ExecutionToken executionToken = this.cancelExecToken;
        if (executionToken != null) {
            if (z) {
                Log.m138d("KeyguardVisible", "cancel");
            }
            executionToken.run();
            this.cancelExecToken = null;
        }
    }

    public final KeyguardStateController getKeyguardStateController() {
        return (KeyguardStateController) this.keyguardStateController$delegate.getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void panelLog(ShadeExpansionChangeEvent shadeExpansionChangeEvent, Integer num) {
        boolean z;
        if (this.panelExpansionChangeEvent == null) {
            return;
        }
        int intValue = num != null ? num.intValue() : this.panelState;
        if (shadeExpansionChangeEvent == null) {
            shadeExpansionChangeEvent = this.panelExpansionChangeEvent;
        }
        ShadeExpansionChangeEvent shadeExpansionChangeEvent2 = this.panelExpansionChangeEvent;
        if (Intrinsics.areEqual(shadeExpansionChangeEvent2 != null ? Boolean.valueOf(shadeExpansionChangeEvent2.tracking) : null, shadeExpansionChangeEvent != null ? Boolean.valueOf(shadeExpansionChangeEvent.tracking) : null)) {
            ShadeExpansionChangeEvent shadeExpansionChangeEvent3 = this.panelExpansionChangeEvent;
            if (Intrinsics.areEqual(shadeExpansionChangeEvent3 != null ? Boolean.valueOf(shadeExpansionChangeEvent3.expanded) : null, shadeExpansionChangeEvent != null ? Boolean.valueOf(shadeExpansionChangeEvent.expanded) : null) && this.panelState == intValue) {
                z = false;
                if (z) {
                    return;
                }
                int i = this.panelState;
                String str = "OPEN";
                String valueOf = i != 0 ? i != 1 ? i != 2 ? String.valueOf(i) : "OPEN" : "OPENING" : "CLOSED";
                if (intValue == 0) {
                    str = "CLOSED";
                } else if (intValue == 1) {
                    str = "OPENING";
                } else if (intValue != 2) {
                    str = String.valueOf(intValue);
                }
                Boolean valueOf2 = shadeExpansionChangeEvent != null ? Boolean.valueOf(shadeExpansionChangeEvent.tracking) : null;
                Boolean valueOf3 = shadeExpansionChangeEvent != null ? Boolean.valueOf(shadeExpansionChangeEvent.expanded) : null;
                Float valueOf4 = shadeExpansionChangeEvent != null ? Float.valueOf(shadeExpansionChangeEvent.fraction) : null;
                StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("go panelState: ", valueOf, " -> ", str, " tracking=");
                m87m.append(valueOf2);
                m87m.append(", expanded=");
                m87m.append(valueOf3);
                m87m.append(", fraction=");
                m87m.append(valueOf4);
                android.util.Log.d("KeyguardVisible", m87m.toString());
                return;
            }
        }
        z = true;
        if (z) {
        }
    }

    public final void registerMonitor(View view, SecNotificationShadeWindowControllerHelperImpl$initView$1 secNotificationShadeWindowControllerHelperImpl$initView$1) {
        this.listener = secNotificationShadeWindowControllerHelperImpl$initView$1;
        NotificationShadeWindowView notificationShadeWindowView = view instanceof NotificationShadeWindowView ? (NotificationShadeWindowView) view : null;
        if (notificationShadeWindowView != null) {
            notificationShadeWindowView.mVisibilityChangedListener = new IntConsumer() { // from class: com.android.systemui.keyguard.KeyguardVisibilityMonitor$registerMonitor$1$1
                @Override // java.util.function.IntConsumer
                public final void accept(int i) {
                    KeyguardVisibilityMonitor.this.visibilityChanged(i);
                }
            };
        }
        ((KeyguardStateControllerImpl) getKeyguardStateController()).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.keyguard.KeyguardVisibilityMonitor$registerMonitor$2
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardFadingAwayChanged() {
                int i = KeyguardVisibilityMonitor.$r8$clinit;
                KeyguardVisibilityMonitor keyguardVisibilityMonitor = KeyguardVisibilityMonitor.this;
                Log.m138d("KeyguardVisible", "onKeyguardFadingAwayChanged " + ((KeyguardStateControllerImpl) keyguardVisibilityMonitor.getKeyguardStateController()).mKeyguardFadingAway);
                if (!((KeyguardStateControllerImpl) keyguardVisibilityMonitor.getKeyguardStateController()).mKeyguardFadingAway) {
                    SystemClock.elapsedRealtime();
                }
                keyguardVisibilityMonitor.getClass();
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardGoingAwayChanged() {
                int i = KeyguardVisibilityMonitor.$r8$clinit;
                KeyguardVisibilityMonitor keyguardVisibilityMonitor = KeyguardVisibilityMonitor.this;
                Log.m138d("KeyguardVisible", "onKeyguardGoingAwayChanged " + ((KeyguardStateControllerImpl) keyguardVisibilityMonitor.getKeyguardStateController()).mKeyguardGoingAway);
                if (!((KeyguardStateControllerImpl) keyguardVisibilityMonitor.getKeyguardStateController()).mKeyguardGoingAway) {
                    SystemClock.elapsedRealtime();
                }
                keyguardVisibilityMonitor.getClass();
            }
        });
        ShadeExpansionStateManager shadeExpansionStateManager = (ShadeExpansionStateManager) this.shadeExpansionStateManagerLazy.get();
        shadeExpansionStateManager.addExpansionListener(new ShadeExpansionListener() { // from class: com.android.systemui.keyguard.KeyguardVisibilityMonitor$registerMonitor$3$1
            @Override // com.android.systemui.shade.ShadeExpansionListener
            public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
                int i = KeyguardVisibilityMonitor.$r8$clinit;
                KeyguardVisibilityMonitor keyguardVisibilityMonitor = KeyguardVisibilityMonitor.this;
                keyguardVisibilityMonitor.panelLog(shadeExpansionChangeEvent, null);
                keyguardVisibilityMonitor.panelExpansionChangeEvent = shadeExpansionChangeEvent;
            }
        });
        shadeExpansionStateManager.stateListeners.add(new ShadeStateListener() { // from class: com.android.systemui.keyguard.KeyguardVisibilityMonitor$registerMonitor$3$2
            @Override // com.android.systemui.shade.ShadeStateListener
            public final void onPanelStateChanged(int i) {
                Integer valueOf = Integer.valueOf(i);
                int i2 = KeyguardVisibilityMonitor.$r8$clinit;
                KeyguardVisibilityMonitor keyguardVisibilityMonitor = KeyguardVisibilityMonitor.this;
                keyguardVisibilityMonitor.panelLog(null, valueOf);
                if (keyguardVisibilityMonitor.panelState != i) {
                    Iterator it = CollectionsKt___CollectionsKt.toList(keyguardVisibilityMonitor.panelStateChangedListeners).iterator();
                    while (it.hasNext()) {
                        ((Function2) it.next()).invoke(Integer.valueOf(keyguardVisibilityMonitor.panelState), Integer.valueOf(i));
                    }
                }
                keyguardVisibilityMonitor.panelState = i;
            }
        });
        BuildersKt.launch$default(this.scope, null, null, new KeyguardVisibilityMonitor$registerMonitor$4(this, null), 3);
    }

    @Override // java.lang.Runnable
    public final void run() {
        cancelExecToken(false);
        Consumer consumer = this.listener;
        if (consumer == null) {
            consumer = null;
        }
        consumer.accept(Boolean.valueOf(this.needsExpand));
    }

    public final void start(boolean z) {
        Log.m138d("KeyguardVisible", "start needsExpand=" + z);
        cancelExecToken(false);
        this.needsExpand = z;
        this.cancelExecToken = this.executor.executeDelayed(1500L, this);
    }

    public final void visibilityChanged(int i) {
        int i2 = this.curVisibility;
        if (i2 == i) {
            return;
        }
        Log.m138d("KeyguardVisible", "visibilityChanged " + i2 + " -> " + i);
        Iterator it = CollectionsKt___CollectionsKt.toList(this.visibilityChangedListeners).iterator();
        while (it.hasNext()) {
            ((IntConsumer) it.next()).accept(i);
        }
        this.curVisibility = i;
    }
}
