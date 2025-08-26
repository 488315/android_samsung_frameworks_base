package android.content.integrity;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final class CompoundFormula extends IntegrityFormula implements Parcelable {
    public static final int AND = 0;
    public static final Parcelable.Creator<CompoundFormula> CREATOR = new Parcelable.Creator<CompoundFormula>() { // from class: android.content.integrity.CompoundFormula.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompoundFormula createFromParcel(Parcel parcel) {
            return new CompoundFormula(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompoundFormula[] newArray(int i) {
            return new CompoundFormula[i];
        }
    };
    public static final int NOT = 2;
    public static final int OR = 1;
    private final int mConnector;
    private final List<IntegrityFormula> mFormulas;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Connector {
    }

    private static boolean isValidConnector(int i) {
        return i == 0 || i == 1 || i == 2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.content.integrity.IntegrityFormula
    public int getTag() {
        return 0;
    }

    public CompoundFormula(int i, List<IntegrityFormula> list) {
        Preconditions.checkArgument(isValidConnector(i), "Unknown connector: %d", Integer.valueOf(i));
        validateFormulas(i, list);
        this.mConnector = i;
        this.mFormulas = Collections.unmodifiableList(list);
    }

    CompoundFormula(Parcel parcel) {
        this.mConnector = parcel.readInt();
        int i = parcel.readInt();
        Preconditions.checkArgument(i >= 0, "Must have non-negative length. Got %d", Integer.valueOf(i));
        this.mFormulas = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            this.mFormulas.add(IntegrityFormula.readFromParcel(parcel));
        }
        validateFormulas(this.mConnector, this.mFormulas);
    }

    public int getConnector() {
        return this.mConnector;
    }

    public List<IntegrityFormula> getFormulas() {
        return this.mFormulas;
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean matches(final AppInstallMetadata appInstallMetadata) {
        int connector = getConnector();
        if (connector == 0) {
            return getFormulas().stream().allMatch(new Predicate() { // from class: android.content.integrity.CompoundFormula$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((IntegrityFormula) obj).matches(appInstallMetadata);
                }
            });
        }
        if (connector == 1) {
            return getFormulas().stream().anyMatch(new Predicate() { // from class: android.content.integrity.CompoundFormula$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((IntegrityFormula) obj).matches(appInstallMetadata);
                }
            });
        }
        if (connector == 2) {
            return !getFormulas().get(0).matches(appInstallMetadata);
        }
        throw new IllegalArgumentException("Unknown connector " + getConnector());
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean isAppCertificateFormula() {
        return getFormulas().stream().anyMatch(new Predicate() { // from class: android.content.integrity.CompoundFormula$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((IntegrityFormula) obj).isAppCertificateFormula();
            }
        });
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean isAppCertificateLineageFormula() {
        return getFormulas().stream().anyMatch(new Predicate() { // from class: android.content.integrity.CompoundFormula$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((IntegrityFormula) obj).isAppCertificateLineageFormula();
            }
        });
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean isInstallerFormula() {
        return getFormulas().stream().anyMatch(new Predicate() { // from class: android.content.integrity.CompoundFormula$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((IntegrityFormula) obj).isInstallerFormula();
            }
        });
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.mFormulas.size() == 1) {
            sb.append(String.format("%s ", connectorToString(this.mConnector)));
            sb.append(this.mFormulas.get(0).toString());
        } else {
            for (int i = 0; i < this.mFormulas.size(); i++) {
                if (i > 0) {
                    sb.append(String.format(" %s ", connectorToString(this.mConnector)));
                }
                sb.append(this.mFormulas.get(i).toString());
            }
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            CompoundFormula compoundFormula = (CompoundFormula) obj;
            if (this.mConnector == compoundFormula.mConnector && this.mFormulas.equals(compoundFormula.mFormulas)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mConnector), this.mFormulas);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mConnector);
        parcel.writeInt(this.mFormulas.size());
        Iterator<IntegrityFormula> it = this.mFormulas.iterator();
        while (it.hasNext()) {
            IntegrityFormula.writeToParcel(it.next(), parcel, i);
        }
    }

    private static void validateFormulas(int i, List<IntegrityFormula> list) {
        if (i == 0 || i == 1) {
            Preconditions.checkArgument(list.size() >= 2, "Connector %s must have at least 2 formulas", connectorToString(i));
        } else {
            if (i != 2) {
                return;
            }
            Preconditions.checkArgument(list.size() == 1, "Connector %s must have 1 formula only", connectorToString(i));
        }
    }

    private static String connectorToString(int i) {
        if (i == 0) {
            return "AND";
        }
        if (i == 1) {
            return "OR";
        }
        if (i == 2) {
            return "NOT";
        }
        throw new IllegalArgumentException("Unknown connector " + i);
    }
}
