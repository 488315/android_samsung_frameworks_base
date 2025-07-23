package com.android.systemui.qs.tiles.detail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
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
import com.android.systemui.media.mediaoutput.activity.MediaOutputWindow;
import com.android.systemui.media.mediaoutput.compose.Screen;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.ext.BundleExtKt;
import com.android.systemui.qs.FullScreenDetailAdapter;
import com.android.systemui.qs.tiles.detail.MediaOutputDetailAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaOutputDetailAdapter extends FullScreenDetailAdapter {
    public static final Companion Companion = new Companion(null);
    public Callback callback;
    public Rect fromRect;
    public final SecMediaHost mediaHost;
    public MediaOutputView mediaOutputView;
    public final Provider mediaOutputViewProvider;
    public final Provider mediaOutputWindowProvider;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        void onDismissRequested();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public MediaOutputDetailAdapter(Context context, SecMediaHost secMediaHost, Provider provider, Provider provider2) {
        this.mediaHost = secMediaHost;
        this.mediaOutputViewProvider = provider;
        this.mediaOutputWindowProvider = provider2;
        context.registerReceiver(new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.detail.MediaOutputDetailAdapter.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                Bundle extras = intent.getExtras();
                Log.d("MediaOutputDetailAdapter", "onReceive() - " + intent + ", " + (extras != null ? BundleExtKt.getSerialize(extras) : null));
                ((MediaOutputWindow) MediaOutputDetailAdapter.this.mediaOutputWindowProvider.get()).show(intent);
            }
        }, new IntentFilter("com.android.systemui.action.OPEN_MEDIA_OUTPUT"), 2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v7, types: [java.lang.Object] */
    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
        Feature feature;
        MediaData mediaData;
        String str;
        MediaData mediaData2;
        MediaData mediaData3;
        Log.d("MediaOutputDetailAdapter", "createDetailView()");
        Object obj = this.mediaOutputViewProvider.get();
        MediaOutputView mediaOutputView = (MediaOutputView) obj;
        MediaOutputView mediaOutputView2 = this.mediaOutputView;
        if (mediaOutputView2 != null) {
            feature = mediaOutputView2.feature;
            if (feature == null) {
                feature = null;
            }
            if (feature != null) {
                feature.isRotated = true;
            } else {
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
                for (Map.Entry entry : secMediaPlayerData.getMediaData()) {
                    Log.e("MediaOutputDetailAdapter", "\t" + ((String) entry.getKey()) + ", " + ((MediaData) entry.getValue()));
                }
                mediaData = secMediaPlayerData.getCurrentMediaData();
                if (mediaData == null) {
                    Iterable mediaData4 = secMediaPlayerData.getMediaData();
                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(mediaData4, 10));
                    Iterator it = mediaData4.iterator();
                    while (it.hasNext()) {
                        arrayList.add((MediaData) ((Map.Entry) it.next()).getValue());
                    }
                    int size = arrayList.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            mediaData2 = 0;
                            break;
                        }
                        mediaData2 = arrayList.get(i);
                        i++;
                        Boolean bool = ((MediaData) mediaData2).isPlaying;
                        if (bool != null ? bool.booleanValue() : false) {
                            break;
                        }
                    }
                    mediaData = mediaData2;
                    if (mediaData == null) {
                        int size2 = arrayList.size();
                        int i2 = 0;
                        while (true) {
                            if (i2 >= size2) {
                                mediaData3 = 0;
                                break;
                            }
                            mediaData3 = arrayList.get(i2);
                            i2++;
                            if (((MediaData) mediaData3).isPlaying != null) {
                                break;
                            }
                        }
                        mediaData = mediaData3;
                        if (mediaData == null) {
                            mediaData = (MediaData) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList);
                        }
                    }
                }
            } else {
                mediaData = null;
            }
            if (mediaData == null || (str = mediaData.packageName) == null) {
                str = "";
            }
            builder.getFeature().packageName = str;
            builder.getFeature().from = 1;
            builder.getFeature().dismissCallback = new Function0() { // from class: com.android.systemui.qs.tiles.detail.MediaOutputDetailAdapter$$ExternalSyntheticLambda0
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
            builder.getFeature().defaultScreen = Screen.Phone.INSTANCE;
            feature = builder.getFeature();
        }
        mediaOutputView.feature = feature;
        this.fromRect = null;
        return (View) obj;
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
