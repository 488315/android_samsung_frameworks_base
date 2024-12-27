package com.android.internal.pm.pkg.component;

import android.annotation.NonNull;
import android.annotation.StringRes;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class ParsedAttributionImpl implements ParsedAttribution, Parcelable {
    public static final Parcelable.Creator<ParsedAttributionImpl> CREATOR =
            new Parcelable.Creator<
                    ParsedAttributionImpl>() { // from class:
                                               // com.android.internal.pm.pkg.component.ParsedAttributionImpl.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ParsedAttributionImpl[] newArray(int size) {
                    return new ParsedAttributionImpl[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ParsedAttributionImpl createFromParcel(Parcel in) {
                    return new ParsedAttributionImpl(in);
                }
            };
    static final int MAX_NUM_ATTRIBUTIONS = 400;
    private List<String> inheritFrom;
    private int label;
    private String tag;

    public ParsedAttributionImpl() {}

    public ParsedAttributionImpl(String tag, int label, List<String> inheritFrom) {
        this.tag = tag;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) tag);
        this.label = label;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) StringRes.class, (Annotation) null, label);
        this.inheritFrom = inheritFrom;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) inheritFrom);
    }

    @Override // com.android.internal.pm.pkg.component.ParsedAttribution
    public String getTag() {
        return this.tag;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedAttribution
    public int getLabel() {
        return this.label;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedAttribution
    public List<String> getInheritFrom() {
        return this.inheritFrom;
    }

    public ParsedAttributionImpl setTag(String value) {
        this.tag = value;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.tag);
        return this;
    }

    public ParsedAttributionImpl setLabel(int value) {
        this.label = value;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) StringRes.class, (Annotation) null, this.label);
        return this;
    }

    public ParsedAttributionImpl setInheritFrom(List<String> value) {
        this.inheritFrom = value;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.inheritFrom);
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tag);
        dest.writeInt(this.label);
        dest.writeStringList(this.inheritFrom);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ParsedAttributionImpl(Parcel in) {
        String _tag = in.readString();
        int _label = in.readInt();
        List<String> _inheritFrom = new ArrayList<>();
        in.readStringList(_inheritFrom);
        this.tag = _tag;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.tag);
        this.label = _label;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) StringRes.class, (Annotation) null, this.label);
        this.inheritFrom = _inheritFrom;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.inheritFrom);
    }

    @Deprecated
    private void __metadata() {}
}
