package com.android.systemui.globalactions;

import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.CoreStartable;
import com.android.systemui.plugins.GlobalActions;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.inject.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GlobalActionsComponent implements CoreStartable, CommandQueue.Callbacks, GlobalActions.GlobalActionsManager {
    public IStatusBarService mBarService;
    public final CommandQueue mCommandQueue;
    public ExtensionControllerImpl.ExtensionImpl mExtension;
    public final ExtensionController mExtensionController;
    public final Provider mGlobalActionsProvider;
    public GlobalActions mPlugin;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;

    public GlobalActionsComponent(CommandQueue commandQueue, ExtensionController extensionController, Provider provider, StatusBarKeyguardViewManager statusBarKeyguardViewManager) {
        this.mCommandQueue = commandQueue;
        this.mExtensionController = extensionController;
        this.mGlobalActionsProvider = provider;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void handleShowGlobalActionsMenu(int i) {
        this.mStatusBarKeyguardViewManager.setGlobalActionsVisible(true);
        ((GlobalActions) this.mExtension.mItem).showGlobalActions(this, i);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void handleShowShutdownUi(String str, boolean z) {
        ((GlobalActions) this.mExtension.mItem).showShutdownUi(z, str);
    }

    @Override // com.android.systemui.plugins.GlobalActions.GlobalActionsManager
    public final boolean isFOTAAvailableForGlobalActions() {
        try {
            return this.mBarService.isFOTAAvailableForGlobalActions();
        } catch (RemoteException unused) {
            return false;
        }
    }

    @Override // com.android.systemui.plugins.GlobalActions.GlobalActionsManager
    public final void onGlobalActionsHidden() {
        try {
            this.mStatusBarKeyguardViewManager.setGlobalActionsVisible(false);
            this.mBarService.onGlobalActionsHidden();
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.systemui.plugins.GlobalActions.GlobalActionsManager
    public final void onGlobalActionsShown() {
        try {
            this.mBarService.onGlobalActionsShown();
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.systemui.plugins.GlobalActions.GlobalActionsManager
    public final void reboot(boolean z) {
        try {
            this.mBarService.reboot(z);
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.systemui.plugins.GlobalActions.GlobalActionsManager
    public final void shutdown() {
        try {
            this.mBarService.shutdown();
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        ExtensionControllerImpl extensionControllerImpl = (ExtensionControllerImpl) this.mExtensionController;
        extensionControllerImpl.getClass();
        ExtensionControllerImpl.ExtensionBuilder extensionBuilder = new ExtensionControllerImpl.ExtensionBuilder(extensionControllerImpl, 0);
        extensionBuilder.withPlugin(GlobalActions.class);
        final Provider provider = this.mGlobalActionsProvider;
        Objects.requireNonNull(provider);
        Supplier supplier = new Supplier() { // from class: com.android.systemui.globalactions.GlobalActionsComponent$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return (GlobalActions) Provider.this.get();
            }
        };
        ExtensionControllerImpl.ExtensionImpl extensionImpl = extensionBuilder.mExtension;
        extensionImpl.mProducers.add(new ExtensionControllerImpl.ExtensionImpl.Default(extensionImpl, supplier));
        extensionBuilder.mExtension.mCallbacks.add(new Consumer() { // from class: com.android.systemui.globalactions.GlobalActionsComponent$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                GlobalActionsComponent globalActionsComponent = GlobalActionsComponent.this;
                GlobalActions globalActions = (GlobalActions) obj;
                GlobalActions globalActions2 = globalActionsComponent.mPlugin;
                if (globalActions2 != null) {
                    globalActions2.destroy();
                }
                globalActionsComponent.mPlugin = globalActions;
            }
        });
        ExtensionControllerImpl.ExtensionImpl build = extensionBuilder.build();
        this.mExtension = build;
        this.mPlugin = (GlobalActions) build.mItem;
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
    }
}
