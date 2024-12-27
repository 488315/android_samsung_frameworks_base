package android.companion.datatransfer;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class PermissionSyncRequest extends SystemDataTransferRequest implements Parcelable {
    public static final Parcelable.Creator<PermissionSyncRequest> CREATOR =
            new Parcelable.Creator<PermissionSyncRequest>() { // from class:
                // android.companion.datatransfer.PermissionSyncRequest.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PermissionSyncRequest createFromParcel(Parcel in) {
                    return new PermissionSyncRequest(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PermissionSyncRequest[] newArray(int size) {
                    return new PermissionSyncRequest[size];
                }
            };

    public PermissionSyncRequest(int associationId) {
        super(associationId, 1);
    }

    public String toString() {
        return "SystemDataTransferRequest(associationId="
                + this.mAssociationId
                + ", userId="
                + this.mUserId
                + ", isUserConsented="
                + this.mUserConsented
                + NavigationBarInflaterView.KEY_CODE_END;
    }

    PermissionSyncRequest(Parcel in) {
        super(in);
    }

    @Override // android.companion.datatransfer.SystemDataTransferRequest
    public PermissionSyncRequest copyWithNewId(int associationId) {
        PermissionSyncRequest newRequest = new PermissionSyncRequest(associationId);
        newRequest.mUserId = this.mUserId;
        newRequest.mUserConsented = this.mUserConsented;
        return newRequest;
    }
}
