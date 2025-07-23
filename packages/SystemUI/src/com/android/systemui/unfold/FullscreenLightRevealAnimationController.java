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
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FullscreenLightRevealAnimationController {
    public static final Companion Companion = null;
    public final Handler bgHandler;
    public final Context context;
    public int currentRotation;
    public final Function1 displaySelector;
    public final Executor executor;
    public final List internalDisplayInfos;
    public final Function1 lightRevealEffectFactory;
    public final String overlayTitle;
    public final ThreadFactory threadFactory;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    throw new IllegalStateException("Not being executed in the background!");
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
        this.context = context;
        this.threadFactory = threadFactory;
        this.bgHandler = handler;
        this.executor = executor;
        this.displaySelector = function1;
        this.lightRevealEffectFactory = function12;
        this.overlayTitle = str;
        this.currentRotation = context.getDisplay().getRotation();
        new RotationWatcher();
        Display[] displays = displayManager.getDisplays("android.hardware.display.category.ALL_INCLUDING_DISABLED");
        ArrayList arrayList = new ArrayList(displays.length);
        int i = 0;
        for (Display display : displays) {
            DisplayInfo displayInfo = new DisplayInfo();
            display.getDisplayInfo(displayInfo);
            arrayList.add(displayInfo);
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (((DisplayInfo) obj).type == 1) {
                arrayList2.add(obj);
            }
        }
        this.internalDisplayInfos = arrayList2;
    }
}
