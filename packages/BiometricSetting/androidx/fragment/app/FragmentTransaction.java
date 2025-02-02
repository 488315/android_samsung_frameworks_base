package androidx.fragment.app;

import androidx.lifecycle.Lifecycle;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class FragmentTransaction {
    boolean mAddToBackStack;
    int mBreadCrumbShortTitleRes;
    CharSequence mBreadCrumbShortTitleText;
    int mBreadCrumbTitleRes;
    CharSequence mBreadCrumbTitleText;
    int mEnterAnim;
    int mExitAnim;
    String mName;
    int mPopEnterAnim;
    int mPopExitAnim;
    ArrayList<String> mSharedElementSourceNames;
    ArrayList<String> mSharedElementTargetNames;
    int mTransition;
    ArrayList<C0138Op> mOps = new ArrayList<>();
    boolean mReorderingAllowed = false;

    /* renamed from: androidx.fragment.app.FragmentTransaction$Op */
    static final class C0138Op {
        int mCmd;
        Lifecycle.State mCurrentMaxState;
        int mEnterAnim;
        int mExitAnim;
        Fragment mFragment;
        boolean mFromExpandedOp;
        Lifecycle.State mOldMaxState;
        int mPopEnterAnim;
        int mPopExitAnim;

        C0138Op() {
        }

        C0138Op(int i, Fragment fragment) {
            this.mCmd = i;
            this.mFragment = fragment;
            this.mFromExpandedOp = false;
            Lifecycle.State state = Lifecycle.State.RESUMED;
            this.mOldMaxState = state;
            this.mCurrentMaxState = state;
        }

        C0138Op(int i, Fragment fragment, int i2) {
            this.mCmd = i;
            this.mFragment = fragment;
            this.mFromExpandedOp = true;
            Lifecycle.State state = Lifecycle.State.RESUMED;
            this.mOldMaxState = state;
            this.mCurrentMaxState = state;
        }
    }

    FragmentTransaction() {
    }

    final void addOp(C0138Op c0138Op) {
        this.mOps.add(c0138Op);
        c0138Op.mEnterAnim = this.mEnterAnim;
        c0138Op.mExitAnim = this.mExitAnim;
        c0138Op.mPopEnterAnim = this.mPopEnterAnim;
        c0138Op.mPopExitAnim = this.mPopExitAnim;
    }
}
