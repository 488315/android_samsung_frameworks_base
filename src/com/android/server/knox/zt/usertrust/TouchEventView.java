package com.android.server.knox.zt.usertrust;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.hardware.input.InputManager;
import android.media.MediaMetrics;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
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
    private AuthFactorTouchManager mAuthFactorTouchManager;
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

    public TouchEventView(Context context, AuthFactorTouchManager authFactorTouchManager) {
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
        this.mIm = (InputManager) context.getSystemService(InputManager.class);
        this.mContext = context;
        this.mAuthFactorTouchManager = authFactorTouchManager;
        arrayList.add(new PointerState());
        this.mActivePointerId = 0;
        this.mVelocity = VelocityTracker.obtain();
    }

    public void setDebugmode(boolean z) {
        this.mDebugmodeOn = z;
    }

    @Override // android.view.WindowManagerPolicyConstants.PointerEventListener
    public void onPointerEvent(MotionEvent motionEvent) {
        this.mAuthFactorTouchManager.onPointerEvent(motionEvent);
        if (this.mDebugmodeOn) {
            this.mRawX = motionEvent.getRawX();
            this.mRawY = motionEvent.getRawY();
            this.mTouchMinor = motionEvent.getTouchMinor();
            this.mTouchMajor = motionEvent.getTouchMajor();
            this.mDownTime = motionEvent.getDownTime();
            this.mUpTime = motionEvent.getEventTime();
            KeyboardInfo();
            int offset = setOffset();
            float f = this.mRawX + this.x_offset;
            this.mRawX = f;
            float f2 = this.mRawY + this.y_offset;
            this.mRawY = f2;
            this.isTyping = isUserTypingKeyboard(f, f2, offset);
            Log.i(TAG, "mTouchMajor: " + this.mTouchMajor + ", mTouchMinor: " + this.mTouchMinor + ", mDownTime: " + this.mDownTime + ", mUpTime: " + this.mUpTime);
            StringBuilder sb = new StringBuilder("isTyping: ");
            sb.append(this.isTyping);
            Log.i(TAG, sb.toString());
            int action = motionEvent.getAction();
            int size = this.mPointers.size();
            if (action == 0 || (action & 255) == 5) {
                int i = (action & 65280) >> 8;
                if (action == 0) {
                    for (int i2 = 0; i2 < size; i2++) {
                        PointerState pointerState = this.mPointers.get(i2);
                        pointerState.clearTrace();
                        pointerState.mCurDown = false;
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
                int i3 = this.mCurNumPointers + 1;
                this.mCurNumPointers = i3;
                if (this.mMaxNumPointers < i3) {
                    this.mMaxNumPointers = i3;
                }
                int pointerId = motionEvent.getPointerId(i);
                while (size <= pointerId) {
                    this.mPointers.add(new PointerState());
                    size++;
                }
                int i4 = this.mActivePointerId;
                if (i4 < 0 || !this.mPointers.get(i4).mCurDown) {
                    this.mActivePointerId = pointerId;
                }
                PointerState pointerState2 = this.mPointers.get(pointerId);
                pointerState2.mCurDown = true;
                InputDevice device = InputDevice.getDevice(motionEvent.getDeviceId());
                pointerState2.mHasBoundingBox = (device == null || device.getMotionRange(32) == null) ? false : true;
            }
            int pointerCount = motionEvent.getPointerCount();
            this.mVelocity.addMovement(motionEvent);
            this.mVelocity.computeCurrentVelocity(1);
            VelocityTracker velocityTracker2 = this.mAltVelocity;
            if (velocityTracker2 != null) {
                velocityTracker2.addMovement(motionEvent);
                this.mAltVelocity.computeCurrentVelocity(1);
            }
            int historySize = motionEvent.getHistorySize();
            for (int i5 = 0; i5 < historySize; i5++) {
                for (int i6 = 0; i6 < pointerCount; i6++) {
                    PointerState pointerState3 = this.mCurDown ? this.mPointers.get(motionEvent.getPointerId(i6)) : null;
                    MotionEvent.PointerCoords pointerCoords = pointerState3 != null ? pointerState3.mCoords : this.mTempCoords;
                    motionEvent.getHistoricalPointerCoords(i6, i5, pointerCoords);
                    if (pointerState3 != null) {
                        pointerState3.addTrace(pointerCoords.x, pointerCoords.y, false);
                    }
                }
            }
            for (int i7 = 0; i7 < pointerCount; i7++) {
                int pointerId2 = motionEvent.getPointerId(i7);
                PointerState pointerState4 = this.mCurDown ? this.mPointers.get(pointerId2) : null;
                MotionEvent.PointerCoords pointerCoords2 = pointerState4 != null ? pointerState4.mCoords : this.mTempCoords;
                motionEvent.getPointerCoords(i7, pointerCoords2);
                if (pointerState4 != null) {
                    pointerState4.addTrace(pointerCoords2.x, pointerCoords2.y, true);
                    pointerState4.mXVelocity = this.mVelocity.getXVelocity(pointerId2);
                    pointerState4.mYVelocity = this.mVelocity.getYVelocity(pointerId2);
                    Log.i(TAG, "mXVelocity: " + pointerState4.mXVelocity + ", mYVelocity: " + pointerState4.mYVelocity);
                    VelocityTracker velocityTracker3 = this.mAltVelocity;
                    if (velocityTracker3 != null) {
                        pointerState4.mAltXVelocity = velocityTracker3.getXVelocity(pointerId2);
                        pointerState4.mAltYVelocity = this.mAltVelocity.getYVelocity(pointerId2);
                        Log.i(TAG, "mAltXVelocity: " + pointerState4.mAltXVelocity + ", mAltYVelocity: " + pointerState4.mAltYVelocity);
                    }
                    pointerState4.mToolType = motionEvent.getToolType(i7);
                    Log.i(TAG, "ToolType: " + pointerState4.mToolType);
                    if (pointerState4.mHasBoundingBox) {
                        pointerState4.mBoundingLeft = motionEvent.getAxisValue(32, i7);
                        pointerState4.mBoundingTop = motionEvent.getAxisValue(33, i7);
                        pointerState4.mBoundingRight = motionEvent.getAxisValue(34, i7);
                        pointerState4.mBoundingBottom = motionEvent.getAxisValue(35, i7);
                        Log.i(TAG, "mBoundingLeft: " + pointerState4.mBoundingLeft + ", mBoundingRight: " + pointerState4.mBoundingRight + ", mBoundingTop: " + pointerState4.mBoundingTop + ", mBoundingBottom: " + pointerState4.mBoundingBottom);
                    }
                }
            }
            if (action == 1 || action == 3 || (action & 255) == 6) {
                int i8 = (action & 65280) >> 8;
                int pointerId3 = motionEvent.getPointerId(i8);
                PointerState pointerState5 = this.mPointers.get(pointerId3);
                pointerState5.mCurDown = false;
                if (action == 1 || action == 3) {
                    this.mCurDown = false;
                    this.mCurNumPointers = 0;
                } else {
                    this.mCurNumPointers--;
                    if (this.mActivePointerId == pointerId3) {
                        this.mActivePointerId = motionEvent.getPointerId(i8 != 0 ? 0 : 1);
                    }
                    pointerState5.addTrace(Float.NaN, Float.NaN, false);
                }
            }
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceAdded(int i) {
        logInputDeviceState(i, "Device Added");
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceChanged(int i) {
        logInputDeviceState(i, "Device Changed");
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceRemoved(int i) {
        logInputDeviceState(i, "Device Removed");
    }

    private void logInputDeviceState(int i, String str) {
        InputManager inputManager = this.mIm;
        if (inputManager == null) {
            return;
        }
        InputDevice inputDevice = inputManager.getInputDevice(i);
        if (inputDevice != null) {
            Log.i(TAG, str + ": " + inputDevice);
            return;
        }
        Log.i(TAG, str + ": " + i);
    }

    private void KeyboardInfo() {
        Cursor cursorQuery;
        String[] strArr = {FLOATING_KEYBOARD_INFO};
        AutoCloseable autoCloseable = null;
        try {
            String string = Settings.Secure.getString(this.mContext.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);
            if (SKBD_DEFAULT_PACKAGE_NAME.equals(string)) {
                cursorQuery = this.mContext.getContentResolver().query(Uri.parse(KEYBOARD_SETTINGS_PROVIDER), null, null, strArr, null);
            } else if (SKBDN_DEFAULT_PACKAGE_NAME.equals(string)) {
                cursorQuery = this.mContext.getContentResolver().query(Uri.parse(KEYBOARD_SETTINGS_PROVIDER_BETA), null, null, strArr, null);
            } else if ("com.samsung.android.honeyboard/.service.HoneyBoardService".equals(string)) {
                cursorQuery = this.mContext.getContentResolver().query(Uri.parse(HONEY_BOARD_PROVIDER), null, null, strArr, null);
            } else {
                Log.d(TAG, "unkown keyboard");
                return;
            }
            if (cursorQuery != null) {
                cursorQuery.moveToFirst();
                int i = cursorQuery.getInt(cursorQuery.getColumnIndex(FLOATING_KEYBOARD_ON));
                int i2 = cursorQuery.getInt(cursorQuery.getColumnIndex(FLOATING_KEYBOARD_LOCATION_X));
                int i3 = cursorQuery.getInt(cursorQuery.getColumnIndex(FLOATING_KEYBOARD_LOCATION_Y));
                int i4 = cursorQuery.getInt(cursorQuery.getColumnIndex(FLOATING_KEYBOARD_LOCATION_LAND_X));
                int i5 = cursorQuery.getInt(cursorQuery.getColumnIndex(FLOATING_KEYBOARD_LOCATION_LAND_Y));
                int i6 = cursorQuery.getInt(cursorQuery.getColumnIndex(FLOATING_KEYBOARD_WIDTH));
                int i7 = cursorQuery.getInt(cursorQuery.getColumnIndex(FLOATING_KEYBOARD_HEIGHT));
                Log.i(TAG, "isFloatingKeyboardOn: " + i + ", x: " + i2 + ", y: " + i3 + ", landX: " + i4 + ", landY: " + i5);
                this.keyboard_x = i6;
                this.keyboard_y = i7;
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        } catch (Exception unused) {
            if (0 != 0) {
                autoCloseable.close();
            }
        } catch (Throwable th) {
            if (0 != 0) {
                autoCloseable.close();
                throw th;
            }
            throw th;
        }
    }

    private boolean isKeyboardShown() {
        return ((InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).semIsInputMethodShown();
    }

    private int setOffset() {
        int rotation = ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        if (rotation == 0) {
            this.x_offset = 0.0f;
            this.y_offset = -1524.0f;
            return rotation;
        }
        if (rotation == 1) {
            this.x_offset = -301.0f;
            this.y_offset = -600.0f;
            return rotation;
        }
        if (rotation == 3) {
            this.x_offset = -353.0f;
            this.y_offset = -600.0f;
        }
        return rotation;
    }

    private boolean isUserTypingKeyboard(float f, float f2, int i) {
        if (!isKeyboardShown()) {
            return false;
        }
        if (i == 2 || i == 0) {
            if (f >= 0.0f && this.keyboard_x >= f && f2 >= 0.0f && this.keyboard_y >= f2) {
                return true;
            }
        } else if ((i == 1 || i == 3) && f >= 0.0f && this.keyboard_x >= f && f2 >= 0.0f && this.keyboard_y >= f2) {
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

        public void addTrace(float f, float f2, boolean z) {
            float[] fArr = this.mTraceX;
            int length = fArr.length;
            int i = this.mTraceCount;
            if (i == length) {
                int i2 = length * 2;
                float[] fArr2 = new float[i2];
                System.arraycopy(fArr, 0, fArr2, 0, i);
                this.mTraceX = fArr2;
                float[] fArr3 = new float[i2];
                System.arraycopy(this.mTraceY, 0, fArr3, 0, this.mTraceCount);
                this.mTraceY = fArr3;
                boolean[] zArr = new boolean[i2];
                System.arraycopy(this.mTraceCurrent, 0, zArr, 0, this.mTraceCount);
                this.mTraceCurrent = zArr;
            }
            float[] fArr4 = this.mTraceX;
            int i3 = this.mTraceCount;
            fArr4[i3] = f;
            this.mTraceY[i3] = f2;
            this.mTraceCurrent[i3] = z;
            this.mTraceCount = i3 + 1;
        }
    }

    private static final class FasterStringBuilder {
        private char[] mChars = new char[64];
        private int mLength;

        public FasterStringBuilder clear() {
            this.mLength = 0;
            return this;
        }

        public FasterStringBuilder append(String str) {
            int length = str.length();
            str.getChars(0, length, this.mChars, reserve(length));
            this.mLength += length;
            return this;
        }

        public FasterStringBuilder append(int i) {
            return append(i, 0);
        }

        public FasterStringBuilder append(int i, int i2) {
            boolean z = i < 0;
            if (z && (i = -i) < 0) {
                append("-2147483648");
                return this;
            }
            int iReserve = reserve(11);
            char[] cArr = this.mChars;
            if (i == 0) {
                cArr[iReserve] = '0';
                this.mLength++;
                return this;
            }
            if (z) {
                cArr[iReserve] = '-';
                iReserve++;
            }
            int i3 = 1000000000;
            int i4 = 10;
            while (i < i3) {
                i3 /= 10;
                i4--;
                if (i4 < i2) {
                    cArr[iReserve] = '0';
                    iReserve++;
                }
            }
            while (true) {
                int i5 = i / i3;
                i -= i5 * i3;
                i3 /= 10;
                int i6 = iReserve + 1;
                cArr[iReserve] = (char) (i5 + 48);
                if (i3 == 0) {
                    this.mLength = i6;
                    return this;
                }
                iReserve = i6;
            }
        }

        public FasterStringBuilder append(float f, int i) {
            int i2 = 1;
            for (int i3 = 0; i3 < i; i3++) {
                i2 *= 10;
            }
            float f2 = i2;
            float fRint = (float) (Math.rint(f * f2) / i2);
            int i4 = (int) fRint;
            if (i4 == 0 && fRint < 0.0f) {
                append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            }
            append(i4);
            if (i != 0) {
                append(MediaMetrics.SEPARATOR);
                double dAbs = Math.abs(fRint);
                append((int) (((float) (dAbs - Math.floor(dAbs))) * f2), i);
            }
            return this;
        }

        public String toString() {
            return new String(this.mChars, 0, this.mLength);
        }

        private int reserve(int i) {
            int i2 = this.mLength;
            int i3 = i + i2;
            char[] cArr = this.mChars;
            int length = cArr.length;
            if (i3 > length) {
                char[] cArr2 = new char[length * 2];
                System.arraycopy(cArr, 0, cArr2, 0, i2);
                this.mChars = cArr2;
            }
            return i2;
        }
    }
}
