package android.service.notification;

import android.content.pm.VersionedPackage;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.io.IOException;

/* loaded from: classes3.dex */
public class NotificationListenerFilter implements Parcelable {
    public static final Parcelable.Creator<NotificationListenerFilter> CREATOR = new Parcelable.Creator<NotificationListenerFilter>() { // from class: android.service.notification.NotificationListenerFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationListenerFilter createFromParcel(Parcel parcel) {
            return new NotificationListenerFilter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationListenerFilter[] newArray(int i) {
            return new NotificationListenerFilter[i];
        }
    };
    private static final int DEFAULT_TYPES = 15;
    private int mAllowedNotificationTypes;
    private ArraySet<VersionedPackage> mDisallowedPackages;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NotificationListenerFilter() {
        this.mAllowedNotificationTypes = 15;
        this.mDisallowedPackages = new ArraySet<>();
    }

    public NotificationListenerFilter(int i, ArraySet<VersionedPackage> arraySet) {
        this.mAllowedNotificationTypes = i;
        this.mDisallowedPackages = arraySet;
    }

    protected NotificationListenerFilter(Parcel parcel) {
        this.mAllowedNotificationTypes = parcel.readInt();
        this.mDisallowedPackages = parcel.readArraySet(VersionedPackage.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        parcel.writeInt(this.mAllowedNotificationTypes);
        parcel.writeArraySet(this.mDisallowedPackages);
    }

    public boolean isTypeAllowed(int i) {
        return (this.mAllowedNotificationTypes & i) != 0;
    }

    public boolean areAllTypesAllowed() {
        return 15 == this.mAllowedNotificationTypes;
    }

    public boolean isPackageAllowed(VersionedPackage versionedPackage) {
        return !this.mDisallowedPackages.contains(versionedPackage);
    }

    public int getTypes() {
        return this.mAllowedNotificationTypes;
    }

    public ArraySet<VersionedPackage> getDisallowedPackages() {
        return this.mDisallowedPackages;
    }

    public void setTypes(int i) {
        this.mAllowedNotificationTypes = i;
    }

    public void setDisallowedPackages(ArraySet<VersionedPackage> arraySet) {
        this.mDisallowedPackages = arraySet;
    }

    public void removePackage(VersionedPackage versionedPackage) {
        this.mDisallowedPackages.remove(versionedPackage);
    }

    public void addPackage(VersionedPackage versionedPackage) {
        this.mDisallowedPackages.add(versionedPackage);
    }
}
