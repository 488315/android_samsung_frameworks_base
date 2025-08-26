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

    public abstract class Stub extends Binder implements ILauncherProxy {
        public static final /* synthetic */ int $r8$clinit = 0;

        public class Proxy implements ILauncherProxy {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void appTransitionPending(boolean z) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(35, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void checkNavBarModes(int i) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void disable(int i, int i2, int i3, boolean z) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void finishBarAnimations(int i) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void handleNavigationBarEvent(NavBarEvents navBarEvents) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeTypedObject(navBarEvents, 0);
                    this.mRemote.transact(102, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void isTaskbarEnabled(boolean z) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(104, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void notifyPayInfo(int i, boolean z) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(101, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onActiveNavBarRegionChanges(Region region) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeTypedObject(region, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onAssistantAvailable(boolean z, boolean z2) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onAssistantOverrideInvoked(int i) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onDisplayAddSystemDecorations(int i) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onDisplayRemoveSystemDecorations(int i) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onDisplayRemoved(int i) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onInitialize(Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onNavButtonsDarkIntensityChanged(float f) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(23, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onNavigationBarLumaSamplingEnabled(int i, boolean z) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(24, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onNumberOfVisibleFgsChanged(int i) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(103, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onOverviewHidden(boolean z, boolean z2) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onOverviewShown(boolean z) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onOverviewToggle() {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onQuickScrubEnd() {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    this.mRemote.transact(107, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onQuickScrubStart() {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    this.mRemote.transact(106, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onRotationProposal(int i, boolean z) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onScreenTurningOff() {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    this.mRemote.transact(108, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onSystemBarAttributesChanged(int i, int i2) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onSystemUiStateChanged(int i, long j) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onTaskbarToggled() {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    this.mRemote.transact(28, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onThreeFingerGestureEvent(KeyEvent keyEvent) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(109, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onTransitionModeUpdated(int i) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(true);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void onUnbind(LauncherProxyService.AnonymousClass9 anonymousClass9) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeStrongInterface(anonymousClass9);
                    this.mRemote.transact(36, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void touchAutoDim(int i, boolean z) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(33, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void transitionTo(int i, int i2) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(true);
                    this.mRemote.transact(34, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.shared.recents.ILauncherProxy
            public final void updateWallpaperVisibility(int i, boolean z) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.recents.ILauncherProxy");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(30, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
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
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onOverviewShown(z);
                return true;
            }
            if (i == 9) {
                boolean z2 = parcel.readBoolean();
                boolean z3 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onOverviewHidden(z2, z3);
                return true;
            }
            if (i == 17) {
                long j = parcel.readLong();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSystemUiStateChanged(i3, j);
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
                    boolean z4 = parcel.readBoolean();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAssistantAvailable(z4, z5);
                    return true;
                case 15:
                    parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    onAssistantVisibilityChanged();
                    return true;
                default:
                    switch (i) {
                        case 19:
                            int i4 = parcel.readInt();
                            boolean z6 = parcel.readBoolean();
                            parcel.enforceNoDataAvail();
                            onRotationProposal(i4, z6);
                            return true;
                        case 20:
                            int i5 = parcel.readInt();
                            int i6 = parcel.readInt();
                            int i7 = parcel.readInt();
                            boolean z7 = parcel.readBoolean();
                            parcel.enforceNoDataAvail();
                            disable(i5, i6, i7, z7);
                            return true;
                        case 21:
                            int i8 = parcel.readInt();
                            int i9 = parcel.readInt();
                            parcel.enforceNoDataAvail();
                            onSystemBarAttributesChanged(i8, i9);
                            return true;
                        case 22:
                            int i10 = parcel.readInt();
                            parcel.readBoolean();
                            parcel.enforceNoDataAvail();
                            onTransitionModeUpdated(i10);
                            return true;
                        case 23:
                            float f = parcel.readFloat();
                            parcel.enforceNoDataAvail();
                            onNavButtonsDarkIntensityChanged(f);
                            return true;
                        case 24:
                            int i11 = parcel.readInt();
                            boolean z8 = parcel.readBoolean();
                            parcel.enforceNoDataAvail();
                            onNavigationBarLumaSamplingEnabled(i11, z8);
                            return true;
                        default:
                            switch (i) {
                                case 28:
                                    onTaskbarToggled();
                                    return true;
                                case 29:
                                    int i12 = parcel.readInt();
                                    parcel.enforceNoDataAvail();
                                    onAssistantOverrideInvoked(i12);
                                    return true;
                                case 30:
                                    int i13 = parcel.readInt();
                                    boolean z9 = parcel.readBoolean();
                                    parcel.enforceNoDataAvail();
                                    updateWallpaperVisibility(i13, z9);
                                    return true;
                                case 31:
                                    int i14 = parcel.readInt();
                                    parcel.enforceNoDataAvail();
                                    checkNavBarModes(i14);
                                    return true;
                                case 32:
                                    int i15 = parcel.readInt();
                                    parcel.enforceNoDataAvail();
                                    finishBarAnimations(i15);
                                    return true;
                                case 33:
                                    int i16 = parcel.readInt();
                                    boolean z10 = parcel.readBoolean();
                                    parcel.enforceNoDataAvail();
                                    touchAutoDim(i16, z10);
                                    return true;
                                case 34:
                                    int i17 = parcel.readInt();
                                    int i18 = parcel.readInt();
                                    parcel.readBoolean();
                                    parcel.enforceNoDataAvail();
                                    transitionTo(i17, i18);
                                    return true;
                                case 35:
                                    boolean z11 = parcel.readBoolean();
                                    parcel.enforceNoDataAvail();
                                    appTransitionPending(z11);
                                    return true;
                                case 36:
                                    IRemoteCallback iRemoteCallbackAsInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                                    parcel.enforceNoDataAvail();
                                    onUnbind((LauncherProxyService.AnonymousClass9) iRemoteCallbackAsInterface);
                                    return true;
                                case 37:
                                    int i19 = parcel.readInt();
                                    parcel.enforceNoDataAvail();
                                    onDisplayAddSystemDecorations(i19);
                                    return true;
                                case 38:
                                    int i20 = parcel.readInt();
                                    parcel.enforceNoDataAvail();
                                    onDisplayRemoved(i20);
                                    return true;
                                case 39:
                                    int i21 = parcel.readInt();
                                    parcel.enforceNoDataAvail();
                                    onDisplayRemoveSystemDecorations(i21);
                                    return true;
                                default:
                                    switch (i) {
                                        case 101:
                                            boolean z12 = parcel.readBoolean();
                                            int i22 = parcel.readInt();
                                            parcel.enforceNoDataAvail();
                                            notifyPayInfo(i22, z12);
                                            return true;
                                        case 102:
                                            NavBarEvents navBarEvents = (NavBarEvents) parcel.readTypedObject(NavBarEvents.CREATOR);
                                            parcel.enforceNoDataAvail();
                                            handleNavigationBarEvent(navBarEvents);
                                            return true;
                                        case 103:
                                            int i23 = parcel.readInt();
                                            parcel.enforceNoDataAvail();
                                            onNumberOfVisibleFgsChanged(i23);
                                            return true;
                                        case 104:
                                            boolean z13 = parcel.readBoolean();
                                            parcel.enforceNoDataAvail();
                                            isTaskbarEnabled(z13);
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
