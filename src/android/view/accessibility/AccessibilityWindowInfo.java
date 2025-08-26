package android.view.accessibility;

import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.LongArray;
import android.util.Pools;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public final class AccessibilityWindowInfo implements Parcelable {
    public static final int ACTIVE_WINDOW_ID = Integer.MAX_VALUE;
    public static final int ANY_WINDOW_ID = -2;
    private static final int BOOLEAN_PROPERTY_ACCESSIBILITY_FOCUSED = 4;
    private static final int BOOLEAN_PROPERTY_ACTIVE = 1;
    private static final int BOOLEAN_PROPERTY_FOCUSED = 2;
    private static final int BOOLEAN_PROPERTY_PICTURE_IN_PICTURE = 8;
    private static final boolean DEBUG = false;
    private static final int MAX_POOL_SIZE = 10;
    public static final int PICTURE_IN_PICTURE_ACTION_REPLACER_WINDOW_ID = -3;
    public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
    public static final int TYPE_APPLICATION = 1;
    public static final int TYPE_INPUT_METHOD = 2;
    public static final int TYPE_MAGNIFICATION_OVERLAY = 6;
    public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
    public static final int TYPE_SYSTEM = 3;
    public static final int TYPE_WINDOW_CONTROL = 7;
    public static final int UNDEFINED_CONNECTION_ID = -1;
    public static final int UNDEFINED_WINDOW_ID = -1;
    private static AtomicInteger sNumInstancesInUse;
    private int mBooleanProperties;
    private LongArray mChildIds;
    private CharSequence mTitle;
    private long mTransitionTime;
    private static final Pools.SynchronizedPool<AccessibilityWindowInfo> sPool = new Pools.SynchronizedPool<>(10);
    public static final Parcelable.Creator<AccessibilityWindowInfo> CREATOR = new Parcelable.Creator<AccessibilityWindowInfo>() { // from class: android.view.accessibility.AccessibilityWindowInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessibilityWindowInfo createFromParcel(Parcel parcel) {
            AccessibilityWindowInfo accessibilityWindowInfoObtain = AccessibilityWindowInfo.obtain();
            accessibilityWindowInfoObtain.initFromParcel(parcel);
            return accessibilityWindowInfoObtain;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessibilityWindowInfo[] newArray(int i) {
            return new AccessibilityWindowInfo[i];
        }
    };
    private int mDisplayId = -1;
    private int mType = -1;
    private int mLayer = -1;
    private int mId = -1;
    private int mParentId = -1;
    private int mTaskId = -1;
    private Region mRegionInScreen = new Region();
    private long mAnchorId = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
    private int mConnectionId = -1;
    private LocaleList mLocales = LocaleList.getEmptyLocaleList();
    private int mRawType = -1;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AccessibilityWindowInfo() {
    }

    public AccessibilityWindowInfo(AccessibilityWindowInfo accessibilityWindowInfo) {
        init(accessibilityWindowInfo);
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
    }

    public int getType() {
        return this.mType;
    }

    public void setType(int i) {
        this.mType = i;
    }

    public int semGetRawType() {
        return this.mRawType;
    }

    public void semSetRawType(int i) {
        this.mRawType = i;
    }

    public int getLayer() {
        return this.mLayer;
    }

    public void setLayer(int i) {
        this.mLayer = i;
    }

    public AccessibilityNodeInfo getRoot() {
        return getRoot(4);
    }

    public AccessibilityNodeInfo getRoot(int i) {
        if (this.mConnectionId == -1) {
            return null;
        }
        return AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mId, AccessibilityNodeInfo.ROOT_NODE_ID, true, i, (Bundle) null);
    }

    public void setAnchorId(long j) {
        this.mAnchorId = j;
    }

    public AccessibilityNodeInfo getAnchor() {
        if (this.mConnectionId == -1 || this.mAnchorId == AccessibilityNodeInfo.UNDEFINED_NODE_ID || this.mParentId == -1) {
            return null;
        }
        return AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mParentId, this.mAnchorId, true, 0, (Bundle) null);
    }

    public void setPictureInPicture(boolean z) {
        setBooleanProperty(8, z);
    }

    public boolean isInPictureInPictureMode() {
        return getBooleanProperty(8);
    }

    public AccessibilityWindowInfo getParent() {
        if (this.mConnectionId == -1 || this.mParentId == -1) {
            return null;
        }
        return AccessibilityInteractionClient.getInstance().getWindow(this.mConnectionId, this.mParentId);
    }

    public void setParentId(int i) {
        this.mParentId = i;
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int i) {
        this.mId = i;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public void setTaskId(int i) {
        this.mTaskId = i;
    }

    public void setConnectionId(int i) {
        this.mConnectionId = i;
    }

    public void getRegionInScreen(Region region) {
        region.set(this.mRegionInScreen);
    }

    public void setRegionInScreen(Region region) {
        this.mRegionInScreen.set(region);
    }

    public void getBoundsInScreen(Rect rect) {
        rect.set(this.mRegionInScreen.getBounds());
    }

    public boolean isActive() {
        return getBooleanProperty(1);
    }

    public void setActive(boolean z) {
        setBooleanProperty(1, z);
    }

    public boolean isFocused() {
        return getBooleanProperty(2);
    }

    public void setFocused(boolean z) {
        setBooleanProperty(2, z);
    }

    public boolean isAccessibilityFocused() {
        return getBooleanProperty(4);
    }

    public void setAccessibilityFocused(boolean z) {
        setBooleanProperty(4, z);
    }

    public int getChildCount() {
        LongArray longArray = this.mChildIds;
        if (longArray != null) {
            return longArray.size();
        }
        return 0;
    }

    public AccessibilityWindowInfo getChild(int i) {
        LongArray longArray = this.mChildIds;
        if (longArray == null) {
            throw new IndexOutOfBoundsException();
        }
        if (this.mConnectionId == -1) {
            return null;
        }
        return AccessibilityInteractionClient.getInstance().getWindow(this.mConnectionId, (int) longArray.get(i));
    }

    public void addChild(int i) {
        if (this.mChildIds == null) {
            this.mChildIds = new LongArray();
        }
        this.mChildIds.add(i);
    }

    public void setDisplayId(int i) {
        this.mDisplayId = i;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public void setTransitionTimeMillis(long j) {
        this.mTransitionTime = j;
    }

    public long getTransitionTimeMillis() {
        return this.mTransitionTime;
    }

    public void setLocales(LocaleList localeList) {
        this.mLocales = localeList;
    }

    public LocaleList getLocales() {
        return this.mLocales;
    }

    public static AccessibilityWindowInfo obtain() {
        AccessibilityWindowInfo accessibilityWindowInfoAcquire = sPool.acquire();
        if (accessibilityWindowInfoAcquire == null) {
            accessibilityWindowInfoAcquire = new AccessibilityWindowInfo();
        }
        AtomicInteger atomicInteger = sNumInstancesInUse;
        if (atomicInteger != null) {
            atomicInteger.incrementAndGet();
        }
        return accessibilityWindowInfoAcquire;
    }

    public static AccessibilityWindowInfo obtain(AccessibilityWindowInfo accessibilityWindowInfo) {
        AccessibilityWindowInfo accessibilityWindowInfoObtain = obtain();
        accessibilityWindowInfoObtain.init(accessibilityWindowInfo);
        return accessibilityWindowInfoObtain;
    }

    public static void setNumInstancesInUseCounter(AtomicInteger atomicInteger) {
        if (sNumInstancesInUse != null) {
            sNumInstancesInUse = atomicInteger;
        }
    }

    public void recycle() {
        clear();
        sPool.release(this);
        AtomicInteger atomicInteger = sNumInstancesInUse;
        if (atomicInteger != null) {
            atomicInteger.decrementAndGet();
        }
    }

    public boolean refresh() {
        AccessibilityWindowInfo window;
        if (this.mConnectionId == -1 || this.mId == -1 || (window = AccessibilityInteractionClient.getInstance().getWindow(this.mConnectionId, this.mId, true)) == null) {
            return false;
        }
        init(window);
        window.recycle();
        return true;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDisplayId);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mLayer);
        parcel.writeInt(this.mBooleanProperties);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mParentId);
        parcel.writeInt(this.mTaskId);
        this.mRegionInScreen.writeToParcel(parcel, i);
        parcel.writeCharSequence(this.mTitle);
        parcel.writeLong(this.mAnchorId);
        parcel.writeLong(this.mTransitionTime);
        LongArray longArray = this.mChildIds;
        if (longArray == null) {
            parcel.writeInt(0);
        } else {
            int size = longArray.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeInt((int) longArray.get(i2));
            }
        }
        parcel.writeInt(this.mConnectionId);
        parcel.writeParcelable(this.mLocales, i);
        parcel.writeInt(this.mRawType);
    }

    private void init(AccessibilityWindowInfo accessibilityWindowInfo) {
        this.mDisplayId = accessibilityWindowInfo.mDisplayId;
        this.mType = accessibilityWindowInfo.mType;
        this.mLayer = accessibilityWindowInfo.mLayer;
        this.mBooleanProperties = accessibilityWindowInfo.mBooleanProperties;
        this.mId = accessibilityWindowInfo.mId;
        this.mParentId = accessibilityWindowInfo.mParentId;
        this.mTaskId = accessibilityWindowInfo.mTaskId;
        this.mRegionInScreen.set(accessibilityWindowInfo.mRegionInScreen);
        this.mTitle = accessibilityWindowInfo.mTitle;
        this.mAnchorId = accessibilityWindowInfo.mAnchorId;
        this.mTransitionTime = accessibilityWindowInfo.mTransitionTime;
        LongArray longArray = this.mChildIds;
        if (longArray != null) {
            longArray.clear();
        }
        LongArray longArray2 = accessibilityWindowInfo.mChildIds;
        if (longArray2 != null && longArray2.size() > 0) {
            LongArray longArray3 = this.mChildIds;
            if (longArray3 == null) {
                this.mChildIds = accessibilityWindowInfo.mChildIds.m5513clone();
            } else {
                longArray3.addAll(accessibilityWindowInfo.mChildIds);
            }
        }
        this.mConnectionId = accessibilityWindowInfo.mConnectionId;
        this.mLocales = accessibilityWindowInfo.mLocales;
        this.mRawType = accessibilityWindowInfo.mRawType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFromParcel(Parcel parcel) {
        this.mDisplayId = parcel.readInt();
        this.mType = parcel.readInt();
        this.mLayer = parcel.readInt();
        this.mBooleanProperties = parcel.readInt();
        this.mId = parcel.readInt();
        this.mParentId = parcel.readInt();
        this.mTaskId = parcel.readInt();
        this.mRegionInScreen = Region.CREATOR.createFromParcel(parcel);
        this.mTitle = parcel.readCharSequence();
        this.mAnchorId = parcel.readLong();
        this.mTransitionTime = parcel.readLong();
        int i = parcel.readInt();
        if (i > 0) {
            if (this.mChildIds == null) {
                this.mChildIds = new LongArray(i);
            }
            for (int i2 = 0; i2 < i; i2++) {
                this.mChildIds.add(parcel.readInt());
            }
        }
        this.mConnectionId = parcel.readInt();
        this.mLocales = (LocaleList) parcel.readParcelable(null, LocaleList.class);
        this.mRawType = parcel.readInt();
    }

    public int hashCode() {
        return this.mId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.mId == ((AccessibilityWindowInfo) obj).mId;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AccessibilityWindowInfo[title=");
        sb.append(this.mTitle);
        sb.append(", displayId=");
        sb.append(this.mDisplayId);
        sb.append(", id=");
        sb.append(this.mId);
        sb.append(", taskId=");
        sb.append(this.mTaskId);
        sb.append(", type=");
        sb.append(typeToString(this.mType));
        sb.append(", layer=");
        sb.append(this.mLayer);
        sb.append(", region=");
        sb.append(this.mRegionInScreen);
        sb.append(", bounds=");
        sb.append(this.mRegionInScreen.getBounds());
        sb.append(", focused=");
        sb.append(isFocused());
        sb.append(", active=");
        sb.append(isActive());
        sb.append(", pictureInPicture=");
        sb.append(isInPictureInPictureMode());
        sb.append(", transitionTime=");
        sb.append(this.mTransitionTime);
        sb.append(", hasParent=");
        sb.append(this.mParentId != -1);
        sb.append(", isAnchored=");
        sb.append(this.mAnchorId != AccessibilityNodeInfo.UNDEFINED_NODE_ID);
        sb.append(", hasChildren=");
        LongArray longArray = this.mChildIds;
        sb.append(longArray != null && longArray.size() > 0);
        sb.append(']');
        return sb.toString();
    }

    private void clear() {
        this.mDisplayId = -1;
        this.mType = -1;
        this.mLayer = -1;
        this.mBooleanProperties = 0;
        this.mId = -1;
        this.mParentId = -1;
        this.mTaskId = -1;
        this.mRegionInScreen.setEmpty();
        this.mChildIds = null;
        this.mConnectionId = -1;
        this.mAnchorId = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
        this.mTitle = null;
        this.mTransitionTime = 0L;
        this.mLocales = LocaleList.getEmptyLocaleList();
        this.mRawType = -1;
    }

    private boolean getBooleanProperty(int i) {
        return (this.mBooleanProperties & i) != 0;
    }

    private void setBooleanProperty(int i, boolean z) {
        if (z) {
            this.mBooleanProperties = i | this.mBooleanProperties;
        } else {
            this.mBooleanProperties = (~i) & this.mBooleanProperties;
        }
    }

    public static String typeToString(int i) {
        switch (i) {
            case 1:
                return "TYPE_APPLICATION";
            case 2:
                return "TYPE_INPUT_METHOD";
            case 3:
                return "TYPE_SYSTEM";
            case 4:
                return "TYPE_ACCESSIBILITY_OVERLAY";
            case 5:
                return "TYPE_SPLIT_SCREEN_DIVIDER";
            case 6:
                return "TYPE_MAGNIFICATION_OVERLAY";
            default:
                if (Flags.enableTypeWindowControl() && i == 7) {
                    return "TYPE_WINDOW_CONTROL";
                }
                return "<UNKNOWN:" + i + ">";
        }
    }

    public int differenceFrom(AccessibilityWindowInfo accessibilityWindowInfo) {
        if (accessibilityWindowInfo.mId != this.mId) {
            throw new IllegalArgumentException("Not same window.");
        }
        if (accessibilityWindowInfo.mType != this.mType) {
            throw new IllegalArgumentException("Not same type.");
        }
        int i = !TextUtils.equals(this.mTitle, accessibilityWindowInfo.mTitle) ? 4 : 0;
        if (!this.mRegionInScreen.equals(accessibilityWindowInfo.mRegionInScreen)) {
            i |= 8;
        }
        if (this.mLayer != accessibilityWindowInfo.mLayer) {
            i |= 16;
        }
        if (getBooleanProperty(1) != accessibilityWindowInfo.getBooleanProperty(1)) {
            i |= 32;
        }
        if (getBooleanProperty(2) != accessibilityWindowInfo.getBooleanProperty(2)) {
            i |= 64;
        }
        if (getBooleanProperty(4) != accessibilityWindowInfo.getBooleanProperty(4)) {
            i |= 128;
        }
        if (getBooleanProperty(8) != accessibilityWindowInfo.getBooleanProperty(8)) {
            i |= 1024;
        }
        if (this.mParentId != accessibilityWindowInfo.mParentId) {
            i |= 256;
        }
        return !Objects.equals(this.mChildIds, accessibilityWindowInfo.mChildIds) ? i | 512 : i;
    }

    public static final class WindowListSparseArray extends SparseArray<List<AccessibilityWindowInfo>> implements Parcelable {
        public static final Parcelable.Creator<WindowListSparseArray> CREATOR = new Parcelable.Creator<WindowListSparseArray>() { // from class: android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public WindowListSparseArray createFromParcel(Parcel parcel) {
                WindowListSparseArray windowListSparseArray = new WindowListSparseArray();
                ClassLoader classLoader = windowListSparseArray.getClass().getClassLoader();
                int i = parcel.readInt();
                for (int i2 = 0; i2 < i; i2++) {
                    ArrayList arrayList = new ArrayList();
                    parcel.readParcelableList(arrayList, classLoader, AccessibilityWindowInfo.class);
                    windowListSparseArray.put(parcel.readInt(), arrayList);
                }
                return windowListSparseArray;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public WindowListSparseArray[] newArray(int i) {
                return new WindowListSparseArray[i];
            }
        };

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            int size = size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeParcelableList(valueAt(i2), 0);
                parcel.writeInt(keyAt(i2));
            }
        }
    }
}
