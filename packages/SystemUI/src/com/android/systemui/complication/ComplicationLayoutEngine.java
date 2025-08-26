package com.android.systemui.complication;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import com.android.systemui.R;
import com.android.systemui.complication.Complication;
import com.android.systemui.complication.ComplicationLayoutEngine;
import com.android.systemui.touch.TouchInsetManager;
import com.android.systemui.touch.TouchInsetManager$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public class ComplicationLayoutEngine implements Complication.VisibilityController {
    public final Provider mComplicationMarginsProvider;
    public final int mDefaultDirectionalSpacing;
    public final int mFadeInDuration;
    public final int mFadeOutDuration;
    public final ConstraintLayout mLayout;
    public final HashMap mPositionDirectionMarginMapping;
    public Rect mScreenBounds;
    public final TouchInsetManager.TouchInsetSession mSession;
    public final HashMap mEntries = new HashMap();
    public final HashMap mPositions = new HashMap();

    public class DirectionGroup implements ViewEntry.Parent {
        public final Parent mParent;
        public final ArrayList mViews = new ArrayList();

        public interface Parent {
        }

        public DirectionGroup(Parent parent) {
            this.mParent = parent;
        }
    }

    public class Margins {
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

    public class PositionGroup implements DirectionGroup.Parent {
        public final int mDefaultDirectionalSpacing;
        public final HashMap mDirectionGroups = new HashMap();
        public final HashMap mDirectionalMargins;

        public PositionGroup(int i, HashMap<Integer, Margins> map) {
            this.mDefaultDirectionalSpacing = i;
            this.mDirectionalMargins = map;
        }

        public final void onEntriesChanged() {
            ViewEntry viewEntry = null;
            for (DirectionGroup directionGroup : this.mDirectionGroups.values()) {
                ViewEntry viewEntry2 = directionGroup.mViews.isEmpty() ? null : (ViewEntry) directionGroup.mViews.get(0);
                if (viewEntry == null || (viewEntry2 != null && viewEntry2.compareTo(viewEntry) > 0)) {
                    viewEntry = viewEntry2;
                }
            }
            if (viewEntry == null) {
                return;
            }
            for (DirectionGroup directionGroup2 : this.mDirectionGroups.values()) {
                View view = viewEntry.mView;
                ArrayList arrayList = directionGroup2.mViews;
                int size = arrayList.size();
                final View view2 = view;
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    final ViewEntry viewEntry3 = (ViewEntry) obj;
                    viewEntry3.getClass();
                    ComplicationLayoutParams complicationLayoutParams = viewEntry3.mLayoutParams;
                    final Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(((ViewGroup.LayoutParams) complicationLayoutParams).width, ((ViewGroup.LayoutParams) complicationLayoutParams).height);
                    ComplicationLayoutParams complicationLayoutParams2 = viewEntry3.mLayoutParams;
                    final int i2 = complicationLayoutParams2.mDirection;
                    final boolean z = complicationLayoutParams2.mSnapToGuide;
                    final boolean z2 = view2 == viewEntry3.mView;
                    ComplicationLayoutParams.iteratePositions(complicationLayoutParams2.mPosition, new Consumer() { // from class: com.android.systemui.complication.ComplicationLayoutEngine$ViewEntry$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj2) {
                            ComplicationLayoutEngine.Margins margins;
                            ComplicationLayoutEngine.ViewEntry viewEntry4 = viewEntry3;
                            boolean z3 = z2;
                            int i3 = i2;
                            Constraints.LayoutParams layoutParams2 = layoutParams;
                            View view3 = view2;
                            boolean z4 = z;
                            int iIntValue = ((Integer) obj2).intValue();
                            if (iIntValue == 1) {
                                if (z3 || i3 != 2) {
                                    layoutParams2.topToTop = 0;
                                } else {
                                    layoutParams2.topToBottom = view3.getId();
                                }
                                if (z4 && (i3 == 8 || i3 == 4)) {
                                    layoutParams2.endToStart = R.id.complication_top_guide;
                                }
                            } else if (iIntValue == 2) {
                                if (z3 || i3 != 1) {
                                    layoutParams2.bottomToBottom = 0;
                                } else {
                                    layoutParams2.bottomToTop = view3.getId();
                                }
                                if (z4 && (i3 == 8 || i3 == 4)) {
                                    layoutParams2.topToBottom = R.id.complication_bottom_guide;
                                }
                            } else if (iIntValue == 4) {
                                if (z3 || i3 != 8) {
                                    layoutParams2.startToStart = 0;
                                } else {
                                    layoutParams2.startToEnd = view3.getId();
                                }
                                if (z4 && (i3 == 2 || i3 == 1)) {
                                    layoutParams2.endToStart = R.id.complication_start_guide;
                                }
                            } else if (iIntValue == 8) {
                                if (z3 || i3 != 4) {
                                    layoutParams2.endToEnd = 0;
                                } else {
                                    layoutParams2.endToStart = view3.getId();
                                }
                                if (z4 && (i3 == 1 || i3 == 2)) {
                                    layoutParams2.startToEnd = R.id.complication_end_guide;
                                }
                            }
                            ComplicationLayoutEngine.DirectionGroup directionGroup3 = (ComplicationLayoutEngine.DirectionGroup) viewEntry4.mParent;
                            directionGroup3.getClass();
                            ComplicationLayoutParams complicationLayoutParams3 = viewEntry4.mLayoutParams;
                            ComplicationLayoutEngine.PositionGroup positionGroup = (ComplicationLayoutEngine.PositionGroup) directionGroup3.mParent;
                            int i4 = positionGroup.mDefaultDirectionalSpacing;
                            int i5 = complicationLayoutParams3.mDirectionalSpacing;
                            if (i5 != -1) {
                                i4 = i5;
                            }
                            ComplicationLayoutEngine.Margins margins2 = new ComplicationLayoutEngine.Margins();
                            if (!z3) {
                                int i6 = viewEntry4.mLayoutParams.mDirection;
                                if (i6 == 1) {
                                    margins2 = new ComplicationLayoutEngine.Margins(0, 0, 0, i4);
                                } else if (i6 == 2) {
                                    margins2 = new ComplicationLayoutEngine.Margins(0, i4, 0, 0);
                                } else if (i6 == 4) {
                                    margins2 = new ComplicationLayoutEngine.Margins(0, 0, i4, 0);
                                } else if (i6 == 8) {
                                    margins2 = new ComplicationLayoutEngine.Margins(i4, 0, 0, 0);
                                }
                            }
                            if (z3) {
                                margins = new ComplicationLayoutEngine.Margins();
                                Iterator it = positionGroup.mDirectionalMargins.values().iterator();
                                while (it.hasNext()) {
                                    margins = ComplicationLayoutEngine.Margins.combine((ComplicationLayoutEngine.Margins) it.next(), margins);
                                }
                            } else {
                                margins = (ComplicationLayoutEngine.Margins) positionGroup.mDirectionalMargins.get(Integer.valueOf(viewEntry4.mLayoutParams.mDirection));
                            }
                            ComplicationLayoutEngine.Margins marginsCombine = ComplicationLayoutEngine.Margins.combine(margins, margins2);
                            layoutParams2.setMarginsRelative(marginsCombine.start, marginsCombine.top, marginsCombine.end, marginsCombine.bottom);
                        }
                    });
                    int i3 = viewEntry3.mLayoutParams.mConstraint;
                    if (i3 != -1) {
                        if (i2 == 1 || i2 == 2) {
                            layoutParams.matchConstraintMaxHeight = i3;
                        } else if (i2 == 4 || i2 == 8) {
                            layoutParams.matchConstraintMaxWidth = i3;
                        }
                    }
                    viewEntry3.mView.setLayoutParams(layoutParams);
                    view2 = viewEntry3.mView;
                }
            }
        }
    }

    public class ViewEntry implements Comparable {
        public final int mCategory;
        public final ComplicationLayoutParams mLayoutParams;
        public final Parent mParent;
        public final TouchInsetManager.TouchInsetSession mTouchInsetSession;
        public final View mView;

        public class Builder {
            public final int mCategory;
            public final ComplicationLayoutParams mLayoutParams;
            public DirectionGroup mParent;
            public final TouchInsetManager.TouchInsetSession mTouchSession;
            public final View mView;

            public Builder(View view, TouchInsetManager.TouchInsetSession touchInsetSession, ComplicationLayoutParams complicationLayoutParams, int i) {
                this.mView = view;
                this.mLayoutParams = complicationLayoutParams;
                this.mCategory = i;
                this.mTouchSession = touchInsetSession;
            }
        }

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

    public ComplicationLayoutEngine(ConstraintLayout constraintLayout, int i, Provider provider, TouchInsetManager.TouchInsetSession touchInsetSession, int i2, int i3) {
        HashMap map = new HashMap();
        this.mPositionDirectionMarginMapping = map;
        this.mScreenBounds = new Rect();
        this.mLayout = constraintLayout;
        this.mDefaultDirectionalSpacing = i;
        this.mSession = touchInsetSession;
        this.mFadeInDuration = i2;
        this.mFadeOutDuration = i3;
        this.mComplicationMarginsProvider = provider;
        updatePositionDirectionalMarginsMapping(map, (Margins) provider.get());
    }

    public static void addToMapping(Map map, int i, int i2, Margins margins) {
        HashMap map2 = (HashMap) map;
        if (!map2.containsKey(Integer.valueOf(i))) {
            map2.put(Integer.valueOf(i), new HashMap());
        }
        ((HashMap) map2.get(Integer.valueOf(i))).put(Integer.valueOf(i2), margins);
    }

    public static void updatePositionDirectionalMarginsMapping(Map map, Margins margins) {
        Margins margins2 = new Margins(margins.start, 0, 0, 0);
        Margins margins3 = new Margins(0, margins.top, 0, 0);
        Margins margins4 = new Margins(0, 0, margins.end, 0);
        Margins margins5 = new Margins(0, 0, 0, margins.bottom);
        addToMapping(map, 5, 8, margins3);
        addToMapping(map, 5, 2, margins2);
        addToMapping(map, 6, 8, margins5);
        addToMapping(map, 6, 1, margins2);
        addToMapping(map, 9, 4, margins3);
        addToMapping(map, 9, 2, margins4);
        addToMapping(map, 10, 4, margins5);
        addToMapping(map, 10, 1, margins4);
    }

    public final void removeComplication(ComplicationId complicationId) {
        ViewEntry viewEntry = (ViewEntry) this.mEntries.remove(complicationId);
        if (viewEntry == null) {
            Log.e("ComplicationLayoutEng", "could not find id:" + complicationId);
            return;
        }
        DirectionGroup directionGroup = (DirectionGroup) viewEntry.mParent;
        directionGroup.mViews.remove(viewEntry);
        ((PositionGroup) directionGroup.mParent).onEntriesChanged();
        ((ViewGroup) viewEntry.mView.getParent()).removeView(viewEntry.mView);
        TouchInsetManager.TouchInsetSession touchInsetSession = viewEntry.mTouchInsetSession;
        touchInsetSession.mExecutor.execute(new TouchInsetManager$$ExternalSyntheticLambda0(touchInsetSession, viewEntry.mView, 2));
    }
}
