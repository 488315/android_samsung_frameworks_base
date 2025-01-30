package androidx.fragment.app;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.os.CancellationSignal;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DefaultSpecialEffectsController;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class FragmentTransitionImpl {
    public static void bfsAddViewChildren(View view, List list) {
        boolean z;
        boolean z2;
        int size = list.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                z = false;
                break;
            } else {
                if (list.get(i) == view) {
                    z = true;
                    break;
                }
                i++;
            }
        }
        if (z) {
            return;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api21Impl.getTransitionName(view) != null) {
            list.add(view);
        }
        for (int i2 = size; i2 < list.size(); i2++) {
            View view2 = (View) list.get(i2);
            if (view2 instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view2;
                int childCount = viewGroup.getChildCount();
                for (int i3 = 0; i3 < childCount; i3++) {
                    View childAt = viewGroup.getChildAt(i3);
                    int i4 = 0;
                    while (true) {
                        if (i4 >= size) {
                            z2 = false;
                            break;
                        } else {
                            if (list.get(i4) == childAt) {
                                z2 = true;
                                break;
                            }
                            i4++;
                        }
                    }
                    if (!z2 && ViewCompat.Api21Impl.getTransitionName(childAt) != null) {
                        list.add(childAt);
                    }
                }
            }
        }
    }

    public static void getBoundsOnScreen(Rect rect, View view) {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api19Impl.isAttachedToWindow(view)) {
            RectF rectF = new RectF();
            rectF.set(0.0f, 0.0f, view.getWidth(), view.getHeight());
            view.getMatrix().mapRect(rectF);
            rectF.offset(view.getLeft(), view.getTop());
            Object parent = view.getParent();
            while (parent instanceof View) {
                View view2 = (View) parent;
                rectF.offset(-view2.getScrollX(), -view2.getScrollY());
                view2.getMatrix().mapRect(rectF);
                rectF.offset(view2.getLeft(), view2.getTop());
                parent = view2.getParent();
            }
            view.getRootView().getLocationOnScreen(new int[2]);
            rectF.offset(r1[0], r1[1]);
            rect.set(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
        }
    }

    public static boolean isNullOrEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public abstract void addTarget(View view, Object obj);

    public abstract void addTargets(Object obj, ArrayList arrayList);

    public abstract void beginDelayedTransition(ViewGroup viewGroup, Object obj);

    public abstract boolean canHandle(Object obj);

    public abstract Object cloneTransition(Object obj);

    public abstract Object mergeTransitionsInSequence(Object obj, Object obj2, Object obj3);

    public abstract Object mergeTransitionsTogether(Object obj, Object obj2);

    public abstract void scheduleHideFragmentView(Object obj, View view, ArrayList arrayList);

    public abstract void scheduleRemoveTargets(Object obj, Object obj2, ArrayList arrayList, Object obj3, ArrayList arrayList2);

    public abstract void setEpicenter(View view, Object obj);

    public abstract void setEpicenter(Object obj, Rect rect);

    public void setListenerForTransitionEnd(Object obj, CancellationSignal cancellationSignal, DefaultSpecialEffectsController.RunnableC02189 runnableC02189) {
        runnableC02189.run();
    }

    public abstract void setSharedElementTargets(Object obj, View view, ArrayList arrayList);

    public abstract void swapSharedElementTargets(Object obj, ArrayList arrayList, ArrayList arrayList2);

    public abstract Object wrapTransitionInSet(Object obj);
}
