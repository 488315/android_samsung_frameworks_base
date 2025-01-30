package com.android.server.wm;

import android.graphics.Insets;
import android.graphics.Point;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.InsetsSource;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class InsetsStateController {
  public final DisplayContent mDisplayContent;
  public int mForcedConsumingTypes;
  public final InsetsState mLastState = new InsetsState();
  public final InsetsState mState = new InsetsState();
  public final SparseArray mProviders = new SparseArray();
  public final ArrayMap mControlTargetProvidersMap = new ArrayMap();
  public final SparseArray mIdControlTargetMap = new SparseArray();
  public final SparseArray mIdFakeControlTargetMap = new SparseArray();
  public final ArraySet mPendingControlChanged = new ArraySet();
  public final Consumer mDispatchInsetsChanged =
      new Consumer() { // from class:
                       // com.android.server.wm.InsetsStateController$$ExternalSyntheticLambda3
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
          InsetsStateController.lambda$new$0((WindowState) obj);
        }
      };
  public final InsetsControlTarget mEmptyImeControlTarget = new C29361();

  public static /* synthetic */ void lambda$new$0(WindowState windowState) {
    if (windowState.isReadyToDispatchInsetsState()) {
      windowState.notifyInsetsChanged();
    }
  }

  /* renamed from: com.android.server.wm.InsetsStateController$1 */
  public class C29361 implements InsetsControlTarget {
    public C29361() {}

    @Override // com.android.server.wm.InsetsControlTarget
    public void notifyInsetsControlChanged() {
      InsetsSourceControl[] controlsForDispatch =
          InsetsStateController.this.getControlsForDispatch(this);
      if (controlsForDispatch == null) {
        return;
      }
      for (InsetsSourceControl insetsSourceControl : controlsForDispatch) {
        if (insetsSourceControl.getType() == WindowInsets.Type.ime()) {
          InsetsStateController.this.mDisplayContent.mWmService.f1749mH.post(
              new Runnable() { // from class:
                               // com.android.server.wm.InsetsStateController$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  InsetsStateController.C29361.lambda$notifyInsetsControlChanged$0();
                }
              });
        }
      }
    }

    public static /* synthetic */ void lambda$notifyInsetsControlChanged$0() {
      InputMethodManagerInternal.get().removeImeSurface();
    }
  }

  public InsetsStateController(DisplayContent displayContent) {
    this.mDisplayContent = displayContent;
  }

  public InsetsState getRawInsetsState() {
    return this.mState;
  }

  public InsetsSourceControl[] getControlsForDispatch(InsetsControlTarget insetsControlTarget) {
    boolean z;
    boolean z2;
    ArrayList arrayList = (ArrayList) this.mControlTargetProvidersMap.get(insetsControlTarget);
    if (insetsControlTarget.getWindow() != null) {
      z = PolicyControl.shouldApplyImmersiveStatus(insetsControlTarget.getWindow());
      z2 = PolicyControl.shouldApplyImmersiveNavigation(insetsControlTarget.getWindow());
      if (z || z2) {
        if (arrayList == null) {
          arrayList = new ArrayList();
        } else {
          arrayList = new ArrayList(arrayList);
        }
        if (z) {
          collectPolicyControlledTypes(WindowInsets.Type.statusBars(), arrayList);
        }
        if (z2) {
          collectPolicyControlledTypes(WindowInsets.Type.navigationBars(), arrayList);
        }
      }
    } else {
      z = false;
      z2 = false;
    }
    if (!CoreRune.CARLIFE_NAVBAR || !this.mDisplayContent.isCarLifeDisplay()) {
      arrayList =
          this.mDisplayContent
              .getDisplayPolicy()
              .mExt
              .getTaskbarController()
              .adjustInsetsControlForTaskbar(arrayList);
    }
    if (arrayList == null) {
      return null;
    }
    int size = arrayList.size();
    InsetsSourceControl[] insetsSourceControlArr = new InsetsSourceControl[size];
    for (int i = 0; i < size; i++) {
      InsetsSource source = ((InsetsSourceProvider) arrayList.get(i)).getSource();
      if (isPolicyControlledType(source.getType(), z, z2)) {
        insetsSourceControlArr[i] =
            new InsetsSourceControl(
                source.getId(),
                source.getType(),
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

  public SparseArray getSourceProviders() {
    return this.mProviders;
  }

  public InsetsSourceProvider getOrCreateSourceProvider(int i, int i2) {
    InsetsSourceProvider insetsSourceProvider;
    InsetsSourceProvider insetsSourceProvider2 = (InsetsSourceProvider) this.mProviders.get(i);
    if (insetsSourceProvider2 != null) {
      return insetsSourceProvider2;
    }
    InsetsSource orCreateSource = this.mState.getOrCreateSource(i, i2);
    if (i == InsetsSource.ID_IME) {
      insetsSourceProvider =
          new ImeInsetsSourceProvider(orCreateSource, this, this.mDisplayContent);
    } else {
      insetsSourceProvider = new InsetsSourceProvider(orCreateSource, this, this.mDisplayContent);
    }
    insetsSourceProvider.setFlags((i2 & this.mForcedConsumingTypes) != 0 ? 4 : 0, 4);
    this.mProviders.put(i, insetsSourceProvider);
    return insetsSourceProvider;
  }

  public ImeInsetsSourceProvider getImeSourceProvider() {
    return (ImeInsetsSourceProvider)
        getOrCreateSourceProvider(InsetsSource.ID_IME, WindowInsets.Type.ime());
  }

  public void removeSourceProvider(int i) {
    if (i != InsetsSource.ID_IME) {
      this.mState.removeSource(i);
      this.mProviders.remove(i);
    }
  }

  public void setForcedConsumingTypes(int i) {
    if (this.mForcedConsumingTypes != i) {
      this.mForcedConsumingTypes = i;
      boolean z = false;
      for (int size = this.mProviders.size() - 1; size >= 0; size--) {
        InsetsSourceProvider insetsSourceProvider =
            (InsetsSourceProvider) this.mProviders.valueAt(size);
        z |=
            insetsSourceProvider.setFlags(
                (insetsSourceProvider.getSource().getType() & i) != 0 ? 4 : 0, 4);
      }
      if (z) {
        notifyInsetsChanged();
      }
    }
  }

  public void onPostLayout() {
    if (containsDeferTarget()) {
      return;
    }
    Trace.traceBegin(32L, "ISC.onPostLayout");
    for (int size = this.mProviders.size() - 1; size >= 0; size--) {
      ((InsetsSourceProvider) this.mProviders.valueAt(size)).onPostLayout();
    }
    if (!this.mLastState.equals(this.mState)) {
      this.mLastState.set(this.mState, true);
      notifyInsetsChanged();
    }
    Trace.traceEnd(32L);
  }

  public void updateAboveInsetsState(boolean z) {
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

  public void onDisplayFramesUpdated(boolean z) {
    final ArrayList arrayList = new ArrayList();
    this.mDisplayContent.forAllWindows(
        new Consumer() { // from class:
                         // com.android.server.wm.InsetsStateController$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            InsetsStateController.this.lambda$onDisplayFramesUpdated$1(
                arrayList, (WindowState) obj);
          }
        },
        true);
    if (z) {
      for (int size = arrayList.size() - 1; size >= 0; size--) {
        this.mDispatchInsetsChanged.accept((WindowState) arrayList.get(size));
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onDisplayFramesUpdated$1(
      ArrayList arrayList, WindowState windowState) {
    windowState.mAboveInsetsState.set(this.mState, WindowInsets.Type.displayCutout());
    arrayList.add(windowState);
  }

  public void onInsetsModified(InsetsControlTarget insetsControlTarget) {
    boolean z = false;
    for (int size = this.mProviders.size() - 1; size >= 0; size--) {
      z |=
          ((InsetsSourceProvider) this.mProviders.valueAt(size))
              .updateClientVisibility(insetsControlTarget);
    }
    if (z) {
      notifyInsetsChanged();
      this.mDisplayContent.updateSystemGestureExclusion();
      this.mDisplayContent.updateKeepClearAreas();
      this.mDisplayContent.getDisplayPolicy().updateSystemBarAttributes();
    }
  }

  public int getFakeControllingTypes(InsetsControlTarget insetsControlTarget) {
    int i = 0;
    for (int size = this.mProviders.size() - 1; size >= 0; size--) {
      InsetsSourceProvider insetsSourceProvider =
          (InsetsSourceProvider) this.mProviders.valueAt(size);
      if (insetsControlTarget == insetsSourceProvider.getFakeControlTarget()) {
        i |= insetsSourceProvider.getSource().getType();
      }
    }
    return i;
  }

  public void onImeControlTargetChanged(InsetsControlTarget insetsControlTarget) {
    if (insetsControlTarget == null) {
      insetsControlTarget = this.mEmptyImeControlTarget;
    }
    onControlTargetChanged(getImeSourceProvider(), insetsControlTarget, false);
    if (ProtoLogCache.WM_DEBUG_IME_enabled) {
      ProtoLogImpl.d(
          ProtoLogGroup.WM_DEBUG_IME,
          1658605381,
          0,
          (String) null,
          new Object[] {
            String.valueOf(insetsControlTarget != null ? insetsControlTarget.getWindow() : "null")
          });
    }
    notifyPendingInsetsControlChanged();
  }

  public void onBarControlTargetChanged(
      InsetsControlTarget insetsControlTarget,
      InsetsControlTarget insetsControlTarget2,
      InsetsControlTarget insetsControlTarget3,
      InsetsControlTarget insetsControlTarget4) {
    for (int size = this.mProviders.size() - 1; size >= 0; size--) {
      InsetsSourceProvider insetsSourceProvider =
          (InsetsSourceProvider) this.mProviders.valueAt(size);
      int type = insetsSourceProvider.getSource().getType();
      if (type == WindowInsets.Type.statusBars()) {
        onControlTargetChanged(insetsSourceProvider, insetsControlTarget, false);
        onControlTargetChanged(insetsSourceProvider, insetsControlTarget2, true);
      } else if (type == WindowInsets.Type.navigationBars()) {
        onControlTargetChanged(insetsSourceProvider, insetsControlTarget3, false);
        onControlTargetChanged(insetsSourceProvider, insetsControlTarget4, true);
      }
    }
    notifyPendingInsetsControlChanged();
  }

  public void notifyControlRevoked(
      InsetsControlTarget insetsControlTarget, InsetsSourceProvider insetsSourceProvider) {
    removeFromControlMaps(insetsControlTarget, insetsSourceProvider, false);
  }

  public final void onControlTargetChanged(
      InsetsSourceProvider insetsSourceProvider,
      InsetsControlTarget insetsControlTarget,
      boolean z) {
    InsetsControlTarget insetsControlTarget2;
    if (z) {
      insetsControlTarget2 =
          (InsetsControlTarget)
              this.mIdFakeControlTargetMap.get(insetsSourceProvider.getSource().getId());
    } else {
      insetsControlTarget2 =
          (InsetsControlTarget)
              this.mIdControlTargetMap.get(insetsSourceProvider.getSource().getId());
    }
    if (insetsControlTarget != insetsControlTarget2 && insetsSourceProvider.isControllable()) {
      if (z) {
        insetsSourceProvider.updateFakeControlTarget(insetsControlTarget);
      } else {
        insetsSourceProvider.updateControlForTarget(insetsControlTarget, false);
        insetsControlTarget = insetsSourceProvider.getControlTarget();
        if (insetsControlTarget == insetsControlTarget2) {
          return;
        }
      }
      if (insetsControlTarget2 != null) {
        removeFromControlMaps(insetsControlTarget2, insetsSourceProvider, z);
        this.mPendingControlChanged.add(insetsControlTarget2);
      }
      if (insetsControlTarget != null) {
        addToControlMaps(insetsControlTarget, insetsSourceProvider, z);
        this.mPendingControlChanged.add(insetsControlTarget);
      }
    }
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
      this.mIdFakeControlTargetMap.remove(insetsSourceProvider.getSource().getId());
    } else {
      this.mIdControlTargetMap.remove(insetsSourceProvider.getSource().getId());
    }
  }

  public final void addToControlMaps(
      InsetsControlTarget insetsControlTarget,
      InsetsSourceProvider insetsSourceProvider,
      boolean z) {
    ((ArrayList)
            this.mControlTargetProvidersMap.computeIfAbsent(
                insetsControlTarget,
                new Function() { // from class:
                                 // com.android.server.wm.InsetsStateController$$ExternalSyntheticLambda1
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    ArrayList lambda$addToControlMaps$2;
                    lambda$addToControlMaps$2 =
                        InsetsStateController.lambda$addToControlMaps$2((InsetsControlTarget) obj);
                    return lambda$addToControlMaps$2;
                  }
                }))
        .add(insetsSourceProvider);
    if (z) {
      this.mIdFakeControlTargetMap.put(
          insetsSourceProvider.getSource().getId(), insetsControlTarget);
    } else {
      this.mIdControlTargetMap.put(insetsSourceProvider.getSource().getId(), insetsControlTarget);
    }
  }

  public static /* synthetic */ ArrayList lambda$addToControlMaps$2(
      InsetsControlTarget insetsControlTarget) {
    return new ArrayList();
  }

  public void notifyControlChanged(InsetsControlTarget insetsControlTarget) {
    this.mPendingControlChanged.add(insetsControlTarget);
    notifyPendingInsetsControlChanged();
  }

  public final void notifyPendingInsetsControlChanged() {
    if (this.mPendingControlChanged.isEmpty()) {
      return;
    }
    this.mDisplayContent.mWmService.mAnimator.addAfterPrepareSurfacesRunnable(
        new Runnable() { // from class:
                         // com.android.server.wm.InsetsStateController$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            InsetsStateController.this.lambda$notifyPendingInsetsControlChanged$3();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notifyPendingInsetsControlChanged$3() {
    for (int size = this.mProviders.size() - 1; size >= 0; size--) {
      ((InsetsSourceProvider) this.mProviders.valueAt(size)).onSurfaceTransactionApplied();
    }
    ArraySet arraySet = new ArraySet();
    for (int size2 = this.mPendingControlChanged.size() - 1; size2 >= 0; size2--) {
      InsetsControlTarget insetsControlTarget =
          (InsetsControlTarget) this.mPendingControlChanged.valueAt(size2);
      insetsControlTarget.notifyInsetsControlChanged();
      if (this.mControlTargetProvidersMap.containsKey(insetsControlTarget)) {
        arraySet.add(insetsControlTarget);
      }
    }
    this.mPendingControlChanged.clear();
    for (int size3 = arraySet.size() - 1; size3 >= 0; size3--) {
      onInsetsModified((InsetsControlTarget) arraySet.valueAt(size3));
    }
    arraySet.clear();
  }

  public void notifyInsetsChanged() {
    this.mDisplayContent.notifyInsetsChanged(this.mDispatchInsetsChanged);
  }

  public final boolean containsDeferTarget() {
    for (int size = this.mProviders.size() - 1; size >= 0; size--) {
      if (((InsetsSourceProvider) this.mProviders.valueAt(size)).shouldDeferUpdate()) {
        return true;
      }
    }
    return false;
  }

  public void dump(String str, PrintWriter printWriter) {
    printWriter.println(str + "WindowInsetsStateController");
    String str2 = str + "  ";
    this.mState.dump(str2, printWriter);
    printWriter.println(str2 + "Control map:");
    for (int size = this.mControlTargetProvidersMap.size() + (-1); size >= 0; size--) {
      InsetsControlTarget insetsControlTarget =
          (InsetsControlTarget) this.mControlTargetProvidersMap.keyAt(size);
      printWriter.print(str2 + "  ");
      printWriter.print(insetsControlTarget);
      printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
      ArrayList arrayList = (ArrayList) this.mControlTargetProvidersMap.valueAt(size);
      for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
        InsetsSourceProvider insetsSourceProvider = (InsetsSourceProvider) arrayList.get(size2);
        if (insetsSourceProvider != null) {
          printWriter.print(str2 + "    ");
          if (insetsControlTarget == insetsSourceProvider.getFakeControlTarget()) {
            printWriter.print("(fake) ");
          }
          printWriter.println(insetsSourceProvider.getControl(insetsControlTarget));
        }
      }
    }
    if (this.mControlTargetProvidersMap.isEmpty()) {
      printWriter.print(str2 + "  none");
    }
    printWriter.println(str2 + "InsetsSourceProviders:");
    for (int size3 = this.mProviders.size() + (-1); size3 >= 0; size3 += -1) {
      ((InsetsSourceProvider) this.mProviders.valueAt(size3)).dump(printWriter, str2 + "  ");
    }
    if (this.mForcedConsumingTypes != 0) {
      printWriter.println(
          str2 + "mForcedConsumingTypes=" + WindowInsets.Type.toString(this.mForcedConsumingTypes));
    }
  }

  public void dumpDebug(ProtoOutputStream protoOutputStream, int i) {
    for (int size = this.mProviders.size() - 1; size >= 0; size--) {
      InsetsSourceProvider insetsSourceProvider =
          (InsetsSourceProvider) this.mProviders.valueAt(size);
      insetsSourceProvider.dumpDebug(
          protoOutputStream,
          insetsSourceProvider.getSource().getType() == WindowInsets.Type.ime()
              ? 1146756268063L
              : 2246267895843L,
          i);
    }
  }

  public final void collectPolicyControlledTypes(int i, ArrayList arrayList) {
    SparseArray sourceProviders = getSourceProviders();
    for (int size = sourceProviders.size() - 1; size >= 0; size--) {
      InsetsSourceProvider insetsSourceProvider =
          (InsetsSourceProvider) sourceProviders.valueAt(size);
      if (insetsSourceProvider.getSource().getType() == i) {
        arrayList.add(insetsSourceProvider);
      }
    }
  }

  public final boolean isPolicyControlledType(int i, boolean z, boolean z2) {
    if (z && i == WindowInsets.Type.statusBars()) {
      return true;
    }
    return z2 && i == WindowInsets.Type.navigationBars();
  }
}
