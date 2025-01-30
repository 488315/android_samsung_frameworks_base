package com.android.server.pm.pkg.component;

import android.annotation.NonNull;
import android.annotation.StringRes;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ParsedAttributionImpl implements ParsedAttribution, Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.android.server.pm.pkg.component.ParsedAttributionImpl.1
        @Override // android.os.Parcelable.Creator
        public ParsedAttributionImpl[] newArray(int i) {
            return new ParsedAttributionImpl[i];
        }

        @Override // android.os.Parcelable.Creator
        public ParsedAttributionImpl createFromParcel(Parcel parcel) {
            return new ParsedAttributionImpl(parcel);
        }
    };
    public List inheritFrom;
    public int label;
    public String tag;

    public ParsedAttributionImpl() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ParsedAttributionImpl(String str, int i, List list) {
        this.tag = str;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, str);
        this.label = i;
        AnnotationValidations.validate(StringRes.class, (Annotation) null, i);
        this.inheritFrom = list;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, list);
    }

    @Override // com.android.server.pm.pkg.component.ParsedAttribution
    public String getTag() {
        return this.tag;
    }

    @Override // com.android.server.pm.pkg.component.ParsedAttribution
    public int getLabel() {
        return this.label;
    }

    @Override // com.android.server.pm.pkg.component.ParsedAttribution
    public List getInheritFrom() {
        return this.inheritFrom;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tag);
        parcel.writeInt(this.label);
        parcel.writeStringList(this.inheritFrom);
    }

    public ParsedAttributionImpl(Parcel parcel) {
        String readString = parcel.readString();
        int readInt = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        parcel.readStringList(arrayList);
        this.tag = readString;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, readString);
        this.label = readInt;
        AnnotationValidations.validate(StringRes.class, (Annotation) null, readInt);
        this.inheritFrom = arrayList;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, arrayList);
    }
}
