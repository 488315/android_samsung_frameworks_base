package com.android.systemui.edgelighting.reflection.content;

import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.edgelighting.reflection.AbstractBaseReflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ContextReflection extends AbstractBaseReflection {
    public UserHandleReflection mUserHandle;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
