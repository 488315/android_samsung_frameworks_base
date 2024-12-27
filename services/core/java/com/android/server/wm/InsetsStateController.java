package com.android.server.wm;

import android.graphics.Insets;
import android.graphics.Point;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.view.InsetsSource;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.inputmethod.Flags;

import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;
import com.android.server.inputmethod.InputMethodManagerInternal;

import java.util.ArrayList;
import java.util.function.Consumer;

public final class InsetsStateController {
    public final DisplayContent mDisplayContent;
    public int mForcedConsumingTypes;
    public final InsetsState mLastState = new InsetsState();
    public final InsetsState mState = new InsetsState();
    public final SparseArray mProviders = new SparseArray();
    public final ArrayMap mControlTargetProvidersMap = new ArrayMap();
    public final SparseArray mIdControlTargetMap = new SparseArray();
    public final SparseArray mIdFakeControlTargetMap = new SparseArray();
    public final ArraySet mPendingControlChanged = new ArraySet();
    public final InsetsStateController$$ExternalSyntheticLambda2 mDispatchInsetsChanged =
            new InsetsStateController$$ExternalSyntheticLambda2();
    public final AnonymousClass1 mEmptyImeControlTarget =
            new InsetsControlTarget() { // from class: com.android.server.wm.InsetsStateController.1
                @Override // com.android.server.wm.InsetsControlTarget
                public final void notifyInsetsControlChanged(final int i) {
                    InsetsStateController insetsStateController = InsetsStateController.this;
                    InsetsSourceControl[] controlsForDispatch =
                            insetsStateController.getControlsForDispatch(this);
                    if (controlsForDispatch == null) {
                        return;
                    }
                    for (InsetsSourceControl insetsSourceControl : controlsForDispatch) {
                        if (insetsSourceControl.getType() == WindowInsets.Type.ime()) {
                            insetsStateController.mDisplayContent.mWmService.mH.post(
                                    new Runnable(
                                            i) { // from class:
                                                 // com.android.server.wm.InsetsStateController$1$$ExternalSyntheticLambda0
                                        public final /* synthetic */ int f$0;

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            InputMethodManagerInternal.get().removeImeSurface();
                                        }
                                    });
                        }
                    }
                }
            };

    public InsetsStateController(DisplayContent displayContent) {
        this.mDisplayContent = displayContent;
    }

    public final void collectPolicyControlledTypes(ArrayList arrayList, int i) {
        SparseArray sparseArray = this.mProviders;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            InsetsSourceProvider insetsSourceProvider =
                    (InsetsSourceProvider) sparseArray.valueAt(size);
            if (insetsSourceProvider.mSource.getType() == i) {
                arrayList.add(insetsSourceProvider);
            }
        }
    }

    public final InsetsSourceControl[] getControlsForDispatch(
            InsetsControlTarget insetsControlTarget) {
        boolean z;
        boolean z2;
        ArrayList arrayList = (ArrayList) this.mControlTargetProvidersMap.get(insetsControlTarget);
        if (insetsControlTarget.getWindow() != null) {
            z = PolicyControl.shouldApplyImmersiveStatus(insetsControlTarget.getWindow());
            z2 =
                    PolicyControl.shouldApplyImmersiveNavigation(
                            insetsControlTarget.getWindow(), false);
            if (z || z2) {
                arrayList = arrayList == null ? new ArrayList() : new ArrayList(arrayList);
                if (z) {
                    collectPolicyControlledTypes(arrayList, WindowInsets.Type.statusBars());
                }
                if (z2) {
                    collectPolicyControlledTypes(arrayList, WindowInsets.Type.navigationBars());
                }
            }
        } else {
            z = false;
            z2 = false;
        }
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        InsetsSourceControl[] insetsSourceControlArr = new InsetsSourceControl[size];
        for (int i = 0; i < size; i++) {
            InsetsSource insetsSource = ((InsetsSourceProvider) arrayList.get(i)).mSource;
            int type = insetsSource.getType();
            if ((z && type == WindowInsets.Type.statusBars())
                    || (z2 && type == WindowInsets.Type.navigationBars())) {
                insetsSourceControlArr[i] =
                        new InsetsSourceControl(
                                insetsSource.getId(),
                                insetsSource.getType(),
                                (SurfaceControl) null,
                                false,
                                new Point(),
                                Insets.NONE,
                                true);
            } else {
                insetsSourceControlArr[i] =
                        ((InsetsSourceProvider) arrayList.get(i)).getControl(insetsControlTarget);
            }
        }
        return insetsSourceControlArr;
    }

    public final ImeInsetsSourceProvider getImeSourceProvider() {
        return (ImeInsetsSourceProvider)
                getOrCreateSourceProvider(InsetsSource.ID_IME, WindowInsets.Type.ime());
    }

    public final InsetsSourceProvider getOrCreateSourceProvider(int i, int i2) {
        InsetsSourceProvider insetsSourceProvider = (InsetsSourceProvider) this.mProviders.get(i);
        if (insetsSourceProvider != null) {
            return insetsSourceProvider;
        }
        InsetsSource orCreateSource = this.mState.getOrCreateSource(i, i2);
        int i3 = InsetsSource.ID_IME;
        DisplayContent displayContent = this.mDisplayContent;
        InsetsSourceProvider imeInsetsSourceProvider =
                i == i3
                        ? new ImeInsetsSourceProvider(orCreateSource, this, displayContent)
                        : new InsetsSourceProvider(orCreateSource, this, displayContent);
        imeInsetsSourceProvider.setFlags((i2 & this.mForcedConsumingTypes) != 0 ? 4 : 0);
        this.mProviders.put(i, imeInsetsSourceProvider);
        return imeInsetsSourceProvider;
    }

    public final void notifyControlChanged(InsetsControlTarget insetsControlTarget) {
        this.mPendingControlChanged.add(insetsControlTarget);
        notifyPendingInsetsControlChanged();
        if (Flags.refactorInsetsController()) {
            notifyInsetsChanged();
            DisplayContent displayContent = this.mDisplayContent;
            displayContent.updateSystemGestureExclusion();
            displayContent.updateKeepClearAreas();
            displayContent.mDisplayPolicy.updateSystemBarAttributes();
        }
    }

    public final void notifyInsetsChanged() {
        DisplayContent displayContent = this.mDisplayContent;
        ActivityRecord activityRecord = displayContent.mFixedRotationLaunchingApp;
        if (activityRecord != null) {
            InsetsState insetsState =
                    activityRecord.isFixedRotationTransforming()
                            ? activityRecord
                                    .mFixedRotationTransformState
                                    .mDisplayFrames
                                    .mInsetsState
                            : null;
            if (insetsState != null) {
                InsetsState.traverse(
                        insetsState,
                        displayContent.mInsetsStateController.mState,
                        DisplayContent.COPY_SOURCE_VISIBILITY);
            }
        }
        displayContent.forAllWindows((Consumer) this.mDispatchInsetsChanged, true);
        DisplayContent.RemoteInsetsControlTarget remoteInsetsControlTarget =
                displayContent.mRemoteInsetsControlTarget;
        if (remoteInsetsControlTarget != null) {
            try {
                remoteInsetsControlTarget.mRemoteInsetsController.insetsChanged(
                        DisplayContent.this.mInsetsStateController.mState);
            } catch (RemoteException e) {
                Slog.w("WindowManager", "Failed to deliver inset state change", e);
            }
        }
        if (displayContent.mWmService.mAccessibilityController.hasCallbacks()) {
            InsetsControlTarget insetsControlTarget = displayContent.mImeControlTarget;
            boolean z =
                    insetsControlTarget != null
                            && insetsControlTarget.isRequestedVisible(WindowInsets.Type.ime());
            AccessibilityController accessibilityController =
                    displayContent.mWmService.mAccessibilityController;
            int i = displayContent.mDisplayId;
            AccessibilityController.AccessibilityControllerInternalImpl
                    accessibilityControllerInternalImpl =
                            accessibilityController.mAccessibilityTracing;
            if (accessibilityControllerInternalImpl.isTracingEnabled(2048L)) {
                accessibilityControllerInternalImpl.logTrace(
                        "AccessibilityController.updateImeVisibilityIfNeeded",
                        2048L,
                        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(
                                i, "displayId=", ";shown=", z));
            }
            if (accessibilityController.mIsImeVisibleArray.get(i, false) == z) {
                return;
            }
            accessibilityController.mIsImeVisibleArray.put(i, z);
            AccessibilityController.DisplayMagnifier displayMagnifier =
                    (AccessibilityController.DisplayMagnifier)
                            accessibilityController.mDisplayMagnifiers.get(i);
            if (displayMagnifier != null) {
                displayMagnifier.notifyImeWindowVisibilityChanged(z);
            }
        }
    }

    public final void notifyPendingInsetsControlChanged() {
        if (this.mPendingControlChanged.isEmpty()) {
            return;
        }
        this.mDisplayContent.mWmService.mAnimator.addAfterPrepareSurfacesRunnable(
                new Runnable() { // from class:
                                 // com.android.server.wm.InsetsStateController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        InsetsStateController insetsStateController = InsetsStateController.this;
                        for (int size = insetsStateController.mProviders.size() - 1;
                                size >= 0;
                                size--) {
                            ((InsetsSourceProvider) insetsStateController.mProviders.valueAt(size))
                                            .mIsLeashReadyForDispatching =
                                    true;
                        }
                        ArraySet arraySet = new ArraySet();
                        int i = insetsStateController.mDisplayContent.mDisplayId;
                        for (int size2 = insetsStateController.mPendingControlChanged.size() - 1;
                                size2 >= 0;
                                size2--) {
                            InsetsControlTarget insetsControlTarget =
                                    (InsetsControlTarget)
                                            insetsStateController.mPendingControlChanged.valueAt(
                                                    size2);
                            insetsControlTarget.notifyInsetsControlChanged(i);
                            if (insetsStateController.mControlTargetProvidersMap.containsKey(
                                    insetsControlTarget)) {
                                arraySet.add(insetsControlTarget);
                            }
                        }
                        insetsStateController.mPendingControlChanged.clear();
                        for (int size3 = arraySet.size() - 1; size3 >= 0; size3--) {
                            insetsStateController.onRequestedVisibleTypesChanged(
                                    (InsetsControlTarget) arraySet.valueAt(size3));
                        }
                        arraySet.clear();
                        insetsStateController
                                .getImeSourceProvider()
                                .checkAndStartShowImePostLayout();
                    }
                });
    }

    public final void onControlTargetChanged(
            InsetsControlTarget insetsControlTarget,
            InsetsSourceProvider insetsSourceProvider,
            boolean z) {
        InsetsControlTarget insetsControlTarget2 =
                z
                        ? (InsetsControlTarget)
                                this.mIdFakeControlTargetMap.get(
                                        insetsSourceProvider.mSource.getId())
                        : (InsetsControlTarget)
                                this.mIdControlTargetMap.get(insetsSourceProvider.mSource.getId());
        if (insetsControlTarget != insetsControlTarget2 && insetsSourceProvider.mControllable) {
            if (!z) {
                insetsSourceProvider.updateControlForTarget(insetsControlTarget, false);
                insetsControlTarget = insetsSourceProvider.mControlTarget;
                if (insetsControlTarget == insetsControlTarget2) {
                    return;
                }
            } else if (insetsControlTarget != insetsSourceProvider.mFakeControlTarget) {
                insetsSourceProvider.mFakeControlTarget = insetsControlTarget;
                Slog.d(
                        "InsetsSourceProvider",
                        "updateFakeControlTarget: fakeControl="
                                + insetsSourceProvider.mFakeControl
                                + ", fakeTarget="
                                + insetsControlTarget);
            }
            if (insetsControlTarget2 != null) {
                removeFromControlMaps(insetsControlTarget2, insetsSourceProvider, z);
                this.mPendingControlChanged.add(insetsControlTarget2);
            }
            if (insetsControlTarget != null) {
                ((ArrayList)
                                this.mControlTargetProvidersMap.computeIfAbsent(
                                        insetsControlTarget,
                                        new InsetsStateController$$ExternalSyntheticLambda3()))
                        .add(insetsSourceProvider);
                if (z) {
                    this.mIdFakeControlTargetMap.put(
                            insetsSourceProvider.mSource.getId(), insetsControlTarget);
                } else {
                    this.mIdControlTargetMap.put(
                            insetsSourceProvider.mSource.getId(), insetsControlTarget);
                }
                this.mPendingControlChanged.add(insetsControlTarget);
            }
        }
    }

    public final void onRequestedVisibleTypesChanged(InsetsControlTarget insetsControlTarget) {
        boolean z = false;
        for (int size = this.mProviders.size() - 1; size >= 0; size--) {
            z |=
                    ((InsetsSourceProvider) this.mProviders.valueAt(size))
                            .updateClientVisibility(insetsControlTarget);
        }
        if (Flags.refactorInsetsController() || !z) {
            return;
        }
        notifyInsetsChanged();
        DisplayContent displayContent = this.mDisplayContent;
        displayContent.updateSystemGestureExclusion();
        displayContent.mDisplayPolicy.updateSystemBarAttributes();
    }

    public final void removeFromControlMaps(
            InsetsControlTarget insetsControlTarget,
            InsetsSourceProvider insetsSourceProvider,
            boolean z) {
        ArrayList arrayList = (ArrayList) this.mControlTargetProvidersMap.get(insetsControlTarget);
        if (arrayList == null) {
            return;
        }
        arrayList.remove(insetsSourceProvider);
        if (arrayList.isEmpty()) {
            this.mControlTargetProvidersMap.remove(insetsControlTarget);
        }
        if (z) {
            this.mIdFakeControlTargetMap.remove(insetsSourceProvider.mSource.getId());
        } else {
            this.mIdControlTargetMap.remove(insetsSourceProvider.mSource.getId());
        }
    }

    public final void updateAboveInsetsState(boolean z) {
        InsetsState insetsState = new InsetsState();
        insetsState.set(
                this.mState,
                WindowInsets.Type.displayCutout()
                        | WindowInsets.Type.systemGestures()
                        | WindowInsets.Type.mandatorySystemGestures());
        SparseArray sparseArray = new SparseArray();
        ArraySet arraySet = new ArraySet();
        this.mDisplayContent.updateAboveInsetsState(insetsState, sparseArray, arraySet);
        if (z) {
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                this.mDispatchInsetsChanged.accept((WindowState) arraySet.valueAt(size));
            }
        }
    }
}
