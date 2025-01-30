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

/* loaded from: classes4.dex */
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

  public RemoteServiceProxy(Context context, Class<?> serviceClass, Map<Integer, Object> options) {
    this(
        context,
        (String)
            Optional.ofNullable(serviceClass.getPackage())
                .map(
                    new Function() { // from class:
                                     // com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda4
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        return ((Package) obj).getName();
                      }
                    })
                .orElse("n/a"),
        serviceClass.getName(),
        options);
  }

  RemoteServiceProxy(
      Context context, String packageName, String className, Map<Integer, Object> options) {
    this.requestThreadPool = Executors.newFixedThreadPool(4);
    this.mfControllerSync = new ConditionVariable();
    this.responseList = new CopyOnWriteArrayList();
    this.requestChannel = new LinkedBlockingQueue();
    this.context = context;
    String str = TAG;
    HandlerThread handlerThread = new HandlerThread(str);
    this.responseHandlerThread = handlerThread;
    handlerThread.start();
    this.responseMessenger =
        new Messenger(
            new IncomingHandler(
                new Consumer() { // from class:
                                 // com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda2
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    RemoteServiceProxy.this.onReceiveResponse((Response) obj);
                  }
                },
                this.responseHandlerThread.getLooper()));
    this.connection =
        new ServiceConnection() { // from class:
                                  // com.samsung.android.sume.core.service.RemoteServiceProxy.1
          @Override // android.content.ServiceConnection
          public void onServiceConnected(ComponentName name, IBinder service) {
            Log.m94d(RemoteServiceProxy.TAG, "onServiceConnected E");
            RemoteServiceProxy.this.requestMessenger = new Messenger(service);
            Request.m433of(905)
                .setReceiver(RemoteServiceProxy.this.requestMessenger)
                .setResponseReceiver(RemoteServiceProxy.this.responseMessenger)
                .post();
            Log.m94d(RemoteServiceProxy.TAG, "onServiceConnected X");
          }

          @Override // android.content.ServiceConnection
          public void onServiceDisconnected(ComponentName name) {
            Log.m96e(RemoteServiceProxy.TAG, "onServiceDisconnected E");
            Exception exception = new IllegalStateException("service disconnected abnormally");
            RemoteServiceProxy.this.onError(Response.m437of(-4, exception));
            Log.m96e(RemoteServiceProxy.TAG, "onServiceDisconnected X");
          }

          @Override // android.content.ServiceConnection
          public void onBindingDied(ComponentName name) {
            super.onBindingDied(name);
          }

          @Override // android.content.ServiceConnection
          public void onNullBinding(ComponentName name) {
            super.onNullBinding(name);
          }
        };
    this.requestJob =
        this.requestThreadPool.submit(
            new Runnable() { // from class:
                             // com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda3
              @Override // java.lang.Runnable
              public final void run() {
                RemoteServiceProxy.this.m458x6d765b44();
              }
            });
    Intent intent = new Intent();
    intent.setClassName(packageName, className);
    if (options.containsKey(0)) {
      intent.setAction("start-foreground");
      Intent activityIntent = new Intent();
      activityIntent.setClass(context, (Class) options.get(0));
      intent.putExtra("activity-intent", activityIntent);
    }
    if (options.containsKey(1)) {
      context.startService(intent);
    }
    boolean success = context.bindService(intent, this.connection, 1);
    Log.m94d(str, "bind to service: " + success);
  }

  /* renamed from: lambda$new$0$com-samsung-android-sume-core-service-RemoteServiceProxy */
  /* synthetic */ void m458x6d765b44() {
    this.mfControllerSync.block();
    while (true) {
      try {
        Request request = this.requestChannel.take();
        String str = TAG;
        Log.m94d(
            str,
            "take request: "
                + request.getCode()
                + "[id="
                + this.mediaFilterControllerId
                + NavigationBarInflaterView.SIZE_MOD_END);
        request.put("id", Integer.valueOf(this.mediaFilterControllerId));
        Message msg = request.toAndroidMessage();
        if (!request.isOneWay()) {
          request.setResponseReceiver(this.responseMessenger);
        }
        request.setReceiver(this.requestMessenger).post();
        Log.m94d(str, "send message to remote: " + msg);
      } catch (InterruptedException e) {
        Log.m102w(TAG, "request canceled or release");
        return;
      }
    }
  }

  @Override // com.samsung.android.sume.core.service.ServiceProxy
  public IBinder getBinder() {
    return this.requestMessenger.getBinder();
  }

  @Override // com.samsung.android.sume.core.service.ServiceProxy
  public Future<Response> request(final Request request) {
    final ResponseHolder responseHolder = new ResponseHolder(request.getCode());
    this.responseList.add(responseHolder);
    try {
      if (request.isOneWay()) {
        responseHolder.put(Response.m436of(0));
      } else {
        request.then(
            new Consumer() { // from class:
                             // com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                RemoteServiceProxy.lambda$request$1(
                    ResponseHolder.this, (com.samsung.android.sume.core.message.Message) obj);
              }
            });
      }
      this.requestChannel.put(request);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return this.requestThreadPool.submit(
        new Callable() { // from class:
                         // com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda1
          @Override // java.util.concurrent.Callable
          public final Object call() {
            return RemoteServiceProxy.this.m459xface98b3(request, responseHolder);
          }
        });
  }

  static /* synthetic */ void lambda$request$1(
      ResponseHolder responseHolder, com.samsung.android.sume.core.message.Message response) {
    responseHolder.put((Response) response);
    responseHolder.signal();
  }

  /* renamed from: lambda$request$2$com-samsung-android-sume-core-service-RemoteServiceProxy */
  /* synthetic */ Response m459xface98b3(Request request, ResponseHolder responseHolder)
      throws Exception {
    ExceptionHandler exceptionHandler;
    String str = TAG;
    Log.m94d(str, "request: " + request.getCode());
    try {
      if (!request.isOneWay()) {
        Log.m94d(str, "wait response...E: " + request.getCode());
        responseHolder.await();
        Log.m94d(str, "wait response...X: " + request.getCode());
      }
    } catch (Exception e) {
      if (responseHolder.get() != null) {
        responseHolder.get().setException(e);
      } else {
        e.printStackTrace();
      }
    }
    this.responseList.remove(responseHolder);
    Response response = responseHolder.reset();
    if (response.getException() != null
        && ((exceptionHandler = this.exceptionHandler) == null
            || !exceptionHandler.accept(response.getException()))) {
      throw response.getException();
    }
    return response;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void onReceiveResponse(Response response) {
    String str = TAG;
    Log.m94d(str, "onReceiveResponse: " + response);
    if (response.getResponseListener() != null) {
      Log.m94d(str, "invoke response listener");
      response.getResponseListener().accept(response);
      return;
    }
    switch (response.getCode()) {
      case 905:
        this.mediaFilterControllerId =
            ((Integer) Optional.ofNullable((Integer) response.get("id")).orElse(-1)).intValue();
        this.mfControllerSync.open();
        break;
      case 906:
        this.mfControllerSync.open();
        break;
      default:
        if (response.isError()) {
          onError(response);
        } else if (response.isWarn()) {
          onWarn(response);
        }
        MediaController.OnEventListener eventListener = this.eventListener.get();
        if (eventListener != null) {
          eventListener.onEvent(Event.m425of(response));
          break;
        }
        break;
    }
  }

  private void onWarn(final Response response) {
    Log.m94d(TAG, "onWarn: " + response);
    this.responseList.forEach(
        new Consumer() { // from class:
                         // com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda6
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            RemoteServiceProxy.lambda$onWarn$3(Response.this, (ResponseHolder) obj);
          }
        });
  }

  static /* synthetic */ void lambda$onWarn$3(Response response, ResponseHolder it) {
    String str = TAG;
    Log.m102w(
        str,
        "send response("
            + response.getCode()
            + ") for request("
            + it.getCode()
            + NavigationBarInflaterView.KEY_CODE_END);
    Log.m102w(str, "\tmessage: " + ((String) response.get("message", "")));
    it.put(response);
    it.signal();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void onError(final Response response) {
    final Exception exception = response.getException();
    ExceptionHandler exceptionHandler = this.exceptionHandler;
    if (exceptionHandler != null) {
      exceptionHandler.accept(exception);
    } else {
      this.responseList.forEach(
          new Consumer() { // from class:
                           // com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              RemoteServiceProxy.lambda$onError$4(Response.this, exception, (ResponseHolder) obj);
            }
          });
    }
  }

  static /* synthetic */ void lambda$onError$4(
      Response response, Exception exception, ResponseHolder it) {
    String str = TAG;
    Log.m96e(
        str,
        "send response("
            + response.getCode()
            + ") for request("
            + it.getCode()
            + NavigationBarInflaterView.KEY_CODE_END);
    Log.m96e(str, "\tmessage: " + ((String) response.get("message", "")));
    if (it.get() != null) {
      it.get().setException(exception);
    } else {
      it.put(Response.m437of(-4, exception));
    }
    it.signal();
  }

  /* JADX WARN: Finally extract failed */
  @Override // com.samsung.android.sume.core.service.ServiceProxy
  public synchronized void release() {
    String str;
    String str2;
    try {
      try {
        String str3 = TAG;
        Log.m94d(str3, "release E: " + this.requestMessenger);
        this.mfControllerSync.close();
        Message msg =
            Request.m433of(906)
                .setResponseReceiver(this.responseMessenger)
                .put("id", Integer.valueOf(this.mediaFilterControllerId))
                .toAndroidMessage();
        this.requestMessenger.send(msg);
        Log.m94d(str3, "wait to release...E");
        boolean success = this.mfControllerSync.block(TimeUnit.SECONDS.toMillis(3L));
        Log.m94d(str3, "wait to release...X: " + success);
        if (this.connection != null) {
          Log.m94d(str3, "try to unbind");
          try {
            this.context.unbindService(this.connection);
          } catch (NoSuchElementException e) {
            Log.m102w(TAG, "broken connection: " + e.getMessage());
          }
          this.connection = null;
        }
        this.responseList.forEach(
            new Consumer() { // from class:
                             // com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda7
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
        Log.m102w(str4, "can't send message: " + e2.getMessage());
        if (this.connection != null) {
          Log.m94d(str4, "try to unbind");
          try {
            this.context.unbindService(this.connection);
          } catch (NoSuchElementException e3) {
            Log.m102w(TAG, "broken connection: " + e3.getMessage());
          }
          this.connection = null;
        }
        this.responseList.forEach(
            new Consumer() { // from class:
                             // com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda7
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
      Log.m94d(str, str2);
    } catch (Throwable th) {
      if (this.connection != null) {
        Log.m94d(TAG, "try to unbind");
        try {
          this.context.unbindService(this.connection);
        } catch (NoSuchElementException e4) {
          Log.m102w(TAG, "broken connection: " + e4.getMessage());
        }
        this.connection = null;
      }
      this.responseList.forEach(
          new Consumer() { // from class:
                           // com.samsung.android.sume.core.service.RemoteServiceProxy$$ExternalSyntheticLambda7
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
      Log.m94d(TAG, "release X");
      throw th;
    }
  }

  static /* synthetic */ void lambda$release$5(ResponseHolder it) {
    Log.m98i(TAG, "send canceled response for " + it.getCode() + " to finish up releasing");
    it.put(Response.m436of(702));
    it.signal();
  }

  @Override // com.samsung.android.sume.core.service.ServiceProxy
  public void setEventListener(MediaController.OnEventListener eventListener) {
    this.eventListener = new WeakReference<>(eventListener);
  }

  private static final class IncomingHandler extends Handler {
    private final Consumer<Response> responseConsumer;

    public IncomingHandler(Consumer<Response> responseConsumer, Looper looper) {
      super(looper);
      this.responseConsumer = responseConsumer;
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
      Log.m94d(RemoteServiceProxy.TAG, "receive message: " + msg);
      this.responseConsumer.accept(Response.m439of(msg));
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
