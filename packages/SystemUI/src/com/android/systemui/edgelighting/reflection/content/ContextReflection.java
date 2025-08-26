package com.android.systemui.edgelighting.reflection.content;

import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.edgelighting.reflection.AbstractBaseReflection;

/* loaded from: classes2.dex */
public class ContextReflection extends AbstractBaseReflection {
    public UserHandleReflection mUserHandle;

    public class UserHandleReflection extends AbstractBaseReflection {
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

    public final Context createPackageContextAsUser(Object obj, String str) throws NoSuchMethodException, SecurityException {
        Class cls = Integer.TYPE;
        int i = 0;
        Class[] clsArr = {cls};
        if (this.mUserHandle == null) {
            this.mUserHandle = new UserHandleReflection(i);
        }
        Object objInvokeNormalMethod = invokeNormalMethod(obj, "createPackageContextAsUser", new Class[]{String.class, cls, UserHandle.class}, str, 3, this.mUserHandle.createInstance(clsArr, 0));
        if (objInvokeNormalMethod != null) {
            return (Context) objInvokeNormalMethod;
        }
        return null;
    }

    @Override // com.android.systemui.edgelighting.reflection.AbstractBaseReflection
    public final String getBaseClassName() {
        return "android.content.Context";
    }
}
