package com.android.systemui.navigationbar.gestural;

import android.app.ActivityThread;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;
import android.view.TwoFingerSwipeGestureDetector;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.SystemUIInitializer;
import com.android.systemui.shared.system.InputMonitorCompat;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.rune.CoreRune;
import java.util.Optional;
import java.util.function.Function;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class EdgeBackSplitGestureHandler {
    public static final Companion Companion = new Companion(null);
    public static final boolean SAFE_DEBUG;
    public final Context context;
    public final DisplayController displayController;
    public final int displayId;
    public boolean enabled;
    public boolean gestureDetected;
    public final TwoFingerSwipeGestureDetector gestureDetector;
    public InputMonitorCompat inputMonitor;
    private final SettingsHelper settingsHelper;
    public final SplitScreenController splitScreenController;
    public final Rect tmpBounds = new Rect();
    private SettingsHelper.OnChangedCallback settingsCallBack = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackSplitGestureHandler$settingsCallBack$1
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            EdgeBackSplitGestureHandler edgeBackSplitGestureHandler = EdgeBackSplitGestureHandler.this;
            edgeBackSplitGestureHandler.setEnabled(edgeBackSplitGestureHandler.getSettingsHelper().isSplitGestureEnabled());
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        String str = Build.TYPE;
        SAFE_DEBUG = str.equals("userdebug") | str.equals("eng");
    }

    public EdgeBackSplitGestureHandler(Context context, int i, SettingsHelper settingsHelper) {
        this.context = context;
        this.displayId = i;
        this.settingsHelper = settingsHelper;
        if (BasicRune.NAVBAR_MW_ENTER_SPLIT_USING_GESTURE && Process.myUserHandle().isSystem() && Intrinsics.areEqual(ActivityThread.currentProcessName(), ActivityThread.currentPackageName())) {
            SystemUIAppComponentFactoryBase.Companion.getClass();
            SystemUIInitializer systemUIInitializer = SystemUIAppComponentFactoryBase.systemUIInitializer;
            Intrinsics.checkNotNull(systemUIInitializer);
            Optional displayController = systemUIInitializer.getWMComponent().getDisplayController();
            if (displayController.isPresent()) {
                this.displayController = (DisplayController) displayController.get();
            }
            Optional splitScreenController = systemUIInitializer.getWMComponent().getSplitScreenController();
            if (splitScreenController.isPresent()) {
                this.splitScreenController = (SplitScreenController) splitScreenController.get();
            }
        }
        this.gestureDetector = new TwoFingerSwipeGestureDetector(context, new Function() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackSplitGestureHandler.1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                final TwoFingerSwipeGestureDetector twoFingerSwipeGestureDetector = (TwoFingerSwipeGestureDetector) obj;
                final EdgeBackSplitGestureHandler edgeBackSplitGestureHandler = EdgeBackSplitGestureHandler.this;
                Companion companion = EdgeBackSplitGestureHandler.Companion;
                edgeBackSplitGestureHandler.getClass();
                return new TwoFingerSwipeGestureDetector.GestureListener() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackSplitGestureHandler$createGestureListener$1
                    public final void onCanceled() {
                        EdgeBackSplitGestureHandler.Companion.getClass();
                        if (EdgeBackSplitGestureHandler.SAFE_DEBUG) {
                            Log.d("EdgeBackSplitGestureHandler", "onCanceled SplitGestureHandler");
                        }
                        EdgeBackSplitGestureHandler.this.gestureDetected = false;
                    }

                    public final void onCommitted(int i2) {
                        EdgeBackSplitGestureHandler.Companion.getClass();
                        if (EdgeBackSplitGestureHandler.SAFE_DEBUG) {
                            ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "onCommitted SplitGestureHandler , getureFrom = ", "EdgeBackSplitGestureHandler");
                        }
                        EdgeBackSplitGestureHandler edgeBackSplitGestureHandler2 = EdgeBackSplitGestureHandler.this;
                        edgeBackSplitGestureHandler2.gestureDetected = false;
                        SplitScreenController splitScreenController2 = edgeBackSplitGestureHandler2.splitScreenController;
                        if (splitScreenController2 != null) {
                            splitScreenController2.mImpl.startSplitByTwoTouchSwipeIfPossible(i2);
                        } else {
                            Log.e("EdgeBackSplitGestureHandler", "gesture committed but split controller is null.");
                        }
                    }

                    public final void onDetected() {
                        EdgeBackSplitGestureHandler.Companion.getClass();
                        if (EdgeBackSplitGestureHandler.SAFE_DEBUG) {
                            Log.d("EdgeBackSplitGestureHandler", "onDetected SplitGestureHandler");
                        }
                        InputMonitorCompat inputMonitorCompat = EdgeBackSplitGestureHandler.this.inputMonitor;
                        if (inputMonitorCompat == null) {
                            inputMonitorCompat = null;
                        }
                        inputMonitorCompat.mInputMonitor.pilferPointers();
                        EdgeBackSplitGestureHandler.this.gestureDetected = true;
                    }

                    public final void onDetecting() {
                        DisplayLayout displayLayout;
                        Resources resources;
                        Configuration configuration;
                        EdgeBackSplitGestureHandler.Companion.getClass();
                        if (EdgeBackSplitGestureHandler.SAFE_DEBUG) {
                            Log.d("EdgeBackSplitGestureHandler", "onDetecting in SplitGestureHandler");
                        }
                        EdgeBackSplitGestureHandler edgeBackSplitGestureHandler2 = EdgeBackSplitGestureHandler.this;
                        DisplayController displayController2 = edgeBackSplitGestureHandler2.displayController;
                        if (displayController2 == null || (displayLayout = displayController2.getDisplayLayout(edgeBackSplitGestureHandler2.displayId)) == null) {
                            Log.e("EdgeBackSplitGestureHandler", "gesture detecting but display frame is null");
                            return;
                        }
                        EdgeBackSplitGestureHandler edgeBackSplitGestureHandler3 = EdgeBackSplitGestureHandler.this;
                        TwoFingerSwipeGestureDetector twoFingerSwipeGestureDetector2 = twoFingerSwipeGestureDetector;
                        displayLayout.getDisplayBounds(edgeBackSplitGestureHandler3.tmpBounds);
                        int i2 = 5;
                        if ((displayLayout.mWidth > displayLayout.mHeight ? (char) 2 : (char) 1) != 2 && (!CoreRune.MW_MULTI_SPLIT_FULL_TO_SPLIT_BY_GESTURE || ((resources = edgeBackSplitGestureHandler3.context.getResources()) != null && (configuration = resources.getConfiguration()) != null && configuration.semDisplayDeviceType == 5))) {
                            i2 = 0;
                        }
                        twoFingerSwipeGestureDetector2.init(edgeBackSplitGestureHandler3.tmpBounds, displayLayout.density(), i2);
                    }

                    public final void onEnd() {
                        EdgeBackSplitGestureHandler.Companion.getClass();
                        if (EdgeBackSplitGestureHandler.SAFE_DEBUG) {
                            Log.d("EdgeBackSplitGestureHandler", "onCanceled SplitGestureHandler");
                        }
                        EdgeBackSplitGestureHandler.this.gestureDetected = false;
                    }
                };
            }
        }, "EdgeBack");
    }

    public final SettingsHelper getSettingsHelper() {
        return this.settingsHelper;
    }

    public final void onNavBarAttached() {
        this.settingsHelper.registerCallback(this.settingsCallBack, Settings.Global.getUriFor(SettingsHelper.INDEX_MW_ENTER_SPLIT_USING_GESTURE));
    }

    public final void onNavBarDetached() {
        this.settingsHelper.unregisterCallback(this.settingsCallBack);
    }

    public final void setEnabled(boolean z) {
        boolean isSplitGestureEnabled = z & this.settingsHelper.isSplitGestureEnabled();
        if (this.enabled != isSplitGestureEnabled) {
            this.enabled = isSplitGestureEnabled;
        }
    }
}
