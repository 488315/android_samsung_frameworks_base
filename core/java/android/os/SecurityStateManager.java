package android.os;

import java.util.Objects;

/* loaded from: classes3.dex */
public class SecurityStateManager {
    public static final String KEY_KERNEL_VERSION = "kernel_version";
    public static final String KEY_SYSTEM_SPL = "system_spl";
    public static final String KEY_VENDOR_SPL = "vendor_spl";
    private final ISecurityStateManager mService;

    public SecurityStateManager(ISecurityStateManager service) {
        this.mService = (ISecurityStateManager) Objects.requireNonNull(service, "missing ISecurityStateManager");
    }

    public Bundle getGlobalSecurityState() {
        try {
            return this.mService.getGlobalSecurityState();
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }
}
