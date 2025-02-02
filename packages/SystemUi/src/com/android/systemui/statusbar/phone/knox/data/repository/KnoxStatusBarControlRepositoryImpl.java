package com.android.systemui.statusbar.phone.knox.data.repository;

import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.statusbar.phone.knox.data.model.KnoxStatusBarControlModel;
import com.android.systemui.util.DeviceType;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KnoxStatusBarControlRepositoryImpl implements KnoxStatusBarControlRepository {
    public final boolean enableLog = DeviceType.isEngOrUTBinary();
    public final KnoxStateMonitor knoxStateMonitor;
    public final KnoxStatusBarControlModel knoxStatusBarControlModel;
    public final ReadonlyStateFlow knoxStatusBarState;

    public KnoxStatusBarControlRepositoryImpl(KnoxStateMonitor knoxStateMonitor, CoroutineScope coroutineScope) {
        this.knoxStateMonitor = knoxStateMonitor;
        KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) knoxStateMonitor;
        boolean isStatusBarHidden = knoxStateMonitorImpl.isStatusBarHidden();
        CustomSdkMonitor customSdkMonitor = knoxStateMonitorImpl.mCustomSdkMonitor;
        KnoxStatusBarControlModel knoxStatusBarControlModel = new KnoxStatusBarControlModel(isStatusBarHidden, customSdkMonitor != null && customSdkMonitor.mStatusBarIconsState, customSdkMonitor == null ? null : customSdkMonitor.mStatusBarText, customSdkMonitor == null ? 0 : customSdkMonitor.mStatusBarTextStyle, customSdkMonitor == null ? 0 : customSdkMonitor.mStatusBarTextSize, customSdkMonitor == null ? 0 : customSdkMonitor.mStatusBarTextWidth);
        this.knoxStatusBarControlModel = knoxStatusBarControlModel;
        this.knoxStatusBarState = FlowKt.stateIn(FlowKt.callbackFlow(new KnoxStatusBarControlRepositoryImpl$knoxStatusBarState$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion), knoxStatusBarControlModel);
    }
}
