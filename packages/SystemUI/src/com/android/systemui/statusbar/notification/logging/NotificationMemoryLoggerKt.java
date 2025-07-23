package com.android.systemui.statusbar.notification.logging;

import com.android.systemui.statusbar.notification.logging.NotificationMemoryLogger;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class NotificationMemoryLoggerKt {
    public static final Map<Pair<String, Integer>, NotificationMemoryLogger.NotificationMemoryUseAtomBuilder> aggregateMemoryUsageData(List<NotificationMemoryUsage> list) {
        Object obj;
        NotificationMemoryLoggerKt$aggregateMemoryUsageData$$inlined$groupingBy$1 notificationMemoryLoggerKt$aggregateMemoryUsageData$$inlined$groupingBy$1 = new NotificationMemoryLoggerKt$aggregateMemoryUsageData$$inlined$groupingBy$1(list);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj2 : notificationMemoryLoggerKt$aggregateMemoryUsageData$$inlined$groupingBy$1.$this_groupingBy) {
            Object keyOf = notificationMemoryLoggerKt$aggregateMemoryUsageData$$inlined$groupingBy$1.keyOf(obj2);
            Object obj3 = linkedHashMap.get(keyOf);
            boolean z = obj3 == null && !linkedHashMap.containsKey(keyOf);
            NotificationMemoryUsage notificationMemoryUsage = (NotificationMemoryUsage) obj2;
            NotificationMemoryLogger.NotificationMemoryUseAtomBuilder notificationMemoryUseAtomBuilder = (NotificationMemoryLogger.NotificationMemoryUseAtomBuilder) obj3;
            if (z) {
                notificationMemoryUseAtomBuilder = new NotificationMemoryLogger.NotificationMemoryUseAtomBuilder(notificationMemoryUsage.uid, notificationMemoryUsage.objectUsage.style);
            } else {
                notificationMemoryUseAtomBuilder.getClass();
            }
            notificationMemoryUseAtomBuilder.count++;
            if (!notificationMemoryUsage.viewUsage.isEmpty()) {
                notificationMemoryUseAtomBuilder.countWithInflatedViews++;
            }
            int i = notificationMemoryUseAtomBuilder.smallIconObject;
            NotificationObjectUsage notificationObjectUsage = notificationMemoryUsage.objectUsage;
            int i2 = notificationObjectUsage.smallIcon;
            notificationMemoryUseAtomBuilder.smallIconObject = i + i2;
            if (i2 > 0) {
                notificationMemoryUseAtomBuilder.smallIconBitmapCount++;
            }
            int i3 = notificationMemoryUseAtomBuilder.largeIconObject;
            int i4 = notificationObjectUsage.largeIcon;
            notificationMemoryUseAtomBuilder.largeIconObject = i3 + i4;
            if (i4 > 0) {
                notificationMemoryUseAtomBuilder.largeIconBitmapCount++;
            }
            int i5 = notificationMemoryUseAtomBuilder.bigPictureObject;
            int i6 = notificationObjectUsage.bigPicture;
            notificationMemoryUseAtomBuilder.bigPictureObject = i5 + i6;
            if (i6 > 0) {
                notificationMemoryUseAtomBuilder.bigPictureBitmapCount++;
            }
            notificationMemoryUseAtomBuilder.extras += notificationObjectUsage.extras;
            notificationMemoryUseAtomBuilder.extenders += notificationObjectUsage.extender;
            Iterator it = notificationMemoryUsage.viewUsage.iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                if (((NotificationViewUsage) obj).viewType == ViewType.TOTAL) {
                    break;
                }
            }
            NotificationViewUsage notificationViewUsage = (NotificationViewUsage) obj;
            if (notificationViewUsage != null) {
                notificationMemoryUseAtomBuilder.smallIconViews += notificationViewUsage.smallIcon;
                notificationMemoryUseAtomBuilder.largeIconViews += notificationViewUsage.largeIcon;
                notificationMemoryUseAtomBuilder.systemIconViews += notificationViewUsage.systemIcons;
                notificationMemoryUseAtomBuilder.styleViews += notificationViewUsage.style;
                notificationMemoryUseAtomBuilder.customViews += notificationViewUsage.customViews;
                notificationMemoryUseAtomBuilder.softwareBitmaps += notificationViewUsage.softwareBitmapsPenalty;
            }
            linkedHashMap.put(keyOf, notificationMemoryUseAtomBuilder);
        }
        return linkedHashMap;
    }
}
