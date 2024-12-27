package com.android.server.input;

import android.os.UEventObserver;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface UEventManager {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class UEventListener {
        public final AnonymousClass1 mObserver =
                new UEventObserver() { // from class:
                                       // com.android.server.input.UEventManager.UEventListener.1
                    public final void onUEvent(UEventObserver.UEvent uEvent) {
                        UEventListener.this.onUEvent(uEvent);
                    }
                };

        public abstract void onUEvent(UEventObserver.UEvent uEvent);
    }
}
