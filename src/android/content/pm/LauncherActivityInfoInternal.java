package android.content.pm;

import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;

/* loaded from: classes.dex */
public class LauncherActivityInfoInternal implements Parcelable {
    public static final Parcelable.Creator<LauncherActivityInfoInternal> CREATOR = new Parcelable.Creator<LauncherActivityInfoInternal>() { // from class: android.content.pm.LauncherActivityInfoInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LauncherActivityInfoInternal createFromParcel(Parcel parcel) {
            return new LauncherActivityInfoInternal(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LauncherActivityInfoInternal[] newArray(int i) {
            return new LauncherActivityInfoInternal[i];
        }
    };
    private ActivityInfo mActivityInfo;
    private ComponentName mComponentName;
    private IncrementalStatesInfo mIncrementalStatesInfo;
    private boolean mSupportsMultiInstance;
    private UserHandle mUser;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LauncherActivityInfoInternal(ActivityInfo activityInfo, IncrementalStatesInfo incrementalStatesInfo, UserHandle userHandle, boolean z) {
        this.mActivityInfo = activityInfo;
        this.mComponentName = new ComponentName(activityInfo.packageName, activityInfo.name);
        this.mIncrementalStatesInfo = incrementalStatesInfo;
        this.mUser = userHandle;
        this.mSupportsMultiInstance = z;
    }

    public LauncherActivityInfoInternal(Parcel parcel) {
        this.mActivityInfo = (ActivityInfo) parcel.readTypedObject(ActivityInfo.CREATOR);
        this.mComponentName = new ComponentName(this.mActivityInfo.packageName, this.mActivityInfo.name);
        this.mIncrementalStatesInfo = (IncrementalStatesInfo) parcel.readTypedObject(IncrementalStatesInfo.CREATOR);
        this.mUser = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
        this.mSupportsMultiInstance = parcel.readBoolean();
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public ActivityInfo getActivityInfo() {
        return this.mActivityInfo;
    }

    public UserHandle getUser() {
        return this.mUser;
    }

    public IncrementalStatesInfo getIncrementalStatesInfo() {
        return this.mIncrementalStatesInfo;
    }

    public boolean supportsMultiInstance() {
        return this.mSupportsMultiInstance;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mActivityInfo, i);
        parcel.writeTypedObject(this.mIncrementalStatesInfo, i);
        parcel.writeTypedObject(this.mUser, i);
        parcel.writeBoolean(this.mSupportsMultiInstance);
    }
}
