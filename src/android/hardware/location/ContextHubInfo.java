package android.hardware.location;

import android.annotation.SystemApi;
import android.chre.flags.Flags;
import android.hardware.contexthub.V1_0.ContextHub;
import android.media.MediaMetrics;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.proto.ProtoOutputStream;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public class ContextHubInfo implements Parcelable {
    public static final Parcelable.Creator<ContextHubInfo> CREATOR = new Parcelable.Creator<ContextHubInfo>() { // from class: android.hardware.location.ContextHubInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextHubInfo createFromParcel(Parcel parcel) {
            return new ContextHubInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextHubInfo[] newArray(int i) {
            return new ContextHubInfo[i];
        }
    };
    private byte mChreApiMajorVersion;
    private byte mChreApiMinorVersion;
    private short mChrePatchVersion;
    private long mChrePlatformId;
    private int mId;
    private int mMaxPacketLengthBytes;
    private MemoryRegion[] mMemoryRegions;
    private String mName;
    private float mPeakMips;
    private float mPeakPowerDrawMw;
    private int mPlatformVersion;
    private float mSleepPowerDrawMw;
    private float mStoppedPowerDrawMw;
    private int[] mSupportedSensors;
    private boolean mSupportsReliableMessages;
    private String mToolchain;
    private int mToolchainVersion;
    private String mVendor;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ContextHubInfo() {
    }

    public ContextHubInfo(ContextHub contextHub) {
        this.mId = contextHub.hubId;
        this.mName = contextHub.name;
        this.mVendor = contextHub.f2vendor;
        this.mToolchain = contextHub.toolchain;
        this.mPlatformVersion = contextHub.platformVersion;
        this.mToolchainVersion = contextHub.toolchainVersion;
        this.mPeakMips = contextHub.peakMips;
        this.mStoppedPowerDrawMw = contextHub.stoppedPowerDrawMw;
        this.mSleepPowerDrawMw = contextHub.sleepPowerDrawMw;
        this.mPeakPowerDrawMw = contextHub.peakPowerDrawMw;
        this.mMaxPacketLengthBytes = contextHub.maxSupportedMsgLen;
        this.mSupportsReliableMessages = false;
        this.mChrePlatformId = contextHub.chrePlatformId;
        this.mChreApiMajorVersion = contextHub.chreApiMajorVersion;
        this.mChreApiMinorVersion = contextHub.chreApiMinorVersion;
        this.mChrePatchVersion = contextHub.chrePatchVersion;
        this.mSupportedSensors = new int[0];
        this.mMemoryRegions = new MemoryRegion[0];
    }

    public ContextHubInfo(android.hardware.contexthub.ContextHubInfo contextHubInfo) {
        this.mId = contextHubInfo.id;
        this.mName = contextHubInfo.name;
        this.mVendor = contextHubInfo.f1vendor;
        this.mToolchain = contextHubInfo.toolchain;
        this.mPlatformVersion = 0;
        this.mToolchainVersion = 0;
        this.mPeakMips = contextHubInfo.peakMips;
        this.mStoppedPowerDrawMw = 0.0f;
        this.mSleepPowerDrawMw = 0.0f;
        this.mPeakPowerDrawMw = 0.0f;
        this.mMaxPacketLengthBytes = contextHubInfo.maxSupportedMessageLengthBytes;
        this.mSupportsReliableMessages = contextHubInfo.supportsReliableMessages;
        this.mChrePlatformId = contextHubInfo.chrePlatformId;
        this.mChreApiMajorVersion = contextHubInfo.chreApiMajorVersion;
        this.mChreApiMinorVersion = contextHubInfo.chreApiMinorVersion;
        this.mChrePatchVersion = (short) contextHubInfo.chrePatchVersion;
        this.mSupportedSensors = new int[0];
        this.mMemoryRegions = new MemoryRegion[0];
    }

    public int getMaxPacketLengthBytes() {
        return this.mMaxPacketLengthBytes;
    }

    public boolean supportsReliableMessages() {
        return this.mSupportsReliableMessages;
    }

    public int getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public String getVendor() {
        return this.mVendor;
    }

    public String getToolchain() {
        return this.mToolchain;
    }

    public int getPlatformVersion() {
        return this.mPlatformVersion;
    }

    public int getStaticSwVersion() {
        return Short.toUnsignedInt(this.mChrePatchVersion) | (Byte.toUnsignedInt(this.mChreApiMajorVersion) << 24) | (Byte.toUnsignedInt(this.mChreApiMinorVersion) << 16);
    }

    public int getToolchainVersion() {
        return this.mToolchainVersion;
    }

    public float getPeakMips() {
        return this.mPeakMips;
    }

    public float getStoppedPowerDrawMw() {
        return this.mStoppedPowerDrawMw;
    }

    public float getSleepPowerDrawMw() {
        return this.mSleepPowerDrawMw;
    }

    public float getPeakPowerDrawMw() {
        return this.mPeakPowerDrawMw;
    }

    public int[] getSupportedSensors() {
        int[] iArr = this.mSupportedSensors;
        return Arrays.copyOf(iArr, iArr.length);
    }

    public MemoryRegion[] getMemoryRegions() {
        MemoryRegion[] memoryRegionArr = this.mMemoryRegions;
        return (MemoryRegion[]) Arrays.copyOf(memoryRegionArr, memoryRegionArr.length);
    }

    public long getChrePlatformId() {
        return this.mChrePlatformId;
    }

    public byte getChreApiMajorVersion() {
        return this.mChreApiMajorVersion;
    }

    public byte getChreApiMinorVersion() {
        return this.mChreApiMinorVersion;
    }

    public short getChrePatchVersion() {
        return this.mChrePatchVersion;
    }

    public String toString() {
        return "ID/handle : " + this.mId + ", Name : " + this.mName + "\n\tVendor : " + this.mVendor + ", Toolchain : " + this.mToolchain + ", Toolchain version: 0x" + Integer.toHexString(this.mToolchainVersion) + "\n\tPlatformVersion : 0x" + Integer.toHexString(this.mPlatformVersion) + ", SwVersion : " + Byte.toUnsignedInt(this.mChreApiMajorVersion) + MediaMetrics.SEPARATOR + Byte.toUnsignedInt(this.mChreApiMinorVersion) + MediaMetrics.SEPARATOR + Short.toUnsignedInt(this.mChrePatchVersion) + ", CHRE platform ID: 0x" + Long.toHexString(this.mChrePlatformId) + "\n\tPeakMips : " + this.mPeakMips + ", StoppedPowerDraw : " + this.mStoppedPowerDrawMw + " mW, PeakPowerDraw : " + this.mPeakPowerDrawMw + " mW, MaxPacketLength : " + this.mMaxPacketLengthBytes + " Bytes, SupportsReliableMessages : " + this.mSupportsReliableMessages;
    }

    public void dump(ProtoOutputStream protoOutputStream) {
        protoOutputStream.write(1120986464257L, this.mId);
        protoOutputStream.write(1138166333442L, this.mName);
        protoOutputStream.write(1138166333443L, this.mVendor);
        protoOutputStream.write(1138166333444L, this.mToolchain);
        protoOutputStream.write(1120986464261L, this.mPlatformVersion);
        protoOutputStream.write(1120986464262L, getStaticSwVersion());
        protoOutputStream.write(1120986464263L, this.mToolchainVersion);
        protoOutputStream.write(1112396529672L, this.mChrePlatformId);
        protoOutputStream.write(1108101562377L, this.mPeakMips);
        protoOutputStream.write(1108101562378L, this.mStoppedPowerDrawMw);
        protoOutputStream.write(1108101562379L, this.mSleepPowerDrawMw);
        protoOutputStream.write(1108101562380L, this.mPeakPowerDrawMw);
        protoOutputStream.write(1120986464269L, this.mMaxPacketLengthBytes);
        protoOutputStream.write(1120986464270L, this.mSupportsReliableMessages);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ContextHubInfo) {
            ContextHubInfo contextHubInfo = (ContextHubInfo) obj;
            if (contextHubInfo.getId() == this.mId && contextHubInfo.getName().equals(this.mName) && contextHubInfo.getVendor().equals(this.mVendor) && contextHubInfo.getToolchain().equals(this.mToolchain) && contextHubInfo.getToolchainVersion() == this.mToolchainVersion && contextHubInfo.getStaticSwVersion() == getStaticSwVersion() && contextHubInfo.getChrePlatformId() == this.mChrePlatformId && contextHubInfo.getPeakMips() == this.mPeakMips && contextHubInfo.getStoppedPowerDrawMw() == this.mStoppedPowerDrawMw && contextHubInfo.getSleepPowerDrawMw() == this.mSleepPowerDrawMw && contextHubInfo.getPeakPowerDrawMw() == this.mPeakPowerDrawMw && contextHubInfo.getMaxPacketLengthBytes() == this.mMaxPacketLengthBytes && contextHubInfo.supportsReliableMessages() == this.mSupportsReliableMessages && Arrays.equals(contextHubInfo.getSupportedSensors(), this.mSupportedSensors) && Arrays.equals(contextHubInfo.getMemoryRegions(), this.mMemoryRegions)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        if (!Flags.fixApiCheck()) {
            return super.hashCode();
        }
        return Objects.hash(Integer.valueOf(this.mId), this.mName, this.mVendor, this.mToolchain, Integer.valueOf(this.mToolchainVersion), Integer.valueOf(getStaticSwVersion()), Long.valueOf(this.mChrePlatformId), Float.valueOf(this.mPeakMips), Float.valueOf(this.mStoppedPowerDrawMw), Float.valueOf(this.mSleepPowerDrawMw), Float.valueOf(this.mPeakPowerDrawMw), Integer.valueOf(this.mMaxPacketLengthBytes), Boolean.valueOf(this.mSupportsReliableMessages), Integer.valueOf(Arrays.hashCode(this.mSupportedSensors)), Integer.valueOf(Arrays.hashCode(this.mMemoryRegions)));
    }

    private ContextHubInfo(Parcel parcel) {
        this.mId = parcel.readInt();
        this.mName = parcel.readString();
        this.mVendor = parcel.readString();
        this.mToolchain = parcel.readString();
        this.mPlatformVersion = parcel.readInt();
        this.mToolchainVersion = parcel.readInt();
        this.mPeakMips = parcel.readFloat();
        this.mStoppedPowerDrawMw = parcel.readFloat();
        this.mSleepPowerDrawMw = parcel.readFloat();
        this.mPeakPowerDrawMw = parcel.readFloat();
        this.mMaxPacketLengthBytes = parcel.readInt();
        this.mChrePlatformId = parcel.readLong();
        this.mChreApiMajorVersion = parcel.readByte();
        this.mChreApiMinorVersion = parcel.readByte();
        this.mChrePatchVersion = (short) parcel.readInt();
        int[] iArr = new int[parcel.readInt()];
        this.mSupportedSensors = iArr;
        parcel.readIntArray(iArr);
        this.mMemoryRegions = (MemoryRegion[]) parcel.createTypedArray(MemoryRegion.CREATOR);
        this.mSupportsReliableMessages = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mName);
        parcel.writeString(this.mVendor);
        parcel.writeString(this.mToolchain);
        parcel.writeInt(this.mPlatformVersion);
        parcel.writeInt(this.mToolchainVersion);
        parcel.writeFloat(this.mPeakMips);
        parcel.writeFloat(this.mStoppedPowerDrawMw);
        parcel.writeFloat(this.mSleepPowerDrawMw);
        parcel.writeFloat(this.mPeakPowerDrawMw);
        parcel.writeInt(this.mMaxPacketLengthBytes);
        parcel.writeLong(this.mChrePlatformId);
        parcel.writeByte(this.mChreApiMajorVersion);
        parcel.writeByte(this.mChreApiMinorVersion);
        parcel.writeInt(this.mChrePatchVersion);
        parcel.writeInt(this.mSupportedSensors.length);
        parcel.writeIntArray(this.mSupportedSensors);
        parcel.writeTypedArray(this.mMemoryRegions, i);
        parcel.writeBoolean(this.mSupportsReliableMessages);
    }
}
