package com.android.systemui.media;

import com.android.systemui.Dumpable;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SecMediaPlayerData implements Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SecMediaControlPanel firstPageView;
    public SecMediaControlPanel lastPageView;
    public final Lazy mediaData$delegate = LazyKt__LazyJVMKt.lazy(SecMediaPlayerData$mediaData$2.INSTANCE);
    public final Lazy mediaPlayers$delegate = LazyKt__LazyJVMKt.lazy(SecMediaPlayerData$mediaPlayers$2.INSTANCE);
    public final Lazy sortedMediaPlayers$delegate = LazyKt__LazyJVMKt.lazy(SecMediaPlayerData$sortedMediaPlayers$2.INSTANCE);
    public int currentPosition = -1;

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

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("mediaPlayers: " + getMediaPlayers());
        printWriter.println("mediaData: " + getMediaData());
        printWriter.println("sortedMediaPlayers: " + getSortedMediaPlayers());
    }

    public final ConcurrentHashMap getMediaData() {
        return (ConcurrentHashMap) this.mediaData$delegate.getValue();
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
}
