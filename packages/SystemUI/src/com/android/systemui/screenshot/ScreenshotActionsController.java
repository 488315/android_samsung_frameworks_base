package com.android.systemui.screenshot;

import com.android.systemui.screenshot.ui.viewmodel.ActionButtonAppearance;
import com.android.systemui.screenshot.ui.viewmodel.ActionButtonViewModel;
import com.android.systemui.screenshot.ui.viewmodel.ScreenshotViewModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.UUID;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScreenshotActionsController {
    public final ScreenshotViewModel viewModel;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ActionsCallback {
        public final UUID screenshotId;

        public ActionsCallback(UUID uuid) {
            this.screenshotId = uuid;
        }

        public final void provideActionButton(ActionButtonAppearance actionButtonAppearance, Function0 function0) {
            UUID uuid = this.screenshotId;
            ScreenshotActionsController screenshotActionsController = ScreenshotActionsController.this;
            screenshotActionsController.getClass();
            if (Intrinsics.areEqual(uuid, (Object) null)) {
                StateFlowImpl stateFlowImpl = screenshotActionsController.viewModel._actions;
                ArrayList arrayList = new ArrayList((Collection) stateFlowImpl.getValue());
                ActionButtonViewModel.Companion.getClass();
                arrayList.add(ActionButtonViewModel.Companion.withNextId(actionButtonAppearance, function0));
                stateFlowImpl.updateState(null, arrayList);
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        ScreenshotActionsController getController(ActionExecutor actionExecutor);
    }

    public ScreenshotActionsController(ScreenshotViewModel screenshotViewModel, ScreenshotActionsProvider$Factory screenshotActionsProvider$Factory, ActionExecutor actionExecutor) {
        this.viewModel = screenshotViewModel;
        new LinkedHashMap();
    }
}
