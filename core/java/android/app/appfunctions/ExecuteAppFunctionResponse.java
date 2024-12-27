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
            GenericDocumentWrapper resultWrapper = (GenericDocumentWrapper) Objects.requireNonNull(GenericDocumentWrapper.CREATOR.createFromParcel(parcel));
            Bundle extras = (Bundle) Objects.requireNonNull(parcel.readBundle(Bundle.class.getClassLoader()));
            return new ExecuteAppFunctionResponse(resultWrapper.getValue(), extras);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExecuteAppFunctionResponse[] newArray(int size) {
            return new ExecuteAppFunctionResponse[size];
        }
    };
    public static final String PROPERTY_RETURN_VALUE = "androidAppfunctionsReturnValue";
    private final Bundle mExtras;
    private final GenericDocumentWrapper mResultDocumentWrapper;

    public ExecuteAppFunctionResponse(GenericDocument resultDocument) {
        this(resultDocument, Bundle.EMPTY);
    }

    public ExecuteAppFunctionResponse(GenericDocument resultDocument, Bundle extras) {
        this.mResultDocumentWrapper = new GenericDocumentWrapper((GenericDocument) Objects.requireNonNull(resultDocument));
        this.mExtras = (Bundle) Objects.requireNonNull(extras);
    }

    public GenericDocument getResultDocument() {
        return this.mResultDocumentWrapper.getValue();
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        this.mResultDocumentWrapper.writeToParcel(dest, flags);
        dest.writeBundle(this.mExtras);
    }
}
