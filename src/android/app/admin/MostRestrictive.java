package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/* loaded from: classes.dex */
public final class MostRestrictive<V> extends ResolutionMechanism<V> {
    public static final Parcelable.Creator<MostRestrictive<?>> CREATOR = new Parcelable.Creator<MostRestrictive<?>>() { // from class: android.app.admin.MostRestrictive.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MostRestrictive<?> createFromParcel(Parcel parcel) {
            return new MostRestrictive<>(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MostRestrictive<?>[] newArray(int i) {
            return new MostRestrictive[i];
        }
    };
    private final List<PolicyValue<V>> mMostToLeastRestrictive;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MostRestrictive(List<PolicyValue<V>> list) {
        this.mMostToLeastRestrictive = new ArrayList(list);
    }

    public List<V> getMostToLeastRestrictiveValues() {
        return this.mMostToLeastRestrictive.stream().map(new Function() { // from class: android.app.admin.MostRestrictive$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((PolicyValue) obj).getValue();
            }
        }).toList();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            try {
                return Objects.equals(this.mMostToLeastRestrictive, ((MostRestrictive) obj).mMostToLeastRestrictive);
            } catch (ClassCastException unused) {
            }
        }
        return false;
    }

    public int hashCode() {
        return this.mMostToLeastRestrictive.hashCode();
    }

    public MostRestrictive(Parcel parcel) {
        this.mMostToLeastRestrictive = new ArrayList();
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            this.mMostToLeastRestrictive.add((PolicyValue) parcel.readParcelable(PolicyValue.class.getClassLoader()));
        }
    }

    public String toString() {
        return "MostRestrictive { mMostToLeastRestrictive= " + this.mMostToLeastRestrictive + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mMostToLeastRestrictive.size());
        Iterator<PolicyValue<V>> it = this.mMostToLeastRestrictive.iterator();
        while (it.hasNext()) {
            parcel.writeParcelable(it.next(), i);
        }
    }
}
