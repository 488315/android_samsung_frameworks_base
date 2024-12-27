package android.hardware.broadcastradio.V2_0;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.os.HidlSupport;

import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ProgramFilter {
    public boolean excludeModifications;
    public ArrayList identifierTypes;
    public ArrayList identifiers;
    public boolean includeCategories;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ProgramFilter.class) {
            return false;
        }
        ProgramFilter programFilter = (ProgramFilter) obj;
        return HidlSupport.deepEquals(this.identifierTypes, programFilter.identifierTypes)
                && HidlSupport.deepEquals(this.identifiers, programFilter.identifiers)
                && this.includeCategories == programFilter.includeCategories
                && this.excludeModifications == programFilter.excludeModifications;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(this.identifierTypes)),
                Integer.valueOf(HidlSupport.deepHashCode(this.identifiers)),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.includeCategories),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.excludeModifications));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.identifierTypes = ");
        sb.append(this.identifierTypes);
        sb.append(", .identifiers = ");
        sb.append(this.identifiers);
        sb.append(", .includeCategories = ");
        sb.append(this.includeCategories);
        sb.append(", .excludeModifications = ");
        return OptionalBool$$ExternalSyntheticOutline0.m("}", sb, this.excludeModifications);
    }
}
