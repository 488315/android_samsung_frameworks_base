package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.os.AsyncTask;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.drawable.UserIconDrawable;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.UserInfoController;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class UserInfoControllerImpl implements UserInfoController {
    public final ArrayList mCallbacks = new ArrayList();
    public final Context mContext;
    public int mCurrentUserId;
    public final AnonymousClass2 mProfileReceiver;
    public String mUserAccount;
    public final UserTracker.Callback mUserChangedCallback;
    public Drawable mUserDrawable;
    public AnonymousClass3 mUserInfoTask;
    public String mUserName;
    public final UserTracker mUserTracker;

    public class UserInfoQueryResult {
        public final Drawable mAvatar;
        public final String mName;
        public final String mUserAccount;

        public UserInfoQueryResult(String str, Drawable drawable, String str2) {
            this.mName = str;
            this.mAvatar = drawable;
            this.mUserAccount = str2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.content.BroadcastReceiver, com.android.systemui.statusbar.policy.UserInfoControllerImpl$2] */
    public UserInfoControllerImpl(Context context, Executor executor, UserTracker userTracker) {
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.policy.UserInfoControllerImpl.1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                Display[] displays;
                UserInfoControllerImpl userInfoControllerImpl = UserInfoControllerImpl.this;
                userInfoControllerImpl.reloadUserInfo();
                if (BasicRune.STATUS_LAYOUT_MUM_ICON) {
                    if (Settings.System.getIntForUser(userInfoControllerImpl.mContext.getContentResolver(), "dex_on_external_display", 0, userInfoControllerImpl.mCurrentUserId) != 0) {
                        try {
                            displays = ((DisplayManager) userInfoControllerImpl.mContext.getSystemService("display")).getDisplays();
                        } catch (Exception e) {
                            EmergencyButton$$ExternalSyntheticOutline0.m("ERROR - ", e, "UserInfoController");
                        }
                        if (displays != null) {
                            for (Display display : displays) {
                                if (display.semGetType() == 2) {
                                    break;
                                }
                            }
                            Toast.makeText(userInfoControllerImpl.mContext, R.string.display_disconnected_to_change_user, 1).show();
                        } else {
                            Toast.makeText(userInfoControllerImpl.mContext, R.string.display_disconnected_to_change_user, 1).show();
                        }
                    }
                    userInfoControllerImpl.mCurrentUserId = i;
                }
            }
        };
        this.mUserChangedCallback = callback;
        ?? r2 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.policy.UserInfoControllerImpl.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("android.provider.Contacts.PROFILE_CHANGED".equals(action) || "android.intent.action.USER_INFO_CHANGED".equals(action)) {
                    if (intent.getIntExtra("android.intent.extra.user_handle", getSendingUserId()) == ((UserTrackerImpl) UserInfoControllerImpl.this.mUserTracker).getUserId()) {
                        UserInfoControllerImpl.this.reloadUserInfo();
                    }
                }
            }
        };
        this.mProfileReceiver = r2;
        this.mContext = context;
        this.mUserTracker = userTracker;
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        userTrackerImpl.addCallback(callback, executor);
        if (BasicRune.STATUS_LAYOUT_MUM_ICON) {
            this.mCurrentUserId = userTrackerImpl.getUserId();
        }
        context.registerReceiverAsUser(r2, UserHandle.ALL, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.provider.Contacts.PROFILE_CHANGED", "android.intent.action.USER_INFO_CHANGED"), null, null, 2);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        UserInfoController.OnUserInfoChangedListener onUserInfoChangedListener = (UserInfoController.OnUserInfoChangedListener) obj;
        this.mCallbacks.add(onUserInfoChangedListener);
        onUserInfoChangedListener.onUserInfoChanged(this.mUserName, this.mUserDrawable, this.mUserAccount);
    }

    /* JADX WARN: Type inference failed for: r7v0, types: [android.os.AsyncTask, com.android.systemui.statusbar.policy.UserInfoControllerImpl$3] */
    public final void reloadUserInfo() {
        AnonymousClass3 anonymousClass3 = this.mUserInfoTask;
        if (anonymousClass3 != null) {
            anonymousClass3.cancel(false);
            this.mUserInfoTask = null;
        }
        try {
            UserInfo userInfo = ((UserTrackerImpl) this.mUserTracker).getUserInfo();
            Context contextCreatePackageContextAsUser = this.mContext.createPackageContextAsUser("android", 0, new UserHandle(userInfo.id));
            int i = userInfo.id;
            boolean zIsGuest = userInfo.isGuest();
            String str = userInfo.name;
            boolean z = this.mContext.getThemeResId() != 2132018949;
            Resources resources = this.mContext.getResources();
            ?? r7 = new AsyncTask(str, i, Math.max(resources.getDimensionPixelSize(R.dimen.multi_user_avatar_expanded_size), resources.getDimensionPixelSize(R.dimen.multi_user_avatar_keyguard_size)), contextCreatePackageContextAsUser, zIsGuest, z) { // from class: com.android.systemui.statusbar.policy.UserInfoControllerImpl.3
                public final /* synthetic */ int val$avatarSize;
                public final /* synthetic */ Context val$context;
                public final /* synthetic */ boolean val$isGuest;
                public final /* synthetic */ int val$userId;
                public final /* synthetic */ String val$userName;

                @Override // android.os.AsyncTask
                public final Object doInBackground(Object[] objArr) {
                    Drawable drawable;
                    UserManager userManager = UserManager.get(UserInfoControllerImpl.this.mContext);
                    String string = this.val$userName;
                    Bitmap userIcon = userManager.getUserIcon(this.val$userId);
                    Cursor cursorQuery = null;
                    if (userIcon != null) {
                        UserIconDrawable userIconDrawable = new UserIconDrawable(this.val$avatarSize);
                        userIconDrawable.setIcon(userIcon);
                        userIconDrawable.setBadgeIfManagedUser(this.val$userId, UserInfoControllerImpl.this.mContext);
                        if (userIconDrawable.mSize <= 0) {
                            throw new IllegalStateException("Baking requires an explicit intrinsic size");
                        }
                        int i2 = userIconDrawable.mSize;
                        userIconDrawable.onBoundsChange(new Rect(0, 0, i2, i2));
                        userIconDrawable.rebake();
                        userIconDrawable.mFrameColor = null;
                        userIconDrawable.mFramePaint = null;
                        userIconDrawable.mClearPaint = null;
                        Drawable drawable2 = userIconDrawable.mUserDrawable;
                        if (drawable2 != null) {
                            drawable2.setCallback(null);
                            userIconDrawable.mUserDrawable = null;
                            drawable = userIconDrawable;
                        } else {
                            Bitmap bitmap = userIconDrawable.mUserIcon;
                            drawable = userIconDrawable;
                            if (bitmap != null) {
                                bitmap.recycle();
                                userIconDrawable.mUserIcon = null;
                                drawable = userIconDrawable;
                            }
                        }
                    } else {
                        Drawable drawable3 = this.val$context.getDrawable(R.drawable.default_user_avatar);
                        drawable = drawable3;
                        drawable = drawable3;
                        if (this.val$isGuest && drawable3 != null) {
                            drawable3.setTint(Color.parseColor("#FFFFFFFF"));
                            drawable = drawable3;
                        }
                    }
                    if (userManager.getUsers().size() <= 1) {
                        try {
                            Log.d("UserInfoController", "doInBackground() will call query");
                            cursorQuery = this.val$context.getContentResolver().query(ContactsContract.Profile.CONTENT_URI, new String[]{"_id", "display_name"}, null, null, null);
                        } catch (Exception e) {
                            Log.e("UserInfoController", "queryForUserInformation(doInBackground) userName:" + this.val$userName + ", " + e);
                        }
                        if (cursorQuery != null) {
                            try {
                                if (cursorQuery.moveToFirst()) {
                                    string = cursorQuery.getString(cursorQuery.getColumnIndex("display_name"));
                                }
                                cursorQuery.close();
                            } catch (Throwable th) {
                                cursorQuery.close();
                                throw th;
                            }
                        }
                    }
                    return new UserInfoQueryResult(string, drawable, userManager.getUserAccount(this.val$userId));
                }

                @Override // android.os.AsyncTask
                public final void onPostExecute(Object obj) {
                    UserInfoQueryResult userInfoQueryResult = (UserInfoQueryResult) obj;
                    UserInfoControllerImpl userInfoControllerImpl = UserInfoControllerImpl.this;
                    userInfoControllerImpl.mUserName = userInfoQueryResult.mName;
                    userInfoControllerImpl.mUserDrawable = userInfoQueryResult.mAvatar;
                    userInfoControllerImpl.mUserAccount = userInfoQueryResult.mUserAccount;
                    userInfoControllerImpl.mUserInfoTask = null;
                    ArrayList arrayList = userInfoControllerImpl.mCallbacks;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj2 = arrayList.get(i2);
                        i2++;
                        ((UserInfoController.OnUserInfoChangedListener) obj2).onUserInfoChanged(userInfoControllerImpl.mUserName, userInfoControllerImpl.mUserDrawable, userInfoControllerImpl.mUserAccount);
                    }
                }
            };
            this.mUserInfoTask = r7;
            try {
                r7.execute(new Void[0]);
            } catch (Exception e) {
                Log.e("UserInfoController", "queryForUserInformation(execute) userName:" + str + ", " + e);
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e("UserInfoController", "Couldn't create user context", e2);
            throw new RuntimeException(e2);
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mCallbacks.remove((UserInfoController.OnUserInfoChangedListener) obj);
    }
}
