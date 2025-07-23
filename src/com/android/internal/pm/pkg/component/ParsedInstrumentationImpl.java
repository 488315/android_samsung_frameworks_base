package com.android.internal.pm.pkg.component;

import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.internal.pm.parsing.pkg.PackageImpl;

/* loaded from: classes5.dex */
public class ParsedInstrumentationImpl extends ParsedComponentImpl implements ParsedInstrumentation, Parcelable {
    public static final Parcelable.Creator<ParsedInstrumentationImpl> CREATOR = new Parcelable.Creator<ParsedInstrumentationImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedInstrumentationImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParsedInstrumentationImpl createFromParcel(Parcel parcel) {
            return new ParsedInstrumentationImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParsedInstrumentationImpl[] newArray(int i) {
            return new ParsedInstrumentationImpl[i];
        }
    };
    private boolean functionalTest;
    private boolean handleProfiling;
    private String targetPackage;
    private String targetProcesses;

    @Deprecated
    private void __metadata() {
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ParsedInstrumentationImpl() {
    }

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

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        PackageImpl.sForInternedString.parcel(this.targetPackage, parcel, i);
        PackageImpl.sForInternedString.parcel(this.targetProcesses, parcel, i);
        parcel.writeBoolean(this.handleProfiling);
        parcel.writeBoolean(this.functionalTest);
    }

    protected ParsedInstrumentationImpl(Parcel parcel) {
        super(parcel);
        this.targetPackage = PackageImpl.sForInternedString.unparcel(parcel);
        this.targetProcesses = PackageImpl.sForInternedString.unparcel(parcel);
        this.handleProfiling = parcel.readByte() != 0;
        this.functionalTest = parcel.readByte() != 0;
    }

    public ParsedInstrumentationImpl(String str, String str2, boolean z, boolean z2) {
        this.targetPackage = str;
        this.targetProcesses = str2;
        this.handleProfiling = z;
        this.functionalTest = z2;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedInstrumentation
    public String getTargetPackage() {
        return this.targetPackage;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedInstrumentation
    public String getTargetProcesses() {
        return this.targetProcesses;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedInstrumentation
    public boolean isHandleProfiling() {
        return this.handleProfiling;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedInstrumentation
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
