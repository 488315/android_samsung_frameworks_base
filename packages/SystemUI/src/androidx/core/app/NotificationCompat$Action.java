package androidx.core.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import androidx.core.graphics.drawable.IconCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/* loaded from: classes.dex */
public class NotificationCompat$Action {
    public final PendingIntent actionIntent;
    public final int icon;
    public final boolean mAllowGeneratedReplies;
    public final boolean mAuthenticationRequired;
    public final Bundle mExtras;
    public IconCompat mIcon;
    public final boolean mIsContextual;
    public final RemoteInput[] mRemoteInputs;
    public final int mSemanticAction;
    public final boolean mShowsUserInterface;
    public final CharSequence title;

    public final class Builder {
        public boolean mAllowGeneratedReplies;
        public boolean mAuthenticationRequired;
        public final Bundle mExtras;
        public final IconCompat mIcon;
        public final PendingIntent mIntent;
        public boolean mIsContextual;
        public ArrayList mRemoteInputs;
        public int mSemanticAction;
        public final boolean mShowsUserInterface;
        public final CharSequence mTitle;

        public class Api20Impl {
            private Api20Impl() {
            }

            public static Bundle getExtras(Notification.Action action) {
                return action.getExtras();
            }

            public static android.app.RemoteInput[] getRemoteInputs(Notification.Action action) {
                return action.getRemoteInputs();
            }
        }

        public class Api23Impl {
            private Api23Impl() {
            }

            public static Icon getIcon(Notification.Action action) {
                return action.getIcon();
            }
        }

        public class Api24Impl {
            private Api24Impl() {
            }

            public static boolean getAllowGeneratedReplies(Notification.Action action) {
                return action.getAllowGeneratedReplies();
            }
        }

        public class Api28Impl {
            private Api28Impl() {
            }

            public static int getSemanticAction(Notification.Action action) {
                return action.getSemanticAction();
            }
        }

        public class Api29Impl {
            private Api29Impl() {
            }

            public static boolean isContextual(Notification.Action action) {
                return action.isContextual();
            }
        }

        public class Api31Impl {
            private Api31Impl() {
            }

            public static boolean isAuthenticationRequired(Notification.Action action) {
                return action.isAuthenticationRequired();
            }
        }

        public Builder(IconCompat iconCompat, CharSequence charSequence, PendingIntent pendingIntent) {
            this(iconCompat, charSequence, pendingIntent, new Bundle(), null, true, 0, true, false, false);
        }

        public final NotificationCompat$Action build() {
            CharSequence[] charSequenceArr;
            Set set;
            if (this.mIsContextual && this.mIntent == null) {
                throw new NullPointerException("Contextual Actions must contain a valid PendingIntent");
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = this.mRemoteInputs;
            if (arrayList3 != null) {
                int size = arrayList3.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList3.get(i);
                    i++;
                    RemoteInput remoteInput = (RemoteInput) obj;
                    if (remoteInput.mAllowFreeFormTextInput || (!((charSequenceArr = remoteInput.mChoices) == null || charSequenceArr.length == 0) || (set = remoteInput.mAllowedDataTypes) == null || set.isEmpty())) {
                        arrayList2.add(remoteInput);
                    } else {
                        arrayList.add(remoteInput);
                    }
                }
            }
            return new NotificationCompat$Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, arrayList2.isEmpty() ? null : (RemoteInput[]) arrayList2.toArray(new RemoteInput[arrayList2.size()]), arrayList.isEmpty() ? null : (RemoteInput[]) arrayList.toArray(new RemoteInput[arrayList.size()]), this.mAllowGeneratedReplies, this.mSemanticAction, this.mShowsUserInterface, this.mIsContextual, this.mAuthenticationRequired);
        }

        public Builder(int i, CharSequence charSequence, PendingIntent pendingIntent) {
            this(i != 0 ? IconCompat.createWithResource(i, null, "") : null, charSequence, pendingIntent, new Bundle(), null, true, 0, true, false, false);
        }

        public Builder(NotificationCompat$Action notificationCompat$Action) {
            this(notificationCompat$Action.getIconCompat(), notificationCompat$Action.title, notificationCompat$Action.actionIntent, new Bundle(notificationCompat$Action.mExtras), notificationCompat$Action.mRemoteInputs, notificationCompat$Action.mAllowGeneratedReplies, notificationCompat$Action.mSemanticAction, notificationCompat$Action.mShowsUserInterface, notificationCompat$Action.mIsContextual, notificationCompat$Action.mAuthenticationRequired);
        }

        private Builder(IconCompat iconCompat, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, boolean z, int i, boolean z2, boolean z3, boolean z4) {
            this.mAllowGeneratedReplies = true;
            this.mShowsUserInterface = true;
            this.mIcon = iconCompat;
            this.mTitle = NotificationCompat$Builder.limitCharSequenceLength(charSequence);
            this.mIntent = pendingIntent;
            this.mExtras = bundle;
            this.mRemoteInputs = remoteInputArr == null ? null : new ArrayList(Arrays.asList(remoteInputArr));
            this.mAllowGeneratedReplies = z;
            this.mSemanticAction = i;
            this.mShowsUserInterface = z2;
            this.mIsContextual = z3;
            this.mAuthenticationRequired = z4;
        }
    }

    public NotificationCompat$Action(int i, CharSequence charSequence, PendingIntent pendingIntent) {
        this(i != 0 ? IconCompat.createWithResource(i, null, "") : null, charSequence, pendingIntent);
    }

    public final IconCompat getIconCompat() {
        int i;
        if (this.mIcon == null && (i = this.icon) != 0) {
            this.mIcon = IconCompat.createWithResource(i, null, "");
        }
        return this.mIcon;
    }

    public NotificationCompat$Action(IconCompat iconCompat, CharSequence charSequence, PendingIntent pendingIntent) {
        this(iconCompat, charSequence, pendingIntent, new Bundle(), (RemoteInput[]) null, (RemoteInput[]) null, true, 0, true, false, false);
    }

    public NotificationCompat$Action(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, RemoteInput[] remoteInputArr2, boolean z, int i2, boolean z2, boolean z3, boolean z4) {
        this(i != 0 ? IconCompat.createWithResource(i, null, "") : null, charSequence, pendingIntent, bundle, remoteInputArr, remoteInputArr2, z, i2, z2, z3, z4);
    }

    public NotificationCompat$Action(IconCompat iconCompat, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, RemoteInput[] remoteInputArr2, boolean z, int i, boolean z2, boolean z3, boolean z4) {
        this.mShowsUserInterface = true;
        this.mIcon = iconCompat;
        if (iconCompat != null) {
            int i2 = iconCompat.mType;
            if ((i2 == -1 ? ((Icon) iconCompat.mObj1).getType() : i2) == 2) {
                this.icon = iconCompat.getResId();
            }
        }
        this.title = NotificationCompat$Builder.limitCharSequenceLength(charSequence);
        this.actionIntent = pendingIntent;
        this.mExtras = bundle == null ? new Bundle() : bundle;
        this.mRemoteInputs = remoteInputArr;
        this.mAllowGeneratedReplies = z;
        this.mSemanticAction = i;
        this.mShowsUserInterface = z2;
        this.mIsContextual = z3;
        this.mAuthenticationRequired = z4;
    }
}
