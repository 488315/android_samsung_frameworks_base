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
    public static final Parcelable.Creator<Entry> CREATOR = new Parcelable.Creator<Entry>() { // from class: android.credentials.selection.Entry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Entry createFromParcel(Parcel parcel) {
            return new Entry(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Entry[] newArray(int i) {
            return new Entry[i];
        }
    };
    private Intent mFrameworkExtrasIntent;
    private final String mKey;
    private PendingIntent mPendingIntent;
    private final Slice mSlice;
    private final String mSubkey;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private Entry(Parcel parcel) {
        String string8 = parcel.readString8();
        String string82 = parcel.readString8();
        Slice slice = (Slice) parcel.readTypedObject(Slice.CREATOR);
        this.mKey = string8;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string8);
        this.mSubkey = string82;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string82);
        this.mSlice = slice;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) slice);
        this.mPendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
        this.mFrameworkExtrasIntent = (Intent) parcel.readTypedObject(Intent.CREATOR);
    }

    public Entry(String str, String str2, Slice slice, Intent intent) {
        this.mKey = str;
        this.mSubkey = str2;
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mKey);
        parcel.writeString8(this.mSubkey);
        parcel.writeTypedObject(this.mSlice, i);
        parcel.writeTypedObject(this.mPendingIntent, i);
        parcel.writeTypedObject(this.mFrameworkExtrasIntent, i);
    }
}
