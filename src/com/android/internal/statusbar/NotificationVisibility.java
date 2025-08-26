package com.android.internal.statusbar;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class NotificationVisibility implements Parcelable {
    public static final Parcelable.Creator<NotificationVisibility> CREATOR = new Parcelable.Creator<NotificationVisibility>() { // from class: com.android.internal.statusbar.NotificationVisibility.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationVisibility createFromParcel(Parcel parcel) {
            return NotificationVisibility.obtain(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationVisibility[] newArray(int i) {
            return new NotificationVisibility[i];
        }
    };
    private static final int MAX_POOL_SIZE = 25;
    private static final String TAG = "NoViz";
    private static int sNexrId;
    public int count;
    int id;
    public String key;
    public NotificationLocation location;
    public int rank;
    public boolean visible;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void recycle() {
    }

    public enum NotificationLocation {
        LOCATION_UNKNOWN(0),
        LOCATION_FIRST_HEADS_UP(1),
        LOCATION_HIDDEN_TOP(2),
        LOCATION_MAIN_AREA(3),
        LOCATION_BOTTOM_STACK_PEEKING(4),
        LOCATION_BOTTOM_STACK_HIDDEN(5),
        LOCATION_GONE(6);

        private final int mMetricsEventNotificationLocation;

        NotificationLocation(int i) {
            this.mMetricsEventNotificationLocation = i;
        }

        public int toMetricsEventEnum() {
            return this.mMetricsEventNotificationLocation;
        }
    }

    private NotificationVisibility() {
        this.visible = true;
        int i = sNexrId;
        sNexrId = i + 1;
        this.id = i;
    }

    private NotificationVisibility(String str, int i, int i2, boolean z, NotificationLocation notificationLocation) {
        this();
        this.key = str;
        this.rank = i;
        this.count = i2;
        this.visible = z;
        this.location = notificationLocation;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NotificationVisibility(id=");
        sb.append(this.id);
        sb.append(" key=");
        sb.append(this.key);
        sb.append(" rank=");
        sb.append(this.rank);
        sb.append(" count=");
        sb.append(this.count);
        sb.append(this.visible ? " visible" : "");
        sb.append(" location=");
        sb.append(this.location.name());
        sb.append(" )");
        return sb.toString();
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public NotificationVisibility m8293clone() {
        return obtain(this.key, this.rank, this.count, this.visible, this.location);
    }

    public int hashCode() {
        String str = this.key;
        if (str == null) {
            return 0;
        }
        return str.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof NotificationVisibility) {
            NotificationVisibility notificationVisibility = (NotificationVisibility) obj;
            String str = this.key;
            if ((str == null && notificationVisibility.key == null) || str.equals(notificationVisibility.key)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.key);
        parcel.writeInt(this.rank);
        parcel.writeInt(this.count);
        parcel.writeInt(this.visible ? 1 : 0);
        parcel.writeString(this.location.name());
    }

    private void readFromParcel(Parcel parcel) {
        this.key = parcel.readString();
        this.rank = parcel.readInt();
        this.count = parcel.readInt();
        this.visible = parcel.readInt() != 0;
        this.location = NotificationLocation.valueOf(parcel.readString());
    }

    public static NotificationVisibility obtain(String str, int i, int i2, boolean z) {
        return obtain(str, i, i2, z, NotificationLocation.LOCATION_UNKNOWN);
    }

    public static NotificationVisibility obtain(String str, int i, int i2, boolean z, NotificationLocation notificationLocation) {
        NotificationVisibility notificationVisibilityObtain = obtain();
        notificationVisibilityObtain.key = str;
        notificationVisibilityObtain.rank = i;
        notificationVisibilityObtain.count = i2;
        notificationVisibilityObtain.visible = z;
        notificationVisibilityObtain.location = notificationLocation;
        return notificationVisibilityObtain;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static NotificationVisibility obtain(Parcel parcel) {
        NotificationVisibility notificationVisibilityObtain = obtain();
        notificationVisibilityObtain.readFromParcel(parcel);
        return notificationVisibilityObtain;
    }

    private static NotificationVisibility obtain() {
        return new NotificationVisibility();
    }
}
