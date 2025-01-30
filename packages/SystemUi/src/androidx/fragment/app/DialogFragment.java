package androidx.fragment.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.activity.ComponentDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager.PopBackStackState;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class DialogFragment extends Fragment implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {
    public int mBackStackId;
    public boolean mCancelable;
    public boolean mCreatingDialog;
    public Dialog mDialog;
    public boolean mDialogCreated;
    public final RunnableC02191 mDismissRunnable;
    public boolean mDismissed;
    public Handler mHandler;
    public final C02224 mObserver;
    public final DialogInterfaceOnCancelListenerC02202 mOnCancelListener;
    public final DialogInterfaceOnDismissListenerC02213 mOnDismissListener;
    public boolean mShownByMe;
    public boolean mShowsDialog;
    public int mStyle;
    public int mTheme;
    public boolean mViewDestroyed;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.fragment.app.DialogFragment$3 */
    public final class DialogInterfaceOnDismissListenerC02213 implements DialogInterface.OnDismissListener {
        public DialogInterfaceOnDismissListenerC02213() {
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public final void onDismiss(DialogInterface dialogInterface) {
            DialogFragment dialogFragment = DialogFragment.this;
            Dialog dialog = dialogFragment.mDialog;
            if (dialog != null) {
                dialogFragment.onDismiss(dialog);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.fragment.app.DialogFragment$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.fragment.app.DialogFragment$2] */
    /* JADX WARN: Type inference failed for: r1v2, types: [androidx.fragment.app.DialogFragment$4] */
    public DialogFragment() {
        this.mDismissRunnable = new Runnable() { // from class: androidx.fragment.app.DialogFragment.1
            @Override // java.lang.Runnable
            public final void run() {
                DialogFragment dialogFragment = DialogFragment.this;
                dialogFragment.mOnDismissListener.onDismiss(dialogFragment.mDialog);
            }
        };
        this.mOnCancelListener = new DialogInterface.OnCancelListener() { // from class: androidx.fragment.app.DialogFragment.2
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                DialogFragment dialogFragment = DialogFragment.this;
                Dialog dialog = dialogFragment.mDialog;
                if (dialog != null) {
                    dialogFragment.onCancel(dialog);
                }
            }
        };
        this.mOnDismissListener = new DialogInterfaceOnDismissListenerC02213();
        this.mStyle = 0;
        this.mTheme = 0;
        this.mCancelable = true;
        this.mShowsDialog = true;
        this.mBackStackId = -1;
        this.mObserver = new Observer() { // from class: androidx.fragment.app.DialogFragment.4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                if (((LifecycleOwner) obj) != null) {
                    DialogFragment dialogFragment = DialogFragment.this;
                    if (dialogFragment.mShowsDialog) {
                        View requireView = dialogFragment.requireView();
                        if (requireView.getParent() != null) {
                            throw new IllegalStateException("DialogFragment can not be attached to a container view");
                        }
                        if (dialogFragment.mDialog != null) {
                            if (FragmentManager.isLoggingEnabled(3)) {
                                Log.d("SeslDialogFragment", "DialogFragment " + this + " setting the content view on " + dialogFragment.mDialog);
                            }
                            dialogFragment.mDialog.setContentView(requireView);
                        }
                    }
                }
            }
        };
        this.mDialogCreated = false;
    }

    @Override // androidx.fragment.app.Fragment
    public final FragmentContainer createFragmentContainer() {
        final Fragment.C02265 c02265 = new Fragment.C02265();
        return new FragmentContainer() { // from class: androidx.fragment.app.DialogFragment.5
            @Override // androidx.fragment.app.FragmentContainer
            public final View onFindViewById(int i) {
                FragmentContainer fragmentContainer = c02265;
                if (fragmentContainer.onHasView()) {
                    return fragmentContainer.onFindViewById(i);
                }
                Dialog dialog = DialogFragment.this.mDialog;
                if (dialog != null) {
                    return dialog.findViewById(i);
                }
                return null;
            }

            @Override // androidx.fragment.app.FragmentContainer
            public final boolean onHasView() {
                return c02265.onHasView() || DialogFragment.this.mDialogCreated;
            }
        };
    }

    public final void dismissInternal(boolean z, boolean z2) {
        if (this.mDismissed) {
            return;
        }
        this.mDismissed = true;
        this.mShownByMe = false;
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.setOnDismissListener(null);
            this.mDialog.dismiss();
            if (!z2) {
                if (Looper.myLooper() == this.mHandler.getLooper()) {
                    onDismiss(this.mDialog);
                } else {
                    this.mHandler.post(this.mDismissRunnable);
                }
            }
        }
        this.mViewDestroyed = true;
        if (this.mBackStackId >= 0) {
            FragmentManager parentFragmentManager = getParentFragmentManager();
            int i = this.mBackStackId;
            if (i < 0) {
                throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Bad id: ", i));
            }
            parentFragmentManager.enqueueAction(parentFragmentManager.new PopBackStackState(null, i, 1), z);
            this.mBackStackId = -1;
            return;
        }
        BackStackRecord backStackRecord = new BackStackRecord(getParentFragmentManager());
        backStackRecord.mReorderingAllowed = true;
        backStackRecord.remove(this);
        if (z) {
            backStackRecord.commitAllowingStateLoss();
        } else {
            backStackRecord.commit();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        this.mCalled = true;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mViewLifecycleOwnerLiveData.observeForever(this.mObserver);
        if (this.mShownByMe) {
            return;
        }
        this.mDismissed = false;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mHandler = new Handler();
        this.mShowsDialog = this.mContainerId == 0;
        if (bundle != null) {
            this.mStyle = bundle.getInt("android:style", 0);
            this.mTheme = bundle.getInt("android:theme", 0);
            this.mCancelable = bundle.getBoolean("android:cancelable", true);
            this.mShowsDialog = bundle.getBoolean("android:showsDialog", this.mShowsDialog);
            this.mBackStackId = bundle.getInt("android:backStackId", -1);
        }
    }

    public Dialog onCreateDialog() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d("SeslDialogFragment", "onCreateDialog called for DialogFragment " + this);
        }
        return new ComponentDialog(requireContext(), this.mTheme);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroyView() {
        this.mCalled = true;
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            this.mViewDestroyed = true;
            dialog.setOnDismissListener(null);
            this.mDialog.dismiss();
            if (!this.mDismissed) {
                onDismiss(this.mDialog);
            }
            this.mDialog = null;
            this.mDialogCreated = false;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDetach() {
        this.mCalled = true;
        if (!this.mShownByMe && !this.mDismissed) {
            this.mDismissed = true;
        }
        this.mViewLifecycleOwnerLiveData.removeObserver(this.mObserver);
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        if (this.mViewDestroyed) {
            return;
        }
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d("SeslDialogFragment", "onDismiss called for DialogFragment " + this);
        }
        dismissInternal(true, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0046 A[Catch: all -> 0x006b, TryCatch #0 {all -> 0x006b, blocks: (B:10:0x001a, B:12:0x0026, B:18:0x003e, B:20:0x0046, B:21:0x004d, B:23:0x0030, B:25:0x0036, B:26:0x003b, B:27:0x0065), top: B:9:0x001a }] */
    @Override // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final LayoutInflater onGetLayoutInflater(Bundle bundle) {
        Context context;
        LayoutInflater onGetLayoutInflater = super.onGetLayoutInflater(bundle);
        boolean z = this.mShowsDialog;
        if (!z || this.mCreatingDialog) {
            if (FragmentManager.isLoggingEnabled(2)) {
                String str = "getting layout inflater for DialogFragment " + this;
                if (this.mShowsDialog) {
                    AbstractC0000x2c234b15.m3m("mCreatingDialog = true: ", str, "SeslDialogFragment");
                } else {
                    AbstractC0000x2c234b15.m3m("mShowsDialog = false: ", str, "SeslDialogFragment");
                }
            }
            return onGetLayoutInflater;
        }
        if (z && !this.mDialogCreated) {
            try {
                this.mCreatingDialog = true;
                Dialog onCreateDialog = onCreateDialog();
                this.mDialog = onCreateDialog;
                if (this.mShowsDialog) {
                    int i = this.mStyle;
                    if (i != 1 && i != 2) {
                        if (i == 3) {
                            Window window = onCreateDialog.getWindow();
                            if (window != null) {
                                window.addFlags(24);
                            }
                        } else {
                            context = getContext();
                            if (context instanceof Activity) {
                                this.mDialog.setOwnerActivity((Activity) context);
                            }
                            this.mDialog.setCancelable(this.mCancelable);
                            this.mDialog.setOnCancelListener(this.mOnCancelListener);
                            this.mDialog.setOnDismissListener(this.mOnDismissListener);
                            this.mDialogCreated = true;
                        }
                    }
                    onCreateDialog.requestWindowFeature(1);
                    context = getContext();
                    if (context instanceof Activity) {
                    }
                    this.mDialog.setCancelable(this.mCancelable);
                    this.mDialog.setOnCancelListener(this.mOnCancelListener);
                    this.mDialog.setOnDismissListener(this.mOnDismissListener);
                    this.mDialogCreated = true;
                } else {
                    this.mDialog = null;
                }
            } finally {
                this.mCreatingDialog = false;
            }
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.d("SeslDialogFragment", "get layout inflater for DialogFragment " + this + " from dialog context");
        }
        Dialog dialog = this.mDialog;
        return dialog != null ? onGetLayoutInflater.cloneInContext(dialog.getContext()) : onGetLayoutInflater;
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            Bundle onSaveInstanceState = dialog.onSaveInstanceState();
            onSaveInstanceState.putBoolean("android:dialogShowing", false);
            bundle.putBundle("android:savedDialogState", onSaveInstanceState);
        }
        int i = this.mStyle;
        if (i != 0) {
            bundle.putInt("android:style", i);
        }
        int i2 = this.mTheme;
        if (i2 != 0) {
            bundle.putInt("android:theme", i2);
        }
        boolean z = this.mCancelable;
        if (!z) {
            bundle.putBoolean("android:cancelable", z);
        }
        boolean z2 = this.mShowsDialog;
        if (!z2) {
            bundle.putBoolean("android:showsDialog", z2);
        }
        int i3 = this.mBackStackId;
        if (i3 != -1) {
            bundle.putInt("android:backStackId", i3);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        this.mCalled = true;
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            this.mViewDestroyed = false;
            dialog.show();
            View decorView = this.mDialog.getWindow().getDecorView();
            decorView.setTag(R.id.view_tree_lifecycle_owner, this);
            decorView.setTag(R.id.view_tree_view_model_store_owner, this);
            decorView.setTag(R.id.view_tree_saved_state_registry_owner, this);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        this.mCalled = true;
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.hide();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewStateRestored(Bundle bundle) {
        Bundle bundle2;
        this.mCalled = true;
        if (this.mDialog == null || bundle == null || (bundle2 = bundle.getBundle("android:savedDialogState")) == null) {
            return;
        }
        this.mDialog.onRestoreInstanceState(bundle2);
    }

    @Override // androidx.fragment.app.Fragment
    public final void performCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Bundle bundle2;
        super.performCreateView(layoutInflater, viewGroup, bundle);
        if (this.mView != null || this.mDialog == null || bundle == null || (bundle2 = bundle.getBundle("android:savedDialogState")) == null) {
            return;
        }
        this.mDialog.onRestoreInstanceState(bundle2);
    }

    public final Dialog requireDialog() {
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            return dialog;
        }
        throw new IllegalStateException("DialogFragment " + this + " does not have a Dialog.");
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.fragment.app.DialogFragment$4] */
    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.fragment.app.DialogFragment$1] */
    /* JADX WARN: Type inference failed for: r2v2, types: [androidx.fragment.app.DialogFragment$2] */
    public DialogFragment(int i) {
        super(i);
        this.mDismissRunnable = new Runnable() { // from class: androidx.fragment.app.DialogFragment.1
            @Override // java.lang.Runnable
            public final void run() {
                DialogFragment dialogFragment = DialogFragment.this;
                dialogFragment.mOnDismissListener.onDismiss(dialogFragment.mDialog);
            }
        };
        this.mOnCancelListener = new DialogInterface.OnCancelListener() { // from class: androidx.fragment.app.DialogFragment.2
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                DialogFragment dialogFragment = DialogFragment.this;
                Dialog dialog = dialogFragment.mDialog;
                if (dialog != null) {
                    dialogFragment.onCancel(dialog);
                }
            }
        };
        this.mOnDismissListener = new DialogInterfaceOnDismissListenerC02213();
        this.mStyle = 0;
        this.mTheme = 0;
        this.mCancelable = true;
        this.mShowsDialog = true;
        this.mBackStackId = -1;
        this.mObserver = new Observer() { // from class: androidx.fragment.app.DialogFragment.4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                if (((LifecycleOwner) obj) != null) {
                    DialogFragment dialogFragment = DialogFragment.this;
                    if (dialogFragment.mShowsDialog) {
                        View requireView = dialogFragment.requireView();
                        if (requireView.getParent() != null) {
                            throw new IllegalStateException("DialogFragment can not be attached to a container view");
                        }
                        if (dialogFragment.mDialog != null) {
                            if (FragmentManager.isLoggingEnabled(3)) {
                                Log.d("SeslDialogFragment", "DialogFragment " + this + " setting the content view on " + dialogFragment.mDialog);
                            }
                            dialogFragment.mDialog.setContentView(requireView);
                        }
                    }
                }
            }
        };
        this.mDialogCreated = false;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
    }
}
