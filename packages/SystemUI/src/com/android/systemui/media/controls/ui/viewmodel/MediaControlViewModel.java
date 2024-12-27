package com.android.systemui.media.controls.ui.viewmodel;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

public final class MediaControlViewModel {
    public static final Companion Companion = null;
    public static final List SEMANTIC_ACTIONS_ALL;
    public static final List SEMANTIC_ACTIONS_COMPACT;
    public static final List SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING;
    public final Context applicationContext;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Executor backgroundExecutor;
    public final MediaControlInteractor interactor;
    public boolean isAnyButtonClicked;
    public boolean isPlaying;
    public final MediaUiEventLogger logger;
    public final Flow player;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        Integer valueOf = Integer.valueOf(R.id.actionPlayPause);
        Integer valueOf2 = Integer.valueOf(R.id.actionPrev);
        Integer valueOf3 = Integer.valueOf(R.id.actionNext);
        SEMANTIC_ACTIONS_COMPACT = CollectionsKt__CollectionsKt.listOf(valueOf, valueOf2, valueOf3);
        SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING = CollectionsKt__CollectionsKt.listOf(valueOf2, valueOf3);
        SEMANTIC_ACTIONS_ALL = CollectionsKt__CollectionsKt.listOf(valueOf, valueOf2, valueOf3, Integer.valueOf(R.id.action0), Integer.valueOf(R.id.action1));
    }

    public MediaControlViewModel(Context context, CoroutineDispatcher coroutineDispatcher, Executor executor, MediaControlInteractor mediaControlInteractor, MediaUiEventLogger mediaUiEventLogger) {
        this.applicationContext = context;
        this.backgroundDispatcher = coroutineDispatcher;
        this.backgroundExecutor = executor;
        this.interactor = mediaControlInteractor;
        this.logger = mediaUiEventLogger;
        FlowKt.flowOn(FlowKt.distinctUntilChanged(FlowKt.transformLatest(mediaControlInteractor.onAnyMediaConfigurationChange, new MediaControlViewModel$special$$inlined$flatMapLatest$1(null, this))), coroutineDispatcher);
    }

    /* JADX WARN: Code restructure failed: missing block: B:165:0x013a, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x013b, code lost:
    
        android.util.Log.w("MediaControlViewModel", "Cannot find icon for package " + r12, r0);
        r0 = new com.android.systemui.common.shared.model.Icon.Resource(com.android.systemui.R.drawable.ic_music_note, null);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$toViewModel(com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel r46, com.android.systemui.media.controls.shared.model.MediaControlModel r47, kotlin.coroutines.Continuation r48) {
        /*
            Method dump skipped, instructions count: 963
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel.access$toViewModel(com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel, com.android.systemui.media.controls.shared.model.MediaControlModel, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
