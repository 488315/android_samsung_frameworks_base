package com.samsung.android.globalactions.presentation.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.dreams.DreamService;
import android.service.dreams.IDreamManager;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.android.internal.R;
import com.android.internal.statusbar.IStatusBarService;

import com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter;
import com.samsung.android.globalactions.presentation.features.FeatureFactory;
import com.samsung.android.globalactions.presentation.strategies.WindowDecorationStrategy;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.HandlerUtil;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.SystemConditions;
import com.samsung.android.globalactions.util.ToastController;
import com.samsung.android.globalactions.util.WindowManagerUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class SamsungGlobalActionsDialogBase
        implements ExtendableGlobalActionsView, ViewStateController {
    private static final String TAG = "SamsungGlobalActionsDialogBase";
    protected ConditionChecker mConditionChecker;
    private ContentView mContentView;
    protected ContentViewFactoryBase mContentViewFactory;
    protected final Context mContext;
    protected Dialog mDialog;
    protected FeatureFactory mFeatureFactory;
    protected boolean mFromSystemServer;
    protected HandlerUtil mHandlerUtil;
    protected LogWrapper mLogWrapper;
    protected SamsungGlobalActionsPresenter mPresenter;
    protected ResourceFactory mResourceFactory;
    protected final Resources mResources;
    protected ToastController mToastController;
    protected WindowManagerUtils mWindowManagerUtil;
    protected final float DEFAULT_DIM_AMOUNT = 1.0f;
    protected IBinder mToken = new Binder();
    protected final IDreamManager mDreamManager =
            IDreamManager.Stub.asInterface(ServiceManager.getService(DreamService.DREAM_SERVICE));
    protected List<WindowDecorationStrategy> mWindowDecorationStrategies = new ArrayList();
    protected int mDialogStyle = 0;
    private ViewAnimationState mViewAnimationState = ViewAnimationState.IDLE;
    private ViewStateController mViewStateController = this;
    private boolean mCoverSecureConfirmState = false;
    protected IStatusBarService mStatusBarService =
            IStatusBarService.Stub.asInterface(
                    ServiceManager.getService(Context.STATUS_BAR_SERVICE));

    public SamsungGlobalActionsDialogBase(Context context, ResourceFactory resourceFactory) {
        this.mContext = new ContextThemeWrapper(context, 16974123);
        this.mResourceFactory = resourceFactory;
        this.mResources = this.mContext.getResources();
    }

    @Override // com.samsung.android.globalactions.presentation.view.ViewStateController
    public ViewAnimationState getState() {
        return this.mViewAnimationState;
    }

    @Override // com.samsung.android.globalactions.presentation.view.ViewStateController
    public void setState(ViewAnimationState state) {
        this.mViewAnimationState = state;
    }

    private void awakenIfNecessary() {
        if (this.mDreamManager != null) {
            try {
                if (this.mDreamManager.isDreaming()) {
                    this.mDreamManager.awaken();
                }
            } catch (RemoteException e) {
            }
        }
    }

    public void show(
            final boolean keyguardShowing,
            final boolean deviceProvisioned,
            final boolean fromSystemServer,
            final int sideKeyType) {
        this.mFromSystemServer = fromSystemServer;
        this.mHandlerUtil.post(
                new Runnable() { // from class:
                                 // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsDialogBase$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        SamsungGlobalActionsDialogBase.this.lambda$show$0(
                                keyguardShowing, deviceProvisioned, fromSystemServer, sideKeyType);
                    }
                });
    }

    public /* synthetic */ void lambda$show$0(
            boolean keyguardShowing,
            boolean deviceProvisioned,
            boolean fromSystemServer,
            int sideKeyType) {
        if (this.mPresenter.onStart(
                keyguardShowing, deviceProvisioned, fromSystemServer, sideKeyType)) {
            showDialog();
        }
    }

    protected ContentView createContentView(Dialog dialog) {
        return this.mContentViewFactory.createContentView(dialog);
    }

    protected void showDialog() {
        this.mLogWrapper.i(TAG, "showDialog()");
        awakenIfNecessary();
        if (this.mDialogStyle != 0) {
            this.mDialog = new ActionsDialog(this.mContext, this.mDialogStyle);
        } else {
            this.mDialog = new ActionsDialog(this.mContext);
        }
        this.mContentView = createContentView(this.mDialog);
        setRotationSuggestionsEnabled(false);
        this.mContentView.initDimens();
        this.mContentView.initLayouts();
        prepareWindow();
        updateViewList();
        this.mContentView.initAnimations();
        this.mContentView.registerRotationWatcher();
        this.mContentView.setInterceptor();
        this.mContentView.show();
        this.mPresenter.onShowDialog();
        this.mDialog.setOnKeyListener(
                new DialogInterface
                        .OnKeyListener() { // from class:
                                           // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsDialogBase$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnKeyListener
                    public final boolean onKey(
                            DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        boolean lambda$showDialog$1;
                        lambda$showDialog$1 =
                                SamsungGlobalActionsDialogBase.this.lambda$showDialog$1(
                                        dialogInterface, i, keyEvent);
                        return lambda$showDialog$1;
                    }
                });
    }

    public /* synthetic */ boolean lambda$showDialog$1(
            DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        return this.mPresenter.createOnKeyListenerActions(keyEvent, i);
    }

    private void prepareWindow() {
        Window window = this.mDialog.getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams attrs = window.getAttributes();
        attrs.setTitle(this.mResources.getString(R.string.global_actions));
        attrs.hasManualSurfaceInsets = true;
        attrs.privateFlags |= 2;
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_SUPPORT_SF_EFFECT)
                && (this.mConditionChecker.isEnabled(
                                SystemConditions.IS_CLEAR_SIDE_VIEW_COVER_CLOSED)
                        || this.mConditionChecker.isEnabled(
                                SystemConditions.IS_MINI_SVIEW_COVER_CLOSED))) {
            attrs.flags |= 2;
            attrs.dimAmount = 1.0f;
        } else {
            attrs.flags &= -3;
        }
        attrs.flags |= 131072;
        attrs.flags |= 16777216;
        attrs.flags |= 1024;
        attrs.flags |= 256;
        attrs.gravity = 17;
        attrs.layoutInDisplayCutoutMode = 1;
        window.setLayout(-1, -1);
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setWindowAnimations(0);
        window.setAttributes(attrs);
        window.setType(WindowManager.LayoutParams.TYPE_GLOBAL_ACTION);
        this.mWindowDecorationStrategies.clear();
        this.mPresenter.onPrepareWindow();
        int systemUIflags = window.getDecorView().getSystemUiVisibility();
        window.getDecorView().setSystemUiVisibility(systemUIflags | 65536 | 512);
        for (WindowDecorationStrategy decorator : this.mWindowDecorationStrategies) {
            decorator.onDecorateWindow(window);
        }
    }

    private void setRotationSuggestionsEnabled(boolean enabled) {
        try {
            int userId = Binder.getCallingUserHandle().semGetIdentifier();
            int what = enabled ? 0 : 16;
            this.mStatusBarService.disable2ForUser(
                    what, this.mToken, this.mContext.getPackageName(), userId);
        } catch (RemoteException re) {
            this.mLogWrapper.e(
                    TAG, "RemoteException occured while setRotationSuggestionsEnabled " + enabled);
            re.rethrowFromSystemServer();
        }
    }

    @Override // com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView
    public void updateViewList() {
        this.mContentView.updateItemLists(this.mPresenter);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView
    public void addWindowDecorator(WindowDecorationStrategy strategy) {
        this.mWindowDecorationStrategies.add(strategy);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView
    public void setCoverSecureConfirmState(boolean state) {
        this.mCoverSecureConfirmState = state;
    }

    @Override // com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView
    public boolean getCoverSecureConfirmState() {
        return this.mCoverSecureConfirmState;
    }

    @Override // com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView
    public void dismiss() {
        if (this.mCoverSecureConfirmState) {
            this.mCoverSecureConfirmState = false;
            this.mDialog.hide();
        } else {
            this.mHandlerUtil.post(
                    new Runnable() { // from class:
                                     // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsDialogBase$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SamsungGlobalActionsDialogBase.this.lambda$dismiss$2();
                        }
                    });
        }
    }

    public /* synthetic */ void lambda$dismiss$2() {
        setRotationSuggestionsEnabled(true);
        if (this.mContentView != null
                && this.mDialog != null
                && this.mContentView.getAnimationState() == ViewAnimationState.IDLE) {
            this.mLogWrapper.i(TAG, "dismiss()");
            this.mDialog.dismiss();
            this.mDialog = null;
            this.mContentView.onDismiss();
            this.mPresenter.onDismiss();
            this.mContentView = null;
        }
    }

    @Override // com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView
    public void dismissWithAnimation() {
        if (this.mContentView != null) {
            this.mContentView.dismiss();
        }
    }

    @Override // com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView
    public void showActionConfirming(ActionViewModel viewModel) {
        this.mContentView.showConfirm(viewModel);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView
    public void hideDialogOnSecureConfirm() {
        this.mContentView.hideDialogOnSecureConfirm();
    }

    @Override // com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView
    public void cancelConfirming() {
        this.mContentView.hideConfirm();
    }

    @Override // com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView
    public void forceRequestLayout() {
        this.mContentView.forceRequestLayout();
    }

    @Override // com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView
    public void notifyDataSetChanged() {
        this.mContentView.notifyDataSetChanged();
    }

    class ActionsDialog extends Dialog implements DialogInterface {
        public ActionsDialog(Context context) {
            super(context);
            setCanceledOnTouchOutside(true);
        }

        public ActionsDialog(Context context, int themeResId) {
            super(context, themeResId);
            setCanceledOnTouchOutside(true);
        }

        @Override // android.app.Dialog, android.content.DialogInterface
        public void cancel() {
            if (SamsungGlobalActionsDialogBase.this.mContentView != null
                    && SamsungGlobalActionsDialogBase.this.mContentView.getAnimationState()
                            == ViewAnimationState.IDLE) {
                SamsungGlobalActionsDialogBase.this.mPresenter.onCancelDialog();
            }
        }
    }
}
