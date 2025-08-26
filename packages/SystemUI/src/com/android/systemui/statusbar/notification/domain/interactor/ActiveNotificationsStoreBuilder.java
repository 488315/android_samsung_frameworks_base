package com.android.systemui.statusbar.notification.domain.interactor;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Person;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import androidx.preference.PreferenceGroupAdapter$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.internal.util.ArrayUtils;
import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.collection.ListAttachState;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore;
import com.android.systemui.statusbar.notification.icon.IconPack;
import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModel;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import com.android.systemui.statusbar.notification.shared.CallType;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class ActiveNotificationsStoreBuilder {
    public final ActiveNotificationsStore.Builder builder = new ActiveNotificationsStore.Builder();
    public final Context context;
    public final ActiveNotificationsStore existingModels;
    public final SectionStyleProvider sectionStyleProvider;

    public ActiveNotificationsStoreBuilder(ActiveNotificationsStore activeNotificationsStore, SectionStyleProvider sectionStyleProvider, Context context) {
        this.existingModels = activeNotificationsStore;
        this.sectionStyleProvider = sectionStyleProvider;
        this.context = context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:173:0x02cf A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00e1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ActiveNotificationModel toModel(NotificationEntry notificationEntry) {
        boolean zIsExceptionalOngoingActivity;
        boolean zContains;
        int i;
        boolean zContains2;
        boolean z;
        boolean zEquals;
        long j;
        Notification.MessagingStyle.Message message;
        String str;
        boolean z2;
        Icon icon;
        int i2;
        boolean z3;
        PromotedNotificationContentModel.Companion.getClass();
        int i3 = notificationEntry.mSbn.getNotification().extras.getInt("android.callChipBg", -1);
        int i4 = notificationEntry.mSbn.getNotification().extras.getInt("android.callChipVisible", 0);
        if (NotiRune.NOTI_ONGOING_GEMINI_DEMO) {
            OngoingActivityDataHelper.INSTANCE.getClass();
            zIsExceptionalOngoingActivity = OngoingActivityDataHelper.isExceptionalOngoingActivity(notificationEntry);
        } else {
            zIsExceptionalOngoingActivity = false;
        }
        String groupKey = notificationEntry.mSbn.getGroupKey();
        long j2 = notificationEntry.mSbn.getNotification().when;
        boolean zIsForegroundService = notificationEntry.mSbn.getNotification().isForegroundService();
        boolean z4 = (notificationEntry.mSbn.getNotification().flags & 2) != 0;
        SectionStyleProvider sectionStyleProvider = this.sectionStyleProvider;
        sectionStyleProvider.getClass();
        ListAttachState listAttachState = notificationEntry.mAttachState;
        NotifSection notifSection = listAttachState.section;
        ActiveNotificationModel activeNotificationModel = null;
        if (notifSection == null) {
            zContains = true;
        } else {
            Set set = sectionStyleProvider.lowPrioritySections;
            if (set == null) {
                set = null;
            }
            zContains = set.contains(notifSection.sectioner);
        }
        boolean zIsRowDismissed = notificationEntry.isRowDismissed();
        NotifSection notifSection2 = listAttachState.section;
        if (notifSection2 == null) {
            z = true;
            i = 1;
        } else {
            i = 1;
            if (notifSection2.bucket == 11) {
                zContains2 = !sectionStyleProvider.highPriorityProvider.isHighPriorityConversation(notificationEntry);
            } else {
                Set set2 = sectionStyleProvider.silentSections;
                if (set2 == null) {
                    set2 = null;
                }
                zContains2 = set2.contains(notifSection2.sectioner);
            }
            z = zContains2;
        }
        if (notificationEntry.hasSentReply) {
            Bundle bundle = notificationEntry.mSbn.getNotification().extras;
            zEquals = true;
            if (ArrayUtils.isEmpty(bundle.getParcelableArray("android.remoteInputHistoryItems"))) {
                List<Notification.MessagingStyle.Message> messagesFromBundleArray = Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray("android.messages"));
                if (messagesFromBundleArray != null && !messagesFromBundleArray.isEmpty() && (message = (Notification.MessagingStyle.Message) PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, messagesFromBundleArray)) != null) {
                    Person senderPerson = message.getSenderPerson();
                    if (senderPerson == null) {
                        j = j2;
                    } else {
                        j = j2;
                        zEquals = Objects.equals((Person) bundle.getParcelable("android.messagingUser", Person.class), senderPerson);
                    }
                }
            }
        } else {
            j = j2;
            zEquals = false;
        }
        boolean zShouldSuppressVisualEffect = notificationEntry.shouldSuppressVisualEffect(32);
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        int i5 = (expandableNotificationRow == null || !expandableNotificationRow.showingPulsing()) ? 0 : i;
        IconPack iconPack = notificationEntry.mIcons;
        StatusBarIconView statusBarIconView = iconPack.mAodIcon;
        Icon icon2 = statusBarIconView != null ? statusBarIconView.mIcon.icon : null;
        boolean z5 = zIsExceptionalOngoingActivity;
        StatusBarIconView statusBarIconView2 = iconPack.mShelfIcon;
        Icon icon3 = statusBarIconView2 != null ? statusBarIconView2.mIcon.icon : null;
        StatusBarIconView statusBarIconView3 = iconPack.mStatusBarIcon;
        Icon icon4 = statusBarIconView3 != null ? statusBarIconView3.mIcon.icon : null;
        int i6 = i3;
        int uid = notificationEntry.mSbn.getUid();
        String packageName = notificationEntry.mSbn.getPackageName();
        Icon icon5 = icon4;
        String strLoadHeaderAppName = notificationEntry.mSbn.getNotification().loadHeaderAppName(this.context);
        if (strLoadHeaderAppName == null) {
            strLoadHeaderAppName = "";
        }
        PendingIntent pendingIntent = notificationEntry.mSbn.getNotification().contentIntent;
        InstanceId instanceId = notificationEntry.mSbn.getInstanceId();
        String str2 = strLoadHeaderAppName;
        boolean zIsGroupSummary = notificationEntry.mSbn.getNotification().isGroupSummary();
        int i7 = notificationEntry.mBucket;
        InstanceId instanceId2 = instanceId;
        Icon icon6 = icon3;
        int i8 = notificationEntry.mSbn.getNotification().extras.getInt("android.callType", -1);
        CallType callType = i8 != -1 ? i8 != 0 ? i8 != i ? i8 != 2 ? i8 != 3 ? CallType.Unknown : CallType.Screening : CallType.Ongoing : CallType.Incoming : CallType.Unknown : CallType.None;
        Map map = this.existingModels.individuals;
        String str3 = notificationEntry.mKey;
        ActiveNotificationModel activeNotificationModel2 = (ActiveNotificationModel) map.get(str3);
        StatusBarIconView statusBarIconView4 = iconPack.mStatusBarChipIcon;
        if (activeNotificationModel2 != null) {
            if (Intrinsics.areEqual(str3, activeNotificationModel2.key) && Intrinsics.areEqual(groupKey, activeNotificationModel2.groupKey)) {
                z2 = zIsRowDismissed;
                if (j == activeNotificationModel2.whenTime && zIsForegroundService == activeNotificationModel2.isForegroundService && z4 == activeNotificationModel2.isOngoingEvent && zContains == activeNotificationModel2.isAmbient && z2 == activeNotificationModel2.isRowDismissed && z == activeNotificationModel2.isSilent && zEquals == activeNotificationModel2.isLastMessageFromReply && zShouldSuppressVisualEffect == activeNotificationModel2.isSuppressedFromStatusBar && i5 == activeNotificationModel2.isPulsing && Intrinsics.areEqual(icon2, activeNotificationModel2.aodIcon)) {
                    icon = icon6;
                    if (Intrinsics.areEqual(icon, activeNotificationModel2.shelfIcon)) {
                        str = str3;
                        if (Intrinsics.areEqual(icon5, activeNotificationModel2.statusBarIcon) && Intrinsics.areEqual(statusBarIconView4, activeNotificationModel2.statusBarChipIconView)) {
                            icon5 = icon5;
                            if (uid != activeNotificationModel2.uid) {
                                uid = uid;
                            } else {
                                uid = uid;
                                if (Intrinsics.areEqual(instanceId2, activeNotificationModel2.instanceId)) {
                                    instanceId2 = instanceId2;
                                    if (zIsGroupSummary != activeNotificationModel2.isGroupSummary) {
                                        zIsGroupSummary = zIsGroupSummary;
                                    } else {
                                        zIsGroupSummary = zIsGroupSummary;
                                        if (Intrinsics.areEqual(packageName, activeNotificationModel2.packageName)) {
                                            packageName = packageName;
                                            if (str2.equals(activeNotificationModel2.appName)) {
                                                str2 = str2;
                                                if (Intrinsics.areEqual(pendingIntent, activeNotificationModel2.contentIntent)) {
                                                    pendingIntent = pendingIntent;
                                                    if (i7 == activeNotificationModel2.bucket && callType == activeNotificationModel2.callType) {
                                                        i7 = i7;
                                                        if (Intrinsics.areEqual((Object) null, activeNotificationModel2.promotedContent)) {
                                                            if (i6 != activeNotificationModel2.callChipColor) {
                                                                i6 = i6;
                                                            } else {
                                                                i6 = i6;
                                                                i2 = i4;
                                                                if (i2 != activeNotificationModel2.extraVisibleFlag) {
                                                                    z3 = z5;
                                                                } else {
                                                                    boolean z6 = activeNotificationModel2.isCallChipNotNeeded;
                                                                    z3 = z5;
                                                                    if (z3 == z6) {
                                                                        activeNotificationModel = activeNotificationModel2;
                                                                    }
                                                                }
                                                                if (activeNotificationModel != null) {
                                                                    return activeNotificationModel;
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        i7 = i7;
                                                    }
                                                } else {
                                                    pendingIntent = pendingIntent;
                                                }
                                            } else {
                                                str2 = str2;
                                            }
                                        } else {
                                            packageName = packageName;
                                        }
                                    }
                                } else {
                                    instanceId2 = instanceId2;
                                }
                            }
                        } else {
                            icon5 = icon5;
                        }
                    } else {
                        str = str3;
                    }
                    i2 = i4;
                    z3 = z5;
                    if (activeNotificationModel != null) {
                    }
                } else {
                    str = str3;
                }
            } else {
                str = str3;
                z2 = zIsRowDismissed;
            }
            icon = icon6;
            i2 = i4;
            z3 = z5;
            if (activeNotificationModel != null) {
            }
        } else {
            str = str3;
            z2 = zIsRowDismissed;
            icon = icon6;
            i2 = i4;
            z3 = z5;
        }
        return new ActiveNotificationModel(str, groupKey, j, zIsForegroundService, z4, zContains, z2, z, zEquals, zShouldSuppressVisualEffect, i5, icon2, icon, icon5, statusBarIconView4, uid, packageName, str2, pendingIntent, instanceId2, zIsGroupSummary, i7, callType, null, i6, i2, z3);
    }
}
