package com.android.server.pm;

import android.app.ActivityManagerInternal;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.UserHandle;

import com.android.internal.pm.pkg.component.ParsedMainComponent;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.LocalServices;
import com.android.server.compat.CompatChange;
import com.android.server.compat.PlatformCompat;
import com.android.server.pm.resolution.ComponentResolverApi;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class SaferIntentUtils {
    public static final ThreadLocal DISABLE_ENFORCE_INTENTS_TO_MATCH_INTENT_FILTERS =
            ThreadLocal.withInitial(new SaferIntentUtils$$ExternalSyntheticLambda1());

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IntentArgs {
        public final int callingPid;
        public final int callingUid;
        public Intent intent;
        public final boolean isReceiver;
        public PlatformCompat platformCompat;
        public final boolean resolveForStart;
        public final String resolvedType;
        public Computer snapshot;

        public IntentArgs(boolean z, int i, int i2, Intent intent, String str, boolean z2) {
            this.isReceiver = z;
            this.intent = intent;
            this.resolvedType = str;
            this.resolveForStart = z2;
            this.callingUid = i;
            this.callingPid = z2 ? i2 : -1;
        }

        public final boolean isChangeEnabled(long j) {
            boolean z;
            PlatformCompat platformCompat = this.platformCompat;
            if (platformCompat == null) {
                return true;
            }
            PackageManager packageManager = platformCompat.mContext.getPackageManager();
            int i = this.callingUid;
            String[] packagesForUid = packageManager.getPackagesForUid(i);
            if (packagesForUid == null || packagesForUid.length == 0) {
                z =
                        ((CompatChange) platformCompat.mCompatConfig.mChanges.get(Long.valueOf(j)))
                                        == null
                                ? true
                                : !r8.getDisabled();
            } else {
                int userId = UserHandle.getUserId(i);
                z = true;
                for (String str : packagesForUid) {
                    z &=
                            platformCompat.isChangeEnabledInternalNoLogging(
                                    j, PlatformCompat.getApplicationInfo(str, userId));
                }
            }
            return z;
        }

        public final void reportEvent(int i, boolean z) {
            if (this.resolveForStart) {
                SaferIntentUtils.reportUnsafeIntentEvent(
                        i, this.callingUid, this.callingPid, this.intent, this.resolvedType, z);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0056, code lost:

       if (r8.intent.getAction() == null) goto L31;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0065, code lost:

       r6 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0063, code lost:

       if (r8.intent.getAction() == null) goto L31;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void blockNullAction(
            com.android.server.pm.SaferIntentUtils.IntentArgs r8, java.util.List r9) {
        /*
            int r0 = r8.callingUid
            boolean r0 = android.app.ActivityManager.canAccessUnexportedComponents(r0)
            if (r0 == 0) goto L9
            return
        L9:
            com.android.server.pm.Computer r0 = r8.snapshot
            boolean r1 = android.security.Flags.blockNullActionIntents()
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L1e
            r4 = 293560872(0x117f6228, double:1.45038342E-315)
            boolean r1 = r8.isChangeEnabled(r4)
            if (r1 == 0) goto L1e
            r1 = r3
            goto L1f
        L1e:
            r1 = r2
        L1f:
            int r4 = r9.size()
            int r4 = r4 - r3
            r5 = 0
        L25:
            if (r4 < 0) goto L8a
            java.lang.Object r6 = r9.get(r4)
            boolean r7 = r6 instanceof android.content.pm.ResolveInfo
            if (r7 == 0) goto L59
            android.content.pm.ResolveInfo r6 = (android.content.pm.ResolveInfo) r6
            if (r0 != 0) goto L34
            return
        L34:
            if (r5 != 0) goto L3a
            com.android.server.pm.resolution.ComponentResolverApi r5 = r0.getComponentResolver()
        L3a:
            android.content.pm.ComponentInfo r6 = r6.getComponentInfo()
            boolean r7 = r8.isReceiver
            com.android.internal.pm.pkg.component.ParsedMainComponent r6 = infoToComponent(r6, r5, r7)
            if (r6 == 0) goto L67
            java.util.List r6 = r6.getIntents()
            boolean r6 = r6.isEmpty()
            if (r6 != 0) goto L67
            android.content.Intent r6 = r8.intent
            java.lang.String r6 = r6.getAction()
            if (r6 != 0) goto L67
            goto L65
        L59:
            boolean r6 = r6 instanceof android.content.IntentFilter
            if (r6 == 0) goto L67
            android.content.Intent r6 = r8.intent
            java.lang.String r6 = r6.getAction()
            if (r6 != 0) goto L67
        L65:
            r6 = r2
            goto L68
        L67:
            r6 = r3
        L68:
            if (r6 != 0) goto L87
            r8.reportEvent(r3, r1)
            if (r1 == 0) goto L87
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "Blocking intent with null action: "
            r6.<init>(r7)
            android.content.Intent r7 = r8.intent
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            java.lang.String r7 = "PackageManager"
            android.util.Slog.w(r7, r6)
            r9.remove(r4)
        L87:
            int r4 = r4 + (-1)
            goto L25
        L8a:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.SaferIntentUtils.blockNullAction(com.android.server.pm.SaferIntentUtils$IntentArgs,"
                    + " java.util.List):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01a6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void enforceIntentFilterMatching(
            com.android.server.pm.SaferIntentUtils.IntentArgs r24, java.util.List r25) {
        /*
            Method dump skipped, instructions count: 429
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.SaferIntentUtils.enforceIntentFilterMatching(com.android.server.pm.SaferIntentUtils$IntentArgs,"
                    + " java.util.List):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0054 A[EDGE_INSN: B:18:0x0054->B:19:0x0054 BREAK  A[LOOP:0: B:9:0x0029->B:17:0x0050], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void filterNonExportedComponents(
            com.android.server.pm.SaferIntentUtils.IntentArgs r6, java.util.List r7) {
        /*
            if (r7 == 0) goto L5a
            android.content.Intent r0 = r6.intent
            java.lang.String r0 = r0.getPackage()
            if (r0 != 0) goto L5a
            android.content.Intent r0 = r6.intent
            android.content.ComponentName r0 = r0.getComponent()
            if (r0 != 0) goto L5a
            int r0 = r6.callingUid
            boolean r0 = android.app.ActivityManager.canAccessUnexportedComponents(r0)
            if (r0 == 0) goto L1b
            goto L5a
        L1b:
            r0 = 229362273(0xdabca61, double:1.133200195E-315)
            boolean r0 = r6.isChangeEnabled(r0)
            int r1 = r7.size()
            r2 = 1
            int r1 = r1 - r2
            r3 = 0
        L29:
            if (r1 < 0) goto L53
            java.lang.Object r4 = r7.get(r1)
            boolean r5 = r4 instanceof android.content.pm.ResolveInfo
            if (r5 == 0) goto L3e
            android.content.pm.ResolveInfo r4 = (android.content.pm.ResolveInfo) r4
            android.content.pm.ComponentInfo r4 = r4.getComponentInfo()
            boolean r4 = r4.exported
            if (r4 == 0) goto L49
            goto L50
        L3e:
            boolean r5 = r4 instanceof com.android.server.am.BroadcastFilter
            if (r5 == 0) goto L50
            com.android.server.am.BroadcastFilter r4 = (com.android.server.am.BroadcastFilter) r4
            boolean r4 = r4.exported
            if (r4 == 0) goto L49
            goto L50
        L49:
            if (r0 != 0) goto L4c
            goto L54
        L4c:
            r7.remove(r1)
            r3 = r2
        L50:
            int r1 = r1 + (-1)
            goto L29
        L53:
            r2 = r3
        L54:
            if (r2 == 0) goto L5a
            r7 = 2
            r6.reportEvent(r7, r0)
        L5a:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.SaferIntentUtils.filterNonExportedComponents(com.android.server.pm.SaferIntentUtils$IntentArgs,"
                    + " java.util.List):void");
    }

    public static ParsedMainComponent infoToComponent(
            ComponentInfo componentInfo, ComponentResolverApi componentResolverApi, boolean z) {
        if (componentInfo instanceof ActivityInfo) {
            return z
                    ? componentResolverApi.getReceiver(componentInfo.getComponentName())
                    : componentResolverApi.getActivity(componentInfo.getComponentName());
        }
        if (componentInfo instanceof ServiceInfo) {
            return componentResolverApi.getService(componentInfo.getComponentName());
        }
        throw new IllegalArgumentException("Unsupported component type");
    }

    public static void reportUnsafeIntentEvent(
            int i, int i2, int i3, Intent intent, String str, boolean z) {
        FrameworkStatsLog.write(
                FrameworkStatsLog.UNSAFE_INTENT_EVENT_REPORTED,
                i,
                i2,
                intent.getComponent() == null ? null : intent.getComponent().flattenToString(),
                intent.getPackage(),
                intent.getAction(),
                intent.getCategories() == null
                        ? new String[0]
                        : (String[])
                                intent.getCategories()
                                        .toArray(new SaferIntentUtils$$ExternalSyntheticLambda0()),
                str,
                intent.getScheme(),
                z);
        ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class))
                .triggerUnsafeIntentStrictMode(i3, i, intent);
    }
}
