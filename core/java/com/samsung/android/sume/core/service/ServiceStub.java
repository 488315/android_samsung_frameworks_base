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

/* loaded from: classes4.dex */
public abstract class ServiceStub extends Service
    implements ServiceController, MediaController.OnEventListener {
  private static final String TAG = Def.tagOf((Class<?>) ServiceStub.class);
  private final AtomicInteger controllerId = new AtomicInteger(0);
  protected Map<Integer, MediaFilterController> mediaFilterControllers = new ConcurrentHashMap();

  protected abstract Graph<MediaFilter> createGraph(MFDescriptorGraph mFDescriptorGraph);

  protected abstract void startForegroundServiceStub(Intent intent);

  protected abstract void stopForegroundServiceStub();

  @Override // android.app.Service
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.m94d(TAG, "onStartCommand");
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
    String action =
        (String)
            Optional.ofNullable(intent)
                .map(
                    new Function() { // from class:
                                     // com.samsung.android.sume.core.service.ServiceStub$$ExternalSyntheticLambda0
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        return ((Intent) obj).getAction();
                      }
                    })
                .orElse("n/a");
    Log.m94d(TAG, "intent: action=" + action);
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
    MediaFilterController mediaFilterController =
        this.mediaFilterControllers.remove(Integer.valueOf(id));
    if (mediaFilterController != null) {
      mediaFilterController.release();
    }
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Code restructure failed: missing block: B:16:0x007c, code lost:

     return r0;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public ResponseHolder request(int id, final Request request) {
    ResponseHolder responseHolder = new ResponseHolder(request);
    MediaFilterController mediaFilterController =
        this.mediaFilterControllers.get(Integer.valueOf(id));
    if (mediaFilterController == null
        && Stream.of((Object[]) new Integer[] {900, 904})
            .anyMatch(
                new Predicate() { // from class:
                                  // com.samsung.android.sume.core.service.ServiceStub$$ExternalSyntheticLambda1
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    return ServiceStub.lambda$request$0(Request.this, (Integer) obj);
                  }
                })) {
      Log.m94d(TAG, "no mediaFilterController given, maybe canceled");
      responseHolder.put(Response.m436of(702));
      return responseHolder;
    }
    switch (request.getCode()) {
      case 900:
        MFDescriptorGraph descriptorGraph = (MFDescriptorGraph) request.get("graph");
        Def.check(descriptorGraph != null, "no descriptorGraph", new Object[0]);
        mediaFilterController.setMediaFilterGraph(createGraph(descriptorGraph));
        responseHolder.put(Response.m440of(request));
        break;
      case 904:
        mediaFilterController.release();
        responseHolder.put(Response.m440of(request));
        break;
    }
  }

  static /* synthetic */ boolean lambda$request$0(Request request, Integer it) {
    return it.intValue() == request.getCode();
  }
}
