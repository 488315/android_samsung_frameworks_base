package com.android.systemui.keyguard;

import android.app.IWallpaperManager;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.Trace;
import android.os.TraceNameSupplier;
import android.util.DisplayMetrics;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import java.util.LinkedList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardLifecyclesDispatcher {
    public final KeyguardLifecycleHandler mHandler;
    public int mLastWakeReason = -1;
    public final ScreenLifecycle mScreenLifecycle;
    public final WakefulnessLifecycle mWakefulnessLifecycle;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class KeyguardLifecycleHandler extends Handler {
        public final ScreenLifecycle mScreenLifecycle;
        public final WakefulnessLifecycle mWakefulnessLifecycle;

        public KeyguardLifecycleHandler(Looper looper, ScreenLifecycle screenLifecycle, WakefulnessLifecycle wakefulnessLifecycle) {
            super(looper);
            this.mScreenLifecycle = screenLifecycle;
            this.mWakefulnessLifecycle = wakefulnessLifecycle;
        }

        public final String getTraceName(Message message) {
            String str;
            if ((message.getCallback() instanceof TraceNameSupplier) || message.getCallback() != null) {
                return super.getTraceName(message);
            }
            int i = message.what;
            if (i == 11) {
                str = "SCREEN_INTERNAL_TURNING_ON";
            } else if (i != 12) {
                switch (i) {
                    case 0:
                        str = "SCREEN_TURNING_ON";
                        break;
                    case 1:
                        str = "SCREEN_TURNED_ON";
                        break;
                    case 2:
                        str = "SCREEN_TURNING_OFF";
                        break;
                    case 3:
                        str = "SCREEN_TURNED_OFF";
                        break;
                    case 4:
                        str = "STARTED_WAKING_UP";
                        break;
                    case 5:
                        str = "FINISHED_WAKING_UP";
                        break;
                    case 6:
                        str = "STARTED_GOING_TO_SLEEP";
                        break;
                    case 7:
                        str = "FINISHED_GOING_TO_SLEEP";
                        break;
                    default:
                        str = "UNKNOWN";
                        break;
                }
            } else {
                str = "SCREEN_INTERNAL_TURNING_OFF";
            }
            return "KeyguardLifecycleHandler#".concat(str);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifecycle;
            ScreenLifecycle screenLifecycle = this.mScreenLifecycle;
            if (i == 11) {
                screenLifecycle.getClass();
                final int i2 = 1;
                screenLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ScreenLifecycle.Observer observer = (ScreenLifecycle.Observer) obj;
                        switch (i2) {
                            case 0:
                                observer.onScreenTurnedOn();
                                break;
                            case 1:
                                observer.onScreenInternalTurningOn();
                                break;
                            case 2:
                                observer.onScreenInternalTurningOff();
                                break;
                            case 3:
                                observer.onScreenTurningOn();
                                break;
                            case 4:
                                observer.onScreenTurningOff();
                                break;
                            default:
                                observer.onScreenTurnedOff();
                                break;
                        }
                    }
                });
            } else if (i != 12) {
                switch (i) {
                    case 0:
                        screenLifecycle.mScreenState = 1;
                        Trace.traceCounter(4096L, "screenState", 1);
                        final int i3 = 3;
                        screenLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ScreenLifecycle.Observer observer = (ScreenLifecycle.Observer) obj;
                                switch (i3) {
                                    case 0:
                                        observer.onScreenTurnedOn();
                                        break;
                                    case 1:
                                        observer.onScreenInternalTurningOn();
                                        break;
                                    case 2:
                                        observer.onScreenInternalTurningOff();
                                        break;
                                    case 3:
                                        observer.onScreenTurningOn();
                                        break;
                                    case 4:
                                        observer.onScreenTurningOff();
                                        break;
                                    default:
                                        observer.onScreenTurnedOff();
                                        break;
                                }
                            }
                        });
                        break;
                    case 1:
                        screenLifecycle.mScreenState = 2;
                        Trace.traceCounter(4096L, "screenState", 2);
                        final int i4 = 0;
                        screenLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ScreenLifecycle.Observer observer = (ScreenLifecycle.Observer) obj;
                                switch (i4) {
                                    case 0:
                                        observer.onScreenTurnedOn();
                                        break;
                                    case 1:
                                        observer.onScreenInternalTurningOn();
                                        break;
                                    case 2:
                                        observer.onScreenInternalTurningOff();
                                        break;
                                    case 3:
                                        observer.onScreenTurningOn();
                                        break;
                                    case 4:
                                        observer.onScreenTurningOff();
                                        break;
                                    default:
                                        observer.onScreenTurnedOff();
                                        break;
                                }
                            }
                        });
                        break;
                    case 2:
                        screenLifecycle.mScreenState = 3;
                        Trace.traceCounter(4096L, "screenState", 3);
                        final int i5 = 4;
                        screenLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ScreenLifecycle.Observer observer = (ScreenLifecycle.Observer) obj;
                                switch (i5) {
                                    case 0:
                                        observer.onScreenTurnedOn();
                                        break;
                                    case 1:
                                        observer.onScreenInternalTurningOn();
                                        break;
                                    case 2:
                                        observer.onScreenInternalTurningOff();
                                        break;
                                    case 3:
                                        observer.onScreenTurningOn();
                                        break;
                                    case 4:
                                        observer.onScreenTurningOff();
                                        break;
                                    default:
                                        observer.onScreenTurnedOff();
                                        break;
                                }
                            }
                        });
                        break;
                    case 3:
                        screenLifecycle.mScreenState = 0;
                        Trace.traceCounter(4096L, "screenState", 0);
                        final int i6 = 5;
                        screenLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ScreenLifecycle.Observer observer = (ScreenLifecycle.Observer) obj;
                                switch (i6) {
                                    case 0:
                                        observer.onScreenTurnedOn();
                                        break;
                                    case 1:
                                        observer.onScreenInternalTurningOn();
                                        break;
                                    case 2:
                                        observer.onScreenInternalTurningOff();
                                        break;
                                    case 3:
                                        observer.onScreenTurningOn();
                                        break;
                                    case 4:
                                        observer.onScreenTurningOff();
                                        break;
                                    default:
                                        observer.onScreenTurnedOff();
                                        break;
                                }
                            }
                        });
                        break;
                    case 4:
                        int i7 = message.arg1;
                        if (wakefulnessLifecycle.mWakefulness != 1) {
                            wakefulnessLifecycle.mWakefulness = 1;
                            Trace.traceCounter(4096L, "wakefulness", 1);
                            wakefulnessLifecycle.mLastWakeReason = i7;
                            wakefulnessLifecycle.mSystemClock.uptimeMillis();
                            wakefulnessLifecycle.mLastWakeOriginLocation = null;
                            if (wakefulnessLifecycle.mLastWakeReason != 1) {
                                DisplayMetrics displayMetrics = wakefulnessLifecycle.mDisplayMetrics;
                                wakefulnessLifecycle.mLastWakeOriginLocation = new Point(displayMetrics.widthPixels / 2, displayMetrics.heightPixels);
                            } else {
                                wakefulnessLifecycle.mLastWakeOriginLocation = wakefulnessLifecycle.getPowerButtonOrigin();
                            }
                            IWallpaperManager iWallpaperManager = wakefulnessLifecycle.mWallpaperManagerService;
                            if (iWallpaperManager != null) {
                                try {
                                    Point point = wakefulnessLifecycle.mLastWakeOriginLocation;
                                    iWallpaperManager.notifyWakingUp(point.x, point.y, new Bundle());
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                            final int i8 = 0;
                            wakefulnessLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    WakefulnessLifecycle.Observer observer = (WakefulnessLifecycle.Observer) obj;
                                    switch (i8) {
                                        case 0:
                                            observer.onStartedWakingUp();
                                            break;
                                        case 1:
                                            observer.onFinishedWakingUp();
                                            break;
                                        case 2:
                                            observer.onPostFinishedWakingUp();
                                            break;
                                        case 3:
                                            observer.onFinishedGoingToSleep();
                                            break;
                                        default:
                                            observer.onStartedGoingToSleep();
                                            break;
                                    }
                                }
                            });
                            break;
                        }
                        break;
                    case 5:
                        if (wakefulnessLifecycle.mWakefulness != 2) {
                            wakefulnessLifecycle.mWakefulness = 2;
                            Trace.traceCounter(4096L, "wakefulness", 2);
                            final int i9 = 1;
                            wakefulnessLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    WakefulnessLifecycle.Observer observer = (WakefulnessLifecycle.Observer) obj;
                                    switch (i9) {
                                        case 0:
                                            observer.onStartedWakingUp();
                                            break;
                                        case 1:
                                            observer.onFinishedWakingUp();
                                            break;
                                        case 2:
                                            observer.onPostFinishedWakingUp();
                                            break;
                                        case 3:
                                            observer.onFinishedGoingToSleep();
                                            break;
                                        default:
                                            observer.onStartedGoingToSleep();
                                            break;
                                    }
                                }
                            });
                            final int i10 = 2;
                            wakefulnessLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    WakefulnessLifecycle.Observer observer = (WakefulnessLifecycle.Observer) obj;
                                    switch (i10) {
                                        case 0:
                                            observer.onStartedWakingUp();
                                            break;
                                        case 1:
                                            observer.onFinishedWakingUp();
                                            break;
                                        case 2:
                                            observer.onPostFinishedWakingUp();
                                            break;
                                        case 3:
                                            observer.onFinishedGoingToSleep();
                                            break;
                                        default:
                                            observer.onStartedGoingToSleep();
                                            break;
                                    }
                                }
                            });
                            break;
                        }
                        break;
                    case 6:
                        int i11 = message.arg1;
                        if (wakefulnessLifecycle.mWakefulness != 3) {
                            wakefulnessLifecycle.mWakefulness = 3;
                            Trace.traceCounter(4096L, "wakefulness", 3);
                            wakefulnessLifecycle.mLastSleepReason = i11;
                            wakefulnessLifecycle.mLastSleepOriginLocation = null;
                            if (i11 != 4) {
                                DisplayMetrics displayMetrics2 = wakefulnessLifecycle.mDisplayMetrics;
                                wakefulnessLifecycle.mLastSleepOriginLocation = new Point(displayMetrics2.widthPixels / 2, displayMetrics2.heightPixels);
                            } else {
                                wakefulnessLifecycle.mLastSleepOriginLocation = wakefulnessLifecycle.getPowerButtonOrigin();
                            }
                            IWallpaperManager iWallpaperManager2 = wakefulnessLifecycle.mWallpaperManagerService;
                            if (iWallpaperManager2 != null) {
                                try {
                                    Point point2 = wakefulnessLifecycle.mLastSleepOriginLocation;
                                    iWallpaperManager2.notifyGoingToSleep(point2.x, point2.y, new Bundle());
                                } catch (RemoteException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            final int i12 = 4;
                            wakefulnessLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    WakefulnessLifecycle.Observer observer = (WakefulnessLifecycle.Observer) obj;
                                    switch (i12) {
                                        case 0:
                                            observer.onStartedWakingUp();
                                            break;
                                        case 1:
                                            observer.onFinishedWakingUp();
                                            break;
                                        case 2:
                                            observer.onPostFinishedWakingUp();
                                            break;
                                        case 3:
                                            observer.onFinishedGoingToSleep();
                                            break;
                                        default:
                                            observer.onStartedGoingToSleep();
                                            break;
                                    }
                                }
                            });
                            break;
                        }
                        break;
                    case 7:
                        if (wakefulnessLifecycle.mWakefulness != 0) {
                            wakefulnessLifecycle.mWakefulness = 0;
                            Trace.traceCounter(4096L, "wakefulness", 0);
                            final int i13 = 3;
                            wakefulnessLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    WakefulnessLifecycle.Observer observer = (WakefulnessLifecycle.Observer) obj;
                                    switch (i13) {
                                        case 0:
                                            observer.onStartedWakingUp();
                                            break;
                                        case 1:
                                            observer.onFinishedWakingUp();
                                            break;
                                        case 2:
                                            observer.onPostFinishedWakingUp();
                                            break;
                                        case 3:
                                            observer.onFinishedGoingToSleep();
                                            break;
                                        default:
                                            observer.onStartedGoingToSleep();
                                            break;
                                    }
                                }
                            });
                            break;
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown message: " + message);
                }
            } else {
                screenLifecycle.getClass();
                final int i14 = 2;
                screenLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ScreenLifecycle.Observer observer = (ScreenLifecycle.Observer) obj;
                        switch (i14) {
                            case 0:
                                observer.onScreenTurnedOn();
                                break;
                            case 1:
                                observer.onScreenInternalTurningOn();
                                break;
                            case 2:
                                observer.onScreenInternalTurningOff();
                                break;
                            case 3:
                                observer.onScreenTurningOn();
                                break;
                            case 4:
                                observer.onScreenTurningOff();
                                break;
                            default:
                                observer.onScreenTurnedOff();
                                break;
                        }
                    }
                });
            }
            if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK || LsRune.KEYGUARD_SUB_DISPLAY_LARGE_FRONT) {
                if (message.what <= 3) {
                    synchronized (screenLifecycle.mMsgForLifecycle) {
                        ((LinkedList) screenLifecycle.mMsgForLifecycle).poll();
                    }
                } else {
                    synchronized (wakefulnessLifecycle.mMsgForLifecycle) {
                        ((LinkedList) wakefulnessLifecycle.mMsgForLifecycle).poll();
                    }
                }
            }
        }
    }

    public KeyguardLifecyclesDispatcher(Looper looper, ScreenLifecycle screenLifecycle, WakefulnessLifecycle wakefulnessLifecycle) {
        this.mScreenLifecycle = screenLifecycle;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mHandler = new KeyguardLifecycleHandler(looper, screenLifecycle, wakefulnessLifecycle);
    }

    public final void dispatch(int i) {
        boolean z = LsRune.KEYGUARD_PERFORMANCE_SCREEN_ON;
        KeyguardLifecycleHandler keyguardLifecycleHandler = this.mHandler;
        if (!z || !dispatchLowPriorityStartedWakingUp(keyguardLifecycleHandler.obtainMessage(i))) {
            keyguardLifecycleHandler.obtainMessage(i).sendToTarget();
        }
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK || LsRune.KEYGUARD_SUB_DISPLAY_LARGE_FRONT) {
            generateWakefulnessOrScreenStateByLastMsg(i, -1);
        }
    }

    public final boolean dispatchLowPriorityStartedWakingUp(Message message) {
        int i;
        int i2 = message.what;
        boolean z = true;
        KeyguardLifecycleHandler keyguardLifecycleHandler = this.mHandler;
        if (i2 == 4 && this.mScreenLifecycle.mScreenState == 0) {
            message.obj = this;
            this.mLastWakeReason = message.arg1;
            keyguardLifecycleHandler.sendMessageDelayed(message, 100L);
        } else if (keyguardLifecycleHandler.hasMessages(4, this) && ((i = message.what) == 5 || i == 6 || i == 7)) {
            keyguardLifecycleHandler.removeMessages(4);
            keyguardLifecycleHandler.sendMessage(keyguardLifecycleHandler.obtainMessage(4, this.mLastWakeReason, 0));
            message.sendToTarget();
        } else {
            z = false;
        }
        if (!z) {
            message.recycle();
        }
        return z;
    }

    public final void generateWakefulnessOrScreenStateByLastMsg(int i, int i2) {
        if (i <= 3) {
            this.mScreenLifecycle.setLifecycle(i, i2);
        } else {
            this.mWakefulnessLifecycle.setLifecycle(i, i2);
        }
    }

    public final void dispatch(int i, int i2) {
        boolean z = LsRune.KEYGUARD_PERFORMANCE_SCREEN_ON;
        KeyguardLifecycleHandler keyguardLifecycleHandler = this.mHandler;
        if (!z || !dispatchLowPriorityStartedWakingUp(keyguardLifecycleHandler.obtainMessage(i, i2, 0))) {
            Message obtainMessage = keyguardLifecycleHandler.obtainMessage(i);
            obtainMessage.arg1 = i2;
            obtainMessage.sendToTarget();
        }
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK || LsRune.KEYGUARD_SUB_DISPLAY_LARGE_FRONT) {
            generateWakefulnessOrScreenStateByLastMsg(i, i2);
        }
    }

    public final void dispatch(Object obj) {
        boolean z = LsRune.KEYGUARD_PERFORMANCE_SCREEN_ON;
        KeyguardLifecycleHandler keyguardLifecycleHandler = this.mHandler;
        if (!z || !dispatchLowPriorityStartedWakingUp(keyguardLifecycleHandler.obtainMessage(0, obj))) {
            keyguardLifecycleHandler.obtainMessage(0, obj).sendToTarget();
        }
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK || LsRune.KEYGUARD_SUB_DISPLAY_LARGE_FRONT) {
            generateWakefulnessOrScreenStateByLastMsg(0, -1);
        }
    }
}
