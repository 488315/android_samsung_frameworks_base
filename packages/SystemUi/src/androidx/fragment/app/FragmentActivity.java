package androidx.fragment.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.contextaware.OnContextAvailableListener;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.core.app.OnMultiWindowModeChangedProvider;
import androidx.core.app.OnPictureInPictureModeChangedProvider;
import androidx.core.content.OnConfigurationChangedProvider;
import androidx.core.content.OnTrimMemoryProvider;
import androidx.core.util.Consumer;
import androidx.core.view.MenuHost;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.app.LoaderManagerImpl;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class FragmentActivity extends ComponentActivity {
    public boolean mCreated;
    public final LifecycleRegistry mFragmentLifecycleRegistry;
    public final FragmentController mFragments;
    public boolean mResumed;
    public boolean mStopped;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HostCallbacks extends FragmentHostCallback implements OnConfigurationChangedProvider, OnTrimMemoryProvider, OnMultiWindowModeChangedProvider, OnPictureInPictureModeChangedProvider, ViewModelStoreOwner, OnBackPressedDispatcherOwner, ActivityResultRegistryOwner, SavedStateRegistryOwner, FragmentOnAttachListener, MenuHost {
        public HostCallbacks() {
            super(FragmentActivity.this);
        }

        @Override // androidx.core.view.MenuHost
        public final void addMenuProvider(FragmentManager.C02322 c02322) {
            FragmentActivity.this.addMenuProvider(c02322);
        }

        @Override // androidx.core.content.OnConfigurationChangedProvider
        public final void addOnConfigurationChangedListener(Consumer consumer) {
            FragmentActivity.this.addOnConfigurationChangedListener(consumer);
        }

        @Override // androidx.core.app.OnMultiWindowModeChangedProvider
        public final void addOnMultiWindowModeChangedListener(FragmentManager$$ExternalSyntheticLambda0 fragmentManager$$ExternalSyntheticLambda0) {
            FragmentActivity.this.addOnMultiWindowModeChangedListener(fragmentManager$$ExternalSyntheticLambda0);
        }

        @Override // androidx.core.app.OnPictureInPictureModeChangedProvider
        public final void addOnPictureInPictureModeChangedListener(FragmentManager$$ExternalSyntheticLambda0 fragmentManager$$ExternalSyntheticLambda0) {
            FragmentActivity.this.addOnPictureInPictureModeChangedListener(fragmentManager$$ExternalSyntheticLambda0);
        }

        @Override // androidx.core.content.OnTrimMemoryProvider
        public final void addOnTrimMemoryListener(FragmentManager$$ExternalSyntheticLambda0 fragmentManager$$ExternalSyntheticLambda0) {
            FragmentActivity.this.addOnTrimMemoryListener(fragmentManager$$ExternalSyntheticLambda0);
        }

        @Override // androidx.activity.result.ActivityResultRegistryOwner
        public final ActivityResultRegistry getActivityResultRegistry() {
            return FragmentActivity.this.mActivityResultRegistry;
        }

        @Override // androidx.lifecycle.LifecycleOwner
        public final LifecycleRegistry getLifecycle() {
            return FragmentActivity.this.mFragmentLifecycleRegistry;
        }

        @Override // androidx.activity.OnBackPressedDispatcherOwner
        public final OnBackPressedDispatcher getOnBackPressedDispatcher() {
            return FragmentActivity.this.mOnBackPressedDispatcher;
        }

        @Override // androidx.savedstate.SavedStateRegistryOwner
        public final SavedStateRegistry getSavedStateRegistry() {
            return FragmentActivity.this.mSavedStateRegistryController.savedStateRegistry;
        }

        @Override // androidx.lifecycle.ViewModelStoreOwner
        public final ViewModelStore getViewModelStore() {
            return FragmentActivity.this.getViewModelStore();
        }

        @Override // androidx.fragment.app.FragmentOnAttachListener
        public final void onAttachFragment$1() {
            FragmentActivity.this.getClass();
        }

        @Override // androidx.fragment.app.FragmentHostCallback
        public final void onDump(PrintWriter printWriter, String[] strArr) {
            FragmentActivity.this.dump("  ", null, printWriter, strArr);
        }

        @Override // androidx.fragment.app.FragmentHostCallback, androidx.fragment.app.FragmentContainer
        public final View onFindViewById(int i) {
            return FragmentActivity.this.findViewById(i);
        }

        @Override // androidx.fragment.app.FragmentHostCallback
        public final FragmentActivity onGetHost$1() {
            return FragmentActivity.this;
        }

        @Override // androidx.fragment.app.FragmentHostCallback
        public final LayoutInflater onGetLayoutInflater() {
            FragmentActivity fragmentActivity = FragmentActivity.this;
            return fragmentActivity.getLayoutInflater().cloneInContext(fragmentActivity);
        }

        @Override // androidx.fragment.app.FragmentHostCallback, androidx.fragment.app.FragmentContainer
        public final boolean onHasView() {
            Window window = FragmentActivity.this.getWindow();
            return (window == null || window.peekDecorView() == null) ? false : true;
        }

        @Override // androidx.fragment.app.FragmentHostCallback
        public final void onSupportInvalidateOptionsMenu() {
            FragmentActivity.this.invalidateOptionsMenu();
        }

        @Override // androidx.core.view.MenuHost
        public final void removeMenuProvider(FragmentManager.C02322 c02322) {
            FragmentActivity.this.removeMenuProvider(c02322);
        }

        @Override // androidx.core.content.OnConfigurationChangedProvider
        public final void removeOnConfigurationChangedListener(FragmentManager$$ExternalSyntheticLambda0 fragmentManager$$ExternalSyntheticLambda0) {
            FragmentActivity.this.removeOnConfigurationChangedListener(fragmentManager$$ExternalSyntheticLambda0);
        }

        @Override // androidx.core.app.OnMultiWindowModeChangedProvider
        public final void removeOnMultiWindowModeChangedListener(FragmentManager$$ExternalSyntheticLambda0 fragmentManager$$ExternalSyntheticLambda0) {
            FragmentActivity.this.removeOnMultiWindowModeChangedListener(fragmentManager$$ExternalSyntheticLambda0);
        }

        @Override // androidx.core.app.OnPictureInPictureModeChangedProvider
        public final void removeOnPictureInPictureModeChangedListener(FragmentManager$$ExternalSyntheticLambda0 fragmentManager$$ExternalSyntheticLambda0) {
            FragmentActivity.this.removeOnPictureInPictureModeChangedListener(fragmentManager$$ExternalSyntheticLambda0);
        }

        @Override // androidx.core.content.OnTrimMemoryProvider
        public final void removeOnTrimMemoryListener(FragmentManager$$ExternalSyntheticLambda0 fragmentManager$$ExternalSyntheticLambda0) {
            FragmentActivity.this.removeOnTrimMemoryListener(fragmentManager$$ExternalSyntheticLambda0);
        }
    }

    public FragmentActivity() {
        this.mFragments = FragmentController.createController(new HostCallbacks());
        this.mFragmentLifecycleRegistry = new LifecycleRegistry(this);
        this.mStopped = true;
        init();
    }

    private void init() {
        this.mSavedStateRegistryController.savedStateRegistry.registerSavedStateProvider("android:support:lifecycle", new SavedStateRegistry.SavedStateProvider() { // from class: androidx.fragment.app.FragmentActivity$$ExternalSyntheticLambda0
            @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
            public final Bundle saveState() {
                FragmentActivity fragmentActivity;
                do {
                    fragmentActivity = FragmentActivity.this;
                } while (FragmentActivity.markState(fragmentActivity.getSupportFragmentManager(), Lifecycle.State.CREATED));
                fragmentActivity.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
                return new Bundle();
            }
        });
        final int i = 0;
        addOnConfigurationChangedListener(new Consumer(this) { // from class: androidx.fragment.app.FragmentActivity$$ExternalSyntheticLambda1
            public final /* synthetic */ FragmentActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                FragmentActivity fragmentActivity = this.f$0;
                switch (i2) {
                    case 0:
                        fragmentActivity.mFragments.noteStateNotSaved();
                        break;
                    default:
                        fragmentActivity.mFragments.noteStateNotSaved();
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mOnNewIntentListeners.add(new Consumer(this) { // from class: androidx.fragment.app.FragmentActivity$$ExternalSyntheticLambda1
            public final /* synthetic */ FragmentActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                int i22 = i2;
                FragmentActivity fragmentActivity = this.f$0;
                switch (i22) {
                    case 0:
                        fragmentActivity.mFragments.noteStateNotSaved();
                        break;
                    default:
                        fragmentActivity.mFragments.noteStateNotSaved();
                        break;
                }
            }
        });
        addOnContextAvailableListener(new OnContextAvailableListener() { // from class: androidx.fragment.app.FragmentActivity$$ExternalSyntheticLambda2
            @Override // androidx.activity.contextaware.OnContextAvailableListener
            public final void onContextAvailable() {
                FragmentHostCallback fragmentHostCallback = FragmentActivity.this.mFragments.mHost;
                fragmentHostCallback.mFragmentManager.attachController(fragmentHostCallback, fragmentHostCallback, null);
            }
        });
    }

    public static boolean markState(FragmentManager fragmentManager, Lifecycle.State state) {
        boolean z = false;
        for (Fragment fragment : fragmentManager.mFragmentStore.getFragments()) {
            if (fragment != null) {
                FragmentHostCallback fragmentHostCallback = fragment.mHost;
                if ((fragmentHostCallback == null ? null : fragmentHostCallback.onGetHost$1()) != null) {
                    z |= markState(fragment.getChildFragmentManager(), state);
                }
                FragmentViewLifecycleOwner fragmentViewLifecycleOwner = fragment.mViewLifecycleOwner;
                if (fragmentViewLifecycleOwner != null) {
                    fragmentViewLifecycleOwner.initialize();
                    if (fragmentViewLifecycleOwner.mLifecycleRegistry.mState.isAtLeast(Lifecycle.State.STARTED)) {
                        fragment.mViewLifecycleOwner.mLifecycleRegistry.setCurrentState(state);
                        z = true;
                    }
                }
                if (fragment.mLifecycleRegistry.mState.isAtLeast(Lifecycle.State.STARTED)) {
                    fragment.mLifecycleRegistry.setCurrentState(state);
                    z = true;
                }
            }
        }
        return z;
    }

    @Override // android.app.Activity
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        boolean z = false;
        if (strArr != null && strArr.length > 0) {
            String str2 = strArr[0];
            str2.getClass();
            switch (str2) {
                case "--translation":
                case "--dump-dumpable":
                case "--list-dumpables":
                case "--contentcapture":
                case "--autofill":
                    z = true;
                    break;
            }
        }
        if (true ^ z) {
            printWriter.print(str);
            printWriter.print("Local FragmentActivity ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.println(" State:");
            String str3 = str + "  ";
            printWriter.print(str3);
            printWriter.print("mCreated=");
            printWriter.print(this.mCreated);
            printWriter.print(" mResumed=");
            printWriter.print(this.mResumed);
            printWriter.print(" mStopped=");
            printWriter.print(this.mStopped);
            if (getApplication() != null) {
                new LoaderManagerImpl(this, getViewModelStore()).dump(str3, fileDescriptor, printWriter, strArr);
            }
            this.mFragments.mHost.mFragmentManager.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    public final FragmentManagerImpl getSupportFragmentManager() {
        return this.mFragments.mHost.mFragmentManager;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        this.mFragments.noteStateNotSaved();
        super.onActivityResult(i, i2, intent);
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        FragmentManagerImpl fragmentManagerImpl = this.mFragments.mHost.mFragmentManager;
        fragmentManagerImpl.mStateSaved = false;
        fragmentManagerImpl.mStopped = false;
        fragmentManagerImpl.mNonConfig.mIsStateSaved = false;
        fragmentManagerImpl.dispatchStateChange(1);
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View onCreateView = this.mFragments.mHost.mFragmentManager.mLayoutInflaterFactory.onCreateView(view, str, context, attributeSet);
        return onCreateView == null ? super.onCreateView(view, str, context, attributeSet) : onCreateView;
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mFragments.mHost.mFragmentManager.dispatchDestroy();
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        if (super.onMenuItemSelected(i, menuItem)) {
            return true;
        }
        if (i == 6) {
            return this.mFragments.mHost.mFragmentManager.dispatchContextItemSelected();
        }
        return false;
    }

    @Override // android.app.Activity
    public void onPause() {
        super.onPause();
        this.mResumed = false;
        this.mFragments.mHost.mFragmentManager.dispatchStateChange(5);
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    @Override // android.app.Activity
    public void onPostResume() {
        super.onPostResume();
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        FragmentManagerImpl fragmentManagerImpl = this.mFragments.mHost.mFragmentManager;
        fragmentManagerImpl.mStateSaved = false;
        fragmentManagerImpl.mStopped = false;
        fragmentManagerImpl.mNonConfig.mIsStateSaved = false;
        fragmentManagerImpl.dispatchStateChange(7);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        this.mFragments.noteStateNotSaved();
        super.onRequestPermissionsResult(i, strArr, iArr);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        this.mResumed = true;
        FragmentController fragmentController = this.mFragments;
        fragmentController.noteStateNotSaved();
        fragmentController.mHost.mFragmentManager.execPendingActions(true);
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        this.mStopped = false;
        boolean z = this.mCreated;
        FragmentController fragmentController = this.mFragments;
        if (!z) {
            this.mCreated = true;
            FragmentManagerImpl fragmentManagerImpl = fragmentController.mHost.mFragmentManager;
            fragmentManagerImpl.mStateSaved = false;
            fragmentManagerImpl.mStopped = false;
            fragmentManagerImpl.mNonConfig.mIsStateSaved = false;
            fragmentManagerImpl.dispatchStateChange(4);
        }
        fragmentController.noteStateNotSaved();
        FragmentHostCallback fragmentHostCallback = fragmentController.mHost;
        fragmentHostCallback.mFragmentManager.execPendingActions(true);
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        FragmentManagerImpl fragmentManagerImpl2 = fragmentHostCallback.mFragmentManager;
        fragmentManagerImpl2.mStateSaved = false;
        fragmentManagerImpl2.mStopped = false;
        fragmentManagerImpl2.mNonConfig.mIsStateSaved = false;
        fragmentManagerImpl2.dispatchStateChange(5);
    }

    @Override // android.app.Activity
    public final void onStateNotSaved() {
        this.mFragments.noteStateNotSaved();
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
        this.mStopped = true;
        while (markState(getSupportFragmentManager(), Lifecycle.State.CREATED)) {
        }
        FragmentManagerImpl fragmentManagerImpl = this.mFragments.mHost.mFragmentManager;
        fragmentManagerImpl.mStopped = true;
        fragmentManagerImpl.mNonConfig.mIsStateSaved = true;
        fragmentManagerImpl.dispatchStateChange(4);
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    public FragmentActivity(int i) {
        super(i);
        this.mFragments = FragmentController.createController(new HostCallbacks());
        this.mFragmentLifecycleRegistry = new LifecycleRegistry(this);
        this.mStopped = true;
        init();
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        View onCreateView = this.mFragments.mHost.mFragmentManager.mLayoutInflaterFactory.onCreateView(null, str, context, attributeSet);
        return onCreateView == null ? super.onCreateView(str, context, attributeSet) : onCreateView;
    }
}
