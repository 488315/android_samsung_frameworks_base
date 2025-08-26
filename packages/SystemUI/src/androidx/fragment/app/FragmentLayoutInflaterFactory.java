package androidx.fragment.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.fragment.R$styleable;
import androidx.fragment.app.strictmode.FragmentStrictMode;
import androidx.fragment.app.strictmode.FragmentTagUsageViolation;

/* loaded from: classes.dex */
public class FragmentLayoutInflaterFactory implements LayoutInflater.Factory2 {
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
        boolean zIsAssignableFrom;
        final FragmentStateManager fragmentStateManagerCreateOrGetFragmentStateManager;
        if (FragmentContainerView.class.getName().equals(str)) {
            return new FragmentContainerView(context, attributeSet, this.mFragmentManager);
        }
        if ("fragment".equals(str)) {
            String attributeValue = attributeSet.getAttributeValue(null, "class");
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Fragment);
            if (attributeValue == null) {
                attributeValue = typedArrayObtainStyledAttributes.getString(0);
            }
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(1, -1);
            String string = typedArrayObtainStyledAttributes.getString(2);
            typedArrayObtainStyledAttributes.recycle();
            if (attributeValue != null) {
                try {
                    zIsAssignableFrom = Fragment.class.isAssignableFrom(FragmentFactory.loadClass(context.getClassLoader(), attributeValue));
                } catch (ClassNotFoundException unused) {
                    zIsAssignableFrom = false;
                }
                if (zIsAssignableFrom) {
                    int id = view != null ? view.getId() : 0;
                    if (id == -1 && resourceId == -1 && string == null) {
                        throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + attributeValue);
                    }
                    Fragment fragmentFindFragmentById = resourceId != -1 ? this.mFragmentManager.findFragmentById(resourceId) : null;
                    if (fragmentFindFragmentById == null && string != null) {
                        fragmentFindFragmentById = this.mFragmentManager.findFragmentByTag(string);
                    }
                    if (fragmentFindFragmentById == null && id != -1) {
                        fragmentFindFragmentById = this.mFragmentManager.findFragmentById(id);
                    }
                    if (fragmentFindFragmentById == null) {
                        fragmentFindFragmentById = this.mFragmentManager.getFragmentFactory().instantiate(context.getClassLoader(), attributeValue);
                        fragmentFindFragmentById.mFromLayout = true;
                        fragmentFindFragmentById.mFragmentId = resourceId != 0 ? resourceId : id;
                        fragmentFindFragmentById.mContainerId = id;
                        fragmentFindFragmentById.mTag = string;
                        fragmentFindFragmentById.mInLayout = true;
                        FragmentManager fragmentManager = this.mFragmentManager;
                        fragmentFindFragmentById.mFragmentManager = fragmentManager;
                        FragmentHostCallback fragmentHostCallback = fragmentManager.mHost;
                        fragmentFindFragmentById.mHost = fragmentHostCallback;
                        Context context2 = fragmentHostCallback.context;
                        fragmentFindFragmentById.mCalled = true;
                        if ((fragmentHostCallback != null ? fragmentHostCallback.activity : null) != null) {
                            fragmentFindFragmentById.mCalled = true;
                        }
                        fragmentStateManagerCreateOrGetFragmentStateManager = fragmentManager.addFragment(fragmentFindFragmentById);
                        if (FragmentManager.isLoggingEnabled(2)) {
                            fragmentFindFragmentById.toString();
                            Integer.toHexString(resourceId);
                        }
                    } else {
                        if (fragmentFindFragmentById.mInLayout) {
                            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(resourceId) + ", tag " + string + ", or parent id 0x" + Integer.toHexString(id) + " with another fragment for " + attributeValue);
                        }
                        fragmentFindFragmentById.mInLayout = true;
                        FragmentManager fragmentManager2 = this.mFragmentManager;
                        fragmentFindFragmentById.mFragmentManager = fragmentManager2;
                        FragmentHostCallback fragmentHostCallback2 = fragmentManager2.mHost;
                        fragmentFindFragmentById.mHost = fragmentHostCallback2;
                        Context context3 = fragmentHostCallback2.context;
                        fragmentFindFragmentById.mCalled = true;
                        if ((fragmentHostCallback2 != null ? fragmentHostCallback2.activity : null) != null) {
                            fragmentFindFragmentById.mCalled = true;
                        }
                        fragmentStateManagerCreateOrGetFragmentStateManager = fragmentManager2.createOrGetFragmentStateManager(fragmentFindFragmentById);
                        if (FragmentManager.isLoggingEnabled(2)) {
                            fragmentFindFragmentById.toString();
                            Integer.toHexString(resourceId);
                        }
                    }
                    ViewGroup viewGroup = (ViewGroup) view;
                    FragmentStrictMode fragmentStrictMode = FragmentStrictMode.INSTANCE;
                    FragmentTagUsageViolation fragmentTagUsageViolation = new FragmentTagUsageViolation(fragmentFindFragmentById, viewGroup);
                    FragmentStrictMode.INSTANCE.getClass();
                    FragmentStrictMode.logIfDebuggingEnabled(fragmentTagUsageViolation);
                    FragmentStrictMode.Policy nearestPolicy = FragmentStrictMode.getNearestPolicy(fragmentFindFragmentById);
                    if (nearestPolicy.flags.contains(FragmentStrictMode.Flag.DETECT_FRAGMENT_TAG_USAGE) && FragmentStrictMode.shouldHandlePolicyViolation(nearestPolicy, fragmentFindFragmentById.getClass(), FragmentTagUsageViolation.class)) {
                        FragmentStrictMode.handlePolicyViolation(nearestPolicy, fragmentTagUsageViolation);
                    }
                    fragmentFindFragmentById.mContainer = viewGroup;
                    fragmentStateManagerCreateOrGetFragmentStateManager.moveToExpectedState();
                    fragmentStateManagerCreateOrGetFragmentStateManager.ensureInflatedView();
                    View view2 = fragmentFindFragmentById.mView;
                    if (view2 == null) {
                        throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Fragment ", attributeValue, " did not create a view."));
                    }
                    if (resourceId != 0) {
                        view2.setId(resourceId);
                    }
                    if (fragmentFindFragmentById.mView.getTag() == null) {
                        fragmentFindFragmentById.mView.setTag(string);
                    }
                    fragmentFindFragmentById.mView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: androidx.fragment.app.FragmentLayoutInflaterFactory.1
                        @Override // android.view.View.OnAttachStateChangeListener
                        public final void onViewAttachedToWindow(View view3) {
                            FragmentStateManager fragmentStateManager = fragmentStateManagerCreateOrGetFragmentStateManager;
                            Fragment fragment = fragmentStateManager.mFragment;
                            fragmentStateManager.moveToExpectedState();
                            SpecialEffectsController.getOrCreateController((ViewGroup) fragment.mView.getParent(), FragmentLayoutInflaterFactory.this.mFragmentManager).forceCompleteAllOperations();
                        }

                        @Override // android.view.View.OnAttachStateChangeListener
                        public final void onViewDetachedFromWindow(View view3) {
                        }
                    });
                    return fragmentFindFragmentById.mView;
                }
            }
        }
        return null;
    }
}
