package com.android.systemui.media.controls.data.repository;

import com.android.systemui.Dumpable;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class MediaDataRepository implements Dumpable {
    public final StateFlowImpl _mediaEntries;
    public final ReadonlyStateFlow mediaEntries;

    public MediaDataRepository(DumpManager dumpManager) {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(new LinkedHashMap());
        this._mediaEntries = stateFlowImplMutableStateFlow;
        this.mediaEntries = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        dumpManager.registerNormalDumpable("MediaDataRepository", this);
    }

    public final MediaData addMediaEntry(MediaData mediaData, String str) {
        StateFlowImpl stateFlowImpl = this._mediaEntries;
        LinkedHashMap linkedHashMap = new LinkedHashMap((Map) stateFlowImpl.getValue());
        MediaData mediaData2 = (MediaData) linkedHashMap.put(str, mediaData);
        stateFlowImpl.updateState(null, linkedHashMap);
        return mediaData2;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("mediaEntries: ", this.mediaEntries.$$delegate_0.getValue(), printWriter);
    }

    public final MediaData removeMediaEntry(String str) {
        StateFlowImpl stateFlowImpl = this._mediaEntries;
        LinkedHashMap linkedHashMap = new LinkedHashMap((Map) stateFlowImpl.getValue());
        MediaData mediaData = (MediaData) linkedHashMap.remove(str);
        stateFlowImpl.updateState(null, linkedHashMap);
        return mediaData;
    }
}
