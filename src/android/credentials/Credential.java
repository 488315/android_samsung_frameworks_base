package android.credentials;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;
import java.util.Objects;

/* loaded from: classes.dex */
public final class Credential implements Parcelable {
    public static final Parcelable.Creator<Credential> CREATOR = new Parcelable.Creator<Credential>() { // from class: android.credentials.Credential.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Credential[] newArray(int i) {
            return new Credential[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Credential createFromParcel(Parcel parcel) {
            return new Credential(parcel);
        }
    };
    public static final String TYPE_PASSWORD_CREDENTIAL = "android.credentials.TYPE_PASSWORD_CREDENTIAL";
    private final Bundle mData;
    private final String mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getType() {
        return this.mType;
    }

    public Bundle getData() {
        return this.mData;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mType);
        parcel.writeBundle(this.mData);
    }

    public String toString() {
        return "Credential {type=" + this.mType + ", data=" + this.mData + "}";
    }

    public Credential(String str, Bundle bundle) {
        this.mType = (String) Preconditions.checkStringNotEmpty(str, "type must not be empty");
        this.mData = (Bundle) Objects.requireNonNull(bundle, "data must not be null");
    }

    private Credential(Parcel parcel) {
        String readString8 = parcel.readString8();
        Bundle readBundle = parcel.readBundle();
        this.mType = readString8;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString8);
        this.mData = readBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readBundle);
    }
}
