package android.companion;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.provider.OneTimeUseBuilder;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class BluetoothDeviceFilter implements DeviceFilter<BluetoothDevice> {
    public static final Parcelable.Creator<BluetoothDeviceFilter> CREATOR = new Parcelable.Creator<BluetoothDeviceFilter>() { // from class: android.companion.BluetoothDeviceFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothDeviceFilter createFromParcel(Parcel parcel) {
            return new BluetoothDeviceFilter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothDeviceFilter[] newArray(int i) {
            return new BluetoothDeviceFilter[i];
        }
    };
    private final String mAddress;
    private final Pattern mNamePattern;
    private final List<ParcelUuid> mServiceUuidMasks;
    private final List<ParcelUuid> mServiceUuids;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.companion.DeviceFilter
    public int getMediumType() {
        return 0;
    }

    private BluetoothDeviceFilter(Pattern pattern, String str, List<ParcelUuid> list, List<ParcelUuid> list2) {
        this.mNamePattern = pattern;
        this.mAddress = str;
        this.mServiceUuids = CollectionUtils.emptyIfNull(list);
        this.mServiceUuidMasks = CollectionUtils.emptyIfNull(list2);
    }

    private BluetoothDeviceFilter(Parcel parcel) {
        this(BluetoothDeviceFilterUtils.patternFromString(parcel.readString()), parcel.readString(), readUuids(parcel), readUuids(parcel));
    }

    private static List<ParcelUuid> readUuids(Parcel parcel) {
        return parcel.readParcelableList(new ArrayList(), ParcelUuid.class.getClassLoader(), ParcelUuid.class);
    }

    @Override // android.companion.DeviceFilter
    public boolean matches(BluetoothDevice bluetoothDevice) {
        return BluetoothDeviceFilterUtils.matchesAddress(this.mAddress, bluetoothDevice) && BluetoothDeviceFilterUtils.matchesServiceUuids(this.mServiceUuids, this.mServiceUuidMasks, bluetoothDevice) && BluetoothDeviceFilterUtils.matchesName(getNamePattern(), bluetoothDevice);
    }

    @Override // android.companion.DeviceFilter
    public String getDeviceDisplayName(BluetoothDevice bluetoothDevice) {
        return BluetoothDeviceFilterUtils.getDeviceDisplayNameInternal(bluetoothDevice);
    }

    public Pattern getNamePattern() {
        return this.mNamePattern;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public List<ParcelUuid> getServiceUuids() {
        return this.mServiceUuids;
    }

    public List<ParcelUuid> getServiceUuidMasks() {
        return this.mServiceUuidMasks;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(BluetoothDeviceFilterUtils.patternToString(getNamePattern()));
        parcel.writeString(this.mAddress);
        parcel.writeParcelableList(this.mServiceUuids, i);
        parcel.writeParcelableList(this.mServiceUuidMasks, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            BluetoothDeviceFilter bluetoothDeviceFilter = (BluetoothDeviceFilter) obj;
            if (Objects.equals(this.mNamePattern, bluetoothDeviceFilter.mNamePattern) && Objects.equals(this.mAddress, bluetoothDeviceFilter.mAddress) && Objects.equals(this.mServiceUuids, bluetoothDeviceFilter.mServiceUuids) && Objects.equals(this.mServiceUuidMasks, bluetoothDeviceFilter.mServiceUuidMasks)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mNamePattern, this.mAddress, this.mServiceUuids, this.mServiceUuidMasks);
    }

    public String toString() {
        return "BluetoothDeviceFilter{mNamePattern=" + this.mNamePattern + ", mAddress='" + this.mAddress + "', mServiceUuids=" + this.mServiceUuids + ", mServiceUuidMasks=" + this.mServiceUuidMasks + '}';
    }

    public static final class Builder extends OneTimeUseBuilder<BluetoothDeviceFilter> {
        private String mAddress;
        private Pattern mNamePattern;
        private ArrayList<ParcelUuid> mServiceUuid;
        private ArrayList<ParcelUuid> mServiceUuidMask;

        public Builder setNamePattern(Pattern pattern) {
            checkNotUsed();
            this.mNamePattern = pattern;
            return this;
        }

        public Builder setAddress(String str) {
            checkNotUsed();
            this.mAddress = str;
            return this;
        }

        public Builder addServiceUuid(ParcelUuid parcelUuid, ParcelUuid parcelUuid2) {
            checkNotUsed();
            this.mServiceUuid = ArrayUtils.add(this.mServiceUuid, parcelUuid);
            this.mServiceUuidMask = ArrayUtils.add(this.mServiceUuidMask, parcelUuid2);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.provider.OneTimeUseBuilder
        public BluetoothDeviceFilter build() {
            markUsed();
            return new BluetoothDeviceFilter(this.mNamePattern, this.mAddress, this.mServiceUuid, this.mServiceUuidMask);
        }
    }
}
