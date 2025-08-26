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
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.FlatteningSequence;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;

/* loaded from: classes3.dex */
public final class NotificationConversationTemplateViewWrapper extends NotificationTemplateViewWrapper {
    public static final /* synthetic */ int $r8$clinit = 0;
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
        this.messagingLinearLayout = this.conversationLayout.getMessagingLinearLayout();
        this.imageMessageContainer = this.conversationLayout.getImageMessageContainer();
        this.messageContainers = this.conversationLayout.getMessagingGroups();
        ConversationLayout conversationLayout = this.conversationLayout;
        this.conversationIconContainer = conversationLayout.requireViewById(android.R.id.eight);
        this.conversationIconView = conversationLayout.requireViewById(android.R.id.dvorak);
        this.conversationBadgeBg = conversationLayout.requireViewById(android.R.id.editable);
        this.expandBtn = conversationLayout.requireViewById(android.R.id.flagRetrieveInteractiveWindows);
        this.expandBtnContainer = conversationLayout.findViewById(android.R.id.floatType);
        this.importanceRing = conversationLayout.requireViewById(android.R.id.edittext_container);
        this.appName = conversationLayout.requireViewById(android.R.id.beforeDescendants);
        this.conversationTitleView = conversationLayout.requireViewById(android.R.id.end);
        this.facePileTop = conversationLayout.findViewById(android.R.id.drag);
        this.facePileBottom = conversationLayout.findViewById(android.R.id.divider);
        this.facePileBottomBg = conversationLayout.findViewById(android.R.id.dpad);
        this.expandIcon = conversationLayout.requireViewById(android.R.id.floating);
        this.overflowNumber = conversationLayout.requireViewById(android.R.id.floating_popup_container);
        super.onContentUpdated(expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void setAnimationsRunning(boolean z) {
        ArrayList arrayList = this.messageContainers;
        if (arrayList == null) {
            arrayList = null;
        }
        final int i = 0;
        TransformingSequence transformingSequence = new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(arrayList), new Function1() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationConversationTemplateViewWrapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                switch (i) {
                    case 0:
                        int i2 = NotificationConversationTemplateViewWrapper.$r8$clinit;
                        return ((MessagingGroup) obj).getMessageContainer();
                    case 1:
                        ViewGroup viewGroup = (ViewGroup) obj;
                        int i3 = NotificationConversationTemplateViewWrapper.$r8$clinit;
                        viewGroup.getClass();
                        return ConvenienceExtensionsKt.getChildren(viewGroup);
                    default:
                        MessagingImageMessage messagingImageMessage = (View) obj;
                        int i4 = NotificationConversationTemplateViewWrapper.$r8$clinit;
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
            }
        });
        ViewGroup viewGroup = this.imageMessageContainer;
        viewGroup.getClass();
        final int i2 = 1;
        FlatteningSequence flatteningSequenceFlatMap = SequencesKt___SequencesKt.flatMap(SequencesKt__SequencesKt.flatten(ArraysKt___ArraysKt.asSequence(new Sequence[]{transformingSequence, ArraysKt___ArraysKt.asSequence(new ViewGroup[]{viewGroup})})), new Function1() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationConversationTemplateViewWrapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                switch (i2) {
                    case 0:
                        int i22 = NotificationConversationTemplateViewWrapper.$r8$clinit;
                        return ((MessagingGroup) obj).getMessageContainer();
                    case 1:
                        ViewGroup viewGroup2 = (ViewGroup) obj;
                        int i3 = NotificationConversationTemplateViewWrapper.$r8$clinit;
                        viewGroup2.getClass();
                        return ConvenienceExtensionsKt.getChildren(viewGroup2);
                    default:
                        MessagingImageMessage messagingImageMessage = (View) obj;
                        int i4 = NotificationConversationTemplateViewWrapper.$r8$clinit;
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
            }
        });
        final int i3 = 2;
        for (AnimatedImageDrawable animatedImageDrawable : SequencesKt___SequencesKt.toSet(SequencesKt___SequencesKt.mapNotNull(flatteningSequenceFlatMap, new Function1() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationConversationTemplateViewWrapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                switch (i3) {
                    case 0:
                        int i22 = NotificationConversationTemplateViewWrapper.$r8$clinit;
                        return ((MessagingGroup) obj).getMessageContainer();
                    case 1:
                        ViewGroup viewGroup2 = (ViewGroup) obj;
                        int i32 = NotificationConversationTemplateViewWrapper.$r8$clinit;
                        viewGroup2.getClass();
                        return ConvenienceExtensionsKt.getChildren(viewGroup2);
                    default:
                        MessagingImageMessage messagingImageMessage = (View) obj;
                        int i4 = NotificationConversationTemplateViewWrapper.$r8$clinit;
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
            }
        }))) {
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
    public final void updateExpandability(boolean z, ExpandableNotificationRow.AnonymousClass1 anonymousClass1, boolean z2) {
        this.conversationLayout.updateExpandability(z, anonymousClass1);
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
        if (viewGroup != null) {
            viewTransformationHelper.setCustomTransformation(new NotificationMessagingTemplateViewWrapper.AnonymousClass1(), viewGroup.getId());
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
