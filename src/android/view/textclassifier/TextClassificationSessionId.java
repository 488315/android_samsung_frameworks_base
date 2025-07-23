package android.view.textclassifier;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/* loaded from: classes4.dex */
public final class TextClassificationSessionId implements Parcelable {
    public static final Parcelable.Creator<TextClassificationSessionId> CREATOR = new Parcelable.Creator<TextClassificationSessionId>() { // from class: android.view.textclassifier.TextClassificationSessionId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextClassificationSessionId createFromParcel(Parcel parcel) {
            return new TextClassificationSessionId(parcel.readString(), parcel.readStrongBinder());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextClassificationSessionId[] newArray(int i) {
            return new TextClassificationSessionId[i];
        }
    };
    private final IBinder mToken;
    private final String mValue;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TextClassificationSessionId() {
        this(UUID.randomUUID().toString(), new Binder());
    }

    public TextClassificationSessionId(String str, IBinder iBinder) {
        this.mValue = (String) Objects.requireNonNull(str);
        this.mToken = (IBinder) Objects.requireNonNull(iBinder);
    }

    public IBinder getToken() {
        return this.mToken;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TextClassificationSessionId textClassificationSessionId = (TextClassificationSessionId) obj;
            if (Objects.equals(this.mValue, textClassificationSessionId.mValue) && Objects.equals(this.mToken, textClassificationSessionId.mToken)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mValue, this.mToken);
    }

    public String toString() {
        return String.format(Locale.US, "TextClassificationSessionId {%s}", this.mValue);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mValue);
        parcel.writeStrongBinder(this.mToken);
    }

    public String getValue() {
        return this.mValue;
    }
}
