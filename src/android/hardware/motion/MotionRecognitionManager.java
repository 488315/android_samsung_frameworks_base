package android.hardware.motion;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.samsung.android.gesture.SemMotionEventListener;
import com.samsung.android.gesture.SemMotionRecognitionEvent;
import com.samsung.android.gesture.SemMotionRecognitionManager;
import java.util.ArrayList;

@Deprecated
/* loaded from: classes2.dex */
public class MotionRecognitionManager extends SemMotionRecognitionManager {
    public static final int EVENT_CALL_POSE = 262144;
    public static final int EVENT_DIRECT_CALLING = 1024;
    public static final int EVENT_FLAT = 8192;
    public static final int EVENT_LCD_UP_STEADY = 65536;
    public static final int EVENT_OVER_TURN = 1;
    public static final int EVENT_REACTIVE_ALERT = 4;
    public static final int EVENT_SMART_RELAY = 1048576;
    private final ArrayList<ListenerDelegate> sListeners;

    @Deprecated
    public void setMotionAngle(MRListener mRListener, int i) {
    }

    @Deprecated
    public void useMotionAlways(MRListener mRListener, boolean z) {
    }

    public MotionRecognitionManager(Looper looper) {
        super(looper);
        this.sListeners = new ArrayList<>();
    }

    @Deprecated
    public void registerListenerEvent(MRListener mRListener, int i) {
        registerListenerEvent(mRListener, i, null);
    }

    @Deprecated
    public void registerListenerEvent(MRListener mRListener, int i, Handler handler) {
        registerListenerEvent(mRListener, 0, i, handler);
    }

    @Deprecated
    public void registerListenerEvent(MRListener mRListener, int i, int i2, Handler handler) {
        Log.d("MotionRecognitionService", "deprecated API. use com.samsung.android.gesture.SemMotionRecognitionManager.");
    }

    @Deprecated
    public void unregisterListener(MRListener mRListener, int i) {
        unregisterListener(mRListener);
    }

    @Deprecated
    public void unregisterListener(MRListener mRListener) {
        synchronized (this.sListeners) {
            int size = this.sListeners.size();
            ListenerDelegate listenerDelegate = null;
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                listenerDelegate = this.sListeners.get(i);
                if (listenerDelegate.moldListener == mRListener) {
                    Log.d("MotionRecognitionManager", "unregisterListener " + i);
                    break;
                }
                i++;
            }
            if (listenerDelegate != null) {
                this.sListeners.remove(listenerDelegate);
                super.unregisterListener(listenerDelegate.mListener);
            }
        }
    }

    @Deprecated
    public void setSmartMotionAngle(MRListener mRListener, int i) {
        synchronized (this.sListeners) {
            int size = this.sListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                ListenerDelegate listenerDelegate = this.sListeners.get(i2);
                if (listenerDelegate.moldListener == mRListener) {
                    super.setSmartMotionAngle(listenerDelegate.mListener, i);
                    return;
                }
            }
        }
    }

    private static class ListenerDelegate {
        private final SemMotionEventListener mListener = new SemMotionEventListener() { // from class: android.hardware.motion.MotionRecognitionManager.ListenerDelegate.1
            @Override // com.samsung.android.gesture.SemMotionEventListener
            public void onMotionEvent(SemMotionRecognitionEvent semMotionRecognitionEvent) {
                MREvent mREvent = new MREvent();
                mREvent.setMotion(semMotionRecognitionEvent.getMotion());
                mREvent.setTilt(semMotionRecognitionEvent.getTilt());
                mREvent.setPanningDx(semMotionRecognitionEvent.getPanningDx());
                mREvent.setPanningDy(semMotionRecognitionEvent.getPanningDy());
                mREvent.setPanningDz(semMotionRecognitionEvent.getPanningDz());
                mREvent.setPanningDxImage(semMotionRecognitionEvent.getPanningDxImage());
                mREvent.setPanningDyImage(semMotionRecognitionEvent.getPanningDyImage());
                mREvent.setPanningDzImage(semMotionRecognitionEvent.getPanningDzImage());
                ListenerDelegate.this.moldListener.onMotionListener(mREvent);
            }
        };
        private final MRListener moldListener;

        private ListenerDelegate(MRListener mRListener) {
            this.moldListener = mRListener;
        }
    }
}
