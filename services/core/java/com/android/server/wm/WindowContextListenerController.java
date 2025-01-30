package com.android.server.wm;

import android.app.IWindowToken;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.view.Display;
import android.window.WindowProviderService;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import java.io.PrintWriter;
import java.util.Objects;

/* loaded from: classes3.dex */
public class WindowContextListenerController {
    final ArrayMap mListeners = new ArrayMap();

    public void registerWindowContainerListener(IBinder iBinder, WindowContainer windowContainer, int i, int i2, Bundle bundle) {
        registerWindowContainerListener(iBinder, windowContainer, i, i2, bundle, true);
    }

    public void registerWindowContainerListener(IBinder iBinder, WindowContainer windowContainer, int i, int i2, Bundle bundle, boolean z) {
        WindowContextListenerImpl windowContextListenerImpl = (WindowContextListenerImpl) this.mListeners.get(iBinder);
        if (windowContextListenerImpl == null) {
            new WindowContextListenerImpl(iBinder, windowContainer, i, i2, bundle).register(z);
        } else {
            windowContextListenerImpl.updateContainer(windowContainer);
        }
    }

    public void unregisterWindowContainerListener(IBinder iBinder) {
        WindowContextListenerImpl windowContextListenerImpl = (WindowContextListenerImpl) this.mListeners.get(iBinder);
        if (windowContextListenerImpl == null) {
            return;
        }
        windowContextListenerImpl.unregister();
        if (windowContextListenerImpl.mDeathRecipient != null) {
            windowContextListenerImpl.mDeathRecipient.unlinkToDeath();
        }
    }

