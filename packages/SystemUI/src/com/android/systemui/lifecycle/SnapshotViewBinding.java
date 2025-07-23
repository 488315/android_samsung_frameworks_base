package com.android.systemui.lifecycle;

import android.view.View;
import androidx.compose.runtime.snapshots.MutableSnapshot;
import androidx.compose.runtime.snapshots.Snapshot;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SnapshotViewBinding implements View.OnAttachStateChangeListener {
    public final Function1 onError;
    public final Function0 performBind;

    public SnapshotViewBinding(Function0 function0, Function1 function1) {
        this.performBind = function0;
        this.onError = function1;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        Snapshot.Companion.getClass();
        MutableSnapshot takeMutableSnapshot = Snapshot.Companion.takeMutableSnapshot(null, null);
        try {
            Snapshot makeCurrent = takeMutableSnapshot.makeCurrent();
            try {
                SnapshotViewBindingRoot access$getBindingRoot = SnapshotViewBindingKt.access$getBindingRoot(view.getRootView());
                try {
                    access$getBindingRoot.observer.observeReads(this, access$getBindingRoot.onBindingChanged, this.performBind);
                } catch (Throwable th) {
                    this.onError.mo779invoke(th);
                }
                Unit unit = Unit.INSTANCE;
                takeMutableSnapshot.apply().check();
            } finally {
                Snapshot.restoreCurrent(makeCurrent);
            }
        } finally {
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        SnapshotViewBindingRoot access$getBindingRoot = SnapshotViewBindingKt.access$getBindingRoot(view.getRootView());
        access$getBindingRoot.observer.clear(this);
        access$getBindingRoot.invalidatedBindings.remove(this);
    }
}
