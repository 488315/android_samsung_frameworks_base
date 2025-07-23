package android.service.credentials;

import android.annotation.NonNull;
import android.content.pm.ParceledListSlice;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class BeginCreateCredentialResponse implements Parcelable {
    public static final Parcelable.Creator<BeginCreateCredentialResponse> CREATOR = new Parcelable.Creator<BeginCreateCredentialResponse>() { // from class: android.service.credentials.BeginCreateCredentialResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BeginCreateCredentialResponse createFromParcel(Parcel parcel) {
            return new BeginCreateCredentialResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BeginCreateCredentialResponse[] newArray(int i) {
            return new BeginCreateCredentialResponse[i];
        }
    };
    private final ParceledListSlice<CreateEntry> mCreateEntries;
    private final RemoteEntry mRemoteCreateEntry;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BeginCreateCredentialResponse() {
        this((ParceledListSlice<CreateEntry>) new ParceledListSlice(new ArrayList()), (RemoteEntry) null);
    }

    private BeginCreateCredentialResponse(Parcel parcel) {
        this.mCreateEntries = (ParceledListSlice) parcel.readParcelable(null, ParceledListSlice.class);
        this.mRemoteCreateEntry = (RemoteEntry) parcel.readTypedObject(RemoteEntry.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mCreateEntries, i);
        parcel.writeTypedObject(this.mRemoteCreateEntry, i);
    }

    BeginCreateCredentialResponse(ParceledListSlice<CreateEntry> parceledListSlice, RemoteEntry remoteEntry) {
        this.mCreateEntries = parceledListSlice;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) parceledListSlice);
        this.mRemoteCreateEntry = remoteEntry;
    }

    public List<CreateEntry> getCreateEntries() {
        return this.mCreateEntries.getList();
    }

    public RemoteEntry getRemoteCreateEntry() {
        return this.mRemoteCreateEntry;
    }

    public static final class Builder {
        private List<CreateEntry> mCreateEntries = new ArrayList();
        private RemoteEntry mRemoteCreateEntry;

        public Builder setCreateEntries(List<CreateEntry> list) {
            Preconditions.checkCollectionNotEmpty(list, "createEntries");
            this.mCreateEntries = (List) Preconditions.checkCollectionElementsNotNull(list, "createEntries");
            return this;
        }

        public Builder addCreateEntry(CreateEntry createEntry) {
            this.mCreateEntries.add((CreateEntry) Objects.requireNonNull(createEntry));
            return this;
        }

        public Builder setRemoteCreateEntry(RemoteEntry remoteEntry) {
            this.mRemoteCreateEntry = remoteEntry;
            return this;
        }

        public BeginCreateCredentialResponse build() {
            return new BeginCreateCredentialResponse((ParceledListSlice<CreateEntry>) new ParceledListSlice(this.mCreateEntries), this.mRemoteCreateEntry);
        }
    }
}
