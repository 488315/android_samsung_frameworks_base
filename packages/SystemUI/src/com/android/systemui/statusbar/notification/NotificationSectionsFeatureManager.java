package com.android.systemui.statusbar.notification;

import android.content.Context;
import com.android.systemui.util.DeviceConfigProxy;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationSectionsFeatureManager {
    public final DeviceConfigProxy proxy;

    public NotificationSectionsFeatureManager(DeviceConfigProxy deviceConfigProxy, Context context) {
        this.proxy = deviceConfigProxy;
    }

    public final void clearCache() {
        NotificationSectionsFeatureManagerKt.sUsePeopleFiltering = null;
    }

    public final boolean isFilteringEnabled() {
        if (NotificationSectionsFeatureManagerKt.sUsePeopleFiltering == null) {
            NotificationSectionsFeatureManagerKt.sUsePeopleFiltering = Boolean.valueOf(this.proxy.getBoolean("systemui", "notifications_use_people_filtering", true));
        }
        Boolean bool = NotificationSectionsFeatureManagerKt.sUsePeopleFiltering;
        Intrinsics.checkNotNull(bool);
        return bool.booleanValue();
    }
}
