package android.accessibilityservice;

import android.content.pm.ParceledListSlice;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.MotionEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public final class AccessibilityGestureEvent implements Parcelable {
    public static final Parcelable.Creator<AccessibilityGestureEvent> CREATOR = new Parcelable.Creator<AccessibilityGestureEvent>() { // from class: android.accessibilityservice.AccessibilityGestureEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessibilityGestureEvent createFromParcel(Parcel parcel) {
            return new AccessibilityGestureEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessibilityGestureEvent[] newArray(int i) {
            return new AccessibilityGestureEvent[i];
        }
    };
    private final int mDisplayId;
    private final int mGestureId;
    private List<MotionEvent> mMotionEvents;

    @Retention(RetentionPolicy.SOURCE)
    public @interface GestureId {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AccessibilityGestureEvent(int i, int i2, List<MotionEvent> list) {
        ArrayList arrayList = new ArrayList();
        this.mMotionEvents = arrayList;
        this.mGestureId = i;
        this.mDisplayId = i2;
        arrayList.addAll(list);
    }

    public AccessibilityGestureEvent(int i, int i2) {
        this(i, i2, new ArrayList());
    }

    private AccessibilityGestureEvent(Parcel parcel) {
        this.mMotionEvents = new ArrayList();
        this.mGestureId = parcel.readInt();
        this.mDisplayId = parcel.readInt();
        this.mMotionEvents = ((ParceledListSlice) parcel.readParcelable(getClass().getClassLoader(), ParceledListSlice.class)).getList();
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public int getGestureId() {
        return this.mGestureId;
    }

    public List<MotionEvent> getMotionEvents() {
        return this.mMotionEvents;
    }

    public AccessibilityGestureEvent copyForAsync() {
        return new AccessibilityGestureEvent(this.mGestureId, this.mDisplayId, this.mMotionEvents.stream().map(new Function() { // from class: android.accessibilityservice.AccessibilityGestureEvent$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MotionEvent) obj).copy();
            }
        }).toList());
    }

    public void recycle() {
        this.mMotionEvents.forEach(new Consumer() { // from class: android.accessibilityservice.AccessibilityGestureEvent$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((MotionEvent) obj).recycle();
            }
        });
        this.mMotionEvents.clear();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AccessibilityGestureEvent[gestureId: ");
        sb.append(gestureIdToString(this.mGestureId));
        sb.append(", displayId: ");
        sb.append(this.mDisplayId);
        sb.append(", Motion Events: [");
        for (int i = 0; i < this.mMotionEvents.size(); i++) {
            sb.append(MotionEvent.actionToString(this.mMotionEvents.get(i).getActionMasked()));
            if (i < this.mMotionEvents.size() - 1) {
                sb.append(", ");
            } else {
                sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            }
        }
        sb.append(']');
        return sb.toString();
    }

    public static String gestureIdToString(int i) {
        switch (i) {
            case -2:
                return "GESTURE_TOUCH_EXPLORATION";
            case -1:
                return "GESTURE_PASSTHROUGH";
            case 0:
                return "GESTURE_UNKNOWN";
            case 1:
                return "GESTURE_SWIPE_UP";
            case 2:
                return "GESTURE_SWIPE_DOWN";
            case 3:
                return "GESTURE_SWIPE_LEFT";
            case 4:
                return "GESTURE_SWIPE_RIGHT";
            case 5:
                return "GESTURE_SWIPE_LEFT_AND_RIGHT";
            case 6:
                return "GESTURE_SWIPE_RIGHT_AND_LEFT";
            case 7:
                return "GESTURE_SWIPE_UP_AND_DOWN";
            case 8:
                return "GESTURE_SWIPE_DOWN_AND_UP";
            case 9:
                return "GESTURE_SWIPE_LEFT_AND_UP";
            case 10:
                return "GESTURE_SWIPE_LEFT_AND_DOWN";
            case 11:
                return "GESTURE_SWIPE_RIGHT_AND_UP";
            case 12:
                return "GESTURE_SWIPE_RIGHT_AND_DOWN";
            case 13:
                return "GESTURE_SWIPE_UP_AND_LEFT";
            case 14:
                return "GESTURE_SWIPE_UP_AND_RIGHT";
            case 15:
                return "GESTURE_SWIPE_DOWN_AND_LEFT";
            case 16:
                return "GESTURE_SWIPE_DOWN_AND_RIGHT";
            case 17:
                return "GESTURE_DOUBLE_TAP";
            case 18:
                return "GESTURE_DOUBLE_TAP_AND_HOLD";
            case 19:
                return "GESTURE_2_FINGER_SINGLE_TAP";
            case 20:
                return "GESTURE_2_FINGER_DOUBLE_TAP";
            case 21:
                return "GESTURE_2_FINGER_TRIPLE_TAP";
            case 22:
                return "GESTURE_3_FINGER_SINGLE_TAP";
            case 23:
                return "GESTURE_3_FINGER_DOUBLE_TAP";
            case 24:
                return "GESTURE_3_FINGER_TRIPLE_TAP";
            case 25:
                return "GESTURE_2_FINGER_SWIPE_UP";
            case 26:
                return "GESTURE_2_FINGER_SWIPE_DOWN";
            case 27:
                return "GESTURE_2_FINGER_SWIPE_LEFT";
            case 28:
                return "GESTURE_2_FINGER_SWIPE_RIGHT";
            case 29:
                return "GESTURE_3_FINGER_SWIPE_UP";
            case 30:
                return "GESTURE_3_FINGER_SWIPE_DOWN";
            case 31:
                return "GESTURE_3_FINGER_SWIPE_LEFT";
            case 32:
                return "GESTURE_3_FINGER_SWIPE_RIGHT";
            case 33:
                return "GESTURE_4_FINGER_SWIPE_UP";
            case 34:
                return "GESTURE_4_FINGER_SWIPE_DOWN";
            case 35:
                return "GESTURE_4_FINGER_SWIPE_LEFT";
            case 36:
                return "GESTURE_4_FINGER_SWIPE_RIGHT";
            case 37:
                return "GESTURE_4_FINGER_SINGLE_TAP";
            case 38:
                return "GESTURE_4_FINGER_DOUBLE_TAP";
            case 39:
                return "GESTURE_4_FINGER_TRIPLE_TAP";
            case 40:
                return "GESTURE_2_FINGER_DOUBLE_TAP_AND_HOLD";
            case 41:
                return "GESTURE_3_FINGER_DOUBLE_TAP_AND_HOLD";
            case 42:
                return "GESTURE_4_FINGER_DOUBLE_TAP_AND_HOLD";
            case 43:
                return "GESTURE_2_FINGER_TRIPLE_TAP_AND_HOLD";
            case 44:
                return "GESTURE_3_FINGER_SINGLE_TAP_AND_HOLD";
            case 45:
                return "GESTURE_3_FINGER_TRIPLE_TAP_AND_HOLD";
            default:
                return Integer.toHexString(i);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mGestureId);
        parcel.writeInt(this.mDisplayId);
        parcel.writeParcelable(new ParceledListSlice(this.mMotionEvents), 0);
    }
}
