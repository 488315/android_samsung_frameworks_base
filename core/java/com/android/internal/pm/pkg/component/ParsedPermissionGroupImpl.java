package com.android.internal.pm.pkg.component;

import android.os.Parcel;
import android.os.Parcelable;

public class ParsedPermissionGroupImpl extends ParsedComponentImpl
        implements ParsedPermissionGroup, Parcelable {
    public static final Parcelable.Creator<ParsedPermissionGroupImpl> CREATOR =
            new Parcelable.Creator<
                    ParsedPermissionGroupImpl>() { // from class:
                                                   // com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl.1
                @Override // android.os.Parcelable.Creator
                public ParsedPermissionGroupImpl[] newArray(int size) {
                    return new ParsedPermissionGroupImpl[size];
                }

                @Override // android.os.Parcelable.Creator
                public ParsedPermissionGroupImpl createFromParcel(Parcel in) {
                    return new ParsedPermissionGroupImpl(in);
                }
            };
    private int backgroundRequestDetailRes;
    private int backgroundRequestRes;
    private int priority;
    private int requestDetailRes;
    private int requestRes;

    public String toString() {
        return "PermissionGroup{"
                + Integer.toHexString(System.identityHashCode(this))
                + " "
                + getName()
                + "}";
    }

    public ParsedPermissionGroupImpl() {}

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.requestDetailRes);
        dest.writeInt(this.backgroundRequestRes);
        dest.writeInt(this.backgroundRequestDetailRes);
        dest.writeInt(this.requestRes);
        dest.writeInt(this.priority);
    }

    protected ParsedPermissionGroupImpl(Parcel in) {
        super(in);
        this.requestDetailRes = in.readInt();
        this.backgroundRequestRes = in.readInt();
        this.backgroundRequestDetailRes = in.readInt();
        this.requestRes = in.readInt();
        this.priority = in.readInt();
    }

    public ParsedPermissionGroupImpl(
            int requestDetailRes,
            int backgroundRequestRes,
            int backgroundRequestDetailRes,
            int requestRes,
            int priority) {
        this.requestDetailRes = requestDetailRes;
        this.backgroundRequestRes = backgroundRequestRes;
        this.backgroundRequestDetailRes = backgroundRequestDetailRes;
        this.requestRes = requestRes;
        this.priority = priority;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermissionGroup
    public int getRequestDetailRes() {
        return this.requestDetailRes;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermissionGroup
    public int getBackgroundRequestRes() {
        return this.backgroundRequestRes;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermissionGroup
    public int getBackgroundRequestDetailRes() {
        return this.backgroundRequestDetailRes;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermissionGroup
    public int getRequestRes() {
        return this.requestRes;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermissionGroup
    public int getPriority() {
        return this.priority;
    }

    public ParsedPermissionGroupImpl setRequestDetailRes(int value) {
        this.requestDetailRes = value;
        return this;
    }

    public ParsedPermissionGroupImpl setBackgroundRequestRes(int value) {
        this.backgroundRequestRes = value;
        return this;
    }

    public ParsedPermissionGroupImpl setBackgroundRequestDetailRes(int value) {
        this.backgroundRequestDetailRes = value;
        return this;
    }

    public ParsedPermissionGroupImpl setRequestRes(int value) {
        this.requestRes = value;
        return this;
    }

    public ParsedPermissionGroupImpl setPriority(int value) {
        this.priority = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    private void __metadata() {}
}
