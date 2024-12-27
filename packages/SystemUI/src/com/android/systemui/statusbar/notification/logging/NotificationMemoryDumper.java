package com.android.systemui.statusbar.notification.logging;

import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.DumpsysTableLogger;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import dalvik.annotation.optimization.NeverCompile;
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
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    @NeverCompile
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Object obj;
        String str;
        NotificationMemoryMeter notificationMemoryMeter = NotificationMemoryMeter.INSTANCE;
        Collection allNotifs = this.notificationPipeline.getAllNotifs();
        notificationMemoryMeter.getClass();
        List sortedWith = CollectionsKt___CollectionsKt.sortedWith(SequencesKt___SequencesKt.toList(new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(allNotifs), NotificationMemoryMeter$notificationMemoryUse$1.INSTANCE)), ComparisonsKt__ComparisonsKt.compareBy(new Function1() { // from class: com.android.systemui.statusbar.notification.logging.NotificationMemoryDumper$dump$memoryUse$1
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
        List<NotificationMemoryUsage> list = sortedWith;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (NotificationMemoryUsage notificationMemoryUsage : list) {
            String str2 = notificationMemoryUsage.packageName;
            NotificationObjectUsage notificationObjectUsage = notificationMemoryUsage.objectUsage;
            String kb = toKb(notificationObjectUsage.smallIcon);
            String kb2 = toKb(notificationObjectUsage.largeIcon);
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
            arrayList.add(CollectionsKt__CollectionsKt.listOf(str2, kb, kb2, str, toKb(notificationObjectUsage.styleIcon), toKb(notificationObjectUsage.bigPicture), toKb(notificationObjectUsage.extender), toKb(notificationObjectUsage.extras), String.valueOf(notificationObjectUsage.hasCustomView), notificationMemoryUsage.notificationKey.replace('|', (char) 9474)));
        }
        NotificationMemoryDumper$dumpNotificationObjects$Totals notificationMemoryDumper$dumpNotificationObjects$Totals = new NotificationMemoryDumper$dumpNotificationObjects$Totals(0, 0, 0, 0, 0, 0, 63, null);
        for (NotificationMemoryUsage notificationMemoryUsage2 : list) {
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
        for (Object obj2 : list) {
            if (!((NotificationMemoryUsage) obj2).viewUsage.isEmpty()) {
                arrayList2.add(obj2);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            NotificationMemoryUsage notificationMemoryUsage3 = (NotificationMemoryUsage) it.next();
            List<NotificationViewUsage> list2 = notificationMemoryUsage3.viewUsage;
            ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            for (NotificationViewUsage notificationViewUsage : list2) {
                arrayList4.add(CollectionsKt__CollectionsKt.listOf(notificationMemoryUsage3.packageName, notificationViewUsage.viewType.toString(), toKb(notificationViewUsage.smallIcon), toKb(notificationViewUsage.largeIcon), toKb(notificationViewUsage.style), toKb(notificationViewUsage.customViews), toKb(notificationViewUsage.softwareBitmapsPenalty), notificationMemoryUsage3.notificationKey.replace('|', (char) 9474)));
            }
            CollectionsKt__MutableCollectionsKt.addAll(arrayList4, arrayList3);
        }
        NotificationMemoryDumper$dumpNotificationViewUsage$Totals notificationMemoryDumper$dumpNotificationViewUsage$Totals = new NotificationMemoryDumper$dumpNotificationViewUsage$Totals(0, 0, 0, 0, 0, 31, null);
        ArrayList arrayList5 = new ArrayList();
        for (Object obj3 : list) {
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
        Iterator it4 = ((ArrayList) CollectionsKt___CollectionsKt.filterNotNull(arrayList6)).iterator();
        while (it4.hasNext()) {
            NotificationViewUsage notificationViewUsage2 = (NotificationViewUsage) it4.next();
            notificationMemoryDumper$dumpNotificationViewUsage$Totals.smallIcon += notificationViewUsage2.smallIcon;
            notificationMemoryDumper$dumpNotificationViewUsage$Totals.largeIcon += notificationViewUsage2.largeIcon;
            notificationMemoryDumper$dumpNotificationViewUsage$Totals.style += notificationViewUsage2.style;
            notificationMemoryDumper$dumpNotificationViewUsage$Totals.customViews += notificationViewUsage2.customViews;
            notificationMemoryDumper$dumpNotificationViewUsage$Totals.softwareBitmapsPenalty += notificationViewUsage2.softwareBitmapsPenalty;
        }
        new DumpsysTableLogger("Notification View Usage", listOf2, CollectionsKt___CollectionsKt.plus((Iterable) Collections.singletonList(CollectionsKt__CollectionsKt.listOf("TOTALS", "", toKb(notificationMemoryDumper$dumpNotificationViewUsage$Totals.smallIcon), toKb(notificationMemoryDumper$dumpNotificationViewUsage$Totals.largeIcon), toKb(notificationMemoryDumper$dumpNotificationViewUsage$Totals.style), toKb(notificationMemoryDumper$dumpNotificationViewUsage$Totals.customViews), toKb(notificationMemoryDumper$dumpNotificationViewUsage$Totals.softwareBitmapsPenalty), "")), (Collection) arrayList3)).printTableData(printWriter);
    }
}
