package com.samsung.systemui.splugins.extensions;

import android.util.SparseArray;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.util.SparseArrayMapWrapper;
import com.samsung.systemui.splugins.volume.VolumeState;
import com.samsung.systemui.splugins.volume.VolumeStreamState;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt___MapsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class VolumeStateConverter {
    public static final VolumeStateConverter INSTANCE = new VolumeStateConverter();

    private VolumeStateConverter() {
    }

    public static final VolumeState convert(VolumeDialogController.State state) {
        return new VolumeState.Builder().setStreamStates(INSTANCE.convert(state.states)).activeStream(state.activeStream).ringerModeInternal(state.ringerModeInternal).fixedScoVolume(state.fixedSCOVolume).remoteMic(state.remoteMic).zenMode(state.zenMode).disallowRinger(state.disallowRinger).disallowSystem(state.disallowSystem).disallowMedia(state.disallowMedia).isDualAudio(state.dualAudio).isAodVolumePanel(state.aodEnabled).isLeBroadcasting(state.isLeBroadcasting).broadcastMode(state.broadcastMode).build();
    }

    public final List<VolumeStreamState> convert(SparseArray<VolumeDialogController.StreamState> sparseArray) {
        List<Pair> list = MapsKt___MapsKt.toList(new SparseArrayMapWrapper(sparseArray));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (Pair pair : list) {
            VolumeStreamState.Builder streamType = new VolumeStreamState.Builder().setStreamType(((Number) pair.getFirst()).intValue());
            String str = ((VolumeDialogController.StreamState) pair.getSecond()).nameRes;
            String str2 = "";
            if (str == null) {
                str = "";
            }
            VolumeStreamState.Builder max = streamType.nameRes(str).isDynamic(((VolumeDialogController.StreamState) pair.getSecond()).dynamic).isMuted(((VolumeDialogController.StreamState) pair.getSecond()).muted).isMuteSupport(((VolumeDialogController.StreamState) pair.getSecond()).muteSupported).isRoutedToBt(((VolumeDialogController.StreamState) pair.getSecond()).routedToBluetooth).isRoutedToAppMirroring(((VolumeDialogController.StreamState) pair.getSecond()).appMirroring).isRoutedToRemoteSpeaker(((VolumeDialogController.StreamState) pair.getSecond()).remoteSpeaker).isRoutedToBuds(((VolumeDialogController.StreamState) pair.getSecond()).routedToBuds).isRoutedToBuds3(((VolumeDialogController.StreamState) pair.getSecond()).routedToBuds3).isRoutedToHomeMini(((VolumeDialogController.StreamState) pair.getSecond()).routedToHomeMini).isRoutedToHearingAid(((VolumeDialogController.StreamState) pair.getSecond()).routedToHearingAid).isRoutedToHeadset(((VolumeDialogController.StreamState) pair.getSecond()).routedToHeadset).isDisabledFixedSession(((VolumeDialogController.StreamState) pair.getSecond()).remoteFixedVolume).level(((VolumeDialogController.StreamState) pair.getSecond()).level).min(((VolumeDialogController.StreamState) pair.getSecond()).levelMin).max(((VolumeDialogController.StreamState) pair.getSecond()).levelMax);
            String str3 = ((VolumeDialogController.StreamState) pair.getSecond()).remoteLabel;
            if (str3 == null) {
                str3 = "";
            }
            VolumeStreamState.Builder remoteLabel = max.remoteLabel(str3);
            String str4 = ((VolumeDialogController.StreamState) pair.getSecond()).bluetoothDeviceAddress;
            if (str4 == null) {
                str4 = "";
            }
            VolumeStreamState.Builder dualBtDeviceAddress = remoteLabel.dualBtDeviceAddress(str4);
            String str5 = ((VolumeDialogController.StreamState) pair.getSecond()).bluetoothDeviceName;
            if (str5 != null) {
                str2 = str5;
            }
            arrayList.add(dualBtDeviceAddress.dualBtDeviceName(str2).build());
        }
        return arrayList;
    }
}
