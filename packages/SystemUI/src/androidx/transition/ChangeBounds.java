package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.transition.Transition;
import com.android.systemui.R;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ChangeBounds extends Transition {
    public static final AnonymousClass3 BOTTOM_RIGHT_ONLY_PROPERTY;
    public static final AnonymousClass2 BOTTOM_RIGHT_PROPERTY;
    public static final AnonymousClass4 TOP_LEFT_ONLY_PROPERTY;
    public static final AnonymousClass1 TOP_LEFT_PROPERTY;
    public final boolean mResizeClip;
    public static final String[] sTransitionProperties = {"android:changeBounds:bounds", "android:changeBounds:clip", "android:changeBounds:parent", "android:changeBounds:windowX", "android:changeBounds:windowY"};
    public static final AnonymousClass5 POSITION_PROPERTY = new Property(PointF.class, SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_POSITION) { // from class: androidx.transition.ChangeBounds.5
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ClipListener extends AnimatorListenerAdapter implements Transition.TransitionListener {
        public final int mEndBottom;
        public final Rect mEndClip;
        public final boolean mEndClipIsNull;
        public final int mEndLeft;
        public final int mEndRight;
        public final int mEndTop;
        public boolean mIsCanceled;
        public final int mStartBottom;
        public final Rect mStartClip;
        public final boolean mStartClipIsNull;
        public final int mStartLeft;
        public final int mStartRight;
        public final int mStartTop;
        public final View mView;

        public ClipListener(View view, Rect rect, boolean z, Rect rect2, boolean z2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            this.mView = view;
            this.mStartClip = rect;
            this.mStartClipIsNull = z;
            this.mEndClip = rect2;
            this.mEndClipIsNull = z2;
            this.mStartLeft = i;
            this.mStartTop = i2;
            this.mStartRight = i3;
            this.mStartBottom = i4;
            this.mEndLeft = i5;
            this.mEndTop = i6;
            this.mEndRight = i7;
            this.mEndBottom = i8;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            onAnimationEnd(animator, false);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            onAnimationStart(animator, false);
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionCancel(Transition transition) {
            this.mIsCanceled = true;
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionPause() {
            this.mView.setTag(R.id.transition_clip, this.mView.getClipBounds());
            this.mView.setClipBounds(this.mEndClipIsNull ? null : this.mEndClip);
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionResume() {
            Rect rect = (Rect) this.mView.getTag(R.id.transition_clip);
            this.mView.setTag(R.id.transition_clip, null);
            this.mView.setClipBounds(rect);
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator, boolean z) {
            if (this.mIsCanceled) {
                return;
            }
            Rect rect = null;
            if (z) {
                if (!this.mStartClipIsNull) {
                    rect = this.mStartClip;
                }
            } else if (!this.mEndClipIsNull) {
                rect = this.mEndClip;
            }
            this.mView.setClipBounds(rect);
            if (z) {
                ViewUtils.setLeftTopRightBottom(this.mView, this.mStartLeft, this.mStartTop, this.mStartRight, this.mStartBottom);
            } else {
                ViewUtils.setLeftTopRightBottom(this.mView, this.mEndLeft, this.mEndTop, this.mEndRight, this.mEndBottom);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator, boolean z) {
            int max = Math.max(this.mStartRight - this.mStartLeft, this.mEndRight - this.mEndLeft);
            int max2 = Math.max(this.mStartBottom - this.mStartTop, this.mEndBottom - this.mEndTop);
            int i = z ? this.mEndLeft : this.mStartLeft;
            int i2 = z ? this.mEndTop : this.mStartTop;
            ViewUtils.setLeftTopRightBottom(this.mView, i, i2, max + i, max2 + i2);
            this.mView.setClipBounds(z ? this.mEndClip : this.mStartClip);
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionEnd(Transition transition) {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionStart(Transition transition) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SuppressLayoutListener extends TransitionListenerAdapter {
        public boolean mCanceled = false;
        public final ViewGroup mParent;

        public SuppressLayoutListener(ViewGroup viewGroup) {
            this.mParent = viewGroup;
        }

        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
        public final void onTransitionCancel(Transition transition) {
            ViewGroupUtils$Api29Impl.suppressLayout(this.mParent, false);
            this.mCanceled = true;
        }

        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
        public final void onTransitionEnd(Transition transition) {
            if (!this.mCanceled) {
                ViewGroupUtils$Api29Impl.suppressLayout(this.mParent, false);
            }
            transition.removeListener(this);
        }

        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
        public final void onTransitionPause() {
            ViewGroupUtils$Api29Impl.suppressLayout(this.mParent, false);
        }

        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
        public final void onTransitionResume() {
            ViewGroupUtils$Api29Impl.suppressLayout(this.mParent, true);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ViewBounds {
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
    static {
        String str = "topLeft";
        TOP_LEFT_PROPERTY = new Property(PointF.class, str) { // from class: androidx.transition.ChangeBounds.1
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
        BOTTOM_RIGHT_PROPERTY = new Property(PointF.class, str2) { // from class: androidx.transition.ChangeBounds.2
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
        BOTTOM_RIGHT_ONLY_PROPERTY = new Property(PointF.class, str2) { // from class: androidx.transition.ChangeBounds.3
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
        TOP_LEFT_ONLY_PROPERTY = new Property(PointF.class, str) { // from class: androidx.transition.ChangeBounds.4
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
        this.mResizeClip = false;
    }

    @Override // androidx.transition.Transition
    public final void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // androidx.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        Rect rect;
        captureValues(transitionValues);
        if (!this.mResizeClip || (rect = (Rect) transitionValues.view.getTag(R.id.transition_clip)) == null) {
            return;
        }
        ((HashMap) transitionValues.values).put("android:changeBounds:clip", rect);
    }

    public final void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (!view.isLaidOut() && view.getWidth() == 0 && view.getHeight() == 0) {
            return;
        }
        ((HashMap) transitionValues.values).put("android:changeBounds:bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        ((HashMap) transitionValues.values).put("android:changeBounds:parent", transitionValues.view.getParent());
        if (this.mResizeClip) {
            ((HashMap) transitionValues.values).put("android:changeBounds:clip", view.getClipBounds());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.transition.Transition
    public final Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        int i;
        int i2;
        int i3;
        int i4;
        Animator ofObject;
        int i5;
        Rect rect;
        ObjectAnimator objectAnimator;
        if (transitionValues != null && transitionValues2 != null) {
            Map map = transitionValues.values;
            Map map2 = transitionValues2.values;
            ViewGroup viewGroup2 = (ViewGroup) ((HashMap) map).get("android:changeBounds:parent");
            ViewGroup viewGroup3 = (ViewGroup) ((HashMap) map2).get("android:changeBounds:parent");
            if (viewGroup2 != null && viewGroup3 != null) {
                View view = transitionValues2.view;
                Rect rect2 = (Rect) ((HashMap) transitionValues.values).get("android:changeBounds:bounds");
                Rect rect3 = (Rect) ((HashMap) transitionValues2.values).get("android:changeBounds:bounds");
                int i6 = rect2.left;
                int i7 = rect3.left;
                int i8 = rect2.top;
                int i9 = rect3.top;
                int i10 = rect2.right;
                int i11 = rect3.right;
                int i12 = rect2.bottom;
                int i13 = rect3.bottom;
                int i14 = i10 - i6;
                int i15 = i12 - i8;
                int i16 = i11 - i7;
                int i17 = i13 - i9;
                Rect rect4 = (Rect) ((HashMap) transitionValues.values).get("android:changeBounds:clip");
                Rect rect5 = (Rect) ((HashMap) transitionValues2.values).get("android:changeBounds:clip");
                if ((i14 == 0 || i15 == 0) && (i16 == 0 || i17 == 0)) {
                    i = 0;
                } else {
                    i = (i6 == i7 && i8 == i9) ? 0 : 1;
                    if (i10 != i11 || i12 != i13) {
                        i++;
                    }
                }
                if ((rect4 != null && !rect4.equals(rect5)) || (rect4 == null && rect5 != null)) {
                    i++;
                }
                int i18 = i;
                if (i18 <= 0) {
                    return null;
                }
                if (this.mResizeClip) {
                    ViewUtils.setLeftTopRightBottom(view, i6, i8, i6 + Math.max(i14, i16), i8 + Math.max(i15, i17));
                    if (i6 == i7 && i8 == i9) {
                        ofObject = null;
                        i2 = i13;
                        i4 = i7;
                        i3 = i11;
                    } else {
                        i2 = i13;
                        i3 = i11;
                        i4 = i7;
                        ofObject = ObjectAnimatorUtils$Api21Impl.ofObject(view, POSITION_PROPERTY, this.mPathMotion.getPath(i6, i8, i7, i9));
                    }
                    boolean z = rect4 == null;
                    if (z) {
                        i5 = 0;
                        rect = new Rect(0, 0, i14, i15);
                    } else {
                        i5 = 0;
                        rect = rect4;
                    }
                    int i19 = rect5 == null ? 1 : i5;
                    Rect rect6 = i19 != 0 ? new Rect(i5, i5, i16, i17) : rect5;
                    if (rect.equals(rect6)) {
                        objectAnimator = null;
                    } else {
                        view.setClipBounds(rect);
                        objectAnimator = ObjectAnimator.ofObject(view, "clipBounds", sRectEvaluator, rect, rect6);
                        ClipListener clipListener = new ClipListener(view, rect, z, rect6, i19, i6, i8, i10, i12, i4, i9, i3, i2);
                        objectAnimator.addListener(clipListener);
                        addListener(clipListener);
                    }
                    if (ofObject == null) {
                        ofObject = objectAnimator;
                    } else if (objectAnimator != null) {
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(ofObject, objectAnimator);
                        ofObject = animatorSet;
                    }
                } else {
                    ViewUtils.setLeftTopRightBottom(view, i6, i8, i10, i12);
                    if (i18 != 2) {
                        ofObject = (i6 == i7 && i8 == i9) ? ObjectAnimatorUtils$Api21Impl.ofObject(view, BOTTOM_RIGHT_ONLY_PROPERTY, this.mPathMotion.getPath(i10, i12, i11, i13)) : ObjectAnimatorUtils$Api21Impl.ofObject(view, TOP_LEFT_ONLY_PROPERTY, this.mPathMotion.getPath(i6, i8, i7, i9));
                    } else if (i14 == i16 && i15 == i17) {
                        ofObject = ObjectAnimatorUtils$Api21Impl.ofObject(view, POSITION_PROPERTY, this.mPathMotion.getPath(i6, i8, i7, i9));
                    } else {
                        ViewBounds viewBounds = new ViewBounds(view);
                        Animator ofObject2 = ObjectAnimatorUtils$Api21Impl.ofObject(viewBounds, TOP_LEFT_PROPERTY, this.mPathMotion.getPath(i6, i8, i7, i9));
                        Animator ofObject3 = ObjectAnimatorUtils$Api21Impl.ofObject(viewBounds, BOTTOM_RIGHT_PROPERTY, this.mPathMotion.getPath(i10, i12, i11, i13));
                        AnimatorSet animatorSet2 = new AnimatorSet();
                        animatorSet2.playTogether(ofObject2, ofObject3);
                        animatorSet2.addListener(new AnimatorListenerAdapter(this, viewBounds) { // from class: androidx.transition.ChangeBounds.6
                            private final ViewBounds mViewBounds;

                            {
                                this.mViewBounds = viewBounds;
                            }
                        });
                        ofObject = animatorSet2;
                    }
                }
                if (view.getParent() instanceof ViewGroup) {
                    ViewGroup viewGroup4 = (ViewGroup) view.getParent();
                    ViewGroupUtils$Api29Impl.suppressLayout(viewGroup4, true);
                    getRootTransition().addListener(new SuppressLayoutListener(viewGroup4));
                }
                return ofObject;
            }
        }
        return null;
    }

    @Override // androidx.transition.Transition
    public final String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public ChangeBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mResizeClip = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.CHANGE_BOUNDS);
        boolean z = TypedArrayUtils.hasAttribute((XmlResourceParser) attributeSet, "resizeClip") ? obtainStyledAttributes.getBoolean(0, false) : false;
        obtainStyledAttributes.recycle();
        this.mResizeClip = z;
    }
}
