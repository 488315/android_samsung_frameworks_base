package com.android.systemui.noticenter;

import android.os.Bundle;
import android.widget.RemoteViews;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.notification.collection.coordinator.NotilusCoordinator;
import com.samsung.systemui.splugins.noticenter.PluginNotiCenter;
import java.util.ArrayList;
import java.util.HashSet;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotiCenterPlugin$notiCenterCallback$1 implements PluginNotiCenter.Callback {
    @Override // com.samsung.systemui.splugins.noticenter.PluginNotiCenter.Callback
    public final void onChangedVisibilityOnKeyguard(final boolean z) {
        NotiCenterPlugin.handler.post(new Runnable() { // from class: com.android.systemui.noticenter.NotiCenterPlugin$notiCenterCallback$1$onChangedVisibilityOnKeyguard$1
            @Override // java.lang.Runnable
            public final void run() {
                NotiCenterPlugin notiCenterPlugin = NotiCenterPlugin.INSTANCE;
                NotiCenterPlugin.showNotilusOnKeyguard = z;
            }
        });
    }

    @Override // com.samsung.systemui.splugins.noticenter.PluginNotiCenter.Callback
    public final void onNoclearAppListUpdate(Bundle bundle) {
        HashSet hashSet;
        ArrayList<String> stringArrayList = bundle.getStringArrayList("NoclearAppList");
        NotiCenterPlugin notiCenterPlugin = NotiCenterPlugin.INSTANCE;
        if (stringArrayList != null) {
            hashSet = new HashSet(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(stringArrayList, 12)));
            CollectionsKt___CollectionsKt.toCollection(stringArrayList, hashSet);
        } else {
            hashSet = new HashSet();
        }
        notiCenterPlugin.getClass();
        NotiCenterPlugin.noclearAppList = hashSet;
        if (NotiCenterPlugin.mListener != null) {
            NotiCenterPlugin.handler.post(new Runnable() { // from class: com.android.systemui.noticenter.NotiCenterPlugin$notiCenterCallback$1$onNoclearAppListUpdate$1
                @Override // java.lang.Runnable
                public final void run() {
                    NotilusCoordinator notilusCoordinator = NotiCenterPlugin.mListener;
                    if (notilusCoordinator != null) {
                        notilusCoordinator.invalidateList("onUpdateNotiList");
                    }
                }
            });
        }
    }

    @Override // com.samsung.systemui.splugins.noticenter.PluginNotiCenter.Callback
    public final void onNoclearUpdate(boolean z) {
        NotiCenterPlugin.INSTANCE.getClass();
        NotiCenterPlugin.noclearEnabled = z;
        if (NotiCenterPlugin.mListener != null) {
            NotiCenterPlugin.handler.post(new Runnable() { // from class: com.android.systemui.noticenter.NotiCenterPlugin$notiCenterCallback$1$onNoclearUpdate$1
                @Override // java.lang.Runnable
                public final void run() {
                    NotilusCoordinator notilusCoordinator = NotiCenterPlugin.mListener;
                    if (notilusCoordinator != null) {
                        notilusCoordinator.invalidateList("onUpdateNotiList");
                    }
                }
            });
        }
    }

    @Override // com.samsung.systemui.splugins.noticenter.PluginNotiCenter.Callback
    public final void onNotiStarPanelShowOnKeyguard(boolean z) {
        ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).dispatchNotiStarState(z);
    }

    @Override // com.samsung.systemui.splugins.noticenter.PluginNotiCenter.Callback
    public final void onNotiCenterPanelUpdate(RemoteViews remoteViews) {
    }
}
