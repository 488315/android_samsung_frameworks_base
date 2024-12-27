package com.android.systemui.media.controls.data.repository;

import android.content.Context;
import com.android.systemui.media.controls.data.model.MediaSortKeyModel;
import com.android.systemui.media.controls.shared.model.MediaCommonModel;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDataLoadingModel;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaLoadingModel;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaFilterRepository {
    public final StateFlowImpl _allUserEntries;
    public final StateFlowImpl _currentMedia;
    public final StateFlowImpl _reactivatedId;
    public final StateFlowImpl _selectedUserEntries;
    public final StateFlowImpl _smartspaceMediaData;
    public final ReadonlyStateFlow allUserEntries;
    public final MediaFilterRepository$special$$inlined$thenByDescending$8 comparator;
    public final ConfigurationController configurationController;
    public final ReadonlyStateFlow currentMedia;
    public Locale locale;
    public String mediaFromRecPackageName;
    public final Flow onAnyMediaConfigurationChange;
    public final ReadonlyStateFlow reactivatedId;
    public final ReadonlyStateFlow selectedUserEntries;
    public final ReadonlyStateFlow smartspaceMediaData;
    public TreeMap sortedMedia;
    public final SystemClock systemClock;

    /* JADX WARN: Type inference failed for: r1v19, types: [com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$8, java.util.Comparator] */
    public MediaFilterRepository(Context context, SystemClock systemClock, ConfigurationController configurationController) {
        this.systemClock = systemClock;
        this.configurationController = configurationController;
        this.onAnyMediaConfigurationChange = FlowConflatedKt.conflatedCallbackFlow(new MediaFilterRepository$onAnyMediaConfigurationChange$1(this, context, null));
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._reactivatedId = MutableStateFlow;
        this.reactivatedId = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(new SmartspaceMediaData(null, false, null, null, null, null, 0L, null, 0L, 511, null));
        this._smartspaceMediaData = MutableStateFlow2;
        this.smartspaceMediaData = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(new LinkedHashMap());
        this._selectedUserEntries = MutableStateFlow3;
        this.selectedUserEntries = FlowKt.asStateFlow(MutableStateFlow3);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(new LinkedHashMap());
        this._allUserEntries = MutableStateFlow4;
        this.allUserEntries = FlowKt.asStateFlow(MutableStateFlow4);
        final Comparator comparator = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$compareByDescending$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                MediaSortKeyModel mediaSortKeyModel = (MediaSortKeyModel) obj2;
                Boolean bool = mediaSortKeyModel.isPlaying;
                Boolean bool2 = Boolean.TRUE;
                boolean z = false;
                Boolean valueOf = Boolean.valueOf(Intrinsics.areEqual(bool, bool2) && mediaSortKeyModel.playbackLocation == 0);
                MediaSortKeyModel mediaSortKeyModel2 = (MediaSortKeyModel) obj;
                if (Intrinsics.areEqual(mediaSortKeyModel2.isPlaying, bool2) && mediaSortKeyModel2.playbackLocation == 0) {
                    z = true;
                }
                return ComparisonsKt__ComparisonsKt.compareValues(valueOf, Boolean.valueOf(z));
            }
        };
        final Comparator comparator2 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare = comparator.compare(obj, obj2);
                if (compare != 0) {
                    return compare;
                }
                MediaSortKeyModel mediaSortKeyModel = (MediaSortKeyModel) obj2;
                Boolean bool = mediaSortKeyModel.isPlaying;
                Boolean bool2 = Boolean.TRUE;
                boolean z = false;
                Boolean valueOf = Boolean.valueOf(Intrinsics.areEqual(bool, bool2) && mediaSortKeyModel.playbackLocation == 1);
                MediaSortKeyModel mediaSortKeyModel2 = (MediaSortKeyModel) obj;
                if (Intrinsics.areEqual(mediaSortKeyModel2.isPlaying, bool2) && mediaSortKeyModel2.playbackLocation == 1) {
                    z = true;
                }
                return ComparisonsKt__ComparisonsKt.compareValues(valueOf, Boolean.valueOf(z));
            }
        };
        final Comparator comparator3 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare = comparator2.compare(obj, obj2);
                return compare != 0 ? compare : ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(((MediaSortKeyModel) obj2).active), Boolean.valueOf(((MediaSortKeyModel) obj).active));
            }
        };
        final Comparator comparator4 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare = comparator3.compare(obj, obj2);
                return compare != 0 ? compare : ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(((MediaSortKeyModel) obj2).isPrioritizedRec), Boolean.valueOf(((MediaSortKeyModel) obj).isPrioritizedRec));
            }
        };
        final Comparator comparator5 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$4
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare = comparator4.compare(obj, obj2);
                return compare != 0 ? compare : ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(!((MediaSortKeyModel) obj2).isResume), Boolean.valueOf(!((MediaSortKeyModel) obj).isResume));
            }
        };
        final Comparator comparator6 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$5
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare = comparator5.compare(obj, obj2);
                if (compare != 0) {
                    return compare;
                }
                return ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(((MediaSortKeyModel) obj2).playbackLocation != 2), Boolean.valueOf(((MediaSortKeyModel) obj).playbackLocation != 2));
            }
        };
        final Comparator comparator7 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$6
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare = comparator6.compare(obj, obj2);
                return compare != 0 ? compare : ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((MediaSortKeyModel) obj2).lastActive), Long.valueOf(((MediaSortKeyModel) obj).lastActive));
            }
        };
        final Comparator comparator8 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$7
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare = comparator7.compare(obj, obj2);
                return compare != 0 ? compare : ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((MediaSortKeyModel) obj2).updateTime), Long.valueOf(((MediaSortKeyModel) obj).updateTime));
            }
        };
        ?? r1 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$8
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare = comparator8.compare(obj, obj2);
                return compare != 0 ? compare : ComparisonsKt__ComparisonsKt.compareValues(((MediaSortKeyModel) obj2).notificationKey, ((MediaSortKeyModel) obj).notificationKey);
            }
        };
        this.comparator = r1;
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(new ArrayList());
        this._currentMedia = MutableStateFlow5;
        this.currentMedia = FlowKt.asStateFlow(MutableStateFlow5);
        this.sortedMedia = new TreeMap((Comparator) r1);
        this.locale = context.getResources().getConfiguration().getLocales().get(0);
    }

    public final void addMediaDataLoadingState(MediaDataLoadingModel mediaDataLoadingModel) {
        TreeMap treeMap = new TreeMap(this.comparator);
        TreeMap treeMap2 = this.sortedMedia;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : treeMap2.entrySet()) {
            MediaCommonModel mediaCommonModel = (MediaCommonModel) entry.getValue();
            if (!(mediaCommonModel instanceof MediaCommonModel.MediaControl) || !Intrinsics.areEqual(((MediaCommonModel.MediaControl) mediaCommonModel).mediaLoadedModel.instanceId, mediaDataLoadingModel.getInstanceId())) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        treeMap.putAll(linkedHashMap);
        MediaData mediaData = (MediaData) ((Map) this._selectedUserEntries.getValue()).get(mediaDataLoadingModel.getInstanceId());
        StateFlowImpl stateFlowImpl = this._currentMedia;
        if (mediaData != null) {
            MediaSortKeyModel mediaSortKeyModel = new MediaSortKeyModel(false, mediaData.isPlaying, mediaData.playbackLocation, mediaData.active, mediaData.resumption, mediaData.lastActive, mediaData.notificationKey, this.systemClock.currentTimeMillis(), mediaData.instanceId);
            if (mediaDataLoadingModel instanceof MediaDataLoadingModel.Loaded) {
                MediaDataLoadingModel.Loaded loaded = (MediaDataLoadingModel.Loaded) mediaDataLoadingModel;
                Boolean bool = mediaData.isPlaying;
                boolean z = (bool != null ? bool.booleanValue() ^ true : mediaData.isClearable) && !mediaData.active;
                Boolean bool2 = Boolean.TRUE;
                boolean areEqual = Intrinsics.areEqual(bool, bool2);
                String str = mediaData.packageName;
                MediaCommonModel.MediaControl mediaControl = new MediaCommonModel.MediaControl(loaded, z, areEqual && Intrinsics.areEqual(this.mediaFromRecPackageName, str));
                treeMap.put(mediaSortKeyModel, mediaControl);
                if (Intrinsics.areEqual(this.mediaFromRecPackageName, str)) {
                    if (Intrinsics.areEqual(bool, bool2)) {
                        this.mediaFromRecPackageName = null;
                        stateFlowImpl.setValue(CollectionsKt___CollectionsKt.toList(treeMap.values()));
                    }
                } else if (treeMap.size() <= ((List) stateFlowImpl.getValue()).size() || !mediaData.active) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.addAll((Collection) stateFlowImpl.getValue());
                    Iterator it = arrayList.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        Object next = it.next();
                        int i2 = i + 1;
                        if (i < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                            throw null;
                        }
                        MediaCommonModel mediaCommonModel2 = (MediaCommonModel) next;
                        if ((mediaCommonModel2 instanceof MediaCommonModel.MediaControl) && Intrinsics.areEqual(((MediaCommonModel.MediaControl) mediaCommonModel2).mediaLoadedModel.instanceId, ((MediaDataLoadingModel.Loaded) mediaDataLoadingModel).instanceId) && !Intrinsics.areEqual(mediaCommonModel2, mediaControl)) {
                            arrayList.set(i, mediaControl);
                        }
                        i = i2;
                    }
                    stateFlowImpl.updateState(null, arrayList);
                } else {
                    stateFlowImpl.setValue(CollectionsKt___CollectionsKt.toList(treeMap.values()));
                }
            }
        }
        this.sortedMedia = treeMap;
        if (mediaDataLoadingModel instanceof MediaDataLoadingModel.Removed) {
            Iterable iterable = (Iterable) stateFlowImpl.getValue();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : iterable) {
                MediaCommonModel mediaCommonModel3 = (MediaCommonModel) obj;
                if (((mediaCommonModel3 instanceof MediaCommonModel.MediaControl) && Intrinsics.areEqual(((MediaDataLoadingModel.Removed) mediaDataLoadingModel).instanceId, ((MediaCommonModel.MediaControl) mediaCommonModel3).mediaLoadedModel.instanceId)) ? false : true) {
                    arrayList2.add(obj);
                }
            }
            stateFlowImpl.updateState(null, arrayList2);
        }
    }

    public final void setRecommendationsLoadingState(SmartspaceMediaLoadingModel smartspaceMediaLoadingModel) {
        boolean z = smartspaceMediaLoadingModel instanceof SmartspaceMediaLoadingModel.Loaded;
        boolean z2 = z ? ((SmartspaceMediaLoadingModel.Loaded) smartspaceMediaLoadingModel).isPrioritized : false;
        TreeMap treeMap = new TreeMap(this.comparator);
        TreeMap treeMap2 = this.sortedMedia;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : treeMap2.entrySet()) {
            if (!(((MediaCommonModel) entry.getValue()) instanceof MediaCommonModel.MediaRecommendations)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        treeMap.putAll(linkedHashMap);
        MediaSortKeyModel mediaSortKeyModel = new MediaSortKeyModel(z2, Boolean.FALSE, 0, ((SmartspaceMediaData) this._smartspaceMediaData.getValue()).isActive, false, 0L, null, 0L, null, 500, null);
        StateFlowImpl stateFlowImpl = this._currentMedia;
        if (z) {
            treeMap.put(mediaSortKeyModel, new MediaCommonModel.MediaRecommendations(smartspaceMediaLoadingModel));
        } else if (smartspaceMediaLoadingModel instanceof SmartspaceMediaLoadingModel.Removed) {
            Iterable iterable = (Iterable) stateFlowImpl.getValue();
            ArrayList arrayList = new ArrayList();
            for (Object obj : iterable) {
                if (!(((MediaCommonModel) obj) instanceof MediaCommonModel.MediaRecommendations)) {
                    arrayList.add(obj);
                }
            }
            stateFlowImpl.updateState(null, arrayList);
        }
        if (treeMap.size() > this.sortedMedia.size()) {
            stateFlowImpl.setValue(CollectionsKt___CollectionsKt.toList(treeMap.values()));
        }
        this.sortedMedia = treeMap;
    }
}
