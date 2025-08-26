package android.hardware.usb;

import android.annotation.SystemApi;
import android.app.slice.Slice;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.util.Log;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.util.Preconditions;
import com.samsung.android.lock.LsConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes2.dex */
public final class UsbPort {
    public static final int ENABLE_LIMIT_POWER_TRANSFER_ERROR_INTERNAL = 1;
    public static final int ENABLE_LIMIT_POWER_TRANSFER_ERROR_NOT_SUPPORTED = 2;
    public static final int ENABLE_LIMIT_POWER_TRANSFER_ERROR_OTHER = 4;
    public static final int ENABLE_LIMIT_POWER_TRANSFER_ERROR_PORT_MISMATCH = 3;
    public static final int ENABLE_LIMIT_POWER_TRANSFER_SUCCESS = 0;
    public static final int ENABLE_USB_DATA_ERROR_INTERNAL = 1;
    public static final int ENABLE_USB_DATA_ERROR_NOT_SUPPORTED = 2;
    public static final int ENABLE_USB_DATA_ERROR_OTHER = 4;
    public static final int ENABLE_USB_DATA_ERROR_PORT_MISMATCH = 3;
    public static final int ENABLE_USB_DATA_SUCCESS = 0;
    public static final int ENABLE_USB_DATA_WHILE_DOCKED_ERROR_DATA_ENABLED = 4;
    public static final int ENABLE_USB_DATA_WHILE_DOCKED_ERROR_INTERNAL = 1;
    public static final int ENABLE_USB_DATA_WHILE_DOCKED_ERROR_NOT_SUPPORTED = 2;
    public static final int ENABLE_USB_DATA_WHILE_DOCKED_ERROR_OTHER = 5;
    public static final int ENABLE_USB_DATA_WHILE_DOCKED_ERROR_PORT_MISMATCH = 3;
    public static final int ENABLE_USB_DATA_WHILE_DOCKED_SUCCESS = 0;
    public static final int FLAG_ALT_MODE_TYPE_DISPLAYPORT = 1;
    private static final int NUM_DATA_ROLES = 3;
    private static final int POWER_ROLE_OFFSET = 0;
    public static final int RESET_USB_PORT_ERROR_INTERNAL = 1;
    public static final int RESET_USB_PORT_ERROR_NOT_SUPPORTED = 2;
    public static final int RESET_USB_PORT_ERROR_OTHER = 4;
    public static final int RESET_USB_PORT_ERROR_PORT_MISMATCH = 3;
    public static final int RESET_USB_PORT_SUCCESS = 0;
    private static final String TAG = "UsbPort";
    private static final AtomicInteger sUsbOperationCount = new AtomicInteger();
    private final String mId;
    private final int mSupportedAltModes;
    private final int mSupportedContaminantProtectionModes;
    private final int mSupportedModes;
    private final boolean mSupportsComplianceWarnings;
    private final boolean mSupportsEnableContaminantPresenceDetection;
    private final boolean mSupportsEnableContaminantPresenceProtection;
    private final UsbManager mUsbManager;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AltModeType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface EnableLimitPowerTransferStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface EnableUsbDataStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface EnableUsbDataWhileDockedStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface ResetUsbPortStatus {
    }

    public UsbPort(UsbManager usbManager, String str, int i, int i2, boolean z, boolean z2) {
        this(usbManager, str, i, i2, z, z2, false, 0);
    }

    public UsbPort(UsbManager usbManager, String str, int i, int i2, boolean z, boolean z2, boolean z3, int i3) {
        Objects.requireNonNull(str);
        Preconditions.checkFlagsArgument(i, 15);
        this.mUsbManager = usbManager;
        this.mId = str;
        this.mSupportedModes = i;
        this.mSupportedContaminantProtectionModes = i2;
        this.mSupportsEnableContaminantPresenceProtection = z;
        this.mSupportsEnableContaminantPresenceDetection = z2;
        this.mSupportsComplianceWarnings = z3;
        this.mSupportedAltModes = i3;
    }

    public String getId() {
        return this.mId;
    }

    public int getSupportedModes() {
        return this.mSupportedModes;
    }

    public int getSupportedContaminantProtectionModes() {
        return this.mSupportedContaminantProtectionModes;
    }

    public boolean supportsEnableContaminantPresenceProtection() {
        return this.mSupportsEnableContaminantPresenceProtection;
    }

    public boolean supportsEnableContaminantPresenceDetection() {
        return this.mSupportsEnableContaminantPresenceDetection;
    }

    public UsbPortStatus getStatus() {
        return this.mUsbManager.getPortStatus(this);
    }

    public boolean isModeChangeSupported() {
        return this.mUsbManager.isModeChangeSupported(this);
    }

    public boolean supportsComplianceWarnings() {
        return this.mSupportsComplianceWarnings;
    }

