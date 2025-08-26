package com.android.systemui.bixby2.interactor;

import android.app.ActivityThread;
import android.content.Context;
import android.os.Process;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$2$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.bixby2.controller.AppController;
import com.android.systemui.bixby2.controller.MWBixbyController;
import com.android.systemui.bixby2.util.PackageInfoBixby;
import com.android.systemui.bixby2.util.ParamsParser;
import com.google.gson.Gson;
import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.action.JSONStringAction;
import com.samsung.android.sdk.command.provider.CommandProvider;
import com.samsung.android.sdk.command.provider.ICommandActionCallback;
import com.samsung.android.sdk.command.template.CommandTemplate;
import com.samsung.android.sdk.command.template.UnformattedTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class AppControlActionInteractor implements ActionInteractor {
    private final String TAG = "AppControlActionInteractor";
    private final AppController mAppController;
    private final Context mContext;
    private Gson mGson;
    private final MWBixbyController mMWBixbyController;

    enum Action {
        close_application,
        close_all_application,
        close_foreground_application,
        launch_application,
        start_multiwindow,
        open_recentsapp,
        launch_mostrecent_application,
        close_multiple_application,
        app_resizable,
        startapp_splitposition,
        exchange_position_splitscreen,
        change_layout_splitscreen,
        replaceapp_splitscreen,
        maximize_app,
        check_orientation,
        check_splittype,
        check_splitstate,
        check_launchervisible,
        get_packageinsplit,
        close_all_application_except_currentapp,
        close_all_application_except_specificapp
    }

    public AppControlActionInteractor(Context context, AppController appController, MWBixbyController mWBixbyController) {
        Log.d("AppControlActionInteractor", "AppControlActionInteractor()");
        this.mContext = context;
        this.mAppController = appController;
        if (Process.myUserHandle().isSystem() && ActivityThread.currentProcessName().equals(ActivityThread.currentPackageName())) {
            this.mMWBixbyController = mWBixbyController;
            mWBixbyController.initSplitScreenController(null);
        } else {
            Log.w("AppControlActionInteractor", "init in non-system user.");
            this.mMWBixbyController = null;
        }
        this.mGson = new Gson();
    }

    private String getJsonString(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("result", str);
            if (str2 != null) {
                jSONObject.put("description", str2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    private int getResponseCode(String str) {
        return "success".equals(str) ? 1 : 2;
    }

    private boolean isCheckAction(String str) {
        return Action.check_orientation.toString().equals(str);
    }

    private boolean isJsonParameterAction(String str) {
        return Action.launch_application.toString().equals(str) || Action.close_application.toString().equals(str) || Action.start_multiwindow.toString().equals(str) || Action.launch_mostrecent_application.toString().equals(str) || Action.close_multiple_application.toString().equals(str) || Action.app_resizable.toString().equals(str) || Action.startapp_splitposition.toString().equals(str) || Action.exchange_position_splitscreen.toString().equals(str) || Action.change_layout_splitscreen.toString().equals(str) || Action.replaceapp_splitscreen.toString().equals(str) || Action.maximize_app.toString().equals(str) || Action.close_all_application_except_specificapp.toString().equals(str);
    }

    private boolean isLoadStatefulMultiWindowCommand(String str) {
        return Action.check_splitstate.toString().equals(str) || Action.get_packageinsplit.toString().equals(str) || Action.check_splittype.toString().equals(str) || Action.check_launchervisible.toString().equals(str) || Action.app_resizable.toString().equals(str);
    }

    private boolean isSimpleAction(String str) {
        return Action.close_all_application.toString().equals(str) || Action.close_foreground_application.toString().equals(str) || Action.open_recentsapp.toString().equals(str) || Action.close_all_application_except_currentapp.toString().equals(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$matchAction$0(String str, String str2) {
        return str2.equals(str);
    }

    private CommandTemplate loadStatefulMultiWindowCommand(String str, String str2) throws JSONException, NumberFormatException {
        CommandActionResponse commandActionResponseCheckSupportMultiWindow;
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("loadStatefulMultiWindowCommand  actionName=", str, "AppControlActionInteractor");
        if (this.mMWBixbyController == null) {
            return null;
        }
        if (Action.check_splitstate.toString().equals(str)) {
            commandActionResponseCheckSupportMultiWindow = this.mMWBixbyController.checkSplitState();
        } else if (Action.get_packageinsplit.toString().equals(str) && !TextUtils.isEmpty(str2)) {
            commandActionResponseCheckSupportMultiWindow = this.mMWBixbyController.getPackageNameInSplit(ParamsParser.getPackageInfoFromJson(str2));
        } else if (Action.check_splittype.toString().equals(str)) {
            commandActionResponseCheckSupportMultiWindow = this.mMWBixbyController.checkSupportMultiSplit();
        } else if (Action.check_launchervisible.toString().equals(str)) {
            commandActionResponseCheckSupportMultiWindow = this.mMWBixbyController.checkTopFullscreenHomeOrRecents();
        } else if (!Action.app_resizable.toString().equals(str) || TextUtils.isEmpty(str2)) {
            commandActionResponseCheckSupportMultiWindow = null;
        } else {
            commandActionResponseCheckSupportMultiWindow = this.mMWBixbyController.checkSupportMultiWindow(this.mContext, ParamsParser.getPackageInfoFromJson(str2));
        }
        if (commandActionResponseCheckSupportMultiWindow == null) {
            return null;
        }
        Log.d("AppControlActionInteractor", "responseMessage: " + commandActionResponseCheckSupportMultiWindow.responseMessage);
        return new UnformattedTemplate(commandActionResponseCheckSupportMultiWindow.responseMessage);
    }

    private boolean matchAction(final String str) {
        return Arrays.stream(Action.values()).map(new AppControlActionInteractor$$ExternalSyntheticLambda0()).anyMatch(new Predicate() { // from class: com.android.systemui.bixby2.interactor.AppControlActionInteractor$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AppControlActionInteractor.lambda$matchAction$0(str, (String) obj);
            }
        });
    }

    private boolean performMultiWindowCommandAction(String str, String str2, CommandActionResponse commandActionResponse) throws JSONException, NumberFormatException {
        String strMaximizeApp;
        int responseCode;
        if (this.mMWBixbyController == null) {
            return false;
        }
        PackageInfoBixby packageInfoFromJson = ParamsParser.getPackageInfoFromJson(str2);
        if (Action.start_multiwindow.toString().equals(str)) {
            strMaximizeApp = this.mMWBixbyController.startMultiWindow(this.mContext, packageInfoFromJson);
            responseCode = getResponseCode(strMaximizeApp);
        } else if (Action.app_resizable.toString().equals(str)) {
            CommandActionResponse commandActionResponseCheckSupportMultiWindow = this.mMWBixbyController.checkSupportMultiWindow(this.mContext, packageInfoFromJson);
            strMaximizeApp = commandActionResponseCheckSupportMultiWindow.responseMessage;
            responseCode = commandActionResponseCheckSupportMultiWindow.responseCode;
        } else if (Action.startapp_splitposition.toString().equals(str)) {
            strMaximizeApp = this.mMWBixbyController.startAppSplitPosition(packageInfoFromJson);
            responseCode = getResponseCode(strMaximizeApp);
        } else if (Action.exchange_position_splitscreen.toString().equals(str)) {
            strMaximizeApp = this.mMWBixbyController.exchangePositionOfSplitScreen(packageInfoFromJson);
            responseCode = getResponseCode(strMaximizeApp);
        } else if (Action.change_layout_splitscreen.toString().equals(str)) {
            strMaximizeApp = this.mMWBixbyController.changeLayoutOfSplitScreen(packageInfoFromJson);
            responseCode = getResponseCode(strMaximizeApp);
        } else if (Action.replaceapp_splitscreen.toString().equals(str)) {
            strMaximizeApp = this.mMWBixbyController.replaceAppOfSplitScreen(packageInfoFromJson);
            responseCode = getResponseCode(strMaximizeApp);
        } else if (Action.maximize_app.toString().equals(str)) {
            strMaximizeApp = this.mMWBixbyController.maximizeApp(this.mContext, packageInfoFromJson);
            responseCode = getResponseCode(strMaximizeApp);
        } else if (Action.check_splittype.toString().equals(str)) {
            CommandActionResponse commandActionResponseCheckSupportMultiSplit = this.mMWBixbyController.checkSupportMultiSplit();
            strMaximizeApp = commandActionResponseCheckSupportMultiSplit.responseMessage;
            responseCode = commandActionResponseCheckSupportMultiSplit.responseCode;
        } else if (Action.check_splitstate.toString().equals(str)) {
            CommandActionResponse commandActionResponseCheckSplitState = this.mMWBixbyController.checkSplitState();
            strMaximizeApp = commandActionResponseCheckSplitState.responseMessage;
            responseCode = commandActionResponseCheckSplitState.responseCode;
        } else if (Action.check_launchervisible.toString().equals(str)) {
            CommandActionResponse commandActionResponseCheckTopFullscreenHomeOrRecents = this.mMWBixbyController.checkTopFullscreenHomeOrRecents();
            strMaximizeApp = commandActionResponseCheckTopFullscreenHomeOrRecents.responseMessage;
            responseCode = commandActionResponseCheckTopFullscreenHomeOrRecents.responseCode;
        } else if (Action.get_packageinsplit.toString().equals(str)) {
            CommandActionResponse packageNameInSplit = this.mMWBixbyController.getPackageNameInSplit(packageInfoFromJson);
            strMaximizeApp = packageNameInSplit.responseMessage;
            responseCode = packageNameInSplit.responseCode;
        } else {
            strMaximizeApp = null;
            responseCode = 0;
        }
        if (responseCode == 0) {
            return false;
        }
        commandActionResponse.responseCode = responseCode;
        commandActionResponse.responseMessage = strMaximizeApp;
        return true;
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public List<String> getSupportingActions() {
        return (List) Arrays.stream(Action.values()).map(new AppControlActionInteractor$$ExternalSyntheticLambda0()).collect(Collectors.toList());
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public Command loadStatefulCommandInteractor(String str, Command command) throws JSONException, NumberFormatException {
        CommandTemplate unformattedTemplate;
        if (!matchAction(str)) {
            return null;
        }
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("loadStateful in AppContorlActionInteractor action=", str, "AppControlActionInteractor");
        if (isLoadStatefulMultiWindowCommand(str)) {
            unformattedTemplate = loadStatefulMultiWindowCommand(str, null);
        } else if (isCheckAction(str)) {
            CommandActionResponse commandActionResponse = new CommandActionResponse(1, this.mAppController.checkOrientation() ? ActionResults.RESULT_ORIENTATION_PORTRAIT : ActionResults.RESULT_ORIENTATION_LANDSCAPE);
            Log.d("AppControlActionInteractor", "responseMessage: " + commandActionResponse.responseMessage);
            unformattedTemplate = new UnformattedTemplate(commandActionResponse.responseMessage);
        } else if (isJsonParameterAction(str)) {
            try {
                unformattedTemplate = new UnformattedTemplate(new JSONObject().toString());
            } catch (Exception e) {
                Log.e("AppControlActionInteractor", "JSONException: " + e.toString());
            }
        } else {
            unformattedTemplate = isSimpleAction(str) ? CommandTemplate.NO_TEMPLATE : null;
        }
        if (unformattedTemplate == null) {
            return null;
        }
        Command.StatefulBuilder statefulBuilder = new Command.StatefulBuilder(command.mCommandId);
        statefulBuilder.mStatus = 1;
        statefulBuilder.mTemplate = unformattedTemplate;
        return statefulBuilder.build();
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00c9, code lost:
    
        if (r12.mAppController.removeSearchedTask(r12.mContext, r14) != false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0157, code lost:
    
        if (r12.mAppController.removeAllTasks(r12.mContext) != false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x016f, code lost:
    
        if (r12.mAppController.removeFocusedTask(r12.mContext) != false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0187, code lost:
    
        if (r12.mAppController.openRecentsApp(r12.mContext) != false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x01ce, code lost:
    
        if (r12.mAppController.removeNavigationApp(r12.mContext, r14) != false) goto L101;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00f5  */
    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void performCommandActionInteractor(String str, CommandAction commandAction, ICommandActionCallback iCommandActionCallback) {
        String str2;
        int i;
        String str3;
        int i2 = 1;
        String json = "success";
        CommandActionResponse commandActionResponse = new CommandActionResponse(1, "success");
        if (matchAction(str)) {
            StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("perform in AppContorlActionInteractor  actionName = ", str, ", actionType = ");
            sbM.append(commandAction.getActionType());
            Log.d("AppControlActionInteractor", sbM.toString());
            if (commandAction.getActionType() != 5) {
                str3 = "invalid_action";
                i = 2;
                str2 = null;
            } else {
                StringBuilder sb = new StringBuilder("newJSONStringValue = ");
                str2 = ((JSONStringAction) commandAction).mNewValue;
                ExifInterface$$ExternalSyntheticOutline0.m(sb, str2, "AppControlActionInteractor");
                i = 1;
                str3 = "success";
            }
            if (Action.launch_application.toString().equals(str)) {
                if (this.mAppController.checkInstalledApp(this.mContext, str2)) {
                    if (this.mAppController.launchApplication(this.mContext, str2)) {
                        if (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && this.mAppController.isFolderClosed()) {
                            String packageNameFromPdss = this.mAppController.getPackageNameFromPdss(str2);
                            boolean zCheckSettingsCoverLauncher = this.mAppController.checkSettingsCoverLauncher(this.mContext);
                            boolean zCheckIncludeCoverLauncher = this.mAppController.checkIncludeCoverLauncher(packageNameFromPdss);
                            boolean zCheckAvailableCoverLauncher = this.mAppController.checkAvailableCoverLauncher(packageNameFromPdss);
                            if (!zCheckIncludeCoverLauncher) {
                                json = ActionResults.RESULT_NOT_INCLUDE_COVERLAUNCHER;
                            } else if (!zCheckSettingsCoverLauncher) {
                                json = ActionResults.RESULT_SET_OFF_COVERLAUNCHER;
                            } else if (!zCheckAvailableCoverLauncher) {
                                json = ActionResults.RESULT_NOT_AVAILABLE_COVERLAUNCHER;
                            }
                        }
                    }
                    json = str3;
                    i2 = 2;
                } else {
                    i2 = 2;
                    json = ActionResults.RESULT_NOT_INSTALLED;
                }
            } else if (Action.close_application.toString().equals(str)) {
                if (!this.mAppController.checkInstalledApp(this.mContext, str2)) {
                }
            } else if (Action.close_all_application_except_currentapp.toString().equals(str)) {
                if (this.mAppController.isDexMode()) {
                    i2 = 2;
                    json = ActionResults.RESULT_DEX_MODE;
                } else if (!this.mAppController.removeAllTasks(this.mContext, true, null)) {
                    i2 = 2;
                    json = ActionResults.RESULT_NO_APP_CLOSE;
                }
            } else if (Action.close_all_application_except_specificapp.toString().equals(str)) {
                ArrayList<String> arrayList = new ArrayList<>();
                if (this.mAppController.checkInstalledApp(this.mContext, str2)) {
                    if (!this.mAppController.isDexMode()) {
                        if (!this.mAppController.checkRunningInRecents(this.mContext, str2, arrayList)) {
                            json = this.mGson.toJson(arrayList);
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("notRunningPackageList = ", json, "AppControlActionInteractor");
                            i2 = 2;
                        } else if (!this.mAppController.removeAllTasks(this.mContext, false, str2)) {
                        }
                    }
                }
            } else {
                if (!Action.close_all_application.toString().equals(str)) {
                    if (!Action.close_foreground_application.toString().equals(str)) {
                        if (!Action.open_recentsapp.toString().equals(str)) {
                            if (Action.launch_mostrecent_application.toString().equals(str)) {
                                if (!this.mAppController.isDexMode()) {
                                    if (this.mAppController.startNavigationApp(this.mContext, str2, commandActionResponse)) {
                                        i2 = commandActionResponse.responseCode;
                                        json = commandActionResponse.responseMessage;
                                    }
                                    json = str3;
                                }
                            } else if (Action.close_multiple_application.toString().equals(str)) {
                                if (this.mAppController.isDexMode()) {
                                }
                            } else if (Action.check_orientation.toString().equals(str)) {
                                json = this.mAppController.checkOrientation() ? ActionResults.RESULT_ORIENTATION_PORTRAIT : ActionResults.RESULT_ORIENTATION_LANDSCAPE;
                            } else if (performMultiWindowCommandAction(str, str2, commandActionResponse)) {
                                i2 = commandActionResponse.responseCode;
                                json = commandActionResponse.responseMessage;
                            } else {
                                json = str3;
                                i2 = i;
                            }
                        }
                    }
                }
                i2 = 2;
            }
            if (iCommandActionCallback != null) {
                KeyguardCarrierViewController$2$$ExternalSyntheticOutline0.m(i2, "responseCode = ", ", responseMessage = ", json, "AppControlActionInteractor");
                ((CommandProvider.AnonymousClass1) iCommandActionCallback).onActionFinished(i2, json);
            }
        }
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public Command loadStatefulCommandInteractor(String str, Command command, CommandAction commandAction) throws JSONException, NumberFormatException {
        String str2;
        CommandTemplate unformattedTemplate;
        String str3;
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("loadStateful in AppContorlActionInteractor(with CommandAction) action=", str, "AppControlActionInteractor");
        if (!Action.get_packageinsplit.toString().equals(str) && !Action.app_resizable.toString().equals(str)) {
            if (matchAction(str)) {
                if (isLoadStatefulMultiWindowCommand(str)) {
                    unformattedTemplate = loadStatefulMultiWindowCommand(str, null);
                } else if (isCheckAction(str)) {
                    if (this.mAppController.checkOrientation()) {
                        str3 = ActionResults.RESULT_ORIENTATION_PORTRAIT;
                    } else {
                        str3 = ActionResults.RESULT_ORIENTATION_LANDSCAPE;
                    }
                    CommandActionResponse commandActionResponse = new CommandActionResponse(1, str3);
                    Log.d("AppControlActionInteractor", "responseMessage: " + commandActionResponse.responseMessage);
                    unformattedTemplate = new UnformattedTemplate(commandActionResponse.responseMessage);
                } else if (isJsonParameterAction(str)) {
                    try {
                        unformattedTemplate = new UnformattedTemplate(new JSONObject().toString());
                    } catch (Exception e) {
                        Log.e("AppControlActionInteractor", "JSONException: " + e.toString());
                    }
                } else {
                    unformattedTemplate = isSimpleAction(str) ? CommandTemplate.NO_TEMPLATE : null;
                }
                if (unformattedTemplate == null) {
                    return null;
                }
                Command.StatefulBuilder statefulBuilder = new Command.StatefulBuilder(command.mCommandId);
                statefulBuilder.mStatus = 1;
                statefulBuilder.mTemplate = unformattedTemplate;
                return statefulBuilder.build();
            }
        } else {
            if (commandAction.getActionType() != 5) {
                str2 = null;
            } else {
                StringBuilder sb = new StringBuilder("newJSONStringValue = ");
                str2 = ((JSONStringAction) commandAction).mNewValue;
                ExifInterface$$ExternalSyntheticOutline0.m(sb, str2, "AppControlActionInteractor");
            }
            CommandTemplate commandTemplateLoadStatefulMultiWindowCommand = loadStatefulMultiWindowCommand(str, str2);
            if (commandTemplateLoadStatefulMultiWindowCommand != null) {
                Command.StatefulBuilder statefulBuilder2 = new Command.StatefulBuilder(command.mCommandId);
                statefulBuilder2.mStatus = 1;
                statefulBuilder2.mTemplate = commandTemplateLoadStatefulMultiWindowCommand;
                return statefulBuilder2.build();
            }
        }
        return null;
    }
}
