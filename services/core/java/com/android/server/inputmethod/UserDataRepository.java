package com.android.server.inputmethod;

import android.content.pm.UserInfo;
import android.os.Handler;
import android.util.SparseArray;

import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.pm.UserManagerInternal;

import java.util.function.IntFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UserDataRepository {
    public final IntFunction mBindingControllerFactory;
    public final SparseArray mUserData = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.inputmethod.UserDataRepository$1, reason: invalid class name */
    public final class AnonymousClass1 implements UserManagerInternal.UserLifecycleListener {
        public final /* synthetic */ Handler val$handler;

        public AnonymousClass1(Handler handler) {
            this.val$handler = handler;
        }

        @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
        public final void onUserCreated(UserInfo userInfo, Object obj) {
            this.val$handler.post(
                    new UserDataRepository$1$$ExternalSyntheticLambda0(this, userInfo.id, 1));
        }

        @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
        public final void onUserRemoved(UserInfo userInfo) {
            this.val$handler.post(
                    new UserDataRepository$1$$ExternalSyntheticLambda0(this, userInfo.id, 0));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserData {
        public final InputMethodBindingController mBindingController;
        public final int mUserId;

        public UserData(int i, InputMethodBindingController inputMethodBindingController) {
            this.mUserId = i;
            this.mBindingController = inputMethodBindingController;
        }

        public final String toString() {
            return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                    new StringBuilder("UserData{mUserId="), this.mUserId, '}');
        }
    }

    public UserDataRepository(
            Handler handler, UserManagerInternal userManagerInternal, IntFunction intFunction) {
        this.mBindingControllerFactory = intFunction;
        userManagerInternal.addUserLifecycleListener(new AnonymousClass1(handler));
    }

    public final UserData getOrCreate(int i) {
        UserData userData = (UserData) this.mUserData.get(i);
        if (userData != null) {
            return userData;
        }
        UserData userData2 =
                new UserData(
                        i, (InputMethodBindingController) this.mBindingControllerFactory.apply(i));
        this.mUserData.put(i, userData2);
        return userData2;
    }
}
