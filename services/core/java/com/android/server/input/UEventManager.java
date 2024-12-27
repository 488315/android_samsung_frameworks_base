package com.android.server.input;

import android.os.UEventObserver;

public interface UEventManager {

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
