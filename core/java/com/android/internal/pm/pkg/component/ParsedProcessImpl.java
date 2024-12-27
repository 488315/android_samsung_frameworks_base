package com.android.internal.pm.pkg.component;

import android.annotation.NonNull;
import android.content.pm.ApplicationInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.ArraySet;

import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.Parcelling;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;

public class ParsedProcessImpl implements ParsedProcess, Parcelable {
    public static final Parcelable.Creator<ParsedProcessImpl> CREATOR;
    static Parcelling<Set<String>> sParcellingForDeniedPermissions;
    private ArrayMap<String, String> appClassNamesByPackage;
    private Set<String> deniedPermissions;
    private int gwpAsanMode;
    private int memtagMode;
    private String name;
    private int nativeHeapZeroInitialized;
    private boolean useEmbeddedDex;

    public ParsedProcessImpl() {
        this.appClassNamesByPackage = ArrayMap.EMPTY;
        this.deniedPermissions = Collections.emptySet();
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
    }

    public ParsedProcessImpl(ParsedProcess other) {
        this.appClassNamesByPackage = ArrayMap.EMPTY;
        this.deniedPermissions = Collections.emptySet();
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
        this.name = other.getName();
        this.appClassNamesByPackage =
                other.getAppClassNamesByPackage().size() == 0
                        ? ArrayMap.EMPTY
                        : new ArrayMap<>(other.getAppClassNamesByPackage());
        this.deniedPermissions = new ArraySet(other.getDeniedPermissions());
        this.gwpAsanMode = other.getGwpAsanMode();
        this.memtagMode = other.getMemtagMode();
        this.nativeHeapZeroInitialized = other.getNativeHeapZeroInitialized();
        this.useEmbeddedDex = other.isUseEmbeddedDex();
    }

    public void addStateFrom(ParsedProcess other) {
        this.deniedPermissions =
                CollectionUtils.addAll(this.deniedPermissions, other.getDeniedPermissions());
        this.gwpAsanMode = other.getGwpAsanMode();
        this.memtagMode = other.getMemtagMode();
        this.nativeHeapZeroInitialized = other.getNativeHeapZeroInitialized();
        this.useEmbeddedDex = other.isUseEmbeddedDex();
        ArrayMap<String, String> oacn = other.getAppClassNamesByPackage();
        for (int i = 0; i < oacn.size(); i++) {
            this.appClassNamesByPackage.put(oacn.keyAt(i), oacn.valueAt(i));
        }
    }

    public void putAppClassNameForPackage(String packageName, String className) {
        if (this.appClassNamesByPackage.size() == 0) {
            this.appClassNamesByPackage = new ArrayMap<>(4);
        }
        this.appClassNamesByPackage.put(packageName, className);
    }

