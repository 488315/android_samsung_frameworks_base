package com.android.server.usb.descriptors.report;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;

import com.android.internal.util.FrameworkStatsLog;

import com.att.iqi.lib.metrics.hw.HwConstants;
import com.att.iqi.lib.metrics.mm.MM05;
import com.samsung.android.knoxguard.service.utils.IntegritySeUtil;

import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class UsbStrings {
    public static final HashMap sACControlInterfaceNames;
    public static final HashMap sACStreamingInterfaceNames;
    public static final HashMap sAudioEncodingNames;
    public static final HashMap sAudioSubclassNames;
    public static final HashMap sClassNames;
    public static final HashMap sDescriptorNames;
    public static final HashMap sFormatNames;
    public static final HashMap sTerminalNames;

    static {
        HashMap hashMap = new HashMap();
        sDescriptorNames = hashMap;
        hashMap.put((byte) 1, "Device");
        sDescriptorNames.put((byte) 2, "Config");
        sDescriptorNames.put((byte) 3, "String");
        sDescriptorNames.put((byte) 4, "Interface");
        sDescriptorNames.put((byte) 5, "Endpoint");
        sDescriptorNames.put((byte) 15, "BOS (whatever that means)");
        sDescriptorNames.put(
                Byte.valueOf(MM05.IQ_SIP_CALL_STATE_DISCONNECTING), "Interface Association");
        sDescriptorNames.put(Byte.valueOf(HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED), "Capability");
        sDescriptorNames.put((byte) 33, "HID");
        sDescriptorNames.put((byte) 34, "Report");
        sDescriptorNames.put((byte) 35, "Physical");
        sDescriptorNames.put((byte) 36, "Class-specific Interface");
        sDescriptorNames.put((byte) 37, "Class-specific Endpoint");
        sDescriptorNames.put((byte) 41, "Hub");
        sDescriptorNames.put((byte) 42, "Superspeed Hub");
        sDescriptorNames.put((byte) 48, "Endpoint Companion");
        HashMap hashMap2 = new HashMap();
        sACControlInterfaceNames = hashMap2;
        hashMap2.put((byte) 0, "Undefined");
        sACControlInterfaceNames.put((byte) 1, "Header");
        sACControlInterfaceNames.put((byte) 2, "Input Terminal");
        sACControlInterfaceNames.put((byte) 3, "Output Terminal");
        sACControlInterfaceNames.put((byte) 4, "Mixer Unit");
        sACControlInterfaceNames.put((byte) 5, "Selector Unit");
        sACControlInterfaceNames.put((byte) 6, "Feature Unit");
        sACControlInterfaceNames.put((byte) 7, "Processing Unit");
        sACControlInterfaceNames.put((byte) 8, "Extension Unit");
        sACControlInterfaceNames.put((byte) 10, "Clock Source");
        sACControlInterfaceNames.put(
                Byte.valueOf(MM05.IQ_SIP_CALL_STATE_DISCONNECTING), "Clock Selector");
        sACControlInterfaceNames.put((byte) 12, "Clock Multiplier");
        sACControlInterfaceNames.put((byte) 13, "Sample Rate Converter");
        HashMap hashMap3 = new HashMap();
        sACStreamingInterfaceNames = hashMap3;
        hashMap3.put((byte) 0, "Undefined");
        sACStreamingInterfaceNames.put((byte) 1, "General");
        sACStreamingInterfaceNames.put((byte) 2, "Format Type");
        sACStreamingInterfaceNames.put((byte) 3, "Format Specific");
        HashMap hashMap4 = new HashMap();
        sClassNames = hashMap4;
        hashMap4.put(0, "Device");
        sClassNames.put(1, "Audio");
        sClassNames.put(2, "Communications");
        sClassNames.put(3, "HID");
        sClassNames.put(5, "Physical");
        sClassNames.put(6, "Image");
        sClassNames.put(7, "Printer");
        sClassNames.put(8, "Storage");
        sClassNames.put(9, "Hub");
        sClassNames.put(10, "CDC Control");
        sClassNames.put(11, "Smart Card");
        sClassNames.put(13, "Security");
        sClassNames.put(14, "Video");
        sClassNames.put(15, "Healthcare");
        sClassNames.put(16, "Audio/Video");
        sClassNames.put(17, "Billboard");
        sClassNames.put(18, "Type C Bridge");
        sClassNames.put(
                Integer.valueOf(
                        FrameworkStatsLog
                                .CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_ASTRO),
                "Diagnostic");
        sClassNames.put(224, "Wireless");
        sClassNames.put(
                Integer.valueOf(FrameworkStatsLog.BOOT_TIME_EVENT_DURATION_REPORTED), "Misc");
        sClassNames.put(
                Integer.valueOf(FrameworkStatsLog.APP_FREEZE_CHANGED), "Application Specific");
        sClassNames.put(
                Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT),
                "Vendor Specific");
        HashMap hashMap5 = new HashMap();
        sAudioSubclassNames = hashMap5;
        hashMap5.put(0, "Undefinded");
        sAudioSubclassNames.put(1, "Audio Control");
        sAudioSubclassNames.put(2, "Audio Streaming");
        sAudioSubclassNames.put(3, "MIDI Streaming");
        HashMap hashMap6 = new HashMap();
        sAudioEncodingNames = hashMap6;
        hashMap6.put(0, "Format I Undefined");
        sAudioEncodingNames.put(1, "Format I PCM");
        sAudioEncodingNames.put(2, "Format I PCM8");
        sAudioEncodingNames.put(3, "Format I FLOAT");
        sAudioEncodingNames.put(4, "Format I ALAW");
        sAudioEncodingNames.put(5, "Format I MuLAW");
        sAudioEncodingNames.put(4096, "FORMAT_II Undefined");
        sAudioEncodingNames.put(
                Integer.valueOf(IntegritySeUtil.CLIENT_INTEGRITY_BASE2), "FORMAT_II MPEG");
        sAudioEncodingNames.put(4098, "FORMAT_II AC3");
        sAudioEncodingNames.put(8192, "FORMAT_III Undefined");
        sAudioEncodingNames.put(8193, "FORMAT_III IEC1937 AC3");
        sAudioEncodingNames.put(8194, "FORMAT_III MPEG1 Layer 1");
        sAudioEncodingNames.put(8195, "FORMAT_III MPEG1 Layer 2");
        sAudioEncodingNames.put(8196, "FORMAT_III MPEG2 EXT");
        sAudioEncodingNames.put(8197, "FORMAT_III MPEG2 Layer1LS");
        HashMap hashMap7 = new HashMap();
        sTerminalNames = hashMap7;
        hashMap7.put(
                Integer.valueOf(
                        FrameworkStatsLog
                                .HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP),
                "USB Streaming");
        sTerminalNames.put(512, "Undefined");
        sTerminalNames.put(513, "Microphone");
        sTerminalNames.put(
                Integer.valueOf(FrameworkStatsLog.DEVICE_WIDE_JOB_CONSTRAINT_CHANGED),
                "Desktop Microphone");
        sTerminalNames.put(
                Integer.valueOf(FrameworkStatsLog.AMBIENT_MODE_CHANGED),
                "Personal (headset) Microphone");
        sTerminalNames.put(
                Integer.valueOf(FrameworkStatsLog.ANR_LATENCY_REPORTED), "Omni Microphone");
        sTerminalNames.put(
                Integer.valueOf(FrameworkStatsLog.RESOURCE_API_INFO), "Microphone Array");
        sTerminalNames.put(518, "Proecessing Microphone Array");
        sTerminalNames.put(
                Integer.valueOf(
                        FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_USAGE),
                "Undefined");
        sTerminalNames.put(769, "Speaker");
        sTerminalNames.put(770, "Headphones");
        sTerminalNames.put(771, "Head Mounted Speaker");
        sTerminalNames.put(
                Integer.valueOf(FrameworkStatsLog.THERMAL_STATUS_CALLED), "Desktop Speaker");
        sTerminalNames.put(
                Integer.valueOf(FrameworkStatsLog.THERMAL_HEADROOM_CALLED), "Room Speaker");
        sTerminalNames.put(
                Integer.valueOf(FrameworkStatsLog.THERMAL_HEADROOM_THRESHOLDS_CALLED),
                "Communications Speaker");
        sTerminalNames.put(
                Integer.valueOf(FrameworkStatsLog.BOOT_INTEGRITY_INFO_REPORTED),
                "Low Frequency Speaker");
        sTerminalNames.put(1024, "Undefined");
        sTerminalNames.put(1025, "Handset");
        sTerminalNames.put(1026, "Headset");
        sTerminalNames.put(1027, "Speaker Phone");
        sTerminalNames.put(1028, "Speaker Phone (echo supressing)");
        sTerminalNames.put(1029, "Speaker Phone (echo canceling)");
        sTerminalNames.put(1280, "Undefined");
        sTerminalNames.put(1281, "Phone Line");
        sTerminalNames.put(1282, "Telephone");
        sTerminalNames.put(1283, "Down Line Phone");
        sTerminalNames.put(
                Integer.valueOf(
                        FrameworkStatsLog
                                .APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM),
                "Undefined");
        sTerminalNames.put(1537, "Analog Connector");
        sTerminalNames.put(1538, "Digital Connector");
        sTerminalNames.put(1539, "Line Connector");
        sTerminalNames.put(1540, "Legacy Audio Connector");
        sTerminalNames.put(1541, "S/PIDF Interface");
        sTerminalNames.put(1542, "1394 Audio");
        sTerminalNames.put(1543, "1394 Audio/Video");
        sTerminalNames.put(1792, "Undefined");
        sTerminalNames.put(1793, "Calibration Nose");
        sTerminalNames.put(1794, "EQ Noise");
        sTerminalNames.put(1795, "CD Player");
        sTerminalNames.put(1796, "DAT");
        sTerminalNames.put(1797, "DCC");
        sTerminalNames.put(1798, "Mini Disk");
        sTerminalNames.put(1799, "Analog Tap");
        sTerminalNames.put(1800, "Phonograph");
        sTerminalNames.put(1801, "VCR Audio");
        sTerminalNames.put(1802, "Video Disk Audio");
        sTerminalNames.put(1803, "DVD Audio");
        sTerminalNames.put(1804, "TV Audio");
        sTerminalNames.put(1805, "Satellite Audio");
        sTerminalNames.put(1806, "Cable Tuner Audio");
        sTerminalNames.put(1807, "DSS Audio");
        sTerminalNames.put(1809, "Radio Transmitter");
        sTerminalNames.put(1810, "Multitrack Recorder");
        sTerminalNames.put(1811, "Synthesizer");
        HashMap hashMap8 = new HashMap();
        sFormatNames = hashMap8;
        hashMap8.put(1, "FORMAT_TYPE_I");
        sFormatNames.put(2, "FORMAT_TYPE_II");
        sFormatNames.put(3, "FORMAT_TYPE_III");
        sFormatNames.put(4, "FORMAT_TYPE_IV");
        sFormatNames.put(-127, "EXT_FORMAT_TYPE_I");
        sFormatNames.put(-126, "EXT_FORMAT_TYPE_II");
        sFormatNames.put(-125, "EXT_FORMAT_TYPE_III");
    }

    public static String getClassName(int i) {
        String str = (String) sClassNames.get(Integer.valueOf(i));
        int i2 = i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        if (str != null) {
            return str;
        }
        return "Unknown Class ID [0x" + Integer.toHexString(i2) + ":" + i2 + "]";
    }
}
