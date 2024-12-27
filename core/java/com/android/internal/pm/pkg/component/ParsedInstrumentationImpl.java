package com.android.internal.pm.pkg.component;

import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.android.internal.pm.parsing.pkg.PackageImpl;

/* loaded from: classes5.dex */
public class ParsedInstrumentationImpl extends ParsedComponentImpl
        implements ParsedInstrumentation, Parcelable {
    public static final Parcelable.Creator<ParsedInstrumentationImpl> CREATOR =
            new Parcelable.Creator<
                    ParsedInstrumentationImpl>() { // from class:
                                                   // com.android.internal.pm.pkg.component.ParsedInstrumentationImpl.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ParsedInstrumentationImpl createFromParcel(Parcel source) {
                    return new ParsedInstrumentationImpl(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ParsedInstrumentationImpl[] newArray(int size) {
                    return new ParsedInstrumentationImpl[size];
                }
            };
    private boolean functionalTest;
    private boolean handleProfiling;
    private String targetPackage;
    private String targetProcesses;

    public ParsedInstrumentationImpl() {}

    public ParsedInstrumentationImpl setTargetPackage(String targetPackage) {
        this.targetPackage = TextUtils.safeIntern(targetPackage);
        return this;
    }

    public ParsedInstrumentationImpl setTargetProcesses(String targetProcesses) {
        this.targetProcesses = TextUtils.safeIntern(targetProcesses);
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
    public int describeContents() {
        return 0;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        PackageImpl.sForInternedString.parcel(this.targetPackage, dest, flags);
        PackageImpl.sForInternedString.parcel(this.targetProcesses, dest, flags);
        dest.writeBoolean(this.handleProfiling);
        dest.writeBoolean(this.functionalTest);
    }

    protected ParsedInstrumentationImpl(Parcel in) {
        super(in);
        this.targetPackage = PackageImpl.sForInternedString.unparcel(in);
        this.targetProcesses = PackageImpl.sForInternedString.unparcel(in);
        this.handleProfiling = in.readByte() != 0;
        this.functionalTest = in.readByte() != 0;
    }

    public ParsedInstrumentationImpl(
            String targetPackage,
            String targetProcesses,
            boolean handleProfiling,
            boolean functionalTest) {
        this.targetPackage = targetPackage;
        this.targetProcesses = targetProcesses;
        this.handleProfiling = handleProfiling;
        this.functionalTest = functionalTest;
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

    public ParsedInstrumentationImpl setHandleProfiling(boolean value) {
        this.handleProfiling = value;
        return this;
    }

    public ParsedInstrumentationImpl setFunctionalTest(boolean value) {
        this.functionalTest = value;
        return this;
    }

    @Deprecated
    private void __metadata() {}
}