    public int getSupportedAltModesMask() {
        return this.mSupportedAltModes;
    }

    public boolean isAltModeSupported(int i) {
        return (this.mSupportedAltModes & i) == i;
    }

    public void setRoles(int i, int i2) {
        checkRoles(i, i2);
        this.mUsbManager.setPortRoles(this, i, i2);
    }

    public void resetUsbPort(Executor executor, Consumer<Integer> consumer) {
        int iIncrementAndGet = sUsbOperationCount.incrementAndGet() + Binder.getCallingUid();
        Log.i(TAG, "resetUsbPort opId:" + iIncrementAndGet);
        this.mUsbManager.resetUsbPort(this, iIncrementAndGet, new UsbOperationInternal(iIncrementAndGet, this.mId, executor, consumer));
    }

    public int enableUsbData(boolean z) {
        int iIncrementAndGet = sUsbOperationCount.incrementAndGet() + Binder.getCallingUid();
        Log.i(TAG, "enableUsbData opId:" + iIncrementAndGet + " callingUid:" + Binder.getCallingUid());
        UsbOperationInternal usbOperationInternal = new UsbOperationInternal(iIncrementAndGet, this.mId);
        boolean zEnableUsbData = this.mUsbManager.enableUsbData(this, z, iIncrementAndGet, usbOperationInternal);
        int i = 1;
        if (zEnableUsbData) {
            usbOperationInternal.waitForOperationComplete();
        }
        int status = usbOperationInternal.getStatus();
        if (status == 0) {
            return 0;
        }
        if (status != 1) {
            i = 2;
            if (status != 2) {
                i = 3;
                if (status != 3) {
                    return 4;
                }
            }
        }
        return i;
    }

    public int enableUsbDataWhileDocked() {
        int iIncrementAndGet = sUsbOperationCount.incrementAndGet() + Binder.getCallingUid();
        Log.i(TAG, "enableUsbData opId:" + iIncrementAndGet + " callingUid:" + Binder.getCallingUid());
        UsbPortStatus status = getStatus();
        if (status != null && (status.getUsbDataStatus() & 8) != 8) {
            return 4;
        }
        UsbOperationInternal usbOperationInternal = new UsbOperationInternal(iIncrementAndGet, this.mId);
        this.mUsbManager.enableUsbDataWhileDocked(this, iIncrementAndGet, usbOperationInternal);
        usbOperationInternal.waitForOperationComplete();
        int status2 = usbOperationInternal.getStatus();
        if (status2 == 0) {
            return 0;
        }
        int i = 1;
        if (status2 != 1) {
            i = 2;
            if (status2 != 2) {
                i = 3;
                if (status2 != 3) {
                    return 5;
                }
            }
        }
        return i;
    }

    public int enableLimitPowerTransfer(boolean z) {
        int iIncrementAndGet = sUsbOperationCount.incrementAndGet() + Binder.getCallingUid();
        Log.i(TAG, "enableLimitPowerTransfer opId:" + iIncrementAndGet + " callingUid:" + Binder.getCallingUid());
        UsbOperationInternal usbOperationInternal = new UsbOperationInternal(iIncrementAndGet, this.mId);
        this.mUsbManager.enableLimitPowerTransfer(this, z, iIncrementAndGet, usbOperationInternal);
        usbOperationInternal.waitForOperationComplete();
        int status = usbOperationInternal.getStatus();
        if (status == 0) {
            return 0;
        }
        int i = 1;
        if (status != 1) {
            i = 2;
            if (status != 2) {
                i = 3;
                if (status != 3) {
                    return 4;
                }
            }
        }
        return i;
    }

    public void enableContaminantDetection(boolean z) {
        this.mUsbManager.enableContaminantDetection(this, z);
    }

    public static int combineRolesAsBit(int i, int i2) {
        checkRoles(i, i2);
        return 1 << ((i * 3) + i2);
    }

    public static String modeToString(int i) {
        StringBuilder sb = new StringBuilder();
        if (i == 0) {
            return "none";
        }
        if ((i & 3) == 3) {
            sb.append("dual, ");
        } else if ((i & 2) == 2) {
            sb.append("dfp, ");
        } else if ((i & 1) == 1) {
            sb.append("ufp, ");
        }
        if ((i & 4) == 4) {
            sb.append("audio_acc, ");
        }
        if ((i & 8) == 8) {
            sb.append("debug_acc, ");
        }
        if (sb.length() == 0) {
            return Integer.toString(i);
        }
        return sb.substring(0, sb.length() - 2);
    }

    public static String powerRoleToString(int i) {
        if (i == 0) {
            return "no-power";
        }
        if (i == 1) {
            return Slice.SUBTYPE_SOURCE;
        }
        if (i == 2) {
            return "sink";
        }
        return Integer.toString(i);
    }

