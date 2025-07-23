package android.content.integrity;

import android.annotation.SystemApi;
import android.content.pm.ASKSManager;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class Rule implements Parcelable {
    public static final Parcelable.Creator<Rule> CREATOR = new Parcelable.Creator<Rule>() { // from class: android.content.integrity.Rule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Rule createFromParcel(Parcel parcel) {
            return new Rule(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Rule[] newArray(int i) {
            return new Rule[i];
        }
    };
    public static final int DENY = 0;
    public static final int FORCE_ALLOW = 1;
    private final int mEffect;
    private final IntegrityFormula mFormula;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Effect {
    }

    private static boolean isValidEffect(int i) {
        return i == 0 || i == 1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Rule(IntegrityFormula integrityFormula, int i) {
        Preconditions.checkArgument(isValidEffect(i), "Unknown effect: %d", Integer.valueOf(i));
        this.mFormula = (IntegrityFormula) Objects.requireNonNull(integrityFormula);
        this.mEffect = i;
    }

    Rule(Parcel parcel) {
        this.mFormula = IntegrityFormula.readFromParcel(parcel);
        this.mEffect = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        IntegrityFormula.writeToParcel(this.mFormula, parcel, i);
        parcel.writeInt(this.mEffect);
    }

    public IntegrityFormula getFormula() {
        return this.mFormula;
    }

    public int getEffect() {
        return this.mEffect;
    }

    public String toString() {
        return String.format("Rule: %s, %s", this.mFormula, effectToString(this.mEffect));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Rule rule = (Rule) obj;
            if (this.mEffect == rule.mEffect && Objects.equals(this.mFormula, rule.mFormula)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mFormula, Integer.valueOf(this.mEffect));
    }

    private static String effectToString(int i) {
        if (i == 0) {
            return ASKSManager.TYPE_DENY;
        }
        if (i == 1) {
            return "FORCE_ALLOW";
        }
        throw new IllegalArgumentException("Unknown effect " + i);
    }
}
