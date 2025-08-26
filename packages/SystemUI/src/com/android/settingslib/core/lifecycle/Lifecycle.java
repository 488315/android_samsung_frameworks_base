package com.android.settingslib.core.lifecycle;

import android.os.Trace;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.OnLifecycleEvent;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.wifi.WifiTracker;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class Lifecycle extends LifecycleRegistry {
    public final List mObservers;

    /* renamed from: com.android.settingslib.core.lifecycle.Lifecycle$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
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

    public class LifecycleProxy implements LifecycleObserver {
        public /* synthetic */ LifecycleProxy(Lifecycle lifecycle, int i) {
            this();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        public void onLifecycleEvent(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            int i = AnonymousClass1.$SwitchMap$androidx$lifecycle$Lifecycle$Event[event.ordinal()];
            int i2 = 0;
            Lifecycle lifecycle = Lifecycle.this;
            switch (i) {
                case 2:
                    int size = ((ArrayList) lifecycle.mObservers).size();
                    while (i2 < size) {
                        WifiTracker wifiTracker = (WifiTracker) ((ArrayList) lifecycle.mObservers).get(i2);
                        if (wifiTracker != null) {
                            Trace.traceBegin(1L, wifiTracker.getClass().getSimpleName().concat("#OnStart"));
                            wifiTracker.onStart();
                            Trace.traceEnd(1L);
                        }
                        i2++;
                    }
                    break;
                case 3:
                    int size2 = ((ArrayList) lifecycle.mObservers).size();
                    while (i2 < size2) {
                        i2++;
                    }
                    break;
                case 4:
                    int size3 = ((ArrayList) lifecycle.mObservers).size();
                    while (i2 < size3) {
                        i2++;
                    }
                    break;
                case 5:
                    int size4 = ((ArrayList) lifecycle.mObservers).size();
                    while (i2 < size4) {
                        WifiTracker wifiTracker2 = (WifiTracker) ((ArrayList) lifecycle.mObservers).get(i2);
                        if (wifiTracker2 != null) {
                            wifiTracker2.onStop();
                        }
                        i2++;
                    }
                    break;
                case 6:
                    int size5 = ((ArrayList) lifecycle.mObservers).size();
                    while (i2 < size5) {
                        WifiTracker wifiTracker3 = (WifiTracker) ((ArrayList) lifecycle.mObservers).get(i2);
                        if (wifiTracker3 != null) {
                            wifiTracker3.mWorkThread.quit();
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
    public final void addObserver(LifecycleObserver lifecycleObserver) {
        if (!ThreadUtils.isMainThread()) {
            throw new RuntimeException("Must be called on the UI thread");
        }
        super.addObserver(lifecycleObserver);
        if (lifecycleObserver instanceof WifiTracker) {
            ((ArrayList) this.mObservers).add((WifiTracker) lifecycleObserver);
        }
    }

    @Override // androidx.lifecycle.LifecycleRegistry, androidx.lifecycle.Lifecycle
    public final void removeObserver(LifecycleObserver lifecycleObserver) {
        if (!ThreadUtils.isMainThread()) {
            throw new RuntimeException("Must be called on the UI thread");
        }
        super.removeObserver(lifecycleObserver);
        if (lifecycleObserver instanceof WifiTracker) {
            ((ArrayList) this.mObservers).remove(lifecycleObserver);
        }
    }
}
