package com.android.internal.usb;

import android.content.Context;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbConfiguration;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbPort;
import android.hardware.usb.UsbPortStatus;
import android.media.midi.MidiDeviceInfo;
import com.android.internal.util.dump.DualDumpOutputStream;

/* loaded from: classes4.dex */
public class DumpUtils {
    public static void writeAccessory(DualDumpOutputStream dualDumpOutputStream, String str, long j, UsbAccessory usbAccessory) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write(MidiDeviceInfo.PROPERTY_MANUFACTURER, 1138166333441L, usbAccessory.getManufacturer());
        dualDumpOutputStream.write("model", 1138166333442L, usbAccessory.getModel());
        com.android.internal.util.dump.DumpUtils.writeStringIfNotNull(dualDumpOutputStream, "description", 1138166333443L, usbAccessory.getManufacturer());
        dualDumpOutputStream.write("version", 1138166333444L, usbAccessory.getVersion());
        com.android.internal.util.dump.DumpUtils.writeStringIfNotNull(dualDumpOutputStream, "uri", 1138166333445L, usbAccessory.getUri());
        dualDumpOutputStream.write(Context.SERIAL_SERVICE, 1138166333446L, usbAccessory.getSerial());
        dualDumpOutputStream.end(start);
    }

    public static void writeDevice(DualDumpOutputStream dualDumpOutputStream, String str, long j, UsbDevice usbDevice) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("name", 1138166333441L, usbDevice.getDeviceName());
        dualDumpOutputStream.write("vendor_id", 1120986464258L, usbDevice.getVendorId());
        dualDumpOutputStream.write("product_id", 1120986464259L, usbDevice.getProductId());
        dualDumpOutputStream.write("class", 1120986464260L, usbDevice.getDeviceClass());
        dualDumpOutputStream.write("subclass", 1120986464261L, usbDevice.getDeviceSubclass());
        dualDumpOutputStream.write("protocol", 1120986464262L, usbDevice.getDeviceProtocol());
        dualDumpOutputStream.write("manufacturer_name", 1138166333447L, usbDevice.getManufacturerName());
        dualDumpOutputStream.write("product_name", 1138166333448L, usbDevice.getProductName());
        dualDumpOutputStream.write("version", 1138166333449L, usbDevice.getVersion());
        dualDumpOutputStream.write("serial_number", 1138166333450L, usbDevice.getSerialNumber());
        int configurationCount = usbDevice.getConfigurationCount();
        for (int i = 0; i < configurationCount; i++) {
            writeConfiguration(dualDumpOutputStream, "configurations", 2246267895819L, usbDevice.getConfiguration(i));
        }
        dualDumpOutputStream.end(start);
    }

    private static void writeConfiguration(DualDumpOutputStream dualDumpOutputStream, String str, long j, UsbConfiguration usbConfiguration) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("id", 1120986464257L, usbConfiguration.getId());
        dualDumpOutputStream.write("name", 1138166333442L, usbConfiguration.getName());
        dualDumpOutputStream.write("attributes", 1155346202627L, usbConfiguration.getAttributes());
        dualDumpOutputStream.write("max_power", 1120986464260L, usbConfiguration.getMaxPower());
        int interfaceCount = usbConfiguration.getInterfaceCount();
        for (int i = 0; i < interfaceCount; i++) {
            writeInterface(dualDumpOutputStream, "interfaces", 2246267895813L, usbConfiguration.getInterface(i));
        }
        dualDumpOutputStream.end(start);
    }

    private static void writeInterface(DualDumpOutputStream dualDumpOutputStream, String str, long j, UsbInterface usbInterface) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("id", 1120986464257L, usbInterface.getId());
        dualDumpOutputStream.write("alternate_settings", 1120986464258L, usbInterface.getAlternateSetting());
        dualDumpOutputStream.write("name", 1138166333443L, usbInterface.getName());
        dualDumpOutputStream.write("class", 1120986464260L, usbInterface.getInterfaceClass());
        dualDumpOutputStream.write("subclass", 1120986464261L, usbInterface.getInterfaceSubclass());
        dualDumpOutputStream.write("protocol", 1120986464262L, usbInterface.getInterfaceProtocol());
        int endpointCount = usbInterface.getEndpointCount();
        for (int i = 0; i < endpointCount; i++) {
            writeEndpoint(dualDumpOutputStream, "endpoints", 2246267895815L, usbInterface.getEndpoint(i));
        }
        dualDumpOutputStream.end(start);
    }

    private static void writeEndpoint(DualDumpOutputStream dualDumpOutputStream, String str, long j, UsbEndpoint usbEndpoint) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("endpoint_number", 1120986464257L, usbEndpoint.getEndpointNumber());
        dualDumpOutputStream.write("direction", 1159641169922L, usbEndpoint.getDirection());
        dualDumpOutputStream.write("address", 1120986464259L, usbEndpoint.getAddress());
        dualDumpOutputStream.write("type", 1159641169924L, usbEndpoint.getType());
        dualDumpOutputStream.write("attributes", 1155346202629L, usbEndpoint.getAttributes());
        dualDumpOutputStream.write("max_packet_size", 1120986464262L, usbEndpoint.getMaxPacketSize());
        dualDumpOutputStream.write("interval", 1120986464263L, usbEndpoint.getInterval());
        dualDumpOutputStream.end(start);
    }

    public static void writePort(DualDumpOutputStream dualDumpOutputStream, String str, long j, UsbPort usbPort) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("id", 1138166333441L, usbPort.getId());
        int supportedModes = usbPort.getSupportedModes();
        if (!dualDumpOutputStream.isProto()) {
            dualDumpOutputStream.write("supported_modes", 2259152797698L, UsbPort.modeToString(supportedModes));
        } else if (supportedModes == 0) {
            dualDumpOutputStream.write("supported_modes", 2259152797698L, 0);
        } else {
            if ((supportedModes & 3) == 3) {
                dualDumpOutputStream.write("supported_modes", 2259152797698L, 3);
            } else if ((supportedModes & 2) == 2) {
                dualDumpOutputStream.write("supported_modes", 2259152797698L, 2);
            } else if ((supportedModes & 1) == 1) {
                dualDumpOutputStream.write("supported_modes", 2259152797698L, 1);
            }
            if ((supportedModes & 4) == 4) {
                dualDumpOutputStream.write("supported_modes", 2259152797698L, 4);
            }
            if ((supportedModes & 8) == 8) {
                dualDumpOutputStream.write("supported_modes", 2259152797698L, 8);
            }
        }
        dualDumpOutputStream.write("supports_compliance_warnings", 1133871366147L, usbPort.supportsComplianceWarnings());
        if (usbPort.isAltModeSupported(1)) {
            dualDumpOutputStream.write("supported_alt_modes", 2259152797700L, 1);
        }
        dualDumpOutputStream.end(start);
    }

    private static void writePowerRole(DualDumpOutputStream dualDumpOutputStream, String str, long j, int i) {
        if (dualDumpOutputStream.isProto()) {
            dualDumpOutputStream.write(str, j, i);
        } else {
            dualDumpOutputStream.write(str, j, UsbPort.powerRoleToString(i));
        }
    }

    private static void writeDataRole(DualDumpOutputStream dualDumpOutputStream, String str, long j, int i) {
        if (dualDumpOutputStream.isProto()) {
            dualDumpOutputStream.write(str, j, i);
        } else {
            dualDumpOutputStream.write(str, j, UsbPort.dataRoleToString(i));
        }
    }

    private static void writeContaminantPresenceStatus(DualDumpOutputStream dualDumpOutputStream, String str, long j, int i) {
        if (dualDumpOutputStream.isProto()) {
            dualDumpOutputStream.write(str, j, i);
        } else {
            dualDumpOutputStream.write(str, j, UsbPort.contaminantPresenceStatusToString(i));
        }
    }

    public static void writePortStatus(DualDumpOutputStream dualDumpOutputStream, String str, long j, UsbPortStatus usbPortStatus) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("connected", 1133871366145L, usbPortStatus.isConnected());
        if (dualDumpOutputStream.isProto()) {
            dualDumpOutputStream.write("current_mode", 1159641169922L, usbPortStatus.getCurrentMode());
        } else {
            dualDumpOutputStream.write("current_mode", 1159641169922L, UsbPort.modeToString(usbPortStatus.getCurrentMode()));
        }
        writePowerRole(dualDumpOutputStream, "power_role", 1159641169923L, usbPortStatus.getCurrentPowerRole());
        writeDataRole(dualDumpOutputStream, "data_role", 1159641169924L, usbPortStatus.getCurrentDataRole());
        int supportedRoleCombinations = usbPortStatus.getSupportedRoleCombinations();
        while (supportedRoleCombinations != 0) {
            int numberOfTrailingZeros = Integer.numberOfTrailingZeros(supportedRoleCombinations);
            supportedRoleCombinations &= ~(1 << numberOfTrailingZeros);
            long start2 = dualDumpOutputStream.start("role_combinations", 2246267895813L);
            writePowerRole(dualDumpOutputStream, "power_role", 1159641169921L, numberOfTrailingZeros / 3);
            writeDataRole(dualDumpOutputStream, "data_role", 1159641169922L, numberOfTrailingZeros % 3);
            dualDumpOutputStream.end(start2);
        }
        writeContaminantPresenceStatus(dualDumpOutputStream, "contaminant_presence_status", 1159641169926L, usbPortStatus.getContaminantDetectionStatus());
        dualDumpOutputStream.write("usb_data_status", 1138166333447L, UsbPort.usbDataStatusToString(usbPortStatus.getUsbDataStatus()));
        dualDumpOutputStream.write("is_power_transfer_limited", 1133871366152L, usbPortStatus.isPowerTransferLimited());
        dualDumpOutputStream.write("usb_power_brick_status", 1138166333449L, UsbPort.powerBrickConnectionStatusToString(usbPortStatus.getPowerBrickConnectionStatus()));
        dualDumpOutputStream.write("compliance_warning_status", 1138166333450L, UsbPort.complianceWarningsToString(usbPortStatus.getComplianceWarnings()));
        if (usbPortStatus.getDisplayPortAltModeInfo() != null) {
            dualDumpOutputStream.write("displayport_alt_mode_status", 1138166333451L, usbPortStatus.getDisplayPortAltModeInfo().toString());
        }
        dualDumpOutputStream.end(start);
    }
}
