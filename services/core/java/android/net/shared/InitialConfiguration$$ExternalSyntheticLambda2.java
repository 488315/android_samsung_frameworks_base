package android.net.shared;

import android.net.IpPrefix;
import android.net.RouteInfo;
import java.net.InetAddress;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class InitialConfiguration$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ InitialConfiguration$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean lambda$isValid$1;
        boolean lambda$isProvisionedBy$3;
        boolean lambda$not$4;
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                lambda$isValid$1 = InitialConfiguration.lambda$isValid$1((InetAddress) obj2, (IpPrefix) obj);
                return lambda$isValid$1;
            case 1:
                lambda$isProvisionedBy$3 = InitialConfiguration.lambda$isProvisionedBy$3((IpPrefix) obj2, (RouteInfo) obj);
                return lambda$isProvisionedBy$3;
            default:
                lambda$not$4 = InitialConfiguration.lambda$not$4((Predicate) obj2, obj);
                return lambda$not$4;
        }
    }
}
