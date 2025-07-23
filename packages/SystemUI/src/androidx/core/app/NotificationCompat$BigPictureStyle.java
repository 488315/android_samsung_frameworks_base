package androidx.core.app;

import android.app.Notification;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.core.graphics.drawable.IconCompat;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class NotificationCompat$BigPictureStyle extends NotificationCompat$Style {
    public IconCompat mBigLargeIcon;
    public boolean mBigLargeIconSet;
    public IconCompat mPictureIcon;
    public boolean mShowBigPictureWhenCollapsed;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Api23Impl {
        private Api23Impl() {
        }

        public static void setBigLargeIcon(Notification.BigPictureStyle bigPictureStyle, Icon icon) {
            bigPictureStyle.bigLargeIcon(icon);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Api31Impl {
        private Api31Impl() {
        }

        public static void setBigPicture(Notification.BigPictureStyle bigPictureStyle, Icon icon) {
            bigPictureStyle.bigPicture(icon);
        }

        public static void setContentDescription(Notification.BigPictureStyle bigPictureStyle, CharSequence charSequence) {
            bigPictureStyle.setContentDescription(charSequence);
        }

        public static void showBigPictureWhenCollapsed(Notification.BigPictureStyle bigPictureStyle, boolean z) {
            bigPictureStyle.showBigPictureWhenCollapsed(z);
        }
    }

    public NotificationCompat$BigPictureStyle() {
    }

    public static IconCompat asIconCompat(Parcelable parcelable) {
        if (parcelable == null) {
            return null;
        }
        if (parcelable instanceof Icon) {
            PorterDuff.Mode mode = IconCompat.DEFAULT_TINT_MODE;
            return IconCompat.Api23Impl.createFromIconInner((Icon) parcelable);
        }
        if (parcelable instanceof Bitmap) {
            return IconCompat.createWithBitmap((Bitmap) parcelable);
        }
        return null;
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void apply(NotificationCompatBuilder notificationCompatBuilder) {
        Notification.BigPictureStyle bigContentTitle = new Notification.BigPictureStyle(notificationCompatBuilder.mBuilder).setBigContentTitle(this.mBigContentTitle);
        IconCompat iconCompat = this.mPictureIcon;
        if (iconCompat != null) {
            Api31Impl.setBigPicture(bigContentTitle, iconCompat.toIcon$1());
        }
        if (this.mBigLargeIconSet) {
            IconCompat iconCompat2 = this.mBigLargeIcon;
            if (iconCompat2 == null) {
                bigContentTitle.bigLargeIcon((Bitmap) null);
            } else {
                Api23Impl.setBigLargeIcon(bigContentTitle, iconCompat2.toIcon$1());
            }
        }
        if (this.mSummaryTextSet) {
            bigContentTitle.setSummaryText(this.mSummaryText);
        }
        Api31Impl.showBigPictureWhenCollapsed(bigContentTitle, this.mShowBigPictureWhenCollapsed);
        Api31Impl.setContentDescription(bigContentTitle, null);
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void clearCompatExtraKeys(Bundle bundle) {
        super.clearCompatExtraKeys(bundle);
        bundle.remove("android.largeIcon.big");
        bundle.remove("android.picture");
        bundle.remove("android.pictureIcon");
        bundle.remove("android.showBigPictureWhenCollapsed");
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final String getClassName() {
        return "androidx.core.app.NotificationCompat$BigPictureStyle";
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void restoreFromCompatExtras(Bundle bundle) {
        super.restoreFromCompatExtras(bundle);
        if (bundle.containsKey("android.largeIcon.big")) {
            this.mBigLargeIcon = asIconCompat(bundle.getParcelable("android.largeIcon.big"));
            this.mBigLargeIconSet = true;
        }
        Parcelable parcelable = bundle.getParcelable("android.picture");
        this.mPictureIcon = parcelable != null ? asIconCompat(parcelable) : asIconCompat(bundle.getParcelable("android.pictureIcon"));
        this.mShowBigPictureWhenCollapsed = bundle.getBoolean("android.showBigPictureWhenCollapsed");
    }

    public NotificationCompat$BigPictureStyle(NotificationCompat$Builder notificationCompat$Builder) {
        setBuilder(notificationCompat$Builder);
    }
}