    public static String dataRoleToString(int i) {
        if (i == 0) {
            return "no-data";
        }
        if (i == 1) {
            return "host";
        }
        if (i == 2) {
            return "device";
        }
        return Integer.toString(i);
    }

    public static String contaminantPresenceStatusToString(int i) {
        if (i == 0) {
            return "not-supported";
        }
        if (i == 1) {
            return "disabled";
        }
        if (i == 2) {
            return "not detected";
        }
        if (i == 3) {
            return "detected";
        }
        return Integer.toString(i);
    }

    public static String usbDataStatusToString(int i) {
        StringBuilder sb = new StringBuilder();
        if (i == 0) {
            return "unknown";
        }
        if ((i & 1) == 1) {
            return "enabled";
        }
        if ((i & 2) == 2) {
            sb.append("disabled-overheat, ");
        }
        if ((i & 4) == 4) {
            sb.append("disabled-contaminant, ");
        }
        if ((i & 8) == 8) {
            sb.append("disabled-dock, ");
        }
        if ((i & 16) == 16) {
            sb.append("disabled-force, ");
        }
        if ((i & 32) == 32) {
            sb.append("disabled-debug, ");
        }
        if ((i & 64) == 64) {
            sb.append("disabled-host-dock, ");
        }
        if ((i & 128) == 128) {
            sb.append("disabled-device-dock, ");
        }
        return sb.toString().replaceAll(", $", "");
    }

    public static String powerBrickConnectionStatusToString(int i) {
        if (i == 0) {
            return "unknown";
        }
        if (i == 1) {
            return "connected";
        }
        if (i == 2) {
            return "disconnected";
        }
        return Integer.toString(i);
    }

    public static String roleCombinationsToString(int i) {
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        boolean z = true;
        while (i != 0) {
            int iNumberOfTrailingZeros = Integer.numberOfTrailingZeros(i);
            i &= ~(1 << iNumberOfTrailingZeros);
            int i2 = iNumberOfTrailingZeros / 3;
            int i3 = iNumberOfTrailingZeros % 3;
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(powerRoleToString(i2));
            sb.append(ShortcutConstants.SERVICES_SEPARATOR);
            sb.append(dataRoleToString(i3));
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    public static String complianceWarningsToString(int[] iArr) {
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        if (iArr != null) {
            for (int i : iArr) {
                switch (i) {
                    case 1:
                        sb.append("other, ");
                        break;
                    case 2:
                        sb.append("debug accessory, ");
                        break;
                    case 3:
                        sb.append("bc12, ");
                        break;
                    case 4:
                        sb.append("missing rp, ");
                        break;
                    case 5:
                        sb.append("input power limited, ");
                        break;
                    case 6:
                        sb.append("missing data lines, ");
                        break;
                    case 7:
                        sb.append("enumeration fail, ");
                        break;
                    case 8:
                        sb.append("flaky connection, ");
                        break;
                    case 9:
                        sb.append("unreliable io, ");
                        break;
                    default:
                        sb.append(String.format("Unknown(%d), ", Integer.valueOf(i)));
                        break;
                }
            }
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString().replaceAll(", ]$", NavigationBarInflaterView.SIZE_MOD_END);
    }

    public static String dpAltModeStatusToString(int i) {
        if (i == 0) {
            return LsConstants.TAG_UNKNOWN;
        }
        if (i == 1) {
            return "Not Capable";
        }
        if (i == 2) {
            return "Capable-Disabled";
        }
        if (i == 3) {
            return "Enabled";
        }
        return Integer.toString(i);
    }

    public static void checkMode(int i) {
        Preconditions.checkArgumentInRange(i, 0, 3, "portMode");
    }

    public static void checkPowerRole(int i) {
        Preconditions.checkArgumentInRange(i, 0, 2, "powerRole");
    }

    public static void checkDataRole(int i) {
        Preconditions.checkArgumentInRange(i, 0, 2, "powerRole");
    }

    public static void checkRoles(int i, int i2) {
        Preconditions.checkArgumentInRange(i, 0, 2, "powerRole");
        Preconditions.checkArgumentInRange(i2, 0, 2, "dataRole");
    }

    public boolean isModeSupported(int i) {
        return (this.mSupportedModes & i) == i;
    }

    public String toString() {
        return "UsbPort{id=" + this.mId + ", supportedModes=" + modeToString(this.mSupportedModes) + ", supportedContaminantProtectionModes=" + this.mSupportedContaminantProtectionModes + ", supportsEnableContaminantPresenceProtection=" + this.mSupportsEnableContaminantPresenceProtection + ", supportsEnableContaminantPresenceDetection=" + this.mSupportsEnableContaminantPresenceDetection + ", supportsComplianceWarnings=" + this.mSupportsComplianceWarnings;
    }
}
