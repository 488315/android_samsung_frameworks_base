package com.android.systemui.screenshot;

import android.util.Log;
import com.android.systemui.screenshot.ScreenshotActionsProvider;
import com.android.systemui.screenshot.ui.viewmodel.ActionButtonAppearance;
import com.android.systemui.screenshot.ui.viewmodel.ActionButtonViewModel;
import com.android.systemui.screenshot.ui.viewmodel.ScreenshotViewModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
public final class ScreenshotActionsController {
    public final ActionExecutor actionExecutor;
    public final Map actionProviders = new LinkedHashMap();
    public final ScreenshotActionsProvider.Factory actionsProviderFactory;
    public UUID currentScreenshotId;
    public final ScreenshotViewModel viewModel;

    public final class ActionsCallback {
        public final UUID screenshotId;

        public ActionsCallback(UUID uuid) {
            this.screenshotId = uuid;
        }

        public final void provideActionButton(ActionButtonAppearance actionButtonAppearance, Function0 function0) {
            UUID uuid = this.screenshotId;
            ScreenshotActionsController screenshotActionsController = ScreenshotActionsController.this;
            if (Intrinsics.areEqual(uuid, screenshotActionsController.currentScreenshotId)) {
                StateFlowImpl stateFlowImpl = screenshotActionsController.viewModel._actions;
                ArrayList arrayList = new ArrayList((Collection) stateFlowImpl.getValue());
                ActionButtonViewModel.Companion.getClass();
                arrayList.add(ActionButtonViewModel.Companion.withNextId(actionButtonAppearance, function0));
                stateFlowImpl.updateState(null, arrayList);
            }
        }
    }

    public interface Factory {
        ScreenshotActionsController getController(ActionExecutor actionExecutor);
    }

    public ScreenshotActionsController(ScreenshotViewModel screenshotViewModel, ScreenshotActionsProvider.Factory factory, ActionExecutor actionExecutor) {
        this.viewModel = screenshotViewModel;
        this.actionsProviderFactory = factory;
        this.actionExecutor = actionExecutor;
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [kotlin.coroutines.jvm.internal.SuspendLambda, kotlin.jvm.functions.Function2] */
    public final void setCompletedScreenshot(UUID uuid, ScreenshotSavedResult screenshotSavedResult) {
        ScreenshotActionsProvider screenshotActionsProvider;
        if (!Intrinsics.areEqual(uuid, this.currentScreenshotId) || (screenshotActionsProvider = (ScreenshotActionsProvider) ((LinkedHashMap) this.actionProviders).get(uuid)) == null) {
            return;
        }
        DefaultScreenshotActionsProvider defaultScreenshotActionsProvider = (DefaultScreenshotActionsProvider) screenshotActionsProvider;
        if (defaultScreenshotActionsProvider.result != null) {
            Log.e("ScreenshotActionsPrvdr", "Got a second completed screenshot for existing request!");
            return;
        }
        defaultScreenshotActionsProvider.result = screenshotSavedResult;
        ?? r3 = defaultScreenshotActionsProvider.pendingAction;
        if (r3 != 0) {
            BuildersKt.launch$default(defaultScreenshotActionsProvider.applicationScope, null, null, new DefaultScreenshotActionsProvider$setCompletedScreenshot$1$1(r3, screenshotSavedResult, null), 3);
        }
    }
}
