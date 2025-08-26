package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.content.res.TypedArrayUtils;
import androidx.transition.Transition;
import com.android.systemui.R;
import java.util.HashMap;

/* loaded from: classes.dex */
public abstract class Visibility extends Transition {
    public static final String[] sTransitionProperties = {"android:visibility:visibility", "android:visibility:parent"};
    public int mMode;

    public class DisappearListener extends AnimatorListenerAdapter implements Transition.TransitionListener {
        public boolean mCanceled = false;
        public final int mFinalVisibility;
        public boolean mLayoutSuppressed;
        public final ViewGroup mParent;
        public final boolean mSuppressLayout;
        public final View mView;

        public DisappearListener(View view, int i, boolean z) {
            this.mView = view;
            this.mFinalVisibility = i;
            this.mParent = (ViewGroup) view.getParent();
            this.mSuppressLayout = z;
            suppressLayout(true);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            this.mCanceled = true;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
                ViewGroup viewGroup = this.mParent;
                if (viewGroup != null) {
                    viewGroup.invalidate();
                }
            }
            suppressLayout(false);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionEnd(Transition transition) {
            transition.removeListener(this);
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionPause() {
            suppressLayout(false);
            if (this.mCanceled) {
                return;
            }
            ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionResume() {
            suppressLayout(true);
            if (this.mCanceled) {
                return;
            }
            ViewUtils.setTransitionVisibility(this.mView, 0);
        }

        public final void suppressLayout(boolean z) {
            ViewGroup viewGroup;
            if (!this.mSuppressLayout || this.mLayoutSuppressed == z || (viewGroup = this.mParent) == null) {
                return;
            }
            this.mLayoutSuppressed = z;
            ViewGroupUtils$Api29Impl.suppressLayout(viewGroup, z);
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator, boolean z) {
            if (z) {
                ViewUtils.setTransitionVisibility(this.mView, 0);
                ViewGroup viewGroup = this.mParent;
                if (viewGroup != null) {
                    viewGroup.invalidate();
                }
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator, boolean z) {
            if (z) {
                return;
            }
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
                ViewGroup viewGroup = this.mParent;
                if (viewGroup != null) {
                    viewGroup.invalidate();
                }
            }
            suppressLayout(false);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionCancel(Transition transition) {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionStart(Transition transition) {
        }
    }

    public class OverlayListener extends AnimatorListenerAdapter implements Transition.TransitionListener {
        public boolean mHasOverlay = true;
        public final ViewGroup mOverlayHost;
        public final View mOverlayView;
        public final View mStartView;

        public OverlayListener(ViewGroup viewGroup, View view, View view2) {
            this.mOverlayHost = viewGroup;
            this.mOverlayView = view;
            this.mStartView = view2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            removeFromOverlay();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
        public final void onAnimationPause(Animator animator) {
            this.mOverlayHost.getOverlay().remove(this.mOverlayView);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
        public final void onAnimationResume(Animator animator) {
            if (this.mOverlayView.getParent() == null) {
                this.mOverlayHost.getOverlay().add(this.mOverlayView);
            } else {
                Visibility.this.cancel();
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator, boolean z) {
            if (z) {
                this.mStartView.setTag(R.id.save_overlay_view, this.mOverlayView);
                this.mOverlayHost.getOverlay().add(this.mOverlayView);
                this.mHasOverlay = true;
            }
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionCancel(Transition transition) {
            if (this.mHasOverlay) {
                removeFromOverlay();
            }
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionEnd(Transition transition) {
            transition.removeListener(this);
        }

        public final void removeFromOverlay() {
            this.mStartView.setTag(R.id.save_overlay_view, null);
            this.mOverlayHost.getOverlay().remove(this.mOverlayView);
            this.mHasOverlay = false;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator, boolean z) {
            if (z) {
                return;
            }
            removeFromOverlay();
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionPause() {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionResume() {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionStart(Transition transition) {
        }
    }

    public class VisibilityInfo {
        public ViewGroup mEndParent;
        public int mEndVisibility;
        public boolean mFadeIn;
        public ViewGroup mStartParent;
        public int mStartVisibility;
        public boolean mVisibilityChange;
    }

    public Visibility() {
        this.mMode = 3;
    }

    public static void captureValues$1(TransitionValues transitionValues) {
        int visibility = transitionValues.view.getVisibility();
        ((HashMap) transitionValues.values).put("android:visibility:visibility", Integer.valueOf(visibility));
        ((HashMap) transitionValues.values).put("android:visibility:parent", transitionValues.view.getParent());
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        ((HashMap) transitionValues.values).put("android:visibility:screenLocation", iArr);
    }

    public static VisibilityInfo getVisibilityChangeInfo(TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityInfo = new VisibilityInfo();
        visibilityInfo.mVisibilityChange = false;
        visibilityInfo.mFadeIn = false;
        if (transitionValues == null || !((HashMap) transitionValues.values).containsKey("android:visibility:visibility")) {
            visibilityInfo.mStartVisibility = -1;
            visibilityInfo.mStartParent = null;
        } else {
            visibilityInfo.mStartVisibility = ((Integer) ((HashMap) transitionValues.values).get("android:visibility:visibility")).intValue();
            visibilityInfo.mStartParent = (ViewGroup) ((HashMap) transitionValues.values).get("android:visibility:parent");
        }
        if (transitionValues2 == null || !((HashMap) transitionValues2.values).containsKey("android:visibility:visibility")) {
            visibilityInfo.mEndVisibility = -1;
            visibilityInfo.mEndParent = null;
        } else {
            visibilityInfo.mEndVisibility = ((Integer) ((HashMap) transitionValues2.values).get("android:visibility:visibility")).intValue();
            visibilityInfo.mEndParent = (ViewGroup) ((HashMap) transitionValues2.values).get("android:visibility:parent");
        }
        if (transitionValues != null && transitionValues2 != null) {
            int i = visibilityInfo.mStartVisibility;
            int i2 = visibilityInfo.mEndVisibility;
            if (i != i2 || visibilityInfo.mStartParent != visibilityInfo.mEndParent) {
                if (i != i2) {
                    if (i == 0) {
                        visibilityInfo.mFadeIn = false;
                        visibilityInfo.mVisibilityChange = true;
                        return visibilityInfo;
                    }
                    if (i2 == 0) {
                        visibilityInfo.mFadeIn = true;
                        visibilityInfo.mVisibilityChange = true;
                        return visibilityInfo;
                    }
                } else {
                    if (visibilityInfo.mEndParent == null) {
                        visibilityInfo.mFadeIn = false;
                        visibilityInfo.mVisibilityChange = true;
                        return visibilityInfo;
                    }
                    if (visibilityInfo.mStartParent == null) {
                        visibilityInfo.mFadeIn = true;
                        visibilityInfo.mVisibilityChange = true;
                        return visibilityInfo;
                    }
                }
            }
        } else {
            if (transitionValues == null && visibilityInfo.mEndVisibility == 0) {
                visibilityInfo.mFadeIn = true;
                visibilityInfo.mVisibilityChange = true;
                return visibilityInfo;
            }
            if (transitionValues2 == null && visibilityInfo.mStartVisibility == 0) {
                visibilityInfo.mFadeIn = false;
                visibilityInfo.mVisibilityChange = true;
            }
        }
        return visibilityInfo;
    }

    @Override // androidx.transition.Transition
    public final void captureEndValues(TransitionValues transitionValues) {
        captureValues$1(transitionValues);
    }

    @Override // androidx.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues$1(transitionValues);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0043, code lost:
    
        if (getVisibilityChangeInfo(getMatchedTransitionValues(r1, false), getTransitionValues(r1, false)).mVisibilityChange != false) goto L9;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01f6  */
    /* JADX WARN: Type inference failed for: r17v5 */
    /* JADX WARN: Type inference failed for: r17v6 */
    /* JADX WARN: Type inference failed for: r17v7 */
    /* JADX WARN: Type inference failed for: r17v8 */
    @Override // androidx.transition.Transition
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        boolean z;
        View view;
        int i;
        int i2;
        boolean z2;
        View view2;
        Animator animator;
        boolean z3;
        View view3;
        boolean z4;
        ViewGroup viewGroup2;
        int i3;
        Bitmap bitmapCreateBitmap;
        boolean z5;
        VisibilityInfo visibilityChangeInfo = getVisibilityChangeInfo(transitionValues, transitionValues2);
        if (visibilityChangeInfo.mVisibilityChange && (visibilityChangeInfo.mStartParent != null || visibilityChangeInfo.mEndParent != null)) {
            int i4 = 0;
            if (!visibilityChangeInfo.mFadeIn) {
                int i5 = visibilityChangeInfo.mEndVisibility;
                if ((this.mMode & 2) == 2 && transitionValues != null) {
                    View view4 = transitionValues.view;
                    View view5 = transitionValues2 != null ? transitionValues2.view : null;
                    View view6 = (View) view4.getTag(R.id.save_overlay_view);
                    if (view6 != null) {
                        i = i5;
                        i2 = 0;
                        i4 = 1;
                        z5 = 1;
                        view3 = null;
                        animator = null;
                    } else {
                        if (view5 == null || view5.getParent() == null) {
                            if (view5 != null) {
                                z = false;
                            }
                            view = null;
                            if (!z) {
                                i = i5;
                                i2 = 0;
                                z2 = true;
                                view2 = view;
                                animator = null;
                                view6 = view5;
                                z3 = z2;
                                i4 = i2;
                                view3 = view2;
                                z5 = z3;
                            } else if (view4.getParent() == null) {
                                i = i5;
                                i2 = 0;
                                z5 = 1;
                                view3 = view;
                                animator = null;
                                view6 = view4;
                            } else {
                                if (view4.getParent() instanceof View) {
                                    View view7 = (View) view4.getParent();
                                    if (getVisibilityChangeInfo(getTransitionValues(view7, true), getMatchedTransitionValues(view7, true)).mVisibilityChange) {
                                        i = i5;
                                        i2 = 0;
                                        boolean z6 = true;
                                        view2 = view;
                                        animator = null;
                                        int id = view7.getId();
                                        z2 = z6;
                                        if (view7.getParent() == null) {
                                            z2 = z6;
                                            if (id != -1) {
                                                viewGroup.findViewById(id);
                                                z2 = z6;
                                            }
                                        }
                                    } else {
                                        Matrix matrix = new Matrix();
                                        matrix.setTranslate(-view7.getScrollX(), -view7.getScrollY());
                                        ViewUtils.IMPL.getClass();
                                        view4.transformMatrixToGlobal(matrix);
                                        viewGroup.transformMatrixToLocal(matrix);
                                        animator = null;
                                        RectF rectF = new RectF(0.0f, 0.0f, view4.getWidth(), view4.getHeight());
                                        matrix.mapRect(rectF);
                                        int iRound = Math.round(rectF.left);
                                        int iRound2 = Math.round(rectF.top);
                                        int iRound3 = Math.round(rectF.right);
                                        z3 = true;
                                        int iRound4 = Math.round(rectF.bottom);
                                        i2 = 0;
                                        ImageView imageView = new ImageView(view4.getContext());
                                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                        boolean zIsAttachedToWindow = view4.isAttachedToWindow();
                                        boolean zIsAttachedToWindow2 = viewGroup.isAttachedToWindow();
                                        if (zIsAttachedToWindow) {
                                            z4 = zIsAttachedToWindow;
                                            viewGroup2 = null;
                                            i3 = 0;
                                        } else if (zIsAttachedToWindow2) {
                                            ViewGroup viewGroup3 = (ViewGroup) view4.getParent();
                                            int iIndexOfChild = viewGroup3.indexOfChild(view4);
                                            viewGroup.getOverlay().add(view4);
                                            z4 = zIsAttachedToWindow;
                                            i3 = iIndexOfChild;
                                            viewGroup2 = viewGroup3;
                                        } else {
                                            i = i5;
                                            view2 = view;
                                            bitmapCreateBitmap = null;
                                            if (bitmapCreateBitmap != null) {
                                                imageView.setImageBitmap(bitmapCreateBitmap);
                                            }
                                            imageView.measure(View.MeasureSpec.makeMeasureSpec(iRound3 - iRound, 1073741824), View.MeasureSpec.makeMeasureSpec(iRound4 - iRound2, 1073741824));
                                            imageView.layout(iRound, iRound2, iRound3, iRound4);
                                            view6 = imageView;
                                            i4 = i2;
                                            view3 = view2;
                                            z5 = z3;
                                        }
                                        view2 = view;
                                        int iRound5 = Math.round(rectF.width());
                                        i = i5;
                                        int iRound6 = Math.round(rectF.height());
                                        if (iRound5 <= 0 || iRound6 <= 0) {
                                            bitmapCreateBitmap = null;
                                        } else {
                                            float fMin = Math.min(1.0f, 1048576.0f / (iRound5 * iRound6));
                                            int iRound7 = Math.round(iRound5 * fMin);
                                            int iRound8 = Math.round(iRound6 * fMin);
                                            matrix.postTranslate(-rectF.left, -rectF.top);
                                            matrix.postScale(fMin, fMin);
                                            Picture picture = new Picture();
                                            Canvas canvasBeginRecording = picture.beginRecording(iRound7, iRound8);
                                            canvasBeginRecording.concat(matrix);
                                            view4.draw(canvasBeginRecording);
                                            picture.endRecording();
                                            bitmapCreateBitmap = TransitionUtils$Api28Impl.createBitmap(picture);
                                        }
                                        if (!z4) {
                                            viewGroup.getOverlay().remove(view4);
                                            viewGroup2.addView(view4, i3);
                                        }
                                        if (bitmapCreateBitmap != null) {
                                        }
                                        imageView.measure(View.MeasureSpec.makeMeasureSpec(iRound3 - iRound, 1073741824), View.MeasureSpec.makeMeasureSpec(iRound4 - iRound2, 1073741824));
                                        imageView.layout(iRound, iRound2, iRound3, iRound4);
                                        view6 = imageView;
                                        i4 = i2;
                                        view3 = view2;
                                        z5 = z3;
                                    }
                                }
                                view6 = view5;
                                z3 = z2;
                                i4 = i2;
                                view3 = view2;
                                z5 = z3;
                            }
                        } else if (i5 == 4 || view4 == view5) {
                            z = false;
                            view = view5;
                            view5 = null;
                            if (!z) {
                            }
                        }
                        z = true;
                        view5 = null;
                        view = null;
                        if (!z) {
                        }
                    }
                    if (view6 == null) {
                        if (view3 == null) {
                            return animator;
                        }
                        int visibility = view3.getVisibility();
                        ViewUtils.setTransitionVisibility(view3, i2);
                        Animator animatorOnDisappear = onDisappear(view3, transitionValues, transitionValues2);
                        if (animatorOnDisappear == null) {
                            ViewUtils.setTransitionVisibility(view3, visibility);
                            return animatorOnDisappear;
                        }
                        DisappearListener disappearListener = new DisappearListener(view3, i, z5);
                        animatorOnDisappear.addListener(disappearListener);
                        getRootTransition().addListener(disappearListener);
                        return animatorOnDisappear;
                    }
                    if (i4 == 0) {
                        int[] iArr = (int[]) ((HashMap) transitionValues.values).get("android:visibility:screenLocation");
                        int i6 = iArr[i2];
                        int i7 = iArr[z5];
                        int[] iArr2 = new int[2];
                        viewGroup.getLocationOnScreen(iArr2);
                        view6.offsetLeftAndRight((i6 - iArr2[i2]) - view6.getLeft());
                        view6.offsetTopAndBottom((i7 - iArr2[z5]) - view6.getTop());
                        viewGroup.getOverlay().add(view6);
                    }
                    Animator animatorOnDisappear2 = onDisappear(view6, transitionValues, transitionValues2);
                    if (i4 == 0) {
                        if (animatorOnDisappear2 == null) {
                            viewGroup.getOverlay().remove(view6);
                            return animatorOnDisappear2;
                        }
                        view4.setTag(R.id.save_overlay_view, view6);
                        OverlayListener overlayListener = new OverlayListener(viewGroup, view6, view4);
                        animatorOnDisappear2.addListener(overlayListener);
                        animatorOnDisappear2.addPauseListener(overlayListener);
                        getRootTransition().addListener(overlayListener);
                    }
                    return animatorOnDisappear2;
                }
            } else if ((this.mMode & 1) == 1 && transitionValues2 != null) {
                if (transitionValues == null) {
                    View view8 = (View) transitionValues2.view.getParent();
                }
                return onAppear(transitionValues2.view, transitionValues);
            }
        }
        return null;
    }

    @Override // androidx.transition.Transition
    public final String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    @Override // androidx.transition.Transition
    public final boolean isTransitionRequired(TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null && transitionValues2 == null) {
            return false;
        }
        if (transitionValues != null && transitionValues2 != null && ((HashMap) transitionValues2.values).containsKey("android:visibility:visibility") != ((HashMap) transitionValues.values).containsKey("android:visibility:visibility")) {
            return false;
        }
        VisibilityInfo visibilityChangeInfo = getVisibilityChangeInfo(transitionValues, transitionValues2);
        if (visibilityChangeInfo.mVisibilityChange) {
            return visibilityChangeInfo.mStartVisibility == 0 || visibilityChangeInfo.mEndVisibility == 0;
        }
        return false;
    }

    public Animator onAppear(View view, TransitionValues transitionValues) {
        return null;
    }

    public Animator onDisappear(View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    public final void setMode(int i) {
        if ((i & (-4)) != 0) {
            throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
        }
        this.mMode = i;
    }

    public Visibility(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMode = 3;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.VISIBILITY_TRANSITION);
        int namedInt = TypedArrayUtils.getNamedInt(typedArrayObtainStyledAttributes, (XmlResourceParser) attributeSet, "transitionVisibilityMode", 0, 0);
        typedArrayObtainStyledAttributes.recycle();
        if (namedInt != 0) {
            setMode(namedInt);
        }
    }
}
