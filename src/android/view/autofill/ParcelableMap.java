package android.view.autofill;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
class ParcelableMap extends HashMap<AutofillId, AutofillValue> implements Parcelable {
    public static final Parcelable.Creator<ParcelableMap> CREATOR = new Parcelable.Creator<ParcelableMap>() { // from class: android.view.autofill.ParcelableMap.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableMap createFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            ParcelableMap parcelableMap = new ParcelableMap(readInt);
            for (int i = 0; i < readInt; i++) {
                parcelableMap.put((AutofillId) parcel.readParcelable(null, AutofillId.class), (AutofillValue) parcel.readParcelable(null, AutofillValue.class));
            }
            return parcelableMap;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableMap[] newArray(int i) {
            return new ParcelableMap[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ParcelableMap(int i) {
        super(i);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(size());
        for (Map.Entry<AutofillId, AutofillValue> entry : entrySet()) {
            parcel.writeParcelable(entry.getKey(), 0);
            parcel.writeParcelable(entry.getValue(), 0);
        }
    }
}
