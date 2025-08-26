package com.android.systemui.media.controls.ui.controller;

import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.controls.shared.model.MediaData;
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

/* loaded from: classes2.dex */
public final class MediaPlayerData {
    public static final MediaPlayerData INSTANCE = new MediaPlayerData();
    public static final MediaPlayerData$special$$inlined$thenByDescending$7 comparator = null;
    public static boolean isSwipedAway;
    public static final Map mediaData;
    public static final TreeMap mediaPlayers;
    public static final LinkedHashMap visibleMediaPlayers;

    public final class MediaSortKey {
        public final MediaData data;
        public final String key;
        public final long updateTime;

        public MediaSortKey(MediaData mediaData, String str, long j) {
            this.data = mediaData;
            this.key = str;
            this.updateTime = j;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MediaSortKey)) {
                return false;
            }
            MediaSortKey mediaSortKey = (MediaSortKey) obj;
            return Intrinsics.areEqual(this.data, mediaSortKey.data) && Intrinsics.areEqual(this.key, mediaSortKey.key) && this.updateTime == mediaSortKey.updateTime;
        }

        public final int hashCode() {
            return Long.hashCode(this.updateTime) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.data.hashCode() * 31, 31, this.key);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("MediaSortKey(data=");
            sb.append(this.data);
            sb.append(", key=");
            sb.append(this.key);
            sb.append(", updateTime=");
            return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.updateTime, ")", sb);
        }

        public /* synthetic */ MediaSortKey(MediaData mediaData, String str, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(mediaData, str, (i & 4) != 0 ? 0L : j);
        }
    }

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
                Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual(bool, bool2) && mediaSortKey.data.playbackLocation == 0);
                MediaPlayerData.MediaSortKey mediaSortKey2 = (MediaPlayerData.MediaSortKey) obj;
                if (Intrinsics.areEqual(mediaSortKey2.data.isPlaying, bool2) && mediaSortKey2.data.playbackLocation == 0) {
                    z = true;
                }
                return ComparisonsKt__ComparisonsKt.compareValues(boolValueOf, Boolean.valueOf(z));
            }
        };
        final Comparator comparator3 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator2.compare(obj, obj2);
                if (iCompare != 0) {
                    return iCompare;
                }
                MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) obj2;
                Boolean bool = mediaSortKey.data.isPlaying;
                Boolean bool2 = Boolean.TRUE;
                boolean z = false;
                Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual(bool, bool2) && mediaSortKey.data.playbackLocation == 1);
                MediaPlayerData.MediaSortKey mediaSortKey2 = (MediaPlayerData.MediaSortKey) obj;
                if (Intrinsics.areEqual(mediaSortKey2.data.isPlaying, bool2) && mediaSortKey2.data.playbackLocation == 1) {
                    z = true;
                }
                return ComparisonsKt__ComparisonsKt.compareValues(boolValueOf, Boolean.valueOf(z));
            }
        };
        final Comparator comparator4 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator3.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(((MediaPlayerData.MediaSortKey) obj2).data.active), Boolean.valueOf(((MediaPlayerData.MediaSortKey) obj).data.active));
            }
        };
        final Comparator comparator5 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator4.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(!((MediaPlayerData.MediaSortKey) obj2).data.resumption), Boolean.valueOf(!((MediaPlayerData.MediaSortKey) obj).data.resumption));
            }
        };
        final Comparator comparator6 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$4
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator5.compare(obj, obj2);
                if (iCompare != 0) {
                    return iCompare;
                }
                return ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(((MediaPlayerData.MediaSortKey) obj2).data.playbackLocation != 2), Boolean.valueOf(((MediaPlayerData.MediaSortKey) obj).data.playbackLocation != 2));
            }
        };
        final Comparator comparator7 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$5
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator6.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((MediaPlayerData.MediaSortKey) obj2).data.lastActive), Long.valueOf(((MediaPlayerData.MediaSortKey) obj).data.lastActive));
            }
        };
        final Comparator comparator8 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$6
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator7.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((MediaPlayerData.MediaSortKey) obj2).updateTime), Long.valueOf(((MediaPlayerData.MediaSortKey) obj).updateTime));
            }
        };
        mediaPlayers = new TreeMap(new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$7
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator8.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt__ComparisonsKt.compareValues(((MediaPlayerData.MediaSortKey) obj2).data.notificationKey, ((MediaPlayerData.MediaSortKey) obj).data.notificationKey);
            }
        });
        mediaData = new LinkedHashMap();
        visibleMediaPlayers = new LinkedHashMap();
    }

    private MediaPlayerData() {
    }

    public static MediaData getFirstActiveMediaData() {
        for (Map.Entry entry : mediaPlayers.entrySet()) {
            if (((MediaSortKey) entry.getKey()).data.active) {
                return ((MediaSortKey) entry.getKey()).data;
            }
        }
        return null;
    }

    public final void addMediaPlayer(String str, MediaData mediaData2, MediaControlPanel mediaControlPanel, SystemClock systemClock, MediaCarouselControllerLogger mediaCarouselControllerLogger) {
        Map map = mediaData;
        MediaSortKey mediaSortKey = (MediaSortKey) map.remove(str);
        MediaControlPanel mediaControlPanel2 = mediaSortKey != null ? (MediaControlPanel) mediaPlayers.remove(mediaSortKey) : null;
        if (mediaControlPanel2 != null && !mediaControlPanel2.equals(mediaControlPanel)) {
            if (mediaCarouselControllerLogger != null) {
                LogLevel logLevel = LogLevel.DEBUG;
                MediaCarouselControllerLogger$$ExternalSyntheticLambda0 mediaCarouselControllerLogger$$ExternalSyntheticLambda0 = new MediaCarouselControllerLogger$$ExternalSyntheticLambda0(3);
                LogBuffer logBuffer = mediaCarouselControllerLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("MediaCarouselCtlrLog", logLevel, mediaCarouselControllerLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).str1 = str;
                logBuffer.commit(logMessageObtain);
            }
            mediaControlPanel2.onDestroy();
        }
        MediaSortKey mediaSortKey2 = new MediaSortKey(mediaData2, str, systemClock.currentTimeMillis());
        map.put(str, mediaSortKey2);
        mediaPlayers.put(mediaSortKey2, mediaControlPanel);
        visibleMediaPlayers.put(str, mediaSortKey2);
    }

    public final void clear() {
        ((LinkedHashMap) mediaData).clear();
        mediaPlayers.clear();
        visibleMediaPlayers.clear();
    }
}
