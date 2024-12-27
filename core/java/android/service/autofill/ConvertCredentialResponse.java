package android.service.autofill;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

public final class ConvertCredentialResponse implements Parcelable {
    public static final Parcelable.Creator<ConvertCredentialResponse> CREATOR =
            new Parcelable.Creator<
                    ConvertCredentialResponse>() { // from class:
                                                   // android.service.autofill.ConvertCredentialResponse.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ConvertCredentialResponse[] newArray(int size) {
                    return new ConvertCredentialResponse[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ConvertCredentialResponse createFromParcel(Parcel in) {
                    return new ConvertCredentialResponse(in);
                }
            };
    private final Bundle mClientState;
    private final Dataset mDataset;

    public ConvertCredentialResponse(Dataset dataset, Bundle clientState) {
        this.mDataset = dataset;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mDataset);
        this.mClientState = clientState;
    }

    public Dataset getDataset() {
        return this.mDataset;
    }

    public Bundle getClientState() {
        return this.mClientState;
    }

    public String toString() {
        return "ConvertCredentialResponse { dataset = "
                + this.mDataset
                + ", clientState = "
                + this.mClientState
                + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.mClientState != null ? (byte) (0 | 2) : (byte) 0;
        dest.writeByte(flg);
        dest.writeTypedObject(this.mDataset, flags);
        if (this.mClientState != null) {
            dest.writeBundle(this.mClientState);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ConvertCredentialResponse(Parcel in) {
        byte flg = in.readByte();
        Dataset dataset = (Dataset) in.readTypedObject(Dataset.CREATOR);
        Bundle clientState = (flg & 2) == 0 ? null : in.readBundle();
        this.mDataset = dataset;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mDataset);
        this.mClientState = clientState;
    }

    @Deprecated
    private void __metadata() {}
}
