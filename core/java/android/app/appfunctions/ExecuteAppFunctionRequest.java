package android.app.appfunctions;

import android.app.appsearch.GenericDocument;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public final class ExecuteAppFunctionRequest implements Parcelable {
    public static final Parcelable.Creator<ExecuteAppFunctionRequest> CREATOR =
            new Parcelable.Creator<ExecuteAppFunctionRequest>() { // from class:
                // android.app.appfunctions.ExecuteAppFunctionRequest.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ExecuteAppFunctionRequest createFromParcel(Parcel parcel) {
                    String targetPackageName = parcel.readString8();
                    String functionIdentifier = parcel.readString8();
                    GenericDocumentWrapper parameters =
                            GenericDocumentWrapper.CREATOR.createFromParcel(parcel);
                    Bundle extras = parcel.readBundle(Bundle.class.getClassLoader());
                    return new ExecuteAppFunctionRequest(
                            targetPackageName, functionIdentifier, extras, parameters);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ExecuteAppFunctionRequest[] newArray(int size) {
                    return new ExecuteAppFunctionRequest[size];
                }
            };
    private final Bundle mExtras;
    private final String mFunctionIdentifier;
    private final GenericDocumentWrapper mParameters;
    private final String mTargetPackageName;

    private ExecuteAppFunctionRequest(
            String targetPackageName,
            String functionIdentifier,
            Bundle extras,
            GenericDocumentWrapper parameters) {
        this.mTargetPackageName = (String) Objects.requireNonNull(targetPackageName);
        this.mFunctionIdentifier = (String) Objects.requireNonNull(functionIdentifier);
        this.mExtras = (Bundle) Objects.requireNonNull(extras);
        this.mParameters = (GenericDocumentWrapper) Objects.requireNonNull(parameters);
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

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString8(this.mTargetPackageName);
        dest.writeString8(this.mFunctionIdentifier);
        this.mParameters.writeToParcel(dest, flags);
        dest.writeBundle(this.mExtras);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private final String mFunctionIdentifier;
        private final String mTargetPackageName;
        private Bundle mExtras = Bundle.EMPTY;
        private GenericDocument mParameters = new GenericDocument.Builder("", "", "").build();

        public Builder(String targetPackageName, String functionIdentifier) {
            this.mTargetPackageName = (String) Objects.requireNonNull(targetPackageName);
            this.mFunctionIdentifier = (String) Objects.requireNonNull(functionIdentifier);
        }

        public Builder setExtras(Bundle extras) {
            this.mExtras = (Bundle) Objects.requireNonNull(extras);
            return this;
        }

        public Builder setParameters(GenericDocument parameters) {
            Objects.requireNonNull(parameters);
            this.mParameters = parameters;
            return this;
        }

        public ExecuteAppFunctionRequest build() {
            return new ExecuteAppFunctionRequest(
                    this.mTargetPackageName,
                    this.mFunctionIdentifier,
                    this.mExtras,
                    new GenericDocumentWrapper(this.mParameters));
        }
    }
}
