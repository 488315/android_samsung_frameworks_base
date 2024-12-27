package com.android.systemui.qs.tiles.detail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.android.systemui.R;
import com.android.systemui.media.MediaOutputView;
import com.android.systemui.media.MediaType;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.SecMediaPlayerData;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.mediaoutput.compose.Screen;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.qs.FullScreenDetailAdapter;
import com.android.systemui.qs.tiles.detail.MediaOutputDetailAdapter;
import com.android.systemui.shade.ShadeController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaOutputDetailAdapter extends FullScreenDetailAdapter {
    public static final Companion Companion = new Companion(null);
    public Callback callback;
    public List deviceIds;
    public Integer from;
    public Rect fromRect;
    public final SecMediaHost mediaHost;
    public MediaOutputView mediaOutputView;
    public final Provider mediaOutputViewProvider;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
        void onDismissRequested();

        void showAdapter();
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MediaOutputDetailAdapter(Context context, SecMediaHost secMediaHost, final ShadeController shadeController, Provider provider) {
        this.mediaHost = secMediaHost;
        this.mediaOutputViewProvider = provider;
        context.registerReceiver(new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.detail.MediaOutputDetailAdapter.1
            /* JADX WARN: Code restructure failed: missing block: B:14:0x0040, code lost:
            
                if (r6 == null) goto L15;
             */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onReceive(android.content.Context r6, android.content.Intent r7) {
                /*
                    r5 = this;
                    android.os.Bundle r6 = r7.getExtras()
                    r0 = 0
                    if (r6 == 0) goto L45
                    java.util.Set r1 = r6.keySet()
                    if (r1 == 0) goto L42
                    java.lang.Iterable r1 = (java.lang.Iterable) r1
                    java.util.LinkedHashMap r2 = new java.util.LinkedHashMap
                    r3 = 10
                    int r3 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r1, r3)
                    int r3 = kotlin.collections.MapsKt__MapsJVMKt.mapCapacity(r3)
                    r4 = 16
                    if (r3 >= r4) goto L20
                    r3 = r4
                L20:
                    r2.<init>(r3)
                    java.util.Iterator r1 = r1.iterator()
                L27:
                    boolean r3 = r1.hasNext()
                    if (r3 == 0) goto L3c
                    java.lang.Object r3 = r1.next()
                    r4 = r3
                    java.lang.String r4 = (java.lang.String) r4
                    java.lang.Object r4 = r6.get(r4)
                    r2.put(r3, r4)
                    goto L27
                L3c:
                    java.lang.String r6 = r2.toString()
                    if (r6 != 0) goto L46
                L42:
                    java.lang.String r6 = "empty"
                    goto L46
                L45:
                    r6 = r0
                L46:
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    java.lang.String r2 = "onReceive() - "
                    r1.<init>(r2)
                    r1.append(r7)
                    java.lang.String r2 = ", "
                    r1.append(r2)
                    r1.append(r6)
                    java.lang.String r6 = r1.toString()
                    java.lang.String r1 = "MediaOutputDetailAdapter"
                    android.util.Log.d(r1, r6)
                    com.android.systemui.shade.ShadeController r6 = com.android.systemui.shade.ShadeController.this
                    com.android.systemui.shade.BaseShadeControllerImpl r6 = (com.android.systemui.shade.BaseShadeControllerImpl) r6
                    r6.animateExpandQs()
                    kotlinx.coroutines.scheduling.DefaultScheduler r6 = kotlinx.coroutines.Dispatchers.Default
                    kotlinx.coroutines.MainCoroutineDispatcher r6 = kotlinx.coroutines.internal.MainDispatcherLoader.dispatcher
                    kotlinx.coroutines.internal.ContextScope r6 = kotlinx.coroutines.CoroutineScopeKt.CoroutineScope(r6)
                    com.android.systemui.qs.tiles.detail.MediaOutputDetailAdapter$1$onReceive$1 r1 = new com.android.systemui.qs.tiles.detail.MediaOutputDetailAdapter$1$onReceive$1
                    com.android.systemui.qs.tiles.detail.MediaOutputDetailAdapter r5 = r2
                    r1.<init>(r5, r7, r0)
                    r5 = 3
                    kotlinx.coroutines.BuildersKt.launch$default(r6, r0, r0, r1, r5)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.detail.MediaOutputDetailAdapter.AnonymousClass1.onReceive(android.content.Context, android.content.Intent):void");
            }
        }, new IntentFilter("com.android.systemui.action.OPEN_MEDIA_OUTPUT"), 2);
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
        Feature feature;
        MediaData mediaData;
        String str;
        Object obj;
        Object obj2;
        Log.d("MediaOutputDetailAdapter", "createDetailView()");
        Object obj3 = this.mediaOutputViewProvider.get();
        MediaOutputView mediaOutputView = (MediaOutputView) obj3;
        MediaOutputView mediaOutputView2 = this.mediaOutputView;
        if (mediaOutputView2 != null) {
            feature = mediaOutputView2.feature;
            if (feature == null) {
                feature = null;
            }
            dismissListPopupWindow();
        } else {
            feature = null;
        }
        this.mediaOutputView = mediaOutputView;
        ViewParent parent = mediaOutputView.getParent();
        ViewGroup viewGroup2 = parent instanceof ViewGroup ? (ViewGroup) parent : null;
        if (viewGroup2 != null) {
            viewGroup2.removeView(mediaOutputView);
        }
        if (feature == null) {
            Feature.Builder builder = new Feature.Builder();
            Companion.getClass();
            SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) this.mediaHost.mMediaPlayerData.get(MediaType.QS);
            if (secMediaPlayerData != null) {
                Log.e("MediaOutputDetailAdapter", secMediaPlayerData.currentPosition + ", " + secMediaPlayerData.getCurrentMediaData());
                for (Map.Entry entry : secMediaPlayerData.getMediaData().entrySet()) {
                    Log.e("MediaOutputDetailAdapter", "\t" + ((String) entry.getKey()) + ", " + ((MediaData) entry.getValue()));
                }
                mediaData = secMediaPlayerData.getCurrentMediaData();
                if (mediaData == null) {
                    Set entrySet = secMediaPlayerData.getMediaData().entrySet();
                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(entrySet, 10));
                    Iterator it = entrySet.iterator();
                    while (it.hasNext()) {
                        arrayList.add((MediaData) ((Map.Entry) it.next()).getValue());
                    }
                    Iterator it2 = arrayList.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            obj = null;
                            break;
                        }
                        obj = it2.next();
                        Boolean bool = ((MediaData) obj).isPlaying;
                        if (bool != null ? bool.booleanValue() : false) {
                            break;
                        }
                    }
                    MediaData mediaData2 = (MediaData) obj;
                    if (mediaData2 == null) {
                        Iterator it3 = arrayList.iterator();
                        while (true) {
                            if (!it3.hasNext()) {
                                obj2 = null;
                                break;
                            }
                            obj2 = it3.next();
                            if (((MediaData) obj2).isPlaying != null) {
                                break;
                            }
                        }
                        mediaData2 = (MediaData) obj2;
                        if (mediaData2 == null) {
                            mediaData = (MediaData) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList);
                        }
                    }
                    mediaData = mediaData2;
                }
            } else {
                mediaData = null;
            }
            if (mediaData == null || (str = mediaData.packageName) == null) {
                str = "";
            }
            builder.getFeature().packageName = str;
            Integer num = this.from;
            builder.getFeature().from = num != null ? num.intValue() : 1;
            builder.getFeature().dismissCallback = new Function0() { // from class: com.android.systemui.qs.tiles.detail.MediaOutputDetailAdapter$createDetailView$1$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    MediaOutputDetailAdapter.Callback callback = MediaOutputDetailAdapter.this.callback;
                    if (callback == null) {
                        callback = null;
                    }
                    callback.onDismissRequested();
                    return Unit.INSTANCE;
                }
            };
            builder.getFeature().anchorViewId = this.fromRect != null ? R.id.media_player_container : R.id.media_title;
            List list = this.deviceIds;
            if (list != null) {
                builder.getFeature().defaultScreen = list.size() > 1 ? Screen.Selector.INSTANCE : Screen.TV.INSTANCE;
                builder.getFeature().deviceIds = list;
            } else {
                builder.getFeature().defaultScreen = Screen.Phone.INSTANCE;
            }
            feature = builder.getFeature();
        }
        mediaOutputView.feature = feature;
        this.from = null;
        this.deviceIds = null;
        this.fromRect = null;
        return (View) obj3;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final void dismissListPopupWindow() {
        Log.d("MediaOutputDetailAdapter", "dismissListPopupWindow()");
        MediaOutputView mediaOutputView = this.mediaOutputView;
        for (MediaOutputView mediaOutputView2 : mediaOutputView != null ? Collections.singletonList(mediaOutputView) : EmptyList.INSTANCE) {
            ViewParent parent = mediaOutputView2.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mediaOutputView2);
            }
        }
        this.mediaOutputView = null;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final int getMetricsCategory() {
        return -1355489319;
    }
}
