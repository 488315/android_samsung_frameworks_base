package android.credentials.selection;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class BaseDialogResult implements Parcelable {
    public static final Parcelable.Creator<BaseDialogResult> CREATOR = new Parcelable.Creator<BaseDialogResult>() { // from class: android.credentials.selection.BaseDialogResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BaseDialogResult createFromParcel(Parcel parcel) {
            return new BaseDialogResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BaseDialogResult[] newArray(int i) {
            return new BaseDialogResult[i];
        }
    };
    private static final String EXTRA_BASE_RESULT = "android.credentials.selection.extra.BASE_RESULT";
    public static final int RESULT_CODE_CANCELED_AND_LAUNCHED_SETTINGS = 1;
    public static final int RESULT_CODE_DATA_PARSING_FAILURE = 3;
    public static final int RESULT_CODE_DIALOG_COMPLETE_WITH_SELECTION = 2;
    public static final int RESULT_CODE_DIALOG_USER_CANCELED = 0;

    @Deprecated
    private final IBinder mRequestToken;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResultCode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static BaseDialogResult fromResultData(Bundle bundle) {
        return (BaseDialogResult) bundle.getParcelable(EXTRA_BASE_RESULT, BaseDialogResult.class);
    }

    public static void addToBundle(BaseDialogResult baseDialogResult, Bundle bundle) {
        bundle.putParcelable(EXTRA_BASE_RESULT, baseDialogResult);
    }

    public BaseDialogResult(IBinder iBinder) {
        this.mRequestToken = iBinder;
    }

    @Deprecated
    public IBinder getRequestToken() {
        return this.mRequestToken;
    }

    protected BaseDialogResult(Parcel parcel) {
        this.mRequestToken = parcel.readStrongBinder();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mRequestToken);
    }
}
