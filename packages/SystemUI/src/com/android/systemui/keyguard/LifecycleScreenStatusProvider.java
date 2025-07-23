package com.android.systemui.keyguard;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.unfold.updates.screen.ScreenStatusProvider;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LifecycleScreenStatusProvider implements ScreenStatusProvider, ScreenLifecycle.Observer {
    public final List listeners;

    public LifecycleScreenStatusProvider(ScreenLifecycle screenLifecycle) {
        screenLifecycle.addObserver(this);
        this.listeners = new CopyOnWriteArrayList();
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void addCallback(Object obj) {
        ((CopyOnWriteArrayList) this.listeners).add((ScreenStatusProvider.ScreenListener) obj);
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public final void onScreenTurnedOn() {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("LifecycleScreenStatusProvider#onScreenTurnedOn");
        }
        try {
            Iterator it = ((CopyOnWriteArrayList) this.listeners).iterator();
            while (it.hasNext()) {
                ((ScreenStatusProvider.ScreenListener) it.next()).onScreenTurnedOn();
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
            Iterator it = ((CopyOnWriteArrayList) this.listeners).iterator();
            while (it.hasNext()) {
                ((ScreenStatusProvider.ScreenListener) it.next()).onScreenTurningOff();
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
            Iterator it = ((CopyOnWriteArrayList) this.listeners).iterator();
            while (it.hasNext()) {
                ((ScreenStatusProvider.ScreenListener) it.next()).onScreenTurningOn();
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

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void removeCallback(Object obj) {
        ((CopyOnWriteArrayList) this.listeners).remove((ScreenStatusProvider.ScreenListener) obj);
    }
}
