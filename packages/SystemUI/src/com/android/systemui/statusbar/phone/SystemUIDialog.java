package com.android.systemui.statusbar.phone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.DialogKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SystemUIDialog extends AlertDialog implements ViewRootImpl.ConfigChangedCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final DialogDelegate mDelegate;
    public final SystemUIDialogManager mDialogManager;
    public final DismissReceiver mDismissReceiver;
    public final Handler mHandler;
    public int mLastConfigurationHeightDp;
    public int mLastConfigurationWidthDp;
    public int mLastHeight;
    public int mLastWidth;
    public final Handler mMainHandler;
    public final List mOnCreateRunnables;
    public final SysUiState mSysUiState;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Delegate extends DialogDelegate {
        SystemUIDialog createDialog();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DismissReceiver extends BroadcastReceiver {
        public final BroadcastDispatcher mBroadcastDispatcher;
        public final Dialog mDialog;
        public final DialogTransitionAnimator mDialogTransitionAnimator;
        public final IntentFilter mIntentFilter;
        public boolean mRegistered;

        public DismissReceiver(Dialog dialog, BroadcastDispatcher broadcastDispatcher, DialogTransitionAnimator dialogTransitionAnimator, boolean z) {
            IntentFilter intentFilter = new IntentFilter();
            this.mIntentFilter = intentFilter;
            this.mDialog = dialog;
            this.mBroadcastDispatcher = broadcastDispatcher;
            this.mDialogTransitionAnimator = dialogTransitionAnimator;
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            if (z) {
                intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
            }
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            this.mDialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
            this.mDialog.dismiss();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Factory {
        public final BroadcastDispatcher mBroadcastDispatcher;
        public final Context mContext;
        public final DialogTransitionAnimator mDialogTransitionAnimator;
        public final SysUiState mSysUiState;
        public final SystemUIDialogManager mSystemUIDialogManager;

        public Factory(Context context, SystemUIDialogManager systemUIDialogManager, SysUiState sysUiState, BroadcastDispatcher broadcastDispatcher, DialogTransitionAnimator dialogTransitionAnimator) {
            this.mContext = context;
            this.mSystemUIDialogManager = systemUIDialogManager;
            this.mSysUiState = sysUiState;
            this.mBroadcastDispatcher = broadcastDispatcher;
            this.mDialogTransitionAnimator = dialogTransitionAnimator;
        }

        public final SystemUIDialog create() {
            DialogDelegate dialogDelegate = new DialogDelegate(this) { // from class: com.android.systemui.statusbar.phone.SystemUIDialog.Factory.1
            };
            Context context = this.mContext;
            int i = SystemUIDialog.$r8$clinit;
            return create(dialogDelegate, context, R.style.Theme_SystemUI_Dialog, true);
        }

        public final SystemUIDialog create(Context context) {
            DialogDelegate dialogDelegate = new DialogDelegate(this) { // from class: com.android.systemui.statusbar.phone.SystemUIDialog.Factory.2
            };
            int i = SystemUIDialog.$r8$clinit;
            return create(dialogDelegate, context, R.style.Theme_SystemUI_Dialog, true);
        }

        public final SystemUIDialog create(Delegate delegate, Context context) {
            int i = SystemUIDialog.$r8$clinit;
            return create(delegate, context, R.style.Theme_SystemUI_Dialog, true);
        }

        public final SystemUIDialog create(DialogDelegate dialogDelegate, Context context, int i, boolean z) {
            return new SystemUIDialog(context, i, true, this.mSystemUIDialogManager, this.mSysUiState, this.mBroadcastDispatcher, this.mDialogTransitionAnimator, dialogDelegate, z);
        }
    }

    @Deprecated
    public SystemUIDialog(Context context) {
        this(context, R.style.Theme_SystemUI_Dialog, true);
    }

    public static void applyFlags(AlertDialog alertDialog, boolean z) {
        Window window = alertDialog.getWindow();
        window.setType(2017);
        window.addFlags(131072);
        if (z) {
            window.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        }
        window.getAttributes().setFitInsetsTypes(window.getAttributes().getFitInsetsTypes() & (~WindowInsets.Type.statusBars()));
    }

    public static int calculateDialogWidthWithInsets(Dialog dialog, int i) {
        return Math.round(TypedValue.applyDimension(1, i, dialog.getContext().getResources().getDisplayMetrics()) + getHorizontalInsets(dialog));
    }

    public static int getDefaultDialogWidth(Dialog dialog) {
        Context context = dialog.getContext();
        int i = SystemProperties.getInt("persist.systemui.flag_tablet_dialog_width", 0);
        if (i == -1) {
            return calculateDialogWidthWithInsets(dialog, 624);
        }
        if (i == -2) {
            return calculateDialogWidthWithInsets(dialog, 348);
        }
        if (i > 0) {
            return calculateDialogWidthWithInsets(dialog, i);
        }
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.sec_large_dialog_width);
        return dimensionPixelSize > 0 ? getHorizontalInsets(dialog) + dimensionPixelSize : dimensionPixelSize;
    }

    public static int getHorizontalInsets(Dialog dialog) {
        View decorView = dialog.getWindow().getDecorView();
        if (decorView == null) {
            return 0;
        }
        View findViewByPredicate = decorView.findViewByPredicate(new SystemUIDialog$$ExternalSyntheticLambda6());
        Drawable background = findViewByPredicate != null ? findViewByPredicate.getBackground() : decorView.getBackground();
        Insets opticalInsets = background != null ? background.getOpticalInsets() : Insets.NONE;
        return opticalInsets.left + opticalInsets.right;
    }

    public static void registerDismissListener(Dialog dialog) {
        final DismissReceiver dismissReceiver = new DismissReceiver(dialog, (BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class), (DialogTransitionAnimator) Dependency.sDependency.getDependencyInner(DialogTransitionAnimator.class), true);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.statusbar.phone.SystemUIDialog$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                SystemUIDialog.DismissReceiver dismissReceiver2 = SystemUIDialog.DismissReceiver.this;
                int i = SystemUIDialog.$r8$clinit;
                if (dismissReceiver2.mRegistered) {
                    dismissReceiver2.mBroadcastDispatcher.unregisterReceiver(dismissReceiver2);
                    dismissReceiver2.mRegistered = false;
                }
            }
        });
        dismissReceiver.mBroadcastDispatcher.registerReceiver(dismissReceiver, dismissReceiver.mIntentFilter, null, UserHandle.CURRENT);
        dismissReceiver.mRegistered = true;
    }

    public static void setDialogSize(Dialog dialog) {
        dialog.create();
        dialog.getWindow().setLayout(getDefaultDialogWidth(dialog), -2);
    }

    public static void setShowForAllUsers(Dialog dialog) {
        dialog.getWindow().getAttributes().privateFlags |= 16;
    }

    public static void setWindowOnTop(Dialog dialog, boolean z) {
        Window window = dialog.getWindow();
        window.setType(2017);
        if (z) {
            window.getAttributes().setFitInsetsTypes(window.getAttributes().getFitInsetsTypes() & (~WindowInsets.Type.statusBars()));
        }
    }

    public int getHeight() {
        return this.mDelegate.getHeight(this);
    }

    public int getWidth() {
        return this.mDelegate.getWidth(this);
    }

    public void onConfigurationChanged(Configuration configuration) {
        int i = this.mLastConfigurationWidthDp;
        int i2 = configuration.screenWidthDp;
        if (i != i2 || this.mLastConfigurationHeightDp != configuration.screenHeightDp) {
            this.mLastConfigurationWidthDp = i2;
            this.mLastConfigurationHeightDp = configuration.compatScreenWidthDp;
            updateWindowSize();
        }
        this.mDelegate.onConfigurationChanged(this, configuration);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.statusbar.phone.SystemUIDialog$$ExternalSyntheticLambda2] */
    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle bundle) {
        this.mDelegate.beforeCreate(this);
        super.onCreate(bundle);
        this.mDelegate.onCreate(this, bundle);
        Configuration configuration = getContext().getResources().getConfiguration();
        this.mLastConfigurationWidthDp = configuration.screenWidthDp;
        this.mLastConfigurationHeightDp = configuration.screenHeightDp;
        updateWindowSize();
        for (int i = 0; i < ((ArrayList) this.mOnCreateRunnables).size(); i++) {
            ((Runnable) ((ArrayList) this.mOnCreateRunnables).get(i)).run();
        }
        final View decorView = getWindow().getDecorView();
        DialogKt.registerAnimationOnBackInvoked(this, decorView, this.mDelegate.getBackAnimationSpec(new Function0() { // from class: com.android.systemui.statusbar.phone.SystemUIDialog$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                View view = decorView;
                int i2 = SystemUIDialog.$r8$clinit;
                return view.getResources().getDisplayMetrics();
            }
        }));
    }

    @Override // android.app.Dialog
    public final void onStart() {
        super.onStart();
        DismissReceiver dismissReceiver = this.mDismissReceiver;
        if (dismissReceiver != null) {
            dismissReceiver.mBroadcastDispatcher.registerReceiver(dismissReceiver, dismissReceiver.mIntentFilter, null, UserHandle.CURRENT);
            dismissReceiver.mRegistered = true;
        }
        ViewRootImpl.addConfigCallback(this);
        this.mDialogManager.setShowing(this, true);
        this.mMainHandler.post(new SystemUIDialog$$ExternalSyntheticLambda0(this, 1));
        this.mDelegate.onStart(this);
        start();
    }

    @Override // android.app.Dialog
    public final void onStop() {
        super.onStop();
        DismissReceiver dismissReceiver = this.mDismissReceiver;
        if (dismissReceiver != null && dismissReceiver.mRegistered) {
            dismissReceiver.mBroadcastDispatcher.unregisterReceiver(dismissReceiver);
            dismissReceiver.mRegistered = false;
        }
        ViewRootImpl.removeConfigCallback(this);
        this.mDialogManager.setShowing(this, false);
        this.mMainHandler.post(new SystemUIDialog$$ExternalSyntheticLambda0(this, 2));
        this.mDelegate.onStop(this);
        stop();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.mDelegate.onWindowFocusChanged(this, z);
        if (z) {
            SysUiState flag = this.mSysUiState.setFlag(32768L, true);
            this.mContext.getDisplayId();
            ((SysUiStateImpl) flag).commitUpdate();
        }
    }

    public final void sendAnnouncementEvent(String str) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
        if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
            return;
        }
        AccessibilityEvent obtain = AccessibilityEvent.obtain(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
        obtain.getText().clear();
        obtain.getText().add(str);
        obtain.setPackageName(this.mContext.getPackageName());
        accessibilityManager.sendAccessibilityEvent(obtain);
    }

    public final void setButton(final int i, int i2, final DialogInterface.OnClickListener onClickListener, boolean z) {
        if (z) {
            setButton(i, this.mContext.getString(i2), onClickListener);
            return;
        }
        setButton(i, this.mContext.getString(i2), (DialogInterface.OnClickListener) null);
        ((ArrayList) this.mOnCreateRunnables).add(new Runnable() { // from class: com.android.systemui.statusbar.phone.SystemUIDialog$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                final SystemUIDialog systemUIDialog = this;
                final int i3 = i;
                final DialogInterface.OnClickListener onClickListener2 = onClickListener;
                int i4 = SystemUIDialog.$r8$clinit;
                systemUIDialog.getButton(i3).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.SystemUIDialog$$ExternalSyntheticLambda7
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SystemUIDialog systemUIDialog2 = systemUIDialog;
                        DialogInterface.OnClickListener onClickListener3 = onClickListener2;
                        int i5 = i3;
                        int i6 = SystemUIDialog.$r8$clinit;
                        systemUIDialog2.getClass();
                        onClickListener3.onClick(systemUIDialog2, i5);
                    }
                });
            }
        });
    }

    public final void setMessage(int i) {
        setMessage(this.mContext.getString(i));
    }

    public final void setNegativeButton(int i, DialogInterface.OnClickListener onClickListener) {
        setButton(-2, i, onClickListener, true);
    }

    public final void setPositiveButton(int i, DialogInterface.OnClickListener onClickListener) {
        setButton(-1, i, onClickListener, true);
    }

    public final void updateWindowSize() {
        if (Looper.myLooper() != this.mHandler.getLooper()) {
            this.mHandler.post(new SystemUIDialog$$ExternalSyntheticLambda0(this, 0));
            return;
        }
        int width = getWidth();
        int height = getHeight();
        if (width == this.mLastWidth && height == this.mLastHeight) {
            return;
        }
        this.mLastWidth = width;
        this.mLastHeight = height;
        getWindow().setLayout(width, height);
    }

    public SystemUIDialog(Context context, int i) {
        this(context, i, true);
    }

    public SystemUIDialog(Context context, int i, boolean z, SystemUIDialogManager systemUIDialogManager, SysUiState sysUiState, BroadcastDispatcher broadcastDispatcher, DialogTransitionAnimator dialogTransitionAnimator) {
        this(context, i, z, systemUIDialogManager, sysUiState, broadcastDispatcher, dialogTransitionAnimator, new DialogDelegate() { // from class: com.android.systemui.statusbar.phone.SystemUIDialog.1
        }, true);
    }

    public SystemUIDialog(Context context, int i, boolean z, SystemUIDialogManager systemUIDialogManager, SysUiState sysUiState, BroadcastDispatcher broadcastDispatcher, DialogTransitionAnimator dialogTransitionAnimator, Delegate delegate) {
        this(context, i, z, systemUIDialogManager, sysUiState, broadcastDispatcher, dialogTransitionAnimator, delegate, true);
    }

    public SystemUIDialog(Context context, int i, boolean z, SystemUIDialogManager systemUIDialogManager, SysUiState sysUiState, BroadcastDispatcher broadcastDispatcher, DialogTransitionAnimator dialogTransitionAnimator, DialogDelegate dialogDelegate, boolean z2) {
        super(context, i);
        this.mHandler = new Handler();
        this.mLastWidth = Integer.MIN_VALUE;
        this.mLastHeight = Integer.MIN_VALUE;
        this.mLastConfigurationWidthDp = -1;
        this.mLastConfigurationHeightDp = -1;
        this.mOnCreateRunnables = new ArrayList();
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mContext = context;
        this.mDelegate = dialogDelegate;
        applyFlags(this, true);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.setTitle(getClass().getSimpleName());
        getWindow().setAttributes(attributes);
        this.mDismissReceiver = z ? new DismissReceiver(this, broadcastDispatcher, dialogTransitionAnimator, z2) : null;
        this.mDialogManager = systemUIDialogManager;
        this.mSysUiState = sysUiState;
        semSetBackgroundBlurEnabled(true);
    }

    public SystemUIDialog(Context context, int i, boolean z) {
        this(context, i, z, (SystemUIDialogManager) Dependency.sDependency.getDependencyInner(SystemUIDialogManager.class), (SysUiState) Dependency.sDependency.getDependencyInner(SysUiState.class), (BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class), (DialogTransitionAnimator) Dependency.sDependency.getDependencyInner(DialogTransitionAnimator.class));
    }

    public void start() {
    }

    public void stop() {
    }
}
