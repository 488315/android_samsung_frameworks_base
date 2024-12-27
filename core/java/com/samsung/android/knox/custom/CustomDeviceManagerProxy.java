package com.samsung.android.knox.custom;

import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;
import android.util.Log;

public class CustomDeviceManagerProxy {
    public static final int DESTINATION_ADDRESS = 332;
    public static final int KEYBOARD_MODE_NORMAL = 0;
    public static final int KEYBOARD_MODE_PREDICTION_OFF = 1;
    public static final int KEYBOARD_MODE_SETTINGS_OFF = 2;
    public static final int NOTIFICATIONS_ALL = 31;
    public static final int NOTIFICATIONS_BATTERY_FULL = 2;
    public static final int NOTIFICATIONS_BATTERY_LOW = 1;
    public static final int NOTIFICATIONS_NITZ_SET_TIME = 16;
    public static final int NOTIFICATIONS_SAFE_VOLUME = 4;
    public static final int NOTIFICATIONS_STATUS_BAR = 8;
    public static final int SENSOR_ACCELEROMETER = 2;
    public static final int SENSOR_ALL = 127;
    public static final int SENSOR_GYROSCOPE = 1;
    public static final int SENSOR_LIGHT = 4;
    public static final int SENSOR_MAGNETIC = 32;
    public static final int SENSOR_ORIENTATION = 8;
    public static final int SENSOR_PRESSURE = 64;
    public static final int SENSOR_PROXIMITY = 16;
    public static final int SOURCE_ADDRESS = 331;
    private static final String TAG = "CustomDeviceManagerProxy";
    public static final int VOLUME_CONTROL_STREAM_DEFAULT = 0;
    public static final int VOLUME_CONTROL_STREAM_MUSIC = 3;
    public static final int VOLUME_CONTROL_STREAM_NOTIFICATION = 4;
    public static final int VOLUME_CONTROL_STREAM_RING = 2;
    public static final int VOLUME_CONTROL_STREAM_SYSTEM = 1;
    private static CustomDeviceManagerProxy mProxy;

    public static CustomDeviceManagerProxy getInstance() {
        if (mProxy == null) {
            mProxy = new CustomDeviceManagerProxy();
        }
        return mProxy;
    }

    public boolean getProKioskState() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getProKioskState();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getProKioskState returning default value");
            return false;
        }
    }

    public boolean getProKioskNotificationMessagesState() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getProKioskNotificationMessagesState();
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getProKioskNotificationMessagesState returning default value");
            return true;
        }
    }

    public int getProKioskHideNotificationMessages() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getProKioskHideNotificationMessages();
            }
            return 0;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getProKioskHideNotificationMessages returning default value");
            return 0;
        }
    }

    public int getVolumeControlStream() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getVolumeControlStream();
            }
            return 0;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getVolumeControlStream returning default value");
            return 0;
        }
    }

    public boolean getToastEnabledState() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getToastEnabledState();
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getToastEnabledState returning default value");
            return true;
        }
    }

    public boolean getToastShowPackageNameState() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getToastShowPackageNameState();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getToastShowPackageNameState returning default value");
            return false;
        }
    }

    public int getSensorDisabled() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getSensorDisabled();
            }
            return 0;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getSensorDisabled returning default value");
            return 0;
        }
    }

    public boolean getVolumePanelEnabledState() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getVolumePanelEnabledState();
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getVolumePanelEnabledState returning default value");
            return true;
        }
    }

    public boolean getVolumeButtonRotationState() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getVolumeButtonRotationState();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getVolumeButtonRotationState returning default value");
            return false;
        }
    }

    public boolean getToastGravityEnabledState() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getToastGravityEnabledState();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getToastGravityEnabledState returning default value");
            return false;
        }
    }

    public int getToastGravity() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getToastGravity();
            }
            return 0;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getToastGravity returning default value");
            return 0;
        }
    }

    public int getToastGravityXOffset() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getToastGravityXOffset();
            }
            return 0;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getToastGravityXOffset returning default value");
            return 0;
        }
    }

    public int getToastGravityYOffset() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getToastGravityYOffset();
            }
            return 0;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getToastGravityYOffset returning default value");
            return 0;
        }
    }

    public int getKeyboardMode() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getKeyboardMode();
            }
            return 0;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getKeyboardMode returning default value");
            return 0;
        }
    }

    public boolean getWifiState() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getWifiState();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getWifiState() FAIL");
            return false;
        }
    }

    public boolean getUsbNetStateInternal() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getUsbNetStateInternal();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getUsbNetStateInternal() FAIL");
            return false;
        }
    }

    public String getUsbNetAddress(int addressType) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getUsbNetAddress(addressType);
            }
            return null;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getUsbNetAddress() FAIL");
            return null;
        }
    }
}
