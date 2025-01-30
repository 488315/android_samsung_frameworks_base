package com.android.server.pm.pkg.component;

import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.server.pm.parsing.pkg.PackageImpl;

/* loaded from: classes3.dex */
public class ParsedInstrumentationImpl extends ParsedComponentImpl
    implements ParsedInstrumentation {
  public static final Parcelable.Creator CREATOR =
      new Parcelable
          .Creator() { // from class:
                       // com.android.server.pm.pkg.component.ParsedInstrumentationImpl.1
        @Override // android.os.Parcelable.Creator
        public ParsedInstrumentationImpl createFromParcel(Parcel parcel) {
          return new ParsedInstrumentationImpl(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public ParsedInstrumentationImpl[] newArray(int i) {
          return new ParsedInstrumentationImpl[i];
        }
      };
  public boolean functionalTest;
  public boolean handleProfiling;
  public String targetPackage;
  public String targetProcesses;

  @Override // com.android.server.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  public ParsedInstrumentationImpl() {}

  public ParsedInstrumentationImpl setTargetPackage(String str) {
    this.targetPackage = TextUtils.safeIntern(str);
    return this;
  }

  public ParsedInstrumentationImpl setTargetProcesses(String str) {
    this.targetProcesses = TextUtils.safeIntern(str);
    return this;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder(128);
    sb.append("Instrumentation{");
    sb.append(Integer.toHexString(System.identityHashCode(this)));
    sb.append(' ');
    ComponentName.appendShortString(sb, getPackageName(), getName());
    sb.append('}');
    return sb.toString();
  }

  @Override // com.android.server.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    super.writeToParcel(parcel, i);
    PackageImpl.sForInternedString.parcel(this.targetPackage, parcel, i);
    PackageImpl.sForInternedString.parcel(this.targetProcesses, parcel, i);
    parcel.writeBoolean(this.handleProfiling);
    parcel.writeBoolean(this.functionalTest);
  }

  public ParsedInstrumentationImpl(Parcel parcel) {
    super(parcel);
    this.targetPackage = PackageImpl.sForInternedString.unparcel(parcel);
    this.targetProcesses = PackageImpl.sForInternedString.unparcel(parcel);
    this.handleProfiling = parcel.readByte() != 0;
    this.functionalTest = parcel.readByte() != 0;
  }

  @Override // com.android.server.pm.pkg.component.ParsedInstrumentation
  public String getTargetPackage() {
    return this.targetPackage;
  }

  @Override // com.android.server.pm.pkg.component.ParsedInstrumentation
  public String getTargetProcesses() {
    return this.targetProcesses;
  }

  @Override // com.android.server.pm.pkg.component.ParsedInstrumentation
  public boolean isHandleProfiling() {
    return this.handleProfiling;
  }

  @Override // com.android.server.pm.pkg.component.ParsedInstrumentation
  public boolean isFunctionalTest() {
    return this.functionalTest;
  }

  public ParsedInstrumentationImpl setHandleProfiling(boolean z) {
    this.handleProfiling = z;
    return this;
  }

  public ParsedInstrumentationImpl setFunctionalTest(boolean z) {
    this.functionalTest = z;
    return this;
  }
}
