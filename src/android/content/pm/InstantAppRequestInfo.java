package android.content.pm;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import com.android.internal.util.AnnotationValidations;

@SystemApi
/* loaded from: classes.dex */
public final class InstantAppRequestInfo implements Parcelable {
    public static final Parcelable.Creator<InstantAppRequestInfo> CREATOR = new Parcelable.Creator<InstantAppRequestInfo>() { // from class: android.content.pm.InstantAppRequestInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstantAppRequestInfo[] newArray(int i) {
            return new InstantAppRequestInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstantAppRequestInfo createFromParcel(Parcel parcel) {
            return new InstantAppRequestInfo(parcel);
        }
    };
    private final int[] mHostDigestPrefix;
    private final Intent mIntent;
    private final boolean mRequesterInstantApp;
    private final String mToken;
    private final UserHandle mUserHandle;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public InstantAppRequestInfo(Intent intent, int[] iArr, UserHandle userHandle, boolean z, String str) {
        this.mIntent = intent;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) intent);
        this.mHostDigestPrefix = iArr;
        this.mUserHandle = userHandle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) userHandle);
        this.mRequesterInstantApp = z;
        this.mToken = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public int[] getHostDigestPrefix() {
        return this.mHostDigestPrefix;
    }

    public UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public boolean isRequesterInstantApp() {
        return this.mRequesterInstantApp;
    }

    public String getToken() {
        return this.mToken;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.mRequesterInstantApp ? (byte) 8 : (byte) 0;
        if (this.mHostDigestPrefix != null) {
            b = (byte) (b | 2);
        }
        parcel.writeByte(b);
        parcel.writeTypedObject(this.mIntent, i);
        int[] iArr = this.mHostDigestPrefix;
        if (iArr != null) {
            parcel.writeIntArray(iArr);
        }
        parcel.writeTypedObject(this.mUserHandle, i);
        parcel.writeString(this.mToken);
    }

    InstantAppRequestInfo(Parcel parcel) {
        byte b = parcel.readByte();
        boolean z = (b & 8) != 0;
        Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
        int[] iArrCreateIntArray = (b & 2) == 0 ? null : parcel.createIntArray();
        UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
        String string = parcel.readString();
        this.mIntent = intent;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) intent);
        this.mHostDigestPrefix = iArrCreateIntArray;
        this.mUserHandle = userHandle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) userHandle);
        this.mRequesterInstantApp = z;
        this.mToken = string;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string);
    }
}
