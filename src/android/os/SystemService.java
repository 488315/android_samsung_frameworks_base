package android.os;

import com.google.android.collect.Maps;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/* loaded from: classes3.dex */
public class SystemService {
    private static HashMap<String, State> sStates = Maps.newHashMap();
    private static Object sPropertyLock = new Object();

    static {
        SystemProperties.addChangeCallback(new Runnable() { // from class: android.os.SystemService.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (SystemService.sPropertyLock) {
                    SystemService.sPropertyLock.notifyAll();
                }
            }
        });
    }

    public enum State {
        RUNNING("running"),
        STOPPING("stopping"),
        STOPPED("stopped"),
        RESTARTING("restarting");

        State(String str) {
            SystemService.sStates.put(str, this);
        }
    }

    public static void start(String str) {
        SystemProperties.set("ctl.start", str);
    }

    public static void stop(String str) {
        SystemProperties.set("ctl.stop", str);
    }

    public static void restart(String str) {
        SystemProperties.set("ctl.restart", str);
    }

    public static State getState(String str) {
        State state = sStates.get(SystemProperties.get("init.svc." + str));
        return state != null ? state : State.STOPPED;
    }

    public static boolean isStopped(String str) {
        return State.STOPPED.equals(getState(str));
    }

    public static boolean isRunning(String str) {
        return State.RUNNING.equals(getState(str));
    }

    public static void waitForState(String str, State state, long j) throws TimeoutException {
        long jElapsedRealtime = SystemClock.elapsedRealtime() + j;
        while (true) {
            synchronized (sPropertyLock) {
                State state2 = getState(str);
                if (state.equals(state2)) {
                    return;
                }
                if (SystemClock.elapsedRealtime() >= jElapsedRealtime) {
                    throw new TimeoutException("Service " + str + " currently " + state2 + "; waited " + j + "ms for " + state);
                }
                try {
                    sPropertyLock.wait(j);
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    public static void waitForAnyStopped(String... strArr) {
        while (true) {
            synchronized (sPropertyLock) {
                for (String str : strArr) {
                    if (State.STOPPED.equals(getState(str))) {
                        return;
                    }
                }
                try {
                    sPropertyLock.wait();
                } catch (InterruptedException unused) {
                }
            }
        }
    }
}
