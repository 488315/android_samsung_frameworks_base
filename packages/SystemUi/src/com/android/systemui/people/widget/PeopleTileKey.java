package com.android.systemui.people.widget;

import android.app.people.PeopleSpaceTile;
import android.service.notification.NotificationListenerService;
import android.text.TextUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PeopleTileKey {
    public static final Pattern KEY_PATTERN = Pattern.compile("(.+)/(-?\\d+)/(\\p{L}.*)");
    public final String mPackageName;
    public final String mShortcutId;
    public int mUserId;

    public PeopleTileKey(String str, int i, String str2) {
        this.mShortcutId = str;
        this.mUserId = i;
        this.mPackageName = str2;
    }

    public static PeopleTileKey fromString(String str) {
        if (str == null) {
            return null;
        }
        Matcher matcher = KEY_PATTERN.matcher(str);
        if (matcher.find()) {
            try {
                return new PeopleTileKey(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3));
            } catch (NumberFormatException unused) {
            }
        }
        return null;
    }

    public static boolean isValid(PeopleTileKey peopleTileKey) {
        if (peopleTileKey != null) {
            return !TextUtils.isEmpty(peopleTileKey.mShortcutId) && !TextUtils.isEmpty(peopleTileKey.mPackageName) && peopleTileKey.mUserId >= 0;
        }
        return false;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PeopleTileKey) {
            return Objects.equals(((PeopleTileKey) obj).toString(), toString());
        }
        return false;
    }

    public final int hashCode() {
        return this.mShortcutId.hashCode() + Integer.valueOf(this.mUserId).hashCode() + this.mPackageName.hashCode();
    }

    public final String toString() {
        return this.mShortcutId + "/" + this.mUserId + "/" + this.mPackageName;
    }

    public PeopleTileKey(PeopleSpaceTile peopleSpaceTile) {
        this.mShortcutId = peopleSpaceTile.getId();
        this.mUserId = peopleSpaceTile.getUserHandle().getIdentifier();
        this.mPackageName = peopleSpaceTile.getPackageName();
    }

    public PeopleTileKey(NotificationEntry notificationEntry) {
        NotificationListenerService.Ranking ranking = notificationEntry.mRanking;
        this.mShortcutId = (ranking == null || ranking.getConversationShortcutInfo() == null) ? "" : notificationEntry.mRanking.getConversationShortcutInfo().getId();
        this.mUserId = notificationEntry.mSbn.getUser() != null ? notificationEntry.mSbn.getUser().getIdentifier() : -1;
        this.mPackageName = notificationEntry.mSbn.getPackageName();
    }
}
