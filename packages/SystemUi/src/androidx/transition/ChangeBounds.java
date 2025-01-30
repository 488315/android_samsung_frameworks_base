package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeConverter;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ChangeBounds extends Transition {
    public static final C05204 BOTTOM_RIGHT_ONLY_PROPERTY;
    public static final C05193 BOTTOM_RIGHT_PROPERTY;
    public static final C05215 TOP_LEFT_ONLY_PROPERTY;
    public static final C05182 TOP_LEFT_PROPERTY;
    public boolean mResizeClip;
    public final int[] mTempLocation;
    public static final String[] sTransitionProperties = {"android:changeBounds:bounds", "android:changeBounds:clip", "android:changeBounds:parent", "android:changeBounds:windowX", "android:changeBounds:windowY"};
    public static final C05171 DRAWABLE_ORIGIN_PROPERTY = new Property(PointF.class, "boundsOrigin") { // from class: androidx.transition.ChangeBounds.1
        public final Rect mBounds = new Rect();

        @Override // android.util.Property
        public final Object get(Object obj) {
            ((Drawable) obj).copyBounds(this.mBounds);
            Rect rect = this.mBounds;
            return new PointF(rect.left, rect.top);
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            Drawable drawable = (Drawable) obj;
            PointF pointF = (PointF) obj2;
            drawable.copyBounds(this.mBounds);
            this.mBounds.offsetTo(Math.round(pointF.x), Math.round(pointF.y));
            drawable.setBounds(this.mBounds);
        }
    };
    public static final C05226 POSITION_PROPERTY = new Property(PointF.class, "position") { // from class: androidx.transition.ChangeBounds.6
        @Override // android.util.Property
        public final /* bridge */ /* synthetic */ Object get(Object obj) {
            return null;
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            View view = (View) obj;
            PointF pointF = (PointF) obj2;
            int round = Math.round(pointF.x);
            int round2 = Math.round(pointF.y);
            ViewUtils.setLeftTopRightBottom(view, round, round2, view.getWidth() + round, view.getHeight() + round2);
        }
    };
    public static final RectEvaluator sRectEvaluator = new RectEvaluator();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ViewBounds {
        public int mBottom;
        public int mBottomRightCalls;
        public int mLeft;
        public int mRight;
        public int mTop;
        public int mTopLeftCalls;
        public final View mView;

        public ViewBounds(View view) {
            this.mView = view;
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.transition.ChangeBounds$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.transition.ChangeBounds$2] */
    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.transition.ChangeBounds$3] */
    /* JADX WARN: Type inference failed for: r0v5, types: [androidx.transition.ChangeBounds$4] */
    /* JADX WARN: Type inference failed for: r0v6, types: [androidx.transition.ChangeBounds$5] */
    /* JADX WARN: Type inference failed for: r0v7, types: [androidx.transition.ChangeBounds$6] */
    static {
        String str = "topLeft";
        TOP_LEFT_PROPERTY = new Property(PointF.class, str) { // from class: androidx.transition.ChangeBounds.2
            @Override // android.util.Property
            public final /* bridge */ /* synthetic */ Object get(Object obj) {
                return null;
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                ViewBounds viewBounds = (ViewBounds) obj;
                PointF pointF = (PointF) obj2;
                viewBounds.getClass();
                viewBounds.mLeft = Math.round(pointF.x);
                int round = Math.round(pointF.y);
                viewBounds.mTop = round;
                int i = viewBounds.mTopLeftCalls + 1;
                viewBounds.mTopLeftCalls = i;
                if (i == viewBounds.mBottomRightCalls) {
                    ViewUtils.setLeftTopRightBottom(viewBounds.mView, viewBounds.mLeft, round, viewBounds.mRight, viewBounds.mBottom);
                    viewBounds.mTopLeftCalls = 0;
                    viewBounds.mBottomRightCalls = 0;
                }
            }
        };
        String str2 = "bottomRight";
        BOTTOM_RIGHT_PROPERTY = new Property(PointF.class, str2) { // from class: androidx.transition.ChangeBounds.3
            @Override // android.util.Property
            public final /* bridge */ /* synthetic */ Object get(Object obj) {
                return null;
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                ViewBounds viewBounds = (ViewBounds) obj;
                PointF pointF = (PointF) obj2;
                viewBounds.getClass();
                viewBounds.mRight = Math.round(pointF.x);
                int round = Math.round(pointF.y);
                viewBounds.mBottom = round;
                int i = viewBounds.mBottomRightCalls + 1;
                viewBounds.mBottomRightCalls = i;
                if (viewBounds.mTopLeftCalls == i) {
                    ViewUtils.setLeftTopRightBottom(viewBounds.mView, viewBounds.mLeft, viewBounds.mTop, viewBounds.mRight, round);
                    viewBounds.mTopLeftCalls = 0;
                    viewBounds.mBottomRightCalls = 0;
                }
            }
        };
        BOTTOM_RIGHT_ONLY_PROPERTY = new Property(PointF.class, str2) { // from class: androidx.transition.ChangeBounds.4
            @Override // android.util.Property
            public final /* bridge */ /* synthetic */ Object get(Object obj) {
                return null;
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                View view = (View) obj;
                PointF pointF = (PointF) obj2;
                ViewUtils.setLeftTopRightBottom(view, view.getLeft(), view.getTop(), Math.round(pointF.x), Math.round(pointF.y));
            }
        };
        TOP_LEFT_ONLY_PROPERTY = new Property(PointF.class, str) { // from class: androidx.transition.ChangeBounds.5
            @Override // android.util.Property
            public final /* bridge */ /* synthetic */ Object get(Object obj) {
                return null;
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                View view = (View) obj;
                PointF pointF = (PointF) obj2;
                ViewUtils.setLeftTopRightBottom(view, Math.round(pointF.x), Math.round(pointF.y), view.getRight(), view.getBottom());
            }
        };
    }

    public ChangeBounds() {
        this.mTempLocation = new int[2];
        this.mResizeClip = false;
    }

    @Override // androidx.transition.Transition
    public final void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // androidx.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public final void captureValues(TransitionValues transitionValues) {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        View view = transitionValues.view;
        if (!ViewCompat.Api19Impl.isLaidOut(view) && view.getWidth() == 0 && view.getHeight() == 0) {
            return;
        }
        HashMap hashMap = (HashMap) transitionValues.values;
        hashMap.put("android:changeBounds:bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        hashMap.put("android:changeBounds:parent", view.getParent());
        if (this.mResizeClip) {
            hashMap.put("android:changeBounds:clip", ViewCompat.Api18Impl.getClipBounds(view));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.transition.Transition
    public final Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        int i;
        int i2;
        Rect rect;
        final View view;
        boolean z;
        ObjectAnimator objectAnimator;
        ObjectAnimator objectAnimator2;
        ObjectAnimator ofObject;
        ObjectAnimator objectAnimator3;
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        Map map = transitionValues.values;
        Map map2 = transitionValues2.values;
        HashMap hashMap = (HashMap) map;
        ViewGroup viewGroup2 = (ViewGroup) hashMap.get("android:changeBounds:parent");
        HashMap hashMap2 = (HashMap) map2;
        ViewGroup viewGroup3 = (ViewGroup) hashMap2.get("android:changeBounds:parent");
        if (viewGroup2 == null || viewGroup3 == null) {
            return null;
        }
        Rect rect2 = (Rect) hashMap.get("android:changeBounds:bounds");
        Rect rect3 = (Rect) hashMap2.get("android:changeBounds:bounds");
        int i3 = rect2.left;
        final int i4 = rect3.left;
        int i5 = rect2.top;
        final int i6 = rect3.top;
        int i7 = rect2.right;
        final int i8 = rect3.right;
        int i9 = rect2.bottom;
        final int i10 = rect3.bottom;
        int i11 = i7 - i3;
        int i12 = i9 - i5;
        int i13 = i8 - i4;
        int i14 = i10 - i6;
        Rect rect4 = (Rect) hashMap.get("android:changeBounds:clip");
        final Rect rect5 = (Rect) hashMap2.get("android:changeBounds:clip");
        if ((i11 == 0 || i12 == 0) && (i13 == 0 || i14 == 0)) {
            i = 0;
        } else {
            i = (i3 == i4 && i5 == i6) ? 0 : 1;
            if (i7 != i8 || i9 != i10) {
                i++;
            }
        }
        if ((rect4 != null && !rect4.equals(rect5)) || (rect4 == null && rect5 != null)) {
            i++;
        }
        int i15 = i;
        if (i15 <= 0) {
            return null;
        }
        boolean z2 = this.mResizeClip;
        View view2 = transitionValues2.view;
        if (z2) {
            ViewUtils.setLeftTopRightBottom(view2, i3, i5, Math.max(i11, i13) + i3, Math.max(i12, i14) + i5);
            ObjectAnimator ofObject2 = (i3 == i4 && i5 == i6) ? null : ObjectAnimator.ofObject(view2, POSITION_PROPERTY, (TypeConverter) null, this.mPathMotion.getPath(i3, i5, i4, i6));
            if (rect4 == null) {
                i2 = 0;
                rect = new Rect(0, 0, i11, i12);
            } else {
                i2 = 0;
                rect = rect4;
            }
            Rect rect6 = rect5 == null ? new Rect(i2, i2, i13, i14) : rect5;
            if (rect.equals(rect6)) {
                view = view2;
                z = true;
                objectAnimator = null;
            } else {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api18Impl.setClipBounds(view2, rect);
                ObjectAnimator ofObject3 = ObjectAnimator.ofObject(view2, "clipBounds", sRectEvaluator, rect, rect6);
                view = view2;
                z = true;
                ofObject3.addListener(new AnimatorListenerAdapter(this) { // from class: androidx.transition.ChangeBounds.8
                    public boolean mIsCanceled;

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        this.mIsCanceled = true;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        if (this.mIsCanceled) {
                            return;
                        }
                        View view3 = view;
                        Rect rect7 = rect5;
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        ViewCompat.Api18Impl.setClipBounds(view3, rect7);
                        ViewUtils.setLeftTopRightBottom(view, i4, i6, i8, i10);
                    }
                });
                objectAnimator = ofObject3;
            }
            if (ofObject2 == null) {
                objectAnimator2 = objectAnimator;
            } else if (objectAnimator == null) {
                objectAnimator2 = ofObject2;
            } else {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(ofObject2, objectAnimator);
                objectAnimator2 = animatorSet;
            }
        } else {
            ViewUtils.setLeftTopRightBottom(view2, i3, i5, i7, i9);
            if (i15 != 2) {
                ofObject = (i3 == i4 && i5 == i6) ? ObjectAnimator.ofObject(view2, BOTTOM_RIGHT_ONLY_PROPERTY, (TypeConverter) null, this.mPathMotion.getPath(i7, i9, i8, i10)) : ObjectAnimator.ofObject(view2, TOP_LEFT_ONLY_PROPERTY, (TypeConverter) null, this.mPathMotion.getPath(i3, i5, i4, i6));
            } else if (i11 == i13 && i12 == i14) {
                ofObject = ObjectAnimator.ofObject(view2, POSITION_PROPERTY, (TypeConverter) null, this.mPathMotion.getPath(i3, i5, i4, i6));
            } else {
                ViewBounds viewBounds = new ViewBounds(view2);
                ObjectAnimator ofObject4 = ObjectAnimator.ofObject(viewBounds, TOP_LEFT_PROPERTY, (TypeConverter) null, this.mPathMotion.getPath(i3, i5, i4, i6));
                ObjectAnimator ofObject5 = ObjectAnimator.ofObject(viewBounds, BOTTOM_RIGHT_PROPERTY, (TypeConverter) null, this.mPathMotion.getPath(i7, i9, i8, i10));
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(ofObject4, ofObject5);
                animatorSet2.addListener(new AnimatorListenerAdapter(this, viewBounds) { // from class: androidx.transition.ChangeBounds.7
                    private ViewBounds mViewBounds;

                    {
                        this.mViewBounds = viewBounds;
                    }
                });
                objectAnimator3 = animatorSet2;
                view = view2;
                z = true;
                objectAnimator2 = objectAnimator3;
            }
            objectAnimator3 = ofObject;
            view = view2;
            z = true;
            objectAnimator2 = objectAnimator3;
        }
        if (view.getParent() instanceof ViewGroup) {
            final ViewGroup viewGroup4 = (ViewGroup) view.getParent();
            viewGroup4.suppressLayout(z);
            addListener(new TransitionListenerAdapter(this) { // from class: androidx.transition.ChangeBounds.9
                public boolean mCanceled = false;

                @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                public final void onTransitionCancel() {
                    viewGroup4.suppressLayout(false);
                    this.mCanceled = true;
                }

                @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                public final void onTransitionEnd(Transition transition) {
                    if (!this.mCanceled) {
                        viewGroup4.suppressLayout(false);
                    }
                    transition.removeListener(this);
                }

                @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                public final void onTransitionPause() {
                    viewGroup4.suppressLayout(false);
                }

                @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                public final void onTransitionResume() {
                    viewGroup4.suppressLayout(true);
                }
            });
        }
        return objectAnimator2;
    }

    @Override // androidx.transition.Transition
    public final String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public ChangeBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTempLocation = new int[2];
        this.mResizeClip = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.CHANGE_BOUNDS);
        boolean z = TypedArrayUtils.hasAttribute((XmlResourceParser) attributeSet, "resizeClip") ? obtainStyledAttributes.getBoolean(0, false) : false;
        obtainStyledAttributes.recycle();
        this.mResizeClip = z;
    }
}
