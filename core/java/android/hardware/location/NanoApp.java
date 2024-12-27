package android.hardware.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Objects;

@SystemApi
@Deprecated
public class NanoApp implements Parcelable {
    public static final Parcelable.Creator<NanoApp> CREATOR =
            new Parcelable.Creator<NanoApp>() { // from class: android.hardware.location.NanoApp.1
                @Override // android.os.Parcelable.Creator
                public NanoApp createFromParcel(Parcel in) {
                    return new NanoApp(in);
                }

                @Override // android.os.Parcelable.Creator
                public NanoApp[] newArray(int size) {
                    return new NanoApp[size];
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

    public NanoApp() {
        this(0L, (byte[]) null);
        this.mAppIdSet = false;
    }

    @Deprecated
    public NanoApp(int appId, byte[] appBinary) {
        this.TAG = "NanoApp";
        this.UNKNOWN = "Unknown";
        Log.w(
                "NanoApp",
                "NanoApp(int, byte[]) is deprecated, please use NanoApp(long, byte[]) instead.");
    }

    public NanoApp(long appId, byte[] appBinary) {
        this.TAG = "NanoApp";
        this.UNKNOWN = "Unknown";
        this.mPublisher = "Unknown";
        this.mName = "Unknown";
        this.mAppId = appId;
        this.mAppIdSet = true;
        this.mAppVersion = 0;
        this.mNeededReadMemBytes = 0;
        this.mNeededWriteMemBytes = 0;
        this.mNeededExecMemBytes = 0;
        this.mNeededSensors = new int[0];
        this.mOutputEvents = new int[0];
        this.mAppBinary = appBinary;
    }

    public void setPublisher(String publisher) {
        this.mPublisher = publisher;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setAppId(long appId) {
        this.mAppId = appId;
        this.mAppIdSet = true;
    }

    public void setAppVersion(int appVersion) {
        this.mAppVersion = appVersion;
    }

    public void setNeededReadMemBytes(int neededReadMemBytes) {
        this.mNeededReadMemBytes = neededReadMemBytes;
    }

    public void setNeededWriteMemBytes(int neededWriteMemBytes) {
        this.mNeededWriteMemBytes = neededWriteMemBytes;
    }

    public void setNeededExecMemBytes(int neededExecMemBytes) {
        this.mNeededExecMemBytes = neededExecMemBytes;
    }

    public void setNeededSensors(int[] neededSensors) {
        Objects.requireNonNull(neededSensors, "neededSensors must not be null");
        this.mNeededSensors = neededSensors;
    }

    public void setOutputEvents(int[] outputEvents) {
        Objects.requireNonNull(outputEvents, "outputEvents must not be null");
        this.mOutputEvents = outputEvents;
    }

    public void setAppBinary(byte[] appBinary) {
        Objects.requireNonNull(appBinary, "appBinary must not be null");
        this.mAppBinary = appBinary;
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

    private NanoApp(Parcel in) {
        this.TAG = "NanoApp";
        this.UNKNOWN = "Unknown";
        this.mPublisher = in.readString();
        this.mName = in.readString();
        this.mAppId = in.readLong();
        this.mAppVersion = in.readInt();
        this.mNeededReadMemBytes = in.readInt();
        this.mNeededWriteMemBytes = in.readInt();
        this.mNeededExecMemBytes = in.readInt();
        int mNeededSensorsLength = in.readInt();
        this.mNeededSensors = new int[mNeededSensorsLength];
        in.readIntArray(this.mNeededSensors);
        int mOutputEventsLength = in.readInt();
        this.mOutputEvents = new int[mOutputEventsLength];
        in.readIntArray(this.mOutputEvents);
        int binaryLength = in.readInt();
        this.mAppBinary = new byte[binaryLength];
        in.readByteArray(this.mAppBinary);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        if (this.mAppBinary == null) {
            throw new IllegalStateException(
                    "Must set non-null AppBinary for nanoapp " + this.mName);
        }
        if (!this.mAppIdSet) {
            throw new IllegalStateException("Must set AppId for nanoapp " + this.mName);
        }
        out.writeString(this.mPublisher);
        out.writeString(this.mName);
        out.writeLong(this.mAppId);
        out.writeInt(this.mAppVersion);
        out.writeInt(this.mNeededReadMemBytes);
        out.writeInt(this.mNeededWriteMemBytes);
        out.writeInt(this.mNeededExecMemBytes);
        out.writeInt(this.mNeededSensors.length);
        out.writeIntArray(this.mNeededSensors);
        out.writeInt(this.mOutputEvents.length);
        out.writeIntArray(this.mOutputEvents);
        out.writeInt(this.mAppBinary.length);
        out.writeByteArray(this.mAppBinary);
    }

    public String toString() {
        String retVal = "Id : " + this.mAppId;
        return ((retVal + ", Version : " + this.mAppVersion) + ", Name : " + this.mName)
                + ", Publisher : "
                + this.mPublisher;
    }
}
