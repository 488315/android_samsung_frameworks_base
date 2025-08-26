package com.android.systemui.lifecycle;

import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;
import android.view.View;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.runtime.snapshots.Snapshot$Companion$$ExternalSyntheticLambda0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver;
import com.android.systemui.R;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public abstract class SnapshotViewBindingKt {
    public static final SnapshotViewBindingRoot access$getBindingRoot(View view) {
        Looper looper;
        Object tag = view.getTag(R.id.snapshot_view_binding_root);
        SnapshotViewBindingRoot snapshotViewBindingRoot = tag instanceof SnapshotViewBindingRoot ? (SnapshotViewBindingRoot) tag : null;
        if (snapshotViewBindingRoot != null) {
            return snapshotViewBindingRoot;
        }
        Handler handler = view.getHandler();
        if (handler == null || (looper = handler.getLooper()) == null) {
            throw new IllegalStateException((view + " is not attached to a window").toString());
        }
        final SnapshotViewBindingRoot snapshotViewBindingRoot2 = new SnapshotViewBindingRoot(Handler.createAsync(looper), Choreographer.getInstance());
        view.setTag(R.id.snapshot_view_binding_root, snapshotViewBindingRoot2);
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.lifecycle.SnapshotViewBindingKt$bindingRoot$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view2) {
                SnapshotStateObserver snapshotStateObserver = snapshotViewBindingRoot2.observer;
                snapshotStateObserver.getClass();
                Snapshot.Companion companion = Snapshot.Companion;
                Function2 function2 = snapshotStateObserver.applyObserver;
                companion.getClass();
                snapshotStateObserver.applyUnsubscribe = Snapshot.Companion.registerApplyObserver(function2);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view2) {
                SnapshotViewBindingRoot snapshotViewBindingRoot3 = snapshotViewBindingRoot2;
                Snapshot$Companion$$ExternalSyntheticLambda0 snapshot$Companion$$ExternalSyntheticLambda0 = snapshotViewBindingRoot3.observer.applyUnsubscribe;
                if (snapshot$Companion$$ExternalSyntheticLambda0 != null) {
                    snapshot$Companion$$ExternalSyntheticLambda0.dispose();
                }
                snapshotViewBindingRoot3.choreographer.removeFrameCallback(snapshotViewBindingRoot3.frameCallback);
                snapshotViewBindingRoot3.isFrameScheduled = false;
                snapshotViewBindingRoot3.invalidatedBindings.clear();
            }
        });
        if (view.isAttachedToWindow()) {
            SnapshotStateObserver snapshotStateObserver = snapshotViewBindingRoot2.observer;
            snapshotStateObserver.getClass();
            Snapshot.Companion companion = Snapshot.Companion;
            Function2 function2 = snapshotStateObserver.applyObserver;
            companion.getClass();
            snapshotStateObserver.applyUnsubscribe = Snapshot.Companion.registerApplyObserver(function2);
        }
        return snapshotViewBindingRoot2;
    }
}
