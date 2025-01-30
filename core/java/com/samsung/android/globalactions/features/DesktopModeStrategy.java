package com.samsung.android.globalactions.features;

import android.content.Context;
import com.samsung.android.globalactions.presentation.strategies.DefaultActionsCreationStrategy;
import com.samsung.android.globalactions.presentation.strategies.DisposingStrategy;
import com.samsung.android.globalactions.presentation.strategies.InitializationStrategy;
import com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.DesktopModeManagerWrapper;
import com.samsung.android.globalactions.util.SystemConditions;

/* loaded from: classes5.dex */
public class DesktopModeStrategy
    implements InitializationStrategy, DisposingStrategy, DefaultActionsCreationStrategy {
  private final ConditionChecker mConditionChecker;
  Context mContext;
  DesktopModeManagerWrapper mDesktopModeManager;
  ExtendableGlobalActionsView mView;

  public DesktopModeStrategy(
      Context context,
      ExtendableGlobalActionsView view,
      DesktopModeManagerWrapper desktopModeManagerWrapper,
      ConditionChecker conditionChecker) {
    this.mContext = context;
    this.mView = view;
    this.mDesktopModeManager = desktopModeManagerWrapper;
    this.mConditionChecker = conditionChecker;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onInitialize$0() {
    this.mView.dismiss();
  }

  @Override // com.samsung.android.globalactions.presentation.strategies.InitializationStrategy
  public void onInitialize(boolean isKeyguardShowing) {
    this.mDesktopModeManager.registerModeChangedListener(
        new Runnable() { // from class:
                         // com.samsung.android.globalactions.features.DesktopModeStrategy$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            DesktopModeStrategy.this.lambda$onInitialize$0();
          }
        });
  }

  @Override // com.samsung.android.globalactions.presentation.strategies.DisposingStrategy
  public void onDispose() {
    this.mDesktopModeManager.dispose();
  }

  @Override // com.samsung.android.globalactions.presentation.strategies.DefaultActionsCreationStrategy
  public boolean onCreateEmergencyAction() {
    return !this.mConditionChecker.isEnabled(SystemConditions.IS_DESKTOP_MODE_DUAL_VIEW);
  }
}
