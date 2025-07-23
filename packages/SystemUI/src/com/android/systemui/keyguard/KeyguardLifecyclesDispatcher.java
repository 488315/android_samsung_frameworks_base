package com.android.systemui.keyguard;

import android.app.IWallpaperManager;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.TraceNameSupplier;
import android.util.DisplayMetrics;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.util.LinkedList;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class KeyguardLifecyclesDispatcher {
    public final KeyguardLifecycleHandler mHandler;
    public final ScreenLifecycle mScreenLifecycle;
    public final WakefulnessLifecycle mWakefulnessLifecycle;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class KeyguardLifecycleHandler extends Handler {
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
            ScreenLifecycle screenLifecycle = this.mScreenLifecycle;
            WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifecycle;
            if (i != 11 && i != 12) {
                switch (i) {
                    case 0:
                        screenLifecycle.mScreenState = 1;
                        TrackTracer.instantForGroup(1, PluginLock.KEY_SCREEN, "screenState");
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
                                        observer.onScreenTurningOn();
                                        break;
                                    case 2:
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
                        TrackTracer.instantForGroup(2, PluginLock.KEY_SCREEN, "screenState");
                        final int i3 = 0;
                        screenLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ScreenLifecycle.Observer observer = (ScreenLifecycle.Observer) obj;
                                switch (i3) {
                                    case 0:
                                        observer.onScreenTurnedOn();
                                        break;
                                    case 1:
                                        observer.onScreenTurningOn();
                                        break;
                                    case 2:
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
                        TrackTracer.instantForGroup(3, PluginLock.KEY_SCREEN, "screenState");
                        final int i4 = 2;
                        screenLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ScreenLifecycle.Observer observer = (ScreenLifecycle.Observer) obj;
                                switch (i4) {
                                    case 0:
                                        observer.onScreenTurnedOn();
                                        break;
                                    case 1:
                                        observer.onScreenTurningOn();
                                        break;
                                    case 2:
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
                        TrackTracer.instantForGroup(0, PluginLock.KEY_SCREEN, "screenState");
                        final int i5 = 3;
                        screenLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ScreenLifecycle.Observer observer = (ScreenLifecycle.Observer) obj;
                                switch (i5) {
                                    case 0:
                                        observer.onScreenTurnedOn();
                                        break;
                                    case 1:
                                        observer.onScreenTurningOn();
                                        break;
                                    case 2:
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
                        int i6 = message.arg1;
                        if (wakefulnessLifecycle.mWakefulness != 1) {
                            wakefulnessLifecycle.mWakefulness = 1;
                            TrackTracer.instantForGroup(1, PluginLock.KEY_SCREEN, "wakefulness");
                            wakefulnessLifecycle.mLastWakeReason = i6;
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
                            final int i7 = 0;
                            wakefulnessLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    WakefulnessLifecycle.Observer observer = (WakefulnessLifecycle.Observer) obj;
                                    switch (i7) {
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
                            TrackTracer.instantForGroup(2, PluginLock.KEY_SCREEN, "wakefulness");
                            final int i8 = 1;
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
                            final int i9 = 2;
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
                            break;
                        }
                        break;
                    case 6:
                        int i10 = message.arg1;
                        if (wakefulnessLifecycle.mWakefulness != 3) {
                            wakefulnessLifecycle.mWakefulness = 3;
                            TrackTracer.instantForGroup(3, PluginLock.KEY_SCREEN, "wakefulness");
                            wakefulnessLifecycle.mLastSleepReason = i10;
                            wakefulnessLifecycle.mLastSleepOriginLocation = null;
                            if (i10 != 4) {
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
                            final int i11 = 4;
                            wakefulnessLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    WakefulnessLifecycle.Observer observer = (WakefulnessLifecycle.Observer) obj;
                                    switch (i11) {
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
                            TrackTracer.instantForGroup(0, PluginLock.KEY_SCREEN, "wakefulness");
                            final int i12 = 3;
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
                    default:
                        throw new IllegalArgumentException("Unknown message: " + message);
                }
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
        this.mHandler.obtainMessage(i).sendToTarget();
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK || LsRune.KEYGUARD_SUB_DISPLAY_LARGE_FRONT) {
            generateWakefulnessOrScreenStateByLastMsg(i, -1);
        }
    }

    public final void generateWakefulnessOrScreenStateByLastMsg(int i, int i2) {
        if (i <= 3) {
            this.mScreenLifecycle.setLifecycle(i, i2);
        } else {
            this.mWakefulnessLifecycle.setLifecycle(i, i2);
        }
    }
}
