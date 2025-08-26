package com.android.systemui.qs.pipeline.data.domain.interactor;

import android.content.Context;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.indexsearch.SystemUIIndexMediator;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.user.data.model.SelectedUserModel;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class TileSearchInteractorImpl implements TileSearchInteractor {
    public final CoroutineDispatcher backgroundDispatcher;
    public final ConfigurationInteractor configurationInteractor;
    public final Context context;
    public final CurrentTilesInteractor currentTileInteractor;
    public int currentUiMode;
    public int currentUser;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 refreshSearchableTiles;
    public final SystemUIIndexMediator systemUIIndexMediator;
    public final UserRepository userRepository;
    public final UserTracker userTracker;

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

    public TileSearchInteractorImpl(CurrentTilesInteractor currentTilesInteractor, Context context, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, SystemUIIndexMediator systemUIIndexMediator, ConfigurationInteractor configurationInteractor, UserTracker userTracker, UserRepository userRepository) {
        this.currentTileInteractor = currentTilesInteractor;
        this.context = context;
        this.backgroundDispatcher = coroutineDispatcher;
        this.systemUIIndexMediator = systemUIIndexMediator;
        this.configurationInteractor = configurationInteractor;
        this.userTracker = userTracker;
        this.userRepository = userRepository;
        this.currentUser = ((SelectedUserModel) ((UserRepositoryImpl) userRepository).selectedUser.$$delegate_0.getValue()).userInfo.id;
        this.currentUiMode = context.getResources().getConfiguration().uiMode;
        this.refreshSearchableTiles = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(currentTilesInteractor.getCurrentTiles(), currentTilesInteractor.getCurrentBarTileList(), new TileSearchInteractorImpl$refreshSearchableTiles$1(null));
        BuildersKt.launch$default(coroutineScope, null, null, new TileSearchInteractorImpl$startTileCollection$1(this, null), 3);
    }
}
