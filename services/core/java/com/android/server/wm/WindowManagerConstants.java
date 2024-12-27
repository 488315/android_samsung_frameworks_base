package com.android.server.wm;

import android.provider.DeviceConfig;
import android.provider.DeviceConfigInterface;

import com.android.server.am.ActivityManagerConstants$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WindowManagerConstants {
    public final DeviceConfigInterface mDeviceConfig;
    public final WindowManagerGlobalLock mGlobalLock;
    public final WindowManagerConstants$$ExternalSyntheticLambda0 mListenerAndroid;
    public final WindowManagerConstants$$ExternalSyntheticLambda0 mListenerWindowManager;
    public boolean mSystemGestureExcludedByPreQStickyImmersive;
    public int mSystemGestureExclusionLimitDp;
    public long mSystemGestureExclusionLogDebounceTimeoutMillis;
    public final Runnable mUpdateSystemGestureExclusionCallback;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.wm.WindowManagerConstants$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.wm.WindowManagerConstants$$ExternalSyntheticLambda0] */
    public WindowManagerConstants(
            WindowManagerGlobalLock windowManagerGlobalLock,
            Runnable runnable,
            DeviceConfigInterface deviceConfigInterface) {
        Objects.requireNonNull(windowManagerGlobalLock);
        this.mGlobalLock = windowManagerGlobalLock;
        Objects.requireNonNull(runnable);
        this.mUpdateSystemGestureExclusionCallback = runnable;
        this.mDeviceConfig = deviceConfigInterface;
        final int i = 0;
        this.mListenerAndroid =
                new DeviceConfig.OnPropertiesChangedListener(this) { // from class:
                    // com.android.server.wm.WindowManagerConstants$$ExternalSyntheticLambda0
                    public final /* synthetic */ WindowManagerConstants f$0;

                    {
                        this.f$0 = this;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:52:0x00a6  */
                    /* JADX WARN: Removed duplicated region for block: B:61:0x00b8 A[SYNTHETIC] */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onPropertiesChanged(
                            android.provider.DeviceConfig.Properties r8) {
                        /*
                            r7 = this;
                            int r0 = r2
                            com.android.server.wm.WindowManagerConstants r7 = r7.f$0
                            switch(r0) {
                                case 0: goto L58;
                                default: goto L7;
                            }
                        L7:
                            com.android.server.wm.WindowManagerGlobalLock r0 = r7.mGlobalLock
                            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection()
                            monitor-enter(r0)
                            java.util.Set r8 = r8.getKeyset()     // Catch: java.lang.Throwable -> L28
                            java.util.Iterator r8 = r8.iterator()     // Catch: java.lang.Throwable -> L28
                        L15:
                            boolean r1 = r8.hasNext()     // Catch: java.lang.Throwable -> L28
                            if (r1 == 0) goto L4e
                            java.lang.Object r1 = r8.next()     // Catch: java.lang.Throwable -> L28
                            java.lang.String r1 = (java.lang.String) r1     // Catch: java.lang.Throwable -> L28
                            if (r1 != 0) goto L2a
                            monitor-exit(r0)     // Catch: java.lang.Throwable -> L28
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
                            goto L52
                        L28:
                            r7 = move-exception
                            goto L53
                        L2a:
                            int r2 = r1.hashCode()     // Catch: java.lang.Throwable -> L28
                            r3 = -125834358(0xfffffffff87feb8a, float:-2.0762703E34)
                            if (r2 == r3) goto L34
                            goto L15
                        L34:
                            java.lang.String r2 = "system_gesture_exclusion_log_debounce_millis"
                            boolean r1 = r1.equals(r2)     // Catch: java.lang.Throwable -> L28
                            if (r1 == 0) goto L15
                            android.provider.DeviceConfigInterface r1 = r7.mDeviceConfig     // Catch: java.lang.Throwable -> L28
                            java.lang.String r2 = "system_gesture_exclusion_log_debounce_millis"
                            java.lang.String r3 = "window_manager"
                            r4 = 0
                            long r1 = r1.getLong(r3, r2, r4)     // Catch: java.lang.Throwable -> L28
                            r7.mSystemGestureExclusionLogDebounceTimeoutMillis = r1     // Catch: java.lang.Throwable -> L28
                            goto L15
                        L4e:
                            monitor-exit(r0)     // Catch: java.lang.Throwable -> L28
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
                        L52:
                            return
                        L53:
                            monitor-exit(r0)     // Catch: java.lang.Throwable -> L28
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
                            throw r7
                        L58:
                            com.android.server.wm.WindowManagerGlobalLock r0 = r7.mGlobalLock
                            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection()
                            monitor-enter(r0)
                            java.util.Set r8 = r8.getKeyset()     // Catch: java.lang.Throwable -> L7b
                            java.util.Iterator r8 = r8.iterator()     // Catch: java.lang.Throwable -> L7b
                            r1 = 0
                            r2 = r1
                        L68:
                            boolean r3 = r8.hasNext()     // Catch: java.lang.Throwable -> L7b
                            if (r3 == 0) goto Lcc
                            java.lang.Object r3 = r8.next()     // Catch: java.lang.Throwable -> L7b
                            java.lang.String r3 = (java.lang.String) r3     // Catch: java.lang.Throwable -> L7b
                            if (r3 != 0) goto L7d
                            monitor-exit(r0)     // Catch: java.lang.Throwable -> L7b
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
                            goto Ld7
                        L7b:
                            r7 = move-exception
                            goto Ld8
                        L7d:
                            int r4 = r3.hashCode()     // Catch: java.lang.Throwable -> L7b
                            r5 = -1271675449(0xffffffffb433c5c7, float:-1.6742625E-7)
                            r6 = 1
                            if (r4 == r5) goto L98
                            r5 = 316878247(0x12e32da7, float:1.4336968E-27)
                            if (r4 == r5) goto L8d
                            goto La3
                        L8d:
                            java.lang.String r4 = "system_gesture_exclusion_limit_dp"
                            boolean r3 = r3.equals(r4)     // Catch: java.lang.Throwable -> L7b
                            if (r3 == 0) goto La3
                            r3 = r1
                            goto La4
                        L98:
                            java.lang.String r4 = "system_gestures_excluded_by_pre_q_sticky_immersive"
                            boolean r3 = r3.equals(r4)     // Catch: java.lang.Throwable -> L7b
                            if (r3 == 0) goto La3
                            r3 = r6
                            goto La4
                        La3:
                            r3 = -1
                        La4:
                            if (r3 == 0) goto Lb8
                            if (r3 == r6) goto La9
                            goto L68
                        La9:
                            android.provider.DeviceConfigInterface r2 = r7.mDeviceConfig     // Catch: java.lang.Throwable -> L7b
                            java.lang.String r3 = "system_gestures_excluded_by_pre_q_sticky_immersive"
                            java.lang.String r4 = "android"
                            boolean r2 = r2.getBoolean(r4, r3, r1)     // Catch: java.lang.Throwable -> L7b
                            r7.mSystemGestureExcludedByPreQStickyImmersive = r2     // Catch: java.lang.Throwable -> L7b
                        Lb6:
                            r2 = r6
                            goto L68
                        Lb8:
                            android.provider.DeviceConfigInterface r2 = r7.mDeviceConfig     // Catch: java.lang.Throwable -> L7b
                            java.lang.String r3 = "system_gesture_exclusion_limit_dp"
                            java.lang.String r4 = "android"
                            int r2 = r2.getInt(r4, r3, r1)     // Catch: java.lang.Throwable -> L7b
                            r3 = 200(0xc8, float:2.8E-43)
                            int r2 = java.lang.Math.max(r3, r2)     // Catch: java.lang.Throwable -> L7b
                            r7.mSystemGestureExclusionLimitDp = r2     // Catch: java.lang.Throwable -> L7b
                            goto Lb6
                        Lcc:
                            if (r2 == 0) goto Ld3
                            java.lang.Runnable r7 = r7.mUpdateSystemGestureExclusionCallback     // Catch: java.lang.Throwable -> L7b
                            r7.run()     // Catch: java.lang.Throwable -> L7b
                        Ld3:
                            monitor-exit(r0)     // Catch: java.lang.Throwable -> L7b
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
                        Ld7:
                            return
                        Ld8:
                            monitor-exit(r0)     // Catch: java.lang.Throwable -> L7b
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
                            throw r7
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.wm.WindowManagerConstants$$ExternalSyntheticLambda0.onPropertiesChanged(android.provider.DeviceConfig$Properties):void");
                    }
                };
        final int i2 = 1;
        this.mListenerWindowManager =
                new DeviceConfig.OnPropertiesChangedListener(this) { // from class:
                    // com.android.server.wm.WindowManagerConstants$$ExternalSyntheticLambda0
                    public final /* synthetic */ WindowManagerConstants f$0;

                    {
                        this.f$0 = this;
                    }

                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        /*
                            this = this;
                            int r0 = r2
                            com.android.server.wm.WindowManagerConstants r7 = r7.f$0
                            switch(r0) {
                                case 0: goto L58;
                                default: goto L7;
                            }
                        L7:
                            com.android.server.wm.WindowManagerGlobalLock r0 = r7.mGlobalLock
                            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection()
                            monitor-enter(r0)
                            java.util.Set r8 = r8.getKeyset()     // Catch: java.lang.Throwable -> L28
                            java.util.Iterator r8 = r8.iterator()     // Catch: java.lang.Throwable -> L28
                        L15:
                            boolean r1 = r8.hasNext()     // Catch: java.lang.Throwable -> L28
                            if (r1 == 0) goto L4e
                            java.lang.Object r1 = r8.next()     // Catch: java.lang.Throwable -> L28
                            java.lang.String r1 = (java.lang.String) r1     // Catch: java.lang.Throwable -> L28
                            if (r1 != 0) goto L2a
                            monitor-exit(r0)     // Catch: java.lang.Throwable -> L28
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
                            goto L52
                        L28:
                            r7 = move-exception
                            goto L53
                        L2a:
                            int r2 = r1.hashCode()     // Catch: java.lang.Throwable -> L28
                            r3 = -125834358(0xfffffffff87feb8a, float:-2.0762703E34)
                            if (r2 == r3) goto L34
                            goto L15
                        L34:
                            java.lang.String r2 = "system_gesture_exclusion_log_debounce_millis"
                            boolean r1 = r1.equals(r2)     // Catch: java.lang.Throwable -> L28
                            if (r1 == 0) goto L15
                            android.provider.DeviceConfigInterface r1 = r7.mDeviceConfig     // Catch: java.lang.Throwable -> L28
                            java.lang.String r2 = "system_gesture_exclusion_log_debounce_millis"
                            java.lang.String r3 = "window_manager"
                            r4 = 0
                            long r1 = r1.getLong(r3, r2, r4)     // Catch: java.lang.Throwable -> L28
                            r7.mSystemGestureExclusionLogDebounceTimeoutMillis = r1     // Catch: java.lang.Throwable -> L28
                            goto L15
                        L4e:
                            monitor-exit(r0)     // Catch: java.lang.Throwable -> L28
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
                        L52:
                            return
                        L53:
                            monitor-exit(r0)     // Catch: java.lang.Throwable -> L28
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
                            throw r7
                        L58:
                            com.android.server.wm.WindowManagerGlobalLock r0 = r7.mGlobalLock
                            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection()
                            monitor-enter(r0)
                            java.util.Set r8 = r8.getKeyset()     // Catch: java.lang.Throwable -> L7b
                            java.util.Iterator r8 = r8.iterator()     // Catch: java.lang.Throwable -> L7b
                            r1 = 0
                            r2 = r1
                        L68:
                            boolean r3 = r8.hasNext()     // Catch: java.lang.Throwable -> L7b
                            if (r3 == 0) goto Lcc
                            java.lang.Object r3 = r8.next()     // Catch: java.lang.Throwable -> L7b
                            java.lang.String r3 = (java.lang.String) r3     // Catch: java.lang.Throwable -> L7b
                            if (r3 != 0) goto L7d
                            monitor-exit(r0)     // Catch: java.lang.Throwable -> L7b
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
                            goto Ld7
                        L7b:
                            r7 = move-exception
                            goto Ld8
                        L7d:
                            int r4 = r3.hashCode()     // Catch: java.lang.Throwable -> L7b
                            r5 = -1271675449(0xffffffffb433c5c7, float:-1.6742625E-7)
                            r6 = 1
                            if (r4 == r5) goto L98
                            r5 = 316878247(0x12e32da7, float:1.4336968E-27)
                            if (r4 == r5) goto L8d
                            goto La3
                        L8d:
                            java.lang.String r4 = "system_gesture_exclusion_limit_dp"
                            boolean r3 = r3.equals(r4)     // Catch: java.lang.Throwable -> L7b
                            if (r3 == 0) goto La3
                            r3 = r1
                            goto La4
                        L98:
                            java.lang.String r4 = "system_gestures_excluded_by_pre_q_sticky_immersive"
                            boolean r3 = r3.equals(r4)     // Catch: java.lang.Throwable -> L7b
                            if (r3 == 0) goto La3
                            r3 = r6
                            goto La4
                        La3:
                            r3 = -1
                        La4:
                            if (r3 == 0) goto Lb8
                            if (r3 == r6) goto La9
                            goto L68
                        La9:
                            android.provider.DeviceConfigInterface r2 = r7.mDeviceConfig     // Catch: java.lang.Throwable -> L7b
                            java.lang.String r3 = "system_gestures_excluded_by_pre_q_sticky_immersive"
                            java.lang.String r4 = "android"
                            boolean r2 = r2.getBoolean(r4, r3, r1)     // Catch: java.lang.Throwable -> L7b
                            r7.mSystemGestureExcludedByPreQStickyImmersive = r2     // Catch: java.lang.Throwable -> L7b
                        Lb6:
                            r2 = r6
                            goto L68
                        Lb8:
                            android.provider.DeviceConfigInterface r2 = r7.mDeviceConfig     // Catch: java.lang.Throwable -> L7b
                            java.lang.String r3 = "system_gesture_exclusion_limit_dp"
                            java.lang.String r4 = "android"
                            int r2 = r2.getInt(r4, r3, r1)     // Catch: java.lang.Throwable -> L7b
                            r3 = 200(0xc8, float:2.8E-43)
                            int r2 = java.lang.Math.max(r3, r2)     // Catch: java.lang.Throwable -> L7b
                            r7.mSystemGestureExclusionLimitDp = r2     // Catch: java.lang.Throwable -> L7b
                            goto Lb6
                        Lcc:
                            if (r2 == 0) goto Ld3
                            java.lang.Runnable r7 = r7.mUpdateSystemGestureExclusionCallback     // Catch: java.lang.Throwable -> L7b
                            r7.run()     // Catch: java.lang.Throwable -> L7b
                        Ld3:
                            monitor-exit(r0)     // Catch: java.lang.Throwable -> L7b
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
                        Ld7:
                            return
                        Ld8:
                            monitor-exit(r0)     // Catch: java.lang.Throwable -> L7b
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
                            throw r7
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.wm.WindowManagerConstants$$ExternalSyntheticLambda0.onPropertiesChanged(android.provider.DeviceConfig$Properties):void");
                    }
                };
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER CONSTANTS (dumpsys window constants):");
        printWriter.print("  ");
        printWriter.print("system_gesture_exclusion_log_debounce_millis");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mSystemGestureExclusionLogDebounceTimeoutMillis,
                printWriter,
                "  ",
                "system_gesture_exclusion_limit_dp");
        printWriter.print("=");
        printWriter.println(this.mSystemGestureExclusionLimitDp);
        printWriter.print("  ");
        printWriter.print("system_gestures_excluded_by_pre_q_sticky_immersive");
        printWriter.print("=");
        printWriter.println(this.mSystemGestureExcludedByPreQStickyImmersive);
        printWriter.println();
    }
}
