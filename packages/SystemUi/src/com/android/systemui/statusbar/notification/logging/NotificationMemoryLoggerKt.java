package com.android.systemui.statusbar.notification.logging;

import com.android.systemui.statusbar.notification.logging.NotificationMemoryLogger;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.Grouping;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class NotificationMemoryLoggerKt {
    public static final Map<Pair<String, Integer>, NotificationMemoryLogger.NotificationMemoryUseAtomBuilder> aggregateMemoryUsageData(final List<NotificationMemoryUsage> list) {
        Object obj;
        Grouping grouping = new Grouping() { // from class: com.android.systemui.statusbar.notification.logging.NotificationMemoryLoggerKt$aggregateMemoryUsageData$$inlined$groupingBy$1
            @Override // kotlin.collections.Grouping
            public final Object keyOf(Object obj2) {
                NotificationMemoryUsage notificationMemoryUsage = (NotificationMemoryUsage) obj2;
                return new Pair(notificationMemoryUsage.packageName, Integer.valueOf(notificationMemoryUsage.objectUsage.style));
            }

            @Override // kotlin.collections.Grouping
            public final Iterator sourceIterator() {
                return list.iterator();
            }
        };
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator sourceIterator = grouping.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object next = sourceIterator.next();
            Object keyOf = grouping.keyOf(next);
            Object obj2 = linkedHashMap.get(keyOf);
            boolean z = obj2 == null && !linkedHashMap.containsKey(keyOf);
            NotificationMemoryUsage notificationMemoryUsage = (NotificationMemoryUsage) next;
            NotificationMemoryLogger.NotificationMemoryUseAtomBuilder notificationMemoryUseAtomBuilder = (NotificationMemoryLogger.NotificationMemoryUseAtomBuilder) obj2;
            if (z) {
                notificationMemoryUseAtomBuilder = new NotificationMemoryLogger.NotificationMemoryUseAtomBuilder(notificationMemoryUsage.uid, notificationMemoryUsage.objectUsage.style);
            } else {
                Intrinsics.checkNotNull(notificationMemoryUseAtomBuilder);
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
