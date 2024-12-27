package com.android.server.power;

import com.android.internal.util.RingBuffer;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SuspendBlockerMonitor {
    public String mDetail;
    public int mEvents;
    public static final RingBuffer mSuspendBlockerMonitorCallbacks =
            new RingBuffer(CallbackHistory.class, 200);
    public static final SuspendBlockerMonitor GLOBAL_INSTANCE = new SuspendBlockerMonitor();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CallbackHistory {
        public String mCmd;
        public String mDetail;
        public String mTimeStr;

        public final String toString() {
            return String.format("[%s] [%25s] [%s]", this.mTimeStr, this.mDetail, this.mCmd);
        }
    }

    public static void addNewCallbackEvent(int i, String str) {
        RingBuffer ringBuffer = mSuspendBlockerMonitorCallbacks;
        String currentTimeAsString = PowerManagerUtil.getCurrentTimeAsString();
        CallbackHistory callbackHistory = new CallbackHistory();
        callbackHistory.mTimeStr = currentTimeAsString;
        callbackHistory.mDetail = str;
        if (i == 1) {
            callbackHistory.mCmd = "ACQ";
        } else if (i == 2) {
            callbackHistory.mCmd = "REL";
        }
        ringBuffer.append(callbackHistory);
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("SuspendBlockerMonitor:");
        printWriter.println(toString());
        printWriter.println();
    }

    public final void setEvent(int i) {
        this.mEvents = i | this.mEvents;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        if ((this.mEvents & 15) != 0) {
            sb.append("    [Global]\n");
            sb.append("      mEvents = 0x" + Integer.toHexString(this.mEvents));
            sb.append(" ( ");
            if ((this.mEvents & 1) != 0) {
                sb.append("READY ");
            }
            if ((this.mEvents & 2) != 0) {
                sb.append("PROGRESS ");
            }
            if ((this.mEvents & 4) != 0) {
                sb.append("DOZING ");
            }
            if ((this.mEvents & 8) != 0) {
                sb.append("SOME_GROUPS ");
            }
            sb.append(")");
        }
        if ((this.mEvents & 240) != 0) {
            sb.append("\n");
            sb.append("      mEvents = 0x" + Integer.toHexString(this.mEvents));
            sb.append(" ( ");
            if ((this.mEvents & 16) != 0) {
                sb.append("BRIGHT_OR_DIM ");
            }
            if ((this.mEvents & 32) != 0) {
                sb.append("DOZE_ON_STATE ");
            }
            if ((this.mEvents & 64) != 0) {
                sb.append("CHARGING_UI ");
            }
            if ((this.mEvents & 128) != 0) {
                sb.append("BUILT_IN_DISPLAY_STATE_ON ");
            }
            sb.append(")\n");
        }
        if ((this.mEvents & 256) != 0) {
            sb.append("    [Callback]\n");
            sb.append(this.mDetail);
        }
        if (this == GLOBAL_INSTANCE) {
            StringBuilder sb2 = new StringBuilder("\n    CallBack History = ");
            RingBuffer ringBuffer = mSuspendBlockerMonitorCallbacks;
            sb2.append(ringBuffer.size());
            sb2.append("\n");
            sb.append(sb2.toString());
            for (CallbackHistory callbackHistory : (CallbackHistory[]) ringBuffer.toArray()) {
                sb.append(callbackHistory.toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
