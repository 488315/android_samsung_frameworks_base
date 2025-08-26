package com.samsung.android.sume.core.service;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
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

/* loaded from: classes6.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        if (this.mediaFilterControllers.isEmpty()) {
            Pair pair = new Pair(30, TimeUnit.MINUTES);
            Log.i(TAG, "all clients are disconnected, run exit-timer[" + pair.first + " " + pair.second + " to stop service");
            Timer timer = new Timer("Self-stop SumeNNService");
            this.exitTimer.set(timer);
            timer.schedule(new TimerTask() { // from class: com.samsung.android.sume.core.service.RemoteService.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    RemoteService.this.stopSelf();
                }
            }, ((TimeUnit) pair.second).toMillis((long) ((Integer) pair.first).intValue()));
        }
        return super.onUnbind(intent);
    }

    @Override // com.samsung.android.sume.core.service.ServiceStub, com.samsung.android.sume.core.service.ServiceController
    public ResponseHolder request(int i, Request request) {
        ResponseHolder responseHolderRequest = super.request(i, request);
        if (!responseHolderRequest.contains()) {
            int code = request.getCode();
            if (code == 905) {
                int iCreateMediaFilterController = createMediaFilterController();
                this.replyListeners.put(Integer.valueOf(iCreateMediaFilterController), request.getResponseReceiver());
                Response responseOf = Response.of(request);
                responseOf.put("id", Integer.valueOf(iCreateMediaFilterController));
                responseHolderRequest.put(responseOf);
            } else if (code == 906) {
                releaseMediaFilterController(i);
                Response responseOf2 = Response.of(request);
                Messenger messengerRemove = this.replyListeners.remove(Integer.valueOf(i));
                if (messengerRemove != null) {
                    responseOf2.setResponseReceiver(messengerRemove);
                }
                responseHolderRequest.put(responseOf2);
                return responseHolderRequest;
            }
        }
        return responseHolderRequest;
    }

    private static class IncommingHandler extends Handler {
        private final WeakReference<RemoteService> weakRefService;

        IncommingHandler(RemoteService remoteService) {
            super(Looper.getMainLooper());
            this.weakRefService = new WeakReference<>(remoteService);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            Log.d(RemoteService.TAG, "handleMessage: msg=" + message + " on " + Thread.currentThread().getId());
            message.getData().setClassLoader(MFDescriptorGraph.class.getClassLoader());
            Request requestOf = Request.of(message);
            int iIntValue = ((Integer) requestOf.get("id", 0)).intValue();
            if (this.weakRefService.get() != null) {
                this.weakRefService.get().request(iIntValue, requestOf);
            }
        }
    }
}
