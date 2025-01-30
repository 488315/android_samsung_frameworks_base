package com.android.server.pm.pkg.component;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Parcelling;

/* loaded from: classes3.dex */
public class ParsedApexSystemServiceImpl implements ParsedApexSystemService, Parcelable {
    public static final Parcelable.Creator CREATOR;
    public static Parcelling sParcellingForJarPath;
    public static Parcelling sParcellingForMaxSdkVersion;
    public static Parcelling sParcellingForMinSdkVersion;
    public static Parcelling sParcellingForName;
    public int initOrder;
    public String jarPath;
    public String maxSdkVersion;
    public String minSdkVersion;
    public String name;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ParsedApexSystemServiceImpl() {
    }

    @Override // com.android.server.pm.pkg.component.ParsedApexSystemService
    public String getName() {
        return this.name;
    }

    @Override // com.android.server.pm.pkg.component.ParsedApexSystemService
    public String getJarPath() {
        return this.jarPath;
    }

    @Override // com.android.server.pm.pkg.component.ParsedApexSystemService
    public String getMinSdkVersion() {
        return this.minSdkVersion;
    }

    @Override // com.android.server.pm.pkg.component.ParsedApexSystemService
    public String getMaxSdkVersion() {
        return this.maxSdkVersion;
    }

    @Override // com.android.server.pm.pkg.component.ParsedApexSystemService
    public int getInitOrder() {
        return this.initOrder;
    }

    public ParsedApexSystemServiceImpl setName(String str) {
        this.name = str;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, str);
        return this;
    }

    public ParsedApexSystemServiceImpl setJarPath(String str) {
        this.jarPath = str;
        return this;
    }

    public ParsedApexSystemServiceImpl setMinSdkVersion(String str) {
        this.minSdkVersion = str;
        return this;
    }

    public ParsedApexSystemServiceImpl setMaxSdkVersion(String str) {
        this.maxSdkVersion = str;
        return this;
    }

    public ParsedApexSystemServiceImpl setInitOrder(int i) {
        this.initOrder = i;
        return this;
    }

    static {
        Parcelling parcelling = Parcelling.Cache.get(Parcelling.BuiltIn.ForInternedString.class);
        sParcellingForName = parcelling;
        if (parcelling == null) {
            sParcellingForName = Parcelling.Cache.put(new Parcelling.BuiltIn.ForInternedString());
        }
        Parcelling parcelling2 = Parcelling.Cache.get(Parcelling.BuiltIn.ForInternedString.class);
        sParcellingForJarPath = parcelling2;
        if (parcelling2 == null) {
            sParcellingForJarPath = Parcelling.Cache.put(new Parcelling.BuiltIn.ForInternedString());
        }
        Parcelling parcelling3 = Parcelling.Cache.get(Parcelling.BuiltIn.ForInternedString.class);
        sParcellingForMinSdkVersion = parcelling3;
        if (parcelling3 == null) {
            sParcellingForMinSdkVersion = Parcelling.Cache.put(new Parcelling.BuiltIn.ForInternedString());
        }
        Parcelling parcelling4 = Parcelling.Cache.get(Parcelling.BuiltIn.ForInternedString.class);
        sParcellingForMaxSdkVersion = parcelling4;
        if (parcelling4 == null) {
            sParcellingForMaxSdkVersion = Parcelling.Cache.put(new Parcelling.BuiltIn.ForInternedString());
        }
        CREATOR = new Parcelable.Creator() { // from class: com.android.server.pm.pkg.component.ParsedApexSystemServiceImpl.1
            @Override // android.os.Parcelable.Creator
            public ParsedApexSystemServiceImpl[] newArray(int i) {
                return new ParsedApexSystemServiceImpl[i];
            }

            @Override // android.os.Parcelable.Creator
            public ParsedApexSystemServiceImpl createFromParcel(Parcel parcel) {
                return new ParsedApexSystemServiceImpl(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.jarPath != null ? (byte) 2 : (byte) 0;
        if (this.minSdkVersion != null) {
            b = (byte) (b | 4);
        }
        if (this.maxSdkVersion != null) {
            b = (byte) (b | 8);
        }
        parcel.writeByte(b);
        sParcellingForName.parcel(this.name, parcel, i);
        sParcellingForJarPath.parcel(this.jarPath, parcel, i);
        sParcellingForMinSdkVersion.parcel(this.minSdkVersion, parcel, i);
        sParcellingForMaxSdkVersion.parcel(this.maxSdkVersion, parcel, i);
        parcel.writeInt(this.initOrder);
    }

    public ParsedApexSystemServiceImpl(Parcel parcel) {
        parcel.readByte();
        String str = (String) sParcellingForName.unparcel(parcel);
        String str2 = (String) sParcellingForJarPath.unparcel(parcel);
        String str3 = (String) sParcellingForMinSdkVersion.unparcel(parcel);
        String str4 = (String) sParcellingForMaxSdkVersion.unparcel(parcel);
        int readInt = parcel.readInt();
        this.name = str;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, str);
        this.jarPath = str2;
        this.minSdkVersion = str3;
        this.maxSdkVersion = str4;
        this.initOrder = readInt;
    }
}
