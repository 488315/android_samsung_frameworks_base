package com.android.systemui.statusbar.notification.logging;

import android.app.Notification;
import android.app.Person;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationMemoryMeter$$ExternalSyntheticLambda0 implements Function1 {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v13, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r6v11, types: [android.view.View[]] */
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        int i;
        int i2;
        int i3;
        boolean z;
        List filterNotNull;
        Iterator it;
        Icon icon;
        NotificationEntry notificationEntry = (NotificationEntry) obj;
        NotificationMemoryMeter notificationMemoryMeter = NotificationMemoryMeter.INSTANCE;
        String packageName = notificationEntry.mSbn.getPackageName();
        int uid = notificationEntry.mSbn.getUid();
        Notification notification2 = notificationEntry.mSbn.getNotification();
        HashSet hashSet = new HashSet();
        NotificationMemoryMeter.INSTANCE.getClass();
        Bundle bundle = notification2.extras;
        int computeIconUse = NotificationMemoryMeter.computeIconUse(notification2.getSmallIcon(), hashSet);
        int computeIconUse2 = NotificationMemoryMeter.computeIconUse(notification2.getLargeIcon(), hashSet);
        int computeParcelableUse = NotificationMemoryMeter.computeParcelableUse(bundle, "android.largeIcon.big", hashSet);
        int computeParcelableUse2 = NotificationMemoryMeter.computeParcelableUse(bundle, "android.pictureIcon", hashSet) + NotificationMemoryMeter.computeParcelableUse(bundle, "android.picture", hashSet);
        ArrayList parcelableArrayList = bundle.getParcelableArrayList("android.people.list");
        if (parcelableArrayList != null) {
            int size = parcelableArrayList.size();
            i = 0;
            int i4 = 0;
            while (i4 < size) {
                Object obj2 = parcelableArrayList.get(i4);
                i4++;
                i += NotificationMemoryMeter.computeIconUse(((Person) obj2).getIcon(), hashSet);
            }
        } else {
            i = 0;
        }
        int computeParcelableUse3 = NotificationMemoryMeter.computeParcelableUse(bundle, "android.callPerson", hashSet);
        int computeParcelableUse4 = NotificationMemoryMeter.computeParcelableUse(bundle, "android.verificationIcon", hashSet);
        Iterator it2 = Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray("android.messages")).iterator();
        int i5 = 0;
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Person senderPerson = ((Notification.MessagingStyle.Message) it2.next()).getSenderPerson();
            if (senderPerson != null) {
                r18 = senderPerson.getIcon();
            }
            i5 += NotificationMemoryMeter.computeIconUse(r18, hashSet);
        }
        Iterator it3 = Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray("android.messages.historic")).iterator();
        int i6 = 0;
        while (it3.hasNext()) {
            Person senderPerson2 = ((Notification.MessagingStyle.Message) it3.next()).getSenderPerson();
            if (senderPerson2 != null) {
                it = it3;
                icon = senderPerson2.getIcon();
            } else {
                it = it3;
                icon = null;
            }
            i6 += NotificationMemoryMeter.computeIconUse(icon, hashSet);
            it3 = it;
        }
        Bundle bundle2 = bundle.getBundle("android.car.EXTENSIONS");
        int computeBundleSize = bundle2 != null ? NotificationMemoryMeter.computeBundleSize(bundle2) : 0;
        int computeParcelableUse5 = NotificationMemoryMeter.computeParcelableUse(bundle2, "large_icon", hashSet);
        Bundle bundle3 = bundle.getBundle("android.tv.EXTENSIONS");
        int computeBundleSize2 = bundle3 != null ? NotificationMemoryMeter.computeBundleSize(bundle3) : 0;
        Bundle bundle4 = bundle.getBundle("android.wearable.EXTENSIONS");
        int computeBundleSize3 = bundle4 != null ? NotificationMemoryMeter.computeBundleSize(bundle4) : 0;
        int i7 = computeBundleSize2;
        int computeParcelableUse6 = NotificationMemoryMeter.computeParcelableUse(bundle4, BriefViewController.SUGGESTION_BACKGROUND_KEY, hashSet);
        if (Intrinsics.areEqual(notification2.getGroup(), "ranker_group")) {
            i2 = 8;
        } else {
            Class notificationStyle = notification2.getNotificationStyle();
            String name = notificationStyle != null ? notificationStyle.getName() : null;
            i2 = name == null ? 0 : name.equals(Notification.BigTextStyle.class.getName()) ? 2 : name.equals(Notification.BigPictureStyle.class.getName()) ? 1 : name.equals(Notification.InboxStyle.class.getName()) ? 5 : name.equals(Notification.MediaStyle.class.getName()) ? 6 : name.equals(Notification.DecoratedCustomViewStyle.class.getName()) ? 4 : name.equals(Notification.MessagingStyle.class.getName()) ? 7 : name.equals(Notification.CallStyle.class.getName()) ? 3 : -1000;
        }
        if (notification2.contentView == null && notification2.bigContentView == null) {
            i3 = computeBundleSize;
            z = false;
        } else {
            i3 = computeBundleSize;
            z = true;
        }
        NotificationObjectUsage notificationObjectUsage = new NotificationObjectUsage(computeIconUse, computeIconUse2, NotificationMemoryMeter.computeBundleSize(bundle), i2, i6 + computeParcelableUse + i + computeParcelableUse3 + computeParcelableUse4 + i5, computeParcelableUse2, i3 + computeParcelableUse5 + i7 + computeBundleSize3 + computeParcelableUse6, z);
        NotificationMemoryViewWalker notificationMemoryViewWalker = NotificationMemoryViewWalker.INSTANCE;
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        notificationMemoryViewWalker.getClass();
        if (expandableNotificationRow == null) {
            filterNotNull = EmptyList.INSTANCE;
        } else {
            ViewType viewType = ViewType.PRIVATE_EXPANDED_VIEW;
            View[] viewArr = new View[1];
            NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
            viewArr[0] = notificationContentView != null ? notificationContentView.mExpandedChild : null;
            NotificationViewUsage viewUsage$default = NotificationMemoryViewWalker.getViewUsage$default(notificationMemoryViewWalker, viewType, viewArr);
            ViewType viewType2 = ViewType.PRIVATE_CONTRACTED_VIEW;
            View[] viewArr2 = new View[1];
            NotificationContentView notificationContentView2 = expandableNotificationRow.mPrivateLayout;
            viewArr2[0] = notificationContentView2 != null ? notificationContentView2.mContractedChild : null;
            NotificationViewUsage viewUsage$default2 = NotificationMemoryViewWalker.getViewUsage$default(notificationMemoryViewWalker, viewType2, viewArr2);
            ViewType viewType3 = ViewType.PRIVATE_HEADS_UP_VIEW;
            View[] viewArr3 = new View[1];
            NotificationContentView notificationContentView3 = expandableNotificationRow.mPrivateLayout;
            viewArr3[0] = notificationContentView3 != null ? notificationContentView3.mHeadsUpChild : null;
            NotificationViewUsage viewUsage$default3 = NotificationMemoryViewWalker.getViewUsage$default(notificationMemoryViewWalker, viewType3, viewArr3);
            ViewType viewType4 = ViewType.PUBLIC_VIEW;
            View[] viewArr4 = new View[3];
            NotificationContentView notificationContentView4 = expandableNotificationRow.mPublicLayout;
            viewArr4[0] = notificationContentView4 != null ? notificationContentView4.mExpandedChild : null;
            viewArr4[1] = notificationContentView4 != null ? notificationContentView4.mContractedChild : null;
            viewArr4[2] = notificationContentView4 != null ? notificationContentView4.mHeadsUpChild : null;
            filterNotNull = CollectionsKt___CollectionsKt.filterNotNull(Arrays.asList(viewUsage$default, viewUsage$default2, viewUsage$default3, NotificationMemoryViewWalker.getViewUsage$default(notificationMemoryViewWalker, viewType4, viewArr4)));
            if (((ArrayList) filterNotNull).isEmpty()) {
                filterNotNull = EmptyList.INSTANCE;
            } else {
                HashSet hashSet2 = new HashSet();
                ViewType viewType5 = ViewType.TOTAL;
                ?? r6 = new View[6];
                NotificationContentView notificationContentView5 = expandableNotificationRow.mPrivateLayout;
                r6[0] = notificationContentView5 != null ? notificationContentView5.mExpandedChild : null;
                r6[1] = notificationContentView5 != null ? notificationContentView5.mContractedChild : null;
                r6[2] = notificationContentView5 != null ? notificationContentView5.mHeadsUpChild : null;
                NotificationContentView notificationContentView6 = expandableNotificationRow.mPublicLayout;
                r6[3] = notificationContentView6 != null ? notificationContentView6.mExpandedChild : null;
                r6[4] = notificationContentView6 != null ? notificationContentView6.mContractedChild : null;
                r6[5] = notificationContentView6 != null ? notificationContentView6.mHeadsUpChild : null;
                NotificationViewUsage viewUsage = NotificationMemoryViewWalker.getViewUsage(viewType5, r6, hashSet2);
                if (viewUsage != null) {
                    filterNotNull = CollectionsKt___CollectionsKt.plus(filterNotNull, viewUsage);
                }
            }
        }
        List list = filterNotNull;
        packageName.getClass();
        return new NotificationMemoryUsage(packageName, uid, NotificationUtils.logKey(notificationEntry.mSbn.getKey()), notificationEntry.mSbn.getNotification(), notificationObjectUsage, list);
    }
}
