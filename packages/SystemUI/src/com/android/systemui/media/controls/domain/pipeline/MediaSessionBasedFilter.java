package com.android.systemui.media.controls.domain.pipeline;

import android.content.ComponentName;
import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.util.Log;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.statusbar.phone.NotificationListenerWithPlugins;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class MediaSessionBasedFilter implements MediaDataManager.Listener {
    public final Executor backgroundExecutor;
    public final Executor foregroundExecutor;
    public final MediaSessionManager sessionManager;
    public final Set listeners = new LinkedHashSet();
    public final LinkedHashMap packageControllers = new LinkedHashMap();
    public final Map keyedTokens = new LinkedHashMap();
    public final Set tokensWithNotifications = new LinkedHashSet();
    public final MediaSessionBasedFilter$sessionListener$1 sessionListener = new MediaSessionManager.OnActiveSessionsChangedListener() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter$sessionListener$1
        @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
        public final void onActiveSessionsChanged(List list) {
            MediaSessionBasedFilter.access$handleControllersChanged(this.this$0, list);
        }
    };

    public final class TokenId {
        public final int id;

        public TokenId(int i) {
            this.id = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof TokenId) && this.id == ((TokenId) obj).id;
        }

        public final int hashCode() {
            return Integer.hashCode(this.id);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.id, ")", new StringBuilder("TokenId(id="));
        }

        public TokenId(MediaSession.Token token) {
            this(token.hashCode());
        }
    }

    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter$sessionListener$1] */
    public MediaSessionBasedFilter(final Context context, MediaSessionManager mediaSessionManager, Executor executor, Executor executor2) {
        this.sessionManager = mediaSessionManager;
        this.foregroundExecutor = executor;
        this.backgroundExecutor = executor2;
        executor2.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter.1
            @Override // java.lang.Runnable
            public final void run() {
                ComponentName componentName = new ComponentName(context, (Class<?>) NotificationListenerWithPlugins.class);
                MediaSessionBasedFilter mediaSessionBasedFilter = this;
                mediaSessionBasedFilter.sessionManager.addOnActiveSessionsChangedListener(mediaSessionBasedFilter.sessionListener, componentName);
                MediaSessionBasedFilter mediaSessionBasedFilter2 = this;
                MediaSessionBasedFilter.access$handleControllersChanged(mediaSessionBasedFilter2, mediaSessionBasedFilter2.sessionManager.getActiveSessions(componentName));
            }
        });
    }

    public static final void access$handleControllersChanged(MediaSessionBasedFilter mediaSessionBasedFilter, List list) {
        mediaSessionBasedFilter.packageControllers.clear();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                MediaController mediaController = (MediaController) it.next();
                List list2 = (List) mediaSessionBasedFilter.packageControllers.get(mediaController.getPackageName());
                if (list2 != null) {
                    list2.add(mediaController);
                } else {
                    mediaSessionBasedFilter.packageControllers.put(mediaController.getPackageName(), CollectionsKt__CollectionsKt.mutableListOf(mediaController));
                }
            }
        }
        if (list != null) {
            List list3 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
            Iterator it2 = list3.iterator();
            while (it2.hasNext()) {
                arrayList.add(new TokenId(((MediaController) it2.next()).getSessionToken()));
            }
            mediaSessionBasedFilter.tokensWithNotifications.retainAll(arrayList);
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataLoaded(final String str, final String str2, final MediaData mediaData, final boolean z) {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter.onMediaDataLoaded.1
            @Override // java.lang.Runnable
            public final void run() {
                ArrayList arrayList;
                Set set;
                MediaSession.Token token = mediaData.token;
                if (token != null) {
                    this.tokensWithNotifications.add(new TokenId(token));
                }
                String str3 = str2;
                boolean z2 = (str3 == null || Intrinsics.areEqual(str, str3)) ? false : true;
                if (z2 && (set = (Set) this.keyedTokens.remove(str2)) != null) {
                }
                if (mediaData.token != null) {
                    Set set2 = (Set) ((LinkedHashMap) this.keyedTokens).get(str);
                    if (set2 != null) {
                        set2.add(new TokenId(mediaData.token));
                    }
                }
                List list = (List) this.packageControllers.get(mediaData.packageName);
                MediaController mediaController = null;
                if (list != null) {
                    arrayList = new ArrayList();
                    for (Object obj : list) {
                        MediaController.PlaybackInfo playbackInfo = ((MediaController) obj).getPlaybackInfo();
                        if (playbackInfo != null && playbackInfo.getPlaybackType() == 2) {
                            arrayList.add(obj);
                        }
                    }
                } else {
                    arrayList = null;
                }
                if (arrayList != null && arrayList.size() == 1) {
                    mediaController = (MediaController) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList);
                }
                if (z2 || mediaController == null || Intrinsics.areEqual(mediaController.getSessionToken(), mediaData.token) || !this.tokensWithNotifications.contains(new TokenId(mediaController.getSessionToken()))) {
                    final MediaSessionBasedFilter mediaSessionBasedFilter = this;
                    final String str4 = str;
                    final String str5 = str2;
                    final MediaData mediaData2 = mediaData;
                    final boolean z3 = z;
                    mediaSessionBasedFilter.foregroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter$dispatchMediaDataLoaded$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Set set3 = CollectionsKt___CollectionsKt.toSet(mediaSessionBasedFilter.listeners);
                            String str6 = str4;
                            String str7 = str5;
                            MediaData mediaData3 = mediaData2;
                            boolean z4 = z3;
                            Iterator it = set3.iterator();
                            while (it.hasNext()) {
                                MediaDataManager.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str6, str7, mediaData3, z4, 48);
                            }
                        }
                    });
                    return;
                }
                Log.d("MediaSessionBasedFilter", "filtering key=" + str + " local=" + mediaData.token + " remote=" + mediaController.getSessionToken());
                Object obj2 = ((LinkedHashMap) this.keyedTokens).get(str);
                obj2.getClass();
                if (((Set) obj2).contains(new TokenId(mediaController.getSessionToken()))) {
                    return;
                }
                MediaSessionBasedFilter mediaSessionBasedFilter2 = this;
                mediaSessionBasedFilter2.foregroundExecutor.execute(new MediaSessionBasedFilter$dispatchMediaDataRemoved$1(mediaSessionBasedFilter2, str, false));
            }
        });
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataRemoved(final String str, final boolean z) {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter.onMediaDataRemoved.1
            @Override // java.lang.Runnable
            public final void run() {
                MediaSessionBasedFilter.this.keyedTokens.remove(str);
                MediaSessionBasedFilter mediaSessionBasedFilter = MediaSessionBasedFilter.this;
                mediaSessionBasedFilter.foregroundExecutor.execute(new MediaSessionBasedFilter$dispatchMediaDataRemoved$1(mediaSessionBasedFilter, str, z));
            }
        });
    }
}
