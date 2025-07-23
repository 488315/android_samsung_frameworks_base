package android.app.appfunctions;

import android.app.appsearch.GenericDocument;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ExecuteAppFunctionResponse implements Parcelable {
    public static final Parcelable.Creator<ExecuteAppFunctionResponse> CREATOR = new Parcelable.Creator<ExecuteAppFunctionResponse>() { // from class: android.app.appfunctions.ExecuteAppFunctionResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExecuteAppFunctionResponse createFromParcel(Parcel parcel) {
            GenericDocumentWrapper genericDocumentWrapper = (GenericDocumentWrapper) Objects.requireNonNull(GenericDocumentWrapper.CREATOR.createFromParcel(parcel));
            return new ExecuteAppFunctionResponse(genericDocumentWrapper.getValue(), (Bundle) Objects.requireNonNull(parcel.readBundle(Bundle.class.getClassLoader())));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExecuteAppFunctionResponse[] newArray(int i) {
            return new ExecuteAppFunctionResponse[i];
        }
    };
    public static final String PROPERTY_RETURN_VALUE = "androidAppfunctionsReturnValue";
    private final Bundle mExtras;
    private final GenericDocumentWrapper mResultDocumentWrapper;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ExecuteAppFunctionResponse(GenericDocument genericDocument) {
        this(genericDocument, Bundle.EMPTY);
    }

    public ExecuteAppFunctionResponse(GenericDocument genericDocument, Bundle bundle) {
        this.mResultDocumentWrapper = new GenericDocumentWrapper((GenericDocument) Objects.requireNonNull(genericDocument));
        this.mExtras = (Bundle) Objects.requireNonNull(bundle);
    }

    public GenericDocument getResultDocument() {
        return this.mResultDocumentWrapper.getValue();
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public int getResponseDataSize() {
        return this.mResultDocumentWrapper.getDataSize() + this.mExtras.getSize();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mResultDocumentWrapper.writeToParcel(parcel, i);
        parcel.writeBundle(this.mExtras);
    }
}
