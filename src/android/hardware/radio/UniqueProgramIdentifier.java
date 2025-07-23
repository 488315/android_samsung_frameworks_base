package android.hardware.radio;

import android.hardware.radio.ProgramSelector;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: classes2.dex */
public final class UniqueProgramIdentifier implements Parcelable {
    public static final Parcelable.Creator<UniqueProgramIdentifier> CREATOR = new Parcelable.Creator<UniqueProgramIdentifier>() { // from class: android.hardware.radio.UniqueProgramIdentifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UniqueProgramIdentifier createFromParcel(Parcel parcel) {
            return new UniqueProgramIdentifier(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UniqueProgramIdentifier[] newArray(int i) {
            return new UniqueProgramIdentifier[i];
        }
    };
    private final ProgramSelector.Identifier[] mCriticalSecondaryIds;
    private final ProgramSelector.Identifier mPrimaryId;

    public static boolean requireCriticalSecondaryIds(int i) {
        return i == 14 || i == 5;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UniqueProgramIdentifier(ProgramSelector programSelector) {
        Objects.requireNonNull(programSelector, "Program selector can not be null");
        ProgramSelector.Identifier primaryId = programSelector.getPrimaryId();
        this.mPrimaryId = primaryId;
        int type = primaryId.getType();
        if (type == 5 || type == 14) {
            ProgramSelector.Identifier[] secondaryIds = programSelector.getSecondaryIds();
            ProgramSelector.Identifier identifier = null;
            ProgramSelector.Identifier identifier2 = null;
            for (int i = 0; i < secondaryIds.length; i++) {
                if (identifier == null && secondaryIds[i].getType() == 6) {
                    identifier = programSelector.getSecondaryIds()[i];
                } else if (identifier2 == null && secondaryIds[i].getType() == 8) {
                    identifier2 = secondaryIds[i];
                }
                if (identifier != null && identifier2 != null) {
                    break;
                }
            }
            if (identifier == null) {
                if (identifier2 == null) {
                    this.mCriticalSecondaryIds = new ProgramSelector.Identifier[0];
                    return;
                } else {
                    this.mCriticalSecondaryIds = new ProgramSelector.Identifier[]{identifier2};
                    return;
                }
            }
            if (identifier2 == null) {
                this.mCriticalSecondaryIds = new ProgramSelector.Identifier[]{identifier};
                return;
            } else {
                this.mCriticalSecondaryIds = new ProgramSelector.Identifier[]{identifier, identifier2};
                return;
            }
        }
        this.mCriticalSecondaryIds = new ProgramSelector.Identifier[0];
    }

    public UniqueProgramIdentifier(ProgramSelector.Identifier identifier) {
        this.mPrimaryId = identifier;
        this.mCriticalSecondaryIds = new ProgramSelector.Identifier[0];
    }

    public ProgramSelector.Identifier getPrimaryId() {
        return this.mPrimaryId;
    }

    public List<ProgramSelector.Identifier> getCriticalSecondaryIds() {
        return List.of((Object[]) this.mCriticalSecondaryIds);
    }

    public String toString() {
        return "UniqueProgramIdentifier(primary=" + this.mPrimaryId + ", criticalSecondary=" + Arrays.toString(this.mCriticalSecondaryIds) + NavigationBarInflaterView.KEY_CODE_END;
    }

    public int hashCode() {
        return Objects.hash(this.mPrimaryId, Integer.valueOf(Arrays.hashCode(this.mCriticalSecondaryIds)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UniqueProgramIdentifier)) {
            return false;
        }
        UniqueProgramIdentifier uniqueProgramIdentifier = (UniqueProgramIdentifier) obj;
        return uniqueProgramIdentifier.mPrimaryId.equals(this.mPrimaryId) && Arrays.equals(uniqueProgramIdentifier.mCriticalSecondaryIds, this.mCriticalSecondaryIds);
    }

    private UniqueProgramIdentifier(Parcel parcel) {
        this.mPrimaryId = (ProgramSelector.Identifier) parcel.readTypedObject(ProgramSelector.Identifier.CREATOR);
        this.mCriticalSecondaryIds = (ProgramSelector.Identifier[]) parcel.createTypedArray(ProgramSelector.Identifier.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mPrimaryId, 0);
        parcel.writeTypedArray(this.mCriticalSecondaryIds, 0);
        if (Stream.of((Object[]) this.mCriticalSecondaryIds).anyMatch(new Predicate() { // from class: android.hardware.radio.UniqueProgramIdentifier$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.isNull((ProgramSelector.Identifier) obj);
            }
        })) {
            throw new IllegalArgumentException("criticalSecondaryIds list must not contain nulls");
        }
    }
}
