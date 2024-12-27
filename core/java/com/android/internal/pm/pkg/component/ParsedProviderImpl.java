package com.android.internal.pm.pkg.component;

import android.annotation.NonNull;
import android.content.ComponentName;
import android.content.pm.PathPermission;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PatternMatcher;
import android.text.TextUtils;

import com.android.internal.pm.parsing.pkg.PackageImpl;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParsedProviderImpl extends ParsedMainComponentImpl
        implements ParsedProvider, Parcelable {
    public static final Parcelable.Creator<ParsedProviderImpl> CREATOR =
            new Parcelable.Creator<
                    ParsedProviderImpl>() { // from class:
                                            // com.android.internal.pm.pkg.component.ParsedProviderImpl.1
                @Override // android.os.Parcelable.Creator
                public ParsedProviderImpl createFromParcel(Parcel source) {
                    return new ParsedProviderImpl(source);
                }

                @Override // android.os.Parcelable.Creator
                public ParsedProviderImpl[] newArray(int size) {
                    return new ParsedProviderImpl[size];
                }
            };
    private String authority;
    private boolean forceUriPermissions;
    private boolean grantUriPermissions;
    private int initOrder;
    private boolean multiProcess;
    private List<PathPermission> pathPermissions;
    private String readPermission;
    private boolean syncable;
    private List<PatternMatcher> uriPermissionPatterns;
    private String writePermission;

    public ParsedProviderImpl(ParsedProvider other) {
        super(other);
        this.uriPermissionPatterns = Collections.emptyList();
        this.pathPermissions = Collections.emptyList();
        this.authority = other.getAuthority();
        this.syncable = other.isSyncable();
        this.readPermission = other.getReadPermission();
        this.writePermission = other.getWritePermission();
        this.grantUriPermissions = other.isGrantUriPermissions();
        this.forceUriPermissions = other.isForceUriPermissions();
        this.multiProcess = other.isMultiProcess();
        this.initOrder = other.getInitOrder();
        this.uriPermissionPatterns = new ArrayList(other.getUriPermissionPatterns());
        this.pathPermissions = new ArrayList(other.getPathPermissions());
    }

    public ParsedProviderImpl setReadPermission(String readPermission) {
        this.readPermission = TextUtils.isEmpty(readPermission) ? null : readPermission.intern();
        return this;
    }

    public ParsedProviderImpl setWritePermission(String writePermission) {
        this.writePermission = TextUtils.isEmpty(writePermission) ? null : writePermission.intern();
        return this;
    }

    public ParsedProviderImpl addUriPermissionPattern(PatternMatcher value) {
        this.uriPermissionPatterns = CollectionUtils.add(this.uriPermissionPatterns, value);
        return this;
    }

    public ParsedProviderImpl addPathPermission(PathPermission value) {
        this.pathPermissions = CollectionUtils.add(this.pathPermissions, value);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("Provider{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(' ');
        ComponentName.appendShortString(sb, getPackageName(), getName());
        sb.append('}');
        return sb.toString();
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponentImpl,
              // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponentImpl,
              // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.authority);
        dest.writeBoolean(this.syncable);
        PackageImpl.sForInternedString.parcel(this.readPermission, dest, flags);
        PackageImpl.sForInternedString.parcel(this.writePermission, dest, flags);
        dest.writeBoolean(this.grantUriPermissions);
        dest.writeBoolean(this.forceUriPermissions);
        dest.writeBoolean(this.multiProcess);
        dest.writeInt(this.initOrder);
        dest.writeTypedList(this.uriPermissionPatterns, flags);
        dest.writeTypedList(this.pathPermissions, flags);
    }

    public ParsedProviderImpl() {
        this.uriPermissionPatterns = Collections.emptyList();
        this.pathPermissions = Collections.emptyList();
    }

    protected ParsedProviderImpl(Parcel in) {
        super(in);
        this.uriPermissionPatterns = Collections.emptyList();
        this.pathPermissions = Collections.emptyList();
        this.authority = in.readString();
        this.syncable = in.readBoolean();
        this.readPermission = PackageImpl.sForInternedString.unparcel(in);
        this.writePermission = PackageImpl.sForInternedString.unparcel(in);
        this.grantUriPermissions = in.readBoolean();
        this.forceUriPermissions = in.readBoolean();
        this.multiProcess = in.readBoolean();
        this.initOrder = in.readInt();
        this.uriPermissionPatterns = in.createTypedArrayList(PatternMatcher.CREATOR);
        this.pathPermissions = in.createTypedArrayList(PathPermission.CREATOR);
    }

    public ParsedProviderImpl(
            String authority,
            boolean syncable,
            String readPermission,
            String writePermission,
            boolean grantUriPermissions,
            boolean forceUriPermissions,
            boolean multiProcess,
            int initOrder,
            List<PatternMatcher> uriPermissionPatterns,
            List<PathPermission> pathPermissions) {
        this.uriPermissionPatterns = Collections.emptyList();
        this.pathPermissions = Collections.emptyList();
        this.authority = authority;
        this.syncable = syncable;
        this.readPermission = readPermission;
        this.writePermission = writePermission;
        this.grantUriPermissions = grantUriPermissions;
        this.forceUriPermissions = forceUriPermissions;
        this.multiProcess = multiProcess;
        this.initOrder = initOrder;
        this.uriPermissionPatterns = uriPermissionPatterns;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) uriPermissionPatterns);
        this.pathPermissions = pathPermissions;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) pathPermissions);
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public String getAuthority() {
        return this.authority;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public boolean isSyncable() {
        return this.syncable;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public String getReadPermission() {
        return this.readPermission;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public String getWritePermission() {
        return this.writePermission;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public boolean isGrantUriPermissions() {
        return this.grantUriPermissions;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public boolean isForceUriPermissions() {
        return this.forceUriPermissions;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public boolean isMultiProcess() {
        return this.multiProcess;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public int getInitOrder() {
        return this.initOrder;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public List<PatternMatcher> getUriPermissionPatterns() {
        return this.uriPermissionPatterns;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public List<PathPermission> getPathPermissions() {
        return this.pathPermissions;
    }

    public ParsedProviderImpl setAuthority(String value) {
        this.authority = value;
        return this;
    }

    public ParsedProviderImpl setSyncable(boolean value) {
        this.syncable = value;
        return this;
    }

    public ParsedProviderImpl setGrantUriPermissions(boolean value) {
        this.grantUriPermissions = value;
        return this;
    }

    public ParsedProviderImpl setForceUriPermissions(boolean value) {
        this.forceUriPermissions = value;
        return this;
    }

    public ParsedProviderImpl setMultiProcess(boolean value) {
        this.multiProcess = value;
        return this;
    }

    public ParsedProviderImpl setInitOrder(int value) {
        this.initOrder = value;
        return this;
    }

    @Deprecated
    private void __metadata() {}
}
