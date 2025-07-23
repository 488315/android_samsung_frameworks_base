package android.hardware.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SystemApi
/* loaded from: classes2.dex */
public final class NanoAppState implements Parcelable {
    public static final Parcelable.Creator<NanoAppState> CREATOR = new Parcelable.Creator<NanoAppState>() { // from class: android.hardware.location.NanoAppState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NanoAppState createFromParcel(Parcel parcel) {
            return new NanoAppState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NanoAppState[] newArray(int i) {
            return new NanoAppState[i];
        }
    };
    private boolean mIsEnabled;
    private long mNanoAppId;
    private List<String> mNanoAppPermissions;
    private List<NanoAppRpcService> mNanoAppRpcServiceList;
    private int mNanoAppVersion;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NanoAppState(long j, int i, boolean z) {
        this.mNanoAppPermissions = new ArrayList();
        this.mNanoAppRpcServiceList = new ArrayList();
        this.mNanoAppId = j;
        this.mNanoAppVersion = i;
        this.mIsEnabled = z;
    }

    public NanoAppState(long j, int i, boolean z, List<String> list) {
        this.mNanoAppPermissions = new ArrayList();
        this.mNanoAppRpcServiceList = new ArrayList();
        this.mNanoAppId = j;
        this.mNanoAppVersion = i;
        this.mIsEnabled = z;
        this.mNanoAppPermissions = Collections.unmodifiableList(list);
    }

    public NanoAppState(long j, int i, boolean z, List<String> list, List<NanoAppRpcService> list2) {
        this.mNanoAppPermissions = new ArrayList();
        this.mNanoAppRpcServiceList = new ArrayList();
        this.mNanoAppId = j;
        this.mNanoAppVersion = i;
        this.mIsEnabled = z;
        this.mNanoAppPermissions = Collections.unmodifiableList(list);
        this.mNanoAppRpcServiceList = Collections.unmodifiableList(list2);
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

    private NanoAppState(Parcel parcel) {
        this.mNanoAppPermissions = new ArrayList();
        this.mNanoAppRpcServiceList = new ArrayList();
        this.mNanoAppId = parcel.readLong();
        this.mNanoAppVersion = parcel.readInt();
        this.mIsEnabled = parcel.readInt() == 1;
        ArrayList arrayList = new ArrayList();
        this.mNanoAppPermissions = arrayList;
        parcel.readStringList(arrayList);
        this.mNanoAppRpcServiceList = Collections.unmodifiableList(Arrays.asList((NanoAppRpcService[]) parcel.readParcelableArray(NanoAppRpcService.class.getClassLoader(), NanoAppRpcService.class)));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mNanoAppId);
        parcel.writeInt(this.mNanoAppVersion);
        parcel.writeInt(this.mIsEnabled ? 1 : 0);
        parcel.writeStringList(this.mNanoAppPermissions);
        parcel.writeParcelableArray((NanoAppRpcService[]) this.mNanoAppRpcServiceList.toArray(new NanoAppRpcService[0]), 0);
    }
}
