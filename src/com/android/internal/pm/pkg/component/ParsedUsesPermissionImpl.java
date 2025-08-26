package com.android.internal.pm.pkg.component;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.pm.pkg.component.ParsedUsesPermission;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Parcelling;
import java.lang.annotation.Annotation;

/* loaded from: classes5.dex */
public class ParsedUsesPermissionImpl implements ParsedUsesPermission, Parcelable {
    public static final Parcelable.Creator<ParsedUsesPermissionImpl> CREATOR;
    static Parcelling<String> sParcellingForName;
    private String name;
    private int usesPermissionFlags;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ParsedUsesPermissionImpl(String str, int i) {
        this.name = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.usesPermissionFlags = i;
        AnnotationValidations.validate((Class<? extends Annotation>) ParsedUsesPermission.UsesPermissionFlags.class, (Annotation) null, i);
    }

    @Override // com.android.internal.pm.pkg.component.ParsedUsesPermission
    public String getName() {
        return this.name;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedUsesPermission
    public int getUsesPermissionFlags() {
        return this.usesPermissionFlags;
    }

    public ParsedUsesPermissionImpl setName(String str) {
        this.name = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        return this;
    }

    public ParsedUsesPermissionImpl setUsesPermissionFlags(int i) {
        this.usesPermissionFlags = i;
        AnnotationValidations.validate((Class<? extends Annotation>) ParsedUsesPermission.UsesPermissionFlags.class, (Annotation) null, i);
        return this;
    }

    static {
        Parcelling<String> parcelling = Parcelling.Cache.get(Parcelling.BuiltIn.ForInternedString.class);
        sParcellingForName = parcelling;
        if (parcelling == null) {
            sParcellingForName = Parcelling.Cache.put(new Parcelling.BuiltIn.ForInternedString());
        }
        CREATOR = new Parcelable.Creator<ParsedUsesPermissionImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedUsesPermissionImpl.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ParsedUsesPermissionImpl[] newArray(int i) {
                return new ParsedUsesPermissionImpl[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ParsedUsesPermissionImpl createFromParcel(Parcel parcel) {
                return new ParsedUsesPermissionImpl(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        sParcellingForName.parcel(this.name, parcel, i);
        parcel.writeInt(this.usesPermissionFlags);
    }

    protected ParsedUsesPermissionImpl(Parcel parcel) {
        String strUnparcel = sParcellingForName.unparcel(parcel);
        int i = parcel.readInt();
        this.name = strUnparcel;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) strUnparcel);
        this.usesPermissionFlags = i;
        AnnotationValidations.validate((Class<? extends Annotation>) ParsedUsesPermission.UsesPermissionFlags.class, (Annotation) null, i);
    }
}
