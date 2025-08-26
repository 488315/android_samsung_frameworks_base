package com.android.systemui.media.controls.data.repository;

import com.android.systemui.media.controls.data.model.MediaSortKeyModel;
import com.android.systemui.media.controls.shared.model.MediaCommonModel;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDataLoadingModel;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class MediaFilterRepository {
    public final StateFlowImpl _allUserEntries;
    public final StateFlowImpl _currentMedia;
    public final StateFlowImpl _selectedUserEntries;
    public final ReadonlyStateFlow allUserEntries;
    public final MediaFilterRepository$special$$inlined$thenByDescending$7 comparator;
    public final ReadonlyStateFlow currentMedia;
    public final ReadonlyStateFlow selectedUserEntries;
    public TreeMap sortedMedia;
    public final SystemClock systemClock;

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$7, java.util.Comparator] */
    public MediaFilterRepository(SystemClock systemClock) {
        this.systemClock = systemClock;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(new LinkedHashMap());
        this._selectedUserEntries = stateFlowImplMutableStateFlow;
        this.selectedUserEntries = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(new LinkedHashMap());
        this._allUserEntries = stateFlowImplMutableStateFlow2;
        this.allUserEntries = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        final Comparator comparator = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$compareByDescending$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                MediaSortKeyModel mediaSortKeyModel = (MediaSortKeyModel) obj2;
                Boolean bool = mediaSortKeyModel.isPlaying;
                Boolean bool2 = Boolean.TRUE;
                boolean z = false;
                Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual(bool, bool2) && mediaSortKeyModel.playbackLocation == 0);
                MediaSortKeyModel mediaSortKeyModel2 = (MediaSortKeyModel) obj;
                if (Intrinsics.areEqual(mediaSortKeyModel2.isPlaying, bool2) && mediaSortKeyModel2.playbackLocation == 0) {
                    z = true;
                }
                return ComparisonsKt__ComparisonsKt.compareValues(boolValueOf, Boolean.valueOf(z));
            }
        };
        final Comparator comparator2 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator.compare(obj, obj2);
                if (iCompare != 0) {
                    return iCompare;
                }
                MediaSortKeyModel mediaSortKeyModel = (MediaSortKeyModel) obj2;
                Boolean bool = mediaSortKeyModel.isPlaying;
                Boolean bool2 = Boolean.TRUE;
                boolean z = false;
                Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual(bool, bool2) && mediaSortKeyModel.playbackLocation == 1);
                MediaSortKeyModel mediaSortKeyModel2 = (MediaSortKeyModel) obj;
                if (Intrinsics.areEqual(mediaSortKeyModel2.isPlaying, bool2) && mediaSortKeyModel2.playbackLocation == 1) {
                    z = true;
                }
                return ComparisonsKt__ComparisonsKt.compareValues(boolValueOf, Boolean.valueOf(z));
            }
        };
        final Comparator comparator3 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator2.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(((MediaSortKeyModel) obj2).active), Boolean.valueOf(((MediaSortKeyModel) obj).active));
            }
        };
        final Comparator comparator4 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator3.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(!((MediaSortKeyModel) obj2).isResume), Boolean.valueOf(!((MediaSortKeyModel) obj).isResume));
            }
        };
        final Comparator comparator5 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$4
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator4.compare(obj, obj2);
                if (iCompare != 0) {
                    return iCompare;
                }
                return ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(((MediaSortKeyModel) obj2).playbackLocation != 2), Boolean.valueOf(((MediaSortKeyModel) obj).playbackLocation != 2));
            }
        };
        final Comparator comparator6 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$5
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator5.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((MediaSortKeyModel) obj2).lastActive), Long.valueOf(((MediaSortKeyModel) obj).lastActive));
            }
        };
        final Comparator comparator7 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$6
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator6.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((MediaSortKeyModel) obj2).updateTime), Long.valueOf(((MediaSortKeyModel) obj).updateTime));
            }
        };
        ?? r0 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$7
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator7.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt__ComparisonsKt.compareValues(((MediaSortKeyModel) obj2).notificationKey, ((MediaSortKeyModel) obj).notificationKey);
            }
        };
        this.comparator = r0;
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(new ArrayList());
        this._currentMedia = stateFlowImplMutableStateFlow3;
        this.currentMedia = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        this.sortedMedia = new TreeMap((Comparator) r0);
    }

    public final void addMediaDataLoadingState(MediaDataLoadingModel mediaDataLoadingModel, boolean z) {
        TreeMap treeMap = new TreeMap(this.comparator);
        TreeMap treeMap2 = this.sortedMedia;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : treeMap2.entrySet()) {
            if (!Intrinsics.areEqual(((MediaCommonModel) entry.getValue()).mediaLoadedModel.instanceId, mediaDataLoadingModel.getInstanceId())) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        treeMap.putAll(linkedHashMap);
        MediaData mediaData = (MediaData) ((Map) this._selectedUserEntries.getValue()).get(mediaDataLoadingModel.getInstanceId());
        StateFlowImpl stateFlowImpl = this._currentMedia;
        if (mediaData != null) {
            boolean z2 = mediaData.active;
            long j = mediaData.lastActive;
            SystemClock systemClock = this.systemClock;
            MediaSortKeyModel mediaSortKeyModel = new MediaSortKeyModel(mediaData.isPlaying, mediaData.playbackLocation, z2, mediaData.resumption, j, mediaData.notificationKey, systemClock.currentTimeMillis(), mediaData.instanceId);
            if (mediaDataLoadingModel instanceof MediaDataLoadingModel.Loaded) {
                MediaDataLoadingModel.Loaded loaded = (MediaDataLoadingModel.Loaded) mediaDataLoadingModel;
                boolean z3 = true;
                Boolean bool = mediaData.isPlaying;
                MediaCommonModel mediaCommonModel = new MediaCommonModel(loaded, (bool != null ? bool.booleanValue() ^ true : mediaData.isClearable) && !mediaData.active, z ? systemClock.currentTimeMillis() : 0L);
                treeMap.put(mediaSortKeyModel, mediaCommonModel);
                ArrayList arrayList = new ArrayList();
                arrayList.addAll((Collection) stateFlowImpl.getValue());
                int size = arrayList.size();
                int i = 0;
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    int i3 = i + 1;
                    if (i < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                    MediaCommonModel mediaCommonModel2 = (MediaCommonModel) obj;
                    if (Intrinsics.areEqual(mediaCommonModel2.mediaLoadedModel.instanceId, loaded.instanceId)) {
                        if (!mediaCommonModel2.equals(mediaCommonModel)) {
                            arrayList.set(i, mediaCommonModel);
                        }
                        z3 = false;
                    }
                    i = i3;
                }
                if (z3 && mediaData.active) {
                    stateFlowImpl.setValue(CollectionsKt___CollectionsKt.toList(treeMap.values()));
                } else {
                    stateFlowImpl.updateState(null, arrayList);
                }
                this.sortedMedia = treeMap;
            }
        }
        if (mediaDataLoadingModel instanceof MediaDataLoadingModel.Removed) {
            Iterable iterable = (Iterable) stateFlowImpl.getValue();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : iterable) {
                if (!Intrinsics.areEqual(((MediaDataLoadingModel.Removed) mediaDataLoadingModel).instanceId, ((MediaCommonModel) obj2).mediaLoadedModel.instanceId)) {
                    arrayList2.add(obj2);
                }
            }
            stateFlowImpl.updateState(null, arrayList2);
            this.sortedMedia = treeMap;
        }
    }

    public final boolean addSelectedUserMediaEntry(MediaData mediaData) {
        StateFlowImpl stateFlowImpl = this._selectedUserEntries;
        LinkedHashMap linkedHashMap = new LinkedHashMap((Map) stateFlowImpl.getValue());
        boolean zContainsKey = ((Map) stateFlowImpl.getValue()).containsKey(mediaData.instanceId);
        linkedHashMap.put(mediaData.instanceId, mediaData);
        stateFlowImpl.updateState(null, linkedHashMap);
        return zContainsKey;
    }
}
