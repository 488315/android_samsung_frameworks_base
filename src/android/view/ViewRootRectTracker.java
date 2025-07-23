package android.view;

import android.graphics.Rect;
import com.android.internal.util.Preconditions;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/* loaded from: classes4.dex */
public class ViewRootRectTracker {
    private final Function<View, List<Rect>> mRectCollector;
    private boolean mViewsChanged = false;
    private boolean mRootRectsChanged = false;
    private List<Rect> mRootRects = Collections.EMPTY_LIST;
    private List<ViewInfo> mViewInfos = new ArrayList();
    private List<Rect> mRects = Collections.EMPTY_LIST;
    private boolean mWaitingForComputeChanges = false;

    public ViewRootRectTracker(Function<View, List<Rect>> function) {
        this.mRectCollector = function;
    }

    public void updateRectsForView(View view) {
        Iterator<ViewInfo> it = this.mViewInfos.iterator();
        while (true) {
            if (it.hasNext()) {
                ViewInfo next = it.next();
                View view2 = next.getView();
                if (view2 == null || !view2.isAttachedToWindow() || !view2.isAggregatedVisible()) {
                    this.mViewsChanged = true;
                    it.remove();
                } else if (view2 == view) {
                    next.mDirty = true;
                    break;
                }
            } else if (view.isAttachedToWindow()) {
                this.mViewInfos.add(new ViewInfo(view));
                this.mViewsChanged = true;
            }
        }
        this.mWaitingForComputeChanges = true;
    }

    public List<Rect> computeChangedRects() {
        if (computeChanges()) {
            return this.mRects;
        }
        return null;
    }

    public boolean computeChanges() {
        this.mWaitingForComputeChanges = false;
        boolean z = this.mRootRectsChanged;
        Iterator<ViewInfo> it = this.mViewInfos.iterator();
        ArrayList arrayList = new ArrayList(this.mRootRects);
        while (it.hasNext()) {
            ViewInfo next = it.next();
            int update = next.update();
            if (update == 0) {
                z = true;
            } else if (update != 1) {
                if (update == 2) {
                    this.mViewsChanged = true;
                    it.remove();
                }
            }
            arrayList.addAll(next.mRects);
        }
        if (z || this.mViewsChanged) {
            this.mViewsChanged = false;
            this.mRootRectsChanged = false;
            if (!this.mRects.equals(arrayList)) {
                this.mRects = arrayList;
                return true;
            }
        }
        return false;
    }

    public boolean isWaitingForComputeChanges() {
        return this.mWaitingForComputeChanges;
    }

    public List<Rect> getLastComputedRects() {
        return this.mRects;
    }

    public void setRootRects(List<Rect> list) {
        Preconditions.checkNotNull(list, "rects must not be null");
        this.mRootRects = list;
        this.mRootRectsChanged = true;
        this.mWaitingForComputeChanges = true;
    }

    public List<Rect> getRootRects() {
        return this.mRootRects;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<Rect> getTrackedRectsForView(View view) {
        List<Rect> apply = this.mRectCollector.apply(view);
        return apply == null ? Collections.EMPTY_LIST : apply;
    }

    private class ViewInfo {
        public static final int CHANGED = 0;
        public static final int GONE = 2;
        public static final int UNCHANGED = 1;
        boolean mDirty = true;
        List<Rect> mRects = Collections.EMPTY_LIST;
        private final WeakReference<View> mView;

        ViewInfo(View view) {
            this.mView = new WeakReference<>(view);
        }

        public View getView() {
            return this.mView.get();
        }

        public int update() {
            View view = getView();
            if (view == null || !view.isAttachedToWindow() || !view.isAggregatedVisible()) {
                return 2;
            }
            List trackedRectsForView = ViewRootRectTracker.this.getTrackedRectsForView(view);
            ArrayList arrayList = new ArrayList(trackedRectsForView.size());
            Iterator it = trackedRectsForView.iterator();
            while (it.hasNext()) {
                Rect rect = new Rect((Rect) it.next());
                ViewParent parent = view.getParent();
                if (parent != null && parent.getChildVisibleRect(view, rect, null)) {
                    arrayList.add(rect);
                }
            }
            if (this.mRects.equals(trackedRectsForView)) {
                return 1;
            }
            this.mRects = arrayList;
            return 0;
        }
    }
}
