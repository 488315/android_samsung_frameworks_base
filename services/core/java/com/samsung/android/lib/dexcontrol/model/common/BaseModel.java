package com.samsung.android.lib.dexcontrol.model.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import com.samsung.android.lib.dexcontrol.utils.SLog;
import java.util.HashMap;

public abstract class BaseModel {
    public Context mContext;
    public final AnonymousClass3 mUsbDeviceReceiver;
    public final HashMap mUsbClassMap = new HashMap() { // from class: com.samsung.android.lib.dexcontrol.model.common.BaseModel.1
        {
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.AUDIO, this, "A");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.COMMUNICATIONS_AND_CDC_CONTROL, this, "B");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.HID, this, "C");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.PHYSICAL, this, "D");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.IMAGE, this, "E");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.PRINTER, this, "F");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.MASS_STORAGE, this, "G");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.HUB, this, "H");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.CDC_DATA, this, "I");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.SMART_CARD, this, "J");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.CONTENT_SECURITY, this, "K");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.VIDEO, this, "L");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.PERSOLNAL_HEALTHCARE, this, "M");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.AUDIO_VIDEO_DEVICES, this, "N");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.BILLBOARD_DEVICE_CLASS, this, "O");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.USB_TYPE_C_BRIDGE_CLASS, this, "P");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.DIAGONOSTIC_DEVICES, this, "Q");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.WIRELESS_CONTROLLER, this, "R");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.MISCELLANEOUS, this, "S");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.APPLICATION_SPECIFIC, this, "T");
            BaseModel$1$$ExternalSyntheticOutline0.m(USB_CLASS_CODE.VENDOR_SPECIFIC, this, "U");
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

    public enum TA_TYPE_CODE {
        PD("PD"),
        SDP("SDP"),
        CDP("CDP"),
        DCP("DCP"),
        AFC("AFC"),
        QC("QC");

        private final int mValue;

        TA_TYPE_CODE(String str) {
            this.mValue = r2;
        }

        public final int getValue() {
            return this.mValue;
        }
    }

    public enum USB_CLASS_CODE {
        AUDIO("AUDIO"),
        COMMUNICATIONS_AND_CDC_CONTROL("COMMUNICATIONS_AND_CDC_CONTROL"),
        HID("HID"),
        PHYSICAL("PHYSICAL"),
        IMAGE("IMAGE"),
        PRINTER("PRINTER"),
        MASS_STORAGE("MASS_STORAGE"),
        HUB("HUB"),
        CDC_DATA("CDC_DATA"),
        SMART_CARD("SMART_CARD"),
        CONTENT_SECURITY("CONTENT_SECURITY"),
        VIDEO("VIDEO"),
        PERSOLNAL_HEALTHCARE("PERSOLNAL_HEALTHCARE"),
        AUDIO_VIDEO_DEVICES("AUDIO_VIDEO_DEVICES"),
        BILLBOARD_DEVICE_CLASS("BILLBOARD_DEVICE_CLASS"),
        USB_TYPE_C_BRIDGE_CLASS("USB_TYPE_C_BRIDGE_CLASS"),
        DIAGONOSTIC_DEVICES("DIAGONOSTIC_DEVICES"),
        WIRELESS_CONTROLLER("WIRELESS_CONTROLLER"),
        MISCELLANEOUS("MISCELLANEOUS"),
        APPLICATION_SPECIFIC("APPLICATION_SPECIFIC"),
        VENDOR_SPECIFIC("VENDOR_SPECIFIC");

        private final int mValue;

        USB_CLASS_CODE(String str) {
            this.mValue = r2;
        }

        public final int getValue() {
            return this.mValue;
        }
    }

    public BaseModel(Context context) {
        this.mContext = null;
        ?? r0 = new BroadcastReceiver() { // from class: com.samsung.android.lib.dexcontrol.model.common.BaseModel.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                action.getClass();
                if (!action.equals("android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
                    SLog.e("BaseModel", "Not in Case");
                    return;
                }
                UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                if (usbDevice != null) {
                    BaseModel baseModel = BaseModel.this;
                    baseModel.getClass();
                    if (usbDevice.getInterfaceCount() > 0) {
                        baseModel.usbDeviceChanged(usbDevice.getProductId(), (String) baseModel.mUsbClassMap.get(Integer.valueOf(usbDevice.getInterface(0).getInterfaceClass())));
                    }
                }
            }
        };
        this.mUsbDeviceReceiver = r0;
        this.mContext = context;
        this.mContext.registerReceiver(r0, new IntentFilter("android.hardware.usb.action.USB_DEVICE_ATTACHED"), 2);
    }

    public abstract void usbDeviceChanged(int i, String str);
}
