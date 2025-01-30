package com.android.systemui.statusbar.policy;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface CallbackController {
    void addCallback(Object obj);

    default void observe(Lifecycle lifecycle, final Object obj) {
        lifecycle.addObserver(new LifecycleEventObserver() { // from class: com.android.systemui.statusbar.policy.CallbackController$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                CallbackController callbackController = CallbackController.this;
                callbackController.getClass();
                Lifecycle.Event event2 = Lifecycle.Event.ON_RESUME;
                Object obj2 = obj;
                if (event == event2) {
                    callbackController.addCallback(obj2);
                } else if (event == Lifecycle.Event.ON_PAUSE) {
                    callbackController.removeCallback(obj2);
                }
            }
        });
    }

    void removeCallback(Object obj);
}
