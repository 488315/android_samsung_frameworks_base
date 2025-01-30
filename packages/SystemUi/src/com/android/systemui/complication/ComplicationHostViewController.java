package com.android.systemui.complication;

import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.android.systemui.R;
import com.android.systemui.complication.ComplicationLayoutEngine;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.touch.TouchInsetManager;
import com.android.systemui.touch.TouchInsetManager$$ExternalSyntheticLambda2;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ComplicationHostViewController extends ViewController {
    public static final boolean DEBUG = Log.isLoggable("ComplicationHostVwCtrl", 3);
    public final ComplicationCollectionViewModel mComplicationCollectionViewModel;
    public final HashMap mComplications;
    boolean mIsAnimationEnabled;
    public final ComplicationLayoutEngine mLayoutEngine;
    public final LifecycleOwner mLifecycleOwner;

    public ComplicationHostViewController(ConstraintLayout constraintLayout, ComplicationLayoutEngine complicationLayoutEngine, DreamOverlayStateController dreamOverlayStateController, LifecycleOwner lifecycleOwner, ComplicationCollectionViewModel complicationCollectionViewModel, SecureSettings secureSettings) {
        super(constraintLayout);
        this.mComplications = new HashMap();
        this.mLayoutEngine = complicationLayoutEngine;
        this.mLifecycleOwner = lifecycleOwner;
        this.mComplicationCollectionViewModel = complicationCollectionViewModel;
        this.mIsAnimationEnabled = secureSettings.getFloatForUser("animator_duration_scale", -2, 1.0f) != 0.0f;
    }

    public final List getViewsAtPosition(final int i) {
        final int i2 = 0;
        Stream flatMap = this.mLayoutEngine.mPositions.entrySet().stream().filter(new Predicate() { // from class: com.android.systemui.complication.ComplicationLayoutEngine$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                int i3 = i;
                return (((Integer) ((Map.Entry) obj).getKey()).intValue() & i3) == i3;
            }
        }).flatMap(new Function() { // from class: com.android.systemui.complication.ComplicationLayoutEngine$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i2) {
                    case 0:
                        ComplicationLayoutEngine.PositionGroup positionGroup = (ComplicationLayoutEngine.PositionGroup) ((Map.Entry) obj).getValue();
                        positionGroup.getClass();
                        ArrayList arrayList = new ArrayList();
                        Iterator it = positionGroup.mDirectionGroups.values().iterator();
                        while (it.hasNext()) {
                            arrayList.addAll(((ComplicationLayoutEngine.DirectionGroup) it.next()).mViews);
                        }
                        return arrayList.stream();
                    default:
                        return ((ComplicationLayoutEngine.ViewEntry) obj).mView;
                }
            }
        });
        final int i3 = 1;
        return (List) flatMap.map(new Function() { // from class: com.android.systemui.complication.ComplicationLayoutEngine$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i3) {
                    case 0:
                        ComplicationLayoutEngine.PositionGroup positionGroup = (ComplicationLayoutEngine.PositionGroup) ((Map.Entry) obj).getValue();
                        positionGroup.getClass();
                        ArrayList arrayList = new ArrayList();
                        Iterator it = positionGroup.mDirectionGroups.values().iterator();
                        while (it.hasNext()) {
                            arrayList.addAll(((ComplicationLayoutEngine.DirectionGroup) it.next()).mViews);
                        }
                        return arrayList.stream();
                    default:
                        return ((ComplicationLayoutEngine.ViewEntry) obj).mView;
                }
            }
        }).collect(Collectors.toList());
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        this.mComplicationCollectionViewModel.mComplications.observe(this.mLifecycleOwner, new Observer() { // from class: com.android.systemui.complication.ComplicationHostViewController$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Collection collection = (Collection) obj;
                final ComplicationHostViewController complicationHostViewController = ComplicationHostViewController.this;
                HashMap hashMap = complicationHostViewController.mComplications;
                if (ComplicationHostViewController.DEBUG) {
                    Log.d("ComplicationHostVwCtrl", "updateComplications called. Callers = " + Debug.getCallers(25));
                    Log.d("ComplicationHostVwCtrl", "    mComplications = " + hashMap.toString());
                    Log.d("ComplicationHostVwCtrl", "    complications = " + collection.toString());
                }
                final Collection collection2 = (Collection) collection.stream().map(new ComplicationHostViewController$$ExternalSyntheticLambda1()).collect(Collectors.toSet());
                final int i = 0;
                ((Collection) hashMap.keySet().stream().filter(new Predicate() { // from class: com.android.systemui.complication.ComplicationHostViewController$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        boolean containsKey;
                        switch (i) {
                            case 0:
                                containsKey = ((Collection) collection2).contains((ComplicationId) obj2);
                                break;
                            default:
                                containsKey = ((ComplicationHostViewController) collection2).mComplications.containsKey(((ComplicationViewModel) obj2).mId);
                                break;
                        }
                        return !containsKey;
                    }
                }).collect(Collectors.toSet())).forEach(new Consumer() { // from class: com.android.systemui.complication.ComplicationHostViewController$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        Constraints.LayoutParams layoutParams;
                        switch (i) {
                            case 0:
                                ComplicationHostViewController complicationHostViewController2 = complicationHostViewController;
                                ComplicationId complicationId = (ComplicationId) obj2;
                                ComplicationLayoutEngine.ViewEntry viewEntry = (ComplicationLayoutEngine.ViewEntry) complicationHostViewController2.mLayoutEngine.mEntries.remove(complicationId);
                                if (viewEntry == null) {
                                    Log.e("ComplicationLayoutEng", "could not find id:" + complicationId);
                                } else {
                                    ComplicationLayoutEngine.DirectionGroup directionGroup = (ComplicationLayoutEngine.DirectionGroup) viewEntry.mParent;
                                    directionGroup.mViews.remove(viewEntry);
                                    HashMap hashMap2 = ((ComplicationLayoutEngine.PositionGroup) directionGroup.mParent).mDirectionGroups;
                                    Iterator it = hashMap2.values().iterator();
                                    ComplicationLayoutEngine.ViewEntry viewEntry2 = null;
                                    while (true) {
                                        boolean z = false;
                                        if (it.hasNext()) {
                                            ArrayList arrayList = ((ComplicationLayoutEngine.DirectionGroup) it.next()).mViews;
                                            ComplicationLayoutEngine.ViewEntry viewEntry3 = arrayList.isEmpty() ? null : (ComplicationLayoutEngine.ViewEntry) arrayList.get(0);
                                            if (viewEntry2 == null || (viewEntry3 != null && viewEntry3.compareTo(viewEntry2) > 0)) {
                                                viewEntry2 = viewEntry3;
                                            }
                                        } else {
                                            if (viewEntry2 != null) {
                                                for (ComplicationLayoutEngine.DirectionGroup directionGroup2 : hashMap2.values()) {
                                                    View view = viewEntry2.mView;
                                                    Iterator it2 = directionGroup2.mViews.iterator();
                                                    final View view2 = view;
                                                    while (it2.hasNext()) {
                                                        final ComplicationLayoutEngine.ViewEntry viewEntry4 = (ComplicationLayoutEngine.ViewEntry) it2.next();
                                                        viewEntry4.getClass();
                                                        ComplicationLayoutParams complicationLayoutParams = viewEntry4.mLayoutParams;
                                                        final Constraints.LayoutParams layoutParams2 = new Constraints.LayoutParams(((ViewGroup.LayoutParams) complicationLayoutParams).width, ((ViewGroup.LayoutParams) complicationLayoutParams).height);
                                                        ComplicationLayoutParams complicationLayoutParams2 = viewEntry4.mLayoutParams;
                                                        final int i2 = complicationLayoutParams2.mDirection;
                                                        final boolean z2 = complicationLayoutParams2.mSnapToGuide;
                                                        final boolean z3 = view2 == viewEntry4.mView ? true : z;
                                                        ComplicationLayoutParams.iteratePositions(complicationLayoutParams2.mPosition, new Consumer() { // from class: com.android.systemui.complication.ComplicationLayoutEngine$ViewEntry$$ExternalSyntheticLambda0
                                                            @Override // java.util.function.Consumer
                                                            public final void accept(Object obj3) {
                                                                ComplicationLayoutEngine.Margins margins;
                                                                ComplicationLayoutEngine.ViewEntry viewEntry5 = ComplicationLayoutEngine.ViewEntry.this;
                                                                boolean z4 = z3;
                                                                int i3 = i2;
                                                                ConstraintLayout.LayoutParams layoutParams3 = layoutParams2;
                                                                View view3 = view2;
                                                                boolean z5 = z2;
                                                                viewEntry5.getClass();
                                                                int intValue = ((Integer) obj3).intValue();
                                                                if (intValue == 1) {
                                                                    if (z4 || i3 != 2) {
                                                                        layoutParams3.topToTop = 0;
                                                                    } else {
                                                                        layoutParams3.topToBottom = view3.getId();
                                                                    }
                                                                    if (z5 && (i3 == 8 || i3 == 4)) {
                                                                        layoutParams3.endToStart = R.id.complication_top_guide;
                                                                    }
                                                                } else if (intValue == 2) {
                                                                    if (z4 || i3 != 1) {
                                                                        layoutParams3.bottomToBottom = 0;
                                                                    } else {
                                                                        layoutParams3.bottomToTop = view3.getId();
                                                                    }
                                                                    if (z5 && (i3 == 8 || i3 == 4)) {
                                                                        layoutParams3.topToBottom = R.id.complication_bottom_guide;
                                                                    }
                                                                } else if (intValue == 4) {
                                                                    if (z4 || i3 != 8) {
                                                                        layoutParams3.startToStart = 0;
                                                                    } else {
                                                                        layoutParams3.startToEnd = view3.getId();
                                                                    }
                                                                    if (z5 && (i3 == 2 || i3 == 1)) {
                                                                        layoutParams3.endToStart = R.id.complication_start_guide;
                                                                    }
                                                                } else if (intValue == 8) {
                                                                    if (z4 || i3 != 4) {
                                                                        layoutParams3.endToEnd = 0;
                                                                    } else {
                                                                        layoutParams3.endToStart = view3.getId();
                                                                    }
                                                                    if (z5 && (i3 == 1 || i3 == 2)) {
                                                                        layoutParams3.startToEnd = R.id.complication_end_guide;
                                                                    }
                                                                }
                                                                ComplicationLayoutEngine.DirectionGroup directionGroup3 = (ComplicationLayoutEngine.DirectionGroup) viewEntry5.mParent;
                                                                directionGroup3.getClass();
                                                                ComplicationLayoutParams complicationLayoutParams3 = viewEntry5.mLayoutParams;
                                                                ComplicationLayoutEngine.PositionGroup positionGroup = (ComplicationLayoutEngine.PositionGroup) directionGroup3.mParent;
                                                                int i4 = positionGroup.mDefaultDirectionalSpacing;
                                                                int i5 = complicationLayoutParams3.mDirectionalSpacing;
                                                                if (i5 != -1) {
                                                                    i4 = i5;
                                                                }
                                                                ComplicationLayoutEngine.Margins margins2 = new ComplicationLayoutEngine.Margins();
                                                                if (!z4) {
                                                                    int i6 = viewEntry5.mLayoutParams.mDirection;
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
                                                                HashMap hashMap3 = positionGroup.mDirectionalMargins;
                                                                if (z4) {
                                                                    margins = new ComplicationLayoutEngine.Margins();
                                                                    for (ComplicationLayoutEngine.Margins margins3 : hashMap3.values()) {
                                                                        margins = new ComplicationLayoutEngine.Margins(margins3.start + margins.start, margins3.top + margins.top, margins3.end + margins.end, margins3.bottom + margins.bottom);
                                                                    }
                                                                } else {
                                                                    margins = (ComplicationLayoutEngine.Margins) hashMap3.get(Integer.valueOf(viewEntry5.mLayoutParams.mDirection));
                                                                }
                                                                ComplicationLayoutEngine.Margins margins4 = new ComplicationLayoutEngine.Margins(margins.start + margins2.start, margins.top + margins2.top, margins.end + margins2.end, margins.bottom + margins2.bottom);
                                                                layoutParams3.setMarginsRelative(margins4.start, margins4.top, margins4.end, margins4.bottom);
                                                            }
                                                        });
                                                        int i3 = viewEntry4.mLayoutParams.mConstraint;
                                                        if (i3 != -1) {
                                                            if (i2 == 1 || i2 == 2) {
                                                                layoutParams = layoutParams2;
                                                                layoutParams.matchConstraintMaxHeight = i3;
                                                            } else if (i2 == 4 || i2 == 8) {
                                                                layoutParams = layoutParams2;
                                                                layoutParams.matchConstraintMaxWidth = i3;
                                                            }
                                                            viewEntry4.mView.setLayoutParams(layoutParams);
                                                            view2 = viewEntry4.mView;
                                                            z = false;
                                                        }
                                                        layoutParams = layoutParams2;
                                                        viewEntry4.mView.setLayoutParams(layoutParams);
                                                        view2 = viewEntry4.mView;
                                                        z = false;
                                                    }
                                                }
                                            }
                                            ((ViewGroup) viewEntry.mView.getParent()).removeView(viewEntry.mView);
                                            TouchInsetManager.TouchInsetSession touchInsetSession = viewEntry.mTouchInsetSession;
                                            View view3 = viewEntry.mView;
                                            touchInsetSession.getClass();
                                            touchInsetSession.mExecutor.execute(new TouchInsetManager$$ExternalSyntheticLambda2(touchInsetSession, view3, 2));
                                        }
                                    }
                                }
                                complicationHostViewController2.mComplications.remove(complicationId);
                                return;
                            default:
                                complicationHostViewController.getClass();
                                ComplicationId complicationId2 = ((ComplicationViewModel) obj2).mId;
                                throw null;
                        }
                    }
                });
                final int i2 = 1;
                ((Collection) collection.stream().filter(new Predicate() { // from class: com.android.systemui.complication.ComplicationHostViewController$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        boolean containsKey;
                        switch (i2) {
                            case 0:
                                containsKey = ((Collection) complicationHostViewController).contains((ComplicationId) obj2);
                                break;
                            default:
                                containsKey = ((ComplicationHostViewController) complicationHostViewController).mComplications.containsKey(((ComplicationViewModel) obj2).mId);
                                break;
                        }
                        return !containsKey;
                    }
                }).collect(Collectors.toSet())).forEach(new Consumer() { // from class: com.android.systemui.complication.ComplicationHostViewController$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        Constraints.LayoutParams layoutParams;
                        switch (i2) {
                            case 0:
                                ComplicationHostViewController complicationHostViewController2 = complicationHostViewController;
                                ComplicationId complicationId = (ComplicationId) obj2;
                                ComplicationLayoutEngine.ViewEntry viewEntry = (ComplicationLayoutEngine.ViewEntry) complicationHostViewController2.mLayoutEngine.mEntries.remove(complicationId);
                                if (viewEntry == null) {
                                    Log.e("ComplicationLayoutEng", "could not find id:" + complicationId);
                                } else {
                                    ComplicationLayoutEngine.DirectionGroup directionGroup = (ComplicationLayoutEngine.DirectionGroup) viewEntry.mParent;
                                    directionGroup.mViews.remove(viewEntry);
                                    HashMap hashMap2 = ((ComplicationLayoutEngine.PositionGroup) directionGroup.mParent).mDirectionGroups;
                                    Iterator it = hashMap2.values().iterator();
                                    ComplicationLayoutEngine.ViewEntry viewEntry2 = null;
                                    while (true) {
                                        boolean z = false;
                                        if (it.hasNext()) {
                                            ArrayList arrayList = ((ComplicationLayoutEngine.DirectionGroup) it.next()).mViews;
                                            ComplicationLayoutEngine.ViewEntry viewEntry3 = arrayList.isEmpty() ? null : (ComplicationLayoutEngine.ViewEntry) arrayList.get(0);
                                            if (viewEntry2 == null || (viewEntry3 != null && viewEntry3.compareTo(viewEntry2) > 0)) {
                                                viewEntry2 = viewEntry3;
                                            }
                                        } else {
                                            if (viewEntry2 != null) {
                                                for (ComplicationLayoutEngine.DirectionGroup directionGroup2 : hashMap2.values()) {
                                                    View view = viewEntry2.mView;
                                                    Iterator it2 = directionGroup2.mViews.iterator();
                                                    final View view2 = view;
                                                    while (it2.hasNext()) {
                                                        final ComplicationLayoutEngine.ViewEntry viewEntry4 = (ComplicationLayoutEngine.ViewEntry) it2.next();
                                                        viewEntry4.getClass();
                                                        ComplicationLayoutParams complicationLayoutParams = viewEntry4.mLayoutParams;
                                                        final Constraints.LayoutParams layoutParams2 = new Constraints.LayoutParams(((ViewGroup.LayoutParams) complicationLayoutParams).width, ((ViewGroup.LayoutParams) complicationLayoutParams).height);
                                                        ComplicationLayoutParams complicationLayoutParams2 = viewEntry4.mLayoutParams;
                                                        final int i22 = complicationLayoutParams2.mDirection;
                                                        final boolean z2 = complicationLayoutParams2.mSnapToGuide;
                                                        final boolean z3 = view2 == viewEntry4.mView ? true : z;
                                                        ComplicationLayoutParams.iteratePositions(complicationLayoutParams2.mPosition, new Consumer() { // from class: com.android.systemui.complication.ComplicationLayoutEngine$ViewEntry$$ExternalSyntheticLambda0
                                                            @Override // java.util.function.Consumer
                                                            public final void accept(Object obj3) {
                                                                ComplicationLayoutEngine.Margins margins;
                                                                ComplicationLayoutEngine.ViewEntry viewEntry5 = ComplicationLayoutEngine.ViewEntry.this;
                                                                boolean z4 = z3;
                                                                int i3 = i22;
                                                                ConstraintLayout.LayoutParams layoutParams3 = layoutParams2;
                                                                View view3 = view2;
                                                                boolean z5 = z2;
                                                                viewEntry5.getClass();
                                                                int intValue = ((Integer) obj3).intValue();
                                                                if (intValue == 1) {
                                                                    if (z4 || i3 != 2) {
                                                                        layoutParams3.topToTop = 0;
                                                                    } else {
                                                                        layoutParams3.topToBottom = view3.getId();
                                                                    }
                                                                    if (z5 && (i3 == 8 || i3 == 4)) {
                                                                        layoutParams3.endToStart = R.id.complication_top_guide;
                                                                    }
                                                                } else if (intValue == 2) {
                                                                    if (z4 || i3 != 1) {
                                                                        layoutParams3.bottomToBottom = 0;
                                                                    } else {
                                                                        layoutParams3.bottomToTop = view3.getId();
                                                                    }
                                                                    if (z5 && (i3 == 8 || i3 == 4)) {
                                                                        layoutParams3.topToBottom = R.id.complication_bottom_guide;
                                                                    }
                                                                } else if (intValue == 4) {
                                                                    if (z4 || i3 != 8) {
                                                                        layoutParams3.startToStart = 0;
                                                                    } else {
                                                                        layoutParams3.startToEnd = view3.getId();
                                                                    }
                                                                    if (z5 && (i3 == 2 || i3 == 1)) {
                                                                        layoutParams3.endToStart = R.id.complication_start_guide;
                                                                    }
                                                                } else if (intValue == 8) {
                                                                    if (z4 || i3 != 4) {
                                                                        layoutParams3.endToEnd = 0;
                                                                    } else {
                                                                        layoutParams3.endToStart = view3.getId();
                                                                    }
                                                                    if (z5 && (i3 == 1 || i3 == 2)) {
                                                                        layoutParams3.startToEnd = R.id.complication_end_guide;
                                                                    }
                                                                }
                                                                ComplicationLayoutEngine.DirectionGroup directionGroup3 = (ComplicationLayoutEngine.DirectionGroup) viewEntry5.mParent;
                                                                directionGroup3.getClass();
                                                                ComplicationLayoutParams complicationLayoutParams3 = viewEntry5.mLayoutParams;
                                                                ComplicationLayoutEngine.PositionGroup positionGroup = (ComplicationLayoutEngine.PositionGroup) directionGroup3.mParent;
                                                                int i4 = positionGroup.mDefaultDirectionalSpacing;
                                                                int i5 = complicationLayoutParams3.mDirectionalSpacing;
                                                                if (i5 != -1) {
                                                                    i4 = i5;
                                                                }
                                                                ComplicationLayoutEngine.Margins margins2 = new ComplicationLayoutEngine.Margins();
                                                                if (!z4) {
                                                                    int i6 = viewEntry5.mLayoutParams.mDirection;
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
                                                                HashMap hashMap3 = positionGroup.mDirectionalMargins;
                                                                if (z4) {
                                                                    margins = new ComplicationLayoutEngine.Margins();
                                                                    for (ComplicationLayoutEngine.Margins margins3 : hashMap3.values()) {
                                                                        margins = new ComplicationLayoutEngine.Margins(margins3.start + margins.start, margins3.top + margins.top, margins3.end + margins.end, margins3.bottom + margins.bottom);
                                                                    }
                                                                } else {
                                                                    margins = (ComplicationLayoutEngine.Margins) hashMap3.get(Integer.valueOf(viewEntry5.mLayoutParams.mDirection));
                                                                }
                                                                ComplicationLayoutEngine.Margins margins4 = new ComplicationLayoutEngine.Margins(margins.start + margins2.start, margins.top + margins2.top, margins.end + margins2.end, margins.bottom + margins2.bottom);
                                                                layoutParams3.setMarginsRelative(margins4.start, margins4.top, margins4.end, margins4.bottom);
                                                            }
                                                        });
                                                        int i3 = viewEntry4.mLayoutParams.mConstraint;
                                                        if (i3 != -1) {
                                                            if (i22 == 1 || i22 == 2) {
                                                                layoutParams = layoutParams2;
                                                                layoutParams.matchConstraintMaxHeight = i3;
                                                            } else if (i22 == 4 || i22 == 8) {
                                                                layoutParams = layoutParams2;
                                                                layoutParams.matchConstraintMaxWidth = i3;
                                                            }
                                                            viewEntry4.mView.setLayoutParams(layoutParams);
                                                            view2 = viewEntry4.mView;
                                                            z = false;
                                                        }
                                                        layoutParams = layoutParams2;
                                                        viewEntry4.mView.setLayoutParams(layoutParams);
                                                        view2 = viewEntry4.mView;
                                                        z = false;
                                                    }
                                                }
                                            }
                                            ((ViewGroup) viewEntry.mView.getParent()).removeView(viewEntry.mView);
                                            TouchInsetManager.TouchInsetSession touchInsetSession = viewEntry.mTouchInsetSession;
                                            View view3 = viewEntry.mView;
                                            touchInsetSession.getClass();
                                            touchInsetSession.mExecutor.execute(new TouchInsetManager$$ExternalSyntheticLambda2(touchInsetSession, view3, 2));
                                        }
                                    }
                                }
                                complicationHostViewController2.mComplications.remove(complicationId);
                                return;
                            default:
                                complicationHostViewController.getClass();
                                ComplicationId complicationId2 = ((ComplicationViewModel) obj2).mId;
                                throw null;
                        }
                    }
                });
            }
        });
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
    }
}
