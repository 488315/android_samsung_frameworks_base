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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public float mTransformationEndX = -1.0f;
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
        if (view.getId() == 16908752) {
            ActionListTransformState actionListTransformState = (ActionListTransformState) ActionListTransformState.sInstancePool.acquire();
            if (actionListTransformState == null) {
                actionListTransformState = new ActionListTransformState();
            }
            actionListTransformState.initFrom(view, viewTransformationHelper);
            return actionListTransformState;
        }
        if (view.getId() == 16909415) {
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
    /* JADX WARN: Removed duplicated region for block: B:66:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0130  */
    /* JADX WARN: Type inference failed for: r1v10, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r1v25 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void transformViewFrom(com.android.systemui.statusbar.notification.TransformState r22, int r23, com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation r24, float r25) {
        /*
            Method dump skipped, instructions count: 420
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.TransformState.transformViewFrom(com.android.systemui.statusbar.notification.TransformState, int, com.android.systemui.statusbar.ViewTransformationHelper$CustomTransformation, float):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:69:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void transformViewTo(com.android.systemui.statusbar.notification.TransformState r18, int r19, com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation r20, float r21) {
        /*
            Method dump skipped, instructions count: 336
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.TransformState.transformViewTo(com.android.systemui.statusbar.notification.TransformState, int, com.android.systemui.statusbar.ViewTransformationHelper$CustomTransformation, float):void");
    }
}
