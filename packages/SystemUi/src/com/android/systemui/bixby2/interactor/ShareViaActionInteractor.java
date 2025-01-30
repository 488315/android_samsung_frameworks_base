package com.android.systemui.bixby2.interactor;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.action.JSONStringAction;
import com.samsung.android.sdk.command.provider.ICommandActionCallback;
import com.samsung.android.sdk.command.template.UnformattedTemplate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ShareViaActionInteractor implements ActionInteractor {
    static final String PACKAGE_NAME_GOOGLE_SMS = "com.google.android.apps.messaging";
    static final String PACKAGE_NAME_SAMSUNG_SMS = "com.samsung.android.messaging";
    private final String TAG = "ShareViaActionInteractor";
    Context mContext;
    String mJsonString;
    PackageManager mPm;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Action {
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

    /* JADX WARN: Code restructure failed: missing block: B:41:0x011b, code lost:
    
        if (com.android.systemui.bixby2.interactor.ShareViaActionInteractor.PACKAGE_NAME_GOOGLE_SMS.equals(r14.packageName) != false) goto L47;
     */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01d3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String handleFindAppInfoAction(String str) {
        String str2;
        String str3;
        String str4;
        String str5;
        Iterator<ResolveInfo> it;
        String str6;
        String str7 = PACKAGE_NAME_SAMSUNG_SMS;
        HashMap hashMap = new HashMap();
        try {
            new JsonParser();
            if (JsonParser.parseString(str) instanceof JsonArray) {
                JSONArray jSONArray = new JSONArray(String.valueOf(str));
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    Iterator<String> keys = jSONObject.keys();
                    while (keys.hasNext()) {
                        String obj = keys.next().toString();
                        hashMap.put(obj, jSONObject.getString(obj));
                    }
                }
            } else {
                JSONObject jSONObject2 = new JSONObject(str);
                Iterator<String> keys2 = jSONObject2.keys();
                while (keys2.hasNext()) {
                    String obj2 = keys2.next().toString();
                    hashMap.put(obj2, jSONObject2.getString(obj2));
                }
            }
        } catch (JSONException e) {
            Log.e("ShareViaActionInteractor", "JSONException: " + e.toString());
        }
        String str8 = (String) hashMap.get("intentType");
        String str9 = (String) hashMap.get("intentAction");
        Log.i("ShareViaActionInteractor", "intentAction: " + str9 + ", intentType: " + str8);
        if (str9 == null || str8 == null) {
            return "";
        }
        Intent intent = new Intent(str9);
        intent.setType(str8);
        this.mPm = this.mContext.getPackageManager();
        List<ResolveInfo> queryIntentActivities = this.mContext.getPackageManager().queryIntentActivities(intent, 65664);
        try {
            str2 = Telephony.Sms.getDefaultSmsPackage(this.mContext);
        } catch (Exception e2) {
            e2.toString();
            str2 = null;
        }
        try {
            JSONArray jSONArray2 = new JSONArray();
            if (queryIntentActivities != null) {
                Iterator<ResolveInfo> it2 = queryIntentActivities.iterator();
                str3 = "";
                while (it2.hasNext()) {
                    try {
                        ActivityInfo activityInfo = it2.next().activityInfo;
                        if (activityInfo != null && activityInfo.exported) {
                            if (!TextUtils.isEmpty(str2)) {
                                if (!str7.equals(str2)) {
                                    if (PACKAGE_NAME_GOOGLE_SMS.equals(str2) && str7.equals(activityInfo.packageName)) {
                                        str4 = str2;
                                        str5 = str7;
                                        it = it2;
                                    }
                                }
                                str2 = str4;
                                str7 = str5;
                                it2 = it;
                            }
                            String str10 = activityInfo.packageName;
                            String str11 = activityInfo.name;
                            String charSequence = activityInfo.loadLabel(this.mPm).toString();
                            str4 = str2;
                            str5 = str7;
                            String charSequence2 = activityInfo.applicationInfo.loadLabel(this.mPm).toString();
                            it = it2;
                            List<ResolveInfo> queryIntentActivities2 = this.mPm.queryIntentActivities(new Intent("android.intent.action.MAIN").setPackage(activityInfo.packageName).addCategory("android.intent.category.LAUNCHER"), 131072);
                            if (queryIntentActivities2.size() > 0) {
                                str6 = queryIntentActivities2.get(0).activityInfo.loadLabel(this.mPm).toString();
                                if (!str6.isEmpty()) {
                                    JSONObject jSONObject3 = new JSONObject();
                                    jSONObject3.put("packageName", str10);
                                    jSONObject3.put("activityName", str11);
                                    jSONObject3.put("activityLabel", charSequence);
                                    jSONObject3.put("appLabel", str6);
                                    jSONObject3.put("iconUrl", (Object) null);
                                    jSONArray2.put(jSONObject3);
                                    str3 = jSONArray2.toString();
                                    str2 = str4;
                                    str7 = str5;
                                    it2 = it;
                                }
                            }
                            str6 = charSequence2;
                            JSONObject jSONObject32 = new JSONObject();
                            jSONObject32.put("packageName", str10);
                            jSONObject32.put("activityName", str11);
                            jSONObject32.put("activityLabel", charSequence);
                            jSONObject32.put("appLabel", str6);
                            jSONObject32.put("iconUrl", (Object) null);
                            jSONArray2.put(jSONObject32);
                            str3 = jSONArray2.toString();
                            str2 = str4;
                            str7 = str5;
                            it2 = it;
                        }
                        str4 = str2;
                        str5 = str7;
                        it = it2;
                        Objects.toString(activityInfo);
                        str2 = str4;
                        str7 = str5;
                        it2 = it;
                    } catch (JSONException e3) {
                        e = e3;
                        e.printStackTrace();
                        if ("".equals(str3)) {
                        }
                    }
                }
            } else {
                str3 = "";
            }
        } catch (JSONException e4) {
            e = e4;
            str3 = "";
        }
        if ("".equals(str3)) {
            return str3;
        }
        try {
            JSONArray jSONArray3 = new JSONArray();
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("packageName", "");
            jSONObject4.put("activityName", "");
            jSONObject4.put("activityLabel", "");
            jSONObject4.put("appLabel", "");
            jSONObject4.put("iconUrl", (Object) null);
            jSONArray3.put(jSONObject4);
            return jSONArray3.toString();
        } catch (JSONException e5) {
            e5.printStackTrace();
            return str3;
        }
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
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m70m("load actionName = ", str, "ShareViaActionInteractor");
        return null;
    }
}
