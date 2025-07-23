package android.view;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.view.IDragAndDropPermissions;

/* loaded from: classes4.dex */
public class DragEvent implements Parcelable {
    public static final int ACTION_DRAG_ENDED = 4;
    public static final int ACTION_DRAG_ENTERED = 5;
    public static final int ACTION_DRAG_EXITED = 6;
    public static final int ACTION_DRAG_LOCATION = 2;
    public static final int ACTION_DRAG_STARTED = 1;
    public static final int ACTION_DROP = 3;
    private static final int MAX_RECYCLED = 10;
    private static final boolean TRACK_RECYCLED_LOCATION = false;
    private static DragEvent gRecyclerTop;
    private static int gRecyclerUsed;
    int mAction;
    ClipData mClipData;
    ClipDescription mClipDescription;
    private int mDisplayId;
    IDragAndDropPermissions mDragAndDropPermissions;
    boolean mDragResult;
    private SurfaceControl mDragSurface;
    boolean mEventHandlerWasCalled;
    private int mFlags;
    private boolean mIsEavesDropEvent;
    private boolean mIsStickyEvent;
    Object mLocalState;
    private DragEvent mNext;
    private float mOffsetX;
    private float mOffsetY;
    private boolean mRecycled;
    private RuntimeException mRecycledLocation;
    float mX;
    float mY;
    private static final Object gRecyclerLock = new Object();
    public static final Parcelable.Creator<DragEvent> CREATOR = new Parcelable.Creator<DragEvent>() { // from class: android.view.DragEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DragEvent createFromParcel(Parcel parcel) {
            DragEvent obtain = DragEvent.obtain();
            obtain.mAction = parcel.readInt();
            obtain.mX = parcel.readFloat();
            obtain.mY = parcel.readFloat();
            obtain.mOffsetX = parcel.readFloat();
            obtain.mOffsetY = parcel.readFloat();
            obtain.mFlags = parcel.readInt();
            obtain.mDragResult = parcel.readInt() != 0;
            obtain.mIsEavesDropEvent = parcel.readBoolean();
            obtain.mIsStickyEvent = parcel.readBoolean();
            obtain.mDisplayId = parcel.readInt();
            if (parcel.readInt() != 0) {
                obtain.mClipData = ClipData.CREATOR.createFromParcel(parcel);
            }
            if (parcel.readInt() != 0) {
                obtain.mClipDescription = ClipDescription.CREATOR.createFromParcel(parcel);
            }
            if (parcel.readInt() != 0) {
                obtain.mDragSurface = SurfaceControl.CREATOR.createFromParcel(parcel);
                obtain.mDragSurface.setUnreleasedWarningCallSite("DragEvent");
            }
            if (parcel.readInt() != 0) {
                obtain.mDragAndDropPermissions = IDragAndDropPermissions.Stub.asInterface(parcel.readStrongBinder());
            }
            return obtain;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DragEvent[] newArray(int i) {
            return new DragEvent[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private DragEvent() {
    }

    private void init(int i, float f, float f2, float f3, float f4, int i2, int i3, ClipDescription clipDescription, ClipData clipData, SurfaceControl surfaceControl, IDragAndDropPermissions iDragAndDropPermissions, Object obj, boolean z) {
        this.mAction = i;
        this.mX = f;
        this.mY = f2;
        this.mOffsetX = f3;
        this.mOffsetY = f4;
        this.mDisplayId = i2;
        this.mFlags = i3;
        this.mClipDescription = clipDescription;
        this.mClipData = clipData;
        this.mDragSurface = surfaceControl;
        this.mDragAndDropPermissions = iDragAndDropPermissions;
        this.mLocalState = obj;
        this.mDragResult = z;
    }

    static DragEvent obtain() {
        return obtain(0, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, null, null, null, null, null, false, false);
    }

    public static DragEvent obtain(int i, float f, float f2, float f3, float f4, int i2, int i3, Object obj, ClipDescription clipDescription, ClipData clipData, SurfaceControl surfaceControl, IDragAndDropPermissions iDragAndDropPermissions, boolean z) {
        return obtain(i, f, f2, f3, f4, i2, i3, obj, clipDescription, clipData, surfaceControl, iDragAndDropPermissions, z, false);
    }

    public static DragEvent obtain(int i, float f, float f2, float f3, float f4, int i2, int i3, Object obj, ClipDescription clipDescription, ClipData clipData, SurfaceControl surfaceControl, IDragAndDropPermissions iDragAndDropPermissions, boolean z, boolean z2) {
        synchronized (gRecyclerLock) {
            DragEvent dragEvent = gRecyclerTop;
            if (dragEvent == null) {
                DragEvent dragEvent2 = new DragEvent();
                dragEvent2.init(i, f, f2, f3, f4, i2, i3, clipDescription, clipData, surfaceControl, iDragAndDropPermissions, obj, z);
                dragEvent2.mIsStickyEvent = z2;
                return dragEvent2;
            }
            gRecyclerTop = dragEvent.mNext;
            gRecyclerUsed--;
            dragEvent.mRecycledLocation = null;
            dragEvent.mRecycled = false;
            dragEvent.mNext = null;
            dragEvent.init(i, f, f2, f3, f4, i2, i3, clipDescription, clipData, surfaceControl, iDragAndDropPermissions, obj, z);
            dragEvent.mIsStickyEvent = z2;
            return dragEvent;
        }
    }

    public static DragEvent obtain(DragEvent dragEvent) {
        return obtain(dragEvent.mAction, dragEvent.mX, dragEvent.mY, dragEvent.mOffsetX, dragEvent.mOffsetY, dragEvent.mDisplayId, dragEvent.mFlags, dragEvent.mLocalState, dragEvent.mClipDescription, dragEvent.mClipData, dragEvent.mDragSurface, dragEvent.mDragAndDropPermissions, dragEvent.mDragResult, dragEvent.mIsStickyEvent);
    }

    public int getAction() {
        return this.mAction;
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public float getOffsetX() {
        return this.mOffsetX;
    }

    public float getOffsetY() {
        return this.mOffsetY;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public ClipData getClipData() {
        return this.mClipData;
    }

    public ClipDescription getClipDescription() {
        return this.mClipDescription;
    }

    public SurfaceControl getDragSurface() {
        return this.mDragSurface;
    }

    public int getDragFlags() {
        return this.mFlags;
    }

    public IDragAndDropPermissions getDragAndDropPermissions() {
        return this.mDragAndDropPermissions;
    }

    public Object getLocalState() {
        return this.mLocalState;
    }

    public boolean getResult() {
        return this.mDragResult;
    }

    public final void recycle() {
        if (this.mRecycled) {
            throw new RuntimeException(toString() + " recycled twice!");
        }
        this.mRecycled = true;
        this.mClipData = null;
        this.mClipDescription = null;
        this.mLocalState = null;
        this.mEventHandlerWasCalled = false;
        this.mIsEavesDropEvent = false;
        this.mIsStickyEvent = false;
        this.mDisplayId = 0;
        synchronized (gRecyclerLock) {
            int i = gRecyclerUsed;
            if (i < 10) {
                gRecyclerUsed = i + 1;
                this.mNext = gRecyclerTop;
                gRecyclerTop = this;
            }
        }
    }

    public static String actionToString(int i) {
        switch (i) {
            case 1:
                return "ACTION_DRAG_STARTED";
            case 2:
                return "ACTION_DRAG_LOCATION";
            case 3:
                return "ACTION_DROP";
            case 4:
                return "ACTION_DRAG_ENDED";
            case 5:
                return "ACTION_DRAG_ENTERED";
            case 6:
                return "ACTION_DRAG_EXITED";
            default:
                return Integer.toString(i);
        }
    }

    public String toString() {
        return "DragEvent{" + Integer.toHexString(System.identityHashCode(this)) + " action=" + this.mAction + " @ (" + this.mX + ", " + this.mY + ") desc=" + this.mClipDescription + " data=" + this.mClipData + " local=" + this.mLocalState + " result=" + this.mDragResult + " isEavesDrop=" + this.mIsEavesDropEvent + " isStickyEvent=" + this.mIsStickyEvent + " displayId=" + this.mDisplayId + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAction);
        parcel.writeFloat(this.mX);
        parcel.writeFloat(this.mY);
        parcel.writeFloat(this.mOffsetX);
        parcel.writeFloat(this.mOffsetY);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mDragResult ? 1 : 0);
        parcel.writeBoolean(this.mIsEavesDropEvent);
        parcel.writeBoolean(this.mIsStickyEvent);
        parcel.writeInt(this.mDisplayId);
        if (this.mClipData == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mClipData.writeToParcel(parcel, i);
        }
        if (this.mClipDescription == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mClipDescription.writeToParcel(parcel, i);
        }
        if (this.mDragSurface == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mDragSurface.writeToParcel(parcel, i);
        }
        if (this.mDragAndDropPermissions == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeStrongBinder(this.mDragAndDropPermissions.asBinder());
        }
    }

    public void setIsStickyEvent(boolean z) {
        this.mIsStickyEvent = z;
    }

    public boolean isStickyEvent() {
        return this.mIsStickyEvent;
    }

    public void setEavesDrop(boolean z) {
        this.mIsEavesDropEvent = z;
    }

    public boolean isEavesDrop() {
        return this.mIsEavesDropEvent;
    }

    public void setDisplayId(int i) {
        this.mDisplayId = i;
    }
}
