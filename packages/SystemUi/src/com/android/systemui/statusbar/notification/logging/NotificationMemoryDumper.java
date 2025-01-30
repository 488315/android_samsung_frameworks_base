package com.android.systemui.statusbar.notification.logging;

import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.DumpsysTableLogger;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationMemoryDumper implements Dumpable {
    public final DumpManager dumpManager;
    public final NotifPipeline notificationPipeline;

    public NotificationMemoryDumper(DumpManager dumpManager, NotifPipeline notifPipeline) {
        this.dumpManager = dumpManager;
        this.notificationPipeline = notifPipeline;
    }

    public static String toKb(int i) {
        return i == 0 ? "--" : String.format("%.2f KB", Arrays.copyOf(new Object[]{Float.valueOf(i / 1024.0f)}, 1));
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Object obj;
        String replace;
        String str;
        String replace2;
        NotificationMemoryMeter notificationMemoryMeter = NotificationMemoryMeter.INSTANCE;
        Collection allNotifs = this.notificationPipeline.getAllNotifs();
        notificationMemoryMeter.getClass();
        List<NotificationMemoryUsage> sortedWith = CollectionsKt___CollectionsKt.sortedWith(NotificationMemoryMeter.notificationMemoryUse(allNotifs), ComparisonsKt__ComparisonsKt.compareBy(new Function1() { // from class: com.android.systemui.statusbar.notification.logging.NotificationMemoryDumper$dump$memoryUse$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return ((NotificationMemoryUsage) obj2).packageName;
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.logging.NotificationMemoryDumper$dump$memoryUse$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return ((NotificationMemoryUsage) obj2).notificationKey;
            }
        }));
        List listOf = CollectionsKt__CollectionsKt.listOf("Package", "Small Icon", "Large Icon", "Style", "Style Icon", "Big Picture", "Extender", "Extras", "Custom View", "Key");
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(sortedWith, 10));
        for (NotificationMemoryUsage notificationMemoryUsage : sortedWith) {
            String[] strArr2 = new String[10];
            strArr2[0] = notificationMemoryUsage.packageName;
            NotificationObjectUsage notificationObjectUsage = notificationMemoryUsage.objectUsage;
            strArr2[1] = toKb(notificationObjectUsage.smallIcon);
            strArr2[2] = toKb(notificationObjectUsage.largeIcon);
            int i = notificationObjectUsage.style;
            if (i != -1000) {
                switch (i) {
                    case 0:
                        str = "None";
                        break;
                    case 1:
                        str = "BigPicture";
                        break;
                    case 2:
                        str = "BigText";
                        break;
                    case 3:
                        str = "Call";
                        break;
                    case 4:
                        str = "DCustomView";
                        break;
                    case 5:
                        str = "Inbox";
                        break;
                    case 6:
                        str = "Media";
                        break;
                    case 7:
                        str = "Messaging";
                        break;
                    case 8:
                        str = "RankerGroup";
                        break;
                    default:
                        str = "Unknown";
                        break;
                }
            } else {
                str = "Unspecified";
            }
            strArr2[3] = str;
            strArr2[4] = toKb(notificationObjectUsage.styleIcon);
            strArr2[5] = toKb(notificationObjectUsage.bigPicture);
            strArr2[6] = toKb(notificationObjectUsage.extender);
            strArr2[7] = toKb(notificationObjectUsage.extras);
            strArr2[8] = String.valueOf(notificationObjectUsage.hasCustomView);
            replace2 = notificationMemoryUsage.notificationKey.replace('|', (char) 9474);
            strArr2[9] = replace2;
            arrayList.add(CollectionsKt__CollectionsKt.listOf(strArr2));
        }
        NotificationMemoryDumper$dumpNotificationObjects$Totals notificationMemoryDumper$dumpNotificationObjects$Totals = new NotificationMemoryDumper$dumpNotificationObjects$Totals(0, 0, 0, 0, 0, 0, 63, null);
        for (NotificationMemoryUsage notificationMemoryUsage2 : sortedWith) {
            int i2 = notificationMemoryDumper$dumpNotificationObjects$Totals.smallIcon;
            NotificationObjectUsage notificationObjectUsage2 = notificationMemoryUsage2.objectUsage;
            notificationMemoryDumper$dumpNotificationObjects$Totals.smallIcon = i2 + notificationObjectUsage2.smallIcon;
            notificationMemoryDumper$dumpNotificationObjects$Totals.largeIcon += notificationObjectUsage2.largeIcon;
            notificationMemoryDumper$dumpNotificationObjects$Totals.styleIcon += notificationObjectUsage2.styleIcon;
            notificationMemoryDumper$dumpNotificationObjects$Totals.bigPicture += notificationObjectUsage2.bigPicture;
            notificationMemoryDumper$dumpNotificationObjects$Totals.extender += notificationObjectUsage2.extender;
            notificationMemoryDumper$dumpNotificationObjects$Totals.extras += notificationObjectUsage2.extras;
        }
        new DumpsysTableLogger("Notification Object Usage", listOf, CollectionsKt___CollectionsKt.plus((Iterable) Collections.singletonList(CollectionsKt__CollectionsKt.listOf("TOTALS", toKb(notificationMemoryDumper$dumpNotificationObjects$Totals.smallIcon), toKb(notificationMemoryDumper$dumpNotificationObjects$Totals.largeIcon), "", toKb(notificationMemoryDumper$dumpNotificationObjects$Totals.styleIcon), toKb(notificationMemoryDumper$dumpNotificationObjects$Totals.bigPicture), toKb(notificationMemoryDumper$dumpNotificationObjects$Totals.extender), toKb(notificationMemoryDumper$dumpNotificationObjects$Totals.extras), "", "")), (Collection) arrayList)).printTableData(printWriter);
        List listOf2 = CollectionsKt__CollectionsKt.listOf("Package", "View Type", "Small Icon", "Large Icon", "Style Use", "Custom View", "Software Bitmaps", "Key");
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : sortedWith) {
            if (!((NotificationMemoryUsage) obj2).viewUsage.isEmpty()) {
                arrayList2.add(obj2);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            NotificationMemoryUsage notificationMemoryUsage3 = (NotificationMemoryUsage) it.next();
            List<NotificationViewUsage> list = notificationMemoryUsage3.viewUsage;
            ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            for (NotificationViewUsage notificationViewUsage : list) {
                String str2 = notificationMemoryUsage3.packageName;
                String str3 = notificationViewUsage.viewType.toString();
                String kb = toKb(notificationViewUsage.smallIcon);
                String kb2 = toKb(notificationViewUsage.largeIcon);
                String kb3 = toKb(notificationViewUsage.style);
                String kb4 = toKb(notificationViewUsage.customViews);
                String kb5 = toKb(notificationViewUsage.softwareBitmapsPenalty);
                replace = notificationMemoryUsage3.notificationKey.replace('|', (char) 9474);
                arrayList4.add(CollectionsKt__CollectionsKt.listOf(str2, str3, kb, kb2, kb3, kb4, kb5, replace));
            }
            CollectionsKt__MutableCollectionsKt.addAll(arrayList4, arrayList3);
        }
        NotificationMemoryDumper$dumpNotificationViewUsage$Totals notificationMemoryDumper$dumpNotificationViewUsage$Totals = new NotificationMemoryDumper$dumpNotificationViewUsage$Totals(0, 0, 0, 0, 0, 31, null);
        ArrayList arrayList5 = new ArrayList();
        for (Object obj3 : sortedWith) {
            if (!((NotificationMemoryUsage) obj3).viewUsage.isEmpty()) {
                arrayList5.add(obj3);
            }
        }
        ArrayList arrayList6 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList5, 10));
        Iterator it2 = arrayList5.iterator();
        while (it2.hasNext()) {
            Iterator it3 = ((NotificationMemoryUsage) it2.next()).viewUsage.iterator();
            while (true) {
                if (it3.hasNext()) {
                    obj = it3.next();
                    if (((NotificationViewUsage) obj).viewType == ViewType.TOTAL) {
                        break;
                    }
                } else {
                    obj = null;
                }
            }
            arrayList6.add((NotificationViewUsage) obj);
        }
        ArrayList arrayList7 = new ArrayList();
        Iterator it4 = arrayList6.iterator();
        while (it4.hasNext()) {
            Object next = it4.next();
            if (next != null) {
                arrayList7.add(next);
            }
        }
        Iterator it5 = arrayList7.iterator();
        while (it5.hasNext()) {
            NotificationViewUsage notificationViewUsage2 = (NotificationViewUsage) it5.next();
            notificationMemoryDumper$dumpNotificationViewUsage$Totals.smallIcon += notificationViewUsage2.smallIcon;
            notificationMemoryDumper$dumpNotificationViewUsage$Totals.largeIcon += notificationViewUsage2.largeIcon;
            notificationMemoryDumper$dumpNotificationViewUsage$Totals.style += notificationViewUsage2.style;
            notificationMemoryDumper$dumpNotificationViewUsage$Totals.customViews += notificationViewUsage2.customViews;
            notificationMemoryDumper$dumpNotificationViewUsage$Totals.softwareBitmapsPenalty += notificationViewUsage2.softwareBitmapsPenalty;
        }
        new DumpsysTableLogger("Notification View Usage", listOf2, CollectionsKt___CollectionsKt.plus((Iterable) Collections.singletonList(CollectionsKt__CollectionsKt.listOf("TOTALS", "", toKb(notificationMemoryDumper$dumpNotificationViewUsage$Totals.smallIcon), toKb(notificationMemoryDumper$dumpNotificationViewUsage$Totals.largeIcon), toKb(notificationMemoryDumper$dumpNotificationViewUsage$Totals.style), toKb(notificationMemoryDumper$dumpNotificationViewUsage$Totals.customViews), toKb(notificationMemoryDumper$dumpNotificationViewUsage$Totals.softwareBitmapsPenalty), "")), (Collection) arrayList3)).printTableData(printWriter);
    }
}
