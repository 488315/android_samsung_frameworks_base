package androidx.leanback.widget;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.widget.GridLayoutManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        View findViewById = view.findViewById(-1);
        if (findViewById == null) {
            findViewById = view;
        }
        if (i != 0) {
            if (findViewById == view) {
                layoutParams.getClass();
                height = (findViewById.getHeight() - layoutParams.mTopInset) - layoutParams.mBottomInset;
            } else {
                height = findViewById.getHeight();
            }
            int i2 = (int) ((height * 50.0f) / 100.0f);
            if (view == findViewById) {
                return i2;
            }
            Rect rect = sRect;
            rect.top = i2;
            ((ViewGroup) view).offsetDescendantRectToMyCoords(findViewById, rect);
            return rect.top - layoutParams.mTopInset;
        }
        if (view.getLayoutDirection() != 1) {
            if (findViewById == view) {
                layoutParams.getClass();
                width = (findViewById.getWidth() - layoutParams.mLeftInset) - layoutParams.mRightInset;
            } else {
                width = findViewById.getWidth();
            }
            int i3 = (int) ((width * 50.0f) / 100.0f);
            if (view == findViewById) {
                return i3;
            }
            Rect rect2 = sRect;
            rect2.left = i3;
            ((ViewGroup) view).offsetDescendantRectToMyCoords(findViewById, rect2);
            return rect2.left - layoutParams.mLeftInset;
        }
        if (findViewById == view) {
            layoutParams.getClass();
            width2 = (findViewById.getWidth() - layoutParams.mLeftInset) - layoutParams.mRightInset;
        } else {
            width2 = findViewById.getWidth();
        }
        if (findViewById == view) {
            layoutParams.getClass();
            width3 = (findViewById.getWidth() - layoutParams.mLeftInset) - layoutParams.mRightInset;
        } else {
            width3 = findViewById.getWidth();
        }
        int i4 = width2 - ((int) ((width3 * 50.0f) / 100.0f));
        if (view == findViewById) {
            return i4;
        }
        Rect rect3 = sRect;
        rect3.right = i4;
        ((ViewGroup) view).offsetDescendantRectToMyCoords(findViewById, rect3);
        return rect3.right + layoutParams.mRightInset;
    }
}
