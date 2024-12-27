package com.android.server.ambientcontext;

import android.app.ambientcontext.AmbientContextEventRequest;
import android.app.ambientcontext.IAmbientContextObserver;
import android.content.ComponentName;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteCallback;
import android.os.ShellCommand;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService;
import com.android.server.ambientcontext.AmbientContextManagerService;
import com.android.server.ambientcontext.AmbientContextShellCommand;
import com.android.server.infra.AbstractPerUserSystemService;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AmbientContextShellCommand extends ShellCommand {
    public final AmbientContextManagerService mService;
    public static final AmbientContextEventRequest REQUEST = new AmbientContextEventRequest.Builder().addEventType(1).addEventType(2).addEventType(3).build();
    public static final AmbientContextEventRequest WEARABLE_REQUEST = new AmbientContextEventRequest.Builder().addEventType(FrameworkStatsLog.GPS_ENGINE_STATE_CHANGED).build();
    public static final AmbientContextEventRequest MIXED_REQUEST = new AmbientContextEventRequest.Builder().addEventType(1).addEventType(FrameworkStatsLog.GPS_ENGINE_STATE_CHANGED).build();
    public static final TestableCallbackInternal sTestableCallbackInternal = new TestableCallbackInternal();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TestableCallbackInternal {
        public int mLastStatus;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.ambientcontext.AmbientContextShellCommand$TestableCallbackInternal$1, reason: invalid class name */
        public final class AnonymousClass1 extends IAmbientContextObserver.Stub {
            public AnonymousClass1() {
            }

            public final void onEvents(List list) {
                TestableCallbackInternal.this.getClass();
                System.out.println("Detection events available: " + list);
            }

            public final void onRegistrationComplete(int i) {
                TestableCallbackInternal.this.mLastStatus = i;
            }
        }

        /* renamed from: -$$Nest$mcreateAmbientContextObserver, reason: not valid java name */
        public static AnonymousClass1 m212$$Nest$mcreateAmbientContextObserver(TestableCallbackInternal testableCallbackInternal) {
            testableCallbackInternal.getClass();
            return testableCallbackInternal.new AnonymousClass1();
        }
    }

    public AmbientContextShellCommand(AmbientContextManagerService ambientContextManagerService) {
        this.mService = ambientContextManagerService;
    }

    public final int onCommand(String str) {
        ComponentName componentName;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str) {
            case "get-bound-package":
                PrintWriter outPrintWriter = getOutPrintWriter();
                int parseInt = Integer.parseInt(getNextArgRequired());
                AmbientContextManagerService ambientContextManagerService = this.mService;
                AmbientContextManagerPerUserService.ServiceType serviceType = AmbientContextManagerPerUserService.ServiceType.DEFAULT;
                synchronized (ambientContextManagerService.mLock) {
                    try {
                        AmbientContextManagerPerUserService serviceForType = ambientContextManagerService.getServiceForType(parseInt, serviceType);
                        componentName = serviceForType != null ? serviceForType.getComponentName() : null;
                    } finally {
                    }
                }
                outPrintWriter.println(componentName == null ? "" : componentName.getPackageName());
                return 0;
            case "stop-detection":
                int parseInt2 = Integer.parseInt(getNextArgRequired());
                String nextArgRequired = getNextArgRequired();
                AmbientContextManagerService ambientContextManagerService2 = this.mService;
                ambientContextManagerService2.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_AMBIENT_CONTEXT_EVENT", "AmbientContextManagerService");
                synchronized (ambientContextManagerService2.mLock) {
                    try {
                        for (AmbientContextManagerService.ClientRequest clientRequest : ambientContextManagerService2.mExistingClientRequests) {
                            Slog.i("AmbientContextManagerService", "Looping through clients");
                            if (clientRequest.hasUserIdAndPackageName(parseInt2, nextArgRequired)) {
                                Slog.i("AmbientContextManagerService", "we have an existing client");
                                AmbientContextManagerPerUserService ambientContextManagerPerUserServiceForEventTypes = ambientContextManagerService2.getAmbientContextManagerPerUserServiceForEventTypes(parseInt2, clientRequest.mRequest.getEventTypes());
                                if (ambientContextManagerPerUserServiceForEventTypes != null) {
                                    ambientContextManagerPerUserServiceForEventTypes.stopDetection(nextArgRequired);
                                } else {
                                    Slog.i("AmbientContextManagerService", "service not available for user_id: " + parseInt2);
                                }
                            }
                        }
                    } finally {
                    }
                }
                return 0;
            case "query-mixed-service-status":
                int parseInt3 = Integer.parseInt(getNextArgRequired());
                String nextArgRequired2 = getNextArgRequired();
                int[] iArr = {1, FrameworkStatsLog.GPS_ENGINE_STATE_CHANGED};
                AmbientContextManagerService ambientContextManagerService3 = this.mService;
                final TestableCallbackInternal testableCallbackInternal = sTestableCallbackInternal;
                testableCallbackInternal.getClass();
                ambientContextManagerService3.queryServiceStatus(parseInt3, nextArgRequired2, iArr, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: com.android.server.ambientcontext.AmbientContextShellCommand$TestableCallbackInternal$$ExternalSyntheticLambda0
                    public final void onResult(Bundle bundle) {
                        AmbientContextShellCommand.TestableCallbackInternal testableCallbackInternal2 = AmbientContextShellCommand.TestableCallbackInternal.this;
                        testableCallbackInternal2.getClass();
                        int i = bundle.getInt("android.app.ambientcontext.AmbientContextStatusBundleKey");
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            testableCallbackInternal2.mLastStatus = i;
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }));
                return 0;
            case "start-detection-wearable":
                int parseInt4 = Integer.parseInt(getNextArgRequired());
                String nextArgRequired3 = getNextArgRequired();
                AmbientContextManagerService ambientContextManagerService4 = this.mService;
                AmbientContextEventRequest ambientContextEventRequest = WEARABLE_REQUEST;
                TestableCallbackInternal testableCallbackInternal2 = sTestableCallbackInternal;
                ambientContextManagerService4.startDetection(parseInt4, ambientContextEventRequest, nextArgRequired3, TestableCallbackInternal.m212$$Nest$mcreateAmbientContextObserver(testableCallbackInternal2));
                this.mService.newClientAdded(parseInt4, ambientContextEventRequest, nextArgRequired3, testableCallbackInternal2.new AnonymousClass1());
                return 0;
            case "start-detection-mixed":
                int parseInt5 = Integer.parseInt(getNextArgRequired());
                String nextArgRequired4 = getNextArgRequired();
                AmbientContextManagerService ambientContextManagerService5 = this.mService;
                AmbientContextEventRequest ambientContextEventRequest2 = MIXED_REQUEST;
                TestableCallbackInternal testableCallbackInternal3 = sTestableCallbackInternal;
                ambientContextManagerService5.startDetection(parseInt5, ambientContextEventRequest2, nextArgRequired4, TestableCallbackInternal.m212$$Nest$mcreateAmbientContextObserver(testableCallbackInternal3));
                this.mService.newClientAdded(parseInt5, ambientContextEventRequest2, nextArgRequired4, testableCallbackInternal3.new AnonymousClass1());
                return 0;
            case "set-temporary-services":
                PrintWriter outPrintWriter2 = getOutPrintWriter();
                int parseInt6 = Integer.parseInt(getNextArgRequired());
                this.mService.setDefaultServiceEnabled(parseInt6, false);
                String nextArg = getNextArg();
                String nextArg2 = getNextArg();
                if (nextArg == null || nextArg2 == null) {
                    this.mService.resetTemporaryService(parseInt6);
                    this.mService.setDefaultServiceEnabled(parseInt6, true);
                    outPrintWriter2.println("AmbientContextDetectionService temporary reset.");
                } else {
                    String[] strArr = {nextArg, nextArg2};
                    int parseInt7 = Integer.parseInt(getNextArgRequired());
                    AmbientContextManagerService ambientContextManagerService6 = this.mService;
                    Slog.i(ambientContextManagerService6.mTag, ActiveServices$$ExternalSyntheticOutline0.m(parseInt7, Arrays.toString(strArr), " for ", "ms", BatteryService$$ExternalSyntheticOutline0.m(parseInt6, "setTemporaryService(", ") to ")));
                    if (ambientContextManagerService6.mServiceNameResolver != null) {
                        ambientContextManagerService6.enforceCallingPermissionForManagement();
                        if (parseInt7 > 30000) {
                            throw new IllegalArgumentException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(parseInt7, "Max duration is 30000 (called with ", ")"));
                        }
                        synchronized (ambientContextManagerService6.mLock) {
                            try {
                                AbstractPerUserSystemService peekServiceForUserLocked = ambientContextManagerService6.peekServiceForUserLocked(parseInt6);
                                if (peekServiceForUserLocked != null) {
                                    peekServiceForUserLocked.removeSelfFromCache();
                                }
                                ambientContextManagerService6.mServiceNameResolver.setTemporaryServices(parseInt6, parseInt7, strArr);
                            } finally {
                            }
                        }
                    }
                    StringBuilder sb = new StringBuilder("AmbientContextDetectionService temporarily set to ");
                    sb.append(strArr[0]);
                    sb.append(" and ");
                    Slog.w("AmbientContextShellCommand", ActiveServices$$ExternalSyntheticOutline0.m(parseInt7, strArr[1], " for ", "ms", sb));
                    StringBuilder sb2 = new StringBuilder("AmbientContextDetectionService temporarily set to ");
                    sb2.append(strArr[0]);
                    sb2.append(" and ");
                    outPrintWriter2.println(ActiveServices$$ExternalSyntheticOutline0.m(parseInt7, strArr[1], " for ", "ms", sb2));
                }
                return 0;
            case "query-wearable-service-status":
                int parseInt8 = Integer.parseInt(getNextArgRequired());
                String nextArgRequired5 = getNextArgRequired();
                int[] iArr2 = {FrameworkStatsLog.GPS_ENGINE_STATE_CHANGED};
                AmbientContextManagerService ambientContextManagerService7 = this.mService;
                final TestableCallbackInternal testableCallbackInternal4 = sTestableCallbackInternal;
                testableCallbackInternal4.getClass();
                ambientContextManagerService7.queryServiceStatus(parseInt8, nextArgRequired5, iArr2, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: com.android.server.ambientcontext.AmbientContextShellCommand$TestableCallbackInternal$$ExternalSyntheticLambda0
                    public final void onResult(Bundle bundle) {
                        AmbientContextShellCommand.TestableCallbackInternal testableCallbackInternal22 = AmbientContextShellCommand.TestableCallbackInternal.this;
                        testableCallbackInternal22.getClass();
                        int i = bundle.getInt("android.app.ambientcontext.AmbientContextStatusBundleKey");
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            testableCallbackInternal22.mLastStatus = i;
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }));
                return 0;
            case "set-temporary-service":
                PrintWriter outPrintWriter3 = getOutPrintWriter();
                int parseInt9 = Integer.parseInt(getNextArgRequired());
                String nextArg3 = getNextArg();
                if (nextArg3 == null) {
                    this.mService.resetTemporaryService(parseInt9);
                    outPrintWriter3.println("AmbientContextDetectionService temporary reset. ");
                    this.mService.setDefaultServiceEnabled(parseInt9, true);
                } else {
                    int parseInt10 = Integer.parseInt(getNextArgRequired());
                    this.mService.setTemporaryService(parseInt9, nextArg3, parseInt10);
                    outPrintWriter3.println(ActiveServices$$ExternalSyntheticOutline0.m(parseInt10, nextArg3, " for ", "ms", new StringBuilder("AmbientContextDetectionService temporarily set to ")));
                }
                return 0;
            case "query-service-status":
                AmbientContextManagerService ambientContextManagerService8 = this.mService;
                final TestableCallbackInternal testableCallbackInternal5 = sTestableCallbackInternal;
                testableCallbackInternal5.getClass();
                ambientContextManagerService8.queryServiceStatus(Integer.parseInt(getNextArgRequired()), getNextArgRequired(), new int[]{1, 2}, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: com.android.server.ambientcontext.AmbientContextShellCommand$TestableCallbackInternal$$ExternalSyntheticLambda0
                    public final void onResult(Bundle bundle) {
                        AmbientContextShellCommand.TestableCallbackInternal testableCallbackInternal22 = AmbientContextShellCommand.TestableCallbackInternal.this;
                        testableCallbackInternal22.getClass();
                        int i = bundle.getInt("android.app.ambientcontext.AmbientContextStatusBundleKey");
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            testableCallbackInternal22.mLastStatus = i;
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }));
                return 0;
            case "get-last-status-code":
                getOutPrintWriter().println(sTestableCallbackInternal.mLastStatus);
                return 0;
            case "start-detection":
                int parseInt11 = Integer.parseInt(getNextArgRequired());
                String nextArgRequired6 = getNextArgRequired();
                AmbientContextManagerService ambientContextManagerService9 = this.mService;
                AmbientContextEventRequest ambientContextEventRequest3 = REQUEST;
                TestableCallbackInternal testableCallbackInternal6 = sTestableCallbackInternal;
                ambientContextManagerService9.startDetection(parseInt11, ambientContextEventRequest3, nextArgRequired6, TestableCallbackInternal.m212$$Nest$mcreateAmbientContextObserver(testableCallbackInternal6));
                this.mService.newClientAdded(parseInt11, ambientContextEventRequest3, nextArgRequired6, testableCallbackInternal6.new AnonymousClass1());
                return 0;
            default:
                return handleDefaultCommands(str);
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("AmbientContextEvent commands: ");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  start-detection USER_ID PACKAGE_NAME: Starts AmbientContextEvent detection.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  start-detection-wearable USER_ID PACKAGE_NAME: Starts AmbientContextEvent detection for wearable.", "  start-detection-mixed USER_ID PACKAGE_NAME:  Starts AmbientContextEvent detection for mixed events.", "  stop-detection USER_ID PACKAGE_NAME: Stops AmbientContextEvent detection.", "  get-last-status-code: Prints the latest request status code.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  query-service-status USER_ID PACKAGE_NAME: Prints the service status code.", "  query-wearable-service-status USER_ID PACKAGE_NAME: Prints the service status code for wearable.", "  query-mixed-service-status USER_ID PACKAGE_NAME: Prints the service status code for mixed events.", "  get-bound-package USER_ID:     Print the bound package that implements the service.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  set-temporary-service USER_ID [PACKAGE_NAME] [COMPONENT_NAME DURATION]", "    Temporarily (for DURATION ms) changes the service implementation.", "    To reset, call with just the USER_ID argument.", "  set-temporary-services USER_ID [FIRST_PACKAGE_NAME] [SECOND_PACKAGE_NAME] [COMPONENT_NAME DURATION]");
        outPrintWriter.println("    Temporarily (for DURATION ms) changes the service implementation.");
        outPrintWriter.println("    To reset, call with just the USER_ID argument.");
    }
}
