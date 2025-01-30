package com.android.systemui.statusbar.phone;

import android.R;
import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Trace;
import com.android.systemui.LsRune;
import com.android.systemui.shade.ShadeControllerImpl;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FoldStateListener implements DeviceStateManager.DeviceStateCallback {
    public final int[] foldedDeviceStates;
    public final int[] goToSleepDeviceStates;
    public final OnFoldStateChangeListener listener;
    public Boolean wasFolded;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnFoldStateChangeListener {
    }

    public FoldStateListener(Context context, OnFoldStateChangeListener onFoldStateChangeListener) {
        this.listener = onFoldStateChangeListener;
        this.foldedDeviceStates = context.getResources().getIntArray(R.array.networks_not_clear_data);
        this.goToSleepDeviceStates = context.getResources().getIntArray(R.array.config_sms_enabled_single_shift_tables);
    }

    public final void onStateChanged(int i) {
        boolean contains = ArraysKt___ArraysKt.contains(i, this.foldedDeviceStates);
        if (Intrinsics.areEqual(this.wasFolded, Boolean.valueOf(contains))) {
            return;
        }
        this.wasFolded = Boolean.valueOf(contains);
        ArraysKt___ArraysKt.contains(i, this.goToSleepDeviceStates);
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) ((CentralSurfacesImpl$$ExternalSyntheticLambda0) this.listener).f$0;
        centralSurfacesImpl.getClass();
        Trace.beginSection("CentralSurfaces#onFoldedStateChanged");
        centralSurfacesImpl.mIsFolded = contains;
        centralSurfacesImpl.mSecLightRevealScrimHelper.isFolded = contains;
        if (LsRune.AOD_SUB_DISPLAY_LOCK || LsRune.AOD_SUB_DISPLAY_COVER) {
            centralSurfacesImpl.mAODAmbientWallpaperHelper.isFolded = contains;
            centralSurfacesImpl.mWallpaperChangedReceiver.onReceive(centralSurfacesImpl.mContext, null);
        }
        ShadeControllerImpl shadeControllerImpl = (ShadeControllerImpl) centralSurfacesImpl.mShadeController;
        boolean isShadeFullyExpanded = shadeControllerImpl.mNotificationPanelViewController.isShadeFullyExpanded();
        boolean isExpandingOrCollapsing = shadeControllerImpl.mNotificationPanelViewController.isExpandingOrCollapsing();
        if (centralSurfacesImpl.mState != 0 && (isShadeFullyExpanded || isExpandingOrCollapsing)) {
            centralSurfacesImpl.mCloseQsBeforeScreenOff = true;
        }
        Trace.endSection();
    }
}
