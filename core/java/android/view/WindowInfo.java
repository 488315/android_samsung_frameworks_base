package android.view;

import android.graphics.Matrix;
import android.graphics.Region;
import android.os.IBinder;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pools;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

public class WindowInfo implements Parcelable {
    private static final int MAX_POOL_SIZE = 10;
    public IBinder activityToken;
    public List<IBinder> childTokens;
    public boolean focused;
    public boolean hasFlagWatchOutsideTouch;
    public boolean inPictureInPicture;
    public int layer;
    public IBinder parentToken;
    public CharSequence title;
    public IBinder token;
    public int type;
    private static final Pools.SynchronizedPool<WindowInfo> sPool =
            new Pools.SynchronizedPool<>(10);
    public static final Parcelable.Creator<WindowInfo> CREATOR =
            new Parcelable.Creator<WindowInfo>() { // from class: android.view.WindowInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public WindowInfo createFromParcel(Parcel parcel) {
                    WindowInfo window = WindowInfo.obtain();
                    window.initFromParcel(parcel);
                    return window;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public WindowInfo[] newArray(int size) {
                    return new WindowInfo[size];
                }
            };
    public Region regionInScreen = new Region();
    public long accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
    public int displayId = -1;
    public int taskId = -1;
    public float[] mTransformMatrix = new float[9];
    public MagnificationSpec mMagnificationSpec = new MagnificationSpec();
    public LocaleList locales = LocaleList.getEmptyLocaleList();

    private WindowInfo() {}

    public static WindowInfo obtain() {
        WindowInfo window = sPool.acquire();
        if (window == null) {
            return new WindowInfo();
        }
        return window;
    }

    public static WindowInfo obtain(WindowInfo other) {
        WindowInfo window = obtain();
        window.displayId = other.displayId;
        window.taskId = other.taskId;
        window.type = other.type;
        window.layer = other.layer;
        window.token = other.token;
        window.parentToken = other.parentToken;
        window.activityToken = other.activityToken;
        window.focused = other.focused;
        window.regionInScreen.set(other.regionInScreen);
        window.title = other.title;
        window.accessibilityIdOfAnchor = other.accessibilityIdOfAnchor;
        window.inPictureInPicture = other.inPictureInPicture;
        window.hasFlagWatchOutsideTouch = other.hasFlagWatchOutsideTouch;
        for (int i = 0; i < window.mTransformMatrix.length; i++) {
            window.mTransformMatrix[i] = other.mTransformMatrix[i];
        }
        if (other.childTokens != null && !other.childTokens.isEmpty()) {
            if (window.childTokens == null) {
                window.childTokens = new ArrayList(other.childTokens);
            } else {
                window.childTokens.addAll(other.childTokens);
            }
        }
        window.mMagnificationSpec.setTo(other.mMagnificationSpec);
        window.locales = other.locales;
        return window;
    }

    public void recycle() {
        clear();
        sPool.release(this);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.displayId);
        parcel.writeInt(this.taskId);
        parcel.writeInt(this.type);
        parcel.writeInt(this.layer);
        parcel.writeStrongBinder(this.token);
        parcel.writeStrongBinder(this.parentToken);
        parcel.writeStrongBinder(this.activityToken);
        parcel.writeInt(this.focused ? 1 : 0);
        this.regionInScreen.writeToParcel(parcel, i);
        parcel.writeCharSequence(this.title);
        parcel.writeLong(this.accessibilityIdOfAnchor);
        parcel.writeInt(this.inPictureInPicture ? 1 : 0);
        parcel.writeInt(this.hasFlagWatchOutsideTouch ? 1 : 0);
        parcel.writeFloatArray(this.mTransformMatrix);
        if (this.childTokens != null && !this.childTokens.isEmpty()) {
            parcel.writeInt(1);
            parcel.writeBinderList(this.childTokens);
        } else {
            parcel.writeInt(0);
        }
        this.mMagnificationSpec.writeToParcel(parcel, i);
        parcel.writeParcelable(this.locales, i);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("WindowInfo[");
        builder.append("title=").append(this.title);
        builder.append(", displayId=").append(this.displayId);
        builder.append(", taskId=").append(this.taskId);
        builder.append(", type=").append(this.type);
        builder.append(", layer=").append(this.layer);
        builder.append(", token=").append(this.token);
        builder.append(", region=").append(this.regionInScreen);
        builder.append(", bounds=").append(this.regionInScreen.getBounds());
        builder.append(", parent=").append(this.parentToken);
        builder.append(", focused=").append(this.focused);
        builder.append(", children=").append(this.childTokens);
        builder.append(", accessibility anchor=").append(this.accessibilityIdOfAnchor);
        builder.append(", pictureInPicture=").append(this.inPictureInPicture);
        builder.append(", watchOutsideTouch=").append(this.hasFlagWatchOutsideTouch);
        Matrix matrix = new Matrix();
        matrix.setValues(this.mTransformMatrix);
        builder.append(", mTransformMatrix=").append(matrix);
        builder.append(", mMagnificationSpec=").append(this.mMagnificationSpec);
        builder.append(", locales=").append(this.locales);
        builder.append(']');
        return builder.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFromParcel(Parcel parcel) {
        this.displayId = parcel.readInt();
        this.taskId = parcel.readInt();
        this.type = parcel.readInt();
        this.layer = parcel.readInt();
        this.token = parcel.readStrongBinder();
        this.parentToken = parcel.readStrongBinder();
        this.activityToken = parcel.readStrongBinder();
        this.focused = parcel.readInt() == 1;
        this.regionInScreen = Region.CREATOR.createFromParcel(parcel);
        this.title = parcel.readCharSequence();
        this.accessibilityIdOfAnchor = parcel.readLong();
        this.inPictureInPicture = parcel.readInt() == 1;
        this.hasFlagWatchOutsideTouch = parcel.readInt() == 1;
        parcel.readFloatArray(this.mTransformMatrix);
        boolean hasChildren = parcel.readInt() == 1;
        if (hasChildren) {
            if (this.childTokens == null) {
                this.childTokens = new ArrayList();
            }
            parcel.readBinderList(this.childTokens);
        }
        this.mMagnificationSpec = MagnificationSpec.CREATOR.createFromParcel(parcel);
        this.locales = (LocaleList) parcel.readParcelable(null, LocaleList.class);
    }

    private void clear() {
        this.displayId = -1;
        this.taskId = -1;
        this.type = 0;
        this.layer = 0;
        this.token = null;
        this.parentToken = null;
        this.activityToken = null;
        this.focused = false;
        this.regionInScreen.setEmpty();
        if (this.childTokens != null) {
            this.childTokens.clear();
        }
        this.inPictureInPicture = false;
        this.hasFlagWatchOutsideTouch = false;
        for (int i = 0; i < this.mTransformMatrix.length; i++) {
            this.mTransformMatrix[i] = 0.0f;
        }
        this.mMagnificationSpec.clear();
        this.title = null;
        this.accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
        this.locales = LocaleList.getEmptyLocaleList();
    }
}
