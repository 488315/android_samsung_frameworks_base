package androidx.fragment.app;

import androidx.fragment.app.strictmode.FragmentStrictMode;
import androidx.lifecycle.Lifecycle;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class FragmentTransaction {
    public boolean mAddToBackStack;
    public boolean mAllowAddToBackStack;
    public int mBreadCrumbShortTitleRes;
    public CharSequence mBreadCrumbShortTitleText;
    public int mBreadCrumbTitleRes;
    public CharSequence mBreadCrumbTitleText;
    public int mEnterAnim;
    public int mExitAnim;
    public String mName;
    public final ArrayList mOps;
    public int mPopEnterAnim;
    public int mPopExitAnim;
    public boolean mReorderingAllowed;
    public ArrayList mSharedElementSourceNames;
    public ArrayList mSharedElementTargetNames;
    public int mTransition;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.fragment.app.FragmentTransaction$Op */
    public final class C0247Op {
        public int mCmd;
        public Lifecycle.State mCurrentMaxState;
        public int mEnterAnim;
        public int mExitAnim;
        public Fragment mFragment;
        public boolean mFromExpandedOp;
        public Lifecycle.State mOldMaxState;
        public int mPopEnterAnim;
        public int mPopExitAnim;

        public C0247Op() {
        }

        public C0247Op(int i, Fragment fragment) {
            this.mCmd = i;
            this.mFragment = fragment;
            this.mFromExpandedOp = false;
            Lifecycle.State state = Lifecycle.State.RESUMED;
            this.mOldMaxState = state;
            this.mCurrentMaxState = state;
        }

        public C0247Op(int i, Fragment fragment, boolean z) {
            this.mCmd = i;
            this.mFragment = fragment;
            this.mFromExpandedOp = z;
            Lifecycle.State state = Lifecycle.State.RESUMED;
            this.mOldMaxState = state;
            this.mCurrentMaxState = state;
        }

        public C0247Op(int i, Fragment fragment, Lifecycle.State state) {
            this.mCmd = i;
            this.mFragment = fragment;
            this.mFromExpandedOp = false;
            this.mOldMaxState = fragment.mMaxState;
            this.mCurrentMaxState = state;
        }

        public C0247Op(C0247Op c0247Op) {
            this.mCmd = c0247Op.mCmd;
            this.mFragment = c0247Op.mFragment;
            this.mFromExpandedOp = c0247Op.mFromExpandedOp;
            this.mEnterAnim = c0247Op.mEnterAnim;
            this.mExitAnim = c0247Op.mExitAnim;
            this.mPopEnterAnim = c0247Op.mPopEnterAnim;
            this.mPopExitAnim = c0247Op.mPopExitAnim;
            this.mOldMaxState = c0247Op.mOldMaxState;
            this.mCurrentMaxState = c0247Op.mCurrentMaxState;
        }
    }

    @Deprecated
    public FragmentTransaction() {
        this.mOps = new ArrayList();
        this.mAllowAddToBackStack = true;
        this.mReorderingAllowed = false;
    }

    public final void addOp(C0247Op c0247Op) {
        this.mOps.add(c0247Op);
        c0247Op.mEnterAnim = this.mEnterAnim;
        c0247Op.mExitAnim = this.mExitAnim;
        c0247Op.mPopEnterAnim = this.mPopEnterAnim;
        c0247Op.mPopExitAnim = this.mPopExitAnim;
    }

    public void doAddOp(int i, Fragment fragment, String str, int i2) {
        String str2 = fragment.mPreviousWho;
        if (str2 != null) {
            FragmentStrictMode.onFragmentReuse(fragment, str2);
        }
        Class<?> cls = fragment.getClass();
        int modifiers = cls.getModifiers();
        if (cls.isAnonymousClass() || !Modifier.isPublic(modifiers) || (cls.isMemberClass() && !Modifier.isStatic(modifiers))) {
            throw new IllegalStateException("Fragment " + cls.getCanonicalName() + " must be a public static class to be  properly recreated from instance state.");
        }
        if (str != null) {
            String str3 = fragment.mTag;
            if (str3 != null && !str.equals(str3)) {
                StringBuilder sb = new StringBuilder("Can't change tag of fragment ");
                sb.append(fragment);
                sb.append(": was ");
                throw new IllegalStateException(FragmentTransaction$$ExternalSyntheticOutline0.m38m(sb, fragment.mTag, " now ", str));
            }
            fragment.mTag = str;
        }
        if (i != 0) {
            if (i == -1) {
                throw new IllegalArgumentException("Can't add fragment " + fragment + " with tag " + str + " to container view with no id");
            }
            int i3 = fragment.mFragmentId;
            if (i3 != 0 && i3 != i) {
                throw new IllegalStateException("Can't change container ID of fragment " + fragment + ": was " + fragment.mFragmentId + " now " + i);
            }
            fragment.mFragmentId = i;
            fragment.mContainerId = i;
        }
        addOp(new C0247Op(i2, fragment));
    }

    public final void replace(int i, Fragment fragment, String str) {
        if (i == 0) {
            throw new IllegalArgumentException("Must use non-zero containerViewId");
        }
        doAddOp(i, fragment, str, 2);
    }

    public FragmentTransaction(FragmentFactory fragmentFactory, ClassLoader classLoader) {
        this.mOps = new ArrayList();
        this.mAllowAddToBackStack = true;
        this.mReorderingAllowed = false;
    }

    public FragmentTransaction(FragmentFactory fragmentFactory, ClassLoader classLoader, FragmentTransaction fragmentTransaction) {
        this(fragmentFactory, classLoader);
        Iterator it = fragmentTransaction.mOps.iterator();
        while (it.hasNext()) {
            this.mOps.add(new C0247Op((C0247Op) it.next()));
        }
        this.mEnterAnim = fragmentTransaction.mEnterAnim;
        this.mExitAnim = fragmentTransaction.mExitAnim;
        this.mPopEnterAnim = fragmentTransaction.mPopEnterAnim;
        this.mPopExitAnim = fragmentTransaction.mPopExitAnim;
        this.mTransition = fragmentTransaction.mTransition;
        this.mAddToBackStack = fragmentTransaction.mAddToBackStack;
        this.mAllowAddToBackStack = fragmentTransaction.mAllowAddToBackStack;
        this.mName = fragmentTransaction.mName;
        this.mBreadCrumbShortTitleRes = fragmentTransaction.mBreadCrumbShortTitleRes;
        this.mBreadCrumbShortTitleText = fragmentTransaction.mBreadCrumbShortTitleText;
        this.mBreadCrumbTitleRes = fragmentTransaction.mBreadCrumbTitleRes;
        this.mBreadCrumbTitleText = fragmentTransaction.mBreadCrumbTitleText;
        if (fragmentTransaction.mSharedElementSourceNames != null) {
            ArrayList arrayList = new ArrayList();
            this.mSharedElementSourceNames = arrayList;
            arrayList.addAll(fragmentTransaction.mSharedElementSourceNames);
        }
        if (fragmentTransaction.mSharedElementTargetNames != null) {
            ArrayList arrayList2 = new ArrayList();
            this.mSharedElementTargetNames = arrayList2;
            arrayList2.addAll(fragmentTransaction.mSharedElementTargetNames);
        }
        this.mReorderingAllowed = fragmentTransaction.mReorderingAllowed;
    }
}
