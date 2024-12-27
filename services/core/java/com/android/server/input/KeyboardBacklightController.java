package com.android.server.input;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManager;
import android.hardware.input.IKeyboardBacklightListener;
import android.hardware.input.IKeyboardBacklightState;
import android.hardware.input.InputManager;
import android.hardware.lights.Light;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UEventObserver;
import android.text.TextUtils;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.InputDevice;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.TreeSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class KeyboardBacklightController
        implements InputManagerService.KeyboardBacklightControllerInterface,
                InputManager.InputDeviceListener {
    static final int MAX_BRIGHTNESS_CHANGE_STEPS = 10;
    public final AmbientKeyboardBacklightController mAmbientController;
    public KeyboardBacklightController$$ExternalSyntheticLambda1 mAmbientListener;
    public final AnimatorFactory mAnimatorFactory;
    public final Context mContext;
    public final PersistentDataStore mDataStore;
    public final Handler mHandler;
    public final NativeInputManagerService mNative;
    public final UEventManager mUEventManager;
    public static final boolean DEBUG = Log.isLoggable("KbdBacklightController", 3);
    public static final long TRANSITION_ANIMATION_DURATION_MILLIS =
            Duration.ofSeconds(1).toMillis();
    static final long USER_INACTIVITY_THRESHOLD_MILLIS = Duration.ofSeconds(30).toMillis();
    static final int[] DEFAULT_BRIGHTNESS_VALUE_FOR_LEVEL = new int[11];
    public final SparseArray mKeyboardBacklights = new SparseArray(1);
    public boolean mIsBacklightOn = false;
    public boolean mIsInteractive = true;
    public final SparseArray mKeyboardBacklightListenerRecords = new SparseArray();
    public int mAmbientBacklightValue = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface AnimatorFactory {
        ValueAnimator makeIntAnimator(int i, int i2);
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class Direction {
        public static final /* synthetic */ Direction[] $VALUES;
        public static final Direction DIRECTION_DOWN;
        public static final Direction DIRECTION_UP;

        static {
            Direction direction = new Direction("DIRECTION_UP", 0);
            DIRECTION_UP = direction;
            Direction direction2 = new Direction("DIRECTION_DOWN", 1);
            DIRECTION_DOWN = direction2;
            $VALUES = new Direction[] {direction, direction2};
        }

        public static Direction valueOf(String str) {
            return (Direction) Enum.valueOf(Direction.class, str);
        }

        public static Direction[] values() {
            return (Direction[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KeyboardBacklightListenerRecord implements IBinder.DeathRecipient {
        public final IKeyboardBacklightListener mListener;
        public final int mPid;

        public KeyboardBacklightListenerRecord(
                int i, IKeyboardBacklightListener iKeyboardBacklightListener) {
            this.mPid = i;
            this.mListener = iKeyboardBacklightListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            if (KeyboardBacklightController.DEBUG) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Keyboard backlight listener for pid "),
                        this.mPid,
                        " died.",
                        "KbdBacklightController");
            }
            KeyboardBacklightController keyboardBacklightController =
                    KeyboardBacklightController.this;
            int i = this.mPid;
            synchronized (keyboardBacklightController.mKeyboardBacklightListenerRecords) {
                keyboardBacklightController.mKeyboardBacklightListenerRecords.remove(i);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KeyboardBacklightState {
        public ValueAnimator mAnimator;
        public int mBrightnessLevel;
        public final int[] mBrightnessValueForLevel;
        public final int mDeviceId;
        public final Light mLight;
        public boolean mUseAmbientController =
                InputFeatureFlagProvider.isAmbientKeyboardBacklightControlEnabled();

        public KeyboardBacklightState(int i, Light light) {
            int[] iArr;
            this.mDeviceId = i;
            this.mLight = light;
            if (((Boolean)
                            InputFeatureFlagProvider.sKeyboardBacklightCustomLevelsOverride.orElse(
                                    Boolean.valueOf(
                                            InputFeatureFlagProvider
                                                    .KEYBOARD_BACKLIGHT_CUSTOM_LEVELS_ENABLED)))
                    .booleanValue()) {
                int[] preferredBrightnessLevels = light.getPreferredBrightnessLevels();
                if (preferredBrightnessLevels == null || preferredBrightnessLevels.length == 0) {
                    iArr = KeyboardBacklightController.DEFAULT_BRIGHTNESS_VALUE_FOR_LEVEL;
                } else {
                    TreeSet treeSet = new TreeSet();
                    int i2 = 0;
                    treeSet.add(0);
                    for (int i3 : preferredBrightnessLevels) {
                        if (i3 > 0 && i3 < 255) {
                            treeSet.add(Integer.valueOf(i3));
                        }
                    }
                    treeSet.add(
                            Integer.valueOf(
                                    IDnsResolverUnsolicitedEventListener
                                            .DNS_HEALTH_RESULT_TIMEOUT));
                    if (treeSet.size() - 1 > 10) {
                        iArr = KeyboardBacklightController.DEFAULT_BRIGHTNESS_VALUE_FOR_LEVEL;
                    } else {
                        iArr = new int[treeSet.size()];
                        Iterator it = treeSet.iterator();
                        while (it.hasNext()) {
                            iArr[i2] = ((Integer) it.next()).intValue();
                            i2++;
                        }
                    }
                }
            } else {
                iArr = KeyboardBacklightController.DEFAULT_BRIGHTNESS_VALUE_FOR_LEVEL;
            }
            this.mBrightnessValueForLevel = iArr;
        }

        public final void setBacklightValue(int i) {
            KeyboardBacklightController keyboardBacklightController =
                    KeyboardBacklightController.this;
            NativeInputManagerService nativeInputManagerService =
                    keyboardBacklightController.mNative;
            int id = this.mLight.getId();
            int i2 = this.mDeviceId;
            int alpha = Color.alpha(nativeInputManagerService.getLightColor(i2, id));
            if (alpha == i) {
                return;
            }
            if (!((Boolean)
                            InputFeatureFlagProvider.sKeyboardBacklightAnimationOverride.orElse(
                                    Boolean.valueOf(
                                            InputFeatureFlagProvider
                                                    .KEYBOARD_BACKLIGHT_ANIMATION_ENABLED)))
                    .booleanValue()) {
                keyboardBacklightController.mNative.setLightColor(
                        i2, this.mLight.getId(), Color.argb(i, 0, 0, 0));
                return;
            }
            ValueAnimator valueAnimator = this.mAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.mAnimator.cancel();
            }
            ValueAnimator makeIntAnimator =
                    keyboardBacklightController.mAnimatorFactory.makeIntAnimator(alpha, i);
            this.mAnimator = makeIntAnimator;
            makeIntAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // com.android.server.input.KeyboardBacklightController$KeyboardBacklightState$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            KeyboardBacklightController.KeyboardBacklightState
                                    keyboardBacklightState =
                                            KeyboardBacklightController.KeyboardBacklightState.this;
                            KeyboardBacklightController.this.mNative.setLightColor(
                                    keyboardBacklightState.mDeviceId,
                                    keyboardBacklightState.mLight.getId(),
                                    Color.argb(
                                            ((Integer) valueAnimator2.getAnimatedValue())
                                                    .intValue(),
                                            0,
                                            0,
                                            0));
                        }
                    });
            this.mAnimator
                    .setDuration(KeyboardBacklightController.TRANSITION_ANIMATION_DURATION_MILLIS)
                    .start();
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("KeyboardBacklightState{Light=");
            sb.append(this.mLight.getId());
            sb.append(", BrightnessLevel=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mBrightnessLevel, sb, "}");
        }
    }

    static {
        for (int i = 0; i <= 10; i++) {
            DEFAULT_BRIGHTNESS_VALUE_FOR_LEVEL[i] = (int) Math.floor((i * 255.0f) / 10.0f);
        }
    }

    public KeyboardBacklightController(
            Context context,
            NativeInputManagerService nativeInputManagerService,
            PersistentDataStore persistentDataStore,
            Looper looper,
            AnimatorFactory animatorFactory,
            UEventManager uEventManager) {
        this.mContext = context;
        this.mNative = nativeInputManagerService;
        this.mDataStore = persistentDataStore;
        this.mHandler =
                new Handler(
                        looper,
                        new Handler
                                .Callback() { // from class:
                                              // com.android.server.input.KeyboardBacklightController$$ExternalSyntheticLambda2
                            @Override // android.os.Handler.Callback
                            public final boolean handleMessage(Message message) {
                                KeyboardBacklightController keyboardBacklightController =
                                        KeyboardBacklightController.this;
                                keyboardBacklightController.getClass();
                                switch (message.what) {
                                    case 1:
                                        for (int i : (int[]) message.obj) {
                                            keyboardBacklightController.onInputDeviceAdded(i);
                                        }
                                        break;
                                    case 2:
                                        keyboardBacklightController.updateKeyboardBacklight(
                                                ((Integer) message.obj).intValue(),
                                                KeyboardBacklightController.Direction.DIRECTION_UP);
                                        break;
                                    case 3:
                                        keyboardBacklightController.updateKeyboardBacklight(
                                                ((Integer) message.obj).intValue(),
                                                KeyboardBacklightController.Direction
                                                        .DIRECTION_DOWN);
                                        break;
                                    case 4:
                                        keyboardBacklightController.handleUserActivity();
                                        break;
                                    case 5:
                                        keyboardBacklightController.mIsBacklightOn = false;
                                        for (int i2 = 0;
                                                i2
                                                        < keyboardBacklightController
                                                                .mKeyboardBacklights.size();
                                                i2++) {
                                            KeyboardBacklightController.KeyboardBacklightState
                                                    keyboardBacklightState =
                                                            (KeyboardBacklightController
                                                                            .KeyboardBacklightState)
                                                                    keyboardBacklightController
                                                                            .mKeyboardBacklights
                                                                            .valueAt(i2);
                                            boolean z =
                                                    keyboardBacklightState.mUseAmbientController;
                                            KeyboardBacklightController
                                                    keyboardBacklightController2 =
                                                            KeyboardBacklightController.this;
                                            int i3 =
                                                    z
                                                            ? keyboardBacklightController2
                                                                    .mAmbientBacklightValue
                                                            : keyboardBacklightState
                                                                    .mBrightnessValueForLevel[
                                                                    keyboardBacklightState
                                                                            .mBrightnessLevel];
                                            if (!keyboardBacklightController2.mIsBacklightOn) {
                                                i3 = 0;
                                            }
                                            keyboardBacklightState.setBacklightValue(i3);
                                        }
                                        break;
                                    case 6:
                                        keyboardBacklightController.handleInteractiveStateChange(
                                                ((Boolean) message.obj).booleanValue());
                                        break;
                                    default:
                                        return false;
                                }
                                return true;
                            }
                        });
        this.mAnimatorFactory = animatorFactory;
        this.mAmbientController = new AmbientKeyboardBacklightController(context, looper);
        this.mUEventManager = uEventManager;
    }

    @Override // com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface
    public final void decrementKeyboardBacklight(int i) {
        this.mHandler.sendMessage(Message.obtain(this.mHandler, 3, Integer.valueOf(i)));
    }

    @Override // com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface
    public final void dump(PrintWriter printWriter) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.println(
                "KbdBacklightController: "
                        + this.mKeyboardBacklights.size()
                        + " keyboard backlights");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mKeyboardBacklights.size(); i++) {
            indentingPrintWriter.println(
                    i
                            + ": "
                            + ((KeyboardBacklightState) this.mKeyboardBacklights.valueAt(i))
                                    .toString());
        }
        indentingPrintWriter.decreaseIndent();
    }

    public void handleAmbientLightValueChanged(int i) {
        this.mAmbientBacklightValue = i;
        for (int i2 = 0; i2 < this.mKeyboardBacklights.size(); i2++) {
            KeyboardBacklightState keyboardBacklightState =
                    (KeyboardBacklightState) this.mKeyboardBacklights.valueAt(i2);
            KeyboardBacklightController keyboardBacklightController =
                    KeyboardBacklightController.this;
            if (keyboardBacklightController.mIsBacklightOn
                    && keyboardBacklightState.mUseAmbientController) {
                keyboardBacklightState.setBacklightValue(
                        keyboardBacklightController.mAmbientBacklightValue);
            }
        }
    }

    public void handleInteractiveStateChange(boolean z) {
        this.mIsInteractive = z;
        if (z) {
            handleUserActivity();
        } else {
            this.mIsBacklightOn = false;
            for (int i = 0; i < this.mKeyboardBacklights.size(); i++) {
                KeyboardBacklightState keyboardBacklightState =
                        (KeyboardBacklightState) this.mKeyboardBacklights.valueAt(i);
                boolean z2 = keyboardBacklightState.mUseAmbientController;
                KeyboardBacklightController keyboardBacklightController =
                        KeyboardBacklightController.this;
                int i2 =
                        z2
                                ? keyboardBacklightController.mAmbientBacklightValue
                                : keyboardBacklightState
                                        .mBrightnessValueForLevel[
                                        keyboardBacklightState.mBrightnessLevel];
                if (!keyboardBacklightController.mIsBacklightOn) {
                    i2 = 0;
                }
                keyboardBacklightState.setBacklightValue(i2);
            }
        }
        updateAmbientLightListener();
    }

    public final void handleUserActivity() {
        if (this.mIsInteractive) {
            this.mIsBacklightOn = true;
            for (int i = 0; i < this.mKeyboardBacklights.size(); i++) {
                KeyboardBacklightState keyboardBacklightState =
                        (KeyboardBacklightState) this.mKeyboardBacklights.valueAt(i);
                boolean z = keyboardBacklightState.mUseAmbientController;
                KeyboardBacklightController keyboardBacklightController =
                        KeyboardBacklightController.this;
                int i2 =
                        z
                                ? keyboardBacklightController.mAmbientBacklightValue
                                : keyboardBacklightState
                                        .mBrightnessValueForLevel[
                                        keyboardBacklightState.mBrightnessLevel];
                if (!keyboardBacklightController.mIsBacklightOn) {
                    i2 = 0;
                }
                keyboardBacklightState.setBacklightValue(i2);
            }
            this.mHandler.removeMessages(5);
            this.mHandler.sendEmptyMessageAtTime(
                    5, SystemClock.uptimeMillis() + USER_INACTIVITY_THRESHOLD_MILLIS);
        }
    }

    @Override // com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface
    public final void incrementKeyboardBacklight(int i) {
        this.mHandler.sendMessage(Message.obtain(this.mHandler, 2, Integer.valueOf(i)));
    }

    @Override // com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface
    public final void notifyUserActivity() {
        this.mHandler.sendMessage(Message.obtain(this.mHandler, 4));
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceAdded(int i) {
        onInputDeviceChanged(i);
        updateAmbientLightListener();
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceChanged(int i) {
        OptionalInt empty;
        InputManager inputManager =
                (InputManager) this.mContext.getSystemService(InputManager.class);
        Light light = null;
        InputDevice inputDevice = inputManager != null ? inputManager.getInputDevice(i) : null;
        if (inputDevice == null) {
            return;
        }
        Iterator<Light> it = inputDevice.getLightsManager().getLights().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Light next = it.next();
            if (next.getType() == 10003 && next.hasBrightnessControl()) {
                light = next;
                break;
            }
        }
        if (light == null) {
            this.mKeyboardBacklights.remove(i);
            return;
        }
        KeyboardBacklightState keyboardBacklightState =
                (KeyboardBacklightState) this.mKeyboardBacklights.get(i);
        if (keyboardBacklightState == null
                || keyboardBacklightState.mLight.getId() != light.getId()) {
            this.mKeyboardBacklights.put(i, new KeyboardBacklightState(i, light));
            if (InputFeatureFlagProvider.isAmbientKeyboardBacklightControlEnabled()) {
                return;
            }
            KeyboardBacklightState keyboardBacklightState2 =
                    (KeyboardBacklightState) this.mKeyboardBacklights.get(inputDevice.getId());
            synchronized (this.mDataStore) {
                PersistentDataStore persistentDataStore = this.mDataStore;
                String descriptor = inputDevice.getDescriptor();
                int id = light.getId();
                persistentDataStore.loadIfNeeded();
                PersistentDataStore.InputDeviceState inputDeviceState =
                        (PersistentDataStore.InputDeviceState)
                                persistentDataStore.mInputDevices.get(descriptor);
                if (inputDeviceState == null) {
                    empty = OptionalInt.empty();
                } else {
                    int i2 = inputDeviceState.mKeyboardBacklightBrightnessMap.get(id, -1);
                    empty = i2 == -1 ? OptionalInt.empty() : OptionalInt.of(i2);
                }
            }
            if (keyboardBacklightState2 == null || !empty.isPresent()) {
                return;
            }
            int binarySearch =
                    Arrays.binarySearch(
                            keyboardBacklightState2.mBrightnessValueForLevel,
                            Math.max(
                                    0,
                                    Math.min(
                                            IDnsResolverUnsolicitedEventListener
                                                    .DNS_HEALTH_RESULT_TIMEOUT,
                                            empty.getAsInt())));
            if (binarySearch < 0) {
                binarySearch =
                        Math.min(
                                keyboardBacklightState2.mBrightnessValueForLevel.length - 1,
                                -(binarySearch + 1));
            }
            keyboardBacklightState2.mUseAmbientController = false;
            if (KeyboardBacklightController.this.mIsBacklightOn) {
                keyboardBacklightState2.setBacklightValue(
                        keyboardBacklightState2.mBrightnessValueForLevel[binarySearch]);
            }
            keyboardBacklightState2.mBrightnessLevel = binarySearch;
            if (DEBUG) {
                Slog.d("KbdBacklightController", "Restoring brightness level " + empty.getAsInt());
            }
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceRemoved(int i) {
        this.mKeyboardBacklights.remove(i);
        updateAmbientLightListener();
    }

    @Override // com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface
    public final void onInteractiveChanged(boolean z) {
        this.mHandler.sendMessage(Message.obtain(this.mHandler, 6, Boolean.valueOf(z)));
    }

    public void onKeyboardBacklightUEvent(UEventObserver.UEvent uEvent) {
        int lastIndexOf;
        if ("ADD".equalsIgnoreCase(uEvent.get("ACTION"))
                && "LEDS".equalsIgnoreCase(uEvent.get("SUBSYSTEM"))) {
            String str = uEvent.get("DEVPATH");
            if (!TextUtils.isEmpty(str) && (lastIndexOf = str.lastIndexOf(47)) >= 0) {
                String substring = str.substring(lastIndexOf + 1);
                String substring2 = str.substring(0, lastIndexOf);
                if (substring2.endsWith("leds")
                        && substring.contains("kbd_backlight")
                        && substring2.lastIndexOf(47) >= 0) {
                    this.mNative.sysfsNodeChanged("/sys" + str);
                }
            }
        }
    }

    @Override // com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface
    public final void registerKeyboardBacklightListener(
            IKeyboardBacklightListener iKeyboardBacklightListener, int i) {
        synchronized (this.mKeyboardBacklightListenerRecords) {
            try {
                if (this.mKeyboardBacklightListenerRecords.get(i) != null) {
                    throw new IllegalStateException(
                            "The calling process has already registered a"
                                + " KeyboardBacklightListener.");
                }
                KeyboardBacklightListenerRecord keyboardBacklightListenerRecord =
                        new KeyboardBacklightListenerRecord(i, iKeyboardBacklightListener);
                try {
                    iKeyboardBacklightListener
                            .asBinder()
                            .linkToDeath(keyboardBacklightListenerRecord, 0);
                    this.mKeyboardBacklightListenerRecords.put(i, keyboardBacklightListenerRecord);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface
    public final void systemRunning() {
        InputManager inputManager =
                (InputManager) this.mContext.getSystemService(InputManager.class);
        Objects.requireNonNull(inputManager);
        inputManager.registerInputDeviceListener(this, this.mHandler);
        this.mHandler.sendMessage(
                Message.obtain(this.mHandler, 1, inputManager.getInputDeviceIds()));
        UEventManager uEventManager = this.mUEventManager;
        UEventManager.UEventListener uEventListener =
                new UEventManager
                        .UEventListener() { // from class:
                                            // com.android.server.input.KeyboardBacklightController.1
                    @Override // com.android.server.input.UEventManager.UEventListener
                    public final void onUEvent(UEventObserver.UEvent uEvent) {
                        KeyboardBacklightController.this.onKeyboardBacklightUEvent(uEvent);
                    }
                };
        uEventManager.getClass();
        uEventListener.mObserver.startObserving("kbd_backlight");
        if (InputFeatureFlagProvider.isAmbientKeyboardBacklightControlEnabled()) {
            AmbientKeyboardBacklightController ambientKeyboardBacklightController =
                    this.mAmbientController;
            ambientKeyboardBacklightController.mHandler.sendEmptyMessage(1);
            DisplayManager displayManager =
                    (DisplayManager)
                            ambientKeyboardBacklightController.mContext.getSystemService(
                                    DisplayManager.class);
            Objects.requireNonNull(displayManager);
            displayManager.registerDisplayListener(
                    ambientKeyboardBacklightController,
                    ambientKeyboardBacklightController.mHandler);
        }
    }

    @Override // com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface
    public final void unregisterKeyboardBacklightListener(
            IKeyboardBacklightListener iKeyboardBacklightListener, int i) {
        synchronized (this.mKeyboardBacklightListenerRecords) {
            try {
                KeyboardBacklightListenerRecord keyboardBacklightListenerRecord =
                        (KeyboardBacklightListenerRecord)
                                this.mKeyboardBacklightListenerRecords.get(i);
                if (keyboardBacklightListenerRecord == null) {
                    throw new IllegalStateException(
                            "The calling process has no registered KeyboardBacklightListener.");
                }
                if (keyboardBacklightListenerRecord.mListener.asBinder()
                        != iKeyboardBacklightListener.asBinder()) {
                    throw new IllegalStateException(
                            "The calling process has a different registered"
                                + " KeyboardBacklightListener.");
                }
                keyboardBacklightListenerRecord
                        .mListener
                        .asBinder()
                        .unlinkToDeath(keyboardBacklightListenerRecord, 0);
                this.mKeyboardBacklightListenerRecords.remove(i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateAmbientLightListener() {
        KeyboardBacklightController$$ExternalSyntheticLambda1
                keyboardBacklightController$$ExternalSyntheticLambda1;
        if (InputFeatureFlagProvider.isAmbientKeyboardBacklightControlEnabled()) {
            boolean z = false;
            for (int i = 0; i < this.mKeyboardBacklights.size(); i++) {
                z |=
                        ((KeyboardBacklightState) this.mKeyboardBacklights.valueAt(i))
                                .mUseAmbientController;
            }
            boolean z2 = this.mIsInteractive & z;
            if (z2 && this.mAmbientListener == null) {
                KeyboardBacklightController$$ExternalSyntheticLambda1
                        keyboardBacklightController$$ExternalSyntheticLambda12 =
                                new KeyboardBacklightController$$ExternalSyntheticLambda1(this);
                this.mAmbientListener = keyboardBacklightController$$ExternalSyntheticLambda12;
                AmbientKeyboardBacklightController ambientKeyboardBacklightController =
                        this.mAmbientController;
                ambientKeyboardBacklightController.getClass();
                synchronized (AmbientKeyboardBacklightController.sAmbientControllerLock) {
                    try {
                        if (((ArrayList)
                                        ambientKeyboardBacklightController
                                                .mAmbientKeyboardBacklightListeners)
                                .contains(keyboardBacklightController$$ExternalSyntheticLambda12)) {
                            throw new IllegalStateException(
                                    "AmbientKeyboardBacklightListener was already registered,"
                                        + " listener = "
                                            + keyboardBacklightController$$ExternalSyntheticLambda12);
                        }
                        if (((ArrayList)
                                        ambientKeyboardBacklightController
                                                .mAmbientKeyboardBacklightListeners)
                                .isEmpty()) {
                            ambientKeyboardBacklightController.addSensorListener(
                                    ambientKeyboardBacklightController.mLightSensor);
                        }
                        ((ArrayList)
                                        ambientKeyboardBacklightController
                                                .mAmbientKeyboardBacklightListeners)
                                .add(keyboardBacklightController$$ExternalSyntheticLambda12);
                    } finally {
                    }
                }
            }
            if (z2
                    || (keyboardBacklightController$$ExternalSyntheticLambda1 =
                                    this.mAmbientListener)
                            == null) {
                return;
            }
            AmbientKeyboardBacklightController ambientKeyboardBacklightController2 =
                    this.mAmbientController;
            ambientKeyboardBacklightController2.getClass();
            synchronized (AmbientKeyboardBacklightController.sAmbientControllerLock) {
                try {
                    if (!((ArrayList)
                                    ambientKeyboardBacklightController2
                                            .mAmbientKeyboardBacklightListeners)
                            .contains(keyboardBacklightController$$ExternalSyntheticLambda1)) {
                        throw new IllegalStateException(
                                "AmbientKeyboardBacklightListener was never registered, listener = "
                                        + keyboardBacklightController$$ExternalSyntheticLambda1);
                    }
                    ((ArrayList)
                                    ambientKeyboardBacklightController2
                                            .mAmbientKeyboardBacklightListeners)
                            .remove(keyboardBacklightController$$ExternalSyntheticLambda1);
                    if (((ArrayList)
                                    ambientKeyboardBacklightController2
                                            .mAmbientKeyboardBacklightListeners)
                            .isEmpty()) {
                        ambientKeyboardBacklightController2.removeSensorListener(
                                ambientKeyboardBacklightController2.mLightSensor);
                    }
                } finally {
                }
            }
            this.mAmbientListener = null;
        }
    }

    public final void updateKeyboardBacklight(int i, Direction direction) {
        int i2;
        InputManager inputManager =
                (InputManager) this.mContext.getSystemService(InputManager.class);
        InputDevice inputDevice = inputManager != null ? inputManager.getInputDevice(i) : null;
        KeyboardBacklightState keyboardBacklightState =
                (KeyboardBacklightState) this.mKeyboardBacklights.get(i);
        if (inputDevice == null || keyboardBacklightState == null) {
            return;
        }
        if (keyboardBacklightState.mUseAmbientController) {
            i2 =
                    Arrays.binarySearch(
                            keyboardBacklightState.mBrightnessValueForLevel,
                            this.mAmbientBacklightValue);
            if (i2 < 0) {
                i2 = Math.max(0, (-(i2 + 1)) - 1);
                if (direction != Direction.DIRECTION_UP) {
                    i2++;
                }
            }
        } else {
            i2 = keyboardBacklightState.mBrightnessLevel;
        }
        int min =
                direction == Direction.DIRECTION_UP
                        ? Math.min(
                                i2 + 1, keyboardBacklightState.mBrightnessValueForLevel.length - 1)
                        : Math.max(i2 - 1, 0);
        keyboardBacklightState.mUseAmbientController = false;
        if (KeyboardBacklightController.this.mIsBacklightOn) {
            keyboardBacklightState.setBacklightValue(
                    keyboardBacklightState.mBrightnessValueForLevel[min]);
        }
        keyboardBacklightState.mBrightnessLevel = min;
        updateAmbientLightListener();
        Light light = keyboardBacklightState.mLight;
        int i3 = keyboardBacklightState.mBrightnessValueForLevel[min];
        if (!InputFeatureFlagProvider.isAmbientKeyboardBacklightControlEnabled()) {
            synchronized (this.mDataStore) {
                try {
                    PersistentDataStore persistentDataStore = this.mDataStore;
                    String descriptor = inputDevice.getDescriptor();
                    int id = light.getId();
                    PersistentDataStore.InputDeviceState orCreateInputDeviceState =
                            persistentDataStore.getOrCreateInputDeviceState(descriptor);
                    if (orCreateInputDeviceState.mKeyboardBacklightBrightnessMap.get(id, -1)
                            != i3) {
                        orCreateInputDeviceState.mKeyboardBacklightBrightnessMap.put(id, i3);
                        persistentDataStore.mDirty = true;
                    }
                } finally {
                    this.mDataStore.saveIfNeeded();
                }
            }
        }
        if (DEBUG) {
            Slog.d(
                    "KbdBacklightController",
                    "Changing state from "
                            + keyboardBacklightState.mBrightnessLevel
                            + " to "
                            + min);
        }
        synchronized (this.mKeyboardBacklightListenerRecords) {
            for (int i4 = 0; i4 < this.mKeyboardBacklightListenerRecords.size(); i4++) {
                try {
                    IKeyboardBacklightState iKeyboardBacklightState = new IKeyboardBacklightState();
                    iKeyboardBacklightState.brightnessLevel = min;
                    iKeyboardBacklightState.maxBrightnessLevel =
                            keyboardBacklightState.mBrightnessValueForLevel.length - 1;
                    KeyboardBacklightListenerRecord keyboardBacklightListenerRecord =
                            (KeyboardBacklightListenerRecord)
                                    this.mKeyboardBacklightListenerRecords.valueAt(i4);
                    keyboardBacklightListenerRecord.getClass();
                    try {
                        keyboardBacklightListenerRecord.mListener.onBrightnessChanged(
                                i, iKeyboardBacklightState, true);
                    } catch (RemoteException e) {
                        Slog.w(
                                "KbdBacklightController",
                                "Failed to notify process "
                                        + keyboardBacklightListenerRecord.mPid
                                        + " that keyboard backlight changed, assuming it died.",
                                e);
                        keyboardBacklightListenerRecord.binderDied();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
