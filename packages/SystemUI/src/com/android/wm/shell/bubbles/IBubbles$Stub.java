package com.android.wm.shell.bubbles;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.graphics.Point;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.UserHandle;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleLogger;
import com.android.wm.shell.bubbles.BubbleTransitions;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.shared.bubbles.BubbleBarLocation;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class IBubbles$Stub extends Binder implements IInterface {
    public IBubbles$Stub() {
        attachInterface(this, "com.android.wm.shell.bubbles.IBubbles");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        IInterface iInterface;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.wm.shell.bubbles.IBubbles");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.android.wm.shell.bubbles.IBubbles");
            return true;
        }
        switch (i) {
            case 2:
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder == null) {
                    iInterface = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.wm.shell.bubbles.IBubblesListener");
                    iInterface = (queryLocalInterface == null || !(queryLocalInterface instanceof IBubblesListener$Stub$Proxy)) ? new IInterface(readStrongBinder) { // from class: com.android.wm.shell.bubbles.IBubblesListener$Stub$Proxy
                        public final IBinder mRemote;

                        {
                            this.mRemote = readStrongBinder;
                        }

                        @Override // android.os.IInterface
                        public final IBinder asBinder() {
                            return this.mRemote;
                        }
                    } : (IBubblesListener$Stub$Proxy) queryLocalInterface;
                }
                parcel.enforceNoDataAvail();
                BubbleController.IBubblesImpl iBubblesImpl = (BubbleController.IBubblesImpl) this;
                BubbleController.this.mMainExecutor.execute(new BubbleController$$ExternalSyntheticLambda14(8, iBubblesImpl, iInterface));
                return true;
            case 3:
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                if (readStrongBinder2 != null) {
                    IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.android.wm.shell.bubbles.IBubblesListener");
                    if (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof IBubblesListener$Stub$Proxy)) {
                        new IInterface(readStrongBinder2) { // from class: com.android.wm.shell.bubbles.IBubblesListener$Stub$Proxy
                            public final IBinder mRemote;

                            {
                                this.mRemote = readStrongBinder2;
                            }

                            @Override // android.os.IInterface
                            public final IBinder asBinder() {
                                return this.mRemote;
                            }
                        };
                    }
                }
                parcel.enforceNoDataAvail();
                BubbleController.IBubblesImpl iBubblesImpl2 = (BubbleController.IBubblesImpl) this;
                ShellExecutor shellExecutor = BubbleController.this.mMainExecutor;
                SingleInstanceRemoteListener singleInstanceRemoteListener = iBubblesImpl2.mListener;
                Objects.requireNonNull(singleInstanceRemoteListener);
                shellExecutor.execute(new BubbleController$6$$ExternalSyntheticLambda0(singleInstanceRemoteListener, 2));
                return true;
            case 4:
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                BubbleController.IBubblesImpl iBubblesImpl3 = (BubbleController.IBubblesImpl) this;
                BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda0(iBubblesImpl3, readString, readInt, 2));
                return true;
            case 5:
                final String readString2 = parcel.readString();
                final long readLong = parcel.readLong();
                parcel.enforceNoDataAvail();
                final BubbleController.IBubblesImpl iBubblesImpl4 = (BubbleController.IBubblesImpl) this;
                BubbleController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleController$IBubblesImpl$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        BubbleTransitions.BubbleTransition bubbleTransition;
                        BubbleController.IBubblesImpl iBubblesImpl5 = BubbleController.IBubblesImpl.this;
                        String str = readString2;
                        long j = readLong;
                        BubbleController bubbleController = iBubblesImpl5.mController;
                        BubbleData bubbleData = bubbleController.mBubbleData;
                        BubbleViewProvider bubbleViewProvider = bubbleData.mSelectedBubble;
                        String key = bubbleViewProvider != null ? bubbleViewProvider.getKey() : null;
                        Bubble anyBubbleWithKey = bubbleData.getAnyBubbleWithKey(str);
                        BubbleLogger bubbleLogger = bubbleController.mLogger;
                        if (anyBubbleWithKey != null) {
                            Bubble bubbleInStackWithKey = bubbleData.getBubbleInStackWithKey(str);
                            if (bubbleInStackWithKey == null || Math.max(bubbleInStackWithKey.mLastUpdated, bubbleInStackWithKey.mLastAccessed) <= j) {
                                bubbleData.doRemove(18, str);
                                bubbleData.dispatchPendingChanges();
                            }
                            bubbleLogger.log(anyBubbleWithKey, BubbleLogger.Event.BUBBLE_BAR_BUBBLE_DISMISSED_DRAG_BUBBLE);
                        }
                        if (((ArrayList) bubbleData.mBubbles).isEmpty()) {
                            return;
                        }
                        BubbleViewProvider bubbleViewProvider2 = bubbleController.mBubbleData.mSelectedBubble;
                        if (bubbleViewProvider2 != null && (bubbleViewProvider2 instanceof Bubble) && (bubbleTransition = ((Bubble) bubbleViewProvider2).mPreparingTransition) != null) {
                            bubbleTransition.continueExpand();
                        }
                        if (str.equals(key)) {
                            BubbleViewProvider bubbleViewProvider3 = bubbleData.mSelectedBubble;
                            if (bubbleViewProvider3 instanceof Bubble) {
                                bubbleLogger.log((Bubble) bubbleViewProvider3, BubbleLogger.Event.BUBBLE_BAR_BUBBLE_SWITCHED);
                            }
                        }
                    }
                });
                return true;
            case 6:
                BubbleController.IBubblesImpl iBubblesImpl5 = (BubbleController.IBubblesImpl) this;
                BubbleController.this.mMainExecutor.execute(new BubbleController$IBubblesImpl$$ExternalSyntheticLambda2(iBubblesImpl5, 0));
                return true;
            case 7:
                BubbleController.IBubblesImpl iBubblesImpl6 = (BubbleController.IBubblesImpl) this;
                BubbleController.this.mMainExecutor.execute(new BubbleController$IBubblesImpl$$ExternalSyntheticLambda2(iBubblesImpl6, 1));
                return true;
            case 8:
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                BubbleController.IBubblesImpl iBubblesImpl7 = (BubbleController.IBubblesImpl) this;
                BubbleController.this.mMainExecutor.execute(new BubbleController$$ExternalSyntheticLambda14(7, iBubblesImpl7, readString3));
                return true;
            case 9:
                final int readInt2 = parcel.readInt();
                final int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                final BubbleController.IBubblesImpl iBubblesImpl8 = (BubbleController.IBubblesImpl) this;
                BubbleController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleController$IBubblesImpl$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        BubbleController.IBubblesImpl iBubblesImpl9 = BubbleController.IBubblesImpl.this;
                        int i3 = readInt2;
                        int i4 = readInt3;
                        BubbleController bubbleController = iBubblesImpl9.mController;
                        new Point(i3, i4);
                        bubbleController.getClass();
                    }
                });
                return true;
            case 10:
                BubbleBarLocation bubbleBarLocation = (BubbleBarLocation) parcel.readTypedObject(BubbleBarLocation.CREATOR);
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                BubbleController.IBubblesImpl iBubblesImpl9 = (BubbleController.IBubblesImpl) this;
                BubbleController.this.mMainExecutor.execute(new BubbleController$IBubblesImpl$$ExternalSyntheticLambda2(iBubblesImpl9, bubbleBarLocation, readInt4));
                return true;
            case 11:
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                BubbleController.IBubblesImpl iBubblesImpl10 = (BubbleController.IBubblesImpl) this;
                BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda15(iBubblesImpl10, readInt5, 1));
                return true;
            case 12:
                BubbleBarLocation bubbleBarLocation2 = (BubbleBarLocation) parcel.readTypedObject(BubbleBarLocation.CREATOR);
                int readInt6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                BubbleController.IBubblesImpl iBubblesImpl11 = (BubbleController.IBubblesImpl) this;
                BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda0(iBubblesImpl11, bubbleBarLocation2, readInt6, 1));
                return true;
            case 13:
                ShortcutInfo shortcutInfo = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
                BubbleBarLocation bubbleBarLocation3 = (BubbleBarLocation) parcel.readTypedObject(BubbleBarLocation.CREATOR);
                parcel.enforceNoDataAvail();
                BubbleController.IBubblesImpl iBubblesImpl12 = (BubbleController.IBubblesImpl) this;
                BubbleController.this.mMainExecutor.execute(new BubbleController$IBubblesImpl$$ExternalSyntheticLambda2(iBubblesImpl12, shortcutInfo, bubbleBarLocation3));
                return true;
            case 14:
                Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                BubbleBarLocation bubbleBarLocation4 = (BubbleBarLocation) parcel.readTypedObject(BubbleBarLocation.CREATOR);
                parcel.enforceNoDataAvail();
                BubbleController.IBubblesImpl iBubblesImpl13 = (BubbleController.IBubblesImpl) this;
                BubbleController.this.mMainExecutor.execute(new BubbleController$IBubblesImpl$$ExternalSyntheticLambda2(iBubblesImpl13, intent, userHandle, bubbleBarLocation4));
                return true;
            case 15:
                BubbleController.IBubblesImpl iBubblesImpl14 = (BubbleController.IBubblesImpl) this;
                BubbleController.this.mMainExecutor.execute(new BubbleController$IBubblesImpl$$ExternalSyntheticLambda2(iBubblesImpl14, 4));
                return true;
            case 16:
                boolean readBoolean = parcel.readBoolean();
                BubbleBarLocation bubbleBarLocation5 = (BubbleBarLocation) parcel.readTypedObject(BubbleBarLocation.CREATOR);
                parcel.enforceNoDataAvail();
                BubbleController.IBubblesImpl iBubblesImpl15 = (BubbleController.IBubblesImpl) this;
                BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda10(iBubblesImpl15, readBoolean, bubbleBarLocation5));
                return true;
            case 17:
                String readString4 = parcel.readString();
                Point point = (Point) parcel.readTypedObject(Point.CREATOR);
                parcel.enforceNoDataAvail();
                BubbleController.IBubblesImpl iBubblesImpl16 = (BubbleController.IBubblesImpl) this;
                BubbleController.this.mMainExecutor.execute(new BubbleController$$ExternalSyntheticLambda13(iBubblesImpl16, readString4, 2, point));
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
