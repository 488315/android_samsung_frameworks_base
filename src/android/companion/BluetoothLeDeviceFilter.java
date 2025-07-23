package android.companion;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.OneTimeUseBuilder;
import android.text.TextUtils;
import com.android.internal.util.BitUtils;
import com.android.internal.util.ObjectUtils;
import com.android.internal.util.Preconditions;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;
import libcore.util.HexEncoding;

/* loaded from: classes.dex */
public final class BluetoothLeDeviceFilter implements DeviceFilter<ScanResult> {
    public static final Parcelable.Creator<BluetoothLeDeviceFilter> CREATOR = new Parcelable.Creator<BluetoothLeDeviceFilter>() { // from class: android.companion.BluetoothLeDeviceFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothLeDeviceFilter createFromParcel(Parcel parcel) {
            Builder scanFilter = new Builder().setNamePattern(BluetoothDeviceFilterUtils.patternFromString(parcel.readString())).setScanFilter((ScanFilter) parcel.readParcelable(null, ScanFilter.class));
            byte[] createByteArray = parcel.createByteArray();
            byte[] createByteArray2 = parcel.createByteArray();
            if (createByteArray != null) {
                scanFilter.setRawDataFilter(createByteArray, createByteArray2);
            }
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            if (readString != null) {
                if (readInt >= 0) {
                    scanFilter.setRenameFromBytes(readString, readString2, readInt, readInt2, readBoolean ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
                } else {
                    scanFilter.setRenameFromName(readString, readString2, readInt3, readInt4);
                }
            }
            return scanFilter.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothLeDeviceFilter[] newArray(int i) {
            return new BluetoothLeDeviceFilter[i];
        }
    };
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "CDM_BluetoothLeDeviceFilter";
    private static final int RENAME_PREFIX_LENGTH_LIMIT = 10;
    private final Pattern mNamePattern;
    private final byte[] mRawDataFilter;
    private final byte[] mRawDataFilterMask;
    private final int mRenameBytesFrom;
    private final int mRenameBytesLength;
    private final boolean mRenameBytesReverseOrder;
    private final int mRenameNameFrom;
    private final int mRenameNameLength;
    private final String mRenamePrefix;
    private final String mRenameSuffix;
    private final ScanFilter mScanFilter;

    public static int getRenamePrefixLengthLimit() {
        return 10;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.companion.DeviceFilter
    public int getMediumType() {
        return 1;
    }

    private BluetoothLeDeviceFilter(Pattern pattern, ScanFilter scanFilter, byte[] bArr, byte[] bArr2, String str, String str2, int i, int i2, int i3, int i4, boolean z) {
        this.mNamePattern = pattern;
        this.mScanFilter = (ScanFilter) ObjectUtils.firstNotNull(scanFilter, new ScanFilter.Builder().build());
        this.mRawDataFilter = bArr;
        this.mRawDataFilterMask = bArr2;
        this.mRenamePrefix = str;
        this.mRenameSuffix = str2;
        this.mRenameBytesFrom = i;
        this.mRenameBytesLength = i2;
        this.mRenameNameFrom = i3;
        this.mRenameNameLength = i4;
        this.mRenameBytesReverseOrder = z;
    }

    public Pattern getNamePattern() {
        return this.mNamePattern;
    }

    public ScanFilter getScanFilter() {
        return this.mScanFilter;
    }

    public byte[] getRawDataFilter() {
        return this.mRawDataFilter;
    }

    public byte[] getRawDataFilterMask() {
        return this.mRawDataFilterMask;
    }

    public String getRenamePrefix() {
        return this.mRenamePrefix;
    }

    public String getRenameSuffix() {
        return this.mRenameSuffix;
    }

    public int getRenameBytesFrom() {
        return this.mRenameBytesFrom;
    }

    public int getRenameBytesLength() {
        return this.mRenameBytesLength;
    }

    public boolean isRenameBytesReverseOrder() {
        return this.mRenameBytesReverseOrder;
    }

    @Override // android.companion.DeviceFilter
    public String getDeviceDisplayName(ScanResult scanResult) {
        if (this.mRenameBytesFrom < 0 && this.mRenameNameFrom < 0) {
            return BluetoothDeviceFilterUtils.getDeviceDisplayNameInternal(scanResult.getDevice());
        }
        StringBuilder sb = new StringBuilder(TextUtils.emptyIfNull(this.mRenamePrefix));
        if (this.mRenameBytesFrom >= 0) {
            byte[] bytes = scanResult.getScanRecord().getBytes();
            int i = this.mRenameBytesFrom;
            int i2 = (this.mRenameBytesLength + i) - 1;
            boolean z = this.mRenameBytesReverseOrder;
            int i3 = z ? -1 : 1;
            for (int i4 = z ? i2 : i; i <= i4 && i4 <= i2; i4 += i3) {
                sb.append(HexEncoding.encodeToString(bytes[i4], true));
            }
        } else {
            String deviceDisplayNameInternal = BluetoothDeviceFilterUtils.getDeviceDisplayNameInternal(scanResult.getDevice());
            int i5 = this.mRenameNameFrom;
            sb.append(deviceDisplayNameInternal.substring(i5, this.mRenameNameLength + i5));
        }
        sb.append(TextUtils.emptyIfNull(this.mRenameSuffix));
        return sb.toString();
    }

    @Override // android.companion.DeviceFilter
    public boolean matches(ScanResult scanResult) {
        BluetoothDevice device = scanResult.getDevice();
        if (getScanFilter().matches(scanResult) && BluetoothDeviceFilterUtils.matchesName(getNamePattern(), device)) {
            return this.mRawDataFilter == null || BitUtils.maskedEquals(scanResult.getScanRecord().getBytes(), this.mRawDataFilter, this.mRawDataFilterMask);
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            BluetoothLeDeviceFilter bluetoothLeDeviceFilter = (BluetoothLeDeviceFilter) obj;
            if (this.mRenameBytesFrom == bluetoothLeDeviceFilter.mRenameBytesFrom && this.mRenameBytesLength == bluetoothLeDeviceFilter.mRenameBytesLength && this.mRenameNameFrom == bluetoothLeDeviceFilter.mRenameNameFrom && this.mRenameNameLength == bluetoothLeDeviceFilter.mRenameNameLength && this.mRenameBytesReverseOrder == bluetoothLeDeviceFilter.mRenameBytesReverseOrder && Objects.equals(this.mNamePattern, bluetoothLeDeviceFilter.mNamePattern) && Objects.equals(this.mScanFilter, bluetoothLeDeviceFilter.mScanFilter) && Arrays.equals(this.mRawDataFilter, bluetoothLeDeviceFilter.mRawDataFilter) && Arrays.equals(this.mRawDataFilterMask, bluetoothLeDeviceFilter.mRawDataFilterMask) && Objects.equals(this.mRenamePrefix, bluetoothLeDeviceFilter.mRenamePrefix) && Objects.equals(this.mRenameSuffix, bluetoothLeDeviceFilter.mRenameSuffix)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mNamePattern, this.mScanFilter, Integer.valueOf(Arrays.hashCode(this.mRawDataFilter)), Integer.valueOf(Arrays.hashCode(this.mRawDataFilterMask)), this.mRenamePrefix, this.mRenameSuffix, Integer.valueOf(this.mRenameBytesFrom), Integer.valueOf(this.mRenameBytesLength), Integer.valueOf(this.mRenameNameFrom), Integer.valueOf(this.mRenameNameLength), Boolean.valueOf(this.mRenameBytesReverseOrder));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(BluetoothDeviceFilterUtils.patternToString(getNamePattern()));
        parcel.writeParcelable(this.mScanFilter, i);
        parcel.writeByteArray(this.mRawDataFilter);
        parcel.writeByteArray(this.mRawDataFilterMask);
        parcel.writeString(this.mRenamePrefix);
        parcel.writeString(this.mRenameSuffix);
        parcel.writeInt(this.mRenameBytesFrom);
        parcel.writeInt(this.mRenameBytesLength);
        parcel.writeInt(this.mRenameNameFrom);
        parcel.writeInt(this.mRenameNameLength);
        parcel.writeBoolean(this.mRenameBytesReverseOrder);
    }

    public String toString() {
        return "BluetoothLEDeviceFilter{mNamePattern=" + this.mNamePattern + ", mScanFilter=" + this.mScanFilter + ", mRawDataFilter=" + Arrays.toString(this.mRawDataFilter) + ", mRawDataFilterMask=" + Arrays.toString(this.mRawDataFilterMask) + ", mRenamePrefix='" + this.mRenamePrefix + "', mRenameSuffix='" + this.mRenameSuffix + "', mRenameBytesFrom=" + this.mRenameBytesFrom + ", mRenameBytesLength=" + this.mRenameBytesLength + ", mRenameNameFrom=" + this.mRenameNameFrom + ", mRenameNameLength=" + this.mRenameNameLength + ", mRenameBytesReverseOrder=" + this.mRenameBytesReverseOrder + '}';
    }

    public static final class Builder extends OneTimeUseBuilder<BluetoothLeDeviceFilter> {
        private Pattern mNamePattern;
        private byte[] mRawDataFilter;
        private byte[] mRawDataFilterMask;
        private int mRenameBytesLength;
        private int mRenameNameLength;
        private String mRenamePrefix;
        private String mRenameSuffix;
        private ScanFilter mScanFilter;
        private int mRenameBytesFrom = -1;
        private int mRenameNameFrom = -1;
        private boolean mRenameBytesReverseOrder = false;

        public Builder setNamePattern(Pattern pattern) {
            checkNotUsed();
            this.mNamePattern = pattern;
            return this;
        }

        public Builder setScanFilter(ScanFilter scanFilter) {
            checkNotUsed();
            this.mScanFilter = scanFilter;
            return this;
        }

        public Builder setRawDataFilter(byte[] bArr, byte[] bArr2) {
            checkNotUsed();
            Objects.requireNonNull(bArr);
            Preconditions.checkArgument(bArr2 == null || bArr.length == bArr2.length, "Mask and filter should be the same length");
            this.mRawDataFilter = bArr;
            this.mRawDataFilterMask = bArr2;
            return this;
        }

        public Builder setRenameFromBytes(String str, String str2, int i, int i2, ByteOrder byteOrder) {
            checkRenameNotSet();
            checkRangeNotEmpty(i2);
            this.mRenameBytesFrom = i;
            this.mRenameBytesLength = i2;
            this.mRenameBytesReverseOrder = byteOrder == ByteOrder.LITTLE_ENDIAN;
            return setRename(str, str2);
        }

        public Builder setRenameFromName(String str, String str2, int i, int i2) {
            checkRenameNotSet();
            checkRangeNotEmpty(i2);
            this.mRenameNameFrom = i;
            this.mRenameNameLength = i2;
            this.mRenameBytesReverseOrder = false;
            return setRename(str, str2);
        }

        private void checkRenameNotSet() {
            Preconditions.checkState(this.mRenamePrefix == null, "Renaming rule can only be set once");
        }

        private void checkRangeNotEmpty(int i) {
            Preconditions.checkArgument(i > 0, "Range must be non-empty");
        }

        private Builder setRename(String str, String str2) {
            checkNotUsed();
            Preconditions.checkArgument(TextUtils.length(str) <= BluetoothLeDeviceFilter.getRenamePrefixLengthLimit(), "Prefix is too long");
            this.mRenamePrefix = str;
            this.mRenameSuffix = str2;
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.provider.OneTimeUseBuilder
        public BluetoothLeDeviceFilter build() {
            markUsed();
            return new BluetoothLeDeviceFilter(this.mNamePattern, this.mScanFilter, this.mRawDataFilter, this.mRawDataFilterMask, this.mRenamePrefix, this.mRenameSuffix, this.mRenameBytesFrom, this.mRenameBytesLength, this.mRenameNameFrom, this.mRenameNameLength, this.mRenameBytesReverseOrder);
        }
    }
}
