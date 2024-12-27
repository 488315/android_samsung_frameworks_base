package com.android.server.pm;

import android.content.pm.PackageInfo;
import android.os.UserHandle;

import com.android.internal.util.FunctionalUtils;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;

import java.util.ArrayList;

public final /* synthetic */ class CrossProfileAppsServiceImpl$$ExternalSyntheticLambda3
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CrossProfileAppsServiceImpl f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ CrossProfileAppsServiceImpl$$ExternalSyntheticLambda3(
            int i, int i2, CrossProfileAppsServiceImpl crossProfileAppsServiceImpl, String str) {
        this.$r8$classId = i2;
        this.f$0 = crossProfileAppsServiceImpl;
        this.f$2 = i;
        this.f$1 = str;
    }

    public /* synthetic */ CrossProfileAppsServiceImpl$$ExternalSyntheticLambda3(
            CrossProfileAppsServiceImpl crossProfileAppsServiceImpl, String str, int i) {
        this.$r8$classId = 0;
        this.f$0 = crossProfileAppsServiceImpl;
        this.f$1 = str;
        this.f$2 = i;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                CrossProfileAppsServiceImpl crossProfileAppsServiceImpl = this.f$0;
                return Boolean.valueOf(
                        crossProfileAppsServiceImpl
                                        .mInjector
                                        .getPackageManagerInternal()
                                        .getPackageInfo(
                                                crossProfileAppsServiceImpl.mInjector
                                                        .getCallingUid(),
                                                this.f$2,
                                                786432L,
                                                this.f$1)
                                != null);
            case 1:
                return Boolean.valueOf(
                        this.f$0
                                .mInjector
                                .getDevicePolicyManagerInternal()
                                .getAllCrossProfilePackages(this.f$2)
                                .contains(this.f$1));
            case 2:
                CrossProfileAppsServiceImpl crossProfileAppsServiceImpl2 = this.f$0;
                int i = this.f$2;
                String str = this.f$1;
                for (int i2 :
                        crossProfileAppsServiceImpl2
                                .mInjector
                                .getUserManager()
                                .getProfileIdsExcludingHidden(i, false)) {
                    if (i2 != i
                            && ((Boolean)
                                            crossProfileAppsServiceImpl2.mInjector
                                                    .withCleanCallingIdentity(
                                                            new CrossProfileAppsServiceImpl$$ExternalSyntheticLambda3(
                                                                    crossProfileAppsServiceImpl2,
                                                                    str,
                                                                    i2)))
                                    .booleanValue()) {
                        return Boolean.TRUE;
                    }
                }
                return Boolean.FALSE;
            default:
                final CrossProfileAppsServiceImpl crossProfileAppsServiceImpl3 = this.f$0;
                int i3 = this.f$2;
                final String str2 = this.f$1;
                int[] profileIdsExcludingHidden =
                        crossProfileAppsServiceImpl3
                                .mInjector
                                .getUserManager()
                                .getProfileIdsExcludingHidden(i3, true);
                ArrayList arrayList = new ArrayList();
                for (final int i4 : profileIdsExcludingHidden) {
                    if (i4 != i3) {
                        final int callingUid =
                                crossProfileAppsServiceImpl3.mInjector.getCallingUid();
                        if (((Boolean)
                                                crossProfileAppsServiceImpl3.mInjector
                                                        .withCleanCallingIdentity(
                                                                new FunctionalUtils
                                                                        .ThrowingSupplier() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda16
                                                                    public final Object
                                                                            getOrThrow() {
                                                                        CrossProfileAppsServiceImpl
                                                                                crossProfileAppsServiceImpl4 =
                                                                                        crossProfileAppsServiceImpl3;
                                                                        PackageInfo packageInfo =
                                                                                crossProfileAppsServiceImpl4
                                                                                        .mInjector
                                                                                        .getPackageManagerInternal()
                                                                                        .getPackageInfo(
                                                                                                callingUid,
                                                                                                i4,
                                                                                                786432L,
                                                                                                str2);
                                                                        return Boolean.valueOf(
                                                                                packageInfo != null
                                                                                        && packageInfo
                                                                                                .applicationInfo
                                                                                                .enabled);
                                                                    }
                                                                }))
                                        .booleanValue()
                                && !SemPersonaManager.isSecureFolderId(i4)
                                && !SemDualAppManager.isDualAppId(i4)) {
                            arrayList.add(UserHandle.of(i4));
                        }
                    }
                }
                return arrayList;
        }
    }
}
