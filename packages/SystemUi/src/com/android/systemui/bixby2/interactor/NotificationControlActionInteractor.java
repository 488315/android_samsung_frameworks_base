package com.android.systemui.bixby2.interactor;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.bixby2.util.PackageInfoBixby;
import com.android.systemui.bixby2.util.ParamsParser;
import com.android.systemui.util.DeviceType;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
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
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class NotificationControlActionInteractor implements ActionInteractor {
    static final String NOTI_ID = "notiID";
    static final String NOTI_ITEM_COUNT = "itemCount";
    static final String NOTI_LIST = "notificationList";
    static final String NOTI_REPLY = "canReply";
    static final String NOTI_RESULT = "result";
    static final String NOTI_TEXT = "notiText";
    static final String NOTI_TITLE = "notiTitle";
    static final int RESULT_ALREADY_TURNED_ON_NOTIFICATION = 32;
    static final int RESULT_INTERRUPTED_BY_APP_NOTIFICATION_ALERTS = 32768;
    static final int RESULT_INTERRUPTED_BY_DND = 4096;
    static final int RESULT_INTERRUPTED_BY_NOTIFICATION_VOLUME = 16384;
    static final int RESULT_INTERRUPTED_BY_RINGER_MODE = 8192;
    static final int RESULT_INTERRUPTED_BY_USER_CHANGED = 65536;
    static final int RESULT_NO_CHANNEL = 4;
    static final int RESULT_NO_PERMISSION = 2;
    static final int RESULT_TURNED_ON_APP_NOTIFICATION = 8;
    static final int RESULT_TURNED_ON_NOTIFICATION_CHANNEL = 16;
    static final int RESULT_UNKNOWN_CAUSE = 1;
    private final Context mContext;
    private final NotificationController mNotificationController;
    private final String TAG = "NotificationControlActionInteractor";
    private boolean DEBUG = DeviceType.isEngOrUTBinary();
    private Gson mGson = new Gson();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Action {
        read_notification_withid,
        delete_notification,
        reply_notification,
        open_notification_panel,
        goto_edgelighting,
        set_notification_permission,
        set_notification_sound,
        setoff_notification_permission
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class DisplayContent {

        @SerializedName("appName")
        private String appName;

        @SerializedName("notiCount")
        private String notiCount;

        public DisplayContent(String str, String str2) {
            this.appName = str;
            this.notiCount = str2;
        }

        public String getAppName() {
            return this.appName;
        }

        public String getNotiCount() {
            return this.notiCount;
        }

        public void setAppName(String str) {
            this.appName = str;
        }

        public void setNotiCount(String str) {
            this.notiCount = str;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class NotificationList {

        @SerializedName("appName")
        private String appName;

        @SerializedName(NotificationControlActionInteractor.NOTI_REPLY)
        private String canReply;

        @SerializedName("fgs")
        private String fgs;

        @SerializedName(NotificationControlActionInteractor.NOTI_ID)
        private String notiID;

        @SerializedName(NotificationControlActionInteractor.NOTI_TEXT)
        private String notiText;

        @SerializedName(NotificationControlActionInteractor.NOTI_TITLE)
        private String notiTitle;

        @SerializedName("ongoing")
        private String ongoing;

        @SerializedName("when")
        private String when;

        public NotificationList(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
            this.notiID = str;
            this.notiTitle = str2;
            this.notiText = str3;
            this.canReply = str4;
            this.when = str5;
            this.fgs = str6;
            this.ongoing = str7;
            this.appName = str8;
        }

        public String getAppName() {
            return this.appName;
        }

        public String getFgs() {
            return this.fgs;
        }

        public String getNotiCanReply() {
            return this.canReply;
        }

        public String getNotiID() {
            return this.notiID;
        }

        public String getNotiText() {
            return this.notiText;
        }

        public String getNotiTitle() {
            return this.notiTitle;
        }

        public String getOngoing() {
            return this.ongoing;
        }

        public String getWhen() {
            return this.when;
        }

        public void setNotiCanReply(String str) {
            this.canReply = str;
        }

        public void setNotiID(String str) {
            this.notiID = str;
        }

        public void setNotiText(String str) {
            this.notiText = str;
        }

        public void setNotiTitle(String str) {
            this.notiTitle = str;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class Result {

        @SerializedName("contentForDisplay")
        private List<DisplayContent> contentForDisplay;

        @SerializedName(NotificationControlActionInteractor.NOTI_ITEM_COUNT)
        private String itemCount;

        @SerializedName("notificationContent")
        private String notificationContent;

        @SerializedName(NotificationControlActionInteractor.NOTI_LIST)
        private List<NotificationList> notificationList;

        @SerializedName("result")
        private String result;

        public Result() {
        }

        public List<DisplayContent> getContentForDisplay() {
            return this.contentForDisplay;
        }

        public String getItemCount() {
            return this.itemCount;
        }

        public String getNotificationContent() {
            return this.notificationContent;
        }

        public List<NotificationList> getNotificationList() {
            return this.notificationList;
        }

        public String getResult() {
            return this.result;
        }

        public void setContentForDisplay(List<DisplayContent> list) {
            this.contentForDisplay = list;
        }

        public void setItemCount(String str) {
            this.itemCount = str;
        }

        public void setNotificationContent(String str) {
            this.notificationContent = str;
        }

        public void setNotificationList(List<NotificationList> list) {
            this.notificationList = list;
        }

        public void setResult(String str) {
            this.result = str;
        }

        public Result(String str, String str2, String str3) {
            this.result = str;
            this.notificationContent = str2;
            this.itemCount = str3;
        }
    }

    public NotificationControlActionInteractor(Context context, NotificationController notificationController) {
        this.mContext = context;
        this.mNotificationController = notificationController;
    }

    private boolean isNotiPerformAction(String str) {
        return Action.delete_notification.toString().equals(str) || Action.reply_notification.toString().equals(str) || Action.set_notification_permission.toString().equals(str) || Action.set_notification_sound.toString().equals(str) || Action.setoff_notification_permission.toString().equals(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$matchAction$0(String str, String str2) {
        return str2.equals(str);
    }

    private boolean matchAction(final String str) {
        return Arrays.stream(Action.values()).map(new NotificationControlActionInteractor$$ExternalSyntheticLambda0(0)).anyMatch(new Predicate() { // from class: com.android.systemui.bixby2.interactor.NotificationControlActionInteractor$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$matchAction$0;
                lambda$matchAction$0 = NotificationControlActionInteractor.lambda$matchAction$0(str, (String) obj);
                return lambda$matchAction$0;
            }
        });
    }

    private CommandActionResponse respondCompleted(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? new CommandActionResponse(2, ActionResults.RESULT_FAIL) : new CommandActionResponse(2, ActionResults.RESULT_ALL_IS_ONGOING_NOTI) : new CommandActionResponse(2, ActionResults.RESULT_NO_MATCHED_APP_NAME) : new CommandActionResponse(2, ActionResults.RESULT_NO_NOTIFICATION_EXIST) : new CommandActionResponse(1, "success");
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public List<String> getSupportingActions() {
        return (List) Arrays.stream(Action.values()).map(new NotificationControlActionInteractor$$ExternalSyntheticLambda0(1)).collect(Collectors.toList());
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public Command loadStatefulCommandInteractor(String str, Command command) {
        UnformattedTemplate unformattedTemplate;
        if (!matchAction(str)) {
            return null;
        }
        if (Action.read_notification_withid.toString().equals(str)) {
            Bundle readNotificationWithID = this.mNotificationController.readNotificationWithID(null);
            CommandActionResponse respondCompleted = respondCompleted(readNotificationWithID.getInt("result"), readNotificationWithID);
            if (this.DEBUG) {
                ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("responseMessage: "), respondCompleted.responseMessage, "NotificationControlActionInteractor");
            }
            UnformattedTemplate unformattedTemplate2 = new UnformattedTemplate(respondCompleted.responseMessage);
            Command.StatefulBuilder statefulBuilder = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder.mStatus = 1;
            statefulBuilder.mTemplate = unformattedTemplate2;
            return statefulBuilder.build();
        }
        if (isNotiPerformAction(str)) {
            try {
                unformattedTemplate = new UnformattedTemplate(new JSONObject().toString());
            } catch (Exception e) {
                Log.e("NotificationControlActionInteractor", "JSONException: " + e.toString());
                unformattedTemplate = null;
            }
            if (unformattedTemplate == null) {
                return null;
            }
            Command.StatefulBuilder statefulBuilder2 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder2.mStatus = 1;
            statefulBuilder2.mTemplate = unformattedTemplate;
            return statefulBuilder2.build();
        }
        if (Action.goto_edgelighting.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder3 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder3.mStatus = 1;
            statefulBuilder3.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder3.build();
        }
        if (Action.open_notification_panel.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder4 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder4.mStatus = 1;
            statefulBuilder4.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder4.build();
        }
        if (Action.set_notification_permission.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder5 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder5.mStatus = 1;
            statefulBuilder5.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder5.build();
        }
        if (Action.set_notification_sound.toString().equals(str)) {
            Command.StatefulBuilder statefulBuilder6 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder6.mStatus = 1;
            statefulBuilder6.mTemplate = CommandTemplate.NO_TEMPLATE;
            return statefulBuilder6.build();
        }
        if (!Action.setoff_notification_permission.toString().equals(str)) {
            return null;
        }
        Command.StatefulBuilder statefulBuilder7 = new Command.StatefulBuilder(command.mCommandId);
        statefulBuilder7.mStatus = 1;
        statefulBuilder7.mTemplate = CommandTemplate.NO_TEMPLATE;
        return statefulBuilder7.build();
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public void performCommandActionInteractor(String str, CommandAction commandAction, ICommandActionCallback iCommandActionCallback) {
        CommandActionResponse commandActionResponse;
        if (matchAction(str)) {
            Log.d("NotificationControlActionInteractor", "commandAction.getActionType() = " + commandAction.getActionType());
            CommandActionResponse commandActionResponse2 = null;
            String str2 = commandAction.getActionType() != 5 ? null : ((JSONStringAction) commandAction).mNewValue;
            if (isNotiPerformAction(str)) {
                PackageInfoBixby packageInfoFromJson = ParamsParser.getPackageInfoFromJson(str2);
                if (packageInfoFromJson == null || TextUtils.isEmpty(packageInfoFromJson.PackageName)) {
                    Log.d("NotificationControlActionInteractor", " packageInfo is null");
                }
                if (Action.delete_notification.toString().equals(str)) {
                    ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("-- delete_notification --   packageName = "), packageInfoFromJson.PackageName, "NotificationControlActionInteractor");
                    commandActionResponse = respondCompleted(this.mNotificationController.deleteNotification(packageInfoFromJson.PackageName));
                } else if (Action.reply_notification.toString().equals(str)) {
                    commandActionResponse = respondCompleted(this.mNotificationController.replyNotification(packageInfoFromJson.notiID, packageInfoFromJson.MsgStr));
                } else if (Action.set_notification_permission.toString().equals(str)) {
                    Log.d("NotificationControlActionInteractor", "-- set_notification_permission --");
                    if (packageInfoFromJson.PackageName == null) {
                        packageInfoFromJson.PackageName = this.mNotificationController.getFocusedPackageName();
                    }
                    int checkNotificationStatusForPackage = this.mNotificationController.checkNotificationStatusForPackage(packageInfoFromJson.PackageName);
                    ListPopupWindow$$ExternalSyntheticOutline0.m10m("checkNotificationStatusForPackage() result = ", checkNotificationStatusForPackage, "NotificationControlActionInteractor");
                    commandActionResponse = checkNotificationStatusForPackage == 2 ? new CommandActionResponse(1, ActionResults.RESULT_NO_CATEGORY_NOTIFICATION) : (checkNotificationStatusForPackage == 4 || checkNotificationStatusForPackage == 32) ? new CommandActionResponse(1, ActionResults.RESULT_ALREADY_ON_SETTING_NOTIFICATION) : checkNotificationStatusForPackage == 8 ? new CommandActionResponse(1, ActionResults.RESULT_SET_ON_SETTING_NOTIFICATION) : checkNotificationStatusForPackage == 16 ? new CommandActionResponse(1, "SetOnSoundNotification") : new CommandActionResponse(2, ActionResults.RESULT_FAIL);
                } else if (Action.set_notification_sound.toString().equals(str)) {
                    Log.d("NotificationControlActionInteractor", "-- set_notification_sound --");
                    if (packageInfoFromJson.PackageName == null) {
                        packageInfoFromJson.PackageName = this.mNotificationController.getFocusedPackageName();
                    }
                    int checkNotificationSoundStatus = this.mNotificationController.checkNotificationSoundStatus(packageInfoFromJson.PackageName);
                    ListPopupWindow$$ExternalSyntheticOutline0.m10m("checkNotificationSoundStatus() result = ", checkNotificationSoundStatus, "NotificationControlActionInteractor");
                    commandActionResponse = (checkNotificationSoundStatus & 8192) != 0 ? new CommandActionResponse(1, "SetOnSoundNotification") : (checkNotificationSoundStatus & 4096) != 0 ? new CommandActionResponse(1, ActionResults.RESULT_SET_OFF_DND_NOTIFICATION) : (32768 & checkNotificationSoundStatus) != 0 ? new CommandActionResponse(1, ActionResults.RESULT_SET_ON_SOUND_APP_NOTIFICATION) : (checkNotificationSoundStatus & 65536) != 0 ? new CommandActionResponse(1, ActionResults.RESULT_SET_IMPORTANCE_NOTIFICATION) : new CommandActionResponse(2, ActionResults.RESULT_FAIL);
                } else if (Action.setoff_notification_permission.toString().equals(str)) {
                    Log.d("NotificationControlActionInteractor", "-- setoff_notification_permission --");
                    if (packageInfoFromJson.PackageName == null) {
                        packageInfoFromJson.PackageName = this.mNotificationController.getFocusedPackageName();
                    }
                    int notificationTurnOffForPackage = this.mNotificationController.setNotificationTurnOffForPackage(packageInfoFromJson.PackageName);
                    ListPopupWindow$$ExternalSyntheticOutline0.m10m("setNotificationTurnOffForPackage() result = ", notificationTurnOffForPackage, "NotificationControlActionInteractor");
                    commandActionResponse = notificationTurnOffForPackage == 8 ? new CommandActionResponse(2, ActionResults.RESULT_CANNOT_SET_OFF_NOTIFICATION) : notificationTurnOffForPackage == 9 ? new CommandActionResponse(2, "already_set") : notificationTurnOffForPackage == 1 ? new CommandActionResponse(1, "success") : new CommandActionResponse(2, ActionResults.RESULT_FAIL);
                }
                commandActionResponse2 = commandActionResponse;
            } else if (Action.open_notification_panel.toString().equals(str)) {
                this.mNotificationController.openNotificationPanel();
                commandActionResponse2 = new CommandActionResponse(1, "success");
            } else if (Action.goto_edgelighting.toString().equals(str)) {
                commandActionResponse2 = this.mNotificationController.goToNotiSettings(this.mContext) ? new CommandActionResponse(1, "success") : new CommandActionResponse(2, ActionResults.RESULT_FAIL);
            }
            if (commandActionResponse2 == null && iCommandActionCallback == null) {
                return;
            }
            if (this.DEBUG) {
                StringBuilder sb = new StringBuilder("responseCode = ");
                sb.append(commandActionResponse2.responseCode);
                sb.append(", responseMessage = ");
                ExifInterface$$ExternalSyntheticOutline0.m35m(sb, commandActionResponse2.responseMessage, "NotificationControlActionInteractor");
            }
            ((CommandProvider.C47571) iCommandActionCallback).onActionFinished(commandActionResponse2.responseCode, commandActionResponse2.responseMessage);
        }
    }

    private CommandActionResponse respondCompleted(int i, Bundle bundle) {
        int i2 = 0;
        if (i != 5) {
            if (i != 6) {
                return new CommandActionResponse(2, ActionResults.RESULT_FAIL);
            }
            if (bundle != null) {
                Result result = new Result();
                result.setResult("success");
                result.setItemCount(Integer.toString(bundle.getInt(NOTI_ITEM_COUNT)));
                ArrayList arrayList = new ArrayList(Arrays.asList((NotificationList[]) this.mGson.fromJson(NotificationList[].class, bundle.getString(NOTI_LIST))));
                if (this.DEBUG) {
                    while (i2 < arrayList.size()) {
                        ((NotificationList) arrayList.get(i2)).getNotiID();
                        ((NotificationList) arrayList.get(i2)).getNotiTitle().length();
                        ((NotificationList) arrayList.get(i2)).getNotiText().length();
                        ((NotificationList) arrayList.get(i2)).getNotiCanReply();
                        ((NotificationList) arrayList.get(i2)).getWhen();
                        ((NotificationList) arrayList.get(i2)).getFgs();
                        ((NotificationList) arrayList.get(i2)).getOngoing();
                        ((NotificationList) arrayList.get(i2)).getAppName();
                        i2++;
                    }
                }
                result.setNotificationList(arrayList);
                String json = this.mGson.toJson(result);
                if (this.DEBUG) {
                    result.getResult();
                }
                return new CommandActionResponse(1, json);
            }
            return new CommandActionResponse(2, ActionResults.RESULT_FAIL);
        }
        if (bundle != null) {
            Result result2 = new Result();
            result2.setResult("success");
            result2.setItemCount(Integer.toString(bundle.getInt(NOTI_ITEM_COUNT)));
            ArrayList arrayList2 = new ArrayList(Arrays.asList((NotificationList[]) this.mGson.fromJson(NotificationList[].class, bundle.getString(NOTI_LIST))));
            if (this.DEBUG) {
                for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                    ((NotificationList) arrayList2.get(i3)).getNotiID();
                    ((NotificationList) arrayList2.get(i3)).getNotiTitle().length();
                    ((NotificationList) arrayList2.get(i3)).getNotiText().length();
                    ((NotificationList) arrayList2.get(i3)).getNotiCanReply();
                    ((NotificationList) arrayList2.get(i3)).getWhen();
                    ((NotificationList) arrayList2.get(i3)).getFgs();
                    ((NotificationList) arrayList2.get(i3)).getOngoing();
                    ((NotificationList) arrayList2.get(i3)).getAppName();
                }
            }
            result2.setNotificationList(arrayList2);
            ArrayList arrayList3 = new ArrayList(Arrays.asList((DisplayContent[]) this.mGson.fromJson(DisplayContent[].class, this.mNotificationController.getDisplayDescription().toString())));
            while (i2 < arrayList3.size()) {
                ((DisplayContent) arrayList3.get(i2)).getAppName();
                ((DisplayContent) arrayList3.get(i2)).getNotiCount();
                i2++;
            }
            result2.setContentForDisplay(arrayList3);
            String json2 = this.mGson.toJson(result2);
            if (this.DEBUG) {
                result2.getResult();
            }
            return new CommandActionResponse(1, json2);
        }
        return new CommandActionResponse(2, ActionResults.RESULT_FAIL);
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public Command loadStatefulCommandInteractor(String str, Command command, CommandAction commandAction) {
        if (!Action.read_notification_withid.toString().equals(str)) {
            return null;
        }
        Bundle readNotificationWithID = this.mNotificationController.readNotificationWithID(ParamsParser.getPackageInfoFromJson(commandAction.getActionType() == 5 ? ((JSONStringAction) commandAction).mNewValue : null).PackageName);
        CommandActionResponse respondCompleted = respondCompleted(readNotificationWithID.getInt("result"), readNotificationWithID);
        if (this.DEBUG) {
            ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("responseMessage: "), respondCompleted.responseMessage, "NotificationControlActionInteractor");
        }
        UnformattedTemplate unformattedTemplate = new UnformattedTemplate(respondCompleted.responseMessage);
        Command.StatefulBuilder statefulBuilder = new Command.StatefulBuilder(command.mCommandId);
        statefulBuilder.mStatus = 1;
        statefulBuilder.mTemplate = unformattedTemplate;
        return statefulBuilder.build();
    }
}
