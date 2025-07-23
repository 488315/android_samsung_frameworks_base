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
/* loaded from: classes.dex */
public final class AuthenticationEntry implements Parcelable {
    public static final Parcelable.Creator<AuthenticationEntry> CREATOR = new Parcelable.Creator<AuthenticationEntry>() { // from class: android.credentials.selection.AuthenticationEntry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticationEntry createFromParcel(Parcel parcel) {
            return new AuthenticationEntry(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticationEntry[] newArray(int i) {
            return new AuthenticationEntry[i];
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
    public @interface Status {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AuthenticationEntry(Parcel parcel) {
        String readString8 = parcel.readString8();
        this.mKey = readString8;
        String readString82 = parcel.readString8();
        this.mSubkey = readString82;
        this.mStatus = parcel.readInt();
        Slice slice = (Slice) parcel.readTypedObject(Slice.CREATOR);
        this.mSlice = slice;
        this.mFrameworkExtrasIntent = (Intent) parcel.readTypedObject(Intent.CREATOR);
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString8);
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString82);
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) slice);
    }

    public AuthenticationEntry(String str, String str2, Slice slice, int i, Intent intent) {
        this.mKey = str;
        this.mSubkey = str2;
        this.mSlice = slice;
        this.mStatus = i;
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mKey);
        parcel.writeString8(this.mSubkey);
        parcel.writeInt(this.mStatus);
        parcel.writeTypedObject(this.mSlice, i);
        parcel.writeTypedObject(this.mFrameworkExtrasIntent, i);
    }
}
