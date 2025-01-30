package com.android.server.enterprise.plm;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.android.server.enterprise.plm.impl.KeepAliveImpl;

/* loaded from: classes2.dex */
public class ProcessAdapter extends Handler {
    public static final String TAG = ProcessAdapter.class.getSimpleName();
    public final Context mContext;
    public final KeepAliveImpl mKeepAliveImpl;
    public IStateDelegate mStateDelegate;

    enum TryReason {
        START,
        STOP,
        ALIVE,
        DEATH,
        UPDATE
    }

    public ProcessAdapter(Looper looper, Context context, KeepAliveImpl keepAliveImpl) {
        super(looper);
        this.mContext = context;
        this.mKeepAliveImpl = keepAliveImpl;
    }

    public void setDelegate(IStateDelegate iStateDelegate) {
        this.mStateDelegate = iStateDelegate;
    }

    public void start() {
        sendEmptyMessage(1);
    }

    public void stop() {
        sendEmptyMessage(2);
    }

    public void update() {
        if (hasMessages(5)) {
            return;
        }
        sendEmptyMessage(5);
    }

    public String getPackageName() {
        return this.mKeepAliveImpl.getPackageName();
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        String str = TAG;
        Log.d(str, "handleMessage : " + message.what);
        try {
            int i = message.what;
            if (i == 1) {
                onAdapterStart(message);
            } else if (i == 2) {
                onAdapterStop(message);
            } else if (i == 3) {
                onProcessAlive(message);
            } else if (i == 4) {
                onProcessDeath(message);
            } else if (i == 5) {
                onStatusUpdate(message);
            } else {
                Log.e(str, "Invalid message " + message.what);
            }
        } catch (Throwable th) {
            Log.e(TAG, th.toString());
        }
    }

    public final void onAdapterStart(Message message) {
        tryKeepAlive(TryReason.START);
    }

    public final void onAdapterStop(Message message) {
        tryKeepAlive(TryReason.STOP);
    }

    public final void onProcessAlive(Message message) {
        tryKeepAlive(TryReason.ALIVE);
    }

    public final void onProcessDeath(Message message) {
        tryKeepAlive(TryReason.DEATH);
    }

    public final void onStatusUpdate(Message message) {
        tryKeepAlive(TryReason.UPDATE);
    }

    public final void tryKeepAlive(TryReason tryReason) {
        String str = TAG;
        Log.d(str, "tryKeepAlive(" + tryReason + ", " + this.mKeepAliveImpl.getDisplayName() + ")");
        int i = AbstractC14531.f1695x12f16fa8[tryReason.ordinal()];
        if (i == 1) {
            this.mKeepAliveImpl.registerObserver(this, 3, 4);
        } else if (i == 2) {
            this.mKeepAliveImpl.unregisterObserver(this);
            removeCallbacksAndMessages(null);
        } else if (i == 3) {
            this.mKeepAliveImpl.changeAdjLevel(true);
        }
        boolean z = tryReason != TryReason.STOP && this.mKeepAliveImpl.needToKeepAlive(this.mStateDelegate);
        Log.d(str, "needToKeepAlive : " + z);
        if (z) {
            if (this.mKeepAliveImpl.startProcess()) {
                return;
            }
            Log.e(str, "failed to start keep alive");
        } else {
            if (this.mKeepAliveImpl.stopProcess()) {
                return;
            }
            Log.e(str, "failed to stop keep alive");
        }
    }

    /* renamed from: com.android.server.enterprise.plm.ProcessAdapter$1 */
    public abstract /* synthetic */ class AbstractC14531 {

        /* renamed from: $SwitchMap$com$android$server$enterprise$plm$ProcessAdapter$TryReason */
        public static final /* synthetic */ int[] f1695x12f16fa8;

        static {
            int[] iArr = new int[TryReason.values().length];
            f1695x12f16fa8 = iArr;
            try {
                iArr[TryReason.START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1695x12f16fa8[TryReason.STOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1695x12f16fa8[TryReason.ALIVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }
}
