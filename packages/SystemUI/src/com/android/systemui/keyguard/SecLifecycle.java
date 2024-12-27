package com.android.systemui.keyguard;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Rune;
import com.android.systemui.uithreadmonitor.LooperSlowLogController;
import com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl;
import com.android.systemui.util.LogUtil;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;

public class SecLifecycle {
    public LooperSlowLogController mLooperSlowLogController;
    public final ArrayList mObservers = new ArrayList();
    public boolean mIsDispatching = false;
    public int mLastScreenState = -1;
    public int mLastWakefulness = -1;
    public final int QUEUE_MAX = 8;
    public final Queue mMsgForLifecycle = new LinkedList();

    public final class Msg {
        public final int msg;
        public final int reason;

        public /* synthetic */ Msg(int i, int i2, int i3) {
            this(i, i2);
        }

        private Msg(int i, int i2) {
            this.msg = i;
            this.reason = i2;
        }
    }

    public static Msg createMsg(int i, int i2) {
        return new Msg(i, i2, 0);
    }

    public final void addObserver(Object obj) {
        if (this.mIsDispatching) {
            throw new IllegalStateException("do not add or remove a observer on dispatching: " + obj);
        }
        ArrayList arrayList = this.mObservers;
        Objects.requireNonNull(obj);
        if (arrayList.contains(obj)) {
            return;
        }
        this.mObservers.add(obj);
    }

    public final void dispatch(Consumer consumer) {
        String str;
        this.mIsDispatching = true;
        int screenState = getScreenState();
        int wakefulness = getWakefulness();
        if (screenState == -1 || this.mLastScreenState == screenState) {
            str = "";
        } else {
            str = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(screenState, "screenState=");
            this.mLastScreenState = screenState;
        }
        if (wakefulness != -1 && this.mLastWakefulness != wakefulness) {
            str = str + "wakefulness=" + wakefulness;
            this.mLastWakefulness = wakefulness;
        }
        if (!TextUtils.isEmpty(str)) {
            Log.d("SecLifecycle", str);
        }
        if (Rune.SYSUI_UI_THREAD_MONITOR) {
            for (int i = 0; i < this.mObservers.size(); i++) {
                if (this.mLooperSlowLogController == null) {
                    this.mLooperSlowLogController = (LooperSlowLogController) Dependency.sDependency.getDependencyInner(LooperSlowLogController.class);
                }
                LooperSlowLogController looperSlowLogController = this.mLooperSlowLogController;
                int startTime = (looperSlowLogController == null || !((LooperSlowLogControllerImpl) looperSlowLogController).isEnabled()) ? -1 : LogUtil.startTime(-1);
                Object obj = this.mObservers.get(i);
                consumer.accept(obj);
                if (startTime >= 0) {
                    LogUtil.endTime(startTime, 20, "LooperSlow", "dispatch " + obj, new Object[0]);
                }
            }
        } else {
            for (int i2 = 0; i2 < this.mObservers.size(); i2++) {
                consumer.accept(this.mObservers.get(i2));
            }
        }
        this.mIsDispatching = false;
    }

    public int getScreenState() {
        return -1;
    }

    public int getWakefulness() {
        return -1;
    }

    public final void removeObserver(Object obj) {
        if (!this.mIsDispatching) {
            this.mObservers.remove(obj);
        } else {
            throw new IllegalStateException("do not add or remove a observer on dispatching: " + obj);
        }
    }

    public final void setLifecycle(int i, int i2) {
        Msg createMsg;
        synchronized (this.mMsgForLifecycle) {
            try {
                if (((LinkedList) this.mMsgForLifecycle).size() >= this.QUEUE_MAX) {
                    Log.d("SecLifecycle", "Saved message is over the max");
                    ((LinkedList) this.mMsgForLifecycle).remove();
                }
                switch (i) {
                    case 0:
                        createMsg = createMsg(1, i2);
                        break;
                    case 1:
                        createMsg = createMsg(2, i2);
                        break;
                    case 2:
                        createMsg = createMsg(3, i2);
                        break;
                    case 3:
                        createMsg = createMsg(0, i2);
                        break;
                    case 4:
                        createMsg = createMsg(1, i2);
                        break;
                    case 5:
                        createMsg = createMsg(2, i2);
                        break;
                    case 6:
                        createMsg = createMsg(3, i2);
                        break;
                    case 7:
                        createMsg = createMsg(0, i2);
                        break;
                    default:
                        createMsg = null;
                        break;
                }
                if (createMsg != null) {
                    ((LinkedList) this.mMsgForLifecycle).offer(createMsg);
                }
            } finally {
            }
        }
    }
}
