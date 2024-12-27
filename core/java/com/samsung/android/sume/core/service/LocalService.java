package com.samsung.android.sume.core.service;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.controller.MediaController;
import com.samsung.android.sume.core.message.Event;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
public abstract class LocalService extends ServiceStub {
    private static final String TAG = Def.tagOf((Class<?>) LocalService.class);
    protected Binder binder = new LocalBinder();
    protected MediaController.OnEventListener eventListener;

    public void setEventListener(MediaController.OnEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override // com.samsung.android.sume.core.service.ServiceStub, android.app.Service
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return this.binder;
    }

    @Override // com.samsung.android.sume.core.service.ServiceStub
    protected void startForegroundServiceStub(Intent intent) {
        throw new UnsupportedOperationException("Local Service do not this, if required, override it");
    }

    @Override // com.samsung.android.sume.core.service.ServiceStub
    protected void stopForegroundServiceStub() {
        throw new UnsupportedOperationException("Local Service do not this, if required, override it");
    }

    @Override // com.samsung.android.sume.core.controller.MediaController.OnEventListener
    public void onEvent(Event event) {
        switch (event.getCode()) {
            case 510:
                Bundle data = event.toAndroidMessage().getData();
                Parcelable[] buffers = data.getParcelableArray("buffer-list");
                if (buffers != null) {
                    event.put("buffer-list", Arrays.stream(buffers).map(new Function() { // from class: com.samsung.android.sume.core.service.LocalService$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return LocalService.lambda$onEvent$0((Parcelable) obj);
                        }
                    }).collect(Collectors.toList()));
                    break;
                }
                break;
        }
    }

    static /* synthetic */ MediaBuffer lambda$onEvent$0(Parcelable it) {
        return (MediaBuffer) it;
    }

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public LocalService getService() {
            return LocalService.this;
        }
    }
}
