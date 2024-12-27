package com.android.server.display.notifications;

import android.content.Context;
import android.hardware.usb.DisplayPortAltModeInfo;
import android.hardware.usb.UsbManager;

import com.android.server.display.feature.DisplayManagerFlags;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ConnectedDisplayUsbErrorsDetector
        implements UsbManager.DisplayPortAltModeInfoListener {
    public final Context mContext;
    public final Injector mInjector;
    public final boolean mIsConnectedDisplayErrorHandlingEnabled;
    public DisplayNotificationManager mListener;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Injector {}

    public ConnectedDisplayUsbErrorsDetector(
            DisplayManagerFlags displayManagerFlags, Context context, Injector injector) {
        this.mContext = context;
        this.mInjector = injector;
        this.mIsConnectedDisplayErrorHandlingEnabled =
                displayManagerFlags.mConnectedDisplayErrorHandlingFlagState.isEnabled();
    }

    public final void onDisplayPortAltModeInfoChanged(
            String str, DisplayPortAltModeInfo displayPortAltModeInfo) {
        if (this.mListener == null) {
            return;
        }
        if (2 == displayPortAltModeInfo.getPartnerSinkStatus()
                && 1 == displayPortAltModeInfo.getCableStatus()) {
            this.mListener.onCableNotCapableDisplayPort();
        } else if (2 == displayPortAltModeInfo.getLinkTrainingStatus()) {
            this.mListener.onDisplayPortLinkTrainingFailure();
        }
    }
}
