package android.net.shared;

import java.net.InetAddress;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
