package android.service.autofill;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;

/* loaded from: classes3.dex */
public final class ConvertCredentialResponse implements Parcelable {
    public static final Parcelable.Creator<ConvertCredentialResponse> CREATOR = new Parcelable.Creator<ConvertCredentialResponse>() { // from class: android.service.autofill.ConvertCredentialResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConvertCredentialResponse[] newArray(int i) {
            return new ConvertCredentialResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConvertCredentialResponse createFromParcel(Parcel parcel) {
            return new ConvertCredentialResponse(parcel);
        }
    };
    private final Bundle mClientState;
    private final Dataset mDataset;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ConvertCredentialResponse(Dataset dataset, Bundle bundle) {
        this.mDataset = dataset;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) dataset);
        this.mClientState = bundle;
    }

    public Dataset getDataset() {
        return this.mDataset;
    }

    public Bundle getClientState() {
        return this.mClientState;
    }

    public String toString() {
        return "ConvertCredentialResponse { dataset = " + this.mDataset + ", clientState = " + this.mClientState + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mClientState != null ? (byte) 2 : (byte) 0);
        parcel.writeTypedObject(this.mDataset, i);
        Bundle bundle = this.mClientState;
        if (bundle != null) {
            parcel.writeBundle(bundle);
        }
    }

    ConvertCredentialResponse(Parcel parcel) {
        byte b = parcel.readByte();
        Dataset dataset = (Dataset) parcel.readTypedObject(Dataset.CREATOR);
        Bundle bundle = (b & 2) == 0 ? null : parcel.readBundle();
        this.mDataset = dataset;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) dataset);
        this.mClientState = bundle;
    }
}
