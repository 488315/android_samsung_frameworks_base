package androidx.leanback.widget;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.widget.GridLayoutManager;
import androidx.leanback.widget.ItemAlignmentFacet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ItemAlignmentFacetHelper {
    public static final Rect sRect = new Rect();

    private ItemAlignmentFacetHelper() {
    }

    public static int getAlignmentPosition(View view, ItemAlignmentFacet.ItemAlignmentDef itemAlignmentDef, int i) {
        View view2;
        int i2;
        int i3;
        int height;
        int width;
        int width2;
        int width3;
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        int i4 = itemAlignmentDef.mViewId;
        if (i4 == 0 || (view2 = view.findViewById(i4)) == null) {
            view2 = view;
        }
        Rect rect = sRect;
        int i5 = 0;
        float f = itemAlignmentDef.mOffsetPercent;
        if (i != 0) {
            if (f != -1.0f) {
                if (view2 == view) {
                    layoutParams.getClass();
                    height = (view2.getHeight() - layoutParams.mTopInset) - layoutParams.mBottomInset;
                } else {
                    height = view2.getHeight();
                }
                i5 = 0 + ((int) ((height * f) / 100.0f));
            }
            if (view != view2) {
                rect.top = i5;
                ((ViewGroup) view).offsetDescendantRectToMyCoords(view2, rect);
                i2 = rect.top;
                i3 = layoutParams.mTopInset;
                return i2 - i3;
            }
            return i5;
        }
        if (view.getLayoutDirection() != 1) {
            if (f != -1.0f) {
                if (view2 == view) {
                    layoutParams.getClass();
                    width = (view2.getWidth() - layoutParams.mLeftInset) - layoutParams.mRightInset;
                } else {
                    width = view2.getWidth();
                }
                i5 = 0 + ((int) ((width * f) / 100.0f));
            }
            if (view != view2) {
                rect.left = i5;
                ((ViewGroup) view).offsetDescendantRectToMyCoords(view2, rect);
                i2 = rect.left;
                i3 = layoutParams.mLeftInset;
                return i2 - i3;
            }
            return i5;
        }
        if (view2 == view) {
            layoutParams.getClass();
            width2 = (view2.getWidth() - layoutParams.mLeftInset) - layoutParams.mRightInset;
        } else {
            width2 = view2.getWidth();
        }
        int i6 = width2 - 0;
        if (f != -1.0f) {
            if (view2 == view) {
                layoutParams.getClass();
                width3 = (view2.getWidth() - layoutParams.mLeftInset) - layoutParams.mRightInset;
            } else {
                width3 = view2.getWidth();
            }
            i6 -= (int) ((width3 * f) / 100.0f);
        }
        if (view == view2) {
            return i6;
        }
        rect.right = i6;
        ((ViewGroup) view).offsetDescendantRectToMyCoords(view2, rect);
        return rect.right + layoutParams.mRightInset;
    }
}
