package com.android.wm.shell.desktopmode.education;

import android.content.Context;
import com.android.wm.shell.desktopmode.WindowDecorCaptionHandleRepository;
import com.android.wm.shell.desktopmode.education.data.AppToWebEducationDatastoreRepository;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.windowdecor.common.DecorThemeUtil;
import com.android.wm.shell.windowdecor.education.DesktopWindowingEducationPromoController;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.MainCoroutineDispatcher;

/* loaded from: classes3.dex */
public final class AppToWebEducationController {
    public static final Companion Companion = null;
    public final AppToWebEducationDatastoreRepository appToWebEducationDatastoreRepository;
    public final AppToWebEducationFilter appToWebEducationFilter;
    public final MainCoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final DecorThemeUtil decorThemeUtil;
    public final WindowDecorCaptionHandleRepository windowDecorCaptionHandleRepository;
    public final DesktopWindowingEducationPromoController windowingEducationViewController;

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

    public AppToWebEducationController(Context context, AppToWebEducationFilter appToWebEducationFilter, AppToWebEducationDatastoreRepository appToWebEducationDatastoreRepository, WindowDecorCaptionHandleRepository windowDecorCaptionHandleRepository, DesktopWindowingEducationPromoController desktopWindowingEducationPromoController, CoroutineScope coroutineScope, MainCoroutineDispatcher mainCoroutineDispatcher, DesktopState desktopState) {
        this.context = context;
        this.appToWebEducationFilter = appToWebEducationFilter;
        this.appToWebEducationDatastoreRepository = appToWebEducationDatastoreRepository;
        this.windowDecorCaptionHandleRepository = windowDecorCaptionHandleRepository;
        this.windowingEducationViewController = desktopWindowingEducationPromoController;
        this.backgroundDispatcher = mainCoroutineDispatcher;
        this.decorThemeUtil = new DecorThemeUtil(context);
        boolean z = ((DesktopStateImpl) desktopState).canEnterDesktopMode;
    }
}
