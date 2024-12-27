package com.android.systemui.screenshot;

import android.app.ActivityOptions;
import android.app.ExitTransitionCoordinator;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Pair;
import android.view.Window;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

public final class ActionExecutor {
    public final CoroutineScope applicationScope;
    public final Function0 finishDismiss;
    public final ActionIntentExecutor intentExecutor;
    public boolean isPendingSharedTransition;
    public final ScreenshotViewProxy viewProxy;
    public final Window window;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public interface Factory {
        ActionExecutor create(Window window, ScreenshotViewProxy screenshotViewProxy, Function0 function0);
    }

    static {
        new Companion(null);
    }

    public ActionExecutor(ActionIntentExecutor actionIntentExecutor, CoroutineScope coroutineScope, Window window, ScreenshotViewProxy screenshotViewProxy, Function0 function0) {
        this.intentExecutor = actionIntentExecutor;
        this.applicationScope = coroutineScope;
        this.window = window;
        this.viewProxy = screenshotViewProxy;
        this.finishDismiss = function0;
    }

    public final void startSharedTransition(Intent intent, UserHandle userHandle, boolean z) {
        this.isPendingSharedTransition = true;
        ScreenshotViewProxy screenshotViewProxy = this.viewProxy;
        screenshotViewProxy.fadeForSharedTransition();
        BuildersKt.launch$default(this.applicationScope, EmptyCoroutineContext.INSTANCE, null, new ActionExecutor$startSharedTransition$$inlined$launch$default$1("ActionExecutor#launchIntentAsync", null, this, intent, userHandle, z, ActivityOptions.startSharedElementAnimation(this.window, new ExitTransitionCoordinator.ExitTransitionCallbacks() { // from class: com.android.systemui.screenshot.ActionExecutor$createWindowTransition$callbacks$1
            public final void hideSharedElements() {
                ActionExecutor actionExecutor = ActionExecutor.this;
                actionExecutor.isPendingSharedTransition = false;
                actionExecutor.finishDismiss.invoke();
            }

            public final boolean isReturnTransitionAllowed() {
                return false;
            }

            public final void onFinish() {
            }
        }, null, new Pair[]{Pair.create(screenshotViewProxy.getScreenshotPreview(), "screenshot_preview_image")})), 2);
    }
}
