package com.android.systemui.navigationbar.gestural;

import android.app.ActivityThread;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.util.Log;
import android.view.InputMonitor;
import android.view.TwoFingerSwipeGestureDetector;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.systemui.BasicRune;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.SystemUIInitializer;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.rune.CoreRune;
import java.util.Optional;
import java.util.function.Function;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EdgeBackSplitGestureHandler {
    public static final Companion Companion = new Companion(null);
    public static final boolean SAFE_DEBUG;
    public final Context context;
    public final DisplayController displayController;
    public final int displayId;
    public boolean enabled;
    public boolean gestureDetected;
    public final TwoFingerSwipeGestureDetector gestureDetector;
    public InputMonitor inputMonitor;
    public final SettingsHelper settingsHelper;
    public final SplitScreenController splitScreenController;
    public final Rect tmpBounds = new Rect();
    public final EdgeBackSplitGestureHandler$settingsCallBack$1 settingsCallBack = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackSplitGestureHandler$settingsCallBack$1
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            EdgeBackSplitGestureHandler edgeBackSplitGestureHandler = EdgeBackSplitGestureHandler.this;
            SettingsHelper settingsHelper = edgeBackSplitGestureHandler.settingsHelper;
            settingsHelper.getClass();
            boolean z = BasicRune.NAVBAR_MW_ENTER_SPLIT_USING_GESTURE;
            boolean z2 = z && settingsHelper.mItemLists.get("open_in_split_screen_view").getIntValue() != 0;
            SettingsHelper settingsHelper2 = edgeBackSplitGestureHandler.settingsHelper;
            settingsHelper2.getClass();
            boolean z3 = z2 & (z && settingsHelper2.mItemLists.get("open_in_split_screen_view").getIntValue() != 0);
            if (edgeBackSplitGestureHandler.enabled != z3) {
                edgeBackSplitGestureHandler.enabled = z3;
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.navigationbar.gestural.EdgeBackSplitGestureHandler$settingsCallBack$1] */
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
                            ListPopupWindow$$ExternalSyntheticOutline0.m10m("onCommitted SplitGestureHandler , getureFrom = ", i2, "EdgeBackSplitGestureHandler");
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
                        InputMonitor inputMonitor = EdgeBackSplitGestureHandler.this.inputMonitor;
                        if (inputMonitor == null) {
                            inputMonitor = null;
                        }
                        inputMonitor.pilferPointers();
                        EdgeBackSplitGestureHandler.this.gestureDetected = true;
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:21:0x004f, code lost:
                    
                        if (((r2 == null || (r2 = r2.getConfiguration()) == null || r2.semDisplayDeviceType != 5) ? false : true) == false) goto L28;
                     */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void onDetecting() {
                        DisplayLayout displayLayout;
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
                        if ((displayLayout.mWidth > displayLayout.mHeight ? (char) 2 : (char) 1) != 2) {
                            if (CoreRune.MW_MULTI_SPLIT_FULL_TO_SPLIT_BY_GESTURE) {
                                Resources resources = edgeBackSplitGestureHandler3.context.getResources();
                            }
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
}
