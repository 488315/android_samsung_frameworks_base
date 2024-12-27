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
        public CancelSelectionRequest createFromParcel(Parcel in) {
            return new CancelSelectionRequest(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CancelSelectionRequest[] newArray(int size) {
            return new CancelSelectionRequest[size];
        }
    };
    public static final String EXTRA_CANCEL_UI_REQUEST = "android.credentials.selection.extra.CANCEL_UI_REQUEST";
    private final String mPackageName;
    private final boolean mShouldShowCancellationExplanation;
    private final IBinder mToken;

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

    public CancelSelectionRequest(RequestToken requestToken, boolean shouldShowCancellationExplanation, String packageName) {
        this.mToken = requestToken.getToken();
        this.mShouldShowCancellationExplanation = shouldShowCancellationExplanation;
        this.mPackageName = packageName;
    }

    private CancelSelectionRequest(Parcel in) {
        this.mToken = in.readStrongBinder();
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mToken);
        this.mShouldShowCancellationExplanation = in.readBoolean();
        this.mPackageName = in.readString8();
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mPackageName);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.mToken);
        dest.writeBoolean(this.mShouldShowCancellationExplanation);
        dest.writeString8(this.mPackageName);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
