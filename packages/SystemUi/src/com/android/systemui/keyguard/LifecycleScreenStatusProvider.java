package com.android.systemui.keyguard;

import android.os.Trace;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.unfold.updates.DeviceFoldStateProvider;
import com.android.systemui.unfold.updates.hinge.HingeAngleProvider;
import com.android.systemui.unfold.updates.screen.ScreenStatusProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LifecycleScreenStatusProvider implements ScreenStatusProvider, ScreenLifecycle.Observer {
    public final List listeners;

    public LifecycleScreenStatusProvider(ScreenLifecycle screenLifecycle) {
        screenLifecycle.addObserver(this);
        this.listeners = new ArrayList();
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void addCallback(Object obj) {
        ((ArrayList) this.listeners).add((DeviceFoldStateProvider.ScreenStatusListener) obj);
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public final void onScreenTurnedOn() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        List list = this.listeners;
        if (!isTagEnabled) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ((DeviceFoldStateProvider.ScreenStatusListener) it.next()).onScreenTurnedOn();
            }
            return;
        }
        Trace.traceBegin(4096L, "LifecycleScreenStatusProvider#onScreenTurnedOn");
        try {
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                ((DeviceFoldStateProvider.ScreenStatusListener) it2.next()).onScreenTurnedOn();
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public final void onScreenTurningOff() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        List list = this.listeners;
        if (!isTagEnabled) {
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                DeviceFoldStateProvider deviceFoldStateProvider = DeviceFoldStateProvider.this;
                deviceFoldStateProvider.isScreenOn = false;
                deviceFoldStateProvider.hingeAngleProvider.stop();
            }
            return;
        }
        Trace.traceBegin(4096L, "LifecycleScreenStatusProvider#onScreenTurningOff");
        try {
            Iterator it2 = ((ArrayList) list).iterator();
            while (it2.hasNext()) {
                DeviceFoldStateProvider deviceFoldStateProvider2 = DeviceFoldStateProvider.this;
                deviceFoldStateProvider2.isScreenOn = false;
                deviceFoldStateProvider2.hingeAngleProvider.stop();
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public final void onScreenTurningOn() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        List list = this.listeners;
        if (!isTagEnabled) {
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                DeviceFoldStateProvider deviceFoldStateProvider = DeviceFoldStateProvider.this;
                deviceFoldStateProvider.isScreenOn = true;
                boolean z = deviceFoldStateProvider.isFolded;
                HingeAngleProvider hingeAngleProvider = deviceFoldStateProvider.hingeAngleProvider;
                if (z) {
                    hingeAngleProvider.stop();
                } else {
                    hingeAngleProvider.start();
                }
            }
            return;
        }
        Trace.traceBegin(4096L, "LifecycleScreenStatusProvider#onScreenTurningOn");
        try {
            Iterator it2 = ((ArrayList) list).iterator();
            while (it2.hasNext()) {
                DeviceFoldStateProvider deviceFoldStateProvider2 = DeviceFoldStateProvider.this;
                deviceFoldStateProvider2.isScreenOn = true;
                boolean z2 = deviceFoldStateProvider2.isFolded;
                HingeAngleProvider hingeAngleProvider2 = deviceFoldStateProvider2.hingeAngleProvider;
                if (z2) {
                    hingeAngleProvider2.stop();
                } else {
                    hingeAngleProvider2.start();
                }
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }
}
