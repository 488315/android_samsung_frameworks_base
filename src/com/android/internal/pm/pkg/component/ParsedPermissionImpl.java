package com.android.internal.pm.pkg.component;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArraySet;
import com.android.internal.util.Parcelling;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes5.dex */
public class ParsedPermissionImpl extends ParsedComponentImpl implements ParsedPermission, Parcelable {
    private String backgroundPermission;
    private String group;
    private Set<String> knownCerts;
    private ParsedPermissionGroup parsedPermissionGroup;
    private int protectionLevel;
    private int requestRes;
    private boolean tree;
    private static final Parcelling.BuiltIn.ForStringSet sForStringSet = (Parcelling.BuiltIn.ForStringSet) Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForStringSet.class);
    public static final Parcelable.Creator<ParsedPermissionImpl> CREATOR = new Parcelable.Creator<ParsedPermissionImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedPermissionImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParsedPermissionImpl createFromParcel(Parcel parcel) {
            return new ParsedPermissionImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParsedPermissionImpl[] newArray(int i) {
            return new ParsedPermissionImpl[i];
        }
    };

    @Deprecated
    private void __metadata() {
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ParsedPermissionImpl() {
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermission
    public ParsedPermissionGroup getParsedPermissionGroup() {
        return this.parsedPermissionGroup;
    }

    public ParsedPermissionImpl setGroup(String str) {
        this.group = TextUtils.safeIntern(str);
        return this;
    }

    protected void setKnownCert(String str) {
        this.knownCerts = Set.of(str.toUpperCase(Locale.US));
    }

    protected void setKnownCerts(String[] strArr) {
        this.knownCerts = new ArraySet();
        for (String str : strArr) {
            this.knownCerts.add(str.toUpperCase(Locale.US));
        }
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermission
    public Set<String> getKnownCerts() {
        Set<String> set = this.knownCerts;
        return set == null ? Collections.EMPTY_SET : set;
    }

    public String toString() {
        return "Permission{" + Integer.toHexString(System.identityHashCode(this)) + " " + getName() + "}";
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.backgroundPermission);
        parcel.writeString(this.group);
        parcel.writeInt(this.requestRes);
        parcel.writeInt(this.protectionLevel);
        parcel.writeBoolean(this.tree);
        parcel.writeParcelable((ParsedPermissionGroupImpl) this.parsedPermissionGroup, i);
        sForStringSet.parcel(this.knownCerts, parcel, i);
    }

    protected ParsedPermissionImpl(Parcel parcel) {
        super(parcel);
        this.backgroundPermission = parcel.readString();
        this.group = TextUtils.safeIntern(parcel.readString());
        this.requestRes = parcel.readInt();
        this.protectionLevel = parcel.readInt();
        this.tree = parcel.readBoolean();
        this.parsedPermissionGroup = (ParsedPermissionGroup) parcel.readParcelable(ParsedPermissionGroupImpl.class.getClassLoader(), ParsedPermissionGroupImpl.class);
        this.knownCerts = sForStringSet.unparcel(parcel);
    }

    public ParsedPermissionImpl(String str, String str2, int i, int i2, boolean z, ParsedPermissionGroup parsedPermissionGroup, Set<String> set) {
        this.backgroundPermission = str;
        this.group = str2;
        this.requestRes = i;
        this.protectionLevel = i2;
        this.tree = z;
        this.parsedPermissionGroup = parsedPermissionGroup;
        this.knownCerts = set;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermission
    public String getBackgroundPermission() {
        return this.backgroundPermission;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermission
    public String getGroup() {
        return this.group;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermission
    public int getRequestRes() {
        return this.requestRes;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermission
    public int getProtectionLevel() {
        return this.protectionLevel;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermission
    public boolean isTree() {
        return this.tree;
    }

    public ParsedPermissionImpl setBackgroundPermission(String str) {
        this.backgroundPermission = str;
        return this;
    }

    public ParsedPermissionImpl setRequestRes(int i) {
        this.requestRes = i;
        return this;
    }

    public ParsedPermissionImpl setProtectionLevel(int i) {
        this.protectionLevel = i;
        return this;
    }

    public ParsedPermissionImpl setTree(boolean z) {
        this.tree = z;
        return this;
    }

    public ParsedPermissionImpl setParsedPermissionGroup(ParsedPermissionGroup parsedPermissionGroup) {
        this.parsedPermissionGroup = parsedPermissionGroup;
        return this;
    }

    public ParsedPermissionImpl setKnownCerts(Set<String> set) {
        this.knownCerts = set;
        return this;
    }
}
