package androidx.compose.ui.platform;

import android.view.View;
import android.view.ViewGroup;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class FocusFinderCompat_androidKt {
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0030, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x005a, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final android.view.View access$findUserSetNextFocus(final android.view.View r4, final android.view.View r5, int r6) {
        /*
            r0 = 0
            r1 = 1
            r2 = -1
            if (r6 == r1) goto L32
            r1 = 2
            if (r6 == r1) goto L9
            goto L38
        L9:
            int r6 = r4.getNextFocusForwardId()
            if (r6 != r2) goto L10
            goto L38
        L10:
            androidx.compose.ui.platform.FocusFinderCompat_androidKt$findViewInsideOutShouldExist$1 r1 = new androidx.compose.ui.platform.FocusFinderCompat_androidKt$findViewInsideOutShouldExist$1
            r1.<init>(r6)
            r6 = r0
        L16:
            android.view.View r6 = findViewByPredicateTraversal(r4, r1, r6)
            if (r6 != 0) goto L31
            if (r4 != r5) goto L1f
            goto L31
        L1f:
            android.view.ViewParent r6 = r4.getParent()
            if (r6 == 0) goto L30
            boolean r2 = r6 instanceof android.view.View
            if (r2 != 0) goto L2a
            goto L30
        L2a:
            android.view.View r6 = (android.view.View) r6
            r3 = r6
            r6 = r4
            r4 = r3
            goto L16
        L30:
            return r0
        L31:
            return r6
        L32:
            int r6 = r4.getId()
            if (r6 != r2) goto L39
        L38:
            return r0
        L39:
            androidx.compose.ui.platform.FocusFinderCompat_androidKt$findUserSetNextFocus$1 r6 = new androidx.compose.ui.platform.FocusFinderCompat_androidKt$findUserSetNextFocus$1
            r6.<init>()
            r1 = r0
        L3f:
            android.view.View r1 = findViewByPredicateTraversal(r4, r6, r1)
            if (r1 != 0) goto L5a
            if (r4 != r5) goto L48
            goto L5a
        L48:
            android.view.ViewParent r1 = r4.getParent()
            if (r1 == 0) goto L59
            boolean r2 = r1 instanceof android.view.View
            if (r2 != 0) goto L53
            goto L59
        L53:
            android.view.View r1 = (android.view.View) r1
            r3 = r1
            r1 = r4
            r4 = r3
            goto L3f
        L59:
            return r0
        L5a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.FocusFinderCompat_androidKt.access$findUserSetNextFocus(android.view.View, android.view.View, int):android.view.View");
    }

    public static final View findViewByPredicateTraversal(View view, Function1 function1, View view2) {
        View findViewByPredicateTraversal;
        if (((Boolean) function1.mo779invoke(view)).booleanValue()) {
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt != view2 && (findViewByPredicateTraversal = findViewByPredicateTraversal(childAt, function1, view2)) != null) {
                return findViewByPredicateTraversal;
            }
        }
        return null;
    }
}
