package com.android.systemui.bixby2.interactor;

import android.app.ActivityThread;
import android.content.Context;
import android.os.Process;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
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
import com.samsung.android.sdk.command.template.CommandTemplate;
import com.samsung.android.sdk.command.template.UnformattedTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class AppControlActionInteractor implements ActionInteractor {
    private final String TAG = "AppControlActionInteractor";
    private final AppController mAppController;
    private final Context mContext;
    private Gson mGson;
    private final MWBixbyController mMWBixbyController;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    private String getJsonString(String str, String str2) {
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

    private CommandTemplate loadStatefulMultiWindowCommand(String str, String str2) {
        CommandActionResponse commandActionResponse;
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("loadStatefulMultiWindowCommand  actionName=", str, "AppControlActionInteractor");
        if (this.mMWBixbyController == null) {
            return null;
        }
        if (Action.check_splitstate.toString().equals(str)) {
            commandActionResponse = this.mMWBixbyController.checkSplitState();
        } else if (Action.get_packageinsplit.toString().equals(str) && !TextUtils.isEmpty(str2)) {
            commandActionResponse = this.mMWBixbyController.getPackageNameInSplit(ParamsParser.getPackageInfoFromJson(str2));
        } else if (Action.check_splittype.toString().equals(str)) {
            commandActionResponse = this.mMWBixbyController.checkSupportMultiSplit();
        } else if (Action.check_launchervisible.toString().equals(str)) {
            commandActionResponse = this.mMWBixbyController.checkTopFullscreenHomeOrRecents();
        } else if (!Action.app_resizable.toString().equals(str) || TextUtils.isEmpty(str2)) {
            commandActionResponse = null;
        } else {
            commandActionResponse = this.mMWBixbyController.checkSupportMultiWindow(this.mContext, ParamsParser.getPackageInfoFromJson(str2));
        }
        if (commandActionResponse == null) {
            return null;
        }
        Log.d("AppControlActionInteractor", "responseMessage: " + commandActionResponse.responseMessage);
        return new UnformattedTemplate(commandActionResponse.responseMessage);
    }

    private boolean matchAction(final String str) {
        return Arrays.stream(Action.values()).map(new AppControlActionInteractor$$ExternalSyntheticLambda0()).anyMatch(new Predicate() { // from class: com.android.systemui.bixby2.interactor.AppControlActionInteractor$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$matchAction$0;
                lambda$matchAction$0 = AppControlActionInteractor.lambda$matchAction$0(str, (String) obj);
                return lambda$matchAction$0;
            }
        });
    }

    private boolean performMultiWindowCommandAction(String str, String str2, CommandActionResponse commandActionResponse) {
        String str3;
        int i;
        if (this.mMWBixbyController == null) {
            return false;
        }
        PackageInfoBixby packageInfoFromJson = ParamsParser.getPackageInfoFromJson(str2);
        if (Action.start_multiwindow.toString().equals(str)) {
            str3 = this.mMWBixbyController.startMultiWindow(this.mContext, packageInfoFromJson);
            i = getResponseCode(str3);
        } else if (Action.app_resizable.toString().equals(str)) {
            CommandActionResponse checkSupportMultiWindow = this.mMWBixbyController.checkSupportMultiWindow(this.mContext, packageInfoFromJson);
            str3 = checkSupportMultiWindow.responseMessage;
            i = checkSupportMultiWindow.responseCode;
        } else if (Action.startapp_splitposition.toString().equals(str)) {
            str3 = this.mMWBixbyController.startAppSplitPosition(packageInfoFromJson);
            i = getResponseCode(str3);
        } else if (Action.exchange_position_splitscreen.toString().equals(str)) {
            str3 = this.mMWBixbyController.exchangePositionOfSplitScreen(packageInfoFromJson);
            i = getResponseCode(str3);
        } else if (Action.change_layout_splitscreen.toString().equals(str)) {
            str3 = this.mMWBixbyController.changeLayoutOfSplitScreen(packageInfoFromJson);
            i = getResponseCode(str3);
        } else if (Action.replaceapp_splitscreen.toString().equals(str)) {
            str3 = this.mMWBixbyController.replaceAppOfSplitScreen(packageInfoFromJson);
            i = getResponseCode(str3);
        } else if (Action.maximize_app.toString().equals(str)) {
            str3 = this.mMWBixbyController.maximizeApp(this.mContext, packageInfoFromJson);
            i = getResponseCode(str3);
        } else if (Action.check_splittype.toString().equals(str)) {
            CommandActionResponse checkSupportMultiSplit = this.mMWBixbyController.checkSupportMultiSplit();
            str3 = checkSupportMultiSplit.responseMessage;
            i = checkSupportMultiSplit.responseCode;
        } else if (Action.check_splitstate.toString().equals(str)) {
            CommandActionResponse checkSplitState = this.mMWBixbyController.checkSplitState();
            str3 = checkSplitState.responseMessage;
            i = checkSplitState.responseCode;
        } else if (Action.check_launchervisible.toString().equals(str)) {
            CommandActionResponse checkTopFullscreenHomeOrRecents = this.mMWBixbyController.checkTopFullscreenHomeOrRecents();
            str3 = checkTopFullscreenHomeOrRecents.responseMessage;
            i = checkTopFullscreenHomeOrRecents.responseCode;
        } else if (Action.get_packageinsplit.toString().equals(str)) {
            CommandActionResponse packageNameInSplit = this.mMWBixbyController.getPackageNameInSplit(packageInfoFromJson);
            str3 = packageNameInSplit.responseMessage;
            i = packageNameInSplit.responseCode;
        } else {
            str3 = null;
            i = 0;
        }
        if (i == 0) {
            return false;
        }
        commandActionResponse.responseCode = i;
        commandActionResponse.responseMessage = str3;
        return true;
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public List<String> getSupportingActions() {
        return (List) Arrays.stream(Action.values()).map(new AppControlActionInteractor$$ExternalSyntheticLambda0()).collect(Collectors.toList());
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public Command loadStatefulCommandInteractor(String str, Command command) {
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
            if (isSimpleAction(str)) {
                unformattedTemplate = CommandTemplate.NO_TEMPLATE;
            }
            unformattedTemplate = null;
        }
        if (unformattedTemplate == null) {
            return null;
        }
        Command.StatefulBuilder statefulBuilder = new Command.StatefulBuilder(command.mCommandId);
        statefulBuilder.mStatus = 1;
        statefulBuilder.mTemplate = unformattedTemplate;
        return statefulBuilder.build();
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00c8, code lost:
    
        if (r12.mAppController.removeSearchedTask(r12.mContext, r14) != false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00f0, code lost:
    
        if (r12.mAppController.removeAllTasks(r12.mContext, true, null) != false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00f4, code lost:
    
        r1 = 2;
        r2 = com.android.systemui.bixby2.actionresult.ActionResults.RESULT_NO_APP_CLOSE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0131, code lost:
    
        if (r12.mAppController.removeAllTasks(r12.mContext, false, r14) != false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0156, code lost:
    
        if (r12.mAppController.removeAllTasks(r12.mContext) != false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x016e, code lost:
    
        if (r12.mAppController.removeFocusedTask(r12.mContext) != false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0186, code lost:
    
        if (r12.mAppController.openRecentsApp(r12.mContext) != false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01cd, code lost:
    
        if (r12.mAppController.removeNavigationApp(r12.mContext, r14) != false) goto L101;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x01f9  */
    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void performCommandActionInteractor(java.lang.String r13, com.samsung.android.sdk.command.action.CommandAction r14, com.samsung.android.sdk.command.provider.ICommandActionCallback r15) {
        /*
            Method dump skipped, instructions count: 536
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bixby2.interactor.AppControlActionInteractor.performCommandActionInteractor(java.lang.String, com.samsung.android.sdk.command.action.CommandAction, com.samsung.android.sdk.command.provider.ICommandActionCallback):void");
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public Command loadStatefulCommandInteractor(String str, Command command, CommandAction commandAction) {
        String str2;
        CommandTemplate unformattedTemplate;
        String str3;
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("loadStateful in AppContorlActionInteractor(with CommandAction) action=", str, "AppControlActionInteractor");
        if (!Action.get_packageinsplit.toString().equals(str) && !Action.app_resizable.toString().equals(str)) {
            if (!matchAction(str)) {
                return null;
            }
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
                if (isSimpleAction(str)) {
                    unformattedTemplate = CommandTemplate.NO_TEMPLATE;
                }
                unformattedTemplate = null;
            }
            if (unformattedTemplate == null) {
                return null;
            }
            Command.StatefulBuilder statefulBuilder = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder.mStatus = 1;
            statefulBuilder.mTemplate = unformattedTemplate;
            return statefulBuilder.build();
        }
        if (commandAction.getActionType() != 5) {
            str2 = null;
        } else {
            StringBuilder sb = new StringBuilder("newJSONStringValue = ");
            str2 = ((JSONStringAction) commandAction).mNewValue;
            ExifInterface$$ExternalSyntheticOutline0.m(sb, str2, "AppControlActionInteractor");
        }
        CommandTemplate loadStatefulMultiWindowCommand = loadStatefulMultiWindowCommand(str, str2);
        if (loadStatefulMultiWindowCommand == null) {
            return null;
        }
        Command.StatefulBuilder statefulBuilder2 = new Command.StatefulBuilder(command.mCommandId);
        statefulBuilder2.mStatus = 1;
        statefulBuilder2.mTemplate = loadStatefulMultiWindowCommand;
        return statefulBuilder2.build();
    }
}
