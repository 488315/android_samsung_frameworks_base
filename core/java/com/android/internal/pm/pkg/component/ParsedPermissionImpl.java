package com.android.internal.pm.pkg.component;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArraySet;

import com.android.internal.util.Parcelling;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

public class ParsedPermissionImpl extends ParsedComponentImpl
        implements ParsedPermission, Parcelable {
    private String backgroundPermission;
    private String group;
    private Set<String> knownCerts;
    private ParsedPermissionGroup parsedPermissionGroup;
    private int protectionLevel;
    private int requestRes;
    private boolean tree;
    private static final Parcelling.BuiltIn.ForStringSet sForStringSet =
            (Parcelling.BuiltIn.ForStringSet)
                    Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForStringSet.class);
    public static final Parcelable.Creator<ParsedPermissionImpl> CREATOR =
            new Parcelable.Creator<
                    ParsedPermissionImpl>() { // from class:
                                              // com.android.internal.pm.pkg.component.ParsedPermissionImpl.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ParsedPermissionImpl createFromParcel(Parcel source) {
                    return new ParsedPermissionImpl(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ParsedPermissionImpl[] newArray(int size) {
                    return new ParsedPermissionImpl[size];
                }
            };

    public ParsedPermissionImpl() {}

    @Override // com.android.internal.pm.pkg.component.ParsedPermission
    public ParsedPermissionGroup getParsedPermissionGroup() {
        return this.parsedPermissionGroup;
    }

    public ParsedPermissionImpl setGroup(String group) {
        this.group = TextUtils.safeIntern(group);
        return this;
    }

    protected void setKnownCert(String knownCert) {
        this.knownCerts = Set.of(knownCert.toUpperCase(Locale.US));
    }

    protected void setKnownCerts(String[] knownCerts) {
        this.knownCerts = new ArraySet();
        for (String knownCert : knownCerts) {
            this.knownCerts.add(knownCert.toUpperCase(Locale.US));
        }
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermission
    public Set<String> getKnownCerts() {
        return this.knownCerts == null ? Collections.emptySet() : this.knownCerts;
    }

    public String toString() {
        return "Permission{"
                + Integer.toHexString(System.identityHashCode(this))
                + " "
                + getName()
                + "}";
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.backgroundPermission);
        dest.writeString(this.group);
        dest.writeInt(this.requestRes);
        dest.writeInt(this.protectionLevel);
        dest.writeBoolean(this.tree);
        dest.writeParcelable((ParsedPermissionGroupImpl) this.parsedPermissionGroup, flags);
        sForStringSet.parcel(this.knownCerts, dest, flags);
    }

    protected ParsedPermissionImpl(Parcel in) {
        super(in);
        this.backgroundPermission = in.readString();
        this.group = TextUtils.safeIntern(in.readString());
        this.requestRes = in.readInt();
        this.protectionLevel = in.readInt();
        this.tree = in.readBoolean();
        this.parsedPermissionGroup =
                (ParsedPermissionGroup)
                        in.readParcelable(
                                ParsedPermissionGroupImpl.class.getClassLoader(),
                                ParsedPermissionGroupImpl.class);
        this.knownCerts = sForStringSet.unparcel(in);
    }

    public ParsedPermissionImpl(
            String backgroundPermission,
            String group,
            int requestRes,
            int protectionLevel,
            boolean tree,
            ParsedPermissionGroup parsedPermissionGroup,
            Set<String> knownCerts) {
        this.backgroundPermission = backgroundPermission;
        this.group = group;
        this.requestRes = requestRes;
        this.protectionLevel = protectionLevel;
        this.tree = tree;
        this.parsedPermissionGroup = parsedPermissionGroup;
        this.knownCerts = knownCerts;
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

    public ParsedPermissionImpl setBackgroundPermission(String value) {
        this.backgroundPermission = value;
        return this;
    }

    public ParsedPermissionImpl setRequestRes(int value) {
        this.requestRes = value;
        return this;
    }

    public ParsedPermissionImpl setProtectionLevel(int value) {
        this.protectionLevel = value;
        return this;
    }

    public ParsedPermissionImpl setTree(boolean value) {
        this.tree = value;
        return this;
    }

    public ParsedPermissionImpl setParsedPermissionGroup(ParsedPermissionGroup value) {
        this.parsedPermissionGroup = value;
        return this;
    }

    public ParsedPermissionImpl setKnownCerts(Set<String> value) {
        this.knownCerts = value;
        return this;
    }

    @Deprecated
    private void __metadata() {}
}
