package com.samsung.cmfa.AuthTouch;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.hardware.input.InputManager;
import android.media.MediaMetrics;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.WindowManager;
import android.view.WindowManagerPolicyConstants;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.content.NativeLibraryHelper;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class TouchEventView implements InputManager.InputDeviceListener, WindowManagerPolicyConstants.PointerEventListener {
    private static final String FLOATING_KEYBOARD_HEIGHT = "floating_keyboard_height";
    private static final String FLOATING_KEYBOARD_INFO = "floating_keyboard_info";
    private static final String FLOATING_KEYBOARD_LOCATION_LAND_X = "floating_keyboard_location_land_x";
    private static final String FLOATING_KEYBOARD_LOCATION_LAND_Y = "floating_keyboard_location_land_y";
    private static final String FLOATING_KEYBOARD_LOCATION_X = "floating_keyboard_location_x";
    private static final String FLOATING_KEYBOARD_LOCATION_Y = "floating_keyboard_location_y";
    private static final String FLOATING_KEYBOARD_ON = "floating_keyboard_on";
    private static final String FLOATING_KEYBOARD_WIDTH = "floating_keyboard_width";
    public static final String HONEY_BOARD_PROVIDER = "content://com.samsung.android.honeyboard.provider.KeyboardSettingsProvider";
    private static final String HONEY_DEFAULT_PACKAGE_NAME = "com.samsung.android.honeyboard/.service.HoneyBoardService";
    public static final String KEYBOARD_SETTINGS_PROVIDER = "content://com.sec.android.inputmethod.implement.setting.provider.KeyboardSettingsProvider";
    public static final String KEYBOARD_SETTINGS_PROVIDER_BETA = "content://com.sec.android.inputmethod.beta.implement.setting.provider.KeyboardSettingsProvider";
    private static final String SKBDN_DEFAULT_PACKAGE_NAME = "com.sec.android.inputmethod.beta/com.sec.android.inputmethod.SamsungKeypad";
    private static final String SKBD_DEFAULT_PACKAGE_NAME = "com.sec.android.inputmethod/.SamsungKeypad";
    private static final String TAG = "TouchEventView";
    private boolean isTyping;
    private int keyboard_x;
    private int keyboard_y;
    private int mActivePointerId;
    private Context mContext;
    private boolean mCurDown;
    private int mCurNumPointers;
    private boolean mDebugmodeOn;
    private long mDownTime;
    private InputManager mIm;
    private int mMaxNumPointers;
    private final ArrayList<PointerState> mPointers;
    private float mRawX;
    private float mRawY;
    private float mTouchMajor;
    private float mTouchMinor;
    private long mUpTime;
    private final VelocityTracker mVelocity;
    private float x_offset;
    private float y_offset;
    private final Paint.FontMetricsInt mTextMetrics = new Paint.FontMetricsInt();
    private final FasterStringBuilder mText = new FasterStringBuilder();
    private final VelocityTracker mAltVelocity = null;
    private final MotionEvent.PointerCoords mTempCoords = new MotionEvent.PointerCoords();

    public TouchEventView(Context c, IAuthTouchEnableListener listener) {
        this.mIm = null;
        ArrayList<PointerState> arrayList = new ArrayList<>();
        this.mPointers = arrayList;
        this.mDebugmodeOn = false;
        this.mRawX = 0.0f;
        this.mRawY = 0.0f;
        this.mTouchMinor = 0.0f;
        this.mTouchMajor = 0.0f;
        this.mDownTime = 0L;
        this.mUpTime = 0L;
        this.keyboard_x = 0;
        this.keyboard_y = 0;
        this.x_offset = 0.0f;
        this.y_offset = 0.0f;
        this.isTyping = false;
        this.mIm = (InputManager) c.getSystemService(InputManager.class);
        this.mContext = c;
        PointerState ps = new PointerState();
        arrayList.add(ps);
        this.mActivePointerId = 0;
        this.mVelocity = VelocityTracker.obtain();
        AuthFactorTouchManager.getInstance(this.mContext).registerAuthTouchEnableListener(listener);
    }

    public void setDebugmode(boolean set) {
        this.mDebugmodeOn = set;
    }

    @Override // android.view.WindowManagerPolicyConstants.PointerEventListener
    public void onPointerEvent(MotionEvent event) {
        AuthFactorTouchManager.getInstance(this.mContext).onPointerEvent(event);
        if (this.mDebugmodeOn) {
            this.mRawX = event.getRawX();
            this.mRawY = event.getRawY();
            this.mTouchMinor = event.getTouchMinor();
            this.mTouchMajor = event.getTouchMajor();
            this.mDownTime = event.getDownTime();
            this.mUpTime = event.getEventTime();
            KeyboardInfo();
            int orient = setOffset();
            float f = this.mRawX + this.x_offset;
            this.mRawX = f;
            float f2 = this.mRawY + this.y_offset;
            this.mRawY = f2;
            this.isTyping = isUserTypingKeyboard(f, f2, orient);
            Log.m98i(TAG, "mTouchMajor: " + this.mTouchMajor + ", mTouchMinor: " + this.mTouchMinor + ", mDownTime: " + this.mDownTime + ", mUpTime: " + this.mUpTime);
            Log.m98i(TAG, "isTyping: " + this.isTyping);
            int action = event.getAction();
            int NP = this.mPointers.size();
            if (action == 0 || (action & 255) == 5) {
                int index = (action & 65280) >> 8;
                if (action == 0) {
                    for (int p = 0; p < NP; p++) {
                        PointerState ps = this.mPointers.get(p);
                        ps.clearTrace();
                        ps.mCurDown = false;
                    }
                    this.mCurDown = true;
                    this.mCurNumPointers = 0;
                    this.mMaxNumPointers = 0;
                    this.mVelocity.clear();
                    VelocityTracker velocityTracker = this.mAltVelocity;
                    if (velocityTracker != null) {
                        velocityTracker.clear();
                    }
                }
                int i = this.mCurNumPointers + 1;
                this.mCurNumPointers = i;
                if (this.mMaxNumPointers < i) {
                    this.mMaxNumPointers = i;
                }
                int id = event.getPointerId(index);
                while (NP <= id) {
                    this.mPointers.add(new PointerState());
                    NP++;
                }
                int i2 = this.mActivePointerId;
                if (i2 < 0 || !this.mPointers.get(i2).mCurDown) {
                    this.mActivePointerId = id;
                }
                PointerState ps2 = this.mPointers.get(id);
                ps2.mCurDown = true;
                InputDevice device = InputDevice.getDevice(event.getDeviceId());
                ps2.mHasBoundingBox = (device == null || device.getMotionRange(32) == null) ? false : true;
            }
            int NI = event.getPointerCount();
            this.mVelocity.addMovement(event);
            this.mVelocity.computeCurrentVelocity(1);
            VelocityTracker velocityTracker2 = this.mAltVelocity;
            if (velocityTracker2 != null) {
                velocityTracker2.addMovement(event);
                this.mAltVelocity.computeCurrentVelocity(1);
            }
            int N = event.getHistorySize();
            for (int historyPos = 0; historyPos < N; historyPos++) {
                for (int i3 = 0; i3 < NI; i3++) {
                    PointerState ps3 = this.mCurDown ? this.mPointers.get(event.getPointerId(i3)) : null;
                    MotionEvent.PointerCoords coords = ps3 != null ? ps3.mCoords : this.mTempCoords;
                    event.getHistoricalPointerCoords(i3, historyPos, coords);
                    if (ps3 != null) {
                        ps3.addTrace(coords.f544x, coords.f545y, false);
                    }
                }
            }
            for (int i4 = 0; i4 < NI; i4++) {
                int id2 = event.getPointerId(i4);
                PointerState ps4 = this.mCurDown ? this.mPointers.get(id2) : null;
                MotionEvent.PointerCoords coords2 = ps4 != null ? ps4.mCoords : this.mTempCoords;
                event.getPointerCoords(i4, coords2);
                if (ps4 != null) {
                    ps4.addTrace(coords2.f544x, coords2.f545y, true);
                    ps4.mXVelocity = this.mVelocity.getXVelocity(id2);
                    ps4.mYVelocity = this.mVelocity.getYVelocity(id2);
                    Log.m98i(TAG, "mXVelocity: " + ps4.mXVelocity + ", mYVelocity: " + ps4.mYVelocity);
                    VelocityTracker velocityTracker3 = this.mAltVelocity;
                    if (velocityTracker3 != null) {
                        ps4.mAltXVelocity = velocityTracker3.getXVelocity(id2);
                        ps4.mAltYVelocity = this.mAltVelocity.getYVelocity(id2);
                        Log.m98i(TAG, "mAltXVelocity: " + ps4.mAltXVelocity + ", mAltYVelocity: " + ps4.mAltYVelocity);
                    }
                    ps4.mToolType = event.getToolType(i4);
                    Log.m98i(TAG, "ToolType: " + ps4.mToolType);
                    if (ps4.mHasBoundingBox) {
                        ps4.mBoundingLeft = event.getAxisValue(32, i4);
                        ps4.mBoundingTop = event.getAxisValue(33, i4);
                        ps4.mBoundingRight = event.getAxisValue(34, i4);
                        ps4.mBoundingBottom = event.getAxisValue(35, i4);
                        Log.m98i(TAG, "mBoundingLeft: " + ps4.mBoundingLeft + ", mBoundingRight: " + ps4.mBoundingRight + ", mBoundingTop: " + ps4.mBoundingTop + ", mBoundingBottom: " + ps4.mBoundingBottom);
                    }
                }
            }
            if (action == 1 || action == 3 || (action & 255) == 6) {
                int index2 = (65280 & action) >> 8;
                int id3 = event.getPointerId(index2);
                PointerState ps5 = this.mPointers.get(id3);
                ps5.mCurDown = false;
                if (action != 1 && action != 3) {
                    this.mCurNumPointers--;
                    if (this.mActivePointerId == id3) {
                        this.mActivePointerId = event.getPointerId(index2 != 0 ? 0 : 1);
                    }
                    ps5.addTrace(Float.NaN, Float.NaN, false);
                    return;
                }
                this.mCurDown = false;
                this.mCurNumPointers = 0;
            }
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceAdded(int deviceId) {
        logInputDeviceState(deviceId, "Device Added");
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceChanged(int deviceId) {
        logInputDeviceState(deviceId, "Device Changed");
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceRemoved(int deviceId) {
        logInputDeviceState(deviceId, "Device Removed");
    }

    private void logInputDeviceState(int deviceId, String state) {
        InputManager inputManager = this.mIm;
        if (inputManager == null) {
            return;
        }
        InputDevice device = inputManager.getInputDevice(deviceId);
        if (device != null) {
            Log.m98i(TAG, state + ": " + device);
        } else {
            Log.m98i(TAG, state + ": " + deviceId);
        }
    }

    private void KeyboardInfo() {
        String[] columns = {FLOATING_KEYBOARD_INFO};
        Cursor c = null;
        try {
            String defaultIme = Settings.Secure.getString(this.mContext.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);
            if (SKBD_DEFAULT_PACKAGE_NAME.equals(defaultIme)) {
                c = this.mContext.getContentResolver().query(Uri.parse(KEYBOARD_SETTINGS_PROVIDER), null, null, columns, null);
            } else if (SKBDN_DEFAULT_PACKAGE_NAME.equals(defaultIme)) {
                c = this.mContext.getContentResolver().query(Uri.parse(KEYBOARD_SETTINGS_PROVIDER_BETA), null, null, columns, null);
            } else {
                if (!"com.samsung.android.honeyboard/.service.HoneyBoardService".equals(defaultIme)) {
                    Log.m94d(TAG, "unkown keyboard");
                    if (0 != 0) {
                        c.close();
                        return;
                    }
                    return;
                }
                c = this.mContext.getContentResolver().query(Uri.parse(HONEY_BOARD_PROVIDER), null, null, columns, null);
            }
            if (c != null) {
                c.moveToFirst();
                int isFloatingKeyboardOn = c.getInt(c.getColumnIndex(FLOATING_KEYBOARD_ON));
                int x = c.getInt(c.getColumnIndex(FLOATING_KEYBOARD_LOCATION_X));
                int y = c.getInt(c.getColumnIndex(FLOATING_KEYBOARD_LOCATION_Y));
                int landX = c.getInt(c.getColumnIndex(FLOATING_KEYBOARD_LOCATION_LAND_X));
                int landY = c.getInt(c.getColumnIndex(FLOATING_KEYBOARD_LOCATION_LAND_Y));
                int width = c.getInt(c.getColumnIndex(FLOATING_KEYBOARD_WIDTH));
                int height = c.getInt(c.getColumnIndex(FLOATING_KEYBOARD_HEIGHT));
                Log.m98i(TAG, "isFloatingKeyboardOn: " + isFloatingKeyboardOn + ", x: " + x + ", y: " + y + ", landX: " + landX + ", landY: " + landY);
                this.keyboard_x = width;
                this.keyboard_y = height;
            }
            if (c == null) {
                return;
            }
        } catch (Exception e) {
            if (0 == 0) {
                return;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                c.close();
            }
            throw th;
        }
        c.close();
    }

    private boolean isKeyboardShown() {
        InputMethodManager is = (InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        return is.semIsInputMethodShown();
    }

    private int setOffset() {
        Display display = ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        if (rotation == 0) {
            this.x_offset = 0.0f;
            this.y_offset = -1524.0f;
        } else if (rotation == 1) {
            this.x_offset = -301.0f;
            this.y_offset = -600.0f;
        } else if (rotation == 3) {
            this.x_offset = -353.0f;
            this.y_offset = -600.0f;
        }
        return rotation;
    }

    private boolean isUserTypingKeyboard(float x, float y, int orient) {
        if (!isKeyboardShown()) {
            return false;
        }
        if (orient == 2 || orient == 0) {
            if (x >= 0.0f && this.keyboard_x >= x && y >= 0.0f && this.keyboard_y >= y) {
                return true;
            }
        } else if ((orient == 1 || orient == 3) && x >= 0.0f && this.keyboard_x >= x && y >= 0.0f && this.keyboard_y >= y) {
            return true;
        }
        return false;
    }

    public static class PointerState {
        private float mAltXVelocity;
        private float mAltYVelocity;
        private float mBoundingBottom;
        private float mBoundingLeft;
        private float mBoundingRight;
        private float mBoundingTop;
        private boolean mCurDown;
        private boolean mHasBoundingBox;
        private int mToolType;
        private int mTraceCount;
        private float mXVelocity;
        private float mYVelocity;
        private float[] mTraceX = new float[32];
        private float[] mTraceY = new float[32];
        private boolean[] mTraceCurrent = new boolean[32];
        private MotionEvent.PointerCoords mCoords = new MotionEvent.PointerCoords();

        public void clearTrace() {
            this.mTraceCount = 0;
        }

        public void addTrace(float x, float y, boolean current) {
            float[] fArr = this.mTraceX;
            int traceCapacity = fArr.length;
            int i = this.mTraceCount;
            if (i == traceCapacity) {
                int traceCapacity2 = traceCapacity * 2;
                float[] newTraceX = new float[traceCapacity2];
                System.arraycopy(fArr, 0, newTraceX, 0, i);
                this.mTraceX = newTraceX;
                float[] newTraceY = new float[traceCapacity2];
                System.arraycopy(this.mTraceY, 0, newTraceY, 0, this.mTraceCount);
                this.mTraceY = newTraceY;
                boolean[] newTraceCurrent = new boolean[traceCapacity2];
                System.arraycopy(this.mTraceCurrent, 0, newTraceCurrent, 0, this.mTraceCount);
                this.mTraceCurrent = newTraceCurrent;
            }
            float[] newTraceY2 = this.mTraceX;
            int i2 = this.mTraceCount;
            newTraceY2[i2] = x;
            this.mTraceY[i2] = y;
            this.mTraceCurrent[i2] = current;
            this.mTraceCount = i2 + 1;
        }
    }

    private static final class FasterStringBuilder {
        private char[] mChars = new char[64];
        private int mLength;

        public FasterStringBuilder clear() {
            this.mLength = 0;
            return this;
        }

        public FasterStringBuilder append(String value) {
            int valueLength = value.length();
            int index = reserve(valueLength);
            value.getChars(0, valueLength, this.mChars, index);
            this.mLength += valueLength;
            return this;
        }

        public FasterStringBuilder append(int value) {
            return append(value, 0);
        }

        public FasterStringBuilder append(int value, int zeroPadWidth) {
            boolean negative = value < 0;
            if (negative && (value = -value) < 0) {
                append("-2147483648");
                return this;
            }
            int index = reserve(11);
            char[] chars = this.mChars;
            if (value == 0) {
                int i = index + 1;
                chars[index] = '0';
                this.mLength++;
                return this;
            }
            if (negative) {
                chars[index] = '-';
                index++;
            }
            int divisor = 1000000000;
            int numberWidth = 10;
            while (value < divisor) {
                divisor /= 10;
                numberWidth--;
                if (numberWidth < zeroPadWidth) {
                    chars[index] = '0';
                    index++;
                }
            }
            while (true) {
                int digit = value / divisor;
                value -= digit * divisor;
                divisor /= 10;
                int index2 = index + 1;
                chars[index] = (char) (digit + 48);
                if (divisor != 0) {
                    index = index2;
                } else {
                    this.mLength = index2;
                    return this;
                }
            }
        }

        public FasterStringBuilder append(float value, int precision) {
            int scale = 1;
            for (int i = 0; i < precision; i++) {
                scale *= 10;
            }
            float value2 = (float) (Math.rint(scale * value) / scale);
            if (((int) value2) == 0 && value2 < 0.0f) {
                append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            }
            append((int) value2);
            if (precision != 0) {
                append(MediaMetrics.SEPARATOR);
                float value3 = Math.abs(value2);
                append((int) (scale * ((float) (value3 - Math.floor(value3)))), precision);
            }
            return this;
        }

        public String toString() {
            return new String(this.mChars, 0, this.mLength);
        }

        private int reserve(int length) {
            int oldLength = this.mLength;
            int newLength = this.mLength + length;
            char[] oldChars = this.mChars;
            int oldCapacity = oldChars.length;
            if (newLength > oldCapacity) {
                int newCapacity = oldCapacity * 2;
                char[] newChars = new char[newCapacity];
                System.arraycopy(oldChars, 0, newChars, 0, oldLength);
                this.mChars = newChars;
            }
            return oldLength;
        }
    }
}
