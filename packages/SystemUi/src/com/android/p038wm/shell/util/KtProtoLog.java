package com.android.p038wm.shell.util;

import android.util.Log;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KtProtoLog {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: d */
        public static void m234d(ShellProtoLogGroup shellProtoLogGroup, String str, Object... objArr) {
            if (ShellProtoLogImpl.isEnabled(shellProtoLogGroup)) {
                String tag = shellProtoLogGroup.getTag();
                int i = StringCompanionObject.$r8$clinit;
                Object[] copyOf = Arrays.copyOf(objArr, objArr.length);
                Log.d(tag, String.format(str, Arrays.copyOf(copyOf, copyOf.length)));
            }
        }

        /* renamed from: v */
        public static void m235v(ShellProtoLogGroup shellProtoLogGroup, String str, Object... objArr) {
            if (ShellProtoLogImpl.isEnabled(shellProtoLogGroup)) {
                shellProtoLogGroup.getTag();
                int i = StringCompanionObject.$r8$clinit;
                Object[] copyOf = Arrays.copyOf(objArr, objArr.length);
                String.format(str, Arrays.copyOf(copyOf, copyOf.length));
            }
        }

        /* renamed from: w */
        public static void m236w(ShellProtoLogGroup shellProtoLogGroup, String str, Object... objArr) {
            if (ShellProtoLogImpl.isEnabled(shellProtoLogGroup)) {
                String tag = shellProtoLogGroup.getTag();
                int i = StringCompanionObject.$r8$clinit;
                Object[] copyOf = Arrays.copyOf(objArr, objArr.length);
                Log.w(tag, String.format(str, Arrays.copyOf(copyOf, copyOf.length)));
            }
        }
    }
}
