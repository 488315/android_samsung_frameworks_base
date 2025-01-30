package android.service.credentials;

import android.app.slice.Slice;
import android.content.p002pm.ParceledListSlice;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class BeginGetCredentialResponse implements Parcelable {
    public static final Parcelable.Creator<BeginGetCredentialResponse> CREATOR = new Parcelable.Creator<BeginGetCredentialResponse>() { // from class: android.service.credentials.BeginGetCredentialResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BeginGetCredentialResponse createFromParcel(Parcel in) {
            return new BeginGetCredentialResponse(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BeginGetCredentialResponse[] newArray(int size) {
            return new BeginGetCredentialResponse[size];
        }
    };
    private final ParceledListSlice<Action> mActions;
    private final ParceledListSlice<Action> mAuthenticationEntries;
    private final ParceledListSlice<CredentialEntry> mCredentialEntries;
    private final RemoteEntry mRemoteCredentialEntry;

    public BeginGetCredentialResponse() {
        this(new ParceledListSlice(new ArrayList()), new ParceledListSlice(new ArrayList()), new ParceledListSlice(new ArrayList()), null);
    }

    private BeginGetCredentialResponse(ParceledListSlice<CredentialEntry> credentialEntries, ParceledListSlice<Action> authenticationEntries, ParceledListSlice<Action> actions, RemoteEntry remoteCredentialEntry) {
        this.mCredentialEntries = credentialEntries;
        this.mAuthenticationEntries = authenticationEntries;
        this.mActions = actions;
        this.mRemoteCredentialEntry = remoteCredentialEntry;
    }

    private BeginGetCredentialResponse(Parcel in) {
        this.mCredentialEntries = (ParceledListSlice) in.readParcelable(null, ParceledListSlice.class);
        this.mAuthenticationEntries = (ParceledListSlice) in.readParcelable(null, ParceledListSlice.class);
        this.mActions = (ParceledListSlice) in.readParcelable(null, ParceledListSlice.class);
        this.mRemoteCredentialEntry = (RemoteEntry) in.readTypedObject(RemoteEntry.CREATOR);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mCredentialEntries, flags);
        dest.writeParcelable(this.mAuthenticationEntries, flags);
        dest.writeParcelable(this.mActions, flags);
        dest.writeTypedObject(this.mRemoteCredentialEntry, flags);
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

        public Builder setRemoteCredentialEntry(RemoteEntry remoteCredentialEntry) {
            this.mRemoteCredentialEntry = remoteCredentialEntry;
            return this;
        }

        public Builder addCredentialEntry(CredentialEntry credentialEntry) {
            this.mCredentialEntries.add((CredentialEntry) Objects.requireNonNull(credentialEntry));
            return this;
        }

        public Builder addAuthenticationAction(Action authenticationAction) {
            this.mAuthenticationEntries.add((Action) Objects.requireNonNull(authenticationAction));
            return this;
        }

        public Builder addAction(Action action) {
            this.mActions.add((Action) Objects.requireNonNull(action, "action must not be null"));
            return this;
        }

        public Builder setActions(List<Action> actions) {
            this.mActions = (List) Preconditions.checkCollectionElementsNotNull(actions, Slice.HINT_ACTIONS);
            return this;
        }

        public Builder setCredentialEntries(List<CredentialEntry> credentialEntries) {
            this.mCredentialEntries = (List) Preconditions.checkCollectionElementsNotNull(credentialEntries, "credentialEntries");
            return this;
        }

        public Builder setAuthenticationActions(List<Action> authenticationActions) {
            this.mAuthenticationEntries = (List) Preconditions.checkCollectionElementsNotNull(authenticationActions, "authenticationActions");
            return this;
        }

        public BeginGetCredentialResponse build() {
            return new BeginGetCredentialResponse(new ParceledListSlice(this.mCredentialEntries), new ParceledListSlice(this.mAuthenticationEntries), new ParceledListSlice(this.mActions), this.mRemoteCredentialEntry);
        }
    }
}
