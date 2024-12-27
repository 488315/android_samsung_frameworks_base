package com.android.server.enterprise.impl;

import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IWindowManagerAdapter;
import com.android.server.enterprise.adapterlayer.WindowManagerAdapter;
import com.android.server.enterprise.common.KeyCodeRestrictionCallback;

import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;
import com.samsung.android.view.SemWindowManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class KeyCodeMediatorImpl implements Handler.Callback {
    public final AnonymousClass1 mInjector;
    public final Map mRestrictedKeyCodeBackup;
    public final Set mRestrictionCallbacks;

    /* renamed from: com.android.server.enterprise.impl.KeyCodeMediatorImpl$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public Handler mHandler;
    }

    public KeyCodeMediatorImpl() {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mRestrictedKeyCodeBackup = new HashMap();
        this.mRestrictionCallbacks = new HashSet();
        this.mInjector = anonymousClass1;
        if (anonymousClass1.mHandler == null) {
            anonymousClass1.mHandler = new Handler(Looper.myLooper(), this);
        }
    }

    public final Set getAllRestrictedKeyCodes() {
        HashSet hashSet = new HashSet();
        Iterator it = ((HashSet) this.mRestrictionCallbacks).iterator();
        while (it.hasNext()) {
            KeyCodeRestrictionCallback keyCodeRestrictionCallback =
                    (KeyCodeRestrictionCallback) it.next();
            if (keyCodeRestrictionCallback.getRestrictedKeyCodes() != null) {
                hashSet.addAll(keyCodeRestrictionCallback.getRestrictedKeyCodes());
            }
        }
        return hashSet;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        long clearCallingIdentity;
        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo;
        int i = message.what;
        IWindowManagerAdapter iWindowManagerAdapter =
                (IWindowManagerAdapter)
                        AdapterRegistry.mAdapterHandles.get(IWindowManagerAdapter.class);
        Objects.requireNonNull(iWindowManagerAdapter);
        WindowManagerAdapter windowManagerAdapter = (WindowManagerAdapter) iWindowManagerAdapter;
        Iterator it = WindowManagerAdapter.PRESS_TYPES_TO_BLOCK.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Integer num = (Integer) it.next();
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    keyCustomizationInfo =
                            windowManagerAdapter.mWindowManagerService.getKeyCustomizationInfo(
                                    10, num.intValue(), i);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } finally {
                }
            } catch (RemoteException e) {
                KnoxsdkFileLog.e("WindowManagerAdapter", "fail to getKeyCustomizationInfo " + e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                keyCustomizationInfo = null;
            }
            if (keyCustomizationInfo != null
                    && keyCustomizationInfo.id == 10
                    && keyCustomizationInfo.action == 4) {
                IWindowManagerAdapter iWindowManagerAdapter2 =
                        (IWindowManagerAdapter)
                                AdapterRegistry.mAdapterHandles.get(IWindowManagerAdapter.class);
                Objects.requireNonNull(iWindowManagerAdapter2);
                WindowManagerAdapter windowManagerAdapter2 =
                        (WindowManagerAdapter) iWindowManagerAdapter2;
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        KnoxsdkFileLog.d(
                                "WindowManagerAdapter",
                                "clearKeyCustomizationInfoByKeyCode with priority/keyCode : 10/"
                                        + i);
                        windowManagerAdapter2.mWindowManagerService
                                .clearKeyCustomizationInfoByAction(10, i, 4);
                    } finally {
                    }
                } catch (Exception e2) {
                    KnoxsdkFileLog.e("WindowManagerAdapter", "fail to clearAllConfiguration " + e2);
                }
                break;
            }
        }
        Iterator it2 = ((HashSet) this.mRestrictionCallbacks).iterator();
        while (it2.hasNext()) {
            KeyCodeRestrictionCallback keyCodeRestrictionCallback =
                    (KeyCodeRestrictionCallback) it2.next();
            if (!keyCodeRestrictionCallback.isKeyCodeInputAllowed(i)) {
                AnonymousClass1 anonymousClass1 = this.mInjector;
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                i, "keyCode ", " must be blocked by ");
                m.append(keyCodeRestrictionCallback.getServiceName());
                String sb = m.toString();
                anonymousClass1.getClass();
                KnoxsdkFileLog.w("KeyCodeMediator", sb);
                IWindowManagerAdapter iWindowManagerAdapter3 =
                        (IWindowManagerAdapter)
                                AdapterRegistry.mAdapterHandles.get(IWindowManagerAdapter.class);
                Objects.requireNonNull(iWindowManagerAdapter3);
                WindowManagerAdapter windowManagerAdapter3 =
                        (WindowManagerAdapter) iWindowManagerAdapter3;
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo2 =
                                new SemWindowManager.KeyCustomizationInfo(
                                        3, 10, i, 4, (Intent) null, -1, -2);
                        KnoxsdkFileLog.d(
                                "WindowManagerAdapter",
                                "putKeyCustomizationInfo with pressType : 3, "
                                        + WindowManagerAdapter.keyCustomizationInfoToString(
                                                keyCustomizationInfo2));
                        windowManagerAdapter3.mWindowManagerService.putKeyCustomizationInfo(
                                keyCustomizationInfo2);
                    } catch (Exception e3) {
                        KnoxsdkFileLog.e(
                                "WindowManagerAdapter", "fail to putKeyCustomizationInfo " + e3);
                    }
                    return true;
                } finally {
                }
            }
        }
        return true;
    }

    public final void onAdminRemoved(int i) {
        Set set = (Set) ((HashMap) this.mRestrictedKeyCodeBackup).get(Integer.valueOf(i));
        ((HashMap) this.mRestrictedKeyCodeBackup).remove(Integer.valueOf(i));
        if (set != null) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                update(((Integer) it.next()).intValue());
            }
        } else {
            this.mInjector.getClass();
            KnoxsdkFileLog.w(
                    "KeyCodeMediator", "Restricted Keycodes are appearing Null for Admin: " + i);
        }
    }

    public final void onPreAdminRemoval(int i) {
        Set allRestrictedKeyCodes = getAllRestrictedKeyCodes();
        ((HashMap) this.mRestrictedKeyCodeBackup).put(Integer.valueOf(i), allRestrictedKeyCodes);
    }

    public final void update(int i) {
        AnonymousClass1 anonymousClass1 = this.mInjector;
        if (anonymousClass1.mHandler == null) {
            anonymousClass1.mHandler = new Handler(Looper.myLooper(), this);
        }
        Handler handler = anonymousClass1.mHandler;
        if (handler != null) {
            if (handler.hasMessages(i)) {
                handler.removeMessages(i);
            }
            if (handler.sendEmptyMessage(i)) {
                return;
            }
        }
        this.mInjector.getClass();
        KnoxsdkFileLog.w("KeyCodeMediator", "Failed to update for keycode :" + i);
    }
}
