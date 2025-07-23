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
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class FragmentActivity extends ComponentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mCreated;
    public final LifecycleRegistry mFragmentLifecycleRegistry;
    public final FragmentController mFragments;
    public boolean mResumed;
    public boolean mStopped;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class HostCallbacks extends FragmentHostCallback implements OnConfigurationChangedProvider, OnTrimMemoryProvider, OnMultiWindowModeChangedProvider, OnPictureInPictureModeChangedProvider, ViewModelStoreOwner, OnBackPressedDispatcherOwner, ActivityResultRegistryOwner, SavedStateRegistryOwner, FragmentOnAttachListener, MenuHost {
        public HostCallbacks() {
            super(FragmentActivity.this);
        }

        @Override // androidx.core.view.MenuHost
        public final void addMenuProvider(FragmentManager.AnonymousClass2 anonymousClass2) {
            FragmentActivity.this.addMenuProvider(anonymousClass2);
        }

        @Override // androidx.core.content.OnConfigurationChangedProvider
        public final void addOnConfigurationChangedListener(Consumer consumer) {
            FragmentActivity.this.addOnConfigurationChangedListener(consumer);
        }

        @Override // androidx.core.app.OnMultiWindowModeChangedProvider
        public final void addOnMultiWindowModeChangedListener(FragmentManager$$ExternalSyntheticLambda1 fragmentManager$$ExternalSyntheticLambda1) {
            FragmentActivity.this.addOnMultiWindowModeChangedListener(fragmentManager$$ExternalSyntheticLambda1);
        }

        @Override // androidx.core.app.OnPictureInPictureModeChangedProvider
        public final void addOnPictureInPictureModeChangedListener(FragmentManager$$ExternalSyntheticLambda1 fragmentManager$$ExternalSyntheticLambda1) {
            FragmentActivity.this.addOnPictureInPictureModeChangedListener(fragmentManager$$ExternalSyntheticLambda1);
        }

        @Override // androidx.core.content.OnTrimMemoryProvider
        public final void addOnTrimMemoryListener(FragmentManager$$ExternalSyntheticLambda1 fragmentManager$$ExternalSyntheticLambda1) {
            FragmentActivity.this.addOnTrimMemoryListener(fragmentManager$$ExternalSyntheticLambda1);
        }

        @Override // androidx.activity.result.ActivityResultRegistryOwner
        public final ActivityResultRegistry getActivityResultRegistry() {
            return FragmentActivity.this.activityResultRegistry;
        }

        @Override // androidx.lifecycle.LifecycleOwner
        public final Lifecycle getLifecycle() {
            return FragmentActivity.this.mFragmentLifecycleRegistry;
        }

        @Override // androidx.activity.OnBackPressedDispatcherOwner
        public final OnBackPressedDispatcher getOnBackPressedDispatcher() {
            return FragmentActivity.this.getOnBackPressedDispatcher();
        }

        @Override // androidx.savedstate.SavedStateRegistryOwner
        public final SavedStateRegistry getSavedStateRegistry() {
            return FragmentActivity.this.savedStateRegistryController.savedStateRegistry;
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
        public final void removeMenuProvider(FragmentManager.AnonymousClass2 anonymousClass2) {
            FragmentActivity.this.removeMenuProvider(anonymousClass2);
        }

        @Override // androidx.core.content.OnConfigurationChangedProvider
        public final void removeOnConfigurationChangedListener(Consumer consumer) {
            FragmentActivity.this.removeOnConfigurationChangedListener(consumer);
        }

        @Override // androidx.core.app.OnMultiWindowModeChangedProvider
        public final void removeOnMultiWindowModeChangedListener(FragmentManager$$ExternalSyntheticLambda1 fragmentManager$$ExternalSyntheticLambda1) {
            FragmentActivity.this.removeOnMultiWindowModeChangedListener(fragmentManager$$ExternalSyntheticLambda1);
        }

        @Override // androidx.core.app.OnPictureInPictureModeChangedProvider
        public final void removeOnPictureInPictureModeChangedListener(FragmentManager$$ExternalSyntheticLambda1 fragmentManager$$ExternalSyntheticLambda1) {
            FragmentActivity.this.removeOnPictureInPictureModeChangedListener(fragmentManager$$ExternalSyntheticLambda1);
        }

        @Override // androidx.core.content.OnTrimMemoryProvider
        public final void removeOnTrimMemoryListener(FragmentManager$$ExternalSyntheticLambda1 fragmentManager$$ExternalSyntheticLambda1) {
            FragmentActivity.this.removeOnTrimMemoryListener(fragmentManager$$ExternalSyntheticLambda1);
        }
    }

    public FragmentActivity() {
        this.mFragments = FragmentController.createController(new HostCallbacks());
        this.mFragmentLifecycleRegistry = new LifecycleRegistry(this);
        this.mStopped = true;
        init$4();
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
                    if (fragmentViewLifecycleOwner.mLifecycleRegistry.state.isAtLeast(Lifecycle.State.STARTED)) {
                        fragment.mViewLifecycleOwner.mLifecycleRegistry.setCurrentState(state);
                        z = true;
                    }
                }
                if (fragment.mLifecycleRegistry.state.isAtLeast(Lifecycle.State.STARTED)) {
                    fragment.mLifecycleRegistry.setCurrentState(state);
                    z = true;
                }
            }
        }
        return z;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @Override // android.app.Activity
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        if (strArr != null && strArr.length != 0) {
            String str2 = strArr[0];
            switch (str2.hashCode()) {
                case -645125871:
                    if (str2.equals("--translation")) {
                        return;
                    }
                    break;
                case 100470631:
                    if (str2.equals("--dump-dumpable")) {
                        return;
                    }
                    break;
                case 472614934:
                    if (str2.equals("--list-dumpables")) {
                        return;
                    }
                    break;
                case 1159329357:
                    if (str2.equals("--contentcapture")) {
                        return;
                    }
                    break;
                case 1455016274:
                    if (str2.equals("--autofill")) {
                        return;
                    }
                    break;
            }
        }
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
            new LoaderManagerImpl(this, getViewModelStore()).dump(printWriter, str3);
        }
        this.mFragments.mHost.fragmentManager.dump(str, fileDescriptor, printWriter, strArr);
    }

    public final FragmentManagerImpl getSupportFragmentManager() {
        return this.mFragments.mHost.fragmentManager;
    }

    public final void init$4() {
        this.savedStateRegistryController.savedStateRegistry.registerSavedStateProvider("android:support:lifecycle", new SavedStateRegistry.SavedStateProvider() { // from class: androidx.fragment.app.FragmentActivity$$ExternalSyntheticLambda0
            @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
            public final Bundle saveState() {
                FragmentActivity fragmentActivity;
                int i = FragmentActivity.$r8$clinit;
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
                switch (i) {
                    case 0:
                        this.f$0.mFragments.noteStateNotSaved();
                        break;
                    default:
                        this.f$0.mFragments.noteStateNotSaved();
                        break;
                }
            }
        });
        final int i2 = 1;
        this.onNewIntentListeners.add(new Consumer(this) { // from class: androidx.fragment.app.FragmentActivity$$ExternalSyntheticLambda1
            public final /* synthetic */ FragmentActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                switch (i2) {
                    case 0:
                        this.f$0.mFragments.noteStateNotSaved();
                        break;
                    default:
                        this.f$0.mFragments.noteStateNotSaved();
                        break;
                }
            }
        });
        addOnContextAvailableListener(new OnContextAvailableListener() { // from class: androidx.fragment.app.FragmentActivity$$ExternalSyntheticLambda3
            @Override // androidx.activity.contextaware.OnContextAvailableListener
            public final void onContextAvailable() {
                FragmentHostCallback fragmentHostCallback = FragmentActivity.this.mFragments.mHost;
                fragmentHostCallback.fragmentManager.attachController(fragmentHostCallback, fragmentHostCallback, null);
            }
        });
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        this.mFragments.noteStateNotSaved();
        super.onActivityResult(i, i2, intent);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        Fragment$$ExternalSyntheticLambda1 fragment$$ExternalSyntheticLambda1;
        FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.getClass();
        Fragment fragment = (Fragment) CollectionsKt___CollectionsKt.getOrNull(0, supportFragmentManager.mFragmentStore.getFragments());
        if (fragment != null && (fragment$$ExternalSyntheticLambda1 = fragment.mDisposableHandle) != null) {
            fragment$$ExternalSyntheticLambda1.dispose();
        }
        super.onBackPressed();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        FragmentManagerImpl fragmentManagerImpl = this.mFragments.mHost.fragmentManager;
        fragmentManagerImpl.mStateSaved = false;
        fragmentManagerImpl.mStopped = false;
        fragmentManagerImpl.mNonConfig.mIsStateSaved = false;
        fragmentManagerImpl.dispatchStateChange(1);
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View onCreateView = this.mFragments.mHost.fragmentManager.mLayoutInflaterFactory.onCreateView(view, str, context, attributeSet);
        return onCreateView == null ? super.onCreateView(view, str, context, attributeSet) : onCreateView;
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mFragments.mHost.fragmentManager.dispatchDestroy();
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        if (super.onMenuItemSelected(i, menuItem)) {
            return true;
        }
        if (i == 6) {
            return this.mFragments.mHost.fragmentManager.dispatchContextItemSelected();
        }
        return false;
    }

    @Override // android.app.Activity
    public void onPause() {
        super.onPause();
        this.mResumed = false;
        this.mFragments.mHost.fragmentManager.dispatchStateChange(5);
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    @Override // android.app.Activity
    public void onPostResume() {
        super.onPostResume();
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        FragmentManagerImpl fragmentManagerImpl = this.mFragments.mHost.fragmentManager;
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
        fragmentController.mHost.fragmentManager.execPendingActions(true);
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        this.mStopped = false;
        boolean z = this.mCreated;
        FragmentController fragmentController = this.mFragments;
        if (!z) {
            this.mCreated = true;
            FragmentManagerImpl fragmentManagerImpl = fragmentController.mHost.fragmentManager;
            fragmentManagerImpl.mStateSaved = false;
            fragmentManagerImpl.mStopped = false;
            fragmentManagerImpl.mNonConfig.mIsStateSaved = false;
            fragmentManagerImpl.dispatchStateChange(4);
        }
        fragmentController.noteStateNotSaved();
        FragmentHostCallback fragmentHostCallback = fragmentController.mHost;
        fragmentHostCallback.fragmentManager.execPendingActions(true);
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        FragmentManagerImpl fragmentManagerImpl2 = fragmentHostCallback.fragmentManager;
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
        FragmentManagerImpl fragmentManagerImpl = this.mFragments.mHost.fragmentManager;
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
        init$4();
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        View onCreateView = this.mFragments.mHost.fragmentManager.mLayoutInflaterFactory.onCreateView(null, str, context, attributeSet);
        return onCreateView == null ? super.onCreateView(str, context, attributeSet) : onCreateView;
    }
}
