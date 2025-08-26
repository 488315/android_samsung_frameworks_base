package com.samsung.android.multicontrol;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.MotionEvent;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class MCInputEventReceiver extends InputEventReceiver {
    private final String ACTION_TRIGGERED;
    private final int DEFAULT_TRIGGER_THRESHOLD;
    private final String EXTRA_TRIGGER_DIRECTION;
    private final String EXTRA_TRIGGER_DISPLAY_ID;
    private final String EXTRA_TRIGGER_X;
    private final String EXTRA_TRIGGER_Y;
    private final String RECEIVER_PERMISSION;
    private final String TAG;
    public final String TAG_PREFIX;
    private float countX;
    private float countY;
    private ArrayList<Direction> directionList;
    private boolean firstInput;
    Context mContext;
    private int mDisplayId;
    InputMonitor mInputMonitor;
    private WindowManager mWindowManager;
    private int triggerThreshold;

    private enum Direction {
        RIGHT,
        LEFT,
        TOP,
        BOTTOM
    }

    public MCInputEventReceiver(Context context, int i, InputMonitor inputMonitor, InputChannel inputChannel, Looper looper) {
        super(inputChannel, looper);
        this.TAG_PREFIX = SemMultiControlManager.TAG_PREFIX;
        this.TAG = "MultiControl@MCInputEventReceiver";
        this.ACTION_TRIGGERED = "com.samsung.android.inputshare.action.ACTION_TRIGGERED";
        this.EXTRA_TRIGGER_X = "x";
        this.EXTRA_TRIGGER_Y = "y";
        this.EXTRA_TRIGGER_DISPLAY_ID = "displayId";
        this.EXTRA_TRIGGER_DIRECTION = "direction";
        this.RECEIVER_PERMISSION = Manifest.permission.MULTI_CONTROL_RECEIVER_PERMISSION;
        this.DEFAULT_TRIGGER_THRESHOLD = 150;
        this.triggerThreshold = 150;
        this.mDisplayId = 0;
        this.directionList = new ArrayList<>();
        this.countX = 0.0f;
        this.countY = 0.0f;
        this.firstInput = true;
        this.mContext = context;
        this.mInputMonitor = inputMonitor;
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.directionList.add(Direction.RIGHT);
        this.directionList.add(Direction.LEFT);
        this.directionList.add(Direction.TOP);
        this.directionList.add(Direction.BOTTOM);
        this.mDisplayId = i;
    }

    @Override // android.view.InputEventReceiver
    public void onInputEvent(InputEvent inputEvent) {
        try {
            if (this.firstInput) {
                Rect bounds = this.mWindowManager.getMaximumWindowMetrics().getBounds();
                Log.i(this.TAG, "[onInputEvent] displayRect : " + bounds);
                this.firstInput = false;
            }
            if (MotionEvent.class.isInstance(inputEvent)) {
                handleInput((MotionEvent) inputEvent);
            }
        } catch (Exception unused) {
        }
        finishInputEvent(inputEvent, true);
    }

    public void setTriggerThreshold(int i) {
        try {
            this.triggerThreshold = i;
        } catch (Exception e) {
            Log.e(this.TAG, "[setTriggerThreshold]", e);
        }
    }

    private boolean isValidMove(float f, float f2, Direction direction) {
        Rect bounds = this.mWindowManager.getMaximumWindowMetrics().getBounds();
        int iOrdinal = direction.ordinal();
        return iOrdinal != 0 ? iOrdinal != 1 ? iOrdinal != 2 ? iOrdinal == 3 && f2 >= ((float) (bounds.bottom + (-5))) : f2 <= ((float) bounds.top) : f <= ((float) bounds.left) : f >= ((float) (bounds.right + (-5)));
    }

    private boolean isValidTrigger(float f, float f2, Direction direction) {
        int iOrdinal = direction.ordinal();
        if (iOrdinal != 0) {
            if (iOrdinal != 1) {
                if (iOrdinal != 2) {
                    if (iOrdinal == 3 && f2 > 0.0f) {
                        this.countY += 1.0f;
                    }
                } else if (f2 < 0.0f) {
                    this.countY += 1.0f;
                }
            } else if (f < 0.0f) {
                this.countX += 1.0f;
            }
        } else if (f > 0.0f) {
            this.countX += 1.0f;
        }
        float f3 = this.countX;
        int i = this.triggerThreshold;
        return f3 > ((float) i) || this.countY > ((float) i);
    }

    private void handleInput(MotionEvent motionEvent) {
        try {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            float axisValue = motionEvent.getAxisValue(27);
            float axisValue2 = motionEvent.getAxisValue(28);
            Iterator<Direction> it = this.directionList.iterator();
            while (it.hasNext()) {
                Direction next = it.next();
                if (isValidMove(x, y, next)) {
                    if (isValidTrigger(axisValue, axisValue2, next)) {
                        this.countX = 0.0f;
                        this.countY = 0.0f;
                        try {
                            Intent intent = new Intent("com.samsung.android.inputshare.action.ACTION_TRIGGERED");
                            intent.putExtra("displayId", this.mDisplayId);
                            intent.putExtra("direction", next.name());
                            intent.putExtra("y", y);
                            intent.putExtra("x", x);
                            intent.setPackage("com.samsung.android.inputshare");
                            this.mContext.sendBroadcastAsUser(intent, UserHandle.SEM_CURRENT, Manifest.permission.MULTI_CONTROL_RECEIVER_PERMISSION);
                            Log.i(this.TAG, "sendBroadcast - ACTION_TRIGGERED");
                            return;
                        } catch (Exception e) {
                            Log.e(this.TAG, "sendBroadcast - ACTION_TRIGGERED", e);
                            return;
                        }
                    }
                    return;
                }
            }
            this.countX = 0.0f;
            this.countY = 0.0f;
        } catch (Exception e2) {
            Log.e(this.TAG, "[handleInput]", e2);
        }
    }
}
