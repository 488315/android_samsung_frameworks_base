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
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        animatedImageNotificationManager.getClass();
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        if (expandableNotificationRow != null) {
            boolean z = expandableNotificationRow.mIsHeadsUp || animatedImageNotificationManager.isStatusBarExpanded;
            NotificationContentView[] notificationContentViewArr = expandableNotificationRow.mLayouts;
            NotificationContentView[] notificationContentViewArr2 = (NotificationContentView[]) Arrays.copyOf(notificationContentViewArr, notificationContentViewArr.length);
            if (notificationContentViewArr2 == null || (sequence = ArraysKt___ArraysKt.asSequence(notificationContentViewArr2)) == null) {
                sequence = EmptySequence.INSTANCE;
            }
            FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.mapNotNull(SequencesKt___SequencesKt.flatMap(SequencesKt___SequencesKt.flatMap(SequencesKt___SequencesKt.flatMap(sequence, new Function1() { // from class: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$updateAnimatedImageDrawables$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    NotificationContentView notificationContentView = (NotificationContentView) obj;
                    return ArraysKt___ArraysKt.asSequence(new View[]{notificationContentView.mContractedChild, notificationContentView.mHeadsUpChild, notificationContentView.mExpandedChild, notificationContentView.mSingleLineView});
                }
            }), new Function1() { // from class: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$updateAnimatedImageDrawables$3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    ArrayList messagingGroups;
                    ArrayList messagingGroups2;
                    ConversationLayout conversationLayout = (View) obj;
                    ConversationLayout conversationLayout2 = conversationLayout instanceof ConversationLayout ? conversationLayout : null;
                    if (conversationLayout2 != null && (messagingGroups2 = conversationLayout2.getMessagingGroups()) != null) {
                        return new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(messagingGroups2);
                    }
                    MessagingLayout messagingLayout = conversationLayout instanceof MessagingLayout ? (MessagingLayout) conversationLayout : null;
                    return (messagingLayout == null || (messagingGroups = messagingLayout.getMessagingGroups()) == null) ? EmptySequence.INSTANCE : new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(messagingGroups);
                }
            }), new Function1() { // from class: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$updateAnimatedImageDrawables$4
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ConvenienceExtensionsKt.getChildren(((MessagingGroup) obj).getMessageContainer());
                }
            }), new Function1() { // from class: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$updateAnimatedImageDrawables$5
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
