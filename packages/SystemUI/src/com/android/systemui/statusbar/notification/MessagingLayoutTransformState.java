package com.android.systemui.statusbar.notification;

import android.util.Pools;
import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.internal.widget.IMessagingLayout;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.internal.widget.MessagingPropertyAnimator;
import com.android.systemui.statusbar.ViewTransformationHelper;
import java.util.ArrayList;
import java.util.HashMap;

public final class MessagingLayoutTransformState extends TransformState {
    public static final Pools.SimplePool sInstancePool = new Pools.SimplePool(40);
    public final HashMap mGroupMap = new HashMap();
    public IMessagingLayout mMessagingLayout;
    public float mRelativeTranslationOffset;

    public static ArrayList filterHiddenGroups(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList(arrayList);
        int i = 0;
        while (i < arrayList2.size()) {
            if (isGone((MessagingGroup) arrayList2.get(i))) {
                arrayList2.remove(i);
                i--;
            }
            i++;
        }
        return arrayList2;
    }

    public static boolean isGone(View view) {
        if (view == null || view.getVisibility() == 8 || view.getParent() == null || view.getWidth() == 0) {
            return true;
        }
        MessagingLinearLayout.LayoutParams layoutParams = view.getLayoutParams();
        return (layoutParams instanceof MessagingLinearLayout.LayoutParams) && layoutParams.hide;
    }

    public final void appear(float f, View view) {
        if (view == null || view.getVisibility() == 8) {
            return;
        }
        TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
        createFrom.appear(f, null);
        createFrom.recycle();
    }

