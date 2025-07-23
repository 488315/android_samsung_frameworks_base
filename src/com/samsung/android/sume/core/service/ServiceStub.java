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
import com.samsung.android.sume.core.message.Response;
import com.samsung.android.sume.core.message.ResponseHolder;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public abstract class ServiceStub extends Service implements ServiceController, MediaController.OnEventListener {
    private static final String TAG = Def.tagOf((Class<?>) ServiceStub.class);
    private final AtomicInteger controllerId = new AtomicInteger(0);
    protected Map<Integer, MediaFilterController> mediaFilterControllers = new ConcurrentHashMap();

    protected abstract Graph<MediaFilter> createGraph(MFDescriptorGraph mFDescriptorGraph);

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected abstract void startForegroundServiceStub(Intent intent);

    protected abstract void stopForegroundServiceStub();

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, i, i2);
    }

    @Override // android.app.Service
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    protected void onIntentReceived(Intent intent) {
        String str = (String) Optional.ofNullable(intent).map(new Function() { // from class: com.samsung.android.sume.core.service.ServiceStub$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Intent) obj).getAction();
            }
        }).orElse("n/a");
        Log.d(TAG, "intent: action=" + str);
        if ("start-foreground".equals(str)) {
            startForegroundService((Intent) intent.getParcelableExtra("activity-intent"));
        } else if ("start-foreground".equals(str)) {
            stopForegroundServiceStub();
        }
    }

    @Override // com.samsung.android.sume.core.service.ServiceController
    public int createMediaFilterController() {
        int andIncrement = this.controllerId.getAndIncrement();
        MediaFilterController mediaFilterController = new MediaFilterController(andIncrement);
        this.mediaFilterControllers.put(Integer.valueOf(andIncrement), mediaFilterController);
        mediaFilterController.setOnEventListener(this);
        return andIncrement;
    }

    @Override // com.samsung.android.sume.core.service.ServiceController
    public void releaseMediaFilterController(int i) {
        MediaFilterController remove = this.mediaFilterControllers.remove(Integer.valueOf(i));
        if (remove != null) {
            remove.release();
        }
    }

    public ResponseHolder request(int i, final Request request) {
        ResponseHolder responseHolder = new ResponseHolder(request);
        MediaFilterController mediaFilterController = this.mediaFilterControllers.get(Integer.valueOf(i));
        if (mediaFilterController == null && Stream.of((Object[]) new Integer[]{900, 904}).anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.service.ServiceStub$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ServiceStub.lambda$request$0(Request.this, (Integer) obj);
            }
        })) {
            Log.d(TAG, "no mediaFilterController given, maybe canceled");
            responseHolder.put(Response.of(702));
            return responseHolder;
        }
        int code = request.getCode();
        if (code != 900) {
            if (code != 904) {
                return responseHolder;
            }
            mediaFilterController.release();
            responseHolder.put(Response.of(request));
            return responseHolder;
        }
        MFDescriptorGraph mFDescriptorGraph = (MFDescriptorGraph) request.get("graph");
        Def.check(mFDescriptorGraph != null, "no descriptorGraph", new Object[0]);
        mediaFilterController.setMediaFilterGraph(createGraph(mFDescriptorGraph));
        responseHolder.put(Response.of(request));
        return responseHolder;
    }

    static /* synthetic */ boolean lambda$request$0(Request request, Integer num) {
        return num.intValue() == request.getCode();
    }
}
