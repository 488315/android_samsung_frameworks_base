package android.hardware.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SystemApi
public final class NanoAppState implements Parcelable {
    public static final Parcelable.Creator<NanoAppState> CREATOR =
            new Parcelable.Creator<
                    NanoAppState>() { // from class: android.hardware.location.NanoAppState.1
                @Override // android.os.Parcelable.Creator
                public NanoAppState createFromParcel(Parcel in) {
                    return new NanoAppState(in);
                }

                @Override // android.os.Parcelable.Creator
                public NanoAppState[] newArray(int size) {
                    return new NanoAppState[size];
                }
            };
    private boolean mIsEnabled;
    private long mNanoAppId;
    private List<String> mNanoAppPermissions;
    private List<NanoAppRpcService> mNanoAppRpcServiceList;
    private int mNanoAppVersion;

    public NanoAppState(long nanoAppId, int appVersion, boolean enabled) {
        this.mNanoAppPermissions = new ArrayList();
        this.mNanoAppRpcServiceList = new ArrayList();
        this.mNanoAppId = nanoAppId;
        this.mNanoAppVersion = appVersion;
        this.mIsEnabled = enabled;
    }

    public NanoAppState(
            long nanoAppId, int appVersion, boolean enabled, List<String> nanoAppPermissions) {
        this.mNanoAppPermissions = new ArrayList();
        this.mNanoAppRpcServiceList = new ArrayList();
        this.mNanoAppId = nanoAppId;
        this.mNanoAppVersion = appVersion;
        this.mIsEnabled = enabled;
        this.mNanoAppPermissions = Collections.unmodifiableList(nanoAppPermissions);
    }

    public NanoAppState(
            long nanoAppId,
            int appVersion,
            boolean enabled,
            List<String> nanoAppPermissions,
            List<NanoAppRpcService> nanoAppRpcServiceList) {
        this.mNanoAppPermissions = new ArrayList();
        this.mNanoAppRpcServiceList = new ArrayList();
        this.mNanoAppId = nanoAppId;
        this.mNanoAppVersion = appVersion;
        this.mIsEnabled = enabled;
        this.mNanoAppPermissions = Collections.unmodifiableList(nanoAppPermissions);
        this.mNanoAppRpcServiceList = Collections.unmodifiableList(nanoAppRpcServiceList);
    }

    public long getNanoAppId() {
        return this.mNanoAppId;
    }

    public long getNanoAppVersion() {
        return this.mNanoAppVersion;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public List<String> getNanoAppPermissions() {
        return this.mNanoAppPermissions;
    }

    public List<NanoAppRpcService> getRpcServices() {
        return this.mNanoAppRpcServiceList;
    }

    private NanoAppState(Parcel in) {
        this.mNanoAppPermissions = new ArrayList();
        this.mNanoAppRpcServiceList = new ArrayList();
        this.mNanoAppId = in.readLong();
        this.mNanoAppVersion = in.readInt();
        this.mIsEnabled = in.readInt() == 1;
        this.mNanoAppPermissions = new ArrayList();
        in.readStringList(this.mNanoAppPermissions);
        this.mNanoAppRpcServiceList =
                Collections.unmodifiableList(
                        Arrays.asList(
                                (NanoAppRpcService[])
                                        in.readParcelableArray(
                                                NanoAppRpcService.class.getClassLoader(),
                                                NanoAppRpcService.class)));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mNanoAppId);
        parcel.writeInt(this.mNanoAppVersion);
        parcel.writeInt(this.mIsEnabled ? 1 : 0);
        parcel.writeStringList(this.mNanoAppPermissions);
        parcel.writeParcelableArray(
                (NanoAppRpcService[]) this.mNanoAppRpcServiceList.toArray(new NanoAppRpcService[0]),
                0);
    }
}
