package com.android.systemui.lifecycle;

import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;
import androidx.collection.MutableScatterSet;
import androidx.compose.runtime.snapshots.MutableSnapshot;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.runtime.snapshots.SnapshotStateObserver;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final class SnapshotViewBindingRoot {
    public final Choreographer choreographer;
    public final Handler handler;
    public boolean isFrameScheduled;
    public final SnapshotStateObserver observer;
    public final SnapshotViewBindingRoot$$ExternalSyntheticLambda0 onBindingChanged;
    public final MutableScatterSet invalidatedBindings = new MutableScatterSet(0, 1, null);
    public final SnapshotViewBindingRoot$frameCallback$1 frameCallback = new Choreographer.FrameCallback() { // from class: com.android.systemui.lifecycle.SnapshotViewBindingRoot$frameCallback$1
        @Override // android.view.Choreographer.FrameCallback
        public final void doFrame(long j) {
            try {
                SnapshotViewBindingRoot.access$bindInvalidatedBindings(this.this$0);
            } finally {
                this.this$0.isFrameScheduled = false;
            }
        }
    };

    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.lifecycle.SnapshotViewBindingRoot$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.lifecycle.SnapshotViewBindingRoot$frameCallback$1] */
    public SnapshotViewBindingRoot(Handler handler, Choreographer choreographer) {
        this.handler = handler;
        this.choreographer = choreographer;
        final int i = 0;
        this.observer = new SnapshotStateObserver(new Function1(this) { // from class: com.android.systemui.lifecycle.SnapshotViewBindingRoot$$ExternalSyntheticLambda0
            public final /* synthetic */ SnapshotViewBindingRoot f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                switch (i) {
                    case 0:
                        final Function0 function0 = (Function0) obj;
                        Looper looperMyLooper = Looper.myLooper();
                        SnapshotViewBindingRoot snapshotViewBindingRoot = this.f$0;
                        if (looperMyLooper == snapshotViewBindingRoot.handler.getLooper()) {
                            function0.invoke();
                        } else {
                            snapshotViewBindingRoot.handler.post(new Runnable() { // from class: com.android.systemui.lifecycle.SnapshotViewBindingKt$sam$java_lang_Runnable$0
                                @Override // java.lang.Runnable
                                public final /* synthetic */ void run() {
                                    function0.invoke();
                                }
                            });
                        }
                        break;
                    default:
                        SnapshotViewBindingRoot snapshotViewBindingRoot2 = this.f$0;
                        snapshotViewBindingRoot2.invalidatedBindings.plusAssign((SnapshotViewBinding) obj);
                        if (!snapshotViewBindingRoot2.isFrameScheduled) {
                            snapshotViewBindingRoot2.choreographer.postFrameCallback(snapshotViewBindingRoot2.frameCallback);
                            snapshotViewBindingRoot2.isFrameScheduled = true;
                        }
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        final int i2 = 1;
        this.onBindingChanged = new Function1(this) { // from class: com.android.systemui.lifecycle.SnapshotViewBindingRoot$$ExternalSyntheticLambda0
            public final /* synthetic */ SnapshotViewBindingRoot f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                switch (i2) {
                    case 0:
                        final Function0 function0 = (Function0) obj;
                        Looper looperMyLooper = Looper.myLooper();
                        SnapshotViewBindingRoot snapshotViewBindingRoot = this.f$0;
                        if (looperMyLooper == snapshotViewBindingRoot.handler.getLooper()) {
                            function0.invoke();
                        } else {
                            snapshotViewBindingRoot.handler.post(new Runnable() { // from class: com.android.systemui.lifecycle.SnapshotViewBindingKt$sam$java_lang_Runnable$0
                                @Override // java.lang.Runnable
                                public final /* synthetic */ void run() {
                                    function0.invoke();
                                }
                            });
                        }
                        break;
                    default:
                        SnapshotViewBindingRoot snapshotViewBindingRoot2 = this.f$0;
                        snapshotViewBindingRoot2.invalidatedBindings.plusAssign((SnapshotViewBinding) obj);
                        if (!snapshotViewBindingRoot2.isFrameScheduled) {
                            snapshotViewBindingRoot2.choreographer.postFrameCallback(snapshotViewBindingRoot2.frameCallback);
                            snapshotViewBindingRoot2.isFrameScheduled = true;
                        }
                        break;
                }
                return Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0077  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void access$bindInvalidatedBindings(SnapshotViewBindingRoot snapshotViewBindingRoot) {
        int i;
        SnapshotViewBindingRoot snapshotViewBindingRoot2 = snapshotViewBindingRoot;
        snapshotViewBindingRoot2.getClass();
        Snapshot.Companion.getClass();
        MutableSnapshot mutableSnapshotTakeMutableSnapshot = Snapshot.Companion.takeMutableSnapshot(null, null);
        try {
            Snapshot snapshotMakeCurrent = mutableSnapshotTakeMutableSnapshot.makeCurrent();
            try {
                MutableScatterSet mutableScatterSet = snapshotViewBindingRoot2.invalidatedBindings;
                Object[] objArr = mutableScatterSet.elements;
                long[] jArr = mutableScatterSet.metadata;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i2 = 0;
                    while (true) {
                        long j = jArr[i2];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i3 = 8;
                            int i4 = 8 - ((~(i2 - length)) >>> 31);
                            int i5 = 0;
                            while (i5 < i4) {
                                if ((255 & j) < 128) {
                                    int i6 = (i2 << 3) + i5;
                                    SnapshotViewBinding snapshotViewBinding = (SnapshotViewBinding) objArr[i6];
                                    try {
                                        i = i3;
                                        try {
                                            snapshotViewBindingRoot2.observer.observeReads(snapshotViewBinding, snapshotViewBindingRoot2.onBindingChanged, snapshotViewBinding.performBind);
                                        } catch (Throwable th) {
                                            th = th;
                                            snapshotViewBinding.onError.mo781invoke(th);
                                            mutableScatterSet.removeElementAt(i6);
                                            j >>= i;
                                            i5++;
                                            snapshotViewBindingRoot2 = snapshotViewBindingRoot;
                                            i3 = i;
                                        }
                                    } catch (Throwable th2) {
                                        th = th2;
                                        i = i3;
                                    }
                                    mutableScatterSet.removeElementAt(i6);
                                } else {
                                    i = i3;
                                }
                                j >>= i;
                                i5++;
                                snapshotViewBindingRoot2 = snapshotViewBindingRoot;
                                i3 = i;
                            }
                            if (i4 != i3) {
                                break;
                            }
                            if (i2 == length) {
                                break;
                            }
                            i2++;
                            snapshotViewBindingRoot2 = snapshotViewBindingRoot;
                        }
                    }
                }
                Unit unit = Unit.INSTANCE;
                Snapshot.restoreCurrent(snapshotMakeCurrent);
                mutableSnapshotTakeMutableSnapshot.apply().check();
            } catch (Throwable th3) {
                Snapshot.restoreCurrent(snapshotMakeCurrent);
                throw th3;
            }
        } finally {
        }
    }
}
