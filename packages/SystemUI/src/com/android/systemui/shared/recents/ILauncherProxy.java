package com.android.systemui.shared.recents;

import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.view.KeyEvent;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.shared.navigationbar.NavBarEvents;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ILauncherProxy extends IInterface {
    void appTransitionPending(boolean z);

    void checkNavBarModes(int i);

    void disable(int i, int i2, int i3, boolean z);

    void enterStageSplitFromRunningApp();

    void executeSearcle();

    void finishBarAnimations(int i);

    void handleNavigationBarEvent(NavBarEvents navBarEvents);

    void isTaskbarEnabled(boolean z);

    void notifyPayInfo(int i, boolean z);

    void onActiveNavBarRegionChanges(Region region);

    void onAssistantAvailable(boolean z, boolean z2);

    void onAssistantOverrideInvoked(int i);

    void onAssistantVisibilityChanged();

    void onDisplayAddSystemDecorations(int i);

    void onDisplayRemoveSystemDecorations(int i);

    void onDisplayRemoved(int i);

    void onInitialize(Bundle bundle);

    void onNavButtonsDarkIntensityChanged(float f);

    void onNavigationBarLumaSamplingEnabled(int i, boolean z);

    void onNumberOfVisibleFgsChanged(int i);

    void onOverviewHidden(boolean z, boolean z2);

    void onOverviewShown(boolean z);

    void onOverviewToggle();

    void onQuickScrubEnd();

    void onQuickScrubStart();

    void onRotationProposal(int i, boolean z);

    void onScreenTurningOff();

    void onSystemBarAttributesChanged(int i, int i2);

    void onSystemUiStateChanged(int i, long j);

    void onTaskbarToggled();

    void onThreeFingerGestureEvent(KeyEvent keyEvent);

    void onTransitionModeUpdated(int i);

    void onUnbind(LauncherProxyService.AnonymousClass9 anonymousClass9);

    void touchAutoDim(int i, boolean z);

    void transitionTo(int i, int i2);

    void updateWallpaperVisibility(int i, boolean z);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ILauncherProxy {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Proxy implements ILauncherProxy {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void appTransitionPending(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void checkNavBarModes(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void disable(int i, int i2, int i3, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void finishBarAnimations(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void handleNavigationBarEvent(NavBarEvents navBarEvents) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeTypedObject(navBarEvents, 0);
                    this.mRemote.transact(102, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void isTaskbarEnabled(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeBoolean(z);
                    this.mRemote.transact(104, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void notifyPayInfo(int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(101, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onActiveNavBarRegionChanges(Region region) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeTypedObject(region, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onAssistantAvailable(boolean z, boolean z2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onAssistantOverrideInvoked(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onDisplayAddSystemDecorations(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onDisplayRemoveSystemDecorations(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onDisplayRemoved(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onInitialize(Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onNavButtonsDarkIntensityChanged(float f) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeFloat(f);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onNavigationBarLumaSamplingEnabled(int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onNumberOfVisibleFgsChanged(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeInt(i);
                    this.mRemote.transact(103, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onOverviewHidden(boolean z, boolean z2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onOverviewShown(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onOverviewToggle() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onQuickScrubEnd() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    this.mRemote.transact(107, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onQuickScrubStart() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    this.mRemote.transact(106, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onRotationProposal(int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onScreenTurningOff() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    this.mRemote.transact(108, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onSystemBarAttributesChanged(int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onSystemUiStateChanged(int i, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onTaskbarToggled() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onThreeFingerGestureEvent(KeyEvent keyEvent) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(109, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onTransitionModeUpdated(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeInt(i);
                    obtain.writeBoolean(true);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onUnbind(LauncherProxyService.AnonymousClass9 anonymousClass9) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeStrongInterface(anonymousClass9);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void touchAutoDim(int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void transitionTo(int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(true);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void updateWallpaperVisibility(int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.shared.recents.ILauncherProxy");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.shared.recents.ILauncherProxy");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.shared.recents.ILauncherProxy");
                return true;
            }
            if (i == 7) {
                onOverviewToggle();
                return true;
            }
            if (i == 8) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onOverviewShown(readBoolean);
                return true;
            }
            if (i == 9) {
                boolean readBoolean2 = parcel.readBoolean();
                boolean readBoolean3 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onOverviewHidden(readBoolean2, readBoolean3);
                return true;
            }
            if (i == 17) {
                long readLong = parcel.readLong();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSystemUiStateChanged(readInt, readLong);
                return true;
            }
            if (i == 26) {
                parcel.readBoolean();
                parcel.enforceNoDataAvail();
                enterStageSplitFromRunningApp();
                return true;
            }
            switch (i) {
                case 12:
                    Region region = (Region) parcel.readTypedObject(Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    onActiveNavBarRegionChanges(region);
                    return true;
                case 13:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onInitialize(bundle);
                    return true;
                case 14:
                    boolean readBoolean4 = parcel.readBoolean();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAssistantAvailable(readBoolean4, readBoolean5);
                    return true;
                case 15:
                    parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    onAssistantVisibilityChanged();
                    return true;
                default:
                    switch (i) {
                        case 19:
                            int readInt2 = parcel.readInt();
                            boolean readBoolean6 = parcel.readBoolean();
                            parcel.enforceNoDataAvail();
                            onRotationProposal(readInt2, readBoolean6);
                            return true;
                        case 20:
                            int readInt3 = parcel.readInt();
                            int readInt4 = parcel.readInt();
                            int readInt5 = parcel.readInt();
                            boolean readBoolean7 = parcel.readBoolean();
                            parcel.enforceNoDataAvail();
                            disable(readInt3, readInt4, readInt5, readBoolean7);
                            return true;
                        case 21:
                            int readInt6 = parcel.readInt();
                            int readInt7 = parcel.readInt();
                            parcel.enforceNoDataAvail();
                            onSystemBarAttributesChanged(readInt6, readInt7);
                            return true;
                        case 22:
                            int readInt8 = parcel.readInt();
                            parcel.readBoolean();
                            parcel.enforceNoDataAvail();
                            onTransitionModeUpdated(readInt8);
                            return true;
                        case 23:
                            float readFloat = parcel.readFloat();
                            parcel.enforceNoDataAvail();
                            onNavButtonsDarkIntensityChanged(readFloat);
                            return true;
                        case 24:
                            int readInt9 = parcel.readInt();
                            boolean readBoolean8 = parcel.readBoolean();
                            parcel.enforceNoDataAvail();
                            onNavigationBarLumaSamplingEnabled(readInt9, readBoolean8);
                            return true;
                        default:
                            switch (i) {
                                case 28:
                                    onTaskbarToggled();
                                    return true;
                                case 29:
                                    int readInt10 = parcel.readInt();
                                    parcel.enforceNoDataAvail();
                                    onAssistantOverrideInvoked(readInt10);
                                    return true;
                                case 30:
                                    int readInt11 = parcel.readInt();
                                    boolean readBoolean9 = parcel.readBoolean();
                                    parcel.enforceNoDataAvail();
                                    updateWallpaperVisibility(readInt11, readBoolean9);
                                    return true;
                                case 31:
                                    int readInt12 = parcel.readInt();
                                    parcel.enforceNoDataAvail();
                                    checkNavBarModes(readInt12);
                                    return true;
                                case 32:
                                    int readInt13 = parcel.readInt();
                                    parcel.enforceNoDataAvail();
                                    finishBarAnimations(readInt13);
                                    return true;
                                case 33:
                                    int readInt14 = parcel.readInt();
                                    boolean readBoolean10 = parcel.readBoolean();
                                    parcel.enforceNoDataAvail();
                                    touchAutoDim(readInt14, readBoolean10);
                                    return true;
                                case 34:
                                    int readInt15 = parcel.readInt();
                                    int readInt16 = parcel.readInt();
                                    parcel.readBoolean();
                                    parcel.enforceNoDataAvail();
                                    transitionTo(readInt15, readInt16);
                                    return true;
                                case 35:
                                    boolean readBoolean11 = parcel.readBoolean();
                                    parcel.enforceNoDataAvail();
                                    appTransitionPending(readBoolean11);
                                    return true;
                                case 36:
                                    IRemoteCallback asInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                                    parcel.enforceNoDataAvail();
                                    onUnbind((LauncherProxyService.AnonymousClass9) asInterface);
                                    return true;
                                case 37:
                                    int readInt17 = parcel.readInt();
                                    parcel.enforceNoDataAvail();
                                    onDisplayAddSystemDecorations(readInt17);
                                    return true;
                                case 38:
                                    int readInt18 = parcel.readInt();
                                    parcel.enforceNoDataAvail();
                                    onDisplayRemoved(readInt18);
                                    return true;
                                case 39:
                                    int readInt19 = parcel.readInt();
                                    parcel.enforceNoDataAvail();
                                    onDisplayRemoveSystemDecorations(readInt19);
                                    return true;
                                default:
                                    switch (i) {
                                        case 101:
                                            boolean readBoolean12 = parcel.readBoolean();
                                            int readInt20 = parcel.readInt();
                                            parcel.enforceNoDataAvail();
                                            notifyPayInfo(readInt20, readBoolean12);
                                            return true;
                                        case 102:
                                            NavBarEvents navBarEvents = (NavBarEvents) parcel.readTypedObject(NavBarEvents.CREATOR);
                                            parcel.enforceNoDataAvail();
                                            handleNavigationBarEvent(navBarEvents);
                                            return true;
                                        case 103:
                                            int readInt21 = parcel.readInt();
                                            parcel.enforceNoDataAvail();
                                            onNumberOfVisibleFgsChanged(readInt21);
                                            return true;
                                        case 104:
                                            boolean readBoolean13 = parcel.readBoolean();
                                            parcel.enforceNoDataAvail();
                                            isTaskbarEnabled(readBoolean13);
                                            return true;
                                        case 105:
                                            executeSearcle();
                                            return true;
                                        case 106:
                                            onQuickScrubStart();
                                            return true;
                                        case 107:
                                            onQuickScrubEnd();
                                            return true;
                                        case 108:
                                            onScreenTurningOff();
                                            return true;
                                        case 109:
                                            KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                                            parcel.enforceNoDataAvail();
                                            onThreeFingerGestureEvent(keyEvent);
                                            return true;
                                        default:
                                            return super.onTransact(i, parcel, parcel2, i2);
                                    }
                            }
                    }
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
