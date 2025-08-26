package android.credentials.selection;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;

@SystemApi
/* loaded from: classes.dex */
public final class CancelSelectionRequest implements Parcelable {
    public static final Parcelable.Creator<CancelSelectionRequest> CREATOR = new Parcelable.Creator<CancelSelectionRequest>() { // from class: android.credentials.selection.CancelSelectionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CancelSelectionRequest createFromParcel(Parcel parcel) {
            return new CancelSelectionRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CancelSelectionRequest[] newArray(int i) {
            return new CancelSelectionRequest[i];
        }
    };
    public static final String EXTRA_CANCEL_UI_REQUEST = "android.credentials.selection.extra.CANCEL_UI_REQUEST";
    private final String mPackageName;
    private final boolean mShouldShowCancellationExplanation;
    private final IBinder mToken;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public IBinder getToken() {
        return this.mToken;
    }

    public RequestToken getRequestToken() {
        return new RequestToken(this.mToken);
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public boolean shouldShowCancellationExplanation() {
        return this.mShouldShowCancellationExplanation;
    }

    public CancelSelectionRequest(RequestToken requestToken, boolean z, String str) {
        this.mToken = requestToken.getToken();
        this.mShouldShowCancellationExplanation = z;
        this.mPackageName = str;
    }

    private CancelSelectionRequest(Parcel parcel) {
        IBinder strongBinder = parcel.readStrongBinder();
        this.mToken = strongBinder;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) strongBinder);
        this.mShouldShowCancellationExplanation = parcel.readBoolean();
        String string8 = parcel.readString8();
        this.mPackageName = string8;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string8);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mToken);
        parcel.writeBoolean(this.mShouldShowCancellationExplanation);
        parcel.writeString8(this.mPackageName);
    }
}
