package com.android.systemui.communal;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.shared.model.DozeStateModel;
import com.android.systemui.keyguard.shared.model.DozeTransitionModel;
import com.android.systemui.shared.condition.Condition;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeviceInactiveCondition extends Condition {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean anyDoze;
    public Job anyDozeListenerJob;
    public final CoroutineScope applicationScope;
    public final JavaAdapter javaAdapter;
    public final KeyguardInteractor keyguardInteractor;
    public final DeviceInactiveCondition$keyguardStateCallback$1 keyguardStateCallback;
    public final KeyguardStateController keyguardStateController;
    public final DeviceInactiveCondition$keyguardUpdateCallback$1 keyguardUpdateCallback;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final WakefulnessLifecycle wakefulnessLifecycle;
    public final DeviceInactiveCondition$wakefulnessObserver$1 wakefulnessObserver;

    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.communal.DeviceInactiveCondition$keyguardStateCallback$1] */
    /* JADX WARN: Type inference failed for: r6v2, types: [com.android.systemui.communal.DeviceInactiveCondition$wakefulnessObserver$1] */
    /* JADX WARN: Type inference failed for: r6v3, types: [com.android.systemui.communal.DeviceInactiveCondition$keyguardUpdateCallback$1] */
    public DeviceInactiveCondition(CoroutineScope coroutineScope, CoroutineScope coroutineScope2, KeyguardStateController keyguardStateController, WakefulnessLifecycle wakefulnessLifecycle, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardInteractor keyguardInteractor, JavaAdapter javaAdapter) {
        super(coroutineScope2, null, false, 6, null);
        this.applicationScope = coroutineScope;
        this.keyguardStateController = keyguardStateController;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.keyguardInteractor = keyguardInteractor;
        this.javaAdapter = javaAdapter;
        this.keyguardStateCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.communal.DeviceInactiveCondition$keyguardStateCallback$1
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                int i = DeviceInactiveCondition.$r8$clinit;
                DeviceInactiveCondition.this.updateState();
            }
        };
        this.wakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.communal.DeviceInactiveCondition$wakefulnessObserver$1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                int i = DeviceInactiveCondition.$r8$clinit;
                DeviceInactiveCondition.this.updateState();
            }
        };
        this.keyguardUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.communal.DeviceInactiveCondition$keyguardUpdateCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDreamingStateChanged(boolean z) {
                int i = DeviceInactiveCondition.$r8$clinit;
                DeviceInactiveCondition.this.updateState();
            }
        };
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final int getStartStrategy() {
        return 0;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final Object start(Continuation continuation) {
        updateState();
        ((KeyguardStateControllerImpl) this.keyguardStateController).addCallback(this.keyguardStateCallback);
        BuildersKt.launch$default(this.applicationScope, null, null, new DeviceInactiveCondition$start$2(this, null), 3);
        this.wakefulnessLifecycle.addObserver(this.wakefulnessObserver);
        this.anyDozeListenerJob = this.javaAdapter.alwaysCollectFlow(this.keyguardInteractor.dozeTransitionModel, new Consumer() { // from class: com.android.systemui.communal.DeviceInactiveCondition$start$3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DeviceInactiveCondition deviceInactiveCondition = DeviceInactiveCondition.this;
                DozeStateModel.Companion companion = DozeStateModel.Companion;
                DozeStateModel dozeStateModel = ((DozeTransitionModel) obj).to;
                companion.getClass();
                deviceInactiveCondition.anyDoze = !(dozeStateModel == DozeStateModel.UNINITIALIZED || dozeStateModel == DozeStateModel.FINISH);
                DeviceInactiveCondition.this.updateState();
            }
        });
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void stop() {
        ((KeyguardStateControllerImpl) this.keyguardStateController).removeCallback(this.keyguardStateCallback);
        this.keyguardUpdateMonitor.removeCallback(this.keyguardUpdateCallback);
        this.wakefulnessLifecycle.removeObserver(this.wakefulnessObserver);
        Job job = this.anyDozeListenerJob;
        if (job != null) {
            job.cancel(null);
        }
    }

    public final void updateState() {
        boolean z = false;
        boolean z2 = this.wakefulnessLifecycle.mWakefulness == 0;
        if (!this.anyDoze && (z2 || ((KeyguardStateControllerImpl) this.keyguardStateController).mShowing || this.keyguardUpdateMonitor.mIsDreaming)) {
            z = true;
        }
        updateCondition(z);
    }
}
