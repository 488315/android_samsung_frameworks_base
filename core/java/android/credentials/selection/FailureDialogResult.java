package android.credentials.selection;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public final class FailureDialogResult extends BaseDialogResult implements Parcelable {
    public static final Parcelable.Creator<FailureDialogResult> CREATOR =
            new Parcelable.Creator<FailureDialogResult>() { // from class:
                // android.credentials.selection.FailureDialogResult.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FailureDialogResult createFromParcel(Parcel in) {
                    return new FailureDialogResult(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FailureDialogResult[] newArray(int size) {
                    return new FailureDialogResult[size];
                }
            };
    private static final String EXTRA_FAILURE_RESULT =
            "android.credentials.selection.extra.FAILURE_RESULT";
    private final String mErrorMessage;

    public static FailureDialogResult fromResultData(Bundle resultData) {
        return (FailureDialogResult)
                resultData.getParcelable(EXTRA_FAILURE_RESULT, FailureDialogResult.class);
    }

    public static void addToBundle(FailureDialogResult result, Bundle bundle) {
        bundle.putParcelable(EXTRA_FAILURE_RESULT, result);
    }

    public FailureDialogResult(IBinder requestToken, String errorMessage) {
        super(requestToken);
        this.mErrorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    private FailureDialogResult(Parcel in) {
        super(in);
        this.mErrorMessage = in.readString8();
    }

    @Override // android.credentials.selection.BaseDialogResult, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString8(this.mErrorMessage);
    }

    @Override // android.credentials.selection.BaseDialogResult, android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
