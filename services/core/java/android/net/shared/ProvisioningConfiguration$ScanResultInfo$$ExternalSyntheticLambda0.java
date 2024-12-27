package android.net.shared;

import android.net.InformationElementParcelable;

import java.util.function.Function;

public final /* synthetic */
class ProvisioningConfiguration$ScanResultInfo$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ProvisioningConfiguration.ScanResultInfo.InformationElement
                        .fromStableParcelable((InformationElementParcelable) obj);
            default:
                return ((ProvisioningConfiguration.ScanResultInfo.InformationElement) obj)
                        .toStableParcelable();
        }
    }
}
