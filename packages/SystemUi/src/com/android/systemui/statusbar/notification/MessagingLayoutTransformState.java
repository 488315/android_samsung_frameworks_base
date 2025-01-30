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
import com.android.systemui.statusbar.notification.TransformState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    public final void appear(View view, float f) {
        if (view == null || view.getVisibility() == 8) {
            return;
        }
        TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
        createFrom.appear(f, null);
        createFrom.recycle();
    }

    public final void disappear(View view, float f) {
        if (view == null || view.getVisibility() == 8) {
            return;
        }
        TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
        createFrom.disappear(f, null);
        createFrom.recycle();
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void initFrom(View view, TransformState.TransformInfo transformInfo) {
        super.initFrom(view, transformInfo);
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
                        resetTransformedView(childAt);
                        TransformState.setClippingDeactivated(childAt, false);
                    }
                }
                resetTransformedView(messagingGroup.getAvatar());
                resetTransformedView(messagingGroup.getSenderView());
                MessagingImageMessage isolatedMessage = messagingGroup.getIsolatedMessage();
                if (isolatedMessage != null) {
                    resetTransformedView(isolatedMessage);
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01f7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void transformViewInternal(MessagingLayoutTransformState messagingLayoutTransformState, float f, boolean z) {
        int i;
        float f2;
        float f3;
        float abs;
        int i2;
        List messages;
        int i3;
        View view;
        float f4;
        View view2;
        int i4;
        List list;
        List list2;
        int i5;
        MessagingGroup messagingGroup;
        float translationY;
        ensureVisible();
        ArrayList filterHiddenGroups = filterHiddenGroups(this.mMessagingLayout.getMessagingGroups());
        ArrayList filterHiddenGroups2 = filterHiddenGroups(messagingLayoutTransformState.mMessagingLayout.getMessagingGroups());
        HashMap hashMap = this.mGroupMap;
        hashMap.clear();
        int size = filterHiddenGroups.size() - 1;
        int i6 = Integer.MAX_VALUE;
        while (true) {
            int i7 = 0;
            if (size < 0) {
                break;
            }
            MessagingGroup messagingGroup2 = (MessagingGroup) filterHiddenGroups.get(size);
            MessagingGroup messagingGroup3 = null;
            for (int min = Math.min(filterHiddenGroups2.size(), i6) - 1; min >= 0; min--) {
                MessagingGroup messagingGroup4 = (MessagingGroup) filterHiddenGroups2.get(min);
                int calculateGroupCompatibility = messagingGroup2.calculateGroupCompatibility(messagingGroup4);
                if (calculateGroupCompatibility > i7) {
                    i6 = min;
                    messagingGroup3 = messagingGroup4;
                    i7 = calculateGroupCompatibility;
                }
            }
            if (messagingGroup3 != null) {
                hashMap.put(messagingGroup2, messagingGroup3);
            }
            size--;
        }
        int i8 = 1;
        int size2 = filterHiddenGroups.size() - 1;
        float f5 = 0.0f;
        MessagingGroup messagingGroup5 = null;
        while (size2 >= 0) {
            MessagingGroup messagingGroup6 = (MessagingGroup) filterHiddenGroups.get(size2);
            MessagingGroup messagingGroup7 = (MessagingGroup) hashMap.get(messagingGroup6);
            if (isGone(messagingGroup6)) {
                i = i8;
            } else {
                if (messagingGroup7 != null) {
                    if (messagingGroup7.getIsolatedMessage() == null) {
                        ValueAnimator valueAnimator = ((ViewTransformationHelper) this.mTransformInfo).mViewTransformationAnimation;
                        if (((valueAnimator == null || !valueAnimator.isRunning()) ? 0 : i8) == 0) {
                            i2 = i8;
                            TextView senderView = messagingGroup6.getSenderView();
                            TextView senderView2 = messagingGroup7.getSenderView();
                            Layout layout = senderView.getLayout();
                            int i9 = (layout != null || layout.getEllipsisCount(layout.getLineCount() - i8) <= 0) ? 0 : i8;
                            Layout layout2 = senderView2.getLayout();
                            float f6 = 1.0f;
                            MessagingGroup messagingGroup8 = messagingGroup6;
                            boolean z2 = i2;
                            transformView(f, z, senderView, senderView2, (i9 == ((layout2 != null || layout2.getEllipsisCount(layout2.getLineCount() - i8) <= 0) ? 0 : i8) ? i8 : 0) ^ 1, z2);
                            int transformView = transformView(f, z, messagingGroup8.getAvatar(), messagingGroup7.getAvatar(), true, z2);
                            messages = messagingGroup8.getMessages();
                            List messages2 = messagingGroup7.getMessages();
                            i3 = 0;
                            int i10 = i8;
                            float f7 = 0.0f;
                            while (i3 < messages.size()) {
                                View view3 = ((MessagingMessage) messages.get((messages.size() - i8) - i3)).getView();
                                if (isGone(view3)) {
                                    i4 = i3;
                                    list = messages2;
                                    list2 = messages;
                                    i5 = transformView;
                                    messagingGroup = messagingGroup8;
                                } else {
                                    int size3 = (messages2.size() - i8) - i3;
                                    if (size3 >= 0) {
                                        View view4 = ((MessagingMessage) messages2.get(size3)).getView();
                                        if (!isGone(view4)) {
                                            view = view4;
                                            if (view == null || f7 >= 0.0f) {
                                                f4 = f;
                                            } else {
                                                float max = Math.max(0.0f, Math.min(f6, ((view3.getHeight() + view3.getTop()) + f7) / view3.getHeight()));
                                                if (z) {
                                                    max = f6 - max;
                                                }
                                                f4 = max;
                                            }
                                            view2 = view;
                                            i4 = i3;
                                            list = messages2;
                                            list2 = messages;
                                            i5 = transformView;
                                            int transformView2 = transformView(f4, z, view3, view2, false, i2);
                                            boolean z3 = messagingGroup7.getIsolatedMessage() != view2;
                                            if (f4 == 0.0f || !(z3 || messagingGroup7.isSingleLine())) {
                                                messagingGroup = messagingGroup8;
                                            } else {
                                                messagingGroup = messagingGroup8;
                                                messagingGroup.setClippingDisabled(false);
                                                this.mMessagingLayout.setMessagingClippingDisabled(false);
                                            }
                                            if (view2 != null) {
                                                if (i10 != 0) {
                                                    f7 = senderView.getTranslationY();
                                                }
                                                translationY = f7;
                                                view3.setTranslationY(translationY);
                                                TransformState.setClippingDeactivated(view3, true);
                                            } else {
                                                if (messagingGroup.getIsolatedMessage() != view3 && !z3) {
                                                    if (z) {
                                                        f7 = view2.getTranslationY() - transformView2;
                                                    } else {
                                                        translationY = view3.getTranslationY();
                                                    }
                                                }
                                                i10 = 0;
                                            }
                                            f7 = translationY;
                                            i10 = 0;
                                        }
                                    }
                                    view = null;
                                    if (view == null) {
                                    }
                                    f4 = f;
                                    view2 = view;
                                    i4 = i3;
                                    list = messages2;
                                    list2 = messages;
                                    i5 = transformView;
                                    int transformView22 = transformView(f4, z, view3, view2, false, i2);
                                    if (messagingGroup7.getIsolatedMessage() != view2) {
                                    }
                                    if (f4 == 0.0f) {
                                    }
                                    messagingGroup = messagingGroup8;
                                    if (view2 != null) {
                                    }
                                    f7 = translationY;
                                    i10 = 0;
                                }
                                messagingGroup8 = messagingGroup;
                                transformView = i5;
                                messages2 = list;
                                messages = list2;
                                f6 = 1.0f;
                                i8 = 1;
                                i3 = i4 + 1;
                            }
                            int i11 = transformView;
                            MessagingGroup messagingGroup9 = messagingGroup8;
                            messagingGroup9.updateClipRect();
                            if (messagingGroup5 == null) {
                                f5 = z ? messagingGroup7.getAvatar().getTranslationY() - i11 : messagingGroup9.getAvatar().getTranslationY();
                                messagingGroup5 = messagingGroup9;
                            }
                        }
                    }
                    i2 = 0;
                    TextView senderView3 = messagingGroup6.getSenderView();
                    TextView senderView22 = messagingGroup7.getSenderView();
                    Layout layout3 = senderView3.getLayout();
                    if (layout3 != null) {
                    }
                    Layout layout22 = senderView22.getLayout();
                    float f62 = 1.0f;
                    MessagingGroup messagingGroup82 = messagingGroup6;
                    boolean z22 = i2;
                    transformView(f, z, senderView3, senderView22, (i9 == ((layout22 != null || layout22.getEllipsisCount(layout22.getLineCount() - i8) <= 0) ? 0 : i8) ? i8 : 0) ^ 1, z22);
                    int transformView3 = transformView(f, z, messagingGroup82.getAvatar(), messagingGroup7.getAvatar(), true, z22);
                    messages = messagingGroup82.getMessages();
                    List messages22 = messagingGroup7.getMessages();
                    i3 = 0;
                    int i102 = i8;
                    float f72 = 0.0f;
                    while (i3 < messages.size()) {
                    }
                    int i112 = transformView3;
                    MessagingGroup messagingGroup92 = messagingGroup82;
                    messagingGroup92.updateClipRect();
                    if (messagingGroup5 == null) {
                    }
                } else {
                    if (messagingGroup5 != null) {
                        float f8 = z ? this.mRelativeTranslationOffset * f : this.mRelativeTranslationOffset * (1.0f - f);
                        if (messagingGroup6.getSenderView().getVisibility() != 8) {
                            f8 *= 0.5f;
                        }
                        messagingGroup6.getMessageContainer().setTranslationY(f8);
                        messagingGroup6.getSenderView().setTranslationY(f8);
                        messagingGroup6.setTranslationY(0.9f * f5);
                        float top = messagingGroup6.getTop() + f5;
                        ValueAnimator valueAnimator2 = ((ViewTransformationHelper) this.mTransformInfo).mViewTransformationAnimation;
                        if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                            float f9 = (-messagingGroup6.getHeight()) * 0.75f;
                            f3 = top - f9;
                            abs = Math.abs(f9) + messagingGroup6.getTop();
                        } else {
                            float f10 = (-messagingGroup6.getHeight()) * 0.5f;
                            f3 = top - f10;
                            abs = Math.abs(f10);
                        }
                        f2 = Math.max(0.0f, Math.min(1.0f, f3 / abs));
                        if (z) {
                            f2 = 1.0f - f2;
                        }
                    } else {
                        f2 = f;
                    }
                    if (z) {
                        MessagingLinearLayout messageContainer = messagingGroup6.getMessageContainer();
                        for (int i12 = 0; i12 < messageContainer.getChildCount(); i12++) {
                            View childAt = messageContainer.getChildAt(i12);
                            if (!isGone(childAt)) {
                                disappear(childAt, f2);
                                TransformState.setClippingDeactivated(childAt, true);
                            }
                        }
                        disappear(messagingGroup6.getAvatar(), f2);
                        disappear(messagingGroup6.getSenderView(), f2);
                        disappear(messagingGroup6.getIsolatedMessage(), f2);
                        TransformState.setClippingDeactivated(messagingGroup6.getSenderView(), true);
                        TransformState.setClippingDeactivated(messagingGroup6.getAvatar(), true);
                    } else {
                        MessagingLinearLayout messageContainer2 = messagingGroup6.getMessageContainer();
                        for (int i13 = 0; i13 < messageContainer2.getChildCount(); i13++) {
                            View childAt2 = messageContainer2.getChildAt(i13);
                            if (!isGone(childAt2)) {
                                appear(childAt2, f2);
                                TransformState.setClippingDeactivated(childAt2, true);
                            }
                        }
                        appear(messagingGroup6.getAvatar(), f2);
                        appear(messagingGroup6.getSenderView(), f2);
                        appear(messagingGroup6.getIsolatedMessage(), f2);
                        i = 1;
                        TransformState.setClippingDeactivated(messagingGroup6.getSenderView(), true);
                        TransformState.setClippingDeactivated(messagingGroup6.getAvatar(), true);
                    }
                }
                i = 1;
            }
            size2--;
            i8 = i;
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
        TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
        createFrom.setVisible(z, z2);
        createFrom.recycle();
    }

    public final void resetTransformedView(View view) {
        TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
        createFrom.resetTransformedView();
        createFrom.recycle();
    }
}
