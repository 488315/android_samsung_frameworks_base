package com.android.server.pm.pkg.component;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Parcelling;
import com.android.server.pm.pkg.component.ParsedUsesPermission;
import java.lang.annotation.Annotation;

/* loaded from: classes3.dex */
public class ParsedUsesPermissionImpl implements ParsedUsesPermission, Parcelable {
    public static final Parcelable.Creator CREATOR;
    public static Parcelling sParcellingForName;
    public String name;
    public int usesPermissionFlags;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ParsedUsesPermissionImpl(String str, int i) {
        this.name = str;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, str);
        this.usesPermissionFlags = i;
        AnnotationValidations.validate(ParsedUsesPermission.UsesPermissionFlags.class, (Annotation) null, i);
    }

    @Override // com.android.server.pm.pkg.component.ParsedUsesPermission
    public String getName() {
        return this.name;
    }

    @Override // com.android.server.pm.pkg.component.ParsedUsesPermission
    public int getUsesPermissionFlags() {
        return this.usesPermissionFlags;
    }

    static {
        Parcelling parcelling = Parcelling.Cache.get(Parcelling.BuiltIn.ForInternedString.class);
        sParcellingForName = parcelling;
        if (parcelling == null) {
            sParcellingForName = Parcelling.Cache.put(new Parcelling.BuiltIn.ForInternedString());
        }
        CREATOR = new Parcelable.Creator() { // from class: com.android.server.pm.pkg.component.ParsedUsesPermissionImpl.1
            @Override // android.os.Parcelable.Creator
            public ParsedUsesPermissionImpl[] newArray(int i) {
                return new ParsedUsesPermissionImpl[i];
            }

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

    public ParsedUsesPermissionImpl(Parcel parcel) {
        String str = (String) sParcellingForName.unparcel(parcel);
        int readInt = parcel.readInt();
        this.name = str;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, str);
        this.usesPermissionFlags = readInt;
        AnnotationValidations.validate(ParsedUsesPermission.UsesPermissionFlags.class, (Annotation) null, readInt);
    }
}
