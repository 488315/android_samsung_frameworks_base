package android.os;

import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes3.dex */
public abstract class UEventObserver {
    private static final boolean DEBUG = false;
    private static final String TAG = "UEventObserver";
    private static UEventThread sThread;

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddMatch(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeRemoveMatch(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetup();

    /* JADX INFO: Access modifiers changed from: private */
    public static native String nativeWaitForNextEvent();

    public abstract void onUEvent(UEvent uEvent);

    protected void finalize() throws Throwable {
        try {
            stopObserving();
        } finally {
            super.finalize();
        }
    }

    private static UEventThread getThread() {
        UEventThread uEventThread;
        synchronized (UEventObserver.class) {
            if (sThread == null) {
                UEventThread uEventThread2 = new UEventThread();
                sThread = uEventThread2;
                uEventThread2.start();
            }
            uEventThread = sThread;
        }
        return uEventThread;
    }

    private static UEventThread peekThread() {
        UEventThread uEventThread;
        synchronized (UEventObserver.class) {
            uEventThread = sThread;
        }
        return uEventThread;
    }

    public final void startObserving(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("match substring must be non-empty");
        }
        getThread().addObserver(str, this);
    }

    public final void stopObserving() {
        UEventThread uEventThreadPeekThread = peekThread();
        if (uEventThreadPeekThread != null) {
            uEventThreadPeekThread.removeObserver(this);
        }
    }

    public static final class UEvent {
        private final HashMap<String, String> mMap = new HashMap<>();

        public UEvent(String str) {
            int length = str.length();
            int i = 0;
            while (i < length) {
                int iIndexOf = str.indexOf(61, i);
                int iIndexOf2 = str.indexOf(0, i);
                if (iIndexOf2 < 0) {
                    return;
                }
                if (iIndexOf > i && iIndexOf < iIndexOf2) {
                    this.mMap.put(str.substring(i, iIndexOf), str.substring(iIndexOf + 1, iIndexOf2));
                }
                i = iIndexOf2 + 1;
            }
        }

        public String get(String str) {
            return this.mMap.get(str);
        }

        public String get(String str, String str2) {
            String str3 = this.mMap.get(str);
            return str3 == null ? str2 : str3;
        }

        public String toString() {
            return this.mMap.toString();
        }
    }

    private static final class UEventThread extends Thread {
        private final ArrayList<Object> mKeysAndObservers;
        private final ArrayList<UEventObserver> mTempObserversToSignal;

        public UEventThread() {
            super(UEventObserver.TAG);
            this.mKeysAndObservers = new ArrayList<>();
            this.mTempObserversToSignal = new ArrayList<>();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            UEventObserver.nativeSetup();
            while (true) {
                String strNativeWaitForNextEvent = UEventObserver.nativeWaitForNextEvent();
                if (strNativeWaitForNextEvent != null) {
                    sendEvent(strNativeWaitForNextEvent);
                }
            }
        }

        private void sendEvent(String str) {
            int i;
            synchronized (this.mKeysAndObservers) {
                int size = this.mKeysAndObservers.size();
                for (int i2 = 0; i2 < size; i2 += 2) {
                    if (str.contains((String) this.mKeysAndObservers.get(i2))) {
                        this.mTempObserversToSignal.add((UEventObserver) this.mKeysAndObservers.get(i2 + 1));
                    }
                }
            }
            if (this.mTempObserversToSignal.isEmpty()) {
                return;
            }
            UEvent uEvent = new UEvent(str);
            int size2 = this.mTempObserversToSignal.size();
            for (i = 0; i < size2; i++) {
                this.mTempObserversToSignal.get(i).onUEvent(uEvent);
            }
            this.mTempObserversToSignal.clear();
        }

        public void addObserver(String str, UEventObserver uEventObserver) {
            synchronized (this.mKeysAndObservers) {
                this.mKeysAndObservers.add(str);
                this.mKeysAndObservers.add(uEventObserver);
                UEventObserver.nativeAddMatch(str);
            }
        }

        public void removeObserver(UEventObserver uEventObserver) {
            synchronized (this.mKeysAndObservers) {
                int i = 0;
                while (i < this.mKeysAndObservers.size()) {
                    int i2 = i + 1;
                    if (this.mKeysAndObservers.get(i2) == uEventObserver) {
                        this.mKeysAndObservers.remove(i2);
                        UEventObserver.nativeRemoveMatch((String) this.mKeysAndObservers.remove(i));
                    } else {
                        i += 2;
                    }
                }
            }
        }
    }
}
