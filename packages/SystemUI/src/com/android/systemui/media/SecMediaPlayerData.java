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
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecMediaPlayerData implements Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy mediaData$delegate = LazyKt__LazyJVMKt.lazy(SecMediaPlayerData$mediaData$2.INSTANCE);
    public final Lazy mediaPlayers$delegate = LazyKt__LazyJVMKt.lazy(SecMediaPlayerData$mediaPlayers$2.INSTANCE);
    public final Lazy sortedMediaPlayers$delegate = LazyKt__LazyJVMKt.lazy(SecMediaPlayerData$sortedMediaPlayers$2.INSTANCE);
    public int currentPosition = -1;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("mediaPlayers: " + getMediaPlayers());
        printWriter.println("mediaData: " + getMediaData());
        printWriter.println("sortedMediaPlayers: " + getSortedMediaPlayers());
    }

    public final MediaData getCurrentMediaData() {
        if (getSortedMediaPlayers().isEmpty() || this.currentPosition < 0 || getSortedMediaPlayers().size() <= this.currentPosition) {
            return null;
        }
        Optional findFirst = getMediaPlayers().entrySet().stream().filter(new Predicate() { // from class: com.android.systemui.media.SecMediaPlayerData$currentMediaData$1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                Map.Entry entry = (Map.Entry) obj;
                Intrinsics.checkNotNull(entry);
                String str = (String) entry.getKey();
                SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) entry.getValue();
                SecMediaPlayerData secMediaPlayerData = SecMediaPlayerData.this;
                int i = SecMediaPlayerData.$r8$clinit;
                return secMediaPlayerData.getMediaData().containsKey(str) && SecMediaPlayerData.this.getSortedMediaPlayers().get(SecMediaPlayerData.this.currentPosition) == secMediaControlPanel;
            }
        }).map(new Function() { // from class: com.android.systemui.media.SecMediaPlayerData$currentMediaData$2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Map.Entry entry = (Map.Entry) obj;
                Intrinsics.checkNotNull(entry);
                String str = (String) entry.getKey();
                SecMediaPlayerData secMediaPlayerData = SecMediaPlayerData.this;
                int i = SecMediaPlayerData.$r8$clinit;
                return (MediaData) secMediaPlayerData.getMediaData().get(str);
            }
        }).findFirst();
        if (!findFirst.isPresent()) {
            findFirst = null;
        }
        if (findFirst != null) {
            return (MediaData) findFirst.get();
        }
        return null;
    }

    public final ConcurrentHashMap getMediaData() {
        return (ConcurrentHashMap) this.mediaData$delegate.getValue();
    }

    public final int getMediaDataSize$1() {
        return getMediaData().size();
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
}
