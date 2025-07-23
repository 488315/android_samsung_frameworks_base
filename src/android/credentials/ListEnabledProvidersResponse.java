package android.credentials;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ListEnabledProvidersResponse implements Parcelable {
    public static final Parcelable.Creator<ListEnabledProvidersResponse> CREATOR = new Parcelable.Creator<ListEnabledProvidersResponse>() { // from class: android.credentials.ListEnabledProvidersResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ListEnabledProvidersResponse createFromParcel(Parcel parcel) {
            return new ListEnabledProvidersResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ListEnabledProvidersResponse[] newArray(int i) {
            return new ListEnabledProvidersResponse[i];
        }
    };
    private final List<String> mProviders;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static ListEnabledProvidersResponse create(List<String> list) {
        Objects.requireNonNull(list, "providers must not be null");
        Preconditions.checkCollectionElementsNotNull(list, "providers");
        return new ListEnabledProvidersResponse(list);
    }

    private ListEnabledProvidersResponse(List<String> list) {
        this.mProviders = list;
    }

    private ListEnabledProvidersResponse(Parcel parcel) {
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
