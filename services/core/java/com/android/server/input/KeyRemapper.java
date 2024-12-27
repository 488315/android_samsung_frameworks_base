package com.android.server.input;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArrayMap;
import android.util.FeatureFlagUtils;
import android.view.InputDevice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class KeyRemapper implements InputManager.InputDeviceListener {
    public final Context mContext;
    public final PersistentDataStore mDataStore;
    public final Handler mHandler;
    public final NativeInputManagerService mNative;

    public KeyRemapper(
            Context context,
            NativeInputManagerService.NativeImpl nativeImpl,
            PersistentDataStore persistentDataStore,
            Looper looper) {
        this.mContext = context;
        this.mNative = nativeImpl;
        this.mDataStore = persistentDataStore;
        this.mHandler =
                new Handler(
                        looper,
                        new Handler
                                .Callback() { // from class:
                                              // com.android.server.input.KeyRemapper$$ExternalSyntheticLambda0
                            /* JADX WARN: Multi-variable type inference failed */
                            /* JADX WARN: Type inference failed for: r7v1, types: [com.android.server.input.KeyRemapper, java.lang.Object] */
                            /* JADX WARN: Type inference failed for: r7v5, types: [com.android.server.input.PersistentDataStore] */
                            @Override // android.os.Handler.Callback
                            public final boolean handleMessage(Message message) {
                                KeyRemapper keyRemapper = KeyRemapper.this;
                                keyRemapper.getClass();
                                int i = message.what;
                                if (i == 1) {
                                    for (int i2 : (int[]) message.obj) {
                                        keyRemapper.onInputDeviceAdded(i2);
                                    }
                                    return true;
                                }
                                if (i != 2) {
                                    if (i != 3) {
                                        return false;
                                    }
                                    synchronized (keyRemapper.mDataStore) {
                                        try {
                                            try {
                                                PersistentDataStore persistentDataStore2 =
                                                        keyRemapper.mDataStore;
                                                persistentDataStore2.loadIfNeeded();
                                                Iterator it =
                                                        new HashMap(
                                                                        persistentDataStore2
                                                                                .mKeyRemapping)
                                                                .keySet()
                                                                .iterator();
                                                while (it.hasNext()) {
                                                    int intValue = ((Integer) it.next()).intValue();
                                                    keyRemapper.mDataStore.clearMappedKey(intValue);
                                                    keyRemapper.addKeyRemapping(intValue, intValue);
                                                }
                                            } finally {
                                                keyRemapper.mDataStore.saveIfNeeded();
                                            }
                                        } finally {
                                        }
                                    }
                                    return true;
                                }
                                int i3 = message.arg1;
                                int i4 = message.arg2;
                                keyRemapper.addKeyRemapping(i3, i4);
                                synchronized (keyRemapper.mDataStore) {
                                    try {
                                        if (i3 == i4) {
                                            keyRemapper.mDataStore.clearMappedKey(i3);
                                        } else {
                                            PersistentDataStore persistentDataStore3 =
                                                    keyRemapper.mDataStore;
                                            persistentDataStore3.loadIfNeeded();
                                            if (((Integer)
                                                                    ((HashMap)
                                                                                    persistentDataStore3
                                                                                            .mKeyRemapping)
                                                                            .getOrDefault(
                                                                                    Integer.valueOf(
                                                                                            i3),
                                                                                    -1))
                                                            .intValue()
                                                    != i4) {
                                                ((HashMap) persistentDataStore3.mKeyRemapping)
                                                        .put(
                                                                Integer.valueOf(i3),
                                                                Integer.valueOf(i4));
                                                persistentDataStore3.mDirty = true;
                                            }
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    } finally {
                                    }
                                }
                                return true;
                            }
                        });
    }

    public final void addKeyRemapping(int i, int i2) {
        InputManager inputManager =
                (InputManager) this.mContext.getSystemService(InputManager.class);
        Objects.requireNonNull(inputManager);
        for (int i3 : inputManager.getInputDeviceIds()) {
            InputDevice inputDevice = inputManager.getInputDevice(i3);
            if (inputDevice != null && !inputDevice.isVirtual() && inputDevice.isFullKeyboard()) {
                this.mNative.addKeyRemapping(i3, i, i2);
            }
        }
    }

    public final Map getKeyRemapping() {
        HashMap hashMap;
        if (!FeatureFlagUtils.isEnabled(this.mContext, "settings_new_keyboard_modifier_key")) {
            return new ArrayMap();
        }
        synchronized (this.mDataStore) {
            PersistentDataStore persistentDataStore = this.mDataStore;
            persistentDataStore.loadIfNeeded();
            hashMap = new HashMap(persistentDataStore.mKeyRemapping);
        }
        return hashMap;
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceAdded(final int i) {
        if (FeatureFlagUtils.isEnabled(this.mContext, "settings_new_keyboard_modifier_key")) {
            InputManager inputManager =
                    (InputManager) this.mContext.getSystemService(InputManager.class);
            Objects.requireNonNull(inputManager);
            InputDevice inputDevice = inputManager.getInputDevice(i);
            if (inputDevice == null || inputDevice.isVirtual() || !inputDevice.isFullKeyboard()) {
                return;
            }
            getKeyRemapping()
                    .forEach(
                            new BiConsumer() { // from class:
                                               // com.android.server.input.KeyRemapper$$ExternalSyntheticLambda1
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    KeyRemapper keyRemapper = KeyRemapper.this;
                                    keyRemapper.mNative.addKeyRemapping(
                                            i,
                                            ((Integer) obj).intValue(),
                                            ((Integer) obj2).intValue());
                                }
                            });
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceChanged(int i) {}

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceRemoved(int i) {}
}
