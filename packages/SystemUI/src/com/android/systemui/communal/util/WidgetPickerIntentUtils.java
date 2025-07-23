package com.android.systemui.communal.util;

import android.content.ComponentName;
import android.os.UserHandle;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class WidgetPickerIntentUtils {
    public static final WidgetPickerIntentUtils INSTANCE = new WidgetPickerIntentUtils();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class WidgetExtra {
        public final ComponentName componentName;
        public final UserHandle user;

        public WidgetExtra(ComponentName componentName, UserHandle userHandle) {
            this.componentName = componentName;
            this.user = userHandle;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof WidgetExtra)) {
                return false;
            }
            WidgetExtra widgetExtra = (WidgetExtra) obj;
            return Intrinsics.areEqual(this.componentName, widgetExtra.componentName) && Intrinsics.areEqual(this.user, widgetExtra.user);
        }

        public final int hashCode() {
            ComponentName componentName = this.componentName;
            int hashCode = (componentName == null ? 0 : componentName.hashCode()) * 31;
            UserHandle userHandle = this.user;
            return hashCode + (userHandle != null ? userHandle.hashCode() : 0);
        }

        public final String toString() {
            return "WidgetExtra(componentName=" + this.componentName + ", user=" + this.user + ")";
        }
    }

    private WidgetPickerIntentUtils() {
    }
}
