package com.samsung.android.sume.core.controller;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.controller.MediaController;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.functional.ExceptionHandler;
import com.samsung.android.sume.core.graph.MFDescriptorGraph;
import com.samsung.android.sume.core.message.Event;
import com.samsung.android.sume.core.message.Request;
import com.samsung.android.sume.core.message.Response;
import com.samsung.android.sume.core.service.ServiceProxy;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
public class SumeClient implements MediaController<Future<Response>>, MediaController.OnEventListener {
    private static final String TAG = Def.tagOf((Class<?>) SumeClient.class);
    private MediaController.OnEventListener eventListener;
    private final MFDescriptorGraph graph;
    private volatile ServiceProxy serviceProxy;

    static /* synthetic */ void lambda$request$5() {
    }

    static /* synthetic */ void lambda$run$2() {
    }

    @Override // com.samsung.android.sume.core.controller.MediaController
    public /* bridge */ /* synthetic */ Future<Response> run(List list, List list2) {
        return run2((List<MediaBuffer>) list, (List<MediaBuffer>) list2);
    }

    public SumeClient(ServiceProxy serviceProxy, MFDescriptorGraph mFDescriptorGraph) {
        this.serviceProxy = serviceProxy;
        this.graph = mFDescriptorGraph;
        if (!mFDescriptorGraph.getOption().contains(7)) {
            serviceProxy.setExceptionHandler(new ExceptionHandler() { // from class: com.samsung.android.sume.core.controller.SumeClient$$ExternalSyntheticLambda6
                @Override // com.samsung.android.sume.core.functional.ExceptionHandler
                public final boolean accept(Exception exc) {
                    return SumeClient.lambda$new$0(exc);
                }
            });
        }
        serviceProxy.setEventListener(this);
        serviceProxy.request(Request.of(900, "graph", mFDescriptorGraph).asOneWay());
    }

    static /* synthetic */ boolean lambda$new$0(Exception exc) {
        Log.d(TAG, "ignore exception: " + exc.getMessage());
        return true;
    }

    public Future<Response> run(List<MediaBuffer> list, MediaFormat mediaFormat) {
        MediaBuffer[] mediaBufferArr = new MediaBuffer[list.size()];
        Arrays.fill(mediaBufferArr, MediaBuffer.sharedOf(mediaFormat));
        return run2(list, Arrays.asList(mediaBufferArr));
    }

    @Override // com.samsung.android.sume.core.controller.MediaController
    /* renamed from: run, reason: avoid collision after fix types in other method */
    public Future<Response> run2(final List<MediaBuffer> list, final List<MediaBuffer> list2) {
        return (Future) Optional.ofNullable(this.serviceProxy).map(new Function() { // from class: com.samsung.android.sume.core.controller.SumeClient$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ServiceProxy) obj).request(Request.of(901).setInputBuffer((List<MediaBuffer>) list).setOutputBuffer((List<MediaBuffer>) list2));
            }
        }).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.controller.SumeClient$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return SumeClient.lambda$run$3();
            }
        });
    }

    static /* synthetic */ Future lambda$run$3() {
        return new FutureTask(new Runnable() { // from class: com.samsung.android.sume.core.controller.SumeClient$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                SumeClient.lambda$run$2();
            }
        }, Response.of(-5));
    }

    @Override // com.samsung.android.sume.core.controller.MediaController
    public Future<Response> request(final Request request) {
        return (Future) Optional.ofNullable(this.serviceProxy).map(new Function() { // from class: com.samsung.android.sume.core.controller.SumeClient$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ServiceProxy) obj).request(request);
            }
        }).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.controller.SumeClient$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return SumeClient.lambda$request$6();
            }
        });
    }

    static /* synthetic */ Future lambda$request$6() {
        return new FutureTask(new Runnable() { // from class: com.samsung.android.sume.core.controller.SumeClient$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                SumeClient.lambda$request$5();
            }
        }, Response.of(-5));
    }

    @Override // com.samsung.android.sume.core.controller.MediaController
    public void release() {
        Log.d(TAG, "release");
        if (this.serviceProxy != null) {
            synchronized (this.serviceProxy) {
                if (this.serviceProxy != null) {
                    this.serviceProxy.release();
                    this.serviceProxy = null;
                    this.eventListener = null;
                }
            }
        }
    }

    @Override // com.samsung.android.sume.core.controller.MediaController
    public void setOnEventListener(MediaController.OnEventListener onEventListener) {
        this.eventListener = onEventListener;
    }

    public MFDescriptorGraph getGraph() {
        return this.graph;
    }

    @Override // com.samsung.android.sume.core.controller.MediaController.OnEventListener
    public void onEvent(Event event) {
        String str = TAG;
        Log.d(str, "onEvent:  " + event);
        if (event.isError()) {
            Log.d(str, "error occur, do force-release: " + event.getException());
        }
        MediaController.OnEventListener onEventListener = this.eventListener;
        if (onEventListener != null) {
            onEventListener.onEvent(event);
        }
    }

    public ExceptionHandler getExceptionHandler() {
        return this.serviceProxy.getExceptionHandler();
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.serviceProxy.setExceptionHandler(exceptionHandler);
    }
}
