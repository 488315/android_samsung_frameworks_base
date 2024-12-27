package android.credentials.selection;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.app.slice.Slice;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

@SystemApi
/* loaded from: classes.dex */
public final class Entry implements Parcelable {
    public static final Parcelable.Creator<Entry> CREATOR =
            new Parcelable.Creator<Entry>() { // from class: android.credentials.selection.Entry.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Entry createFromParcel(Parcel in) {
                    return new Entry(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Entry[] newArray(int size) {
                    return new Entry[size];
                }
            };
    private Intent mFrameworkExtrasIntent;
    private final String mKey;
    private PendingIntent mPendingIntent;
    private final Slice mSlice;
    private final String mSubkey;

    private Entry(Parcel in) {
        String key = in.readString8();
        String subkey = in.readString8();
        Slice slice = (Slice) in.readTypedObject(Slice.CREATOR);
        this.mKey = key;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mKey);
        this.mSubkey = subkey;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSubkey);
        this.mSlice = slice;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSlice);
        this.mPendingIntent = (PendingIntent) in.readTypedObject(PendingIntent.CREATOR);
        this.mFrameworkExtrasIntent = (Intent) in.readTypedObject(Intent.CREATOR);
    }

    public Entry(String key, String subkey, Slice slice, Intent intent) {
        this.mKey = key;
        this.mSubkey = subkey;
        this.mSlice = slice;
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

    public Intent getFrameworkExtrasIntent() {
        return this.mFrameworkExtrasIntent;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString8(this.mKey);
        dest.writeString8(this.mSubkey);
        dest.writeTypedObject(this.mSlice, flags);
        dest.writeTypedObject(this.mPendingIntent, flags);
        dest.writeTypedObject(this.mFrameworkExtrasIntent, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
