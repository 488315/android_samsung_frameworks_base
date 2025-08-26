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
                    /* JADX WARN: Removed duplicated region for block: B:37:0x009a A[LOOP:2: B:35:0x0094->B:37:0x009a, LOOP_END] */
                    /* JADX WARN: Removed duplicated region for block: B:55:? A[RETURN, SYNTHETIC] */
                    @Override // android.os.Handler
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public void handleMessage(Message message) {
                        ArrayList arrayList;
                        int i;
                        synchronized (AudioPortEventHandler.this.mLock) {
                            if (message.what == 4) {
                                arrayList = new ArrayList();
                                if (AudioPortEventHandler.this.mListeners.contains(message.obj)) {
                                    arrayList.add((AudioManager.OnAudioPortUpdateListener) message.obj);
                                }
                            } else {
                                arrayList = (ArrayList) AudioPortEventHandler.this.mListeners.clone();
                            }
                        }
                        if (message.what == 1 || message.what == 2 || message.what == 3) {
                            AudioManager.resetAudioPortGeneration();
                        }
                        if (arrayList.isEmpty()) {
                            return;
                        }
                        ArrayList arrayList2 = new ArrayList();
                        ArrayList arrayList3 = new ArrayList();
                        if (AudioManager.updateAudioPortCache(arrayList2, arrayList3, null) != 0) {
                            sendMessageDelayed(obtainMessage(message.what, message.obj), AudioPortEventHandler.RESCHEDULE_MESSAGE_DELAY_MS);
                            return;
                        }
                        int i2 = message.what;
                        int i3 = 0;
                        if (i2 == 1) {
                            AudioPort[] audioPortArr = (AudioPort[]) arrayList2.toArray(new AudioPort[0]);
                            for (i = 0; i < arrayList.size(); i++) {
                                ((AudioManager.OnAudioPortUpdateListener) arrayList.get(i)).onAudioPortListUpdate(audioPortArr);
                            }
                            if (message.what == 1) {
                                return;
                            }
                        } else if (i2 != 2) {
                            if (i2 != 3) {
                                if (i2 != 4) {
                                    return;
                                }
                                AudioPort[] audioPortArr2 = (AudioPort[]) arrayList2.toArray(new AudioPort[0]);
                                while (i < arrayList.size()) {
                                }
                                if (message.what == 1) {
                                }
                            } else {
                                while (i3 < arrayList.size()) {
                                    ((AudioManager.OnAudioPortUpdateListener) arrayList.get(i3)).onServiceDied();
                                    i3++;
                                }
                                return;
                            }
                        }
                        AudioPatch[] audioPatchArr = (AudioPatch[]) arrayList3.toArray(new AudioPatch[0]);
                        while (i3 < arrayList.size()) {
                            ((AudioManager.OnAudioPortUpdateListener) arrayList.get(i3)).onAudioPatchListUpdate(audioPatchArr);
                            i3++;
                        }
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
        Message messageObtainMessage = handler.obtainMessage(i, i2, i3, obj2);
        if (i != 4) {
            handler.removeMessages(i);
        }
        handler.sendMessage(messageObtainMessage);
    }
}
