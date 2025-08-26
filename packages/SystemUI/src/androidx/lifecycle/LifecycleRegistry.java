package androidx.lifecycle;

import android.os.Looper;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public class LifecycleRegistry extends Lifecycle {
    public static final Companion Companion = new Companion(null);
    public final StateFlowImpl _currentStateFlow;
    public int addingObserverCounter;
    public final boolean enforceMainThread;
    public boolean handlingEvent;
    public final WeakReference lifecycleOwner;
    public boolean newEventOccurred;
    public FastSafeIterableMap observerMap;
    public final ArrayList parentStates;
    public Lifecycle.State state;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class ObserverWithState {
        public final LifecycleEventObserver lifecycleObserver;
        public Lifecycle.State state;

        public ObserverWithState(LifecycleObserver lifecycleObserver, Lifecycle.State state) {
            LifecycleEventObserver reflectiveGenericLifecycleObserver;
            lifecycleObserver.getClass();
            Lifecycling lifecycling = Lifecycling.INSTANCE;
            boolean z = lifecycleObserver instanceof LifecycleEventObserver;
            boolean z2 = lifecycleObserver instanceof DefaultLifecycleObserver;
            if (z && z2) {
                reflectiveGenericLifecycleObserver = new DefaultLifecycleObserverAdapter((DefaultLifecycleObserver) lifecycleObserver, (LifecycleEventObserver) lifecycleObserver);
            } else if (z2) {
                reflectiveGenericLifecycleObserver = new DefaultLifecycleObserverAdapter((DefaultLifecycleObserver) lifecycleObserver, null);
            } else if (z) {
                reflectiveGenericLifecycleObserver = (LifecycleEventObserver) lifecycleObserver;
            } else {
                Class<?> cls = lifecycleObserver.getClass();
                Lifecycling.INSTANCE.getClass();
                if (Lifecycling.getObserverConstructorType(cls) == 2) {
                    Object obj = ((HashMap) Lifecycling.classToAdapters).get(cls);
                    obj.getClass();
                    List list = (List) obj;
                    if (list.size() == 1) {
                        Lifecycling.createGeneratedAdapter((Constructor) list.get(0), lifecycleObserver);
                        reflectiveGenericLifecycleObserver = new SingleGeneratedAdapterObserver(null);
                    } else {
                        int size = list.size();
                        GeneratedAdapter[] generatedAdapterArr = new GeneratedAdapter[size];
                        for (int i = 0; i < size; i++) {
                            Lifecycling.createGeneratedAdapter((Constructor) list.get(i), lifecycleObserver);
                            generatedAdapterArr[i] = null;
                        }
                        reflectiveGenericLifecycleObserver = new CompositeGeneratedAdaptersObserver(generatedAdapterArr);
                    }
                } else {
                    reflectiveGenericLifecycleObserver = new ReflectiveGenericLifecycleObserver(lifecycleObserver);
                }
            }
            this.lifecycleObserver = reflectiveGenericLifecycleObserver;
            this.state = state;
        }

        public final void dispatchEvent(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            Lifecycle.State targetState = event.getTargetState();
            Companion companion = LifecycleRegistry.Companion;
            Lifecycle.State state = this.state;
            companion.getClass();
            if (targetState != null && targetState.compareTo(state) < 0) {
                state = targetState;
            }
            this.state = state;
            this.lifecycleObserver.onStateChanged(lifecycleOwner, event);
            this.state = targetState;
        }
    }

    public /* synthetic */ LifecycleRegistry(LifecycleOwner lifecycleOwner, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(lifecycleOwner, z);
    }

    public static final LifecycleRegistry createUnsafe(LifecycleOwner lifecycleOwner) {
        Companion.getClass();
        return new LifecycleRegistry(lifecycleOwner, false, null);
    }

    @Override // androidx.lifecycle.Lifecycle
    public void addObserver(LifecycleObserver lifecycleObserver) {
        LifecycleOwner lifecycleOwner;
        enforceMainThreadIfNeeded("addObserver");
        Lifecycle.State state = this.state;
        Lifecycle.State state2 = Lifecycle.State.DESTROYED;
        if (state != state2) {
            state2 = Lifecycle.State.INITIALIZED;
        }
        ObserverWithState observerWithState = new ObserverWithState(lifecycleObserver, state2);
        if (((ObserverWithState) this.observerMap.putIfAbsent(lifecycleObserver, observerWithState)) == null && (lifecycleOwner = (LifecycleOwner) this.lifecycleOwner.get()) != null) {
            boolean z = this.addingObserverCounter != 0 || this.handlingEvent;
            Lifecycle.State stateCalculateTargetState = calculateTargetState(lifecycleObserver);
            this.addingObserverCounter++;
            while (observerWithState.state.compareTo(stateCalculateTargetState) < 0 && this.observerMap.mHashMap.containsKey(lifecycleObserver)) {
                this.parentStates.add(observerWithState.state);
                Lifecycle.Event.Companion companion = Lifecycle.Event.Companion;
                Lifecycle.State state3 = observerWithState.state;
                companion.getClass();
                int i = Lifecycle.Event.Companion.WhenMappings.$EnumSwitchMapping$0[state3.ordinal()];
                Lifecycle.Event event = i != 1 ? i != 2 ? i != 5 ? null : Lifecycle.Event.ON_CREATE : Lifecycle.Event.ON_RESUME : Lifecycle.Event.ON_START;
                if (event == null) {
                    throw new IllegalStateException("no event up from " + observerWithState.state);
                }
                observerWithState.dispatchEvent(lifecycleOwner, event);
                ArrayList arrayList = this.parentStates;
                arrayList.remove(arrayList.size() - 1);
                stateCalculateTargetState = calculateTargetState(lifecycleObserver);
            }
            if (!z) {
                sync();
            }
            this.addingObserverCounter--;
        }
    }

    public final Lifecycle.State calculateTargetState(LifecycleObserver lifecycleObserver) {
        ObserverWithState observerWithState;
        FastSafeIterableMap fastSafeIterableMap = this.observerMap;
        SafeIterableMap.Entry entry = fastSafeIterableMap.mHashMap.containsKey(lifecycleObserver) ? ((SafeIterableMap.Entry) fastSafeIterableMap.mHashMap.get(lifecycleObserver)).mPrevious : null;
        Lifecycle.State state = (entry == null || (observerWithState = (ObserverWithState) entry.getValue()) == null) ? null : observerWithState.state;
        Lifecycle.State state2 = this.parentStates.isEmpty() ? null : (Lifecycle.State) AlertController$$ExternalSyntheticOutline0.m(1, this.parentStates);
        Lifecycle.State state3 = this.state;
        Companion.getClass();
        if (state == null || state.compareTo(state3) >= 0) {
            state = state3;
        }
        return (state2 == null || state2.compareTo(state) >= 0) ? state : state2;
    }

    public final void enforceMainThreadIfNeeded(String str) {
        if (this.enforceMainThread) {
            ArchTaskExecutor.getInstance().mDelegate.getClass();
            if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
                throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Method ", str, " must be called on the main thread").toString());
            }
        }
    }

    @Override // androidx.lifecycle.Lifecycle
    public final Lifecycle.State getCurrentState() {
        return this.state;
    }

    public final void handleLifecycleEvent(Lifecycle.Event event) {
        enforceMainThreadIfNeeded("handleLifecycleEvent");
        moveToState(event.getTargetState());
    }

    public final void moveToState(Lifecycle.State state) {
        Lifecycle.State state2 = this.state;
        if (state2 == state) {
            return;
        }
        if (state2 == Lifecycle.State.INITIALIZED && state == Lifecycle.State.DESTROYED) {
            throw new IllegalStateException(("no event down from " + this.state + " in component " + this.lifecycleOwner.get()).toString());
        }
        this.state = state;
        if (this.handlingEvent || this.addingObserverCounter != 0) {
            this.newEventOccurred = true;
            return;
        }
        this.handlingEvent = true;
        sync();
        this.handlingEvent = false;
        if (this.state == Lifecycle.State.DESTROYED) {
            this.observerMap = new FastSafeIterableMap();
        }
    }

    @Override // androidx.lifecycle.Lifecycle
    public void removeObserver(LifecycleObserver lifecycleObserver) {
        enforceMainThreadIfNeeded("removeObserver");
        this.observerMap.remove(lifecycleObserver);
    }

    public final void setCurrentState(Lifecycle.State state) {
        enforceMainThreadIfNeeded("setCurrentState");
        moveToState(state);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0034, code lost:
    
        r10.newEventOccurred = false;
        r10._currentStateFlow.setValue(r10.state);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x003d, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void sync() {
        LifecycleOwner lifecycleOwner = (LifecycleOwner) this.lifecycleOwner.get();
        if (lifecycleOwner == null) {
            throw new IllegalStateException("LifecycleOwner of this LifecycleRegistry is already garbage collected. It is too late to change lifecycle state.");
        }
        while (true) {
            FastSafeIterableMap fastSafeIterableMap = this.observerMap;
            if (fastSafeIterableMap.mSize != 0) {
                SafeIterableMap.Entry entry = fastSafeIterableMap.mStart;
                entry.getClass();
                Lifecycle.State state = ((ObserverWithState) entry.getValue()).state;
                SafeIterableMap.Entry entry2 = this.observerMap.mEnd;
                entry2.getClass();
                Lifecycle.State state2 = ((ObserverWithState) entry2.getValue()).state;
                if (state == state2 && this.state == state2) {
                    break;
                }
                this.newEventOccurred = false;
                Lifecycle.State state3 = this.state;
                SafeIterableMap.Entry entry3 = this.observerMap.mStart;
                entry3.getClass();
                if (state3.compareTo(((ObserverWithState) entry3.getValue()).state) < 0) {
                    FastSafeIterableMap fastSafeIterableMap2 = this.observerMap;
                    fastSafeIterableMap2.getClass();
                    SafeIterableMap.DescendingIterator descendingIterator = new SafeIterableMap.DescendingIterator(fastSafeIterableMap2.mEnd, fastSafeIterableMap2.mStart);
                    fastSafeIterableMap2.mIterators.put(descendingIterator, Boolean.FALSE);
                    while (descendingIterator.hasNext() && !this.newEventOccurred) {
                        Map.Entry entry4 = (Map.Entry) descendingIterator.next();
                        LifecycleObserver lifecycleObserver = (LifecycleObserver) entry4.getKey();
                        ObserverWithState observerWithState = (ObserverWithState) entry4.getValue();
                        while (observerWithState.state.compareTo(this.state) > 0 && !this.newEventOccurred && this.observerMap.mHashMap.containsKey(lifecycleObserver)) {
                            Lifecycle.Event.Companion companion = Lifecycle.Event.Companion;
                            Lifecycle.State state4 = observerWithState.state;
                            companion.getClass();
                            int i = Lifecycle.Event.Companion.WhenMappings.$EnumSwitchMapping$0[state4.ordinal()];
                            Lifecycle.Event event = i != 1 ? i != 2 ? i != 3 ? null : Lifecycle.Event.ON_PAUSE : Lifecycle.Event.ON_STOP : Lifecycle.Event.ON_DESTROY;
                            if (event == null) {
                                throw new IllegalStateException("no event down from " + observerWithState.state);
                            }
                            this.parentStates.add(event.getTargetState());
                            observerWithState.dispatchEvent(lifecycleOwner, event);
                            ArrayList arrayList = this.parentStates;
                            arrayList.remove(arrayList.size() - 1);
                        }
                    }
                }
                SafeIterableMap.Entry entry5 = this.observerMap.mEnd;
                if (!this.newEventOccurred && entry5 != null && this.state.compareTo(((ObserverWithState) entry5.getValue()).state) > 0) {
                    FastSafeIterableMap fastSafeIterableMap3 = this.observerMap;
                    fastSafeIterableMap3.getClass();
                    SafeIterableMap.IteratorWithAdditions iteratorWithAdditions = new SafeIterableMap.IteratorWithAdditions();
                    fastSafeIterableMap3.mIterators.put(iteratorWithAdditions, Boolean.FALSE);
                    while (iteratorWithAdditions.hasNext() && !this.newEventOccurred) {
                        Map.Entry entry6 = (Map.Entry) iteratorWithAdditions.next();
                        LifecycleObserver lifecycleObserver2 = (LifecycleObserver) entry6.getKey();
                        ObserverWithState observerWithState2 = (ObserverWithState) entry6.getValue();
                        while (observerWithState2.state.compareTo(this.state) < 0 && !this.newEventOccurred && this.observerMap.mHashMap.containsKey(lifecycleObserver2)) {
                            this.parentStates.add(observerWithState2.state);
                            Lifecycle.Event.Companion companion2 = Lifecycle.Event.Companion;
                            Lifecycle.State state5 = observerWithState2.state;
                            companion2.getClass();
                            int i2 = Lifecycle.Event.Companion.WhenMappings.$EnumSwitchMapping$0[state5.ordinal()];
                            Lifecycle.Event event2 = i2 != 1 ? i2 != 2 ? i2 != 5 ? null : Lifecycle.Event.ON_CREATE : Lifecycle.Event.ON_RESUME : Lifecycle.Event.ON_START;
                            if (event2 == null) {
                                throw new IllegalStateException("no event up from " + observerWithState2.state);
                            }
                            observerWithState2.dispatchEvent(lifecycleOwner, event2);
                            ArrayList arrayList2 = this.parentStates;
                            arrayList2.remove(arrayList2.size() - 1);
                        }
                    }
                }
            } else {
                break;
            }
        }
    }

    private LifecycleRegistry(LifecycleOwner lifecycleOwner, boolean z) {
        this.enforceMainThread = z;
        this.observerMap = new FastSafeIterableMap();
        Lifecycle.State state = Lifecycle.State.INITIALIZED;
        this.state = state;
        this.parentStates = new ArrayList();
        this.lifecycleOwner = new WeakReference(lifecycleOwner);
        this._currentStateFlow = StateFlowKt.MutableStateFlow(state);
    }

    public LifecycleRegistry(LifecycleOwner lifecycleOwner) {
        this(lifecycleOwner, true);
    }
}
