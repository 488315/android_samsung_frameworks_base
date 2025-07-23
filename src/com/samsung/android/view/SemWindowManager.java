package com.samsung.android.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.devicestate.DeviceStateManagerGlobal;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.view.MultiResolutionChangeRequestInfo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class SemWindowManager {
    public static final int ACTION_BLOCK_KEY_EVENT = 4;
    public static final int ACTION_NOT_SET = 0;
    public static final int ACTION_SEND_BROADCAST = 2;
    public static final int ACTION_START_ACTIVITY = 1;
    public static final int ACTION_START_SERVICE = 3;
    public static final int APPLICATION_UI_LAST_ID = 2003;
    public static final int APP_CONTINUITY_MODE_APPLIED = 1;
    public static final int APP_CONTINUITY_MODE_NOT_APPLIED = 2;
    public static final int APP_CONTINUITY_MODE_RESTRICTED = 0;
    public static final int DISPATCHING = 0;
    public static final int FORCED_HIDE_CUTOUT_DEFAULT = -1;
    public static final int FORCED_HIDE_CUTOUT_OFF = 0;
    public static final int FORCED_HIDE_CUTOUT_ON = 1;
    public static final int FORCED_HIDE_CUTOUT_ON_WM_ONLY = 2;
    public static final int ID_APPLICATION_UI = 2000;
    public static final int ID_APPLICATION_UI_CAMERA = 2001;
    public static final int ID_APPLICATION_UI_TV_MODE = 2002;
    public static final int ID_DEFAULT = 1000;
    public static final int ID_GENERAL_APPLICATION = 2003;
    public static final int ID_KNOX_LEGACY = 50;
    public static final int ID_KNOX_MDM = 10;
    public static final int ID_KNOX_V2 = 30;
    public static final int ID_OLD_GOODLOCK_ROUTINE_PLUS = 900;
    public static final int ID_SETTING_UI = 1100;
    public static final int ID_SETTING_UI_B2B_DELTA = 951;
    public static final int ID_SETTING_UI_B2B_DELTA_OLD = 1102;
    public static final int ID_SETTING_UI_HIGH = 950;
    public static final int ID_SETTING_UI_MOUSE_BUTTON = 1107;
    public static final int ID_SETTING_UI_OLD_SOS_MESSAGE = 1105;
    public static final int ID_SETTING_UI_ONE_HAND_MODE = 1106;
    public static final int ID_SETTING_UI_SIDE_KEY = 1104;
    public static final int ID_SETTING_UI_XCOVER_TOP = 1103;
    public static final int KEY_CUSTOMIZATION_LAST_ID = 2003;
    public static final int KEY_PRESS_DOUBLE = 8;
    public static final int KEY_PRESS_DOWN = 1;
    public static final int KEY_PRESS_LONG = 4;
    public static final int KEY_PRESS_QUADRUPLE = 32;
    public static final int KEY_PRESS_QUINTUPLE = 64;
    public static final int KEY_PRESS_SINGLE = 3;
    public static final int KEY_PRESS_TRIPLE = 16;
    public static final int KEY_PRESS_UP = 2;
    public static final int MAX_ASPECT_RATIO_FIXED_OFF = 3;
    public static final int MAX_ASPECT_RATIO_FIXED_ON = 2;
    public static final int MAX_ASPECT_RATIO_OFF = 0;
    public static final int MAX_ASPECT_RATIO_ON = 1;
    public static final int NO_DISPATCHING = -1;
    public static final int SETTING_UI_LAST_ID = 1107;
    public static final int SUPPORTS_DISPLAY_CUTOUT = 2;
    public static final int SUPPORTS_FLEX_MODE = 16;
    public static final int SUPPORTS_FLEX_PANEL_DISABLED = 2;
    public static final int SUPPORTS_FLEX_PANEL_ENABLED = 1;
    public static final int SUPPORTS_FLEX_PANEL_HOME_ACTIVITY = 32;
    public static final int SUPPORTS_FLEX_PANEL_RUNNABLE = 8;
    public static final int SUPPORTS_FLEX_PANEL_UNCHANGEABLE = 4;
    public static final int SUPPORTS_FLEX_PANEL_UNRESIZABLE_ACTIVITY = 64;
    public static final int SUPPORTS_MAX_ASPECT_RATIO = 1;
    private static final String TAG = "SemWindowManager";
    private static SemWindowManager sInstance;
    private final IWindowManager mWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));
    private final WindowManagerGlobal mGlobal = WindowManagerGlobal.getInstance();
    private final DeviceStateManagerGlobal mDeviceStateManagerGlobal = DeviceStateManagerGlobal.getInstance();

    @Deprecated
    public interface FoldStateListener {
        @Deprecated
        void onFoldStateChanged(boolean z);

        @Deprecated
        void onTableModeChanged(boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyPressType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SystemKeyCode {
    }

    @Deprecated
    public void setStartingWindowContentView(ComponentName componentName, int i) {
    }

    private SemWindowManager() {
    }

    public static synchronized SemWindowManager getInstance() {
        SemWindowManager semWindowManager;
        synchronized (SemWindowManager.class) {
            if (sInstance == null) {
                sInstance = new SemWindowManager();
            }
            semWindowManager = sInstance;
        }
        return semWindowManager;
    }

    @Deprecated(forRemoval = true, since = "16.0")
    public boolean requestSystemKeyEvent(int i, ComponentName componentName, boolean z) {
        try {
            return this.mWindowManager.requestSystemKeyEvent(i, componentName, z);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to request system keyevent, ", e);
            return false;
        }
    }

    @Deprecated(forRemoval = true, since = "16.0")
    public boolean isSystemKeyEventRequested(int i, ComponentName componentName) {
        try {
            return this.mWindowManager.isSystemKeyEventRequested(i, componentName);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to is system keyevent, ", e);
            return false;
        }
    }

    public void registerSystemKeyEvent(int i, ComponentName componentName, int i2) throws IllegalArgumentException {
        try {
            this.mWindowManager.registerSystemKeyEvent(i, componentName, i2);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed registerSystemKeyEvent ", e);
        }
    }

    public void unregisterSystemKeyEvent(int i, ComponentName componentName) throws IllegalArgumentException {
        try {
            this.mWindowManager.unregisterSystemKeyEvent(i, componentName);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed unregisterSystemKeyEvent ", e);
        }
    }

    public void requestMetaKeyEvent(ComponentName componentName, boolean z) {
        try {
            this.mWindowManager.requestMetaKeyEvent(componentName, z);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to request meta keyevent, ", e);
        }
    }

    public void getInitialDisplaySize(Point point) {
        try {
            this.mWindowManager.getInitialDisplaySize(0, point);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to getInitialDisplaySize", e);
        }
    }

    public int getInitialDensity() {
        try {
            return this.mWindowManager.getInitialDisplayDensity(0);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to getInitialDisplayDensity", e);
            return -1;
        }
    }

    public void getUserDisplaySize(Point point) {
        try {
            this.mWindowManager.getUserDisplaySize(point);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to getUserDisplaySize, ", e);
        }
    }

    public int getUserDensity() {
        try {
            return this.mWindowManager.getUserDisplayDensity();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to getUserDisplayDensity, ", e);
            return -1;
        }
    }

    public void setForcedDisplaySizeDensity(int i, int i2, int i3) {
        setForcedDisplaySizeDensityInner(i, i2, i3, false, -1);
    }

    public void setForcedDisplaySizeDensity(int i, int i2, int i3, boolean z) {
        setForcedDisplaySizeDensityInner(i, i2, i3, z, -1);
    }

    public void setForcedDisplaySizeDensity(int i, int i2, int i3, boolean z, boolean z2) {
        setForcedDisplaySizeDensityInner(i, i2, i3, z, z2 ? 1 : 0);
    }

    public void clearForcedDisplaySizeDensity() {
        Log.d(TAG, "clearForcedDisplaySizeDensity userId=" + UserHandle.myUserId());
        try {
            this.mWindowManager.clearForcedDisplaySizeDensity(0);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to clearForcedDisplaySizeDensity, ", e);
        }
    }

    public void registerFoldStateListener(FoldStateListener foldStateListener, Handler handler) {
        if (foldStateListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        this.mDeviceStateManagerGlobal.registerFoldStateListener(foldStateListener, handler);
    }

    public void unregisterFoldStateListener(FoldStateListener foldStateListener) {
        if (foldStateListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        this.mDeviceStateManagerGlobal.unregisterFoldStateListener(foldStateListener);
    }

    @Deprecated
    public boolean isFolded() {
        try {
            return this.mWindowManager.isFolded();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to isFolded", e);
            return false;
        }
    }

    @Deprecated
    public boolean isTableMode() {
        try {
            return this.mWindowManager.isTableMode();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to isTableMode", e);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setForcedDefaultDisplayDevice(int r6) {
        /*
            r5 = this;
            java.lang.String r0 = "SemWindowManager"
            if (r6 < 0) goto L8f
            r1 = 7
            if (r6 <= r1) goto L9
            goto L8f
        L9:
            int r2 = android.os.Binder.getCallingPid()
            if (r6 != 0) goto L27
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = "setForcedDefaultDisplayDevice main, callingPid="
            r1.<init>(r3)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r0, r1)
            android.hardware.devicestate.DeviceStateManagerGlobal r1 = r5.mDeviceStateManagerGlobal
            r1.cancelStateRequest()
            goto L62
        L27:
            r3 = 5
            if (r6 != r3) goto L34
            r1 = 0
            android.hardware.devicestate.DeviceStateRequest$Builder r1 = android.hardware.devicestate.DeviceStateRequest.newBuilder(r1)
            android.hardware.devicestate.DeviceStateRequest r1 = r1.build()
            goto L63
        L34:
            r4 = 6
            if (r6 != r4) goto L40
            android.hardware.devicestate.DeviceStateRequest$Builder r1 = android.hardware.devicestate.DeviceStateRequest.newBuilder(r3)
            android.hardware.devicestate.DeviceStateRequest r1 = r1.build()
            goto L63
        L40:
            r3 = 4
            if (r6 != r3) goto L50
            android.hardware.devicestate.DeviceStateRequest$Builder r1 = android.hardware.devicestate.DeviceStateRequest.newBuilder(r3)
            android.hardware.devicestate.DeviceStateRequest$Builder r1 = r1.setFlags(r3)
            android.hardware.devicestate.DeviceStateRequest r1 = r1.build()
            goto L63
        L50:
            if (r6 != r1) goto L62
            r1 = 1
            android.hardware.devicestate.DeviceStateRequest$Builder r1 = android.hardware.devicestate.DeviceStateRequest.newBuilder(r1)
            r3 = 8
            android.hardware.devicestate.DeviceStateRequest$Builder r1 = r1.setFlags(r3)
            android.hardware.devicestate.DeviceStateRequest r1 = r1.build()
            goto L63
        L62:
            r1 = 0
        L63:
            if (r1 == 0) goto L8e
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "setForcedDefaultDisplayDevice "
            r3.<init>(r4)
            r3.append(r6)
            java.lang.String r6 = ", callingPid="
            r3.append(r6)
            r3.append(r2)
            java.lang.String r6 = r3.toString()
            android.util.Log.d(r0, r6)
            android.hardware.devicestate.DeviceStateManagerGlobal r6 = r5.mDeviceStateManagerGlobal
            android.app.PendingIntent$$ExternalSyntheticLambda0 r0 = new android.app.PendingIntent$$ExternalSyntheticLambda0
            r0.<init>()
            com.samsung.android.view.SemWindowManager$1 r3 = new com.samsung.android.view.SemWindowManager$1
            r3.<init>(r5)
            r6.requestState(r1, r0, r3)
        L8e:
            return
        L8f:
            java.lang.String r5 = "displayDeviceType is wrong"
            android.util.Log.e(r0, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.view.SemWindowManager.setForcedDefaultDisplayDevice(int):void");
    }

    public int getFullScreenAppsSupportMode() {
        try {
            return this.mWindowManager.getFullScreenAppsSupportMode();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to getFullScreenAppsSupportMode", e);
            return 0;
        }
    }

    public static boolean isSupportAspectRatioMode(Context context) {
        return CoreRune.IS_TABLET_DEVICE;
    }

    public int getMaxAspectRatioPolicy(String str, int i) {
        try {
            return this.mWindowManager.getMaxAspectRatioPolicy(str, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to getMaxAspectRatioPolicy", e);
            return 0;
        }
    }

    public void setMaxAspectRatioPolicy(String str, int i, boolean z, int i2) {
        try {
            this.mWindowManager.setMaxAspectRatioPolicy(str, i, z, i2);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to setMaxAspectRatioPolicy", e);
        }
    }

    public int getAppContinuityMode(String str, ActivityInfo activityInfo, int i) {
        try {
            return this.mWindowManager.getAppContinuityMode(i, str, activityInfo);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to getAppContinuityMode", e);
            return 0;
        }
    }

    public void setAppContinuityMode(String str, int i, boolean z) {
        try {
            this.mWindowManager.setAppContinuityMode(i, str, z);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to setAppContinuityMode", e);
        }
    }

    public static class VisibleWindowInfo implements Parcelable {
        public static final Parcelable.Creator<VisibleWindowInfo> CREATOR = new Parcelable.Creator<VisibleWindowInfo>() { // from class: com.samsung.android.view.SemWindowManager.VisibleWindowInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public VisibleWindowInfo createFromParcel(Parcel parcel) {
                return new VisibleWindowInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public VisibleWindowInfo[] newArray(int i) {
                return new VisibleWindowInfo[i];
            }
        };
        public boolean focused;
        public boolean lastFocused;
        public String name;
        public String packageName;
        public int type;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public VisibleWindowInfo() {
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.packageName);
            parcel.writeString(this.name);
            parcel.writeInt(this.type);
            parcel.writeInt(this.focused ? 1 : 0);
            parcel.writeInt(this.lastFocused ? 1 : 0);
        }

        public void readFromParcel(Parcel parcel) {
            this.packageName = parcel.readString();
            this.name = parcel.readString();
            this.type = parcel.readInt();
            this.focused = parcel.readInt() != 0;
            this.lastFocused = parcel.readInt() != 0;
        }

        private VisibleWindowInfo(Parcel parcel) {
            readFromParcel(parcel);
        }
    }

    @Deprecated
    public List<VisibleWindowInfo> getVisibleWindowInfo() {
        return new ArrayList();
    }

    public List<VisibleWindowInfo> getVisibleWindowInfoList() {
        try {
            return this.mWindowManager.getVisibleWindowInfoList();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to getVisibleWindowInfoList", e);
            return null;
        }
    }

    public Bitmap screenshot(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2) {
        return screenshot(i, i2, z, rect, i3, i4, z2, 0);
    }

    public Bitmap screenshot(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, int i5) {
        return screenshot(i, i2, z, rect, i3, i4, z2, i5, false);
    }

    public Bitmap screenshot(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, int i5, boolean z3) {
        try {
            ScreenshotResult takeScreenshotToTargetWindow = this.mWindowManager.takeScreenshotToTargetWindow(i, i2, z, rect, i3, i4, z2, z3);
            if (takeScreenshotToTargetWindow != null) {
                return takeScreenshotToTargetWindow.getCapturedBitmap();
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to screenshot", e);
            return null;
        }
    }

    public int getSupportsFlexPanel(int i, String str) {
        try {
            return this.mWindowManager.getSupportsFlexPanel(i, str);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to getSupportsFlexPanel", e);
            return 2;
        }
    }

    public void setSupportsFlexPanel(int i, String str, boolean z) {
        try {
            this.mWindowManager.setSupportsFlexPanel(i, str, z);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to setSupportsFlexPanel", e);
        }
    }

    public static final class KeyCustomizationInfo implements Parcelable {
        public static final Parcelable.Creator<KeyCustomizationInfo> CREATOR = new Parcelable.Creator<KeyCustomizationInfo>() { // from class: com.samsung.android.view.SemWindowManager.KeyCustomizationInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public KeyCustomizationInfo createFromParcel(Parcel parcel) {
                return new KeyCustomizationInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public KeyCustomizationInfo[] newArray(int i) {
                return new KeyCustomizationInfo[i];
            }
        };
        public int action;
        public String callingPackageName;
        public int callingPid;
        public int dispatching;
        public int id;
        public Intent intent;
        public int keyCode;
        public long longPressTimeout;
        public long multiPressTimeout;
        public String ownerPackage;
        public int press;
        public long timestamp;
        public int userId;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public KeyCustomizationInfo() {
            this.press = -1;
            this.id = 1000;
            this.keyCode = 0;
            this.action = -1;
            this.intent = null;
            this.dispatching = 0;
            this.userId = -2;
        }

        public KeyCustomizationInfo(int i, int i2, int i3, int i4) {
            this(i, i2, i3, i4, null);
        }

        public KeyCustomizationInfo(int i, int i2, int i3, int i4, Intent intent) {
            this(i, i2, i3, i4, intent, -1);
        }

        public KeyCustomizationInfo(int i, int i2, int i3, int i4, Intent intent, int i5) {
            this(i, i2, i3, i4, intent, i5, -2, null);
        }

        public KeyCustomizationInfo(int i, int i2, int i3, int i4, Intent intent, int i5, int i6) {
            this(i, i2, i3, i4, intent, i5, i6, null);
        }

        private KeyCustomizationInfo(Builder builder) {
            this(builder.press, builder.id, builder.keyCode, builder.action, builder.intent, builder.dispatching, builder.userId, builder.ownerPackage);
        }

        private KeyCustomizationInfo(int i, int i2, int i3, int i4, Intent intent, int i5, int i6, String str) {
            this.press = i;
            this.id = i2;
            this.keyCode = i3;
            this.action = i4;
            this.intent = intent;
            this.dispatching = i5;
            this.userId = i6;
            this.ownerPackage = str;
        }

        public void setLongPressTimeoutMs(long j) {
            this.longPressTimeout = j;
        }

        public void setMultiPressTimeoutMs(long j) {
            this.multiPressTimeout = j;
        }

        public int getId() {
            return this.id;
        }

        public int getPress() {
            return this.press;
        }

        public int getKeyCode() {
            return this.keyCode;
        }

        public int getAction() {
            return this.action;
        }

        public Intent getIntent() {
            return this.intent;
        }

        public int getDispatching() {
            return this.dispatching;
        }

        public int getUserId() {
            return this.userId;
        }

        public String getOwnerPackage() {
            return this.ownerPackage;
        }

        public static class Builder {
            private int action;
            private int id;
            private Intent intent;
            private int keyCode;
            private String ownerPackage;
            private int press;
            private int dispatching = 0;
            private int userId = -2;

            public Builder(int i, int i2, int i3, Intent intent, String str) {
                this.press = -1;
                this.id = -1;
                this.keyCode = 0;
                this.action = -1;
                this.intent = null;
                this.ownerPackage = null;
                if (i3 != 4 && intent == null) {
                    throw new IllegalArgumentException("Intent is null. When the action is not ACTION_BLOCK_KEY_EVENT, you have to add intent parameter.");
                }
                this.press = i;
                this.keyCode = i2;
                this.action = i3;
                this.intent = intent;
                this.ownerPackage = str;
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                this.id = 2003;
            }

            public KeyCustomizationInfo build() {
                return new KeyCustomizationInfo(this);
            }

            public Builder setDispatching(int i) {
                this.dispatching = i;
                return this;
            }

            public Builder setUserId(int i) {
                this.userId = i;
                return this;
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.press);
            parcel.writeInt(this.id);
            parcel.writeInt(this.keyCode);
            parcel.writeInt(this.action);
            parcel.writeTypedObject(this.intent, i);
            parcel.writeInt(this.dispatching);
            parcel.writeInt(this.userId);
            parcel.writeLong(this.longPressTimeout);
            parcel.writeLong(this.multiPressTimeout);
            parcel.writeString(this.ownerPackage);
        }

        public void readFromParcel(Parcel parcel) {
            this.press = parcel.readInt();
            this.id = parcel.readInt();
            this.keyCode = parcel.readInt();
            this.action = parcel.readInt();
            this.intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            this.dispatching = parcel.readInt();
            this.userId = parcel.readInt();
            this.longPressTimeout = parcel.readLong();
            this.multiPressTimeout = parcel.readLong();
            this.ownerPackage = parcel.readString();
        }

        private KeyCustomizationInfo(Parcel parcel) {
            this.press = -1;
            this.id = 1000;
            this.keyCode = 0;
            this.action = -1;
            this.intent = null;
            this.dispatching = 0;
            this.userId = -2;
            readFromParcel(parcel);
        }
    }

    public void putKeyCustomizationInfo(KeyCustomizationInfo keyCustomizationInfo) throws IllegalArgumentException {
        try {
            this.mWindowManager.putKeyCustomizationInfo(keyCustomizationInfo);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to putKeyCustomizationInfo", e);
        }
    }

    public KeyCustomizationInfo getKeyCustomizationInfo(String str, int i, int i2) {
        try {
            return this.mWindowManager.getKeyCustomizationInfoByPackage(str, i, i2);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to getKeyCustomizationInfo", e);
            return null;
        }
    }

    public KeyCustomizationInfo getLastKeyCustomizationInfo(int i, int i2) {
        try {
            return this.mWindowManager.getLastKeyCustomizationInfo(i, i2);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to getKeyCustomizationInfo", e);
            return null;
        }
    }

    public void removeKeyCustomizationInfo(String str, int i, int i2) {
        try {
            this.mWindowManager.removeKeyCustomizationInfoByPackage(str, i, i2);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to removeKeyCustomizationInfo", e);
        }
    }

    private void setForcedDisplaySizeDensityInner(int i, int i2, int i3, boolean z, int i4) {
        Log.d(TAG, "setForcedDisplaySizeDensityInner userId=" + UserHandle.myUserId());
        if (validateForcedDisplaySizeDensityValues(i, i2, i3)) {
            try {
                this.mWindowManager.setForcedDisplaySizeDensityWithInfo(new MultiResolutionChangeRequestInfo.Builder(0).setWidth(i).setHeight(i2).setDensity(i3).setSaveToSettings(z).setForcedHideCutout(i4).build());
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to setForcedDisplaySizeDensity, ", e);
            }
        }
    }

    private boolean validateForcedDisplaySizeDensityValues(int i, int i2, int i3) {
        if (i == i2) {
            Log.w(TAG, "validateForcedDisplaySizeDensityValues: width/height must be different");
            return false;
        }
        if (i <= 200 && i2 <= 200) {
            Log.w(TAG, "validateForcedDisplaySizeDensityValues: width/height must be > 200");
            return false;
        }
        if (i3 >= 72) {
            return true;
        }
        Log.w(TAG, "validateForcedDisplaySizeDensityValues: density must be >= 72");
        return false;
    }
}
