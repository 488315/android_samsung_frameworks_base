package android.credentials;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class SetEnabledProvidersRequest implements Parcelable {
    public static final Parcelable.Creator<SetEnabledProvidersRequest> CREATOR = new Parcelable.Creator<SetEnabledProvidersRequest>() { // from class: android.credentials.SetEnabledProvidersRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SetEnabledProvidersRequest createFromParcel(Parcel parcel) {
            return new SetEnabledProvidersRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SetEnabledProvidersRequest[] newArray(int i) {
            return new SetEnabledProvidersRequest[i];
        }
    };
    private final List<String> mProviders;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SetEnabledProvidersRequest(List<String> list) {
        Objects.requireNonNull(list, "providers must not be null");
        Preconditions.checkCollectionElementsNotNull(list, "providers");
        this.mProviders = list;
    }

    private SetEnabledProvidersRequest(Parcel parcel) {
        this.mProviders = parcel.createStringArrayList();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(this.mProviders);
    }

    public List<String> getProviderComponentNames() {
        return this.mProviders;
    }
}
