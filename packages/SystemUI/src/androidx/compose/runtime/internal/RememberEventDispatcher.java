package androidx.compose.runtime.internal;

import androidx.collection.MutableIntList;
import androidx.collection.MutableScatterMap;
import androidx.collection.MutableScatterSet;
import androidx.compose.runtime.ComposeNodeLifecycleCallback;
import androidx.compose.runtime.RememberObserver;
import androidx.compose.runtime.RememberObserverHolder;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.tooling.CompositionErrorContext;
import androidx.compose.runtime.tooling.CompositionErrorContextImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RememberEventDispatcher {
    public final Set abandoning;
    public final MutableIntList afters;
    public MutableVector currentRememberingList;
    public final MutableVector leaving;
    public ArrayList nestedRemembersLists;
    public final MutableScatterMap pausedPlaceholders;
    public final List pending;
    public final MutableIntList priorities;
    public MutableScatterSet releasing;
    public final MutableVector remembering;
    public final MutableVector sideEffects;
    public final CompositionErrorContext traceContext;

    public RememberEventDispatcher(Set<RememberObserver> set, CompositionErrorContext compositionErrorContext) {
        this.abandoning = set;
        this.traceContext = compositionErrorContext;
        MutableVector mutableVector = new MutableVector(new RememberObserverHolder[16], 0);
        this.remembering = mutableVector;
        this.currentRememberingList = mutableVector;
        this.leaving = new MutableVector(new Object[16], 0);
        this.sideEffects = new MutableVector(new Function0[16], 0);
        this.pending = new ArrayList();
        this.priorities = new MutableIntList(0, 1, null);
        this.afters = new MutableIntList(0, 1, null);
    }

    public final void dispatchAbandons() {
        if (this.abandoning.isEmpty()) {
            return;
        }
        Trace.INSTANCE.getClass();
        android.os.Trace.beginSection("Compose:abandons");
        try {
            Iterator it = this.abandoning.iterator();
            while (it.hasNext()) {
                RememberObserver rememberObserver = (RememberObserver) it.next();
                it.remove();
                rememberObserver.onAbandoned();
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.INSTANCE.getClass();
            android.os.Trace.endSection();
        }
    }

    public final void dispatchRememberObservers() {
        processPendingLeaving(Integer.MIN_VALUE);
        MutableVector mutableVector = this.leaving;
        int i = mutableVector.size;
        CompositionErrorContext compositionErrorContext = this.traceContext;
        if (i != 0) {
            Trace.INSTANCE.getClass();
            android.os.Trace.beginSection("Compose:onForgotten");
            try {
                MutableScatterSet mutableScatterSet = this.releasing;
                for (int i2 = mutableVector.size - 1; -1 < i2; i2--) {
                    Object obj = mutableVector.content[i2];
                    try {
                        if (obj instanceof RememberObserverHolder) {
                            RememberObserver rememberObserver = ((RememberObserverHolder) obj).wrapped;
                            this.abandoning.remove(rememberObserver);
                            rememberObserver.onForgotten();
                        }
                        if (obj instanceof ComposeNodeLifecycleCallback) {
                            if (mutableScatterSet == null || !mutableScatterSet.contains(obj)) {
                                ((ComposeNodeLifecycleCallback) obj).onDeactivate();
                            } else {
                                ((ComposeNodeLifecycleCallback) obj).onRelease();
                            }
                        }
                        Unit unit = Unit.INSTANCE;
                    } catch (Throwable th) {
                        if (compositionErrorContext != null) {
                            ((CompositionErrorContextImpl) compositionErrorContext).attachComposeStackTrace(obj, th);
                        }
                        throw th;
                    }
                }
                Unit unit2 = Unit.INSTANCE;
            } finally {
            }
        }
        MutableVector mutableVector2 = this.remembering;
        if (mutableVector2.size != 0) {
            Trace.INSTANCE.getClass();
            android.os.Trace.beginSection("Compose:onRemembered");
            try {
                Object[] objArr = mutableVector2.content;
                int i3 = mutableVector2.size;
                for (int i4 = 0; i4 < i3; i4++) {
                    RememberObserverHolder rememberObserverHolder = (RememberObserverHolder) objArr[i4];
                    RememberObserver rememberObserver2 = rememberObserverHolder.wrapped;
                    this.abandoning.remove(rememberObserver2);
                    try {
                        rememberObserver2.onRemembered();
                        Unit unit3 = Unit.INSTANCE;
                    } catch (Throwable th2) {
                        if (compositionErrorContext != null) {
                            ((CompositionErrorContextImpl) compositionErrorContext).attachComposeStackTrace(rememberObserverHolder, th2);
                        }
                        throw th2;
                    }
                }
                Unit unit4 = Unit.INSTANCE;
            } finally {
            }
        }
    }

    public final void dispatchSideEffects() {
        MutableVector mutableVector = this.sideEffects;
        if (mutableVector.size != 0) {
            Trace.INSTANCE.getClass();
            android.os.Trace.beginSection("Compose:sideeffects");
            try {
                Object[] objArr = mutableVector.content;
                int i = mutableVector.size;
                for (int i2 = 0; i2 < i; i2++) {
                    ((Function0) objArr[i2]).invoke();
                }
                mutableVector.clear();
                Unit unit = Unit.INSTANCE;
            } finally {
                Trace.INSTANCE.getClass();
                android.os.Trace.endSection();
            }
        }
    }

    public final void processPendingLeaving(int i) {
        if (((ArrayList) this.pending).isEmpty()) {
            return;
        }
        int i2 = 0;
        int i3 = 0;
        List list = null;
        MutableIntList mutableIntList = null;
        MutableIntList mutableIntList2 = null;
        while (true) {
            MutableIntList mutableIntList3 = this.afters;
            if (i3 >= mutableIntList3._size) {
                break;
            }
            if (i <= mutableIntList3.get(i3)) {
                Object remove = ((ArrayList) this.pending).remove(i3);
                int removeAt = mutableIntList3.removeAt(i3);
                int removeAt2 = this.priorities.removeAt(i3);
                if (list == null) {
                    list = CollectionsKt__CollectionsKt.mutableListOf(remove);
                    mutableIntList2 = new MutableIntList(0, 1, null);
                    mutableIntList2.add(removeAt);
                    mutableIntList = new MutableIntList(0, 1, null);
                    mutableIntList.add(removeAt2);
                } else {
                    list.add(remove);
                    mutableIntList2.add(removeAt);
                    mutableIntList.add(removeAt2);
                }
            } else {
                i3++;
            }
        }
        if (list != null) {
            int size = list.size() - 1;
            while (i2 < size) {
                int i4 = i2 + 1;
                int size2 = list.size();
                for (int i5 = i4; i5 < size2; i5++) {
                    int i6 = mutableIntList2.get(i2);
                    int i7 = mutableIntList2.get(i5);
                    if (i6 < i7 || (i7 == i6 && mutableIntList.get(i2) < mutableIntList.get(i5))) {
                        Object obj = list.get(i2);
                        list.set(i2, list.get(i5));
                        list.set(i5, obj);
                        int i8 = mutableIntList.get(i2);
                        mutableIntList.set(i2, mutableIntList.get(i5));
                        mutableIntList.set(i5, i8);
                        int i9 = mutableIntList2.get(i2);
                        mutableIntList2.set(i2, mutableIntList2.get(i5));
                        mutableIntList2.set(i5, i9);
                    }
                }
                i2 = i4;
            }
            MutableVector mutableVector = this.leaving;
            mutableVector.addAll(mutableVector.size, list);
        }
    }

    public final void recordLeaving(int i, int i2, int i3, Object obj) {
        processPendingLeaving(i);
        if (i3 < 0 || i3 >= i) {
            this.leaving.add(obj);
            return;
        }
        ((ArrayList) this.pending).add(obj);
        this.priorities.add(i2);
        this.afters.add(i3);
    }
}
