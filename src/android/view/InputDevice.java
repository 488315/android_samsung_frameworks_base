package android.view;

import android.app.jank.AppJankStats;
import android.hardware.BatteryState;
import android.hardware.SensorManager;
import android.hardware.input.HostUsiVersion;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManagerGlobal;
import android.hardware.lights.LightsManager;
import android.icu.util.ULocale;
import android.os.NullVibrator;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.text.TextUtils;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class InputDevice implements Parcelable {
    public static final Parcelable.Creator<InputDevice> CREATOR = new Parcelable.Creator<InputDevice>() { // from class: android.view.InputDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputDevice createFromParcel(Parcel parcel) {
            return new InputDevice(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputDevice[] newArray(int i) {
            return new InputDevice[i];
        }
    };
    public static final int KEYBOARD_TYPE_ALPHABETIC = 2;
    public static final int KEYBOARD_TYPE_NONE = 0;
    public static final int KEYBOARD_TYPE_NON_ALPHABETIC = 1;
    private static final int MAX_RANGES = 1000;

    @Deprecated
    public static final int MOTION_RANGE_ORIENTATION = 8;

    @Deprecated
    public static final int MOTION_RANGE_PRESSURE = 2;

    @Deprecated
    public static final int MOTION_RANGE_SIZE = 3;

    @Deprecated
    public static final int MOTION_RANGE_TOOL_MAJOR = 6;

    @Deprecated
    public static final int MOTION_RANGE_TOOL_MINOR = 7;

    @Deprecated
    public static final int MOTION_RANGE_TOUCH_MAJOR = 4;

    @Deprecated
    public static final int MOTION_RANGE_TOUCH_MINOR = 5;

    @Deprecated
    public static final int MOTION_RANGE_X = 0;

    @Deprecated
    public static final int MOTION_RANGE_Y = 1;

    @Deprecated(forRemoval = true, since = "13.0")
    public static final int SEM_SOURCE_CAR_KNOB = 1073741824;
    public static final int SOURCE_ANY = -256;
    public static final int SOURCE_BLUETOOTH_STYLUS = 49154;
    public static final int SOURCE_CLASS_BUTTON = 1;
    public static final int SOURCE_CLASS_JOYSTICK = 16;
    public static final int SOURCE_CLASS_MASK = 255;
    public static final int SOURCE_CLASS_NONE = 0;
    public static final int SOURCE_CLASS_POINTER = 2;
    public static final int SOURCE_CLASS_POSITION = 8;
    public static final int SOURCE_CLASS_TRACKBALL = 4;
    public static final int SOURCE_DPAD = 513;
    public static final int SOURCE_GAMEPAD = 1025;
    public static final int SOURCE_HDMI = 33554433;
    public static final int SOURCE_JOYSTICK = 16777232;
    public static final int SOURCE_KEYBOARD = 257;
    public static final int SOURCE_MOUSE = 8194;
    public static final int SOURCE_MOUSE_RELATIVE = 131076;
    public static final int SOURCE_ROTARY_ENCODER = 4194304;
    public static final int SOURCE_SENSOR = 67108864;
    public static final int SOURCE_STYLUS = 16386;
    public static final int SOURCE_TOUCHPAD = 1048584;
    public static final int SOURCE_TOUCHSCREEN = 4098;
    public static final int SOURCE_TOUCH_NAVIGATION = 2097152;
    public static final int SOURCE_TRACKBALL = 65540;
    public static final int SOURCE_UNKNOWN = 0;
    private static final int VIBRATOR_ID_ALL = -1;
    private final int mAssociatedDisplayId;
    private final int mControllerNumber;
    private final String mDescriptor;
    private final int mDeviceBus;
    private final boolean mEnabled;
    private final int mGeneration;
    private final boolean mHasBattery;
    private final boolean mHasButtonUnderPad;
    private final boolean mHasMicrophone;
    private final boolean mHasSensor;
    private final boolean mHasVibrator;
    private final HostUsiVersion mHostUsiVersion;
    private final int mId;
    private final InputDeviceIdentifier mIdentifier;
    private final boolean mIsExternal;
    private final KeyCharacterMap mKeyCharacterMap;
    private final String mKeyboardLanguageTag;
    private final String mKeyboardLayoutType;
    private final int mKeyboardType;
    private LightsManager mLightsManager;
    private final ArrayList<MotionRange> mMotionRanges;
    private final String mName;
    private final int mProductId;
    private SensorManager mSensorManager;
    private final int mSources;
    private final int mVendorId;
    private Vibrator mVibrator;
    private VibratorManager mVibratorManager;
    private final ViewBehavior mViewBehavior;

    @Retention(RetentionPolicy.SOURCE)
    @interface InputSourceClass {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface Source {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void semSetPointerType(int i) {
    }

    private InputDevice(int i, int i2, int i3, String str, int i4, int i5, int i6, String str2, boolean z, int i7, int i8, KeyCharacterMap keyCharacterMap, String str3, String str4, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i9, int i10, int i11, boolean z7) {
        this.mMotionRanges = new ArrayList<>();
        this.mViewBehavior = new ViewBehavior(this);
        this.mId = i;
        this.mGeneration = i2;
        this.mControllerNumber = i3;
        this.mName = str;
        this.mVendorId = i4;
        this.mProductId = i5;
        this.mDeviceBus = i6;
        this.mDescriptor = str2;
        this.mIsExternal = z;
        this.mSources = i7;
        this.mKeyboardType = i8;
        this.mKeyCharacterMap = keyCharacterMap;
        if (!TextUtils.isEmpty(str3)) {
            String languageTag = ULocale.createCanonical(ULocale.forLanguageTag(str3)).toLanguageTag();
            this.mKeyboardLanguageTag = TextUtils.equals(languageTag, "und") ? null : languageTag;
        } else {
            this.mKeyboardLanguageTag = null;
        }
        this.mKeyboardLayoutType = str4;
        this.mHasVibrator = z2;
        this.mHasMicrophone = z3;
        this.mHasButtonUnderPad = z4;
        this.mHasSensor = z5;
        this.mHasBattery = z6;
        this.mIdentifier = new InputDeviceIdentifier(str2, i4, i5);
        this.mHostUsiVersion = new HostUsiVersion(i9, i10);
        this.mAssociatedDisplayId = i11;
        this.mEnabled = z7;
    }

    private InputDevice(Parcel parcel) {
        this.mMotionRanges = new ArrayList<>();
        this.mViewBehavior = new ViewBehavior(this);
        this.mKeyCharacterMap = KeyCharacterMap.CREATOR.createFromParcel(parcel);
        this.mId = parcel.readInt();
        this.mGeneration = parcel.readInt();
        this.mControllerNumber = parcel.readInt();
        this.mName = parcel.readString();
        int readInt = parcel.readInt();
        this.mVendorId = readInt;
        int readInt2 = parcel.readInt();
        this.mProductId = readInt2;
        this.mDeviceBus = parcel.readInt();
        String readString = parcel.readString();
        this.mDescriptor = readString;
        this.mIsExternal = parcel.readInt() != 0;
        this.mSources = parcel.readInt();
        this.mKeyboardType = parcel.readInt();
        this.mKeyboardLanguageTag = parcel.readString8();
        this.mKeyboardLayoutType = parcel.readString8();
        this.mHasVibrator = parcel.readInt() != 0;
        this.mHasMicrophone = parcel.readInt() != 0;
        this.mHasButtonUnderPad = parcel.readInt() != 0;
        this.mHasSensor = parcel.readInt() != 0;
        this.mHasBattery = parcel.readInt() != 0;
        this.mHostUsiVersion = HostUsiVersion.CREATOR.createFromParcel(parcel);
        this.mAssociatedDisplayId = parcel.readInt();
        this.mEnabled = parcel.readInt() != 0;
        this.mIdentifier = new InputDeviceIdentifier(readString, readInt, readInt2);
        int readInt3 = parcel.readInt();
        readInt3 = readInt3 > 1000 ? 1000 : readInt3;
        for (int i = 0; i < readInt3; i++) {
            addMotionRange(parcel.readInt(), parcel.readInt(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat());
        }
        this.mViewBehavior.mShouldSmoothScroll = parcel.readBoolean();
    }

    public static class Builder {
        private boolean mShouldSmoothScroll;
        private int mId = 0;
        private int mGeneration = 0;
        private int mControllerNumber = 0;
        private String mName = "";
        private int mVendorId = 0;
        private int mProductId = 0;
        private int mDeviceBus = 0;
        private String mDescriptor = "";
        private boolean mIsExternal = false;
        private int mSources = 0;
        private int mKeyboardType = 0;
        private KeyCharacterMap mKeyCharacterMap = null;
        private boolean mHasVibrator = false;
        private boolean mHasMicrophone = false;
        private boolean mHasButtonUnderPad = false;
        private boolean mHasSensor = false;
        private boolean mHasBattery = false;
        private String mKeyboardLanguageTag = null;
        private String mKeyboardLayoutType = null;
        private int mUsiVersionMajor = -1;
        private int mUsiVersionMinor = -1;
        private int mAssociatedDisplayId = -1;
        private boolean mEnabled = true;
        private List<MotionRange> mMotionRanges = new ArrayList();

        public Builder setId(int i) {
            this.mId = i;
            return this;
        }

        public Builder setGeneration(int i) {
            this.mGeneration = i;
            return this;
        }

        public Builder setControllerNumber(int i) {
            this.mControllerNumber = i;
            return this;
        }

        public Builder setName(String str) {
            this.mName = str;
            return this;
        }

        public Builder setVendorId(int i) {
            this.mVendorId = i;
            return this;
        }

        public Builder setProductId(int i) {
            this.mProductId = i;
            return this;
        }

        public Builder setDeviceBus(int i) {
            this.mDeviceBus = i;
            return this;
        }

        public Builder setDescriptor(String str) {
            this.mDescriptor = str;
            return this;
        }

        public Builder setExternal(boolean z) {
            this.mIsExternal = z;
            return this;
        }

        public Builder setSources(int i) {
            this.mSources = i;
            return this;
        }

        public Builder setKeyboardType(int i) {
            this.mKeyboardType = i;
            return this;
        }

        public Builder setKeyCharacterMap(KeyCharacterMap keyCharacterMap) {
            this.mKeyCharacterMap = keyCharacterMap;
            return this;
        }

        public Builder setHasVibrator(boolean z) {
            this.mHasVibrator = z;
            return this;
        }

        public Builder setHasMicrophone(boolean z) {
            this.mHasMicrophone = z;
            return this;
        }

        public Builder setHasButtonUnderPad(boolean z) {
            this.mHasButtonUnderPad = z;
            return this;
        }

        public Builder setHasSensor(boolean z) {
            this.mHasSensor = z;
            return this;
        }

        public Builder setHasBattery(boolean z) {
            this.mHasBattery = z;
            return this;
        }

        public Builder setKeyboardLanguageTag(String str) {
            this.mKeyboardLanguageTag = str;
            return this;
        }

        public Builder setKeyboardLayoutType(String str) {
            this.mKeyboardLayoutType = str;
            return this;
        }

        public Builder setUsiVersion(HostUsiVersion hostUsiVersion) {
            this.mUsiVersionMajor = hostUsiVersion != null ? hostUsiVersion.getMajorVersion() : -1;
            this.mUsiVersionMinor = hostUsiVersion != null ? hostUsiVersion.getMinorVersion() : -1;
            return this;
        }

        public Builder setAssociatedDisplayId(int i) {
            this.mAssociatedDisplayId = i;
            return this;
        }

        public Builder setEnabled(boolean z) {
            this.mEnabled = z;
            return this;
        }

        public Builder addMotionRange(int i, int i2, float f, float f2, float f3, float f4, float f5) {
            this.mMotionRanges.add(new MotionRange(i, i2, f, f2, f3, f4, f5));
            return this;
        }

        public Builder setShouldSmoothScroll(boolean z) {
            this.mShouldSmoothScroll = z;
            return this;
        }

        public InputDevice build() {
            InputDevice inputDevice = new InputDevice(this.mId, this.mGeneration, this.mControllerNumber, this.mName, this.mVendorId, this.mProductId, this.mDeviceBus, this.mDescriptor, this.mIsExternal, this.mSources, this.mKeyboardType, this.mKeyCharacterMap, this.mKeyboardLanguageTag, this.mKeyboardLayoutType, this.mHasVibrator, this.mHasMicrophone, this.mHasButtonUnderPad, this.mHasSensor, this.mHasBattery, this.mUsiVersionMajor, this.mUsiVersionMinor, this.mAssociatedDisplayId, this.mEnabled);
            int size = this.mMotionRanges.size();
            for (int i = 0; i < size; i++) {
                MotionRange motionRange = this.mMotionRanges.get(i);
                inputDevice.addMotionRange(motionRange.getAxis(), motionRange.getSource(), motionRange.getMin(), motionRange.getMax(), motionRange.getFlat(), motionRange.getFuzz(), motionRange.getResolution());
            }
            inputDevice.setShouldSmoothScroll(this.mShouldSmoothScroll);
            return inputDevice;
        }
    }

    public static InputDevice getDevice(int i) {
        return InputManagerGlobal.getInstance().getInputDevice(i);
    }

    public static int[] getDeviceIds() {
        return InputManagerGlobal.getInstance().getInputDeviceIds();
    }

    public int getId() {
        return this.mId;
    }

    public int getControllerNumber() {
        return this.mControllerNumber;
    }

    public InputDeviceIdentifier getIdentifier() {
        return this.mIdentifier;
    }

    public int getGeneration() {
        return this.mGeneration;
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    public int getProductId() {
        return this.mProductId;
    }

    public int getDeviceBus() {
        return this.mDeviceBus;
    }

    public String getDescriptor() {
        return this.mDescriptor;
    }

    public boolean isVirtual() {
        return this.mId < 0;
    }

    public boolean isExternal() {
        return this.mIsExternal;
    }

    public boolean isFullKeyboard() {
        return (this.mSources & 257) == 257 && this.mKeyboardType == 2;
    }

    public String getName() {
        return this.mName;
    }

    public int getSources() {
        return this.mSources;
    }

    public boolean supportsSource(int i) {
        return (this.mSources & i) == i;
    }

    public int getKeyboardType() {
        return this.mKeyboardType;
    }

    public KeyCharacterMap getKeyCharacterMap() {
        return this.mKeyCharacterMap;
    }

    public String getKeyboardLanguageTag() {
        return this.mKeyboardLanguageTag;
    }

    public String getKeyboardLayoutType() {
        return this.mKeyboardLayoutType;
    }

    public boolean[] hasKeys(int... iArr) {
        return InputManagerGlobal.getInstance().deviceHasKeys(this.mId, iArr);
    }

    public int getKeyCodeForKeyLocation(int i) {
        return InputManagerGlobal.getInstance().getKeyCodeForKeyLocation(this.mId, i);
    }

    public MotionRange getMotionRange(int i) {
        int size = this.mMotionRanges.size();
        for (int i2 = 0; i2 < size; i2++) {
            MotionRange motionRange = this.mMotionRanges.get(i2);
            if (motionRange.mAxis == i) {
                return motionRange;
            }
        }
        return null;
    }

    public MotionRange getMotionRange(int i, int i2) {
        int size = this.mMotionRanges.size();
        for (int i3 = 0; i3 < size; i3++) {
            MotionRange motionRange = this.mMotionRanges.get(i3);
            if (motionRange.mAxis == i && motionRange.mSource == i2) {
                return motionRange;
            }
        }
        return null;
    }

    public List<MotionRange> getMotionRanges() {
        return this.mMotionRanges;
    }

    public ViewBehavior getViewBehavior() {
        return this.mViewBehavior;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addMotionRange(int i, int i2, float f, float f2, float f3, float f4, float f5) {
        this.mMotionRanges.add(new MotionRange(i, i2, f, f2, f3, f4, f5));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setShouldSmoothScroll(boolean z) {
        this.mViewBehavior.mShouldSmoothScroll = z;
    }

    public String getBluetoothAddress() {
        return InputManagerGlobal.getInstance().getInputDeviceBluetoothAddress(this.mId);
    }

    @Deprecated
    public Vibrator getVibrator() {
        Vibrator vibrator;
        synchronized (this.mMotionRanges) {
            if (this.mVibrator == null) {
                if (this.mHasVibrator) {
                    this.mVibrator = InputManagerGlobal.getInstance().getInputDeviceVibrator(this.mId, -1);
                } else {
                    this.mVibrator = NullVibrator.getInstance();
                }
            }
            vibrator = this.mVibrator;
        }
        return vibrator;
    }

    public VibratorManager getVibratorManager() {
        synchronized (this.mMotionRanges) {
            if (this.mVibratorManager == null) {
                this.mVibratorManager = InputManagerGlobal.getInstance().getInputDeviceVibratorManager(this.mId);
            }
        }
        return this.mVibratorManager;
    }

    public BatteryState getBatteryState() {
        return InputManagerGlobal.getInstance().getInputDeviceBatteryState(this.mId, this.mHasBattery);
    }

    public LightsManager getLightsManager() {
        synchronized (this.mMotionRanges) {
            if (this.mLightsManager == null) {
                this.mLightsManager = InputManagerGlobal.getInstance().getInputDeviceLightsManager(this.mId);
            }
        }
        return this.mLightsManager;
    }

    public SensorManager getSensorManager() {
        synchronized (this.mMotionRanges) {
            if (this.mSensorManager == null) {
                this.mSensorManager = InputManagerGlobal.getInstance().getInputDeviceSensorManager(this.mId);
            }
        }
        return this.mSensorManager;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void enable() {
        InputManagerGlobal.getInstance().enableInputDevice(this.mId);
    }

    public void disable() {
        InputManagerGlobal.getInstance().disableInputDevice(this.mId);
    }

    public boolean hasMicrophone() {
        return this.mHasMicrophone;
    }

    public boolean hasButtonUnderPad() {
        return this.mHasButtonUnderPad;
    }

    public boolean hasSensor() {
        return this.mHasSensor;
    }

    public boolean hasBattery() {
        return this.mHasBattery;
    }

    public HostUsiVersion getHostUsiVersion() {
        if (this.mHostUsiVersion.isValid()) {
            return this.mHostUsiVersion;
        }
        return null;
    }

    public int getAssociatedDisplayId() {
        return this.mAssociatedDisplayId;
    }

    public static final class MotionRange {
        private int mAxis;
        private float mFlat;
        private float mFuzz;
        private float mMax;
        private float mMin;
        private float mResolution;
        private int mSource;

        private MotionRange(int i, int i2, float f, float f2, float f3, float f4, float f5) {
            this.mAxis = i;
            this.mSource = i2;
            this.mMin = f;
            this.mMax = f2;
            this.mFlat = f3;
            this.mFuzz = f4;
            this.mResolution = f5;
        }

        public int getAxis() {
            return this.mAxis;
        }

        public int getSource() {
            return this.mSource;
        }

        public boolean isFromSource(int i) {
            return (getSource() & i) == i;
        }

        public float getMin() {
            return this.mMin;
        }

        public float getMax() {
            return this.mMax;
        }

        public float getRange() {
            return this.mMax - this.mMin;
        }

        public float getFlat() {
            return this.mFlat;
        }

        public float getFuzz() {
            return this.mFuzz;
        }

        public float getResolution() {
            return this.mResolution;
        }
    }

    public static final class ViewBehavior {
        private static final boolean DEFAULT_SHOULD_SMOOTH_SCROLL = false;
        private final InputDevice mInputDevice;
        private boolean mShouldSmoothScroll = false;

        public ViewBehavior(InputDevice inputDevice) {
            this.mInputDevice = inputDevice;
        }

        public boolean shouldSmoothScroll(int i, int i2) {
            if (this.mInputDevice.getMotionRange(i, i2) == null) {
                return false;
            }
            return this.mShouldSmoothScroll;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mKeyCharacterMap.writeToParcel(parcel, i);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mGeneration);
        parcel.writeInt(this.mControllerNumber);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mVendorId);
        parcel.writeInt(this.mProductId);
        parcel.writeInt(this.mDeviceBus);
        parcel.writeString(this.mDescriptor);
        parcel.writeInt(this.mIsExternal ? 1 : 0);
        parcel.writeInt(this.mSources);
        parcel.writeInt(this.mKeyboardType);
        parcel.writeString8(this.mKeyboardLanguageTag);
        parcel.writeString8(this.mKeyboardLayoutType);
        parcel.writeInt(this.mHasVibrator ? 1 : 0);
        parcel.writeInt(this.mHasMicrophone ? 1 : 0);
        parcel.writeInt(this.mHasButtonUnderPad ? 1 : 0);
        parcel.writeInt(this.mHasSensor ? 1 : 0);
        parcel.writeInt(this.mHasBattery ? 1 : 0);
        this.mHostUsiVersion.writeToParcel(parcel, i);
        parcel.writeInt(this.mAssociatedDisplayId);
        parcel.writeInt(this.mEnabled ? 1 : 0);
        int size = this.mMotionRanges.size();
        if (size > 1000) {
            size = 1000;
        }
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            MotionRange motionRange = this.mMotionRanges.get(i2);
            parcel.writeInt(motionRange.mAxis);
            parcel.writeInt(motionRange.mSource);
            parcel.writeFloat(motionRange.mMin);
            parcel.writeFloat(motionRange.mMax);
            parcel.writeFloat(motionRange.mFlat);
            parcel.writeFloat(motionRange.mFuzz);
            parcel.writeFloat(motionRange.mResolution);
        }
        parcel.writeBoolean(this.mViewBehavior.mShouldSmoothScroll);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Input Device ");
        sb.append(this.mId);
        sb.append(": ");
        sb.append(this.mName);
        sb.append("\n  Descriptor: ");
        sb.append(this.mDescriptor);
        sb.append("\n  Generation: ");
        sb.append(this.mGeneration);
        sb.append("\n  Location: ");
        sb.append(this.mIsExternal ? "external" : "built-in");
        sb.append("\n  Enabled: ");
        sb.append(isEnabled());
        sb.append("\n  Keyboard Type: ");
        int i = this.mKeyboardType;
        if (i == 0) {
            sb.append("none");
        } else if (i == 1) {
            sb.append("non-alphabetic");
        } else if (i == 2) {
            sb.append("alphabetic");
        }
        sb.append("\n  Has Vibrator: ");
        sb.append(this.mHasVibrator);
        sb.append("\n  Has Sensor: ");
        sb.append(this.mHasSensor);
        sb.append("\n  Has battery: ");
        sb.append(this.mHasBattery);
        sb.append("\n  Has mic: ");
        sb.append(this.mHasMicrophone);
        sb.append("\n  USI Version: ");
        sb.append(getHostUsiVersion());
        sb.append(ShaderAssembler.NEWLINE);
        if (this.mKeyboardLanguageTag != null) {
            sb.append(" Keyboard language tag: ");
            sb.append(this.mKeyboardLanguageTag);
            sb.append(ShaderAssembler.NEWLINE);
        }
        if (this.mKeyboardLayoutType != null) {
            sb.append(" Keyboard layout type: ");
            sb.append(this.mKeyboardLayoutType);
            sb.append(ShaderAssembler.NEWLINE);
        }
        sb.append("  Sources: 0x");
        sb.append(Integer.toHexString(this.mSources));
        sb.append(" (");
        appendSourceDescriptionIfApplicable(sb, 257, AppJankStats.WIDGET_CATEGORY_KEYBOARD);
        appendSourceDescriptionIfApplicable(sb, 513, "dpad");
        appendSourceDescriptionIfApplicable(sb, 4098, "touchscreen");
        appendSourceDescriptionIfApplicable(sb, 8194, "mouse");
        appendSourceDescriptionIfApplicable(sb, 16386, "stylus");
        appendSourceDescriptionIfApplicable(sb, 65540, "trackball");
        appendSourceDescriptionIfApplicable(sb, 131076, "mouse_relative");
        appendSourceDescriptionIfApplicable(sb, 1048584, "touchpad");
        appendSourceDescriptionIfApplicable(sb, SOURCE_JOYSTICK, "joystick");
        appendSourceDescriptionIfApplicable(sb, 1025, "gamepad");
        sb.append(" )\n");
        int size = this.mMotionRanges.size();
        for (int i2 = 0; i2 < size; i2++) {
            MotionRange motionRange = this.mMotionRanges.get(i2);
            sb.append("    ");
            sb.append(MotionEvent.axisToString(motionRange.mAxis));
            sb.append(": source=0x");
            sb.append(Integer.toHexString(motionRange.mSource));
            sb.append(" min=");
            sb.append(motionRange.mMin);
            sb.append(" max=");
            sb.append(motionRange.mMax);
            sb.append(" flat=");
            sb.append(motionRange.mFlat);
            sb.append(" fuzz=");
            sb.append(motionRange.mFuzz);
            sb.append(" resolution=");
            sb.append(motionRange.mResolution);
            sb.append(ShaderAssembler.NEWLINE);
        }
        return sb.toString();
    }

    private void appendSourceDescriptionIfApplicable(StringBuilder sb, int i, String str) {
        if ((this.mSources & i) == i) {
            sb.append(" ");
            sb.append(str);
        }
    }
}
