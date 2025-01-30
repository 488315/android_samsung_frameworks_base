package com.android.systemui.keyguard.animator;

import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import java.util.List;
import java.util.function.Function;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PivotViewController extends ViewAnimationController {
    public int affordancePivotX;
    public int affordancePivotY;
    public final SparseArray pivot;
    public final List pivotViews;

    public PivotViewController(KeyguardTouchAnimator keyguardTouchAnimator) {
        super(keyguardTouchAnimator);
        this.pivotViews = CollectionsKt__CollectionsKt.listOf(1, 2, 3, 4, 5, 8);
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
        initPivot(8, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(((View) obj).getWidth() / 2.0f);
            }
        }, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i;
                if (PivotViewController.this.hasView(1)) {
                    float y = PivotViewController.this.getView(1).getY();
                    PivotViewController pivotViewController = PivotViewController.this;
                    int i2 = pivotViewController.affordancePivotY;
                    if (y < i2) {
                        i = i2 - pivotViewController.getView(1).getHeight();
                        return Float.valueOf(i);
                    }
                }
                i = -PivotViewController.this.affordancePivotY;
                return Float.valueOf(i);
            }
        });
        initPivot(2, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(((View) obj).getWidth() / 2.0f);
            }
        }, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i;
                if (PivotViewController.this.hasView(1)) {
                    float y = PivotViewController.this.getView(1).getY();
                    PivotViewController pivotViewController = PivotViewController.this;
                    int i2 = pivotViewController.affordancePivotY;
                    if (y < i2) {
                        i = pivotViewController.getView(1).getHeight() + i2;
                        return Float.valueOf(i);
                    }
                }
                i = -PivotViewController.this.affordancePivotY;
                return Float.valueOf(i);
            }
        });
        initPivot(4, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(PivotViewController.this.affordancePivotX);
            }
        }, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(-PivotViewController.this.affordancePivotY);
            }
        });
        initPivot(5, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(-PivotViewController.this.affordancePivotX);
            }
        }, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(-PivotViewController.this.affordancePivotY);
            }
        });
        initPivot(3, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(PivotViewController.this.affordancePivotX - ((View) obj).getX());
            }
        }, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i;
                int i2;
                int height;
                View view = (View) obj;
                PivotViewController pivotViewController = PivotViewController.this;
                if (pivotViewController.hasView(1) && pivotViewController.keyguardTouchAnimator.dymLockInjector.mLockStarEnabled) {
                    float y = view.getY();
                    i2 = pivotViewController.affordancePivotY;
                    if (y < i2) {
                        height = pivotViewController.getView(1).getHeight();
                        i = i2 - height;
                    } else {
                        i = -i2;
                    }
                } else {
                    if (pivotViewController.hasView(1)) {
                        float y2 = pivotViewController.getView(1).getY();
                        i2 = pivotViewController.affordancePivotY;
                        if (y2 < i2) {
                            height = pivotViewController.getView(1).getHeight();
                            i = i2 - height;
                        }
                    }
                    i = -pivotViewController.affordancePivotY;
                }
                return Float.valueOf(i);
            }
        });
        initPivot(6, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(((View) obj).getWidth() / 2.0f);
            }
        }, new Function() { // from class: com.android.systemui.keyguard.animator.PivotViewController.14
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
}
