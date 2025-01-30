package com.android.server.pm.pkg.component;

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

/* loaded from: classes3.dex */
public class ParsedProcessImpl implements ParsedProcess, Parcelable {
  public static final Parcelable.Creator CREATOR;
  public static Parcelling sParcellingForDeniedPermissions;
  public ArrayMap appClassNamesByPackage;
  public Set deniedPermissions;
  public int gwpAsanMode;
  public int memtagMode;
  public String name;
  public int nativeHeapZeroInitialized;

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  public ParsedProcessImpl() {
    this.appClassNamesByPackage = ArrayMap.EMPTY;
    this.deniedPermissions = Collections.emptySet();
    this.gwpAsanMode = -1;
    this.memtagMode = -1;
    this.nativeHeapZeroInitialized = -1;
  }

  public ParsedProcessImpl(ParsedProcess parsedProcess) {
    this.appClassNamesByPackage = ArrayMap.EMPTY;
    this.deniedPermissions = Collections.emptySet();
    this.gwpAsanMode = -1;
    this.memtagMode = -1;
    this.nativeHeapZeroInitialized = -1;
    this.name = parsedProcess.getName();
    this.appClassNamesByPackage =
        parsedProcess.getAppClassNamesByPackage().size() == 0
            ? ArrayMap.EMPTY
            : new ArrayMap(parsedProcess.getAppClassNamesByPackage());
    this.deniedPermissions = new ArraySet(parsedProcess.getDeniedPermissions());
    this.gwpAsanMode = parsedProcess.getGwpAsanMode();
    this.memtagMode = parsedProcess.getMemtagMode();
    this.nativeHeapZeroInitialized = parsedProcess.getNativeHeapZeroInitialized();
  }

  public void addStateFrom(ParsedProcess parsedProcess) {
    this.deniedPermissions =
        CollectionUtils.addAll(this.deniedPermissions, parsedProcess.getDeniedPermissions());
    this.gwpAsanMode = parsedProcess.getGwpAsanMode();
    this.memtagMode = parsedProcess.getMemtagMode();
    this.nativeHeapZeroInitialized = parsedProcess.getNativeHeapZeroInitialized();
    ArrayMap appClassNamesByPackage = parsedProcess.getAppClassNamesByPackage();
    for (int i = 0; i < appClassNamesByPackage.size(); i++) {
      this.appClassNamesByPackage.put(
          (String) appClassNamesByPackage.keyAt(i), (String) appClassNamesByPackage.valueAt(i));
    }
  }

  public void putAppClassNameForPackage(String str, String str2) {
    if (this.appClassNamesByPackage.size() == 0) {
      this.appClassNamesByPackage = new ArrayMap(4);
    }
    this.appClassNamesByPackage.put(str, str2);
  }

  @Override // com.android.server.pm.pkg.component.ParsedProcess
  public String getName() {
    return this.name;
  }

  @Override // com.android.server.pm.pkg.component.ParsedProcess
  public ArrayMap getAppClassNamesByPackage() {
    return this.appClassNamesByPackage;
  }

  @Override // com.android.server.pm.pkg.component.ParsedProcess
  public Set getDeniedPermissions() {
    return this.deniedPermissions;
  }

  @Override // com.android.server.pm.pkg.component.ParsedProcess
  public int getGwpAsanMode() {
    return this.gwpAsanMode;
  }

  @Override // com.android.server.pm.pkg.component.ParsedProcess
  public int getMemtagMode() {
    return this.memtagMode;
  }

  @Override // com.android.server.pm.pkg.component.ParsedProcess
  public int getNativeHeapZeroInitialized() {
    return this.nativeHeapZeroInitialized;
  }

  public ParsedProcessImpl setName(String str) {
    this.name = str;
    AnnotationValidations.validate(NonNull.class, (NonNull) null, str);
    return this;
  }

  public ParsedProcessImpl setDeniedPermissions(Set set) {
    this.deniedPermissions = set;
    AnnotationValidations.validate(NonNull.class, (NonNull) null, set);
    return this;
  }

  public ParsedProcessImpl setGwpAsanMode(int i) {
    this.gwpAsanMode = i;
    AnnotationValidations.validate(ApplicationInfo.GwpAsanMode.class, (Annotation) null, i);
    return this;
  }

  public ParsedProcessImpl setMemtagMode(int i) {
    this.memtagMode = i;
    AnnotationValidations.validate(ApplicationInfo.MemtagMode.class, (Annotation) null, i);
    return this;
  }

  public ParsedProcessImpl setNativeHeapZeroInitialized(int i) {
    this.nativeHeapZeroInitialized = i;
    AnnotationValidations.validate(
        ApplicationInfo.NativeHeapZeroInitialized.class, (Annotation) null, i);
    return this;
  }

  static {
    Parcelling parcelling = Parcelling.Cache.get(Parcelling.BuiltIn.ForInternedStringSet.class);
    sParcellingForDeniedPermissions = parcelling;
    if (parcelling == null) {
      sParcellingForDeniedPermissions =
          Parcelling.Cache.put(new Parcelling.BuiltIn.ForInternedStringSet());
    }
    CREATOR =
        new Parcelable
            .Creator() { // from class: com.android.server.pm.pkg.component.ParsedProcessImpl.1
          @Override // android.os.Parcelable.Creator
          public ParsedProcessImpl[] newArray(int i) {
            return new ParsedProcessImpl[i];
          }

          @Override // android.os.Parcelable.Creator
          public ParsedProcessImpl createFromParcel(Parcel parcel) {
            return new ParsedProcessImpl(parcel);
          }
        };
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(this.name);
    parcel.writeMap(this.appClassNamesByPackage);
    sParcellingForDeniedPermissions.parcel(this.deniedPermissions, parcel, i);
    parcel.writeInt(this.gwpAsanMode);
    parcel.writeInt(this.memtagMode);
    parcel.writeInt(this.nativeHeapZeroInitialized);
  }

  public ParsedProcessImpl(Parcel parcel) {
    this.appClassNamesByPackage = ArrayMap.EMPTY;
    this.deniedPermissions = Collections.emptySet();
    this.gwpAsanMode = -1;
    this.memtagMode = -1;
    this.nativeHeapZeroInitialized = -1;
    String readString = parcel.readString();
    ArrayMap arrayMap = new ArrayMap();
    parcel.readMap(arrayMap, String.class.getClassLoader());
    Set set = (Set) sParcellingForDeniedPermissions.unparcel(parcel);
    int readInt = parcel.readInt();
    int readInt2 = parcel.readInt();
    int readInt3 = parcel.readInt();
    this.name = readString;
    AnnotationValidations.validate(NonNull.class, (NonNull) null, readString);
    this.appClassNamesByPackage = arrayMap;
    AnnotationValidations.validate(NonNull.class, (NonNull) null, arrayMap);
    this.deniedPermissions = set;
    AnnotationValidations.validate(NonNull.class, (NonNull) null, set);
    this.gwpAsanMode = readInt;
    AnnotationValidations.validate(ApplicationInfo.GwpAsanMode.class, (Annotation) null, readInt);
    this.memtagMode = readInt2;
    AnnotationValidations.validate(ApplicationInfo.MemtagMode.class, (Annotation) null, readInt2);
    this.nativeHeapZeroInitialized = readInt3;
    AnnotationValidations.validate(
        ApplicationInfo.NativeHeapZeroInitialized.class, (Annotation) null, readInt3);
  }
}
