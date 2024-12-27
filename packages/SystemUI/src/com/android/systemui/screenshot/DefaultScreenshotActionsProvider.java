package com.android.systemui.screenshot;

import android.content.Context;
import android.net.Uri;
import androidx.appcompat.content.res.AppCompatResources;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.screenshot.ScreenshotActionsController;
import com.android.systemui.screenshot.ui.viewmodel.ActionButtonAppearance;
import com.android.systemui.screenshot.ui.viewmodel.PreviewAction;
import java.util.UUID;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DefaultScreenshotActionsProvider {
    public final ActionExecutor actionExecutor;
    public final Context context;
    public final ScreenshotData request;
    public final UiEventLogger uiEventLogger;

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

    public DefaultScreenshotActionsProvider(Context context, UiEventLogger uiEventLogger, UUID uuid, ScreenshotData screenshotData, ActionExecutor actionExecutor, ScreenshotActionsController.ActionsCallback actionsCallback) {
        this.context = context;
        this.uiEventLogger = uiEventLogger;
        this.request = screenshotData;
        this.actionExecutor = actionExecutor;
        PreviewAction previewAction = new PreviewAction(context.getResources().getString(R.string.screenshot_edit_description), new Function0() { // from class: com.android.systemui.screenshot.DefaultScreenshotActionsProvider.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = DebugLogger.$r8$clinit;
                Reflection.getOrCreateKotlinClass(DefaultScreenshotActionsProvider.this.getClass()).getSimpleName();
                DefaultScreenshotActionsProvider defaultScreenshotActionsProvider = DefaultScreenshotActionsProvider.this;
                defaultScreenshotActionsProvider.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_PREVIEW_TAPPED, 0, defaultScreenshotActionsProvider.request.getPackageNameString());
                final DefaultScreenshotActionsProvider defaultScreenshotActionsProvider2 = DefaultScreenshotActionsProvider.this;
                new Function1() { // from class: com.android.systemui.screenshot.DefaultScreenshotActionsProvider.1.2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        ScreenshotSavedResult screenshotSavedResult = (ScreenshotSavedResult) obj;
                        DefaultScreenshotActionsProvider defaultScreenshotActionsProvider3 = DefaultScreenshotActionsProvider.this;
                        ActionExecutor actionExecutor2 = defaultScreenshotActionsProvider3.actionExecutor;
                        ActionIntentCreator actionIntentCreator = ActionIntentCreator.INSTANCE;
                        Uri uri = screenshotSavedResult.uri;
                        Context context2 = defaultScreenshotActionsProvider3.context;
                        actionIntentCreator.getClass();
                        actionExecutor2.startSharedTransition(ActionIntentCreator.createEdit(context2, uri), screenshotSavedResult.user, true);
                        return Unit.INSTANCE;
                    }
                };
                defaultScreenshotActionsProvider2.getClass();
                return Unit.INSTANCE;
            }
        });
        UUID uuid2 = actionsCallback.screenshotId;
        ScreenshotActionsController screenshotActionsController = ScreenshotActionsController.this;
        screenshotActionsController.getClass();
        if (Intrinsics.areEqual(uuid2, (Object) null)) {
            screenshotActionsController.viewModel._previewAction.updateState(null, previewAction);
        }
        actionsCallback.provideActionButton(new ActionButtonAppearance(AppCompatResources.getDrawable(R.drawable.ic_screenshot_share, context), context.getResources().getString(R.string.screenshot_share_label), context.getResources().getString(R.string.screenshot_share_description), false, null, 24, null), new Function0() { // from class: com.android.systemui.screenshot.DefaultScreenshotActionsProvider.2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = DebugLogger.$r8$clinit;
                Reflection.getOrCreateKotlinClass(DefaultScreenshotActionsProvider.this.getClass()).getSimpleName();
                DefaultScreenshotActionsProvider defaultScreenshotActionsProvider = DefaultScreenshotActionsProvider.this;
                defaultScreenshotActionsProvider.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_SHARE_TAPPED, 0, defaultScreenshotActionsProvider.request.getPackageNameString());
                final DefaultScreenshotActionsProvider defaultScreenshotActionsProvider2 = DefaultScreenshotActionsProvider.this;
                new Function1() { // from class: com.android.systemui.screenshot.DefaultScreenshotActionsProvider.2.2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        ScreenshotSavedResult screenshotSavedResult = (ScreenshotSavedResult) obj;
                        ActionExecutor actionExecutor2 = DefaultScreenshotActionsProvider.this.actionExecutor;
                        ActionIntentCreator actionIntentCreator = ActionIntentCreator.INSTANCE;
                        Uri uri = screenshotSavedResult.uri;
                        actionIntentCreator.getClass();
                        actionExecutor2.startSharedTransition(ActionIntentCreator.createShare(uri, screenshotSavedResult.subject, null), screenshotSavedResult.user, false);
                        return Unit.INSTANCE;
                    }
                };
                defaultScreenshotActionsProvider2.getClass();
                return Unit.INSTANCE;
            }
        });
        actionsCallback.provideActionButton(new ActionButtonAppearance(AppCompatResources.getDrawable(R.drawable.ic_screenshot_edit, context), context.getResources().getString(R.string.screenshot_edit_label), context.getResources().getString(R.string.screenshot_edit_description), false, null, 24, null), new Function0() { // from class: com.android.systemui.screenshot.DefaultScreenshotActionsProvider.3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = DebugLogger.$r8$clinit;
                Reflection.getOrCreateKotlinClass(DefaultScreenshotActionsProvider.this.getClass()).getSimpleName();
                DefaultScreenshotActionsProvider defaultScreenshotActionsProvider = DefaultScreenshotActionsProvider.this;
                defaultScreenshotActionsProvider.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_EDIT_TAPPED, 0, defaultScreenshotActionsProvider.request.getPackageNameString());
                final DefaultScreenshotActionsProvider defaultScreenshotActionsProvider2 = DefaultScreenshotActionsProvider.this;
                new Function1() { // from class: com.android.systemui.screenshot.DefaultScreenshotActionsProvider.3.2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        ScreenshotSavedResult screenshotSavedResult = (ScreenshotSavedResult) obj;
                        DefaultScreenshotActionsProvider defaultScreenshotActionsProvider3 = DefaultScreenshotActionsProvider.this;
                        ActionExecutor actionExecutor2 = defaultScreenshotActionsProvider3.actionExecutor;
                        ActionIntentCreator actionIntentCreator = ActionIntentCreator.INSTANCE;
                        Uri uri = screenshotSavedResult.uri;
                        Context context2 = defaultScreenshotActionsProvider3.context;
                        actionIntentCreator.getClass();
                        actionExecutor2.startSharedTransition(ActionIntentCreator.createEdit(context2, uri), screenshotSavedResult.user, true);
                        return Unit.INSTANCE;
                    }
                };
                defaultScreenshotActionsProvider2.getClass();
                return Unit.INSTANCE;
            }
        });
    }
}
