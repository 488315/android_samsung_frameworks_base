package com.android.systemui.statusbar.notification.icon;

import android.app.Notification;
import android.app.Person;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.R;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.EmptySet;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class IconManager implements ConversationIconManager {
    public final IconBuilder iconBuilder;
    public final LauncherApps launcherApps;
    public final CommonNotifCollection notifCollection;
    public Set unimportantConversationKeys = EmptySet.INSTANCE;
    public final IconManager$entryListener$1 entryListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.icon.IconManager$entryListener$1
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryCleanUp(NotificationEntry notificationEntry) {
            notificationEntry.mOnSensitivityChangedListeners.remove(IconManager.this.sensitivityListener);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryInit(NotificationEntry notificationEntry) {
            notificationEntry.mOnSensitivityChangedListeners.addIfAbsent(IconManager.this.sensitivityListener);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onRankingApplied() {
            IconManager.this.recalculateForImportantConversationChange();
        }
    };
    public final IconManager$sensitivityListener$1 sensitivityListener = new NotificationEntry.OnSensitivityChangedListener() { // from class: com.android.systemui.statusbar.notification.icon.IconManager$sensitivityListener$1
        @Override // com.android.systemui.statusbar.notification.collection.NotificationEntry.OnSensitivityChangedListener
        public final void onSensitivityChanged(NotificationEntry notificationEntry) {
            IconManager iconManager = IconManager.this;
            iconManager.getClass();
            try {
                iconManager.updateIcons(notificationEntry);
            } catch (InflationException e) {
                Log.e("IconManager", "Unable to update icon", e);
            }
        }
    };

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.icon.IconManager$entryListener$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.notification.icon.IconManager$sensitivityListener$1] */
    public IconManager(CommonNotifCollection commonNotifCollection, LauncherApps launcherApps, IconBuilder iconBuilder) {
        this.notifCollection = commonNotifCollection;
        this.launcherApps = launcherApps;
        this.iconBuilder = iconBuilder;
    }

    public final void createIcons(NotificationEntry notificationEntry) {
        StatusBarIconView statusBarIconView;
        IconBuilder iconBuilder = this.iconBuilder;
        StatusBarIconView createIconView = iconBuilder.createIconView(notificationEntry);
        createIconView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        StatusBarIconView createIconView2 = iconBuilder.createIconView(notificationEntry);
        createIconView2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        createIconView2.setVisibility(4);
        StatusBarIconView createIconView3 = iconBuilder.createIconView(notificationEntry);
        createIconView3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        createIconView3.mIncreasedSize = true;
        createIconView3.maybeUpdateIconScaleDimens();
        if (notificationEntry.mSbn.getNotification().isMediaNotification()) {
            statusBarIconView = iconBuilder.createIconView(notificationEntry);
            statusBarIconView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        } else {
            statusBarIconView = null;
        }
        StatusBarIcon iconDescriptor = getIconDescriptor(notificationEntry, false);
        Pair pair = new Pair(iconDescriptor, notificationEntry.mSensitive ? getIconDescriptor(notificationEntry, true) : iconDescriptor);
        StatusBarIcon statusBarIcon = (StatusBarIcon) pair.component1();
        StatusBarIcon statusBarIcon2 = (StatusBarIcon) pair.component2();
        try {
            setIcon(statusBarIcon, createIconView, notificationEntry);
            setIcon(statusBarIcon2, createIconView2, notificationEntry);
            setIcon(statusBarIcon2, createIconView3, notificationEntry);
            if (statusBarIconView != null) {
                setIcon(statusBarIcon, statusBarIconView, notificationEntry);
            }
            notificationEntry.mIcons = IconPack.buildPack(createIconView, createIconView2, createIconView3, statusBarIconView, notificationEntry.mIcons);
        } catch (InflationException e) {
            notificationEntry.mIcons = IconPack.buildEmptyPack(notificationEntry.mIcons);
            throw e;
        }
    }

    public final StatusBarIcon getIconDescriptor(NotificationEntry notificationEntry, boolean z) {
        Icon smallIcon;
        Notification notification2 = notificationEntry.mSbn.getNotification();
        boolean z2 = isImportantConversation(notificationEntry) && !z;
        IconPack iconPack = notificationEntry.mIcons;
        StatusBarIcon statusBarIcon = iconPack.mPeopleAvatarDescriptor;
        StatusBarIcon statusBarIcon2 = iconPack.mSmallIconDescriptor;
        if (z2 && statusBarIcon != null) {
            return statusBarIcon;
        }
        if (!z2 && statusBarIcon2 != null) {
            return statusBarIcon2;
        }
        if (z2) {
            ShortcutInfo conversationShortcutInfo = notificationEntry.mRanking.getConversationShortcutInfo();
            smallIcon = conversationShortcutInfo != null ? this.launcherApps.getShortcutIcon(conversationShortcutInfo) : null;
            if (smallIcon == null) {
                Bundle bundle = notificationEntry.mSbn.getNotification().extras;
                List<Notification.MessagingStyle.Message> messagesFromBundleArray = Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray("android.messages"));
                Person person = (Person) bundle.getParcelable("android.messagingUser");
                int size = messagesFromBundleArray.size() - 1;
                if (size >= 0) {
                    while (true) {
                        int i = size - 1;
                        Notification.MessagingStyle.Message message = messagesFromBundleArray.get(size);
                        Person senderPerson = message.getSenderPerson();
                        if (senderPerson != null && senderPerson != person) {
                            Person senderPerson2 = message.getSenderPerson();
                            Intrinsics.checkNotNull(senderPerson2);
                            smallIcon = senderPerson2.getIcon();
                            break;
                        }
                        if (i < 0) {
                            break;
                        }
                        size = i;
                    }
                }
            }
            if (smallIcon == null) {
                smallIcon = notificationEntry.mSbn.getNotification().getLargeIcon();
            }
            if (smallIcon == null) {
                smallIcon = notificationEntry.mSbn.getNotification().getSmallIcon();
            }
            if (smallIcon == null) {
                throw new InflationException(KeyAttributes$$ExternalSyntheticOutline0.m21m("No icon in notification from ", notificationEntry.mSbn.getPackageName()));
            }
        } else {
            smallIcon = notification2.getSmallIcon();
        }
        Icon icon = smallIcon;
        if (icon == null) {
            throw new InflationException(KeyAttributes$$ExternalSyntheticOutline0.m21m("No icon in notification from ", notificationEntry.mSbn.getPackageName()));
        }
        StatusBarIcon statusBarIcon3 = new StatusBarIcon(notificationEntry.mSbn.getUser(), notificationEntry.mSbn.getPackageName(), icon, notification2.iconLevel, notification2.number, StatusBarIconView.contentDescForNotification(this.iconBuilder.context, notification2));
        if (isImportantConversation(notificationEntry)) {
            if (z2) {
                notificationEntry.mIcons.mPeopleAvatarDescriptor = statusBarIcon3;
            } else {
                notificationEntry.mIcons.mSmallIconDescriptor = statusBarIcon3;
            }
        }
        return statusBarIcon3;
    }

    public final boolean isImportantConversation(NotificationEntry notificationEntry) {
        return (notificationEntry.mRanking.getChannel() == null || !notificationEntry.mRanking.getChannel().isImportantConversation() || this.unimportantConversationKeys.contains(notificationEntry.mKey)) ? false : true;
    }

    public final void recalculateForImportantConversationChange() {
        for (NotificationEntry notificationEntry : ((NotifPipeline) this.notifCollection).getAllNotifs()) {
            boolean isImportantConversation = isImportantConversation(notificationEntry);
            IconPack iconPack = notificationEntry.mIcons;
            if (iconPack.mAreIconsAvailable && isImportantConversation != iconPack.mIsImportantConversation) {
                try {
                    updateIcons(notificationEntry);
                } catch (InflationException e) {
                    Log.e("IconManager", "Unable to update icon", e);
                }
            }
            notificationEntry.mIcons.mIsImportantConversation = isImportantConversation;
        }
    }

    public final void setIcon(StatusBarIcon statusBarIcon, StatusBarIconView statusBarIconView, NotificationEntry notificationEntry) {
        IconPack iconPack = notificationEntry.mIcons;
        boolean z = statusBarIconView == iconPack.mShelfIcon || statusBarIconView == iconPack.mAodIcon;
        boolean equals = statusBarIcon.icon.equals(notificationEntry.mSbn.getNotification().getSmallIcon());
        statusBarIconView.setTag(R.id.conversation_notification, Boolean.valueOf(isImportantConversation(notificationEntry) && !equals));
        boolean z2 = (!isImportantConversation(notificationEntry) || equals || (z && notificationEntry.mSensitive)) ? false : true;
        if (statusBarIconView.mShowsConversation != z2) {
            statusBarIconView.mShowsConversation = z2;
            statusBarIconView.updateIconColor();
        }
        statusBarIconView.setTag(R.id.icon_is_pre_L, Boolean.valueOf(notificationEntry.targetSdk < 21));
        if (statusBarIconView.set(statusBarIcon)) {
            return;
        }
        throw new InflationException("Couldn't create icon " + statusBarIcon);
    }

    public final void updateIcons(NotificationEntry notificationEntry) {
        IconPack iconPack = notificationEntry.mIcons;
        if (iconPack.mAreIconsAvailable) {
            iconPack.mSmallIconDescriptor = null;
            iconPack.mPeopleAvatarDescriptor = null;
            StatusBarIcon iconDescriptor = getIconDescriptor(notificationEntry, false);
            Pair pair = new Pair(iconDescriptor, notificationEntry.mSensitive ? getIconDescriptor(notificationEntry, true) : iconDescriptor);
            StatusBarIcon statusBarIcon = (StatusBarIcon) pair.component1();
            StatusBarIcon statusBarIcon2 = (StatusBarIcon) pair.component2();
            StatusBarIconView statusBarIconView = notificationEntry.mIcons.mStatusBarIcon;
            if (statusBarIconView != null) {
                statusBarIconView.setNotification(notificationEntry.mSbn);
                setIcon(statusBarIcon, statusBarIconView, notificationEntry);
            }
            StatusBarIconView statusBarIconView2 = notificationEntry.mIcons.mShelfIcon;
            if (statusBarIconView2 != null) {
                statusBarIconView2.setNotification(notificationEntry.mSbn);
                setIcon(statusBarIcon, statusBarIconView2, notificationEntry);
            }
            StatusBarIconView statusBarIconView3 = notificationEntry.mIcons.mAodIcon;
            if (statusBarIconView3 != null) {
                statusBarIconView3.setNotification(notificationEntry.mSbn);
                setIcon(statusBarIcon2, statusBarIconView3, notificationEntry);
            }
            StatusBarIconView statusBarIconView4 = notificationEntry.mIcons.mCenteredIcon;
            if (statusBarIconView4 != null) {
                statusBarIconView4.setNotification(notificationEntry.mSbn);
                setIcon(statusBarIcon2, statusBarIconView4, notificationEntry);
            }
        }
    }
}
