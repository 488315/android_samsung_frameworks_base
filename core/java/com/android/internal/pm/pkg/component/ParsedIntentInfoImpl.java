package com.android.internal.pm.pkg.component;

import android.annotation.NonNull;
import android.content.IntentFilter;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

public class ParsedIntentInfoImpl implements ParsedIntentInfo, Parcelable {
    public static final Parcelable.Creator<ParsedIntentInfoImpl> CREATOR =
            new Parcelable.Creator<
                    ParsedIntentInfoImpl>() { // from class:
                                              // com.android.internal.pm.pkg.component.ParsedIntentInfoImpl.1
                @Override // android.os.Parcelable.Creator
                public ParsedIntentInfoImpl[] newArray(int size) {
                    return new ParsedIntentInfoImpl[size];
                }

                @Override // android.os.Parcelable.Creator
                public ParsedIntentInfoImpl createFromParcel(Parcel in) {
                    return new ParsedIntentInfoImpl(in);
                }
            };
    private boolean mHasDefault;
    private int mIcon;
    private IntentFilter mIntentFilter;
    private int mLabelRes;
    private CharSequence mNonLocalizedLabel;

    public ParsedIntentInfoImpl() {
        this.mIntentFilter = new IntentFilter();
    }

    @Override // com.android.internal.pm.pkg.component.ParsedIntentInfo
    public boolean isHasDefault() {
        return this.mHasDefault;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedIntentInfo
    public int getLabelRes() {
        return this.mLabelRes;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedIntentInfo
    public CharSequence getNonLocalizedLabel() {
        return this.mNonLocalizedLabel;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedIntentInfo
    public int getIcon() {
        return this.mIcon;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedIntentInfo
    public IntentFilter getIntentFilter() {
        return this.mIntentFilter;
    }

    public ParsedIntentInfoImpl setHasDefault(boolean value) {
        this.mHasDefault = value;
        return this;
    }

    public ParsedIntentInfoImpl setLabelRes(int value) {
        this.mLabelRes = value;
        return this;
    }

    public ParsedIntentInfoImpl setNonLocalizedLabel(CharSequence value) {
        this.mNonLocalizedLabel = value;
        return this;
    }

    public ParsedIntentInfoImpl setIcon(int value) {
        this.mIcon = value;
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.mHasDefault ? (byte) (0 | 1) : (byte) 0;
        if (this.mNonLocalizedLabel != null) {
            flg = (byte) (flg | 4);
        }
        dest.writeByte(flg);
        dest.writeInt(this.mLabelRes);
        if (this.mNonLocalizedLabel != null) {
            dest.writeCharSequence(this.mNonLocalizedLabel);
        }
        dest.writeInt(this.mIcon);
        dest.writeTypedObject(this.mIntentFilter, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ParsedIntentInfoImpl(Parcel in) {
        this.mIntentFilter = new IntentFilter();
        byte flg = in.readByte();
        boolean hasDefault = (flg & 1) != 0;
        int labelRes = in.readInt();
        CharSequence nonLocalizedLabel = (flg & 4) == 0 ? null : in.readCharSequence();
        int icon = in.readInt();
        IntentFilter intentFilter = (IntentFilter) in.readTypedObject(IntentFilter.CREATOR);
        this.mHasDefault = hasDefault;
        this.mLabelRes = labelRes;
        this.mNonLocalizedLabel = nonLocalizedLabel;
        this.mIcon = icon;
        this.mIntentFilter = intentFilter;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mIntentFilter);
    }

    @Deprecated
    private void __metadata() {}
}
