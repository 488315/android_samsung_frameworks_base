package com.android.server.enterprise.plm;

import android.content.Context;
import android.os.HandlerThread;
import android.os.Looper;
import com.android.server.enterprise.plm.context.KnoxZtContext;
import com.android.server.enterprise.plm.context.PeripheralContext;
import com.android.server.enterprise.plm.impl.BindServiceImpl;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProcessLifecycleManager {
    public static volatile ProcessLifecycleManager sInstance;
    public final ProcessStateTracker mStateTracker;

    public ProcessLifecycleManager(Context context) {
        HandlerThread handlerThread = new HandlerThread("ProcessLifecycleManager");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ProcessAdapter(looper, new BindServiceImpl(context, new PeripheralContext())));
        arrayList.add(new ProcessAdapter(looper, new BindServiceImpl(context, new KnoxZtContext())));
        this.mStateTracker = new ProcessStateTracker(looper, context, arrayList);
    }

    public static ProcessLifecycleManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (ProcessLifecycleManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new ProcessLifecycleManager(context);
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }
}
