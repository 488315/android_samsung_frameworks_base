package com.android.internal.pm.pkg.component;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Parcelling;

public class ParsedApexSystemServiceImpl implements ParsedApexSystemService, Parcelable {
    public static final Parcelable.Creator<ParsedApexSystemServiceImpl> CREATOR;
    static Parcelling<String> sParcellingForJarPath;
    static Parcelling<String> sParcellingForMaxSdkVersion;
    static Parcelling<String> sParcellingForMinSdkVersion;
    static Parcelling<String> sParcellingForName;
    private int initOrder;
    private String jarPath;
    private String maxSdkVersion;
    private String minSdkVersion;
    private String name;

    public ParsedApexSystemServiceImpl() {}

    public ParsedApexSystemServiceImpl(
            String name,
            String jarPath,
            String minSdkVersion,
            String maxSdkVersion,
            int initOrder) {
        this.name = name;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) name);
        this.jarPath = jarPath;
        this.minSdkVersion = minSdkVersion;
        this.maxSdkVersion = maxSdkVersion;
        this.initOrder = initOrder;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedApexSystemService
    public String getName() {
        return this.name;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedApexSystemService
    public String getJarPath() {
        return this.jarPath;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedApexSystemService
    public String getMinSdkVersion() {
        return this.minSdkVersion;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedApexSystemService
    public String getMaxSdkVersion() {
        return this.maxSdkVersion;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedApexSystemService
    public int getInitOrder() {
        return this.initOrder;
    }

    public ParsedApexSystemServiceImpl setName(String value) {
        this.name = value;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.name);
        return this;
    }

    public ParsedApexSystemServiceImpl setJarPath(String value) {
        this.jarPath = value;
        return this;
    }

    public ParsedApexSystemServiceImpl setMinSdkVersion(String value) {
        this.minSdkVersion = value;
        return this;
    }

    public ParsedApexSystemServiceImpl setMaxSdkVersion(String value) {
        this.maxSdkVersion = value;
        return this;
    }

    public ParsedApexSystemServiceImpl setInitOrder(int value) {
        this.initOrder = value;
        return this;
    }

    static {
        sParcellingForName = Parcelling.Cache.get(Parcelling.BuiltIn.ForInternedString.class);
        if (sParcellingForName == null) {
            sParcellingForName = Parcelling.Cache.put(new Parcelling.BuiltIn.ForInternedString());
        }
        sParcellingForJarPath = Parcelling.Cache.get(Parcelling.BuiltIn.ForInternedString.class);
        if (sParcellingForJarPath == null) {
            sParcellingForJarPath =
                    Parcelling.Cache.put(new Parcelling.BuiltIn.ForInternedString());
        }
        sParcellingForMinSdkVersion =
                Parcelling.Cache.get(Parcelling.BuiltIn.ForInternedString.class);
        if (sParcellingForMinSdkVersion == null) {
            sParcellingForMinSdkVersion =
                    Parcelling.Cache.put(new Parcelling.BuiltIn.ForInternedString());
        }
        sParcellingForMaxSdkVersion =
                Parcelling.Cache.get(Parcelling.BuiltIn.ForInternedString.class);
        if (sParcellingForMaxSdkVersion == null) {
            sParcellingForMaxSdkVersion =
                    Parcelling.Cache.put(new Parcelling.BuiltIn.ForInternedString());
        }
        CREATOR =
                new Parcelable.Creator<
                        ParsedApexSystemServiceImpl>() { // from class:
                                                         // com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public ParsedApexSystemServiceImpl[] newArray(int size) {
                        return new ParsedApexSystemServiceImpl[size];
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public ParsedApexSystemServiceImpl createFromParcel(Parcel in) {
                        return new ParsedApexSystemServiceImpl(in);
                    }
                };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.jarPath != null ? (byte) (0 | 2) : (byte) 0;
        if (this.minSdkVersion != null) {
            flg = (byte) (flg | 4);
        }
        if (this.maxSdkVersion != null) {
            flg = (byte) (flg | 8);
        }
        dest.writeByte(flg);
        sParcellingForName.parcel(this.name, dest, flags);
        sParcellingForJarPath.parcel(this.jarPath, dest, flags);
        sParcellingForMinSdkVersion.parcel(this.minSdkVersion, dest, flags);
        sParcellingForMaxSdkVersion.parcel(this.maxSdkVersion, dest, flags);
        dest.writeInt(this.initOrder);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ParsedApexSystemServiceImpl(Parcel in) {
        in.readByte();
        String _name = sParcellingForName.unparcel(in);
        String _jarPath = sParcellingForJarPath.unparcel(in);
        String _minSdkVersion = sParcellingForMinSdkVersion.unparcel(in);
        String _maxSdkVersion = sParcellingForMaxSdkVersion.unparcel(in);
        int _initOrder = in.readInt();
        this.name = _name;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.name);
        this.jarPath = _jarPath;
        this.minSdkVersion = _minSdkVersion;
        this.maxSdkVersion = _maxSdkVersion;
        this.initOrder = _initOrder;
    }

    @Deprecated
    private void __metadata() {}
}
