package android.content.pm.dependencyinstaller;

import android.annotation.SystemApi;
import android.content.pm.dependencyinstaller.IDependencyInstallerCallback;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

@SystemApi
/* loaded from: classes.dex */
public final class DependencyInstallerCallback implements Parcelable {
    public static final Parcelable.Creator<DependencyInstallerCallback> CREATOR = new Parcelable.Creator<DependencyInstallerCallback>() { // from class: android.content.pm.dependencyinstaller.DependencyInstallerCallback.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DependencyInstallerCallback createFromParcel(Parcel parcel) {
            return new DependencyInstallerCallback(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DependencyInstallerCallback[] newArray(int i) {
            return new DependencyInstallerCallback[i];
        }
    };
    private final IBinder mBinder;
    private final IDependencyInstallerCallback mCallback;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DependencyInstallerCallback(IBinder iBinder) {
        this.mBinder = iBinder;
        this.mCallback = IDependencyInstallerCallback.Stub.asInterface(iBinder);
    }

    private DependencyInstallerCallback(Parcel parcel) {
        IBinder strongBinder = parcel.readStrongBinder();
        this.mBinder = strongBinder;
        this.mCallback = IDependencyInstallerCallback.Stub.asInterface(strongBinder);
    }

    public void onAllDependenciesResolved(int[] iArr) {
        try {
            this.mCallback.onAllDependenciesResolved(iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onFailureToResolveAllDependencies() {
        try {
            this.mCallback.onFailureToResolveAllDependencies();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mBinder);
    }
}
