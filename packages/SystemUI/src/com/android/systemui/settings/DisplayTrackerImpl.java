package com.android.systemui.settings;

import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.util.Assert;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class DisplayTrackerImpl implements DisplayTracker {
    public final Handler backgroundHandler;
    public final DisplayManager displayManager;
    public final List displayCallbacks = new ArrayList();
    public final List brightnessCallbacks = new ArrayList();
    public final DisplayTrackerImpl$displayChangedListener$1 displayChangedListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.settings.DisplayTrackerImpl$displayChangedListener$1
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(final int i) {
            List<DisplayTrackerImpl.DisplayTrackerDataItem> list;
            DisplayTrackerImpl displayTrackerImpl = this.this$0;
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice("DisplayTrackerImpl.displayChangedDisplayListener#onDisplayAdded");
            }
            try {
                synchronized (displayTrackerImpl.displayCallbacks) {
                    list = CollectionsKt___CollectionsKt.toList(displayTrackerImpl.displayCallbacks);
                }
                Assert.isNotMainThread();
                for (final DisplayTrackerImpl.DisplayTrackerDataItem displayTrackerDataItem : list) {
                    if (displayTrackerDataItem.callback.get() != null) {
                        displayTrackerDataItem.executor.execute(new Runnable() { // from class: com.android.systemui.settings.DisplayTrackerImpl$onDisplayAdded$$inlined$notifySubscribers$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                DisplayTracker.Callback callback = (DisplayTracker.Callback) displayTrackerDataItem.callback.get();
                                if (callback != null) {
                                    callback.onDisplayAdded(i);
                                }
                            }
                        });
                    }
                }
                Unit unit = Unit.INSTANCE;
            } finally {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            List list;
            DisplayTrackerImpl displayTrackerImpl = this.this$0;
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice("DisplayTrackerImpl.displayChangedDisplayListener#onDisplayChanged");
            }
            try {
                synchronized (displayTrackerImpl.displayCallbacks) {
                    list = CollectionsKt___CollectionsKt.toList(displayTrackerImpl.displayCallbacks);
                }
                DisplayTrackerImpl.access$onDisplayChanged(displayTrackerImpl, i, list);
                Unit unit = Unit.INSTANCE;
            } finally {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(final int i) {
            List<DisplayTrackerImpl.DisplayTrackerDataItem> list;
            DisplayTrackerImpl displayTrackerImpl = this.this$0;
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice("DisplayTrackerImpl.displayChangedDisplayListener#onDisplayRemoved");
            }
            try {
                synchronized (displayTrackerImpl.displayCallbacks) {
                    list = CollectionsKt___CollectionsKt.toList(displayTrackerImpl.displayCallbacks);
                }
                Assert.isNotMainThread();
                for (final DisplayTrackerImpl.DisplayTrackerDataItem displayTrackerDataItem : list) {
                    if (displayTrackerDataItem.callback.get() != null) {
                        displayTrackerDataItem.executor.execute(new Runnable() { // from class: com.android.systemui.settings.DisplayTrackerImpl$onDisplayRemoved$$inlined$notifySubscribers$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                DisplayTracker.Callback callback = (DisplayTracker.Callback) displayTrackerDataItem.callback.get();
                                if (callback != null) {
                                    callback.onDisplayRemoved(i);
                                }
                            }
                        });
                    }
                }
                Unit unit = Unit.INSTANCE;
            } finally {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
            }
        }
    };
    public final DisplayTrackerImpl$displayBrightnessChangedListener$1 displayBrightnessChangedListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.settings.DisplayTrackerImpl$displayBrightnessChangedListener$1
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            List list;
            DisplayTrackerImpl displayTrackerImpl = this.this$0;
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice("DisplayTrackerImpl.displayBrightnessChangedDisplayListener#onDisplayChanged");
            }
            try {
                synchronized (displayTrackerImpl.brightnessCallbacks) {
                    list = CollectionsKt___CollectionsKt.toList(displayTrackerImpl.brightnessCallbacks);
                }
                DisplayTrackerImpl.access$onDisplayChanged(displayTrackerImpl, i, list);
                Unit unit = Unit.INSTANCE;
            } finally {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {
        }
    };

    public final class DisplayTrackerDataItem {
        public final WeakReference callback;
        public final Executor executor;

        public DisplayTrackerDataItem(WeakReference<DisplayTracker.Callback> weakReference, Executor executor) {
            this.callback = weakReference;
            this.executor = executor;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DisplayTrackerDataItem)) {
                return false;
            }
            DisplayTrackerDataItem displayTrackerDataItem = (DisplayTrackerDataItem) obj;
            return Intrinsics.areEqual(this.callback, displayTrackerDataItem.callback) && Intrinsics.areEqual(this.executor, displayTrackerDataItem.executor);
        }

        public final int hashCode() {
            return this.executor.hashCode() + (this.callback.hashCode() * 31);
        }

        public final String toString() {
            return "DisplayTrackerDataItem(callback=" + this.callback + ", executor=" + this.executor + ")";
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.settings.DisplayTrackerImpl$displayChangedListener$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.settings.DisplayTrackerImpl$displayBrightnessChangedListener$1] */
    public DisplayTrackerImpl(DisplayManager displayManager, Handler handler) {
        this.displayManager = displayManager;
        this.backgroundHandler = handler;
    }

    public static final void access$onDisplayChanged(DisplayTrackerImpl displayTrackerImpl, final int i, List list) {
        displayTrackerImpl.getClass();
        Assert.isNotMainThread();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            final DisplayTrackerDataItem displayTrackerDataItem = (DisplayTrackerDataItem) it.next();
            if (displayTrackerDataItem.callback.get() != null) {
                displayTrackerDataItem.executor.execute(new Runnable() { // from class: com.android.systemui.settings.DisplayTrackerImpl$onDisplayChanged$$inlined$notifySubscribers$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DisplayTracker.Callback callback = (DisplayTracker.Callback) displayTrackerDataItem.callback.get();
                        if (callback != null) {
                            callback.onDisplayChanged(i);
                        }
                    }
                });
            }
        }
    }

    public final void addBrightnessChangeCallback(DisplayTracker.Callback callback, Executor executor) {
        synchronized (this.brightnessCallbacks) {
            try {
                if (((ArrayList) this.brightnessCallbacks).isEmpty()) {
                    this.displayManager.registerDisplayListener(this.displayBrightnessChangedListener, this.backgroundHandler, 0L, 1L);
                }
                ((ArrayList) this.brightnessCallbacks).add(new DisplayTrackerDataItem(new WeakReference(callback), executor));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addDisplayChangeCallback(DisplayTracker.Callback callback, Executor executor) {
        synchronized (this.displayCallbacks) {
            try {
                if (((ArrayList) this.displayCallbacks).isEmpty()) {
                    this.displayManager.registerDisplayListener(this.displayChangedListener, this.backgroundHandler);
                }
                ((ArrayList) this.displayCallbacks).add(new DisplayTrackerDataItem(new WeakReference(callback), executor));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeCallback(final DisplayTracker.Callback callback) {
        synchronized (this.displayCallbacks) {
            try {
                List list = this.displayCallbacks;
                final int i = 0;
                final Function1 function1 = new Function1() { // from class: com.android.systemui.settings.DisplayTrackerImpl$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        DisplayTrackerImpl.DisplayTrackerDataItem displayTrackerDataItem = (DisplayTrackerImpl.DisplayTrackerDataItem) obj;
                        switch (i) {
                            case 0:
                                DisplayTracker.Callback callback2 = (DisplayTracker.Callback) displayTrackerDataItem.callback.get();
                                return Boolean.valueOf(callback2 != null ? callback2.equals(callback) : true);
                            default:
                                DisplayTracker.Callback callback3 = (DisplayTracker.Callback) displayTrackerDataItem.callback.get();
                                return Boolean.valueOf(callback3 != null ? callback3.equals(callback) : true);
                        }
                    }
                };
                if (((ArrayList) list).removeIf(new Predicate() { // from class: com.android.systemui.settings.DisplayTrackerImpl$sam$java_util_function_Predicate$0
                    @Override // java.util.function.Predicate
                    public final /* synthetic */ boolean test(Object obj) {
                        return ((Boolean) function1.mo781invoke(obj)).booleanValue();
                    }
                }) && ((ArrayList) this.displayCallbacks).isEmpty()) {
                    this.displayManager.unregisterDisplayListener(this.displayChangedListener);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        synchronized (this.brightnessCallbacks) {
            List list2 = this.brightnessCallbacks;
            final int i2 = 1;
            final Function1 function12 = new Function1() { // from class: com.android.systemui.settings.DisplayTrackerImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    DisplayTrackerImpl.DisplayTrackerDataItem displayTrackerDataItem = (DisplayTrackerImpl.DisplayTrackerDataItem) obj;
                    switch (i2) {
                        case 0:
                            DisplayTracker.Callback callback2 = (DisplayTracker.Callback) displayTrackerDataItem.callback.get();
                            return Boolean.valueOf(callback2 != null ? callback2.equals(callback) : true);
                        default:
                            DisplayTracker.Callback callback3 = (DisplayTracker.Callback) displayTrackerDataItem.callback.get();
                            return Boolean.valueOf(callback3 != null ? callback3.equals(callback) : true);
                    }
                }
            };
            if (((ArrayList) list2).removeIf(new Predicate() { // from class: com.android.systemui.settings.DisplayTrackerImpl$sam$java_util_function_Predicate$0
                @Override // java.util.function.Predicate
                public final /* synthetic */ boolean test(Object obj) {
                    return ((Boolean) function12.mo781invoke(obj)).booleanValue();
                }
            }) && ((ArrayList) this.brightnessCallbacks).isEmpty()) {
                this.displayManager.unregisterDisplayListener(this.displayBrightnessChangedListener);
            }
        }
    }

    public static /* synthetic */ void getDisplayBrightnessChangedListener$annotations() {
    }

    public static /* synthetic */ void getDisplayChangedListener$annotations() {
    }
}
