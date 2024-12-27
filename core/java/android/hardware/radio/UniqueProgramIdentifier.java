package android.hardware.radio;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class UniqueProgramIdentifier implements Parcelable {
    public static final Parcelable.Creator<UniqueProgramIdentifier> CREATOR =
            new Parcelable.Creator<UniqueProgramIdentifier>() { // from class:
                // android.hardware.radio.UniqueProgramIdentifier.1
                @Override // android.os.Parcelable.Creator
                public UniqueProgramIdentifier createFromParcel(Parcel in) {
                    return new UniqueProgramIdentifier(in);
                }

                @Override // android.os.Parcelable.Creator
                public UniqueProgramIdentifier[] newArray(int size) {
                    return new UniqueProgramIdentifier[size];
                }
            };
    private final ProgramSelector.Identifier[] mCriticalSecondaryIds;
    private final ProgramSelector.Identifier mPrimaryId;

    public static boolean requireCriticalSecondaryIds(int type) {
        return type == 14 || type == 5;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public UniqueProgramIdentifier(android.hardware.radio.ProgramSelector r8) {
        /*
            r7 = this;
            r7.<init>()
            java.lang.String r0 = "Program selector can not be null"
            java.util.Objects.requireNonNull(r8, r0)
            android.hardware.radio.ProgramSelector$Identifier r0 = r8.getPrimaryId()
            r7.mPrimaryId = r0
            android.hardware.radio.ProgramSelector$Identifier r0 = r7.mPrimaryId
            int r0 = r0.getType()
            r1 = 0
            switch(r0) {
                case 5: goto L1d;
                case 14: goto L1d;
                default: goto L18;
            }
        L18:
            android.hardware.radio.ProgramSelector$Identifier[] r0 = new android.hardware.radio.ProgramSelector.Identifier[r1]
            r7.mCriticalSecondaryIds = r0
            goto L73
        L1d:
            r0 = 0
            r2 = 0
            android.hardware.radio.ProgramSelector$Identifier[] r3 = r8.getSecondaryIds()
            r4 = 0
        L24:
            int r5 = r3.length
            if (r4 >= r5) goto L4f
            if (r0 != 0) goto L39
            r5 = r3[r4]
            int r5 = r5.getType()
            r6 = 6
            if (r5 != r6) goto L39
            android.hardware.radio.ProgramSelector$Identifier[] r5 = r8.getSecondaryIds()
            r0 = r5[r4]
            goto L47
        L39:
            if (r2 != 0) goto L47
            r5 = r3[r4]
            int r5 = r5.getType()
            r6 = 8
            if (r5 != r6) goto L47
            r2 = r3[r4]
        L47:
            if (r0 == 0) goto L4c
            if (r2 == 0) goto L4c
            goto L4f
        L4c:
            int r4 = r4 + 1
            goto L24
        L4f:
            r4 = 1
            if (r0 != 0) goto L60
            if (r2 != 0) goto L59
            android.hardware.radio.ProgramSelector$Identifier[] r1 = new android.hardware.radio.ProgramSelector.Identifier[r1]
            r7.mCriticalSecondaryIds = r1
            goto L73
        L59:
            android.hardware.radio.ProgramSelector$Identifier[] r4 = new android.hardware.radio.ProgramSelector.Identifier[r4]
            r4[r1] = r2
            r7.mCriticalSecondaryIds = r4
            goto L73
        L60:
            if (r2 != 0) goto L69
            android.hardware.radio.ProgramSelector$Identifier[] r4 = new android.hardware.radio.ProgramSelector.Identifier[r4]
            r4[r1] = r0
            r7.mCriticalSecondaryIds = r4
            goto L73
        L69:
            r5 = 2
            android.hardware.radio.ProgramSelector$Identifier[] r5 = new android.hardware.radio.ProgramSelector.Identifier[r5]
            r5[r1] = r0
            r5[r4] = r2
            r7.mCriticalSecondaryIds = r5
        L73:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.hardware.radio.UniqueProgramIdentifier.<init>(android.hardware.radio.ProgramSelector):void");
    }

    public UniqueProgramIdentifier(ProgramSelector.Identifier primaryId) {
        this.mPrimaryId = primaryId;
        this.mCriticalSecondaryIds = new ProgramSelector.Identifier[0];
    }

    public ProgramSelector.Identifier getPrimaryId() {
        return this.mPrimaryId;
    }

    public List<ProgramSelector.Identifier> getCriticalSecondaryIds() {
        return List.of((Object[]) this.mCriticalSecondaryIds);
    }

    public String toString() {
        return "UniqueProgramIdentifier(primary="
                + this.mPrimaryId
                + ", criticalSecondary="
                + Arrays.toString(this.mCriticalSecondaryIds)
                + NavigationBarInflaterView.KEY_CODE_END;
    }

    public int hashCode() {
        return Objects.hash(
                this.mPrimaryId, Integer.valueOf(Arrays.hashCode(this.mCriticalSecondaryIds)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UniqueProgramIdentifier)) {
            return false;
        }
        UniqueProgramIdentifier other = (UniqueProgramIdentifier) obj;
        return other.mPrimaryId.equals(this.mPrimaryId)
                && Arrays.equals(other.mCriticalSecondaryIds, this.mCriticalSecondaryIds);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private UniqueProgramIdentifier(Parcel in) {
        this.mPrimaryId =
                (ProgramSelector.Identifier) in.readTypedObject(ProgramSelector.Identifier.CREATOR);
        this.mCriticalSecondaryIds =
                (ProgramSelector.Identifier[])
                        in.createTypedArray(ProgramSelector.Identifier.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mPrimaryId, 0);
        dest.writeTypedArray(this.mCriticalSecondaryIds, 0);
        if (Stream.of((Object[]) this.mCriticalSecondaryIds)
                .anyMatch(
                        new Predicate() { // from class:
                            // android.hardware.radio.UniqueProgramIdentifier$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return Objects.isNull((ProgramSelector.Identifier) obj);
                            }
                        })) {
            throw new IllegalArgumentException("criticalSecondaryIds list must not contain nulls");
        }
    }
}
