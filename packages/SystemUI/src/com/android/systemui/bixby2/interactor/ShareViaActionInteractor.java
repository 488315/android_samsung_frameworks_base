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

/* loaded from: classes.dex */
public class ShareViaActionInteractor implements ActionInteractor {
    static final String PACKAGE_NAME_GOOGLE_SMS = "com.google.android.apps.messaging";
    static final String PACKAGE_NAME_SAMSUNG_SMS = "com.samsung.android.messaging";
    private final String TAG = "ShareViaActionInteractor";
    Context mContext;
    String mJsonString;
    PackageManager mPm;

    enum Action {
        find_appinfo
    }

    public ShareViaActionInteractor(Context context) {
        this.mContext = context;
    }

    private String getResultResponse(String str, String str2) throws JSONException {
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

    /* JADX WARN: Removed duplicated region for block: B:56:0x0141 A[Catch: JSONException -> 0x012f, TryCatch #3 {JSONException -> 0x012f, blocks: (B:34:0x00f3, B:36:0x00f9, B:38:0x0103, B:41:0x0110, B:43:0x0116, B:46:0x011e, B:51:0x0132, B:53:0x0138, B:56:0x0141, B:58:0x0184, B:63:0x01a1, B:65:0x01c5), top: B:84:0x00f3 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0184 A[Catch: JSONException -> 0x012f, TryCatch #3 {JSONException -> 0x012f, blocks: (B:34:0x00f3, B:36:0x00f9, B:38:0x0103, B:41:0x0110, B:43:0x0116, B:46:0x011e, B:51:0x0132, B:53:0x0138, B:56:0x0141, B:58:0x0184, B:63:0x01a1, B:65:0x01c5), top: B:84:0x00f3 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x019e  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01d7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:95:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String handleFindAppInfoAction(String str) throws JSONException {
        String defaultSmsPackage;
        String string;
        String str2;
        String str3;
        Iterator<ResolveInfo> it;
        List<ResolveInfo> listQueryIntentActivities;
        String string2;
        String str4 = PACKAGE_NAME_SAMSUNG_SMS;
        HashMap map = new HashMap();
        try {
            new JsonParser();
            if (JsonParser.parseString(str).isJsonArray()) {
                JSONArray jSONArray = new JSONArray(String.valueOf(str));
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    Iterator<String> itKeys = jSONObject.keys();
                    while (itKeys.hasNext()) {
                        String string3 = itKeys.next().toString();
                        map.put(string3, jSONObject.getString(string3));
                    }
                }
            } else {
                JSONObject jSONObject2 = new JSONObject(str);
                Iterator<String> itKeys2 = jSONObject2.keys();
                while (itKeys2.hasNext()) {
                    String string4 = itKeys2.next().toString();
                    map.put(string4, jSONObject2.getString(string4));
                }
            }
        } catch (JSONException e) {
            Log.e("ShareViaActionInteractor", "JSONException: " + e.toString());
        }
        String str5 = (String) map.get("intentType");
        String str6 = (String) map.get("intentAction");
        Log.i("ShareViaActionInteractor", "intentAction: " + str6 + ", intentType: " + str5);
        if (str6 == null || str5 == null) {
            return "";
        }
        Intent intent = new Intent(str6);
        intent.setType(str5);
        this.mPm = this.mContext.getPackageManager();
        List<ResolveInfo> listQueryIntentActivities2 = this.mContext.getPackageManager().queryIntentActivities(intent, 65664);
        try {
            defaultSmsPackage = Telephony.Sms.getDefaultSmsPackage(this.mContext);
        } catch (Exception e2) {
            e2.toString();
            defaultSmsPackage = null;
        }
        try {
            JSONArray jSONArray2 = new JSONArray();
            if (listQueryIntentActivities2 != null) {
                Iterator<ResolveInfo> it2 = listQueryIntentActivities2.iterator();
                string = "";
                while (it2.hasNext()) {
                    try {
                        ActivityInfo activityInfo = it2.next().activityInfo;
                        if (activityInfo == null || !activityInfo.exported) {
                            str2 = defaultSmsPackage;
                            str3 = str4;
                            it = it2;
                            Objects.toString(activityInfo);
                        } else if (TextUtils.isEmpty(defaultSmsPackage)) {
                            String str7 = activityInfo.packageName;
                            String str8 = activityInfo.name;
                            String string5 = activityInfo.loadLabel(this.mPm).toString();
                            str2 = defaultSmsPackage;
                            str3 = str4;
                            String string6 = activityInfo.applicationInfo.loadLabel(this.mPm).toString();
                            it = it2;
                            listQueryIntentActivities = this.mPm.queryIntentActivities(new Intent("android.intent.action.MAIN").setPackage(activityInfo.packageName).addCategory("android.intent.category.LAUNCHER"), 131072);
                            if (listQueryIntentActivities.size() <= 0) {
                                string2 = listQueryIntentActivities.get(0).activityInfo.loadLabel(this.mPm).toString();
                                if (!string2.isEmpty()) {
                                }
                                JSONObject jSONObject3 = new JSONObject();
                                jSONObject3.put("packageName", str7);
                                jSONObject3.put("activityName", str8);
                                jSONObject3.put("activityLabel", string5);
                                jSONObject3.put("appLabel", string2);
                                jSONObject3.put("iconUrl", (Object) null);
                                jSONArray2.put(jSONObject3);
                                string = jSONArray2.toString();
                            }
                            string2 = string6;
                            JSONObject jSONObject32 = new JSONObject();
                            jSONObject32.put("packageName", str7);
                            jSONObject32.put("activityName", str8);
                            jSONObject32.put("activityLabel", string5);
                            jSONObject32.put("appLabel", string2);
                            jSONObject32.put("iconUrl", (Object) null);
                            jSONArray2.put(jSONObject32);
                            string = jSONArray2.toString();
                        } else if (!str4.equals(defaultSmsPackage)) {
                            if (PACKAGE_NAME_GOOGLE_SMS.equals(defaultSmsPackage) && str4.equals(activityInfo.packageName)) {
                                str2 = defaultSmsPackage;
                                str3 = str4;
                                it = it2;
                            }
                            String str72 = activityInfo.packageName;
                            String str82 = activityInfo.name;
                            String string52 = activityInfo.loadLabel(this.mPm).toString();
                            str2 = defaultSmsPackage;
                            str3 = str4;
                            String string62 = activityInfo.applicationInfo.loadLabel(this.mPm).toString();
                            it = it2;
                            listQueryIntentActivities = this.mPm.queryIntentActivities(new Intent("android.intent.action.MAIN").setPackage(activityInfo.packageName).addCategory("android.intent.category.LAUNCHER"), 131072);
                            if (listQueryIntentActivities.size() <= 0) {
                            }
                            string2 = string62;
                            JSONObject jSONObject322 = new JSONObject();
                            jSONObject322.put("packageName", str72);
                            jSONObject322.put("activityName", str82);
                            jSONObject322.put("activityLabel", string52);
                            jSONObject322.put("appLabel", string2);
                            jSONObject322.put("iconUrl", (Object) null);
                            jSONArray2.put(jSONObject322);
                            string = jSONArray2.toString();
                        } else if (PACKAGE_NAME_GOOGLE_SMS.equals(activityInfo.packageName)) {
                            str2 = defaultSmsPackage;
                            str3 = str4;
                            it = it2;
                        }
                        defaultSmsPackage = str2;
                        str4 = str3;
                        it2 = it;
                    } catch (JSONException e3) {
                        e = e3;
                        e.printStackTrace();
                        if ("".equals(string)) {
                        }
                    }
                }
            } else {
                string = "";
            }
        } catch (JSONException e4) {
            e = e4;
            string = "";
        }
        if ("".equals(string)) {
            return string;
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
            return string;
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
    public Command loadStatefulCommandInteractor(String str, Command command, CommandAction commandAction) throws JSONException {
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
        String strHandleFindAppInfoAction = handleFindAppInfoAction(str2);
        CommandActionResponse commandActionResponse = "".equals(strHandleFindAppInfoAction) ? new CommandActionResponse(2, getResultResponse(ActionResults.RESULT_FAIL, "app list is null")) : new CommandActionResponse(1, getResultResponse("success", strHandleFindAppInfoAction));
        Log.i("ShareViaActionInteractor", "responseMessage: " + strHandleFindAppInfoAction);
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
