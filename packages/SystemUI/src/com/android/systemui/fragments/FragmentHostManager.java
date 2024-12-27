package com.android.systemui.fragments;

import android.app.Fragment;
import android.app.FragmentController;
import android.app.FragmentHostCallback;
import android.app.FragmentManager;
import android.app.FragmentManagerNonConfig;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.Trace;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.util.leak.LeakDetector;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.inject.Provider;

public final class FragmentHostManager {
    public final InterestingConfigChanges mConfigChanges;
    public final Context mContext;
    public FragmentController mFragments;
    public final LeakDetector mLeakDetector;
    public AnonymousClass1 mLifecycleCallbacks;
    public final FragmentService mManager;
    public final ExtensionFragmentManager mPlugins;
    public final View mRootView;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final HashMap mListeners = new HashMap();

    public final class ExtensionFragmentManager {
        public final ArrayMap mExtensionLookup = new ArrayMap();

        public ExtensionFragmentManager() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final Fragment instantiate(Context context, String str, Bundle bundle) {
            Context context2 = (Context) this.mExtensionLookup.get(str);
            if (context2 == null) {
                return instantiateWithInjections(context, str, bundle);
            }
            Fragment instantiateWithInjections = instantiateWithInjections(context2, str, bundle);
            if (instantiateWithInjections instanceof Plugin) {
                ((Plugin) instantiateWithInjections).onCreate(FragmentHostManager.this.mContext, context2);
            }
            return instantiateWithInjections;
        }

        public final Fragment instantiateWithInjections(Context context, String str, Bundle bundle) {
            Provider provider = (Provider) FragmentHostManager.this.mManager.mInjectionMap.get(str);
            if (provider == null) {
                return Fragment.instantiate(context, str, bundle);
            }
            Fragment fragment = (Fragment) provider.get();
            if (bundle != null) {
                bundle.setClassLoader(fragment.getClass().getClassLoader());
                fragment.setArguments(bundle);
            }
            return fragment;
        }
    }

    public interface Factory {
        FragmentHostManager create(View view);
    }

    public FragmentHostManager(View view, FragmentService fragmentService, LeakDetector leakDetector) {
        InterestingConfigChanges interestingConfigChanges = new InterestingConfigChanges(-1073672188);
        this.mConfigChanges = interestingConfigChanges;
        this.mPlugins = new ExtensionFragmentManager();
        Context context = view.getContext();
        this.mContext = context;
        this.mManager = fragmentService;
        this.mRootView = view;
        this.mLeakDetector = leakDetector;
        interestingConfigChanges.applyNewConfig(context.getResources());
        createFragmentHost(null);
    }

    public final void addTagListener(String str, FragmentListener fragmentListener) {
        ArrayList arrayList = (ArrayList) this.mListeners.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.mListeners.put(str, arrayList);
        }
        arrayList.add(fragmentListener);
        Fragment findFragmentByTag = this.mFragments.getFragmentManager().findFragmentByTag(str);
        if (findFragmentByTag == null || findFragmentByTag.getView() == null) {
            return;
        }
        fragmentListener.onFragmentViewCreated(findFragmentByTag);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.fragments.FragmentHostManager$1] */
    public final void createFragmentHost(Parcelable parcelable) {
        FragmentController createController = FragmentController.createController(new HostCallbacks());
        this.mFragments = createController;
        createController.attachHost(null);
        this.mLifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() { // from class: com.android.systemui.fragments.FragmentHostManager.1
            @Override // android.app.FragmentManager.FragmentLifecycleCallbacks
            public final void onFragmentDestroyed(FragmentManager fragmentManager, Fragment fragment) {
                FragmentHostManager.this.mLeakDetector.trackGarbage(fragment);
            }

            @Override // android.app.FragmentManager.FragmentLifecycleCallbacks
            public final void onFragmentViewCreated(FragmentManager fragmentManager, Fragment fragment, View view, Bundle bundle) {
                FragmentHostManager fragmentHostManager = FragmentHostManager.this;
                fragmentHostManager.getClass();
                String tag = fragment.getTag();
                ArrayList arrayList = (ArrayList) fragmentHostManager.mListeners.get(tag);
                if (arrayList != null) {
                    arrayList.forEach(new FragmentHostManager$$ExternalSyntheticLambda0(tag, fragment, 0));
                }
            }

            @Override // android.app.FragmentManager.FragmentLifecycleCallbacks
            public final void onFragmentViewDestroyed(FragmentManager fragmentManager, Fragment fragment) {
                FragmentHostManager fragmentHostManager = FragmentHostManager.this;
                fragmentHostManager.getClass();
                String tag = fragment.getTag();
                ArrayList arrayList = (ArrayList) fragmentHostManager.mListeners.get(tag);
                if (arrayList != null) {
                    arrayList.forEach(new FragmentHostManager$$ExternalSyntheticLambda0(tag, fragment, 1));
                }
            }
        };
        this.mFragments.getFragmentManager().registerFragmentLifecycleCallbacks(this.mLifecycleCallbacks, true);
        if (parcelable != null) {
            this.mFragments.restoreAllState(parcelable, (FragmentManagerNonConfig) null);
        }
        this.mFragments.dispatchCreate();
        this.mFragments.dispatchStart();
        this.mFragments.dispatchResume();
    }

    public final void reloadFragments() {
        Trace.beginSection("FrargmentHostManager#reloadFragments");
        this.mFragments.dispatchPause();
        Parcelable saveAllState = this.mFragments.saveAllState();
        this.mFragments.dispatchStop();
        this.mFragments.dispatchDestroy();
        this.mFragments.getFragmentManager().unregisterFragmentLifecycleCallbacks(this.mLifecycleCallbacks);
        createFragmentHost(saveAllState);
        Trace.endSection();
    }

    public interface FragmentListener {
        void onFragmentViewCreated(Fragment fragment);

        default void onFragmentViewDestroyed(Fragment fragment) {
        }
    }

    public final class HostCallbacks extends FragmentHostCallback {
        public HostCallbacks() {
            super(FragmentHostManager.this.mContext, FragmentHostManager.this.mHandler, 0);
        }

        public final Fragment instantiate(Context context, String str, Bundle bundle) {
            return FragmentHostManager.this.mPlugins.instantiate(context, str, bundle);
        }

        @Override // android.app.FragmentHostCallback
        public final void onDump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            FragmentHostManager.this.getClass();
        }

        @Override // android.app.FragmentHostCallback, android.app.FragmentContainer
        public final View onFindViewById(int i) {
            return FragmentHostManager.this.mRootView.findViewById(i);
        }

        @Override // android.app.FragmentHostCallback
        public final Object onGetHost() {
            return FragmentHostManager.this;
        }

        @Override // android.app.FragmentHostCallback
        public final LayoutInflater onGetLayoutInflater() {
            return LayoutInflater.from(FragmentHostManager.this.mContext);
        }

        @Override // android.app.FragmentHostCallback
        public final int onGetWindowAnimations() {
            return 0;
        }

        @Override // android.app.FragmentHostCallback, android.app.FragmentContainer
        public final boolean onHasView() {
            return true;
        }

        @Override // android.app.FragmentHostCallback
        public final boolean onHasWindowAnimations() {
            return false;
        }

        @Override // android.app.FragmentHostCallback
        public final boolean onShouldSaveFragmentState(Fragment fragment) {
            return true;
        }

        @Override // android.app.FragmentHostCallback
        public final boolean onUseFragmentManagerInflaterFactory() {
            return true;
        }

        @Override // android.app.FragmentHostCallback
        public final void onAttachFragment(Fragment fragment) {
        }
    }
}
