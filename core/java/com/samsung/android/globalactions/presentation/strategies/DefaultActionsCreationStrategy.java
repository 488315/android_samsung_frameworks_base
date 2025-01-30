package com.samsung.android.globalactions.presentation.strategies;

/* loaded from: classes5.dex */
public interface DefaultActionsCreationStrategy {
  default boolean onCreateEmergencyAction() {
    return false;
  }

  default boolean onCreateEmergencyCallAction() {
    return false;
  }

  default boolean onCreateMedicalInfoAction() {
    return false;
  }

  default boolean onCreateBugReportAction() {
    return false;
  }
}
