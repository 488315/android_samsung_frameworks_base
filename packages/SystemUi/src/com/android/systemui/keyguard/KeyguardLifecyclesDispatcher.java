package com.android.systemui.keyguard;

import android.app.IWallpaperManager;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.util.DisplayMetrics;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.util.time.SystemClockImpl;
import java.util.LinkedList;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardLifecyclesDispatcher {
    public final HandlerC14601 mHandler = new Handler() { // from class: com.android.systemui.keyguard.KeyguardLifecyclesDispatcher.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            final int i2 = 1;
            final int i3 = 3;
            if (i != 11) {
                final int i4 = 2;
                if (i != 12) {
                    final int i5 = 4;
                    final int i6 = 0;
                    switch (i) {
                        case 0:
                            Trace.beginSection("KeyguardLifecyclesDispatcher#SCREEN_TURNING_ON");
                            ScreenLifecycle screenLifecycle = KeyguardLifecyclesDispatcher.this.mScreenLifecycle;
                            screenLifecycle.mScreenState = 1;
                            Trace.traceCounter(4096L, "screenState", 1);
                            screenLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    switch (i6) {
                                        case 0:
                                            ((ScreenLifecycle.Observer) obj).onScreenTurningOn();
                                            break;
                                        case 1:
                                            ((ScreenLifecycle.Observer) obj).onScreenInternalTurningOn();
                                            break;
                                        case 2:
                                            ((ScreenLifecycle.Observer) obj).onScreenInternalTurningOff();
                                            break;
                                        case 3:
                                            ((ScreenLifecycle.Observer) obj).onScreenTurnedOn();
                                            break;
                                        case 4:
                                            ((ScreenLifecycle.Observer) obj).onScreenTurningOff();
                                            break;
                                        default:
                                            ((ScreenLifecycle.Observer) obj).onScreenTurnedOff();
                                            break;
                                    }
                                }
                            });
                            Trace.endSection();
                            break;
                        case 1:
                            ScreenLifecycle screenLifecycle2 = KeyguardLifecyclesDispatcher.this.mScreenLifecycle;
                            screenLifecycle2.mScreenState = 2;
                            Trace.traceCounter(4096L, "screenState", 2);
                            screenLifecycle2.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    switch (i3) {
                                        case 0:
                                            ((ScreenLifecycle.Observer) obj).onScreenTurningOn();
                                            break;
                                        case 1:
                                            ((ScreenLifecycle.Observer) obj).onScreenInternalTurningOn();
                                            break;
                                        case 2:
                                            ((ScreenLifecycle.Observer) obj).onScreenInternalTurningOff();
                                            break;
                                        case 3:
                                            ((ScreenLifecycle.Observer) obj).onScreenTurnedOn();
                                            break;
                                        case 4:
                                            ((ScreenLifecycle.Observer) obj).onScreenTurningOff();
                                            break;
                                        default:
                                            ((ScreenLifecycle.Observer) obj).onScreenTurnedOff();
                                            break;
                                    }
                                }
                            });
                            break;
                        case 2:
                            ScreenLifecycle screenLifecycle3 = KeyguardLifecyclesDispatcher.this.mScreenLifecycle;
                            screenLifecycle3.mScreenState = 3;
                            Trace.traceCounter(4096L, "screenState", 3);
                            screenLifecycle3.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    switch (i5) {
                                        case 0:
                                            ((ScreenLifecycle.Observer) obj).onScreenTurningOn();
                                            break;
                                        case 1:
                                            ((ScreenLifecycle.Observer) obj).onScreenInternalTurningOn();
                                            break;
                                        case 2:
                                            ((ScreenLifecycle.Observer) obj).onScreenInternalTurningOff();
                                            break;
                                        case 3:
                                            ((ScreenLifecycle.Observer) obj).onScreenTurnedOn();
                                            break;
                                        case 4:
                                            ((ScreenLifecycle.Observer) obj).onScreenTurningOff();
                                            break;
                                        default:
                                            ((ScreenLifecycle.Observer) obj).onScreenTurnedOff();
                                            break;
                                    }
                                }
                            });
                            break;
                        case 3:
                            ScreenLifecycle screenLifecycle4 = KeyguardLifecyclesDispatcher.this.mScreenLifecycle;
                            screenLifecycle4.mScreenState = 0;
                            Trace.traceCounter(4096L, "screenState", 0);
                            final int i7 = 5;
                            screenLifecycle4.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    switch (i7) {
                                        case 0:
                                            ((ScreenLifecycle.Observer) obj).onScreenTurningOn();
                                            break;
                                        case 1:
                                            ((ScreenLifecycle.Observer) obj).onScreenInternalTurningOn();
                                            break;
                                        case 2:
                                            ((ScreenLifecycle.Observer) obj).onScreenInternalTurningOff();
                                            break;
                                        case 3:
                                            ((ScreenLifecycle.Observer) obj).onScreenTurnedOn();
                                            break;
                                        case 4:
                                            ((ScreenLifecycle.Observer) obj).onScreenTurningOff();
                                            break;
                                        default:
                                            ((ScreenLifecycle.Observer) obj).onScreenTurnedOff();
                                            break;
                                    }
                                }
                            });
                            break;
                        case 4:
                            WakefulnessLifecycle wakefulnessLifecycle = KeyguardLifecyclesDispatcher.this.mWakefulnessLifecycle;
                            int i8 = message.arg1;
                            if (wakefulnessLifecycle.mWakefulness != 1) {
                                wakefulnessLifecycle.mWakefulness = 1;
                                Trace.traceCounter(4096L, "wakefulness", 1);
                                wakefulnessLifecycle.mLastWakeReason = i8;
                                ((SystemClockImpl) wakefulnessLifecycle.mSystemClock).getClass();
                                SystemClock.uptimeMillis();
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
                                wakefulnessLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        switch (i5) {
                                            case 0:
                                                ((WakefulnessLifecycle.Observer) obj).onStartedGoingToSleep();
                                                break;
                                            case 1:
                                                ((WakefulnessLifecycle.Observer) obj).onFinishedGoingToSleep();
                                                break;
                                            case 2:
                                                ((WakefulnessLifecycle.Observer) obj).onFinishedWakingUp();
                                                break;
                                            case 3:
                                                ((WakefulnessLifecycle.Observer) obj).onPostFinishedWakingUp();
                                                break;
                                            default:
                                                ((WakefulnessLifecycle.Observer) obj).onStartedWakingUp();
                                                break;
                                        }
                                    }
                                });
                                break;
                            }
                            break;
                        case 5:
                            WakefulnessLifecycle wakefulnessLifecycle2 = KeyguardLifecyclesDispatcher.this.mWakefulnessLifecycle;
                            if (wakefulnessLifecycle2.mWakefulness != 2) {
                                wakefulnessLifecycle2.mWakefulness = 2;
                                Trace.traceCounter(4096L, "wakefulness", 2);
                                wakefulnessLifecycle2.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        switch (i4) {
                                            case 0:
                                                ((WakefulnessLifecycle.Observer) obj).onStartedGoingToSleep();
                                                break;
                                            case 1:
                                                ((WakefulnessLifecycle.Observer) obj).onFinishedGoingToSleep();
                                                break;
                                            case 2:
                                                ((WakefulnessLifecycle.Observer) obj).onFinishedWakingUp();
                                                break;
                                            case 3:
                                                ((WakefulnessLifecycle.Observer) obj).onPostFinishedWakingUp();
                                                break;
                                            default:
                                                ((WakefulnessLifecycle.Observer) obj).onStartedWakingUp();
                                                break;
                                        }
                                    }
                                });
                                wakefulnessLifecycle2.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        switch (i3) {
                                            case 0:
                                                ((WakefulnessLifecycle.Observer) obj).onStartedGoingToSleep();
                                                break;
                                            case 1:
                                                ((WakefulnessLifecycle.Observer) obj).onFinishedGoingToSleep();
                                                break;
                                            case 2:
                                                ((WakefulnessLifecycle.Observer) obj).onFinishedWakingUp();
                                                break;
                                            case 3:
                                                ((WakefulnessLifecycle.Observer) obj).onPostFinishedWakingUp();
                                                break;
                                            default:
                                                ((WakefulnessLifecycle.Observer) obj).onStartedWakingUp();
                                                break;
                                        }
                                    }
                                });
                                break;
                            }
                            break;
                        case 6:
                            WakefulnessLifecycle wakefulnessLifecycle3 = KeyguardLifecyclesDispatcher.this.mWakefulnessLifecycle;
                            int i9 = message.arg1;
                            if (wakefulnessLifecycle3.mWakefulness != 3) {
                                wakefulnessLifecycle3.mWakefulness = 3;
                                Trace.traceCounter(4096L, "wakefulness", 3);
                                wakefulnessLifecycle3.mLastSleepReason = i9;
                                wakefulnessLifecycle3.mLastSleepOriginLocation = null;
                                if (i9 != 4) {
                                    DisplayMetrics displayMetrics2 = wakefulnessLifecycle3.mDisplayMetrics;
                                    wakefulnessLifecycle3.mLastSleepOriginLocation = new Point(displayMetrics2.widthPixels / 2, displayMetrics2.heightPixels);
                                } else {
                                    wakefulnessLifecycle3.mLastSleepOriginLocation = wakefulnessLifecycle3.getPowerButtonOrigin();
                                }
                                IWallpaperManager iWallpaperManager2 = wakefulnessLifecycle3.mWallpaperManagerService;
                                if (iWallpaperManager2 != null) {
                                    try {
                                        Point point2 = wakefulnessLifecycle3.mLastSleepOriginLocation;
                                        iWallpaperManager2.notifyGoingToSleep(point2.x, point2.y, new Bundle());
                                    } catch (RemoteException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                                wakefulnessLifecycle3.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        switch (i6) {
                                            case 0:
                                                ((WakefulnessLifecycle.Observer) obj).onStartedGoingToSleep();
                                                break;
                                            case 1:
                                                ((WakefulnessLifecycle.Observer) obj).onFinishedGoingToSleep();
                                                break;
                                            case 2:
                                                ((WakefulnessLifecycle.Observer) obj).onFinishedWakingUp();
                                                break;
                                            case 3:
                                                ((WakefulnessLifecycle.Observer) obj).onPostFinishedWakingUp();
                                                break;
                                            default:
                                                ((WakefulnessLifecycle.Observer) obj).onStartedWakingUp();
                                                break;
                                        }
                                    }
                                });
                                break;
                            }
                            break;
                        case 7:
                            WakefulnessLifecycle wakefulnessLifecycle4 = KeyguardLifecyclesDispatcher.this.mWakefulnessLifecycle;
                            if (wakefulnessLifecycle4.mWakefulness != 0) {
                                wakefulnessLifecycle4.mWakefulness = 0;
                                Trace.traceCounter(4096L, "wakefulness", 0);
                                wakefulnessLifecycle4.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        switch (i2) {
                                            case 0:
                                                ((WakefulnessLifecycle.Observer) obj).onStartedGoingToSleep();
                                                break;
                                            case 1:
                                                ((WakefulnessLifecycle.Observer) obj).onFinishedGoingToSleep();
                                                break;
                                            case 2:
                                                ((WakefulnessLifecycle.Observer) obj).onFinishedWakingUp();
                                                break;
                                            case 3:
                                                ((WakefulnessLifecycle.Observer) obj).onPostFinishedWakingUp();
                                                break;
                                            default:
                                                ((WakefulnessLifecycle.Observer) obj).onStartedWakingUp();
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
                    ScreenLifecycle screenLifecycle5 = KeyguardLifecyclesDispatcher.this.mScreenLifecycle;
                    screenLifecycle5.getClass();
                    screenLifecycle5.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            switch (i4) {
                                case 0:
                                    ((ScreenLifecycle.Observer) obj).onScreenTurningOn();
                                    break;
                                case 1:
                                    ((ScreenLifecycle.Observer) obj).onScreenInternalTurningOn();
                                    break;
                                case 2:
                                    ((ScreenLifecycle.Observer) obj).onScreenInternalTurningOff();
                                    break;
                                case 3:
                                    ((ScreenLifecycle.Observer) obj).onScreenTurnedOn();
                                    break;
                                case 4:
                                    ((ScreenLifecycle.Observer) obj).onScreenTurningOff();
                                    break;
                                default:
                                    ((ScreenLifecycle.Observer) obj).onScreenTurnedOff();
                                    break;
                            }
                        }
                    });
                }
            } else {
                ScreenLifecycle screenLifecycle6 = KeyguardLifecyclesDispatcher.this.mScreenLifecycle;
                screenLifecycle6.getClass();
                screenLifecycle6.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i2) {
                            case 0:
                                ((ScreenLifecycle.Observer) obj).onScreenTurningOn();
                                break;
                            case 1:
                                ((ScreenLifecycle.Observer) obj).onScreenInternalTurningOn();
                                break;
                            case 2:
                                ((ScreenLifecycle.Observer) obj).onScreenInternalTurningOff();
                                break;
                            case 3:
                                ((ScreenLifecycle.Observer) obj).onScreenTurnedOn();
                                break;
                            case 4:
                                ((ScreenLifecycle.Observer) obj).onScreenTurningOff();
                                break;
                            default:
                                ((ScreenLifecycle.Observer) obj).onScreenTurnedOff();
                                break;
                        }
                    }
                });
            }
            if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK || LsRune.KEYGUARD_SUB_DISPLAY_LARGE_FRONT) {
                KeyguardLifecyclesDispatcher keyguardLifecyclesDispatcher = KeyguardLifecyclesDispatcher.this;
                if (message.what <= 3) {
                    ScreenLifecycle screenLifecycle7 = keyguardLifecyclesDispatcher.mScreenLifecycle;
                    synchronized (screenLifecycle7.mMsgForLifecycle) {
                        ((LinkedList) screenLifecycle7.mMsgForLifecycle).poll();
                    }
                    return;
                }
                WakefulnessLifecycle wakefulnessLifecycle5 = keyguardLifecyclesDispatcher.mWakefulnessLifecycle;
                synchronized (wakefulnessLifecycle5.mMsgForLifecycle) {
                    ((LinkedList) wakefulnessLifecycle5.mMsgForLifecycle).poll();
                }
            }
        }
    };
    public int mLastWakeReason = -1;
    public final ScreenLifecycle mScreenLifecycle;
    public final WakefulnessLifecycle mWakefulnessLifecycle;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.keyguard.KeyguardLifecyclesDispatcher$1] */
    public KeyguardLifecyclesDispatcher(ScreenLifecycle screenLifecycle, WakefulnessLifecycle wakefulnessLifecycle) {
        this.mScreenLifecycle = screenLifecycle;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
    }

    public final void dispatch(int i) {
        boolean z = LsRune.KEYGUARD_PERFORMANCE_SCREEN_ON;
        HandlerC14601 handlerC14601 = this.mHandler;
        if (!z || !dispatchLowPriorityStartedWakingUp(handlerC14601.obtainMessage(i))) {
            handlerC14601.obtainMessage(i).sendToTarget();
        }
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK || LsRune.KEYGUARD_SUB_DISPLAY_LARGE_FRONT) {
            generateWakefulnessOrScreenStateByLastMsg(i, -1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchLowPriorityStartedWakingUp(Message message) {
        boolean z;
        int i;
        int i2 = message.what;
        HandlerC14601 handlerC14601 = this.mHandler;
        if (i2 != 4 || this.mScreenLifecycle.mScreenState != 0) {
            z = false;
            if (handlerC14601.hasMessages(4, this) && ((i = message.what) == 5 || i == 6 || i == 7)) {
                handlerC14601.removeMessages(4);
                handlerC14601.sendMessage(handlerC14601.obtainMessage(4, this.mLastWakeReason, 0));
                message.sendToTarget();
            }
            if (!z) {
                message.recycle();
            }
            return z;
        }
        message.obj = this;
        this.mLastWakeReason = message.arg1;
        handlerC14601.sendMessageDelayed(message, 100L);
        z = true;
        if (!z) {
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
        HandlerC14601 handlerC14601 = this.mHandler;
        if (!z || !dispatchLowPriorityStartedWakingUp(handlerC14601.obtainMessage(i, i2, 0))) {
            Message obtainMessage = handlerC14601.obtainMessage(i);
            obtainMessage.arg1 = i2;
            obtainMessage.sendToTarget();
        }
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK || LsRune.KEYGUARD_SUB_DISPLAY_LARGE_FRONT) {
            generateWakefulnessOrScreenStateByLastMsg(i, i2);
        }
    }

    public final void dispatch(Object obj) {
        boolean z = LsRune.KEYGUARD_PERFORMANCE_SCREEN_ON;
        HandlerC14601 handlerC14601 = this.mHandler;
        if (!z || !dispatchLowPriorityStartedWakingUp(handlerC14601.obtainMessage(0, obj))) {
            handlerC14601.obtainMessage(0, obj).sendToTarget();
        }
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK || LsRune.KEYGUARD_SUB_DISPLAY_LARGE_FRONT) {
            generateWakefulnessOrScreenStateByLastMsg(0, -1);
        }
    }
}
