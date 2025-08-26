package com.android.app.viewcapture;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.permission.SafeCloseable;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.android.app.viewcapture.ViewCapture;
import com.android.app.viewcapture.data.ExportedData;
import com.android.app.viewcapture.data.ViewNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public abstract class ViewCapture {
    public static final long MAGIC_NUMBER_FOR_WINSCOPE = (ExportedData.MagicNumber.MAGIC_NUMBER_H.getNumber() << 32) | ExportedData.MagicNumber.MAGIC_NUMBER_L.getNumber();
    public static final LooperExecutor MAIN_EXECUTOR = new LooperExecutor(Looper.getMainLooper());
    public final Executor mBgExecutor;
    public final int mInitPoolSize;
    public final int mMemorySize;
    public final List mListeners = Collections.synchronizedList(new ArrayList());
    public final boolean mIsEnabled = true;
    public boolean mIsStarted = false;

    public final class ViewIdProvider {
        public final SparseArray mNames = new SparseArray();
        public final Resources mRes;

        public ViewIdProvider(Resources resources) {
            this.mRes = resources;
        }
    }

    public class ViewPropertyRef implements Runnable {
        public float alpha;
        public int bottom;
        public Class clazz;
        public float elevation;
        public int hashCode;
        public int id;
        public int left;
        public ViewPropertyRef next;
        public int right;
        public float scaleX;
        public float scaleY;
        public int scrollX;
        public int scrollY;
        public int top;
        public float translateX;
        public float translateY;
        public View view;
        public int visibility;
        public boolean willNotDraw;
        public int childCount = 0;
        public ViewCapture$WindowListener$$ExternalSyntheticLambda0 callback = null;
        public long elapsedRealtimeNanos = 0;

        @Override // java.lang.Runnable
        public final void run() {
            ViewCapture$WindowListener$$ExternalSyntheticLambda0 viewCapture$WindowListener$$ExternalSyntheticLambda0 = this.callback;
            this.callback = null;
            if (viewCapture$WindowListener$$ExternalSyntheticLambda0 != null) {
                viewCapture$WindowListener$$ExternalSyntheticLambda0.accept(this);
            }
        }

        public final ViewPropertyRef toProto(ViewIdProvider viewIdProvider, ArrayList arrayList, ViewNode.Builder builder) throws Resources.NotFoundException {
            int iIndexOf = arrayList.indexOf(this.clazz);
            if (iIndexOf < 0) {
                iIndexOf = arrayList.size();
                arrayList.add(this.clazz);
            }
            builder.copyOnWrite();
            ViewNode.access$100((ViewNode) builder.instance, iIndexOf);
            int i = this.hashCode;
            builder.copyOnWrite();
            ViewNode.access$300((ViewNode) builder.instance, i);
            int i2 = this.id;
            String str = (String) viewIdProvider.mNames.get(i2);
            if (str == null) {
                if (i2 >= 0) {
                    try {
                        str = viewIdProvider.mRes.getResourceTypeName(i2) + '/' + viewIdProvider.mRes.getResourceEntryName(i2);
                    } catch (Resources.NotFoundException unused) {
                        str = "id/0x" + Integer.toHexString(i2).toUpperCase();
                    }
                } else {
                    str = "NO_ID";
                }
                viewIdProvider.mNames.put(i2, str);
            }
            builder.copyOnWrite();
            ViewNode.access$1100((ViewNode) builder.instance, str);
            int i3 = this.left;
            builder.copyOnWrite();
            ViewNode.access$1400((ViewNode) builder.instance, i3);
            int i4 = this.top;
            builder.copyOnWrite();
            ViewNode.access$1600((ViewNode) builder.instance, i4);
            int i5 = this.right - this.left;
            builder.copyOnWrite();
            ViewNode.access$1800((ViewNode) builder.instance, i5);
            int i6 = this.bottom - this.top;
            builder.copyOnWrite();
            ViewNode.access$2000((ViewNode) builder.instance, i6);
            float f = this.translateX;
            builder.copyOnWrite();
            ViewNode.access$2600((ViewNode) builder.instance, f);
            float f2 = this.translateY;
            builder.copyOnWrite();
            ViewNode.access$2800((ViewNode) builder.instance, f2);
            int i7 = this.scrollX;
            builder.copyOnWrite();
            ViewNode.access$2200((ViewNode) builder.instance, i7);
            int i8 = this.scrollY;
            builder.copyOnWrite();
            ViewNode.access$2400((ViewNode) builder.instance, i8);
            float f3 = this.scaleX;
            builder.copyOnWrite();
            ViewNode.access$3000((ViewNode) builder.instance, f3);
            float f4 = this.scaleY;
            builder.copyOnWrite();
            ViewNode.access$3200((ViewNode) builder.instance, f4);
            float f5 = this.alpha;
            builder.copyOnWrite();
            ViewNode.access$3400((ViewNode) builder.instance, f5);
            int i9 = this.visibility;
            builder.copyOnWrite();
            ViewNode.access$4000((ViewNode) builder.instance, i9);
            boolean z = this.willNotDraw;
            builder.copyOnWrite();
            ViewNode.access$3600((ViewNode) builder.instance, z);
            float f6 = this.elevation;
            builder.copyOnWrite();
            ViewNode.access$4200((ViewNode) builder.instance, f6);
            builder.copyOnWrite();
            ViewNode.access$3800((ViewNode) builder.instance);
            ViewPropertyRef proto = this.next;
            for (int i10 = 0; i10 < this.childCount && proto != null; i10++) {
                ViewNode.Builder builderNewBuilder = ViewNode.newBuilder();
                proto = proto.toProto(viewIdProvider, arrayList, builderNewBuilder);
                builder.copyOnWrite();
                ViewNode.access$600((ViewNode) builder.instance, (ViewNode) builderNewBuilder.build());
            }
            return proto;
        }

        public final void transferFrom(View view) {
            this.view = view;
            this.left = view.getLeft();
            this.top = view.getTop();
            this.right = view.getRight();
            this.bottom = view.getBottom();
            this.scrollX = view.getScrollX();
            this.scrollY = view.getScrollY();
            this.translateX = view.getTranslationX();
            this.translateY = view.getTranslationY();
            this.scaleX = view.getScaleX();
            this.scaleY = view.getScaleY();
            this.alpha = view.getAlpha();
            this.elevation = view.getElevation();
            this.visibility = view.getVisibility();
            this.willNotDraw = view.willNotDraw();
        }

        public final void transferTo(ViewPropertyRef viewPropertyRef) {
            viewPropertyRef.clazz = this.clazz;
            viewPropertyRef.hashCode = this.hashCode;
            viewPropertyRef.childCount = this.childCount;
            viewPropertyRef.id = this.id;
            viewPropertyRef.left = this.left;
            viewPropertyRef.top = this.top;
            viewPropertyRef.right = this.right;
            viewPropertyRef.bottom = this.bottom;
            viewPropertyRef.scrollX = this.scrollX;
            viewPropertyRef.scrollY = this.scrollY;
            viewPropertyRef.scaleX = this.scaleX;
            viewPropertyRef.scaleY = this.scaleY;
            viewPropertyRef.translateX = this.translateX;
            viewPropertyRef.translateY = this.translateY;
            viewPropertyRef.alpha = this.alpha;
            viewPropertyRef.visibility = this.visibility;
            viewPropertyRef.willNotDraw = this.willNotDraw;
            viewPropertyRef.elevation = this.elevation;
        }
    }

    public ViewCapture(int i, int i2, Executor executor) {
        this.mMemorySize = i;
        this.mBgExecutor = executor;
        this.mInitPoolSize = i2;
    }

    public static void runOnUiThread(View view, Runnable runnable) {
        if (view == null) {
            Log.i("ViewCapture", "Skipping run on UI thread. Provided view == null.");
            return;
        }
        Handler handler = view.getHandler();
        if (handler == null || handler.getLooper().getThread() != Thread.currentThread()) {
            view.post(runnable);
        } else {
            runnable.run();
        }
    }

    public ExportedData getExportedData(Context context) throws ExecutionException, InterruptedException {
        ArrayList arrayList = new ArrayList();
        ExportedData.Builder builderNewBuilder = ExportedData.newBuilder();
        builderNewBuilder.copyOnWrite();
        ExportedData.access$100((ExportedData) builderNewBuilder.instance, MAGIC_NUMBER_FOR_WINSCOPE);
        String packageName = context.getPackageName();
        builderNewBuilder.copyOnWrite();
        ExportedData.access$900((ExportedData) builderNewBuilder.instance, packageName);
        Iterable iterable = (Iterable) getWindowData(context, arrayList, new ViewCapture$$ExternalSyntheticLambda0()).get();
        builderNewBuilder.copyOnWrite();
        ExportedData.access$600((ExportedData) builderNewBuilder.instance, iterable);
        List list = (List) arrayList.stream().map(new ViewCapture$$ExternalSyntheticLambda6()).collect(Collectors.toList());
        builderNewBuilder.copyOnWrite();
        ExportedData.access$1400((ExportedData) builderNewBuilder.instance, list);
        long nanos = TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()) - SystemClock.elapsedRealtimeNanos();
        builderNewBuilder.copyOnWrite();
        ExportedData.access$1700((ExportedData) builderNewBuilder.instance, nanos);
        return (ExportedData) builderNewBuilder.build();
    }

    public final CompletableFuture getWindowData(Context context, ArrayList arrayList, final Predicate predicate) {
        return CompletableFuture.supplyAsync(new Supplier() { // from class: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                ViewCapture viewCapture = this.f$0;
                return (List) viewCapture.mListeners.stream().filter(predicate).collect(Collectors.toList());
            }
        }, MAIN_EXECUTOR).thenApplyAsync((Function) new ViewCapture$$ExternalSyntheticLambda8(new ViewIdProvider(context.getResources()), arrayList, 0), this.mBgExecutor);
    }

    public SafeCloseable startCapture(View view, String str) {
        View view2;
        this.mIsStarted = true;
        final WindowListener windowListener = new WindowListener(view, str);
        if (this.mIsEnabled && (view2 = windowListener.mRoot) != null) {
            windowListener.mIsActive = true;
            ViewCapture viewCapture = ViewCapture.this;
            ViewCapture$$ExternalSyntheticLambda5 viewCapture$$ExternalSyntheticLambda5 = new ViewCapture$$ExternalSyntheticLambda5(windowListener, 1);
            viewCapture.getClass();
            runOnUiThread(view2, viewCapture$$ExternalSyntheticLambda5);
        }
        this.mListeners.add(windowListener);
        view.getContext().registerComponentCallbacks(windowListener);
        return new SafeCloseable() { // from class: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda2
            public final void close() {
                ViewCapture viewCapture2 = this.f$0;
                ViewCapture.WindowListener windowListener2 = windowListener;
                LooperExecutor looperExecutor = ViewCapture.MAIN_EXECUTOR;
                viewCapture2.getClass();
                View view3 = windowListener2.mRoot;
                if (view3 != null && view3.getContext() != null) {
                    windowListener2.mRoot.getContext().unregisterComponentCallbacks(windowListener2);
                }
                viewCapture2.mListeners.remove(windowListener2);
                windowListener2.mIsActive = false;
                ViewCapture viewCapture3 = ViewCapture.this;
                ViewCapture$$ExternalSyntheticLambda5 viewCapture$$ExternalSyntheticLambda52 = new ViewCapture$$ExternalSyntheticLambda5(windowListener2, 2);
                View view4 = windowListener2.mRoot;
                viewCapture3.getClass();
                ViewCapture.runOnUiThread(view4, viewCapture$$ExternalSyntheticLambda52);
            }
        };
    }

    public void stopCapture(final View view) {
        this.mIsStarted = false;
        this.mListeners.forEach(new Consumer() { // from class: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                View view2 = view;
                ViewCapture.WindowListener windowListener = (ViewCapture.WindowListener) obj;
                LooperExecutor looperExecutor = ViewCapture.MAIN_EXECUTOR;
                View view3 = windowListener.mRoot;
                if (view2 == view3) {
                    ViewCapture.runOnUiThread(view3, new ViewCapture$$ExternalSyntheticLambda5(windowListener, 0));
                }
            }
        });
    }

    public class WindowListener implements ViewTreeObserver.OnDrawListener, ComponentCallbacks2 {
        public final ViewCapture$WindowListener$$ExternalSyntheticLambda0 mCaptureCallback;
        public long[] mFrameTimesNanosBg;
        public boolean mIsActive;
        public ViewPropertyRef[] mNodesBg;
        public ViewPropertyRef mPool;
        public View mRoot;
        public final String name;
        public final ViewPropertyRef mViewPropertyRef = new ViewPropertyRef();
        public int mFrameIndexBg = -1;
        public boolean mIsFirstFrame = true;

        public WindowListener(View view, String str) {
            this.mPool = new ViewPropertyRef();
            int i = ViewCapture.this.mMemorySize;
            this.mFrameTimesNanosBg = new long[i];
            this.mNodesBg = new ViewPropertyRef[i];
            this.mIsActive = true;
            this.mCaptureCallback = new ViewCapture$WindowListener$$ExternalSyntheticLambda0(this);
            this.mRoot = view;
            this.name = str;
            ViewPropertyRef viewPropertyRef = new ViewPropertyRef();
            int i2 = 0;
            ViewPropertyRef viewPropertyRef2 = viewPropertyRef;
            while (i2 < ViewCapture.this.mInitPoolSize) {
                ViewPropertyRef viewPropertyRef3 = new ViewPropertyRef();
                viewPropertyRef2.next = viewPropertyRef3;
                i2++;
                viewPropertyRef2 = viewPropertyRef3;
            }
            viewPropertyRef2.next = this.mPool;
            this.mPool = viewPropertyRef;
        }

        public final ViewPropertyRef captureViewTree(View view, ViewPropertyRef viewPropertyRef) {
            ViewPropertyRef viewPropertyRef2 = this.mPool;
            if (viewPropertyRef2 != null) {
                this.mPool = viewPropertyRef2.next;
                viewPropertyRef2.next = null;
            } else {
                viewPropertyRef2 = new ViewPropertyRef();
            }
            viewPropertyRef.next = viewPropertyRef2;
            if (!(view instanceof ViewGroup)) {
                viewPropertyRef2.childCount = 0;
                viewPropertyRef2.transferFrom(view);
                return viewPropertyRef2;
            }
            ViewGroup viewGroup = (ViewGroup) view;
            if ((view.mPrivateFlags & (-2145386496)) == 0 && !this.mIsFirstFrame) {
                viewPropertyRef2.childCount = -1;
                viewPropertyRef2.view = view;
                return viewPropertyRef2;
            }
            int childCount = viewGroup.getChildCount();
            viewPropertyRef2.childCount = childCount;
            viewPropertyRef2.transferFrom(view);
            for (int i = 0; i < childCount; i++) {
                viewPropertyRef2 = captureViewTree(viewGroup.getChildAt(i), viewPropertyRef2);
            }
            return viewPropertyRef2;
        }

        @Override // android.view.ViewTreeObserver.OnDrawListener
        public final void onDraw() {
            Trace.beginSection("vc#onDraw");
            try {
                View view = this.mRoot;
                if (view == null) {
                    return;
                }
                captureViewTree(view, this.mViewPropertyRef);
                ViewPropertyRef viewPropertyRef = this.mViewPropertyRef.next;
                if (viewPropertyRef != null) {
                    viewPropertyRef.callback = this.mCaptureCallback;
                    viewPropertyRef.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
                    ViewCapture.this.mBgExecutor.execute(viewPropertyRef);
                }
                this.mIsFirstFrame = false;
            } finally {
                Trace.endSection();
            }
        }

        @Override // android.content.ComponentCallbacks
        public final void onLowMemory() {
            onTrimMemory(40);
        }

        @Override // android.content.ComponentCallbacks2
        public final void onTrimMemory(int i) {
            if (i >= 40) {
                this.mNodesBg = new ViewPropertyRef[0];
                this.mFrameTimesNanosBg = new long[0];
                View view = this.mRoot;
                if (view != null && view.getContext() != null) {
                    this.mRoot.getContext().unregisterComponentCallbacks(this);
                }
                this.mIsActive = false;
                ViewCapture viewCapture = ViewCapture.this;
                ViewCapture$$ExternalSyntheticLambda5 viewCapture$$ExternalSyntheticLambda5 = new ViewCapture$$ExternalSyntheticLambda5(this, 2);
                View view2 = this.mRoot;
                viewCapture.getClass();
                ViewCapture.runOnUiThread(view2, viewCapture$$ExternalSyntheticLambda5);
                this.mRoot = null;
            }
        }

        @Override // android.content.ComponentCallbacks
        public final void onConfigurationChanged(Configuration configuration) {
        }
    }

    public void onCapturedViewPropertiesBg(long j, String str, ViewPropertyRef viewPropertyRef) {
    }
}
