package com.android.wm.shell.hidedisplaycutout;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.view.SurfaceControl;
import android.window.DisplayAreaAppearedInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.sysui.ConfigurationChangeListener;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.BiConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HideDisplayCutoutController implements ConfigurationChangeListener {
    public final Context mContext;
    boolean mEnabled;
    public final HideDisplayCutoutOrganizer mOrganizer;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellController mShellController;

    public HideDisplayCutoutController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellController shellController, HideDisplayCutoutOrganizer hideDisplayCutoutOrganizer) {
        this.mContext = context;
        this.mShellCommandHandler = shellCommandHandler;
        this.mShellController = shellController;
        this.mOrganizer = hideDisplayCutoutOrganizer;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.hidedisplaycutout.HideDisplayCutoutController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                final HideDisplayCutoutController hideDisplayCutoutController = HideDisplayCutoutController.this;
                hideDisplayCutoutController.getClass();
                hideDisplayCutoutController.mShellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.hidedisplaycutout.HideDisplayCutoutController$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        HideDisplayCutoutController hideDisplayCutoutController2 = HideDisplayCutoutController.this;
                        PrintWriter printWriter = (PrintWriter) obj;
                        hideDisplayCutoutController2.getClass();
                        printWriter.print("HideDisplayCutoutController");
                        printWriter.println(" states: ");
                        printWriter.print("  ");
                        printWriter.print("mEnabled=");
                        printWriter.println(hideDisplayCutoutController2.mEnabled);
                        HideDisplayCutoutOrganizer hideDisplayCutoutOrganizer2 = hideDisplayCutoutController2.mOrganizer;
                        hideDisplayCutoutOrganizer2.getClass();
                        printWriter.print("HideDisplayCutoutOrganizer");
                        printWriter.println(" states: ");
                        synchronized (hideDisplayCutoutOrganizer2) {
                            printWriter.print("  ");
                            printWriter.print("mDisplayAreaMap=");
                            printWriter.println(hideDisplayCutoutOrganizer2.mDisplayAreaMap);
                        }
                        printWriter.print("  ");
                        printWriter.print("getDisplayBoundsOfNaturalOrientation()=");
                        printWriter.println(hideDisplayCutoutOrganizer2.getDisplayBoundsOfNaturalOrientation());
                        printWriter.print("  ");
                        printWriter.print("mDefaultDisplayBounds=");
                        printWriter.println(hideDisplayCutoutOrganizer2.mDefaultDisplayBounds);
                        printWriter.print("  ");
                        printWriter.print("mCurrentDisplayBounds=");
                        printWriter.println(hideDisplayCutoutOrganizer2.mCurrentDisplayBounds);
                        printWriter.print("  ");
                        printWriter.print("mDefaultCutoutInsets=");
                        printWriter.println(hideDisplayCutoutOrganizer2.mDefaultCutoutInsets);
                        printWriter.print("  ");
                        printWriter.print("mCurrentCutoutInsets=");
                        printWriter.println(hideDisplayCutoutOrganizer2.mCurrentCutoutInsets);
                        printWriter.print("  ");
                        printWriter.print("mRotation=");
                        printWriter.println(hideDisplayCutoutOrganizer2.mRotation);
                        printWriter.print("  ");
                        printWriter.print("mStatusBarHeight=");
                        printWriter.println(hideDisplayCutoutOrganizer2.mStatusBarHeight);
                        printWriter.print("  ");
                        printWriter.print("mOffsetX=");
                        printWriter.println(hideDisplayCutoutOrganizer2.mOffsetX);
                        printWriter.print("  ");
                        printWriter.print("mOffsetY=");
                        printWriter.println(hideDisplayCutoutOrganizer2.mOffsetY);
                    }
                }, hideDisplayCutoutController);
                hideDisplayCutoutController.updateStatus();
                hideDisplayCutoutController.mShellController.addConfigurationChangeListener(hideDisplayCutoutController);
            }
        }, this);
    }

    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
    public final void onConfigurationChanged(Configuration configuration) {
        updateStatus();
    }

    public void updateStatus() {
        boolean z = this.mContext.getResources().getBoolean(R.bool.config_enhanced_iwlan_handover_check);
        if (z == this.mEnabled) {
            return;
        }
        this.mEnabled = z;
        HideDisplayCutoutOrganizer hideDisplayCutoutOrganizer = this.mOrganizer;
        if (!z) {
            hideDisplayCutoutOrganizer.updateBoundsAndOffsets(false);
            hideDisplayCutoutOrganizer.mDisplayController.removeDisplayWindowListener(hideDisplayCutoutOrganizer.mListener);
            hideDisplayCutoutOrganizer.unregisterOrganizer();
            return;
        }
        hideDisplayCutoutOrganizer.mDisplayController.addDisplayWindowListener(hideDisplayCutoutOrganizer.mListener);
        DisplayLayout displayLayout = hideDisplayCutoutOrganizer.mDisplayController.getDisplayLayout(0);
        if (displayLayout != null) {
            hideDisplayCutoutOrganizer.mRotation = displayLayout.mRotation;
        }
        List registerOrganizer = hideDisplayCutoutOrganizer.registerOrganizer(6);
        for (int i = 0; i < registerOrganizer.size(); i++) {
            hideDisplayCutoutOrganizer.addDisplayAreaInfoAndLeashToMap(((DisplayAreaAppearedInfo) registerOrganizer.get(i)).getDisplayAreaInfo(), ((DisplayAreaAppearedInfo) registerOrganizer.get(i)).getLeash());
        }
        hideDisplayCutoutOrganizer.updateBoundsAndOffsets(true);
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        synchronized (hideDisplayCutoutOrganizer) {
            hideDisplayCutoutOrganizer.mDisplayAreaMap.forEach(new HideDisplayCutoutOrganizer$$ExternalSyntheticLambda0(hideDisplayCutoutOrganizer, windowContainerTransaction, transaction));
        }
        hideDisplayCutoutOrganizer.applyTransaction(windowContainerTransaction, transaction);
    }
}
