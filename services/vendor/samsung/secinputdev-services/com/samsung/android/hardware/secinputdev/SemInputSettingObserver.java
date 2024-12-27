package com.samsung.android.hardware.secinputdev;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class SemInputSettingObserver {
    private static final int HANDLER_GLOBAL_START = 200;
    private static final int HANDLER_SECURE_START = 100;
    private static final String PALM_MUTE_FRAME_NUMBER = "palm_mute_frame_number";
    private static final String PALM_MUTE_THRESHOLD = "palm_mute_threshold";
    private static final String PREMIUM_TAP_FOR_WATCH_FACE_SWITCH_ON_OFF =
            "premium_tap_for_watch_face_switch_on_off";
    public static final String STRING_VALUE_KEY = "data";
    private static final String TAG = "SemInputSettingObserver";
    private static final String VIRTUAL_FORCE_EANBLE = "virtual_force_enable";
    private static volatile SemInputSettingObserver uniqueInstance;
    private final Context context;
    private final ArrayList<ContentObserver> observers = new ArrayList<>();
    private SemInputSettingObserverSetup availableMap = new SemInputSettingObserverSetup();

    public interface HandlerMessage {
        public static final int AUTO_WATER_DETECTION = 81;
        public static final int DOUBLE_TAP_TO_WAKE_UP = 6;
        public static final int GLOVE_MODE = 5;
        public static final int LONG_PRESS_TIMEOUT = 102;
        public static final int MOTION_MUTE = 1;
        public static final int PALM_MUTE_FRAME_NUMBER = 11;
        public static final int PALM_MUTE_THRESHOLD = 10;
        public static final int PALM_SWIPE = 3;
        public static final int PREMIUM_TAP_FOR_WATCH_FACE = 4;
        public static final int REFRESH_RATE_MODE = 101;
        public static final int SCREEN_OFF_MEMO = 8;
        public static final int SEM_SETTING_WRIST_DETECTION_DISABLED = 203;
        public static final int SETTING_BEDTIME_MODE_RUNNING_STATE = 201;
        public static final int SETTING_TOUCH_BEZEL_ON = 202;
        public static final int SETTING_TOUCH_SENSITIVITY_ON = 80;
        public static final int SPEN_SAVING_MODE = 7;
        public static final int TAP_DURATION_ENABLED = 103;
        public static final int VIRTUAL_FORCE = 2;
    }

    private enum ValueType {
        INTEGER,
        STRING
    }

    private SemInputSettingObserver(Context context) {
        this.context = context;
        this.availableMap.printSettings();
    }

    public static SemInputSettingObserver getInstance(Context context) {
        if (uniqueInstance == null) {
            synchronized (SemInputSettingObserver.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new SemInputSettingObserver(context);
                }
            }
        }
        return uniqueInstance;
    }

    public void registerDefault(Handler handler) {
        if (SemInputFeatures.IS_WEAROS) {
            addObserver(handler, 80, 0);
            addObserver(handler, HandlerMessage.SETTING_BEDTIME_MODE_RUNNING_STATE, 0);
            addObserver(handler, HandlerMessage.SETTING_TOUCH_BEZEL_ON, 0);
            if (SemInputFeatures.SUPPORT_AWD) {
                addObserver(handler, HandlerMessage.SEM_SETTING_WRIST_DETECTION_DISABLED, 0);
            }
        } else {
            addObserver(handler, HandlerMessage.REFRESH_RATE_MODE, 0);
            addObserver(handler, 5, 0);
        }
        if (SemInputFeatures.IS_PREMIUM_WATCH) {
            addObserver(handler, 4, 1);
        }
    }

    public void addObserver(Handler handler, int handlerMessageWhat, String defaultValue) {
        addObserver(handler, handlerMessageWhat, ValueType.STRING, defaultValue);
    }

    public void addObserver(Handler handler, int handlerMessageWhat, int defaultValue) {
        addObserver(handler, handlerMessageWhat, ValueType.INTEGER, Integer.valueOf(defaultValue));
    }

    private void addObserver(
            Handler handler, int handlerMessageWhat, ValueType valueType, Object defaultValue) {
        ContentObserver observer;
        String key = this.availableMap.get(handlerMessageWhat);
        if (key == null) {
            Log.e(
                    TAG,
                    "addObserver: Handler message #"
                            + handlerMessageWhat
                            + "'s SETTING_KEY is not defined\nDefined SETTING_KEY");
            this.availableMap.printSettings();
            Log.e(
                    TAG,
                    "addObserver: Please add SETTING_KEY to SemInputSettingObserver's constructor");
            return;
        }
        Log.i(TAG, "addObserver: " + key + "(#" + handlerMessageWhat + ")");
        try {
            switch (valueType) {
                case INTEGER:
                    observer =
                            new SettingObserver(
                                    handler,
                                    handlerMessageWhat,
                                    key,
                                    ((Integer) defaultValue).intValue());
                    break;
                case STRING:
                    observer =
                            new SettingObserver(
                                    handler, handlerMessageWhat, key, (String) defaultValue);
                    break;
                default:
                    return;
            }
            if (handlerMessageWhat > HANDLER_GLOBAL_START) {
                this.context
                        .getContentResolver()
                        .registerContentObserver(Settings.Global.getUriFor(key), false, observer);
            } else if (handlerMessageWhat > 100) {
                this.context
                        .getContentResolver()
                        .registerContentObserver(
                                Settings.Secure.getUriFor(key), false, observer, -1);
            } else {
                this.context
                        .getContentResolver()
                        .registerContentObserver(
                                Settings.System.getUriFor(key), false, observer, -1);
            }
            observer.onChange(false);
            this.observers.add(observer);
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "addObserver", e);
        }
    }

    public void updateAll() {
        Log.i(TAG, "updateAll");
        Iterator<ContentObserver> it = this.observers.iterator();
        while (it.hasNext()) {
            ContentObserver observer = it.next();
            observer.onChange(false);
        }
    }

    private class SettingObserver extends ContentObserver {
        private int defaultValueInt;
        private final Handler handler;
        private final String key;
        private final int messageWhat;
        private final ValueType valueType;

        public SettingObserver(Handler handler, int what, String key, int defaultValue) {
            super(handler);
            this.defaultValueInt = 0;
            this.handler = handler;
            this.messageWhat = what;
            this.key = key;
            this.defaultValueInt = defaultValue;
            this.valueType = ValueType.INTEGER;
        }

        public SettingObserver(Handler handler, int what, String key, String defaultValue) {
            super(handler);
            this.defaultValueInt = 0;
            this.handler = handler;
            this.messageWhat = what;
            this.key = key;
            this.valueType = ValueType.STRING;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            switch (this.valueType) {
                case INTEGER:
                    updateInt();
                    break;
                case STRING:
                    updateString();
                    break;
                default:
                    Log.e(
                            SemInputSettingObserver.TAG,
                            "valueType is not defined: " + this.valueType);
                    break;
            }
        }

        public void updateInt() {
            Message message = this.handler.obtainMessage(this.messageWhat);
            if (this.messageWhat > SemInputSettingObserver.HANDLER_GLOBAL_START) {
                message.arg1 =
                        Settings.Global.getInt(
                                SemInputSettingObserver.this.context.getContentResolver(),
                                this.key,
                                this.defaultValueInt);
            } else if (this.messageWhat > 100) {
                message.arg1 =
                        Settings.Secure.getIntForUser(
                                SemInputSettingObserver.this.context.getContentResolver(),
                                this.key,
                                this.defaultValueInt,
                                -2);
            } else {
                message.arg1 =
                        Settings.System.getIntForUser(
                                SemInputSettingObserver.this.context.getContentResolver(),
                                this.key,
                                this.defaultValueInt,
                                -2);
            }
            this.handler.sendMessage(message);
        }

        public void updateString() {
            Message message = this.handler.obtainMessage(this.messageWhat);
            Bundle data = new Bundle();
            if (this.messageWhat > SemInputSettingObserver.HANDLER_GLOBAL_START) {
                data.putString(
                        SemInputSettingObserver.STRING_VALUE_KEY,
                        Settings.Global.getString(
                                SemInputSettingObserver.this.context.getContentResolver(),
                                this.key));
            } else if (this.messageWhat > 100) {
                data.putString(
                        SemInputSettingObserver.STRING_VALUE_KEY,
                        Settings.Secure.getString(
                                SemInputSettingObserver.this.context.getContentResolver(),
                                this.key));
            } else {
                data.putString(
                        SemInputSettingObserver.STRING_VALUE_KEY,
                        Settings.System.getString(
                                SemInputSettingObserver.this.context.getContentResolver(),
                                this.key));
            }
            message.setData(data);
            this.handler.sendMessage(message);
        }
    }
}
