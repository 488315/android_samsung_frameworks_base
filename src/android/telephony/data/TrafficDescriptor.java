package android.telephony.data;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class TrafficDescriptor implements Parcelable {
    public static final Parcelable.Creator<TrafficDescriptor> CREATOR = new Parcelable.Creator<TrafficDescriptor>() { // from class: android.telephony.data.TrafficDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrafficDescriptor createFromParcel(Parcel parcel) {
            return new TrafficDescriptor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrafficDescriptor[] newArray(int i) {
            return new TrafficDescriptor[i];
        }
    };
    private final String mDnn;
    private final OsAppId mOsAppId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class OsAppId {
        private final String mAppId;
        private final int mDifferentiator;
        private final UUID mOsId;
        public static final UUID ANDROID_OS_ID = UUID.fromString("97a498e3-fc92-5c94-8986-0333d06e4e47");
        private static final Set<String> ALLOWED_APP_IDS = Set.of("ENTERPRISE", "PRIORITIZE_LATENCY", "PRIORITIZE_BANDWIDTH", "CBS");

        public OsAppId(UUID uuid, String str) {
            this(uuid, str, 1);
        }

        public OsAppId(UUID uuid, String str, int i) {
            Objects.requireNonNull(uuid);
            Objects.requireNonNull(str);
            if (i < 1) {
                throw new IllegalArgumentException("Invalid differentiator " + i);
            }
            this.mOsId = uuid;
            this.mAppId = str;
            this.mDifferentiator = i;
        }

        public OsAppId(byte[] bArr) {
            try {
                ByteBuffer wrap = ByteBuffer.wrap(bArr);
                this.mOsId = new UUID(wrap.getLong(), wrap.getLong());
                int i = wrap.get();
                byte[] bArr2 = new byte[i];
                wrap.get(bArr2, 0, i);
                String str = new String(bArr2);
                Matcher matcher = Pattern.compile("[^0-9]+([0-9]+)$").matcher(new String(bArr2));
                if (matcher.find()) {
                    this.mDifferentiator = Integer.parseInt(matcher.group(1));
                    this.mAppId = str.replace(matcher.group(1), "");
                } else {
                    this.mDifferentiator = 1;
                    this.mAppId = str;
                }
            } catch (Exception unused) {
                StringBuilder sb = new StringBuilder("Failed to decode ");
                sb.append(bArr != null ? new BigInteger(1, bArr).toString(16) : null);
                throw new IllegalArgumentException(sb.toString());
            }
        }

        public UUID getOsId() {
            return this.mOsId;
        }

        public String getAppId() {
            return this.mAppId;
        }

        public int getDifferentiator() {
            return this.mDifferentiator;
        }

        public byte[] getBytes() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mAppId);
            int i = this.mDifferentiator;
            sb.append(i > 1 ? Integer.valueOf(i) : "");
            byte[] bytes = sb.toString().getBytes();
            ByteBuffer allocate = ByteBuffer.allocate(bytes.length + 17);
            allocate.putLong(this.mOsId.getMostSignificantBits());
            allocate.putLong(this.mOsId.getLeastSignificantBits());
            allocate.put((byte) bytes.length);
            allocate.put(bytes);
            return allocate.array();
        }

        public String toString() {
            return "[OsAppId: OS=" + this.mOsId + ", App=" + this.mAppId + ", differentiator=" + this.mDifferentiator + ", raw=" + new BigInteger(1, getBytes()).toString(16) + NavigationBarInflaterView.SIZE_MOD_END;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                OsAppId osAppId = (OsAppId) obj;
                if (this.mDifferentiator == osAppId.mDifferentiator && this.mOsId.equals(osAppId.mOsId) && this.mAppId.equals(osAppId.mAppId)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mOsId, this.mAppId, Integer.valueOf(this.mDifferentiator));
        }
    }

    private TrafficDescriptor(Parcel parcel) {
        this.mDnn = parcel.readString();
        byte[] createByteArray = parcel.createByteArray();
        this.mOsAppId = createByteArray != null ? new OsAppId(createByteArray) : null;
        enforceAllowedIds();
    }

    public TrafficDescriptor(String str, byte[] bArr) {
        this.mDnn = str;
        this.mOsAppId = bArr != null ? new OsAppId(bArr) : null;
        enforceAllowedIds();
    }

    private void enforceAllowedIds() {
        OsAppId osAppId = this.mOsAppId;
        if (osAppId != null && !osAppId.getOsId().equals(OsAppId.ANDROID_OS_ID)) {
            throw new IllegalArgumentException("OS id " + this.mOsAppId.getOsId() + " does not match " + OsAppId.ANDROID_OS_ID);
        }
        if (this.mOsAppId == null || OsAppId.ALLOWED_APP_IDS.contains(this.mOsAppId.getAppId())) {
            return;
        }
        throw new IllegalArgumentException("Illegal app id " + this.mOsAppId.getAppId() + ". Only allowing one of the following " + OsAppId.ALLOWED_APP_IDS);
    }

    public String getDataNetworkName() {
        return this.mDnn;
    }

    public byte[] getOsAppId() {
        OsAppId osAppId = this.mOsAppId;
        if (osAppId != null) {
            return osAppId.getBytes();
        }
        return null;
    }

    public String toString() {
        return "TrafficDescriptor={mDnn=" + this.mDnn + ", " + this.mOsAppId + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mDnn);
        OsAppId osAppId = this.mOsAppId;
        parcel.writeByteArray(osAppId != null ? osAppId.getBytes() : null);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TrafficDescriptor trafficDescriptor = (TrafficDescriptor) obj;
            if (Objects.equals(this.mDnn, trafficDescriptor.mDnn) && Objects.equals(this.mOsAppId, trafficDescriptor.mOsAppId)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mDnn, this.mOsAppId);
    }

    public static final class Builder {
        private String mDnn = null;
        private byte[] mOsAppId = null;

        public Builder setDataNetworkName(String str) {
            this.mDnn = str;
            return this;
        }

        public Builder setOsAppId(byte[] bArr) {
            this.mOsAppId = bArr;
            return this;
        }

        public TrafficDescriptor build() {
            if (this.mDnn == null && this.mOsAppId == null) {
                throw new IllegalArgumentException("DNN and OS App ID are null");
            }
            return new TrafficDescriptor(this.mDnn, this.mOsAppId);
        }
    }
}
