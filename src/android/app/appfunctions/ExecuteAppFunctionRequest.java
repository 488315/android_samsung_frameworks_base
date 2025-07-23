package android.app.appfunctions;

import android.app.appsearch.GenericDocument;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ExecuteAppFunctionRequest implements Parcelable {
    public static final Parcelable.Creator<ExecuteAppFunctionRequest> CREATOR = new Parcelable.Creator<ExecuteAppFunctionRequest>() { // from class: android.app.appfunctions.ExecuteAppFunctionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExecuteAppFunctionRequest createFromParcel(Parcel parcel) {
            return new ExecuteAppFunctionRequest(parcel.readString8(), parcel.readString8(), parcel.readBundle(Bundle.class.getClassLoader()), GenericDocumentWrapper.CREATOR.createFromParcel(parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExecuteAppFunctionRequest[] newArray(int i) {
            return new ExecuteAppFunctionRequest[i];
        }
    };
    private final Bundle mExtras;
    private final String mFunctionIdentifier;
    private final GenericDocumentWrapper mParameters;
    private final String mTargetPackageName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ExecuteAppFunctionRequest(String str, String str2, Bundle bundle, GenericDocumentWrapper genericDocumentWrapper) {
        this.mTargetPackageName = (String) Objects.requireNonNull(str);
        this.mFunctionIdentifier = (String) Objects.requireNonNull(str2);
        this.mExtras = (Bundle) Objects.requireNonNull(bundle);
        this.mParameters = (GenericDocumentWrapper) Objects.requireNonNull(genericDocumentWrapper);
    }

    public String getTargetPackageName() {
        return this.mTargetPackageName;
    }

    public String getFunctionIdentifier() {
        return this.mFunctionIdentifier;
    }

    public GenericDocument getParameters() {
        return this.mParameters.getValue();
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public int getRequestDataSize() {
        return this.mTargetPackageName.getBytes().length + this.mFunctionIdentifier.getBytes().length + this.mParameters.getDataSize() + this.mExtras.getSize();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mTargetPackageName);
        parcel.writeString8(this.mFunctionIdentifier);
        this.mParameters.writeToParcel(parcel, i);
        parcel.writeBundle(this.mExtras);
    }

    public static final class Builder {
        private final String mFunctionIdentifier;
        private final String mTargetPackageName;
        private Bundle mExtras = Bundle.EMPTY;
        private GenericDocument mParameters = new GenericDocument.Builder("", "", "").build();

        public Builder(String str, String str2) {
            this.mTargetPackageName = (String) Objects.requireNonNull(str);
            this.mFunctionIdentifier = (String) Objects.requireNonNull(str2);
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = (Bundle) Objects.requireNonNull(bundle);
            return this;
        }

        public Builder setParameters(GenericDocument genericDocument) {
            Objects.requireNonNull(genericDocument);
            this.mParameters = genericDocument;
            return this;
        }

        public ExecuteAppFunctionRequest build() {
            return new ExecuteAppFunctionRequest(this.mTargetPackageName, this.mFunctionIdentifier, this.mExtras, new GenericDocumentWrapper(this.mParameters));
        }
    }
}
