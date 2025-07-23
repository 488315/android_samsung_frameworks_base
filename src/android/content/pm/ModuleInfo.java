package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ModuleInfo implements Parcelable {
    public static final Parcelable.Creator<ModuleInfo> CREATOR = new Parcelable.Creator<ModuleInfo>() { // from class: android.content.pm.ModuleInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ModuleInfo createFromParcel(Parcel parcel) {
            return new ModuleInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ModuleInfo[] newArray(int i) {
            return new ModuleInfo[i];
        }
    };
    private String mApexModuleName;
    private List<String> mApkInApexPackageNames;
    private boolean mHidden;
    private CharSequence mName;
    private String mPackageName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ModuleInfo() {
    }

    public ModuleInfo(ModuleInfo moduleInfo) {
        this.mName = moduleInfo.mName;
        this.mPackageName = moduleInfo.mPackageName;
        this.mHidden = moduleInfo.mHidden;
        this.mApexModuleName = moduleInfo.mApexModuleName;
        List<String> list = moduleInfo.mApkInApexPackageNames;
        if (list != null) {
            this.mApkInApexPackageNames = List.copyOf(list);
        }
    }

    public ModuleInfo setName(CharSequence charSequence) {
        this.mName = charSequence;
        return this;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public ModuleInfo setPackageName(String str) {
        this.mPackageName = str;
        return this;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public ModuleInfo setHidden(boolean z) {
        this.mHidden = z;
        return this;
    }

    public boolean isHidden() {
        return this.mHidden;
    }

    public ModuleInfo setApexModuleName(String str) {
        this.mApexModuleName = str;
        return this;
    }

    public String getApexModuleName() {
        return this.mApexModuleName;
    }

    public ModuleInfo setApkInApexPackageNames(Collection<String> collection) {
        Objects.requireNonNull(collection);
        this.mApkInApexPackageNames = List.copyOf(collection);
        return this;
    }

    public Collection<String> getApkInApexPackageNames() {
        List<String> list = this.mApkInApexPackageNames;
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public String toString() {
        return "ModuleInfo{" + Integer.toHexString(System.identityHashCode(this)) + " " + ((Object) this.mName) + "}";
    }

    public int hashCode() {
        return (((((((Objects.hashCode(this.mName) * 31) + Objects.hashCode(this.mPackageName)) * 31) + Objects.hashCode(this.mApexModuleName)) * 31) + Objects.hashCode(this.mApkInApexPackageNames)) * 31) + Boolean.hashCode(this.mHidden);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ModuleInfo)) {
            return false;
        }
        ModuleInfo moduleInfo = (ModuleInfo) obj;
        return Objects.equals(this.mName, moduleInfo.mName) && Objects.equals(this.mPackageName, moduleInfo.mPackageName) && Objects.equals(this.mApexModuleName, moduleInfo.mApexModuleName) && Objects.equals(this.mApkInApexPackageNames, moduleInfo.mApkInApexPackageNames) && this.mHidden == moduleInfo.mHidden;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharSequence(this.mName);
        parcel.writeString(this.mPackageName);
        parcel.writeBoolean(this.mHidden);
        parcel.writeString(this.mApexModuleName);
        parcel.writeStringList(this.mApkInApexPackageNames);
    }

    private ModuleInfo(Parcel parcel) {
        this.mName = parcel.readCharSequence();
        this.mPackageName = parcel.readString();
        this.mHidden = parcel.readBoolean();
        this.mApexModuleName = parcel.readString();
        this.mApkInApexPackageNames = parcel.createStringArrayList();
    }
}
