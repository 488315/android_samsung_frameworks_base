package com.android.systemui.statusbar.notification;

import android.util.Pools;
import android.view.View;
import android.view.animation.Interpolator;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TransformState {
    public boolean mAlignEnd;
    public boolean mSameAsAny;
    public TransformInfo mTransformInfo;
    public View mTransformedView;
    public static final Pools.SimplePool sInstancePool = new Pools.SimplePool(40);
    public static final C27891 CLIPPING_PARAMETERS = new ViewClippingUtil.ClippingParameters() { // from class: com.android.systemui.statusbar.notification.TransformState.1
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
    public float mTransformationEndX = -1.0f;
    public Interpolator mDefaultInterpolator = Interpolators.FAST_OUT_SLOW_IN;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface TransformInfo {
    }

    public static TransformState createFrom(View view, TransformInfo transformInfo) {
        if (view instanceof TextView) {
            TextViewTransformState textViewTransformState = (TextViewTransformState) TextViewTransformState.sInstancePool.acquire();
            if (textViewTransformState == null) {
                textViewTransformState = new TextViewTransformState();
            }
            textViewTransformState.initFrom(view, transformInfo);
            return textViewTransformState;
        }
        if (view.getId() == 16908751) {
            ActionListTransformState actionListTransformState = (ActionListTransformState) ActionListTransformState.sInstancePool.acquire();
            if (actionListTransformState == null) {
                actionListTransformState = new ActionListTransformState();
            }
            actionListTransformState.initFrom(view, transformInfo);
            return actionListTransformState;
        }
        if (view.getId() == 16909410) {
            MessagingLayoutTransformState messagingLayoutTransformState = (MessagingLayoutTransformState) MessagingLayoutTransformState.sInstancePool.acquire();
            if (messagingLayoutTransformState == null) {
                messagingLayoutTransformState = new MessagingLayoutTransformState();
            }
            messagingLayoutTransformState.initFrom(view, transformInfo);
            return messagingLayoutTransformState;
        }
        if (view instanceof MessagingImageMessage) {
            MessagingImageTransformState messagingImageTransformState = (MessagingImageTransformState) MessagingImageTransformState.sInstancePool.acquire();
            if (messagingImageTransformState == null) {
                messagingImageTransformState = new MessagingImageTransformState();
            }
            messagingImageTransformState.initFrom(view, transformInfo);
            return messagingImageTransformState;
        }
        if (view instanceof ImageView) {
            ImageTransformState imageTransformState = (ImageTransformState) ImageTransformState.sInstancePool.acquire();
            if (imageTransformState == null) {
                imageTransformState = new ImageTransformState();
            }
            imageTransformState.initFrom(view, transformInfo);
            return imageTransformState;
        }
        if (view instanceof ProgressBar) {
            ProgressTransformState progressTransformState = (ProgressTransformState) ProgressTransformState.sInstancePool.acquire();
            if (progressTransformState == null) {
                progressTransformState = new ProgressTransformState();
            }
            progressTransformState.initFrom(view, transformInfo);
            return progressTransformState;
        }
        TransformState transformState = (TransformState) sInstancePool.acquire();
        if (transformState == null) {
            transformState = new TransformState();
        }
        transformState.initFrom(view, transformInfo);
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
        Float valueOf = Float.valueOf(-1.0f);
        view.setTag(R.id.transformation_start_x_tag, valueOf);
        this.mTransformedView.setTag(R.id.transformation_start_y_tag, valueOf);
        this.mTransformedView.setTag(R.id.transformation_start_scale_x_tag, valueOf);
        this.mTransformedView.setTag(R.id.transformation_start_scale_y_tag, valueOf);
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

    public void initFrom(View view, TransformInfo transformInfo) {
        this.mTransformedView = view;
        this.mTransformInfo = transformInfo;
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
        this.mTransformationEndX = -1.0f;
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

    public final void setTransformationStartX(float f) {
        this.mTransformedView.setTag(R.id.transformation_start_x_tag, Float.valueOf(f));
    }

    public final void setTransformationStartY(float f) {
        this.mTransformedView.setTag(R.id.transformation_start_y_tag, Float.valueOf(f));
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
        return (this.mAlignEnd && transformState.mAlignEnd) ^ (this.mTransformedView.isLayoutRtl() && transformState.mTransformedView.isLayoutRtl());
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

    public void transformViewFrom(TransformState transformState, int i, ViewTransformationHelper.CustomTransformation customTransformation, float f) {
        int[] locationOnScreen;
        float f2;
        Interpolator customInterpolator;
        Interpolator customInterpolator2;
        View view = this.mTransformedView;
        boolean z = (i & 1) != 0;
        boolean z2 = (i & 16) != 0;
        int contentHeight = getContentHeight();
        int contentHeight2 = transformState.getContentHeight();
        boolean z3 = (contentHeight2 == contentHeight || contentHeight2 == 0 || contentHeight == 0) ? false : true;
        int contentWidth = getContentWidth();
        int contentWidth2 = transformState.getContentWidth();
        boolean z4 = (contentWidth2 == contentWidth || contentWidth2 == 0 || contentWidth == 0) ? false : true;
        boolean z5 = (z3 || z4) && transformScale(transformState);
        boolean transformRightEdge = transformRightEdge(transformState);
        if (f == 0.0f || ((z && getTransformationStartX() == -1.0f) || ((z2 && getTransformationStartY() == -1.0f) || ((z5 && getTransformationStartScaleX() == -1.0f && z4) || (z5 && getTransformationStartScaleY() == -1.0f && z3))))) {
            if (f != 0.0f) {
                locationOnScreen = transformState.getLaidOutLocationOnScreen();
            } else {
                locationOnScreen = transformState.getLocationOnScreen();
            }
            int[] laidOutLocationOnScreen = getLaidOutLocationOnScreen();
            if (customTransformation == null || !customTransformation.initTransformation(this, transformState)) {
                if (z) {
                    if (transformRightEdge) {
                        setTransformationStartX((locationOnScreen[0] + transformState.mTransformedView.getWidth()) - (laidOutLocationOnScreen[0] + view.getWidth()));
                    } else {
                        setTransformationStartX(locationOnScreen[0] - laidOutLocationOnScreen[0]);
                    }
                }
                if (z2) {
                    setTransformationStartY(locationOnScreen[1] - laidOutLocationOnScreen[1]);
                }
                View view2 = transformState.mTransformedView;
                if (z5 && z4) {
                    setTransformationStartScaleX((view2.getScaleX() * contentWidth2) / contentWidth);
                    view.setPivotX(transformRightEdge ? view.getWidth() : 0.0f);
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
                f2 = -1.0f;
            }
            if (!z) {
                setTransformationStartX(f2);
            }
            if (!z2) {
                setTransformationStartY(f2);
            }
            if (!z5) {
                setTransformationStartScaleX(f2);
                setTransformationStartScaleY(f2);
            }
            setClippingDeactivated(view, true);
        }
        float interpolation = this.mDefaultInterpolator.getInterpolation(f);
        if (z) {
            view.setTranslationX(NotificationUtils.interpolate(getTransformationStartX(), 0.0f, (customTransformation == null || (customInterpolator2 = customTransformation.getCustomInterpolator(1, true)) == null) ? interpolation : customInterpolator2.getInterpolation(f)));
        }
        if (z2) {
            view.setTranslationY(NotificationUtils.interpolate(getTransformationStartY(), 0.0f, (customTransformation == null || (customInterpolator = customTransformation.getCustomInterpolator(16, true)) == null) ? interpolation : customInterpolator.getInterpolation(f)));
        }
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

    public final void transformViewTo(TransformState transformState, int i, ViewTransformationHelper.CustomTransformation customTransformation, float f) {
        float f2;
        boolean z;
        int i2;
        float f3;
        View view = this.mTransformedView;
        boolean z2 = (i & 1) != 0;
        boolean z3 = (i & 16) != 0;
        boolean transformScale = transformScale(transformState);
        boolean transformRightEdge = transformRightEdge(transformState);
        int contentWidth = getContentWidth();
        int contentWidth2 = transformState.getContentWidth();
        if (f == 0.0f) {
            if (z2) {
                float transformationStartX = getTransformationStartX();
                if (transformationStartX == -1.0f) {
                    transformationStartX = view.getTranslationX();
                }
                setTransformationStartX(transformationStartX);
            }
            if (z3) {
                float transformationStartY = getTransformationStartY();
                if (transformationStartY == -1.0f) {
                    transformationStartY = view.getTranslationY();
                }
                setTransformationStartY(transformationStartY);
            }
            if (transformScale && contentWidth2 != contentWidth) {
                setTransformationStartScaleX(view.getScaleX());
                view.setPivotX(transformRightEdge ? view.getWidth() : 0.0f);
            } else {
                setTransformationStartScaleX(-1.0f);
            }
            if (transformScale && transformState.getContentHeight() != getContentHeight()) {
                setTransformationStartScaleY(view.getScaleY());
                view.setPivotY(0.0f);
            } else {
                setTransformationStartScaleY(-1.0f);
            }
            setClippingDeactivated(view, true);
        }
        float interpolation = this.mDefaultInterpolator.getInterpolation(f);
        int[] laidOutLocationOnScreen = transformState.getLaidOutLocationOnScreen();
        int[] laidOutLocationOnScreen2 = getLaidOutLocationOnScreen();
        if (z2) {
            int width = view.getWidth();
            int width2 = transformState.mTransformedView.getWidth();
            if (transformRightEdge) {
                z = false;
                i2 = (laidOutLocationOnScreen[0] + width2) - (laidOutLocationOnScreen2[0] + width);
            } else {
                z = false;
                i2 = laidOutLocationOnScreen[0] - laidOutLocationOnScreen2[0];
            }
            float f4 = i2;
            if (customTransformation != null) {
                if (customTransformation.customTransformTarget(this, transformState)) {
                    f4 = this.mTransformationEndX;
                }
                Interpolator customInterpolator = customTransformation.getCustomInterpolator(1, z);
                if (customInterpolator != null) {
                    f3 = customInterpolator.getInterpolation(f);
                    view.setTranslationX(NotificationUtils.interpolate(getTransformationStartX(), f4, f3));
                }
            }
            f3 = interpolation;
            view.setTranslationX(NotificationUtils.interpolate(getTransformationStartX(), f4, f3));
        }
        if (z3) {
            float f5 = laidOutLocationOnScreen[1] - laidOutLocationOnScreen2[1];
            if (customTransformation != null) {
                if (customTransformation.customTransformTarget(this, transformState)) {
                    f5 = this.mTransformationEndY;
                }
                Interpolator customInterpolator2 = customTransformation.getCustomInterpolator(16, false);
                if (customInterpolator2 != null) {
                    f2 = customInterpolator2.getInterpolation(f);
                    view.setTranslationY(NotificationUtils.interpolate(getTransformationStartY(), f5, f2));
                }
            }
            f2 = interpolation;
            view.setTranslationY(NotificationUtils.interpolate(getTransformationStartY(), f5, f2));
        }
        if (transformScale) {
            float transformationStartScaleX = getTransformationStartScaleX();
            if (transformationStartScaleX != -1.0f) {
                float interpolate = NotificationUtils.interpolate(transformationStartScaleX, contentWidth2 / contentWidth, interpolation);
                if (!notAvailableFloatValue(interpolate)) {
                    view.setScaleX(interpolate);
                }
            }
            float transformationStartScaleY = getTransformationStartScaleY();
            if (transformationStartScaleY != -1.0f) {
                float interpolate2 = NotificationUtils.interpolate(transformationStartScaleY, transformState.getContentHeight() / getContentHeight(), interpolation);
                if (notAvailableFloatValue(interpolate2)) {
                    return;
                }
                view.setScaleY(interpolate2);
            }
        }
    }
}
