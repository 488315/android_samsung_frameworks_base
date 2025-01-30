package androidx.fragment.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.fragment.R$styleable;
import androidx.fragment.app.strictmode.FragmentStrictMode;
import androidx.fragment.app.strictmode.FragmentTagUsageViolation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FragmentLayoutInflaterFactory implements LayoutInflater.Factory2 {
    public final FragmentManager mFragmentManager;

    public FragmentLayoutInflaterFactory(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    @Override // android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        boolean z;
        final FragmentStateManager createOrGetFragmentStateManager;
        if (FragmentContainerView.class.getName().equals(str)) {
            return new FragmentContainerView(context, attributeSet, this.mFragmentManager);
        }
        if (!"fragment".equals(str)) {
            return null;
        }
        String attributeValue = attributeSet.getAttributeValue(null, "class");
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Fragment);
        if (attributeValue == null) {
            attributeValue = obtainStyledAttributes.getString(0);
        }
        int resourceId = obtainStyledAttributes.getResourceId(1, -1);
        String string = obtainStyledAttributes.getString(2);
        obtainStyledAttributes.recycle();
        if (attributeValue != null) {
            try {
                z = Fragment.class.isAssignableFrom(FragmentFactory.loadClass(context.getClassLoader(), attributeValue));
            } catch (ClassNotFoundException unused) {
                z = false;
            }
            if (z) {
                int id = view != null ? view.getId() : 0;
                if (id == -1 && resourceId == -1 && string == null) {
                    throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + attributeValue);
                }
                Fragment findFragmentById = resourceId != -1 ? this.mFragmentManager.findFragmentById(resourceId) : null;
                if (findFragmentById == null && string != null) {
                    findFragmentById = this.mFragmentManager.findFragmentByTag(string);
                }
                if (findFragmentById == null && id != -1) {
                    findFragmentById = this.mFragmentManager.findFragmentById(id);
                }
                if (findFragmentById == null) {
                    findFragmentById = this.mFragmentManager.getFragmentFactory().instantiate(context.getClassLoader(), attributeValue);
                    findFragmentById.mFromLayout = true;
                    findFragmentById.mFragmentId = resourceId != 0 ? resourceId : id;
                    findFragmentById.mContainerId = id;
                    findFragmentById.mTag = string;
                    findFragmentById.mInLayout = true;
                    FragmentManager fragmentManager = this.mFragmentManager;
                    findFragmentById.mFragmentManager = fragmentManager;
                    FragmentHostCallback fragmentHostCallback = fragmentManager.mHost;
                    findFragmentById.mHost = fragmentHostCallback;
                    Context context2 = fragmentHostCallback.mContext;
                    findFragmentById.mCalled = true;
                    if ((fragmentHostCallback != null ? fragmentHostCallback.mActivity : null) != null) {
                        findFragmentById.mCalled = true;
                    }
                    createOrGetFragmentStateManager = fragmentManager.addFragment(findFragmentById);
                    if (FragmentManager.isLoggingEnabled(2)) {
                        findFragmentById.toString();
                        Integer.toHexString(resourceId);
                    }
                } else {
                    if (findFragmentById.mInLayout) {
                        throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(resourceId) + ", tag " + string + ", or parent id 0x" + Integer.toHexString(id) + " with another fragment for " + attributeValue);
                    }
                    findFragmentById.mInLayout = true;
                    FragmentManager fragmentManager2 = this.mFragmentManager;
                    findFragmentById.mFragmentManager = fragmentManager2;
                    FragmentHostCallback fragmentHostCallback2 = fragmentManager2.mHost;
                    findFragmentById.mHost = fragmentHostCallback2;
                    Context context3 = fragmentHostCallback2.mContext;
                    findFragmentById.mCalled = true;
                    if ((fragmentHostCallback2 != null ? fragmentHostCallback2.mActivity : null) != null) {
                        findFragmentById.mCalled = true;
                    }
                    createOrGetFragmentStateManager = fragmentManager2.createOrGetFragmentStateManager(findFragmentById);
                    if (FragmentManager.isLoggingEnabled(2)) {
                        findFragmentById.toString();
                        Integer.toHexString(resourceId);
                    }
                }
                ViewGroup viewGroup = (ViewGroup) view;
                FragmentStrictMode fragmentStrictMode = FragmentStrictMode.INSTANCE;
                FragmentTagUsageViolation fragmentTagUsageViolation = new FragmentTagUsageViolation(findFragmentById, viewGroup);
                FragmentStrictMode.INSTANCE.getClass();
                FragmentStrictMode.logIfDebuggingEnabled(fragmentTagUsageViolation);
                FragmentStrictMode.Policy nearestPolicy = FragmentStrictMode.getNearestPolicy(findFragmentById);
                if (nearestPolicy.flags.contains(FragmentStrictMode.Flag.DETECT_FRAGMENT_TAG_USAGE) && FragmentStrictMode.shouldHandlePolicyViolation(nearestPolicy, findFragmentById.getClass(), FragmentTagUsageViolation.class)) {
                    FragmentStrictMode.handlePolicyViolation(nearestPolicy, fragmentTagUsageViolation);
                }
                findFragmentById.mContainer = viewGroup;
                createOrGetFragmentStateManager.moveToExpectedState();
                createOrGetFragmentStateManager.ensureInflatedView();
                View view2 = findFragmentById.mView;
                if (view2 == null) {
                    throw new IllegalStateException(PathParser$$ExternalSyntheticOutline0.m29m("Fragment ", attributeValue, " did not create a view."));
                }
                if (resourceId != 0) {
                    view2.setId(resourceId);
                }
                if (findFragmentById.mView.getTag() == null) {
                    findFragmentById.mView.setTag(string);
                }
                findFragmentById.mView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: androidx.fragment.app.FragmentLayoutInflaterFactory.1
                    @Override // android.view.View.OnAttachStateChangeListener
                    public final void onViewAttachedToWindow(View view3) {
                        FragmentStateManager fragmentStateManager = createOrGetFragmentStateManager;
                        Fragment fragment = fragmentStateManager.mFragment;
                        fragmentStateManager.moveToExpectedState();
                        SpecialEffectsController.getOrCreateController((ViewGroup) fragment.mView.getParent(), FragmentLayoutInflaterFactory.this.mFragmentManager.getSpecialEffectsControllerFactory()).forceCompleteAllOperations();
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public final void onViewDetachedFromWindow(View view3) {
                    }
                });
                return findFragmentById.mView;
            }
        }
        return null;
    }
}
