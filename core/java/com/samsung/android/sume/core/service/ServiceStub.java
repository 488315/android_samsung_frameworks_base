package com.samsung.android.sume.core.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.controller.MediaController;
import com.samsung.android.sume.core.controller.MediaFilterController;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.graph.Graph;
import com.samsung.android.sume.core.graph.MFDescriptorGraph;
import com.samsung.android.sume.core.message.Request;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/* loaded from: classes6.dex */
public abstract class ServiceStub extends Service implements ServiceController, MediaController.OnEventListener {
    private static final String TAG = Def.tagOf((Class<?>) ServiceStub.class);
    private final AtomicInteger controllerId = new AtomicInteger(0);
    protected Map<Integer, MediaFilterController> mediaFilterControllers = new ConcurrentHashMap();

    protected abstract Graph<MediaFilter> createGraph(MFDescriptorGraph mFDescriptorGraph);

    protected abstract void startForegroundServiceStub(Intent intent);

    protected abstract void stopForegroundServiceStub();

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    protected void onIntentReceived(Intent intent) {
        String action = (String) Optional.ofNullable(intent).map(new Function() { // from class: com.samsung.android.sume.core.service.ServiceStub$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Intent) obj).getAction();
            }
        }).orElse("n/a");
        Log.d(TAG, "intent: action=" + action);
        if ("start-foreground".equals(action)) {
            Intent activityIntent = (Intent) intent.getParcelableExtra("activity-intent");
            startForegroundService(activityIntent);
        } else if ("start-foreground".equals(action)) {
            stopForegroundServiceStub();
        }
    }

    @Override // com.samsung.android.sume.core.service.ServiceController
    public int createMediaFilterController() {
        int id = this.controllerId.getAndIncrement();
        MediaFilterController mediaController = new MediaFilterController(id);
        this.mediaFilterControllers.put(Integer.valueOf(id), mediaController);
        mediaController.setOnEventListener(this);
        return id;
    }

    @Override // com.samsung.android.sume.core.service.ServiceController
    public void releaseMediaFilterController(int id) {
        MediaFilterController mediaFilterController = this.mediaFilterControllers.remove(Integer.valueOf(id));
        if (mediaFilterController != null) {
            mediaFilterController.release();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x007a, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.samsung.android.sume.core.message.ResponseHolder request(int r7, final com.samsung.android.sume.core.message.Request r8) {
        /*
            r6 = this;
            com.samsung.android.sume.core.message.ResponseHolder r0 = new com.samsung.android.sume.core.message.ResponseHolder
            r0.<init>(r8)
            java.util.Map<java.lang.Integer, com.samsung.android.sume.core.controller.MediaFilterController> r1 = r6.mediaFilterControllers
            java.lang.Integer r2 = java.lang.Integer.valueOf(r7)
            java.lang.Object r1 = r1.get(r2)
            com.samsung.android.sume.core.controller.MediaFilterController r1 = (com.samsung.android.sume.core.controller.MediaFilterController) r1
            if (r1 != 0) goto L43
            r2 = 900(0x384, float:1.261E-42)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r3 = 904(0x388, float:1.267E-42)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.lang.Integer[] r2 = new java.lang.Integer[]{r2, r3}
            java.util.stream.Stream r2 = java.util.stream.Stream.of(r2)
            com.samsung.android.sume.core.service.ServiceStub$$ExternalSyntheticLambda0 r3 = new com.samsung.android.sume.core.service.ServiceStub$$ExternalSyntheticLambda0
            r3.<init>()
            boolean r2 = r2.anyMatch(r3)
            if (r2 == 0) goto L43
            java.lang.String r2 = com.samsung.android.sume.core.service.ServiceStub.TAG
            java.lang.String r3 = "no mediaFilterController given, maybe canceled"
            android.util.Log.d(r2, r3)
            r2 = 702(0x2be, float:9.84E-43)
            com.samsung.android.sume.core.message.Response r2 = com.samsung.android.sume.core.message.Response.of(r2)
            r0.put(r2)
            return r0
        L43:
            int r2 = r8.getCode()
            switch(r2) {
                case 900: goto L56;
                case 904: goto L4b;
                default: goto L4a;
            }
        L4a:
            goto L7a
        L4b:
            r1.release()
            com.samsung.android.sume.core.message.Response r2 = com.samsung.android.sume.core.message.Response.of(r8)
            r0.put(r2)
            goto L7a
        L56:
            java.lang.String r2 = "graph"
            java.lang.Object r2 = r8.get(r2)
            com.samsung.android.sume.core.graph.MFDescriptorGraph r2 = (com.samsung.android.sume.core.graph.MFDescriptorGraph) r2
            r3 = 0
            if (r2 == 0) goto L63
            r4 = 1
            goto L64
        L63:
            r4 = r3
        L64:
            java.lang.String r5 = "no descriptorGraph"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.samsung.android.sume.core.Def.check(r4, r5, r3)
            com.samsung.android.sume.core.graph.Graph r3 = r6.createGraph(r2)
            r1.setMediaFilterGraph(r3)
            com.samsung.android.sume.core.message.Response r3 = com.samsung.android.sume.core.message.Response.of(r8)
            r0.put(r3)
        L7a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sume.core.service.ServiceStub.request(int, com.samsung.android.sume.core.message.Request):com.samsung.android.sume.core.message.ResponseHolder");
    }

    static /* synthetic */ boolean lambda$request$0(Request request, Integer it) {
        return it.intValue() == request.getCode();
    }
}
