package android.media;

import android.annotation.SystemApi;
import android.media.RouteDiscoveryPreference;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public final class RouteDiscoveryPreference implements Parcelable {
    public static final Parcelable.Creator<RouteDiscoveryPreference> CREATOR = new Parcelable.Creator<RouteDiscoveryPreference>() { // from class: android.media.RouteDiscoveryPreference.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RouteDiscoveryPreference createFromParcel(Parcel parcel) {
            return new RouteDiscoveryPreference(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RouteDiscoveryPreference[] newArray(int i) {
            return new RouteDiscoveryPreference[i];
        }
    };

    @SystemApi
    public static final RouteDiscoveryPreference EMPTY = new Builder(Collections.EMPTY_LIST, false).build();
    private final List<String> mAllowedPackages;
    private final Bundle mExtras;
    private final List<String> mPackageOrder;
    private final List<String> mPreferredFeatures;
    private final boolean mShouldPerformActiveScan;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    RouteDiscoveryPreference(Builder builder) {
        this.mPreferredFeatures = builder.mPreferredFeatures;
        this.mPackageOrder = builder.mPackageOrder;
        this.mAllowedPackages = builder.mAllowedPackages;
        this.mShouldPerformActiveScan = builder.mActiveScan;
        this.mExtras = builder.mExtras;
    }

    RouteDiscoveryPreference(Parcel parcel) {
        this.mPreferredFeatures = parcel.createStringArrayList();
        this.mPackageOrder = parcel.createStringArrayList();
        this.mAllowedPackages = parcel.createStringArrayList();
        this.mShouldPerformActiveScan = parcel.readBoolean();
        this.mExtras = parcel.readBundle();
    }

    public List<String> getPreferredFeatures() {
        return this.mPreferredFeatures;
    }

    public List<String> getDeduplicationPackageOrder() {
        return this.mPackageOrder;
    }

    public List<String> getAllowedPackages() {
        return this.mAllowedPackages;
    }

    public boolean shouldPerformActiveScan() {
        return this.mShouldPerformActiveScan;
    }

    public boolean shouldRemoveDuplicates() {
        return !this.mPackageOrder.isEmpty();
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(this.mPreferredFeatures);
        parcel.writeStringList(this.mPackageOrder);
        parcel.writeStringList(this.mAllowedPackages);
        parcel.writeBoolean(this.mShouldPerformActiveScan);
        parcel.writeBundle(this.mExtras);
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.println(str + "RouteDiscoveryPreference");
        String str2 = str + "  ";
        printWriter.println(str2 + "mShouldPerformActiveScan=" + this.mShouldPerformActiveScan);
        printWriter.println(str2 + "mPreferredFeatures=" + this.mPreferredFeatures);
        printWriter.println(str2 + "mPackageOrder=" + this.mPackageOrder);
        printWriter.println(str2 + "mAllowedPackages=" + this.mAllowedPackages);
        printWriter.println(str2 + "mExtras=" + this.mExtras);
    }

    public String toString() {
        return "RouteDiscoveryRequest{ preferredFeatures={" + String.join(", ", this.mPreferredFeatures) + "}, activeScan=" + this.mShouldPerformActiveScan + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RouteDiscoveryPreference)) {
            return false;
        }
        RouteDiscoveryPreference routeDiscoveryPreference = (RouteDiscoveryPreference) obj;
        return Objects.equals(this.mPreferredFeatures, routeDiscoveryPreference.mPreferredFeatures) && Objects.equals(this.mPackageOrder, routeDiscoveryPreference.mPackageOrder) && Objects.equals(this.mAllowedPackages, routeDiscoveryPreference.mAllowedPackages) && this.mShouldPerformActiveScan == routeDiscoveryPreference.mShouldPerformActiveScan;
    }

    public int hashCode() {
        return Objects.hash(this.mPreferredFeatures, this.mPackageOrder, this.mAllowedPackages, Boolean.valueOf(this.mShouldPerformActiveScan));
    }

    public static final class Builder {
        boolean mActiveScan;
        List<String> mAllowedPackages;
        Bundle mExtras;
        List<String> mPackageOrder;
        List<String> mPreferredFeatures;

        public Builder(List<String> list, boolean z) {
            Objects.requireNonNull(list, "preferredFeatures must not be null");
            this.mPreferredFeatures = (List) list.stream().filter(new Predicate() { // from class: android.media.RouteDiscoveryPreference$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return RouteDiscoveryPreference.Builder.lambda$new$0((String) obj);
                }
            }).collect(Collectors.toList());
            this.mPackageOrder = Collections.EMPTY_LIST;
            this.mAllowedPackages = Collections.EMPTY_LIST;
            this.mActiveScan = z;
        }

        static /* synthetic */ boolean lambda$new$0(String str) {
            return !TextUtils.isEmpty(str);
        }

        public Builder(RouteDiscoveryPreference routeDiscoveryPreference) {
            Objects.requireNonNull(routeDiscoveryPreference, "preference must not be null");
            this.mPreferredFeatures = routeDiscoveryPreference.getPreferredFeatures();
            this.mPackageOrder = routeDiscoveryPreference.getDeduplicationPackageOrder();
            this.mAllowedPackages = routeDiscoveryPreference.getAllowedPackages();
            this.mActiveScan = routeDiscoveryPreference.shouldPerformActiveScan();
            this.mExtras = routeDiscoveryPreference.getExtras();
        }

        public Builder(Collection<RouteDiscoveryPreference> collection) {
            Objects.requireNonNull(collection, "preferences must not be null");
            HashSet hashSet = new HashSet();
            HashSet hashSet2 = new HashSet();
            this.mPackageOrder = Collections.EMPTY_LIST;
            boolean z = false;
            for (RouteDiscoveryPreference routeDiscoveryPreference : collection) {
                hashSet.addAll(routeDiscoveryPreference.mPreferredFeatures);
                hashSet2.addAll(routeDiscoveryPreference.mAllowedPackages);
                z |= routeDiscoveryPreference.mShouldPerformActiveScan;
                if (this.mPackageOrder.isEmpty() && !routeDiscoveryPreference.mPackageOrder.isEmpty()) {
                    this.mPackageOrder = List.copyOf(routeDiscoveryPreference.mPackageOrder);
                }
            }
            this.mPreferredFeatures = List.copyOf(hashSet);
            this.mAllowedPackages = List.copyOf(hashSet2);
            this.mActiveScan = z;
        }

        public Builder setPreferredFeatures(List<String> list) {
            Objects.requireNonNull(list, "preferredFeatures must not be null");
            this.mPreferredFeatures = (List) list.stream().filter(new Predicate() { // from class: android.media.RouteDiscoveryPreference$Builder$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return RouteDiscoveryPreference.Builder.lambda$setPreferredFeatures$1((String) obj);
                }
            }).collect(Collectors.toList());
            return this;
        }

        static /* synthetic */ boolean lambda$setPreferredFeatures$1(String str) {
            return !TextUtils.isEmpty(str);
        }

        public Builder setAllowedPackages(List<String> list) {
            Objects.requireNonNull(list, "allowedPackages must not be null");
            this.mAllowedPackages = List.copyOf(list);
            return this;
        }

        public Builder setShouldPerformActiveScan(boolean z) {
            this.mActiveScan = z;
            return this;
        }

        public Builder setDeduplicationPackageOrder(List<String> list) {
            Objects.requireNonNull(list, "packageOrder must not be null");
            this.mPackageOrder = List.copyOf(list);
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public RouteDiscoveryPreference build() {
            return new RouteDiscoveryPreference(this);
        }
    }
}
