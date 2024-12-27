package android.provider;

import android.app.SystemServiceRegistry;
import android.content.Context;

/* loaded from: classes3.dex */
public class ProviderFrameworkInitializer {
    private ProviderFrameworkInitializer() {}

    public static void registerServiceWrappers() {
        SystemServiceRegistry.registerContextAwareService(
                Context.BLOCKED_NUMBERS_SERVICE,
                BlockedNumbersManager.class,
                new SystemServiceRegistry
                        .ContextAwareServiceProducerWithoutBinder() { // from class:
                                                                      // android.provider.ProviderFrameworkInitializer$$ExternalSyntheticLambda0
                    @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
                    public final Object createService(Context context) {
                        return ProviderFrameworkInitializer.lambda$registerServiceWrappers$0(
                                context);
                    }
                });
    }

    static /* synthetic */ BlockedNumbersManager lambda$registerServiceWrappers$0(Context context) {
        return new BlockedNumbersManager(context);
    }
}
