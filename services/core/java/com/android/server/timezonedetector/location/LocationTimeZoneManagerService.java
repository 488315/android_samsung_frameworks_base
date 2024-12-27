package com.android.server.timezonedetector.location;

import android.R;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;

import com.android.internal.util.DumpUtils;
import com.android.server.FgThread;
import com.android.server.SystemService;
import com.android.server.timezonedetector.Dumpable;
import com.android.server.timezonedetector.ServiceConfigAccessor;
import com.android.server.timezonedetector.ServiceConfigAccessorImpl;
import com.android.server.timezonedetector.ServiceConfigAccessorImpl$$ExternalSyntheticLambda1;
import com.android.server.timezonedetector.StateChangeListener;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class LocationTimeZoneManagerService extends Binder {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long BLOCKING_OP_WAIT_DURATION_MILLIS = Duration.ofSeconds(20).toMillis();
    public final Context mContext;
    public final Handler mHandler;
    public LocationTimeZoneProviderController mLocationTimeZoneProviderController;
    public LocationTimeZoneProviderControllerEnvironmentImpl
            mLocationTimeZoneProviderControllerEnvironment;
    public final ProviderConfig mPrimaryProviderConfig =
            new ProviderConfig(
                    0,
                    "primary",
                    "android.service.timezone.PrimaryLocationTimeZoneProviderService");
    public final ProviderConfig mSecondaryProviderConfig =
            new ProviderConfig(
                    1,
                    "secondary",
                    "android.service.timezone.SecondaryLocationTimeZoneProviderService");
    public final ServiceConfigAccessor mServiceConfigAccessor;
    public final Object mSharedLock;
    public final HandlerThreadingDomain mThreadingDomain;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public LocationTimeZoneManagerService mService;
        public final ServiceConfigAccessor mServiceConfigAccessor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Lifecycle(Context context) {
            super(context);
            Objects.requireNonNull(context);
            this.mServiceConfigAccessor = ServiceConfigAccessorImpl.getInstance(context);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (((ServiceConfigAccessorImpl) this.mServiceConfigAccessor)
                    .mContext
                    .getResources()
                    .getBoolean(R.bool.config_enableMultipleAdmins)) {
                if (i == 500) {
                    final LocationTimeZoneManagerService locationTimeZoneManagerService =
                            this.mService;
                    ServiceConfigAccessor serviceConfigAccessor =
                            locationTimeZoneManagerService.mServiceConfigAccessor;
                    ((ServiceConfigAccessorImpl) serviceConfigAccessor)
                            .mServerFlags.addListener(
                                    new StateChangeListener() { // from class:
                                                                // com.android.server.timezonedetector.location.LocationTimeZoneManagerService$$ExternalSyntheticLambda3
                                        @Override // com.android.server.timezonedetector.StateChangeListener
                                        public final void onChange() {
                                            LocationTimeZoneManagerService
                                                    locationTimeZoneManagerService2 =
                                                            LocationTimeZoneManagerService.this;
                                            HandlerThreadingDomain handlerThreadingDomain =
                                                    locationTimeZoneManagerService2
                                                            .mThreadingDomain;
                                            handlerThreadingDomain.mHandler.post(
                                                    new LocationTimeZoneManagerService$$ExternalSyntheticLambda1(
                                                            locationTimeZoneManagerService2, 3));
                                        }
                                    },
                                    ServiceConfigAccessorImpl
                                            .LOCATION_TIME_ZONE_MANAGER_SERVER_FLAGS_KEYS_TO_WATCH);
                    return;
                }
                if (i == 600) {
                    LocationTimeZoneManagerService locationTimeZoneManagerService2 = this.mService;
                    locationTimeZoneManagerService2.getClass();
                    locationTimeZoneManagerService2.mThreadingDomain.mHandler.post(
                            new LocationTimeZoneManagerService$$ExternalSyntheticLambda1(
                                    locationTimeZoneManagerService2, 1));
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            Context context = getContext();
            ServiceConfigAccessor serviceConfigAccessor = this.mServiceConfigAccessor;
            if (!((ServiceConfigAccessorImpl) serviceConfigAccessor)
                    .mContext
                    .getResources()
                    .getBoolean(R.bool.config_enableMultipleAdmins)) {
                Slog.d(
                        "LocationTZDetector",
                        "Geo time zone detection feature is disabled in config");
                return;
            }
            LocationTimeZoneManagerService locationTimeZoneManagerService =
                    new LocationTimeZoneManagerService(context, serviceConfigAccessor);
            this.mService = locationTimeZoneManagerService;
            publishBinderService("location_time_zone_manager", locationTimeZoneManagerService);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProviderConfig implements Dumpable {
        public final int mIndex;
        public final String mName;
        public final String mServiceAction;

        /* JADX WARN: Code restructure failed: missing block: B:4:0x0008, code lost:

           if (r2 <= 1) goto L8;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public ProviderConfig(int r2, java.lang.String r3, java.lang.String r4) {
            /*
                r0 = this;
                r0.<init>()
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this = r1
                if (r2 < 0) goto Lb
                r1 = 1
                if (r2 > r1) goto Lb
                goto Lc
            Lb:
                r1 = 0
            Lc:
                com.android.internal.util.Preconditions.checkArgument(r1)
                r0.mIndex = r2
                r0.mName = r3
                r0.mServiceAction = r4
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.timezonedetector.location.LocationTimeZoneManagerService.ProviderConfig.<init>(com.android.server.timezonedetector.location.LocationTimeZoneManagerService,"
                        + " int, java.lang.String, java.lang.String):void");
        }

        public final LocationTimeZoneProvider createProvider() {
            boolean z;
            boolean z2;
            boolean z3;
            int i = this.mIndex;
            RealProviderMetricsLogger realProviderMetricsLogger = new RealProviderMetricsLogger(i);
            LocationTimeZoneManagerService locationTimeZoneManagerService =
                    LocationTimeZoneManagerService.this;
            if (Objects.equals(
                    i == 0
                            ? ((ServiceConfigAccessorImpl)
                                            locationTimeZoneManagerService.mServiceConfigAccessor)
                                    .getPrimaryLocationTimeZoneProviderMode()
                            : ((ServiceConfigAccessorImpl)
                                            locationTimeZoneManagerService.mServiceConfigAccessor)
                                    .getSecondaryLocationTimeZoneProviderMode(),
                    "disabled")) {
                LocationTimeZoneManagerService locationTimeZoneManagerService2 =
                        LocationTimeZoneManagerService.this;
                HandlerThreadingDomain handlerThreadingDomain =
                        locationTimeZoneManagerService2.mThreadingDomain;
                String str = this.mName;
                ServiceConfigAccessorImpl serviceConfigAccessorImpl =
                        (ServiceConfigAccessorImpl)
                                locationTimeZoneManagerService2.mServiceConfigAccessor;
                synchronized (serviceConfigAccessorImpl) {
                    z3 = serviceConfigAccessorImpl.mRecordStateChangesForTests;
                }
                return new DisabledLocationTimeZoneProvider(
                        realProviderMetricsLogger,
                        handlerThreadingDomain,
                        str,
                        new DisabledLocationTimeZoneProvider$$ExternalSyntheticLambda0(),
                        z3);
            }
            String str2 = this.mServiceAction;
            if (this.mIndex == 0) {
                ServiceConfigAccessorImpl serviceConfigAccessorImpl2 =
                        (ServiceConfigAccessorImpl)
                                LocationTimeZoneManagerService.this.mServiceConfigAccessor;
                synchronized (serviceConfigAccessorImpl2) {
                    z = serviceConfigAccessorImpl2.mTestPrimaryLocationTimeZoneProviderMode != null;
                }
            } else {
                ServiceConfigAccessorImpl serviceConfigAccessorImpl3 =
                        (ServiceConfigAccessorImpl)
                                LocationTimeZoneManagerService.this.mServiceConfigAccessor;
                synchronized (serviceConfigAccessorImpl3) {
                    z =
                            serviceConfigAccessorImpl3.mTestSecondaryLocationTimeZoneProviderMode
                                    != null;
                }
            }
            boolean z4 = z;
            String packageName = getPackageName();
            LocationTimeZoneManagerService locationTimeZoneManagerService3 =
                    LocationTimeZoneManagerService.this;
            RealLocationTimeZoneProviderProxy realLocationTimeZoneProviderProxy =
                    new RealLocationTimeZoneProviderProxy(
                            locationTimeZoneManagerService3.mContext,
                            locationTimeZoneManagerService3.mHandler,
                            locationTimeZoneManagerService3.mThreadingDomain,
                            str2,
                            packageName,
                            z4);
            LocationTimeZoneManagerService locationTimeZoneManagerService4 =
                    LocationTimeZoneManagerService.this;
            HandlerThreadingDomain handlerThreadingDomain2 =
                    locationTimeZoneManagerService4.mThreadingDomain;
            String str3 = this.mName;
            ServiceConfigAccessorImpl serviceConfigAccessorImpl4 =
                    (ServiceConfigAccessorImpl)
                            locationTimeZoneManagerService4.mServiceConfigAccessor;
            synchronized (serviceConfigAccessorImpl4) {
                z2 = serviceConfigAccessorImpl4.mRecordStateChangesForTests;
            }
            return new BinderLocationTimeZoneProvider(
                    realProviderMetricsLogger,
                    handlerThreadingDomain2,
                    str3,
                    realLocationTimeZoneProviderProxy,
                    z2);
        }

        @Override // com.android.server.timezonedetector.Dumpable
        public final void dump(IndentingPrintWriter indentingPrintWriter, String[] strArr) {
            int i = this.mIndex;
            LocationTimeZoneManagerService locationTimeZoneManagerService =
                    LocationTimeZoneManagerService.this;
            indentingPrintWriter.printf(
                    "getMode()=%s\n",
                    new Object[] {
                        i == 0
                                ? ((ServiceConfigAccessorImpl)
                                                locationTimeZoneManagerService
                                                        .mServiceConfigAccessor)
                                        .getPrimaryLocationTimeZoneProviderMode()
                                : ((ServiceConfigAccessorImpl)
                                                locationTimeZoneManagerService
                                                        .mServiceConfigAccessor)
                                        .getSecondaryLocationTimeZoneProviderMode()
                    });
            indentingPrintWriter.printf("getPackageName()=%s\n", new Object[] {getPackageName()});
        }

        public final String getPackageName() {
            String string;
            String string2;
            if (this.mIndex == 0) {
                ServiceConfigAccessorImpl serviceConfigAccessorImpl =
                        (ServiceConfigAccessorImpl)
                                LocationTimeZoneManagerService.this.mServiceConfigAccessor;
                synchronized (serviceConfigAccessorImpl) {
                    string2 =
                            serviceConfigAccessorImpl.mTestPrimaryLocationTimeZoneProviderMode
                                            != null
                                    ? serviceConfigAccessorImpl
                                            .mTestPrimaryLocationTimeZoneProviderPackageName
                                    : serviceConfigAccessorImpl
                                            .mContext
                                            .getResources()
                                            .getString(
                                                    R.string
                                                            .ext_media_nomedia_notification_message);
                }
                return string2;
            }
            ServiceConfigAccessorImpl serviceConfigAccessorImpl2 =
                    (ServiceConfigAccessorImpl)
                            LocationTimeZoneManagerService.this.mServiceConfigAccessor;
            synchronized (serviceConfigAccessorImpl2) {
                string =
                        serviceConfigAccessorImpl2.mTestSecondaryLocationTimeZoneProviderMode
                                        != null
                                ? serviceConfigAccessorImpl2
                                        .mTestSecondaryLocationTimeZoneProviderPackageName
                                : serviceConfigAccessorImpl2
                                        .mContext
                                        .getResources()
                                        .getString(R.string.face_acquired_poor_gaze);
            }
            return string;
        }
    }

    public LocationTimeZoneManagerService(
            Context context, ServiceConfigAccessor serviceConfigAccessor) {
        this.mContext = context.createAttributionContext("LocationTimeZoneService");
        Handler handler = FgThread.getHandler();
        this.mHandler = handler;
        HandlerThreadingDomain handlerThreadingDomain = new HandlerThreadingDomain(handler);
        this.mThreadingDomain = handlerThreadingDomain;
        this.mSharedLock = handlerThreadingDomain.mLockObject;
        Objects.requireNonNull(serviceConfigAccessor);
        this.mServiceConfigAccessor = serviceConfigAccessor;
    }

    public static void debugLog(String str) {
        if (Log.isLoggable("LocationTZDetector", 3)) {
            Slog.d("LocationTZDetector", str);
        }
    }

    public static void warnLog(String str, Throwable th) {
        if (Log.isLoggable("LocationTZDetector", 5)) {
            Slog.w("LocationTZDetector", str, th);
        }
    }

    @Override // android.os.Binder
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "LocationTZDetector", printWriter)) {
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
            synchronized (this.mSharedLock) {
                try {
                    indentingPrintWriter.println("LocationTimeZoneManagerService:");
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.println("Primary provider config:");
                    indentingPrintWriter.increaseIndent();
                    this.mPrimaryProviderConfig.dump(indentingPrintWriter, strArr);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("Secondary provider config:");
                    indentingPrintWriter.increaseIndent();
                    this.mSecondaryProviderConfig.dump(indentingPrintWriter, strArr);
                    indentingPrintWriter.decreaseIndent();
                    LocationTimeZoneProviderController locationTimeZoneProviderController =
                            this.mLocationTimeZoneProviderController;
                    if (locationTimeZoneProviderController == null) {
                        indentingPrintWriter.println("{Stopped}");
                    } else {
                        locationTimeZoneProviderController.dump(indentingPrintWriter, strArr);
                    }
                    indentingPrintWriter.decreaseIndent();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void onShellCommand(
            FileDescriptor fileDescriptor,
            FileDescriptor fileDescriptor2,
            FileDescriptor fileDescriptor3,
            String[] strArr,
            ShellCallback shellCallback,
            ResultReceiver resultReceiver) {
        new LocationTimeZoneManagerShellCommand(this)
                .exec(
                        this,
                        fileDescriptor,
                        fileDescriptor2,
                        fileDescriptor3,
                        strArr,
                        shellCallback,
                        resultReceiver);
    }

    public final void startOnDomainThread() {
        boolean z;
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            try {
                if (!((ServiceConfigAccessorImpl) this.mServiceConfigAccessor)
                        .isGeoTimeZoneDetectionFeatureSupported()) {
                    debugLog(
                            "Not starting location_time_zone_manager: it is disabled in service"
                                + " config");
                    return;
                }
                if (this.mLocationTimeZoneProviderController == null) {
                    LocationTimeZoneProvider createProvider =
                            this.mPrimaryProviderConfig.createProvider();
                    LocationTimeZoneProvider createProvider2 =
                            this.mSecondaryProviderConfig.createProvider();
                    RealControllerMetricsLogger realControllerMetricsLogger =
                            new RealControllerMetricsLogger();
                    ServiceConfigAccessorImpl serviceConfigAccessorImpl =
                            (ServiceConfigAccessorImpl) this.mServiceConfigAccessor;
                    synchronized (serviceConfigAccessorImpl) {
                        z = serviceConfigAccessorImpl.mRecordStateChangesForTests;
                    }
                    LocationTimeZoneProviderController locationTimeZoneProviderController =
                            new LocationTimeZoneProviderController(
                                    this.mThreadingDomain,
                                    realControllerMetricsLogger,
                                    createProvider,
                                    createProvider2,
                                    z);
                    LocationTimeZoneProviderControllerEnvironmentImpl
                            locationTimeZoneProviderControllerEnvironmentImpl =
                                    new LocationTimeZoneProviderControllerEnvironmentImpl(
                                            this.mThreadingDomain,
                                            this.mServiceConfigAccessor,
                                            locationTimeZoneProviderController);
                    locationTimeZoneProviderController.initialize(
                            locationTimeZoneProviderControllerEnvironmentImpl,
                            new LocationTimeZoneProviderControllerCallbackImpl(
                                    this.mThreadingDomain));
                    this.mLocationTimeZoneProviderControllerEnvironment =
                            locationTimeZoneProviderControllerEnvironmentImpl;
                    this.mLocationTimeZoneProviderController = locationTimeZoneProviderController;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopOnDomainThread() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            try {
                LocationTimeZoneProviderController locationTimeZoneProviderController =
                        this.mLocationTimeZoneProviderController;
                if (locationTimeZoneProviderController != null) {
                    locationTimeZoneProviderController.destroy();
                    this.mLocationTimeZoneProviderController = null;
                    LocationTimeZoneProviderControllerEnvironmentImpl
                            locationTimeZoneProviderControllerEnvironmentImpl =
                                    this.mLocationTimeZoneProviderControllerEnvironment;
                    ServiceConfigAccessor serviceConfigAccessor =
                            locationTimeZoneProviderControllerEnvironmentImpl
                                    .mServiceConfigAccessor;
                    LocationTimeZoneProviderControllerEnvironmentImpl$$ExternalSyntheticLambda0
                            locationTimeZoneProviderControllerEnvironmentImpl$$ExternalSyntheticLambda0 =
                                    locationTimeZoneProviderControllerEnvironmentImpl
                                            .mConfigurationInternalChangeListener;
                    ServiceConfigAccessorImpl serviceConfigAccessorImpl =
                            (ServiceConfigAccessorImpl) serviceConfigAccessor;
                    synchronized (serviceConfigAccessorImpl) {
                        List list = serviceConfigAccessorImpl.mConfigurationInternalListeners;
                        Objects.requireNonNull(
                                locationTimeZoneProviderControllerEnvironmentImpl$$ExternalSyntheticLambda0);
                        ((ArrayList) list)
                                .remove(
                                        locationTimeZoneProviderControllerEnvironmentImpl$$ExternalSyntheticLambda0);
                    }
                    this.mLocationTimeZoneProviderControllerEnvironment = null;
                    ServiceConfigAccessorImpl serviceConfigAccessorImpl2 =
                            (ServiceConfigAccessorImpl) this.mServiceConfigAccessor;
                    synchronized (serviceConfigAccessorImpl2) {
                        serviceConfigAccessorImpl2.mTestPrimaryLocationTimeZoneProviderPackageName =
                                null;
                        serviceConfigAccessorImpl2.mTestPrimaryLocationTimeZoneProviderMode = null;
                        serviceConfigAccessorImpl2
                                        .mTestSecondaryLocationTimeZoneProviderPackageName =
                                null;
                        serviceConfigAccessorImpl2.mTestSecondaryLocationTimeZoneProviderMode =
                                null;
                        serviceConfigAccessorImpl2.mRecordStateChangesForTests = false;
                        serviceConfigAccessorImpl2
                                .mContext
                                .getMainThreadHandler()
                                .post(
                                        new ServiceConfigAccessorImpl$$ExternalSyntheticLambda1(
                                                serviceConfigAccessorImpl2));
                    }
                }
            } finally {
            }
        }
    }
}
