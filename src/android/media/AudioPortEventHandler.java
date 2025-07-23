package android.media;

import android.media.AudioManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes2.dex */
class AudioPortEventHandler {
    private static final int AUDIOPORT_EVENT_NEW_LISTENER = 4;
    private static final int AUDIOPORT_EVENT_PATCH_LIST_UPDATED = 2;
    private static final int AUDIOPORT_EVENT_PORT_LIST_UPDATED = 1;
    private static final int AUDIOPORT_EVENT_SERVICE_DIED = 3;
    private static final long RESCHEDULE_MESSAGE_DELAY_MS = 100;
    private static final String TAG = "AudioPortEventHandler";
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private long mJniCallback;
    private final Object mLock = new Object();
    private final ArrayList<AudioManager.OnAudioPortUpdateListener> mListeners = new ArrayList<>();

    private native void native_finalize();

    private native void native_setup(Object obj);

    AudioPortEventHandler() {
    }

    void init() {
        synchronized (this.mLock) {
            if (this.mHandler != null) {
                return;
            }
            HandlerThread handlerThread = new HandlerThread(TAG);
            this.mHandlerThread = handlerThread;
            handlerThread.start();
            if (this.mHandlerThread.getLooper() != null) {
                this.mHandler = new Handler(this.mHandlerThread.getLooper()) { // from class: android.media.AudioPortEventHandler.1
                    /* JADX WARN: Removed duplicated region for block: B:37:0x00b9 A[LOOP:1: B:35:0x00b3->B:37:0x00b9, LOOP_END] */
                    @Override // android.os.Handler
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public void handleMessage(android.os.Message r9) {
                        /*
                            r8 = this;
                            android.media.AudioPortEventHandler r0 = android.media.AudioPortEventHandler.this
                            java.lang.Object r0 = android.media.AudioPortEventHandler.m2369$$Nest$fgetmLock(r0)
                            monitor-enter(r0)
                            int r1 = r9.what     // Catch: java.lang.Throwable -> Lc6
                            r2 = 4
                            if (r1 != r2) goto L27
                            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Lc6
                            r1.<init>()     // Catch: java.lang.Throwable -> Lc6
                            android.media.AudioPortEventHandler r3 = android.media.AudioPortEventHandler.this     // Catch: java.lang.Throwable -> Lc6
                            java.util.ArrayList r3 = android.media.AudioPortEventHandler.m2368$$Nest$fgetmListeners(r3)     // Catch: java.lang.Throwable -> Lc6
                            java.lang.Object r4 = r9.obj     // Catch: java.lang.Throwable -> Lc6
                            boolean r3 = r3.contains(r4)     // Catch: java.lang.Throwable -> Lc6
                            if (r3 == 0) goto L33
                            java.lang.Object r3 = r9.obj     // Catch: java.lang.Throwable -> Lc6
                            android.media.AudioManager$OnAudioPortUpdateListener r3 = (android.media.AudioManager.OnAudioPortUpdateListener) r3     // Catch: java.lang.Throwable -> Lc6
                            r1.add(r3)     // Catch: java.lang.Throwable -> Lc6
                            goto L33
                        L27:
                            android.media.AudioPortEventHandler r1 = android.media.AudioPortEventHandler.this     // Catch: java.lang.Throwable -> Lc6
                            java.util.ArrayList r1 = android.media.AudioPortEventHandler.m2368$$Nest$fgetmListeners(r1)     // Catch: java.lang.Throwable -> Lc6
                            java.lang.Object r1 = r1.clone()     // Catch: java.lang.Throwable -> Lc6
                            java.util.ArrayList r1 = (java.util.ArrayList) r1     // Catch: java.lang.Throwable -> Lc6
                        L33:
                            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc6
                            int r0 = r9.what
                            r3 = 3
                            r4 = 2
                            r5 = 1
                            if (r0 == r5) goto L43
                            int r0 = r9.what
                            if (r0 == r4) goto L43
                            int r0 = r9.what
                            if (r0 != r3) goto L46
                        L43:
                            android.media.AudioManager.resetAudioPortGeneration()
                        L46:
                            boolean r0 = r1.isEmpty()
                            if (r0 == 0) goto L4e
                            goto Lc5
                        L4e:
                            java.util.ArrayList r0 = new java.util.ArrayList
                            r0.<init>()
                            java.util.ArrayList r6 = new java.util.ArrayList
                            r6.<init>()
                            r7 = 0
                            int r7 = android.media.AudioManager.updateAudioPortCache(r0, r6, r7)
                            if (r7 == 0) goto L6d
                            int r0 = r9.what
                            java.lang.Object r9 = r9.obj
                            android.os.Message r9 = r8.obtainMessage(r0, r9)
                            r0 = 100
                            r8.sendMessageDelayed(r9, r0)
                            return
                        L6d:
                            int r8 = r9.what
                            r7 = 0
                            if (r8 == r5) goto L8b
                            if (r8 == r4) goto Lab
                            if (r8 == r3) goto L79
                            if (r8 == r2) goto L8b
                            goto Lc5
                        L79:
                            int r8 = r1.size()
                            if (r7 >= r8) goto Lc5
                            java.lang.Object r8 = r1.get(r7)
                            android.media.AudioManager$OnAudioPortUpdateListener r8 = (android.media.AudioManager.OnAudioPortUpdateListener) r8
                            r8.onServiceDied()
                            int r7 = r7 + 1
                            goto L79
                        L8b:
                            android.media.AudioPort[] r8 = new android.media.AudioPort[r7]
                            java.lang.Object[] r8 = r0.toArray(r8)
                            android.media.AudioPort[] r8 = (android.media.AudioPort[]) r8
                            r0 = r7
                        L94:
                            int r2 = r1.size()
                            if (r0 >= r2) goto La6
                            java.lang.Object r2 = r1.get(r0)
                            android.media.AudioManager$OnAudioPortUpdateListener r2 = (android.media.AudioManager.OnAudioPortUpdateListener) r2
                            r2.onAudioPortListUpdate(r8)
                            int r0 = r0 + 1
                            goto L94
                        La6:
                            int r8 = r9.what
                            if (r8 != r5) goto Lab
                            goto Lc5
                        Lab:
                            android.media.AudioPatch[] r8 = new android.media.AudioPatch[r7]
                            java.lang.Object[] r8 = r6.toArray(r8)
                            android.media.AudioPatch[] r8 = (android.media.AudioPatch[]) r8
                        Lb3:
                            int r9 = r1.size()
                            if (r7 >= r9) goto Lc5
                            java.lang.Object r9 = r1.get(r7)
                            android.media.AudioManager$OnAudioPortUpdateListener r9 = (android.media.AudioManager.OnAudioPortUpdateListener) r9
                            r9.onAudioPatchListUpdate(r8)
                            int r7 = r7 + 1
                            goto Lb3
                        Lc5:
                            return
                        Lc6:
                            r8 = move-exception
                            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc6
                            throw r8
                        */
                        throw new UnsupportedOperationException("Method not decompiled: android.media.AudioPortEventHandler.AnonymousClass1.handleMessage(android.os.Message):void");
                    }
                };
                native_setup(new WeakReference(this));
            } else {
                this.mHandler = null;
            }
        }
    }

