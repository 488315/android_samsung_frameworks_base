package com.android.systemui.statusbar.notification;

import android.animation.ValueAnimator;
import android.text.Layout;
import android.util.Pools;
import android.view.View;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.internal.widget.IMessagingLayout;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.internal.widget.MessagingMessage;
import com.android.internal.widget.MessagingPropertyAnimator;
import com.android.systemui.statusbar.ViewTransformationHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes3.dex */
public class MessagingLayoutTransformState extends TransformState {
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
        TransformState transformStateCreateFrom = TransformState.createFrom(view, this.mTransformInfo);
        transformStateCreateFrom.appear(f, null);
        transformStateCreateFrom.recycle();
    }

    public final void disappear(float f, View view) {
        if (view == null || view.getVisibility() == 8) {
            return;
        }
        TransformState transformStateCreateFrom = TransformState.createFrom(view, this.mTransformInfo);
        transformStateCreateFrom.disappear(f, null);
        transformStateCreateFrom.recycle();
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
                        TransformState transformStateCreateFrom = TransformState.createFrom(childAt, this.mTransformInfo);
                        transformStateCreateFrom.resetTransformedView();
                        transformStateCreateFrom.recycle();
                        TransformState.setClippingDeactivated(childAt, false);
                    }
                }
                TransformState transformStateCreateFrom2 = TransformState.createFrom(messagingGroup.getAvatar(), this.mTransformInfo);
                transformStateCreateFrom2.resetTransformedView();
                transformStateCreateFrom2.recycle();
                TransformState transformStateCreateFrom3 = TransformState.createFrom(messagingGroup.getSenderView(), this.mTransformInfo);
                transformStateCreateFrom3.resetTransformedView();
                transformStateCreateFrom3.recycle();
                MessagingImageMessage isolatedMessage = messagingGroup.getIsolatedMessage();
                if (isolatedMessage != null) {
                    TransformState transformStateCreateFrom4 = TransformState.createFrom(isolatedMessage, this.mTransformInfo);
                    transformStateCreateFrom4.resetTransformedView();
                    transformStateCreateFrom4.recycle();
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
        this.mMessagingLayout.setMessagingClippingDisabled(true);
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
        TransformState transformStateCreateFrom = TransformState.createFrom(view, this.mTransformInfo);
        if (z3) {
            transformStateCreateFrom.mDefaultInterpolator = Interpolators.LINEAR;
        }
        int i = 0;
        transformStateCreateFrom.mSameAsAny = z2 && !isGone(view2);
        if (z) {
            if (view2 != null) {
                TransformState transformStateCreateFrom2 = TransformState.createFrom(view2, this.mTransformInfo);
                if (isGone(view2)) {
                    if (!isGone(view)) {
                        transformStateCreateFrom.disappear(f, null);
                    }
                    transformStateCreateFrom.transformViewTo(transformStateCreateFrom2, 16, null, f);
                } else {
                    transformStateCreateFrom.transformViewTo(transformStateCreateFrom2, f);
                }
                i = transformStateCreateFrom.getLaidOutLocationOnScreen()[1] - transformStateCreateFrom2.getLaidOutLocationOnScreen()[1];
                transformStateCreateFrom2.recycle();
            } else {
                transformStateCreateFrom.disappear(f, null);
            }
        } else if (view2 != null) {
            TransformState transformStateCreateFrom3 = TransformState.createFrom(view2, this.mTransformInfo);
            if (isGone(view2)) {
                if (!isGone(view)) {
                    transformStateCreateFrom.appear(f, null);
                }
                transformStateCreateFrom.transformViewFrom(transformStateCreateFrom3, 16, null, f);
            } else {
                transformStateCreateFrom.transformViewFrom(transformStateCreateFrom3, f);
            }
            i = transformStateCreateFrom.getLaidOutLocationOnScreen()[1] - transformStateCreateFrom3.getLaidOutLocationOnScreen()[1];
            transformStateCreateFrom3.recycle();
        } else {
            transformStateCreateFrom.appear(f, null);
        }
        transformStateCreateFrom.recycle();
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

    /* JADX WARN: Removed duplicated region for block: B:52:0x0139  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void transformViewInternal(MessagingLayoutTransformState messagingLayoutTransformState, float f, boolean z) {
        boolean z2;
        float fMax;
        float f2;
        float fAbs;
        List list;
        int i;
        View view;
        View view2;
        float f3;
        int i2;
        List list2;
        List list3;
        float f4;
        boolean z3;
        ValueAnimator valueAnimator;
        MessagingLayoutTransformState messagingLayoutTransformState2 = this;
        messagingLayoutTransformState2.ensureVisible();
        ArrayList arrayListFilterHiddenGroups = filterHiddenGroups(messagingLayoutTransformState2.mMessagingLayout.getMessagingGroups());
        ArrayList arrayListFilterHiddenGroups2 = filterHiddenGroups(messagingLayoutTransformState.mMessagingLayout.getMessagingGroups());
        messagingLayoutTransformState2.mGroupMap.clear();
        boolean z4 = true;
        int i3 = Integer.MAX_VALUE;
        for (int size = arrayListFilterHiddenGroups.size() - 1; size >= 0; size--) {
            MessagingGroup messagingGroup = (MessagingGroup) arrayListFilterHiddenGroups.get(size);
            int i4 = 0;
            MessagingGroup messagingGroup2 = null;
            for (int iMin = Math.min(arrayListFilterHiddenGroups2.size(), i3) - 1; iMin >= 0; iMin--) {
                MessagingGroup messagingGroup3 = (MessagingGroup) arrayListFilterHiddenGroups2.get(iMin);
                int iCalculateGroupCompatibility = messagingGroup.calculateGroupCompatibility(messagingGroup3);
                if (iCalculateGroupCompatibility > i4) {
                    i3 = iMin;
                    messagingGroup2 = messagingGroup3;
                    i4 = iCalculateGroupCompatibility;
                }
            }
            if (messagingGroup2 != null) {
                messagingLayoutTransformState2.mGroupMap.put(messagingGroup, messagingGroup2);
            }
        }
        HashMap map = messagingLayoutTransformState2.mGroupMap;
        float f5 = 0.0f;
        int size2 = arrayListFilterHiddenGroups.size() - 1;
        float translationY = 0.0f;
        MessagingGroup messagingGroup4 = null;
        while (size2 >= 0) {
            MessagingGroup messagingGroup5 = (MessagingGroup) arrayListFilterHiddenGroups.get(size2);
            MessagingGroup messagingGroup6 = (MessagingGroup) map.get(messagingGroup5);
            if (isGone(messagingGroup5)) {
                z2 = z4;
            } else if (messagingGroup6 != null) {
                boolean z5 = (messagingGroup6.getIsolatedMessage() != null || ((valueAnimator = messagingLayoutTransformState2.mTransformInfo.mViewTransformationAnimation) != null && valueAnimator.isRunning())) ? false : z4;
                TextView senderView = messagingGroup5.getSenderView();
                TextView senderView2 = messagingGroup6.getSenderView();
                Layout layout = senderView.getLayout();
                boolean z6 = (layout == null || layout.getEllipsisCount(layout.getLineCount() + (-1)) <= 0) ? false : z4;
                Layout layout2 = senderView2.getLayout();
                boolean z7 = z6 != ((layout2 == null || layout2.getEllipsisCount(layout2.getLineCount() + (-1)) <= 0) ? false : z4) ? z4 : false;
                boolean z8 = z4;
                float f6 = 1.0f;
                messagingLayoutTransformState2.transformView(f, z, senderView, senderView2, !z7, z5);
                int iTransformView = transformView(f, z, messagingGroup5.getAvatar(), messagingGroup6.getAvatar(), true, z5);
                List messages = messagingGroup5.getMessages();
                List messages2 = messagingGroup6.getMessages();
                boolean z9 = z8;
                float translationY2 = f5;
                int i5 = 0;
                while (i5 < messages.size()) {
                    View view3 = ((MessagingMessage) messages.get((messages.size() - 1) - i5)).getView();
                    if (isGone(view3)) {
                        list3 = messages;
                        list2 = messages2;
                        i2 = i5;
                        i = iTransformView;
                        f4 = f6;
                    } else {
                        int size3 = (messages2.size() - 1) - i5;
                        if (size3 >= 0) {
                            View view4 = ((MessagingMessage) messages2.get(size3)).getView();
                            if (isGone(view4)) {
                                view4 = null;
                            }
                            if (view4 != null || translationY2 >= f5) {
                                list = messages2;
                                i = iTransformView;
                                view = view3;
                                view2 = view4;
                                f3 = f;
                            } else {
                                float fMax2 = Math.max(0.0f, Math.min(f6, ((view3.getHeight() + view3.getTop()) + translationY2) / view3.getHeight()));
                                if (z) {
                                    fMax2 = f6 - fMax2;
                                }
                                float f7 = fMax2;
                                list = messages2;
                                f3 = f7;
                                i = iTransformView;
                                view = view3;
                                view2 = view4;
                            }
                            i2 = i5;
                            list2 = list;
                            list3 = messages;
                            int iTransformView2 = transformView(f3, z, view, view2, false, z5);
                            boolean z10 = messagingGroup6.getIsolatedMessage() == view2 ? z8 : false;
                            if (f3 == 0.0f && (z10 || messagingGroup6.isSingleLine())) {
                                z3 = false;
                                messagingGroup5.setClippingDisabled(false);
                                f4 = f6;
                                this.mMessagingLayout.setMessagingClippingDisabled(false);
                            } else {
                                f4 = f6;
                                z3 = false;
                            }
                            if (view2 == null) {
                                if (z9) {
                                    translationY2 = senderView.getTranslationY();
                                }
                                float f8 = translationY2;
                                view.setTranslationY(f8);
                                TransformState.setClippingDeactivated(view, z8);
                                translationY2 = f8;
                            } else if (messagingGroup5.getIsolatedMessage() != view && !z10) {
                                translationY2 = z ? view2.getTranslationY() - iTransformView2 : view.getTranslationY();
                            }
                            z9 = z3;
                        }
                    }
                    i5 = i2 + 1;
                    messages = list3;
                    iTransformView = i;
                    messages2 = list2;
                    f6 = f4;
                    z8 = true;
                    f5 = 0.0f;
                }
                messagingLayoutTransformState2 = this;
                int i6 = iTransformView;
                messagingGroup5.updateClipRect();
                if (messagingGroup4 == null) {
                    translationY = z ? messagingGroup6.getAvatar().getTranslationY() - i6 : messagingGroup5.getAvatar().getTranslationY();
                    messagingGroup4 = messagingGroup5;
                }
                z2 = true;
                f5 = 0.0f;
            } else {
                if (messagingGroup4 != null) {
                    float f9 = z ? messagingLayoutTransformState2.mRelativeTranslationOffset * f : (1.0f - f) * messagingLayoutTransformState2.mRelativeTranslationOffset;
                    if (messagingGroup5.getSenderView().getVisibility() != 8) {
                        f9 *= 0.5f;
                    }
                    messagingGroup5.getMessageContainer().setTranslationY(f9);
                    messagingGroup5.getSenderView().setTranslationY(f9);
                    messagingGroup5.setTranslationY(0.9f * translationY);
                    float top = messagingGroup5.getTop() + translationY;
                    ValueAnimator valueAnimator2 = messagingLayoutTransformState2.mTransformInfo.mViewTransformationAnimation;
                    if (valueAnimator2 == null || !valueAnimator2.isRunning()) {
                        float f10 = (-messagingGroup5.getHeight()) * 0.5f;
                        f2 = top - f10;
                        fAbs = Math.abs(f10);
                    } else {
                        float f11 = (-messagingGroup5.getHeight()) * 0.75f;
                        f2 = top - f11;
                        fAbs = Math.abs(f11) + messagingGroup5.getTop();
                    }
                    f5 = 0.0f;
                    fMax = Math.max(0.0f, Math.min(1.0f, f2 / fAbs));
                    if (z) {
                        fMax = 1.0f - fMax;
                    }
                } else {
                    f5 = 0.0f;
                    fMax = f;
                }
                if (z) {
                    MessagingLinearLayout messageContainer = messagingGroup5.getMessageContainer();
                    for (int i7 = 0; i7 < messageContainer.getChildCount(); i7++) {
                        View childAt = messageContainer.getChildAt(i7);
                        if (!isGone(childAt)) {
                            messagingLayoutTransformState2.disappear(fMax, childAt);
                            TransformState.setClippingDeactivated(childAt, true);
                        }
                    }
                    messagingLayoutTransformState2.disappear(fMax, messagingGroup5.getAvatar());
                    messagingLayoutTransformState2.disappear(fMax, messagingGroup5.getSenderView());
                    messagingLayoutTransformState2.disappear(fMax, messagingGroup5.getIsolatedMessage());
                    TransformState.setClippingDeactivated(messagingGroup5.getSenderView(), true);
                    TransformState.setClippingDeactivated(messagingGroup5.getAvatar(), true);
                    z2 = true;
                } else {
                    MessagingLinearLayout messageContainer2 = messagingGroup5.getMessageContainer();
                    for (int i8 = 0; i8 < messageContainer2.getChildCount(); i8++) {
                        View childAt2 = messageContainer2.getChildAt(i8);
                        if (!isGone(childAt2)) {
                            messagingLayoutTransformState2.appear(fMax, childAt2);
                            TransformState.setClippingDeactivated(childAt2, true);
                        }
                    }
                    messagingLayoutTransformState2.appear(fMax, messagingGroup5.getAvatar());
                    messagingLayoutTransformState2.appear(fMax, messagingGroup5.getSenderView());
                    messagingLayoutTransformState2.appear(fMax, messagingGroup5.getIsolatedMessage());
                    z2 = true;
                    TransformState.setClippingDeactivated(messagingGroup5.getSenderView(), true);
                    TransformState.setClippingDeactivated(messagingGroup5.getAvatar(), true);
                }
            }
            size2--;
            z4 = z2;
        }
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
        TransformState transformStateCreateFrom = TransformState.createFrom(view, this.mTransformInfo);
        transformStateCreateFrom.setVisible(z, z2);
        transformStateCreateFrom.recycle();
    }
}
