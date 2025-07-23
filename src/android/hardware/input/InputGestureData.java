package android.hardware.input;

import android.app.PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0;
import android.hardware.input.AidlInputGestureData;
import android.hardware.input.AppLaunchData;
import android.view.KeyEvent;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class InputGestureData {
    public static final int TOUCHPAD_GESTURE_TYPE_THREE_FINGER_TAP = 1;
    public static final int TOUCHPAD_GESTURE_TYPE_UNKNOWN = 0;
    private final AidlInputGestureData mInputGestureData;

    public interface Trigger {
        AidlInputGestureData.Trigger getAidlTrigger();
    }

    public InputGestureData(AidlInputGestureData aidlInputGestureData) {
        this.mInputGestureData = aidlInputGestureData;
        validate();
    }

    public Trigger getTrigger() {
        return createTriggerFromAidlTrigger(this.mInputGestureData.trigger);
    }

    public Action getAction() {
        return new Action(this.mInputGestureData.gestureType, getAppLaunchData());
    }

    private void validate() {
        Trigger trigger = getTrigger();
        Action action = getAction();
        if (trigger == null) {
            throw new IllegalArgumentException("No trigger found");
        }
        if (action.keyGestureType == 0) {
            throw new IllegalArgumentException("No system action found");
        }
        if (action.keyGestureType == 51 && action.appLaunchData == null) {
            throw new IllegalArgumentException("No app launch data for system action launch application");
        }
    }

    public AidlInputGestureData getAidlData() {
        return this.mInputGestureData;
    }

    private AppLaunchData getAppLaunchData() {
        if (this.mInputGestureData.gestureType != 51) {
            return null;
        }
        return AppLaunchData.createLaunchData(this.mInputGestureData.appLaunchCategory, this.mInputGestureData.appLaunchRole, this.mInputGestureData.appLaunchPackageName, this.mInputGestureData.appLaunchClassName);
    }

    public static class Builder {
        private Trigger mTrigger = null;
        private int mKeyGestureType = 0;
        private AppLaunchData mAppLaunchData = null;

        public Builder setTrigger(Trigger trigger) {
            this.mTrigger = trigger;
            return this;
        }

        public Builder setKeyGestureType(int i) {
            this.mKeyGestureType = i;
            return this;
        }

        public Builder setAppLaunchData(AppLaunchData appLaunchData) {
            this.mKeyGestureType = 51;
            this.mAppLaunchData = appLaunchData;
            return this;
        }

        public InputGestureData build() throws IllegalArgumentException {
            if (this.mTrigger == null) {
                throw new IllegalArgumentException("No trigger found");
            }
            int i = this.mKeyGestureType;
            if (i == 0) {
                throw new IllegalArgumentException("No system action found");
            }
            if (i == 51 && this.mAppLaunchData == null) {
                throw new IllegalArgumentException("No app launch data for system action launch application");
            }
            AidlInputGestureData aidlInputGestureData = new AidlInputGestureData();
            aidlInputGestureData.trigger = this.mTrigger.getAidlTrigger();
            aidlInputGestureData.gestureType = this.mKeyGestureType;
            AppLaunchData appLaunchData = this.mAppLaunchData;
            if (appLaunchData != null) {
                if (appLaunchData instanceof AppLaunchData.CategoryData) {
                    aidlInputGestureData.appLaunchCategory = ((AppLaunchData.CategoryData) appLaunchData).getCategory();
                } else if (appLaunchData instanceof AppLaunchData.RoleData) {
                    aidlInputGestureData.appLaunchRole = ((AppLaunchData.RoleData) appLaunchData).getRole();
                } else if (appLaunchData instanceof AppLaunchData.ComponentData) {
                    AppLaunchData.ComponentData componentData = (AppLaunchData.ComponentData) appLaunchData;
                    aidlInputGestureData.appLaunchPackageName = componentData.getPackageName();
                    aidlInputGestureData.appLaunchClassName = componentData.getClassName();
                } else {
                    throw new IllegalArgumentException("AppLaunchData type is invalid!");
                }
            }
            return new InputGestureData(aidlInputGestureData);
        }
    }

    public String toString() {
        return "InputGestureData { trigger = " + getTrigger() + ", action = " + getAction() + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mInputGestureData, ((InputGestureData) obj).mInputGestureData);
    }

    public int hashCode() {
        return this.mInputGestureData.hashCode();
    }

    public static Trigger createKeyTrigger(int i, int i2) {
        return new KeyTrigger(i, i2);
    }

    public static Trigger createTouchpadTrigger(int i) {
        return new TouchpadTrigger(i);
    }

    public static Trigger createTriggerFromAidlTrigger(AidlInputGestureData.Trigger trigger) {
        int tag = trigger.getTag();
        if (tag == 0) {
            AidlInputGestureData.KeyTrigger key = trigger.getKey();
            if (key == null) {
                throw new RuntimeException("aidlTrigger is corrupted, null key trigger!");
            }
            return new KeyTrigger(key);
        }
        if (tag == 1) {
            AidlInputGestureData.TouchpadGestureTrigger touchpadGesture = trigger.getTouchpadGesture();
            if (touchpadGesture == null) {
                throw new RuntimeException("aidlTrigger is corrupted, null touchpad trigger!");
            }
            return new TouchpadTrigger(touchpadGesture);
        }
        throw new RuntimeException("aidlTrigger is corrupted, invalid trigger type!");
    }

    public static class KeyTrigger implements Trigger {
        AidlInputGestureData.KeyTrigger mAidlKeyTrigger;

        private KeyTrigger(AidlInputGestureData.KeyTrigger keyTrigger) {
            this.mAidlKeyTrigger = keyTrigger;
        }

        private KeyTrigger(int i, int i2) {
            if ((i <= 0 || i > KeyEvent.getMaxKeyCode()) && (i < 1000 || i > 1104)) {
                throw new IllegalArgumentException("Invalid keycode = " + i);
            }
            AidlInputGestureData.KeyTrigger keyTrigger = new AidlInputGestureData.KeyTrigger();
            this.mAidlKeyTrigger = keyTrigger;
            keyTrigger.keycode = i;
            this.mAidlKeyTrigger.modifierState = i2;
        }

        public int getKeycode() {
            return this.mAidlKeyTrigger.keycode;
        }

        public int getModifierState() {
            return this.mAidlKeyTrigger.modifierState;
        }

        @Override // android.hardware.input.InputGestureData.Trigger
        public AidlInputGestureData.Trigger getAidlTrigger() {
            AidlInputGestureData.Trigger trigger = new AidlInputGestureData.Trigger();
            trigger.setKey(this.mAidlKeyTrigger);
            return trigger;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof KeyTrigger) {
                return Objects.equals(this.mAidlKeyTrigger, ((KeyTrigger) obj).mAidlKeyTrigger);
            }
            return false;
        }

        public int hashCode() {
            return this.mAidlKeyTrigger.hashCode();
        }

        public String toString() {
            return "KeyTrigger{mKeycode=" + KeyEvent.keyCodeToString(this.mAidlKeyTrigger.keycode) + ", mModifierState=" + this.mAidlKeyTrigger.modifierState + '}';
        }
    }

    public static class TouchpadTrigger implements Trigger {
        AidlInputGestureData.TouchpadGestureTrigger mAidlTouchpadTrigger;

        private TouchpadTrigger(AidlInputGestureData.TouchpadGestureTrigger touchpadGestureTrigger) {
            this.mAidlTouchpadTrigger = touchpadGestureTrigger;
        }

        private TouchpadTrigger(int i) {
            if (i != 1) {
                throw new IllegalArgumentException("Invalid touchpadGestureType = " + i);
            }
            AidlInputGestureData.TouchpadGestureTrigger touchpadGestureTrigger = new AidlInputGestureData.TouchpadGestureTrigger();
            this.mAidlTouchpadTrigger = touchpadGestureTrigger;
            touchpadGestureTrigger.gestureType = i;
        }

        public int getTouchpadGestureType() {
            return this.mAidlTouchpadTrigger.gestureType;
        }

        @Override // android.hardware.input.InputGestureData.Trigger
        public AidlInputGestureData.Trigger getAidlTrigger() {
            AidlInputGestureData.Trigger trigger = new AidlInputGestureData.Trigger();
            trigger.setTouchpadGesture(this.mAidlTouchpadTrigger);
            return trigger;
        }

        public String toString() {
            return "TouchpadTrigger{mTouchpadGestureType=" + this.mAidlTouchpadTrigger.gestureType + '}';
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof TouchpadTrigger) {
                return Objects.equals(this.mAidlTouchpadTrigger, ((TouchpadTrigger) obj).mAidlTouchpadTrigger);
            }
            return false;
        }

        public int hashCode() {
            return this.mAidlTouchpadTrigger.hashCode();
        }
    }

    public static final class Action extends Record {
        private final AppLaunchData appLaunchData;
        private final int keyGestureType;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof Action)) {
                return false;
            }
            Action action = (Action) obj;
            return this.keyGestureType == action.keyGestureType && Objects.equals(this.appLaunchData, action.appLaunchData);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{Integer.valueOf(this.keyGestureType), this.appLaunchData};
        }

        public Action(int keyGestureType, AppLaunchData appLaunchData) {
            this.keyGestureType = keyGestureType;
            this.appLaunchData = appLaunchData;
        }

        public AppLaunchData appLaunchData() {
            return this.appLaunchData;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.keyGestureType, this.appLaunchData);
        }

        public int keyGestureType() {
            return this.keyGestureType;
        }

        @Override // java.lang.Record
        public final String toString() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m($record$getFieldsAsObjects(), Action.class, "keyGestureType;appLaunchData");
        }
    }

    public enum Filter {
        KEY(0),
        TOUCHPAD(1);

        private final int mTag;

        Filter(int i) {
            this.mTag = i;
        }

        public static Filter of(int i) {
            if (i == 0) {
                return KEY;
            }
            if (i != 1) {
                return null;
            }
            return TOUCHPAD;
        }

        public int getTag() {
            return this.mTag;
        }

        public boolean matches(InputGestureData inputGestureData) {
            return this.mTag == inputGestureData.mInputGestureData.trigger.getTag();
        }
    }
}
