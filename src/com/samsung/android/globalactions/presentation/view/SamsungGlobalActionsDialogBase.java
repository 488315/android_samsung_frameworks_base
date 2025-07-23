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
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public abstract class SamsungGlobalActionsDialogBase implements ExtendableGlobalActionsView, ViewStateController {
    private static final String TAG = "SamsungGlobalActionsDialogBase";
    protected ConditionChecker mConditionChecker;
    private ContentView mContentView;
    protected ContentViewFactoryBase mContentViewFactory;
    protected final Context mContext;
    private boolean mCoverSecureConfirmState;
    protected Dialog mDialog;
    protected int mDialogStyle;
    protected final IDreamManager mDreamManager;
    protected FeatureFactory mFeatureFactory;
    protected boolean mFromSystemServer;
    protected HandlerUtil mHandlerUtil;
    protected LogWrapper mLogWrapper;
    protected SamsungGlobalActionsPresenter mPresenter;
    protected ResourceFactory mResourceFactory;
    protected final Resources mResources;
    protected IStatusBarService mStatusBarService;
    protected ToastController mToastController;
    private ViewAnimationState mViewAnimationState;
    private ViewStateController mViewStateController;
    protected List<WindowDecorationStrategy> mWindowDecorationStrategies;
    protected WindowManagerUtils mWindowManagerUtil;
    protected final float DEFAULT_DIM_AMOUNT = 1.0f;
    protected IBinder mToken = new Binder();

    public SamsungGlobalActionsDialogBase(Context context, ResourceFactory resourceFactory) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 16974123);
        this.mContext = contextThemeWrapper;
        this.mResourceFactory = resourceFactory;
        this.mResources = contextThemeWrapper.getResources();
        this.mDreamManager = IDreamManager.Stub.asInterface(ServiceManager.getService(DreamService.DREAM_SERVICE));
        this.mWindowDecorationStrategies = new ArrayList();
        this.mDialogStyle = 0;
        this.mViewAnimationState = ViewAnimationState.IDLE;
        this.mViewStateController = this;
        this.mCoverSecureConfirmState = false;
        this.mStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService(Context.STATUS_BAR_SERVICE));
    }

    @Override // com.samsung.android.globalactions.presentation.view.ViewStateController
    public ViewAnimationState getState() {
        return this.mViewAnimationState;
    }

    @Override // com.samsung.android.globalactions.presentation.view.ViewStateController
    public void setState(ViewAnimationState viewAnimationState) {
        this.mViewAnimationState = viewAnimationState;
    }

    private void awakenIfNecessary() {
        IDreamManager iDreamManager = this.mDreamManager;
        if (iDreamManager != null) {
            try {
                if (iDreamManager.isDreaming()) {
                    this.mDreamManager.awaken();
                }
            } catch (RemoteException unused) {
            }
        }
    }

    public void show(final boolean z, final boolean z2, final boolean z3, final int i) {
        this.mFromSystemServer = z3;
        this.mHandlerUtil.post(new Runnable() { // from class: com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsDialogBase$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SamsungGlobalActionsDialogBase.this.lambda$show$0(z, z2, z3, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$0(boolean z, boolean z2, boolean z3, int i) {
        if (this.mPresenter.onStart(z, z2, z3, i)) {
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
        this.mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsDialogBase$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnKeyListener
            public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                boolean lambda$showDialog$1;
                lambda$showDialog$1 = SamsungGlobalActionsDialogBase.this.lambda$showDialog$1(dialogInterface, i, keyEvent);
                return lambda$showDialog$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDialog$1(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        return this.mPresenter.createOnKeyListenerActions(keyEvent, i);
    }

    private void prepareWindow() {
        Window window = this.mDialog.getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.setTitle(this.mResources.getString(R.string.global_actions));
        attributes.hasManualSurfaceInsets = true;
        attributes.privateFlags |= 2;
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_SUPPORT_SF_EFFECT) && (this.mConditionChecker.isEnabled(SystemConditions.IS_CLEAR_SIDE_VIEW_COVER_CLOSED) || this.mConditionChecker.isEnabled(SystemConditions.IS_MINI_SVIEW_COVER_CLOSED))) {
            attributes.flags |= 2;
            attributes.dimAmount = 1.0f;
        } else {
            attributes.flags &= -3;
        }
        attributes.flags |= 131072;
        attributes.flags |= 16777216;
        attributes.flags |= 1024;
        attributes.flags |= 256;
        attributes.gravity = 17;
        attributes.layoutInDisplayCutoutMode = 1;
        window.setLayout(-1, -1);
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setWindowAnimations(0);
        window.setAttributes(attributes);
        window.setType(WindowManager.LayoutParams.TYPE_GLOBAL_ACTION);
        this.mWindowDecorationStrategies.clear();
        this.mPresenter.onPrepareWindow();
        window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility() | 66048);
        Iterator<WindowDecorationStrategy> it = this.mWindowDecorationStrategies.iterator();
        while (it.hasNext()) {
            it.next().onDecorateWindow(window);
        }
    }

    private void setRotationSuggestionsEnabled(boolean z) {
        try {
            this.mStatusBarService.disable2ForUser(z ? 0 : 16, this.mToken, this.mContext.getPackageName(), Binder.getCallingUserHandle().semGetIdentifier());
        } catch (RemoteException e) {
            this.mLogWrapper.e(TAG, "RemoteException occured while setRotationSuggestionsEnabled " + z);
            e.rethrowFromSystemServer();
        }
    }

    @Override // com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView
    public void updateViewList() {
        this.mContentView.updateItemLists(this.mPresenter);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView
    public void addWindowDecorator(WindowDecorationStrategy windowDecorationStrategy) {
        this.mWindowDecorationStrategies.add(windowDecorationStrategy);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView
    public void setCoverSecureConfirmState(boolean z) {
        this.mCoverSecureConfirmState = z;
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
            this.mHandlerUtil.post(new Runnable() { // from class: com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsDialogBase$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SamsungGlobalActionsDialogBase.this.lambda$dismiss$2();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dismiss$2() {
        setRotationSuggestionsEnabled(true);
        ContentView contentView = this.mContentView;
        if (contentView == null || this.mDialog == null || contentView.getAnimationState() != ViewAnimationState.IDLE) {
            return;
        }
        this.mLogWrapper.i(TAG, "dismiss()");
        this.mDialog.dismiss();
        this.mDialog = null;
        this.mContentView.onDismiss();
        this.mPresenter.onDismiss();
        this.mContentView = null;
    }

    @Override // com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView
    public void dismissWithAnimation() {
        ContentView contentView = this.mContentView;
        if (contentView != null) {
            contentView.dismiss();
        }
    }

    @Override // com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView
    public void showActionConfirming(ActionViewModel actionViewModel) {
        this.mContentView.showConfirm(actionViewModel);
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

        public ActionsDialog(Context context, int i) {
            super(context, i);
            setCanceledOnTouchOutside(true);
        }

        @Override // android.app.Dialog, android.content.DialogInterface
        public void cancel() {
            if (SamsungGlobalActionsDialogBase.this.mContentView != null && SamsungGlobalActionsDialogBase.this.mContentView.getAnimationState() == ViewAnimationState.IDLE) {
                SamsungGlobalActionsDialogBase.this.mPresenter.onCancelDialog();
            }
        }
    }
}
