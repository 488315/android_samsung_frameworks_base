package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.View;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.util.HashSet;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class UdfpsAnimationViewController extends ViewController implements Dumpable {
    public ValueAnimator dialogAlphaAnimator;
    public final UdfpsAnimationViewController$dialogListener$1 dialogListener;
    public final SystemUIDialogManager dialogManager;
    public final DumpManager dumpManager;
    public final String dumpTag;
    public boolean notificationShadeVisible;
    public final UdfpsAnimationViewController$shadeExpansionListener$1 shadeExpansionListener;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final StatusBarStateController statusBarStateController;
    public final PointF touchTranslation;

    public UdfpsAnimationViewController(UdfpsAnimationView udfpsAnimationView, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager) {
        super(udfpsAnimationView);
        this.statusBarStateController = statusBarStateController;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.dialogManager = systemUIDialogManager;
        this.dumpManager = dumpManager;
        this.dialogListener = new UdfpsAnimationViewController$dialogListener$1(this);
        this.shadeExpansionListener = new UdfpsAnimationViewController$shadeExpansionListener$1(this, udfpsAnimationView);
        this.touchTranslation = new PointF(0.0f, 0.0f);
        this.dumpTag = getTag() + " (" + this + ")";
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("mNotificationShadeVisible=" + this.notificationShadeVisible);
        printWriter.println("shouldPauseAuth()=" + shouldPauseAuth());
        printWriter.println("isPauseAuth=" + getView().mPauseAuth);
        printWriter.println("dialogSuggestedAlpha=" + getView().mDialogSuggestedAlpha);
    }

    public abstract String getTag();

    public final UdfpsAnimationView getView() {
        View view = this.mView;
        Intrinsics.checkNotNull(view);
        return (UdfpsAnimationView) view;
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        ShadeExpansionStateManager shadeExpansionStateManager = this.shadeExpansionStateManager;
        UdfpsAnimationViewController$shadeExpansionListener$1 udfpsAnimationViewController$shadeExpansionListener$1 = this.shadeExpansionListener;
        udfpsAnimationViewController$shadeExpansionListener$1.onPanelExpansionChanged(shadeExpansionStateManager.addExpansionListener(udfpsAnimationViewController$shadeExpansionListener$1));
        ((HashSet) this.dialogManager.mListeners).add(this.dialogListener);
        DumpManager.registerDumpable$default(this.dumpManager, this.dumpTag, this);
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        this.shadeExpansionStateManager.expansionListeners.remove(this.shadeExpansionListener);
        ((HashSet) this.dialogManager.mListeners).remove(this.dialogListener);
        this.dumpManager.unregisterDumpable(this.dumpTag);
    }

    public boolean shouldPauseAuth() {
        return this.notificationShadeVisible || this.dialogManager.shouldHideAffordance();
    }

    public void updateAlpha() {
        getView().updateAlpha();
    }

    public final void updatePauseAuth() {
        boolean z;
        UdfpsAnimationView view = getView();
        boolean shouldPauseAuth = shouldPauseAuth();
        if (shouldPauseAuth != view.mPauseAuth) {
            view.mPauseAuth = shouldPauseAuth;
            view.updateAlpha();
            z = true;
        } else {
            z = false;
        }
        if (z) {
            getView().postInvalidate();
        }
    }
}
