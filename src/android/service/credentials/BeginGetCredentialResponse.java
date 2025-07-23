package android.service.credentials;

import android.app.slice.Slice;
import android.content.pm.ParceledListSlice;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class BeginGetCredentialResponse implements Parcelable {
    public static final Parcelable.Creator<BeginGetCredentialResponse> CREATOR = new Parcelable.Creator<BeginGetCredentialResponse>() { // from class: android.service.credentials.BeginGetCredentialResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BeginGetCredentialResponse createFromParcel(Parcel parcel) {
            return new BeginGetCredentialResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BeginGetCredentialResponse[] newArray(int i) {
            return new BeginGetCredentialResponse[i];
        }
    };
    private final ParceledListSlice<Action> mActions;
    private final ParceledListSlice<Action> mAuthenticationEntries;
    private final ParceledListSlice<CredentialEntry> mCredentialEntries;
    private final RemoteEntry mRemoteCredentialEntry;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BeginGetCredentialResponse() {
        this(new ParceledListSlice(new ArrayList()), new ParceledListSlice(new ArrayList()), new ParceledListSlice(new ArrayList()), null);
    }

    private BeginGetCredentialResponse(ParceledListSlice<CredentialEntry> parceledListSlice, ParceledListSlice<Action> parceledListSlice2, ParceledListSlice<Action> parceledListSlice3, RemoteEntry remoteEntry) {
        this.mCredentialEntries = parceledListSlice;
        this.mAuthenticationEntries = parceledListSlice2;
        this.mActions = parceledListSlice3;
        this.mRemoteCredentialEntry = remoteEntry;
    }

    private BeginGetCredentialResponse(Parcel parcel) {
        this.mCredentialEntries = (ParceledListSlice) parcel.readParcelable(null, ParceledListSlice.class);
        this.mAuthenticationEntries = (ParceledListSlice) parcel.readParcelable(null, ParceledListSlice.class);
        this.mActions = (ParceledListSlice) parcel.readParcelable(null, ParceledListSlice.class);
        this.mRemoteCredentialEntry = (RemoteEntry) parcel.readTypedObject(RemoteEntry.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mCredentialEntries, i);
        parcel.writeParcelable(this.mAuthenticationEntries, i);
        parcel.writeParcelable(this.mActions, i);
        parcel.writeTypedObject(this.mRemoteCredentialEntry, i);
    }

    public List<CredentialEntry> getCredentialEntries() {
        return this.mCredentialEntries.getList();
    }

    public List<Action> getAuthenticationActions() {
        return this.mAuthenticationEntries.getList();
    }

    public List<Action> getActions() {
        return this.mActions.getList();
    }

    public RemoteEntry getRemoteCredentialEntry() {
        return this.mRemoteCredentialEntry;
    }

    public static final class Builder {
        private RemoteEntry mRemoteCredentialEntry;
        private List<CredentialEntry> mCredentialEntries = new ArrayList();
        private List<Action> mAuthenticationEntries = new ArrayList();
        private List<Action> mActions = new ArrayList();

        public Builder setRemoteCredentialEntry(RemoteEntry remoteEntry) {
            this.mRemoteCredentialEntry = remoteEntry;
            return this;
        }

        public Builder addCredentialEntry(CredentialEntry credentialEntry) {
            this.mCredentialEntries.add((CredentialEntry) Objects.requireNonNull(credentialEntry));
            return this;
        }

        public Builder addAuthenticationAction(Action action) {
            this.mAuthenticationEntries.add((Action) Objects.requireNonNull(action));
            return this;
        }

        public Builder addAction(Action action) {
            this.mActions.add((Action) Objects.requireNonNull(action, "action must not be null"));
            return this;
        }

        public Builder setActions(List<Action> list) {
            this.mActions = (List) Preconditions.checkCollectionElementsNotNull(list, Slice.HINT_ACTIONS);
            return this;
        }

        public Builder setCredentialEntries(List<CredentialEntry> list) {
            this.mCredentialEntries = (List) Preconditions.checkCollectionElementsNotNull(list, "credentialEntries");
            return this;
        }

        public Builder setAuthenticationActions(List<Action> list) {
            this.mAuthenticationEntries = (List) Preconditions.checkCollectionElementsNotNull(list, "authenticationActions");
            return this;
        }

        public BeginGetCredentialResponse build() {
            return new BeginGetCredentialResponse(new ParceledListSlice(this.mCredentialEntries), new ParceledListSlice(this.mAuthenticationEntries), new ParceledListSlice(this.mActions), this.mRemoteCredentialEntry);
        }
    }
}
