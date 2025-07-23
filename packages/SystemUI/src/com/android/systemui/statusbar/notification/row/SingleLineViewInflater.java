package com.android.systemui.statusbar.notification.row;

import android.app.Notification;
import android.app.Person;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Trace;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.widget.MessagingMessage;
import com.android.internal.widget.PeopleHelper;
import com.android.systemui.R;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.ConversationAvatar;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.ConversationData;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.FacePile;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.SingleIcon;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.SingleLineViewModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SingleLineViewInflater {
    public static final SingleLineViewInflater INSTANCE = new SingleLineViewInflater();
    public static final PeopleHelper peopleHelper = new PeopleHelper();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ConversationTextData {
        public final CharSequence conversationText;
        public final CharSequence conversationTitle;
        public final CharSequence senderName;

        public ConversationTextData(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
            this.conversationTitle = charSequence;
            this.conversationText = charSequence2;
            this.senderName = charSequence3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConversationTextData)) {
                return false;
            }
            ConversationTextData conversationTextData = (ConversationTextData) obj;
            return Intrinsics.areEqual(this.conversationTitle, conversationTextData.conversationTitle) && Intrinsics.areEqual(this.conversationText, conversationTextData.conversationText) && Intrinsics.areEqual(this.senderName, conversationTextData.senderName);
        }

        public final int hashCode() {
            int hashCode = this.conversationTitle.hashCode() * 31;
            CharSequence charSequence = this.conversationText;
            int hashCode2 = (hashCode + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
            CharSequence charSequence2 = this.senderName;
            return hashCode2 + (charSequence2 != null ? charSequence2.hashCode() : 0);
        }

        public final String toString() {
            return "ConversationTextData(conversationTitle=" + ((Object) this.conversationTitle) + ", conversationText=" + ((Object) this.conversationText) + ", senderName=" + ((Object) this.senderName) + ")";
        }
    }

    private SingleLineViewInflater() {
    }

    public static Icon getDefaultAvatar(Notification.Builder builder, CharSequence charSequence, PeopleHelper.NameToPrefixMap nameToPrefixMap) {
        String prefix;
        int smallIconColor = builder.getSmallIconColor(false);
        String str = "";
        if (charSequence == null || charSequence.length() == 0) {
            return peopleHelper.createAvatarSymbol("", "", smallIconColor);
        }
        if (nameToPrefixMap != null && (prefix = nameToPrefixMap.getPrefix(charSequence)) != null) {
            str = prefix;
        }
        return peopleHelper.createAvatarSymbol(charSequence, str, smallIconColor);
    }

    public static final HybridNotificationView inflatePrivateSingleLineView(boolean z, int i, NotificationEntry notificationEntry, Context context, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i2 = AsyncHybridViewInflation.$r8$clinit;
        if ((i & 16) == 0) {
            return null;
        }
        INSTANCE.getClass();
        return inflateSingleLineView(z, i, notificationEntry, context, notificationRowContentBinderLogger);
    }

    public static final HybridNotificationView inflatePublicSingleLineView(boolean z, int i, NotificationEntry notificationEntry, Context context, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
        if ((i & 128) == 0) {
            return null;
        }
        INSTANCE.getClass();
        return inflateSingleLineView(z, i, notificationEntry, context, notificationRowContentBinderLogger);
    }

    public static final SingleLineViewModel inflatePublicSingleLineViewModel(Context context, boolean z) {
        return new SingleLineViewModel(context.getString(R.string.redacted_notification_single_line_title), context.getString(R.string.public_notification_single_line_text), z ? new ConversationData(null, new SingleIcon(context.getDrawable(R.drawable.ic_redacted_notification_single_line_icon)), null) : null);
    }

    public static HybridNotificationView inflateSingleLineView(boolean z, int i, NotificationEntry notificationEntry, Context context, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i2 = AsyncHybridViewInflation.$r8$clinit;
        String logKey = NotificationUtilsKt.getLogKey(notificationEntry);
        notificationRowContentBinderLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationRowContentBinderLogger$$ExternalSyntheticLambda0 notificationRowContentBinderLogger$$ExternalSyntheticLambda0 = new NotificationRowContentBinderLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = notificationRowContentBinderLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationRowContentBinder", logLevel, notificationRowContentBinderLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = logKey;
        logMessageImpl.int1 = i;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
        notificationRowContentBinderLogger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "inflating single-line content view");
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("SingleLineViewInflater#inflateSingleLineView");
        }
        try {
            LayoutInflater from = LayoutInflater.from(context);
            int i3 = HybridNotificationView.$r8$clinit;
            HybridNotificationView hybridNotificationView = (HybridNotificationView) from.inflate(z ? R.layout.hybrid_conversation_notification : R.layout.hybrid_notification, (ViewGroup) null);
            if (hybridNotificationView == null) {
                Log.wtf("SingleLineViewInflater", "Single-line view inflation result is null for entry: " + NotificationUtilsKt.getLogKey(notificationEntry));
            }
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            return hybridNotificationView;
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public static final SingleLineViewModel inflateSingleLineViewModel(Notification notification2, Notification.MessagingStyle messagingStyle, Notification.Builder builder, Context context, boolean z, CharSequence charSequence) {
        CharSequence name;
        ConversationTextData conversationTextData;
        List list;
        int i;
        Notification.MessagingStyle.Message message;
        boolean z2;
        CharSequence charSequence2;
        ArrayList arrayList;
        CharSequence charSequence3;
        ConversationAvatar singleIcon;
        Icon icon;
        CharSequence name2;
        CharSequence charSequence4;
        CharSequence charSequence5;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i2 = AsyncHybridViewInflation.$r8$clinit;
        peopleHelper.init(context);
        CharSequence resolveTitle = HybridGroupManager.resolveTitle(notification2);
        CharSequence string = z ? context.getString(R.string.redacted_otp_notification_single_line_text) : HybridGroupManager.resolveText(notification2);
        if (messagingStyle == null) {
            return new SingleLineViewModel(resolveTitle, string, null);
        }
        boolean isGroupConversation = messagingStyle.isGroupConversation();
        INSTANCE.getClass();
        if (messagingStyle.getMessages().isEmpty()) {
            conversationTextData = null;
        } else {
            Notification.MessagingStyle.Message message2 = messagingStyle.getMessages().get(CollectionsKt__CollectionsKt.getLastIndex(messagingStyle.getMessages()));
            CharSequence text = message2.getText();
            if (text == null && MessagingMessage.hasImage(message2)) {
                text = !MessagingMessage.hasImage(message2) ? null : context.getResources().getString(android.R.string.fingerprint_error_power_pressed);
            }
            Person senderPerson = message2.getSenderPerson();
            if (senderPerson == null || (name = senderPerson.getName()) == null) {
                name = messagingStyle.getUser().getName();
            }
            String string2 = context.getResources().getString(android.R.string.fingerprint_error_security_update_required, name != null ? name.toString() : null);
            CharSequence conversationTitle = messagingStyle.getConversationTitle();
            if (conversationTitle == null) {
                if (messagingStyle.isGroupConversation()) {
                    conversationTitle = context.getResources().getString(android.R.string.fingerprint_error_timeout);
                    conversationTitle.getClass();
                } else {
                    conversationTitle = string2 == null ? context.getResources().getString(android.R.string.fingerprint_error_unable_to_process) : string2;
                }
            }
            conversationTextData = new ConversationTextData(conversationTitle, text, string2);
        }
        if (conversationTextData != null && (charSequence5 = conversationTextData.conversationTitle) != null && charSequence5.length() > 0) {
            resolveTitle = conversationTextData.conversationTitle;
        }
        if (!z && conversationTextData != null && (charSequence4 = conversationTextData.conversationText) != null && charSequence4.length() > 0) {
            string = conversationTextData.conversationText;
        }
        Person user = messagingStyle.getUser();
        CharSequence name3 = user.getKey() == null ? user.getName() : user.getKey();
        Icon shortcutIcon = messagingStyle.getShortcutIcon();
        String conversationTitle2 = messagingStyle.getConversationTitle();
        List<Notification.MessagingStyle.Message> messages = messagingStyle.getMessages();
        List<Notification.MessagingStyle.Message> historicMessages = messagingStyle.getHistoricMessages();
        if (messages.isEmpty() && historicMessages.isEmpty()) {
            list = EmptyList.INSTANCE;
        } else {
            ArrayList arrayList2 = new ArrayList();
            int size = historicMessages.size();
            int size2 = messages.size() + size;
            CharSequence charSequence6 = null;
            ArrayList arrayList3 = null;
            int i3 = 0;
            while (i3 < size2) {
                if (i3 < size) {
                    i = i3;
                    message = historicMessages.get(i3);
                } else {
                    i = i3;
                    message = messages.get(i - size);
                }
                Person senderPerson2 = message.getSenderPerson();
                if (senderPerson2 != null) {
                    CharSequence name4 = senderPerson2.getKey() == null ? senderPerson2.getName() : senderPerson2.getKey();
                    z2 = isGroupConversation;
                    charSequence2 = name4;
                } else {
                    z2 = isGroupConversation;
                    charSequence2 = null;
                }
                if (arrayList3 == null || !Intrinsics.areEqual(charSequence2, charSequence6)) {
                    ArrayList arrayList4 = new ArrayList();
                    arrayList2.add(arrayList4);
                    CharSequence charSequence7 = charSequence2;
                    arrayList = arrayList4;
                    charSequence6 = charSequence7;
                } else {
                    arrayList = arrayList3;
                }
                arrayList.add(message);
                i3 = i + 1;
                arrayList3 = arrayList;
                isGroupConversation = z2;
            }
            list = arrayList2;
        }
        boolean z3 = isGroupConversation;
        PeopleHelper.NameToPrefixMap mapUniqueNamesToPrefixWithGroupList = peopleHelper.mapUniqueNamesToPrefixWithGroupList(list);
        if (!z3) {
            for (int lastIndex = CollectionsKt__CollectionsKt.getLastIndex(messagingStyle.getMessages()); -1 < lastIndex; lastIndex--) {
                Person senderPerson3 = messagingStyle.getMessages().get(lastIndex).getSenderPerson();
                CharSequence name5 = senderPerson3 != null ? senderPerson3.getKey() == null ? senderPerson3.getName() : senderPerson3.getKey() : null;
                if ((senderPerson3 != null && !Intrinsics.areEqual(name5, name3)) || lastIndex == 0) {
                    if (conversationTitle2 == null || conversationTitle2.length() == 0) {
                        conversationTitle2 = (senderPerson3 == null || (name2 = senderPerson3.getName()) == null) ? "" : name2;
                    }
                    if (shortcutIcon == null) {
                        Icon icon2 = senderPerson3 != null ? senderPerson3.getIcon() : null;
                        if (icon2 == null) {
                            icon2 = getDefaultAvatar(builder, conversationTitle2, null);
                        }
                        shortcutIcon = icon2;
                    }
                }
            }
        }
        if (shortcutIcon == null) {
            shortcutIcon = notification2.getLargeIcon();
        }
        if (z3 && shortcutIcon == null) {
            int lastIndex2 = CollectionsKt__CollectionsKt.getLastIndex(list);
            CharSequence charSequence8 = null;
            Icon icon3 = null;
            int i4 = -1;
            while (true) {
                if (i4 >= lastIndex2) {
                    icon = null;
                    break;
                }
                Person senderPerson4 = ((Notification.MessagingStyle.Message) ((List) list.get(lastIndex2)).get(0)).getSenderPerson();
                if (senderPerson4 == null) {
                    senderPerson4 = messagingStyle.getUser();
                }
                CharSequence name6 = senderPerson4.getKey() == null ? senderPerson4.getName() : senderPerson4.getKey();
                boolean areEqual = Intrinsics.areEqual(name6, name3);
                boolean areEqual2 = Intrinsics.areEqual(name6, charSequence8);
                if ((!areEqual && !areEqual2) || (lastIndex2 == 0 && charSequence8 == null)) {
                    if (icon3 == null) {
                        Icon icon4 = senderPerson4.getIcon();
                        if (icon4 == null) {
                            icon4 = getDefaultAvatar(builder, senderPerson4.getName(), mapUniqueNamesToPrefixWithGroupList);
                        }
                        icon3 = icon4;
                        charSequence8 = name6;
                    } else {
                        Icon icon5 = senderPerson4.getIcon();
                        icon = icon5 == null ? getDefaultAvatar(builder, senderPerson4.getName(), mapUniqueNamesToPrefixWithGroupList) : icon5;
                    }
                }
                lastIndex2--;
                i4 = -1;
            }
            charSequence3 = null;
            if (icon3 == null) {
                icon3 = getDefaultAvatar(builder, "", null);
            }
            if (icon == null) {
                icon = getDefaultAvatar(builder, "", null);
            }
            singleIcon = new FacePile(icon.loadDrawable(context), icon3.loadDrawable(context), builder.getBackgroundColor(false));
        } else {
            charSequence3 = null;
            singleIcon = new SingleIcon(shortcutIcon != null ? shortcutIcon.loadDrawable(context) : null);
        }
        if (z3 && conversationTextData != null) {
            charSequence3 = conversationTextData.senderName;
        }
        return new SingleLineViewModel(resolveTitle, string, new ConversationData(charSequence3, singleIcon, charSequence));
    }
}
