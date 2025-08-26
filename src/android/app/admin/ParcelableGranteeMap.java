package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.ArraySet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class ParcelableGranteeMap implements Parcelable {
    public static final Parcelable.Creator<ParcelableGranteeMap> CREATOR = new Parcelable.Creator<ParcelableGranteeMap>() { // from class: android.app.admin.ParcelableGranteeMap.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableGranteeMap createFromParcel(Parcel parcel) {
            ArrayMap arrayMap = new ArrayMap();
            int i = parcel.readInt();
            for (int i2 = 0; i2 < i; i2++) {
                arrayMap.put(Integer.valueOf(parcel.readInt()), new ArraySet(parcel.readStringArray()));
            }
            return new ParcelableGranteeMap(arrayMap);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableGranteeMap[] newArray(int i) {
            return new ParcelableGranteeMap[i];
        }
    };
    private final Map<Integer, Set<String>> mPackagesByUid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPackagesByUid.size());
        for (Map.Entry<Integer, Set<String>> entry : this.mPackagesByUid.entrySet()) {
            parcel.writeInt(entry.getKey().intValue());
            parcel.writeStringArray((String[]) entry.getValue().toArray(new String[0]));
        }
    }

    public ParcelableGranteeMap(Map<Integer, Set<String>> map) {
        this.mPackagesByUid = map;
    }

    public Map<Integer, Set<String>> getPackagesByUid() {
        return this.mPackagesByUid;
    }
}
