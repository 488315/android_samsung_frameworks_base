package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.accessibility.fontscaling.FontScalingDialogDelegate;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FontScalingTile extends QSTileImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final Provider fontScalingDialogDelegateProvider;
    public final QSTile.Icon icon;
    public final KeyguardStateController keyguardStateController;
    public final Handler mainHandler;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public FontScalingTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, KeyguardStateController keyguardStateController, DialogTransitionAnimator dialogTransitionAnimator, Provider provider) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mainHandler = handler;
        this.keyguardStateController = keyguardStateController;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.fontScalingDialogDelegateProvider = provider;
        this.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_font_scaling);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.TEXT_READING_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_font_scaling_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final Expandable expandable) {
        final boolean z = (expandable == null || ((KeyguardStateControllerImpl) this.keyguardStateController).mShowing) ? false : true;
        final Runnable runnable = new Runnable() { // from class: com.android.systemui.qs.tiles.FontScalingTile$handleClick$runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                SystemUIDialog createDialog = ((FontScalingDialogDelegate) FontScalingTile.this.fontScalingDialogDelegateProvider.get()).createDialog();
                if (!z) {
                    createDialog.show();
                    return;
                }
                Expandable expandable2 = expandable;
                Unit unit = null;
                DialogTransitionAnimator.Controller dialogTransitionController = expandable2 != null ? expandable2.dialogTransitionController(new DialogCuj(58, "font_scaling")) : null;
                if (dialogTransitionController != null) {
                    DialogTransitionAnimator dialogTransitionAnimator = FontScalingTile.this.dialogTransitionAnimator;
                    TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
                    dialogTransitionAnimator.show(createDialog, dialogTransitionController, false);
                    unit = Unit.INSTANCE;
                }
                if (unit == null) {
                    createDialog.show();
                }
            }
        };
        this.mainHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.FontScalingTile$handleClick$1
            @Override // java.lang.Runnable
            public final void run() {
                FontScalingTile fontScalingTile = FontScalingTile.this;
                int i = FontScalingTile.$r8$clinit;
                fontScalingTile.mActivityStarter.executeRunnableDismissingKeyguard(runnable, null, true, true, false);
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        if (state != null) {
            state.label = this.mContext.getString(R.string.quick_settings_font_scaling_label);
        }
        if (state == null) {
            return;
        }
        state.icon = this.icon;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.State();
    }
}
