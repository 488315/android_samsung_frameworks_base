package com.android.systemui.statusbar.notification.collection;

import android.os.Trace;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.lifecycle.Observer;
import com.android.systemui.util.ListenerSet;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    public final Object getValue() {
        return this.atomicValue.get();
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
                    String m29m = PathParser$$ExternalSyntheticOutline0.m29m("NotifLiveData(", NotifLiveDataImpl.this.name, ").dispatchToSyncObservers");
                    NotifLiveDataImpl notifLiveDataImpl = NotifLiveDataImpl.this;
                    Object obj2 = obj;
                    if (Trace.isTagEnabled(4096L)) {
                        Trace.traceBegin(4096L, m29m);
                        try {
                            Iterator it = notifLiveDataImpl.syncObservers.iterator();
                            while (it.hasNext()) {
                                ((Observer) it.next()).onChanged(obj2);
                            }
                            Unit unit = Unit.INSTANCE;
                        } finally {
                            Trace.traceEnd(4096L);
                        }
                    } else {
                        Iterator it2 = notifLiveDataImpl.syncObservers.iterator();
                        while (it2.hasNext()) {
                            ((Observer) it2.next()).onChanged(obj2);
                        }
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
                            String m16m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("NotifLiveData("), notifLiveDataImpl3.name, ").dispatchToAsyncObservers");
                            boolean isTagEnabled = Trace.isTagEnabled(4096L);
                            ListenerSet listenerSet = notifLiveDataImpl3.asyncObservers;
                            if (!isTagEnabled) {
                                Iterator it3 = listenerSet.iterator();
                                while (it3.hasNext()) {
                                    ((Observer) it3.next()).onChanged(obj3);
                                }
                                return;
                            }
                            Trace.traceBegin(4096L, m16m);
                            try {
                                Iterator it4 = listenerSet.iterator();
                                while (it4.hasNext()) {
                                    ((Observer) it4.next()).onChanged(obj3);
                                }
                                Unit unit2 = Unit.INSTANCE;
                            } finally {
                                Trace.traceEnd(4096L);
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
