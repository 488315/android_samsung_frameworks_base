package android.credentials.selection;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;

/* loaded from: classes.dex */
public abstract class ProviderData implements Parcelable {
    public static final String EXTRA_DISABLED_PROVIDER_DATA_LIST = "android.credentials.selection.extra.DISABLED_PROVIDER_DATA_LIST";
    public static final String EXTRA_ENABLED_PROVIDER_DATA_LIST = "android.credentials.selection.extra.ENABLED_PROVIDER_DATA_LIST";
    private final String mProviderFlattenedComponentName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ProviderData(String str) {
        this.mProviderFlattenedComponentName = str;
    }

    public String getProviderFlattenedComponentName() {
        return this.mProviderFlattenedComponentName;
    }

    protected ProviderData(Parcel parcel) {
        String readString8 = parcel.readString8();
        this.mProviderFlattenedComponentName = readString8;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString8);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mProviderFlattenedComponentName);
    }
}
