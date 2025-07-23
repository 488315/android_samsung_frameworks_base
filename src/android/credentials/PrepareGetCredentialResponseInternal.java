package android.credentials;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.util.Set;

/* loaded from: classes.dex */
public final class PrepareGetCredentialResponseInternal implements Parcelable {
    public static final Parcelable.Creator<PrepareGetCredentialResponseInternal> CREATOR = new Parcelable.Creator<PrepareGetCredentialResponseInternal>() { // from class: android.credentials.PrepareGetCredentialResponseInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrepareGetCredentialResponseInternal[] newArray(int i) {
            return new PrepareGetCredentialResponseInternal[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrepareGetCredentialResponseInternal createFromParcel(Parcel parcel) {
            return new PrepareGetCredentialResponseInternal(parcel);
        }
    };
    private static final String TAG = "CredentialManager";
    private final ArraySet<String> mCredentialResultTypes;
    private final boolean mHasAuthenticationResults;
    private final boolean mHasQueryApiPermission;
    private final boolean mHasRemoteResults;
    private final PendingIntent mPendingIntent;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    public boolean hasCredentialResults(String str) {
        if (!this.mHasQueryApiPermission) {
            throw new SecurityException("caller doesn't have the permission to query credential results");
        }
        ArraySet<String> arraySet = this.mCredentialResultTypes;
        if (arraySet == null) {
            return false;
        }
        return arraySet.contains(str);
    }

    public boolean hasAuthenticationResults() {
        if (!this.mHasQueryApiPermission) {
            throw new SecurityException("caller doesn't have the permission to query authentication results");
        }
        return this.mHasAuthenticationResults;
    }

    public boolean hasRemoteResults() {
        if (!this.mHasQueryApiPermission) {
            throw new SecurityException("caller doesn't have the permission to query remote results");
        }
        return this.mHasRemoteResults;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.mHasQueryApiPermission);
        parcel.writeArraySet(this.mCredentialResultTypes);
        parcel.writeBoolean(this.mHasAuthenticationResults);
        parcel.writeBoolean(this.mHasRemoteResults);
        parcel.writeTypedObject(this.mPendingIntent, i);
    }

    public PrepareGetCredentialResponseInternal(boolean z, Set<String> set, boolean z2, boolean z3, PendingIntent pendingIntent) {
        this.mHasQueryApiPermission = z;
        this.mCredentialResultTypes = new ArraySet<>(set);
        this.mHasAuthenticationResults = z2;
        this.mHasRemoteResults = z3;
        this.mPendingIntent = pendingIntent;
    }

    private PrepareGetCredentialResponseInternal(Parcel parcel) {
        this.mHasQueryApiPermission = parcel.readBoolean();
        this.mCredentialResultTypes = parcel.readArraySet(null);
        this.mHasAuthenticationResults = parcel.readBoolean();
        this.mHasRemoteResults = parcel.readBoolean();
        this.mPendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
    }
}
