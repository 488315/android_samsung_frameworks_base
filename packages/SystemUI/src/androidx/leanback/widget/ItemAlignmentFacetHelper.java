package androidx.leanback.widget;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.widget.GridLayoutManager;

/* loaded from: classes.dex */
public class ItemAlignmentFacetHelper {
    public static final Rect sRect = new Rect();

    private ItemAlignmentFacetHelper() {
    }

    public static int getAlignmentPosition(View view, ItemAlignmentFacet$ItemAlignmentDef itemAlignmentFacet$ItemAlignmentDef, int i) {
        int height;
        int width;
        int width2;
        int width3;
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        itemAlignmentFacet$ItemAlignmentDef.getClass();
        View viewFindViewById = view.findViewById(-1);
        if (viewFindViewById == null) {
            viewFindViewById = view;
        }
        if (i != 0) {
            if (viewFindViewById == view) {
                layoutParams.getClass();
                height = (viewFindViewById.getHeight() - layoutParams.mTopInset) - layoutParams.mBottomInset;
            } else {
                height = viewFindViewById.getHeight();
            }
            int i2 = (int) ((height * 50.0f) / 100.0f);
            if (view == viewFindViewById) {
                return i2;
            }
            Rect rect = sRect;
            rect.top = i2;
            ((ViewGroup) view).offsetDescendantRectToMyCoords(viewFindViewById, rect);
            return rect.top - layoutParams.mTopInset;
        }
        if (view.getLayoutDirection() != 1) {
            if (viewFindViewById == view) {
                layoutParams.getClass();
                width = (viewFindViewById.getWidth() - layoutParams.mLeftInset) - layoutParams.mRightInset;
            } else {
                width = viewFindViewById.getWidth();
            }
            int i3 = (int) ((width * 50.0f) / 100.0f);
            if (view == viewFindViewById) {
                return i3;
            }
            Rect rect2 = sRect;
            rect2.left = i3;
            ((ViewGroup) view).offsetDescendantRectToMyCoords(viewFindViewById, rect2);
            return rect2.left - layoutParams.mLeftInset;
        }
        if (viewFindViewById == view) {
            layoutParams.getClass();
            width2 = (viewFindViewById.getWidth() - layoutParams.mLeftInset) - layoutParams.mRightInset;
        } else {
            width2 = viewFindViewById.getWidth();
        }
        if (viewFindViewById == view) {
            layoutParams.getClass();
            width3 = (viewFindViewById.getWidth() - layoutParams.mLeftInset) - layoutParams.mRightInset;
        } else {
            width3 = viewFindViewById.getWidth();
        }
        int i4 = width2 - ((int) ((width3 * 50.0f) / 100.0f));
        if (view == viewFindViewById) {
            return i4;
        }
        Rect rect3 = sRect;
        rect3.right = i4;
        ((ViewGroup) view).offsetDescendantRectToMyCoords(viewFindViewById, rect3);
        return rect3.right + layoutParams.mRightInset;
    }
}
