package com.android.systemui.statusbar.notification.icon;

import android.app.Notification;
import android.app.Person;
import android.content.pm.LauncherApps;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.widget.ImageView;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.R;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.NotificationContentDescription;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class IconManager implements ConversationIconManager {
    public final CoroutineScope applicationCoroutineScope;
    public final CoroutineContext bgCoroutineContext;
    public final IconBuilder iconBuilder;
    public final LauncherApps launcherApps;
    public final CoroutineContext mainCoroutineContext;
    public final CommonNotifCollection notifCollection;
    public final Set onIconUpdateRequiredListeners = new LinkedHashSet();
    public Set unimportantConversationKeys = EmptySet.INSTANCE;
    public final ConcurrentHashMap launcherPeopleAvatarIconJobs = new ConcurrentHashMap();
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
                iconManager.updateIcons(notificationEntry, false);
            } catch (InflationException e) {
                Log.e("IconManager", "Unable to update icon", e);
            }
        }
    };

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.notification.icon.IconManager$entryListener$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.statusbar.notification.icon.IconManager$sensitivityListener$1] */
    public IconManager(CommonNotifCollection commonNotifCollection, LauncherApps launcherApps, IconBuilder iconBuilder, CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineContext coroutineContext2) {
        this.notifCollection = commonNotifCollection;
        this.launcherApps = launcherApps;
        this.iconBuilder = iconBuilder;
        this.applicationCoroutineScope = coroutineScope;
        this.bgCoroutineContext = coroutineContext;
        this.mainCoroutineContext = coroutineContext2;
    }

    public final void createIcons(NotificationEntry notificationEntry) {
        IconBuilder iconBuilder = this.iconBuilder;
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("IconManager.createIcons");
        }
        try {
            StatusBarIconView createIconView$default = IconBuilder.createIconView$default(iconBuilder, notificationEntry);
            ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_INSIDE;
            createIconView$default.setScaleType(scaleType);
            StatusBarIconView createIconView$default2 = IconBuilder.createIconView$default(iconBuilder, notificationEntry);
            createIconView$default2.setScaleType(scaleType);
            StatusBarIconView createIconView$default3 = IconBuilder.createIconView$default(iconBuilder, notificationEntry);
            createIconView$default3.setScaleType(scaleType);
            createIconView$default3.setVisibility(4);
            StatusBarIconView createIconView$default4 = IconBuilder.createIconView$default(iconBuilder, notificationEntry);
            createIconView$default4.setScaleType(scaleType);
            Pair iconDescriptors = getIconDescriptors(notificationEntry);
            StatusBarIcon statusBarIcon = (StatusBarIcon) iconDescriptors.component1();
            StatusBarIcon statusBarIcon2 = (StatusBarIcon) iconDescriptors.component2();
            try {
                setIcon(notificationEntry, statusBarIcon, createIconView$default);
                setIcon(notificationEntry, statusBarIcon, createIconView$default2);
                setIcon(notificationEntry, statusBarIcon2, createIconView$default3);
                setIcon(notificationEntry, statusBarIcon, createIconView$default4);
                notificationEntry.mIcons = IconPack.buildPack(createIconView$default, createIconView$default2, createIconView$default3, createIconView$default4, notificationEntry.mIcons);
                Unit unit = Unit.INSTANCE;
            } catch (InflationException e) {
                notificationEntry.mIcons = IconPack.buildEmptyPack(notificationEntry.mIcons);
                throw e;
            }
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    public final StatusBarIcon getIconDescriptor(final NotificationEntry notificationEntry, boolean z) {
        Pair pair;
        boolean z2 = !z && isImportantConversation(notificationEntry);
        IconPack iconPack = notificationEntry.mIcons;
        StatusBarIcon statusBarIcon = iconPack.mPeopleAvatarDescriptor;
        StatusBarIcon statusBarIcon2 = iconPack.mSmallIconDescriptor;
        Icon icon = null;
        if (!z2 || statusBarIcon == null) {
            statusBarIcon = statusBarIcon2 == null ? null : statusBarIcon2;
        }
        if (statusBarIcon != null) {
            return statusBarIcon;
        }
        Notification notification2 = notificationEntry.mSbn.getNotification();
        if (z2) {
            ConcurrentHashMap concurrentHashMap = this.launcherPeopleAvatarIconJobs;
            String str = notificationEntry.mKey;
            Job job = (Job) concurrentHashMap.get(str);
            if (job != null) {
                job.cancel(null);
            }
            ConcurrentHashMap concurrentHashMap2 = this.launcherPeopleAvatarIconJobs;
            StandaloneCoroutine launchTraced$default = CoroutineTracingKt.launchTraced$default(this.applicationCoroutineScope, null, null, new IconManager$createPeopleAvatar$1(this, notificationEntry, null), 7);
            launchTraced$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.statusbar.notification.icon.IconManager$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    IconManager.this.launcherPeopleAvatarIconJobs.remove(notificationEntry.mKey);
                    return Unit.INSTANCE;
                }
            });
            concurrentHashMap2.put(str, launchTraced$default);
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
                        senderPerson2.getClass();
                        icon = senderPerson2.getIcon();
                        break;
                    }
                    if (i < 0) {
                        break;
                    }
                    size = i;
                }
            }
            if (icon == null) {
                icon = notificationEntry.mSbn.getNotification().getLargeIcon();
            }
            if (icon == null) {
                icon = notificationEntry.mSbn.getNotification().getSmallIcon();
            }
            if (icon == null) {
                throw new InflationException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No icon in notification from ", notificationEntry.mSbn.getPackageName()));
            }
            pair = new Pair(icon, StatusBarIcon.Type.PeopleAvatar);
        } else {
            pair = new Pair(notification2.getSmallIcon(), StatusBarIcon.Type.NotifSmallIcon);
        }
        Icon icon2 = (Icon) pair.component1();
        StatusBarIcon.Type type = (StatusBarIcon.Type) pair.component2();
        if (icon2 == null) {
            throw new InflationException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No icon in notification from ", notificationEntry.mSbn.getPackageName()));
        }
        StatusBarIcon statusBarIcon3 = toStatusBarIcon(icon2, notificationEntry, type);
        if (isImportantConversation(notificationEntry)) {
            if (statusBarIcon3.type == StatusBarIcon.Type.PeopleAvatar) {
                notificationEntry.mIcons.mPeopleAvatarDescriptor = statusBarIcon3;
                return statusBarIcon3;
            }
            notificationEntry.mIcons.mSmallIconDescriptor = statusBarIcon3;
        }
        return statusBarIcon3;
    }

    public final Pair getIconDescriptors(NotificationEntry notificationEntry) {
        StatusBarIcon iconDescriptor = getIconDescriptor(notificationEntry, false);
        return new Pair(iconDescriptor, ((Boolean) notificationEntry.mSensitive.getValue()).booleanValue() ? getIconDescriptor(notificationEntry, true) : iconDescriptor);
    }

    public final boolean isImportantConversation(NotificationEntry notificationEntry) {
        return notificationEntry.mRanking.getChannel() != null && notificationEntry.mRanking.getChannel().isImportantConversation() && notificationEntry.mSbn.getNotification().isStyle(Notification.MessagingStyle.class) && !this.unimportantConversationKeys.contains(notificationEntry.mKey);
    }

    public final void recalculateForImportantConversationChange() {
        for (NotificationEntry notificationEntry : ((NotifPipeline) this.notifCollection).getAllNotifs()) {
            notificationEntry.getClass();
            boolean isImportantConversation = isImportantConversation(notificationEntry);
            IconPack iconPack = notificationEntry.mIcons;
            if (iconPack.mAreIconsAvailable && isImportantConversation != iconPack.mIsImportantConversation) {
                try {
                    updateIcons(notificationEntry, false);
                } catch (InflationException e) {
                    Log.e("IconManager", "Unable to update icon", e);
                }
            }
            notificationEntry.mIcons.mIsImportantConversation = isImportantConversation;
        }
    }

    public final void setIcon(NotificationEntry notificationEntry, StatusBarIcon statusBarIcon, StatusBarIconView statusBarIconView) {
        IconPack iconPack = notificationEntry.mIcons;
        boolean z = (!isImportantConversation(notificationEntry) || statusBarIcon.icon.equals(notificationEntry.mSbn.getNotification().getSmallIcon()) || ((statusBarIconView == iconPack.mShelfIcon || statusBarIconView == iconPack.mAodIcon) && ((Boolean) notificationEntry.mSensitive.getValue()).booleanValue())) ? false : true;
        if (statusBarIconView.mShowsConversation != z) {
            statusBarIconView.mShowsConversation = z;
            statusBarIconView.updateIconColor();
        }
        statusBarIconView.setTag(R.id.icon_is_pre_L, Boolean.valueOf(notificationEntry.targetSdk < 21));
        if (statusBarIconView.set(statusBarIcon)) {
            return;
        }
        throw new InflationException("Couldn't create icon " + statusBarIcon);
    }

    public final StatusBarIcon toStatusBarIcon(Icon icon, NotificationEntry notificationEntry, StatusBarIcon.Type type) {
        Notification notification2 = notificationEntry.mSbn.getNotification();
        return new StatusBarIcon(notificationEntry.mSbn.getUser(), notificationEntry.mSbn.getPackageName(), icon, notification2.iconLevel, notification2.number, NotificationContentDescription.contentDescForNotification(this.iconBuilder.context, notification2), type);
    }

    public final Unit updateIcons(NotificationEntry notificationEntry, boolean z) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("IconManager.updateIcons");
        }
        try {
            IconPack iconPack = notificationEntry.mIcons;
            if (iconPack.mAreIconsAvailable) {
                if (!z) {
                    iconPack.mSmallIconDescriptor = null;
                    iconPack.mPeopleAvatarDescriptor = null;
                }
                Pair iconDescriptors = getIconDescriptors(notificationEntry);
                StatusBarIcon statusBarIcon = (StatusBarIcon) iconDescriptors.component1();
                StatusBarIcon statusBarIcon2 = (StatusBarIcon) iconDescriptors.component2();
                Notification notification2 = notificationEntry.mSbn.getNotification();
                CharSequence contentDescForNotification = notification2 != null ? NotificationContentDescription.contentDescForNotification(this.iconBuilder.context, notification2) : null;
                StatusBarIconView statusBarIconView = notificationEntry.mIcons.mStatusBarIcon;
                if (statusBarIconView != null) {
                    statusBarIconView.setNotification(notificationEntry.mSbn, contentDescForNotification);
                    setIcon(notificationEntry, statusBarIcon, statusBarIconView);
                }
                StatusBarIconView statusBarIconView2 = notificationEntry.mIcons.mStatusBarChipIcon;
                if (statusBarIconView2 != null) {
                    statusBarIconView2.setNotification(notificationEntry.mSbn, contentDescForNotification);
                    setIcon(notificationEntry, statusBarIcon, statusBarIconView2);
                }
                StatusBarIconView statusBarIconView3 = notificationEntry.mIcons.mShelfIcon;
                if (statusBarIconView3 != null) {
                    statusBarIconView3.setNotification(notificationEntry.mSbn, contentDescForNotification);
                    setIcon(notificationEntry, statusBarIcon2, statusBarIconView3);
                }
                StatusBarIconView statusBarIconView4 = notificationEntry.mIcons.mAodIcon;
                if (statusBarIconView4 != null) {
                    statusBarIconView4.setNotification(notificationEntry.mSbn, contentDescForNotification);
                    setIcon(notificationEntry, statusBarIcon2, statusBarIconView4);
                }
            }
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            return unit;
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }
}
