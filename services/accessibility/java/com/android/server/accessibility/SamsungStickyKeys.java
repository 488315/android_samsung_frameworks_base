package com.android.server.accessibility;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Message;
import android.p005os.IInstalld;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.A11yRune;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.samsung.android.view.SemWindowManager;

/* loaded from: classes.dex */
public class SamsungStickyKeys extends BaseEventStreamTransformation {
    public Context mContext;
    public final Handler mHandler;
    public EventStreamTransformation mNext;
    public final BroadcastReceiver mReceiver;
    public View mStickyKeysView;
    public final Thread mUiThread;
    public WindowManager mWindowManager;
    public final TextView[] mModifierKeys = new TextView[4];
    public final KeyEvent[] mModifierKeyUpEvent = new KeyEvent[4];
    public final int[] mModifierKeyUpPolicyFlags = new int[4];
    public SemWindowManager.FoldStateListener mFoldStateListener = null;
    public int mModifierKeyMask = 0;

    public final boolean isModifierKey(int i) {
        if (i == 113 || i == 114 || i == 117 || i == 118) {
            return true;
        }
        switch (i) {
            case 57:
            case 58:
            case 59:
            case 60:
                return true;
            default:
                return false;
        }
    }

    public SamsungStickyKeys(Context context, int i) {
        Handler handler = new Handler() { // from class: com.android.server.accessibility.SamsungStickyKeys.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i2;
                switch (message.what) {
                    case 99:
                        SamsungStickyKeys.this.makeViews();
                        i2 = 0;
                        break;
                    case 100:
                        SamsungStickyKeys.this.mModifierKeyMask &= -2;
                        i2 = 2;
                        break;
                    case 101:
                        SamsungStickyKeys.this.mModifierKeyMask &= -257;
                        i2 = 1;
                        break;
                    case 102:
                        SamsungStickyKeys.this.mModifierKeyMask &= -17;
                        i2 = 0;
                        break;
                    case 103:
                        SamsungStickyKeys.this.mModifierKeyMask &= -4097;
                        i2 = 3;
                        break;
                    default:
                        i2 = 0;
                        break;
                }
                if (SamsungStickyKeys.this.mModifierKeyUpEvent[i2] == null || SamsungStickyKeys.this.mNext == null) {
                    return;
                }
                SamsungStickyKeys.this.debugLog("send KEY_UP : " + SamsungStickyKeys.this.mModifierKeyUpEvent[i2].getKeyCode() + ", index : " + i2);
                SamsungStickyKeys.this.mNext.onKeyEvent(SamsungStickyKeys.this.mModifierKeyUpEvent[i2], SamsungStickyKeys.this.mModifierKeyUpPolicyFlags[i2]);
                SamsungStickyKeys.this.mModifierKeyUpEvent[i2] = null;
                SamsungStickyKeys.this.mModifierKeyUpPolicyFlags[i2] = 0;
            }
        };
        this.mHandler = handler;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.accessibility.SamsungStickyKeys.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Log.d("SamsungStickyKeys", "onReceive() : " + intent.getAction());
                if (SamsungStickyKeys.this.isHardwareKeyboardAvailable()) {
                    return;
                }
                SamsungStickyKeys.this.mModifierKeyMask = 0;
                for (int i2 = 0; i2 < 4; i2++) {
                    SamsungStickyKeys.this.setModifierKeyStatus(i2, 0);
                }
            }
        };
        this.mReceiver = broadcastReceiver;
        this.mUiThread = Thread.currentThread();
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        handler.sendEmptyMessage(99);
        this.mContext.registerReceiver(broadcastReceiver, new IntentFilter("android.intent.action.CONFIGURATION_CHANGED"));
        if (A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP) {
            registerFoldStateChangeListener();
        }
    }

    public static KeyEvent createBackKeyEvent(KeyEvent keyEvent) {
        return new KeyEvent(keyEvent.getDownTime(), keyEvent.getEventTime(), keyEvent.getAction(), 4, keyEvent.getRepeatCount(), 0, keyEvent.getDeviceId(), keyEvent.getScanCode(), keyEvent.getFlags(), keyEvent.getSource());
    }

    public static boolean isRTL(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        return configuration != null && (configuration.screenLayout & 192) == 128;
    }

    public final void makeViews() {
        if (A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP) {
            DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
            Context createWindowContext = getSubDisplayContext(this.mContext, displayManager).createWindowContext(displayManager.getDisplay(getDisplayId()), 2009, null);
            this.mContext = createWindowContext;
            this.mWindowManager = (WindowManager) createWindowContext.getSystemService("window");
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.type = 2009;
        layoutParams.flags = 1800;
        layoutParams.format = -3;
        if (isRTL(this.mContext)) {
            layoutParams.gravity = 51;
        } else {
            layoutParams.gravity = 53;
        }
        layoutParams.x = 0;
        layoutParams.y = (int) TypedValue.applyDimension(1, 32.0f, this.mContext.getResources().getDisplayMetrics());
        layoutParams.samsungFlags |= IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
        layoutParams.layoutInDisplayCutoutMode = 1;
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.notification_material_action, (ViewGroup) null);
        this.mStickyKeysView = inflate;
        inflate.bringToFront();
        this.mStickyKeysView.invalidate();
        this.mModifierKeys[0] = (TextView) this.mStickyKeysView.findViewById(R.id.verification_icon);
        this.mModifierKeys[1] = (TextView) this.mStickyKeysView.findViewById(R.id.datetime);
        this.mModifierKeys[2] = (TextView) this.mStickyKeysView.findViewById(R.id.always);
        this.mModifierKeys[3] = (TextView) this.mStickyKeysView.findViewById(R.id.notification_main_column);
        this.mWindowManager.addView(this.mStickyKeysView, layoutParams);
    }

    public final void setModifierKeyStatus(final int i, final int i2) {
        runOnUiThread(new Runnable() { // from class: com.android.server.accessibility.SamsungStickyKeys$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SamsungStickyKeys.this.lambda$setModifierKeyStatus$0(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setModifierKeyStatus$0(int i, int i2) {
        debugLog("setModifierKeyStatus(), index : " + i + ", status : " + i2);
        TextView textView = this.mModifierKeys[i];
        if (i2 == 0) {
            textView.setVisibility(8);
            return;
        }
        if (i2 == 1) {
            textView.setBackgroundResource(17304307);
            textView.setTextColor(this.mContext.getResources().getColor(17171497));
            textView.setVisibility(0);
        } else {
            if (i2 != 2) {
                return;
            }
            textView.setBackgroundResource(17304306);
            textView.setTextColor(this.mContext.getResources().getColor(17171495));
            textView.setVisibility(0);
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onKeyEvent(KeyEvent keyEvent, int i) {
        int action = keyEvent.getAction();
        int keyCode = keyEvent.getKeyCode();
        if (isModifierKey(keyCode)) {
            if (action == 0) {
                proccessModifierKeyPress(keyCode);
                if (getModifierKeyStatus(keyCode) != 1) {
                    debugLog("ignore KeyEvent.ACTION_DOWN : " + keyCode);
                    return;
                }
            } else if (getModifierKeyStatus(keyCode) != 0) {
                debugLog("ignore KeyEvent.ACTION_UP : " + keyCode);
                saveModifierKeyUpEvent(keyCode, keyEvent, i);
                return;
            }
            EventStreamTransformation eventStreamTransformation = this.mNext;
            if (eventStreamTransformation != null) {
                eventStreamTransformation.onKeyEvent(keyEvent, i);
                return;
            }
            return;
        }
        if (this.mModifierKeyMask > 0) {
            keyEvent = injectModifierKeys(keyEvent);
        }
        EventStreamTransformation eventStreamTransformation2 = this.mNext;
        if (eventStreamTransformation2 != null) {
            eventStreamTransformation2.onKeyEvent(keyEvent, i);
        }
        debugLog(keyEvent.getKeyCode() + " is pressed, alt : " + keyEvent.isAltPressed() + ", shift : " + keyEvent.isShiftPressed() + ", ctrl : " + keyEvent.isCtrlPressed() + ", meta : " + keyEvent.isMetaPressed());
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        EventStreamTransformation eventStreamTransformation = this.mNext;
        if (eventStreamTransformation != null) {
            eventStreamTransformation.onMotionEvent(motionEvent, motionEvent2, i);
        }
    }

    public final void saveModifierKeyUpEvent(int i, KeyEvent keyEvent, int i2) {
        char c;
        if (i != 113 && i != 114) {
            if (i != 117 && i != 118) {
                c = 0;
                switch (i) {
                    case 57:
                    case 58:
                        c = 2;
                        break;
                }
            } else {
                c = 3;
            }
        } else {
            c = 1;
        }
        this.mModifierKeyUpEvent[c] = keyEvent.copy();
        this.mModifierKeyUpPolicyFlags[c] = i2;
    }

    public final int getModifierKeyStatus(int i) {
        if (i == 113 || i == 114) {
            int i2 = this.mModifierKeyMask;
            if ((i2 & 256) != 0) {
                return 1;
            }
            return (i2 & 512) != 0 ? 2 : 0;
        }
        if (i != 117 && i != 118) {
            switch (i) {
                case 57:
                case 58:
                    int i3 = this.mModifierKeyMask;
                    if ((i3 & 1) != 0) {
                        return 1;
                    }
                    return (i3 & 2) != 0 ? 2 : 0;
                case 59:
                case 60:
                    int i4 = this.mModifierKeyMask;
                    if ((i4 & 16) != 0) {
                        return 1;
                    }
                    return (i4 & 32) != 0 ? 2 : 0;
                default:
                    return 0;
            }
        }
        int i5 = this.mModifierKeyMask;
        if ((i5 & IInstalld.FLAG_USE_QUOTA) != 0) {
            return 1;
        }
        return (i5 & IInstalld.FLAG_FORCE) != 0 ? 2 : 0;
    }

    public final KeyEvent injectModifierKeys(KeyEvent keyEvent) {
        if ((this.mModifierKeyMask & 3) != 0) {
            keyEvent.semSetAltPressed(true);
            if ((this.mModifierKeyMask & 1) != 0) {
                debugLog("remove STICKY_ALT_ON : " + keyEvent.getKeyCode());
                setModifierKeyStatus(2, 0);
                this.mHandler.sendEmptyMessageDelayed(100, 500L);
            }
        }
        if ((this.mModifierKeyMask & 48) != 0) {
            keyEvent.semSetShiftPressed(true);
            if ((this.mModifierKeyMask & 16) != 0) {
                debugLog("remove STICKY_SHIFT_ON : " + keyEvent.getKeyCode());
                setModifierKeyStatus(0, 0);
                this.mHandler.sendEmptyMessageDelayed(102, 500L);
            }
        }
        if ((this.mModifierKeyMask & FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_USAGE) != 0) {
            keyEvent.semSetCtrlPressed(true);
            if ((this.mModifierKeyMask & 256) != 0) {
                debugLog("remove STICKY_CTRL_ON : " + keyEvent.getKeyCode());
                setModifierKeyStatus(1, 0);
                this.mHandler.sendEmptyMessageDelayed(101, 500L);
            }
        }
        if ((this.mModifierKeyMask & 12288) != 0) {
            keyEvent.semSetMetaPressed(true);
            if (keyEvent.getKeyCode() == 67) {
                debugLog("modify keycode to KEYCODE_BACK");
                keyEvent = createBackKeyEvent(keyEvent);
            }
            if ((this.mModifierKeyMask & IInstalld.FLAG_USE_QUOTA) != 0) {
                debugLog("remove STICKY_META_ON : " + keyEvent.getKeyCode());
                setModifierKeyStatus(3, 0);
                this.mHandler.sendEmptyMessageDelayed(103, 500L);
            }
        }
        return keyEvent;
    }

    public final void proccessModifierKeyPress(int i) {
        if (i != 113 && i != 114) {
            if (i != 117 && i != 118) {
                switch (i) {
                    case 57:
                    case 58:
                        int i2 = this.mModifierKeyMask;
                        if ((i2 & 2) == 0) {
                            if ((i2 & 1) != 0) {
                                debugLog("remove STICKY_ALT_ON and set STICKY_ALT_LOCKED");
                                this.mModifierKeyMask = (this.mModifierKeyMask & (-2)) | 2;
                                setModifierKeyStatus(2, 2);
                                break;
                            } else {
                                debugLog("set STICKY_ALT_ON");
                                this.mModifierKeyMask |= 1;
                                setModifierKeyStatus(2, 1);
                                break;
                            }
                        } else {
                            debugLog("remove STICKY_ALT_LOCKED");
                            this.mModifierKeyMask &= -3;
                            setModifierKeyStatus(2, 0);
                            break;
                        }
                    case 59:
                    case 60:
                        int i3 = this.mModifierKeyMask;
                        if ((i3 & 32) == 0) {
                            if ((i3 & 16) != 0) {
                                debugLog("remove STICKY_SHIFT_ON and set STICKY_SHIFT_LOCKED");
                                this.mModifierKeyMask = (this.mModifierKeyMask & (-17)) | 32;
                                setModifierKeyStatus(0, 2);
                                break;
                            } else {
                                debugLog("set STICKY_SHIFT_ON");
                                this.mModifierKeyMask |= 16;
                                setModifierKeyStatus(0, 1);
                                break;
                            }
                        } else {
                            debugLog("remove STICKY_SHIFT_LOCKED");
                            this.mModifierKeyMask &= -33;
                            setModifierKeyStatus(0, 0);
                            break;
                        }
                }
            } else {
                int i4 = this.mModifierKeyMask;
                if ((i4 & IInstalld.FLAG_FORCE) != 0) {
                    debugLog("remove STICKY_META_LOCKED");
                    this.mModifierKeyMask &= -8193;
                    setModifierKeyStatus(3, 0);
                } else if ((i4 & IInstalld.FLAG_USE_QUOTA) != 0) {
                    debugLog("remove STICKY_META_ON and set STICKY_META_LOCKED");
                    this.mModifierKeyMask = (this.mModifierKeyMask & (-4097)) | IInstalld.FLAG_FORCE;
                    setModifierKeyStatus(3, 2);
                } else {
                    debugLog("set STICKY_META_ON");
                    this.mModifierKeyMask |= IInstalld.FLAG_USE_QUOTA;
                    setModifierKeyStatus(3, 1);
                }
            }
        } else {
            int i5 = this.mModifierKeyMask;
            if ((i5 & 512) != 0) {
                debugLog("remove STICKY_CTRL_LOCKED");
                this.mModifierKeyMask &= -513;
                setModifierKeyStatus(1, 0);
            } else if ((i5 & 256) != 0) {
                debugLog("remove STICKY_CTRL_ON and set STICKY_CTRL_LOCKED");
                this.mModifierKeyMask = (this.mModifierKeyMask & (-257)) | 512;
                setModifierKeyStatus(1, 2);
            } else {
                debugLog("set STICKY_CTRL_ON");
                this.mModifierKeyMask |= 256;
                setModifierKeyStatus(1, 1);
            }
        }
        debugLog("mModifierKeyMask : " + Integer.toBinaryString(this.mModifierKeyMask));
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        EventStreamTransformation eventStreamTransformation = this.mNext;
        if (eventStreamTransformation != null) {
            eventStreamTransformation.onAccessibilityEvent(accessibilityEvent);
        }
    }

    @Override // com.android.server.accessibility.BaseEventStreamTransformation, com.android.server.accessibility.EventStreamTransformation
    public void setNext(EventStreamTransformation eventStreamTransformation) {
        super.setNext(eventStreamTransformation);
        this.mNext = eventStreamTransformation;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void clearEvents(int i) {
        EventStreamTransformation eventStreamTransformation = this.mNext;
        if (eventStreamTransformation != null) {
            eventStreamTransformation.clearEvents(i);
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onDestroy() {
        View view = this.mStickyKeysView;
        if (view != null) {
            this.mWindowManager.removeView(view);
            this.mStickyKeysView = null;
        }
        try {
            this.mContext.unregisterReceiver(this.mReceiver);
        } catch (IllegalArgumentException unused) {
            Log.e("SamsungStickyKeys", "IllegalArgumentException occurred in Sticky keys");
        }
        if (A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP) {
            unRegisterFoldStateChangeListener();
        }
    }

    public final boolean isHardwareKeyboardAvailable() {
        boolean z = this.mContext.getResources().getConfiguration().keyboard != 1;
        Log.d("SamsungStickyKeys", "isHardwareKeyboardAvailable() : " + z);
        return z;
    }

    public final void debugLog(String str) {
        Log.d("SamsungStickyKeys", str);
    }

    public final void runOnUiThread(Runnable runnable) {
        if (Thread.currentThread() != this.mUiThread) {
            this.mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    public final void registerFoldStateChangeListener() {
        if (this.mFoldStateListener == null) {
            this.mFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.android.server.accessibility.SamsungStickyKeys.3
                public void onTableModeChanged(boolean z) {
                }

                public void onFoldStateChanged(boolean z) {
                    if (SamsungStickyKeys.this.mStickyKeysView != null) {
                        SamsungStickyKeys.this.mWindowManager.removeView(SamsungStickyKeys.this.mStickyKeysView);
                        SamsungStickyKeys.this.mStickyKeysView = null;
                    }
                    SamsungStickyKeys.this.mHandler.sendEmptyMessage(99);
                }
            };
            SemWindowManager.getInstance().registerFoldStateListener(this.mFoldStateListener, (Handler) null);
        }
    }

    public final void unRegisterFoldStateChangeListener() {
        if (this.mFoldStateListener != null) {
            SemWindowManager.getInstance().unregisterFoldStateListener(this.mFoldStateListener);
            this.mFoldStateListener = null;
        }
    }

    public final Context getSubDisplayContext(Context context, DisplayManager displayManager) {
        return context.createDisplayContext(displayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN")[1]);
    }

    public final int getDisplayId() {
        return AccessibilityUtils.isFoldedLargeCoverScreen() ? 1 : 0;
    }
}
