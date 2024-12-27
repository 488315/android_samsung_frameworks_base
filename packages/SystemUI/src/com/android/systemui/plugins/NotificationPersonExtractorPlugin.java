package com.android.systemui.plugins;

import android.graphics.drawable.Drawable;
import android.service.notification.StatusBarNotification;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(action = NotificationPersonExtractorPlugin.ACTION, version = 1)
@DependsOn(target = PersonData.class)
public interface NotificationPersonExtractorPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PEOPLE_HUB_PERSON_EXTRACTOR";
    public static final int VERSION = 1;

    @ProvidesInterface(version = 0)
    public final class PersonData {
        public static final int VERSION = 0;
        public final Drawable avatar;
        public final Runnable clickRunnable;
        public final String key;
        public final CharSequence name;

        public PersonData(String str, CharSequence charSequence, Drawable drawable, Runnable runnable) {
            this.key = str;
            this.name = charSequence;
            this.avatar = drawable;
            this.clickRunnable = runnable;
        }
    }

    PersonData extractPerson(StatusBarNotification statusBarNotification);

    default String extractPersonKey(StatusBarNotification statusBarNotification) {
        return extractPerson(statusBarNotification).key;
    }

    default boolean isPersonNotification(StatusBarNotification statusBarNotification) {
        return extractPersonKey(statusBarNotification) != null;
    }
}
