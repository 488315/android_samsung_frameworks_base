package com.android.server.policy;

import android.content.ComponentName;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.wm.DisplayPolicy;
import com.android.server.wm.WindowState;
import java.util.HashMap;
import java.util.HashSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SystemKeyManager {
    public static final int[] SUPPORT_KEYCODE = {26, 3, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED, 6, 1064, 224};
    public String mFocusedWindow;
    public boolean mIsActivatedHomeKey;
    public boolean mIsActivatedRecentKey;
    public boolean mMetaKeyPass;
    public HashSet mMetaKeyRequestedComponents;
    public PhoneWindowManager mPolicy;
    public SparseArray mSystemKeyInfoMap;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SystemKeyInfo {
        public final ComponentName componentName;
        public final int keyCode;
        public final boolean keyPressOld;
        public final int press;

        public SystemKeyInfo(int i, int i2, ComponentName componentName) {
            this.keyCode = i;
            this.press = i2;
            this.componentName = componentName;
        }

        public SystemKeyInfo(int i, ComponentName componentName) {
            this.keyCode = i;
            this.keyPressOld = true;
            this.componentName = componentName;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("keyCode=");
            sb.append(this.keyCode);
            if (this.keyPressOld) {
                sb.append(" keyPressOld");
            } else {
                sb.append(" press=");
                sb.append(SystemKeyManager.keyPressToString(this.press));
            }
            sb.append(" componentName=");
            sb.append(this.componentName.toString());
            return sb.toString();
        }
    }

    public static boolean checkValidRequestedDefaultInfo(int i, int i2, ComponentName componentName) {
        if (componentName == null) {
            throw new IllegalArgumentException("requested component name is null.");
        }
        if (i == 3 || i == 6 || i == 26 || i == 187 || i == 224 || i == 1064) {
            return true;
        }
        if (i2 != -1) {
            throw new IllegalArgumentException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "requested keyCode was wrong. The keyCode(", ") does not supported."));
        }
        FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(i, "requested keyCode was wrong. The keyCode(", ") does not supported.", "SystemKeyManager");
        return false;
    }

    public static String keyPressToString(int i) {
        StringBuilder sb = new StringBuilder();
        if ((i & 3) != 0) {
            sb.append("KEY_PRESS_SINGLE");
        }
        if ((i & 4) != 0) {
            if (sb.length() != 0) {
                sb.append(" | ");
            }
            sb.append("KEY_PRESS_LONG");
        }
        if ((i & 8) != 0) {
            if (sb.length() != 0) {
                sb.append(" | ");
            }
            sb.append("KEY_PRESS_DOUBLE");
        }
        if (sb.length() == 0) {
            sb.append("0");
        }
        return sb.toString();
    }

    public final String findFocusedWindow(int i) {
        WindowState windowState;
        String str = null;
        if (i == 3 || i == 26 || i == 187 || i == 224) {
            DisplayPolicy displayPolicy = this.mPolicy.mDefaultDisplayPolicy;
            WindowState windowState2 = displayPolicy.mExt.mFakeFocusedWindow;
            if (windowState2 != null && ((windowState = displayPolicy.mFocusedWindow) == null || windowState2.mBaseLayer > windowState.mBaseLayer)) {
                str = windowState2.mAttrs.getTitle().toString();
            }
        }
        return !TextUtils.isEmpty(str) ? str : this.mFocusedWindow;
    }

    public final boolean hasSystemKeyInfo(int i, int i2) {
        String str = KeyCustomizationConstants.VOLD_DECRYPT;
        if (PhoneWindowManager.DEBUG_INPUT) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "hasSystemKeyInfo() is called keyCode=", " press=");
            m.append(keyPressToString(i2));
            m.append(" focusedWindow=");
            m.append(this.mFocusedWindow);
            Slog.v("SystemKeyManager", m.toString());
        }
        return hasSystemKeyInfoWithFocusedWindow(i, i2, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x006a, code lost:
    
        if ((r9 & r5.press) != 0) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean hasSystemKeyInfoWithFocusedWindow(int r8, int r9, boolean r10) {
        /*
            r7 = this;
            java.lang.String r0 = "requested systemKeyInfo size="
            java.lang.String r1 = "hasPress() : keyCode="
            java.lang.String r2 = com.android.server.policy.KeyCustomizationConstants.VOLD_DECRYPT
            boolean r2 = com.android.server.policy.PhoneWindowManager.DEBUG_INPUT
            if (r2 == 0) goto L23
            java.lang.String r2 = "SystemKeyManager"
            java.lang.String r3 = "hasSystemKeyInfoWithFocusedWindow() is called keyCode="
            java.lang.String r4 = " press="
            java.lang.StringBuilder r3 = com.android.server.BatteryService$$ExternalSyntheticOutline0.m(r8, r3, r4)
            java.lang.String r4 = keyPressToString(r9)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Slog.v(r2, r3)
        L23:
            monitor-enter(r7)
            android.util.SparseArray r2 = r7.mSystemKeyInfoMap     // Catch: java.lang.Throwable -> L4a
            java.lang.Object r2 = r2.get(r8)     // Catch: java.lang.Throwable -> L4a
            java.util.HashMap r2 = (java.util.HashMap) r2     // Catch: java.lang.Throwable -> L4a
            r3 = 0
            if (r2 == 0) goto La7
            int r4 = r2.size()     // Catch: java.lang.Throwable -> L4a
            if (r4 != 0) goto L37
            goto La7
        L37:
            java.lang.String r4 = r7.findFocusedWindow(r8)     // Catch: java.lang.Throwable -> L4a
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch: java.lang.Throwable -> L4a
            if (r5 == 0) goto L4c
            java.lang.String r8 = "SystemKeyManager"
            java.lang.String r9 = "isSystemKeyEventRequested() : focusedWindow is empty."
            android.util.Slog.i(r8, r9)     // Catch: java.lang.Throwable -> L4a
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L4a
            return r3
        L4a:
            r8 = move-exception
            goto La9
        L4c:
            java.lang.Object r5 = r2.get(r4)     // Catch: java.lang.Throwable -> L4a
            com.android.server.policy.SystemKeyManager$SystemKeyInfo r5 = (com.android.server.policy.SystemKeyManager.SystemKeyInfo) r5     // Catch: java.lang.Throwable -> L4a
            if (r5 != 0) goto L55
            goto L88
        L55:
            boolean r6 = r5.keyPressOld     // Catch: java.lang.Throwable -> L4a
            if (r6 == 0) goto L64
            int r10 = r5.keyCode     // Catch: java.lang.Throwable -> L4a
            r5 = 26
            if (r10 != r5) goto L6c
            r9 = r9 & 4
            if (r9 == 0) goto L6c
            goto L88
        L64:
            if (r10 == 0) goto L67
            goto L6c
        L67:
            int r10 = r5.press     // Catch: java.lang.Throwable -> L4a
            r9 = r9 & r10
            if (r9 == 0) goto L88
        L6c:
            java.lang.String r9 = "SystemKeyManager"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4a
            r10.<init>(r1)     // Catch: java.lang.Throwable -> L4a
            r10.append(r8)     // Catch: java.lang.Throwable -> L4a
            java.lang.String r8 = " focusedWindow="
            r10.append(r8)     // Catch: java.lang.Throwable -> L4a
            r10.append(r4)     // Catch: java.lang.Throwable -> L4a
            java.lang.String r8 = r10.toString()     // Catch: java.lang.Throwable -> L4a
            android.util.Slog.i(r9, r8)     // Catch: java.lang.Throwable -> L4a
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L4a
            r7 = 1
            return r7
        L88:
            java.lang.String r8 = "SystemKeyManager"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4a
            r9.<init>(r0)     // Catch: java.lang.Throwable -> L4a
            int r10 = r2.size()     // Catch: java.lang.Throwable -> L4a
            r9.append(r10)     // Catch: java.lang.Throwable -> L4a
            java.lang.String r10 = " focusedWindow="
            r9.append(r10)     // Catch: java.lang.Throwable -> L4a
            r9.append(r4)     // Catch: java.lang.Throwable -> L4a
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L4a
            android.util.Log.i(r8, r9)     // Catch: java.lang.Throwable -> L4a
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L4a
            return r3
        La7:
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L4a
            return r3
        La9:
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L4a
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.policy.SystemKeyManager.hasSystemKeyInfoWithFocusedWindow(int, int, boolean):boolean");
    }

    public final boolean isSystemKeyEventRequested(int i, ComponentName componentName) {
        String str = KeyCustomizationConstants.VOLD_DECRYPT;
        boolean z = PhoneWindowManager.DEBUG_INPUT;
        if (z) {
            Slog.v("SystemKeyManager", "isSystemKeyEventRequested() is called keyCode=" + i + " componentName=" + componentName);
        }
        if (componentName == null) {
            return false;
        }
        synchronized (this) {
            try {
                HashMap hashMap = (HashMap) this.mSystemKeyInfoMap.get(i);
                if (hashMap == null) {
                    return false;
                }
                String findFocusedWindow = findFocusedWindow(i);
                if (TextUtils.isEmpty(findFocusedWindow)) {
                    if (z) {
                        Slog.i("SystemKeyManager", "isSystemKeyEventRequested() : focusedWindow is empty.");
                    }
                    return false;
                }
                SystemKeyInfo systemKeyInfo = (SystemKeyInfo) hashMap.get(findFocusedWindow);
                if (systemKeyInfo == null) {
                    return false;
                }
                return systemKeyInfo.keyPressOld;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void notifyRequestedSystemKey() {
        boolean z;
        StatusBarManagerInternal statusBarManagerInternal = this.mPolicy.getStatusBarManagerInternal();
        if (statusBarManagerInternal == null) {
            return;
        }
        boolean z2 = true;
        boolean hasSystemKeyInfoWithFocusedWindow = hasSystemKeyInfoWithFocusedWindow(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED, 0, true);
        if (this.mIsActivatedRecentKey != hasSystemKeyInfoWithFocusedWindow) {
            this.mIsActivatedRecentKey = hasSystemKeyInfoWithFocusedWindow;
            z = true;
        } else {
            z = false;
        }
        boolean hasSystemKeyInfoWithFocusedWindow2 = hasSystemKeyInfoWithFocusedWindow(3, 0, true);
        if (this.mIsActivatedHomeKey != hasSystemKeyInfoWithFocusedWindow2) {
            this.mIsActivatedHomeKey = hasSystemKeyInfoWithFocusedWindow2;
        } else {
            z2 = z;
        }
        if (z2) {
            boolean z3 = this.mIsActivatedRecentKey;
            boolean z4 = this.mIsActivatedHomeKey;
            StatusBarManagerService.AnonymousClass2 anonymousClass2 = (StatusBarManagerService.AnonymousClass2) statusBarManagerInternal;
            Slog.d("StatusBarManagerService", "notifyRequestedSystemKey recent=" + z3 + " home=" + z4);
            if (StatusBarManagerService.this.mBar == null) {
                return;
            }
            try {
                StatusBarManagerService.this.mBar.notifyRequestedSystemKey(z3, z4);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void updateFocusedWindow(String str) {
        String str2 = KeyCustomizationConstants.VOLD_DECRYPT;
        boolean z = PhoneWindowManager.DEBUG_INPUT;
        Slog.v("SystemKeyManager", "updateFocusedWindow() is called, " + str);
        synchronized (this) {
            try {
                this.mFocusedWindow = str;
                if (this.mMetaKeyRequestedComponents.contains(str)) {
                    this.mMetaKeyPass = true;
                } else if (this.mMetaKeyPass) {
                    this.mMetaKeyPass = false;
                }
                if (this.mSystemKeyInfoMap.size() != 0) {
                    notifyRequestedSystemKey();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
