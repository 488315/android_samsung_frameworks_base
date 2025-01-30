package com.android.systemui.statusbar.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.connectivity.SubRoomNetworkInfo;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.io.ByteArrayOutputStream;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CoverScreenIconController implements CommandQueue.Callbacks {
    public final DelayableExecutor bgExecutor;
    public final CarrierInfraMediator carrierInfraMediator;
    public final Context context;
    public final DelayableExecutor delayableExecutor;
    public final DelayableExecutor executor;
    public final MobileConnectionsRepository mobileConnectionsRepository;
    public final String slotMode;
    public final SubRoomNetworkInfo subRoomNetworkInfo;

    public CoverScreenIconController(SubScreenManager subScreenManager, CommandQueue commandQueue, CarrierInfraMediator carrierInfraMediator, SubRoomNetworkInfo subRoomNetworkInfo, Context context, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, BroadcastDispatcher broadcastDispatcher, MobileConnectionsRepository mobileConnectionsRepository, DelayableExecutor delayableExecutor3) {
        this.carrierInfraMediator = carrierInfraMediator;
        this.subRoomNetworkInfo = subRoomNetworkInfo;
        this.context = context;
        this.executor = delayableExecutor;
        this.bgExecutor = delayableExecutor2;
        this.mobileConnectionsRepository = mobileConnectionsRepository;
        this.delayableExecutor = delayableExecutor3;
        this.slotMode = context.getResources().getString(17042932);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.CoverScreenIconController$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                final CoverScreenIconController coverScreenIconController = CoverScreenIconController.this;
                coverScreenIconController.delayableExecutor.executeDelayed(100L, new Runnable() { // from class: com.android.systemui.statusbar.phone.CoverScreenIconController$broadcastReceiver$1$onReceive$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CoverScreenNetworkSignalModel noServiceInfo = CoverScreenIconController.this.mobileConnectionsRepository.getNoServiceInfo();
                        Log.d("CoverScreenIconController", "no service state=" + noServiceInfo);
                        CoverScreenIconController coverScreenIconController2 = CoverScreenIconController.this;
                        boolean z = noServiceInfo.isAirplaneMode;
                        coverScreenIconController2.getClass();
                        if (coverScreenIconController2.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.SUB_SCREEN_SIGNAL, 0, new Object[0])) {
                            SubRoomNetworkInfo subRoomNetworkInfo2 = coverScreenIconController2.subRoomNetworkInfo;
                            subRoomNetworkInfo2.isAirplane = z;
                            subRoomNetworkInfo2.noServiceType = noServiceInfo.noServiceType;
                            SubRoom.StateChangeListener stateChangeListener = subRoomNetworkInfo2.stateChangeListener;
                            if (stateChangeListener != null) {
                                Bundle bundle = new Bundle();
                                bundle.putBoolean(SubRoom.EXTRA_KEY_AIRPLANE_MODE, subRoomNetworkInfo2.isAirplane);
                                bundle.putInt(SubRoom.EXTRA_KEY_NO_SIGNAL, subRoomNetworkInfo2.noServiceType);
                                bundle.putByteArray(SubRoom.EXTRA_KEY_ROUTINE_MODE, subRoomNetworkInfo2.modeIcon);
                                stateChangeListener.onStateChanged(bundle);
                            }
                        }
                    }
                });
            }
        };
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.SUB_SCREEN_MODE_ICON, 0, new Object[0])) {
            subScreenManager.setSubRoom(303, subRoomNetworkInfo);
            commandQueue.addCallback((CommandQueue.Callbacks) this);
        }
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.SUB_SCREEN_SIGNAL, 0, new Object[0])) {
            BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, broadcastReceiver, new IntentFilter("android.intent.action.SERVICE_STATE"), null, null, 0, null, 60);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void removeIcon(String str) {
        if (Intrinsics.areEqual(str, this.slotMode)) {
            Log.d("CoverScreenIconController", "Remove mode icon");
            SubRoomNetworkInfo subRoomNetworkInfo = this.subRoomNetworkInfo;
            subRoomNetworkInfo.modeIcon = null;
            SubRoom.StateChangeListener stateChangeListener = subRoomNetworkInfo.stateChangeListener;
            if (stateChangeListener != null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(SubRoom.EXTRA_KEY_AIRPLANE_MODE, subRoomNetworkInfo.isAirplane);
                bundle.putInt(SubRoom.EXTRA_KEY_NO_SIGNAL, subRoomNetworkInfo.noServiceType);
                bundle.putByteArray(SubRoom.EXTRA_KEY_ROUTINE_MODE, subRoomNetworkInfo.modeIcon);
                stateChangeListener.onStateChanged(bundle);
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setIcon(String str, final StatusBarIcon statusBarIcon) {
        if (Intrinsics.areEqual(str, this.slotMode)) {
            if (statusBarIcon.visible) {
                ((ExecutorImpl) this.bgExecutor).execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.CoverScreenIconController$setModeIcon$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        int identifier = statusBarIcon.user.getIdentifier();
                        if (identifier == -1) {
                            identifier = 0;
                        }
                        Drawable loadDrawableAsUser = statusBarIcon.icon.loadDrawableAsUser(this.context, identifier);
                        Bitmap createBitmap = Bitmap.createBitmap(loadDrawableAsUser.getIntrinsicWidth(), loadDrawableAsUser.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(createBitmap);
                        loadDrawableAsUser.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                        loadDrawableAsUser.draw(canvas);
                        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        createBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("Set mode icon ", createBitmap.getWidth(), "x", createBitmap.getHeight(), "CoverScreenIconController");
                        final CoverScreenIconController coverScreenIconController = this;
                        ((ExecutorImpl) coverScreenIconController.executor).execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.CoverScreenIconController$setModeIcon$1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                CoverScreenIconController.this.subRoomNetworkInfo.modeIcon = byteArrayOutputStream.toByteArray();
                                SubRoomNetworkInfo subRoomNetworkInfo = CoverScreenIconController.this.subRoomNetworkInfo;
                                SubRoom.StateChangeListener stateChangeListener = subRoomNetworkInfo.stateChangeListener;
                                if (stateChangeListener != null) {
                                    Bundle bundle = new Bundle();
                                    bundle.putBoolean(SubRoom.EXTRA_KEY_AIRPLANE_MODE, subRoomNetworkInfo.isAirplane);
                                    bundle.putInt(SubRoom.EXTRA_KEY_NO_SIGNAL, subRoomNetworkInfo.noServiceType);
                                    bundle.putByteArray(SubRoom.EXTRA_KEY_ROUTINE_MODE, subRoomNetworkInfo.modeIcon);
                                    stateChangeListener.onStateChanged(bundle);
                                }
                            }
                        });
                    }
                });
                return;
            }
            Log.d("CoverScreenIconController", "Remove mode icon");
            SubRoomNetworkInfo subRoomNetworkInfo = this.subRoomNetworkInfo;
            subRoomNetworkInfo.modeIcon = null;
            SubRoom.StateChangeListener stateChangeListener = subRoomNetworkInfo.stateChangeListener;
            if (stateChangeListener != null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(SubRoom.EXTRA_KEY_AIRPLANE_MODE, subRoomNetworkInfo.isAirplane);
                bundle.putInt(SubRoom.EXTRA_KEY_NO_SIGNAL, subRoomNetworkInfo.noServiceType);
                bundle.putByteArray(SubRoom.EXTRA_KEY_ROUTINE_MODE, subRoomNetworkInfo.modeIcon);
                stateChangeListener.onStateChanged(bundle);
            }
        }
    }
}
