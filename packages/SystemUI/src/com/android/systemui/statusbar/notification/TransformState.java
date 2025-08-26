package com.android.systemui.statusbar.notification;

import android.util.Pools;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingPropertyAnimator;
import com.android.internal.widget.ViewClippingUtil;
import com.android.systemui.R;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* loaded from: classes3.dex */
public class TransformState {
    public boolean mAlignEnd;
    public boolean mSameAsAny;
    public ViewTransformationHelper mTransformInfo;
    public View mTransformedView;
    public static final Pools.SimplePool sInstancePool = new Pools.SimplePool(40);
    public static final AnonymousClass1 CLIPPING_PARAMETERS = new ViewClippingUtil.ClippingParameters() { // from class: com.android.systemui.statusbar.notification.TransformState.1
        public final void onClippingStateChanged(View view, boolean z) {
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (z) {
                    expandableNotificationRow.setClipToActualHeight(true);
                } else if (expandableNotificationRow.isChildInGroup()) {
                    expandableNotificationRow.setClipToActualHeight(false);
                }
            }
        }

        public final boolean shouldFinish(View view) {
            if (view instanceof ExpandableNotificationRow) {
                return !((ExpandableNotificationRow) view).isChildInGroup();
            }
            return false;
        }
    };
    public final int[] mOwnPosition = new int[2];
    public float mTransformationEndY = -1.0f;
    public Interpolator mDefaultInterpolator = Interpolators.FAST_OUT_SLOW_IN;

    public static TransformState createFrom(View view, ViewTransformationHelper viewTransformationHelper) {
        if (view instanceof TextView) {
            TextViewTransformState textViewTransformState = (TextViewTransformState) TextViewTransformState.sInstancePool.acquire();
            if (textViewTransformState == null) {
                textViewTransformState = new TextViewTransformState();
            }
            textViewTransformState.initFrom(view, viewTransformationHelper);
            return textViewTransformState;
        }
        if (view.getId() == 16908786) {
            ActionListTransformState actionListTransformState = (ActionListTransformState) ActionListTransformState.sInstancePool.acquire();
            if (actionListTransformState == null) {
                actionListTransformState = new ActionListTransformState();
            }
            actionListTransformState.initFrom(view, viewTransformationHelper);
            return actionListTransformState;
        }
        if (view.getId() == 16909458) {
            MessagingLayoutTransformState messagingLayoutTransformState = (MessagingLayoutTransformState) MessagingLayoutTransformState.sInstancePool.acquire();
            if (messagingLayoutTransformState == null) {
                messagingLayoutTransformState = new MessagingLayoutTransformState();
            }
            messagingLayoutTransformState.initFrom(view, viewTransformationHelper);
            return messagingLayoutTransformState;
        }
        if (view instanceof MessagingImageMessage) {
            MessagingImageTransformState messagingImageTransformState = (MessagingImageTransformState) MessagingImageTransformState.sInstancePool.acquire();
            if (messagingImageTransformState == null) {
                messagingImageTransformState = new MessagingImageTransformState();
            }
            messagingImageTransformState.initFrom(view, viewTransformationHelper);
            return messagingImageTransformState;
        }
        if (view instanceof ImageView) {
            ImageTransformState imageTransformState = (ImageTransformState) ImageTransformState.sInstancePool.acquire();
            if (imageTransformState == null) {
                imageTransformState = new ImageTransformState();
            }
            imageTransformState.initFrom(view, viewTransformationHelper);
            return imageTransformState;
        }
        if (view instanceof ProgressBar) {
            ProgressTransformState progressTransformState = (ProgressTransformState) ProgressTransformState.sInstancePool.acquire();
            if (progressTransformState == null) {
                progressTransformState = new ProgressTransformState();
            }
            progressTransformState.initFrom(view, viewTransformationHelper);
            return progressTransformState;
        }
        TransformState transformState = (TransformState) sInstancePool.acquire();
        if (transformState == null) {
            transformState = new TransformState();
        }
        transformState.initFrom(view, viewTransformationHelper);
        return transformState;
    }

    public static boolean notAvailableFloatValue(float f) {
        return Float.isNaN(f) || f > Float.MAX_VALUE || f < -3.4028235E38f || f == Float.POSITIVE_INFINITY || f == Float.NEGATIVE_INFINITY;
    }

    public static void setClippingDeactivated(View view, boolean z) {
        ViewClippingUtil.setClippingDeactivated(view, z, CLIPPING_PARAMETERS);
    }

    public final void abortTransformation() {
        View view = this.mTransformedView;
        Float fValueOf = Float.valueOf(-1.0f);
        view.setTag(R.id.transformation_start_x_tag, fValueOf);
        this.mTransformedView.setTag(R.id.transformation_start_y_tag, fValueOf);
        this.mTransformedView.setTag(R.id.transformation_start_scale_x_tag, fValueOf);
        this.mTransformedView.setTag(R.id.transformation_start_scale_y_tag, fValueOf);
    }

    public void appear(float f, TransformableView transformableView) {
        if (f == 0.0f) {
            prepareFadeIn();
        }
        CrossFadeHelper.fadeIn(this.mTransformedView, f, true);
    }

    public void disappear(float f, TransformableView transformableView) {
        CrossFadeHelper.fadeOut(this.mTransformedView, f, true);
    }

    public final void ensureVisible() {
        if (this.mTransformedView.getVisibility() == 4 || this.mTransformedView.getAlpha() != 1.0f) {
            this.mTransformedView.setAlpha(1.0f);
            this.mTransformedView.setVisibility(0);
        }
    }

    public int getContentHeight() {
        return this.mTransformedView.getHeight();
    }

    public int getContentWidth() {
        return this.mTransformedView.getWidth();
    }

    public final int[] getLaidOutLocationOnScreen() {
        int[] locationOnScreen = getLocationOnScreen();
        locationOnScreen[0] = (int) (locationOnScreen[0] - this.mTransformedView.getTranslationX());
        locationOnScreen[1] = (int) (locationOnScreen[1] - this.mTransformedView.getTranslationY());
        return locationOnScreen;
    }

    public final int[] getLocationOnScreen() {
        View view = this.mTransformedView;
        int[] iArr = this.mOwnPosition;
        view.getLocationOnScreen(iArr);
        iArr[0] = (int) (iArr[0] - (this.mTransformedView.getPivotX() * (1.0f - this.mTransformedView.getScaleX())));
        iArr[1] = (int) (iArr[1] - (this.mTransformedView.getPivotY() * (1.0f - this.mTransformedView.getScaleY())));
        iArr[1] = iArr[1] - (MessagingPropertyAnimator.getTop(this.mTransformedView) - MessagingPropertyAnimator.getLayoutTop(this.mTransformedView));
        return iArr;
    }

    public final float getTransformationStartScaleX() {
        Object tag = this.mTransformedView.getTag(R.id.transformation_start_scale_x_tag);
        if (tag == null) {
            return -1.0f;
        }
        return ((Float) tag).floatValue();
    }

    public final float getTransformationStartScaleY() {
        Object tag = this.mTransformedView.getTag(R.id.transformation_start_scale_y_tag);
        if (tag == null) {
            return -1.0f;
        }
        return ((Float) tag).floatValue();
    }

    public final float getTransformationStartX() {
        Object tag = this.mTransformedView.getTag(R.id.transformation_start_x_tag);
        if (tag == null) {
            return -1.0f;
        }
        return ((Float) tag).floatValue();
    }

    public final float getTransformationStartY() {
        Object tag = this.mTransformedView.getTag(R.id.transformation_start_y_tag);
        if (tag == null) {
            return -1.0f;
        }
        return ((Float) tag).floatValue();
    }

    public void initFrom(View view, ViewTransformationHelper viewTransformationHelper) {
        this.mTransformedView = view;
        this.mTransformInfo = viewTransformationHelper;
        this.mAlignEnd = Boolean.TRUE.equals(view.getTag(R.id.align_transform_end_tag));
    }

    public void prepareFadeIn() {
        resetTransformedView();
    }

    public void recycle() {
        reset();
        if (getClass() == TransformState.class) {
            sInstancePool.release(this);
        }
    }

    public void reset() {
        this.mTransformedView = null;
        this.mTransformInfo = null;
        this.mSameAsAny = false;
        this.mTransformationEndY = -1.0f;
        this.mAlignEnd = false;
        this.mDefaultInterpolator = Interpolators.FAST_OUT_SLOW_IN;
    }

    public void resetTransformedView() {
        this.mTransformedView.setTranslationX(0.0f);
        this.mTransformedView.setTranslationY(0.0f);
        this.mTransformedView.setScaleX(1.0f);
        this.mTransformedView.setScaleY(1.0f);
        setClippingDeactivated(this.mTransformedView, false);
        abortTransformation();
    }

    public boolean sameAs(TransformState transformState) {
        return this.mSameAsAny;
    }

    public final void setTransformationStartScaleX(float f) {
        this.mTransformedView.setTag(R.id.transformation_start_scale_x_tag, Float.valueOf(f));
    }

    public final void setTransformationStartScaleY(float f) {
        this.mTransformedView.setTag(R.id.transformation_start_scale_y_tag, Float.valueOf(f));
    }

    public void setVisible(boolean z, boolean z2) {
        if (z2 || this.mTransformedView.getVisibility() != 8) {
            if (this.mTransformedView.getVisibility() != 8) {
                this.mTransformedView.setVisibility(z ? 0 : 4);
            }
            this.mTransformedView.animate().cancel();
            this.mTransformedView.setAlpha(z ? 1.0f : 0.0f);
            resetTransformedView();
        }
    }

    public final boolean transformRightEdge(TransformState transformState) {
        boolean z = false;
        boolean z2 = this.mAlignEnd && transformState.mAlignEnd;
        if (this.mTransformedView.isLayoutRtl() && transformState.mTransformedView.isLayoutRtl()) {
            z = true;
        }
        return z2 ^ z;
    }

    public boolean transformScale(TransformState transformState) {
        return sameAs(transformState);
    }

    public void transformViewFrom(TransformState transformState, float f) {
        this.mTransformedView.animate().cancel();
        if (sameAs(transformState)) {
            ensureVisible();
        } else {
            CrossFadeHelper.fadeIn(this.mTransformedView, f, true);
        }
        transformViewFullyFrom(transformState, f);
    }

    public void transformViewFullyFrom(TransformState transformState, float f) {
        transformViewFrom(transformState, 17, null, f);
    }

    public void transformViewFullyTo(TransformState transformState, float f) {
        transformViewTo(transformState, 17, null, f);
    }

    public boolean transformViewTo(TransformState transformState, float f) {
        this.mTransformedView.animate().cancel();
        if (!sameAs(transformState)) {
            CrossFadeHelper.fadeOut(this.mTransformedView, f, true);
            transformViewFullyTo(transformState, f);
            return true;
        }
        if (this.mTransformedView.getVisibility() != 0) {
            return false;
        }
        this.mTransformedView.setAlpha(0.0f);
        this.mTransformedView.setVisibility(4);
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v23 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v8, types: [boolean, int] */
    public void transformViewFrom(TransformState transformState, int i, ViewTransformationHelper.CustomTransformation customTransformation, float f) {
        int[] locationOnScreen;
        boolean z;
        ?? r1;
        Interpolator customInterpolator;
        Interpolator customInterpolator2;
        View view = this.mTransformedView;
        boolean z2 = (i & 1) != 0;
        int contentHeight = getContentHeight();
        int contentHeight2 = transformState.getContentHeight();
        boolean z3 = (contentHeight2 == contentHeight || contentHeight2 == 0 || contentHeight == 0) ? false : true;
        int contentWidth = getContentWidth();
        int contentWidth2 = transformState.getContentWidth();
        boolean z4 = (contentWidth2 == contentWidth || contentWidth2 == 0 || contentWidth == 0) ? false : true;
        boolean z5 = (z3 || z4) && transformScale(transformState);
        boolean zTransformRightEdge = transformRightEdge(transformState);
        float f2 = -1.0f;
        if (f == 0.0f || ((z2 && getTransformationStartX() == -1.0f) || getTransformationStartY() == -1.0f || ((z5 && getTransformationStartScaleX() == -1.0f && z4) || (z5 && getTransformationStartScaleY() == -1.0f && z3)))) {
            if (f != 0.0f) {
                locationOnScreen = transformState.getLaidOutLocationOnScreen();
            } else {
                locationOnScreen = transformState.getLocationOnScreen();
            }
            int[] laidOutLocationOnScreen = getLaidOutLocationOnScreen();
            if (customTransformation == 0 || !customTransformation.initTransformation(this, transformState)) {
                if (!z2) {
                    z = z2;
                } else if (zTransformRightEdge) {
                    z = z2;
                    this.mTransformedView.setTag(R.id.transformation_start_x_tag, Float.valueOf((locationOnScreen[0] + transformState.mTransformedView.getWidth()) - (laidOutLocationOnScreen[0] + view.getWidth())));
                } else {
                    z = z2;
                    this.mTransformedView.setTag(R.id.transformation_start_x_tag, Float.valueOf(locationOnScreen[0] - laidOutLocationOnScreen[0]));
                }
                this.mTransformedView.setTag(R.id.transformation_start_y_tag, Float.valueOf(locationOnScreen[1] - laidOutLocationOnScreen[1]));
                View view2 = transformState.mTransformedView;
                if (z5 && z4) {
                    setTransformationStartScaleX((view2.getScaleX() * contentWidth2) / contentWidth);
                    view.setPivotX(zTransformRightEdge ? view.getWidth() : 0.0f);
                } else {
                    setTransformationStartScaleX(-1.0f);
                }
                if (z5 && z3) {
                    setTransformationStartScaleY((view2.getScaleY() * contentHeight2) / contentHeight);
                    view.setPivotY(0.0f);
                    f2 = -1.0f;
                } else {
                    f2 = -1.0f;
                    setTransformationStartScaleY(-1.0f);
                }
            } else {
                z = z2;
            }
            if (!z) {
                this.mTransformedView.setTag(R.id.transformation_start_x_tag, Float.valueOf(f2));
            }
            if (!z5) {
                setTransformationStartScaleX(f2);
                setTransformationStartScaleY(f2);
            }
            r1 = 1;
            setClippingDeactivated(view, true);
        } else {
            z = z2;
            r1 = 1;
        }
        float interpolation = this.mDefaultInterpolator.getInterpolation(f);
        if (z) {
            view.setTranslationX(NotificationUtils.interpolate(getTransformationStartX(), 0.0f, (customTransformation == 0 || (customInterpolator2 = customTransformation.getCustomInterpolator(r1, r1)) == null) ? interpolation : ((PathInterpolator) customInterpolator2).getInterpolation(f)));
        }
        view.setTranslationY(NotificationUtils.interpolate(getTransformationStartY(), 0.0f, (customTransformation == 0 || (customInterpolator = customTransformation.getCustomInterpolator(16, true)) == null) ? interpolation : ((PathInterpolator) customInterpolator).getInterpolation(f)));
        if (z5) {
            float transformationStartScaleX = getTransformationStartScaleX();
            if (transformationStartScaleX != -1.0f) {
                view.setScaleX(NotificationUtils.interpolate(transformationStartScaleX, 1.0f, interpolation));
            }
            float transformationStartScaleY = getTransformationStartScaleY();
            if (transformationStartScaleY != -1.0f) {
                view.setScaleY(NotificationUtils.interpolate(transformationStartScaleY, 1.0f, interpolation));
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00e0 A[PHI: r6
      0x00e0: PHI (r6v12 float) = (r6v11 float), (r6v15 float) binds: [B:41:0x00c8, B:46:0x00d7] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x010c A[PHI: r5
      0x010c: PHI (r5v3 float) = (r5v2 float), (r5v6 float) binds: [B:51:0x00f2, B:56:0x0103] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Type inference failed for: r18v1 */
    /* JADX WARN: Type inference failed for: r18v2 */
    /* JADX WARN: Type inference failed for: r18v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void transformViewTo(TransformState transformState, int i, ViewTransformationHelper.CustomTransformation customTransformation, float f) {
        boolean z;
        float interpolation;
        float f2;
        float interpolation2;
        boolean z2;
        View view = this.mTransformedView;
        boolean z3 = (i & 1) != 0;
        boolean zTransformScale = transformScale(transformState);
        boolean zTransformRightEdge = transformRightEdge(transformState);
        int contentWidth = getContentWidth();
        int contentWidth2 = transformState.getContentWidth();
        if (f == 0.0f) {
            if (z3) {
                float transformationStartX = getTransformationStartX();
                if (transformationStartX == -1.0f) {
                    transformationStartX = view.getTranslationX();
                }
                z2 = false;
                this.mTransformedView.setTag(R.id.transformation_start_x_tag, Float.valueOf(transformationStartX));
            } else {
                z2 = false;
            }
            float transformationStartY = getTransformationStartY();
            if (transformationStartY == -1.0f) {
                transformationStartY = view.getTranslationY();
            }
            this.mTransformedView.setTag(R.id.transformation_start_y_tag, Float.valueOf(transformationStartY));
            if (zTransformScale && contentWidth2 != contentWidth) {
                setTransformationStartScaleX(view.getScaleX());
                view.setPivotX(zTransformRightEdge ? view.getWidth() : 0.0f);
            } else {
                setTransformationStartScaleX(-1.0f);
            }
            if (zTransformScale && transformState.getContentHeight() != getContentHeight()) {
                setTransformationStartScaleY(view.getScaleY());
                view.setPivotY(0.0f);
            } else {
                setTransformationStartScaleY(-1.0f);
            }
            setClippingDeactivated(view, true);
            z = z2;
        } else {
            z = 0;
        }
        float interpolation3 = this.mDefaultInterpolator.getInterpolation(f);
        int[] laidOutLocationOnScreen = transformState.getLaidOutLocationOnScreen();
        int[] laidOutLocationOnScreen2 = getLaidOutLocationOnScreen();
        if (z3) {
            int width = view.getWidth();
            int width2 = transformState.mTransformedView.getWidth();
            if (zTransformRightEdge) {
                f2 = (laidOutLocationOnScreen[z] + width2) - (laidOutLocationOnScreen2[z] + width);
            } else {
                f2 = laidOutLocationOnScreen[z] - laidOutLocationOnScreen2[z];
            }
            if (customTransformation != null) {
                if (customTransformation.customTransformTarget(this, transformState)) {
                    f2 = -1.0f;
                }
                Interpolator customInterpolator = customTransformation.getCustomInterpolator(1, z);
                if (customInterpolator != null) {
                    interpolation2 = ((PathInterpolator) customInterpolator).getInterpolation(f);
                }
                view.setTranslationX(NotificationUtils.interpolate(getTransformationStartX(), f2, interpolation2));
            } else {
                interpolation2 = interpolation3;
                view.setTranslationX(NotificationUtils.interpolate(getTransformationStartX(), f2, interpolation2));
            }
        }
        float f3 = laidOutLocationOnScreen[1] - laidOutLocationOnScreen2[1];
        if (customTransformation == null) {
            interpolation = interpolation3;
        } else {
            if (customTransformation.customTransformTarget(this, transformState)) {
                f3 = this.mTransformationEndY;
            }
            Interpolator customInterpolator2 = customTransformation.getCustomInterpolator(16, false);
            if (customInterpolator2 != null) {
                interpolation = ((PathInterpolator) customInterpolator2).getInterpolation(f);
            }
        }
        view.setTranslationY(NotificationUtils.interpolate(getTransformationStartY(), f3, interpolation));
        if (zTransformScale) {
            float transformationStartScaleX = getTransformationStartScaleX();
            if (transformationStartScaleX != -1.0f) {
                float fInterpolate = NotificationUtils.interpolate(transformationStartScaleX, contentWidth2 / contentWidth, interpolation3);
                if (!notAvailableFloatValue(fInterpolate)) {
                    view.setScaleX(fInterpolate);
                }
            }
            float transformationStartScaleY = getTransformationStartScaleY();
            if (transformationStartScaleY != -1.0f) {
                float fInterpolate2 = NotificationUtils.interpolate(transformationStartScaleY, transformState.getContentHeight() / getContentHeight(), interpolation3);
                if (notAvailableFloatValue(fInterpolate2)) {
                    return;
                }
                view.setScaleY(fInterpolate2);
            }
        }
    }
}
