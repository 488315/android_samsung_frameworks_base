package com.android.server.om;

import android.content.om.ISamsungOverlayCallback;
import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.content.om.OverlayInfoExt;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.FgThread;
import com.android.server.om.OverlayManagerSettings;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knoxguard.service.utils.Constants;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class OverlayManagerServiceExt$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OverlayManagerServiceExt f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ ISamsungOverlayCallback f$2;

    public /* synthetic */ OverlayManagerServiceExt$$ExternalSyntheticLambda0(OverlayManagerServiceExt overlayManagerServiceExt, int i, ISamsungOverlayCallback iSamsungOverlayCallback, int i2) {
        this.$r8$classId = i2;
        this.f$0 = overlayManagerServiceExt;
        this.f$1 = i;
        this.f$2 = iSamsungOverlayCallback;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        boolean z;
        boolean z2;
        int[] iArr;
        int i;
        switch (this.$r8$classId) {
            case 0:
                final OverlayManagerServiceExt overlayManagerServiceExt = this.f$0;
                int i2 = this.f$1;
                final ISamsungOverlayCallback iSamsungOverlayCallback = this.f$2;
                final OverlayInfoExt overlayInfoExt = (OverlayInfoExt) obj;
                overlayManagerServiceExt.getClass();
                if (overlayInfoExt == null || overlayInfoExt.info == null) {
                    return null;
                }
                int[] userIds = overlayManagerServiceExt.getUserIds(overlayInfoExt.configFlags, i2);
                boolean z3 = true;
                int length = userIds.length - 1;
                while (length >= 0) {
                    int i3 = userIds[length];
                    PackageState packageStateForUser = overlayManagerServiceExt.mPackageManager.packageManagerHelper.getPackageStateForUser(i3, overlayInfoExt.info.targetPackageName);
                    AndroidPackage androidPackage = packageStateForUser == null ? null : packageStateForUser.getAndroidPackage();
                    if (i3 == i2 || !Constants.SYSTEMUI_PACKAGE_NAME.equals(overlayInfoExt.info.targetPackageName)) {
                        OverlayIdentifier overlayIdentifier = overlayInfoExt.getOverlayIdentifier();
                        int i4 = overlayInfoExt.configFlags & 2048;
                        OverlayManagerSettings overlayManagerSettings = overlayManagerServiceExt.mSettings;
                        if (i4 != 0) {
                            try {
                                z = overlayManagerSettings.getEnabled(overlayIdentifier, i3);
                            } catch (OverlayManagerSettings.BadKeyException unused) {
                                z = false;
                            }
                            overlayManagerSettings.remove(overlayIdentifier, i3);
                            z2 = z;
                        } else {
                            z2 = z3;
                        }
                        OverlayInfo overlayInfo = overlayInfoExt.info;
                        String str = overlayInfo.targetPackageName;
                        String str2 = overlayInfo.targetOverlayableName;
                        String str3 = overlayInfo.baseCodePath;
                        int i5 = overlayInfo.priority;
                        String str4 = overlayInfo.category;
                        overlayManagerSettings.remove(overlayIdentifier, i3);
                        iArr = userIds;
                        i = i2;
                        AndroidPackage androidPackage2 = androidPackage;
                        OverlayManagerSettings.SettingsItem settingsItem = new OverlayManagerSettings.SettingsItem(overlayIdentifier, i3, str, str2, str3, -1, z2, true, i5, str4, false);
                        overlayManagerSettings.insert(settingsItem);
                        OverlayManagerSettings.SettingsItem.m739$$Nest$mgetOverlayInfo(settingsItem);
                        overlayManagerServiceExt.updateOverlayState(androidPackage2, overlayInfoExt, i3, androidPackage2 != null ? z2 ? 3 : 2 : 0);
                    } else {
                        Slog.i("OverlayManagerExt", "skip to update overlay : " + overlayInfoExt.info.packageName + ", for " + i3);
                        i = i2;
                        iArr = userIds;
                    }
                    length--;
                    i2 = i;
                    userIds = iArr;
                    z3 = true;
                }
                final int i6 = i2;
                if (iSamsungOverlayCallback != null) {
                    FgThread.getHandler().post(new Runnable() { // from class: com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            int i7;
                            OverlayManagerServiceExt overlayManagerServiceExt2 = OverlayManagerServiceExt.this;
                            OverlayInfoExt overlayInfoExt2 = overlayInfoExt;
                            int i8 = i6;
                            ISamsungOverlayCallback iSamsungOverlayCallback2 = iSamsungOverlayCallback;
                            overlayManagerServiceExt2.getClass();
                            try {
                                i7 = overlayManagerServiceExt2.mSettings.getState(overlayInfoExt2.getOverlayIdentifier(), i8);
                            } catch (OverlayManagerSettings.BadKeyException unused2) {
                                i7 = -1;
                            }
                            try {
                                OverlayInfo overlayInfo2 = overlayInfoExt2.info;
                                iSamsungOverlayCallback2.onOverlayStateChanged(overlayInfo2.baseCodePath, overlayInfo2.packageName, i7);
                            } catch (RemoteException unused3) {
                            }
                        }
                    });
                }
                return overlayInfoExt.info.targetPackageName;
            default:
                final OverlayManagerServiceExt overlayManagerServiceExt2 = this.f$0;
                final int i7 = this.f$1;
                final ISamsungOverlayCallback iSamsungOverlayCallback2 = this.f$2;
                final OverlayInfoExt overlayInfoExt2 = (OverlayInfoExt) obj;
                overlayManagerServiceExt2.getClass();
                if (overlayInfoExt2 == null || overlayInfoExt2.info == null) {
                    return null;
                }
                int[] userIds2 = overlayManagerServiceExt2.getUserIds(overlayInfoExt2.configFlags, i7);
                for (int length2 = userIds2.length - 1; length2 >= 0; length2--) {
                    int i8 = userIds2[length2];
                    overlayManagerServiceExt2.mSettings.remove(overlayInfoExt2.info.getOverlayIdentifier(), i8);
                    boolean z4 = (overlayInfoExt2.configFlags & 256) != 0;
                    if (SemDualAppManager.isDualAppId(i8) && z4) {
                        Slog.d("OverlayManagerExt", "Skip deleting idmap for dual app");
                    } else {
                        overlayManagerServiceExt2.removeIdmap(overlayInfoExt2);
                    }
                }
                if (iSamsungOverlayCallback2 != null) {
                    FgThread.getHandler().post(new Runnable() { // from class: com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            int i72;
                            OverlayManagerServiceExt overlayManagerServiceExt22 = OverlayManagerServiceExt.this;
                            OverlayInfoExt overlayInfoExt22 = overlayInfoExt2;
                            int i82 = i7;
                            ISamsungOverlayCallback iSamsungOverlayCallback22 = iSamsungOverlayCallback2;
                            overlayManagerServiceExt22.getClass();
                            try {
                                i72 = overlayManagerServiceExt22.mSettings.getState(overlayInfoExt22.getOverlayIdentifier(), i82);
                            } catch (OverlayManagerSettings.BadKeyException unused2) {
                                i72 = -1;
                            }
                            try {
                                OverlayInfo overlayInfo2 = overlayInfoExt22.info;
                                iSamsungOverlayCallback22.onOverlayStateChanged(overlayInfo2.baseCodePath, overlayInfo2.packageName, i72);
                            } catch (RemoteException unused3) {
                            }
                        }
                    });
                }
                return overlayInfoExt2.info.targetPackageName;
        }
    }
}
