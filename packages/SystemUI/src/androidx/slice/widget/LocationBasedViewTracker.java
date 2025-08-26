package androidx.slice.widget;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class LocationBasedViewTracker implements Runnable, View.OnLayoutChangeListener {
    public final Rect mFocusRect;
    public final ViewGroup mParent;
    public final SelectionLogic mSelectionLogic;
    public static final AnonymousClass1 INPUT_FOCUS = new SelectionLogic() { // from class: androidx.slice.widget.LocationBasedViewTracker.1
        @Override // androidx.slice.widget.LocationBasedViewTracker.SelectionLogic
        public final void selectView(View view) {
            view.requestFocus();
        }
    };
    public static final AnonymousClass2 A11Y_FOCUS = new SelectionLogic() { // from class: androidx.slice.widget.LocationBasedViewTracker.2
        @Override // androidx.slice.widget.LocationBasedViewTracker.SelectionLogic
        public final void selectView(View view) {
            view.performAccessibilityAction(64, null);
        }
    };

    public interface SelectionLogic {
        void selectView(View view);
    }

    private LocationBasedViewTracker(ViewGroup viewGroup, View view, SelectionLogic selectionLogic) {
        Rect rect = new Rect();
        this.mFocusRect = rect;
        this.mParent = viewGroup;
        this.mSelectionLogic = selectionLogic;
        view.getDrawingRect(rect);
        viewGroup.offsetDescendantRectToMyCoords(view, rect);
        viewGroup.addOnLayoutChangeListener(this);
        viewGroup.requestLayout();
    }

    public static void trackA11yFocus(SliceView sliceView) {
        View view;
        if (((AccessibilityManager) sliceView.getContext().getSystemService("accessibility")).isTouchExplorationEnabled()) {
            ArrayList<View> arrayList = new ArrayList<>();
            int i = 0;
            sliceView.addFocusables(arrayList, 2, 0);
            int size = arrayList.size();
            while (true) {
                if (i >= size) {
                    view = null;
                    break;
                }
                View view2 = arrayList.get(i);
                i++;
                view = view2;
                if (view.isAccessibilityFocused()) {
                    break;
                }
            }
            if (view != null) {
                new LocationBasedViewTracker(sliceView, view, A11Y_FOCUS);
            }
        }
    }

    public static void trackInputFocused(SliceView sliceView) {
        View viewFindFocus = sliceView.findFocus();
        if (viewFindFocus != null) {
            new LocationBasedViewTracker(sliceView, viewFindFocus, INPUT_FOCUS);
        }
    }

    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.mParent.removeOnLayoutChangeListener(this);
        this.mParent.post(this);
    }

    @Override // java.lang.Runnable
    public final void run() {
        ArrayList<View> arrayList = new ArrayList<>();
        int i = 0;
        this.mParent.addFocusables(arrayList, 2, 0);
        Rect rect = new Rect();
        int size = arrayList.size();
        int i2 = Integer.MAX_VALUE;
        View view = null;
        while (i < size) {
            View view2 = arrayList.get(i);
            i++;
            View view3 = view2;
            view3.getDrawingRect(rect);
            this.mParent.offsetDescendantRectToMyCoords(view3, rect);
            if (this.mFocusRect.intersect(rect)) {
                int iAbs = Math.abs(this.mFocusRect.bottom - rect.bottom) + Math.abs(this.mFocusRect.top - rect.top) + Math.abs(this.mFocusRect.right - rect.right) + Math.abs(this.mFocusRect.left - rect.left);
                if (i2 > iAbs) {
                    view = view3;
                    i2 = iAbs;
                }
            }
        }
        if (view != null) {
            this.mSelectionLogic.selectView(view);
        }
    }
}
