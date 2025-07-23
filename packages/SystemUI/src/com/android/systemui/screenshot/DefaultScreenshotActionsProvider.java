package com.android.systemui.screenshot;

import android.content.Context;
import android.net.Uri;
import androidx.appcompat.content.res.AppCompatResources;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.screenshot.ScreenshotActionsController;
import com.android.systemui.screenshot.ui.viewmodel.ActionButtonAppearance;
import com.android.systemui.screenshot.ui.viewmodel.PreviewAction;
import java.util.UUID;
import kotlin.Unit;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DefaultScreenshotActionsProvider implements ScreenshotActionsProvider {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActionExecutor actionExecutor;
    public final ActionIntentCreator actionIntentCreator;
    public final ScreenshotActionsController.ActionsCallback actionsCallback;
    public boolean addedScrollChip;
    public final CoroutineScope applicationScope;
    public final Context context;
    public LegacyScreenshotController$$ExternalSyntheticLambda15 onScrollClick;
    public SuspendLambda pendingAction;
    public final ScreenshotData request;
    public ScreenshotSavedResult result;
    public final UiEventLogger uiEventLogger;
    public Uri webUri;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public DefaultScreenshotActionsProvider(Context context, UiEventLogger uiEventLogger, ActionIntentCreator actionIntentCreator, CoroutineScope coroutineScope, UUID uuid, ScreenshotData screenshotData, ActionExecutor actionExecutor, ScreenshotActionsController.ActionsCallback actionsCallback) {
        this.context = context;
        this.uiEventLogger = uiEventLogger;
        this.actionIntentCreator = actionIntentCreator;
        this.applicationScope = coroutineScope;
        this.request = screenshotData;
        this.actionExecutor = actionExecutor;
        this.actionsCallback = actionsCallback;
        PreviewAction previewAction = new PreviewAction(context.getResources().getString(R.string.screenshot_edit_description), new DefaultScreenshotActionsProvider$$ExternalSyntheticLambda0(this, 0));
        UUID uuid2 = actionsCallback.screenshotId;
        ScreenshotActionsController screenshotActionsController = ScreenshotActionsController.this;
        if (Intrinsics.areEqual(uuid2, screenshotActionsController.currentScreenshotId)) {
            screenshotActionsController.viewModel._previewAction.updateState(null, previewAction);
        }
        actionsCallback.provideActionButton(new ActionButtonAppearance(AppCompatResources.getDrawable(R.drawable.ic_screenshot_share, context), context.getResources().getString(R.string.screenshot_share_label), context.getResources().getString(R.string.screenshot_share_description), false, null, 24, null), new DefaultScreenshotActionsProvider$$ExternalSyntheticLambda0(this, 1));
        actionsCallback.provideActionButton(new ActionButtonAppearance(AppCompatResources.getDrawable(R.drawable.ic_screenshot_edit, context), context.getResources().getString(R.string.screenshot_edit_label), context.getResources().getString(R.string.screenshot_edit_description), false, null, 24, null), new DefaultScreenshotActionsProvider$$ExternalSyntheticLambda0(this, 2));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onDeferrableActionTapped(Function2 function2) {
        ScreenshotSavedResult screenshotSavedResult = this.result;
        if (screenshotSavedResult != null) {
            BuildersKt.launch$default(this.applicationScope, null, null, new DefaultScreenshotActionsProvider$onDeferrableActionTapped$1$1(function2, screenshotSavedResult, null), 3);
        } else {
            this.pendingAction = (SuspendLambda) function2;
            Unit unit = Unit.INSTANCE;
        }
    }
}
