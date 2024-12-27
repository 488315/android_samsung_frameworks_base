package com.android.internal.pm.pkg.component;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.android.internal.pm.parsing.pkg.PackageImpl;

import libcore.util.EmptyArray;

public class ParsedMainComponentImpl extends ParsedComponentImpl
        implements ParsedMainComponent, Parcelable {
    public static final Parcelable.Creator<ParsedMainComponentImpl> CREATOR =
            new Parcelable.Creator<
                    ParsedMainComponentImpl>() { // from class:
                                                 // com.android.internal.pm.pkg.component.ParsedMainComponentImpl.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ParsedMainComponentImpl createFromParcel(Parcel source) {
                    return new ParsedMainComponentImpl(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ParsedMainComponentImpl[] newArray(int size) {
                    return new ParsedMainComponentImpl[size];
                }
            };
    private String[] attributionTags;
    private boolean directBootAware;
    private boolean enabled;
    private boolean exported;
    private int order;
    private String processName;
    private String splitName;

    public ParsedMainComponentImpl() {
        this.enabled = true;
    }

    public ParsedMainComponentImpl(ParsedMainComponent other) {
        super(other);
        this.enabled = true;
        this.processName = other.getProcessName();
        this.directBootAware = other.isDirectBootAware();
        this.enabled = other.isEnabled();
        this.exported = other.isExported();
        this.order = other.getOrder();
        this.splitName = other.getSplitName();
        this.attributionTags = other.getAttributionTags();
    }

    public ParsedMainComponentImpl setProcessName(String processName) {
        this.processName = TextUtils.safeIntern(processName);
        return this;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponent
    public String getClassName() {
        return getName();
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponent
    public String[] getAttributionTags() {
        return this.attributionTags == null ? EmptyArray.STRING : this.attributionTags;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        PackageImpl.sForInternedString.parcel(this.processName, dest, flags);
        dest.writeBoolean(this.directBootAware);
        dest.writeBoolean(this.enabled);
        dest.writeBoolean(this.exported);
        dest.writeInt(this.order);
        dest.writeString(this.splitName);
        dest.writeString8Array(this.attributionTags);
    }

    protected ParsedMainComponentImpl(Parcel in) {
        super(in);
        this.enabled = true;
        this.processName = PackageImpl.sForInternedString.unparcel(in);
        this.directBootAware = in.readBoolean();
        this.enabled = in.readBoolean();
        this.exported = in.readBoolean();
        this.order = in.readInt();
        this.splitName = in.readString();
        this.attributionTags = in.createString8Array();
    }

    public ParsedMainComponentImpl(
            String processName,
            boolean directBootAware,
            boolean enabled,
            boolean exported,
            int order,
            String splitName,
            String[] attributionTags) {
        this.enabled = true;
        this.processName = processName;
        this.directBootAware = directBootAware;
        this.enabled = enabled;
        this.exported = exported;
        this.order = order;
        this.splitName = splitName;
        this.attributionTags = attributionTags;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponent
    public String getProcessName() {
        return this.processName;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponent
    public boolean isDirectBootAware() {
        return this.directBootAware;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponent
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponent
    public boolean isExported() {
        return this.exported;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponent
    public int getOrder() {
        return this.order;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponent
    public String getSplitName() {
        return this.splitName;
    }

    public ParsedMainComponentImpl setDirectBootAware(boolean value) {
        this.directBootAware = value;
        return this;
    }

    public ParsedMainComponentImpl setEnabled(boolean value) {
        this.enabled = value;
        return this;
    }

    public ParsedMainComponentImpl setExported(boolean value) {
        this.exported = value;
        return this;
    }

    public ParsedMainComponentImpl setOrder(int value) {
        this.order = value;
        return this;
    }

    public ParsedMainComponentImpl setSplitName(String value) {
        this.splitName = value;
        return this;
    }

    public ParsedMainComponentImpl setAttributionTags(String... value) {
        this.attributionTags = value;
        return this;
    }

    @Deprecated
    private void __metadata() {}
}
