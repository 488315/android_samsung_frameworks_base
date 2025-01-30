package com.samsung.android.settingslib.bluetooth;

import android.bluetooth.BluetoothManufacturerData;
import android.util.Log;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ManufacturerData {
    public byte[] mManufacturerRawData = null;
    public int mManufacturerType = 0;
    public final Data mData = new Data(this);
    public final SSdevice mSSdevice = new SSdevice();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Data {
        public boolean mIsDeviceCategoryInitialized = false;
        public final byte[] mContactHash = {0, 0, 0};
        public final byte[] mContactCrc = {0, 0};
        public final byte[] mDeviceId = {0, 0};
        public int mTxPower = 0;
        public byte mDeviceCategory = 0;
        public byte mDeviceIconIndex = 0;
        public final String mDeviceCategoryPrefix = "";
        public byte mBluetoothType = 0;

        public Data(ManufacturerData manufacturerData) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SSdevice {
        public SSdevice() {
            new ArrayList(Arrays.asList("[Phone] ", "[Tablet] ", "[Wearable] ", "[PC] ", "[Accessory] ", "[TV] ", "[AV] ", "[Signage] ", "[Refrigerator] ", "[Washer] ", "[Dryer] ", "[Floor A/C] ", "[Room A/C] ", "[System A/C] ", "[Air Purifier] ", "[Oven] ", "[Range] ", "[Robot Vacuum] ", "[Smart Home] ", "[Printer] ", "[Headphone] ", "[Speaker] ", "[Monitor] ", "[E-Board] ", "[IoT] ", "[Camera] ", "[Camcorder] ", "[Cooktop] ", "[Dish Washer] ", "[Microwave Oven] ", "[Hood] ", "[KimchiRef] ", "[Watch] ", "[Band] ", "[Router] ", "[BD] ", "[Tag] ", "[Car] ", "[Airdresser] ", "[AI Speaker] "));
        }
    }

    public ManufacturerData(byte[] bArr) {
        updateDeviceInfo(bArr);
    }

    public static String byteToString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append("0123456789abcdef".charAt((b & 240) >> 4));
            sb.append("0123456789abcdef".charAt(b & 15));
        }
        return sb.toString();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0036, code lost:
    
        if (r3 != 1) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:?, code lost:
    
        return com.android.systemui.R.drawable.list_ic_the_wall;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0135, code lost:
    
        if (r3 != 3) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getDeviceIcon() {
        byte b;
        int i;
        int i2 = this.mManufacturerType;
        Data data = this.mData;
        boolean z = false;
        if (i2 == 1) {
            byte[] bArr = data.mDeviceId;
            if (bArr != null && bArr[0] == 0) {
                int i3 = bArr[1] & 255;
                if (i3 == 251) {
                    return R.drawable.list_ic_vr_controller;
                }
                if (i3 == 254 || i3 == 219) {
                    return R.drawable.list_ic_gear_camera;
                }
                if (data.mIsDeviceCategoryInitialized && (b = data.mDeviceCategory) >= 0) {
                    if (b == 0) {
                        return R.drawable.list_ic_wearable;
                    }
                    if (b == 1) {
                        return R.drawable.list_ic_ring;
                    }
                    if (b == 2) {
                        return R.drawable.list_ic_band;
                    }
                }
            }
        } else if (i2 == 2 || i2 == 3) {
            byte b2 = data.mDeviceCategory;
            byte b3 = data.mDeviceIconIndex;
            SSdevice sSdevice = this.mSSdevice;
            sSdevice.getClass();
            switch (b2) {
                case 1:
                    return R.drawable.list_ic_mobile;
                case 2:
                    return R.drawable.list_ic_tablet;
                case 3:
                case 33:
                    return R.drawable.list_ic_wearable;
                case 4:
                    return b3 != 1 ? R.drawable.list_ic_laptop : R.drawable.list_ic_pc;
                case 5:
                    return b3 != 1 ? b3 != 2 ? b3 != 3 ? R.drawable.list_ic_accessory_default : R.drawable.list_ic_keyboard : R.drawable.list_ic_game_device : R.drawable.list_ic_mouse;
                case 6:
                    if (b3 == 2) {
                        return R.drawable.list_ic_sero_tv;
                    }
                    break;
                case 7:
                    if (b3 == 1) {
                        return R.drawable.list_ic_soundbar;
                    }
                    if (b3 == 2) {
                        return R.drawable.list_ic_av360r7;
                    }
                    if (b3 == 6) {
                        return R.drawable.list_ic_soundbar;
                    }
                    return R.drawable.list_ic_dlna_audio;
                case 8:
                    return R.drawable.list_ic_signage;
                case 9:
                    return R.drawable.list_ic_refrigerator;
                case 10:
                    return R.drawable.list_ic_washer;
                case 11:
                    return R.drawable.list_ic_dryer;
                case 12:
                    return R.drawable.list_ic_floor_airconditioner;
                case 13:
                    return R.drawable.list_ic_airconditioner;
                case 14:
                    return R.drawable.list_ic_system_airconditioner;
                case 15:
                    return R.drawable.list_ic_air_purifier;
                case 16:
                    return R.drawable.list_ic_oven;
                case 17:
                    return R.drawable.list_ic_range;
                case 18:
                    return R.drawable.list_ic_cleaner;
                case 19:
                    return R.drawable.list_ic_smart_home;
                case 20:
                    return R.drawable.list_ic_printer;
                case 21:
                    if (b3 == 2) {
                        return R.drawable.list_ic_mono_headset;
                    }
                    if (b3 == 3) {
                        byte[] bArr2 = ManufacturerData.this.mData.mDeviceId;
                        if (bArr2 != null && bArr2.length > 1 && bArr2[0] == 1 && (i = bArr2[1] & 255) >= 77 && i <= 98) {
                            z = true;
                        }
                        if (z) {
                            return R.drawable.list_ic_earbuds_stem;
                        }
                    } else if (b3 != 4) {
                        return b3 != 5 ? R.drawable.list_ic_headset : R.drawable.list_ic_earbuds_stem;
                    }
                    return R.drawable.list_ic_true_wireless_earbuds;
                case 22:
                    if (b3 != 1) {
                        return R.drawable.list_ic_sound_accessory_default;
                    }
                    return R.drawable.list_ic_dlna_audio;
                case 23:
                    return R.drawable.list_ic_tv;
                case 24:
                    return R.drawable.list_ic_eboard;
                case 25:
                    return R.drawable.list_ic_samsung_connect;
                case 26:
                    return R.drawable.list_ic_camera;
                case 27:
                    return R.drawable.list_ic_camcoder;
                case 28:
                    return R.drawable.list_ic_cooktop;
                case 29:
                    return R.drawable.list_ic_dishwasher;
                case 30:
                    return R.drawable.list_ic_microwaveoven;
                case 31:
                    return R.drawable.list_ic_hood;
                case 32:
                    return R.drawable.list_ic_kimchi_refrigerator;
                case 34:
                    return R.drawable.list_ic_band;
                case 35:
                    return R.drawable.list_ic_router;
                case 36:
                    return R.drawable.list_ic_blueray_player;
                case 37:
                    return b3 != 1 ? R.drawable.list_ic_tag : R.drawable.list_ic_connect_tag;
                case 38:
                    return R.drawable.list_ic_car;
                case 39:
                    return R.drawable.list_ic_steam_closet;
                case 40:
                    return b3 != 1 ? b3 != 2 ? R.drawable.list_ic_ai_speaker : R.drawable.list_ic_ai_speaker_galaxy_home_mini : R.drawable.list_ic_samsung_ai_speaker;
                case 41:
                    break;
                case 42:
                default:
                    return 0;
                case 43:
                    return R.drawable.list_ic_robot;
            }
        }
        return 0;
    }

    public final boolean isSupportFeature(byte b) {
        byte[] bArr;
        try {
            if (this.mManufacturerType != 3 || (bArr = this.mManufacturerRawData) == null) {
                return false;
            }
            int length = bArr.length;
            int i = BluetoothManufacturerData.OFFSET_SS_LE_FEATURES;
            if (length > i) {
                return (bArr[i] & b) == b;
            }
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final void updateDeviceInfo(byte[] bArr) {
        int i;
        int i2;
        Data data = this.mData;
        if (bArr == null) {
            return;
        }
        this.mManufacturerRawData = bArr;
        if (bArr.length < 9) {
            this.mManufacturerType = 0;
        } else {
            int i3 = BluetoothManufacturerData.OFFSET_OLD_SERVICE_ID;
            if (bArr[i3] == 0 && bArr[i3 + 1] == 2) {
                this.mManufacturerType = 1;
            } else if (bArr[BluetoothManufacturerData.OFFSET_SS_SERVICE_ID] == 9 && bArr[BluetoothManufacturerData.OFFSET_SS_ASSOCIATED_SERVICE_ID] == 0) {
                this.mManufacturerType = 2;
            } else if (bArr[BluetoothManufacturerData.OFFSET_SS_SERVICE_ID] == 9 && bArr[BluetoothManufacturerData.OFFSET_SS_ASSOCIATED_SERVICE_ID] == 2) {
                this.mManufacturerType = 3;
                int i4 = BluetoothManufacturerData.OFFSET_SS_LE_FEATURES;
                byte b = bArr[i4];
                int i5 = i4 + 1;
                for (int i6 = 0; i6 < 5; i6++) {
                    byte b2 = (byte) (((byte) (1 << i6)) & b);
                    if (b2 == 1) {
                        BluetoothManufacturerData.OFFSET_SS_LE_PACKET_NUMBER = i5;
                        i = BluetoothManufacturerData.LENGTH_SS_LE_PACKET_NUMBER;
                    } else if (b2 == 2) {
                        BluetoothManufacturerData.OFFSET_SS_LE_PROXIMITY_TYPE = i5;
                        i = BluetoothManufacturerData.LENGTH_SS_LE_PROXIMITY;
                    } else if (b2 == 4) {
                        BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_TYPE = i5;
                        i = BluetoothManufacturerData.LENGTH_SS_LE_DEVICE;
                    } else if (b2 == 8) {
                        BluetoothManufacturerData.OFFSET_SS_LE_CONNECTIVITY_TYPE = i5;
                        i = BluetoothManufacturerData.LENGTH_SS_LE_CONNECTIVITY;
                    } else if (b2 == 16) {
                        BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH = i5;
                        i = bArr[i5] + 1;
                        BluetoothManufacturerData.LENGTH_SS_LE_ASSOCIATED_SERVICE_DATA = i;
                    }
                    i5 += i;
                }
            } else {
                this.mManufacturerType = 0;
            }
        }
        try {
            int i7 = this.mManufacturerType;
            if (i7 != 2) {
                if (i7 != 3) {
                    data.mTxPower = 0;
                } else {
                    if (isSupportFeature((byte) 2) && bArr.length > BluetoothManufacturerData.OFFSET_SS_LE_PROXIMITY_TYPE + BluetoothManufacturerData.OFFSET_SS_LE_PROXIMITY_INFO) {
                        int i8 = BluetoothManufacturerData.OFFSET_SS_LE_PROXIMITY_TYPE;
                        if (bArr[i8] == 1) {
                            data.mTxPower = bArr[i8 + BluetoothManufacturerData.OFFSET_SS_LE_PROXIMITY_INFO];
                        }
                    }
                    data.mTxPower = 0;
                }
            } else if (bArr.length <= BluetoothManufacturerData.OFFSET_SS_BREDR_PROXIMITY_INFO || (bArr[BluetoothManufacturerData.OFFSET_SS_BREDR_PROXIMITY_TYPE] & 1) != 1) {
                data.mTxPower = 0;
            } else {
                data.mTxPower = bArr[BluetoothManufacturerData.OFFSET_SS_BREDR_PROXIMITY_INFO];
            }
        } catch (ArrayIndexOutOfBoundsException unused) {
            data.mTxPower = 0;
        }
        try {
            int i9 = this.mManufacturerType;
            if (i9 == 1) {
                int length = bArr.length;
                int i10 = BluetoothManufacturerData.OFFSET_OLD_DEVICE_TYPE;
                if (length > i10) {
                    byte b3 = bArr[i10];
                    data.mIsDeviceCategoryInitialized = true;
                    data.mDeviceCategory = b3;
                }
            } else if (i9 == 2) {
                int length2 = bArr.length;
                int i11 = BluetoothManufacturerData.OFFSET_SS_BREDR_DEVICE_TYPE;
                if (length2 > i11) {
                    byte b4 = bArr[i11];
                    data.mIsDeviceCategoryInitialized = true;
                    data.mDeviceCategory = b4;
                }
            } else if (i9 == 3 && isSupportFeature((byte) 4)) {
                int length3 = bArr.length;
                int i12 = BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_TYPE;
                if (length3 > i12) {
                    byte b5 = bArr[i12];
                    data.mIsDeviceCategoryInitialized = true;
                    data.mDeviceCategory = b5;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        try {
            int i13 = this.mManufacturerType;
            if (i13 == 2) {
                int length4 = bArr.length;
                int i14 = BluetoothManufacturerData.OFFSET_SS_BREDR_DEVICE_ICON;
                if (length4 > i14) {
                    data.mDeviceIconIndex = bArr[i14];
                }
            } else if (i13 == 3 && isSupportFeature((byte) 4) && bArr.length > BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_TYPE + BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_ICON) {
                data.mDeviceIconIndex = bArr[BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_TYPE + BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_ICON];
            }
        } catch (ArrayIndexOutOfBoundsException e2) {
            e2.printStackTrace();
        }
        int i15 = this.mManufacturerType;
        if (i15 == 2) {
            System.arraycopy(bArr, BluetoothManufacturerData.OFFSET_SS_BREDR_DEVICE_CONTACT_HASH, data.mContactHash, 0, 3);
        } else if (i15 == 3 && isSupportFeature((byte) 4)) {
            System.arraycopy(bArr, BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_TYPE + BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_CONTACT_HASH, data.mContactHash, 0, 3);
        }
        try {
            int i16 = this.mManufacturerType;
            if (i16 == 2) {
                System.arraycopy(bArr, BluetoothManufacturerData.OFFSET_SS_BREDR_DEVICE_CONTACT_CRC, data.mContactCrc, 0, 2);
            } else if (i16 == 3 && isSupportFeature((byte) 16) && bArr.length > BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH + BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_CONTACT_CRC && (bArr[BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH + BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_EXTRA] & 1) == 1) {
                System.arraycopy(bArr, BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH + BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_CONTACT_CRC, data.mContactCrc, 0, 2);
            }
        } catch (ArrayIndexOutOfBoundsException e3) {
            e3.printStackTrace();
        }
        try {
            int i17 = this.mManufacturerType;
            if (i17 == 1) {
                System.arraycopy(bArr, BluetoothManufacturerData.OFFSET_OLD_DEVICE_ID, data.mDeviceId, 0, 2);
            } else if (i17 != 2) {
                if (i17 == 3 && isSupportFeature((byte) 16)) {
                    System.arraycopy(bArr, BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH + BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_DEVICE_ID, data.mDeviceId, 0, 2);
                }
            } else if (bArr.length > BluetoothManufacturerData.OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA + BluetoothManufacturerData.OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA_DEVICE_ID) {
                System.arraycopy(bArr, BluetoothManufacturerData.OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA + BluetoothManufacturerData.OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA_DEVICE_ID, data.mDeviceId, 0, 2);
            }
        } catch (ArrayIndexOutOfBoundsException e4) {
            e4.printStackTrace();
        }
        try {
            int i18 = this.mManufacturerType;
            if (i18 == 1) {
                byte[] bArr2 = data.mDeviceId;
                if (bArr2 != null && bArr2[0] == 0 && (i2 = bArr2[1] & 255) >= 144 && i2 <= 255) {
                    int length5 = this.mManufacturerRawData.length;
                    int i19 = BluetoothManufacturerData.OFFSET_OLD_BLUETOOTH_TYPE;
                    if (length5 > i19 && bArr.length > i19) {
                        data.mBluetoothType = bArr[i19];
                    }
                }
            } else if (i18 != 2) {
                if (i18 == 3 && isSupportFeature((byte) 16) && bArr.length > BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH + BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_DEVICE_TYPE) {
                    data.mBluetoothType = bArr[BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH + BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_DEVICE_TYPE];
                }
            } else if (bArr.length > BluetoothManufacturerData.OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA + BluetoothManufacturerData.OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA_DEVICE_TYPE) {
                data.mBluetoothType = bArr[BluetoothManufacturerData.OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA + BluetoothManufacturerData.OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA_DEVICE_TYPE];
            }
        } catch (ArrayIndexOutOfBoundsException e5) {
            e5.printStackTrace();
        }
        if (BluetoothUtils.DEBUG) {
            StringBuilder sb = new StringBuilder("updateDeviceInfo :: describe data = ");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("[ManufacturerType] " + this.mManufacturerType);
            sb2.append(", [TxPower] " + data.mTxPower);
            sb2.append(", [DeviceCategory] " + ((int) data.mDeviceCategory));
            sb2.append(", [DeviceIconIndex] " + ((int) data.mDeviceIconIndex));
            sb2.append(", [DevicePrefix] " + data.mDeviceCategoryPrefix);
            sb2.append(", [Contact] " + byteToString(data.mContactHash) + byteToString(data.mContactCrc));
            StringBuilder sb3 = new StringBuilder(", [Device ID] ");
            sb3.append(byteToString(data.mDeviceId));
            sb2.append(sb3.toString());
            sb2.append(", [BT Type] " + ((int) data.mBluetoothType));
            sb.append(sb2.toString());
            Log.d("ManufacturerData", sb.toString());
        }
    }
}