    public void dispatchPendingConfigurationIfNeeded(int i) {
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            WindowContextListenerImpl windowContextListenerImpl = (WindowContextListenerImpl) this.mListeners.valueAt(size);
            if (windowContextListenerImpl.getWindowContainer().getDisplayContent().getDisplayId() == i && windowContextListenerImpl.mHasPendingConfiguration) {
                windowContextListenerImpl.reportConfigToWindowTokenClient();
            }
        }
    }

    public boolean assertCallerCanModifyListener(IBinder iBinder, boolean z, int i) {
        WindowContextListenerImpl windowContextListenerImpl = (WindowContextListenerImpl) this.mListeners.get(iBinder);
        if (windowContextListenerImpl == null) {
            if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
                ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -1136467585, 0, (String) null, (Object[]) null);
            }
            return false;
        }
        if (z || i == windowContextListenerImpl.mOwnerUid) {
            return true;
        }
        throw new UnsupportedOperationException("Uid mismatch. Caller uid is " + i + ", while the listener's owner is from " + windowContextListenerImpl.mOwnerUid);
    }

    public boolean hasListener(IBinder iBinder) {
        return this.mListeners.containsKey(iBinder);
    }

    public int getWindowType(IBinder iBinder) {
        WindowContextListenerImpl windowContextListenerImpl = (WindowContextListenerImpl) this.mListeners.get(iBinder);
        if (windowContextListenerImpl != null) {
            return windowContextListenerImpl.mType;
        }
        return -1;
    }

    public Bundle getOptions(IBinder iBinder) {
        WindowContextListenerImpl windowContextListenerImpl = (WindowContextListenerImpl) this.mListeners.get(iBinder);
        if (windowContextListenerImpl != null) {
            return windowContextListenerImpl.mOptions;
        }
        return null;
    }

    public WindowContainer getContainer(IBinder iBinder) {
        WindowContextListenerImpl windowContextListenerImpl = (WindowContextListenerImpl) this.mListeners.get(iBinder);
        if (windowContextListenerImpl != null) {
            return windowContextListenerImpl.mContainer;
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("WindowContextListenerController{");
        sb.append("mListeners=[");
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

    public void dump(PrintWriter printWriter, String str) {
        String str2 = str + "  ";
        printWriter.print(str);
        printWriter.println(WindowContextListenerController.class.getSimpleName());
        int size = this.mListeners.values().size();
        for (int i = 0; i < size; i++) {
            WindowContextListenerImpl windowContextListenerImpl = (WindowContextListenerImpl) this.mListeners.valueAt(i);
            printWriter.print(str2);
            printWriter.print("mListeners #");
            printWriter.print(i);
            printWriter.print(" {");
            printWriter.print("type:");
            printWriter.print(windowContextListenerImpl.mType);
            printWriter.print(", display:");
            printWriter.print(windowContextListenerImpl.mLastReportedDisplay);
            printWriter.print(", package=");
            printWriter.print(windowContextListenerImpl.mPackageName);
            printWriter.print(", container=");
            printWriter.print(windowContextListenerImpl.mContainer);
            printWriter.println("}");
            if (windowContextListenerImpl.mHasPendingConfiguration) {
                printWriter.print(str2);
                printWriter.println("  mHasPendingConfiguration=true");
            }
            printWriter.print(str2);
            printWriter.print("  mLastReportedConfig=");
            printWriter.println(windowContextListenerImpl.mLastReportedConfig);
        }
        printWriter.println();
    }

    class WindowContextListenerImpl implements WindowContainerListener {
        public final IWindowToken mClientToken;
        public WindowContainer mContainer;
        public DeathRecipient mDeathRecipient;
        public boolean mHasPendingConfiguration;
        public Configuration mLastReportedConfig;
        public int mLastReportedDisplay;
        public final Bundle mOptions;
        public final int mOwnerUid;
        public String mPackageName;
        public final int mType;

        public WindowContextListenerImpl(IBinder iBinder, WindowContainer windowContainer, int i, int i2, Bundle bundle) {
            this.mLastReportedDisplay = -1;
            this.mClientToken = IWindowToken.Stub.asInterface(iBinder);
            Objects.requireNonNull(windowContainer);
            this.mContainer = windowContainer;
            this.mOwnerUid = i;
            this.mType = i2;
            this.mOptions = bundle;
            DeathRecipient deathRecipient = new DeathRecipient();
            try {
                deathRecipient.linkToDeath();
                this.mDeathRecipient = deathRecipient;
            } catch (RemoteException unused) {
                if (ProtoLogCache.WM_ERROR_enabled) {
                    ProtoLogImpl.e(ProtoLogGroup.WM_ERROR, -2014162875, 0, "Could not register window container listener token=%s, container=%s", new Object[]{String.valueOf(iBinder), String.valueOf(this.mContainer)});
                }
            }
            this.mPackageName = this.mContainer.mWmService.mContext.getPackageManager().getNameForUid(this.mOwnerUid);
        }

        public WindowContainer getWindowContainer() {
            return this.mContainer;
        }

        public final void updateContainer(WindowContainer windowContainer) {
            Objects.requireNonNull(windowContainer);
            if (this.mContainer.equals(windowContainer)) {
                return;
            }
            this.mContainer.unregisterWindowContainerListener(this);
            this.mContainer = windowContainer;
            clear();
            register();
        }

        public final void register() {
            register(true);
        }

        public final void register(boolean z) {
            IBinder asBinder = this.mClientToken.asBinder();
            if (this.mDeathRecipient == null) {
                throw new IllegalStateException("Invalid client token: " + asBinder);
            }
            WindowContextListenerController.this.mListeners.putIfAbsent(asBinder, this);
            this.mContainer.registerWindowContainerListener(this, z);
        }

        public final void unregister() {
            this.mContainer.unregisterWindowContainerListener(this);
            WindowContextListenerController.this.mListeners.remove(this.mClientToken.asBinder());
        }

        public final void clear() {
            this.mLastReportedConfig = null;
            this.mLastReportedDisplay = -1;
        }

        @Override // com.android.server.wm.ConfigurationContainerListener
        public void onMergedOverrideConfigurationChanged(Configuration configuration) {
            reportConfigToWindowTokenClient();
        }

        @Override // com.android.server.wm.WindowContainerListener
        public void onDisplayChanged(DisplayContent displayContent) {
            reportConfigToWindowTokenClient();
        }

        public final void reportConfigToWindowTokenClient() {
            if (this.mDeathRecipient == null) {
                throw new IllegalStateException("Invalid client token: " + this.mClientToken.asBinder());
            }
            DisplayContent displayContent = this.mContainer.getDisplayContent();
            if (displayContent.isReady()) {
                if (!WindowProviderService.isWindowProviderService(this.mOptions) && Display.isSuspendedState(displayContent.getDisplayInfo().state)) {
                    this.mHasPendingConfiguration = true;
                    return;
                }
                Configuration configuration = this.mContainer.getConfiguration();
                int displayId = displayContent.getDisplayId();
                if (this.mLastReportedConfig == null) {
                    this.mLastReportedConfig = new Configuration();
                }
                if (configuration.equals(this.mLastReportedConfig) && displayId == this.mLastReportedDisplay) {
                    return;
                }
                this.mLastReportedConfig.setTo(configuration);
                this.mLastReportedDisplay = displayId;
                try {
                    this.mContainer.mWmService.mContext.getPackageManager().getNameForUid(this.mOwnerUid);
                    this.mClientToken.onConfigurationChanged(configuration, displayId);
                } catch (RemoteException unused) {
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1948483534, 0, "Could not report config changes to the window token client.", (Object[]) null);
                    }
                }
                this.mHasPendingConfiguration = false;
            }
        }

        @Override // com.android.server.wm.WindowContainerListener
        public void onRemoved() {
            DisplayContent displayContent;
            if (this.mDeathRecipient == null) {
                throw new IllegalStateException("Invalid client token: " + this.mClientToken.asBinder());
            }
            WindowToken asWindowToken = this.mContainer.asWindowToken();
            if (asWindowToken != null && asWindowToken.isFromClient() && (displayContent = asWindowToken.mWmService.mRoot.getDisplayContent(this.mLastReportedDisplay)) != null) {
                updateContainer(displayContent.findAreaForToken(asWindowToken));
                return;
            }
            this.mDeathRecipient.unlinkToDeath();
            try {
                this.mClientToken.onWindowTokenRemoved();
            } catch (RemoteException unused) {
                if (ProtoLogCache.WM_ERROR_enabled) {
                    ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 90764070, 0, "Could not report token removal to the window token client.", (Object[]) null);
                }
            }
            unregister();
        }

        public String toString() {
            return "WindowContextListenerImpl{clientToken=" + this.mClientToken.asBinder() + ", container=" + this.mContainer + ", " + this.mPackageName + "}";
        }

        public class DeathRecipient implements IBinder.DeathRecipient {
            public DeathRecipient() {
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                WindowManagerGlobalLock windowManagerGlobalLock = WindowContextListenerImpl.this.mContainer.mWmService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        WindowContextListenerImpl.this.mDeathRecipient = null;
                        WindowContextListenerImpl.this.unregister();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }

            public void linkToDeath() {
                WindowContextListenerImpl.this.mClientToken.asBinder().linkToDeath(this, 0);
            }

            public void unlinkToDeath() {
                WindowContextListenerImpl.this.mClientToken.asBinder().unlinkToDeath(this, 0);
            }
        }
    }
}
