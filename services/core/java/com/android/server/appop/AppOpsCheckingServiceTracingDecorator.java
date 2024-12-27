package com.android.server.appop;

import android.os.Trace;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppOpsCheckingServiceTracingDecorator implements AppOpsCheckingServiceInterface {
    public final AppOpsCheckingServiceInterface mService;

    public AppOpsCheckingServiceTracingDecorator(
            AppOpsCheckingServiceInterface appOpsCheckingServiceInterface) {
        this.mService = appOpsCheckingServiceInterface;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final boolean addAppOpsModeChangedListener(
            AppOpsService.AnonymousClass2 anonymousClass2) {
        Trace.traceBegin(
                64L,
                "TaggedTracingAppOpsCheckingServiceInterfaceImpl#addAppOpsModeChangedListener");
        try {
            return this.mService.addAppOpsModeChangedListener(anonymousClass2);
        } finally {
            Trace.traceEnd(64L);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void clearAllModes() {
        Trace.traceBegin(64L, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#clearAllModes");
        try {
            this.mService.clearAllModes();
        } finally {
            Trace.traceEnd(64L);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final SparseBooleanArray getForegroundOps(int i) {
        Trace.traceBegin(64L, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#getForegroundOps");
        try {
            return this.mService.getForegroundOps(i);
        } finally {
            Trace.traceEnd(64L);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final SparseBooleanArray getForegroundOps(int i, String str) {
        Trace.traceBegin(64L, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#getForegroundOps");
        try {
            return this.mService.getForegroundOps(i, str);
        } finally {
            Trace.traceEnd(64L);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final SparseIntArray getNonDefaultPackageModes(int i, String str) {
        Trace.traceBegin(
                64L, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#getNonDefaultPackageModes");
        try {
            return this.mService.getNonDefaultPackageModes(i, str);
        } finally {
            Trace.traceEnd(64L);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final SparseIntArray getNonDefaultUidModes(int i) {
        Trace.traceBegin(
                64L, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#getNonDefaultUidModes");
        try {
            return this.mService.getNonDefaultUidModes(i);
        } finally {
            Trace.traceEnd(64L);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final int getPackageMode(int i, int i2, String str) {
        Trace.traceBegin(64L, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#getPackageMode");
        try {
            return this.mService.getPackageMode(i, i2, str);
        } finally {
            Trace.traceEnd(64L);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final int getUidMode(int i, int i2, String str) {
        Trace.traceBegin(64L, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#getUidMode");
        try {
            return this.mService.getUidMode(i, i2, str);
        } finally {
            Trace.traceEnd(64L);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void readState() {
        Trace.traceBegin(64L, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#readState");
        try {
            this.mService.readState();
        } finally {
            Trace.traceEnd(64L);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final boolean removePackage(int i, String str) {
        Trace.traceBegin(64L, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#removePackage");
        try {
            return this.mService.removePackage(i, str);
        } finally {
            Trace.traceEnd(64L);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void removeUid(int i) {
        Trace.traceBegin(64L, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#removeUid");
        try {
            this.mService.removeUid(i);
        } finally {
            Trace.traceEnd(64L);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void setPackageMode(int i, int i2, int i3, String str) {
        Trace.traceBegin(64L, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#setPackageMode");
        try {
            this.mService.setPackageMode(i, i2, i3, str);
        } finally {
            Trace.traceEnd(64L);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final boolean setUidMode(int i, int i2, int i3) {
        Trace.traceBegin(64L, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#setUidMode");
        try {
            return this.mService.setUidMode(i, i2, i3);
        } finally {
            Trace.traceEnd(64L);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void shutdown() {
        Trace.traceBegin(64L, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#shutdown");
        try {
            this.mService.shutdown();
        } finally {
            Trace.traceEnd(64L);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void systemReady() {
        Trace.traceBegin(64L, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#systemReady");
        try {
            this.mService.systemReady();
        } finally {
            Trace.traceEnd(64L);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void writeState() {
        Trace.traceBegin(64L, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#writeState");
        try {
            this.mService.writeState();
        } finally {
            Trace.traceEnd(64L);
        }
    }
}