    public final void disappear(float f, View view) {
        if (view == null || view.getVisibility() == 8) {
            return;
        }
        TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
        createFrom.disappear(f, null);
        createFrom.recycle();
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void initFrom(View view, ViewTransformationHelper viewTransformationHelper) {
        super.initFrom(view, viewTransformationHelper);
        MessagingLinearLayout messagingLinearLayout = this.mTransformedView;
        if (messagingLinearLayout instanceof MessagingLinearLayout) {
            this.mMessagingLayout = messagingLinearLayout.getMessagingLayout();
            this.mRelativeTranslationOffset = view.getContext().getResources().getDisplayMetrics().density * 8.0f;
        }
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void prepareFadeIn() {
        resetTransformedView();
        setVisible(true, false);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void recycle() {
        super.recycle();
        this.mGroupMap.clear();
        sInstancePool.release(this);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void reset() {
        super.reset();
        this.mMessagingLayout = null;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void resetTransformedView() {
        super.resetTransformedView();
        ArrayList messagingGroups = this.mMessagingLayout.getMessagingGroups();
        for (int i = 0; i < messagingGroups.size(); i++) {
            MessagingGroup messagingGroup = (MessagingGroup) messagingGroups.get(i);
            if (!isGone(messagingGroup)) {
                MessagingLinearLayout messageContainer = messagingGroup.getMessageContainer();
                for (int i2 = 0; i2 < messageContainer.getChildCount(); i2++) {
                    View childAt = messageContainer.getChildAt(i2);
                    if (!isGone(childAt)) {
                        TransformState createFrom = TransformState.createFrom(childAt, this.mTransformInfo);
                        createFrom.resetTransformedView();
                        createFrom.recycle();
                        TransformState.setClippingDeactivated(childAt, false);
                    }
                }
                TransformState createFrom2 = TransformState.createFrom(messagingGroup.getAvatar(), this.mTransformInfo);
                createFrom2.resetTransformedView();
                createFrom2.recycle();
                TransformState createFrom3 = TransformState.createFrom(messagingGroup.getSenderView(), this.mTransformInfo);
                createFrom3.resetTransformedView();
                createFrom3.recycle();
                MessagingImageMessage isolatedMessage = messagingGroup.getIsolatedMessage();
                if (isolatedMessage != null) {
                    TransformState createFrom4 = TransformState.createFrom(isolatedMessage, this.mTransformInfo);
                    createFrom4.resetTransformedView();
                    createFrom4.recycle();
                }
                TransformState.setClippingDeactivated(messagingGroup.getAvatar(), false);
                TransformState.setClippingDeactivated(messagingGroup.getSenderView(), false);
                messagingGroup.setTranslationY(0.0f);
                messagingGroup.getMessageContainer().setTranslationY(0.0f);
                messagingGroup.getSenderView().setTranslationY(0.0f);
            }
            messagingGroup.setClippingDisabled(false);
            messagingGroup.updateClipRect();
        }
        this.mMessagingLayout.setMessagingClippingDisabled(false);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void setVisible(boolean z, boolean z2) {
        super.setVisible(z, z2);
        resetTransformedView();
        ArrayList messagingGroups = this.mMessagingLayout.getMessagingGroups();
        for (int i = 0; i < messagingGroups.size(); i++) {
            MessagingGroup messagingGroup = (MessagingGroup) messagingGroups.get(i);
            if (!isGone(messagingGroup)) {
                MessagingLinearLayout messageContainer = messagingGroup.getMessageContainer();
                for (int i2 = 0; i2 < messageContainer.getChildCount(); i2++) {
                    setVisible(messageContainer.getChildAt(i2), z, z2);
                }
                setVisible(messagingGroup.getAvatar(), z, z2);
                setVisible(messagingGroup.getSenderView(), z, z2);
                MessagingImageMessage isolatedMessage = messagingGroup.getIsolatedMessage();
                if (isolatedMessage != null) {
                    setVisible(isolatedMessage, z, z2);
                }
            }
        }
    }

    public final int transformView(float f, boolean z, View view, View view2, boolean z2, boolean z3) {
        TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
        if (z3) {
            createFrom.mDefaultInterpolator = Interpolators.LINEAR;
        }
        int i = 0;
        createFrom.mSameAsAny = z2 && !isGone(view2);
        if (z) {
            if (view2 != null) {
                TransformState createFrom2 = TransformState.createFrom(view2, this.mTransformInfo);
                if (isGone(view2)) {
                    if (!isGone(view)) {
                        createFrom.disappear(f, null);
                    }
                    createFrom.transformViewTo(createFrom2, 16, null, f);
                } else {
                    createFrom.transformViewTo(createFrom2, f);
                }
                i = createFrom.getLaidOutLocationOnScreen()[1] - createFrom2.getLaidOutLocationOnScreen()[1];
                createFrom2.recycle();
            } else {
                createFrom.disappear(f, null);
            }
        } else if (view2 != null) {
            TransformState createFrom3 = TransformState.createFrom(view2, this.mTransformInfo);
            if (isGone(view2)) {
                if (!isGone(view)) {
                    createFrom.appear(f, null);
                }
                createFrom.transformViewFrom(createFrom3, 16, null, f);
            } else {
                createFrom.transformViewFrom(createFrom3, f);
            }
            i = createFrom.getLaidOutLocationOnScreen()[1] - createFrom3.getLaidOutLocationOnScreen()[1];
            createFrom3.recycle();
        } else {
            createFrom.appear(f, null);
        }
        createFrom.recycle();
        return i;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void transformViewFrom(TransformState transformState, float f) {
        if (transformState instanceof MessagingLayoutTransformState) {
            transformViewInternal((MessagingLayoutTransformState) transformState, f, false);
        } else {
            super.transformViewFrom(transformState, f);
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void transformViewInternal(com.android.systemui.statusbar.notification.MessagingLayoutTransformState r31, float r32, boolean r33) {
        /*
            Method dump skipped, instructions count: 798
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.MessagingLayoutTransformState.transformViewInternal(com.android.systemui.statusbar.notification.MessagingLayoutTransformState, float, boolean):void");
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final boolean transformViewTo(TransformState transformState, float f) {
        if (!(transformState instanceof MessagingLayoutTransformState)) {
            return super.transformViewTo(transformState, f);
        }
        transformViewInternal((MessagingLayoutTransformState) transformState, f, true);
        return true;
    }

    public final void setVisible(View view, boolean z, boolean z2) {
        if (isGone(view) || MessagingPropertyAnimator.isAnimatingAlpha(view)) {
            return;
        }
        TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
        createFrom.setVisible(z, z2);
        createFrom.recycle();
    }
}
