package android.credentials.selection;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;

/* loaded from: classes.dex */
public final class UserSelectionDialogResult extends BaseDialogResult implements Parcelable {
    public static final Parcelable.Creator<UserSelectionDialogResult> CREATOR = new Parcelable.Creator<UserSelectionDialogResult>() { // from class: android.credentials.selection.UserSelectionDialogResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserSelectionDialogResult createFromParcel(Parcel parcel) {
            return new UserSelectionDialogResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserSelectionDialogResult[] newArray(int i) {
            return new UserSelectionDialogResult[i];
        }
    };
    private static final String EXTRA_USER_SELECTION_RESULT = "android.credentials.selection.extra.USER_SELECTION_RESULT";
    private final String mEntryKey;
    private final String mEntrySubkey;
    private final String mProviderId;
    private ProviderPendingIntentResponse mProviderPendingIntentResponse;

    @Override // android.credentials.selection.BaseDialogResult, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static UserSelectionDialogResult fromResultData(Bundle bundle) {
        return (UserSelectionDialogResult) bundle.getParcelable(EXTRA_USER_SELECTION_RESULT, UserSelectionDialogResult.class);
    }

    public static void addToBundle(UserSelectionDialogResult userSelectionDialogResult, Bundle bundle) {
        bundle.putParcelable(EXTRA_USER_SELECTION_RESULT, userSelectionDialogResult);
    }

    public UserSelectionDialogResult(IBinder iBinder, String str, String str2, String str3) {
        super(iBinder);
        this.mProviderId = str;
        this.mEntryKey = str2;
        this.mEntrySubkey = str3;
    }

    public UserSelectionDialogResult(IBinder iBinder, String str, String str2, String str3, ProviderPendingIntentResponse providerPendingIntentResponse) {
        super(iBinder);
        this.mProviderId = str;
        this.mEntryKey = str2;
        this.mEntrySubkey = str3;
        this.mProviderPendingIntentResponse = providerPendingIntentResponse;
    }

    public String getProviderId() {
        return this.mProviderId;
    }

    public String getEntryKey() {
        return this.mEntryKey;
    }

    public String getEntrySubkey() {
        return this.mEntrySubkey;
    }

    public ProviderPendingIntentResponse getPendingIntentProviderResponse() {
        return this.mProviderPendingIntentResponse;
    }

    private UserSelectionDialogResult(Parcel parcel) {
        super(parcel);
        String readString8 = parcel.readString8();
        String readString82 = parcel.readString8();
        String readString83 = parcel.readString8();
        this.mProviderId = readString8;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString8);
        this.mEntryKey = readString82;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString82);
        this.mEntrySubkey = readString83;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString83);
        this.mProviderPendingIntentResponse = (ProviderPendingIntentResponse) parcel.readTypedObject(ProviderPendingIntentResponse.CREATOR);
    }

    @Override // android.credentials.selection.BaseDialogResult, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString8(this.mProviderId);
        parcel.writeString8(this.mEntryKey);
        parcel.writeString8(this.mEntrySubkey);
        parcel.writeTypedObject(this.mProviderPendingIntentResponse, i);
    }
}