    public ParsedProcessImpl(
            String name,
            ArrayMap<String, String> appClassNamesByPackage,
            Set<String> deniedPermissions,
            int gwpAsanMode,
            int memtagMode,
            int nativeHeapZeroInitialized,
            boolean useEmbeddedDex) {
        this.appClassNamesByPackage = ArrayMap.EMPTY;
        this.deniedPermissions = Collections.emptySet();
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
        this.name = name;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) name);
        this.appClassNamesByPackage = appClassNamesByPackage;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) appClassNamesByPackage);
        this.deniedPermissions = deniedPermissions;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) deniedPermissions);
        this.gwpAsanMode = gwpAsanMode;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) ApplicationInfo.GwpAsanMode.class,
                (Annotation) null,
                gwpAsanMode);
        this.memtagMode = memtagMode;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) ApplicationInfo.MemtagMode.class,
                (Annotation) null,
                memtagMode);
        this.nativeHeapZeroInitialized = nativeHeapZeroInitialized;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) ApplicationInfo.NativeHeapZeroInitialized.class,
                (Annotation) null,
                nativeHeapZeroInitialized);
        this.useEmbeddedDex = useEmbeddedDex;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProcess
    public String getName() {
        return this.name;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProcess
    public ArrayMap<String, String> getAppClassNamesByPackage() {
        return this.appClassNamesByPackage;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProcess
    public Set<String> getDeniedPermissions() {
        return this.deniedPermissions;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProcess
    public int getGwpAsanMode() {
        return this.gwpAsanMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProcess
    public int getMemtagMode() {
        return this.memtagMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProcess
    public int getNativeHeapZeroInitialized() {
        return this.nativeHeapZeroInitialized;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProcess
    public boolean isUseEmbeddedDex() {
        return this.useEmbeddedDex;
    }

    public ParsedProcessImpl setName(String value) {
        this.name = value;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.name);
        return this;
    }

    public ParsedProcessImpl setAppClassNamesByPackage(ArrayMap<String, String> value) {
        this.appClassNamesByPackage = value;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class,
                (NonNull) null,
                (Object) this.appClassNamesByPackage);
        return this;
    }

    public ParsedProcessImpl setDeniedPermissions(Set<String> value) {
        this.deniedPermissions = value;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.deniedPermissions);
        return this;
    }

    public ParsedProcessImpl setGwpAsanMode(int value) {
        this.gwpAsanMode = value;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) ApplicationInfo.GwpAsanMode.class,
                (Annotation) null,
                this.gwpAsanMode);
        return this;
    }

    public ParsedProcessImpl setMemtagMode(int value) {
        this.memtagMode = value;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) ApplicationInfo.MemtagMode.class,
                (Annotation) null,
                this.memtagMode);
        return this;
    }

    public ParsedProcessImpl setNativeHeapZeroInitialized(int value) {
        this.nativeHeapZeroInitialized = value;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) ApplicationInfo.NativeHeapZeroInitialized.class,
                (Annotation) null,
                this.nativeHeapZeroInitialized);
        return this;
    }

    public ParsedProcessImpl setUseEmbeddedDex(boolean value) {
        this.useEmbeddedDex = value;
        return this;
    }

    static {
        sParcellingForDeniedPermissions =
                Parcelling.Cache.get(Parcelling.BuiltIn.ForInternedStringSet.class);
        if (sParcellingForDeniedPermissions == null) {
            sParcellingForDeniedPermissions =
                    Parcelling.Cache.put(new Parcelling.BuiltIn.ForInternedStringSet());
        }
        CREATOR =
                new Parcelable.Creator<
                        ParsedProcessImpl>() { // from class:
                                               // com.android.internal.pm.pkg.component.ParsedProcessImpl.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public ParsedProcessImpl[] newArray(int size) {
                        return new ParsedProcessImpl[size];
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public ParsedProcessImpl createFromParcel(Parcel in) {
                        return new ParsedProcessImpl(in);
                    }
                };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.useEmbeddedDex ? (byte) (0 | 64) : (byte) 0;
        dest.writeByte(flg);
        dest.writeString(this.name);
        dest.writeMap(this.appClassNamesByPackage);
        sParcellingForDeniedPermissions.parcel(this.deniedPermissions, dest, flags);
        dest.writeInt(this.gwpAsanMode);
        dest.writeInt(this.memtagMode);
        dest.writeInt(this.nativeHeapZeroInitialized);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ParsedProcessImpl(Parcel in) {
        this.appClassNamesByPackage = ArrayMap.EMPTY;
        this.deniedPermissions = Collections.emptySet();
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
        byte flg = in.readByte();
        boolean _useEmbeddedDex = (flg & 64) != 0;
        String _name = in.readString();
        ArrayMap<String, String> _appClassNamesByPackage = new ArrayMap<>();
        in.readMap(_appClassNamesByPackage, String.class.getClassLoader());
        Set<String> _deniedPermissions = sParcellingForDeniedPermissions.unparcel(in);
        int _gwpAsanMode = in.readInt();
        int _memtagMode = in.readInt();
        int _nativeHeapZeroInitialized = in.readInt();
        this.name = _name;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.name);
        this.appClassNamesByPackage = _appClassNamesByPackage;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class,
                (NonNull) null,
                (Object) this.appClassNamesByPackage);
        this.deniedPermissions = _deniedPermissions;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.deniedPermissions);
        this.gwpAsanMode = _gwpAsanMode;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) ApplicationInfo.GwpAsanMode.class,
                (Annotation) null,
                this.gwpAsanMode);
        this.memtagMode = _memtagMode;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) ApplicationInfo.MemtagMode.class,
                (Annotation) null,
                this.memtagMode);
        this.nativeHeapZeroInitialized = _nativeHeapZeroInitialized;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) ApplicationInfo.NativeHeapZeroInitialized.class,
                (Annotation) null,
                this.nativeHeapZeroInitialized);
        this.useEmbeddedDex = _useEmbeddedDex;
    }

    @Deprecated
    private void __metadata() {}
}
