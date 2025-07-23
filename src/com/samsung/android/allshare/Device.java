package com.samsung.android.allshare;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public abstract class Device {
    public abstract DeviceDomain getDeviceDomain();

    public abstract DeviceType getDeviceType();

    public abstract String getID();

    public abstract String getIPAddress();

    public abstract Uri getIcon();

    public abstract ArrayList<Icon> getIconList();

    public abstract String getModelName();

    public abstract String getNIC();

    public abstract String getName();

    public abstract String getP2pMacAddress();

    public abstract String getProductCapInfo(InformationType informationType);

    public abstract String getScreenSharingInfo();

    public abstract String getScreenSharingInfo(InformationType informationType);

    public abstract String getScreenSharingP2pMacAddr();

    public abstract String getSecProductP2pMacAddr();

    public abstract boolean isSeekableOnPaused();

    public abstract boolean isSupportedByType(int i);

    public abstract boolean isWholeHomeAudio();

    public abstract void requestMobileToTV(String str, int i);

    protected Device() {
    }

    public enum DeviceDomain {
        MY_DEVICE("MY_DEVICE"),
        LOCAL_NETWORK("LOCAL_NETWORK"),
        REMOTE_NETWORK("REMOTE_NETWORK"),
        UNKNOWN("UNKNOWN");

        private final String enumString;

        DeviceDomain(String str) {
            this.enumString = str;
        }

        public String enumToString() {
            return this.enumString;
        }

        public static DeviceDomain stringToEnum(String str) {
            if (str == null) {
                return UNKNOWN;
            }
            if (str.equals("LOCAL_NETWORK")) {
                return LOCAL_NETWORK;
            }
            if (str.equals("MY_DEVICE")) {
                return MY_DEVICE;
            }
            if (str.equals("REMOTE_NETWORK")) {
                return REMOTE_NETWORK;
            }
            if (str.equals("UNKNOWN")) {
                return UNKNOWN;
            }
            return UNKNOWN;
        }
    }

    public enum DeviceType {
        DEVICE_IMAGEVIEWER("DEVICE_IMAGEVIEWER"),
        DEVICE_AVPLAYER("DEVICE_AVPLAYER"),
        DEVICE_PROVIDER("DEVICE_PROVIDER"),
        DEVICE_FILERECEIVER("DEVICE_FILERECEIVER"),
        DEVICE_TV_CONTROLLER("DEVICE_TV_CONTROLLER"),
        DEVICE_KIES("DEVICE_KIES"),
        DEVICE_SCREENSHARING("DEVICE_SCREENSHARING"),
        UNKNOWN("UNKNOWN");

        private final String enumString;

        DeviceType(String str) {
            this.enumString = str;
        }

        public String enumToString() {
            return this.enumString;
        }

        public static DeviceType stringToEnum(String str) {
            if (str == null) {
                return UNKNOWN;
            }
            if (str.equals("DEVICE_AVPLAYER")) {
                return DEVICE_AVPLAYER;
            }
            if (str.equals("DEVICE_FILERECEIVER")) {
                return DEVICE_FILERECEIVER;
            }
            if (str.equals("DEVICE_IMAGEVIEWER")) {
                return DEVICE_IMAGEVIEWER;
            }
            if (str.equals("DEVICE_KIES")) {
                return DEVICE_KIES;
            }
            if (str.equals("DEVICE_SCREENSHARING")) {
                return DEVICE_SCREENSHARING;
            }
            if (str.equals("DEVICE_PROVIDER")) {
                return DEVICE_PROVIDER;
            }
            if (str.equals("DEVICE_TV_CONTROLLER")) {
                return DEVICE_TV_CONTROLLER;
            }
            if (str.equals("UNKNOWN")) {
                return UNKNOWN;
            }
            return UNKNOWN;
        }
    }

    public enum InformationType {
        ALL_INFO("ALL_INFO"),
        P2P_MAC_ADDRESS("P2P_MAC_ADDRESS"),
        UNKNOWN("UNKNOWN");

        private final String enumString;

        InformationType(String str) {
            this.enumString = str;
        }

        public String enumToString() {
            return this.enumString;
        }

        public static InformationType stringToEnum(String str) {
            if (str == null) {
                return UNKNOWN;
            }
            if (str.equals("ALL_INFO")) {
                return ALL_INFO;
            }
            if (str.equals("P2P_MAC_ADDRESS")) {
                return P2P_MAC_ADDRESS;
            }
            if (str.equals("UNKNOWN")) {
                return UNKNOWN;
            }
            return UNKNOWN;
        }
    }

    public String toString() {
        return "Type[" + getDeviceType() + "] Name[" + getName() + "] Ip[" + getIPAddress() + "] Model[" + getModelName() + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
