package com.samsung.android.lib.dexcontrol.model.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import com.android.internal.util.FrameworkStatsLog;
import com.samsung.android.lib.dexcontrol.utils.SLog;
import java.util.HashMap;

/* loaded from: classes2.dex */
public abstract class BaseModel {
    public static final String TAG = "BaseModel";
    public Context mContext;
    public final HashMap mUsbClassMap = new HashMap() { // from class: com.samsung.android.lib.dexcontrol.model.common.BaseModel.1
        {
            put(Integer.valueOf(USB_CLASS_CODE.AUDIO.getValue()), "A");
            put(Integer.valueOf(USB_CLASS_CODE.COMMUNICATIONS_AND_CDC_CONTROL.getValue()), "B");
            put(Integer.valueOf(USB_CLASS_CODE.HID.getValue()), "C");
            put(Integer.valueOf(USB_CLASS_CODE.PHYSICAL.getValue()), "D");
            put(Integer.valueOf(USB_CLASS_CODE.IMAGE.getValue()), "E");
            put(Integer.valueOf(USB_CLASS_CODE.PRINTER.getValue()), "F");
            put(Integer.valueOf(USB_CLASS_CODE.MASS_STORAGE.getValue()), "G");
            put(Integer.valueOf(USB_CLASS_CODE.HUB.getValue()), "H");
            put(Integer.valueOf(USB_CLASS_CODE.CDC_DATA.getValue()), "I");
            put(Integer.valueOf(USB_CLASS_CODE.SMART_CARD.getValue()), "J");
            put(Integer.valueOf(USB_CLASS_CODE.CONTENT_SECURITY.getValue()), "K");
            put(Integer.valueOf(USB_CLASS_CODE.VIDEO.getValue()), "L");
            put(Integer.valueOf(USB_CLASS_CODE.PERSOLNAL_HEALTHCARE.getValue()), "M");
            put(Integer.valueOf(USB_CLASS_CODE.AUDIO_VIDEO_DEVICES.getValue()), "N");
            put(Integer.valueOf(USB_CLASS_CODE.BILLBOARD_DEVICE_CLASS.getValue()), "O");
            put(Integer.valueOf(USB_CLASS_CODE.USB_TYPE_C_BRIDGE_CLASS.getValue()), "P");
            put(Integer.valueOf(USB_CLASS_CODE.DIAGONOSTIC_DEVICES.getValue()), "Q");
            put(Integer.valueOf(USB_CLASS_CODE.WIRELESS_CONTROLLER.getValue()), "R");
            put(Integer.valueOf(USB_CLASS_CODE.MISCELLANEOUS.getValue()), "S");
            put(Integer.valueOf(USB_CLASS_CODE.APPLICATION_SPECIFIC.getValue()), "T");
            put(Integer.valueOf(USB_CLASS_CODE.VENDOR_SPECIFIC.getValue()), "U");
        }
    };
    public final HashMap mTATypeMap = new HashMap() { // from class: com.samsung.android.lib.dexcontrol.model.common.BaseModel.2
        {
            put(Integer.valueOf(TA_TYPE_CODE.PD.getValue()), "P");
            put(Integer.valueOf(TA_TYPE_CODE.SDP.getValue()), "S");
            put(Integer.valueOf(TA_TYPE_CODE.CDP.getValue()), "C");
            put(Integer.valueOf(TA_TYPE_CODE.DCP.getValue()), "D");
            put(Integer.valueOf(TA_TYPE_CODE.AFC.getValue()), "A");
            put(Integer.valueOf(TA_TYPE_CODE.QC.getValue()), "Q");
        }
    };
    public final BroadcastReceiver mUsbDeviceReceiver = new BroadcastReceiver() { // from class: com.samsung.android.lib.dexcontrol.model.common.BaseModel.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.hashCode();
            if (!action.equals("android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
                SLog.m127e(BaseModel.TAG, "Not in Case");
                return;
            }
            UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
            if (usbDevice != null) {
                BaseModel.this.updateUsbClassInfo(usbDevice);
            }
        }
    };

    public abstract void usbDeviceChanged(int i, String str);

    public enum USB_CLASS_CODE {
        AUDIO(1),
        COMMUNICATIONS_AND_CDC_CONTROL(2),
        HID(3),
        PHYSICAL(5),
        IMAGE(6),
        PRINTER(7),
        MASS_STORAGE(8),
        HUB(9),
        CDC_DATA(10),
        SMART_CARD(11),
        CONTENT_SECURITY(13),
        VIDEO(14),
        PERSOLNAL_HEALTHCARE(15),
        AUDIO_VIDEO_DEVICES(16),
        BILLBOARD_DEVICE_CLASS(17),
        USB_TYPE_C_BRIDGE_CLASS(18),
        DIAGONOSTIC_DEVICES(FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_ASTRO),
        WIRELESS_CONTROLLER(224),
        MISCELLANEOUS(FrameworkStatsLog.BOOT_TIME_EVENT_DURATION_REPORTED),
        APPLICATION_SPECIFIC(FrameworkStatsLog.APP_FREEZE_CHANGED),
        VENDOR_SPECIFIC(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);

        private final int mValue;

        USB_CLASS_CODE(int i) {
            this.mValue = i;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    public enum TA_TYPE_CODE {
        PD(1),
        SDP(2),
        CDP(3),
        DCP(4),
        AFC(5),
        QC(6);

        private final int mValue;

        TA_TYPE_CODE(int i) {
            this.mValue = i;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    public BaseModel(Context context) {
        this.mContext = null;
        this.mContext = context;
        init();
    }

    public final void init() {
        registerUsbReceiver();
    }

    public Context getContext() {
        return this.mContext;
    }

    public final void registerUsbReceiver() {
        this.mContext.registerReceiver(this.mUsbDeviceReceiver, new IntentFilter("android.hardware.usb.action.USB_DEVICE_ATTACHED"));
    }

    public final void updateUsbClassInfo(UsbDevice usbDevice) {
        if (usbDevice.getInterfaceCount() > 0) {
            usbDeviceChanged(usbDevice.getProductId(), (String) this.mUsbClassMap.get(Integer.valueOf(usbDevice.getInterface(0).getInterfaceClass())));
        }
    }

    void unregisterReceiver() {
        getContext().unregisterReceiver(this.mUsbDeviceReceiver);
    }

    void destroy() {
        unregisterReceiver();
        this.mContext = null;
    }
}
