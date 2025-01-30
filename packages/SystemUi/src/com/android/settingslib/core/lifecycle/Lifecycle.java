package com.android.settingslib.core.lifecycle;

import android.os.Trace;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.OnLifecycleEvent;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.utils.ThreadUtils;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Lifecycle extends LifecycleRegistry {
    public final List mObservers;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.settingslib.core.lifecycle.Lifecycle$1 */
    public abstract /* synthetic */ class AbstractC09221 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$lifecycle$Lifecycle$Event;

        static {
            int[] iArr = new int[Lifecycle.Event.values().length];
            $SwitchMap$androidx$lifecycle$Lifecycle$Event = iArr;
            try {
                iArr[Lifecycle.Event.ON_CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_START.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_RESUME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_PAUSE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_STOP.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_DESTROY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_ANY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    class LifecycleProxy implements androidx.lifecycle.LifecycleObserver {
        public /* synthetic */ LifecycleProxy(Lifecycle lifecycle, int i) {
            this();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        public void onLifecycleEvent(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            int i = AbstractC09221.$SwitchMap$androidx$lifecycle$Lifecycle$Event[event.ordinal()];
            int i2 = 0;
            Lifecycle lifecycle = Lifecycle.this;
            switch (i) {
                case 2:
                    ArrayList arrayList = (ArrayList) lifecycle.mObservers;
                    int size = arrayList.size();
                    while (i2 < size) {
                        LifecycleObserver lifecycleObserver = (LifecycleObserver) arrayList.get(i2);
                        if (lifecycleObserver instanceof OnStart) {
                            Trace.traceBegin(1L, lifecycleObserver.getClass().getSimpleName().concat("#OnStart"));
                            ((OnStart) lifecycleObserver).onStart();
                            Trace.traceEnd(1L);
                        }
                        i2++;
                    }
                    break;
                case 3:
                    ArrayList arrayList2 = (ArrayList) lifecycle.mObservers;
                    int size2 = arrayList2.size();
                    while (i2 < size2) {
                        i2++;
                    }
                    break;
                case 4:
                    ArrayList arrayList3 = (ArrayList) lifecycle.mObservers;
                    int size3 = arrayList3.size();
                    while (i2 < size3) {
                        i2++;
                    }
                    break;
                case 5:
                    ArrayList arrayList4 = (ArrayList) lifecycle.mObservers;
                    int size4 = arrayList4.size();
                    while (i2 < size4) {
                        LifecycleObserver lifecycleObserver2 = (LifecycleObserver) arrayList4.get(i2);
                        if (lifecycleObserver2 instanceof OnStop) {
                            ((OnStop) lifecycleObserver2).onStop();
                        }
                        i2++;
                    }
                    break;
                case 6:
                    ArrayList arrayList5 = (ArrayList) lifecycle.mObservers;
                    int size5 = arrayList5.size();
                    while (i2 < size5) {
                        LifecycleObserver lifecycleObserver3 = (LifecycleObserver) arrayList5.get(i2);
                        if (lifecycleObserver3 instanceof OnDestroy) {
                            ((OnDestroy) lifecycleObserver3).onDestroy();
                        }
                        i2++;
                    }
                    break;
                case 7:
                    Log.wtf("LifecycleObserver", "Should not receive an 'ANY' event!");
                    break;
            }
        }

        private LifecycleProxy() {
        }
    }

    public Lifecycle(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
        this.mObservers = new ArrayList();
        addObserver(new LifecycleProxy(this, 0));
    }

    @Override // androidx.lifecycle.LifecycleRegistry, androidx.lifecycle.Lifecycle
    public final void addObserver(androidx.lifecycle.LifecycleObserver lifecycleObserver) {
        if (!ThreadUtils.isMainThread()) {
            throw new RuntimeException("Must be called on the UI thread");
        }
        super.addObserver(lifecycleObserver);
        if (lifecycleObserver instanceof LifecycleObserver) {
            ((ArrayList) this.mObservers).add((LifecycleObserver) lifecycleObserver);
        }
    }

    @Override // androidx.lifecycle.LifecycleRegistry, androidx.lifecycle.Lifecycle
    public final void removeObserver(androidx.lifecycle.LifecycleObserver lifecycleObserver) {
        if (!ThreadUtils.isMainThread()) {
            throw new RuntimeException("Must be called on the UI thread");
        }
        super.removeObserver(lifecycleObserver);
        if (lifecycleObserver instanceof LifecycleObserver) {
            ((ArrayList) this.mObservers).remove(lifecycleObserver);
        }
    }
}
