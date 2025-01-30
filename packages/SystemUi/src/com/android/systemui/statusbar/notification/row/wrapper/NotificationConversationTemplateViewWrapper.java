package com.android.systemui.statusbar.notification.row.wrapper;

import android.content.Context;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.ConversationLayout;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.systemui.R;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationMessagingTemplateViewWrapper;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptySet;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationConversationTemplateViewWrapper extends NotificationTemplateViewWrapper {
    public View appName;
    public View conversationBadgeBg;
    public View conversationIconContainer;
    public CachingIconView conversationIconView;
    public final ConversationLayout conversationLayout;
    public View conversationTitleView;
    public View expandBtn;
    public View expandBtnContainer;
    public View expandIcon;
    public View facePileBottom;
    public View facePileBottomBg;
    public View facePileTop;
    public ViewGroup imageMessageContainer;
    public View importanceRing;
    public ArrayList messageContainers;
    public MessagingLinearLayout messagingLinearLayout;
    public final int minHeightWithActions;
    public View overflowNumber;

    public NotificationConversationTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
        this.minHeightWithActions = NotificationUtils.getFontScaledHeight(R.dimen.notification_messaging_actions_min_height, context);
        this.conversationLayout = (ConversationLayout) view;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final int getMinLayoutHeight() {
        View view = this.mActionsContainer;
        if (view == null || view.getVisibility() == 8) {
            return 0;
        }
        return this.minHeightWithActions;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final View getShelfTransformationTarget() {
        if (!this.conversationLayout.isImportantConversation()) {
            return this.mIcon;
        }
        CachingIconView cachingIconView = this.conversationIconView;
        if (cachingIconView == null) {
            cachingIconView = null;
        }
        if (cachingIconView.getVisibility() == 8) {
            return this.mIcon;
        }
        CachingIconView cachingIconView2 = this.conversationIconView;
        if (cachingIconView2 == null) {
            return null;
        }
        return cachingIconView2;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        ConversationLayout conversationLayout = this.conversationLayout;
        this.messagingLinearLayout = conversationLayout.getMessagingLinearLayout();
        this.imageMessageContainer = conversationLayout.getImageMessageContainer();
        this.messageContainers = conversationLayout.getMessagingGroups();
        this.conversationIconContainer = conversationLayout.requireViewById(android.R.id.current_scene);
        this.conversationIconView = conversationLayout.requireViewById(android.R.id.costsMoney);
        this.conversationBadgeBg = conversationLayout.requireViewById(android.R.id.crossfade);
        this.expandBtn = conversationLayout.requireViewById(android.R.id.feedbackAudible);
        this.expandBtnContainer = conversationLayout.requireViewById(android.R.id.feedbackSpoken);
        this.importanceRing = conversationLayout.requireViewById(android.R.id.crosshair);
        this.appName = conversationLayout.requireViewById(android.R.id.audio);
        this.conversationTitleView = conversationLayout.requireViewById(android.R.id.cycle);
        this.facePileTop = conversationLayout.findViewById(android.R.id.conversation_image_message_container);
        this.facePileBottom = conversationLayout.findViewById(android.R.id.conversation_icon_badge_ring);
        this.facePileBottomBg = conversationLayout.findViewById(android.R.id.conversation_icon_container);
        this.expandIcon = conversationLayout.requireViewById(android.R.id.feedbackVisual);
        this.overflowNumber = conversationLayout.requireViewById(android.R.id.ffwd);
        super.onContentUpdated(expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void setAnimationsRunning(boolean z) {
        ArrayList arrayList = this.messageContainers;
        if (arrayList == null) {
            arrayList = null;
        }
        TransformingSequence transformingSequence = new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(arrayList), new Function1() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationConversationTemplateViewWrapper$setAnimationsRunning$containers$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ((MessagingGroup) obj).getMessageContainer();
            }
        });
        ViewGroup[] viewGroupArr = new ViewGroup[1];
        ViewGroup viewGroup = this.imageMessageContainer;
        viewGroupArr[0] = viewGroup != null ? viewGroup : null;
        FilteringSequence mapNotNull = SequencesKt___SequencesKt.mapNotNull(SequencesKt___SequencesKt.flatMap(SequencesKt___SequencesKt.plus(transformingSequence, SequencesKt__SequencesKt.sequenceOf(viewGroupArr)), new Function1() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationConversationTemplateViewWrapper$setAnimationsRunning$drawables$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ConvenienceExtensionsKt.getChildren((ViewGroup) obj);
            }
        }), new Function1() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationConversationTemplateViewWrapper$setAnimationsRunning$drawables$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                MessagingImageMessage messagingImageMessage = (View) obj;
                MessagingImageMessage messagingImageMessage2 = messagingImageMessage instanceof MessagingImageMessage ? messagingImageMessage : null;
                if (messagingImageMessage2 == null) {
                    return null;
                }
                Drawable drawable = messagingImageMessage2.getDrawable();
                if (drawable instanceof AnimatedImageDrawable) {
                    return (AnimatedImageDrawable) drawable;
                }
                return null;
            }
        });
        Set<AnimatedImageDrawable> linkedHashSet = new LinkedHashSet();
        Iterator it = mapNotNull.iterator();
        while (true) {
            FilteringSequence$iterator$1 filteringSequence$iterator$1 = (FilteringSequence$iterator$1) it;
            if (!filteringSequence$iterator$1.hasNext()) {
                break;
            } else {
                linkedHashSet.add(filteringSequence$iterator$1.next());
            }
        }
        int size = linkedHashSet.size();
        if (size == 0) {
            linkedHashSet = EmptySet.INSTANCE;
        } else if (size == 1) {
            linkedHashSet = Collections.singleton(linkedHashSet.iterator().next());
        }
        for (AnimatedImageDrawable animatedImageDrawable : linkedHashSet) {
            if (z) {
                animatedImageDrawable.start();
            } else if (!z) {
                animatedImageDrawable.stop();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void setNotificationFaded(boolean z) {
        View view = this.expandBtn;
        if (view == null) {
            view = null;
        }
        NotificationFadeAware.setLayerTypeForFaded(view, z);
        View view2 = this.conversationIconContainer;
        NotificationFadeAware.setLayerTypeForFaded(view2 != null ? view2 : null, z);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void setRemoteInputVisible(boolean z) {
        this.conversationLayout.showHistoricMessages(z);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void updateExpandability(boolean z, View.OnClickListener onClickListener, boolean z2) {
        this.conversationLayout.updateExpandability(z, onClickListener);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper
    public final void updateTransformedTypes() {
        super.updateTransformedTypes();
        View view = this.conversationTitleView;
        if (view == null) {
            view = null;
        }
        ViewTransformationHelper viewTransformationHelper = this.mTransformationHelper;
        viewTransformationHelper.addTransformedView(view, 1);
        View[] viewArr = new View[2];
        MessagingLinearLayout messagingLinearLayout = this.messagingLinearLayout;
        if (messagingLinearLayout == null) {
            messagingLinearLayout = null;
        }
        viewArr[0] = messagingLinearLayout;
        View view2 = this.appName;
        if (view2 == null) {
            view2 = null;
        }
        viewArr[1] = view2;
        addTransformedViews(viewArr);
        ViewGroup viewGroup = this.imageMessageContainer;
        if (viewGroup == null) {
            viewGroup = null;
        }
        if (viewGroup != null) {
            viewTransformationHelper.setCustomTransformation(new NotificationMessagingTemplateViewWrapper.C29071(), viewGroup.getId());
        }
        View view3 = this.overflowNumber;
        if (view3 == null) {
            view3 = null;
        }
        viewTransformationHelper.addTransformedView(view3, 7);
        View view4 = this.expandIcon;
        if (view4 == null) {
            view4 = null;
        }
        viewTransformationHelper.addTransformedView(view4, 6);
        View[] viewArr2 = new View[6];
        CachingIconView cachingIconView = this.conversationIconView;
        if (cachingIconView == null) {
            cachingIconView = null;
        }
        viewArr2[0] = cachingIconView;
        View view5 = this.conversationBadgeBg;
        if (view5 == null) {
            view5 = null;
        }
        viewArr2[1] = view5;
        View view6 = this.importanceRing;
        viewArr2[2] = view6 != null ? view6 : null;
        viewArr2[3] = this.facePileTop;
        viewArr2[4] = this.facePileBottom;
        viewArr2[5] = this.facePileBottomBg;
        addViewsTransformingToSimilar(viewArr2);
    }
}
