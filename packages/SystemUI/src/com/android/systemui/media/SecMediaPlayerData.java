package com.android.systemui.media;

import com.android.systemui.Dumpable;
import com.android.systemui.media.controls.shared.model.MediaData;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class SecMediaPlayerData implements Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy mediaData$delegate = LazyKt__LazyJVMKt.lazy(SecMediaPlayerData$mediaData$2.INSTANCE);
    public final Lazy mediaPlayers$delegate = LazyKt__LazyJVMKt.lazy(SecMediaPlayerData$mediaPlayers$2.INSTANCE);
    public final Lazy sortedMediaPlayers$delegate = LazyKt__LazyJVMKt.lazy(SecMediaPlayerData$sortedMediaPlayers$2.INSTANCE);
    public int currentPosition = -1;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("mediaPlayers: " + getMediaPlayers());
        printWriter.println("mediaData: " + m2627getMediaData());
        printWriter.println("sortedMediaPlayers: " + getSortedMediaPlayers());
    }

    public final MediaData getCurrentMediaData() {
        if (!getSortedMediaPlayers().isEmpty() && this.currentPosition >= 0 && getSortedMediaPlayers().size() > this.currentPosition) {
            Stream stream = getMediaPlayers().entrySet().stream();
            final int i = 0;
            final Function1 function1 = new Function1(this) { // from class: com.android.systemui.media.SecMediaPlayerData$$ExternalSyntheticLambda0
                public final /* synthetic */ SecMediaPlayerData f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    SecMediaPlayerData secMediaPlayerData = this.f$0;
                    Map.Entry entry = (Map.Entry) obj;
                    switch (i) {
                        case 0:
                            int i2 = SecMediaPlayerData.$r8$clinit;
                            entry.getClass();
                            return Boolean.valueOf(secMediaPlayerData.m2627getMediaData().containsKey((String) entry.getKey()) && secMediaPlayerData.getSortedMediaPlayers().get(secMediaPlayerData.currentPosition) == ((SecMediaControlPanel) entry.getValue()));
                        default:
                            int i3 = SecMediaPlayerData.$r8$clinit;
                            entry.getClass();
                            return (MediaData) secMediaPlayerData.m2627getMediaData().get((String) entry.getKey());
                    }
                }
            };
            Stream streamFilter = stream.filter(new Predicate() { // from class: com.android.systemui.media.SecMediaPlayerData$sam$java_util_function_Predicate$0
                @Override // java.util.function.Predicate
                public final /* synthetic */ boolean test(Object obj) {
                    return ((Boolean) function1.mo781invoke(obj)).booleanValue();
                }
            });
            final int i2 = 1;
            final Function1 function12 = new Function1(this) { // from class: com.android.systemui.media.SecMediaPlayerData$$ExternalSyntheticLambda0
                public final /* synthetic */ SecMediaPlayerData f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    SecMediaPlayerData secMediaPlayerData = this.f$0;
                    Map.Entry entry = (Map.Entry) obj;
                    switch (i2) {
                        case 0:
                            int i22 = SecMediaPlayerData.$r8$clinit;
                            entry.getClass();
                            return Boolean.valueOf(secMediaPlayerData.m2627getMediaData().containsKey((String) entry.getKey()) && secMediaPlayerData.getSortedMediaPlayers().get(secMediaPlayerData.currentPosition) == ((SecMediaControlPanel) entry.getValue()));
                        default:
                            int i3 = SecMediaPlayerData.$r8$clinit;
                            entry.getClass();
                            return (MediaData) secMediaPlayerData.m2627getMediaData().get((String) entry.getKey());
                    }
                }
            };
            Optional optionalFindFirst = streamFilter.map(new Function() { // from class: com.android.systemui.media.SecMediaPlayerData$sam$java_util_function_Function$0
                @Override // java.util.function.Function
                public final /* synthetic */ Object apply(Object obj) {
                    return function12.mo781invoke(obj);
                }
            }).findFirst();
            if (!optionalFindFirst.isPresent()) {
                optionalFindFirst = null;
            }
            if (optionalFindFirst != null) {
                return (MediaData) optionalFindFirst.get();
            }
        }
        return null;
    }

    /* renamed from: getMediaData, reason: collision with other method in class */
    public final ConcurrentHashMap m2627getMediaData() {
        return (ConcurrentHashMap) this.mediaData$delegate.getValue();
    }

    public final SecMediaControlPanel getMediaPlayerFromSortedMediaPlayers(int i) {
        return (SecMediaControlPanel) getSortedMediaPlayers().get(i);
    }

    public final int getMediaPlayerSize$1() {
        return getMediaPlayers().size();
    }

    public final HashMap getMediaPlayers() {
        return (HashMap) this.mediaPlayers$delegate.getValue();
    }

    public final ArrayList getSortedMediaPlayers() {
        return (ArrayList) this.sortedMediaPlayers$delegate.getValue();
    }

    public final int getSortedMediaPlayersSize() {
        return getSortedMediaPlayers().size();
    }

    public final Iterable getMediaData() {
        return m2627getMediaData().entrySet();
    }
}
