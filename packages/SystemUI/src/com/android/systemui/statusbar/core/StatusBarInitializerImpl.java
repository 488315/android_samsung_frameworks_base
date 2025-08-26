package com.android.systemui.statusbar.core;

import android.app.Fragment;
import com.android.systemui.R;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.core.StatusBarInitializer;
import com.android.systemui.statusbar.data.repository.StatusBarConfigurationController;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda24;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.phone.fragment.dagger.HomeStatusBarComponent;
import com.android.systemui.statusbar.pipeline.shared.ui.composable.StatusBarRootFactory;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import java.util.Set;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class StatusBarInitializerImpl implements StatusBarInitializer {
    public final Provider collapsedStatusBarFragmentProvider;
    public HomeStatusBarComponent component;
    public final Set creationListeners;
    public final DarkIconDispatcher darkIconDispatcher;
    public boolean initialized;
    public CentralSurfacesImpl$$ExternalSyntheticLambda24 statusBarViewUpdatedListener;
    public final StatusBarWindowController statusBarWindowController;

    public StatusBarInitializerImpl(StatusBarWindowController statusBarWindowController, StatusBarModePerDisplayRepository statusBarModePerDisplayRepository, StatusBarConfigurationController statusBarConfigurationController, DarkIconDispatcher darkIconDispatcher, Provider provider, StatusBarRootFactory statusBarRootFactory, HomeStatusBarComponent.Factory factory, Set<StatusBarInitializer.OnStatusBarViewInitializedListener> set) {
        this.statusBarWindowController = statusBarWindowController;
        this.darkIconDispatcher = darkIconDispatcher;
        this.collapsedStatusBarFragmentProvider = provider;
        this.creationListeners = set;
    }

    public final void doStart() {
        this.initialized = true;
        StatusBarWindowControllerImpl statusBarWindowControllerImpl = (StatusBarWindowControllerImpl) this.statusBarWindowController;
        FragmentHostManager fragmentHostManager = statusBarWindowControllerImpl.mFragmentService.getFragmentHostManager(statusBarWindowControllerImpl.mStatusBarWindowView);
        fragmentHostManager.addTagListener("CollapsedStatusBarFragment", new FragmentHostManager.FragmentListener() { // from class: com.android.systemui.statusbar.core.StatusBarInitializerImpl$doLegacyStart$1
            @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
            public final void onFragmentViewCreated(Fragment fragment) {
                HomeStatusBarComponent homeStatusBarComponent = ((CollapsedStatusBarFragment) fragment).mHomeStatusBarComponent;
                if (homeStatusBarComponent == null) {
                    return;
                }
                StatusBarInitializerImpl statusBarInitializerImpl = this.this$0;
                statusBarInitializerImpl.component = homeStatusBarComponent;
                CentralSurfacesImpl$$ExternalSyntheticLambda24 centralSurfacesImpl$$ExternalSyntheticLambda24 = statusBarInitializerImpl.statusBarViewUpdatedListener;
                if (centralSurfacesImpl$$ExternalSyntheticLambda24 != null) {
                    PhoneStatusBarViewController phoneStatusBarViewController = homeStatusBarComponent.getPhoneStatusBarViewController();
                    HomeStatusBarComponent homeStatusBarComponent2 = statusBarInitializerImpl.component;
                    homeStatusBarComponent2.getClass();
                    centralSurfacesImpl$$ExternalSyntheticLambda24.onStatusBarViewUpdated(phoneStatusBarViewController, homeStatusBarComponent2.getPhoneStatusBarTransitions());
                }
                for (StatusBarInitializer.OnStatusBarViewInitializedListener onStatusBarViewInitializedListener : statusBarInitializerImpl.creationListeners) {
                    HomeStatusBarComponent homeStatusBarComponent3 = statusBarInitializerImpl.component;
                    homeStatusBarComponent3.getClass();
                    onStatusBarViewInitializedListener.onStatusBarViewInitialized(homeStatusBarComponent3);
                }
            }

            @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
            public final void onFragmentViewDestroyed(Fragment fragment) {
            }
        });
        fragmentHostManager.mFragments.getFragmentManager().beginTransaction().replace(R.id.status_bar_container, (Fragment) this.collapsedStatusBarFragmentProvider.get(), "CollapsedStatusBarFragment").commit();
    }

    public final boolean getInitialized() {
        return this.initialized;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        doStart();
    }
}
