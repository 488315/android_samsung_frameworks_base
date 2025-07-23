package com.android.systemui.statusbar.notification;

import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.android.internal.widget.ConversationLayout;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingLayout;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.FlatteningSequence;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AnimatedImageNotificationManager {
    public final BindEventManager bindEventManager;
    public final HeadsUpManager headsUpManager;
    public boolean isStatusBarExpanded;
    public final CommonNotifCollection notifCollection;
    public final StatusBarStateController statusBarStateController;

    public AnimatedImageNotificationManager(CommonNotifCollection commonNotifCollection, BindEventManager bindEventManager, HeadsUpManager headsUpManager, StatusBarStateController statusBarStateController) {
        this.notifCollection = commonNotifCollection;
        this.bindEventManager = bindEventManager;
        this.headsUpManager = headsUpManager;
        this.statusBarStateController = statusBarStateController;
    }

    public static final void access$updateAnimatedImageDrawables(AnimatedImageNotificationManager animatedImageNotificationManager, NotificationEntry notificationEntry) {
        Sequence sequence;
        final int i = 1;
        final int i2 = 0;
        animatedImageNotificationManager.getClass();
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        if (expandableNotificationRow != null) {
            boolean z = expandableNotificationRow.mIsHeadsUp || animatedImageNotificationManager.isStatusBarExpanded;
            NotificationContentView[] notificationContentViewArr = expandableNotificationRow.mLayouts;
            NotificationContentView[] notificationContentViewArr2 = (NotificationContentView[]) Arrays.copyOf(notificationContentViewArr, notificationContentViewArr.length);
            if (notificationContentViewArr2 == null || (sequence = ArraysKt___ArraysKt.asSequence(notificationContentViewArr2)) == null) {
                sequence = EmptySequence.INSTANCE;
            }
            FlatteningSequence flatMap = SequencesKt___SequencesKt.flatMap(SequencesKt___SequencesKt.flatMap(sequence, new Function1() { // from class: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    ArrayList messagingGroups;
                    ArrayList messagingGroups2;
                    switch (i2) {
                        case 0:
                            return ArraysKt___ArraysKt.asSequence(((NotificationContentView) obj).getAllViews());
                        case 1:
                            ConversationLayout conversationLayout = (View) obj;
                            ConversationLayout conversationLayout2 = conversationLayout instanceof ConversationLayout ? conversationLayout : null;
                            if (conversationLayout2 != null && (messagingGroups2 = conversationLayout2.getMessagingGroups()) != null) {
                                return new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(messagingGroups2);
                            }
                            MessagingLayout messagingLayout = conversationLayout instanceof MessagingLayout ? (MessagingLayout) conversationLayout : null;
                            return (messagingLayout == null || (messagingGroups = messagingLayout.getMessagingGroups()) == null) ? EmptySequence.INSTANCE : new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(messagingGroups);
                        case 2:
                            return ConvenienceExtensionsKt.getChildren(((MessagingGroup) obj).getMessageContainer());
                        default:
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
                }
            }), new Function1() { // from class: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    ArrayList messagingGroups;
                    ArrayList messagingGroups2;
                    switch (i) {
                        case 0:
                            return ArraysKt___ArraysKt.asSequence(((NotificationContentView) obj).getAllViews());
                        case 1:
                            ConversationLayout conversationLayout = (View) obj;
                            ConversationLayout conversationLayout2 = conversationLayout instanceof ConversationLayout ? conversationLayout : null;
                            if (conversationLayout2 != null && (messagingGroups2 = conversationLayout2.getMessagingGroups()) != null) {
                                return new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(messagingGroups2);
                            }
                            MessagingLayout messagingLayout = conversationLayout instanceof MessagingLayout ? (MessagingLayout) conversationLayout : null;
                            return (messagingLayout == null || (messagingGroups = messagingLayout.getMessagingGroups()) == null) ? EmptySequence.INSTANCE : new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(messagingGroups);
                        case 2:
                            return ConvenienceExtensionsKt.getChildren(((MessagingGroup) obj).getMessageContainer());
                        default:
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
                }
            });
            final int i3 = 2;
            FlatteningSequence flatMap2 = SequencesKt___SequencesKt.flatMap(flatMap, new Function1() { // from class: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    ArrayList messagingGroups;
                    ArrayList messagingGroups2;
                    switch (i3) {
                        case 0:
                            return ArraysKt___ArraysKt.asSequence(((NotificationContentView) obj).getAllViews());
                        case 1:
                            ConversationLayout conversationLayout = (View) obj;
                            ConversationLayout conversationLayout2 = conversationLayout instanceof ConversationLayout ? conversationLayout : null;
                            if (conversationLayout2 != null && (messagingGroups2 = conversationLayout2.getMessagingGroups()) != null) {
                                return new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(messagingGroups2);
                            }
                            MessagingLayout messagingLayout = conversationLayout instanceof MessagingLayout ? (MessagingLayout) conversationLayout : null;
                            return (messagingLayout == null || (messagingGroups = messagingLayout.getMessagingGroups()) == null) ? EmptySequence.INSTANCE : new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(messagingGroups);
                        case 2:
                            return ConvenienceExtensionsKt.getChildren(((MessagingGroup) obj).getMessageContainer());
                        default:
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
                }
            });
            final int i4 = 3;
            FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.mapNotNull(flatMap2, new Function1() { // from class: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    ArrayList messagingGroups;
                    ArrayList messagingGroups2;
                    switch (i4) {
                        case 0:
                            return ArraysKt___ArraysKt.asSequence(((NotificationContentView) obj).getAllViews());
                        case 1:
                            ConversationLayout conversationLayout = (View) obj;
                            ConversationLayout conversationLayout2 = conversationLayout instanceof ConversationLayout ? conversationLayout : null;
                            if (conversationLayout2 != null && (messagingGroups2 = conversationLayout2.getMessagingGroups()) != null) {
                                return new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(messagingGroups2);
                            }
                            MessagingLayout messagingLayout = conversationLayout instanceof MessagingLayout ? (MessagingLayout) conversationLayout : null;
                            return (messagingLayout == null || (messagingGroups = messagingLayout.getMessagingGroups()) == null) ? EmptySequence.INSTANCE : new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(messagingGroups);
                        case 2:
                            return ConvenienceExtensionsKt.getChildren(((MessagingGroup) obj).getMessageContainer());
                        default:
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
                }
            }));
            while (filteringSequence$iterator$1.hasNext()) {
                AnimatedImageDrawable animatedImageDrawable = (AnimatedImageDrawable) filteringSequence$iterator$1.next();
                if (z) {
                    animatedImageDrawable.start();
                } else {
                    animatedImageDrawable.stop();
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }
}
