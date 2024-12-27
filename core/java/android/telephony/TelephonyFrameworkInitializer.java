package android.telephony;

import android.app.SystemServiceRegistry;
import android.compat.Compatibility;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemProperties;
import android.os.TelephonyServiceManager;
import android.telephony.euicc.EuiccCardManager;
import android.telephony.euicc.EuiccManager;
import android.telephony.ims.ImsManager;
import android.telephony.satellite.SatelliteManager;

import com.android.internal.telephony.flags.Flags;
import com.android.internal.util.Preconditions;

public class TelephonyFrameworkInitializer {
    static final long ENABLE_CHECKING_TELEPHONY_FEATURES = 330583731;
    private static volatile TelephonyServiceManager sTelephonyServiceManager;

    private TelephonyFrameworkInitializer() {}

    public static void setTelephonyServiceManager(TelephonyServiceManager telephonyServiceManager) {
        Preconditions.checkState(
                sTelephonyServiceManager == null, "setTelephonyServiceManager called twice!");
        sTelephonyServiceManager =
                (TelephonyServiceManager) Preconditions.checkNotNull(telephonyServiceManager);
    }

    private static boolean hasSystemFeature(Context context, String feature) {
        if (!Flags.minimalTelephonyManagersConditionalOnFeatures()) {
            return true;
        }
        int vendorApiLevel =
                SystemProperties.getInt(
                        "ro.vendor.api_level", Build.VERSION.DEVICE_INITIAL_SDK_INT);
        if (vendorApiLevel >= 202404
                && Compatibility.isChangeEnabled(ENABLE_CHECKING_TELEPHONY_FEATURES)) {
            return context.getPackageManager().hasSystemFeature(feature);
        }
        return true;
    }

    public static void registerServiceWrappers() {
        com.android.telephony.Rlog.d(
                "TelephonyFrameworkInitializer", "registerServiceWrappers start");
        SystemServiceRegistry.registerContextAwareService(
                "phone",
                TelephonyManager.class,
                new SystemServiceRegistry
                        .ContextAwareServiceProducerWithoutBinder() { // from class:
                                                                      // android.telephony.TelephonyFrameworkInitializer$$ExternalSyntheticLambda0
                    @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
                    public final Object createService(Context context) {
                        return TelephonyFrameworkInitializer.lambda$registerServiceWrappers$0(
                                context);
                    }
                });
        SystemServiceRegistry.registerContextAwareService(
                Context.TELEPHONY_SUBSCRIPTION_SERVICE,
                SubscriptionManager.class,
                new SystemServiceRegistry
                        .ContextAwareServiceProducerWithoutBinder() { // from class:
                                                                      // android.telephony.TelephonyFrameworkInitializer$$ExternalSyntheticLambda1
                    @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
                    public final Object createService(Context context) {
                        return TelephonyFrameworkInitializer.lambda$registerServiceWrappers$1(
                                context);
                    }
                });
        SystemServiceRegistry.registerContextAwareService(
                "carrier_config",
                CarrierConfigManager.class,
                new SystemServiceRegistry
                        .ContextAwareServiceProducerWithoutBinder() { // from class:
                                                                      // android.telephony.TelephonyFrameworkInitializer$$ExternalSyntheticLambda2
                    @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
                    public final Object createService(Context context) {
                        return TelephonyFrameworkInitializer.lambda$registerServiceWrappers$2(
                                context);
                    }
                });
        SystemServiceRegistry.registerContextAwareService(
                Context.EUICC_SERVICE,
                EuiccManager.class,
                new SystemServiceRegistry
                        .ContextAwareServiceProducerWithoutBinder() { // from class:
                                                                      // android.telephony.TelephonyFrameworkInitializer$$ExternalSyntheticLambda3
                    @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
                    public final Object createService(Context context) {
                        return TelephonyFrameworkInitializer.lambda$registerServiceWrappers$3(
                                context);
                    }
                });
        SystemServiceRegistry.registerContextAwareService(
                Context.EUICC_CARD_SERVICE,
                EuiccCardManager.class,
                new SystemServiceRegistry
                        .ContextAwareServiceProducerWithoutBinder() { // from class:
                                                                      // android.telephony.TelephonyFrameworkInitializer$$ExternalSyntheticLambda4
                    @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
                    public final Object createService(Context context) {
                        return TelephonyFrameworkInitializer.lambda$registerServiceWrappers$4(
                                context);
                    }
                });
        SystemServiceRegistry.registerContextAwareService(
                Context.TELEPHONY_IMS_SERVICE,
                ImsManager.class,
                new SystemServiceRegistry
                        .ContextAwareServiceProducerWithoutBinder() { // from class:
                                                                      // android.telephony.TelephonyFrameworkInitializer$$ExternalSyntheticLambda5
                    @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
                    public final Object createService(Context context) {
                        return TelephonyFrameworkInitializer.lambda$registerServiceWrappers$5(
                                context);
                    }
                });
        SystemServiceRegistry.registerContextAwareService(
                Context.SMS_SERVICE,
                SmsManager.class,
                new SystemServiceRegistry
                        .ContextAwareServiceProducerWithoutBinder() { // from class:
                                                                      // android.telephony.TelephonyFrameworkInitializer$$ExternalSyntheticLambda6
                    @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
                    public final Object createService(Context context) {
                        return TelephonyFrameworkInitializer.lambda$registerServiceWrappers$6(
                                context);
                    }
                });
        SystemServiceRegistry.registerContextAwareService(
                "satellite",
                SatelliteManager.class,
                new SystemServiceRegistry
                        .ContextAwareServiceProducerWithoutBinder() { // from class:
                                                                      // android.telephony.TelephonyFrameworkInitializer$$ExternalSyntheticLambda7
                    @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
                    public final Object createService(Context context) {
                        return TelephonyFrameworkInitializer.lambda$registerServiceWrappers$7(
                                context);
                    }
                });
        com.android.telephony.Rlog.d(
                "TelephonyFrameworkInitializer", "registerServiceWrappers finish");
    }

