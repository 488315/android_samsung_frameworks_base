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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Can't wrap try/catch for region: R(59:0|1|(2:3|(49:5|6|(1:(1:9)(2:174|175))(4:176|(1:178)(1:183)|179|(1:182)(1:181))|10|(1:12)(2:170|(2:172|173))|13|14|(1:16)(1:169)|17|18|(1:20)(1:168)|21|22|(1:167)(1:28)|29|30|31|32|(1:163)(1:35)|36|(1:38)(1:162)|39|(1:41)(1:161)|(1:43)(1:160)|44|(1:46)(1:159)|47|(1:158)(1:51)|(1:53)(1:157)|54|(4:56|(15:59|(1:97)(1:62)|63|(1:65)(1:96)|(1:67)(1:95)|(1:69)(1:94)|70|(1:93)(1:74)|75|(1:77)(1:88)|78|(1:80)(1:87)|(2:82|83)(2:85|86)|84|57)|98|99)(1:156)|100|(3:103|(3:105|(2:107|108)(2:110|111)|109)(3:112|113|114)|101)|116|117|(1:119)(1:155)|120|(1:154)(1:124)|(1:153)(1:128)|(1:151)|132|133|(1:150)(1:137)|(1:139)(1:149)|(1:141)(1:148)|(1:143)(1:147)|144|145|146))|184|6|(0)(0)|10|(0)(0)|13|14|(0)(0)|17|18|(0)(0)|21|22|(1:24)|167|29|30|31|32|(0)|163|36|(0)(0)|39|(0)(0)|(0)(0)|44|(0)(0)|47|(0)|158|(0)(0)|54|(0)(0)|100|(1:101)|116|117|(0)(0)|120|(1:122)|154|(1:126)|153|(1:130)|151|132|133|(1:135)|150|(0)(0)|(0)(0)|(0)(0)|144|145|146) */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x013a, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x013b, code lost:
    
        android.util.Log.w("MediaControlViewModel", "Cannot find icon for package " + r12, r0);
        r0 = new com.android.systemui.common.shared.model.Icon.Resource(com.android.systemui.R.drawable.ic_music_note, null);
     */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0296  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x035c  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0362  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0369  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x036f  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0365  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x035f  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002d  */
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
