package com.android.server.companion.virtual;

import android.content.AttributionSource;
import android.graphics.PointF;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManager;
import android.hardware.input.InputManagerGlobal;
import android.hardware.input.VirtualKeyEvent;
import android.hardware.input.VirtualMouseButtonEvent;
import android.hardware.input.VirtualMouseRelativeEvent;
import android.hardware.input.VirtualMouseScrollEvent;
import android.hardware.input.VirtualStylusButtonEvent;
import android.hardware.input.VirtualStylusMotionEvent;
import android.hardware.input.VirtualTouchEvent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.InputDevice;
import android.view.WindowManager;
import com.android.modules.expresslog.Counter;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.input.InputManagerService;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

class InputController {
    public static final AtomicLong sNextPhysId = new AtomicLong(1);
    public final AttributionSource mAttributionSource;
    public final Handler mHandler;
    public final NativeWrapper mNativeWrapper;
    public final DeviceCreationThreadVerifier mThreadVerifier;
    public final WindowManager mWindowManager;
    public final Object mLock = new Object();
    public final ArrayMap mInputDeviceDescriptors = new ArrayMap();
    public final DisplayManagerInternal mDisplayManagerInternal = (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
    public final InputManagerService.LocalService mInputManagerInternal = (InputManagerService.LocalService) LocalServices.getService(InputManagerService.LocalService.class);

    public final class BinderDeathRecipient implements IBinder.DeathRecipient {
        public final IBinder mDeviceToken;

        public BinderDeathRecipient(IBinder iBinder) {
            this.mDeviceToken = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Slog.e("VirtualInputController", "Virtual input controller binder died");
            InputController.this.unregisterInputDevice(this.mDeviceToken);
        }
    }

    class DeviceCreationException extends Exception {
        public DeviceCreationException(String str) {
            super(str);
        }
    }

    interface DeviceCreationThreadVerifier {
        boolean isValidThread();
    }

    final class InputDeviceDescriptor {
        public static final AtomicLong sNextCreationOrderNumber = new AtomicLong(1);
        public final long mCreationOrderNumber = sNextCreationOrderNumber.getAndIncrement();
        public final IBinder.DeathRecipient mDeathRecipient;
        public final int mDisplayId;
        public final int mInputDeviceId;
        public final String mName;
        public final String mPhys;
        public final long mPtr;
        public final int mType;

        public InputDeviceDescriptor(long j, IBinder.DeathRecipient deathRecipient, int i, int i2, String str, String str2, int i3) {
            this.mPtr = j;
            this.mDeathRecipient = deathRecipient;
            this.mType = i;
            this.mDisplayId = i2;
            this.mPhys = str;
            this.mName = str2;
            this.mInputDeviceId = i3;
        }
    }

    public class NativeWrapper {
        public final void closeUinput(long j) {
            InputController.nativeCloseUinput(j);
        }

        public final long openUinputDpad(String str, int i, int i2, String str2) {
            return InputController.m351$$Nest$smnativeOpenUinputDpad(i, i2, str, str2);
        }

        public final long openUinputKeyboard(String str, int i, int i2, String str2) {
            return InputController.m352$$Nest$smnativeOpenUinputKeyboard(i, i2, str, str2);
        }

        public final long openUinputMouse(String str, int i, int i2, String str2) {
            return InputController.m353$$Nest$smnativeOpenUinputMouse(i, i2, str, str2);
        }

        public final long openUinputStylus(String str, int i, int i2, String str2, int i3, int i4) {
            return InputController.m354$$Nest$smnativeOpenUinputStylus(i, i2, i3, str, str2, i4);
        }

        public final long openUinputTouchscreen(String str, int i, int i2, String str2, int i3, int i4) {
            return InputController.m355$$Nest$smnativeOpenUinputTouchscreen(i, i2, i3, str, str2, i4);
        }

        public final boolean writeButtonEvent(long j, int i, int i2, long j2) {
            return InputController.m356$$Nest$smnativeWriteButtonEvent(i, i2, j, j2);
        }

        public final boolean writeDpadKeyEvent(long j, int i, int i2, long j2) {
            return InputController.m357$$Nest$smnativeWriteDpadKeyEvent(i, i2, j, j2);
        }

        public final boolean writeKeyEvent(long j, int i, int i2, long j2) {
            return InputController.m358$$Nest$smnativeWriteKeyEvent(i, i2, j, j2);
        }

        public final boolean writeRelativeEvent(long j, float f, float f2, long j2) {
            return InputController.nativeWriteRelativeEvent(j, f, f2, j2);
        }

        public final boolean writeScrollEvent(long j, float f, float f2, long j2) {
            return InputController.nativeWriteScrollEvent(j, f, f2, j2);
        }

        public final boolean writeStylusButtonEvent(long j, int i, int i2, long j2) {
            return InputController.m361$$Nest$smnativeWriteStylusButtonEvent(i, i2, j, j2);
        }

        public final boolean writeStylusMotionEvent(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j2) {
            return InputController.nativeWriteStylusMotionEvent(j, i, i2, i3, i4, i5, i6, i7, j2);
        }

        public final boolean writeTouchEvent(long j, int i, int i2, int i3, float f, float f2, float f3, float f4, long j2) {
            return InputController.nativeWriteTouchEvent(j, i, i2, i3, f, f2, f3, f4, j2);
        }
    }

    public final class WaitForDevice implements AutoCloseable {
        public final CountDownLatch mDeviceAddedLatch = new CountDownLatch(1);
        public int mInputDeviceId = -2;
        public final AnonymousClass1 mListener;

        public WaitForDevice(InputController inputController, final String str, final int i, final int i2, final int i3) {
            ?? r0 = new InputManager.InputDeviceListener() { // from class: com.android.server.companion.virtual.InputController.WaitForDevice.1
                @Override // android.hardware.input.InputManager.InputDeviceListener
                public final void onInputDeviceAdded(int i4) {
                    onInputDeviceChanged(i4);
                }

                @Override // android.hardware.input.InputManager.InputDeviceListener
                public final void onInputDeviceChanged(int i4) {
                    InputDevice inputDevice = InputManagerGlobal.getInstance().getInputDevice(i4);
                    Objects.requireNonNull(inputDevice, "Newly added input device was null.");
                    if (inputDevice.getName().equals(str)) {
                        InputDeviceIdentifier identifier = inputDevice.getIdentifier();
                        if (identifier.getVendorId() == i && identifier.getProductId() == i2 && inputDevice.getAssociatedDisplayId() == i3) {
                            WaitForDevice waitForDevice = WaitForDevice.this;
                            waitForDevice.mInputDeviceId = i4;
                            waitForDevice.mDeviceAddedLatch.countDown();
                        }
                    }
                }

                @Override // android.hardware.input.InputManager.InputDeviceListener
                public final void onInputDeviceRemoved(int i4) {
                }
            };
            this.mListener = r0;
            InputManagerGlobal.getInstance().registerInputDeviceListener((InputManager.InputDeviceListener) r0, inputController.mHandler);
        }

        @Override // java.lang.AutoCloseable
        public final void close() {
            InputManagerGlobal.getInstance().unregisterInputDeviceListener(this.mListener);
        }

        public final int waitForDeviceCreation() {
            try {
                if (!this.mDeviceAddedLatch.await(1L, TimeUnit.MINUTES)) {
                    throw new DeviceCreationException("Timed out waiting for virtual device to be created.");
                }
                int i = this.mInputDeviceId;
                if (i != -2) {
                    return i;
                }
                throw new IllegalStateException("Virtual input device was created with an invalid id=" + this.mInputDeviceId);
            } catch (InterruptedException e) {
                throw new DeviceCreationException("Interrupted while waiting for virtual device to be created.", e);
            }
        }
    }

    /* renamed from: -$$Nest$smnativeOpenUinputDpad, reason: not valid java name */
    public static /* bridge */ /* synthetic */ long m351$$Nest$smnativeOpenUinputDpad(int i, int i2, String str, String str2) {
        return nativeOpenUinputDpad(str, i, i2, str2);
    }

    /* renamed from: -$$Nest$smnativeOpenUinputKeyboard, reason: not valid java name */
    public static /* bridge */ /* synthetic */ long m352$$Nest$smnativeOpenUinputKeyboard(int i, int i2, String str, String str2) {
        return nativeOpenUinputKeyboard(str, i, i2, str2);
    }

    /* renamed from: -$$Nest$smnativeOpenUinputMouse, reason: not valid java name */
    public static /* bridge */ /* synthetic */ long m353$$Nest$smnativeOpenUinputMouse(int i, int i2, String str, String str2) {
        return nativeOpenUinputMouse(str, i, i2, str2);
    }

    /* renamed from: -$$Nest$smnativeOpenUinputStylus, reason: not valid java name */
    public static /* bridge */ /* synthetic */ long m354$$Nest$smnativeOpenUinputStylus(int i, int i2, int i3, String str, String str2, int i4) {
        return nativeOpenUinputStylus(str, i, i2, str2, i3, i4);
    }

    /* renamed from: -$$Nest$smnativeOpenUinputTouchscreen, reason: not valid java name */
    public static /* bridge */ /* synthetic */ long m355$$Nest$smnativeOpenUinputTouchscreen(int i, int i2, int i3, String str, String str2, int i4) {
        return nativeOpenUinputTouchscreen(str, i, i2, str2, i3, i4);
    }

    /* renamed from: -$$Nest$smnativeWriteButtonEvent, reason: not valid java name */
    public static /* bridge */ /* synthetic */ boolean m356$$Nest$smnativeWriteButtonEvent(int i, int i2, long j, long j2) {
        return nativeWriteButtonEvent(j, i, i2, j2);
    }

    /* renamed from: -$$Nest$smnativeWriteDpadKeyEvent, reason: not valid java name */
    public static /* bridge */ /* synthetic */ boolean m357$$Nest$smnativeWriteDpadKeyEvent(int i, int i2, long j, long j2) {
        return nativeWriteDpadKeyEvent(j, i, i2, j2);
    }

    /* renamed from: -$$Nest$smnativeWriteKeyEvent, reason: not valid java name */
    public static /* bridge */ /* synthetic */ boolean m358$$Nest$smnativeWriteKeyEvent(int i, int i2, long j, long j2) {
        return nativeWriteKeyEvent(j, i, i2, j2);
    }

    /* renamed from: -$$Nest$smnativeWriteStylusButtonEvent, reason: not valid java name */
    public static /* bridge */ /* synthetic */ boolean m361$$Nest$smnativeWriteStylusButtonEvent(int i, int i2, long j, long j2) {
        return nativeWriteStylusButtonEvent(j, i, i2, j2);
    }

    public InputController(NativeWrapper nativeWrapper, Handler handler, WindowManager windowManager, AttributionSource attributionSource, DeviceCreationThreadVerifier deviceCreationThreadVerifier) {
        this.mHandler = handler;
        this.mNativeWrapper = nativeWrapper;
        this.mWindowManager = windowManager;
        this.mAttributionSource = attributionSource;
        this.mThreadVerifier = deviceCreationThreadVerifier;
    }

    public static String createPhys(String str) {
        return TextUtils.formatSimple("virtual%s:%d", new Object[]{str, Long.valueOf(sNextPhysId.getAndIncrement())});
    }

    public static native void nativeCloseUinput(long j);

    private static native long nativeOpenUinputDpad(String str, int i, int i2, String str2);

    private static native long nativeOpenUinputKeyboard(String str, int i, int i2, String str2);

    private static native long nativeOpenUinputMouse(String str, int i, int i2, String str2);

    private static native long nativeOpenUinputStylus(String str, int i, int i2, String str2, int i3, int i4);

    private static native long nativeOpenUinputTouchscreen(String str, int i, int i2, String str2, int i3, int i4);

    private static native boolean nativeWriteButtonEvent(long j, int i, int i2, long j2);

    private static native boolean nativeWriteDpadKeyEvent(long j, int i, int i2, long j2);

    private static native boolean nativeWriteKeyEvent(long j, int i, int i2, long j2);

    public static native boolean nativeWriteRelativeEvent(long j, float f, float f2, long j2);

    public static native boolean nativeWriteScrollEvent(long j, float f, float f2, long j2);

    private static native boolean nativeWriteStylusButtonEvent(long j, int i, int i2, long j2);

    public static native boolean nativeWriteStylusMotionEvent(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j2);

    public static native boolean nativeWriteTouchEvent(long j, int i, int i2, int i3, float f, float f2, float f3, float f4, long j2);

    public void addDeviceForTesting(IBinder iBinder, long j, int i, int i2, String str, String str2, int i3) {
        synchronized (this.mLock) {
            this.mInputDeviceDescriptors.put(iBinder, new InputDeviceDescriptor(j, new InputController$$ExternalSyntheticLambda0(), i, i2, str, str2, i3));
        }
    }

    public final void close() {
        synchronized (this.mLock) {
            try {
                Iterator it = this.mInputDeviceDescriptors.entrySet().iterator();
                if (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    IBinder iBinder = (IBinder) entry.getKey();
                    InputDeviceDescriptor inputDeviceDescriptor = (InputDeviceDescriptor) entry.getValue();
                    it.remove();
                    closeInputDeviceDescriptorLocked(iBinder, inputDeviceDescriptor);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void closeInputDeviceDescriptorLocked(IBinder iBinder, InputDeviceDescriptor inputDeviceDescriptor) {
        iBinder.unlinkToDeath(inputDeviceDescriptor.mDeathRecipient, 0);
        this.mNativeWrapper.getClass();
        nativeCloseUinput(inputDeviceDescriptor.mPtr);
        InputManagerGlobal inputManagerGlobal = InputManagerGlobal.getInstance();
        String str = inputDeviceDescriptor.mPhys;
        inputManagerGlobal.removeUniqueIdAssociationByPort(str);
        InputManagerService.LocalService localService = this.mInputManagerInternal;
        int i = inputDeviceDescriptor.mType;
        if (i == 5) {
            InputManagerService.this.unsetTypeAssociationInternal(str);
        }
        if (i == 1) {
            localService.getClass();
            boolean z = InputManagerService.DEBUG;
            InputManagerService.this.removeKeyboardLayoutAssociation(str);
        }
    }

    public final void createDeviceInternal(int i, String str, int i2, int i3, IBinder iBinder, int i4, String str2, Supplier supplier) {
        String str3;
        String str4;
        if (!this.mThreadVerifier.isValidThread()) {
            throw new IllegalStateException("Virtual device creation should happen on an auxiliary thread (e.g. binder thread) and not from the handler's thread.");
        }
        synchronized (this.mLock) {
            for (int i5 = 0; i5 < this.mInputDeviceDescriptors.size(); i5++) {
                try {
                    if (((InputDeviceDescriptor) this.mInputDeviceDescriptors.valueAt(i5)).mName.equals(str)) {
                        throw new DeviceCreationException("Input device name already in use: " + str);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        InputManagerGlobal.getInstance().addUniqueIdAssociationByPort(str2, this.mDisplayManagerInternal.getDisplayInfo(i4).uniqueId);
        try {
            str3 = i4;
            WaitForDevice waitForDevice = new WaitForDevice(this, str, i2, i3, str3);
            try {
                long longValue = ((Long) supplier.get()).longValue();
                try {
                    if (longValue == 0) {
                        throw new DeviceCreationException("A native error occurred when creating virtual input device: " + str);
                    }
                    try {
                        int waitForDeviceCreation = waitForDevice.waitForDeviceCreation();
                        BinderDeathRecipient binderDeathRecipient = new BinderDeathRecipient(iBinder);
                        try {
                            iBinder.linkToDeath(binderDeathRecipient, 0);
                            waitForDevice.close();
                            synchronized (this.mLock) {
                                this.mInputDeviceDescriptors.put(iBinder, new InputDeviceDescriptor(longValue, binderDeathRecipient, i, i4, str2, str, waitForDeviceCreation));
                            }
                            if (android.companion.virtualdevice.flags.Flags.metricsCollection()) {
                                switch (i) {
                                    case 1:
                                        str4 = "virtual_devices.value_virtual_keyboard_created_count";
                                        break;
                                    case 2:
                                        str4 = "virtual_devices.value_virtual_mouse_created_count";
                                        break;
                                    case 3:
                                        str4 = "virtual_devices.value_virtual_touchscreen_created_count";
                                        break;
                                    case 4:
                                        str4 = "virtual_devices.value_virtual_dpad_created_count";
                                        break;
                                    case 5:
                                        str4 = "virtual_devices.value_virtual_navigationtouchpad_created_count";
                                        break;
                                    case 6:
                                        str4 = "virtual_devices.value_virtual_stylus_created_count";
                                        break;
                                    default:
                                        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "No metric known for input type: ", "VirtualInputController");
                                        str4 = null;
                                        break;
                                }
                                if (str4 != null) {
                                    Counter.logIncrementWithUid(str4, this.mAttributionSource.getUid());
                                }
                            }
                        } catch (RemoteException e) {
                            try {
                                throw new DeviceCreationException("Client died before virtual device could be created.", e);
                            } catch (DeviceCreationException e2) {
                                e = e2;
                                this.mNativeWrapper.getClass();
                                nativeCloseUinput(longValue);
                                throw e;
                            }
                        }
                    } catch (DeviceCreationException e3) {
                        e = e3;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    Throwable th3 = th;
                    try {
                        try {
                            waitForDevice.close();
                            throw th3;
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                            throw th3;
                        }
                    } catch (DeviceCreationException e4) {
                        e = e4;
                        InputManagerGlobal.getInstance().removeUniqueIdAssociationByPort(str3);
                        throw e;
                    }
                }
            } catch (Throwable th5) {
                th = th5;
                str3 = str2;
            }
        } catch (DeviceCreationException e5) {
            e = e5;
            str3 = str2;
        }
    }

    public final void createKeyboard(String str, int i, int i2, IBinder iBinder, int i3, String str2, String str3) {
        String createPhys = createPhys("Keyboard");
        InputManagerService.LocalService localService = this.mInputManagerInternal;
        localService.getClass();
        boolean z = InputManagerService.DEBUG;
        InputManagerService inputManagerService = InputManagerService.this;
        inputManagerService.addKeyboardLayoutAssociation(createPhys, str2, str3);
        try {
            createDeviceInternal(1, str, i, i2, iBinder, i3, createPhys, new InputController$$ExternalSyntheticLambda1(this, str, i, i2, createPhys, 0));
        } catch (DeviceCreationException e) {
            boolean z2 = InputManagerService.DEBUG;
            inputManagerService.removeKeyboardLayoutAssociation(createPhys);
            throw e;
        }
    }

    public final void createNavigationTouchpad(int i, int i2, int i3, int i4, int i5, IBinder iBinder, String str) {
        String createPhys = createPhys("NavigationTouchpad");
        InputManagerService.LocalService localService = this.mInputManagerInternal;
        InputManagerService.this.setTypeAssociationInternal(createPhys, "touchNavigation");
        try {
            createDeviceInternal(5, str, i, i2, iBinder, i3, createPhys, new InputController$$ExternalSyntheticLambda5(this, str, i, i2, createPhys, i4, i5, 1));
        } catch (DeviceCreationException e) {
            InputManagerService.this.unsetTypeAssociationInternal(createPhys);
            throw e;
        }
    }

    public final PointF getCursorPosition(IBinder iBinder) {
        PointF pointF;
        synchronized (this.mLock) {
            try {
                InputDeviceDescriptor inputDeviceDescriptor = (InputDeviceDescriptor) this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    throw new IllegalArgumentException("Could not get cursor position for input device for given token");
                }
                InputManagerService.LocalService localService = (InputManagerService.LocalService) LocalServices.getService(InputManagerService.LocalService.class);
                float[] mouseCursorPosition = InputManagerService.this.mNative.getMouseCursorPosition(inputDeviceDescriptor.mDisplayId);
                if (mouseCursorPosition == null || mouseCursorPosition.length != 2) {
                    throw new IllegalStateException("Failed to get mouse cursor position");
                }
                pointF = new PointF(mouseCursorPosition[0], mouseCursorPosition[1]);
            } catch (Throwable th) {
                throw th;
            }
        }
        return pointF;
    }

    public Map getInputDeviceDescriptors() {
        ArrayMap arrayMap = new ArrayMap();
        synchronized (this.mLock) {
            arrayMap.putAll((Map) this.mInputDeviceDescriptors);
        }
        return arrayMap;
    }

    public final boolean sendButtonEvent(IBinder iBinder, VirtualMouseButtonEvent virtualMouseButtonEvent) {
        synchronized (this.mLock) {
            try {
                InputDeviceDescriptor inputDeviceDescriptor = (InputDeviceDescriptor) this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    return false;
                }
                NativeWrapper nativeWrapper = this.mNativeWrapper;
                long j = inputDeviceDescriptor.mPtr;
                int buttonCode = virtualMouseButtonEvent.getButtonCode();
                int action = virtualMouseButtonEvent.getAction();
                long eventTimeNanos = virtualMouseButtonEvent.getEventTimeNanos();
                nativeWrapper.getClass();
                return m356$$Nest$smnativeWriteButtonEvent(buttonCode, action, j, eventTimeNanos);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean sendDpadKeyEvent(IBinder iBinder, VirtualKeyEvent virtualKeyEvent) {
        synchronized (this.mLock) {
            try {
                InputDeviceDescriptor inputDeviceDescriptor = (InputDeviceDescriptor) this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    return false;
                }
                NativeWrapper nativeWrapper = this.mNativeWrapper;
                long j = inputDeviceDescriptor.mPtr;
                int keyCode = virtualKeyEvent.getKeyCode();
                int action = virtualKeyEvent.getAction();
                long eventTimeNanos = virtualKeyEvent.getEventTimeNanos();
                nativeWrapper.getClass();
                return m357$$Nest$smnativeWriteDpadKeyEvent(keyCode, action, j, eventTimeNanos);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean sendKeyEvent(IBinder iBinder, VirtualKeyEvent virtualKeyEvent) {
        synchronized (this.mLock) {
            try {
                InputDeviceDescriptor inputDeviceDescriptor = (InputDeviceDescriptor) this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    return false;
                }
                NativeWrapper nativeWrapper = this.mNativeWrapper;
                long j = inputDeviceDescriptor.mPtr;
                int keyCode = virtualKeyEvent.getKeyCode();
                int action = virtualKeyEvent.getAction();
                long eventTimeNanos = virtualKeyEvent.getEventTimeNanos();
                nativeWrapper.getClass();
                return m358$$Nest$smnativeWriteKeyEvent(keyCode, action, j, eventTimeNanos);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean sendRelativeEvent(IBinder iBinder, VirtualMouseRelativeEvent virtualMouseRelativeEvent) {
        synchronized (this.mLock) {
            try {
                InputDeviceDescriptor inputDeviceDescriptor = (InputDeviceDescriptor) this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    return false;
                }
                NativeWrapper nativeWrapper = this.mNativeWrapper;
                long j = inputDeviceDescriptor.mPtr;
                float relativeX = virtualMouseRelativeEvent.getRelativeX();
                float relativeY = virtualMouseRelativeEvent.getRelativeY();
                long eventTimeNanos = virtualMouseRelativeEvent.getEventTimeNanos();
                nativeWrapper.getClass();
                return nativeWriteRelativeEvent(j, relativeX, relativeY, eventTimeNanos);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean sendScrollEvent(IBinder iBinder, VirtualMouseScrollEvent virtualMouseScrollEvent) {
        synchronized (this.mLock) {
            try {
                InputDeviceDescriptor inputDeviceDescriptor = (InputDeviceDescriptor) this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    return false;
                }
                NativeWrapper nativeWrapper = this.mNativeWrapper;
                long j = inputDeviceDescriptor.mPtr;
                float xAxisMovement = virtualMouseScrollEvent.getXAxisMovement();
                float yAxisMovement = virtualMouseScrollEvent.getYAxisMovement();
                long eventTimeNanos = virtualMouseScrollEvent.getEventTimeNanos();
                nativeWrapper.getClass();
                return nativeWriteScrollEvent(j, xAxisMovement, yAxisMovement, eventTimeNanos);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean sendStylusButtonEvent(IBinder iBinder, VirtualStylusButtonEvent virtualStylusButtonEvent) {
        synchronized (this.mLock) {
            try {
                InputDeviceDescriptor inputDeviceDescriptor = (InputDeviceDescriptor) this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    return false;
                }
                NativeWrapper nativeWrapper = this.mNativeWrapper;
                long j = inputDeviceDescriptor.mPtr;
                int buttonCode = virtualStylusButtonEvent.getButtonCode();
                int action = virtualStylusButtonEvent.getAction();
                long eventTimeNanos = virtualStylusButtonEvent.getEventTimeNanos();
                nativeWrapper.getClass();
                return m361$$Nest$smnativeWriteStylusButtonEvent(buttonCode, action, j, eventTimeNanos);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean sendStylusMotionEvent(IBinder iBinder, VirtualStylusMotionEvent virtualStylusMotionEvent) {
        synchronized (this.mLock) {
            try {
                InputDeviceDescriptor inputDeviceDescriptor = (InputDeviceDescriptor) this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    return false;
                }
                NativeWrapper nativeWrapper = this.mNativeWrapper;
                long j = inputDeviceDescriptor.mPtr;
                int toolType = virtualStylusMotionEvent.getToolType();
                int action = virtualStylusMotionEvent.getAction();
                int x = virtualStylusMotionEvent.getX();
                int y = virtualStylusMotionEvent.getY();
                int pressure = virtualStylusMotionEvent.getPressure();
                int tiltX = virtualStylusMotionEvent.getTiltX();
                int tiltY = virtualStylusMotionEvent.getTiltY();
                long eventTimeNanos = virtualStylusMotionEvent.getEventTimeNanos();
                nativeWrapper.getClass();
                return nativeWriteStylusMotionEvent(j, toolType, action, x, y, pressure, tiltX, tiltY, eventTimeNanos);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean sendTouchEvent(IBinder iBinder, VirtualTouchEvent virtualTouchEvent) {
        synchronized (this.mLock) {
            try {
                InputDeviceDescriptor inputDeviceDescriptor = (InputDeviceDescriptor) this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    return false;
                }
                NativeWrapper nativeWrapper = this.mNativeWrapper;
                long j = inputDeviceDescriptor.mPtr;
                int pointerId = virtualTouchEvent.getPointerId();
                int toolType = virtualTouchEvent.getToolType();
                int action = virtualTouchEvent.getAction();
                float x = virtualTouchEvent.getX();
                float y = virtualTouchEvent.getY();
                float pressure = virtualTouchEvent.getPressure();
                float majorAxisSize = virtualTouchEvent.getMajorAxisSize();
                long eventTimeNanos = virtualTouchEvent.getEventTimeNanos();
                nativeWrapper.getClass();
                return nativeWriteTouchEvent(j, pointerId, toolType, action, x, y, pressure, majorAxisSize, eventTimeNanos);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterInputDevice(IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                InputDeviceDescriptor inputDeviceDescriptor = (InputDeviceDescriptor) this.mInputDeviceDescriptors.remove(iBinder);
                if (inputDeviceDescriptor == null) {
                    Slog.w("VirtualInputController", "Could not unregister input device for given token.");
                } else {
                    closeInputDeviceDescriptorLocked(iBinder, inputDeviceDescriptor);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
