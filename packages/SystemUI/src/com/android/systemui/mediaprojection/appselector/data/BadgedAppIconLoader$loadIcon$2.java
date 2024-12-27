package com.android.systemui.mediaprojection.appselector.data;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.launcher3.icons.IconFactory;
import com.android.launcher3.util.UserIconInfo;
import com.android.systemui.mediaprojection.appselector.data.BadgedAppIconLoader;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class BadgedAppIconLoader$loadIcon$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ ComponentName $componentName;
    final /* synthetic */ int $userId;
    final /* synthetic */ RecentTask.UserType $userType;
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    final /* synthetic */ BadgedAppIconLoader this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BadgedAppIconLoader$loadIcon$2(BadgedAppIconLoader badgedAppIconLoader, int i, ComponentName componentName, RecentTask.UserType userType, Continuation continuation) {
        super(2, continuation);
        this.this$0 = badgedAppIconLoader;
        this.$userId = i;
        this.$componentName = componentName;
        this.$userType = userType;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BadgedAppIconLoader$loadIcon$2(this.this$0, this.$userId, this.$componentName, this.$userType, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BadgedAppIconLoader$loadIcon$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        BadgedAppIconLoader badgedAppIconLoader;
        Throwable th;
        AutoCloseable autoCloseable;
        int i;
        RecentTask.UserType userType;
        IconFactory iconFactory;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        int i3 = 1;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            AutoCloseable autoCloseable2 = (AutoCloseable) this.this$0.iconFactoryProvider.get();
            badgedAppIconLoader = this.this$0;
            int i4 = this.$userId;
            ComponentName componentName = this.$componentName;
            RecentTask.UserType userType2 = this.$userType;
            try {
                IconFactory iconFactory2 = (IconFactory) autoCloseable2;
                BasicAppIconLoader basicAppIconLoader = badgedAppIconLoader.basicAppIconLoader;
                this.L$0 = autoCloseable2;
                this.L$1 = badgedAppIconLoader;
                this.L$2 = userType2;
                this.L$3 = iconFactory2;
                this.I$0 = i4;
                this.label = 1;
                BasicPackageManagerAppIconLoader basicPackageManagerAppIconLoader = (BasicPackageManagerAppIconLoader) basicAppIconLoader;
                basicPackageManagerAppIconLoader.getClass();
                Object withContext = BuildersKt.withContext(basicPackageManagerAppIconLoader.backgroundDispatcher, new BasicPackageManagerAppIconLoader$loadIcon$2(basicPackageManagerAppIconLoader, componentName, i4, null), this);
                if (withContext == coroutineSingletons) {
                    return coroutineSingletons;
                }
                i = i4;
                userType = userType2;
                iconFactory = iconFactory2;
                obj = withContext;
                autoCloseable = autoCloseable2;
            } catch (Throwable th2) {
                th = th2;
                autoCloseable = autoCloseable2;
                throw th;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = this.I$0;
            iconFactory = (IconFactory) this.L$3;
            userType = (RecentTask.UserType) this.L$2;
            badgedAppIconLoader = (BadgedAppIconLoader) this.L$1;
            autoCloseable = (AutoCloseable) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th3) {
                th = th3;
                try {
                    throw th;
                } catch (Throwable th4) {
                    AutoCloseableKt.closeFinally(autoCloseable, th);
                    throw th4;
                }
            }
        }
        Drawable drawable = (Drawable) obj;
        if (drawable == null) {
            AutoCloseableKt.closeFinally(autoCloseable, null);
            return null;
        }
        UserHandle of = UserHandle.of(i);
        badgedAppIconLoader.getClass();
        int i5 = BadgedAppIconLoader.WhenMappings.$EnumSwitchMapping$0[userType.ordinal()];
        if (i5 == 1) {
            i3 = 2;
        } else if (i5 != 2) {
            i3 = 3;
            if (i5 != 3) {
                if (i5 != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                i3 = 0;
            }
        }
        BaseIconFactory.IconOptions iconOptions = new BaseIconFactory.IconOptions();
        iconOptions.mUserIconInfo = new UserIconInfo(of, i3);
        FastBitmapDrawable newIcon = iconFactory.createBadgedIconBitmap(drawable, iconOptions).newIcon(0, badgedAppIconLoader.context);
        AutoCloseableKt.closeFinally(autoCloseable, null);
        return newIcon;
    }
}
