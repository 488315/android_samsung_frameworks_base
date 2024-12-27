package com.android.systemui.edgelighting.reflection.content;

import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.edgelighting.reflection.AbstractBaseReflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ContextReflection extends AbstractBaseReflection {
    public UserHandleReflection mUserHandle;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class UserHandleReflection extends AbstractBaseReflection {
        public /* synthetic */ UserHandleReflection(int i) {
            this();
        }

        @Override // com.android.systemui.edgelighting.reflection.AbstractBaseReflection
        public final String getBaseClassName() {
            return "android.os.UserHandle";
        }

        private UserHandleReflection() {
        }
    }

    public final Context createPackageContextAsUser(Object obj, String str) {
        Class cls = Integer.TYPE;
        int i = 0;
        Class[] clsArr = {cls};
        if (this.mUserHandle == null) {
            this.mUserHandle = new UserHandleReflection(i);
        }
        Object invokeNormalMethod = invokeNormalMethod(obj, "createPackageContextAsUser", new Class[]{String.class, cls, UserHandle.class}, str, 3, this.mUserHandle.createInstance(clsArr, 0));
        if (invokeNormalMethod != null) {
            return (Context) invokeNormalMethod;
        }
        return null;
    }

    @Override // com.android.systemui.edgelighting.reflection.AbstractBaseReflection
    public final String getBaseClassName() {
        return "android.content.Context";
    }
}
