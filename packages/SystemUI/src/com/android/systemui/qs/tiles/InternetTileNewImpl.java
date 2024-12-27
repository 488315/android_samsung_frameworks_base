package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Switch;
import androidx.lifecycle.LifecycleRegistry;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tiles.dialog.InternetDialogManager;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.InternetTileBinder;
import com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.InternetTileViewModel;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class InternetTileNewImpl extends QSTileImpl {
    public static final Intent WIFI_SETTINGS;
    public final AccessPointController accessPointController;
    public final InternetDialogManager internetDialogManager;
    public final Handler mainHandler;
    public InternetTileModel model;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        WIFI_SETTINGS = new Intent("android.settings.WIFI_SETTINGS");
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.qs.tiles.InternetTileNewImpl$1] */
    public InternetTileNewImpl(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, InternetTileViewModel internetTileViewModel, InternetDialogManager internetDialogManager, AccessPointController accessPointController) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mainHandler = handler;
        this.internetDialogManager = internetDialogManager;
        this.accessPointController = accessPointController;
        ReadonlyStateFlow readonlyStateFlow = internetTileViewModel.tileModel;
        this.model = (InternetTileModel) readonlyStateFlow.$$delegate_0.getValue();
        InternetTileBinder internetTileBinder = InternetTileBinder.INSTANCE;
        LifecycleRegistry lifecycleRegistry = this.mLifecycle;
        ?? r4 = new Consumer() { // from class: com.android.systemui.qs.tiles.InternetTileNewImpl.1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InternetTileNewImpl internetTileNewImpl = InternetTileNewImpl.this;
                internetTileNewImpl.model = (InternetTileModel) obj;
                internetTileNewImpl.refreshState(null);
            }
        };
        internetTileBinder.getClass();
        InternetTileBinder.bind(lifecycleRegistry, readonlyStateFlow, r4);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return WIFI_SETTINGS;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_internet_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final Expandable expandable) {
        this.mainHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.InternetTileNewImpl$handleClick$1
            @Override // java.lang.Runnable
            public final void run() {
                InternetTileNewImpl internetTileNewImpl = InternetTileNewImpl.this;
                internetTileNewImpl.internetDialogManager.create(((AccessPointControllerImpl) internetTileNewImpl.accessPointController).canConfigMobileData(), ((AccessPointControllerImpl) InternetTileNewImpl.this.accessPointController).canConfigWifi(), expandable);
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        booleanState.label = this.mContext.getResources().getString(R.string.quick_settings_internet_label);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        InternetTileModel internetTileModel = this.model;
        Context context = this.mContext;
        if (internetTileModel.getSecondaryLabel() != null) {
            Text.Companion companion = Text.Companion;
            Text secondaryLabel = internetTileModel.getSecondaryLabel();
            companion.getClass();
            booleanState.secondaryLabel = Text.Companion.loadText(secondaryLabel, context);
        } else {
            booleanState.secondaryLabel = internetTileModel.getSecondaryTitle();
        }
        ContentDescription.Companion companion2 = ContentDescription.Companion;
        ContentDescription stateDescription = internetTileModel.getStateDescription();
        companion2.getClass();
        booleanState.stateDescription = ContentDescription.Companion.loadContentDescription(stateDescription, context);
        booleanState.contentDescription = ContentDescription.Companion.loadContentDescription(internetTileModel.getContentDescription(), context);
        if (internetTileModel.getIcon() != null) {
            booleanState.icon = internetTileModel.getIcon();
        } else if (internetTileModel.getIconId() != null) {
            Integer iconId = internetTileModel.getIconId();
            Intrinsics.checkNotNull(iconId);
            booleanState.icon = QSTileImpl.ResourceIcon.get(iconId.intValue());
        }
        booleanState.state = internetTileModel instanceof InternetTileModel.Active ? 2 : 1;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.forceExpandIcon = true;
        return booleanState;
    }
}
