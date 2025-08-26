package com.android.wm.shell.desktopmode.education;

import android.R;
import android.content.Context;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.desktopmode.WindowDecorCaptionHandleRepository;
import com.android.wm.shell.desktopmode.education.data.AppHandleEducationDatastoreRepository;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.windowdecor.education.DesktopWindowingEducationTooltipController;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.MainCoroutineDispatcher;

/* loaded from: classes3.dex */
public final class AppHandleEducationController {
    public static final Companion Companion = null;
    public final AppHandleEducationDatastoreRepository appHandleEducationDatastoreRepository;
    public final AppHandleEducationFilter appHandleEducationFilter;
    public final MainCoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final DesktopModeUiEventLogger desktopModeUiEventLogger;
    public final WindowDecorCaptionHandleRepository windowDecorCaptionHandleRepository;
    public final DesktopWindowingEducationTooltipController windowingEducationViewController;

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

    public AppHandleEducationController(Context context, AppHandleEducationFilter appHandleEducationFilter, AppHandleEducationDatastoreRepository appHandleEducationDatastoreRepository, WindowDecorCaptionHandleRepository windowDecorCaptionHandleRepository, DesktopWindowingEducationTooltipController desktopWindowingEducationTooltipController, CoroutineScope coroutineScope, MainCoroutineDispatcher mainCoroutineDispatcher, DesktopModeUiEventLogger desktopModeUiEventLogger, DesktopState desktopState) {
        this.context = context;
        this.appHandleEducationFilter = appHandleEducationFilter;
        this.appHandleEducationDatastoreRepository = appHandleEducationDatastoreRepository;
        this.windowDecorCaptionHandleRepository = windowDecorCaptionHandleRepository;
        this.windowingEducationViewController = desktopWindowingEducationTooltipController;
        this.backgroundDispatcher = mainCoroutineDispatcher;
        this.desktopModeUiEventLogger = desktopModeUiEventLogger;
        context.getColor(R.color.search_widget_corpus_item_background);
        context.getColor(R.color.surface_variant_light);
        boolean z = ((DesktopStateImpl) desktopState).canEnterDesktopMode;
    }
}
