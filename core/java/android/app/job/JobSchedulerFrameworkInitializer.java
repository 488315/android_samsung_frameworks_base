package android.app.job;

import android.annotation.SystemApi;
import android.app.JobSchedulerImpl;
import android.app.SystemServiceRegistry;
import android.content.Context;
import android.os.DeviceIdleManager;
import android.os.IBinder;
import android.os.IDeviceIdleController;
import android.os.PowerExemptionManager;
import android.os.PowerWhitelistManager;

@SystemApi
public class JobSchedulerFrameworkInitializer {
    private JobSchedulerFrameworkInitializer() {}

    public static void registerServiceWrappers() {
        SystemServiceRegistry.registerContextAwareService(
                Context.JOB_SCHEDULER_SERVICE,
                JobScheduler.class,
                new SystemServiceRegistry.ContextAwareServiceProducerWithBinder() { // from class:
                    // android.app.job.JobSchedulerFrameworkInitializer$$ExternalSyntheticLambda0
                    @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithBinder
                    public final Object createService(Context context, IBinder iBinder) {
                        return JobSchedulerFrameworkInitializer.lambda$registerServiceWrappers$0(
                                context, iBinder);
                    }
                });
        SystemServiceRegistry.registerContextAwareService(
                Context.DEVICE_IDLE_CONTROLLER,
                DeviceIdleManager.class,
                new SystemServiceRegistry.ContextAwareServiceProducerWithBinder() { // from class:
                    // android.app.job.JobSchedulerFrameworkInitializer$$ExternalSyntheticLambda1
                    @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithBinder
                    public final Object createService(Context context, IBinder iBinder) {
                        return JobSchedulerFrameworkInitializer.lambda$registerServiceWrappers$1(
                                context, iBinder);
                    }
                });
        SystemServiceRegistry.registerContextAwareService(
                Context.POWER_WHITELIST_MANAGER,
                PowerWhitelistManager.class,
                new SystemServiceRegistry
                        .ContextAwareServiceProducerWithoutBinder() { // from class:
                    // android.app.job.JobSchedulerFrameworkInitializer$$ExternalSyntheticLambda2
                    @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
                    public final Object createService(Context context) {
                        return new PowerWhitelistManager(context);
                    }
                });
        SystemServiceRegistry.registerContextAwareService(
                Context.POWER_EXEMPTION_SERVICE,
                PowerExemptionManager.class,
                new SystemServiceRegistry
                        .ContextAwareServiceProducerWithoutBinder() { // from class:
                    // android.app.job.JobSchedulerFrameworkInitializer$$ExternalSyntheticLambda3
                    @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
                    public final Object createService(Context context) {
                        return new PowerExemptionManager(context);
                    }
                });
    }

    static /* synthetic */ JobScheduler lambda$registerServiceWrappers$0(
            Context context, IBinder b) {
        return new JobSchedulerImpl(context, IJobScheduler.Stub.asInterface(b));
    }

    static /* synthetic */ DeviceIdleManager lambda$registerServiceWrappers$1(
            Context context, IBinder b) {
        return new DeviceIdleManager(context, IDeviceIdleController.Stub.asInterface(b));
    }
}
