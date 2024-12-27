package com.samsung.android.globalactions.presentation.features;

import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.strategies.ActionInteractionStrategy;
import com.samsung.android.globalactions.presentation.strategies.ActionUpdateStrategy;
import com.samsung.android.globalactions.presentation.strategies.ActionsCreationStrategy;
import com.samsung.android.globalactions.presentation.strategies.DefaultActionsCreationStrategy;
import com.samsung.android.globalactions.presentation.strategies.DisposingStrategy;
import com.samsung.android.globalactions.presentation.strategies.InitializationStrategy;
import com.samsung.android.globalactions.presentation.strategies.OnKeyListenerStrategy;
import com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy;
import com.samsung.android.globalactions.presentation.strategies.SoftwareUpdateStrategy;
import com.samsung.android.globalactions.presentation.strategies.ViewInflateStrategy;
import com.samsung.android.globalactions.presentation.strategies.WindowDecorationStrategy;
import com.samsung.android.globalactions.presentation.strategies.WindowManagerFunctionStrategy;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface FeatureFactory {
    List<ActionInteractionStrategy> createActionInteractionStrategies(String str);

    List<ActionsCreationStrategy> createActionsCreationStrategies(
            SamsungGlobalActions samsungGlobalActions);

    List<DefaultActionsCreationStrategy> createDefaultActionsCreationStrategy(
            SamsungGlobalActions samsungGlobalActions, String str);

    List<DisposingStrategy> createDisposingStrategies(SamsungGlobalActions samsungGlobalActions);

    List<InitializationStrategy> createInitializationStrategies(
            SamsungGlobalActions samsungGlobalActions);

    List<OnKeyListenerStrategy> createOnKeyListenerStrategy(
            SamsungGlobalActions samsungGlobalActions);

    List<SecureConfirmStrategy> createSecureConfirmStrategy(
            SamsungGlobalActions samsungGlobalActions, String str);

    List<SoftwareUpdateStrategy> createSoftwareUpdateStrategy(
            SamsungGlobalActions samsungGlobalActions, String str);

    List<ViewInflateStrategy> createViewInflateStrategy();

    List<WindowDecorationStrategy> createWindowDecorationStrategies(
            SamsungGlobalActions samsungGlobalActions);

    List<WindowManagerFunctionStrategy> createWindowManagerFunctionStrategy(
            SamsungGlobalActions samsungGlobalActions, String str);

    default List<ActionUpdateStrategy> createActionUpdateStrategies() {
        return new ArrayList();
    }
}
