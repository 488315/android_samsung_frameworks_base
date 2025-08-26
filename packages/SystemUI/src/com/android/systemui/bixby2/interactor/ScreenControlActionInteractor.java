package com.android.systemui.bixby2.interactor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$2$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.controller.ScreenController;
import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.action.BooleanAction;
import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.action.FloatAction;
import com.samsung.android.sdk.command.action.JSONStringAction;
import com.samsung.android.sdk.command.provider.CommandProvider;
import com.samsung.android.sdk.command.provider.ICommandActionCallback;
import com.samsung.android.sdk.command.template.CommandTemplate;
import com.samsung.android.sdk.command.template.SliderTemplate;
import com.samsung.android.sdk.command.template.ToggleTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.json.JSONException;

/* loaded from: classes.dex */
public class ScreenControlActionInteractor implements ActionInteractor {
    private static final String PACKAGE_NAME = "packageName";
    private static final String TAG = "ScreenControlActionInteractor";
    private final Context mContext;
    private final ScreenController mScreenController;

    enum Action {
        goto_homescreen,
        back,
        capture_screen,
        share_screenshot,
        share_screenshot_uri,
        set_brightness,
        scroll_up_down,
        auto_brightness_cover,
        close_panelscreen
    }

    public ScreenControlActionInteractor(Context context, ScreenController screenController) {
        this.mContext = context;
        this.mScreenController = screenController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$matchAction$0(String str, String str2) {
        return str2.equals(str);
    }

    private boolean matchAction(final String str) {
        return Arrays.stream(Action.values()).map(new ScreenControlActionInteractor$$ExternalSyntheticLambda0()).anyMatch(new Predicate() { // from class: com.android.systemui.bixby2.interactor.ScreenControlActionInteractor$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ScreenControlActionInteractor.lambda$matchAction$0(str, (String) obj);
            }
        });
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public List<String> getSupportingActions() {
        return (List) Arrays.stream(Action.values()).map(new ScreenControlActionInteractor$$ExternalSyntheticLambda0()).collect(Collectors.toList());
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public Command loadStatefulCommandInteractor(String str, Command command) {
        if (!matchAction(str)) {
            return null;
        }
        Log.d(TAG, "matched in ScreenContorlActionInteractor");
        if (Action.goto_homescreen.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder.mStatus = 1;
            statefulBuilder.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder.build();
        }
        if (Action.back.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder2 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder2.mStatus = 1;
            statefulBuilder2.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder2.build();
        }
        if (Action.capture_screen.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder3 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder3.mStatus = 1;
            statefulBuilder3.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder3.build();
        }
        if (Action.share_screenshot.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder4 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder4.mStatus = 1;
            statefulBuilder4.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder4.build();
        }
        if (Action.share_screenshot_uri.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder5 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder5.mStatus = 1;
            statefulBuilder5.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder5.build();
        }
        if (Action.set_brightness.toString().equals(str)) {
            int[] brightnessBarInfo = this.mScreenController.getBrightnessBarInfo(this.mContext);
            Command.StatefulBuilder statefulBuilder6 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder6.mStatus = 1;
            statefulBuilder6.mTemplate = new SliderTemplate(0.0f, 100.0f, brightnessBarInfo[0], brightnessBarInfo[1], null);
            return statefulBuilder6.build();
        }
        if (Action.scroll_up_down.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder7 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder7.mStatus = 1;
            statefulBuilder7.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder7.build();
        }
        if (!Action.auto_brightness_cover.toString().equals(str)) {
            if (!Action.close_panelscreen.toString().equals(str)) {
                return null;
            }
            Command.StatefulBuilder statefulBuilder8 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder8.mStatus = 1;
            statefulBuilder8.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder8.build();
        }
        boolean zIsAutoBrightnessCoverEnabled = this.mScreenController.isAutoBrightnessCoverEnabled(this.mContext);
        Log.d(TAG, "isAutoBrightnessCoverEnabled = " + zIsAutoBrightnessCoverEnabled);
        Command.StatefulBuilder statefulBuilder9 = new Command.StatefulBuilder(command.mCommandId);
        statefulBuilder9.mStatus = 1;
        statefulBuilder9.mTemplate = new ToggleTemplate(zIsAutoBrightnessCoverEnabled);
        return statefulBuilder9.build();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x001d A[PHI: r4
      0x001d: PHI (r4v1 java.lang.String) = (r4v0 java.lang.String), (r4v0 java.lang.String), (r4v0 java.lang.String), (r4v3 java.lang.String) binds: [B:18:0x0058, B:15:0x002c, B:13:0x001f, B:11:0x0019] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void performCommandActionInteractor(String str, CommandAction commandAction, ICommandActionCallback iCommandActionCallback) throws JSONException {
        int i;
        int actionType = commandAction.getActionType();
        int i2 = 1;
        String str2 = "success";
        String str3 = null;
        if (actionType != 1) {
            i = 2;
            if (actionType != 2) {
                if (actionType == 4) {
                } else if (actionType != 5) {
                    str2 = "invalid_action";
                } else {
                    str3 = ((JSONStringAction) commandAction).mNewValue;
                }
                i = 1;
            } else if (Action.set_brightness.toString().equals(str)) {
                CommandActionResponse brightness = this.mScreenController.setBrightness(this.mContext, (int) ((FloatAction) commandAction).mNewValue);
                i = brightness.responseCode;
                str2 = brightness.responseMessage;
            } else {
                i = 1;
            }
        } else {
            StringBuilder sb = new StringBuilder("bNewState = ");
            boolean z = ((BooleanAction) commandAction).mNewState;
            ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, TAG);
            if (Action.auto_brightness_cover.toString().equals(str)) {
                CommandActionResponse autoBrightnessCover = this.mScreenController.setAutoBrightnessCover(this.mContext, z);
                i = autoBrightnessCover.responseCode;
                str2 = autoBrightnessCover.responseMessage;
            }
        }
        if (matchAction(str)) {
            if (Action.goto_homescreen.toString().equals(str)) {
                this.mScreenController.goToHomeScreen(this.mContext);
            } else if (Action.back.toString().equals(str)) {
                this.mScreenController.pressBackKey(this.mContext);
            } else if (Action.capture_screen.toString().equals(str)) {
                CommandActionResponse commandActionResponseTakeScreenShot = this.mScreenController.takeScreenShot(this.mContext);
                i2 = commandActionResponseTakeScreenShot.responseCode;
                str2 = commandActionResponseTakeScreenShot.responseMessage;
            } else if (Action.share_screenshot.toString().equals(str)) {
                Bundle bundleM = KeyguardSecPatternView$$ExternalSyntheticOutline0.m("packageName", str3);
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("share_screenshot    newJSONStringValue = ", str3, TAG);
                CommandActionResponse commandActionResponseShareScreenShot = this.mScreenController.shareScreenShot(this.mContext, bundleM);
                i2 = commandActionResponseShareScreenShot.responseCode;
                str2 = commandActionResponseShareScreenShot.responseMessage;
            } else if (Action.share_screenshot_uri.toString().equals(str)) {
                CommandActionResponse commandActionResponseTakeScreenShotUri = this.mScreenController.takeScreenShotUri(this.mContext);
                i2 = commandActionResponseTakeScreenShotUri.responseCode;
                str2 = commandActionResponseTakeScreenShotUri.responseMessage;
            } else if (Action.scroll_up_down.toString().equals(str)) {
                this.mScreenController.screenScroll(this.mContext, str3);
            } else if (Action.close_panelscreen.toString().equals(str)) {
                this.mScreenController.closePanelScreen(this.mContext);
            } else {
                i2 = i;
            }
            if (iCommandActionCallback != null) {
                KeyguardCarrierViewController$2$$ExternalSyntheticOutline0.m(i2, "responseCode = ", ", responseMessage = ", str2, TAG);
                ((CommandProvider.AnonymousClass1) iCommandActionCallback).onActionFinished(i2, str2);
            }
        }
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public Command loadStatefulCommandInteractor(String str, Command command, CommandAction commandAction) {
        if (!matchAction(str)) {
            return null;
        }
        Log.d(TAG, "loadStateful in ScreenContorlActionInteractor(with CommandAction) action=" + str + ", cmdAction = " + commandAction);
        if (Action.goto_homescreen.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder.mStatus = 1;
            statefulBuilder.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder.build();
        }
        if (Action.back.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder2 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder2.mStatus = 1;
            statefulBuilder2.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder2.build();
        }
        if (Action.capture_screen.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder3 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder3.mStatus = 1;
            statefulBuilder3.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder3.build();
        }
        if (Action.share_screenshot.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder4 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder4.mStatus = 1;
            statefulBuilder4.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder4.build();
        }
        if (Action.share_screenshot_uri.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder5 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder5.mStatus = 1;
            statefulBuilder5.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder5.build();
        }
        if (Action.set_brightness.toString().equals(str)) {
            int[] brightnessBarInfo = this.mScreenController.getBrightnessBarInfo(this.mContext);
            Command.StatefulBuilder statefulBuilder6 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder6.mStatus = 1;
            statefulBuilder6.mTemplate = new SliderTemplate(0.0f, 100.0f, brightnessBarInfo[0], brightnessBarInfo[1], null);
            return statefulBuilder6.build();
        }
        if (Action.scroll_up_down.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder7 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder7.mStatus = 1;
            statefulBuilder7.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder7.build();
        }
        if (Action.auto_brightness_cover.toString().equals(str)) {
            boolean zIsAutoBrightnessCoverEnabled = this.mScreenController.isAutoBrightnessCoverEnabled(this.mContext);
            Log.d(TAG, "isAutoBrightnessCoverEnabled = " + zIsAutoBrightnessCoverEnabled);
            Command.StatefulBuilder statefulBuilder8 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder8.mStatus = 1;
            statefulBuilder8.mTemplate = new ToggleTemplate(zIsAutoBrightnessCoverEnabled);
            return statefulBuilder8.build();
        }
        if (!Action.close_panelscreen.toString().equals(str)) {
            return null;
        }
        Command.StatefulBuilder statefulBuilder9 = new Command.StatefulBuilder(command.mCommandId);
        statefulBuilder9.mStatus = 1;
        statefulBuilder9.mTemplate = CommandTemplate.NO_TEMPLATE;
        return statefulBuilder9.build();
    }
}
