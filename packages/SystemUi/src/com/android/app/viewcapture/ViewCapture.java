package com.android.app.viewcapture;

import android.content.res.Resources;
import android.os.Looper;
import android.os.Trace;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.android.app.viewcapture.ViewCapture;
import com.android.app.viewcapture.data.ViewNode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class ViewCapture {
    public static final LooperExecutor MAIN_EXECUTOR = new LooperExecutor(Looper.getMainLooper());
    public final Executor mBgExecutor;
    public final Choreographer mChoreographer;
    public final int mMemorySize;
    public final List mListeners = new ArrayList();
    public ViewRef mPool = new ViewRef(0);
    public final boolean mIsEnabled = true;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ViewIdProvider {
        public final SparseArray mNames = new SparseArray();
        public final Resources mRes;

        public ViewIdProvider(Resources resources) {
            this.mRes = resources;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ViewPropertyRef {
        public float alpha;
        public int bottom;
        public int childCount;
        public Class clazz;
        public boolean clipChildren;
        public float elevation;
        public int hashCode;

        /* renamed from: id */
        public int f200id;
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
        public int visibility;
        public boolean willNotDraw;

        public /* synthetic */ ViewPropertyRef(int i) {
            this();
        }

        public final ViewPropertyRef toProto(ViewIdProvider viewIdProvider, ArrayList arrayList, ViewNode.Builder builder) {
            String str;
            int indexOf = arrayList.indexOf(this.clazz);
            if (indexOf < 0) {
                indexOf = arrayList.size();
                arrayList.add(this.clazz);
            }
            builder.copyOnWrite();
            ViewNode.access$100((ViewNode) builder.instance, indexOf);
            int i = this.hashCode;
            builder.copyOnWrite();
            ViewNode.access$300((ViewNode) builder.instance, i);
            int i2 = this.f200id;
            Resources resources = viewIdProvider.mRes;
            SparseArray sparseArray = viewIdProvider.mNames;
            String str2 = (String) sparseArray.get(i2);
            if (str2 == null) {
                if (i2 >= 0) {
                    try {
                        str = resources.getResourceTypeName(i2) + '/' + resources.getResourceEntryName(i2);
                    } catch (Resources.NotFoundException unused) {
                        str = "id/0x" + Integer.toHexString(i2).toUpperCase();
                    }
                } else {
                    str = "NO_ID";
                }
                str2 = str;
                sparseArray.put(i2, str2);
            }
            builder.copyOnWrite();
            ViewNode.access$1100((ViewNode) builder.instance, str2);
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
            boolean z2 = this.clipChildren;
            builder.copyOnWrite();
            ViewNode.access$3800((ViewNode) builder.instance, z2);
            ViewPropertyRef viewPropertyRef = this.next;
            for (int i10 = 0; i10 < this.childCount && viewPropertyRef != null; i10++) {
                ViewNode.Builder newBuilder = ViewNode.newBuilder();
                viewPropertyRef = viewPropertyRef.toProto(viewIdProvider, arrayList, newBuilder);
                builder.copyOnWrite();
                ViewNode.access$600((ViewNode) builder.instance, (ViewNode) newBuilder.build());
            }
            return viewPropertyRef;
        }

        private ViewPropertyRef() {
            this.childCount = 0;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ViewRef implements Runnable {
        public ViewCapture$$ExternalSyntheticLambda3 callback;
        public int childCount;
        public long choreographerTimeNanos;
        public ViewRef next;
        public View view;

        public /* synthetic */ ViewRef(int i) {
            this();
        }

        @Override // java.lang.Runnable
        public final void run() {
            ViewCapture$$ExternalSyntheticLambda3 viewCapture$$ExternalSyntheticLambda3 = this.callback;
            this.callback = null;
            if (viewCapture$$ExternalSyntheticLambda3 != null) {
                viewCapture$$ExternalSyntheticLambda3.accept(this);
            }
        }

        private ViewRef() {
            this.childCount = 0;
            this.callback = null;
            this.choreographerTimeNanos = 0L;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WindowListener implements ViewTreeObserver.OnDrawListener {
        public final ViewCapture$$ExternalSyntheticLambda3 mCaptureCallback;
        public final long[] mFrameTimesNanosBg;
        public boolean mIsActive;
        public final ViewPropertyRef[] mNodesBg;
        public View mRoot;
        public final String name;
        public final ViewRef mViewRef = new ViewRef(0);
        public int mFrameIndexBg = -1;
        public boolean mIsFirstFrame = true;

        public WindowListener(View view, String str) {
            int i = ViewCapture.this.mMemorySize;
            this.mFrameTimesNanosBg = new long[i];
            this.mNodesBg = new ViewPropertyRef[i];
            this.mIsActive = true;
            this.mCaptureCallback = new ViewCapture$$ExternalSyntheticLambda3(this, 1);
            this.mRoot = view;
            this.name = str;
        }

        public final ViewRef captureViewTree(View view, ViewRef viewRef) {
            ViewCapture viewCapture = ViewCapture.this;
            ViewRef viewRef2 = viewCapture.mPool;
            int i = 0;
            if (viewRef2 != null) {
                viewCapture.mPool = viewRef2.next;
                viewRef2.next = null;
            } else {
                viewRef2 = new ViewRef(i);
            }
            viewRef2.view = view;
            viewRef.next = viewRef2;
            if (!(view instanceof ViewGroup)) {
                viewRef2.childCount = 0;
                return viewRef2;
            }
            ViewGroup viewGroup = (ViewGroup) view;
            if ((view.mPrivateFlags & (-2145386496)) == 0 && !this.mIsFirstFrame) {
                viewRef2.childCount = -viewGroup.getChildCount();
                return viewRef2;
            }
            int childCount = viewGroup.getChildCount();
            viewRef2.childCount = childCount;
            while (i < childCount) {
                viewRef2 = captureViewTree(viewGroup.getChildAt(i), viewRef2);
                i++;
            }
            return viewRef2;
        }

        @Override // android.view.ViewTreeObserver.OnDrawListener
        public final void onDraw() {
            Trace.beginSection("view_capture");
            captureViewTree(this.mRoot, this.mViewRef);
            ViewRef viewRef = this.mViewRef.next;
            if (viewRef != null) {
                viewRef.callback = this.mCaptureCallback;
                viewRef.choreographerTimeNanos = ViewCapture.this.mChoreographer.getFrameTimeNanos();
                ViewCapture.this.mBgExecutor.execute(viewRef);
            }
            this.mIsFirstFrame = false;
            Trace.endSection();
        }
    }

    public ViewCapture(int i, final int i2, Choreographer choreographer, Executor executor) {
        this.mMemorySize = i;
        this.mChoreographer = choreographer;
        this.mBgExecutor = executor;
        executor.execute(new Runnable() { // from class: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ViewCapture viewCapture = ViewCapture.this;
                int i3 = i2;
                viewCapture.getClass();
                int i4 = 0;
                ViewCapture.ViewRef viewRef = new ViewCapture.ViewRef(i4);
                ViewCapture.ViewRef viewRef2 = viewRef;
                int i5 = 0;
                while (i5 < i3) {
                    ViewCapture.ViewRef viewRef3 = new ViewCapture.ViewRef(i4);
                    viewRef2.next = viewRef3;
                    i5++;
                    viewRef2 = viewRef3;
                }
                ViewCapture.MAIN_EXECUTOR.execute(new ViewCapture$$ExternalSyntheticLambda8(viewCapture, viewRef, viewRef2, 0));
            }
        });
    }

    public void stopCapture(View view) {
        ((ArrayList) this.mListeners).forEach(new ViewCapture$$ExternalSyntheticLambda3(view, 0));
    }
}
