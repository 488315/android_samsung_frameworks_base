package android.os;

import android.os.UEventObserver;

/* loaded from: classes3.dex */
public abstract class SemUEventObserver {
    private UEventObserver mUEO = new UEventObserver() { // from class: android.os.SemUEventObserver.1
        @Override // android.os.UEventObserver
        public void onUEvent(UEventObserver.UEvent uEvent) {
            SemUEventObserver.this.onSemUEvent(new SemUEvent(uEvent));
        }
    };

    public abstract void onSemUEvent(SemUEvent semUEvent);

    public final void startObserving(String str) {
        this.mUEO.startObserving(str);
    }

    public final void stopObserving() {
        this.mUEO.stopObserving();
    }

    public static final class SemUEvent {
        private UEventObserver.UEvent mEvent;

        public SemUEvent(UEventObserver.UEvent uEvent) {
            this.mEvent = uEvent;
        }

        public String get(String str) {
            return this.mEvent.get(str);
        }

        public String get(String str, String str2) {
            return this.mEvent.get(str, str2);
        }

        public String toString() {
            return this.mEvent.toString();
        }
    }
}
