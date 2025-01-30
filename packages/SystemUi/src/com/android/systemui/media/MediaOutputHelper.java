package com.android.systemui.media;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.session.MediaSession;
import android.os.UserHandle;
import android.util.Log;
import android.widget.TextView;
import com.android.systemui.media.controls.models.player.MediaData;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaOutputHelper {
    public final SecMediaHost mediaHost;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public MediaOutputHelper(SecMediaHost secMediaHost) {
        this.mediaHost = secMediaHost;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void show(Context context, boolean z, MediaType mediaType, String str, TextView textView, MediaSession.Token token) {
        ArrayList<String> arrayList;
        MediaData mediaData;
        Intent intent = new Intent("com.samsung.android.mdx.quickboard.ACTION_OPEN_MEDIA_PANEL");
        intent.setPackage("com.samsung.android.mdx.quickboard");
        SecMediaHost secMediaHost = this.mediaHost;
        if (str == null) {
            final SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) secMediaHost.mMediaPlayerData.get(mediaType);
            if (secMediaPlayerData != null) {
                if (!secMediaPlayerData.getSortedMediaPlayers().isEmpty() && secMediaPlayerData.currentPosition >= 0 && secMediaPlayerData.getSortedMediaPlayers().size() > secMediaPlayerData.currentPosition) {
                    Optional findFirst = secMediaPlayerData.getMediaPlayers().entrySet().stream().filter(new Predicate() { // from class: com.android.systemui.media.SecMediaPlayerData$currentMediaData$1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            Map.Entry entry = (Map.Entry) obj;
                            String str2 = (String) entry.getKey();
                            SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) entry.getValue();
                            SecMediaPlayerData secMediaPlayerData2 = SecMediaPlayerData.this;
                            int i = SecMediaPlayerData.$r8$clinit;
                            return secMediaPlayerData2.getMediaData().containsKey(str2) && SecMediaPlayerData.this.getSortedMediaPlayers().get(SecMediaPlayerData.this.currentPosition) == secMediaControlPanel;
                        }
                    }).map(new Function() { // from class: com.android.systemui.media.SecMediaPlayerData$currentMediaData$2
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            String str2 = (String) ((Map.Entry) obj).getKey();
                            SecMediaPlayerData secMediaPlayerData2 = SecMediaPlayerData.this;
                            int i = SecMediaPlayerData.$r8$clinit;
                            return (MediaData) secMediaPlayerData2.getMediaData().get(str2);
                        }
                    }).findFirst();
                    if (!findFirst.isPresent()) {
                        findFirst = null;
                    }
                    if (findFirst != null) {
                        mediaData = (MediaData) findFirst.get();
                        if (mediaData != null) {
                            str = mediaData.packageName;
                            if (str == null) {
                                str = "";
                            }
                        }
                    }
                }
                mediaData = null;
                if (mediaData != null) {
                }
            }
            str = null;
            if (str == null) {
            }
        }
        Log.d("MediaOutputHelper", "getCpPackage: ".concat(str));
        intent.putExtra("extra_cp_package", str);
        final SecMediaPlayerData secMediaPlayerData2 = (SecMediaPlayerData) secMediaHost.mMediaPlayerData.get(mediaType);
        if (secMediaPlayerData2 != null) {
            ArrayList arrayList2 = new ArrayList();
            FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(secMediaPlayerData2.getSortedMediaPlayers()), new Function1() { // from class: com.android.systemui.media.SecMediaPlayerData$mediaDataOrderByMediaList$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    final SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) obj;
                    SecMediaPlayerData secMediaPlayerData3 = SecMediaPlayerData.this;
                    int i = SecMediaPlayerData.$r8$clinit;
                    return secMediaPlayerData3.getMediaPlayers().entrySet().stream().filter(new Predicate() { // from class: com.android.systemui.media.SecMediaPlayerData$mediaDataOrderByMediaList$1.1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj2) {
                            return SecMediaControlPanel.this == ((SecMediaControlPanel) ((Map.Entry) obj2).getValue());
                        }
                    }).map(new Function() { // from class: com.android.systemui.media.SecMediaPlayerData$mediaDataOrderByMediaList$1.2
                        @Override // java.util.function.Function
                        public final Object apply(Object obj2) {
                            return (String) ((Map.Entry) obj2).getKey();
                        }
                    }).findFirst();
                }
            }), new Function1() { // from class: com.android.systemui.media.SecMediaPlayerData$mediaDataOrderByMediaList$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return Boolean.valueOf(((Optional) obj).isPresent());
                }
            }));
            while (filteringSequence$iterator$1.hasNext()) {
                arrayList2.add((String) ((Optional) filteringSequence$iterator$1.next()).get());
            }
            ArrayList arrayList3 = new ArrayList();
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                MediaData mediaData2 = (MediaData) secMediaPlayerData2.getMediaData().get((String) it.next());
                if (mediaData2 != null) {
                    arrayList3.add(mediaData2);
                }
            }
            ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
            Iterator it2 = arrayList3.iterator();
            while (it2.hasNext()) {
                arrayList4.add(((MediaData) it2.next()).packageName);
            }
            arrayList = new ArrayList<>(arrayList4);
        } else {
            arrayList = new ArrayList<>();
        }
        Log.d("MediaOutputHelper", "getCpList: " + arrayList);
        intent.putStringArrayListExtra("extra_cp_list", arrayList);
        Rect rect = new Rect();
        textView.getGlobalVisibleRect(rect);
        Log.d("MediaOutputHelper", "getButtonRect: " + rect);
        intent.putExtra("extra_button_rect", rect);
        int i = !z ? 0 : mediaType == MediaType.COVER ? 20 : 1;
        Log.d("MediaOutputHelper", "getFrom: " + i);
        intent.putExtra("extra_from", i);
        if (token != null) {
            intent.putExtra("extra_token", token);
        }
        intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        context.startForegroundServiceAsUser(intent, UserHandle.CURRENT);
    }
}
