package android.hardware.motion;

import android.p009os.Handler;
import android.p009os.Looper;
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

  public MotionRecognitionManager(Looper mainLooper) {
    super(mainLooper);
    this.sListeners = new ArrayList<>();
  }

  @Deprecated
  public void registerListenerEvent(MRListener listener, int motion_events) {
    registerListenerEvent(listener, motion_events, null);
  }

  @Deprecated
  public void registerListenerEvent(MRListener listener, int motion_events, Handler handler) {
    registerListenerEvent(listener, 0, motion_events, handler);
  }

  @Deprecated
  public void registerListenerEvent(
      MRListener listener, int motion_sensors, int motion_events, Handler handler) {
    Log.m94d(
        "MotionRecognitionService",
        "deprecated API. use com.samsung.android.gesture.SemMotionRecognitionManager.");
  }

  @Deprecated
  public void unregisterListener(MRListener listener, int motion_events) {
    unregisterListener(listener);
  }

  @Deprecated
  public void unregisterListener(MRListener listener) {
    synchronized (this.sListeners) {
      int size = this.sListeners.size();
      ListenerDelegate l = null;
      int i = 0;
      while (true) {
        if (i >= size) {
          break;
        }
        l = this.sListeners.get(i);
        if (l.moldListener != listener) {
          i++;
        } else {
          Log.m94d("MotionRecognitionManager", "unregisterListener " + i);
          break;
        }
      }
      if (l != null) {
        this.sListeners.remove(l);
        super.unregisterListener(l.mListener);
      }
    }
  }

  @Deprecated
  public void useMotionAlways(MRListener listener, boolean bUseAlways) {}

  @Deprecated
  public void setMotionAngle(MRListener listener, int status) {}

  @Deprecated
  public void setSmartMotionAngle(MRListener listener, int status) {
    synchronized (this.sListeners) {
      int size = this.sListeners.size();
      for (int i = 0; i < size; i++) {
        ListenerDelegate l = this.sListeners.get(i);
        if (l.moldListener == listener) {
          super.setSmartMotionAngle(l.mListener, status);
          return;
        }
      }
    }
  }

  private static class ListenerDelegate {
    private final SemMotionEventListener mListener =
        new SemMotionEventListener() { // from class:
                                       // android.hardware.motion.MotionRecognitionManager.ListenerDelegate.1
          @Override // com.samsung.android.gesture.SemMotionEventListener
          public void onMotionEvent(SemMotionRecognitionEvent motionEvent) {
            MREvent event = new MREvent();
            event.setMotion(motionEvent.getMotion());
            event.setTilt(motionEvent.getTilt());
            event.setPanningDx(motionEvent.getPanningDx());
            event.setPanningDy(motionEvent.getPanningDy());
            event.setPanningDz(motionEvent.getPanningDz());
            event.setPanningDxImage(motionEvent.getPanningDxImage());
            event.setPanningDyImage(motionEvent.getPanningDyImage());
            event.setPanningDzImage(motionEvent.getPanningDzImage());
            ListenerDelegate.this.moldListener.onMotionListener(event);
          }
        };
    private final MRListener moldListener;

    private ListenerDelegate(MRListener listener) {
      this.moldListener = listener;
    }
  }
}
