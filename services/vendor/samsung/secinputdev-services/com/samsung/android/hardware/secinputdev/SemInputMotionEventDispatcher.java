package com.samsung.android.hardware.secinputdev;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.util.PrintWriterPrinter;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.MotionEvent;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes.dex */
public class SemInputMotionEventDispatcher {
    private static final String TAG = "SemInputMotionEventDispatcher";
    private static volatile SemInputMotionEventDispatcher uniqueInstance = null;
    private final Context context;
    private final ArrayList<SemInputMotionEventListener> listeners = new ArrayList<>();
    private final SemInputDumpsysData cancelDumpsys = new SemInputDumpsysData(20);
    private final Lock registerLock = new ReentrantLock();
    private InputMonitor inputMonitor = null;
    private InputEventReceiver inputEventReceiver = null;
    private boolean isRegistered = false;
    private final HandlerThread inputHandlerThread = new HandlerThread(TAG, -2);

    public interface SemInputMotionEventListener {
        void onMotionEvent(MotionEvent motionEvent);
    }

    private SemInputMotionEventDispatcher(Context context) {
        this.context = context;
        this.inputHandlerThread.start();
    }

    public static SemInputMotionEventDispatcher getInstance(Context context) {
        if (uniqueInstance == null) {
            synchronized (SemInputMotionEventDispatcher.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new SemInputMotionEventDispatcher(context);
                }
            }
        }
        return uniqueInstance;
    }

    public static boolean isCreated() {
        if (uniqueInstance == null) {
            return false;
        }
        return true;
    }

    private boolean checkSecurityPermission(String caller) {
        if (Process.myUid() == 1000) {
            return caller.contains("secinputdev");
        }
        Log.e(TAG, "SemInputMotionEventDispatcher only available from system UID.");
        return false;
    }

    public boolean registerMotionEventListener(SemInputMotionEventListener listener) {
        if (listener == null) {
            return false;
        }
        if (!checkSecurityPermission(listener.toString())) {
            throw new SecurityException("Only SemInput service can use it");
        }
        this.registerLock.lock();
        try {
            synchronized (this.listeners) {
                if (this.listeners.contains(listener)) {
                    Log.d(
                            TAG,
                            "registerMotionEventListener: already registered: "
                                    + listener.toString());
                    return false;
                }
                this.listeners.add(listener);
                int sizeOfListeners = this.listeners.size();
                if (sizeOfListeners == 1 && this.inputMonitor == null) {
                    registerInputEventReceiver();
                }
                this.isRegistered = true;
                this.registerLock.unlock();
                Log.d(TAG, "registerMotionEventListener: " + listener.toString());
                return true;
            }
        } finally {
            this.registerLock.unlock();
        }
    }

    public boolean unregisterMotionEventListener(SemInputMotionEventListener listener) {
        if (listener == null) {
            return false;
        }
        if (!checkSecurityPermission(listener.toString())) {
            throw new SecurityException("Only SemInput service can use it");
        }
        this.registerLock.lock();
        try {
            synchronized (this.listeners) {
                if (!this.listeners.contains(listener)) {
                    return false;
                }
                this.listeners.remove(listener);
                int sizeOfListeners = this.listeners.size();
                Log.d(TAG, "unregisterMotionEventListener: " + listener.toString());
                if (sizeOfListeners == 0) {
                    this.isRegistered = false;
                    unregisterInputEventReceiver();
                }
                this.registerLock.unlock();
                return true;
            }
        } finally {
            this.registerLock.unlock();
        }
    }

    public void pilferPointers(SemInputMotionEventListener listener) {
        if (listener != null) {
            if (!checkSecurityPermission(listener.toString())) {
                throw new SecurityException("Only SemInput service can use it");
            }
            synchronized (this.listeners) {
                if (this.listeners.contains(listener) && this.inputMonitor != null) {
                    this.inputMonitor.pilferPointers();
                    Log.i(TAG, "cancel");
                    this.cancelDumpsys.createDataAndAddQueue(listener.toString());
                }
            }
        }
    }

    private void registerInputEventReceiver() {
        if (this.context == null) {
            Log.w(TAG, "registerInputEventReceiver: context is null");
            return;
        }
        int displayId = this.context.getDisplayId();
        try {
            this.inputMonitor =
                    InputManager.getInstance().monitorGestureInput("secinputdev", displayId);
            if (this.inputMonitor == null) {
                return;
            }
            this.inputEventReceiver =
                    new MyInputEventReceiver(
                            this.inputMonitor.getInputChannel(),
                            this.inputHandlerThread.getLooper());
            Log.d(TAG, "registerInputEventReceiver: displayId: " + displayId);
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "registerInputEventReceiver", e);
            throw e;
        }
    }

    private void unregisterInputEventReceiver() {
        if (this.inputEventReceiver != null) {
            this.inputEventReceiver.dispose();
            this.inputEventReceiver = null;
            Log.i(TAG, "unregisterInputEventReceiver: dispose InputEventReceiver");
        }
        if (this.inputMonitor != null) {
            this.inputMonitor.dispose();
            this.inputMonitor = null;
            Log.i(TAG, "unregisterInputEventReceiver: dispose InputMonitor");
        }
    }

    private final class MyInputEventReceiver extends InputEventReceiver {
        public MyInputEventReceiver(InputChannel channel, Looper looper) {
            super(channel, looper);
        }

        public void onInputEvent(InputEvent event) {
            if (!SemInputMotionEventDispatcher.this.isRegistered) {
                finishInputEvent(event, true);
                return;
            }
            if (event instanceof MotionEvent) {
                MotionEvent motionEvent = (MotionEvent) event;
                synchronized (SemInputMotionEventDispatcher.this.listeners) {
                    Iterator it = SemInputMotionEventDispatcher.this.listeners.iterator();
                    while (it.hasNext()) {
                        SemInputMotionEventListener listener =
                                (SemInputMotionEventListener) it.next();
                        listener.onMotionEvent(motionEvent);
                    }
                }
            }
            finishInputEvent(event, true);
        }
    }

    public void dump(PrintWriter pw) {
        pw.println("dumping SemInputMotionEventDispatcher");
        this.inputHandlerThread.getLooper().dump(new PrintWriterPrinter(pw), "- ");
        ArrayList<SemInputMotionEventListener> copiedListeners = new ArrayList<>();
        synchronized (this.listeners) {
            copiedListeners.addAll(this.listeners);
        }
        if (copiedListeners.size() <= 0) {
            return;
        }
        pw.println("- registered listeners");
        Iterator<SemInputMotionEventListener> it = copiedListeners.iterator();
        while (it.hasNext()) {
            SemInputMotionEventListener listener = it.next();
            pw.println("  " + listener.toString());
        }
    }

    public void dumpEvents(PrintWriter pw) {
        Queue<String> queue = this.cancelDumpsys.getQueue();
        if (queue.size() != 0) {
            pw.println("- canceled MotionEvent: max " + this.cancelDumpsys.getMaxQueueSize());
            for (String data : queue) {
                pw.println("  " + data);
            }
            pw.println("  end canceled");
        }
    }
}
