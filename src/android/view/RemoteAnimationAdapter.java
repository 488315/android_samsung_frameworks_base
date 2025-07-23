package android.view;

import android.app.IApplicationThread;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.IRemoteAnimationRunner;

/* loaded from: classes4.dex */
public class RemoteAnimationAdapter implements Parcelable {
    public static final Parcelable.Creator<RemoteAnimationAdapter> CREATOR = new Parcelable.Creator<RemoteAnimationAdapter>() { // from class: android.view.RemoteAnimationAdapter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteAnimationAdapter createFromParcel(Parcel parcel) {
            return new RemoteAnimationAdapter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteAnimationAdapter[] newArray(int i) {
            return new RemoteAnimationAdapter[i];
        }
    };
    private IApplicationThread mCallingApplication;
    private int mCallingPid;
    private int mCallingUid;
    private final boolean mChangeNeedsSnapshot;
    private final long mDuration;
    private final IRemoteAnimationRunner mRunner;
    private final long mStatusBarTransitionDelay;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RemoteAnimationAdapter(IRemoteAnimationRunner iRemoteAnimationRunner, long j, long j2, boolean z) {
        this.mRunner = iRemoteAnimationRunner;
        this.mDuration = j;
        this.mChangeNeedsSnapshot = z;
        this.mStatusBarTransitionDelay = j2;
    }

    public RemoteAnimationAdapter(IRemoteAnimationRunner iRemoteAnimationRunner, long j, long j2) {
        this(iRemoteAnimationRunner, j, j2, false);
    }

    public RemoteAnimationAdapter(IRemoteAnimationRunner iRemoteAnimationRunner, long j, long j2, IApplicationThread iApplicationThread) {
        this(iRemoteAnimationRunner, j, j2, false);
        this.mCallingApplication = iApplicationThread;
    }

    public RemoteAnimationAdapter(Parcel parcel) {
        this.mRunner = IRemoteAnimationRunner.Stub.asInterface(parcel.readStrongBinder());
        this.mDuration = parcel.readLong();
        this.mStatusBarTransitionDelay = parcel.readLong();
        this.mChangeNeedsSnapshot = parcel.readBoolean();
        this.mCallingApplication = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
    }

    public IRemoteAnimationRunner getRunner() {
        return this.mRunner;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public long getStatusBarTransitionDelay() {
        return this.mStatusBarTransitionDelay;
    }

    public boolean getChangeNeedsSnapshot() {
        return this.mChangeNeedsSnapshot;
    }

    public void setCallingPidUid(int i, int i2) {
        this.mCallingPid = i;
        this.mCallingUid = i2;
    }

    public int getCallingPid() {
        return this.mCallingPid;
    }

    public int getCallingUid() {
        return this.mCallingUid;
    }

    public IApplicationThread getCallingApplication() {
        return this.mCallingApplication;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongInterface(this.mRunner);
        parcel.writeLong(this.mDuration);
        parcel.writeLong(this.mStatusBarTransitionDelay);
        parcel.writeBoolean(this.mChangeNeedsSnapshot);
        parcel.writeStrongInterface(this.mCallingApplication);
    }
}
