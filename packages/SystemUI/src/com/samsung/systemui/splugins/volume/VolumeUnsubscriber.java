package com.samsung.systemui.splugins.volume;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class VolumeUnsubscriber<T> implements VolumeDisposable {
    private static final String TAG = "VolumeUnsubscriber";
    private final Lazy handler$delegate = LazyKt__LazyJVMKt.lazy(new VolumeUnsubscriber$$ExternalSyntheticLambda0());
    private final VolumeObserver<T> observer;
    private final ArrayList<VolumeObserver<T>> observers;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public VolumeUnsubscriber(ArrayList<VolumeObserver<T>> arrayList, VolumeObserver<T> volumeObserver) {
        this.observers = arrayList;
        this.observer = volumeObserver;
    }

    private final Handler getHandler() {
        return (Handler) this.handler$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Handler handler_delegate$lambda$0() {
        return new Handler(Looper.getMainLooper());
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeDisposable
    public void dispose() {
        if (!Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            getHandler().postAtFrontOfQueue(new Runnable(this) { // from class: com.samsung.systemui.splugins.volume.VolumeUnsubscriber$dispose$1
                final /* synthetic */ VolumeUnsubscriber<Object> this$0;

                {
                    this.this$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    VolumeObserver volumeObserver;
                    ArrayList arrayList;
                    VolumeObserver volumeObserver2;
                    volumeObserver = ((VolumeUnsubscriber) this.this$0).observer;
                    Log.d("VolumeUnsubscriber", "dispose() : postAtFrontOfQueue, remove observer=" + volumeObserver);
                    arrayList = ((VolumeUnsubscriber) this.this$0).observers;
                    volumeObserver2 = ((VolumeUnsubscriber) this.this$0).observer;
                    arrayList.remove(volumeObserver2);
                }
            });
            return;
        }
        Log.d(TAG, "dispose() : main thread, remove observer=" + this.observer);
        this.observers.remove(this.observer);
    }
}
