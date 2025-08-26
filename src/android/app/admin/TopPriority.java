package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class TopPriority<V> extends ResolutionMechanism<V> {
    public static final Parcelable.Creator<TopPriority<?>> CREATOR = new Parcelable.Creator<TopPriority<?>>() { // from class: android.app.admin.TopPriority.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TopPriority<?> createFromParcel(Parcel parcel) {
            return new TopPriority<>(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TopPriority<?>[] newArray(int i) {
            return new TopPriority[i];
        }
    };
    private final List<Authority> mHighestToLowestPriorityAuthorities;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TopPriority(List<Authority> list) {
        this.mHighestToLowestPriorityAuthorities = (List) Objects.requireNonNull(list);
    }

    private TopPriority(Parcel parcel) {
        this.mHighestToLowestPriorityAuthorities = new ArrayList();
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            this.mHighestToLowestPriorityAuthorities.add((Authority) parcel.readParcelable(Authority.class.getClassLoader()));
        }
    }

    public List<Authority> getHighestToLowestPriorityAuthorities() {
        return this.mHighestToLowestPriorityAuthorities;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            try {
                return Objects.equals(this.mHighestToLowestPriorityAuthorities, ((TopPriority) obj).mHighestToLowestPriorityAuthorities);
            } catch (ClassCastException unused) {
            }
        }
        return false;
    }

    public int hashCode() {
        return this.mHighestToLowestPriorityAuthorities.hashCode();
    }

    public String toString() {
        return "TopPriority { mHighestToLowestPriorityAuthorities= " + this.mHighestToLowestPriorityAuthorities + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mHighestToLowestPriorityAuthorities.size());
        Iterator<Authority> it = this.mHighestToLowestPriorityAuthorities.iterator();
        while (it.hasNext()) {
            parcel.writeParcelable(it.next(), i);
        }
    }
}
