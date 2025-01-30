package com.samsung.android.multiwindow.splitactivity;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.ArraySet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes5.dex */
public class SplitActivityPackageInfo implements Parcelable {
  public static final Parcelable.Creator<SplitActivityPackageInfo> CREATOR =
      new Parcelable.Creator<
          SplitActivityPackageInfo>() { // from class:
                                        // com.samsung.android.multiwindow.splitactivity.SplitActivityPackageInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SplitActivityPackageInfo createFromParcel(Parcel in) {
          return new SplitActivityPackageInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SplitActivityPackageInfo[] newArray(int size) {
          return new SplitActivityPackageInfo[size];
        }
      };
  private final Set<String> mFullscreenActivities;
  private final List<SplitActivityInfo> mInfos;
  private final String mPackageName;

  public SplitActivityPackageInfo(String packageName) {
    this.mInfos = new ArrayList();
    this.mFullscreenActivities = new ArraySet();
    this.mPackageName = packageName;
  }

  protected SplitActivityPackageInfo(Parcel in) {
    ArrayList arrayList = new ArrayList();
    this.mInfos = arrayList;
    ArraySet arraySet = new ArraySet();
    this.mFullscreenActivities = arraySet;
    this.mPackageName = in.readString();
    in.readTypedList(arrayList, SplitActivityInfo.CREATOR);
    String[] fullscreenActivities = in.readStringArray();
    if (fullscreenActivities != null) {
      Collections.addAll(arraySet, fullscreenActivities);
    }
  }

  public void add(String source, String target, int mode) {
    this.mInfos.add(new SplitActivityInfo(source, target, mode));
  }

  public void remove(final String sourceName, final String targetName) {
    this.mInfos.removeIf(
        new Predicate() { // from class:
                          // com.samsung.android.multiwindow.splitactivity.SplitActivityPackageInfo$$ExternalSyntheticLambda2
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean match;
            match = ((SplitActivityInfo) obj).match(sourceName, targetName);
            return match;
          }
        });
  }

  public boolean isEmpty() {
    return this.mInfos.isEmpty();
  }

  public SplitActivityInfo getInfo(final String source, final String target) {
    return this.mInfos.stream()
        .filter(
            new Predicate() { // from class:
                              // com.samsung.android.multiwindow.splitactivity.SplitActivityPackageInfo$$ExternalSyntheticLambda0
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean matchWithWildcard;
                matchWithWildcard = ((SplitActivityInfo) obj).matchWithWildcard(source, target);
                return matchWithWildcard;
              }
            })
        .findFirst()
        .orElse(null);
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SplitActivityPackageInfo that = (SplitActivityPackageInfo) o;
    if (Objects.equals(this.mPackageName, that.mPackageName)
        && Objects.equals(this.mInfos, that.mInfos)
        && Objects.equals(this.mFullscreenActivities, that.mFullscreenActivities)) {
      return true;
    }
    return false;
  }

  public int hashCode() {
    return Objects.hash(this.mPackageName, this.mInfos, this.mFullscreenActivities);
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.mPackageName);
    dest.writeTypedList(this.mInfos);
    String[] tmpArray = new String[this.mFullscreenActivities.size()];
    this.mFullscreenActivities.toArray(tmpArray);
    dest.writeStringArray(tmpArray);
  }

  public String toShortString() {
    return this.mPackageName
        + " : "
        + ((String)
            this.mInfos.stream()
                .map(
                    new Function() { // from class:
                                     // com.samsung.android.multiwindow.splitactivity.SplitActivityPackageInfo$$ExternalSyntheticLambda1
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        return ((SplitActivityInfo) obj).toShortString();
                      }
                    })
                .collect(Collectors.joining(", ")));
  }
}
