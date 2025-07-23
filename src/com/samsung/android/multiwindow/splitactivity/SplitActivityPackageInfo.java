package com.samsung.android.multiwindow.splitactivity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
public class SplitActivityPackageInfo implements Parcelable {
    public static final Parcelable.Creator<SplitActivityPackageInfo> CREATOR = new Parcelable.Creator<SplitActivityPackageInfo>() { // from class: com.samsung.android.multiwindow.splitactivity.SplitActivityPackageInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SplitActivityPackageInfo createFromParcel(Parcel parcel) {
            return new SplitActivityPackageInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SplitActivityPackageInfo[] newArray(int i) {
            return new SplitActivityPackageInfo[i];
        }
    };
    private final Set<String> mFullscreenActivities;
    private final List<SplitActivityInfo> mInfos;
    private final String mPackageName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SplitActivityPackageInfo(String str) {
        this.mInfos = new ArrayList();
        this.mFullscreenActivities = new ArraySet();
        this.mPackageName = str;
    }

    protected SplitActivityPackageInfo(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        this.mInfos = arrayList;
        ArraySet arraySet = new ArraySet();
        this.mFullscreenActivities = arraySet;
        this.mPackageName = parcel.readString();
        parcel.readTypedList(arrayList, SplitActivityInfo.CREATOR);
        String[] readStringArray = parcel.readStringArray();
        if (readStringArray != null) {
            Collections.addAll(arraySet, readStringArray);
        }
    }

    public void add(String str, String str2, int i) {
        this.mInfos.add(new SplitActivityInfo(str, str2, i));
    }

    public void remove(final String str, final String str2) {
        this.mInfos.removeIf(new Predicate() { // from class: com.samsung.android.multiwindow.splitactivity.SplitActivityPackageInfo$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean match;
                match = ((SplitActivityInfo) obj).match(str, str2);
                return match;
            }
        });
    }

    public boolean isEmpty() {
        return this.mInfos.isEmpty();
    }

    public SplitActivityInfo getInfo(final String str, final String str2) {
        return this.mInfos.stream().filter(new Predicate() { // from class: com.samsung.android.multiwindow.splitactivity.SplitActivityPackageInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean matchWithWildcard;
                matchWithWildcard = ((SplitActivityInfo) obj).matchWithWildcard(str, str2);
                return matchWithWildcard;
            }
        }).findFirst().orElse(null);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SplitActivityPackageInfo splitActivityPackageInfo = (SplitActivityPackageInfo) obj;
            if (Objects.equals(this.mPackageName, splitActivityPackageInfo.mPackageName) && Objects.equals(this.mInfos, splitActivityPackageInfo.mInfos) && Objects.equals(this.mFullscreenActivities, splitActivityPackageInfo.mFullscreenActivities)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mPackageName, this.mInfos, this.mFullscreenActivities);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeTypedList(this.mInfos);
        String[] strArr = new String[this.mFullscreenActivities.size()];
        this.mFullscreenActivities.toArray(strArr);
        parcel.writeStringArray(strArr);
    }

    public String toShortString() {
        return this.mPackageName + " : " + ((String) this.mInfos.stream().map(new Function() { // from class: com.samsung.android.multiwindow.splitactivity.SplitActivityPackageInfo$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((SplitActivityInfo) obj).toShortString();
            }
        }).collect(Collectors.joining(", ")));
    }
}