    protected void finalize() {
        native_finalize();
        if (this.mHandlerThread.isAlive()) {
            this.mHandlerThread.quit();
        }
    }

    void registerListener(AudioManager.OnAudioPortUpdateListener onAudioPortUpdateListener) {
        synchronized (this.mLock) {
            this.mListeners.add(onAudioPortUpdateListener);
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            this.mHandler.sendMessage(handler.obtainMessage(4, 0, 0, onAudioPortUpdateListener));
        }
    }

    void unregisterListener(AudioManager.OnAudioPortUpdateListener onAudioPortUpdateListener) {
        synchronized (this.mLock) {
            this.mListeners.remove(onAudioPortUpdateListener);
        }
    }

    Handler handler() {
        return this.mHandler;
    }

    private static void postEventFromNative(Object obj, int i, int i2, int i3, Object obj2) {
        Handler handler;
        AudioPortEventHandler audioPortEventHandler = (AudioPortEventHandler) ((WeakReference) obj).get();
        if (audioPortEventHandler == null || audioPortEventHandler == null || (handler = audioPortEventHandler.handler()) == null) {
            return;
        }
        Message obtainMessage = handler.obtainMessage(i, i2, i3, obj2);
        if (i != 4) {
            handler.removeMessages(i);
        }
        handler.sendMessage(obtainMessage);
    }
}
