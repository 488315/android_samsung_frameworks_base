package com.samsung.android.sume.core.service;

import android.content.Intent;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.Messenger;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.controller.MediaController;
import com.samsung.android.sume.core.graph.MFDescriptorGraph;
import com.samsung.android.sume.core.message.Request;
import com.samsung.android.sume.core.message.Response;
import com.samsung.android.sume.core.message.ResponseHolder;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public abstract class RemoteService extends ServiceStub implements ServiceController, MediaController.OnEventListener {
    private static final String TAG = Def.tagOf((Class<?>) RemoteService.class);
    protected Messenger requestMessenger = new Messenger(new IncommingHandler(this));
    protected final Map<Integer, Messenger> replyListeners = new HashMap();
    protected AtomicReference<Timer> exitTimer = new AtomicReference<>(null);

    @Override // com.samsung.android.sume.core.service.ServiceStub, android.app.Service
    public IBinder onBind(Intent intent) {
        Timer timer = this.exitTimer.get();
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        this.exitTimer.set(null);
        return null;
    }

    @Override // com.samsung.android.sume.core.service.ServiceStub, android.app.Service
    public void onRebind(Intent intent) {
        Timer timer = this.exitTimer.get();
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        this.exitTimer.set(null);
        super.onRebind(intent);
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        if (this.mediaFilterControllers.isEmpty()) {
            Pair<Integer, TimeUnit> delay = new Pair<>(30, TimeUnit.MINUTES);
            Log.m98i(TAG, "all clients are disconnected, run exit-timer[" + delay.first + " " + delay.second + " to stop service");
            Timer timer = new Timer("Self-stop SumeNNService");
            this.exitTimer.set(timer);
            timer.schedule(new TimerTask() { // from class: com.samsung.android.sume.core.service.RemoteService.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    RemoteService.this.stopSelf();
                }
            }, delay.second.toMillis(delay.first.intValue()));
        }
        return super.onUnbind(intent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.samsung.android.sume.core.service.ServiceStub, com.samsung.android.sume.core.service.ServiceController
    public ResponseHolder request(int id, Request request) {
        ResponseHolder responseHolder = super.request(id, request);
        if (!responseHolder.contains()) {
            switch (request.getCode()) {
                case 905:
                    int newId = createMediaFilterController();
                    this.replyListeners.put(Integer.valueOf(newId), request.getResponseReceiver());
                    Response response = Response.m440of(request);
                    response.put("id", Integer.valueOf(newId));
                    responseHolder.put(response);
                    break;
                case 906:
                    releaseMediaFilterController(id);
                    Response response2 = Response.m440of(request);
                    Messenger replyListener = this.replyListeners.remove(Integer.valueOf(id));
                    if (replyListener != null) {
                        response2.setResponseReceiver(replyListener);
                    }
                    responseHolder.put(response2);
                    break;
            }
        }
        return responseHolder;
    }

    private static class IncommingHandler extends Handler {
        private final WeakReference<RemoteService> weakRefService;

        IncommingHandler(RemoteService remoteService) {
            super(Looper.getMainLooper());
            this.weakRefService = new WeakReference<>(remoteService);
        }

        @Override // android.p009os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.m94d(RemoteService.TAG, "handleMessage: msg=" + msg + " on " + Thread.currentThread().getId());
            msg.getData().setClassLoader(MFDescriptorGraph.class.getClassLoader());
            Request request = Request.m435of(msg);
            int id = ((Integer) request.get("id", 0)).intValue();
            if (this.weakRefService.get() != null) {
                this.weakRefService.get().request(id, request);
            }
        }
    }
}
