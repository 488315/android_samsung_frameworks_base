package com.samsung.android.sdk.command.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.SystemUICommandActionHandler;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.CommandSdk;
import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.util.LogWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public class CommandProvider extends ContentProvider {
    public static final String[] WELL_KNOWN_CALLING_PACKAGES = {"com.android.settings.intelligence", KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.samsung.android.app.routines", "com.samsung.android.app.settings.bixby", "com.samsung.accessibility", "com.samsung.android.app.galaxyfinder", "com.samsung.android.app.galaxyregistry", "com.sec.android.app.launcher"};
    public static final String[] CORE_SYSTEM_PACKAGES = {"com.android.settings.intelligence", "com.samsung.android.app.galaxyfinder", "com.samsung.android.app.galaxyregistry", "com.sec.android.app.launcher"};

    /* renamed from: com.samsung.android.sdk.command.provider.CommandProvider$1, reason: invalid class name */
    public class AnonymousClass1 implements ICommandActionCallback {
        public final /* synthetic */ Bundle val$bundle;
        public final /* synthetic */ String val$commandId;
        public final /* synthetic */ ICommandActionHandler val$handler;

        public AnonymousClass1(CommandProvider commandProvider, Bundle bundle, ICommandActionHandler iCommandActionHandler, String str) {
            this.val$bundle = bundle;
            this.val$handler = iCommandActionHandler;
            this.val$commandId = str;
        }

        public final void onActionFinished(int i, String str) {
            this.val$bundle.putInt("response_code", i);
            this.val$bundle.putString("response_message", str);
            Command commandLoadStatefulCommand = this.val$handler.loadStatefulCommand(this.val$commandId);
            if (commandLoadStatefulCommand != null) {
                this.val$bundle.putBundle("command", commandLoadStatefulCommand.getDataBundle());
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:165:0x03f3 A[Catch: all -> 0x03e8, Exception -> 0x03eb, TryCatch #1 {Exception -> 0x03eb, blocks: (B:154:0x03cb, B:156:0x03d3, B:158:0x03e3, B:165:0x03f3, B:166:0x03ff, B:163:0x03ed), top: B:190:0x03cb, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0415  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00ac  */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Bundle call(String str, String str2, Bundle bundle) {
        int i;
        Command commandLoadStatefulCommand;
        String str3;
        StringBuilder sb;
        String str4;
        StringBuilder sb2;
        int i2;
        String str5;
        StringBuilder sb3;
        int i3 = 2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        Bundle bundleM = KeyguardSecPatternView$$ExternalSyntheticOutline0.m("version", "2.0.8");
        String callingPackage = getCallingPackage();
        if (!TextUtils.isEmpty(callingPackage)) {
            String[] strArr = WELL_KNOWN_CALLING_PACKAGES;
            int i4 = 0;
            while (i4 < 8) {
                if (callingPackage.equalsIgnoreCase(strArr[i4])) {
                    LogWrapper.i("call() version : 2.0.8, caller : " + getCallingPackage() + ", package : " + getContext().getPackageName() + ", method : " + str + ", id : " + str2);
                    ContentProvider.CallingIdentity callingIdentityClearCallingIdentity = Arrays.stream(CORE_SYSTEM_PACKAGES).anyMatch(new Predicate() { // from class: com.samsung.android.sdk.command.provider.CommandProvider$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            CommandProvider commandProvider = this.f$0;
                            String[] strArr2 = CommandProvider.WELL_KNOWN_CALLING_PACKAGES;
                            return ((String) obj).equalsIgnoreCase(commandProvider.getCallingPackage());
                        }
                    }) ? clearCallingIdentity() : null;
                    CommandSdk commandSdk = CommandSdk.LazyHolder.INSTANCE;
                    commandSdk.getClass();
                    Object obj = CommandSdk.sWaitLock;
                    synchronized (obj) {
                        if (commandSdk.mActionHandler == null) {
                            try {
                                Log.w("[CmdL-2.0.8]CommandSdk", "wait until the handler is set (timeout 3 seconds)");
                                obj.wait(3000L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    SystemUICommandActionHandler systemUICommandActionHandler = commandSdk.mActionHandler;
                    if (systemUICommandActionHandler != null) {
                        str.getClass();
                        switch (str.hashCode()) {
                            case -928183932:
                                if (!str.equals("method_LOAD")) {
                                    i = -1;
                                    break;
                                } else {
                                    i = 0;
                                    break;
                                }
                            case 193685549:
                                if (str.equals("method_MIGRATE")) {
                                    i = 1;
                                    break;
                                }
                                break;
                            case 812700742:
                                if (str.equals("method_LOAD_ALL")) {
                                    i = i3;
                                    break;
                                }
                                break;
                            case 1043009364:
                                if (str.equals("method_ACTION")) {
                                    i = 3;
                                    break;
                                }
                                break;
                            case 1113666074:
                                if (str.equals("method_CREATE")) {
                                    i = 4;
                                    break;
                                }
                                break;
                        }
                        try {
                            switch (i) {
                                case 0:
                                    if (bundle != null) {
                                        try {
                                            try {
                                                if (!bundle.containsKey("action") || (commandLoadStatefulCommand = systemUICommandActionHandler.loadStatefulCommand(str2, CommandAction.createActionFromBundle(bundle.getBundle("action")))) == null) {
                                                    commandLoadStatefulCommand = systemUICommandActionHandler.loadStatefulCommand(str2);
                                                }
                                                if (commandLoadStatefulCommand == null) {
                                                    Command.StatelessBuilder statelessBuilder = new Command.StatelessBuilder(str2);
                                                    statelessBuilder.mStatus = 2;
                                                    commandLoadStatefulCommand = statelessBuilder.build();
                                                }
                                                bundleM.putInt("response_code", 1);
                                                bundleM.putParcelable("command", commandLoadStatefulCommand.getDataBundle());
                                            } catch (Exception e2) {
                                                LogWrapper.e("CommandProvider", "failed to load a command : " + str2 + ", reason : " + e2.getMessage());
                                                if (!bundleM.containsKey("response_code")) {
                                                    bundleM.putInt("response_code", 2);
                                                    str3 = "CommandProvider";
                                                    sb = new StringBuilder("failed to load a command : ");
                                                }
                                            }
                                            if (!bundleM.containsKey("response_code")) {
                                                bundleM.putInt("response_code", 2);
                                                str3 = "CommandProvider";
                                                sb = new StringBuilder("failed to load a command : ");
                                                sb.append(str2);
                                                LogWrapper.e(str3, sb.toString());
                                                break;
                                            }
                                        } finally {
                                            if (!bundleM.containsKey("response_code")) {
                                                bundleM.putInt("response_code", 2);
                                                LogWrapper.e("CommandProvider", "failed to load a command : " + str2);
                                            }
                                        }
                                    } else {
                                        commandLoadStatefulCommand = systemUICommandActionHandler.loadStatefulCommand(str2);
                                        if (commandLoadStatefulCommand == null) {
                                        }
                                        bundleM.putInt("response_code", 1);
                                        bundleM.putParcelable("command", commandLoadStatefulCommand.getDataBundle());
                                        if (!bundleM.containsKey("response_code")) {
                                        }
                                    }
                                    break;
                                case 1:
                                    if (bundle == null || !bundle.containsKey("action")) {
                                        bundleM.putInt("response_code", 2);
                                        bundleM.putString("response_message", "invalid_action");
                                        break;
                                    } else {
                                        try {
                                            try {
                                                CommandAction commandActionMigrateCommandAction = systemUICommandActionHandler.migrateCommandAction(str2, CommandAction.createActionFromBundle(bundle.getBundle("action")));
                                                if (commandActionMigrateCommandAction != null) {
                                                    bundleM.putInt("response_code", 1);
                                                    bundleM.putBundle("action", commandActionMigrateCommandAction.getDataBundle());
                                                    i2 = 2;
                                                } else {
                                                    i2 = 2;
                                                    bundleM.putInt("response_code", 2);
                                                }
                                            } catch (Exception e3) {
                                                LogWrapper.e("CommandProvider", "failed to migrate an action : " + str2 + ", reason : " + e3.getMessage());
                                                if (!bundleM.containsKey("response_code")) {
                                                    bundleM.putInt("response_code", 2);
                                                    str4 = "CommandProvider";
                                                    sb2 = new StringBuilder("failed to migrate an action : ");
                                                }
                                            }
                                            if (!bundleM.containsKey("response_code")) {
                                                bundleM.putInt("response_code", i2);
                                                str4 = "CommandProvider";
                                                sb2 = new StringBuilder("failed to migrate an action : ");
                                                sb2.append(str2);
                                                LogWrapper.e(str4, sb2.toString());
                                                break;
                                            }
                                        } finally {
                                            if (!bundleM.containsKey("response_code")) {
                                                bundleM.putInt("response_code", 2);
                                                LogWrapper.e("CommandProvider", "failed to migrate an action : " + str2);
                                            }
                                        }
                                    }
                                    break;
                                case 2:
                                    try {
                                        try {
                                            ArrayList arrayList = (ArrayList) systemUICommandActionHandler.createStatelessCommands();
                                            ArrayList<? extends Parcelable> arrayList2 = new ArrayList<>();
                                            int size = arrayList.size();
                                            int i5 = 0;
                                            while (i5 < size) {
                                                Object obj2 = arrayList.get(i5);
                                                i5++;
                                                Command command = (Command) obj2;
                                                try {
                                                } catch (Exception e4) {
                                                    LogWrapper.e("CommandProvider", "failed to load a command : " + command.mCommandId + ", reason : " + e4.getMessage());
                                                }
                                                if (command.mStatus == i3) {
                                                    LogWrapper.i("not supported command : " + command.mCommandId);
                                                } else {
                                                    Command commandLoadStatefulCommand2 = systemUICommandActionHandler.loadStatefulCommand(command.mCommandId);
                                                    if (commandLoadStatefulCommand2 != null) {
                                                        arrayList2.add(commandLoadStatefulCommand2.getDataBundle());
                                                    }
                                                    i3 = 2;
                                                }
                                            }
                                            bundleM.putInt("response_code", 1);
                                            bundleM.putParcelableArrayList("command_list", arrayList2);
                                        } catch (Exception e5) {
                                            LogWrapper.e("CommandProvider", "failed to load all commands : " + e5.getMessage());
                                            if (!bundleM.containsKey("response_code")) {
                                            }
                                        }
                                        if (!bundleM.containsKey("response_code")) {
                                            bundleM.putInt("response_code", 2);
                                            LogWrapper.e("CommandProvider", "failed to load all commands");
                                            break;
                                        }
                                    } finally {
                                        if (!bundleM.containsKey("response_code")) {
                                            bundleM.putInt("response_code", 2);
                                            LogWrapper.e("CommandProvider", "failed to load all commands");
                                        }
                                    }
                                    break;
                                case 3:
                                    if (bundle == null || !bundle.containsKey("action")) {
                                        bundleM.putInt("response_code", i3);
                                        bundleM.putString("response_message", "invalid_action");
                                        break;
                                    } else {
                                        CommandAction commandActionCreateActionFromBundle = CommandAction.createActionFromBundle(bundle.getBundle("action"));
                                        try {
                                            try {
                                                systemUICommandActionHandler.performCommandAction(str2, commandActionCreateActionFromBundle, new AnonymousClass1(this, bundleM, systemUICommandActionHandler, str2));
                                            } catch (Exception e6) {
                                                StringBuilder sb4 = new StringBuilder();
                                                sb4.append("failed to perform action : ");
                                                sb4.append(str2);
                                                sb4.append(", action type : ");
                                                sb4.append(commandActionCreateActionFromBundle != null ? commandActionCreateActionFromBundle.getActionType() : -1);
                                                sb4.append(", reason : ");
                                                sb4.append(e6.getMessage());
                                                LogWrapper.e("CommandProvider", sb4.toString());
                                                if (!bundleM.containsKey("response_code")) {
                                                    bundleM.putInt("response_code", i3);
                                                    str5 = "CommandProvider";
                                                    sb3 = new StringBuilder("failed to perform action : ");
                                                }
                                            }
                                            if (!bundleM.containsKey("response_code")) {
                                                bundleM.putInt("response_code", i3);
                                                str5 = "CommandProvider";
                                                sb3 = new StringBuilder("failed to perform action : ");
                                                sb3.append(str2);
                                                LogWrapper.e(str5, sb3.toString());
                                                break;
                                            }
                                        } catch (Throwable th) {
                                            if (!bundleM.containsKey("response_code")) {
                                                bundleM.putInt("response_code", i3);
                                                LogWrapper.e("CommandProvider", "failed to perform action : " + str2);
                                            }
                                            throw th;
                                        }
                                    }
                                    break;
                                case 4:
                                    try {
                                        ArrayList arrayList3 = (ArrayList) systemUICommandActionHandler.createStatelessCommands();
                                        ArrayList<? extends Parcelable> arrayList4 = new ArrayList<>();
                                        int size2 = arrayList3.size();
                                        int i6 = 0;
                                        while (i6 < size2) {
                                            Object obj3 = arrayList3.get(i6);
                                            i6++;
                                            arrayList4.add(((Command) obj3).getDataBundle());
                                        }
                                        bundleM.putInt("response_code", 1);
                                        bundleM.putParcelableArrayList("command_list", arrayList4);
                                    } catch (Exception e7) {
                                        LogWrapper.e("CommandProvider", "cannot create command list : " + e7.getMessage());
                                        if (!bundleM.containsKey("response_code")) {
                                        }
                                    }
                                    if (!bundleM.containsKey("response_code")) {
                                        bundleM.putInt("response_code", i3);
                                        LogWrapper.e("CommandProvider", "cannot create command list");
                                        break;
                                    }
                                    break;
                                default:
                                    bundleM.putInt("response_code", i3);
                                    bundleM.putString("response_message", "invalid_method");
                                    LogWrapper.e("CommandProvider", "unknown method : ".concat(str));
                                    break;
                            }
                        } catch (Throwable th2) {
                            if (!bundleM.containsKey("response_code")) {
                                bundleM.putInt("response_code", i3);
                                LogWrapper.e("CommandProvider", "cannot create command list");
                            }
                            throw th2;
                        }
                    } else {
                        bundleM.putInt("response_code", 2);
                        bundleM.putString("response_message", "handler_timeout");
                        LogWrapper.e("CommandProvider", "command action handler is not set");
                    }
                    if (callingIdentityClearCallingIdentity != null) {
                        restoreCallingIdentity(callingIdentityClearCallingIdentity);
                    }
                    LogWrapper.i("call() took time : " + (System.currentTimeMillis() - jCurrentTimeMillis));
                    return bundleM;
                }
                i4++;
                i3 = 2;
            }
        }
        bundleM.putInt("response_code", 2);
        bundleM.putString("response_message", "invalid_calling_package");
        LogWrapper.e("CommandProvider", "called from unauthorized package : " + getCallingPackage());
        return bundleM;
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return false;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
