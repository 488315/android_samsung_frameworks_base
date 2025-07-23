package android.view;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.view.IDragAndDropPermissions;

/* loaded from: classes4.dex */
public final class DragAndDropPermissions implements Parcelable {
    public static final Parcelable.Creator<DragAndDropPermissions> CREATOR = new Parcelable.Creator<DragAndDropPermissions>() { // from class: android.view.DragAndDropPermissions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DragAndDropPermissions createFromParcel(Parcel parcel) {
            return new DragAndDropPermissions(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DragAndDropPermissions[] newArray(int i) {
            return new DragAndDropPermissions[i];
        }
    };
    private static final boolean DEBUG = false;
    private static final String TAG = "DragAndDrop";
    private final IDragAndDropPermissions mDragAndDropPermissions;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static DragAndDropPermissions obtain(DragEvent dragEvent) {
        if (dragEvent.getDragAndDropPermissions() == null) {
            return null;
        }
        return new DragAndDropPermissions(dragEvent.getDragAndDropPermissions());
    }

    private DragAndDropPermissions(IDragAndDropPermissions iDragAndDropPermissions) {
        this.mDragAndDropPermissions = iDragAndDropPermissions;
    }

    public boolean take(IBinder iBinder) {
        try {
            this.mDragAndDropPermissions.take(iBinder);
            return true;
        } catch (RemoteException e) {
            Log.w(TAG, this + ": take() failed with a RemoteException", e);
            return false;
        }
    }

    public boolean takeTransient() {
        try {
            this.mDragAndDropPermissions.takeTransient();
            return true;
        } catch (RemoteException e) {
            Log.w(TAG, this + ": takeTransient() failed with a RemoteException", e);
            return false;
        }
    }

    public void release() {
        try {
            this.mDragAndDropPermissions.release();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getFlags() {
        try {
            return this.mDragAndDropPermissions.getFlags();
        } catch (RemoteException unused) {
            return 0;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongInterface(this.mDragAndDropPermissions);
    }

    private DragAndDropPermissions(Parcel parcel) {
        this.mDragAndDropPermissions = IDragAndDropPermissions.Stub.asInterface(parcel.readStrongBinder());
    }
}
