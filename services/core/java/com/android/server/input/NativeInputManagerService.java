package com.android.server.input;

import android.hardware.display.DisplayViewport;
import android.hardware.input.InputSensorInfo;
import android.os.IBinder;
import android.os.MessageQueue;
import android.util.SparseArray;
import android.view.InputApplicationHandle;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.PointerIcon;
import android.view.VerifiedInputEvent;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface NativeInputManagerService {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class NativeImpl implements NativeInputManagerService {
        public final long mPtr;

        public NativeImpl(InputManagerService inputManagerService, MessageQueue messageQueue) {
            this.mPtr = init(inputManagerService, messageQueue);
        }

        private native long init(
                InputManagerService inputManagerService, MessageQueue messageQueue);

        @Override // com.android.server.input.NativeInputManagerService
        public native void addKeyRemapping(int i, int i2, int i3);

        @Override // com.android.server.input.NativeInputManagerService
        public native boolean canDispatchToDisplay(int i, int i2);

        @Override // com.android.server.input.NativeInputManagerService
        public native void cancelCurrentTouch();

        @Override // com.android.server.input.NativeInputManagerService
        public native void cancelVibrate(int i, int i2);

        @Override // com.android.server.input.NativeInputManagerService
        public native void changeKeyboardLayoutAssociation();

        @Override // com.android.server.input.NativeInputManagerService
        public native void changeTypeAssociation();

        @Override // com.android.server.input.NativeInputManagerService
        public native void changeUniqueIdAssociation();

        @Override // com.android.server.input.NativeInputManagerService
        public native InputChannel createInputChannel(String str, int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native InputChannel createInputMonitor(int i, String str, int i2, int i3);

        @Override // com.android.server.input.NativeInputManagerService
        public native void disableInputDevice(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void disableSensor(int i, int i2);

        @Override // com.android.server.input.NativeInputManagerService
        public native void displayRemoved(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native String dump();

        @Override // com.android.server.input.NativeInputManagerService
        public native void enableFlowPointer(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void enableInputDevice(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native boolean enableSensor(int i, int i2, int i3, int i4);

        @Override // com.android.server.input.NativeInputManagerService
        public native void enableWirelessKeyboardShare(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native boolean flushSensor(int i, int i2);

        @Override // com.android.server.input.NativeInputManagerService
        public native void forceFadeIcon(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void forceHideCursor(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native int getBatteryCapacity(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native String getBatteryDevicePath(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native int getBatteryStatus(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native String getBluetoothAddress(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native int getGlobalMetaState(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native int getInboundQueueLength();

        @Override // com.android.server.input.NativeInputManagerService
        public native int getKeyCodeForKeyLocation(int i, int i2);

        @Override // com.android.server.input.NativeInputManagerService
        public native int getKeyCodeState(int i, int i2, int i3);

        @Override // com.android.server.input.NativeInputManagerService
        public native int getLastUsedInputDeviceId();

        @Override // com.android.server.input.NativeInputManagerService
        public native int getLightColor(int i, int i2);

        @Override // com.android.server.input.NativeInputManagerService
        public native int getLightPlayerId(int i, int i2);

        @Override // com.android.server.input.NativeInputManagerService
        public native List getLights(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native long getMotionIdleTimeMillis(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native float[] getMouseCursorPosition(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native int getMousePointerSpeed();

        @Override // com.android.server.input.NativeInputManagerService
        public native int getScanCodeState(int i, int i2, int i3);

        @Override // com.android.server.input.NativeInputManagerService
        public native InputSensorInfo[] getSensorList(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native int getSwitchState(int i, int i2, int i3);

        @Override // com.android.server.input.NativeInputManagerService
        public native int[] getVibratorIds(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native boolean hasKeys(int i, int i2, int[] iArr, boolean[] zArr);

        @Override // com.android.server.input.NativeInputManagerService
        public native int injectInputEvent(
                InputEvent inputEvent, boolean z, int i, int i2, int i3, int i4, int i5, int i6);

        @Override // com.android.server.input.NativeInputManagerService
        public native boolean isUidTouched(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native boolean isVibrating(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void monitor();

        @Override // com.android.server.input.NativeInputManagerService
        public native void notifyPortAssociationsChanged();

        @Override // com.android.server.input.NativeInputManagerService
        public native void pilferPointers(IBinder iBinder);

        @Override // com.android.server.input.NativeInputManagerService
        public native void reloadCalibration();

        @Override // com.android.server.input.NativeInputManagerService
        public native void reloadDeviceAliases();

        @Override // com.android.server.input.NativeInputManagerService
        public native void reloadKeyboardLayouts();

        @Override // com.android.server.input.NativeInputManagerService
        public native void reloadPointerIcons();

        @Override // com.android.server.input.NativeInputManagerService
        public native void removeInputChannel(IBinder iBinder);

        @Override // com.android.server.input.NativeInputManagerService
        public native void requestPointerCapture(IBinder iBinder, boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setAccessibilityBounceKeysThreshold(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setAccessibilitySlowKeysThreshold(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setAccessibilityStickyKeysEnabled(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setBackButtonBehavior(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setCoverTestModeType(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setCoverVerify(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setCursorPosition(int i, int i2, int i3);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setDexMode(boolean z, int i, int i2);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setDisplayDpi(float f, float f2);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setDisplayEligibilityForPointerCapture(int i, boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setDisplayFolded(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setDisplayViewports(DisplayViewport[] displayViewportArr);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setEnableTapToClick(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setFlowPointerDirection(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setFocusedApplication(
                int i, InputApplicationHandle inputApplicationHandle);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setFocusedDisplay(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setFoldingState(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setForwardButtonBehavior(int i);

        public native void setHoverIcon(PointerIcon pointerIcon, int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native boolean setInTouchMode(boolean z, int i, int i2, boolean z2, int i3);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setInputDispatchMode(boolean z, boolean z2);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setInputFilterEnabled(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setInputMetaData(int i, int i2);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setInputMethodConnectionIsActive(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setInteractive(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setInteractiveForInternalDisplay(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setKeyRepeatConfiguration(int i, int i2);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setLightColor(int i, int i2, int i3);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setLightPlayerId(int i, int i2, int i3);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setMaximumObscuringOpacityForTouch(float f);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setMinTimeBetweenUserActivityPokes(long j);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setMotionClassifierEnabled(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setMousePointerAccelerationEnabled(int i, boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setMultiControlOutOfFocus(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setPenHovering(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setPenModeOnDex(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setPointerDisplayId(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native boolean setPointerIcon(
                PointerIcon pointerIcon, int i, int i2, int i3, IBinder iBinder);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setPointerIconVisibility(int i, boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setPointerSpeed(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setPrimaryMouseButtonLocation(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setReverseSwipeGesture(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setScrollSpeed(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setSecondaryButtonBehavior(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setShowHovering(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setShowTouches(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setStylusButtonMotionEventsEnabled(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setStylusPointerIconEnabled(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setSystemUiLightsOut(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setSystemUiLightsOutForDisplay(boolean z, int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setTalkBack(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setTertiaryButtonBehavior(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setTouchpadNaturalScrollingEnabled(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setTouchpadPointerSpeed(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setTouchpadRightClickZoneEnabled(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setTouchpadTapDraggingEnabled(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setTouchpadTapToClickEnabled(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setTspFeatures(int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native void setUseMouseAcceleration(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void showAllTouches(boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void start();

        @Override // com.android.server.input.NativeInputManagerService
        public native void sysfsNodeChanged(String str);

        @Override // com.android.server.input.NativeInputManagerService
        public native void toggleCapsLock(int i);

        @Override // com.android.server.input.NativeInputManagerService
        @Deprecated
        public native boolean transferTouch(IBinder iBinder, int i);

        @Override // com.android.server.input.NativeInputManagerService
        public native boolean transferTouchGesture(IBinder iBinder, IBinder iBinder2, boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native void updateInputMetaState(int i, boolean z);

        @Override // com.android.server.input.NativeInputManagerService
        public native VerifiedInputEvent verifyInputEvent(InputEvent inputEvent);

        @Override // com.android.server.input.NativeInputManagerService
        public native void vibrate(int i, long[] jArr, int[] iArr, int i2, int i3);

        @Override // com.android.server.input.NativeInputManagerService
        public native void vibrateCombined(
                int i, long[] jArr, SparseArray sparseArray, int i2, int i3);
    }

    void addKeyRemapping(int i, int i2, int i3);

    boolean canDispatchToDisplay(int i, int i2);

    void cancelCurrentTouch();

    void cancelVibrate(int i, int i2);

    void changeKeyboardLayoutAssociation();

    void changeTypeAssociation();

    void changeUniqueIdAssociation();

    InputChannel createInputChannel(String str, int i);

    InputChannel createInputMonitor(int i, String str, int i2, int i3);

    void disableInputDevice(int i);

    void disableSensor(int i, int i2);

    void displayRemoved(int i);

    String dump();

    void enableFlowPointer(boolean z);

    void enableInputDevice(int i);

    boolean enableSensor(int i, int i2, int i3, int i4);

    void enableWirelessKeyboardShare(boolean z);

    boolean flushSensor(int i, int i2);

    void forceFadeIcon(int i);

    void forceHideCursor(boolean z);

    int getBatteryCapacity(int i);

    String getBatteryDevicePath(int i);

    int getBatteryStatus(int i);

    String getBluetoothAddress(int i);

    int getGlobalMetaState(int i);

    int getInboundQueueLength();

    int getKeyCodeForKeyLocation(int i, int i2);

    int getKeyCodeState(int i, int i2, int i3);

    int getLastUsedInputDeviceId();

    int getLightColor(int i, int i2);

    int getLightPlayerId(int i, int i2);

    List getLights(int i);

    long getMotionIdleTimeMillis(boolean z);

    float[] getMouseCursorPosition(int i);

    int getMousePointerSpeed();

    int getScanCodeState(int i, int i2, int i3);

    InputSensorInfo[] getSensorList(int i);

    int getSwitchState(int i, int i2, int i3);

    int[] getVibratorIds(int i);

    boolean hasKeys(int i, int i2, int[] iArr, boolean[] zArr);

    int injectInputEvent(
            InputEvent inputEvent, boolean z, int i, int i2, int i3, int i4, int i5, int i6);

    boolean isUidTouched(int i);

    boolean isVibrating(int i);

    void monitor();

    void notifyPortAssociationsChanged();

    void pilferPointers(IBinder iBinder);

    void reloadCalibration();

    void reloadDeviceAliases();

    void reloadKeyboardLayouts();

    void reloadPointerIcons();

    void removeInputChannel(IBinder iBinder);

    void requestPointerCapture(IBinder iBinder, boolean z);

    void setAccessibilityBounceKeysThreshold(int i);

    void setAccessibilitySlowKeysThreshold(int i);

    void setAccessibilityStickyKeysEnabled(boolean z);

    void setBackButtonBehavior(int i);

    void setCoverTestModeType(int i);

    void setCoverVerify(int i);

    void setCursorPosition(int i, int i2, int i3);

    void setDexMode(boolean z, int i, int i2);

    void setDisplayDpi(float f, float f2);

    void setDisplayEligibilityForPointerCapture(int i, boolean z);

    void setDisplayFolded(boolean z);

    void setDisplayViewports(DisplayViewport[] displayViewportArr);

    void setEnableTapToClick(boolean z);

    void setFlowPointerDirection(int i);

    void setFocusedApplication(int i, InputApplicationHandle inputApplicationHandle);

    void setFocusedDisplay(int i);

    void setFoldingState(int i);

    void setForwardButtonBehavior(int i);

    boolean setInTouchMode(boolean z, int i, int i2, boolean z2, int i3);

    void setInputDispatchMode(boolean z, boolean z2);

    void setInputFilterEnabled(boolean z);

    void setInputMetaData(int i, int i2);

    void setInputMethodConnectionIsActive(boolean z);

    void setInteractive(boolean z);

    void setInteractiveForInternalDisplay(boolean z);

    void setKeyRepeatConfiguration(int i, int i2);

    void setLightColor(int i, int i2, int i3);

    void setLightPlayerId(int i, int i2, int i3);

    void setMaximumObscuringOpacityForTouch(float f);

    void setMinTimeBetweenUserActivityPokes(long j);

    void setMotionClassifierEnabled(boolean z);

    void setMousePointerAccelerationEnabled(int i, boolean z);

    void setMultiControlOutOfFocus(boolean z);

    void setPenHovering(boolean z);

    void setPenModeOnDex(int i);

    void setPointerDisplayId(int i);

    boolean setPointerIcon(PointerIcon pointerIcon, int i, int i2, int i3, IBinder iBinder);

    void setPointerIconVisibility(int i, boolean z);

    void setPointerSpeed(int i);

    void setPrimaryMouseButtonLocation(int i);

    void setReverseSwipeGesture(boolean z);

    void setScrollSpeed(int i);

    void setSecondaryButtonBehavior(int i);

    void setShowHovering(boolean z);

    void setShowTouches(boolean z);

    void setStylusButtonMotionEventsEnabled(boolean z);

    void setStylusPointerIconEnabled(boolean z);

    void setSystemUiLightsOut(boolean z);

    void setSystemUiLightsOutForDisplay(boolean z, int i);

    void setTalkBack(boolean z);

    void setTertiaryButtonBehavior(int i);

    void setTouchpadNaturalScrollingEnabled(boolean z);

    void setTouchpadPointerSpeed(int i);

    void setTouchpadRightClickZoneEnabled(boolean z);

    void setTouchpadTapDraggingEnabled(boolean z);

    void setTouchpadTapToClickEnabled(boolean z);

    void setTspFeatures(int i);

    void setUseMouseAcceleration(boolean z);

    void showAllTouches(boolean z);

    void start();

    void sysfsNodeChanged(String str);

    void toggleCapsLock(int i);

    boolean transferTouch(IBinder iBinder, int i);

    boolean transferTouchGesture(IBinder iBinder, IBinder iBinder2, boolean z);

    void updateInputMetaState(int i, boolean z);

    VerifiedInputEvent verifyInputEvent(InputEvent inputEvent);

    void vibrate(int i, long[] jArr, int[] iArr, int i2, int i3);

    void vibrateCombined(int i, long[] jArr, SparseArray sparseArray, int i2, int i3);
}
