package android.p009os;

import android.p009os.UEventObserver;

/* loaded from: classes3.dex */
public abstract class SemUEventObserver {
    private UEventObserver mUEO = new UEventObserver() { // from class: android.os.SemUEventObserver.1
        @Override // android.p009os.UEventObserver
        public void onUEvent(UEventObserver.UEvent event) {
            SemUEventObserver semUEventObserver = SemUEventObserver.this;
            semUEventObserver.onSemUEvent(semUEventObserver.new SemUEvent(event));
        }
    };

    public abstract void onSemUEvent(SemUEvent semUEvent);

    public final void startObserving(String match) {
        this.mUEO.startObserving(match);
    }

    public final void stopObserving() {
        this.mUEO.stopObserving();
    }

    public final class SemUEvent {
        private UEventObserver.UEvent mEvent;

        public SemUEvent(UEventObserver.UEvent event) {
            this.mEvent = event;
        }

        public String get(String key) {
            return this.mEvent.get(key);
        }

        public String get(String key, String defaultValue) {
            return this.mEvent.get(key, defaultValue);
        }

        public String toString() {
            return this.mEvent.toString();
        }
    }
}
