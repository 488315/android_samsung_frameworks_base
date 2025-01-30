package com.android.systemui.plugins;

import android.graphics.drawable.Drawable;
import android.service.notification.StatusBarNotification;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(action = NotificationPersonExtractorPlugin.ACTION, version = 1)
@DependsOn(target = PersonData.class)
/* loaded from: classes2.dex */
public interface NotificationPersonExtractorPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PEOPLE_HUB_PERSON_EXTRACTOR";
    public static final int VERSION = 1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @ProvidesInterface(version = 0)
    public static final class PersonData {
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
