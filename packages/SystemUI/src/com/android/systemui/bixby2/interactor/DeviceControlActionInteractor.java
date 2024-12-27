package com.android.systemui.bixby2.interactor;

import android.content.Context;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.systemui.bixby2.controller.DeviceController;
import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.template.CommandTemplate;
import com.samsung.android.sdk.command.template.SliderTemplate;
import com.samsung.android.sdk.command.template.ToggleTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class DeviceControlActionInteractor implements ActionInteractor {
    private final String TAG = "DeviceControlActionInteractor";
    private final Context mContext;
    private final DeviceController mDeviceController;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    enum Action {
        power_off,
        reboot,
        turnoff_screen,
        set_flashlight,
        set_flashlight_level,
        set_autorotate,
        set_landscapemode,
        set_portraitmode
    }

    public DeviceControlActionInteractor(Context context, DeviceController deviceController) {
        this.mContext = context;
        this.mDeviceController = deviceController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$matchAction$0(String str, String str2) {
        return str2.equals(str);
    }

    private boolean matchAction(final String str) {
        return Arrays.stream(Action.values()).map(new DeviceControlActionInteractor$$ExternalSyntheticLambda0()).anyMatch(new Predicate() { // from class: com.android.systemui.bixby2.interactor.DeviceControlActionInteractor$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$matchAction$0;
                lambda$matchAction$0 = DeviceControlActionInteractor.lambda$matchAction$0(str, (String) obj);
                return lambda$matchAction$0;
            }
        });
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public List<String> getSupportingActions() {
        return (List) Arrays.stream(Action.values()).map(new DeviceControlActionInteractor$$ExternalSyntheticLambda0()).collect(Collectors.toList());
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public Command loadStatefulCommandInteractor(String str, Command command) {
        if (!matchAction(str)) {
            return null;
        }
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("loadStateful in DeviceContorlActionInteractor action=", str, "DeviceControlActionInteractor");
        if (Action.power_off.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder.mStatus = 1;
            statefulBuilder.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder.build();
        }
        if (Action.reboot.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder2 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder2.mStatus = 1;
            statefulBuilder2.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder2.build();
        }
        if (Action.turnoff_screen.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder3 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder3.mStatus = 1;
            statefulBuilder3.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder3.build();
        }
        if (Action.set_flashlight.toString().equals(str)) {
            boolean hasFlashLight = this.mDeviceController.hasFlashLight();
            boolean isFlashLightEnabled = this.mDeviceController.isFlashLightEnabled();
            Command.StatefulBuilder statefulBuilder4 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder4.mStatus = hasFlashLight ? 1 : 2;
            statefulBuilder4.mLaunchIntent = this.mDeviceController.getFlashLightIntent();
            statefulBuilder4.mTemplate = new ToggleTemplate(isFlashLightEnabled);
            return statefulBuilder4.build();
        }
        if (Action.set_flashlight_level.toString().equals(str)) {
            boolean hasFlashLight2 = this.mDeviceController.hasFlashLight();
            Command.StatefulBuilder statefulBuilder5 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder5.mStatus = hasFlashLight2 ? 1 : 2;
            statefulBuilder5.mTemplate = new SliderTemplate(1.0f, 5.0f, this.mDeviceController.getFlashLightLevel(), 1.0f, null);
            return statefulBuilder5.build();
        }
        if (Action.set_autorotate.toString().equals(str)) {
            boolean isAutoRotationEnabled = this.mDeviceController.isAutoRotationEnabled();
            Command.StatefulBuilder statefulBuilder6 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder6.mStatus = 1;
            statefulBuilder6.mTemplate = new ToggleTemplate(isAutoRotationEnabled);
            return statefulBuilder6.build();
        }
        if (Action.set_landscapemode.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder7 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder7.mStatus = 1;
            statefulBuilder7.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder7.build();
        }
        if (!Action.set_portraitmode.toString().equals(str)) {
            return null;
        }
        Command.StatefulBuilder statefulBuilder8 = new Command.StatefulBuilder(command.mCommandId);
        statefulBuilder8.mStatus = 1;
        statefulBuilder8.mTemplate = CommandTemplate.NO_TEMPLATE;
        return statefulBuilder8.build();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0098  */
    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void performCommandActionInteractor(java.lang.String r4, com.samsung.android.sdk.command.action.CommandAction r5, com.samsung.android.sdk.command.provider.ICommandActionCallback r6) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bixby2.interactor.DeviceControlActionInteractor.performCommandActionInteractor(java.lang.String, com.samsung.android.sdk.command.action.CommandAction, com.samsung.android.sdk.command.provider.ICommandActionCallback):void");
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public Command loadStatefulCommandInteractor(String str, Command command, CommandAction commandAction) {
        if (!matchAction(str)) {
            return null;
        }
        Log.d("DeviceControlActionInteractor", "loadStateful in DeviceContorlActionInteractor(with CommandAction) action=" + str + ", cmdAction = " + commandAction);
        if (Action.power_off.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder.mStatus = 1;
            statefulBuilder.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder.build();
        }
        if (Action.reboot.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder2 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder2.mStatus = 1;
            statefulBuilder2.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder2.build();
        }
        if (Action.turnoff_screen.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder3 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder3.mStatus = 1;
            statefulBuilder3.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder3.build();
        }
        if (Action.set_flashlight.toString().equals(str)) {
            boolean hasFlashLight = this.mDeviceController.hasFlashLight();
            boolean isFlashLightEnabled = this.mDeviceController.isFlashLightEnabled();
            Command.StatefulBuilder statefulBuilder4 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder4.mStatus = hasFlashLight ? 1 : 2;
            statefulBuilder4.mLaunchIntent = this.mDeviceController.getFlashLightIntent();
            statefulBuilder4.mTemplate = new ToggleTemplate(isFlashLightEnabled);
            return statefulBuilder4.build();
        }
        if (Action.set_flashlight_level.toString().equals(str)) {
            boolean hasFlashLight2 = this.mDeviceController.hasFlashLight();
            Command.StatefulBuilder statefulBuilder5 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder5.mStatus = hasFlashLight2 ? 1 : 2;
            statefulBuilder5.mTemplate = new SliderTemplate(1.0f, 5.0f, this.mDeviceController.getFlashLightLevel(), 1.0f, null);
            return statefulBuilder5.build();
        }
        if (Action.set_autorotate.toString().equals(str)) {
            boolean isAutoRotationEnabled = this.mDeviceController.isAutoRotationEnabled();
            Command.StatefulBuilder statefulBuilder6 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder6.mStatus = 1;
            statefulBuilder6.mTemplate = new ToggleTemplate(isAutoRotationEnabled);
            return statefulBuilder6.build();
        }
        if (Action.set_landscapemode.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder7 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder7.mStatus = 1;
            statefulBuilder7.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder7.build();
        }
        if (!Action.set_portraitmode.toString().equals(str)) {
            return null;
        }
        Command.StatefulBuilder statefulBuilder8 = new Command.StatefulBuilder(command.mCommandId);
        statefulBuilder8.mStatus = 1;
        statefulBuilder8.mTemplate = CommandTemplate.NO_TEMPLATE;
        return statefulBuilder8.build();
    }
}
