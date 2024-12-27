package com.android.systemui.media.controls.ui.controller;

import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import com.android.systemui.media.controls.ui.controller.MediaPlayerData;
import com.android.systemui.util.time.SystemClock;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaPlayerData {
    public static final MediaData EMPTY = null;
    public static final MediaPlayerData INSTANCE = new MediaPlayerData();
    public static final MediaPlayerData$special$$inlined$thenByDescending$8 comparator = null;
    public static boolean isSwipedAway;
    public static final Map mediaData;
    public static final TreeMap mediaPlayers;
    public static boolean shouldPrioritizeSs;
    public static SmartspaceMediaData smartspaceMediaData;
    public static final LinkedHashMap visibleMediaPlayers;

    static {
        EmptyList emptyList = EmptyList.INSTANCE;
        new MediaData(-1, false, null, null, null, null, null, emptyList, emptyList, null, "INVALID", null, null, null, true, null, 0, false, null, false, null, false, 0L, 0L, InstanceId.fakeInstanceId(-1), -1, false, null, 218038784, null);
        final Comparator comparator2 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$compareByDescending$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) obj2;
                Boolean bool = mediaSortKey.data.isPlaying;
                Boolean bool2 = Boolean.TRUE;
                boolean z = false;
                Boolean valueOf = Boolean.valueOf(Intrinsics.areEqual(bool, bool2) && mediaSortKey.data.playbackLocation == 0);
                MediaPlayerData.MediaSortKey mediaSortKey2 = (MediaPlayerData.MediaSortKey) obj;
                if (Intrinsics.areEqual(mediaSortKey2.data.isPlaying, bool2) && mediaSortKey2.data.playbackLocation == 0) {
                    z = true;
                }
                return ComparisonsKt__ComparisonsKt.compareValues(valueOf, Boolean.valueOf(z));
            }
        };
        final Comparator comparator3 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare = comparator2.compare(obj, obj2);
                if (compare != 0) {
                    return compare;
                }
                MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) obj2;
                Boolean bool = mediaSortKey.data.isPlaying;
                Boolean bool2 = Boolean.TRUE;
                boolean z = false;
                Boolean valueOf = Boolean.valueOf(Intrinsics.areEqual(bool, bool2) && mediaSortKey.data.playbackLocation == 1);
                MediaPlayerData.MediaSortKey mediaSortKey2 = (MediaPlayerData.MediaSortKey) obj;
                if (Intrinsics.areEqual(mediaSortKey2.data.isPlaying, bool2) && mediaSortKey2.data.playbackLocation == 1) {
                    z = true;
                }
                return ComparisonsKt__ComparisonsKt.compareValues(valueOf, Boolean.valueOf(z));
            }
        };
        final Comparator comparator4 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare = comparator3.compare(obj, obj2);
                return compare != 0 ? compare : ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(((MediaPlayerData.MediaSortKey) obj2).data.active), Boolean.valueOf(((MediaPlayerData.MediaSortKey) obj).data.active));
            }
        };
        final Comparator comparator5 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare = comparator4.compare(obj, obj2);
                if (compare != 0) {
                    return compare;
                }
                MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
                mediaPlayerData.getClass();
                Boolean valueOf = Boolean.valueOf(MediaPlayerData.shouldPrioritizeSs == ((MediaPlayerData.MediaSortKey) obj2).isSsMediaRec);
                mediaPlayerData.getClass();
                return ComparisonsKt__ComparisonsKt.compareValues(valueOf, Boolean.valueOf(MediaPlayerData.shouldPrioritizeSs == ((MediaPlayerData.MediaSortKey) obj).isSsMediaRec));
            }
        };
        final Comparator comparator6 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$4
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare = comparator5.compare(obj, obj2);
                return compare != 0 ? compare : ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(!((MediaPlayerData.MediaSortKey) obj2).data.resumption), Boolean.valueOf(!((MediaPlayerData.MediaSortKey) obj).data.resumption));
            }
        };
        final Comparator comparator7 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$5
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare = comparator6.compare(obj, obj2);
                if (compare != 0) {
                    return compare;
                }
                return ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(((MediaPlayerData.MediaSortKey) obj2).data.playbackLocation != 2), Boolean.valueOf(((MediaPlayerData.MediaSortKey) obj).data.playbackLocation != 2));
            }
        };
        final Comparator comparator8 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$6
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare = comparator7.compare(obj, obj2);
                return compare != 0 ? compare : ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((MediaPlayerData.MediaSortKey) obj2).data.lastActive), Long.valueOf(((MediaPlayerData.MediaSortKey) obj).data.lastActive));
            }
        };
        final Comparator comparator9 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$7
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare = comparator8.compare(obj, obj2);
                return compare != 0 ? compare : ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((MediaPlayerData.MediaSortKey) obj2).updateTime), Long.valueOf(((MediaPlayerData.MediaSortKey) obj).updateTime));
            }
        };
        mediaPlayers = new TreeMap(new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$8
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare = comparator9.compare(obj, obj2);
                return compare != 0 ? compare : ComparisonsKt__ComparisonsKt.compareValues(((MediaPlayerData.MediaSortKey) obj2).data.notificationKey, ((MediaPlayerData.MediaSortKey) obj).data.notificationKey);
            }
        });
        mediaData = new LinkedHashMap();
        visibleMediaPlayers = new LinkedHashMap();
    }

    private MediaPlayerData() {
    }

    public static MediaControlPanel removeMediaPlayer(String str, boolean z) {
        MediaSortKey mediaSortKey = (MediaSortKey) mediaData.remove(str);
        if (mediaSortKey == null) {
            return null;
        }
        if (mediaSortKey.isSsMediaRec) {
            smartspaceMediaData = null;
        }
        if (z) {
            visibleMediaPlayers.remove(str);
        }
        return (MediaControlPanel) mediaPlayers.remove(mediaSortKey);
    }

    public static String smartspaceMediaKey() {
        for (Map.Entry entry : ((LinkedHashMap) mediaData).entrySet()) {
            if (((MediaSortKey) entry.getValue()).isSsMediaRec) {
                return (String) entry.getKey();
            }
        }
        return null;
    }

    public final void addMediaPlayer(String str, MediaData mediaData2, MediaControlPanel mediaControlPanel, SystemClock systemClock, boolean z, MediaCarouselControllerLogger mediaCarouselControllerLogger) {
        MediaControlPanel removeMediaPlayer = removeMediaPlayer(str, false);
        if (removeMediaPlayer != null && !removeMediaPlayer.equals(mediaControlPanel)) {
            if (mediaCarouselControllerLogger != null) {
                mediaCarouselControllerLogger.logPotentialMemoryLeak(str);
            }
            removeMediaPlayer.onDestroy();
        }
        MediaSortKey mediaSortKey = new MediaSortKey(false, mediaData2, str, systemClock.currentTimeMillis(), z);
        mediaData.put(str, mediaSortKey);
        mediaPlayers.put(mediaSortKey, mediaControlPanel);
        visibleMediaPlayers.put(str, mediaSortKey);
    }

    public final void clear() {
        ((LinkedHashMap) mediaData).clear();
        mediaPlayers.clear();
        visibleMediaPlayers.clear();
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class MediaSortKey {
        public final MediaData data;
        public final boolean isSsMediaRec;
        public final boolean isSsReactivated;
        public final String key;
        public final long updateTime;

        public MediaSortKey(boolean z, MediaData mediaData, String str, long j, boolean z2) {
            this.isSsMediaRec = z;
            this.data = mediaData;
            this.key = str;
            this.updateTime = j;
            this.isSsReactivated = z2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MediaSortKey)) {
                return false;
            }
            MediaSortKey mediaSortKey = (MediaSortKey) obj;
            return this.isSsMediaRec == mediaSortKey.isSsMediaRec && Intrinsics.areEqual(this.data, mediaSortKey.data) && Intrinsics.areEqual(this.key, mediaSortKey.key) && this.updateTime == mediaSortKey.updateTime && this.isSsReactivated == mediaSortKey.isSsReactivated;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isSsReactivated) + Scale$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((this.data.hashCode() + (Boolean.hashCode(this.isSsMediaRec) * 31)) * 31, 31, this.key), 31, this.updateTime);
        }

        public final String toString() {
            return "MediaSortKey(isSsMediaRec=" + this.isSsMediaRec + ", data=" + this.data + ", key=" + this.key + ", updateTime=" + this.updateTime + ", isSsReactivated=" + this.isSsReactivated + ")";
        }

        public /* synthetic */ MediaSortKey(boolean z, MediaData mediaData, String str, long j, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(z, mediaData, str, (i & 8) != 0 ? 0L : j, (i & 16) != 0 ? false : z2);
        }
    }
}
