package android.companion.datatransfer;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class PermissionSyncRequest extends SystemDataTransferRequest implements Parcelable {
    public static final Parcelable.Creator<PermissionSyncRequest> CREATOR = new Parcelable.Creator<PermissionSyncRequest>() { // from class: android.companion.datatransfer.PermissionSyncRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PermissionSyncRequest createFromParcel(Parcel parcel) {
            return new PermissionSyncRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PermissionSyncRequest[] newArray(int i) {
            return new PermissionSyncRequest[i];
        }
    };

    public PermissionSyncRequest(int i) {
        super(i, 1);
    }

    public String toString() {
        return "SystemDataTransferRequest(associationId=" + this.mAssociationId + ", userId=" + this.mUserId + ", isUserConsented=" + this.mUserConsented + NavigationBarInflaterView.KEY_CODE_END;
    }

    PermissionSyncRequest(Parcel parcel) {
        super(parcel);
    }

    @Override // android.companion.datatransfer.SystemDataTransferRequest
    public PermissionSyncRequest copyWithNewId(int i) {
        PermissionSyncRequest permissionSyncRequest = new PermissionSyncRequest(i);
        permissionSyncRequest.mUserId = this.mUserId;
        permissionSyncRequest.mUserConsented = this.mUserConsented;
        return permissionSyncRequest;
    }
}
