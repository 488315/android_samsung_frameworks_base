package android.credentials.selection;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.app.slice.Slice;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class AuthenticationEntry implements Parcelable {
    public static final Parcelable.Creator<AuthenticationEntry> CREATOR =
            new Parcelable.Creator<AuthenticationEntry>() { // from class:
                // android.credentials.selection.AuthenticationEntry.1
                @Override // android.os.Parcelable.Creator
                public AuthenticationEntry createFromParcel(Parcel in) {
                    return new AuthenticationEntry(in);
                }

                @Override // android.os.Parcelable.Creator
                public AuthenticationEntry[] newArray(int size) {
                    return new AuthenticationEntry[size];
                }
            };
    public static final int STATUS_LOCKED = 0;
    public static final int STATUS_UNLOCKED_BUT_EMPTY_LESS_RECENT = 1;
    public static final int STATUS_UNLOCKED_BUT_EMPTY_MOST_RECENT = 2;
    private Intent mFrameworkExtrasIntent;
    private final String mKey;
    private final Slice mSlice;
    private final int mStatus;
    private final String mSubkey;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {}

    private AuthenticationEntry(Parcel in) {
        this.mKey = in.readString8();
        this.mSubkey = in.readString8();
        this.mStatus = in.readInt();
        this.mSlice = (Slice) in.readTypedObject(Slice.CREATOR);
        this.mFrameworkExtrasIntent = (Intent) in.readTypedObject(Intent.CREATOR);
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mKey);
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSubkey);
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSlice);
    }

    public AuthenticationEntry(String key, String subkey, Slice slice, int status, Intent intent) {
        this.mKey = key;
        this.mSubkey = subkey;
        this.mSlice = slice;
        this.mStatus = status;
        this.mFrameworkExtrasIntent = intent;
    }

    public String getKey() {
        return this.mKey;
    }

    public String getSubkey() {
        return this.mSubkey;
    }

    public Slice getSlice() {
        return this.mSlice;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public Intent getFrameworkExtrasIntent() {
        return this.mFrameworkExtrasIntent;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString8(this.mKey);
        dest.writeString8(this.mSubkey);
        dest.writeInt(this.mStatus);
        dest.writeTypedObject(this.mSlice, flags);
        dest.writeTypedObject(this.mFrameworkExtrasIntent, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
