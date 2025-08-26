package com.samsung.android.sume.core.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.controller.MediaController;
import com.samsung.android.sume.core.functional.ExceptionHandler;
import com.samsung.android.sume.core.message.Event;
import com.samsung.android.sume.core.message.Request;
import com.samsung.android.sume.core.message.Response;
import com.samsung.android.sume.core.message.ResponseHolder;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes6.dex */
public class RemoteServiceProxy implements ServiceProxy {
    private static final String TAG = Def.tagOf((Class<?>) RemoteServiceProxy.class);
    private ServiceConnection connection;
    private final Context context;
    private WeakReference<MediaController.OnEventListener> eventListener;
    private ExceptionHandler exceptionHandler;
    private int mediaFilterControllerId;
    private final ConditionVariable mfControllerSync;
    private final BlockingQueue<Request> requestChannel;
    private Future<Void> requestJob;
    private Messenger requestMessenger;
    private ExecutorService requestThreadPool;
    private HandlerThread responseHandlerThread;
    private final List<ResponseHolder> responseList;
    private Messenger responseMessenger;

    public RemoteServiceProxy(Context context, Class<?> cls, Map<Integer, Object> map) {
        this(context, (String) Optional.ofNullable(cls.getPackage()).map(new Function() { // from class: com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Package) obj).getName();
            }
        }).orElse("n/a"), cls.getName(), map);
    }

    RemoteServiceProxy(Context context, String str, String str2, Map<Integer, Object> map) {
        this.requestThreadPool = Executors.newFixedThreadPool(4);
        this.mfControllerSync = new ConditionVariable();
        this.responseList = new CopyOnWriteArrayList();
        this.requestChannel = new LinkedBlockingQueue();
        this.context = context;
        String str3 = TAG;
        HandlerThread handlerThread = new HandlerThread(str3);
        this.responseHandlerThread = handlerThread;
        handlerThread.start();
        this.responseMessenger = new Messenger(new IncomingHandler(new Consumer() { // from class: com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.onReceiveResponse((Response) obj);
            }
        }, this.responseHandlerThread.getLooper()));
        this.connection = new ServiceConnection() { // from class: com.samsung.android.sume.core.service.RemoteServiceProxy.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d(RemoteServiceProxy.TAG, "onServiceConnected E");
                RemoteServiceProxy.this.requestMessenger = new Messenger(iBinder);
                Request.of(905).setReceiver(RemoteServiceProxy.this.requestMessenger).setResponseReceiver(RemoteServiceProxy.this.responseMessenger).post();
                Log.d(RemoteServiceProxy.TAG, "onServiceConnected X");
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Log.e(RemoteServiceProxy.TAG, "onServiceDisconnected E");
                RemoteServiceProxy.this.onError(Response.of(-4, new IllegalStateException("service disconnected abnormally")));
                Log.e(RemoteServiceProxy.TAG, "onServiceDisconnected X");
            }

            @Override // android.content.ServiceConnection
            public void onBindingDied(ComponentName componentName) {
                super.onBindingDied(componentName);
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(ComponentName componentName) {
                super.onNullBinding(componentName);
            }
        };
        this.requestJob = this.requestThreadPool.submit(new Runnable() { // from class: com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException {
                this.f$0.m9624x6d765b44();
            }
        });
        Intent intent = new Intent();
        intent.setClassName(str, str2);
        if (map.containsKey(0)) {
            intent.setAction("start-foreground");
            Intent intent2 = new Intent();
            intent2.setClass(context, (Class) map.get(0));
            intent.putExtra("activity-intent", intent2);
        }
        if (map.containsKey(1)) {
            context.startService(intent);
        }
        Log.d(str3, "bind to service: " + context.bindService(intent, this.connection, 1));
    }

    /* renamed from: lambda$new$0$com-samsung-android-sume-core-service-RemoteServiceProxy, reason: not valid java name */
    /* synthetic */ void m9624x6d765b44() throws InterruptedException {
        this.mfControllerSync.block();
        while (true) {
            try {
                Request requestTake = this.requestChannel.take();
                String str = TAG;
                Log.d(str, "take request: " + requestTake.getCode() + "[id=" + this.mediaFilterControllerId + NavigationBarInflaterView.SIZE_MOD_END);
                requestTake.put("id", Integer.valueOf(this.mediaFilterControllerId));
                Message androidMessage = requestTake.toAndroidMessage();
                if (!requestTake.isOneWay()) {
                    requestTake.setResponseReceiver(this.responseMessenger);
                }
                requestTake.setReceiver(this.requestMessenger).post();
                Log.d(str, "send message to remote: " + androidMessage);
            } catch (InterruptedException unused) {
                Log.w(TAG, "request canceled or release");
                return;
            }
        }
    }

    @Override // com.samsung.android.sume.core.service.ServiceProxy
    public IBinder getBinder() {
        return this.requestMessenger.getBinder();
    }

    @Override // com.samsung.android.sume.core.service.ServiceProxy
    public Future<Response> request(final Request request) throws InterruptedException {
        final ResponseHolder responseHolder = new ResponseHolder(request.getCode());
        this.responseList.add(responseHolder);
        try {
            if (request.isOneWay()) {
                responseHolder.put(Response.of(0));
            } else {
                request.then(new Consumer() { // from class: com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        RemoteServiceProxy.lambda$request$1(responseHolder, (com.samsung.android.sume.core.message.Message) obj);
                    }
                });
            }
            this.requestChannel.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.requestThreadPool.submit(new Callable() { // from class: com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda1
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.m9625xface98b3(request, responseHolder);
            }
        });
    }

    static /* synthetic */ void lambda$request$1(ResponseHolder responseHolder, com.samsung.android.sume.core.message.Message message) {
        responseHolder.put((Response) message);
        responseHolder.signal();
    }

    /* renamed from: lambda$request$2$com-samsung-android-sume-core-service-RemoteServiceProxy, reason: not valid java name */
    /* synthetic */ Response m9625xface98b3(Request request, ResponseHolder responseHolder) throws Exception {
        ExceptionHandler exceptionHandler;
        String str = TAG;
        Log.d(str, "request: " + request.getCode());
        try {
            if (!request.isOneWay()) {
                Log.d(str, "wait response...E: " + request.getCode());
                responseHolder.await();
                Log.d(str, "wait response...X: " + request.getCode());
            }
        } catch (Exception e) {
            if (responseHolder.get() != null) {
                responseHolder.get().setException(e);
            } else {
                e.printStackTrace();
            }
        }
        this.responseList.remove(responseHolder);
        Response responseReset = responseHolder.reset();
        if (responseReset.getException() == null || ((exceptionHandler = this.exceptionHandler) != null && exceptionHandler.accept(responseReset.getException()))) {
            return responseReset;
        }
        throw responseReset.getException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onReceiveResponse(Response response) {
        String str = TAG;
        Log.d(str, "onReceiveResponse: " + response);
        if (response.getResponseListener() != null) {
            Log.d(str, "invoke response listener");
            response.getResponseListener().accept(response);
            return;
        }
        int code = response.getCode();
        if (code == 905) {
            this.mediaFilterControllerId = ((Integer) Optional.ofNullable((Integer) response.get("id")).orElse(-1)).intValue();
            this.mfControllerSync.open();
            return;
        }
        if (code == 906) {
            this.mfControllerSync.open();
            return;
        }
        if (response.isError()) {
            onError(response);
        } else if (response.isWarn()) {
            onWarn(response);
        }
        MediaController.OnEventListener onEventListener = this.eventListener.get();
        if (onEventListener != null) {
            onEventListener.onEvent(Event.of(response));
        }
    }

    private void onWarn(final Response response) {
        Log.d(TAG, "onWarn: " + response);
        this.responseList.forEach(new Consumer() { // from class: com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RemoteServiceProxy.lambda$onWarn$3(response, (ResponseHolder) obj);
            }
        });
    }

    static /* synthetic */ void lambda$onWarn$3(Response response, ResponseHolder responseHolder) {
        String str = TAG;
        Log.w(str, "send response(" + response.getCode() + ") for request(" + responseHolder.getCode() + NavigationBarInflaterView.KEY_CODE_END);
        StringBuilder sb = new StringBuilder("\tmessage: ");
        sb.append((String) response.get("message", ""));
        Log.w(str, sb.toString());
        responseHolder.put(response);
        responseHolder.signal();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onError(final Response response) {
        final Exception exception = response.getException();
        ExceptionHandler exceptionHandler = this.exceptionHandler;
        if (exceptionHandler != null) {
            exceptionHandler.accept(exception);
        } else {
            this.responseList.forEach(new Consumer() { // from class: com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RemoteServiceProxy.lambda$onError$4(response, exception, (ResponseHolder) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$onError$4(Response response, Exception exc, ResponseHolder responseHolder) {
        String str = TAG;
        Log.e(str, "send response(" + response.getCode() + ") for request(" + responseHolder.getCode() + NavigationBarInflaterView.KEY_CODE_END);
        StringBuilder sb = new StringBuilder("\tmessage: ");
        sb.append((String) response.get("message", ""));
        Log.e(str, sb.toString());
        if (responseHolder.get() != null) {
            responseHolder.get().setException(exc);
        } else {
            responseHolder.put(Response.of(-4, exc));
        }
        responseHolder.signal();
    }

    /* JADX WARN: Finally extract failed */
    @Override // com.samsung.android.sume.core.service.ServiceProxy
    public synchronized void release() {
        String str;
        String str2;
        try {
            try {
                String str3 = TAG;
                Log.d(str3, "release E: " + this.requestMessenger);
                this.mfControllerSync.close();
                this.requestMessenger.send(Request.of(906).setResponseReceiver(this.responseMessenger).put("id", Integer.valueOf(this.mediaFilterControllerId)).toAndroidMessage());
                Log.d(str3, "wait to release...E");
                Log.d(str3, "wait to release...X: " + this.mfControllerSync.block(TimeUnit.SECONDS.toMillis(3L)));
                if (this.connection != null) {
                    Log.d(str3, "try to unbind");
                    try {
                        this.context.unbindService(this.connection);
                    } catch (NoSuchElementException e) {
                        Log.w(TAG, "broken connection: " + e.getMessage());
                    }
                    this.connection = null;
                }
                this.responseList.forEach(new Consumer() { // from class: com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        RemoteServiceProxy.lambda$release$5((ResponseHolder) obj);
                    }
                });
                Future<Void> future = this.requestJob;
                if (future != null) {
                    future.cancel(true);
                    this.requestJob = null;
                }
                ExecutorService executorService = this.requestThreadPool;
                if (executorService != null) {
                    executorService.shutdownNow();
                    this.requestThreadPool = null;
                }
                HandlerThread handlerThread = this.responseHandlerThread;
                if (handlerThread != null) {
                    handlerThread.quitSafely();
                    this.responseHandlerThread = null;
                }
                this.requestMessenger = null;
                this.responseMessenger = null;
                str = TAG;
                str2 = "release X";
            } catch (RemoteException | NullPointerException e2) {
                String str4 = TAG;
                Log.w(str4, "can't send message: " + e2.getMessage());
                if (this.connection != null) {
                    Log.d(str4, "try to unbind");
                    try {
                        this.context.unbindService(this.connection);
                    } catch (NoSuchElementException e3) {
                        Log.w(TAG, "broken connection: " + e3.getMessage());
                    }
                    this.connection = null;
                }
                this.responseList.forEach(new Consumer() { // from class: com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        RemoteServiceProxy.lambda$release$5((ResponseHolder) obj);
                    }
                });
                Future<Void> future2 = this.requestJob;
                if (future2 != null) {
                    future2.cancel(true);
                    this.requestJob = null;
                }
                ExecutorService executorService2 = this.requestThreadPool;
                if (executorService2 != null) {
                    executorService2.shutdownNow();
                    this.requestThreadPool = null;
                }
                HandlerThread handlerThread2 = this.responseHandlerThread;
                if (handlerThread2 != null) {
                    handlerThread2.quitSafely();
                    this.responseHandlerThread = null;
                }
                this.requestMessenger = null;
                this.responseMessenger = null;
                str = TAG;
                str2 = "release X";
            }
            Log.d(str, str2);
        } catch (Throwable th) {
            if (this.connection != null) {
                Log.d(TAG, "try to unbind");
                try {
                    this.context.unbindService(this.connection);
                } catch (NoSuchElementException e4) {
                    Log.w(TAG, "broken connection: " + e4.getMessage());
                }
                this.connection = null;
            }
            this.responseList.forEach(new Consumer() { // from class: com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RemoteServiceProxy.lambda$release$5((ResponseHolder) obj);
                }
            });
            Future<Void> future3 = this.requestJob;
            if (future3 != null) {
                future3.cancel(true);
                this.requestJob = null;
            }
            ExecutorService executorService3 = this.requestThreadPool;
            if (executorService3 != null) {
                executorService3.shutdownNow();
                this.requestThreadPool = null;
            }
            HandlerThread handlerThread3 = this.responseHandlerThread;
            if (handlerThread3 != null) {
                handlerThread3.quitSafely();
                this.responseHandlerThread = null;
            }
            this.requestMessenger = null;
            this.responseMessenger = null;
            Log.d(TAG, "release X");
            throw th;
        }
    }

    static /* synthetic */ void lambda$release$5(ResponseHolder responseHolder) {
        Log.i(TAG, "send canceled response for " + responseHolder.getCode() + " to finish up releasing");
        responseHolder.put(Response.of(702));
        responseHolder.signal();
    }

    @Override // com.samsung.android.sume.core.service.ServiceProxy
    public void setEventListener(MediaController.OnEventListener onEventListener) {
        this.eventListener = new WeakReference<>(onEventListener);
    }

    private static final class IncomingHandler extends Handler {
        private final Consumer<Response> responseConsumer;

        public IncomingHandler(Consumer<Response> consumer, Looper looper) {
            super(looper);
            this.responseConsumer = consumer;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.d(RemoteServiceProxy.TAG, "receive message: " + message);
            this.responseConsumer.accept(Response.of(message));
        }
    }

    @Override // com.samsung.android.sume.core.service.ServiceProxy
    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    @Override // com.samsung.android.sume.core.service.ServiceProxy
    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }
}
