package android.hardware.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.samsung.android.lock.LsConstants;
import java.util.Objects;

@SystemApi
@Deprecated
/* loaded from: classes2.dex */
public class NanoApp implements Parcelable {
    public static final Parcelable.Creator<NanoApp> CREATOR = new Parcelable.Creator<NanoApp>() { // from class: android.hardware.location.NanoApp.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NanoApp createFromParcel(Parcel parcel) {
            return new NanoApp(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NanoApp[] newArray(int i) {
            return new NanoApp[i];
        }
    };
    private final String TAG;
    private final String UNKNOWN;
    private byte[] mAppBinary;
    private long mAppId;
    private boolean mAppIdSet;
    private int mAppVersion;
    private String mName;
    private int mNeededExecMemBytes;
    private int mNeededReadMemBytes;
    private int[] mNeededSensors;
    private int mNeededWriteMemBytes;
    private int[] mOutputEvents;
    private String mPublisher;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NanoApp() {
        this(0L, (byte[]) null);
        this.mAppIdSet = false;
    }

    @Deprecated
    public NanoApp(int i, byte[] bArr) {
        this.TAG = "NanoApp";
        this.UNKNOWN = LsConstants.TAG_UNKNOWN;
        Log.w("NanoApp", "NanoApp(int, byte[]) is deprecated, please use NanoApp(long, byte[]) instead.");
    }

    public NanoApp(long j, byte[] bArr) {
        this.TAG = "NanoApp";
        this.UNKNOWN = LsConstants.TAG_UNKNOWN;
        this.mPublisher = LsConstants.TAG_UNKNOWN;
        this.mName = LsConstants.TAG_UNKNOWN;
        this.mAppId = j;
        this.mAppIdSet = true;
        this.mAppVersion = 0;
        this.mNeededReadMemBytes = 0;
        this.mNeededWriteMemBytes = 0;
        this.mNeededExecMemBytes = 0;
        this.mNeededSensors = new int[0];
        this.mOutputEvents = new int[0];
        this.mAppBinary = bArr;
    }

    public void setPublisher(String str) {
        this.mPublisher = str;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public void setAppId(long j) {
        this.mAppId = j;
        this.mAppIdSet = true;
    }

    public void setAppVersion(int i) {
        this.mAppVersion = i;
    }

    public void setNeededReadMemBytes(int i) {
        this.mNeededReadMemBytes = i;
    }

    public void setNeededWriteMemBytes(int i) {
        this.mNeededWriteMemBytes = i;
    }

    public void setNeededExecMemBytes(int i) {
        this.mNeededExecMemBytes = i;
    }

    public void setNeededSensors(int[] iArr) {
        Objects.requireNonNull(iArr, "neededSensors must not be null");
        this.mNeededSensors = iArr;
    }

    public void setOutputEvents(int[] iArr) {
        Objects.requireNonNull(iArr, "outputEvents must not be null");
        this.mOutputEvents = iArr;
    }

    public void setAppBinary(byte[] bArr) {
        Objects.requireNonNull(bArr, "appBinary must not be null");
        this.mAppBinary = bArr;
    }

    public String getPublisher() {
        return this.mPublisher;
    }

    public String getName() {
        return this.mName;
    }

    public long getAppId() {
        return this.mAppId;
    }

    public int getAppVersion() {
        return this.mAppVersion;
    }

    public int getNeededReadMemBytes() {
        return this.mNeededReadMemBytes;
    }

    public int getNeededWriteMemBytes() {
        return this.mNeededWriteMemBytes;
    }

    public int getNeededExecMemBytes() {
        return this.mNeededExecMemBytes;
    }

    public int[] getNeededSensors() {
        return this.mNeededSensors;
    }

    public int[] getOutputEvents() {
        return this.mOutputEvents;
    }

    public byte[] getAppBinary() {
        return this.mAppBinary;
    }

    private NanoApp(Parcel parcel) {
        this.TAG = "NanoApp";
        this.UNKNOWN = LsConstants.TAG_UNKNOWN;
        this.mPublisher = parcel.readString();
        this.mName = parcel.readString();
        this.mAppId = parcel.readLong();
        this.mAppVersion = parcel.readInt();
        this.mNeededReadMemBytes = parcel.readInt();
        this.mNeededWriteMemBytes = parcel.readInt();
        this.mNeededExecMemBytes = parcel.readInt();
        int[] iArr = new int[parcel.readInt()];
        this.mNeededSensors = iArr;
        parcel.readIntArray(iArr);
        int[] iArr2 = new int[parcel.readInt()];
        this.mOutputEvents = iArr2;
        parcel.readIntArray(iArr2);
        byte[] bArr = new byte[parcel.readInt()];
        this.mAppBinary = bArr;
        parcel.readByteArray(bArr);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mAppBinary == null) {
            throw new IllegalStateException("Must set non-null AppBinary for nanoapp " + this.mName);
        }
        if (!this.mAppIdSet) {
            throw new IllegalStateException("Must set AppId for nanoapp " + this.mName);
        }
        parcel.writeString(this.mPublisher);
        parcel.writeString(this.mName);
        parcel.writeLong(this.mAppId);
        parcel.writeInt(this.mAppVersion);
        parcel.writeInt(this.mNeededReadMemBytes);
        parcel.writeInt(this.mNeededWriteMemBytes);
        parcel.writeInt(this.mNeededExecMemBytes);
        parcel.writeInt(this.mNeededSensors.length);
        parcel.writeIntArray(this.mNeededSensors);
        parcel.writeInt(this.mOutputEvents.length);
        parcel.writeIntArray(this.mOutputEvents);
        parcel.writeInt(this.mAppBinary.length);
        parcel.writeByteArray(this.mAppBinary);
    }

    public String toString() {
        return ((("Id : " + this.mAppId) + ", Version : " + this.mAppVersion) + ", Name : " + this.mName) + ", Publisher : " + this.mPublisher;
    }
}
