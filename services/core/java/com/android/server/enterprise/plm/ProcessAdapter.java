package com.android.server.enterprise.plm;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.enterprise.plm.impl.BindServiceImpl;

public final class ProcessAdapter extends Handler {
    public final BindServiceImpl mKeepAliveImpl;
    public IStateDelegate mStateDelegate;

    final class TryReason {
        public static final /* synthetic */ TryReason[] $VALUES;
        public static final TryReason ALIVE;
        public static final TryReason DEATH;
        public static final TryReason START;
        public static final TryReason STOP;
        public static final TryReason UPDATE;

        static {
            TryReason tryReason = new TryReason("START", 0);
            START = tryReason;
            TryReason tryReason2 = new TryReason("STOP", 1);
            STOP = tryReason2;
            TryReason tryReason3 = new TryReason("ALIVE", 2);
            ALIVE = tryReason3;
            TryReason tryReason4 = new TryReason("DEATH", 3);
            DEATH = tryReason4;
            TryReason tryReason5 = new TryReason("UPDATE", 4);
            UPDATE = tryReason5;
            $VALUES = new TryReason[] {tryReason, tryReason2, tryReason3, tryReason4, tryReason5};
        }

        public static TryReason valueOf(String str) {
            return (TryReason) Enum.valueOf(TryReason.class, str);
        }

        public static TryReason[] values() {
            return (TryReason[]) $VALUES.clone();
        }
    }

    public ProcessAdapter(Looper looper, BindServiceImpl bindServiceImpl) {
        super(looper);
        this.mKeepAliveImpl = bindServiceImpl;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        GestureWakeup$$ExternalSyntheticOutline0.m(
                new StringBuilder("handleMessage : "), message.what, "ProcessAdapter");
        try {
            int i = message.what;
            if (i == 1) {
                tryKeepAlive(TryReason.START);
            } else if (i == 2) {
                tryKeepAlive(TryReason.STOP);
            } else if (i == 3) {
                tryKeepAlive(TryReason.ALIVE);
            } else if (i == 4) {
                tryKeepAlive(TryReason.DEATH);
            } else if (i != 5) {
                Log.e("ProcessAdapter", "Invalid message " + message.what);
            } else {
                tryKeepAlive(TryReason.UPDATE);
            }
        } catch (Throwable th) {
            Log.e("ProcessAdapter", th.toString());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:88:0x0152, code lost:

       r11 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0153, code lost:

       android.util.Log.e("Utils", r11.toString());
       r14 = 0;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void tryKeepAlive(com.android.server.enterprise.plm.ProcessAdapter.TryReason r14) {
        /*
            Method dump skipped, instructions count: 448
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.plm.ProcessAdapter.tryKeepAlive(com.android.server.enterprise.plm.ProcessAdapter$TryReason):void");
    }
}
