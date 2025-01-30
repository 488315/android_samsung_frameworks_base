package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.ContactsContract;
import android.util.Log;
import com.android.internal.util.UserIcons;
import com.android.settingslib.drawable.UserIconDrawable;
import com.android.systemui.R;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.UserInfoController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UserInfoControllerImpl implements UserInfoController {
    public final ArrayList mCallbacks = new ArrayList();
    public final Context mContext;
    public final C34472 mProfileReceiver;
    public String mUserAccount;
    public final UserTracker.Callback mUserChangedCallback;
    public Drawable mUserDrawable;
    public AsyncTask mUserInfoTask;
    public String mUserName;
    public final UserTracker mUserTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UserInfoQueryResult {
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
                UserInfoControllerImpl.this.reloadUserInfo();
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
        ((UserTrackerImpl) userTracker).addCallback(callback, executor);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Contacts.PROFILE_CHANGED");
        intentFilter.addAction("android.intent.action.USER_INFO_CHANGED");
        context.registerReceiverAsUser(r2, UserHandle.ALL, intentFilter, null, null, 2);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        UserInfoController.OnUserInfoChangedListener onUserInfoChangedListener = (UserInfoController.OnUserInfoChangedListener) obj;
        this.mCallbacks.add(onUserInfoChangedListener);
        onUserInfoChangedListener.onUserInfoChanged(this.mUserName, this.mUserDrawable, this.mUserAccount);
    }

    public final void reloadUserInfo() {
        AsyncTask asyncTask = this.mUserInfoTask;
        if (asyncTask != null) {
            asyncTask.cancel(false);
            this.mUserInfoTask = null;
        }
        Context context = this.mContext;
        try {
            UserInfo userInfo = ((UserTrackerImpl) this.mUserTracker).getUserInfo();
            final Context createPackageContextAsUser = context.createPackageContextAsUser("android", 0, new UserHandle(userInfo.id));
            final int i = userInfo.id;
            final boolean isGuest = userInfo.isGuest();
            final String str = userInfo.name;
            final boolean z = context.getThemeResId() != 2132018537;
            Resources resources = context.getResources();
            final int max = Math.max(resources.getDimensionPixelSize(R.dimen.multi_user_avatar_expanded_size), resources.getDimensionPixelSize(R.dimen.multi_user_avatar_keyguard_size));
            AsyncTask asyncTask2 = new AsyncTask() { // from class: com.android.systemui.statusbar.policy.UserInfoControllerImpl.3
                @Override // android.os.AsyncTask
                public final Object doInBackground(Object[] objArr) {
                    Drawable drawable;
                    Cursor query;
                    UserManager userManager = UserManager.get(UserInfoControllerImpl.this.mContext);
                    String str2 = str;
                    Bitmap userIcon = userManager.getUserIcon(i);
                    if (userIcon != null) {
                        UserIconDrawable userIconDrawable = new UserIconDrawable(max);
                        userIconDrawable.setIcon(userIcon);
                        userIconDrawable.setBadgeIfManagedUser(i, UserInfoControllerImpl.this.mContext);
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
                        drawable = UserIcons.getDefaultUserIcon(createPackageContextAsUser.getResources(), isGuest ? -10000 : i, z);
                    }
                    if (userManager.getUsers().size() <= 1 && (query = createPackageContextAsUser.getContentResolver().query(ContactsContract.Profile.CONTENT_URI, new String[]{"_id", "display_name"}, null, null, null)) != null) {
                        try {
                            if (query.moveToFirst()) {
                                str2 = query.getString(query.getColumnIndex("display_name"));
                            }
                        } finally {
                            query.close();
                        }
                    }
                    return new UserInfoQueryResult(str2, drawable, userManager.getUserAccount(i));
                }

                @Override // android.os.AsyncTask
                public final void onPostExecute(Object obj) {
                    UserInfoQueryResult userInfoQueryResult = (UserInfoQueryResult) obj;
                    UserInfoControllerImpl userInfoControllerImpl = UserInfoControllerImpl.this;
                    userInfoControllerImpl.mUserName = userInfoQueryResult.mName;
                    userInfoControllerImpl.mUserDrawable = userInfoQueryResult.mAvatar;
                    userInfoControllerImpl.mUserAccount = userInfoQueryResult.mUserAccount;
                    userInfoControllerImpl.mUserInfoTask = null;
                    Iterator it = userInfoControllerImpl.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((UserInfoController.OnUserInfoChangedListener) it.next()).onUserInfoChanged(userInfoControllerImpl.mUserName, userInfoControllerImpl.mUserDrawable, userInfoControllerImpl.mUserAccount);
                    }
                }
            };
            this.mUserInfoTask = asyncTask2;
            asyncTask2.execute(new Void[0]);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("UserInfoController", "Couldn't create user context", e);
            throw new RuntimeException(e);
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mCallbacks.remove((UserInfoController.OnUserInfoChangedListener) obj);
    }
}
