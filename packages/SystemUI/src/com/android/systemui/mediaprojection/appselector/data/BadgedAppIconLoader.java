package com.android.systemui.mediaprojection.appselector.data;

import android.content.ComponentName;
import android.content.Context;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import javax.inject.Provider;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BadgedAppIconLoader {
    public final CoroutineDispatcher backgroundDispatcher;
    public final BasicAppIconLoader basicAppIconLoader;
    public final Context context;
    public final Provider iconFactoryProvider;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[RecentTask.UserType.values().length];
            try {
                iArr[RecentTask.UserType.CLONED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[RecentTask.UserType.WORK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[RecentTask.UserType.PRIVATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[RecentTask.UserType.STANDARD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public BadgedAppIconLoader(BasicAppIconLoader basicAppIconLoader, CoroutineDispatcher coroutineDispatcher, Context context, Provider provider) {
        this.basicAppIconLoader = basicAppIconLoader;
        this.backgroundDispatcher = coroutineDispatcher;
        this.context = context;
        this.iconFactoryProvider = provider;
    }

    public final Object loadIcon(int i, RecentTask.UserType userType, ComponentName componentName, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new BadgedAppIconLoader$loadIcon$2(this, i, componentName, userType, null), continuation);
    }
}