    static /* synthetic */ TelephonyManager lambda$registerServiceWrappers$0(Context context) {
        return new TelephonyManager(context);
    }

    static /* synthetic */ SubscriptionManager lambda$registerServiceWrappers$1(Context context) {
        return new SubscriptionManager(context);
    }

    static /* synthetic */ CarrierConfigManager lambda$registerServiceWrappers$2(Context context) {
        if (hasSystemFeature(context, PackageManager.FEATURE_TELEPHONY_SUBSCRIPTION)) {
            return new CarrierConfigManager(context);
        }
        return null;
    }

    static /* synthetic */ EuiccManager lambda$registerServiceWrappers$3(Context context) {
        if (hasSystemFeature(context, PackageManager.FEATURE_TELEPHONY_EUICC)) {
            return new EuiccManager(context);
        }
        return null;
    }

    static /* synthetic */ EuiccCardManager lambda$registerServiceWrappers$4(Context context) {
        if (hasSystemFeature(context, PackageManager.FEATURE_TELEPHONY_EUICC)) {
            return new EuiccCardManager(context);
        }
        return null;
    }

    static /* synthetic */ ImsManager lambda$registerServiceWrappers$5(Context context) {
        if (hasSystemFeature(context, PackageManager.FEATURE_TELEPHONY_IMS)) {
            return new ImsManager(context);
        }
        return null;
    }

    static /* synthetic */ SmsManager lambda$registerServiceWrappers$6(Context context) {
        if (hasSystemFeature(context, PackageManager.FEATURE_TELEPHONY_MESSAGING)) {
            return SmsManager.getSmsManagerForContextAndSubscriptionId(context, Integer.MAX_VALUE);
        }
        return null;
    }

    static /* synthetic */ SatelliteManager lambda$registerServiceWrappers$7(Context context) {
        if (hasSystemFeature(context, PackageManager.FEATURE_TELEPHONY_SATELLITE)) {
            return new SatelliteManager(context);
        }
        return null;
    }

    public static TelephonyServiceManager getTelephonyServiceManager() {
        return sTelephonyServiceManager;
    }
}
