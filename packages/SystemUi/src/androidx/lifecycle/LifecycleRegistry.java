package androidx.lifecycle;

import android.os.Looper;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class LifecycleRegistry extends Lifecycle {
    public int mAddingObserverCounter;
    public final boolean mEnforceMainThread;
    public boolean mHandlingEvent;
    public final WeakReference mLifecycleOwner;
    public boolean mNewEventOccurred;
    public FastSafeIterableMap mObserverMap;
    public final ArrayList mParentStates;
    public Lifecycle.State mState;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ObserverWithState {
        public final LifecycleEventObserver mLifecycleObserver;
        public Lifecycle.State mState;

        public ObserverWithState(LifecycleObserver lifecycleObserver, Lifecycle.State state) {
            LifecycleEventObserver reflectiveGenericLifecycleObserver;
            Map map = Lifecycling.sCallbackCache;
            boolean z = lifecycleObserver instanceof LifecycleEventObserver;
            boolean z2 = lifecycleObserver instanceof FullLifecycleObserver;
            if (z && z2) {
                reflectiveGenericLifecycleObserver = new FullLifecycleObserverAdapter((FullLifecycleObserver) lifecycleObserver, (LifecycleEventObserver) lifecycleObserver);
            } else if (z2) {
                reflectiveGenericLifecycleObserver = new FullLifecycleObserverAdapter((FullLifecycleObserver) lifecycleObserver, null);
            } else if (z) {
                reflectiveGenericLifecycleObserver = (LifecycleEventObserver) lifecycleObserver;
            } else {
                Class<?> cls = lifecycleObserver.getClass();
                if (Lifecycling.getObserverConstructorType(cls) == 2) {
                    List list = (List) ((HashMap) Lifecycling.sClassToAdapters).get(cls);
                    if (list.size() == 1) {
                        Lifecycling.createGeneratedAdapter((Constructor) list.get(0), lifecycleObserver);
                        reflectiveGenericLifecycleObserver = new SingleGeneratedAdapterObserver(null);
                    } else {
                        GeneratedAdapter[] generatedAdapterArr = new GeneratedAdapter[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            Lifecycling.createGeneratedAdapter((Constructor) list.get(i), lifecycleObserver);
                            generatedAdapterArr[i] = null;
                        }
                        reflectiveGenericLifecycleObserver = new CompositeGeneratedAdaptersObserver(generatedAdapterArr);
                    }
                } else {
                    reflectiveGenericLifecycleObserver = new ReflectiveGenericLifecycleObserver(lifecycleObserver);
                }
            }
            this.mLifecycleObserver = reflectiveGenericLifecycleObserver;
            this.mState = state;
        }

        public final void dispatchEvent(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            Lifecycle.State targetState = event.getTargetState();
            Lifecycle.State state = this.mState;
            if (targetState != null && targetState.compareTo(state) < 0) {
                state = targetState;
            }
            this.mState = state;
            this.mLifecycleObserver.onStateChanged(lifecycleOwner, event);
            this.mState = targetState;
        }
    }

    public LifecycleRegistry(LifecycleOwner lifecycleOwner) {
        this(lifecycleOwner, true);
    }

    public static LifecycleRegistry createUnsafe(LifecycleOwner lifecycleOwner) {
        return new LifecycleRegistry(lifecycleOwner, false);
    }

    @Override // androidx.lifecycle.Lifecycle
    public void addObserver(LifecycleObserver lifecycleObserver) {
        LifecycleOwner lifecycleOwner;
        enforceMainThreadIfNeeded("addObserver");
        Lifecycle.State state = this.mState;
        Lifecycle.State state2 = Lifecycle.State.DESTROYED;
        if (state != state2) {
            state2 = Lifecycle.State.INITIALIZED;
        }
        ObserverWithState observerWithState = new ObserverWithState(lifecycleObserver, state2);
        if (((ObserverWithState) this.mObserverMap.putIfAbsent(lifecycleObserver, observerWithState)) == null && (lifecycleOwner = (LifecycleOwner) this.mLifecycleOwner.get()) != null) {
            boolean z = this.mAddingObserverCounter != 0 || this.mHandlingEvent;
            Lifecycle.State calculateTargetState = calculateTargetState(lifecycleObserver);
            this.mAddingObserverCounter++;
            while (observerWithState.mState.compareTo(calculateTargetState) < 0 && this.mObserverMap.mHashMap.containsKey(lifecycleObserver)) {
                Lifecycle.State state3 = observerWithState.mState;
                ArrayList arrayList = this.mParentStates;
                arrayList.add(state3);
                Lifecycle.State state4 = observerWithState.mState;
                Lifecycle.Event event = Lifecycle.Event.ON_CREATE;
                int i = Lifecycle.AbstractC03001.$SwitchMap$androidx$lifecycle$Lifecycle$State[state4.ordinal()];
                Lifecycle.Event event2 = i != 1 ? i != 2 ? i != 5 ? null : Lifecycle.Event.ON_CREATE : Lifecycle.Event.ON_RESUME : Lifecycle.Event.ON_START;
                if (event2 == null) {
                    throw new IllegalStateException("no event up from " + observerWithState.mState);
                }
                observerWithState.dispatchEvent(lifecycleOwner, event2);
                arrayList.remove(arrayList.size() - 1);
                calculateTargetState = calculateTargetState(lifecycleObserver);
            }
            if (!z) {
                sync();
            }
            this.mAddingObserverCounter--;
        }
    }

    public final Lifecycle.State calculateTargetState(LifecycleObserver lifecycleObserver) {
        FastSafeIterableMap fastSafeIterableMap = this.mObserverMap;
        SafeIterableMap.Entry entry = fastSafeIterableMap.mHashMap.containsKey(lifecycleObserver) ? ((SafeIterableMap.Entry) fastSafeIterableMap.mHashMap.get(lifecycleObserver)).mPrevious : null;
        Lifecycle.State state = entry != null ? ((ObserverWithState) entry.mValue).mState : null;
        ArrayList arrayList = this.mParentStates;
        Lifecycle.State state2 = arrayList.isEmpty() ? null : (Lifecycle.State) arrayList.get(arrayList.size() - 1);
        Lifecycle.State state3 = this.mState;
        if (state == null || state.compareTo(state3) >= 0) {
            state = state3;
        }
        return (state2 == null || state2.compareTo(state) >= 0) ? state : state2;
    }

    public final void enforceMainThreadIfNeeded(String str) {
        if (this.mEnforceMainThread) {
            ArchTaskExecutor.getInstance().mDelegate.getClass();
            if (!(Looper.getMainLooper().getThread() == Thread.currentThread())) {
                throw new IllegalStateException(PathParser$$ExternalSyntheticOutline0.m29m("Method ", str, " must be called on the main thread"));
            }
        }
    }

    @Override // androidx.lifecycle.Lifecycle
    public final Lifecycle.State getCurrentState() {
        return this.mState;
    }

    public final void handleLifecycleEvent(Lifecycle.Event event) {
        enforceMainThreadIfNeeded("handleLifecycleEvent");
        moveToState(event.getTargetState());
    }

    public final void moveToState(Lifecycle.State state) {
        Lifecycle.State state2 = this.mState;
        if (state2 == state) {
            return;
        }
        if (state2 == Lifecycle.State.INITIALIZED && state == Lifecycle.State.DESTROYED) {
            throw new IllegalStateException("no event down from " + this.mState + " in component " + this.mLifecycleOwner.get());
        }
        this.mState = state;
        if (this.mHandlingEvent || this.mAddingObserverCounter != 0) {
            this.mNewEventOccurred = true;
            return;
        }
        this.mHandlingEvent = true;
        sync();
        this.mHandlingEvent = false;
        if (this.mState == Lifecycle.State.DESTROYED) {
            this.mObserverMap = new FastSafeIterableMap();
        }
    }

    @Override // androidx.lifecycle.Lifecycle
    public void removeObserver(LifecycleObserver lifecycleObserver) {
        enforceMainThreadIfNeeded("removeObserver");
        this.mObserverMap.remove(lifecycleObserver);
    }

    public final void setCurrentState(Lifecycle.State state) {
        enforceMainThreadIfNeeded("setCurrentState");
        moveToState(state);
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0059, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0100, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x017c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void sync() {
        boolean z;
        LifecycleOwner lifecycleOwner = (LifecycleOwner) this.mLifecycleOwner.get();
        if (lifecycleOwner == null) {
            throw new IllegalStateException("LifecycleOwner of this LifecycleRegistry is alreadygarbage collected. It is too late to change lifecycle state.");
        }
        while (true) {
            FastSafeIterableMap fastSafeIterableMap = this.mObserverMap;
            if (fastSafeIterableMap.mSize != 0) {
                Lifecycle.State state = ((ObserverWithState) fastSafeIterableMap.mStart.mValue).mState;
                Lifecycle.State state2 = ((ObserverWithState) fastSafeIterableMap.mEnd.mValue).mState;
                if (state != state2 || this.mState != state2) {
                    z = false;
                    if (!z) {
                        this.mNewEventOccurred = false;
                        return;
                    }
                    this.mNewEventOccurred = false;
                    int compareTo = this.mState.compareTo(((ObserverWithState) fastSafeIterableMap.mStart.mValue).mState);
                    ArrayList arrayList = this.mParentStates;
                    if (compareTo < 0) {
                        FastSafeIterableMap fastSafeIterableMap2 = this.mObserverMap;
                        fastSafeIterableMap2.getClass();
                        SafeIterableMap.DescendingIterator descendingIterator = new SafeIterableMap.DescendingIterator(fastSafeIterableMap2.mEnd, fastSafeIterableMap2.mStart);
                        fastSafeIterableMap2.mIterators.put(descendingIterator, Boolean.FALSE);
                        while (descendingIterator.hasNext() && !this.mNewEventOccurred) {
                            Map.Entry entry = (Map.Entry) descendingIterator.next();
                            ObserverWithState observerWithState = (ObserverWithState) entry.getValue();
                            while (observerWithState.mState.compareTo(this.mState) > 0 && !this.mNewEventOccurred) {
                                if (this.mObserverMap.mHashMap.containsKey((LifecycleObserver) entry.getKey())) {
                                    Lifecycle.State state3 = observerWithState.mState;
                                    Lifecycle.Event event = Lifecycle.Event.ON_CREATE;
                                    int i = Lifecycle.AbstractC03001.$SwitchMap$androidx$lifecycle$Lifecycle$State[state3.ordinal()];
                                    Lifecycle.Event event2 = i != 1 ? i != 2 ? i != 3 ? null : Lifecycle.Event.ON_PAUSE : Lifecycle.Event.ON_STOP : Lifecycle.Event.ON_DESTROY;
                                    if (event2 == null) {
                                        throw new IllegalStateException("no event down from " + observerWithState.mState);
                                    }
                                    arrayList.add(event2.getTargetState());
                                    observerWithState.dispatchEvent(lifecycleOwner, event2);
                                    arrayList.remove(arrayList.size() - 1);
                                }
                            }
                        }
                    }
                    SafeIterableMap.Entry entry2 = this.mObserverMap.mEnd;
                    if (!this.mNewEventOccurred && entry2 != null && this.mState.compareTo(((ObserverWithState) entry2.mValue).mState) > 0) {
                        FastSafeIterableMap fastSafeIterableMap3 = this.mObserverMap;
                        fastSafeIterableMap3.getClass();
                        SafeIterableMap.IteratorWithAdditions iteratorWithAdditions = new SafeIterableMap.IteratorWithAdditions();
                        fastSafeIterableMap3.mIterators.put(iteratorWithAdditions, Boolean.FALSE);
                        while (iteratorWithAdditions.hasNext() && !this.mNewEventOccurred) {
                            Map.Entry entry3 = (Map.Entry) iteratorWithAdditions.next();
                            ObserverWithState observerWithState2 = (ObserverWithState) entry3.getValue();
                            while (observerWithState2.mState.compareTo(this.mState) < 0 && !this.mNewEventOccurred) {
                                if (this.mObserverMap.mHashMap.containsKey((LifecycleObserver) entry3.getKey())) {
                                    arrayList.add(observerWithState2.mState);
                                    Lifecycle.State state4 = observerWithState2.mState;
                                    Lifecycle.Event event3 = Lifecycle.Event.ON_CREATE;
                                    int i2 = Lifecycle.AbstractC03001.$SwitchMap$androidx$lifecycle$Lifecycle$State[state4.ordinal()];
                                    Lifecycle.Event event4 = i2 != 1 ? i2 != 2 ? i2 != 5 ? null : Lifecycle.Event.ON_CREATE : Lifecycle.Event.ON_RESUME : Lifecycle.Event.ON_START;
                                    if (event4 == null) {
                                        throw new IllegalStateException("no event up from " + observerWithState2.mState);
                                    }
                                    observerWithState2.dispatchEvent(lifecycleOwner, event4);
                                    arrayList.remove(arrayList.size() - 1);
                                }
                            }
                        }
                    }
                }
            }
            z = true;
            if (!z) {
            }
        }
    }

    private LifecycleRegistry(LifecycleOwner lifecycleOwner, boolean z) {
        this.mObserverMap = new FastSafeIterableMap();
        this.mAddingObserverCounter = 0;
        this.mHandlingEvent = false;
        this.mNewEventOccurred = false;
        this.mParentStates = new ArrayList();
        this.mLifecycleOwner = new WeakReference(lifecycleOwner);
        this.mState = Lifecycle.State.INITIALIZED;
        this.mEnforceMainThread = z;
    }
}
