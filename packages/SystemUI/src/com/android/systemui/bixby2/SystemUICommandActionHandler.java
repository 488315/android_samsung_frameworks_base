package com.android.systemui.bixby2;

import android.app.SemStatusBarManager;
import android.content.Context;
import android.net.Uri;
import android.os.SystemProperties;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.NotiRune;
import com.android.systemui.bixby2.interactor.ActionInteractor;
import com.android.systemui.bixby2.interactor.AppControlActionInteractor;
import com.android.systemui.bixby2.interactor.DeviceControlActionInteractor;
import com.android.systemui.bixby2.interactor.MusicControlActionInteractor;
import com.android.systemui.bixby2.interactor.NotificationControlActionInteractor;
import com.android.systemui.bixby2.interactor.ScreenControlActionInteractor;
import com.android.systemui.bixby2.interactor.ShareViaActionInteractor;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.SubscreenNotificationInfo;
import com.android.systemui.statusbar.notification.SubscreenNotificationInfoManager;
import com.android.systemui.statusbar.notification.SubscreenSubRoomNotification;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.samsung.android.sdk.bixby2.AppMetaInfo;
import com.samsung.android.sdk.bixby2.Sbixby;
import com.samsung.android.sdk.bixby2.state.StateHandler;
import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.CommandSdk;
import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.provider.ICommandActionCallback;
import com.samsung.android.sdk.command.provider.ICommandActionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SystemUICommandActionHandler implements ICommandActionHandler {
    private static final String CAPSULE_ID = "viv.systemApp";
    private static final String TAG = "SystemUICommandActionHandler";
    private List<ActionInteractor> mActionInteractors = new ArrayList();
    private Map<String, Command> mCommandMap = null;
    private final Context mContext;
    SubscreenNotificationController mSubscreenNotificationController;

    public SystemUICommandActionHandler(Context context, AppControlActionInteractor appControlActionInteractor, DeviceControlActionInteractor deviceControlActionInteractor, MusicControlActionInteractor musicControlActionInteractor, NotificationControlActionInteractor notificationControlActionInteractor, ScreenControlActionInteractor screenControlActionInteractor, ShareViaActionInteractor shareViaActionInteractor, SubscreenNotificationController subscreenNotificationController) {
        Log.d(TAG, "SystemUICommandActionHandler()");
        this.mContext = context;
        this.mActionInteractors.add(appControlActionInteractor);
        this.mActionInteractors.add(deviceControlActionInteractor);
        this.mActionInteractors.add(musicControlActionInteractor);
        this.mActionInteractors.add(notificationControlActionInteractor);
        this.mActionInteractors.add(screenControlActionInteractor);
        this.mActionInteractors.add(shareViaActionInteractor);
        this.mSubscreenNotificationController = subscreenNotificationController;
        CommandSdk commandSdk = CommandSdk.LazyHolder.INSTANCE;
        commandSdk.getClass();
        Object obj = CommandSdk.sWaitLock;
        synchronized (obj) {
            commandSdk.mActionHandler = this;
            Log.d("[CmdL-2.0.8]CommandSdk", "set the action handler");
            obj.notifyAll();
        }
        Sbixby.initialize(context);
        Sbixby sbixby = Sbixby.getInstance();
        int parseInt = Integer.parseInt(SystemProperties.get("ro.build.version.release"));
        sbixby.getClass();
        if (TextUtils.isEmpty(CAPSULE_ID)) {
            throw new IllegalArgumentException("capsuleId cannot be null or empty");
        }
        if (Sbixby.appMetaInfoMap == null) {
            Sbixby.appMetaInfoMap = new HashMap();
        }
        ((HashMap) Sbixby.appMetaInfoMap).put(CAPSULE_ID, new AppMetaInfo(CAPSULE_ID, parseInt));
        updateSbixbyStateChange();
    }

    private Map<String, Command> getAvailableCommands() {
        if (this.mCommandMap == null) {
            loadAvailableCommands();
        }
        return this.mCommandMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPanelBarExpanded() {
        SemStatusBarManager semStatusBarManager = (SemStatusBarManager) this.mContext.getSystemService(SemStatusBarManager.class);
        if (semStatusBarManager != null) {
            return semStatusBarManager.isPanelExpanded();
        }
        return false;
    }

    private void loadAvailableCommands() {
        if (this.mCommandMap == null) {
            this.mCommandMap = new HashMap();
            ArrayList arrayList = new ArrayList();
            Iterator<ActionInteractor> it = this.mActionInteractors.iterator();
            while (it.hasNext()) {
                arrayList.addAll(it.next().getSupportingActions());
            }
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                String str = (String) obj;
                String uri = new Uri.Builder().scheme("command").authority(this.mContext.getPackageName() + ".command").appendPath(str).build().toString();
                Command.StatelessBuilder statelessBuilder = new Command.StatelessBuilder(uri);
                statelessBuilder.mTitle = str;
                statelessBuilder.mStatus = 0;
                this.mCommandMap.put(uri, statelessBuilder.build());
            }
        }
    }

    private void updateSbixbyStateChange() {
        try {
            StateHandler stateHandler = StateHandler.getInstance();
            StateHandler.Callback callback = new StateHandler.Callback() { // from class: com.android.systemui.bixby2.SystemUICommandActionHandler.1
                @Override // com.samsung.android.sdk.bixby2.state.StateHandler.Callback
                public String onAppStateRequested() {
                    JsonArray jsonArray;
                    JsonArray jsonArray2;
                    SubscreenDeviceModelParent subscreenDeviceModelParent;
                    SubscreenSubRoomNotification subscreenSubRoomNotification;
                    JsonArray jsonArray3;
                    boolean z;
                    int i;
                    JsonObject jsonObject = new JsonObject();
                    JsonObject jsonObject2 = new JsonObject();
                    JsonParser jsonParser = new JsonParser();
                    JsonArray jsonArray4 = new JsonArray();
                    JsonArray jsonArray5 = new JsonArray();
                    String str = null;
                    if (!NotiRune.NOTI_SUBSCREEN_ALL || (subscreenDeviceModelParent = SystemUICommandActionHandler.this.mSubscreenNotificationController.mDeviceModel) == null || (subscreenSubRoomNotification = subscreenDeviceModelParent.mSubRoomNotification) == null) {
                        jsonArray = jsonArray4;
                        jsonArray2 = jsonArray5;
                    } else {
                        JsonObject jsonObject3 = new JsonObject();
                        jsonObject3.addProperty("isShowNotiScreen", Boolean.valueOf(subscreenSubRoomNotification.mIsInNotiRoom));
                        boolean z2 = subscreenSubRoomNotification.mIsShownDetail;
                        jsonObject3.addProperty("currentPageLevel", z2 ? "detail" : (!subscreenSubRoomNotification.mIsShownGroup || z2) ? "list" : "group");
                        subscreenSubRoomNotification.mNotificationInfoManager.getClass();
                        int subscreenNotificationInfoListSize = SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize();
                        JsonArray jsonArray6 = new JsonArray();
                        int i2 = 0;
                        int i3 = 0;
                        while (i2 < subscreenNotificationInfoListSize) {
                            subscreenSubRoomNotification.mNotificationInfoManager.getClass();
                            SubscreenNotificationInfo subscreenNotificationInfo = (SubscreenNotificationInfo) SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.get(i2);
                            boolean bixbyNotificationVisible = subscreenSubRoomNotification.getBixbyNotificationVisible(subscreenNotificationInfo.mKey);
                            int i4 = subscreenNotificationInfoListSize;
                            int i5 = i2;
                            int i6 = i3;
                            if (subscreenNotificationInfo.mRemoteinput) {
                                z = bixbyNotificationVisible;
                                JsonObject jsonObject4 = new JsonObject();
                                jsonArray3 = jsonArray4;
                                jsonObject4.addProperty("notiID", subscreenNotificationInfo.mKey);
                                jsonObject4.addProperty("notiTitle", subscreenNotificationInfo.getTitle());
                                jsonObject4.addProperty("notiAppname", subscreenNotificationInfo.mAppName);
                                jsonObject4.addProperty("notiVisible", Boolean.valueOf(z));
                                jsonArray6.add(jsonObject4);
                                i = i6 + 1;
                            } else {
                                jsonArray3 = jsonArray4;
                                z = bixbyNotificationVisible;
                                i = i6;
                            }
                            if (subscreenNotificationInfo.mGroupSummary) {
                                NotificationChildrenContainer notificationChildrenContainer = subscreenNotificationInfo.mRow.mChildrenContainer;
                                int notificationChildCount = notificationChildrenContainer.getNotificationChildCount();
                                int i7 = i;
                                int i8 = 0;
                                while (i8 < notificationChildCount) {
                                    int i9 = notificationChildCount;
                                    JsonObject jsonObject5 = new JsonObject();
                                    JsonArray jsonArray7 = jsonArray5;
                                    int i10 = i8;
                                    SubscreenNotificationInfo createItemsData = subscreenSubRoomNotification.mNotificationInfoManager.createItemsData((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i8));
                                    if (createItemsData.mRemoteinput) {
                                        boolean bixbyNotificationVisible2 = (subscreenSubRoomNotification.mIsShownGroup || subscreenSubRoomNotification.mIsShownDetail) ? subscreenSubRoomNotification.getBixbyNotificationVisible(createItemsData.mKey) : z;
                                        jsonObject5.addProperty("notiID", createItemsData.mKey);
                                        jsonObject5.addProperty("notiTitle", createItemsData.getTitle());
                                        jsonObject5.addProperty("notiAppname", createItemsData.mAppName);
                                        jsonObject5.addProperty("notiVisible", Boolean.valueOf(bixbyNotificationVisible2));
                                        jsonArray6.add(jsonObject5);
                                        i7++;
                                    }
                                    i8 = i10 + 1;
                                    notificationChildCount = i9;
                                    jsonArray5 = jsonArray7;
                                }
                                i3 = i7;
                            } else {
                                i3 = i;
                            }
                            i2 = i5 + 1;
                            subscreenNotificationInfoListSize = i4;
                            jsonArray4 = jsonArray3;
                            jsonArray5 = jsonArray5;
                        }
                        jsonArray = jsonArray4;
                        jsonArray2 = jsonArray5;
                        jsonObject3.addProperty("notiCount", Integer.valueOf(i3));
                        JsonObject jsonObject6 = new JsonObject();
                        JsonArray jsonArray8 = new JsonArray();
                        JsonObject jsonObject7 = new JsonObject();
                        jsonObject7.addProperty("type", "viv.systemApp.CoverReplicableNotiList");
                        JsonArray jsonArray9 = new JsonArray();
                        JsonObject jsonObject8 = new JsonObject();
                        jsonObject8.add("coverScreenInfo", jsonObject3);
                        jsonObject8.add("coverNotificationList", jsonArray6);
                        jsonArray9.add(jsonObject8);
                        jsonObject7.add("values", jsonArray9);
                        jsonArray8.add(jsonObject7);
                        jsonObject6.add("concepts", jsonArray8);
                        jsonObject6.addProperty("capsuleId", SystemUICommandActionHandler.CAPSULE_ID);
                        jsonObject6.addProperty("appId", "com.sec.android.app.system");
                        jsonObject6.addProperty("appVersionCode", (Number) 1200004531);
                        str = jsonObject6.toString();
                    }
                    JsonObject jsonObject9 = str != null ? (JsonObject) jsonParser.parse(str) : new JsonObject();
                    jsonObject.addProperty("type", "CloseQuickPanelScreen");
                    jsonObject2.addProperty("panelExpanded", Boolean.valueOf(SystemUICommandActionHandler.this.isPanelBarExpanded()));
                    JsonArray jsonArray10 = jsonArray2;
                    jsonArray10.add(jsonObject2);
                    jsonObject.add("value", jsonArray10);
                    JsonArray jsonArray11 = jsonArray;
                    jsonArray11.add(jsonObject);
                    jsonObject9.add("llmContext", jsonArray11);
                    jsonObject9.addProperty("llmCapsuleId", "samsung.systemApp");
                    Log.d(SystemUICommandActionHandler.TAG, "onAppStateRequested: " + jsonObject9);
                    return jsonObject9.toString();
                }

                @Override // com.samsung.android.sdk.bixby2.state.StateHandler.Callback
                public String onCapsuleIdRequested() {
                    return SystemUICommandActionHandler.CAPSULE_ID;
                }
            };
            stateHandler.getClass();
            stateHandler.mCallback = callback;
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "updateAppState() IllegalArgumentException : " + e.getMessage());
        }
    }

    @Override // com.samsung.android.sdk.command.provider.ICommandActionHandler
    public List<Command> createStatelessCommands() {
        Log.d(TAG, "createStatelessCommands()");
        return new ArrayList(getAvailableCommands().values());
    }

    @Override // com.samsung.android.sdk.command.provider.ICommandActionHandler
    public Command loadStatefulCommand(String str) {
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("loadStatefulCommand(), commandId = ", str, TAG);
        Map<String, Command> availableCommands = getAvailableCommands();
        Command command = availableCommands.containsKey(str) ? availableCommands.get(str) : null;
        if (command != null) {
            String lastPathSegment = Uri.parse(command.mCommandId).getLastPathSegment();
            Iterator<ActionInteractor> it = this.mActionInteractors.iterator();
            while (it.hasNext()) {
                Command loadStatefulCommandInteractor = it.next().loadStatefulCommandInteractor(lastPathSegment, command);
                if (loadStatefulCommandInteractor != null) {
                    return loadStatefulCommandInteractor;
                }
            }
        }
        return null;
    }

    @Override // com.samsung.android.sdk.command.provider.ICommandActionHandler
    public /* bridge */ /* synthetic */ CommandAction migrateCommandAction(String str, CommandAction commandAction) {
        return null;
    }

    @Override // com.samsung.android.sdk.command.provider.ICommandActionHandler
    public void performCommandAction(String str, CommandAction commandAction, ICommandActionCallback iCommandActionCallback) {
        Log.d(TAG, "performCommandAction(), commandId = " + str);
        Command loadStatefulCommand = loadStatefulCommand(str);
        if (loadStatefulCommand != null) {
            String lastPathSegment = Uri.parse(loadStatefulCommand.mCommandId).getLastPathSegment();
            Iterator<ActionInteractor> it = this.mActionInteractors.iterator();
            while (it.hasNext()) {
                it.next().performCommandActionInteractor(lastPathSegment, commandAction, iCommandActionCallback);
            }
        }
    }

    @Override // com.samsung.android.sdk.command.provider.ICommandActionHandler
    public Command loadStatefulCommand(String str, CommandAction commandAction) {
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("loadStatefulCommand()(with action) commandId = ", str, TAG);
        Map<String, Command> availableCommands = getAvailableCommands();
        Command command = availableCommands.containsKey(str) ? availableCommands.get(str) : null;
        if (command != null) {
            String lastPathSegment = Uri.parse(command.mCommandId).getLastPathSegment();
            Iterator<ActionInteractor> it = this.mActionInteractors.iterator();
            while (it.hasNext()) {
                Command loadStatefulCommandInteractor = it.next().loadStatefulCommandInteractor(lastPathSegment, command, commandAction);
                if (loadStatefulCommandInteractor != null) {
                    return loadStatefulCommandInteractor;
                }
            }
        }
        return null;
    }
}
