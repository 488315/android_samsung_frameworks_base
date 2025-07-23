package android.credentials.selection;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class FailureDialogResult extends BaseDialogResult implements Parcelable {
    public static final Parcelable.Creator<FailureDialogResult> CREATOR = new Parcelable.Creator<FailureDialogResult>() { // from class: android.credentials.selection.FailureDialogResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FailureDialogResult createFromParcel(Parcel parcel) {
            return new FailureDialogResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FailureDialogResult[] newArray(int i) {
            return new FailureDialogResult[i];
        }
    };
    private static final String EXTRA_FAILURE_RESULT = "android.credentials.selection.extra.FAILURE_RESULT";
    private final String mErrorMessage;

    @Override // android.credentials.selection.BaseDialogResult, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static FailureDialogResult fromResultData(Bundle bundle) {
        return (FailureDialogResult) bundle.getParcelable(EXTRA_FAILURE_RESULT, FailureDialogResult.class);
    }

    public static void addToBundle(FailureDialogResult failureDialogResult, Bundle bundle) {
        bundle.putParcelable(EXTRA_FAILURE_RESULT, failureDialogResult);
    }

    public FailureDialogResult(IBinder iBinder, String str) {
        super(iBinder);
        this.mErrorMessage = str;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    private FailureDialogResult(Parcel parcel) {
        super(parcel);
        this.mErrorMessage = parcel.readString8();
    }

    @Override // android.credentials.selection.BaseDialogResult, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString8(this.mErrorMessage);
    }
}
