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

/* loaded from: classes4.dex */
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
    private static final Pools.SynchronizedPool<WindowInfo> sPool = new Pools.SynchronizedPool<>(10);
    public static final Parcelable.Creator<WindowInfo> CREATOR = new Parcelable.Creator<WindowInfo>() { // from class: android.view.WindowInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowInfo createFromParcel(Parcel parcel) {
            WindowInfo windowInfoObtain = WindowInfo.obtain();
            windowInfoObtain.initFromParcel(parcel);
            return windowInfoObtain;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowInfo[] newArray(int i) {
            return new WindowInfo[i];
        }
    };
    public Region regionInScreen = new Region();
    public long accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
    public int displayId = -1;
    public int taskId = -1;
    public float[] mTransformMatrix = new float[9];
    public MagnificationSpec mMagnificationSpec = new MagnificationSpec();
    public LocaleList locales = LocaleList.getEmptyLocaleList();

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private WindowInfo() {
    }

    public static WindowInfo obtain() {
        WindowInfo windowInfoAcquire = sPool.acquire();
        return windowInfoAcquire == null ? new WindowInfo() : windowInfoAcquire;
    }

    public static WindowInfo obtain(WindowInfo windowInfo) {
        WindowInfo windowInfoObtain = obtain();
        windowInfoObtain.displayId = windowInfo.displayId;
        windowInfoObtain.taskId = windowInfo.taskId;
        windowInfoObtain.type = windowInfo.type;
        windowInfoObtain.layer = windowInfo.layer;
        windowInfoObtain.token = windowInfo.token;
        windowInfoObtain.parentToken = windowInfo.parentToken;
        windowInfoObtain.activityToken = windowInfo.activityToken;
        windowInfoObtain.focused = windowInfo.focused;
        windowInfoObtain.regionInScreen.set(windowInfo.regionInScreen);
        windowInfoObtain.title = windowInfo.title;
        windowInfoObtain.accessibilityIdOfAnchor = windowInfo.accessibilityIdOfAnchor;
        windowInfoObtain.inPictureInPicture = windowInfo.inPictureInPicture;
        windowInfoObtain.hasFlagWatchOutsideTouch = windowInfo.hasFlagWatchOutsideTouch;
        int i = 0;
        while (true) {
            float[] fArr = windowInfoObtain.mTransformMatrix;
            if (i >= fArr.length) {
                break;
            }
            fArr[i] = windowInfo.mTransformMatrix[i];
            i++;
        }
        List<IBinder> list = windowInfo.childTokens;
        if (list != null && !list.isEmpty()) {
            List<IBinder> list2 = windowInfoObtain.childTokens;
            if (list2 == null) {
                windowInfoObtain.childTokens = new ArrayList(windowInfo.childTokens);
            } else {
                list2.addAll(windowInfo.childTokens);
            }
        }
        windowInfoObtain.mMagnificationSpec.setTo(windowInfo.mMagnificationSpec);
        windowInfoObtain.locales = windowInfo.locales;
        return windowInfoObtain;
    }

    public void recycle() {
        clear();
        sPool.release(this);
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
        List<IBinder> list = this.childTokens;
        if (list != null && !list.isEmpty()) {
            parcel.writeInt(1);
            parcel.writeBinderList(this.childTokens);
        } else {
            parcel.writeInt(0);
        }
        this.mMagnificationSpec.writeToParcel(parcel, i);
        parcel.writeParcelable(this.locales, i);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("WindowInfo[title=");
        sb.append(this.title);
        sb.append(", displayId=");
        sb.append(this.displayId);
        sb.append(", taskId=");
        sb.append(this.taskId);
        sb.append(", type=");
        sb.append(this.type);
        sb.append(", layer=");
        sb.append(this.layer);
        sb.append(", token=");
        sb.append(this.token);
        sb.append(", region=");
        sb.append(this.regionInScreen);
        sb.append(", bounds=");
        sb.append(this.regionInScreen.getBounds());
        sb.append(", parent=");
        sb.append(this.parentToken);
        sb.append(", focused=");
        sb.append(this.focused);
        sb.append(", children=");
        sb.append(this.childTokens);
        sb.append(", accessibility anchor=");
        sb.append(this.accessibilityIdOfAnchor);
        sb.append(", pictureInPicture=");
        sb.append(this.inPictureInPicture);
        sb.append(", watchOutsideTouch=");
        sb.append(this.hasFlagWatchOutsideTouch);
        Matrix matrix = new Matrix();
        matrix.setValues(this.mTransformMatrix);
        sb.append(", mTransformMatrix=");
        sb.append(matrix);
        sb.append(", mMagnificationSpec=");
        sb.append(this.mMagnificationSpec);
        sb.append(", locales=");
        sb.append(this.locales);
        sb.append(']');
        return sb.toString();
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
        if (parcel.readInt() == 1) {
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
        int i = 0;
        this.type = 0;
        this.layer = 0;
        this.token = null;
        this.parentToken = null;
        this.activityToken = null;
        this.focused = false;
        this.regionInScreen.setEmpty();
        List<IBinder> list = this.childTokens;
        if (list != null) {
            list.clear();
        }
        this.inPictureInPicture = false;
        this.hasFlagWatchOutsideTouch = false;
        while (true) {
            float[] fArr = this.mTransformMatrix;
            if (i < fArr.length) {
                fArr[i] = 0.0f;
                i++;
            } else {
                this.mMagnificationSpec.clear();
                this.title = null;
                this.accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
                this.locales = LocaleList.getEmptyLocaleList();
                return;
            }
        }
    }
}
