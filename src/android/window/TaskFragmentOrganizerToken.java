package android.window;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.window.ITaskFragmentOrganizer;

/* loaded from: classes5.dex */
public final class TaskFragmentOrganizerToken implements Parcelable {
    public static final Parcelable.Creator<TaskFragmentOrganizerToken> CREATOR = new Parcelable.Creator<TaskFragmentOrganizerToken>() { // from class: android.window.TaskFragmentOrganizerToken.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentOrganizerToken createFromParcel(Parcel parcel) {
            ITaskFragmentOrganizer iTaskFragmentOrganizerAsInterface = ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
            if (iTaskFragmentOrganizerAsInterface == null) {
                return null;
            }
            return new TaskFragmentOrganizerToken(iTaskFragmentOrganizerAsInterface);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentOrganizerToken[] newArray(int i) {
            return new TaskFragmentOrganizerToken[i];
        }
    };
    private final ITaskFragmentOrganizer mRealToken;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    TaskFragmentOrganizerToken(ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        this.mRealToken = iTaskFragmentOrganizer;
    }

    public IBinder asBinder() {
        return this.mRealToken.asBinder();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongInterface(this.mRealToken);
    }

    public int hashCode() {
        return this.mRealToken.asBinder().hashCode();
    }

    public String toString() {
        return "TaskFragmentOrganizerToken{" + this.mRealToken + "}";
    }

    public boolean equals(Object obj) {
        return (obj instanceof TaskFragmentOrganizerToken) && this.mRealToken.asBinder() == ((TaskFragmentOrganizerToken) obj).asBinder();
    }
}
