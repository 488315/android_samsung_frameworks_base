package com.android.internal.pm.pkg.component;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Parcelling;

import java.lang.annotation.Annotation;

public class ParsedUsesPermissionImpl implements ParsedUsesPermission, Parcelable {
    public static final Parcelable.Creator<ParsedUsesPermissionImpl> CREATOR;
    static Parcelling<String> sParcellingForName;
    private String name;
    private int usesPermissionFlags;

    public ParsedUsesPermissionImpl(String name, int usesPermissionFlags) {
        this.name = name;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) name);
        this.usesPermissionFlags = usesPermissionFlags;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) ParsedUsesPermission.UsesPermissionFlags.class,
                (Annotation) null,
                usesPermissionFlags);
    }

    @Override // com.android.internal.pm.pkg.component.ParsedUsesPermission
    public String getName() {
        return this.name;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedUsesPermission
    public int getUsesPermissionFlags() {
        return this.usesPermissionFlags;
    }

    public ParsedUsesPermissionImpl setName(String value) {
        this.name = value;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.name);
        return this;
    }

    public ParsedUsesPermissionImpl setUsesPermissionFlags(int value) {
        this.usesPermissionFlags = value;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) ParsedUsesPermission.UsesPermissionFlags.class,
                (Annotation) null,
                this.usesPermissionFlags);
        return this;
    }

    static {
        sParcellingForName = Parcelling.Cache.get(Parcelling.BuiltIn.ForInternedString.class);
        if (sParcellingForName == null) {
            sParcellingForName = Parcelling.Cache.put(new Parcelling.BuiltIn.ForInternedString());
        }
        CREATOR =
                new Parcelable.Creator<
                        ParsedUsesPermissionImpl>() { // from class:
                                                      // com.android.internal.pm.pkg.component.ParsedUsesPermissionImpl.1
                    @Override // android.os.Parcelable.Creator
                    public ParsedUsesPermissionImpl[] newArray(int size) {
                        return new ParsedUsesPermissionImpl[size];
                    }

                    @Override // android.os.Parcelable.Creator
                    public ParsedUsesPermissionImpl createFromParcel(Parcel in) {
                        return new ParsedUsesPermissionImpl(in);
                    }
                };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        sParcellingForName.parcel(this.name, dest, flags);
        dest.writeInt(this.usesPermissionFlags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ParsedUsesPermissionImpl(Parcel in) {
        String _name = sParcellingForName.unparcel(in);
        int _usesPermissionFlags = in.readInt();
        this.name = _name;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.name);
        this.usesPermissionFlags = _usesPermissionFlags;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) ParsedUsesPermission.UsesPermissionFlags.class,
                (Annotation) null,
                this.usesPermissionFlags);
    }

    @Deprecated
    private void __metadata() {}
}
