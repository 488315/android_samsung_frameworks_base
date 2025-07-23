package android.content.integrity;

import android.annotation.SystemApi;
import android.content.integrity.AtomicFormula;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

@SystemApi
/* loaded from: classes.dex */
public abstract class IntegrityFormula {
    public static final int BOOLEAN_ATOMIC_FORMULA_TAG = 3;
    public static final int COMPOUND_FORMULA_TAG = 0;
    public static final int INSTALLER_ALLOWED_BY_MANIFEST_FORMULA_TAG = 4;
    public static final int LONG_ATOMIC_FORMULA_TAG = 2;
    public static final int STRING_ATOMIC_FORMULA_TAG = 1;

    @Retention(RetentionPolicy.SOURCE)
    @interface Tag {
    }

    public abstract int getTag();

    public abstract boolean isAppCertificateFormula();

    public abstract boolean isAppCertificateLineageFormula();

    public abstract boolean isInstallerFormula();

    public abstract boolean matches(AppInstallMetadata appInstallMetadata);

    public static final class Application {
        public static IntegrityFormula packageNameEquals(String str) {
            return new AtomicFormula.StringAtomicFormula(0, str);
        }

        public static IntegrityFormula certificatesContain(String str) {
            return new AtomicFormula.StringAtomicFormula(1, str);
        }

        public static IntegrityFormula certificateLineageContains(String str) {
            return new AtomicFormula.StringAtomicFormula(8, str);
        }

        public static IntegrityFormula versionCodeEquals(long j) {
            return new AtomicFormula.LongAtomicFormula(4, 0, j);
        }

        public static IntegrityFormula versionCodeGreaterThan(long j) {
            return new AtomicFormula.LongAtomicFormula(4, 1, j);
        }

        public static IntegrityFormula versionCodeGreaterThanOrEqualTo(long j) {
            return new AtomicFormula.LongAtomicFormula(4, 2, j);
        }

        public static IntegrityFormula isPreInstalled() {
            return new AtomicFormula.BooleanAtomicFormula(5, true);
        }

        private Application() {
        }
    }

    public static final class Installer {
        public static IntegrityFormula packageNameEquals(String str) {
            return new AtomicFormula.StringAtomicFormula(2, str);
        }

        public static IntegrityFormula notAllowedByManifest() {
            return IntegrityFormula.not(new InstallerAllowedByManifestFormula());
        }

        public static IntegrityFormula certificatesContain(String str) {
            return new AtomicFormula.StringAtomicFormula(3, str);
        }

        private Installer() {
        }
    }

    public static final class SourceStamp {
        public static IntegrityFormula stampCertificateHashEquals(String str) {
            return new AtomicFormula.StringAtomicFormula(7, str);
        }

        public static IntegrityFormula notTrusted() {
            return new AtomicFormula.BooleanAtomicFormula(6, false);
        }

        private SourceStamp() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void writeToParcel(IntegrityFormula integrityFormula, Parcel parcel, int i) {
        parcel.writeInt(integrityFormula.getTag());
        ((Parcelable) integrityFormula).writeToParcel(parcel, i);
    }

    public static IntegrityFormula readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            return CompoundFormula.CREATOR.createFromParcel(parcel);
        }
        if (readInt == 1) {
            return AtomicFormula.StringAtomicFormula.CREATOR.createFromParcel(parcel);
        }
        if (readInt == 2) {
            return AtomicFormula.LongAtomicFormula.CREATOR.createFromParcel(parcel);
        }
        if (readInt == 3) {
            return AtomicFormula.BooleanAtomicFormula.CREATOR.createFromParcel(parcel);
        }
        if (readInt == 4) {
            return InstallerAllowedByManifestFormula.CREATOR.createFromParcel(parcel);
        }
        throw new IllegalArgumentException("Unknown formula tag " + readInt);
    }

    public static IntegrityFormula any(IntegrityFormula... integrityFormulaArr) {
        return new CompoundFormula(1, Arrays.asList(integrityFormulaArr));
    }

    public static IntegrityFormula all(IntegrityFormula... integrityFormulaArr) {
        return new CompoundFormula(0, Arrays.asList(integrityFormulaArr));
    }

    public static IntegrityFormula not(IntegrityFormula integrityFormula) {
        return new CompoundFormula(2, Arrays.asList(integrityFormula));
    }

    IntegrityFormula() {
    }
}
