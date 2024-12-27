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
import com.android.systemui.media.dream.MediaDreamComplication;
import com.android.systemui.touch.TouchInsetManager;
import com.android.systemui.touch.TouchInsetManager$$ExternalSyntheticLambda0;
import com.android.systemui.util.SettingsHelper;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        this.mIsAnimationEnabled = secureSettings.getFloatForUser(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f, -2) != 0.0f;
    }

    public final View getView() {
        return this.mView;
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
        super.onInit();
        this.mComplicationCollectionViewModel.mComplications.observe(this.mLifecycleOwner, new Observer() { // from class: com.android.systemui.complication.ComplicationHostViewController$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Collection collection = (Collection) obj;
                final ComplicationHostViewController complicationHostViewController = ComplicationHostViewController.this;
                complicationHostViewController.getClass();
                if (ComplicationHostViewController.DEBUG) {
                    Log.d("ComplicationHostVwCtrl", "updateComplications called. Callers = " + Debug.getCallers(25));
                    Log.d("ComplicationHostVwCtrl", "    mComplications = " + complicationHostViewController.mComplications.toString());
                    Log.d("ComplicationHostVwCtrl", "    complications = " + collection.toString());
                }
                final Collection collection2 = (Collection) collection.stream().map(new ComplicationHostViewController$$ExternalSyntheticLambda1()).collect(Collectors.toSet());
                final int i = 0;
                final int i2 = 0;
                ((Collection) complicationHostViewController.mComplications.keySet().stream().filter(new Predicate() { // from class: com.android.systemui.complication.ComplicationHostViewController$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        int i3 = i;
                        Object obj3 = collection2;
                        switch (i3) {
                            case 0:
                                return !((Collection) obj3).contains((ComplicationId) obj2);
                            default:
                                return !((ComplicationHostViewController) obj3).mComplications.containsKey(((ComplicationViewModel) obj2).mId);
                        }
                    }
                }).collect(Collectors.toSet())).forEach(new Consumer() { // from class: com.android.systemui.complication.ComplicationHostViewController$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        int i3 = i2;
                        ComplicationHostViewController complicationHostViewController2 = complicationHostViewController;
                        switch (i3) {
                            case 0:
                                ComplicationId complicationId = (ComplicationId) obj2;
                                ComplicationLayoutEngine.ViewEntry viewEntry = (ComplicationLayoutEngine.ViewEntry) complicationHostViewController2.mLayoutEngine.mEntries.remove(complicationId);
                                if (viewEntry == null) {
                                    Log.e("ComplicationLayoutEng", "could not find id:" + complicationId);
                                } else {
                                    ComplicationLayoutEngine.DirectionGroup directionGroup = (ComplicationLayoutEngine.DirectionGroup) viewEntry.mParent;
                                    directionGroup.mViews.remove(viewEntry);
                                    ComplicationLayoutEngine.PositionGroup positionGroup = (ComplicationLayoutEngine.PositionGroup) directionGroup.mParent;
                                    Iterator it = positionGroup.mDirectionGroups.values().iterator();
                                    ComplicationLayoutEngine.ViewEntry viewEntry2 = null;
                                    while (true) {
                                        boolean z = false;
                                        if (it.hasNext()) {
                                            ComplicationLayoutEngine.DirectionGroup directionGroup2 = (ComplicationLayoutEngine.DirectionGroup) it.next();
                                            ComplicationLayoutEngine.ViewEntry viewEntry3 = directionGroup2.mViews.isEmpty() ? null : (ComplicationLayoutEngine.ViewEntry) directionGroup2.mViews.get(0);
                                            if (viewEntry2 == null || (viewEntry3 != null && viewEntry3.compareTo(viewEntry2) > 0)) {
                                                viewEntry2 = viewEntry3;
                                            }
                                        } else {
                                            if (viewEntry2 != null) {
                                                Iterator it2 = positionGroup.mDirectionGroups.values().iterator();
                                                while (it2.hasNext()) {
                                                    ComplicationLayoutEngine.DirectionGroup directionGroup3 = (ComplicationLayoutEngine.DirectionGroup) it2.next();
                                                    View view = viewEntry2.mView;
                                                    Iterator it3 = directionGroup3.mViews.iterator();
                                                    final View view2 = view;
                                                    while (it3.hasNext()) {
                                                        final ComplicationLayoutEngine.ViewEntry viewEntry4 = (ComplicationLayoutEngine.ViewEntry) it3.next();
                                                        viewEntry4.getClass();
                                                        ComplicationLayoutParams complicationLayoutParams = viewEntry4.mLayoutParams;
                                                        final Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(((ViewGroup.LayoutParams) complicationLayoutParams).width, ((ViewGroup.LayoutParams) complicationLayoutParams).height);
                                                        ComplicationLayoutParams complicationLayoutParams2 = viewEntry4.mLayoutParams;
                                                        final int i4 = complicationLayoutParams2.mDirection;
                                                        final boolean z2 = complicationLayoutParams2.mSnapToGuide;
                                                        Iterator it4 = it2;
                                                        final boolean z3 = view2 == viewEntry4.mView ? true : z;
                                                        Iterator it5 = it3;
                                                        ComplicationLayoutParams.iteratePositions(complicationLayoutParams2.mPosition, new Consumer() { // from class: com.android.systemui.complication.ComplicationLayoutEngine$ViewEntry$$ExternalSyntheticLambda0
                                                            @Override // java.util.function.Consumer
                                                            public final void accept(Object obj3) {
                                                                ComplicationLayoutEngine.Margins margins;
                                                                ComplicationLayoutEngine.ViewEntry viewEntry5 = ComplicationLayoutEngine.ViewEntry.this;
                                                                boolean z4 = z3;
                                                                int i5 = i4;
                                                                ConstraintLayout.LayoutParams layoutParams2 = layoutParams;
                                                                View view3 = view2;
                                                                boolean z5 = z2;
                                                                viewEntry5.getClass();
                                                                int intValue = ((Integer) obj3).intValue();
                                                                if (intValue == 1) {
                                                                    if (z4 || i5 != 2) {
                                                                        layoutParams2.topToTop = 0;
                                                                    } else {
                                                                        layoutParams2.topToBottom = view3.getId();
                                                                    }
                                                                    if (z5 && (i5 == 8 || i5 == 4)) {
                                                                        layoutParams2.endToStart = R.id.complication_top_guide;
                                                                    }
                                                                } else if (intValue == 2) {
                                                                    if (z4 || i5 != 1) {
                                                                        layoutParams2.bottomToBottom = 0;
                                                                    } else {
                                                                        layoutParams2.bottomToTop = view3.getId();
                                                                    }
                                                                    if (z5 && (i5 == 8 || i5 == 4)) {
                                                                        layoutParams2.topToBottom = R.id.complication_bottom_guide;
                                                                    }
                                                                } else if (intValue == 4) {
                                                                    if (z4 || i5 != 8) {
                                                                        layoutParams2.startToStart = 0;
                                                                    } else {
                                                                        layoutParams2.startToEnd = view3.getId();
                                                                    }
                                                                    if (z5 && (i5 == 2 || i5 == 1)) {
                                                                        layoutParams2.endToStart = R.id.complication_start_guide;
                                                                    }
                                                                } else if (intValue == 8) {
                                                                    if (z4 || i5 != 4) {
                                                                        layoutParams2.endToEnd = 0;
                                                                    } else {
                                                                        layoutParams2.endToStart = view3.getId();
                                                                    }
                                                                    if (z5 && (i5 == 1 || i5 == 2)) {
                                                                        layoutParams2.startToEnd = R.id.complication_end_guide;
                                                                    }
                                                                }
                                                                ComplicationLayoutEngine.DirectionGroup directionGroup4 = (ComplicationLayoutEngine.DirectionGroup) viewEntry5.mParent;
                                                                directionGroup4.getClass();
                                                                ComplicationLayoutParams complicationLayoutParams3 = viewEntry5.mLayoutParams;
                                                                ComplicationLayoutEngine.PositionGroup positionGroup2 = (ComplicationLayoutEngine.PositionGroup) directionGroup4.mParent;
                                                                int i6 = positionGroup2.mDefaultDirectionalSpacing;
                                                                int i7 = complicationLayoutParams3.mDirectionalSpacing;
                                                                if (i7 != -1) {
                                                                    i6 = i7;
                                                                }
                                                                ComplicationLayoutEngine.Margins margins2 = new ComplicationLayoutEngine.Margins();
                                                                if (!z4) {
                                                                    int i8 = viewEntry5.mLayoutParams.mDirection;
                                                                    if (i8 == 1) {
                                                                        margins2 = new ComplicationLayoutEngine.Margins(0, 0, 0, i6);
                                                                    } else if (i8 == 2) {
                                                                        margins2 = new ComplicationLayoutEngine.Margins(0, i6, 0, 0);
                                                                    } else if (i8 == 4) {
                                                                        margins2 = new ComplicationLayoutEngine.Margins(0, 0, i6, 0);
                                                                    } else if (i8 == 8) {
                                                                        margins2 = new ComplicationLayoutEngine.Margins(i6, 0, 0, 0);
                                                                    }
                                                                }
                                                                if (z4) {
                                                                    margins = new ComplicationLayoutEngine.Margins();
                                                                    Iterator it6 = positionGroup2.mDirectionalMargins.values().iterator();
                                                                    while (it6.hasNext()) {
                                                                        margins = ComplicationLayoutEngine.Margins.combine((ComplicationLayoutEngine.Margins) it6.next(), margins);
                                                                    }
                                                                } else {
                                                                    margins = (ComplicationLayoutEngine.Margins) positionGroup2.mDirectionalMargins.get(Integer.valueOf(viewEntry5.mLayoutParams.mDirection));
                                                                }
                                                                ComplicationLayoutEngine.Margins combine = ComplicationLayoutEngine.Margins.combine(margins, margins2);
                                                                layoutParams2.setMarginsRelative(combine.start, combine.top, combine.end, combine.bottom);
                                                            }
                                                        });
                                                        int i5 = viewEntry4.mLayoutParams.mConstraint;
                                                        if (i5 != -1) {
                                                            if (i4 == 1 || i4 == 2) {
                                                                layoutParams.matchConstraintMaxHeight = i5;
                                                            } else if (i4 == 4 || i4 == 8) {
                                                                layoutParams.matchConstraintMaxWidth = i5;
                                                            }
                                                        }
                                                        viewEntry4.mView.setLayoutParams(layoutParams);
                                                        view2 = viewEntry4.mView;
                                                        it2 = it4;
                                                        it3 = it5;
                                                        z = false;
                                                    }
                                                }
                                            }
                                            ((ViewGroup) viewEntry.mView.getParent()).removeView(viewEntry.mView);
                                            TouchInsetManager.TouchInsetSession touchInsetSession = viewEntry.mTouchInsetSession;
                                            touchInsetSession.mExecutor.execute(new TouchInsetManager$$ExternalSyntheticLambda0(touchInsetSession, viewEntry.mView, 2));
                                        }
                                    }
                                }
                                complicationHostViewController2.mComplications.remove(complicationId);
                                return;
                            default:
                                ComplicationViewModel complicationViewModel = (ComplicationViewModel) obj2;
                                complicationHostViewController2.getClass();
                                ComplicationId complicationId2 = complicationViewModel.mId;
                                ((MediaDreamComplication) complicationViewModel.mComplication).getClass();
                                throw null;
                        }
                    }
                });
                final int i3 = 1;
                ((Collection) collection.stream().filter(new Predicate() { // from class: com.android.systemui.complication.ComplicationHostViewController$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        int i32 = i3;
                        Object obj3 = complicationHostViewController;
                        switch (i32) {
                            case 0:
                                return !((Collection) obj3).contains((ComplicationId) obj2);
                            default:
                                return !((ComplicationHostViewController) obj3).mComplications.containsKey(((ComplicationViewModel) obj2).mId);
                        }
                    }
                }).collect(Collectors.toSet())).forEach(new Consumer() { // from class: com.android.systemui.complication.ComplicationHostViewController$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        int i32 = i3;
                        ComplicationHostViewController complicationHostViewController2 = complicationHostViewController;
                        switch (i32) {
                            case 0:
                                ComplicationId complicationId = (ComplicationId) obj2;
                                ComplicationLayoutEngine.ViewEntry viewEntry = (ComplicationLayoutEngine.ViewEntry) complicationHostViewController2.mLayoutEngine.mEntries.remove(complicationId);
                                if (viewEntry == null) {
                                    Log.e("ComplicationLayoutEng", "could not find id:" + complicationId);
                                } else {
                                    ComplicationLayoutEngine.DirectionGroup directionGroup = (ComplicationLayoutEngine.DirectionGroup) viewEntry.mParent;
                                    directionGroup.mViews.remove(viewEntry);
                                    ComplicationLayoutEngine.PositionGroup positionGroup = (ComplicationLayoutEngine.PositionGroup) directionGroup.mParent;
                                    Iterator it = positionGroup.mDirectionGroups.values().iterator();
                                    ComplicationLayoutEngine.ViewEntry viewEntry2 = null;
                                    while (true) {
                                        boolean z = false;
                                        if (it.hasNext()) {
                                            ComplicationLayoutEngine.DirectionGroup directionGroup2 = (ComplicationLayoutEngine.DirectionGroup) it.next();
                                            ComplicationLayoutEngine.ViewEntry viewEntry3 = directionGroup2.mViews.isEmpty() ? null : (ComplicationLayoutEngine.ViewEntry) directionGroup2.mViews.get(0);
                                            if (viewEntry2 == null || (viewEntry3 != null && viewEntry3.compareTo(viewEntry2) > 0)) {
                                                viewEntry2 = viewEntry3;
                                            }
                                        } else {
                                            if (viewEntry2 != null) {
                                                Iterator it2 = positionGroup.mDirectionGroups.values().iterator();
                                                while (it2.hasNext()) {
                                                    ComplicationLayoutEngine.DirectionGroup directionGroup3 = (ComplicationLayoutEngine.DirectionGroup) it2.next();
                                                    View view = viewEntry2.mView;
                                                    Iterator it3 = directionGroup3.mViews.iterator();
                                                    final View view2 = view;
                                                    while (it3.hasNext()) {
                                                        final ComplicationLayoutEngine.ViewEntry viewEntry4 = (ComplicationLayoutEngine.ViewEntry) it3.next();
                                                        viewEntry4.getClass();
                                                        ComplicationLayoutParams complicationLayoutParams = viewEntry4.mLayoutParams;
                                                        final Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(((ViewGroup.LayoutParams) complicationLayoutParams).width, ((ViewGroup.LayoutParams) complicationLayoutParams).height);
                                                        ComplicationLayoutParams complicationLayoutParams2 = viewEntry4.mLayoutParams;
                                                        final int i4 = complicationLayoutParams2.mDirection;
                                                        final boolean z2 = complicationLayoutParams2.mSnapToGuide;
                                                        Iterator it4 = it2;
                                                        final boolean z3 = view2 == viewEntry4.mView ? true : z;
                                                        Iterator it5 = it3;
                                                        ComplicationLayoutParams.iteratePositions(complicationLayoutParams2.mPosition, new Consumer() { // from class: com.android.systemui.complication.ComplicationLayoutEngine$ViewEntry$$ExternalSyntheticLambda0
                                                            @Override // java.util.function.Consumer
                                                            public final void accept(Object obj3) {
                                                                ComplicationLayoutEngine.Margins margins;
                                                                ComplicationLayoutEngine.ViewEntry viewEntry5 = ComplicationLayoutEngine.ViewEntry.this;
                                                                boolean z4 = z3;
                                                                int i5 = i4;
                                                                ConstraintLayout.LayoutParams layoutParams2 = layoutParams;
                                                                View view3 = view2;
                                                                boolean z5 = z2;
                                                                viewEntry5.getClass();
                                                                int intValue = ((Integer) obj3).intValue();
                                                                if (intValue == 1) {
                                                                    if (z4 || i5 != 2) {
                                                                        layoutParams2.topToTop = 0;
                                                                    } else {
                                                                        layoutParams2.topToBottom = view3.getId();
                                                                    }
                                                                    if (z5 && (i5 == 8 || i5 == 4)) {
                                                                        layoutParams2.endToStart = R.id.complication_top_guide;
                                                                    }
                                                                } else if (intValue == 2) {
                                                                    if (z4 || i5 != 1) {
                                                                        layoutParams2.bottomToBottom = 0;
                                                                    } else {
                                                                        layoutParams2.bottomToTop = view3.getId();
                                                                    }
                                                                    if (z5 && (i5 == 8 || i5 == 4)) {
                                                                        layoutParams2.topToBottom = R.id.complication_bottom_guide;
                                                                    }
                                                                } else if (intValue == 4) {
                                                                    if (z4 || i5 != 8) {
                                                                        layoutParams2.startToStart = 0;
                                                                    } else {
                                                                        layoutParams2.startToEnd = view3.getId();
                                                                    }
                                                                    if (z5 && (i5 == 2 || i5 == 1)) {
                                                                        layoutParams2.endToStart = R.id.complication_start_guide;
                                                                    }
                                                                } else if (intValue == 8) {
                                                                    if (z4 || i5 != 4) {
                                                                        layoutParams2.endToEnd = 0;
                                                                    } else {
                                                                        layoutParams2.endToStart = view3.getId();
                                                                    }
                                                                    if (z5 && (i5 == 1 || i5 == 2)) {
                                                                        layoutParams2.startToEnd = R.id.complication_end_guide;
                                                                    }
                                                                }
                                                                ComplicationLayoutEngine.DirectionGroup directionGroup4 = (ComplicationLayoutEngine.DirectionGroup) viewEntry5.mParent;
                                                                directionGroup4.getClass();
                                                                ComplicationLayoutParams complicationLayoutParams3 = viewEntry5.mLayoutParams;
                                                                ComplicationLayoutEngine.PositionGroup positionGroup2 = (ComplicationLayoutEngine.PositionGroup) directionGroup4.mParent;
                                                                int i6 = positionGroup2.mDefaultDirectionalSpacing;
                                                                int i7 = complicationLayoutParams3.mDirectionalSpacing;
                                                                if (i7 != -1) {
                                                                    i6 = i7;
                                                                }
                                                                ComplicationLayoutEngine.Margins margins2 = new ComplicationLayoutEngine.Margins();
                                                                if (!z4) {
                                                                    int i8 = viewEntry5.mLayoutParams.mDirection;
                                                                    if (i8 == 1) {
                                                                        margins2 = new ComplicationLayoutEngine.Margins(0, 0, 0, i6);
                                                                    } else if (i8 == 2) {
                                                                        margins2 = new ComplicationLayoutEngine.Margins(0, i6, 0, 0);
                                                                    } else if (i8 == 4) {
                                                                        margins2 = new ComplicationLayoutEngine.Margins(0, 0, i6, 0);
                                                                    } else if (i8 == 8) {
                                                                        margins2 = new ComplicationLayoutEngine.Margins(i6, 0, 0, 0);
                                                                    }
                                                                }
                                                                if (z4) {
                                                                    margins = new ComplicationLayoutEngine.Margins();
                                                                    Iterator it6 = positionGroup2.mDirectionalMargins.values().iterator();
                                                                    while (it6.hasNext()) {
                                                                        margins = ComplicationLayoutEngine.Margins.combine((ComplicationLayoutEngine.Margins) it6.next(), margins);
                                                                    }
                                                                } else {
                                                                    margins = (ComplicationLayoutEngine.Margins) positionGroup2.mDirectionalMargins.get(Integer.valueOf(viewEntry5.mLayoutParams.mDirection));
                                                                }
                                                                ComplicationLayoutEngine.Margins combine = ComplicationLayoutEngine.Margins.combine(margins, margins2);
                                                                layoutParams2.setMarginsRelative(combine.start, combine.top, combine.end, combine.bottom);
                                                            }
                                                        });
                                                        int i5 = viewEntry4.mLayoutParams.mConstraint;
                                                        if (i5 != -1) {
                                                            if (i4 == 1 || i4 == 2) {
                                                                layoutParams.matchConstraintMaxHeight = i5;
                                                            } else if (i4 == 4 || i4 == 8) {
                                                                layoutParams.matchConstraintMaxWidth = i5;
                                                            }
                                                        }
                                                        viewEntry4.mView.setLayoutParams(layoutParams);
                                                        view2 = viewEntry4.mView;
                                                        it2 = it4;
                                                        it3 = it5;
                                                        z = false;
                                                    }
                                                }
                                            }
                                            ((ViewGroup) viewEntry.mView.getParent()).removeView(viewEntry.mView);
                                            TouchInsetManager.TouchInsetSession touchInsetSession = viewEntry.mTouchInsetSession;
                                            touchInsetSession.mExecutor.execute(new TouchInsetManager$$ExternalSyntheticLambda0(touchInsetSession, viewEntry.mView, 2));
                                        }
                                    }
                                }
                                complicationHostViewController2.mComplications.remove(complicationId);
                                return;
                            default:
                                ComplicationViewModel complicationViewModel = (ComplicationViewModel) obj2;
                                complicationHostViewController2.getClass();
                                ComplicationId complicationId2 = complicationViewModel.mId;
                                ((MediaDreamComplication) complicationViewModel.mComplication).getClass();
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
