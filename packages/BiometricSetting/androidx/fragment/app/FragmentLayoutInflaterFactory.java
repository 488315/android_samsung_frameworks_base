package androidx.fragment.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.helper.widget.LogJson$JsonWriter$$ExternalSyntheticOutline0;
import androidx.fragment.R$styleable;
import androidx.fragment.app.strictmode.FragmentStrictMode;

/* loaded from: classes.dex */
final class FragmentLayoutInflaterFactory implements LayoutInflater.Factory2 {
    final FragmentManager mFragmentManager;

    FragmentLayoutInflaterFactory(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    @Override // android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        FragmentStateManager createOrGetFragmentStateManager;
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
        if (attributeValue == null || !FragmentFactory.isFragmentClass(context.getClassLoader(), attributeValue)) {
            return null;
        }
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
            FragmentFactory fragmentFactory = this.mFragmentManager.getFragmentFactory();
            context.getClassLoader();
            findFragmentById = fragmentFactory.instantiate(attributeValue);
            findFragmentById.mFromLayout = true;
            findFragmentById.mFragmentId = resourceId != 0 ? resourceId : id;
            findFragmentById.mContainerId = id;
            findFragmentById.mTag = string;
            findFragmentById.mInLayout = true;
            FragmentManager fragmentManager = this.mFragmentManager;
            findFragmentById.mFragmentManager = fragmentManager;
            findFragmentById.mHost = fragmentManager.getHost();
            this.mFragmentManager.getHost().getClass();
            findFragmentById.onInflate();
            createOrGetFragmentStateManager = this.mFragmentManager.addFragment(findFragmentById);
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v("FragmentManager", "Fragment " + findFragmentById + " has been inflated via the <fragment> tag: id=0x" + Integer.toHexString(resourceId));
            }
        } else {
            if (findFragmentById.mInLayout) {
                throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(resourceId) + ", tag " + string + ", or parent id 0x" + Integer.toHexString(id) + " with another fragment for " + attributeValue);
            }
            findFragmentById.mInLayout = true;
            FragmentManager fragmentManager2 = this.mFragmentManager;
            findFragmentById.mFragmentManager = fragmentManager2;
            findFragmentById.mHost = fragmentManager2.getHost();
            this.mFragmentManager.getHost().getClass();
            findFragmentById.onInflate();
            createOrGetFragmentStateManager = this.mFragmentManager.createOrGetFragmentStateManager(findFragmentById);
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v("FragmentManager", "Retained Fragment " + findFragmentById + " has been re-attached via the <fragment> tag: id=0x" + Integer.toHexString(resourceId));
            }
        }
        ViewGroup viewGroup = (ViewGroup) view;
        FragmentStrictMode.onFragmentTagUsage(findFragmentById, viewGroup);
        findFragmentById.mContainer = viewGroup;
        createOrGetFragmentStateManager.moveToExpectedState();
        createOrGetFragmentStateManager.ensureInflatedView();
        throw new IllegalStateException(LogJson$JsonWriter$$ExternalSyntheticOutline0.m4m("Fragment ", attributeValue, " did not create a view."));
    }
}
