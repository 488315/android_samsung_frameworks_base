package androidx.core.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.text.TextUtils;
import androidx.core.graphics.drawable.IconCompat;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NotificationCompat$BubbleMetadata {
    public final PendingIntent mDeleteIntent;
    public final int mDesiredHeight;
    public final int mDesiredHeightResId;
    public int mFlags;
    public final IconCompat mIcon;
    public final PendingIntent mPendingIntent;
    public final String mShortcutId;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Api30Impl {
        private Api30Impl() {
        }

        public static NotificationCompat$BubbleMetadata fromPlatform(Notification.BubbleMetadata bubbleMetadata) {
            if (bubbleMetadata == null) {
                return null;
            }
            Builder builder = bubbleMetadata.getShortcutId() != null ? new Builder(bubbleMetadata.getShortcutId()) : new Builder(bubbleMetadata.getIntent(), IconCompat.createFromIcon(bubbleMetadata.getIcon()));
            builder.setFlag(1, bubbleMetadata.getAutoExpandBubble());
            builder.mDeleteIntent = bubbleMetadata.getDeleteIntent();
            builder.setFlag(2, bubbleMetadata.isNotificationSuppressed());
            if (bubbleMetadata.getDesiredHeight() != 0) {
                builder.mDesiredHeight = Math.max(bubbleMetadata.getDesiredHeight(), 0);
                builder.mDesiredHeightResId = 0;
            }
            if (bubbleMetadata.getDesiredHeightResId() != 0) {
                builder.mDesiredHeightResId = bubbleMetadata.getDesiredHeightResId();
                builder.mDesiredHeight = 0;
            }
            return builder.build();
        }

        public static Notification.BubbleMetadata toPlatform(NotificationCompat$BubbleMetadata notificationCompat$BubbleMetadata) {
            if (notificationCompat$BubbleMetadata == null) {
                return null;
            }
            String str = notificationCompat$BubbleMetadata.mShortcutId;
            Notification.BubbleMetadata.Builder builder = str != null ? new Notification.BubbleMetadata.Builder(str) : new Notification.BubbleMetadata.Builder(notificationCompat$BubbleMetadata.mPendingIntent, notificationCompat$BubbleMetadata.mIcon.toIcon$1());
            builder.setDeleteIntent(notificationCompat$BubbleMetadata.mDeleteIntent).setAutoExpandBubble((notificationCompat$BubbleMetadata.mFlags & 1) != 0).setSuppressNotification((notificationCompat$BubbleMetadata.mFlags & 2) != 0);
            int i = notificationCompat$BubbleMetadata.mDesiredHeight;
            if (i != 0) {
                builder.setDesiredHeight(i);
            }
            int i2 = notificationCompat$BubbleMetadata.mDesiredHeightResId;
            if (i2 != 0) {
                builder.setDesiredHeightResId(i2);
            }
            return builder.build();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public PendingIntent mDeleteIntent;
        public int mDesiredHeight;
        public int mDesiredHeightResId;
        public int mFlags;
        public final IconCompat mIcon;
        public final PendingIntent mPendingIntent;
        public final String mShortcutId;

        @Deprecated
        public Builder() {
        }

        public final NotificationCompat$BubbleMetadata build() {
            String str = this.mShortcutId;
            if (str == null && this.mPendingIntent == null) {
                throw new NullPointerException("Must supply pending intent or shortcut to bubble");
            }
            if (str == null && this.mIcon == null) {
                throw new NullPointerException("Must supply an icon or shortcut for the bubble");
            }
            NotificationCompat$BubbleMetadata notificationCompat$BubbleMetadata = new NotificationCompat$BubbleMetadata(this.mPendingIntent, this.mDeleteIntent, this.mIcon, this.mDesiredHeight, this.mDesiredHeightResId, this.mFlags, str);
            notificationCompat$BubbleMetadata.mFlags = this.mFlags;
            return notificationCompat$BubbleMetadata;
        }

        public final void setFlag(int i, boolean z) {
            if (z) {
                this.mFlags = i | this.mFlags;
            } else {
                this.mFlags = (~i) & this.mFlags;
            }
        }

        public Builder(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new NullPointerException("Bubble requires a non-null shortcut id");
            }
            this.mShortcutId = str;
        }

        public Builder(PendingIntent pendingIntent, IconCompat iconCompat) {
            if (pendingIntent == null) {
                throw new NullPointerException("Bubble requires non-null pending intent");
            }
            if (iconCompat != null) {
                this.mPendingIntent = pendingIntent;
                this.mIcon = iconCompat;
                return;
            }
            throw new NullPointerException("Bubbles require non-null icon");
        }
    }

    private NotificationCompat$BubbleMetadata(PendingIntent pendingIntent, PendingIntent pendingIntent2, IconCompat iconCompat, int i, int i2, int i3, String str) {
        this.mPendingIntent = pendingIntent;
        this.mIcon = iconCompat;
        this.mDesiredHeight = i;
        this.mDesiredHeightResId = i2;
        this.mDeleteIntent = pendingIntent2;
        this.mFlags = i3;
        this.mShortcutId = str;
    }
}
