package android.hardware.radio;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public final class Announcement implements Parcelable {
    public static final Parcelable.Creator<Announcement> CREATOR = new Parcelable.Creator<Announcement>() { // from class: android.hardware.radio.Announcement.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Announcement createFromParcel(Parcel parcel) {
            return new Announcement(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Announcement[] newArray(int i) {
            return new Announcement[i];
        }
    };
    public static final int TYPE_EMERGENCY = 1;
    public static final int TYPE_EVENT = 6;
    public static final int TYPE_MISC = 8;
    public static final int TYPE_NEWS = 5;
    public static final int TYPE_SPORT = 7;
    public static final int TYPE_TRAFFIC = 3;
    public static final int TYPE_WARNING = 2;
    public static final int TYPE_WEATHER = 4;
    private final ProgramSelector mSelector;
    private final int mType;
    private final Map<String, String> mVendorInfo;

    public interface OnListUpdatedListener {
        void onListUpdated(Collection<Announcement> collection);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Announcement(ProgramSelector programSelector, int i, Map<String, String> map) {
        this.mSelector = (ProgramSelector) Objects.requireNonNull(programSelector, "Program selector cannot be null");
        this.mType = i;
        this.mVendorInfo = (Map) Objects.requireNonNull(map, "Vendor info cannot be null");
    }

    private Announcement(Parcel parcel) {
        this.mSelector = (ProgramSelector) parcel.readTypedObject(ProgramSelector.CREATOR);
        this.mType = parcel.readInt();
        this.mVendorInfo = Utils.readStringMap(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mSelector, 0);
        parcel.writeInt(this.mType);
        Utils.writeStringMap(parcel, this.mVendorInfo);
    }

    public ProgramSelector getSelector() {
        return this.mSelector;
    }

    public int getType() {
        return this.mType;
    }

    public Map<String, String> getVendorInfo() {
        return this.mVendorInfo;
    }
}
