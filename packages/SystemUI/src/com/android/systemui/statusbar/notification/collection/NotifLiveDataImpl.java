package com.android.systemui.statusbar.notification.collection;

import android.os.Trace;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.lifecycle.Observer;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.util.ListenerSet;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotifLiveDataImpl implements PipelineDumpable {
    public final AtomicReference atomicValue;
    public Object lastAsyncValue;
    public final Executor mainExecutor;
    public final String name;
    public final ListenerSet syncObservers = new ListenerSet();
    public final ListenerSet asyncObservers = new ListenerSet();

    public NotifLiveDataImpl(String str, Object obj, Executor executor) {
        this.name = str;
        this.mainExecutor = executor;
        this.atomicValue = new AtomicReference(obj);
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public final void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(this.syncObservers, "syncObservers");
        pipelineDumper.dump(this.asyncObservers, "asyncObservers");
    }

    public final Function0 setValueAndProvideDispatcher(final Object obj) {
        return !Intrinsics.areEqual(this.atomicValue.getAndSet(obj), obj) ? new Function0() { // from class: com.android.systemui.statusbar.notification.collection.NotifLiveDataImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj2 = obj;
                final NotifLiveDataImpl notifLiveDataImpl = NotifLiveDataImpl.this;
                if (!notifLiveDataImpl.syncObservers.isEmpty()) {
                    String m = TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("NotifLiveData("), notifLiveDataImpl.name, ").dispatchToSyncObservers");
                    boolean isEnabled = Trace.isEnabled();
                    if (isEnabled) {
                        TraceUtilsKt.beginSlice(m);
                    }
                    try {
                        Iterator<E> it = notifLiveDataImpl.syncObservers.iterator();
                        while (it.hasNext()) {
                            ((Observer) it.next()).onChanged(obj2);
                        }
                        Unit unit = Unit.INSTANCE;
                        if (isEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                    } catch (Throwable th) {
                        if (isEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                        throw th;
                    }
                }
                if (!notifLiveDataImpl.asyncObservers.isEmpty()) {
                    notifLiveDataImpl.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.NotifLiveDataImpl$setValueAndProvideDispatcher$1$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotifLiveDataImpl notifLiveDataImpl2 = NotifLiveDataImpl.this;
                            Object obj3 = notifLiveDataImpl2.atomicValue.get();
                            if (Intrinsics.areEqual(notifLiveDataImpl2.lastAsyncValue, obj3)) {
                                return;
                            }
                            notifLiveDataImpl2.lastAsyncValue = obj3;
                            String m2 = TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("NotifLiveData("), notifLiveDataImpl2.name, ").dispatchToAsyncObservers");
                            boolean isEnabled2 = Trace.isEnabled();
                            if (isEnabled2) {
                                TraceUtilsKt.beginSlice(m2);
                            }
                            try {
                                Iterator<E> it2 = notifLiveDataImpl2.asyncObservers.iterator();
                                while (it2.hasNext()) {
                                    ((Observer) it2.next()).onChanged(obj3);
                                }
                                Unit unit2 = Unit.INSTANCE;
                                if (isEnabled2) {
                                    TraceUtilsKt.endSlice();
                                }
                            } catch (Throwable th2) {
                                if (isEnabled2) {
                                    TraceUtilsKt.endSlice();
                                }
                                throw th2;
                            }
                        }
                    });
                }
                return Unit.INSTANCE;
            }
        } : new NotifLiveDataImpl$$ExternalSyntheticLambda1();
    }
}
