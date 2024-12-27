package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.util.ArraySet;
import android.util.SparseBooleanArray;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class DeviceProvisionedControllerImpl implements DeviceProvisionedController, DeviceProvisionedController.DeviceProvisionedListener, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final HandlerExecutor backgroundExecutor;
    public final AtomicBoolean deviceProvisioned = new AtomicBoolean(false);
    public final Uri deviceProvisionedUri;
    public final DumpManager dumpManager;
    public final GlobalSettings globalSettings;
    public final AtomicBoolean initted;
    public final ArraySet listeners;
    public final Object lock;
    public final Executor mainExecutor;
    public final DeviceProvisionedControllerImpl$observer$1 observer;
    public final SecureSettings secureSettings;
    public final DeviceProvisionedControllerImpl$userChangedCallback$1 userChangedCallback;
    public final SparseBooleanArray userSetupComplete;
    public final Uri userSetupUri;
    public final UserTracker userTracker;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public DeviceProvisionedControllerImpl(SecureSettings secureSettings, GlobalSettings globalSettings, UserTracker userTracker, DumpManager dumpManager, final Handler handler, Executor executor) {
        this.secureSettings = secureSettings;
        this.globalSettings = globalSettings;
        this.userTracker = userTracker;
        this.dumpManager = dumpManager;
        this.mainExecutor = executor;
        this.deviceProvisionedUri = globalSettings.getUriFor("device_provisioned");
        this.userSetupUri = secureSettings.getUriFor("user_setup_complete");
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        this.userSetupComplete = sparseBooleanArray;
        this.listeners = new ArraySet();
        this.lock = new Object();
        this.backgroundExecutor = new HandlerExecutor(handler);
        this.initted = new AtomicBoolean(false);
        this.observer = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl$observer$1
            public final void onChange(boolean z, Collection collection, int i, int i2) {
                boolean contains = collection.contains(DeviceProvisionedControllerImpl.this.deviceProvisionedUri);
                if (!collection.contains(DeviceProvisionedControllerImpl.this.userSetupUri)) {
                    i2 = -2;
                }
                DeviceProvisionedControllerImpl.this.updateValues(i2, contains);
                if (contains) {
                    DeviceProvisionedControllerImpl.this.onDeviceProvisionedChanged();
                }
                if (i2 != -2) {
                    DeviceProvisionedControllerImpl.this.onUserSetupChanged();
                }
            }
        };
        this.userChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl$userChangedCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context) {
                int i2 = DeviceProvisionedControllerImpl.$r8$clinit;
                DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = DeviceProvisionedControllerImpl.this;
                deviceProvisionedControllerImpl.updateValues(i, false);
                deviceProvisionedControllerImpl.onUserSwitched();
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onProfilesChanged(List list) {
            }
        };
        sparseBooleanArray.put(((UserTrackerImpl) userTracker).getUserId(), false);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        DeviceProvisionedController.DeviceProvisionedListener deviceProvisionedListener = (DeviceProvisionedController.DeviceProvisionedListener) obj;
        synchronized (this.lock) {
            this.listeners.add(deviceProvisionedListener);
        }
    }

    public final void dispatchChange(final Function1 function1) {
        final ArrayList arrayList;
        synchronized (this.lock) {
            arrayList = new ArrayList(this.listeners);
        }
        this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl$dispatchChange$1
            @Override // java.lang.Runnable
            public final void run() {
                ArrayList arrayList2 = arrayList;
                Function1 function12 = function1;
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    function12.invoke(it.next());
                }
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("Device provisioned: ", this.deviceProvisioned.get(), printWriter);
        synchronized (this.lock) {
            printWriter.println("User setup complete: " + this.userSetupComplete);
            printWriter.println("Listeners: " + this.listeners);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final boolean isCurrentUserSetup() {
        return isUserSetup(((UserTrackerImpl) this.userTracker).getUserId());
    }

    public final boolean isUserSetup(int i) {
        int indexOfKey;
        boolean z;
        synchronized (this.lock) {
            indexOfKey = this.userSetupComplete.indexOfKey(i);
        }
        if (indexOfKey < 0) {
            z = this.secureSettings.getIntForUser("user_setup_complete", 0, i) != 0;
            synchronized (this.lock) {
                this.userSetupComplete.put(i, z);
                Unit unit = Unit.INSTANCE;
            }
        } else {
            synchronized (this.lock) {
                z = this.userSetupComplete.get(i, false);
            }
        }
        return z;
    }

    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
    public final void onDeviceProvisionedChanged() {
        dispatchChange(DeviceProvisionedControllerImpl$onDeviceProvisionedChanged$1.INSTANCE);
    }

    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
    public final void onUserSetupChanged() {
        dispatchChange(DeviceProvisionedControllerImpl$onUserSetupChanged$1.INSTANCE);
    }

    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
    public final void onUserSwitched() {
        dispatchChange(DeviceProvisionedControllerImpl$onUserSwitched$1.INSTANCE);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        DeviceProvisionedController.DeviceProvisionedListener deviceProvisionedListener = (DeviceProvisionedController.DeviceProvisionedListener) obj;
        synchronized (this.lock) {
            this.listeners.remove(deviceProvisionedListener);
        }
    }

    public final void updateValues(int i, boolean z) {
        boolean z2 = true;
        if (z) {
            this.deviceProvisioned.set(this.globalSettings.getInt("device_provisioned", 0) != 0);
        }
        synchronized (this.lock) {
            try {
                if (i == -1) {
                    int size = this.userSetupComplete.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        int keyAt = this.userSetupComplete.keyAt(i2);
                        this.userSetupComplete.put(keyAt, this.secureSettings.getIntForUser("user_setup_complete", 0, keyAt) != 0);
                    }
                } else if (i != -2) {
                    if (this.secureSettings.getIntForUser("user_setup_complete", 0, i) == 0) {
                        z2 = false;
                    }
                    this.userSetupComplete.put(i, z2);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
