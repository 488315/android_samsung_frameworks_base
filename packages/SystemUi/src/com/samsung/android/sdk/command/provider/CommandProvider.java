package com.samsung.android.sdk.command.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.CommandSdk;
import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.util.LogWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class CommandProvider extends ContentProvider {
    public static final String[] WELL_KNOWN_CALLING_PACKAGES = {"com.android.settings.intelligence", KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.samsung.android.app.routines", "com.samsung.android.app.settings.bixby", "com.samsung.accessibility", "com.samsung.android.app.galaxyfinder", "com.samsung.android.app.galaxyregistry", "com.sec.android.app.launcher"};
    public static final String[] CORE_SYSTEM_PACKAGES = {"com.android.settings.intelligence", "com.samsung.android.app.galaxyfinder", "com.samsung.android.app.galaxyregistry", "com.sec.android.app.launcher"};

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.samsung.android.sdk.command.provider.CommandProvider$1 */
    public final class C47571 implements ICommandActionCallback {
        public final /* synthetic */ Bundle val$bundle;
        public final /* synthetic */ String val$commandId;
        public final /* synthetic */ ICommandActionHandler val$handler;

        public C47571(CommandProvider commandProvider, Bundle bundle, ICommandActionHandler iCommandActionHandler, String str) {
            this.val$bundle = bundle;
            this.val$handler = iCommandActionHandler;
            this.val$commandId = str;
        }

        public final void onActionFinished(int i, String str) {
            Bundle bundle = this.val$bundle;
            bundle.putInt("response_code", i);
            bundle.putString("response_message", str);
            Command loadStatefulCommand = this.val$handler.loadStatefulCommand(this.val$commandId);
            if (loadStatefulCommand != null) {
                bundle.putBundle("command", loadStatefulCommand.getDataBundle());
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:156:0x02dc, code lost:
    
        if (r2.containsKey("response_code") == false) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x0304, code lost:
    
        r2.putInt("response_code", 2);
        com.samsung.android.sdk.command.util.LogWrapper.m263e("CommandProvider", "failed to load all commands");
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0302, code lost:
    
        if (r2.containsKey("response_code") != false) goto L178;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x015c, code lost:
    
        if (r2.containsKey("response_code") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x0184, code lost:
    
        r2.putInt("response_code", 2);
        com.samsung.android.sdk.command.util.LogWrapper.m263e("CommandProvider", "cannot create command list");
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x0182, code lost:
    
        if (r2.containsKey("response_code") != false) goto L178;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0409 A[Catch: all -> 0x0439, Exception -> 0x043b, TryCatch #4 {Exception -> 0x043b, blocks: (B:124:0x03e6, B:126:0x03ee, B:128:0x03fe, B:105:0x0409, B:106:0x0414, B:103:0x0403), top: B:123:0x03e6, outer: #6 }] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x042a  */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Bundle call(String str, String str2, Bundle bundle) {
        Command loadStatefulCommand;
        String str3;
        StringBuilder sb;
        String str4;
        StringBuilder sb2;
        String str5;
        StringBuilder sb3;
        long currentTimeMillis = System.currentTimeMillis();
        Bundle bundle2 = new Bundle();
        bundle2.putString("version", "2.0.8");
        String callingPackage = getCallingPackage();
        boolean z = false;
        if (!TextUtils.isEmpty(callingPackage)) {
            String[] strArr = WELL_KNOWN_CALLING_PACKAGES;
            int i = 0;
            while (true) {
                if (i >= 8) {
                    break;
                }
                if (callingPackage.equalsIgnoreCase(strArr[i])) {
                    z = true;
                    break;
                }
                i++;
            }
        }
        if (!z) {
            bundle2.putInt("response_code", 2);
            bundle2.putString("response_message", "invalid_calling_package");
            LogWrapper.m263e("CommandProvider", "called from unauthorized package : " + getCallingPackage());
            return bundle2;
        }
        LogWrapper.m264i("call() version : 2.0.8, caller : " + getCallingPackage() + ", package : " + getContext().getPackageName() + ", method : " + str + ", id : " + str2);
        ContentProvider.CallingIdentity clearCallingIdentity = Arrays.stream(CORE_SYSTEM_PACKAGES).anyMatch(new Predicate() { // from class: com.samsung.android.sdk.command.provider.CommandProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                CommandProvider commandProvider = CommandProvider.this;
                String[] strArr2 = CommandProvider.WELL_KNOWN_CALLING_PACKAGES;
                return ((String) obj).equalsIgnoreCase(commandProvider.getCallingPackage());
            }
        }) ? clearCallingIdentity() : null;
        Object obj = CommandSdk.sWaitLock;
        CommandSdk commandSdk = CommandSdk.LazyHolder.INSTANCE;
        commandSdk.getClass();
        Object obj2 = CommandSdk.sWaitLock;
        synchronized (obj2) {
            if (commandSdk.mActionHandler == null) {
                try {
                    Log.w("[CmdL-2.0.8]CommandSdk", "wait until the handler is set (timeout 3 seconds)");
                    obj2.wait(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        ICommandActionHandler iCommandActionHandler = commandSdk.mActionHandler;
        if (iCommandActionHandler != null) {
            str.getClass();
            str.hashCode();
            switch (str) {
                case "method_LOAD":
                    if (bundle != null) {
                        try {
                            try {
                            } catch (Exception e2) {
                                LogWrapper.m263e("CommandProvider", "failed to load a command : " + str2 + ", reason : " + e2.getMessage());
                                if (!bundle2.containsKey("response_code")) {
                                    bundle2.putInt("response_code", 2);
                                    str3 = "CommandProvider";
                                    sb = new StringBuilder("failed to load a command : ");
                                }
                            }
                            if (bundle.containsKey("action")) {
                                loadStatefulCommand = iCommandActionHandler.loadStatefulCommand(str2, CommandAction.createActionFromBundle(bundle.getBundle("action")));
                                if (loadStatefulCommand == null) {
                                    loadStatefulCommand = iCommandActionHandler.loadStatefulCommand(str2);
                                }
                                if (loadStatefulCommand == null) {
                                    Command.StatelessBuilder statelessBuilder = new Command.StatelessBuilder(str2);
                                    statelessBuilder.mStatus = 2;
                                    loadStatefulCommand = statelessBuilder.build();
                                }
                                bundle2.putInt("response_code", 1);
                                bundle2.putParcelable("command", loadStatefulCommand.getDataBundle());
                                if (!bundle2.containsKey("response_code")) {
                                    bundle2.putInt("response_code", 2);
                                    str3 = "CommandProvider";
                                    sb = new StringBuilder("failed to load a command : ");
                                    sb.append(str2);
                                    LogWrapper.m263e(str3, sb.toString());
                                    break;
                                }
                            }
                        } catch (Throwable th) {
                            if (!bundle2.containsKey("response_code")) {
                                bundle2.putInt("response_code", 2);
                                LogWrapper.m263e("CommandProvider", "failed to load a command : " + str2);
                            }
                            throw th;
                        }
                    }
                    loadStatefulCommand = iCommandActionHandler.loadStatefulCommand(str2);
                    if (loadStatefulCommand == null) {
                    }
                    bundle2.putInt("response_code", 1);
                    bundle2.putParcelable("command", loadStatefulCommand.getDataBundle());
                    if (!bundle2.containsKey("response_code")) {
                    }
                    break;
                case "method_MIGRATE":
                    if (bundle == null || !bundle.containsKey("action")) {
                        bundle2.putInt("response_code", 2);
                        bundle2.putString("response_message", "invalid_action");
                        break;
                    } else {
                        try {
                            try {
                                CommandAction migrateCommandAction = iCommandActionHandler.migrateCommandAction(str2, CommandAction.createActionFromBundle(bundle.getBundle("action")));
                                if (migrateCommandAction != null) {
                                    bundle2.putInt("response_code", 1);
                                    bundle2.putBundle("action", migrateCommandAction.getDataBundle());
                                } else {
                                    bundle2.putInt("response_code", 2);
                                }
                            } catch (Exception e3) {
                                LogWrapper.m263e("CommandProvider", "failed to migrate an action : " + str2 + ", reason : " + e3.getMessage());
                                if (!bundle2.containsKey("response_code")) {
                                    bundle2.putInt("response_code", 2);
                                    str4 = "CommandProvider";
                                    sb2 = new StringBuilder("failed to migrate an action : ");
                                }
                            }
                            if (!bundle2.containsKey("response_code")) {
                                bundle2.putInt("response_code", 2);
                                str4 = "CommandProvider";
                                sb2 = new StringBuilder("failed to migrate an action : ");
                                sb2.append(str2);
                                LogWrapper.m263e(str4, sb2.toString());
                                break;
                            }
                        } catch (Throwable th2) {
                            if (!bundle2.containsKey("response_code")) {
                                bundle2.putInt("response_code", 2);
                                LogWrapper.m263e("CommandProvider", "failed to migrate an action : " + str2);
                            }
                            throw th2;
                        }
                    }
                    break;
                case "method_LOAD_ALL":
                    try {
                        try {
                            ArrayList arrayList = (ArrayList) iCommandActionHandler.createStatelessCommands();
                            ArrayList<? extends Parcelable> arrayList2 = new ArrayList<>();
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                Command command = (Command) it.next();
                                try {
                                    if (command.mStatus == 2) {
                                        LogWrapper.m264i("not supported command : " + command.mCommandId);
                                    } else {
                                        Command loadStatefulCommand2 = iCommandActionHandler.loadStatefulCommand(command.mCommandId);
                                        if (loadStatefulCommand2 != null) {
                                            arrayList2.add(loadStatefulCommand2.getDataBundle());
                                        }
                                    }
                                } catch (Exception e4) {
                                    LogWrapper.m263e("CommandProvider", "failed to load a command : " + command.mCommandId + ", reason : " + e4.getMessage());
                                }
                            }
                            bundle2.putInt("response_code", 1);
                            bundle2.putParcelableArrayList("command_list", arrayList2);
                            break;
                        } catch (Exception e5) {
                            LogWrapper.m263e("CommandProvider", "failed to load all commands : " + e5.getMessage());
                            break;
                        }
                    } catch (Throwable th3) {
                        if (!bundle2.containsKey("response_code")) {
                            bundle2.putInt("response_code", 2);
                            LogWrapper.m263e("CommandProvider", "failed to load all commands");
                        }
                        throw th3;
                    }
                case "method_ACTION":
                    if (bundle == null || !bundle.containsKey("action")) {
                        bundle2.putInt("response_code", 2);
                        bundle2.putString("response_message", "invalid_action");
                        break;
                    } else {
                        CommandAction createActionFromBundle = CommandAction.createActionFromBundle(bundle.getBundle("action"));
                        try {
                            try {
                                iCommandActionHandler.performCommandAction(str2, createActionFromBundle, new C47571(this, bundle2, iCommandActionHandler, str2));
                            } catch (Exception e6) {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("failed to perform action : ");
                                sb4.append(str2);
                                sb4.append(", action type : ");
                                sb4.append(createActionFromBundle != null ? createActionFromBundle.getActionType() : -1);
                                sb4.append(", reason : ");
                                sb4.append(e6.getMessage());
                                LogWrapper.m263e("CommandProvider", sb4.toString());
                                if (!bundle2.containsKey("response_code")) {
                                    bundle2.putInt("response_code", 2);
                                    str5 = "CommandProvider";
                                    sb3 = new StringBuilder("failed to perform action : ");
                                }
                            }
                            if (!bundle2.containsKey("response_code")) {
                                bundle2.putInt("response_code", 2);
                                str5 = "CommandProvider";
                                sb3 = new StringBuilder("failed to perform action : ");
                                sb3.append(str2);
                                LogWrapper.m263e(str5, sb3.toString());
                                break;
                            }
                        } catch (Throwable th4) {
                            if (!bundle2.containsKey("response_code")) {
                                bundle2.putInt("response_code", 2);
                                LogWrapper.m263e("CommandProvider", "failed to perform action : " + str2);
                            }
                            throw th4;
                        }
                    }
                    break;
                case "method_CREATE":
                    try {
                        try {
                            ArrayList arrayList3 = (ArrayList) iCommandActionHandler.createStatelessCommands();
                            ArrayList<? extends Parcelable> arrayList4 = new ArrayList<>();
                            Iterator it2 = arrayList3.iterator();
                            while (it2.hasNext()) {
                                arrayList4.add(((Command) it2.next()).getDataBundle());
                            }
                            bundle2.putInt("response_code", 1);
                            bundle2.putParcelableArrayList("command_list", arrayList4);
                            break;
                        } catch (Exception e7) {
                            LogWrapper.m263e("CommandProvider", "cannot create command list : " + e7.getMessage());
                            break;
                        }
                    } catch (Throwable th5) {
                        if (!bundle2.containsKey("response_code")) {
                            bundle2.putInt("response_code", 2);
                            LogWrapper.m263e("CommandProvider", "cannot create command list");
                        }
                        throw th5;
                    }
                default:
                    bundle2.putInt("response_code", 2);
                    bundle2.putString("response_message", "invalid_method");
                    LogWrapper.m263e("CommandProvider", "unknown method : ".concat(str));
                    break;
            }
        } else {
            bundle2.putInt("response_code", 2);
            bundle2.putString("response_message", "handler_timeout");
            LogWrapper.m263e("CommandProvider", "command action handler is not set");
        }
        if (clearCallingIdentity != null) {
            restoreCallingIdentity(clearCallingIdentity);
        }
        LogWrapper.m264i("call() took time : " + (System.currentTimeMillis() - currentTimeMillis));
        return bundle2;
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
