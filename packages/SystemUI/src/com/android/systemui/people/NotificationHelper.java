package com.android.systemui.people;

import android.app.Notification;
import android.app.Person;
import android.os.Bundle;
import android.os.Parcelable;
import android.service.notification.StatusBarNotification;
import com.android.internal.util.ArrayUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationHelper {
    public static final AnonymousClass1 notificationEntryComparator = new Comparator() { // from class: com.android.systemui.people.NotificationHelper.1
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            Notification notification2 = ((NotificationEntry) obj).mSbn.getNotification();
            Notification notification3 = ((NotificationEntry) obj2).mSbn.getNotification();
            boolean isMissedCall = NotificationHelper.isMissedCall(notification2);
            boolean isMissedCall2 = NotificationHelper.isMissedCall(notification3);
            if (isMissedCall && !isMissedCall2) {
                return -1;
            }
            if (isMissedCall || !isMissedCall2) {
                List<Notification.MessagingStyle.Message> messagingStyleMessages = NotificationHelper.getMessagingStyleMessages(notification2);
                List<Notification.MessagingStyle.Message> messagingStyleMessages2 = NotificationHelper.getMessagingStyleMessages(notification3);
                if (messagingStyleMessages != null && messagingStyleMessages2 != null) {
                    return (int) (messagingStyleMessages2.get(0).getTimestamp() - messagingStyleMessages.get(0).getTimestamp());
                }
                if (messagingStyleMessages != null) {
                    if (messagingStyleMessages2 == null) {
                        return -1;
                    }
                    return (int) (notification3.getWhen() - notification2.getWhen());
                }
            }
            return 1;
        }
    };

    public static String getContactUri(StatusBarNotification statusBarNotification) {
        Person senderPerson;
        String uri;
        ArrayList parcelableArrayList = statusBarNotification.getNotification().extras.getParcelableArrayList("android.people.list");
        if (parcelableArrayList != null && parcelableArrayList.get(0) != null && (uri = ((Person) parcelableArrayList.get(0)).getUri()) != null && !uri.isEmpty()) {
            return uri;
        }
        List<Notification.MessagingStyle.Message> messagingStyleMessages = getMessagingStyleMessages(statusBarNotification.getNotification());
        if (messagingStyleMessages == null || messagingStyleMessages.isEmpty() || (senderPerson = messagingStyleMessages.get(0).getSenderPerson()) == null || senderPerson.getUri() == null || senderPerson.getUri().isEmpty()) {
            return null;
        }
        return senderPerson.getUri();
    }

    public static List<Notification.MessagingStyle.Message> getMessagingStyleMessages(Notification notification2) {
        Bundle bundle;
        if (notification2 != null && notification2.isStyle(Notification.MessagingStyle.class) && (bundle = notification2.extras) != null) {
            Parcelable[] parcelableArray = bundle.getParcelableArray("android.messages");
            if (!ArrayUtils.isEmpty(parcelableArray)) {
                List<Notification.MessagingStyle.Message> messagesFromBundleArray = Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArray);
                messagesFromBundleArray.sort(Collections.reverseOrder(Comparator.comparing(new NotificationHelper$$ExternalSyntheticLambda1())));
                return messagesFromBundleArray;
            }
        }
        return null;
    }

    public static boolean isMissedCall(Notification notification2) {
        return notification2 != null && Objects.equals(notification2.category, "missed_call");
    }

    public static boolean isMissedCallOrHasContent(NotificationEntry notificationEntry) {
        List<Notification.MessagingStyle.Message> messagingStyleMessages;
        return ((notificationEntry == null || notificationEntry.mSbn.getNotification() == null || !isMissedCall(notificationEntry.mSbn.getNotification())) && (notificationEntry == null || (messagingStyleMessages = getMessagingStyleMessages(notificationEntry.mSbn.getNotification())) == null || messagingStyleMessages.isEmpty())) ? false : true;
    }
}
