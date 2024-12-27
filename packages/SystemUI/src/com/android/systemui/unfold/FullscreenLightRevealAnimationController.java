package com.android.systemui.unfold;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.view.Display;
import android.view.DisplayInfo;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.unfold.updates.RotationChangeProvider;
import com.android.systemui.util.concurrency.ThreadFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

public final class FullscreenLightRevealAnimationController {
    public static final Companion Companion = null;
    public final Handler bgHandler;
    public final Context context;
    public int currentRotation;
    public final Function1 displaySelector;
    public final Executor executor;
    public final List internalDisplayInfos;
    public final Function1 lightRevealEffectFactory;
    public final ThreadFactory threadFactory;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public interface Factory {
    }

    public final class RotationWatcher implements RotationChangeProvider.RotationListener {
        public RotationWatcher() {
        }

        @Override // com.android.systemui.unfold.updates.RotationChangeProvider.RotationListener
        public final void onRotationChanged(int i) {
            FullscreenLightRevealAnimationController fullscreenLightRevealAnimationController = FullscreenLightRevealAnimationController.this;
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
                TraceUtilsKt.beginSlice("FullscreenLightRevealAnimation#onRotationChanged");
            }
            try {
                Companion companion = FullscreenLightRevealAnimationController.Companion;
                fullscreenLightRevealAnimationController.getClass();
                if (!Intrinsics.areEqual(Looper.myLooper(), fullscreenLightRevealAnimationController.bgHandler.getLooper())) {
                    throw new IllegalStateException("Not being executed in the background!".toString());
                }
                if (fullscreenLightRevealAnimationController.currentRotation != i) {
                    fullscreenLightRevealAnimationController.currentRotation = i;
                }
                Unit unit = Unit.INSTANCE;
            } finally {
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
            }
        }
    }

    static {
        new Companion(null);
    }

    public FullscreenLightRevealAnimationController(Context context, DisplayManager displayManager, ThreadFactory threadFactory, Handler handler, RotationChangeProvider rotationChangeProvider, Optional<Object> optional, DisplayTracker displayTracker, CoroutineScope coroutineScope, Executor executor, Function1 function1, Function1 function12, String str) {
        this.threadFactory = threadFactory;
        this.bgHandler = handler;
        this.executor = executor;
        this.currentRotation = context.getDisplay().getRotation();
        new RotationWatcher();
        Display[] displays = displayManager.getDisplays("android.hardware.display.category.ALL_INCLUDING_DISABLED");
        ArrayList arrayList = new ArrayList(displays.length);
        for (Display display : displays) {
            DisplayInfo displayInfo = new DisplayInfo();
            display.getDisplayInfo(displayInfo);
            arrayList.add(displayInfo);
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (((DisplayInfo) next).type == 1) {
                arrayList2.add(next);
            }
        }
    }
}
