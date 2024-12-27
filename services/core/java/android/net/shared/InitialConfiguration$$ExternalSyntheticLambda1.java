package android.net.shared;

import android.net.IpPrefix;
import android.net.LinkAddress;

import java.util.function.Predicate;

public final /* synthetic */ class InitialConfiguration$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LinkAddress f$0;

    public /* synthetic */ InitialConfiguration$$ExternalSyntheticLambda1(
            LinkAddress linkAddress, int i) {
        this.$r8$classId = i;
        this.f$0 = linkAddress;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean lambda$isValid$0;
        boolean isSameAddressAs;
        int i = this.$r8$classId;
        LinkAddress linkAddress = this.f$0;
        switch (i) {
            case 0:
                lambda$isValid$0 =
                        InitialConfiguration.lambda$isValid$0(linkAddress, (IpPrefix) obj);
                return lambda$isValid$0;
            default:
                isSameAddressAs = linkAddress.isSameAddressAs((LinkAddress) obj);
                return isSameAddressAs;
        }
    }
}
