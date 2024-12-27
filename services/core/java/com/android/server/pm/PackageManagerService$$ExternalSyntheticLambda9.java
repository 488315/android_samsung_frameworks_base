package com.android.server.pm;

import android.R;
import android.app.backup.IBackupManager;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.os.IBinder;
import android.os.ServiceManager;
import android.os.incremental.IncrementalManager;
import android.util.DisplayMetrics;
import com.android.internal.pm.parsing.IPackageCacher;
import com.android.internal.pm.parsing.PackageParser2;
import com.android.internal.util.function.TriConsumer;
import com.android.server.LocalServices;
import com.android.server.SystemConfig;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.compat.PlatformCompat;
import com.android.server.pm.AppsFilterImpl;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerServiceInjector;
import com.android.server.pm.dex.ArtManagerService;
import com.android.server.pm.dex.DexManager;
import com.android.server.pm.dex.DynamicCodeLogger;
import com.android.server.pm.parsing.PackageCacher;
import com.android.server.pm.permission.LegacyPermissionManagerService;
import com.android.server.pm.resolution.ComponentResolver;
import com.samsung.android.server.pm.MetaDataHelper;
import com.samsung.android.server.pm.install.MultiUserInstallPolicy;
import com.samsung.android.server.pm.lifecycle.PmCustomInjector;
import java.io.File;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda9 implements PackageManagerServiceInjector.Producer, PmCustomInjector.Producer, PackageManagerServiceInjector.ServiceProducer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ PackageManagerService$$ExternalSyntheticLambda9(int i) {
        this.$r8$classId = i;
    }

    /* JADX WARN: Type inference failed for: r11v10, types: [android.os.IBinder, com.android.server.pm.permission.LegacyPermissionManagerService] */
    /* JADX WARN: Type inference failed for: r11v19, types: [com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda68] */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda68] */
    /* JADX WARN: Type inference failed for: r2v8, types: [com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda68] */
    @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
    public Object produce(PackageManagerService packageManagerService, PackageManagerServiceInjector packageManagerServiceInjector) {
        String[] stringArray;
        final int i = 0;
        final int i2 = 1;
        switch (this.$r8$classId) {
            case 0:
                return new ComponentResolver(packageManagerServiceInjector.getUserManagerService(), packageManagerService.mUserNeedsBadging);
            case 1:
                return new ArtManagerService(packageManagerServiceInjector.mContext);
            case 2:
                return ApexManager.getInstance();
            case 3:
                return (IncrementalManager) packageManagerServiceInjector.mContext.getSystemService("incremental");
            case 4:
                return new DisplayMetrics();
            case 5:
                String[] strArr = packageManagerService.mSeparateProcesses;
                DisplayMetrics displayMetrics = (DisplayMetrics) packageManagerServiceInjector.mDisplayMetricsProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
                File file = packageManagerService.mCacheDir;
                PackageManagerService.AnonymousClass3 anonymousClass3 = packageManagerService.mPackageParserCallback;
                return new PackageParser2(strArr, displayMetrics, new PackageCacher(file, anonymousClass3), anonymousClass3);
            case 6:
                return new PackageParser2(packageManagerService.mSeparateProcesses, (DisplayMetrics) packageManagerServiceInjector.mDisplayMetricsProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector), (IPackageCacher) null, packageManagerService.mPackageParserCallback);
            case 7:
                return new PackageParser2(packageManagerService.mSeparateProcesses, (DisplayMetrics) packageManagerServiceInjector.mDisplayMetricsProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector), (IPackageCacher) null, packageManagerService.mPackageParserCallback);
            case 8:
                return new PackageInstallerService(packageManagerServiceInjector.mContext, packageManagerService, new PackageManagerService$$ExternalSyntheticLambda55(0, packageManagerServiceInjector));
            case 9:
                return new ModuleInfoProvider(packageManagerServiceInjector.mContext);
            case 10:
                Context context = packageManagerServiceInjector.mContext;
                int i3 = LegacyPermissionManagerService.$r8$clinit;
                LegacyPermissionManagerService.Internal internal = (LegacyPermissionManagerService.Internal) LocalServices.getService(LegacyPermissionManagerService.Internal.class);
                if (internal != null) {
                    return internal;
                }
                ?? legacyPermissionManagerService = new LegacyPermissionManagerService(context, new LegacyPermissionManagerService.Injector(context));
                LocalServices.addService(LegacyPermissionManagerService.Internal.class, new LegacyPermissionManagerService.Internal());
                ServiceManager.addService("legacy_permission", (IBinder) legacyPermissionManagerService);
                return (LegacyPermissionManagerService.Internal) LocalServices.getService(LegacyPermissionManagerService.Internal.class);
            case 11:
                return new PackageHandler(Watchdog$$ExternalSyntheticOutline0.m(0, "PackageManager", true).getLooper(), packageManagerService);
            case 12:
                return IBackupManager.Stub.asInterface(ServiceManager.getService("backup"));
            case 13:
                return new SharedLibrariesImpl(packageManagerService, packageManagerServiceInjector);
            case 14:
                return new UpdateOwnershipHelper();
            case 15:
                return new PackageMonitorCallbackHelper();
            case 16:
                PackageManagerInternal packageManagerInternal = (PackageManagerInternal) packageManagerServiceInjector.mGetLocalServiceProducer.produce(PackageManagerInternal.class);
                boolean z = packageManagerServiceInjector.mContext.getResources().getBoolean(R.bool.config_honor_data_retry_timer_for_emergency_network);
                AppsFilterImpl.FeatureConfigImpl featureConfigImpl = new AppsFilterImpl.FeatureConfigImpl(packageManagerInternal, packageManagerServiceInjector);
                if (z) {
                    stringArray = new String[0];
                } else {
                    stringArray = packageManagerServiceInjector.mContext.getResources().getStringArray(R.array.required_apps_managed_device);
                    while (i < stringArray.length) {
                        stringArray[i] = stringArray[i].intern();
                        i++;
                    }
                }
                AppsFilterImpl appsFilterImpl = new AppsFilterImpl(featureConfigImpl, stringArray, z, null, packageManagerServiceInjector.getHandler());
                featureConfigImpl.mAppsFilter = appsFilterImpl;
                return appsFilterImpl;
            case 17:
                return (PlatformCompat) ServiceManager.getService("platform_compat");
            case 18:
                return SystemConfig.getInstance();
            case 19:
                return new PackageDexOptimizer(packageManagerServiceInjector.mInstaller, packageManagerServiceInjector.mInstallLock, packageManagerServiceInjector.mContext, "*dexopt*");
            case 20:
                return new DexManager(packageManagerServiceInjector.mContext, (PackageDexOptimizer) packageManagerServiceInjector.mPackageDexOptimizerProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector), (DynamicCodeLogger) packageManagerServiceInjector.mDynamicCodeLoggerProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector), null);
            case 21:
                return new DynamicCodeLogger(packageManagerServiceInjector.mInstaller);
            default:
                Settings settings = (Settings) packageManagerServiceInjector.mSettingsProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
                Objects.requireNonNull(settings);
                final int i4 = 2;
                return new MultiUserInstallPolicy(new MultiUserInstallPolicy.PackageSettingsDelegator(new PackageManagerService$$ExternalSyntheticLambda55(1, settings), new TriConsumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda68
                    public final void accept(Object obj, Object obj2, Object obj3) {
                        PackageSetting packageSetting = (PackageSetting) obj;
                        switch (i) {
                            case 0:
                                packageSetting.setInstalled(((Integer) obj3).intValue(), ((Boolean) obj2).booleanValue());
                                break;
                            case 1:
                                packageSetting.setEnabled(((Integer) obj2).intValue(), ((Integer) obj3).intValue(), null);
                                break;
                            default:
                                packageSetting.modifyUserStateComponents(((Integer) obj3).intValue(), true, false).mDisabledComponentsWatched.add((String) obj2);
                                packageSetting.onChanged$2();
                                break;
                        }
                    }
                }, new TriConsumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda68
                    public final void accept(Object obj, Object obj2, Object obj3) {
                        PackageSetting packageSetting = (PackageSetting) obj;
                        switch (i2) {
                            case 0:
                                packageSetting.setInstalled(((Integer) obj3).intValue(), ((Boolean) obj2).booleanValue());
                                break;
                            case 1:
                                packageSetting.setEnabled(((Integer) obj2).intValue(), ((Integer) obj3).intValue(), null);
                                break;
                            default:
                                packageSetting.modifyUserStateComponents(((Integer) obj3).intValue(), true, false).mDisabledComponentsWatched.add((String) obj2);
                                packageSetting.onChanged$2();
                                break;
                        }
                    }
                }, new TriConsumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda68
                    public final void accept(Object obj, Object obj2, Object obj3) {
                        PackageSetting packageSetting = (PackageSetting) obj;
                        switch (i4) {
                            case 0:
                                packageSetting.setInstalled(((Integer) obj3).intValue(), ((Boolean) obj2).booleanValue());
                                break;
                            case 1:
                                packageSetting.setEnabled(((Integer) obj2).intValue(), ((Integer) obj3).intValue(), null);
                                break;
                            default:
                                packageSetting.modifyUserStateComponents(((Integer) obj3).intValue(), true, false).mDisabledComponentsWatched.add((String) obj2);
                                packageSetting.onChanged$2();
                                break;
                        }
                    }
                }), new MetaDataHelper(), UserManagerService.getInstance());
        }
    }

    @Override // com.android.server.pm.PackageManagerServiceInjector.ServiceProducer
    public Object produce(Class cls) {
        return LocalServices.getService(cls);
    }
}
