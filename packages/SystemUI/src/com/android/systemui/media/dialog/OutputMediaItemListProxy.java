package com.android.systemui.media.dialog;

import android.content.Context;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public class OutputMediaItemListProxy {
    public final Context mContext;
    public final List mOutputMediaItemList = new CopyOnWriteArrayList();
    public final List mSelectedMediaItems = new CopyOnWriteArrayList();
    public final List mSuggestedMediaItems = new CopyOnWriteArrayList();
    public final List mSpeakersAndDisplaysMediaItems = new CopyOnWriteArrayList();

    public OutputMediaItemListProxy(Context context) {
        this.mContext = context;
    }

    public static List getRemainingMediaItems(List list, Set set) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) list;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            MediaItem mediaItem = (MediaItem) obj;
            Optional optional = mediaItem.mMediaDeviceOptional;
            if (optional.isPresent()) {
                if (!((HashSet) set).contains(((MediaDevice) optional.get()).getId())) {
                    arrayList.add(mediaItem);
                }
            }
        }
        return arrayList;
    }

    public static void updateMediaItems(List list, List list2, Map map, Set set) {
        ArrayList arrayList = new ArrayList();
        Iterator it = ((CopyOnWriteArrayList) list).iterator();
        while (it.hasNext()) {
            MediaItem mediaItem = (MediaItem) it.next();
            if (mediaItem != null && mediaItem.mMediaDeviceOptional.isPresent()) {
                arrayList.add(((MediaDevice) mediaItem.mMediaDeviceOptional.get()).getId());
            }
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            String str = (String) obj;
            MediaItem mediaItem2 = (MediaItem) ((HashMap) map).get(str);
            if (mediaItem2 != null) {
                ((CopyOnWriteArrayList) list2).add(mediaItem2);
                ((HashSet) set).add(str);
            }
        }
    }

    public final void clear() {
        ((CopyOnWriteArrayList) this.mSelectedMediaItems).clear();
        ((CopyOnWriteArrayList) this.mSuggestedMediaItems).clear();
        ((CopyOnWriteArrayList) this.mSpeakersAndDisplaysMediaItems).clear();
        ((CopyOnWriteArrayList) this.mOutputMediaItemList).clear();
    }

    public final List getOutputMediaItemList() {
        if (isEmpty() && !((CopyOnWriteArrayList) this.mOutputMediaItemList).isEmpty()) {
            ((CopyOnWriteArrayList) this.mOutputMediaItemList).clear();
        } else if (!isEmpty() && ((CopyOnWriteArrayList) this.mOutputMediaItemList).isEmpty()) {
            List list = this.mOutputMediaItemList;
            CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
            copyOnWriteArrayList.addAll(this.mSelectedMediaItems);
            if (!((CopyOnWriteArrayList) this.mSuggestedMediaItems).isEmpty()) {
                copyOnWriteArrayList.add(MediaItem.createGroupDividerMediaItem(this.mContext.getString(R.string.media_output_group_title_suggested_device)));
                copyOnWriteArrayList.addAll(this.mSuggestedMediaItems);
            }
            if (!((CopyOnWriteArrayList) this.mSpeakersAndDisplaysMediaItems).isEmpty()) {
                copyOnWriteArrayList.add(MediaItem.createGroupDividerMediaItem(this.mContext.getString(R.string.media_output_group_title_speakers_and_displays)));
                copyOnWriteArrayList.addAll(this.mSpeakersAndDisplaysMediaItems);
            }
            ((CopyOnWriteArrayList) list).addAll(copyOnWriteArrayList);
        }
        return this.mOutputMediaItemList;
    }

    public final boolean isEmpty() {
        return ((CopyOnWriteArrayList) this.mSelectedMediaItems).isEmpty() && ((CopyOnWriteArrayList) this.mSuggestedMediaItems).isEmpty() && ((CopyOnWriteArrayList) this.mSpeakersAndDisplaysMediaItems).isEmpty();
    }

    public final void removeMutingExpectedDevices() {
        ((CopyOnWriteArrayList) this.mSelectedMediaItems).removeIf(new OutputMediaItemListProxy$$ExternalSyntheticLambda0());
        ((CopyOnWriteArrayList) this.mSuggestedMediaItems).removeIf(new OutputMediaItemListProxy$$ExternalSyntheticLambda0());
        ((CopyOnWriteArrayList) this.mSpeakersAndDisplaysMediaItems).removeIf(new OutputMediaItemListProxy$$ExternalSyntheticLambda0());
        ((CopyOnWriteArrayList) this.mOutputMediaItemList).removeIf(new OutputMediaItemListProxy$$ExternalSyntheticLambda0());
    }

    public final void updateMediaDevices(List list, List list2, MediaDevice mediaDevice, boolean z) {
        Set set = (Set) list2.stream().map(new MediaSwitchingController$$ExternalSyntheticLambda0(0)).collect(Collectors.toSet());
        if (mediaDevice != null) {
            set.add(mediaDevice.getId());
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        HashMap map = new HashMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MediaDevice mediaDevice2 = (MediaDevice) it.next();
            String id = mediaDevice2.getId();
            MediaItem mediaItemCreateDeviceMediaItem = MediaItem.createDeviceMediaItem(mediaDevice2);
            if (z && mediaDevice2.isMutingExpectedDevice()) {
                arrayList.add(0, mediaItemCreateDeviceMediaItem);
            } else if (!z && set.contains(mediaDevice2.getId())) {
                arrayList.add(mediaItemCreateDeviceMediaItem);
            } else if (mediaDevice2.isSuggestedDevice()) {
                arrayList2.add(mediaItemCreateDeviceMediaItem);
            } else {
                arrayList3.add(mediaItemCreateDeviceMediaItem);
            }
            map.put(id, mediaItemCreateDeviceMediaItem);
        }
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        CopyOnWriteArrayList copyOnWriteArrayList2 = new CopyOnWriteArrayList();
        CopyOnWriteArrayList copyOnWriteArrayList3 = new CopyOnWriteArrayList();
        if (isEmpty()) {
            copyOnWriteArrayList.addAll(arrayList);
            copyOnWriteArrayList2.addAll(arrayList2);
            copyOnWriteArrayList3.addAll(arrayList3);
        } else {
            HashSet hashSet = new HashSet();
            updateMediaItems(this.mSelectedMediaItems, copyOnWriteArrayList, map, hashSet);
            updateMediaItems(this.mSuggestedMediaItems, copyOnWriteArrayList2, map, hashSet);
            updateMediaItems(this.mSpeakersAndDisplaysMediaItems, copyOnWriteArrayList3, map, hashSet);
            ArrayList arrayList4 = new ArrayList();
            arrayList4.addAll(getRemainingMediaItems(arrayList, hashSet));
            arrayList4.addAll(getRemainingMediaItems(arrayList2, hashSet));
            arrayList4.addAll(getRemainingMediaItems(arrayList3, hashSet));
            copyOnWriteArrayList3.addAll(arrayList4);
        }
        if (!copyOnWriteArrayList.isEmpty()) {
            Optional optional = ((MediaItem) copyOnWriteArrayList.get(0)).mMediaDeviceOptional;
            if (optional.isPresent()) {
                MediaItem mediaItemCreateDeviceMediaItem2 = MediaItem.createDeviceMediaItem((MediaDevice) optional.get(), true);
                copyOnWriteArrayList.remove(0);
                copyOnWriteArrayList.add(0, mediaItemCreateDeviceMediaItem2);
            }
        }
        ((CopyOnWriteArrayList) this.mSelectedMediaItems).clear();
        ((CopyOnWriteArrayList) this.mSelectedMediaItems).addAll(copyOnWriteArrayList);
        ((CopyOnWriteArrayList) this.mSuggestedMediaItems).clear();
        ((CopyOnWriteArrayList) this.mSuggestedMediaItems).addAll(copyOnWriteArrayList2);
        ((CopyOnWriteArrayList) this.mSpeakersAndDisplaysMediaItems).clear();
        ((CopyOnWriteArrayList) this.mSpeakersAndDisplaysMediaItems).addAll(copyOnWriteArrayList3);
        ((CopyOnWriteArrayList) this.mOutputMediaItemList).clear();
    }
}
