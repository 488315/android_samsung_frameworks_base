package com.android.systemui.keyguard;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.unfold.updates.DeviceFoldStateProvider;
import com.android.systemui.unfold.updates.screen.ScreenStatusProvider;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Unit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LifecycleScreenStatusProvider implements ScreenStatusProvider, ScreenLifecycle.Observer {
    public final List listeners;

    public LifecycleScreenStatusProvider(ScreenLifecycle screenLifecycle) {
        screenLifecycle.addObserver(this);
        this.listeners = new CopyOnWriteArrayList();
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void addCallback(Object obj) {
        ((CopyOnWriteArrayList) this.listeners).add((DeviceFoldStateProvider.ScreenStatusListener) obj);
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public final void onScreenTurnedOn() {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("LifecycleScreenStatusProvider#onScreenTurnedOn");
        }
        try {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((DeviceFoldStateProvider.ScreenStatusListener) it.next()).onScreenTurnedOn();
            }
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public final void onScreenTurningOff() {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("LifecycleScreenStatusProvider#onScreenTurningOff");
        }
        try {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((DeviceFoldStateProvider.ScreenStatusListener) it.next()).onScreenTurningOff();
            }
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public final void onScreenTurningOn() {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("LifecycleScreenStatusProvider#onScreenTurningOn");
        }
        try {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((DeviceFoldStateProvider.ScreenStatusListener) it.next()).onScreenTurningOn();
            }
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }
}
