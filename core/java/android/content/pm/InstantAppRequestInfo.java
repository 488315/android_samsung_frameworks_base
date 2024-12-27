package android.content.pm;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;

import com.android.internal.util.AnnotationValidations;

@SystemApi
public final class InstantAppRequestInfo implements Parcelable {
    public static final Parcelable.Creator<InstantAppRequestInfo> CREATOR =
            new Parcelable.Creator<InstantAppRequestInfo>() { // from class:
                // android.content.pm.InstantAppRequestInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public InstantAppRequestInfo[] newArray(int size) {
                    return new InstantAppRequestInfo[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public InstantAppRequestInfo createFromParcel(Parcel in) {
                    return new InstantAppRequestInfo(in);
                }
            };
    private final int[] mHostDigestPrefix;
    private final Intent mIntent;
    private final boolean mRequesterInstantApp;
    private final String mToken;
    private final UserHandle mUserHandle;

    public InstantAppRequestInfo(
            Intent intent,
            int[] hostDigestPrefix,
            UserHandle userHandle,
            boolean requesterInstantApp,
            String token) {
        this.mIntent = intent;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mIntent);
        this.mHostDigestPrefix = hostDigestPrefix;
        this.mUserHandle = userHandle;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mUserHandle);
        this.mRequesterInstantApp = requesterInstantApp;
        this.mToken = token;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mToken);
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
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.mRequesterInstantApp ? (byte) (0 | 8) : (byte) 0;
        if (this.mHostDigestPrefix != null) {
            flg = (byte) (flg | 2);
        }
        dest.writeByte(flg);
        dest.writeTypedObject(this.mIntent, flags);
        if (this.mHostDigestPrefix != null) {
            dest.writeIntArray(this.mHostDigestPrefix);
        }
        dest.writeTypedObject(this.mUserHandle, flags);
        dest.writeString(this.mToken);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    InstantAppRequestInfo(Parcel in) {
        byte flg = in.readByte();
        boolean requesterInstantApp = (flg & 8) != 0;
        Intent intent = (Intent) in.readTypedObject(Intent.CREATOR);
        int[] hostDigestPrefix = (flg & 2) == 0 ? null : in.createIntArray();
        UserHandle userHandle = (UserHandle) in.readTypedObject(UserHandle.CREATOR);
        String token = in.readString();
        this.mIntent = intent;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mIntent);
        this.mHostDigestPrefix = hostDigestPrefix;
        this.mUserHandle = userHandle;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mUserHandle);
        this.mRequesterInstantApp = requesterInstantApp;
        this.mToken = token;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mToken);
    }

    @Deprecated
    private void __metadata() {}
}
