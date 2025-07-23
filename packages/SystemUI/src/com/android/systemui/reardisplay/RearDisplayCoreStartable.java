package com.android.systemui.reardisplay;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Handler;
import android.view.accessibility.AccessibilityManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.CoreStartable;
import com.android.systemui.display.domain.interactor.RearDisplayStateInteractor;
import com.android.systemui.reardisplay.RearDisplayInnerDialogDelegate;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class RearDisplayCoreStartable implements CoreStartable, AutoCloseable {
    public final AccessibilityManager accessibilityManager;
    public final Context context;
    public final DeviceStateManager deviceStateManager;
    public final Handler handler;
    public final KeyguardUpdateMonitorCallback keyguardCallback;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final StateFlowImpl keyguardVisible;
    public final ReadonlyStateFlow keyguardVisibleFlow;
    public final RearDisplayInnerDialogDelegate.Factory rearDisplayInnerDialogDelegateFactory;
    public final RearDisplayStateInteractor rearDisplayStateInteractor;
    public final CoroutineScope scope;
    public StandaloneCoroutine stateChangeListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public RearDisplayCoreStartable(Context context, DeviceStateManager deviceStateManager, RearDisplayStateInteractor rearDisplayStateInteractor, RearDisplayInnerDialogDelegate.Factory factory, CoroutineScope coroutineScope, KeyguardUpdateMonitor keyguardUpdateMonitor, AccessibilityManager accessibilityManager, Handler handler) {
        this.context = context;
        this.deviceStateManager = deviceStateManager;
        this.rearDisplayStateInteractor = rearDisplayStateInteractor;
        this.rearDisplayInnerDialogDelegateFactory = factory;
        this.scope = coroutineScope;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.accessibilityManager = accessibilityManager;
        this.handler = handler;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this.keyguardVisible = MutableStateFlow;
        this.keyguardVisibleFlow = FlowKt.asStateFlow(MutableStateFlow);
        this.keyguardCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.reardisplay.RearDisplayCoreStartable$keyguardCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                RearDisplayCoreStartable.this.keyguardVisible.updateState(null, Boolean.valueOf(z));
            }
        };
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        StandaloneCoroutine standaloneCoroutine = this.stateChangeListener;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [T, java.util.concurrent.atomic.AtomicBoolean] */
    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
        ref$ObjectRef2.element = new AtomicBoolean(false);
        this.accessibilityManager.addTouchExplorationStateChangeListener(new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: com.android.systemui.reardisplay.RearDisplayCoreStartable$start$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
            public final void onTouchExplorationStateChanged(boolean z) {
                ((AtomicBoolean) ref$ObjectRef2.element).set(z);
            }
        }, this.handler);
        this.keyguardUpdateMonitor.registerCallback(this.keyguardCallback);
        this.stateChangeListener = BuildersKt.launch$default(this.scope, null, null, new RearDisplayCoreStartable$start$2(this, ref$ObjectRef2, ref$ObjectRef, null), 3);
    }

    public static /* synthetic */ void getKeyguardCallback$annotations() {
    }

    public static /* synthetic */ void getStateChangeListener$annotations() {
    }
}
