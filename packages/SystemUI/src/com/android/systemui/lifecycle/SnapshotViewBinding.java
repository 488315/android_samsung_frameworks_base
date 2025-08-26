package com.android.systemui.lifecycle;

import android.view.View;
import androidx.compose.runtime.snapshots.MutableSnapshot;
import androidx.compose.runtime.snapshots.Snapshot;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

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
        MutableSnapshot mutableSnapshotTakeMutableSnapshot = Snapshot.Companion.takeMutableSnapshot(null, null);
        try {
            Snapshot snapshotMakeCurrent = mutableSnapshotTakeMutableSnapshot.makeCurrent();
            try {
                SnapshotViewBindingRoot snapshotViewBindingRootAccess$getBindingRoot = SnapshotViewBindingKt.access$getBindingRoot(view.getRootView());
                try {
                    snapshotViewBindingRootAccess$getBindingRoot.observer.observeReads(this, snapshotViewBindingRootAccess$getBindingRoot.onBindingChanged, this.performBind);
                } catch (Throwable th) {
                    this.onError.mo781invoke(th);
                }
                Unit unit = Unit.INSTANCE;
                mutableSnapshotTakeMutableSnapshot.apply().check();
            } finally {
                Snapshot.restoreCurrent(snapshotMakeCurrent);
            }
        } finally {
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        SnapshotViewBindingRoot snapshotViewBindingRootAccess$getBindingRoot = SnapshotViewBindingKt.access$getBindingRoot(view.getRootView());
        snapshotViewBindingRootAccess$getBindingRoot.observer.clear(this);
        snapshotViewBindingRootAccess$getBindingRoot.invalidatedBindings.remove(this);
    }
}
