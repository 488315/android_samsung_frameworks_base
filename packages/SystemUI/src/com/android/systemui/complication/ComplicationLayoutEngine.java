package com.android.systemui.complication;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.complication.Complication;
import com.android.systemui.touch.TouchInsetManager;
import com.android.systemui.touch.TouchInsetManager$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.HashMap;

public final class ComplicationLayoutEngine implements Complication.VisibilityController {
    public final int mFadeInDuration;
    public final int mFadeOutDuration;
    public final ConstraintLayout mLayout;
    public final HashMap mEntries = new HashMap();
    public final HashMap mPositions = new HashMap();

    public final class DirectionGroup implements ViewEntry.Parent {
        public final Parent mParent;
        public final ArrayList mViews = new ArrayList();

        public interface Parent {
        }

        public DirectionGroup(Parent parent) {
            this.mParent = parent;
        }
    }

    public final class Margins {
        public final int bottom;
        public final int end;
        public final int start;
        public final int top;

        public Margins() {
            this(0, 0, 0, 0);
        }

        public static Margins combine(Margins margins, Margins margins2) {
            return new Margins(margins.start + margins2.start, margins.top + margins2.top, margins.end + margins2.end, margins.bottom + margins2.bottom);
        }

        public Margins(int i, int i2, int i3, int i4) {
            this.start = i;
            this.top = i2;
            this.end = i3;
            this.bottom = i4;
        }
    }

    public final class PositionGroup implements DirectionGroup.Parent {
        public final int mDefaultDirectionalSpacing;
        public final HashMap mDirectionGroups = new HashMap();
        public final HashMap mDirectionalMargins;

        public PositionGroup(int i, HashMap<Integer, Margins> hashMap) {
            this.mDefaultDirectionalSpacing = i;
            this.mDirectionalMargins = hashMap;
        }
    }

    public final class ViewEntry implements Comparable {
        public final int mCategory;
        public final ComplicationLayoutParams mLayoutParams;
        public final Parent mParent;
        public final TouchInsetManager.TouchInsetSession mTouchInsetSession;
        public final View mView;

        public interface Parent {
        }

        public ViewEntry(View view, ComplicationLayoutParams complicationLayoutParams, TouchInsetManager.TouchInsetSession touchInsetSession, int i, Parent parent) {
            this.mView = view;
            view.setId(View.generateViewId());
            this.mLayoutParams = complicationLayoutParams;
            this.mTouchInsetSession = touchInsetSession;
            this.mCategory = i;
            this.mParent = parent;
            touchInsetSession.mExecutor.execute(new TouchInsetManager$$ExternalSyntheticLambda0(touchInsetSession, view, 1));
        }

        @Override // java.lang.Comparable
        public final int compareTo(ViewEntry viewEntry) {
            int i = viewEntry.mCategory;
            int i2 = this.mCategory;
            if (i != i2) {
                return i2 == 2 ? 1 : -1;
            }
            int i3 = viewEntry.mLayoutParams.mWeight;
            int i4 = this.mLayoutParams.mWeight;
            if (i3 != i4) {
                return i4 > i3 ? 1 : -1;
            }
            return 0;
        }
    }

    public ComplicationLayoutEngine(ConstraintLayout constraintLayout, int i, int i2, int i3, int i4, int i5, TouchInsetManager.TouchInsetSession touchInsetSession, int i6, int i7) {
        this.mLayout = constraintLayout;
        this.mFadeInDuration = i6;
        this.mFadeOutDuration = i7;
        HashMap hashMap = new HashMap();
        Margins margins = new Margins(i2, 0, 0, 0);
        Margins margins2 = new Margins(0, i3, 0, 0);
        Margins margins3 = new Margins(0, 0, i4, 0);
        Margins margins4 = new Margins(0, 0, 0, i5);
        addToMapping(hashMap, 5, 8, margins2);
        addToMapping(hashMap, 5, 2, margins);
        addToMapping(hashMap, 6, 8, margins4);
        addToMapping(hashMap, 6, 1, margins);
        addToMapping(hashMap, 9, 4, margins2);
        addToMapping(hashMap, 9, 2, margins3);
        addToMapping(hashMap, 10, 4, margins4);
        addToMapping(hashMap, 10, 1, margins3);
    }

    public static void addToMapping(HashMap hashMap, int i, int i2, Margins margins) {
        if (!hashMap.containsKey(Integer.valueOf(i))) {
            hashMap.put(Integer.valueOf(i), new HashMap());
        }
        ((HashMap) hashMap.get(Integer.valueOf(i))).put(Integer.valueOf(i2), margins);
    }
}
