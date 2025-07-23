package android.security;

/* loaded from: classes3.dex */
public class OverlayNetworkSecurityPolicy extends libcore.net.NetworkSecurityPolicy {
    private final boolean mCleartextTrafficPermitted;
    private final libcore.net.NetworkSecurityPolicy mParent;

    public OverlayNetworkSecurityPolicy(libcore.net.NetworkSecurityPolicy networkSecurityPolicy, boolean z) {
        this.mParent = networkSecurityPolicy;
        this.mCleartextTrafficPermitted = z;
    }

    public boolean isCleartextTrafficPermitted() {
        return this.mCleartextTrafficPermitted;
    }

    public boolean isCleartextTrafficPermitted(String str) {
        return isCleartextTrafficPermitted();
    }

    public boolean isCertificateTransparencyVerificationRequired(String str) {
        return this.mParent.isCertificateTransparencyVerificationRequired(str);
    }
}
