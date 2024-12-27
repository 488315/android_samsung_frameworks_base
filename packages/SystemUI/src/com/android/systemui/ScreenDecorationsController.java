package com.android.systemui;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import com.android.systemui.ScreenDecorationsComponent;
import com.android.systemui.decor.CoverPrivacyDotDecorProviderFactory;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.events.CoverPrivacyDotViewController;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class ScreenDecorationsController implements CoreStartable {
    public final Context mContext;
    public final CoverPrivacyDotDecorProviderFactory mCoverDotFactory;
    public final CoverPrivacyDotViewController mCoverPrivacyDotViewController;
    public final DisplayTracker mDisplayTracker;
    public final PrivacyDotDecorProviderFactory mDotFactory;
    public final PrivacyDotViewController mPrivacyDotViewController;
    public final ScreenDecorationsComponent.Factory mScreenDecorationsFactory;
    public final List mScreenDecorationsList = new ArrayList();

    public ScreenDecorationsController(Context context, DisplayTracker displayTracker, ScreenDecorationsComponent.Factory factory, PrivacyDotViewController privacyDotViewController, CoverPrivacyDotViewController coverPrivacyDotViewController, PrivacyDotDecorProviderFactory privacyDotDecorProviderFactory, CoverPrivacyDotDecorProviderFactory coverPrivacyDotDecorProviderFactory) {
        this.mContext = context;
        this.mDisplayTracker = displayTracker;
        this.mScreenDecorationsFactory = factory;
        this.mPrivacyDotViewController = privacyDotViewController;
        this.mCoverPrivacyDotViewController = coverPrivacyDotViewController;
        this.mDotFactory = privacyDotDecorProviderFactory;
        this.mCoverDotFactory = coverPrivacyDotDecorProviderFactory;
    }

    public final void createScreenDecorations(Display display) {
        if (display == null) {
            return;
        }
        int displayId = display.getDisplayId();
        this.mDisplayTracker.getClass();
        boolean z = displayId == 0;
        ((ArrayList) this.mScreenDecorationsList).add(this.mScreenDecorationsFactory.create(z ? this.mContext : this.mContext.createWindowContext(display, 2024, null), z ? this.mPrivacyDotViewController : this.mCoverPrivacyDotViewController, z ? this.mDotFactory : this.mCoverDotFactory).getScreenDecorations());
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        createScreenDecorations(((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(0));
        if (this.mContext.getResources().getBoolean(R.bool.config_enableCoverScreenPrivacyDot) || this.mContext.getResources().getBoolean(R.bool.config_enableCoverScreenRoundedCorner)) {
            createScreenDecorations(((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(1));
        }
        ((ArrayList) this.mScreenDecorationsList).forEach(new ScreenDecorationsController$$ExternalSyntheticLambda0());
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
    }
}
