package com.android.systemui.shared.recents;

import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.view.SurfaceControl;
import com.android.systemui.shared.navigationbar.NavBarEvents;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface IOverviewProxy extends IInterface {
    void disable(int i, int i2, int i3, boolean z);

    void enterStageSplitFromRunningApp(boolean z);

    void executeSearcle();

    void handleNavigationBarEvent(NavBarEvents navBarEvents);

    void isTaskbarEnabled(boolean z);

    void notifyPayInfo(int i, boolean z);

    void onActiveNavBarRegionChanges(Region region);

    void onAssistantAvailable(boolean z, boolean z2);

    void onAssistantVisibilityChanged();

    void onInitialize(Bundle bundle);

    void onNavButtonsDarkIntensityChanged(float f);

    void onNavigationBarSurface(SurfaceControl surfaceControl);

    void onNumberOfVisibleFgsChanged(int i);

    void onOverviewHidden(boolean z, boolean z2);

    void onOverviewShown(boolean z);

    void onOverviewToggle();

    void onQuickScrubEnd();

    void onQuickScrubStart();

    void onRotationProposal(int i, boolean z);

    void onScreenTurnedOn();

    void onScreenTurningOff();

    void onScreenTurningOn();

    void onSystemBarAttributesChanged(int i, int i2);

    void onSystemUiStateChanged(long j);

    void onTaskbarToggled();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Stub extends Binder implements IOverviewProxy {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Proxy implements IOverviewProxy {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void disable(int i, int i2, int i3, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void enterStageSplitFromRunningApp(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    obtain.writeBoolean(z);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void handleNavigationBarEvent(NavBarEvents navBarEvents) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    obtain.writeTypedObject(navBarEvents, 0);
                    this.mRemote.transact(102, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void isTaskbarEnabled(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    obtain.writeBoolean(z);
                    this.mRemote.transact(104, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void notifyPayInfo(int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(101, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onActiveNavBarRegionChanges(Region region) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    obtain.writeTypedObject(region, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onAssistantAvailable(boolean z, boolean z2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onInitialize(Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onNavButtonsDarkIntensityChanged(float f) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    obtain.writeFloat(f);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onNavigationBarSurface(SurfaceControl surfaceControl) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    obtain.writeTypedObject(surfaceControl, 0);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onNumberOfVisibleFgsChanged(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    obtain.writeInt(i);
                    this.mRemote.transact(103, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onOverviewHidden(boolean z, boolean z2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onOverviewShown(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onOverviewToggle() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onQuickScrubEnd() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onQuickScrubStart() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onRotationProposal(int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onScreenTurnedOn() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onScreenTurningOff() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onScreenTurningOn() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onSystemBarAttributesChanged(int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onSystemUiStateChanged(long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    obtain.writeLong(j);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.IOverviewProxy
            public final void onTaskbarToggled() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.IOverviewProxy");
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.shared.recents.IOverviewProxy");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.shared.recents.IOverviewProxy");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.shared.recents.IOverviewProxy");
                return true;
            }
            if (i == 7) {
                onOverviewToggle();
            } else if (i == 8) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onOverviewShown(readBoolean);
            } else if (i == 9) {
                boolean readBoolean2 = parcel.readBoolean();
                boolean readBoolean3 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onOverviewHidden(readBoolean2, readBoolean3);
            } else if (i != 17) {
                switch (i) {
                    case 12:
                        Region region = (Region) parcel.readTypedObject(Region.CREATOR);
                        parcel.enforceNoDataAvail();
                        onActiveNavBarRegionChanges(region);
                        break;
                    case 13:
                        Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                        parcel.enforceNoDataAvail();
                        onInitialize(bundle);
                        break;
                    case 14:
                        boolean readBoolean4 = parcel.readBoolean();
                        boolean readBoolean5 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        onAssistantAvailable(readBoolean4, readBoolean5);
                        break;
                    case 15:
                        parcel.readFloat();
                        parcel.enforceNoDataAvail();
                        onAssistantVisibilityChanged();
                        break;
                    default:
                        switch (i) {
                            case 19:
                                int readInt = parcel.readInt();
                                boolean readBoolean6 = parcel.readBoolean();
                                parcel.enforceNoDataAvail();
                                onRotationProposal(readInt, readBoolean6);
                                break;
                            case 20:
                                int readInt2 = parcel.readInt();
                                int readInt3 = parcel.readInt();
                                int readInt4 = parcel.readInt();
                                boolean readBoolean7 = parcel.readBoolean();
                                parcel.enforceNoDataAvail();
                                disable(readInt2, readInt3, readInt4, readBoolean7);
                                break;
                            case 21:
                                int readInt5 = parcel.readInt();
                                int readInt6 = parcel.readInt();
                                parcel.enforceNoDataAvail();
                                onSystemBarAttributesChanged(readInt5, readInt6);
                                break;
                            case 22:
                                onScreenTurnedOn();
                                break;
                            case 23:
                                float readFloat = parcel.readFloat();
                                parcel.enforceNoDataAvail();
                                onNavButtonsDarkIntensityChanged(readFloat);
                                break;
                            case 24:
                                onScreenTurningOn();
                                break;
                            case 25:
                                onScreenTurningOff();
                                break;
                            case 26:
                                boolean readBoolean8 = parcel.readBoolean();
                                parcel.enforceNoDataAvail();
                                enterStageSplitFromRunningApp(readBoolean8);
                                break;
                            case 27:
                                SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                                parcel.enforceNoDataAvail();
                                onNavigationBarSurface(surfaceControl);
                                break;
                            case 28:
                                onTaskbarToggled();
                                break;
                            case 29:
                                onQuickScrubStart();
                                break;
                            case 30:
                                onQuickScrubEnd();
                                break;
                            default:
                                switch (i) {
                                    case 101:
                                        boolean readBoolean9 = parcel.readBoolean();
                                        int readInt7 = parcel.readInt();
                                        parcel.enforceNoDataAvail();
                                        notifyPayInfo(readInt7, readBoolean9);
                                        break;
                                    case 102:
                                        NavBarEvents navBarEvents = (NavBarEvents) parcel.readTypedObject(NavBarEvents.CREATOR);
                                        parcel.enforceNoDataAvail();
                                        handleNavigationBarEvent(navBarEvents);
                                        break;
                                    case 103:
                                        int readInt8 = parcel.readInt();
                                        parcel.enforceNoDataAvail();
                                        onNumberOfVisibleFgsChanged(readInt8);
                                        break;
                                    case 104:
                                        boolean readBoolean10 = parcel.readBoolean();
                                        parcel.enforceNoDataAvail();
                                        isTaskbarEnabled(readBoolean10);
                                        break;
                                    case 105:
                                        executeSearcle();
                                        break;
                                    default:
                                        return super.onTransact(i, parcel, parcel2, i2);
                                }
                        }
                }
            } else {
                long readLong = parcel.readLong();
                parcel.enforceNoDataAvail();
                onSystemUiStateChanged(readLong);
            }
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
