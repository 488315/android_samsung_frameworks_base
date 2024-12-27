package com.android.systemui.media.controls.data.repository;

import com.android.systemui.Dumpable;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import com.android.systemui.media.controls.util.MediaFlags;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaDataRepository implements Dumpable {
    public final StateFlowImpl _mediaEntries;
    public final StateFlowImpl _smartspaceMediaData;
    public final ReadonlyStateFlow mediaEntries;
    public final ReadonlyStateFlow smartspaceMediaData;

    public MediaDataRepository(MediaFlags mediaFlags, DumpManager dumpManager) {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new LinkedHashMap());
        this._mediaEntries = MutableStateFlow;
        this.mediaEntries = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(new SmartspaceMediaData(null, false, null, null, null, null, 0L, null, 0L, 511, null));
        this._smartspaceMediaData = MutableStateFlow2;
        this.smartspaceMediaData = FlowKt.asStateFlow(MutableStateFlow2);
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
