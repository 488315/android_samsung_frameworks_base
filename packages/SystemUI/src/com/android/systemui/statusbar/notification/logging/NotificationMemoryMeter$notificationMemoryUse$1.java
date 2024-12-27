package com.android.systemui.statusbar.notification.logging;

import android.app.Notification;
import android.app.Person;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class NotificationMemoryMeter$notificationMemoryUse$1 extends Lambda implements Function1 {
    public static final NotificationMemoryMeter$notificationMemoryUse$1 INSTANCE = new NotificationMemoryMeter$notificationMemoryUse$1();

    public NotificationMemoryMeter$notificationMemoryUse$1() {
        super(1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v7, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r5v31, types: [android.view.View[]] */
    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        int i;
        int i2;
        int i3;
        String str;
        int i4;
        NotificationEntry notificationEntry;
        int i5;
        int i6;
        List filterNotNull;
        Iterator it;
        Icon icon;
        NotificationEntry notificationEntry2 = (NotificationEntry) obj;
        String packageName = notificationEntry2.mSbn.getPackageName();
        int uid = notificationEntry2.mSbn.getUid();
        NotificationMemoryMeter notificationMemoryMeter = NotificationMemoryMeter.INSTANCE;
        Notification notification2 = notificationEntry2.mSbn.getNotification();
        HashSet hashSet = new HashSet();
        notificationMemoryMeter.getClass();
        Bundle bundle = notification2.extras;
        int computeIconUse = NotificationMemoryMeter.computeIconUse(notification2.getSmallIcon(), hashSet);
        int computeIconUse2 = NotificationMemoryMeter.computeIconUse(notification2.getLargeIcon(), hashSet);
        int computeParcelableUse = NotificationMemoryMeter.computeParcelableUse(bundle, "android.largeIcon.big", hashSet);
        int computeParcelableUse2 = NotificationMemoryMeter.computeParcelableUse(bundle, "android.pictureIcon", hashSet) + NotificationMemoryMeter.computeParcelableUse(bundle, "android.picture", hashSet);
        ArrayList parcelableArrayList = bundle.getParcelableArrayList("android.people.list");
        if (parcelableArrayList != null) {
            Iterator it2 = parcelableArrayList.iterator();
            i = 0;
            while (it2.hasNext()) {
                i += NotificationMemoryMeter.computeIconUse(((Person) it2.next()).getIcon(), hashSet);
            }
        } else {
            i = 0;
        }
        int computeParcelableUse3 = NotificationMemoryMeter.computeParcelableUse(bundle, "android.callPerson", hashSet);
        int computeParcelableUse4 = NotificationMemoryMeter.computeParcelableUse(bundle, "android.verificationIcon", hashSet);
        Iterator it3 = Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray("android.messages")).iterator();
        int i7 = 0;
        while (true) {
            if (!it3.hasNext()) {
                break;
            }
            Person senderPerson = ((Notification.MessagingStyle.Message) it3.next()).getSenderPerson();
            if (senderPerson != null) {
                r17 = senderPerson.getIcon();
            }
            i7 += NotificationMemoryMeter.computeIconUse(r17, hashSet);
        }
        Iterator it4 = Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray("android.messages.historic")).iterator();
        int i8 = 0;
        while (it4.hasNext()) {
            Person senderPerson2 = ((Notification.MessagingStyle.Message) it4.next()).getSenderPerson();
            if (senderPerson2 != null) {
                it = it4;
                icon = senderPerson2.getIcon();
            } else {
                it = it4;
                icon = null;
            }
            i8 += NotificationMemoryMeter.computeIconUse(icon, hashSet);
            it4 = it;
        }
        Bundle bundle2 = bundle.getBundle("android.car.EXTENSIONS");
        if (bundle2 != null) {
            i3 = NotificationMemoryMeter.computeBundleSize(bundle2);
            i2 = uid;
        } else {
            i2 = uid;
            i3 = 0;
        }
        int computeParcelableUse5 = NotificationMemoryMeter.computeParcelableUse(bundle2, "large_icon", hashSet);
        Bundle bundle3 = bundle.getBundle("android.tv.EXTENSIONS");
        if (bundle3 != null) {
            i4 = NotificationMemoryMeter.computeBundleSize(bundle3);
            str = packageName;
        } else {
            str = packageName;
            i4 = 0;
        }
        Bundle bundle4 = bundle.getBundle("android.wearable.EXTENSIONS");
        if (bundle4 != null) {
            i5 = NotificationMemoryMeter.computeBundleSize(bundle4);
            notificationEntry = notificationEntry2;
        } else {
            notificationEntry = notificationEntry2;
            i5 = 0;
        }
        int computeParcelableUse6 = NotificationMemoryMeter.computeParcelableUse(bundle4, "background", hashSet);
        if (Intrinsics.areEqual(notification2.getGroup(), "ranker_group")) {
            i6 = 8;
        } else {
            Class notificationStyle = notification2.getNotificationStyle();
            String name = notificationStyle != null ? notificationStyle.getName() : null;
            i6 = name == null ? 0 : name.equals(Notification.BigTextStyle.class.getName()) ? 2 : name.equals(Notification.BigPictureStyle.class.getName()) ? 1 : name.equals(Notification.InboxStyle.class.getName()) ? 5 : name.equals(Notification.MediaStyle.class.getName()) ? 6 : name.equals(Notification.DecoratedCustomViewStyle.class.getName()) ? 4 : name.equals(Notification.MessagingStyle.class.getName()) ? 7 : name.equals(Notification.CallStyle.class.getName()) ? 3 : -1000;
        }
        NotificationObjectUsage notificationObjectUsage = new NotificationObjectUsage(computeIconUse, computeIconUse2, NotificationMemoryMeter.computeBundleSize(bundle), i6, computeParcelableUse + i + computeParcelableUse3 + computeParcelableUse4 + i7 + i8, computeParcelableUse2, i3 + computeParcelableUse5 + i4 + i5 + computeParcelableUse6, (notification2.contentView == null && notification2.bigContentView == null) ? false : true);
        NotificationMemoryViewWalker notificationMemoryViewWalker = NotificationMemoryViewWalker.INSTANCE;
        NotificationEntry notificationEntry3 = notificationEntry;
        ExpandableNotificationRow expandableNotificationRow = notificationEntry3.row;
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
            filterNotNull = CollectionsKt___CollectionsKt.filterNotNull(CollectionsKt__CollectionsKt.listOf(viewUsage$default, viewUsage$default2, viewUsage$default3, NotificationMemoryViewWalker.getViewUsage$default(notificationMemoryViewWalker, viewType4, viewArr4)));
            if (!((ArrayList) filterNotNull).isEmpty()) {
                HashSet hashSet2 = new HashSet();
                ViewType viewType5 = ViewType.TOTAL;
                ?? r5 = new View[6];
                NotificationContentView notificationContentView5 = expandableNotificationRow.mPrivateLayout;
                r5[0] = notificationContentView5 != null ? notificationContentView5.mExpandedChild : null;
                r5[1] = notificationContentView5 != null ? notificationContentView5.mContractedChild : null;
                r5[2] = notificationContentView5 != null ? notificationContentView5.mHeadsUpChild : null;
                NotificationContentView notificationContentView6 = expandableNotificationRow.mPublicLayout;
                r5[3] = notificationContentView6 != null ? notificationContentView6.mExpandedChild : null;
                r5[4] = notificationContentView6 != null ? notificationContentView6.mContractedChild : null;
                r5[5] = notificationContentView6 != null ? notificationContentView6.mHeadsUpChild : null;
                NotificationViewUsage viewUsage = NotificationMemoryViewWalker.getViewUsage(viewType5, r5, hashSet2);
                if (viewUsage != null) {
                    filterNotNull = CollectionsKt___CollectionsKt.plus(filterNotNull, viewUsage);
                }
            } else {
                filterNotNull = EmptyList.INSTANCE;
            }
        }
        List list = filterNotNull;
        Intrinsics.checkNotNull(str);
        return new NotificationMemoryUsage(str, i2, NotificationUtils.logKey(notificationEntry3.mSbn.getKey()), notificationEntry3.mSbn.getNotification(), notificationObjectUsage, list);
    }
}
