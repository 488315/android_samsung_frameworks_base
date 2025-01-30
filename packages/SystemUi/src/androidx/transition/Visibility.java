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
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class Visibility extends Transition {
    public static final String[] sTransitionProperties = {"android:visibility:visibility", "android:visibility:parent"};
    public int mMode;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VisibilityInfo {
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

    /* JADX WARN: Removed duplicated region for block: B:12:0x005d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static VisibilityInfo getVisibilityChangeInfo(TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityInfo = new VisibilityInfo();
        visibilityInfo.mVisibilityChange = false;
        visibilityInfo.mFadeIn = false;
        if (transitionValues != null) {
            HashMap hashMap = (HashMap) transitionValues.values;
            if (hashMap.containsKey("android:visibility:visibility")) {
                visibilityInfo.mStartVisibility = ((Integer) hashMap.get("android:visibility:visibility")).intValue();
                visibilityInfo.mStartParent = (ViewGroup) hashMap.get("android:visibility:parent");
                if (transitionValues2 != null) {
                    HashMap hashMap2 = (HashMap) transitionValues2.values;
                    if (hashMap2.containsKey("android:visibility:visibility")) {
                        visibilityInfo.mEndVisibility = ((Integer) hashMap2.get("android:visibility:visibility")).intValue();
                        visibilityInfo.mEndParent = (ViewGroup) hashMap2.get("android:visibility:parent");
                        if (transitionValues == null && transitionValues2 != null) {
                            int i = visibilityInfo.mStartVisibility;
                            int i2 = visibilityInfo.mEndVisibility;
                            if (i == i2 && visibilityInfo.mStartParent == visibilityInfo.mEndParent) {
                                return visibilityInfo;
                            }
                            if (i != i2) {
                                if (i == 0) {
                                    visibilityInfo.mFadeIn = false;
                                    visibilityInfo.mVisibilityChange = true;
                                } else if (i2 == 0) {
                                    visibilityInfo.mFadeIn = true;
                                    visibilityInfo.mVisibilityChange = true;
                                }
                            } else if (visibilityInfo.mEndParent == null) {
                                visibilityInfo.mFadeIn = false;
                                visibilityInfo.mVisibilityChange = true;
                            } else if (visibilityInfo.mStartParent == null) {
                                visibilityInfo.mFadeIn = true;
                                visibilityInfo.mVisibilityChange = true;
                            }
                        } else if (transitionValues != null && visibilityInfo.mEndVisibility == 0) {
                            visibilityInfo.mFadeIn = true;
                            visibilityInfo.mVisibilityChange = true;
                        } else if (transitionValues2 == null && visibilityInfo.mStartVisibility == 0) {
                            visibilityInfo.mFadeIn = false;
                            visibilityInfo.mVisibilityChange = true;
                        }
                        return visibilityInfo;
                    }
                }
                visibilityInfo.mEndVisibility = -1;
                visibilityInfo.mEndParent = null;
                if (transitionValues == null) {
                }
                if (transitionValues != null) {
                }
                if (transitionValues2 == null) {
                    visibilityInfo.mFadeIn = false;
                    visibilityInfo.mVisibilityChange = true;
                }
                return visibilityInfo;
            }
        }
        visibilityInfo.mStartVisibility = -1;
        visibilityInfo.mStartParent = null;
        if (transitionValues2 != null) {
        }
        visibilityInfo.mEndVisibility = -1;
        visibilityInfo.mEndParent = null;
        if (transitionValues == null) {
        }
        if (transitionValues != null) {
        }
        if (transitionValues2 == null) {
        }
        return visibilityInfo;
    }

    @Override // androidx.transition.Transition
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // androidx.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public final void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        int visibility = view.getVisibility();
        HashMap hashMap = (HashMap) transitionValues.values;
        hashMap.put("android:visibility:visibility", Integer.valueOf(visibility));
        hashMap.put("android:visibility:parent", view.getParent());
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        hashMap.put("android:visibility:screenLocation", iArr);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x003e, code lost:
    
        if (getVisibilityChangeInfo(getMatchedTransitionValues(r5, false), getTransitionValues(r5, false)).mVisibilityChange != false) goto L20;
     */
    /* JADX WARN: Removed duplicated region for block: B:69:0x019e  */
    @Override // androidx.transition.Transition
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Animator createAnimator(final ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        boolean z;
        View view;
        int i;
        ViewGroup viewGroup2;
        int i2;
        Bitmap bitmap;
        VisibilityInfo visibilityChangeInfo = getVisibilityChangeInfo(transitionValues, transitionValues2);
        if (!visibilityChangeInfo.mVisibilityChange) {
            return null;
        }
        if (visibilityChangeInfo.mStartParent == null && visibilityChangeInfo.mEndParent == null) {
            return null;
        }
        boolean z2 = true;
        if (visibilityChangeInfo.mFadeIn) {
            if ((this.mMode & 1) == 1 && transitionValues2 != null) {
                View view2 = transitionValues2.view;
                if (transitionValues == null) {
                    View view3 = (View) view2.getParent();
                }
                return onAppear(viewGroup, view2, transitionValues, transitionValues2);
            }
            return null;
        }
        int i3 = visibilityChangeInfo.mEndVisibility;
        if ((this.mMode & 2) == 2 && transitionValues != null) {
            View view4 = transitionValues2 != null ? transitionValues2.view : null;
            final View view5 = transitionValues.view;
            final View view6 = (View) view5.getTag(R.id.save_overlay_view);
            if (view6 != null) {
                i = i3;
                view4 = null;
            } else {
                if (view4 == null || view4.getParent() == null) {
                    if (view4 != null) {
                        view6 = view4;
                        view4 = null;
                        z = false;
                    }
                    z = true;
                    view4 = null;
                    view6 = null;
                } else {
                    if (i3 == 4 || view5 == view4) {
                        view6 = null;
                        z = false;
                    }
                    z = true;
                    view4 = null;
                    view6 = null;
                }
                if (z) {
                    if (view5.getParent() == null) {
                        i = i3;
                        view6 = view5;
                        z2 = false;
                    } else if (view5.getParent() instanceof View) {
                        View view7 = (View) view5.getParent();
                        if (getVisibilityChangeInfo(getTransitionValues(view7, true), getMatchedTransitionValues(view7, true)).mVisibilityChange) {
                            view = view4;
                            i = i3;
                            int id = view7.getId();
                            if (view7.getParent() == null && id != -1) {
                                viewGroup.findViewById(id);
                            }
                        } else {
                            Matrix matrix = new Matrix();
                            matrix.setTranslate(-view7.getScrollX(), -view7.getScrollY());
                            ViewUtilsApi29 viewUtilsApi29 = ViewUtils.IMPL;
                            viewUtilsApi29.getClass();
                            view5.transformMatrixToGlobal(matrix);
                            viewUtilsApi29.getClass();
                            viewGroup.transformMatrixToLocal(matrix);
                            RectF rectF = new RectF(0.0f, 0.0f, view5.getWidth(), view5.getHeight());
                            matrix.mapRect(rectF);
                            int round = Math.round(rectF.left);
                            int round2 = Math.round(rectF.top);
                            int round3 = Math.round(rectF.right);
                            int round4 = Math.round(rectF.bottom);
                            ImageView imageView = new ImageView(view5.getContext());
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            boolean z3 = !view5.isAttachedToWindow();
                            boolean isAttachedToWindow = viewGroup.isAttachedToWindow();
                            if (!z3) {
                                viewGroup2 = null;
                                i2 = 0;
                            } else if (isAttachedToWindow) {
                                viewGroup2 = (ViewGroup) view5.getParent();
                                int indexOfChild = viewGroup2.indexOfChild(view5);
                                viewGroup.getOverlay().add(view5);
                                i2 = indexOfChild;
                            } else {
                                view = view4;
                                i = i3;
                                bitmap = null;
                                if (bitmap != null) {
                                    imageView.setImageBitmap(bitmap);
                                }
                                imageView.measure(View.MeasureSpec.makeMeasureSpec(round3 - round, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(round4 - round2, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                                imageView.layout(round, round2, round3, round4);
                                view6 = imageView;
                            }
                            view = view4;
                            int round5 = Math.round(rectF.width());
                            i = i3;
                            int round6 = Math.round(rectF.height());
                            if (round5 <= 0 || round6 <= 0) {
                                bitmap = null;
                            } else {
                                float min = Math.min(1.0f, 1048576.0f / (round5 * round6));
                                int round7 = Math.round(round5 * min);
                                int round8 = Math.round(round6 * min);
                                matrix.postTranslate(-rectF.left, -rectF.top);
                                matrix.postScale(min, min);
                                Picture picture = new Picture();
                                Canvas beginRecording = picture.beginRecording(round7, round8);
                                beginRecording.concat(matrix);
                                view5.draw(beginRecording);
                                picture.endRecording();
                                bitmap = Bitmap.createBitmap(picture);
                            }
                            if (z3) {
                                viewGroup.getOverlay().remove(view5);
                                viewGroup2.addView(view5, i2);
                            }
                            if (bitmap != null) {
                            }
                            imageView.measure(View.MeasureSpec.makeMeasureSpec(round3 - round, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(round4 - round2, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                            imageView.layout(round, round2, round3, round4);
                            view6 = imageView;
                        }
                        view4 = view;
                        z2 = false;
                    }
                }
                view = view4;
                i = i3;
                view4 = view;
                z2 = false;
            }
            if (view6 != null) {
                if (!z2) {
                    int[] iArr = (int[]) ((HashMap) transitionValues.values).get("android:visibility:screenLocation");
                    int i4 = iArr[0];
                    int i5 = iArr[1];
                    int[] iArr2 = new int[2];
                    viewGroup.getLocationOnScreen(iArr2);
                    view6.offsetLeftAndRight((i4 - iArr2[0]) - view6.getLeft());
                    view6.offsetTopAndBottom((i5 - iArr2[1]) - view6.getTop());
                    new ViewGroupOverlayApi18(viewGroup).mViewGroupOverlay.add(view6);
                }
                Animator onDisappear = onDisappear(viewGroup, view6, transitionValues);
                if (z2) {
                    return onDisappear;
                }
                if (onDisappear == null) {
                    new ViewGroupOverlayApi18(viewGroup).mViewGroupOverlay.remove(view6);
                    return onDisappear;
                }
                view5.setTag(R.id.save_overlay_view, view6);
                addListener(new TransitionListenerAdapter() { // from class: androidx.transition.Visibility.1
                    @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                    public final void onTransitionEnd(Transition transition) {
                        view5.setTag(R.id.save_overlay_view, null);
                        new ViewGroupOverlayApi18(viewGroup).mViewGroupOverlay.remove(view6);
                        transition.removeListener(this);
                    }

                    @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                    public final void onTransitionPause() {
                        new ViewGroupOverlayApi18(viewGroup).mViewGroupOverlay.remove(view6);
                    }

                    @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                    public final void onTransitionResume() {
                        View view8 = view6;
                        if (view8.getParent() == null) {
                            new ViewGroupOverlayApi18(viewGroup).mViewGroupOverlay.add(view8);
                        } else {
                            Visibility.this.cancel();
                        }
                    }
                });
                return onDisappear;
            }
            if (view4 != null) {
                int visibility = view4.getVisibility();
                ViewUtils.setTransitionVisibility(view4, 0);
                Animator onDisappear2 = onDisappear(viewGroup, view4, transitionValues);
                if (onDisappear2 == null) {
                    ViewUtils.setTransitionVisibility(view4, visibility);
                    return onDisappear2;
                }
                DisappearListener disappearListener = new DisappearListener(view4, i, true);
                onDisappear2.addListener(disappearListener);
                onDisappear2.addPauseListener(disappearListener);
                addListener(disappearListener);
                return onDisappear2;
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

    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues) {
        return null;
    }

    public Visibility(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMode = 3;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.VISIBILITY_TRANSITION);
        int namedInt = TypedArrayUtils.getNamedInt(obtainStyledAttributes, (XmlResourceParser) attributeSet, "transitionVisibilityMode", 0, 0);
        obtainStyledAttributes.recycle();
        if (namedInt != 0) {
            if ((namedInt & (-4)) == 0) {
                this.mMode = namedInt;
                return;
            }
            throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DisappearListener extends AnimatorListenerAdapter implements Transition.TransitionListener {
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

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
        public final void onAnimationPause(Animator animator) {
            if (this.mCanceled) {
                return;
            }
            ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
        public final void onAnimationResume(Animator animator) {
            if (this.mCanceled) {
                return;
            }
            ViewUtils.setTransitionVisibility(this.mView, 0);
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionEnd(Transition transition) {
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
                ViewGroup viewGroup = this.mParent;
                if (viewGroup != null) {
                    viewGroup.invalidate();
                }
            }
            suppressLayout(false);
            transition.removeListener(this);
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionPause() {
            suppressLayout(false);
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionResume() {
            suppressLayout(true);
        }

        public final void suppressLayout(boolean z) {
            ViewGroup viewGroup;
            if (!this.mSuppressLayout || this.mLayoutSuppressed == z || (viewGroup = this.mParent) == null) {
                return;
            }
            this.mLayoutSuppressed = z;
            viewGroup.suppressLayout(z);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionStart(Transition transition) {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionCancel() {
        }
    }
}
