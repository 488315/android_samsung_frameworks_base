package com.samsung.android.allshare.media;

import android.net.Uri;

import com.samsung.android.allshare.Device;
import com.samsung.android.allshare.ERROR;
import com.samsung.android.allshare.Item;

public abstract class ImageViewer extends Device {

    public interface IImageViewerEventListener {
        void onDeviceChanged(ImageViewerState imageViewerState, ERROR error);
    }

    public interface IImageViewerResponseListener {
        void onGetStateResponseReceived(ImageViewerState imageViewerState, ERROR error);

        void onShowResponseReceived(Item item, ContentInfo contentInfo, ERROR error);

        void onStopResponseReceived(ERROR error);
    }

    @Override // com.samsung.android.allshare.Device
    public abstract Device.DeviceDomain getDeviceDomain();

    @Override // com.samsung.android.allshare.Device
    public abstract Device.DeviceType getDeviceType();

    @Override // com.samsung.android.allshare.Device
    public abstract String getID();

    @Override // com.samsung.android.allshare.Device
    public abstract String getIPAddress();

    @Override // com.samsung.android.allshare.Device
    public abstract Uri getIcon();

    @Override // com.samsung.android.allshare.Device
    public abstract String getModelName();

    @Override // com.samsung.android.allshare.Device
    public abstract String getName();

    public abstract void getState();

    public abstract ImageViewerState getViewerState();

    @Deprecated
    public abstract boolean isRedirectSupportable();

    public abstract boolean isSupportRedirect();

    public abstract void setEventListener(IImageViewerEventListener iImageViewerEventListener);

    public abstract void setResponseListener(
            IImageViewerResponseListener iImageViewerResponseListener);

    public abstract void show(Item item, ContentInfo contentInfo);

    public abstract void stop();

    public abstract void zoom(int i, int i2, int i3, int i4);

    protected ImageViewer() {}

    public enum ImageViewerState {
        STOPPED("STOPPED"),
        BUFFERING("BUFFERING"),
        SHOWING("SHOWING"),
        CONTENT_CHANGED("CONTENT_CHANGED"),
        UNKNOWN("UNKNOWN");

        private final String enumString;

        ImageViewerState(String enumStr) {
            this.enumString = enumStr;
        }

        public String enumToString() {
            return this.enumString;
        }

        public static ImageViewerState stringToEnum(String enumStr) {
            if (enumStr == null) {
                return UNKNOWN;
            }
            if (enumStr.equals("BUFFERING")) {
                return BUFFERING;
            }
            if (enumStr.equals("CONTENT_CHANGED")) {
                return CONTENT_CHANGED;
            }
            if (enumStr.equals("SHOWING")) {
                return SHOWING;
            }
            if (enumStr.equals("STOPPED")) {
                return STOPPED;
            }
            if (enumStr.equals("UNKNOWN")) {
                return UNKNOWN;
            }
            return UNKNOWN;
        }
    }
}
