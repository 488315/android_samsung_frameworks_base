package com.android.server.wm;

import android.app.servertransaction.WindowContextInfoChangeItem;
import android.app.servertransaction.WindowContextWindowRemovalItem;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.view.Display;
import android.window.WindowProviderService;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WindowContextListenerController {
    final ArrayMap mListeners = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class WindowContextListenerImpl implements WindowContainerListener {
        public final IBinder mClientToken;
        public WindowContainer mContainer;
        public DeathRecipient mDeathRecipient;
        public boolean mHasPendingConfiguration;
        public Configuration mLastReportedConfig;
        public int mLastReportedDisplay = -1;
        public final Bundle mOptions;
        public final int mType;
        public final WindowProcessController mWpc;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class DeathRecipient implements IBinder.DeathRecipient {
            public DeathRecipient() {}

            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                WindowManagerGlobalLock windowManagerGlobalLock =
                        WindowContextListenerImpl.this.mContainer.mWmService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        WindowContextListenerImpl windowContextListenerImpl =
                                WindowContextListenerImpl.this;
                        windowContextListenerImpl.mDeathRecipient = null;
                        windowContextListenerImpl.unregister();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }

        public WindowContextListenerImpl(
                WindowProcessController windowProcessController,
                IBinder iBinder,
                WindowContainer windowContainer,
                int i,
                Bundle bundle) {
            this.mWpc = windowProcessController;
            this.mClientToken = iBinder;
            Objects.requireNonNull(windowContainer);
            this.mContainer = windowContainer;
            this.mType = i;
            this.mOptions = bundle;
            DeathRecipient deathRecipient = new DeathRecipient();
            try {
                iBinder.linkToDeath(deathRecipient, 0);
                this.mDeathRecipient = deathRecipient;
            } catch (RemoteException unused) {
                if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[4]) {
                    ProtoLogImpl_54989576.e(
                            ProtoLogGroup.WM_ERROR,
                            6139364662459841509L,
                            0,
                            "Could not register window container listener token=%s, container=%s",
                            String.valueOf(iBinder),
                            String.valueOf(this.mContainer));
                }
            }
        }

        public final void dispatchWindowContextInfoChange() {
            if (this.mDeathRecipient == null) {
                throw new IllegalStateException("Invalid client token: " + this.mClientToken);
            }
            DisplayContent displayContent = this.mContainer.getDisplayContent();
            if (displayContent.isReady()) {
                if (!WindowProviderService.isWindowProviderService(this.mOptions)
                        && Display.isSuspendedState(displayContent.mDisplayInfo.state)) {
                    this.mHasPendingConfiguration = true;
                    return;
                }
                Configuration configuration = this.mContainer.getConfiguration();
                int i = displayContent.mDisplayId;
                if (this.mLastReportedConfig == null) {
                    this.mLastReportedConfig = new Configuration();
                }
                if (configuration.equals(this.mLastReportedConfig)
                        && i == this.mLastReportedDisplay) {
                    return;
                }
                this.mLastReportedConfig.setTo(configuration);
                this.mLastReportedDisplay = i;
                this.mWpc.scheduleClientTransactionItem(
                        WindowContextInfoChangeItem.obtain(this.mClientToken, configuration, i));
                this.mHasPendingConfiguration = false;
            }
        }

        public WindowContainer getWindowContainer() {
            return this.mContainer;
        }

        @Override // com.android.server.wm.WindowContainerListener
        public final void onDisplayChanged(DisplayContent displayContent) {
            dispatchWindowContextInfoChange();
        }

        @Override // com.android.server.wm.ConfigurationContainerListener
        public final void onMergedOverrideConfigurationChanged(Configuration configuration) {
            dispatchWindowContextInfoChange();
        }

        @Override // com.android.server.wm.WindowContainerListener
        public final void onRemoved() {
            DisplayContent displayContent;
            if (this.mDeathRecipient == null) {
                throw new IllegalStateException("Invalid client token: " + this.mClientToken);
            }
            WindowToken asWindowToken = this.mContainer.asWindowToken();
            if (asWindowToken != null
                    && asWindowToken.mFromClientToken
                    && (displayContent =
                                    asWindowToken.mWmService.mRoot.getDisplayContent(
                                            this.mLastReportedDisplay))
                            != null) {
                updateContainer(
                        displayContent.findAreaForWindowType(
                                asWindowToken.windowType,
                                asWindowToken.mOptions,
                                asWindowToken.mOwnerCanManageAppTokens,
                                asWindowToken.mRoundedCornerOverlay));
                return;
            }
            DeathRecipient deathRecipient = this.mDeathRecipient;
            WindowContextListenerImpl.this.mClientToken.unlinkToDeath(deathRecipient, 0);
            this.mWpc.scheduleClientTransactionItem(
                    WindowContextWindowRemovalItem.obtain(this.mClientToken));
            unregister();
        }

        public final void register(boolean z) {
            IBinder iBinder = this.mClientToken;
            if (this.mDeathRecipient != null) {
                WindowContextListenerController.this.mListeners.putIfAbsent(iBinder, this);
                this.mContainer.registerWindowContainerListener(this, z);
            } else {
                throw new IllegalStateException("Invalid client token: " + iBinder);
            }
        }

        public final String toString() {
            return "WindowContextListenerImpl{clientToken="
                    + this.mClientToken
                    + ", container="
                    + this.mContainer
                    + "}";
        }

        public final void unregister() {
            this.mContainer.unregisterWindowContainerListener(this);
            WindowContextListenerController.this.mListeners.remove(this.mClientToken);
        }

        public final void updateContainer(WindowContainer windowContainer) {
            Objects.requireNonNull(windowContainer);
            if (this.mContainer.equals(windowContainer)) {
                return;
            }
            this.mContainer.unregisterWindowContainerListener(this);
            this.mContainer = windowContainer;
            this.mLastReportedConfig = null;
            this.mLastReportedDisplay = -1;
            register(true);
        }
    }

    public final boolean assertCallerCanModifyListener(int i, boolean z, IBinder iBinder) {
        WindowContextListenerImpl windowContextListenerImpl =
                (WindowContextListenerImpl) this.mListeners.get(iBinder);
        if (windowContextListenerImpl == null) {
            if (!ProtoLogImpl_54989576.Cache.WM_DEBUG_ADD_REMOVE_enabled[2]) {
                return false;
            }
            ProtoLogImpl_54989576.i(
                    ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 2163930285157267092L, 0, null, null);
            return false;
        }
        if (z) {
            return true;
        }
        WindowProcessController windowProcessController = windowContextListenerImpl.mWpc;
        if (i == windowProcessController.mUid) {
            return true;
        }
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        i, "Uid mismatch. Caller uid is ", ", while the listener's owner is from ");
        m.append(windowProcessController.mUid);
        throw new UnsupportedOperationException(m.toString());
    }

    public final void registerWindowContainerListener(
            WindowProcessController windowProcessController,
            IBinder iBinder,
            WindowContainer windowContainer,
            int i,
            Bundle bundle) {
        if (((WindowContextListenerImpl) this.mListeners.get(iBinder)) == null) {
            new WindowContextListenerImpl(
                            windowProcessController, iBinder, windowContainer, i, bundle)
                    .register(false);
        } else {
            updateContainerForWindowContextListener(iBinder, windowContainer);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("WindowContextListenerController{mListeners=[");
        int size = this.mListeners.values().size();
        for (int i = 0; i < size; i++) {
            sb.append(this.mListeners.valueAt(i));
            if (i != size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }

    public final void updateContainerForWindowContextListener(
            IBinder iBinder, WindowContainer windowContainer) {
        WindowContextListenerImpl windowContextListenerImpl =
                (WindowContextListenerImpl) this.mListeners.get(iBinder);
        if (windowContextListenerImpl != null) {
            windowContextListenerImpl.updateContainer(windowContainer);
        } else {
            throw new IllegalArgumentException("Can't find listener for " + iBinder);
        }
    }
}
