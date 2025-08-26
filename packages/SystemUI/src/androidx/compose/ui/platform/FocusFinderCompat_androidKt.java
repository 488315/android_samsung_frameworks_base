package androidx.compose.ui.platform;

import android.view.View;
import android.view.ViewGroup;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class FocusFinderCompat_androidKt {
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0030, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0059, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final View access$findUserSetNextFocus(final View view, final View view2, int i) {
        View viewFindViewByPredicateTraversal;
        int nextFocusForwardId;
        View viewFindViewByPredicateTraversal2;
        if (i != 1) {
            if (i == 2 && (nextFocusForwardId = view.getNextFocusForwardId()) != -1) {
                FocusFinderCompat_androidKt$findViewInsideOutShouldExist$1 focusFinderCompat_androidKt$findViewInsideOutShouldExist$1 = new FocusFinderCompat_androidKt$findViewInsideOutShouldExist$1(nextFocusForwardId);
                View view3 = null;
                while (true) {
                    viewFindViewByPredicateTraversal2 = findViewByPredicateTraversal(view, focusFinderCompat_androidKt$findViewInsideOutShouldExist$1, view3);
                    if (viewFindViewByPredicateTraversal2 != null || view == view2) {
                        break;
                    }
                    Object parent = view.getParent();
                    if (parent == null || !(parent instanceof View)) {
                        break;
                    }
                    View view4 = (View) parent;
                    view3 = view;
                    view = view4;
                }
                return viewFindViewByPredicateTraversal2;
            }
        } else if (view.getId() != -1) {
            Function1 function1 = new Function1() { // from class: androidx.compose.ui.platform.FocusFinderCompat_androidKt$findUserSetNextFocus$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Code restructure failed: missing block: B:13:0x0029, code lost:
                
                    r1 = r3;
                 */
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object mo781invoke(Object obj) {
                    View view5 = (View) obj;
                    View view6 = view2;
                    FocusFinderCompat_androidKt$findViewInsideOutShouldExist$1 focusFinderCompat_androidKt$findViewInsideOutShouldExist$12 = new FocusFinderCompat_androidKt$findViewInsideOutShouldExist$1(view5.getNextFocusForwardId());
                    View view7 = null;
                    View view8 = null;
                    while (true) {
                        View viewFindViewByPredicateTraversal3 = FocusFinderCompat_androidKt.findViewByPredicateTraversal(view5, focusFinderCompat_androidKt$findViewInsideOutShouldExist$12, view8);
                        if (viewFindViewByPredicateTraversal3 != null || view5 == view6) {
                            break;
                        }
                        Object parent2 = view5.getParent();
                        if (parent2 == null || !(parent2 instanceof View)) {
                            break;
                        }
                        View view9 = (View) parent2;
                        view8 = view5;
                        view5 = view9;
                    }
                    return Boolean.valueOf(view7 == view);
                }
            };
            View view5 = null;
            while (true) {
                viewFindViewByPredicateTraversal = findViewByPredicateTraversal(view, function1, view5);
                if (viewFindViewByPredicateTraversal != null || view == view2) {
                    break;
                }
                Object parent2 = view.getParent();
                if (parent2 == null || !(parent2 instanceof View)) {
                    break;
                }
                View view6 = (View) parent2;
                view5 = view;
                view = view6;
            }
            return viewFindViewByPredicateTraversal;
        }
        return null;
    }

    public static final View findViewByPredicateTraversal(View view, Function1 function1, View view2) {
        View viewFindViewByPredicateTraversal;
        if (((Boolean) function1.mo781invoke(view)).booleanValue()) {
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt != view2 && (viewFindViewByPredicateTraversal = findViewByPredicateTraversal(childAt, function1, view2)) != null) {
                return viewFindViewByPredicateTraversal;
            }
        }
        return null;
    }
}
