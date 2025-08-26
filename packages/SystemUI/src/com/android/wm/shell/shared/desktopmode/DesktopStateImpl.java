package com.android.wm.shell.shared.desktopmode;

import android.R;
import android.app.TaskInfo;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.Debug;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

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

        public static boolean inNonResizableDesktopWindowing(TaskInfo taskInfo) {
            return !taskInfo.isResizeable && inDesktopWindowing(taskInfo.displayId);
        }

        public static void setDesktopExternalDisplayId(int i) {
            int i2 = DesktopStateImpl.desktopExternalDisplayId;
            if (i2 != i) {
                ExifInterface$$ExternalSyntheticOutline0.m(MutableObjectList$$ExternalSyntheticOutline0.m(i2, i, "setDesktopExternalDisplayId: ", " -> ", ", caller="), Debug.getCaller(), "DesktopState");
                DesktopStateImpl.desktopExternalDisplayId = i;
            }
        }

        public static void setInDesktopWindowing(boolean z) {
            if (DesktopStateImpl.inDesktopWindowing != z) {
                DesktopStateImpl.inDesktopWindowing = z;
                Log.d("DesktopState", "setInDesktopWindowing: " + z + ", caller=" + Debug.getCallers(5));
                MultiWindowManager.getInstance().setInDesktopWindowing(z);
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

    /* JADX WARN: Removed duplicated region for block: B:17:0x0061  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public DesktopStateImpl(Context context) throws Resources.NotFoundException {
        boolean z;
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService(WindowManager.class);
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        this.displayManager = displayManager;
        boolean z2 = true;
        boolean z3 = SystemProperties.getBoolean("persist.wm.debug.desktop_mode_enforce_device_restrictions", true);
        this.enforceDeviceRestrictions = z3;
        boolean z4 = context.getResources().getBoolean(R.bool.config_mms_content_disposition_support);
        boolean z5 = context.getResources().getBoolean(R.bool.config_mobile_data_capable);
        boolean z6 = context.getResources().getBoolean(R.bool.config_cbrs_supported);
        this.canInternalDisplayHostDesktops = z6;
        boolean z7 = DesktopModeFlags.isDesktopModeForcedEnabled() && (!z3 || ((z5 && z6) || z4));
        if (z3) {
            z = (DesktopExperienceFlags.ENABLE_PROJECTED_DISPLAY_DESKTOP_MODE.isTrue() ? z5 : z5 && z6) || z4;
        }
        this.canEnterDesktopMode = (z && DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_MODE.isTrue()) || z7;
        this.enterDesktopByDefaultOnFreeformDisplay = DesktopExperienceFlags.ENTER_DESKTOP_BY_DEFAULT_ON_FREEFORM_DISPLAYS.isTrue() && SystemProperties.getBoolean("persist.wm.debug.enter_desktop_by_default_on_freeform_display", context.getResources().getBoolean(R.bool.config_intrusiveNotificationLed));
        DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue();
        Display[] displays = displayManager.getDisplays("android.hardware.display.category.ALL_INCLUDING_DISABLED");
        if (displays != null) {
            ArrayList arrayList = new ArrayList();
            for (Display display : displays) {
                if (display.getType() == 1) {
                    arrayList.add(display);
                }
            }
            if (!arrayList.isEmpty()) {
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    if (((Display) obj).getMinSizeDimensionDp() >= 600.0f) {
                        break;
                    }
                }
            }
        }
        boolean zHasSystemFeature = this.context.getPackageManager().hasSystemFeature("android.software.freeform_window_management");
        boolean z8 = Settings.Global.getInt(this.context.getContentResolver(), "enable_freeform_support", 0) != 0;
        if (!zHasSystemFeature && !z8) {
            z2 = false;
        }
        this.isFreeformEnabled = z2;
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
        if (CoreRune.DW_MULTI_FOLD_POLICY && display.getType() == 1 && this.context.getResources().getConfiguration().semDisplayDeviceType == 5) {
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
