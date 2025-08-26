package androidx.fragment.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.activity.ComponentDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager.PopBackStackState;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class DialogFragment extends Fragment implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {
    public int mBackStackId;
    public boolean mCancelable;
    public boolean mCreatingDialog;
    public Dialog mDialog;
    public boolean mDialogCreated;
    public final AnonymousClass1 mDismissRunnable;
    public boolean mDismissed;
    public Handler mHandler;
    public final AnonymousClass4 mObserver;
    public final AnonymousClass2 mOnCancelListener;
    public final AnonymousClass3 mOnDismissListener;
    public boolean mShownByMe;
    public boolean mShowsDialog;
    public int mStyle;
    public int mTheme;
    public boolean mViewDestroyed;

    /* renamed from: androidx.fragment.app.DialogFragment$3, reason: invalid class name */
    public class AnonymousClass3 implements DialogInterface.OnDismissListener {
        public AnonymousClass3() {
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
        this.mOnDismissListener = new AnonymousClass3();
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
                        View viewRequireView = dialogFragment.requireView();
                        if (viewRequireView.getParent() != null) {
                            throw new IllegalStateException("DialogFragment can not be attached to a container view");
                        }
                        if (dialogFragment.mDialog != null) {
                            if (FragmentManager.isLoggingEnabled(3)) {
                                Log.d("SeslDialogFragment", "DialogFragment " + this + " setting the content view on " + dialogFragment.mDialog);
                            }
                            dialogFragment.mDialog.setContentView(viewRequireView);
                        }
                    }
                }
            }
        };
        this.mDialogCreated = false;
    }

    @Override // androidx.fragment.app.Fragment
    public final FragmentContainer createFragmentContainer() {
        final Fragment.AnonymousClass5 anonymousClass5 = new Fragment.AnonymousClass5();
        return new FragmentContainer() { // from class: androidx.fragment.app.DialogFragment.5
            @Override // androidx.fragment.app.FragmentContainer
            public final View onFindViewById(int i) {
                FragmentContainer fragmentContainer = anonymousClass5;
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
                return anonymousClass5.onHasView() || DialogFragment.this.mDialogCreated;
            }
        };
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
        if (this.mDismissed) {
            return;
        }
        this.mDismissed = true;
        this.mShownByMe = false;
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.setOnDismissListener(null);
            this.mDialog.dismiss();
        }
        this.mViewDestroyed = true;
        if (this.mBackStackId >= 0) {
            FragmentManager parentFragmentManager = getParentFragmentManager();
            int i = this.mBackStackId;
            if (i < 0) {
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Bad id: "));
            }
            parentFragmentManager.enqueueAction(parentFragmentManager.new PopBackStackState(null, i, 1), true);
            this.mBackStackId = -1;
            return;
        }
        BackStackRecord backStackRecord = new BackStackRecord(getParentFragmentManager());
        backStackRecord.mReorderingAllowed = true;
        FragmentManager fragmentManager = this.mFragmentManager;
        if (fragmentManager == null || fragmentManager == backStackRecord.mManager) {
            backStackRecord.addOp(new FragmentTransaction.Op(3, this));
            backStackRecord.commitInternal(true, true);
        } else {
            throw new IllegalStateException("Cannot remove Fragment attached to a different FragmentManager. Fragment " + toString() + " is already attached to a FragmentManager.");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0046 A[Catch: all -> 0x004e, TryCatch #0 {all -> 0x004e, blocks: (B:12:0x001a, B:14:0x0026, B:24:0x003e, B:26:0x0046, B:29:0x0050, B:20:0x0030, B:22:0x0036, B:23:0x003b, B:30:0x0068), top: B:49:0x001a }] */
    @Override // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final LayoutInflater onGetLayoutInflater(Bundle bundle) {
        Context context;
        LayoutInflater layoutInflaterOnGetLayoutInflater = super.onGetLayoutInflater(bundle);
        boolean z = this.mShowsDialog;
        if (z && !this.mCreatingDialog) {
            if (z && !this.mDialogCreated) {
                try {
                    this.mCreatingDialog = true;
                    Dialog dialogOnCreateDialog = onCreateDialog();
                    this.mDialog = dialogOnCreateDialog;
                    if (this.mShowsDialog) {
                        int i = this.mStyle;
                        if (i == 1 || i == 2) {
                            dialogOnCreateDialog.requestWindowFeature(1);
                            context = getContext();
                            if (context instanceof Activity) {
                                this.mDialog.setOwnerActivity((Activity) context);
                            }
                            this.mDialog.setCancelable(this.mCancelable);
                            this.mDialog.setOnCancelListener(this.mOnCancelListener);
                            this.mDialog.setOnDismissListener(this.mOnDismissListener);
                            this.mDialogCreated = true;
                        } else if (i == 3) {
                            Window window = dialogOnCreateDialog.getWindow();
                            if (window != null) {
                                window.addFlags(24);
                            }
                            dialogOnCreateDialog.requestWindowFeature(1);
                            context = getContext();
                            if (context instanceof Activity) {
                            }
                            this.mDialog.setCancelable(this.mCancelable);
                            this.mDialog.setOnCancelListener(this.mOnCancelListener);
                            this.mDialog.setOnDismissListener(this.mOnDismissListener);
                            this.mDialogCreated = true;
                        } else {
                            context = getContext();
                            if (context instanceof Activity) {
                            }
                            this.mDialog.setCancelable(this.mCancelable);
                            this.mDialog.setOnCancelListener(this.mOnCancelListener);
                            this.mDialog.setOnDismissListener(this.mOnDismissListener);
                            this.mDialogCreated = true;
                        }
                    } else {
                        this.mDialog = null;
                    }
                    this.mCreatingDialog = false;
                } catch (Throwable th) {
                    this.mCreatingDialog = false;
                    throw th;
                }
            }
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.d("SeslDialogFragment", "get layout inflater for DialogFragment " + this + " from dialog context");
            }
            Dialog dialog = this.mDialog;
            if (dialog != null) {
                return layoutInflaterOnGetLayoutInflater.cloneInContext(dialog.getContext());
            }
        } else if (FragmentManager.isLoggingEnabled(2)) {
            String str = "getting layout inflater for DialogFragment " + this;
            if (!this.mShowsDialog) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("mShowsDialog = false: ", str, "SeslDialogFragment");
                return layoutInflaterOnGetLayoutInflater;
            }
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("mCreatingDialog = true: ", str, "SeslDialogFragment");
        }
        return layoutInflaterOnGetLayoutInflater;
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            Bundle bundleOnSaveInstanceState = dialog.onSaveInstanceState();
            bundleOnSaveInstanceState.putBoolean("android:dialogShowing", false);
            bundle.putBundle("android:savedDialogState", bundleOnSaveInstanceState);
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
            ViewTreeLifecycleOwner.set(this, decorView);
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
        this.mOnDismissListener = new AnonymousClass3();
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
                        View viewRequireView = dialogFragment.requireView();
                        if (viewRequireView.getParent() != null) {
                            throw new IllegalStateException("DialogFragment can not be attached to a container view");
                        }
                        if (dialogFragment.mDialog != null) {
                            if (FragmentManager.isLoggingEnabled(3)) {
                                Log.d("SeslDialogFragment", "DialogFragment " + this + " setting the content view on " + dialogFragment.mDialog);
                            }
                            dialogFragment.mDialog.setContentView(viewRequireView);
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
