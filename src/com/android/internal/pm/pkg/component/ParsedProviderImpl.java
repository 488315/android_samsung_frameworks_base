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

/* loaded from: classes5.dex */
public class ParsedProviderImpl extends ParsedMainComponentImpl implements ParsedProvider, Parcelable {
    public static final Parcelable.Creator<ParsedProviderImpl> CREATOR = new Parcelable.Creator<ParsedProviderImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedProviderImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParsedProviderImpl createFromParcel(Parcel parcel) {
            return new ParsedProviderImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParsedProviderImpl[] newArray(int i) {
            return new ParsedProviderImpl[i];
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

    @Deprecated
    private void __metadata() {
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponentImpl, com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ParsedProviderImpl(ParsedProvider parsedProvider) {
        super(parsedProvider);
        this.uriPermissionPatterns = Collections.EMPTY_LIST;
        this.pathPermissions = Collections.EMPTY_LIST;
        this.authority = parsedProvider.getAuthority();
        this.syncable = parsedProvider.isSyncable();
        this.readPermission = parsedProvider.getReadPermission();
        this.writePermission = parsedProvider.getWritePermission();
        this.grantUriPermissions = parsedProvider.isGrantUriPermissions();
        this.forceUriPermissions = parsedProvider.isForceUriPermissions();
        this.multiProcess = parsedProvider.isMultiProcess();
        this.initOrder = parsedProvider.getInitOrder();
        this.uriPermissionPatterns = new ArrayList(parsedProvider.getUriPermissionPatterns());
        this.pathPermissions = new ArrayList(parsedProvider.getPathPermissions());
    }

    public ParsedProviderImpl setReadPermission(String str) {
        this.readPermission = TextUtils.isEmpty(str) ? null : str.intern();
        return this;
    }

    public ParsedProviderImpl setWritePermission(String str) {
        this.writePermission = TextUtils.isEmpty(str) ? null : str.intern();
        return this;
    }

    public ParsedProviderImpl addUriPermissionPattern(PatternMatcher patternMatcher) {
        this.uriPermissionPatterns = CollectionUtils.add(this.uriPermissionPatterns, patternMatcher);
        return this;
    }

    public ParsedProviderImpl addPathPermission(PathPermission pathPermission) {
        this.pathPermissions = CollectionUtils.add(this.pathPermissions, pathPermission);
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

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponentImpl, com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.authority);
        parcel.writeBoolean(this.syncable);
        PackageImpl.sForInternedString.parcel(this.readPermission, parcel, i);
        PackageImpl.sForInternedString.parcel(this.writePermission, parcel, i);
        parcel.writeBoolean(this.grantUriPermissions);
        parcel.writeBoolean(this.forceUriPermissions);
        parcel.writeBoolean(this.multiProcess);
        parcel.writeInt(this.initOrder);
        parcel.writeTypedList(this.uriPermissionPatterns, i);
        parcel.writeTypedList(this.pathPermissions, i);
    }

    public ParsedProviderImpl() {
        this.uriPermissionPatterns = Collections.EMPTY_LIST;
        this.pathPermissions = Collections.EMPTY_LIST;
    }

    protected ParsedProviderImpl(Parcel parcel) {
        super(parcel);
        this.uriPermissionPatterns = Collections.EMPTY_LIST;
        this.pathPermissions = Collections.EMPTY_LIST;
        this.authority = parcel.readString();
        this.syncable = parcel.readBoolean();
        this.readPermission = PackageImpl.sForInternedString.unparcel(parcel);
        this.writePermission = PackageImpl.sForInternedString.unparcel(parcel);
        this.grantUriPermissions = parcel.readBoolean();
        this.forceUriPermissions = parcel.readBoolean();
        this.multiProcess = parcel.readBoolean();
        this.initOrder = parcel.readInt();
        this.uriPermissionPatterns = parcel.createTypedArrayList(PatternMatcher.CREATOR);
        this.pathPermissions = parcel.createTypedArrayList(PathPermission.CREATOR);
    }

    public ParsedProviderImpl(String str, boolean z, String str2, String str3, boolean z2, boolean z3, boolean z4, int i, List<PatternMatcher> list, List<PathPermission> list2) {
        this.uriPermissionPatterns = Collections.EMPTY_LIST;
        this.pathPermissions = Collections.EMPTY_LIST;
        this.authority = str;
        this.syncable = z;
        this.readPermission = str2;
        this.writePermission = str3;
        this.grantUriPermissions = z2;
        this.forceUriPermissions = z3;
        this.multiProcess = z4;
        this.initOrder = i;
        this.uriPermissionPatterns = list;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list);
        this.pathPermissions = list2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list2);
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

    public ParsedProviderImpl setAuthority(String str) {
        this.authority = str;
        return this;
    }

    public ParsedProviderImpl setSyncable(boolean z) {
        this.syncable = z;
        return this;
    }

    public ParsedProviderImpl setGrantUriPermissions(boolean z) {
        this.grantUriPermissions = z;
        return this;
    }

    public ParsedProviderImpl setForceUriPermissions(boolean z) {
        this.forceUriPermissions = z;
        return this;
    }

    public ParsedProviderImpl setMultiProcess(boolean z) {
        this.multiProcess = z;
        return this;
    }

    public ParsedProviderImpl setInitOrder(int i) {
        this.initOrder = i;
        return this;
    }
}
