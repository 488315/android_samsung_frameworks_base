package com.samsung.systemui.splugins.volume;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class VolumeUnsubscriber<T> implements VolumeDisposable {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "VolumeUnsubscriber";
    private final Lazy handler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.systemui.splugins.volume.VolumeUnsubscriber$handler$2
        @Override // kotlin.jvm.functions.Function0
        public final Handler invoke() {
            return new Handler(Looper.getMainLooper());
        }
    });
    private final VolumeObserver<T> observer;
    private final ArrayList<VolumeObserver<T>> observers;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public VolumeUnsubscriber(ArrayList<VolumeObserver<T>> arrayList, VolumeObserver<T> volumeObserver) {
        this.observers = arrayList;
        this.observer = volumeObserver;
    }

    private final Handler getHandler() {
        return (Handler) this.handler$delegate.getValue();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeDisposable
    public void dispose() {
        if (!Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            getHandler().postAtFrontOfQueue(new Runnable(this) { // from class: com.samsung.systemui.splugins.volume.VolumeUnsubscriber$dispose$1
                final /* synthetic */ VolumeUnsubscriber<T> this$0;

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
