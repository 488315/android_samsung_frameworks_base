package com.android.systemui.statusbar.notification.collection;

import android.os.Trace;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.lifecycle.Observer;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.util.ListenerSet;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        return !Intrinsics.areEqual(this.atomicValue.getAndSet(obj), obj) ? new Function0() { // from class: com.android.systemui.statusbar.notification.collection.NotifLiveDataImpl$setValueAndProvideDispatcher$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                if (!NotifLiveDataImpl.this.syncObservers.isEmpty()) {
                    String m = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("NotifLiveData(", NotifLiveDataImpl.this.name, ").dispatchToSyncObservers");
                    NotifLiveDataImpl notifLiveDataImpl = NotifLiveDataImpl.this;
                    Object obj2 = obj;
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
                if (!NotifLiveDataImpl.this.asyncObservers.isEmpty()) {
                    final NotifLiveDataImpl notifLiveDataImpl2 = NotifLiveDataImpl.this;
                    notifLiveDataImpl2.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.NotifLiveDataImpl$setValueAndProvideDispatcher$1.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotifLiveDataImpl notifLiveDataImpl3 = NotifLiveDataImpl.this;
                            Object obj3 = notifLiveDataImpl3.atomicValue.get();
                            if (Intrinsics.areEqual(notifLiveDataImpl3.lastAsyncValue, obj3)) {
                                return;
                            }
                            notifLiveDataImpl3.lastAsyncValue = obj3;
                            String m2 = ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("NotifLiveData("), notifLiveDataImpl3.name, ").dispatchToAsyncObservers");
                            boolean isEnabled2 = Trace.isEnabled();
                            if (isEnabled2) {
                                TraceUtilsKt.beginSlice(m2);
                            }
                            try {
                                Iterator<E> it2 = notifLiveDataImpl3.asyncObservers.iterator();
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
        } : new Function0() { // from class: com.android.systemui.statusbar.notification.collection.NotifLiveDataImpl$setValueAndProvideDispatcher$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        };
    }
}
