package com.android.systemui.bixby2.interactor;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.action.JSONStringAction;
import com.samsung.android.sdk.command.provider.ICommandActionCallback;
import com.samsung.android.sdk.command.template.UnformattedTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class ShareViaActionInteractor implements ActionInteractor {
    static final String PACKAGE_NAME_GOOGLE_SMS = "com.google.android.apps.messaging";
    static final String PACKAGE_NAME_SAMSUNG_SMS = "com.samsung.android.messaging";
    private final String TAG = "ShareViaActionInteractor";
    Context mContext;
    String mJsonString;
    PackageManager mPm;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    enum Action {
        find_appinfo
    }

    public ShareViaActionInteractor(Context context) {
        this.mContext = context;
    }

    private String getResultResponse(String str, String str2) {
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

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0122, code lost:
    
        if (com.android.systemui.bixby2.interactor.ShareViaActionInteractor.PACKAGE_NAME_GOOGLE_SMS.equals(r14.packageName) != false) goto L48;
     */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01d5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String handleFindAppInfoAction(java.lang.String r21) {
        /*
            Method dump skipped, instructions count: 509
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bixby2.interactor.ShareViaActionInteractor.handleFindAppInfoAction(java.lang.String):java.lang.String");
    }

    private boolean matchAction(String str) {
        return Action.find_appinfo.toString().equals(str);
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public List<String> getSupportingActions() {
        return (List) Arrays.stream(Action.values()).map(new ShareViaActionInteractor$$ExternalSyntheticLambda0()).collect(Collectors.toList());
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public Command loadStatefulCommandInteractor(String str, Command command, CommandAction commandAction) {
        String str2;
        if (commandAction.getActionType() != 5) {
            str2 = "";
        } else {
            StringBuilder sb = new StringBuilder("CommandAction = ");
            str2 = ((JSONStringAction) commandAction).mNewValue;
            sb.append(str2);
            sb.append(", actionName = ");
            sb.append(str);
            Log.i("ShareViaActionInteractor", sb.toString());
        }
        if (!matchAction(str)) {
            return null;
        }
        String handleFindAppInfoAction = handleFindAppInfoAction(str2);
        CommandActionResponse commandActionResponse = "".equals(handleFindAppInfoAction) ? new CommandActionResponse(2, getResultResponse(ActionResults.RESULT_FAIL, "app list is null")) : new CommandActionResponse(1, getResultResponse("success", handleFindAppInfoAction));
        Log.i("ShareViaActionInteractor", "responseMessage: " + handleFindAppInfoAction);
        UnformattedTemplate unformattedTemplate = new UnformattedTemplate(commandActionResponse.responseMessage);
        Command.StatefulBuilder statefulBuilder = new Command.StatefulBuilder(command.mCommandId);
        statefulBuilder.mStatus = 1;
        statefulBuilder.mTemplate = unformattedTemplate;
        return statefulBuilder.build();
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public void performCommandActionInteractor(String str, CommandAction commandAction, ICommandActionCallback iCommandActionCallback) {
        if (matchAction(str)) {
            Log.i("ShareViaActionInteractor", "perform commandAction = " + commandAction + ", = " + commandAction.getActionType());
        }
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public Command loadStatefulCommandInteractor(String str, Command command) {
        if (!matchAction(str)) {
            return null;
        }
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("load actionName = ", str, "ShareViaActionInteractor");
        return null;
    }
}
