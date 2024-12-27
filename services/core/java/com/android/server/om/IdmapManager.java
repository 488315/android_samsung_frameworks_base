package com.android.server.om;

import android.content.om.OverlayInfo;
import android.os.IIdmap2;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.om.IdmapDaemon;
import com.android.server.om.OverlayManagerService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class IdmapManager {
    public static final boolean VENDOR_IS_Q_OR_LATER;
    public final String mConfigSignaturePackage;
    public final IdmapDaemon mIdmapDaemon;
    public final OverlayManagerService.PackageManagerHelperImpl mPackageManager;

    static {
        boolean z = true;
        try {
            if (Integer.parseInt(SystemProperties.get("ro.vndk.version", "29")) < 29) {
                z = false;
            }
        } catch (NumberFormatException unused) {
        }
        VENDOR_IS_Q_OR_LATER = z;
    }

    public IdmapManager(IdmapDaemon idmapDaemon, OverlayManagerService.PackageManagerHelperImpl packageManagerHelperImpl) {
        this.mPackageManager = packageManagerHelperImpl;
        this.mIdmapDaemon = idmapDaemon;
        String[] knownPackageNames = packageManagerHelperImpl.mPackageManagerInternal.getKnownPackageNames(13, 0);
        this.mConfigSignaturePackage = knownPackageNames.length == 0 ? null : knownPackageNames[0];
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int calculateFulfilledPolicies(com.android.server.pm.pkg.AndroidPackage r5, com.android.server.pm.pkg.PackageState r6, com.android.server.pm.pkg.AndroidPackage r7, int r8) {
        /*
            r4 = this;
            java.lang.String r0 = r5.getPackageName()
            java.lang.String r1 = r7.getPackageName()
            com.android.server.om.OverlayManagerService$PackageManagerHelperImpl r2 = r4.mPackageManager
            r2.getClass()
            android.content.pm.IPackageManager r3 = r2.mPackageManager     // Catch: android.os.RemoteException -> L18
            int r0 = r3.checkSignatures(r0, r1, r8)     // Catch: android.os.RemoteException -> L18
            if (r0 != 0) goto L18
            r0 = 17
            goto L19
        L18:
            r0 = 1
        L19:
            java.lang.String r1 = r7.getOverlayTargetOverlayableName()
            if (r1 == 0) goto L63
            com.android.server.SystemConfig r3 = com.android.server.SystemConfig.getInstance()
            java.util.Map r3 = r3.mNamedActors
            if (r3 == 0) goto L28
            goto L2c
        L28:
            java.util.Map r3 = java.util.Collections.emptyMap()
        L2c:
            boolean r3 = r3.isEmpty()
            if (r3 != 0) goto L63
            java.lang.String r5 = r5.getPackageName()     // Catch: java.lang.Throwable -> L63
            android.content.om.OverlayableInfo r5 = r2.getOverlayableForTarget(r8, r5, r1)     // Catch: java.lang.Throwable -> L63
            if (r5 == 0) goto L63
            java.lang.String r5 = r5.actor     // Catch: java.lang.Throwable -> L63
            if (r5 == 0) goto L63
            com.android.server.SystemConfig r1 = com.android.server.SystemConfig.getInstance()     // Catch: java.lang.Throwable -> L63
            java.util.Map r1 = r1.mNamedActors     // Catch: java.lang.Throwable -> L63
            if (r1 == 0) goto L49
            goto L4d
        L49:
            java.util.Map r1 = java.util.Collections.emptyMap()     // Catch: java.lang.Throwable -> L63
        L4d:
            android.util.Pair r5 = com.android.server.om.OverlayActorEnforcer.getPackageNameForActor(r5, r1)     // Catch: java.lang.Throwable -> L63
            java.lang.Object r5 = r5.first     // Catch: java.lang.Throwable -> L63
            java.lang.String r5 = (java.lang.String) r5     // Catch: java.lang.Throwable -> L63
            java.lang.String r1 = r7.getPackageName()     // Catch: java.lang.Throwable -> L63
            android.content.pm.IPackageManager r3 = r2.mPackageManager     // Catch: java.lang.Throwable -> L63
            int r5 = r3.checkSignatures(r5, r1, r8)     // Catch: java.lang.Throwable -> L63
            if (r5 != 0) goto L63
            r0 = r0 | 128(0x80, float:1.794E-43)
        L63:
            java.lang.String r4 = r4.mConfigSignaturePackage
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 != 0) goto L79
            java.lang.String r5 = r7.getPackageName()
            android.content.pm.IPackageManager r7 = r2.mPackageManager     // Catch: android.os.RemoteException -> L79
            int r4 = r7.checkSignatures(r4, r5, r8)     // Catch: android.os.RemoteException -> L79
            if (r4 != 0) goto L79
            r0 = r0 | 256(0x100, float:3.59E-43)
        L79:
            boolean r4 = r6.isVendor()
            if (r4 == 0) goto L82
            r4 = r0 | 4
            return r4
        L82:
            boolean r4 = r6.isProduct()
            if (r4 == 0) goto L8b
            r4 = r0 | 8
            return r4
        L8b:
            boolean r4 = r6.isOdm()
            if (r4 == 0) goto L94
            r4 = r0 | 32
            return r4
        L94:
            boolean r4 = r6.isOem()
            if (r4 == 0) goto L9d
            r4 = r0 | 64
            return r4
        L9d:
            boolean r4 = r6.isSystem()
            if (r4 != 0) goto Lab
            boolean r4 = r6.isSystemExt()
            if (r4 == 0) goto Laa
            goto Lab
        Laa:
            return r0
        Lab:
            r4 = r0 | 2
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.om.IdmapManager.calculateFulfilledPolicies(com.android.server.pm.pkg.AndroidPackage, com.android.server.pm.pkg.PackageState, com.android.server.pm.pkg.AndroidPackage, int):int");
    }

    public final List getFabricatedOverlayInfos() {
        ArrayList arrayList;
        int i;
        IIdmap2 iIdmap2;
        IdmapDaemon idmapDaemon = this.mIdmapDaemon;
        synchronized (idmapDaemon) {
            arrayList = new ArrayList();
            IdmapDaemon.Connection connection = null;
            try {
                connection = idmapDaemon.connect();
                IIdmap2 iIdmap22 = connection.mIdmap2;
                if (iIdmap22 == null) {
                    Slog.w("OverlayManager", "idmap2d service is not ready for getFabricatedOverlayInfos()");
                    ?? emptyList = Collections.emptyList();
                    connection.close();
                    arrayList = emptyList;
                } else {
                    i = iIdmap22.acquireFabricatedOverlayIterator();
                    while (true) {
                        try {
                            try {
                                List nextFabricatedOverlayInfos = iIdmap22.nextFabricatedOverlayInfos(i);
                                if (nextFabricatedOverlayInfos.isEmpty()) {
                                    try {
                                        break;
                                    } catch (RemoteException unused) {
                                    }
                                } else {
                                    arrayList.addAll(nextFabricatedOverlayInfos);
                                }
                            } catch (Throwable th) {
                                th = th;
                                try {
                                    iIdmap2 = connection.mIdmap2;
                                    if (iIdmap2 != null && i != -1) {
                                        iIdmap2.releaseFabricatedOverlayIterator(i);
                                    }
                                } catch (RemoteException unused2) {
                                }
                                connection.close();
                                throw th;
                            }
                        } catch (Exception e) {
                            e = e;
                            Slog.wtf("OverlayManager", "failed to get all fabricated overlays", e);
                            try {
                                IIdmap2 iIdmap23 = connection.mIdmap2;
                                if (iIdmap23 != null && i != -1) {
                                    iIdmap23.releaseFabricatedOverlayIterator(i);
                                }
                            } catch (RemoteException unused3) {
                            }
                            connection.close();
                            return arrayList;
                        }
                    }
                    IIdmap2 iIdmap24 = connection.mIdmap2;
                    if (iIdmap24 != null && i != -1) {
                        iIdmap24.releaseFabricatedOverlayIterator(i);
                    }
                    connection.close();
                    arrayList = arrayList;
                }
            } catch (Exception e2) {
                e = e2;
                i = -1;
            } catch (Throwable th2) {
                th = th2;
                i = -1;
                iIdmap2 = connection.mIdmap2;
                if (iIdmap2 != null) {
                    iIdmap2.releaseFabricatedOverlayIterator(i);
                }
                connection.close();
                throw th;
            }
        }
        return arrayList;
    }

    public final boolean removeIdmap(OverlayInfo overlayInfo, int i) {
        BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("remove idmap for "), overlayInfo.baseCodePath, "OverlayManager");
        try {
            return this.mIdmapDaemon.removeIdmap(i, overlayInfo.baseCodePath);
        } catch (Exception e) {
            Slog.w("OverlayManager", "failed to remove idmap for " + overlayInfo.baseCodePath, e);
            return false;
        }
    }
}
