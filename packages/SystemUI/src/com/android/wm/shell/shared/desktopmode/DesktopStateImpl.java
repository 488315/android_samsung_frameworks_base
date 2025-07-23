package com.android.wm.shell.shared.desktopmode;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Debug;
import android.util.Slog;
import android.view.Display;
import android.view.WindowManager;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopStateImpl implements DesktopState {
    public static final Companion Companion = new Companion(null);
    public static int desktopExternalDisplayId = -1;
    public static boolean inDesktopWindowing;
    public final boolean canEnterDesktopMode;
    public final boolean canInternalDisplayHostDesktops;
    public final Context context;
    public final DisplayManager displayManager;
    public final boolean enforceDeviceRestrictions;
    public final boolean enterDesktopByDefaultOnFreeformDisplay;
    public final boolean isFreeformEnabled;
    public WindowManager windowManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static DWExternalDisplayMode getDesktopExternalDisplayMode() {
            return DesktopStateImpl.inDesktopWindowing ? DesktopStateImpl.desktopExternalDisplayId == -1 ? DWExternalDisplayMode.DW_EXTERNAL_DISPLAY_STAND_ALONE : DWExternalDisplayMode.DW_EXTERNAL_DISPLAY_EXTENDED : DesktopStateImpl.desktopExternalDisplayId == -1 ? DWExternalDisplayMode.DW_EXTERNAL_DISPLAY_UNDEFINED : DWExternalDisplayMode.DW_EXTERNAL_DISPLAY_PROJECTED;
        }

        public static boolean inDesktopWindowing(int i) {
            return (i == 0 && DesktopStateImpl.inDesktopWindowing) || i == DesktopStateImpl.desktopExternalDisplayId;
        }

        public static void setInDesktopWindowing(boolean z) {
            if (DesktopStateImpl.inDesktopWindowing != z) {
                DesktopStateImpl.inDesktopWindowing = z;
                MultiWindowManager.getInstance().setInDesktopWindowing(z);
                Slog.d("DesktopState", "setInDesktopWindowing called" + Debug.getCallers(5));
            }
        }

        private Companion() {
        }

        public static /* synthetic */ void getENFORCE_DEVICE_RESTRICTIONS_SYS_PROP$annotations() {
        }

        public static /* synthetic */ void getENTER_DESKTOP_BY_DEFAULT_ON_FREEFORM_DISPLAY_SYS_PROP$annotations() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DWExternalDisplayMode {
        public static final /* synthetic */ DWExternalDisplayMode[] $VALUES;
        public static final DWExternalDisplayMode DW_EXTERNAL_DISPLAY_EXTENDED;
        public static final DWExternalDisplayMode DW_EXTERNAL_DISPLAY_PROJECTED;
        public static final DWExternalDisplayMode DW_EXTERNAL_DISPLAY_STAND_ALONE;
        public static final DWExternalDisplayMode DW_EXTERNAL_DISPLAY_UNDEFINED;

        static {
            DWExternalDisplayMode dWExternalDisplayMode = new DWExternalDisplayMode("DW_EXTERNAL_DISPLAY_UNDEFINED", 0);
            DW_EXTERNAL_DISPLAY_UNDEFINED = dWExternalDisplayMode;
            DWExternalDisplayMode dWExternalDisplayMode2 = new DWExternalDisplayMode("DW_EXTERNAL_DISPLAY_PROJECTED", 1);
            DW_EXTERNAL_DISPLAY_PROJECTED = dWExternalDisplayMode2;
            DWExternalDisplayMode dWExternalDisplayMode3 = new DWExternalDisplayMode("DW_EXTERNAL_DISPLAY_EXTENDED", 2);
            DW_EXTERNAL_DISPLAY_EXTENDED = dWExternalDisplayMode3;
            DWExternalDisplayMode dWExternalDisplayMode4 = new DWExternalDisplayMode("DW_EXTERNAL_DISPLAY_STAND_ALONE", 3);
            DW_EXTERNAL_DISPLAY_STAND_ALONE = dWExternalDisplayMode4;
            DWExternalDisplayMode[] dWExternalDisplayModeArr = {dWExternalDisplayMode, dWExternalDisplayMode2, dWExternalDisplayMode3, dWExternalDisplayMode4};
            $VALUES = dWExternalDisplayModeArr;
            EnumEntriesKt.enumEntries(dWExternalDisplayModeArr);
        }

        private DWExternalDisplayMode(String str, int i) {
        }

        public static DWExternalDisplayMode valueOf(String str) {
            return (DWExternalDisplayMode) Enum.valueOf(DWExternalDisplayMode.class, str);
        }

        public static DWExternalDisplayMode[] values() {
            return (DWExternalDisplayMode[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x010c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DesktopStateImpl(android.content.Context r10) {
        /*
            Method dump skipped, instructions count: 278
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.shared.desktopmode.DesktopStateImpl.<init>(android.content.Context):void");
    }

    public final boolean isDesktopModeSupportedOnDisplay(int i) {
        Display display = this.displayManager.getDisplay(i);
        if (display != null) {
            return isDesktopModeSupportedOnDisplay(display);
        }
        return false;
    }

    public final boolean isDesktopModeSupportedOnDisplay(Display display) {
        if (!this.canEnterDesktopMode) {
            return false;
        }
        if (!this.enforceDeviceRestrictions) {
            return true;
        }
        if (CoreRune.DW_MULTI_FOLD_POLICY && display.getType() == 1 && display.getDisplayId() == 1) {
            return false;
        }
        if (display.getType() == 1) {
            return this.canInternalDisplayHostDesktops;
        }
        if (display.getType() != 2 && display.getType() != 4 && (display.getFlags() & 67108864) == 0) {
            return false;
        }
        if (this.windowManager == null) {
            this.windowManager = (WindowManager) this.context.getSystemService(WindowManager.class);
        }
        WindowManager windowManager = this.windowManager;
        if (windowManager != null) {
            return windowManager.isEligibleForDesktopMode(display.getDisplayId());
        }
        return false;
    }
}
