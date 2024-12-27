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
    int mAction;
    ClipData mClipData;
    ClipDescription mClipDescription;
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
    private static int gRecyclerUsed = 0;
    private static DragEvent gRecyclerTop = null;
    public static final Parcelable.Creator<DragEvent> CREATOR = new Parcelable.Creator<DragEvent>() { // from class: android.view.DragEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DragEvent createFromParcel(Parcel in) {
            DragEvent event = DragEvent.obtain();
            event.mAction = in.readInt();
            event.mX = in.readFloat();
            event.mY = in.readFloat();
            event.mOffsetX = in.readFloat();
            event.mOffsetY = in.readFloat();
            event.mFlags = in.readInt();
            event.mDragResult = in.readInt() != 0;
            event.mIsEavesDropEvent = in.readBoolean();
            event.mIsStickyEvent = in.readBoolean();
            if (in.readInt() != 0) {
                event.mClipData = ClipData.CREATOR.createFromParcel(in);
            }
            if (in.readInt() != 0) {
                event.mClipDescription = ClipDescription.CREATOR.createFromParcel(in);
            }
            if (in.readInt() != 0) {
                event.mDragSurface = SurfaceControl.CREATOR.createFromParcel(in);
                event.mDragSurface.setUnreleasedWarningCallSite("DragEvent");
            }
            if (in.readInt() != 0) {
                event.mDragAndDropPermissions = IDragAndDropPermissions.Stub.asInterface(in.readStrongBinder());
            }
            return event;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DragEvent[] newArray(int size) {
            return new DragEvent[size];
        }
    };

    private DragEvent() {
    }

    private void init(int action, float x, float y, float offsetX, float offsetY, int flags, ClipDescription description, ClipData data, SurfaceControl dragSurface, IDragAndDropPermissions dragAndDropPermissions, Object localState, boolean result) {
        this.mAction = action;
        this.mX = x;
        this.mY = y;
        this.mOffsetX = offsetX;
        this.mOffsetY = offsetY;
        this.mFlags = flags;
        this.mClipDescription = description;
        this.mClipData = data;
        this.mDragSurface = dragSurface;
        this.mDragAndDropPermissions = dragAndDropPermissions;
        this.mLocalState = localState;
        this.mDragResult = result;
    }

    static DragEvent obtain() {
        return obtain(0, 0.0f, 0.0f, 0.0f, 0.0f, 0, null, null, null, null, null, false);
    }

    public static DragEvent obtain(int action, float x, float y, float offsetX, float offsetY, int flags, Object localState, ClipDescription description, ClipData data, SurfaceControl dragSurface, IDragAndDropPermissions dragAndDropPermissions, boolean result) {
        return obtain(action, x, y, offsetX, offsetY, flags, localState, description, data, dragSurface, dragAndDropPermissions, result, false);
    }

    public static DragEvent obtain(int action, float x, float y, float offsetX, float offsetY, int flags, Object localState, ClipDescription description, ClipData data, SurfaceControl dragSurface, IDragAndDropPermissions dragAndDropPermissions, boolean result, boolean isStickyEvent) {
        synchronized (gRecyclerLock) {
            if (gRecyclerTop == null) {
                DragEvent ev = new DragEvent();
                ev.init(action, x, y, offsetX, offsetY, flags, description, data, dragSurface, dragAndDropPermissions, localState, result);
                ev.mIsStickyEvent = isStickyEvent;
                return ev;
            }
            DragEvent ev2 = gRecyclerTop;
            gRecyclerTop = ev2.mNext;
            gRecyclerUsed--;
            ev2.mRecycledLocation = null;
            ev2.mRecycled = false;
            ev2.mNext = null;
            ev2.init(action, x, y, offsetX, offsetY, flags, description, data, dragSurface, dragAndDropPermissions, localState, result);
            ev2.mIsStickyEvent = isStickyEvent;
            return ev2;
        }
    }

    public static DragEvent obtain(DragEvent source) {
        return obtain(source.mAction, source.mX, source.mY, source.mOffsetX, source.mOffsetY, source.mFlags, source.mLocalState, source.mClipDescription, source.mClipData, source.mDragSurface, source.mDragAndDropPermissions, source.mDragResult, source.mIsStickyEvent);
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
        synchronized (gRecyclerLock) {
            if (gRecyclerUsed < 10) {
                gRecyclerUsed++;
                this.mNext = gRecyclerTop;
                gRecyclerTop = this;
            }
        }
    }

    public static String actionToString(int action) {
        switch (action) {
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
                return Integer.toString(action);
        }
    }

    public String toString() {
        return "DragEvent{" + Integer.toHexString(System.identityHashCode(this)) + " action=" + this.mAction + " @ (" + this.mX + ", " + this.mY + ") desc=" + this.mClipDescription + " data=" + this.mClipData + " local=" + this.mLocalState + " result=" + this.mDragResult + " isEavesDrop=" + this.mIsEavesDropEvent + " isStickyEvent=" + this.mIsStickyEvent + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

    public void setIsStickyEvent(boolean isStickyEvent) {
        this.mIsStickyEvent = isStickyEvent;
    }

    public boolean isStickyEvent() {
        return this.mIsStickyEvent;
    }

    public void setEavesDrop(boolean eavesDrop) {
        this.mIsEavesDropEvent = eavesDrop;
    }

    public boolean isEavesDrop() {
        return this.mIsEavesDropEvent;
    }
}
