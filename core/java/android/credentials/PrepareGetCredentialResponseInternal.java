package android.credentials;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;

import java.util.Set;

public final class PrepareGetCredentialResponseInternal implements Parcelable {
    public static final Parcelable.Creator<PrepareGetCredentialResponseInternal> CREATOR =
            new Parcelable.Creator<PrepareGetCredentialResponseInternal>() { // from class:
                // android.credentials.PrepareGetCredentialResponseInternal.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PrepareGetCredentialResponseInternal[] newArray(int size) {
                    return new PrepareGetCredentialResponseInternal[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PrepareGetCredentialResponseInternal createFromParcel(Parcel in) {
                    return new PrepareGetCredentialResponseInternal(in);
                }
            };
    private static final String TAG = "CredentialManager";
    private final ArraySet<String> mCredentialResultTypes;
    private final boolean mHasAuthenticationResults;
    private final boolean mHasQueryApiPermission;
    private final boolean mHasRemoteResults;
    private final PendingIntent mPendingIntent;

    public PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    public boolean hasCredentialResults(String credentialType) {
        if (!this.mHasQueryApiPermission) {
            throw new SecurityException(
                    "caller doesn't have the permission to query credential results");
        }
        if (this.mCredentialResultTypes == null) {
            return false;
        }
        return this.mCredentialResultTypes.contains(credentialType);
    }

    public boolean hasAuthenticationResults() {
        if (!this.mHasQueryApiPermission) {
            throw new SecurityException(
                    "caller doesn't have the permission to query authentication results");
        }
        return this.mHasAuthenticationResults;
    }

    public boolean hasRemoteResults() {
        if (!this.mHasQueryApiPermission) {
            throw new SecurityException(
                    "caller doesn't have the permission to query remote results");
        }
        return this.mHasRemoteResults;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBoolean(this.mHasQueryApiPermission);
        dest.writeArraySet(this.mCredentialResultTypes);
        dest.writeBoolean(this.mHasAuthenticationResults);
        dest.writeBoolean(this.mHasRemoteResults);
        dest.writeTypedObject(this.mPendingIntent, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PrepareGetCredentialResponseInternal(
            boolean hasQueryApiPermission,
            Set<String> credentialResultTypes,
            boolean hasAuthenticationResults,
            boolean hasRemoteResults,
            PendingIntent pendingIntent) {
        this.mHasQueryApiPermission = hasQueryApiPermission;
        this.mCredentialResultTypes = new ArraySet<>(credentialResultTypes);
        this.mHasAuthenticationResults = hasAuthenticationResults;
        this.mHasRemoteResults = hasRemoteResults;
        this.mPendingIntent = pendingIntent;
    }

    private PrepareGetCredentialResponseInternal(Parcel in) {
        this.mHasQueryApiPermission = in.readBoolean();
        this.mCredentialResultTypes = in.readArraySet(null);
        this.mHasAuthenticationResults = in.readBoolean();
        this.mHasRemoteResults = in.readBoolean();
        this.mPendingIntent = (PendingIntent) in.readTypedObject(PendingIntent.CREATOR);
    }
}
