package com.android.server.tracing;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.IMessenger;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.UserHandle;
import android.tracing.ITracingServiceProxy;
import android.tracing.TraceReportParams;
import android.util.Log;
import android.util.LruCache;
import android.util.Slog;
import com.android.internal.infra.ServiceConnector;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import java.io.IOException;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TracingServiceProxy extends SystemService {
    public final LruCache mCachedReporterServices;
    public final Context mContext;
    public final PackageManager mPackageManager;
    public boolean mServicePublished;
    public final AnonymousClass1 mTracingServiceProxy;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.tracing.TracingServiceProxy$1] */
    public TracingServiceProxy(Context context) {
        super(context);
        this.mServicePublished = false;
        this.mTracingServiceProxy = new ITracingServiceProxy.Stub() { // from class: com.android.server.tracing.TracingServiceProxy.1
            public final void notifyTraceSessionEnded(boolean z) {
                TracingServiceProxy tracingServiceProxy = TracingServiceProxy.this;
                tracingServiceProxy.getClass();
                Intent intent = new Intent();
                try {
                    intent.setClassName(tracingServiceProxy.mPackageManager.getPackageInfo("com.android.traceur", 1048576).packageName, "com.android.traceur.StopTraceService");
                    if (z) {
                        intent.setAction("com.android.traceur.NOTIFY_SESSION_STOLEN");
                    } else {
                        intent.setAction("com.android.traceur.NOTIFY_SESSION_STOPPED");
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        try {
                            tracingServiceProxy.mContext.startForegroundServiceAsUser(intent, UserHandle.SYSTEM);
                        } catch (RuntimeException e) {
                            Log.e("TracingServiceProxy", "Failed to notifyTraceSessionEnded", e);
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (PackageManager.NameNotFoundException e2) {
                    Log.e("TracingServiceProxy", "Failed to locate Traceur", e2);
                }
            }

            public final void reportTrace(final TraceReportParams traceReportParams) {
                ServiceInfo serviceInfo;
                TracingServiceProxy tracingServiceProxy = TracingServiceProxy.this;
                tracingServiceProxy.getClass();
                FrameworkStatsLog.write(FrameworkStatsLog.TRACING_SERVICE_REPORT_EVENT, 1, traceReportParams.uuidLsb, traceReportParams.uuidMsb);
                ComponentName componentName = new ComponentName(traceReportParams.reporterPackageName, traceReportParams.reporterClassName);
                try {
                    serviceInfo = tracingServiceProxy.mPackageManager.getServiceInfo(componentName, 0);
                } catch (PackageManager.NameNotFoundException unused) {
                    Slog.e("TracingServiceProxy", "Trace reporting service " + componentName.toShortString() + " does not exist");
                }
                if (!"android.permission.BIND_TRACE_REPORT_SERVICE".equals(serviceInfo.permission)) {
                    StringBuilder sb = new StringBuilder("Trace reporting service ");
                    sb.append(componentName.toShortString());
                    sb.append(" does not request android.permission.BIND_TRACE_REPORT_SERVICE permission; instead requests ");
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(sb, serviceInfo.permission, "TracingServiceProxy");
                    FrameworkStatsLog.write(FrameworkStatsLog.TRACING_SERVICE_REPORT_EVENT, 3, traceReportParams.uuidLsb, traceReportParams.uuidMsb);
                    return;
                }
                boolean hasPermission = tracingServiceProxy.hasPermission(componentName, "android.permission.DUMP");
                boolean hasPermission2 = tracingServiceProxy.hasPermission(componentName, "android.permission.PACKAGE_USAGE_STATS");
                if (!hasPermission || !hasPermission2) {
                    FrameworkStatsLog.write(FrameworkStatsLog.TRACING_SERVICE_REPORT_EVENT, 4, traceReportParams.uuidLsb, traceReportParams.uuidMsb);
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    tracingServiceProxy.getOrCreateReporterService(componentName).post(new ServiceConnector.VoidJob() { // from class: com.android.server.tracing.TracingServiceProxy$$ExternalSyntheticLambda1
                        public final void runNoResult(Object obj) {
                            TraceReportParams traceReportParams2 = traceReportParams;
                            IMessenger iMessenger = (IMessenger) obj;
                            if (traceReportParams2.usePipeForTesting) {
                                ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
                                ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(traceReportParams2.fd);
                                try {
                                    ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(createPipe[1]);
                                    try {
                                        byte[] readNBytes = autoCloseInputStream.readNBytes(1024);
                                        if (readNBytes.length == 1024) {
                                            throw new IllegalArgumentException("Trace file too large when |usePipeForTesting| is set.");
                                        }
                                        autoCloseOutputStream.write(readNBytes);
                                        autoCloseOutputStream.close();
                                        autoCloseInputStream.close();
                                        traceReportParams2.fd = createPipe[0];
                                    } finally {
                                    }
                                } catch (Throwable th) {
                                    try {
                                        autoCloseInputStream.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                    throw th;
                                }
                            }
                            Message obtain = Message.obtain();
                            obtain.what = 1;
                            obtain.obj = traceReportParams2;
                            iMessenger.send(obtain);
                            FrameworkStatsLog.write(FrameworkStatsLog.TRACING_SERVICE_REPORT_EVENT, 2, traceReportParams2.uuidLsb, traceReportParams2.uuidMsb);
                        }
                    }).whenComplete(new BiConsumer() { // from class: com.android.server.tracing.TracingServiceProxy$$ExternalSyntheticLambda2
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            TraceReportParams traceReportParams2 = traceReportParams;
                            Throwable th = (Throwable) obj2;
                            if (th != null) {
                                FrameworkStatsLog.write(FrameworkStatsLog.TRACING_SERVICE_REPORT_EVENT, 5, traceReportParams2.uuidLsb, traceReportParams2.uuidMsb);
                                Slog.e("TracingServiceProxy", "Failed to report trace", th);
                            }
                            try {
                                traceReportParams2.fd.close();
                            } catch (IOException unused2) {
                            }
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        };
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mCachedReporterServices = new LruCache(8);
    }

    public final ServiceConnector getOrCreateReporterService(ComponentName componentName) {
        ServiceConnector serviceConnector = (ServiceConnector) this.mCachedReporterServices.get(componentName);
        if (serviceConnector != null) {
            return serviceConnector;
        }
        Intent intent = new Intent();
        intent.setComponent(componentName);
        Context context = this.mContext;
        ServiceConnector.Impl impl = new ServiceConnector.Impl(context, intent, context.getUser().getIdentifier(), new TracingServiceProxy$$ExternalSyntheticLambda0()) { // from class: com.android.server.tracing.TracingServiceProxy.2
            public final long getAutoDisconnectTimeoutMs() {
                return 15000L;
            }

            public final long getRequestTimeoutMs() {
                return 10000L;
            }
        };
        this.mCachedReporterServices.put(intent.getComponent(), impl);
        return impl;
    }

    public final boolean hasPermission(ComponentName componentName, String str) {
        if (this.mPackageManager.checkPermission(str, componentName.getPackageName()) == 0) {
            return true;
        }
        Slog.e("TracingServiceProxy", "Trace reporting service " + componentName.toShortString() + " does not have " + str + " permission");
        return false;
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        if (this.mServicePublished) {
            return;
        }
        publishBinderService("tracing.proxy", this.mTracingServiceProxy);
        this.mServicePublished = true;
    }
}
