package com.android.systemui.statusbar.policy;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

public interface CallbackController {
    void addCallback(Object obj);

    default Object observe(Lifecycle lifecycle, final Object obj) {
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
        return obj;
    }

    void removeCallback(Object obj);
}
