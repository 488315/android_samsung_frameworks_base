package android.service.autofill;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class SaveRequest implements Parcelable {
    public static final Parcelable.Creator<SaveRequest> CREATOR = new Parcelable.Creator<SaveRequest>() { // from class: android.service.autofill.SaveRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SaveRequest createFromParcel(Parcel parcel) {
            return new SaveRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SaveRequest[] newArray(int i) {
            return new SaveRequest[i];
        }
    };
    private final Bundle mClientState;
    private final ArrayList<String> mDatasetIds;
    private final ArrayList<FillContext> mFillContexts;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SaveRequest(ArrayList<FillContext> arrayList, Bundle bundle, ArrayList<String> arrayList2) {
        this.mFillContexts = (ArrayList) Objects.requireNonNull(arrayList, "fillContexts");
        this.mClientState = bundle;
        this.mDatasetIds = arrayList2;
    }

    private SaveRequest(Parcel parcel) {
        this(parcel.createTypedArrayList(FillContext.CREATOR), parcel.readBundle(), parcel.createStringArrayList());
    }

    public List<FillContext> getFillContexts() {
        return this.mFillContexts;
    }

    public Bundle getClientState() {
        return this.mClientState;
    }

    public List<String> getDatasetIds() {
        return this.mDatasetIds;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mFillContexts, i);
        parcel.writeBundle(this.mClientState);
        parcel.writeStringList(this.mDatasetIds);
    }
}
