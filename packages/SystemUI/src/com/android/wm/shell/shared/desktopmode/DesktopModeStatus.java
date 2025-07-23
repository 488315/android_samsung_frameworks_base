package com.android.wm.shell.shared.desktopmode;

import android.os.SystemProperties;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DesktopModeStatus {
    public static final boolean ENFORCE_DEVICE_RESTRICTIONS;
    static final String ENFORCE_DEVICE_RESTRICTIONS_PROPERTY = "persist.wm.debug.desktop_mode_enforce_device_restrictions";

    static {
        SystemProperties.getBoolean("persist.wm.debug.desktop_veiled_resizing", true);
        SystemProperties.getBoolean("persist.wm.debug.desktop_change_display", false);
        SystemProperties.getBoolean("persist.wm.debug.desktop_use_window_shadows", true);
        SystemProperties.getBoolean("persist.wm.debug.desktop_use_window_shadows_focused_window", false);
        SystemProperties.getBoolean("persist.wm.debug.desktop_use_rounded_corners", true);
        ENFORCE_DEVICE_RESTRICTIONS = SystemProperties.getBoolean(ENFORCE_DEVICE_RESTRICTIONS_PROPERTY, true);
        SystemProperties.getBoolean("persist.wm.debug.use_app_to_web_build_time_generic_links", true);
        SystemProperties.getBoolean("persist.wm.debug.desktop_mode_density_enabled", false);
        SystemProperties.getInt("persist.wm.debug.desktop_mode_density", IKnoxCustomManager.Stub.TRANSACTION_registerSystemUiCallback);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0052, code lost:
    
        if (android.window.DesktopModeFlags.isDesktopModeForcedEnabled() == false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0058, code lost:
    
        if (enforceDeviceRestrictions() != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0063, code lost:
    
        if (r7.getResources().getBoolean(android.R.bool.config_mobile_data_capable) == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x006d, code lost:
    
        if (r7.getResources().getBoolean(android.R.bool.config_cbrs_supported) == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0078, code lost:
    
        if (r7.getResources().getBoolean(android.R.bool.config_mms_content_disposition_support) == false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x007c, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x007b, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x004c, code lost:
    
        if (android.window.DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_MODE.isTrue() == false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0044, code lost:
    
        if (r6 == false) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean canEnterDesktopMode(android.content.Context r7) {
        /*
            boolean r0 = enforceDeviceRestrictions()
            r1 = 17891796(0x11101d4, float:2.6633606E-38)
            r2 = 17891797(0x11101d5, float:2.6633608E-38)
            r3 = 17891440(0x1110070, float:2.6632608E-38)
            r4 = 0
            r5 = 1
            if (r0 != 0) goto L12
            goto L46
        L12:
            android.window.DesktopExperienceFlags r0 = android.window.DesktopExperienceFlags.ENABLE_PROJECTED_DISPLAY_DESKTOP_MODE
            boolean r0 = r0.isTrue()
            if (r0 == 0) goto L23
            android.content.res.Resources r0 = r7.getResources()
            boolean r0 = r0.getBoolean(r2)
            goto L3a
        L23:
            android.content.res.Resources r0 = r7.getResources()
            boolean r0 = r0.getBoolean(r2)
            if (r0 == 0) goto L39
            android.content.res.Resources r0 = r7.getResources()
            boolean r0 = r0.getBoolean(r3)
            if (r0 == 0) goto L39
            r0 = r5
            goto L3a
        L39:
            r0 = r4
        L3a:
            android.content.res.Resources r6 = r7.getResources()
            boolean r6 = r6.getBoolean(r1)
            if (r0 != 0) goto L46
            if (r6 == 0) goto L4e
        L46:
            android.window.DesktopModeFlags r0 = android.window.DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_MODE
            boolean r0 = r0.isTrue()
            if (r0 != 0) goto L7c
        L4e:
            boolean r0 = android.window.DesktopModeFlags.isDesktopModeForcedEnabled()
            if (r0 == 0) goto L7b
            boolean r0 = enforceDeviceRestrictions()
            if (r0 != 0) goto L5b
            goto L7c
        L5b:
            android.content.res.Resources r0 = r7.getResources()
            boolean r0 = r0.getBoolean(r2)
            if (r0 == 0) goto L70
            android.content.res.Resources r0 = r7.getResources()
            boolean r0 = r0.getBoolean(r3)
            if (r0 == 0) goto L70
            goto L7c
        L70:
            android.content.res.Resources r7 = r7.getResources()
            boolean r7 = r7.getBoolean(r1)
            if (r7 == 0) goto L7b
            goto L7c
        L7b:
            return r4
        L7c:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.shared.desktopmode.DesktopModeStatus.canEnterDesktopMode(android.content.Context):boolean");
    }

    public static boolean enforceDeviceRestrictions() {
        return ENFORCE_DEVICE_RESTRICTIONS;
    }
}
