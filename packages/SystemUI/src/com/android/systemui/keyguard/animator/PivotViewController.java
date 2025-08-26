package com.android.systemui.keyguard.animator;

import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/* loaded from: classes2.dex */
public final class PivotViewController extends ViewAnimationController {
    public int affordancePivotX;
    public int affordancePivotY;
    public final SparseArray pivot;
    public final List pivotViews;

    public PivotViewController(KeyguardTouchAnimator keyguardTouchAnimator) {
        super(keyguardTouchAnimator);
        this.pivotViews = Arrays.asList(1, 2, 3, 4, 5, 8, 12);
        this.pivot = new SparseArray();
        initPivot(1, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(((View) obj).getWidth() / 2.0f);
            }
        }, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                PivotViewController pivotViewController = PivotViewController.this;
                float y = ((View) obj).getY();
                int i = PivotViewController.this.affordancePivotY;
                if (y >= i) {
                    i = -i;
                }
                pivotViewController.getClass();
                return Float.valueOf(i);
            }
        });
        initPivot(14, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(((View) obj).getWidth() / 2.0f);
            }
        }, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                PivotViewController pivotViewController = PivotViewController.this;
                float y = ((View) obj).getY();
                int i = PivotViewController.this.affordancePivotY;
                if (y >= i) {
                    i = -i;
                }
                pivotViewController.getClass();
                return Float.valueOf(i);
            }
        });
        initPivot(8, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(((View) obj).getWidth() / 2.0f);
            }
        }, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.6
            /* JADX WARN: Removed duplicated region for block: B:7:0x002e  */
            @Override // java.util.function.Function
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object apply(Object obj) {
                int height;
                if (PivotViewController.this.keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1)) {
                    float y = PivotViewController.this.keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1).getY();
                    PivotViewController pivotViewController = PivotViewController.this;
                    int i = pivotViewController.affordancePivotY;
                    height = y < ((float) i) ? i - pivotViewController.keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1).getHeight() : -PivotViewController.this.affordancePivotY;
                }
                return Float.valueOf(height);
            }
        });
        initPivot(2, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(((View) obj).getWidth() / 2.0f);
            }
        }, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                if (PivotViewController.this.keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1)) {
                    if (PivotViewController.this.keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1).getY() < PivotViewController.this.affordancePivotY) {
                        return Float.valueOf(r1.keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1).getHeight() + r2);
                    }
                }
                return Float.valueOf(-PivotViewController.this.affordancePivotY);
            }
        });
        initPivot(4, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(PivotViewController.this.affordancePivotX);
            }
        }, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(-PivotViewController.this.affordancePivotY);
            }
        });
        initPivot(5, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(-PivotViewController.this.affordancePivotX);
            }
        }, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(-PivotViewController.this.affordancePivotY);
            }
        });
        initPivot(3, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(PivotViewController.this.affordancePivotX - ((View) obj).getX());
            }
        }, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i;
                float height;
                int height2;
                View view = (View) obj;
                PivotViewController pivotViewController = PivotViewController.this;
                KeyguardTouchAnimator keyguardTouchAnimator2 = pivotViewController.keyguardTouchAnimator;
                if (keyguardTouchAnimator2.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1) && keyguardTouchAnimator2.dymLockInjector.mLockStarEnabled) {
                    float y = view.getY();
                    i = pivotViewController.affordancePivotY;
                    if (y < i) {
                        height2 = i - keyguardTouchAnimator2.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1).getHeight();
                    }
                    height = height2;
                    return Float.valueOf(height);
                }
                if (keyguardTouchAnimator2.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1)) {
                    float y2 = keyguardTouchAnimator2.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1).getY();
                    int i2 = pivotViewController.affordancePivotY;
                    if (y2 < i2) {
                        height = i2 - keyguardTouchAnimator2.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1).getHeight();
                        return Float.valueOf(height);
                    }
                }
                i = pivotViewController.affordancePivotY;
                height2 = -i;
                height = height2;
                return Float.valueOf(height);
            }
        });
        initPivot(6, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.15
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(((View) obj).getWidth() / 2.0f);
            }
        }, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.16
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                View view = (View) obj;
                PivotViewController pivotViewController = PivotViewController.this;
                float y = view.getY();
                int i = PivotViewController.this.affordancePivotY;
                int height = y < ((float) i) ? i - view.getHeight() : -i;
                pivotViewController.getClass();
                return Float.valueOf(height);
            }
        });
        initPivot(12, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.17
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(((View) obj).getWidth() / 2.0f);
            }
        }, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.18
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                View view = (View) obj;
                PivotViewController pivotViewController = PivotViewController.this;
                float y = view.getY();
                int i = PivotViewController.this.affordancePivotY;
                int height = y < ((float) i) ? i - view.getHeight() : -i;
                pivotViewController.getClass();
                return Float.valueOf(height);
            }
        });
    }

    public final void initPivot(int i, Function function, Function function2) {
        this.pivot.put(i, new Pair(function, function2));
    }

    public final void setChildViewPivot$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        KeyguardTouchAnimator keyguardTouchAnimator;
        View view$frameworks__base__packages__SystemUI__android_common__SystemUI_core;
        Pair pair;
        List list = this.pivotViews;
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (true) {
            boolean zHasNext = it.hasNext();
            keyguardTouchAnimator = this.keyguardTouchAnimator;
            if (!zHasNext) {
                break;
            }
            Object next = it.next();
            if (!keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) next).intValue())) {
                arrayList.add(next);
            }
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Log.d("KeyguardTouchAnimator", ((Number) obj).intValue() + " is null");
        }
        Iterator it2 = this.pivotViews.iterator();
        while (it2.hasNext()) {
            int iIntValue = ((Number) it2.next()).intValue();
            if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(iIntValue) && (view$frameworks__base__packages__SystemUI__android_common__SystemUI_core = keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(iIntValue)) != null && view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.getVisibility() == 0 && (pair = (Pair) this.pivot.get(iIntValue)) != null) {
                view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setPivotX(((Number) ((Function) pair.first).apply(view$frameworks__base__packages__SystemUI__android_common__SystemUI_core)).floatValue());
                view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setPivotY(((Number) ((Function) pair.second).apply(view$frameworks__base__packages__SystemUI__android_common__SystemUI_core)).floatValue());
            }
        }
    }
}
