package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class SharedLibraryInfo implements Parcelable {
    public static final Parcelable.Creator<SharedLibraryInfo> CREATOR = new Parcelable.Creator<SharedLibraryInfo>() { // from class: android.content.pm.SharedLibraryInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SharedLibraryInfo createFromParcel(Parcel parcel) {
            return new SharedLibraryInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SharedLibraryInfo[] newArray(int i) {
            return new SharedLibraryInfo[i];
        }
    };
    public static final int TYPE_BUILTIN = 0;
    public static final int TYPE_DYNAMIC = 1;
    public static final int TYPE_SDK_PACKAGE = 3;
    public static final int TYPE_STATIC = 2;
    public static final int VERSION_UNDEFINED = -1;
    private final List<String> mCertDigests;
    private List<String> mCodePaths;
    private final VersionedPackage mDeclaringPackage;
    private List<SharedLibraryInfo> mDependencies;
    private final List<VersionedPackage> mDependentPackages;
    private final boolean mIsNative;
    private final String mName;
    private final List<VersionedPackage> mOptionalDependentPackages;
    private final String mPackageName;
    private final String mPath;
    private final int mType;
    private final long mVersion;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SharedLibraryInfo(String str, String str2, List<String> list, String str3, long j, int i, VersionedPackage versionedPackage, List<VersionedPackage> list2, List<SharedLibraryInfo> list3, boolean z) {
        this.mPath = str;
        this.mPackageName = str2;
        this.mCodePaths = list;
        this.mName = str3;
        this.mVersion = j;
        this.mType = i;
        this.mDeclaringPackage = versionedPackage;
        this.mDependentPackages = list2;
        this.mDependencies = list3;
        this.mIsNative = z;
        this.mOptionalDependentPackages = null;
        this.mCertDigests = null;
    }

    public SharedLibraryInfo(String str, String str2, List<String> list, String str3, long j, int i, VersionedPackage versionedPackage, List<SharedLibraryInfo> list2, boolean z, Pair<List<VersionedPackage>, List<Boolean>> pair) {
        this.mPath = str;
        this.mPackageName = str2;
        this.mCodePaths = list;
        this.mName = str3;
        this.mVersion = j;
        this.mType = i;
        this.mDeclaringPackage = versionedPackage;
        this.mDependencies = list2;
        this.mIsNative = z;
        ArrayList arrayList = null;
        this.mCertDigests = null;
        List<VersionedPackage> list3 = pair.first;
        List<Boolean> list4 = pair.second;
        this.mDependentPackages = list3;
        if (i == 3 && Flags.sdkLibIndependence() && list3 != null && list4 != null && list3.size() == list4.size()) {
            for (int i2 = 0; i2 < list3.size(); i2++) {
                VersionedPackage versionedPackage2 = list3.get(i2);
                if (list4.get(i2).booleanValue()) {
                    arrayList = arrayList == null ? new ArrayList() : arrayList;
                    arrayList.add(versionedPackage2);
                }
            }
        }
        this.mOptionalDependentPackages = arrayList;
    }

    private SharedLibraryInfo(Parcel parcel) {
        this.mPath = parcel.readString8();
        this.mPackageName = parcel.readString8();
        if (parcel.readInt() != 0) {
            this.mCodePaths = Arrays.asList(parcel.createString8Array());
        } else {
            this.mCodePaths = null;
        }
        this.mName = parcel.readString8();
        this.mVersion = parcel.readLong();
        this.mType = parcel.readInt();
        this.mDeclaringPackage = (VersionedPackage) parcel.readParcelable(null, VersionedPackage.class);
        this.mDependentPackages = parcel.readArrayList(null, VersionedPackage.class);
        this.mDependencies = parcel.createTypedArrayList(CREATOR);
        this.mIsNative = parcel.readBoolean();
        this.mOptionalDependentPackages = parcel.readParcelableList(new ArrayList(), VersionedPackage.class.getClassLoader(), VersionedPackage.class);
        this.mCertDigests = parcel.createStringArrayList();
    }

    public SharedLibraryInfo(String str, long j, int i) {
        this.mPath = null;
        this.mPackageName = null;
        this.mName = str;
        this.mVersion = j;
        this.mType = i;
        this.mDeclaringPackage = null;
        this.mDependentPackages = null;
        this.mDependencies = null;
        this.mIsNative = false;
        this.mOptionalDependentPackages = null;
        this.mCertDigests = null;
    }

    public SharedLibraryInfo(String str, long j, int i, List<String> list) {
        this.mPath = null;
        this.mPackageName = null;
        this.mName = str;
        this.mVersion = j;
        this.mType = i;
        this.mDeclaringPackage = null;
        this.mDependentPackages = null;
        this.mDependencies = null;
        this.mIsNative = false;
        this.mOptionalDependentPackages = null;
        this.mCertDigests = list;
    }

    public int getType() {
        return this.mType;
    }

    public boolean isNative() {
        return this.mIsNative;
    }

    public String getName() {
        return this.mName;
    }

    public String getPath() {
        return this.mPath;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public List<String> getAllCodePaths() {
        if (getPath() != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(getPath());
            return arrayList;
        }
        return (List) Objects.requireNonNull(this.mCodePaths);
    }

    public void setAllCodePaths(List<String> list) {
        this.mCodePaths = list;
    }

    public void addDependency(SharedLibraryInfo sharedLibraryInfo) {
        if (sharedLibraryInfo == null) {
            return;
        }
        if (this.mDependencies == null) {
            this.mDependencies = new ArrayList();
        }
        this.mDependencies.add(sharedLibraryInfo);
    }

    public void clearDependencies() {
        this.mDependencies = null;
    }

    public List<SharedLibraryInfo> getDependencies() {
        return this.mDependencies;
    }

    @Deprecated
    public int getVersion() {
        long j = this.mVersion;
        return j < 0 ? (int) j : (int) (j & 2147483647L);
    }

    public long getLongVersion() {
        return this.mVersion;
    }

    public boolean isBuiltin() {
        return this.mType == 0;
    }

    public boolean isDynamic() {
        return this.mType == 1;
    }

    public boolean isStatic() {
        return this.mType == 2;
    }

    public boolean isSdk() {
        return this.mType == 3;
    }

    public VersionedPackage getDeclaringPackage() {
        return this.mDeclaringPackage;
    }

    public List<VersionedPackage> getDependentPackages() {
        List<VersionedPackage> list = this.mDependentPackages;
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public List<VersionedPackage> getOptionalDependentPackages() {
        List<VersionedPackage> list = this.mOptionalDependentPackages;
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public List<String> getCertDigests() {
        List<String> list = this.mCertDigests;
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SharedLibraryInfo{name:");
        sb.append(this.mName);
        sb.append(", type:");
        sb.append(typeToString(this.mType));
        sb.append(", version:");
        sb.append(this.mVersion);
        sb.append(!getDependentPackages().isEmpty() ? " has dependents" : "");
        sb.append("}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mPath);
        parcel.writeString8(this.mPackageName);
        if (this.mCodePaths != null) {
            parcel.writeInt(1);
            List<String> list = this.mCodePaths;
            parcel.writeString8Array((String[]) list.toArray(new String[list.size()]));
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString8(this.mName);
        parcel.writeLong(this.mVersion);
        parcel.writeInt(this.mType);
        parcel.writeParcelable(this.mDeclaringPackage, i);
        parcel.writeList(this.mDependentPackages);
        parcel.writeTypedList(this.mDependencies);
        parcel.writeBoolean(this.mIsNative);
        parcel.writeParcelableList(this.mOptionalDependentPackages, i);
        parcel.writeStringList(this.mCertDigests);
    }

    private static String typeToString(int i) {
        if (i == 0) {
            return "builtin";
        }
        if (i == 1) {
            return "dynamic";
        }
        if (i == 2) {
            return "static";
        }
        if (i == 3) {
            return "sdk";
        }
        return "unknown";
    }
}
