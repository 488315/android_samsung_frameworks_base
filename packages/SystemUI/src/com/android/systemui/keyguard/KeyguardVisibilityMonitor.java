package com.android.systemui.keyguard;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$initView$1;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntConsumer;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public class KeyguardVisibilityMonitor implements Runnable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Runnable cancelExecToken;
    public final DelayableExecutor executor;
    public final Lazy keyguardStateController$delegate;
    public SecNotificationShadeWindowControllerHelperImpl$initView$1 listener;
    public boolean needsExpand;
    public ShadeExpansionChangeEvent panelExpansionChangeEvent;
    public int panelState;
    public final dagger.Lazy shadeExpansionStateManagerLazy;
    public int curVisibility = -1;
    public final List visibilityChangedListeners = new ArrayList();
    public final List isExpandedChangedListeners = new ArrayList();
    public final List panelStateChangedListeners = new ArrayList();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardVisibilityMonitor(CoroutineScope coroutineScope, KeyguardTransitionInteractor keyguardTransitionInteractor, DelayableExecutor delayableExecutor, final dagger.Lazy lazy, dagger.Lazy lazy2) {
        this.executor = delayableExecutor;
        this.shadeExpansionStateManagerLazy = lazy2;
        this.keyguardStateController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardVisibilityMonitor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = KeyguardVisibilityMonitor.$r8$clinit;
                return (KeyguardStateController) lazy.get();
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
        Runnable runnable = this.cancelExecToken;
        if (runnable != null) {
            if (z) {
                Log.d("KeyguardVisible", "cancel");
            }
            runnable.run();
            this.cancelExecToken = null;
        }
    }

    public final KeyguardStateController getKeyguardStateController() {
        return (KeyguardStateController) this.keyguardStateController$delegate.getValue();
    }

    public final boolean isVisible() {
        return this.curVisibility == 0;
    }

    public final void panelLog(ShadeExpansionChangeEvent shadeExpansionChangeEvent, Integer num) {
        if (this.panelExpansionChangeEvent == null) {
            return;
        }
        int iIntValue = num != null ? num.intValue() : this.panelState;
        if (shadeExpansionChangeEvent == null) {
            shadeExpansionChangeEvent = this.panelExpansionChangeEvent;
        }
        ShadeExpansionChangeEvent shadeExpansionChangeEvent2 = this.panelExpansionChangeEvent;
        if (Intrinsics.areEqual(shadeExpansionChangeEvent2 != null ? Boolean.valueOf(shadeExpansionChangeEvent2.tracking) : null, shadeExpansionChangeEvent != null ? Boolean.valueOf(shadeExpansionChangeEvent.tracking) : null)) {
            ShadeExpansionChangeEvent shadeExpansionChangeEvent3 = this.panelExpansionChangeEvent;
            if (Intrinsics.areEqual(shadeExpansionChangeEvent3 != null ? Boolean.valueOf(shadeExpansionChangeEvent3.expanded) : null, shadeExpansionChangeEvent != null ? Boolean.valueOf(shadeExpansionChangeEvent.expanded) : null) && this.panelState == iIntValue) {
                return;
            }
        }
        int i = this.panelState;
        String strValueOf = i != 0 ? i != 1 ? i != 2 ? String.valueOf(i) : "OPEN" : "OPENING" : "CLOSED";
        String strValueOf2 = iIntValue != 0 ? iIntValue != 1 ? iIntValue != 2 ? String.valueOf(iIntValue) : "OPEN" : "OPENING" : "CLOSED";
        Boolean boolValueOf = shadeExpansionChangeEvent != null ? Boolean.valueOf(shadeExpansionChangeEvent.tracking) : null;
        Boolean boolValueOf2 = shadeExpansionChangeEvent != null ? Boolean.valueOf(shadeExpansionChangeEvent.expanded) : null;
        Float fValueOf = shadeExpansionChangeEvent != null ? Float.valueOf(shadeExpansionChangeEvent.fraction) : null;
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("go panelState: ", strValueOf, " -> ", strValueOf2, " tracking=");
        sbM.append(boolValueOf);
        sbM.append(", expanded=");
        sbM.append(boolValueOf2);
        sbM.append(", fraction=");
        sbM.append(fValueOf);
        android.util.Log.d("KeyguardVisible", sbM.toString());
    }

    @Override // java.lang.Runnable
    public final void run() {
        cancelExecToken(false);
        SecNotificationShadeWindowControllerHelperImpl$initView$1 secNotificationShadeWindowControllerHelperImpl$initView$1 = this.listener;
        if (secNotificationShadeWindowControllerHelperImpl$initView$1 == null) {
            secNotificationShadeWindowControllerHelperImpl$initView$1 = null;
        }
        secNotificationShadeWindowControllerHelperImpl$initView$1.accept(Boolean.valueOf(this.needsExpand));
    }

    public final void start(boolean z) {
        Log.d("KeyguardVisible", "start needsExpand=" + z);
        cancelExecToken(false);
        this.needsExpand = z;
        this.cancelExecToken = this.executor.executeDelayed(this, 1500L);
    }

    public final void visibilityChanged(int i) {
        int i2 = this.curVisibility;
        if (i2 == i) {
            return;
        }
        Log.d("KeyguardVisible", "visibilityChanged " + i2 + " -> " + i);
        Iterator it = CollectionsKt___CollectionsKt.toList(this.visibilityChangedListeners).iterator();
        while (it.hasNext()) {
            ((IntConsumer) it.next()).accept(i);
        }
        this.curVisibility = i;
    }
}
