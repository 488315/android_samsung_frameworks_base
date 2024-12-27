package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ModuleInfo implements Parcelable {
    public static final Parcelable.Creator<ModuleInfo> CREATOR =
            new Parcelable.Creator<ModuleInfo>() { // from class: android.content.pm.ModuleInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ModuleInfo createFromParcel(Parcel source) {
                    return new ModuleInfo(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ModuleInfo[] newArray(int size) {
                    return new ModuleInfo[size];
                }
            };
    private String mApexModuleName;
    private List<String> mApkInApexPackageNames;
    private boolean mHidden;
    private CharSequence mName;
    private String mPackageName;

    public ModuleInfo() {}

    public ModuleInfo(ModuleInfo orig) {
        this.mName = orig.mName;
        this.mPackageName = orig.mPackageName;
        this.mHidden = orig.mHidden;
        this.mApexModuleName = orig.mApexModuleName;
        if (orig.mApkInApexPackageNames != null) {
            this.mApkInApexPackageNames = List.copyOf(orig.mApkInApexPackageNames);
        }
    }

    public ModuleInfo setName(CharSequence name) {
        this.mName = name;
        return this;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public ModuleInfo setPackageName(String packageName) {
        this.mPackageName = packageName;
        return this;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public ModuleInfo setHidden(boolean hidden) {
        this.mHidden = hidden;
        return this;
    }

    public boolean isHidden() {
        return this.mHidden;
    }

    public ModuleInfo setApexModuleName(String apexModuleName) {
        this.mApexModuleName = apexModuleName;
        return this;
    }

    public String getApexModuleName() {
        return this.mApexModuleName;
    }

    public ModuleInfo setApkInApexPackageNames(Collection<String> apkInApexPackageNames) {
        Objects.requireNonNull(apkInApexPackageNames);
        this.mApkInApexPackageNames = List.copyOf(apkInApexPackageNames);
        return this;
    }

    public Collection<String> getApkInApexPackageNames() {
        if (this.mApkInApexPackageNames == null) {
            return Collections.emptyList();
        }
        return this.mApkInApexPackageNames;
    }

    public String toString() {
        return "ModuleInfo{"
                + Integer.toHexString(System.identityHashCode(this))
                + " "
                + ((Object) this.mName)
                + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        int hashCode = (0 * 31) + Objects.hashCode(this.mName);
        return (((((((hashCode * 31) + Objects.hashCode(this.mPackageName)) * 31)
                                                + Objects.hashCode(this.mApexModuleName))
                                        * 31)
                                + Objects.hashCode(this.mApkInApexPackageNames))
                        * 31)
                + Boolean.hashCode(this.mHidden);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ModuleInfo)) {
            return false;
        }
        ModuleInfo other = (ModuleInfo) obj;
        return Objects.equals(this.mName, other.mName)
                && Objects.equals(this.mPackageName, other.mPackageName)
                && Objects.equals(this.mApexModuleName, other.mApexModuleName)
                && Objects.equals(this.mApkInApexPackageNames, other.mApkInApexPackageNames)
                && this.mHidden == other.mHidden;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int parcelableFlags) {
        dest.writeCharSequence(this.mName);
        dest.writeString(this.mPackageName);
        dest.writeBoolean(this.mHidden);
        dest.writeString(this.mApexModuleName);
        dest.writeStringList(this.mApkInApexPackageNames);
    }

    private ModuleInfo(Parcel source) {
        this.mName = source.readCharSequence();
        this.mPackageName = source.readString();
        this.mHidden = source.readBoolean();
        this.mApexModuleName = source.readString();
        this.mApkInApexPackageNames = source.createStringArrayList();
    }
}
