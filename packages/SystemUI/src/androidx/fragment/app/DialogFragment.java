package androidx.fragment.app;

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
import androidx.activity.ComponentDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager.PopBackStackState;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x0046 A[Catch: all -> 0x004e, TryCatch #0 {all -> 0x004e, blocks: (B:10:0x001a, B:12:0x0026, B:18:0x003e, B:20:0x0046, B:21:0x0050, B:23:0x0030, B:25:0x0036, B:26:0x003b, B:27:0x0068), top: B:9:0x001a }] */
    @Override // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.LayoutInflater onGetLayoutInflater(android.os.Bundle r8) {
        /*
            r7 = this;
            android.view.LayoutInflater r8 = super.onGetLayoutInflater(r8)
            boolean r0 = r7.mShowsDialog
            java.lang.String r1 = "SeslDialogFragment"
            r2 = 2
            if (r0 == 0) goto L9a
            boolean r3 = r7.mCreatingDialog
            if (r3 == 0) goto L11
            goto L9a
        L11:
            if (r0 != 0) goto L14
            goto L71
        L14:
            boolean r0 = r7.mDialogCreated
            if (r0 != 0) goto L71
            r0 = 0
            r3 = 1
            r7.mCreatingDialog = r3     // Catch: java.lang.Throwable -> L4e
            android.app.Dialog r4 = r7.onCreateDialog()     // Catch: java.lang.Throwable -> L4e
            r7.mDialog = r4     // Catch: java.lang.Throwable -> L4e
            boolean r5 = r7.mShowsDialog     // Catch: java.lang.Throwable -> L4e
            if (r5 == 0) goto L68
            int r5 = r7.mStyle     // Catch: java.lang.Throwable -> L4e
            if (r5 == r3) goto L3b
            if (r5 == r2) goto L3b
            r6 = 3
            if (r5 == r6) goto L30
            goto L3e
        L30:
            android.view.Window r5 = r4.getWindow()     // Catch: java.lang.Throwable -> L4e
            if (r5 == 0) goto L3b
            r6 = 24
            r5.addFlags(r6)     // Catch: java.lang.Throwable -> L4e
        L3b:
            r4.requestWindowFeature(r3)     // Catch: java.lang.Throwable -> L4e
        L3e:
            android.content.Context r4 = r7.getContext()     // Catch: java.lang.Throwable -> L4e
            boolean r5 = r4 instanceof android.app.Activity     // Catch: java.lang.Throwable -> L4e
            if (r5 == 0) goto L50
            android.app.Dialog r5 = r7.mDialog     // Catch: java.lang.Throwable -> L4e
            android.app.Activity r4 = (android.app.Activity) r4     // Catch: java.lang.Throwable -> L4e
            r5.setOwnerActivity(r4)     // Catch: java.lang.Throwable -> L4e
            goto L50
        L4e:
            r8 = move-exception
            goto L6e
        L50:
            android.app.Dialog r4 = r7.mDialog     // Catch: java.lang.Throwable -> L4e
            boolean r5 = r7.mCancelable     // Catch: java.lang.Throwable -> L4e
            r4.setCancelable(r5)     // Catch: java.lang.Throwable -> L4e
            android.app.Dialog r4 = r7.mDialog     // Catch: java.lang.Throwable -> L4e
            androidx.fragment.app.DialogFragment$2 r5 = r7.mOnCancelListener     // Catch: java.lang.Throwable -> L4e
            r4.setOnCancelListener(r5)     // Catch: java.lang.Throwable -> L4e
            android.app.Dialog r4 = r7.mDialog     // Catch: java.lang.Throwable -> L4e
            androidx.fragment.app.DialogFragment$3 r5 = r7.mOnDismissListener     // Catch: java.lang.Throwable -> L4e
            r4.setOnDismissListener(r5)     // Catch: java.lang.Throwable -> L4e
            r7.mDialogCreated = r3     // Catch: java.lang.Throwable -> L4e
            goto L6b
        L68:
            r3 = 0
            r7.mDialog = r3     // Catch: java.lang.Throwable -> L4e
        L6b:
            r7.mCreatingDialog = r0
            goto L71
        L6e:
            r7.mCreatingDialog = r0
            throw r8
        L71:
            boolean r0 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r2)
            if (r0 == 0) goto L8d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "get layout inflater for DialogFragment "
            r0.<init>(r2)
            r0.append(r7)
            java.lang.String r2 = " from dialog context"
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r1, r0)
        L8d:
            android.app.Dialog r7 = r7.mDialog
            if (r7 == 0) goto Lbd
            android.content.Context r7 = r7.getContext()
            android.view.LayoutInflater r7 = r8.cloneInContext(r7)
            return r7
        L9a:
            boolean r0 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r2)
            if (r0 == 0) goto Lbd
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "getting layout inflater for DialogFragment "
            r0.<init>(r2)
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            boolean r7 = r7.mShowsDialog
            if (r7 != 0) goto Lb8
            java.lang.String r7 = "mShowsDialog = false: "
            android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r7, r0, r1)
            return r8
        Lb8:
            java.lang.String r7 = "mCreatingDialog = true: "
            android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r7, r0, r1)
        Lbd:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.DialogFragment.onGetLayoutInflater(android.os.Bundle):android.view.LayoutInflater");
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
