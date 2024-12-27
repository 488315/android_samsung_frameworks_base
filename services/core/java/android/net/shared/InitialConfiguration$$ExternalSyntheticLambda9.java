package android.net.shared;

import java.net.InetAddress;
import java.util.function.Function;

public final /* synthetic */ class InitialConfiguration$$ExternalSyntheticLambda9
        implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ InitialConfiguration$$ExternalSyntheticLambda9(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return IpConfigurationParcelableUtil.unparcelAddress((String) obj);
            default:
                return IpConfigurationParcelableUtil.parcelAddress((InetAddress) obj);
        }
    }
}
