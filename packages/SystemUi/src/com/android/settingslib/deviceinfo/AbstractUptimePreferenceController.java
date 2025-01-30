package com.android.settingslib.deviceinfo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.format.DateUtils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import java.lang.ref.WeakReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class AbstractUptimePreferenceController extends AbstractPreferenceController implements LifecycleObserver, OnStart, OnStop {
    static final String KEY_UPTIME = "up_time";
    public MyHandler mHandler;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MyHandler extends Handler {
        public final WeakReference mStatus;

        public MyHandler(AbstractUptimePreferenceController abstractUptimePreferenceController) {
            this.mStatus = new WeakReference(abstractUptimePreferenceController);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (((AbstractUptimePreferenceController) this.mStatus.get()) == null) {
                return;
            }
            if (message.what == 500) {
                DateUtils.formatElapsedTime(SystemClock.elapsedRealtime() / 1000);
                throw null;
            }
            throw new IllegalStateException("Unknown message " + message.what);
        }
    }

    public AbstractUptimePreferenceController(Context context, Lifecycle lifecycle) {
        super(context);
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        if (this.mHandler == null) {
            this.mHandler = new MyHandler(this);
        }
        this.mHandler.sendEmptyMessage(500);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        if (this.mHandler == null) {
            this.mHandler = new MyHandler(this);
        }
        this.mHandler.removeMessages(500);
    }
}
