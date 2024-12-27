package com.android.systemui.statusbar.notification.row.wrapper;

import android.content.Context;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.ConversationLayout;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.internal.widget.MessagingMessage;
import com.android.systemui.R;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationMessagingTemplateViewWrapper;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final int isEllipsis() {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        ArrayList arrayList = this.messageContainers;
        if (arrayList == null) {
            arrayList = null;
        }
        arrayList.stream().filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationConversationTemplateViewWrapper$isEllipsis$1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((MessagingGroup) obj) != null;
            }
        }).forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationConversationTemplateViewWrapper$isEllipsis$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MessagingGroup messagingGroup = (MessagingGroup) obj;
                int size = messagingGroup.getMessages().size() - 1;
                MessagingMessage messagingMessage = (MessagingMessage) messagingGroup.getMessages().get(size);
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(size, messagingGroup.getMessages().size(), "conversationTextView ", " ::::: size :  ", "Summarize");
                if (messagingMessage.getView() instanceof TextView) {
                    TextView textView = (TextView) messagingMessage.getView();
                    if (textView.getLayout() == null) {
                        atomicInteger.set(0);
                        Log.d("Summarize", "conversationTextView temp.layout is null");
                    } else if (textView.getLayout().getEllipsisCount(textView.getLayout().getLineCount() - 1) > 0) {
                        atomicInteger.set(1);
                    } else {
                        atomicInteger.set(2);
                    }
                }
                int size2 = messagingGroup.getMessages().size();
                for (int i = 0; i < size2; i++) {
                    MessagingMessage messagingMessage2 = (MessagingMessage) messagingGroup.getMessages().get(i);
                    if (messagingMessage2.getView() instanceof TextView) {
                        TextView textView2 = (TextView) messagingMessage2.getView();
                        if (textView2.getLayout() != null) {
                            RecyclerView$$ExternalSyntheticOutline0.m(textView2.getLayout().getLineCount(), "Summarize", RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, textView2.getLayout().getEllipsisCount(textView2.getLayout().getLineCount() - 1), " conversationTextView message ", " Ellipsis? ", " lineCount "));
                        }
                    }
                }
            }
        });
        return atomicInteger.get();
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        resolveViews$3();
        super.onContentUpdated(expandableNotificationRow);
    }

    public final void resolveViews$3() {
        this.messagingLinearLayout = this.conversationLayout.getMessagingLinearLayout();
        this.imageMessageContainer = this.conversationLayout.getImageMessageContainer();
        this.messageContainers = this.conversationLayout.getMessagingGroups();
        ConversationLayout conversationLayout = this.conversationLayout;
        this.conversationIconContainer = conversationLayout.requireViewById(android.R.id.date_picker_day_picker);
        this.conversationIconView = conversationLayout.requireViewById(android.R.id.dangerous);
        this.conversationBadgeBg = conversationLayout.requireViewById(android.R.id.date);
        this.expandBtn = conversationLayout.requireViewById(android.R.id.fill);
        this.expandBtnContainer = conversationLayout.requireViewById(android.R.id.fill_parent);
        this.importanceRing = conversationLayout.requireViewById(android.R.id.datePicker);
        this.appName = conversationLayout.requireViewById(android.R.id.authtoken_type);
        this.conversationTitleView = conversationLayout.requireViewById(android.R.id.date_picker_header_date);
        this.facePileTop = conversationLayout.findViewById(android.R.id.customPanel);
        this.facePileBottom = conversationLayout.findViewById(android.R.id.crosshair);
        this.facePileBottomBg = conversationLayout.findViewById(android.R.id.current_scene);
        this.expandIcon = conversationLayout.requireViewById(android.R.id.fill_vertical);
        this.overflowNumber = conversationLayout.requireViewById(android.R.id.find);
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
        for (AnimatedImageDrawable animatedImageDrawable : SequencesKt___SequencesKt.toSet(SequencesKt___SequencesKt.mapNotNull(SequencesKt___SequencesKt.flatMap(SequencesKt__SequencesKt.flatten(SequencesKt__SequencesKt.sequenceOf(transformingSequence, SequencesKt__SequencesKt.sequenceOf(viewGroupArr))), new Function1() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationConversationTemplateViewWrapper$setAnimationsRunning$drawables$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ViewGroup viewGroup2 = (ViewGroup) obj;
                Intrinsics.checkNotNull(viewGroup2);
                return ConvenienceExtensionsKt.getChildren(viewGroup2);
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
    public final void updateExpandability(boolean z, View.OnClickListener onClickListener, boolean z2) {
        this.conversationLayout.updateExpandability(z, onClickListener);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void updateSummarize(ExpandableNotificationRow expandableNotificationRow) {
        resolveViews$3();
        super.onContentUpdated(expandableNotificationRow);
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
