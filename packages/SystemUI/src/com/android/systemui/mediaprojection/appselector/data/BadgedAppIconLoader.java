package com.android.systemui.mediaprojection.appselector.data;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.launcher3.icons.IconFactory;
import com.android.launcher3.util.UserIconInfo;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import javax.inject.Provider;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class BadgedAppIconLoader {
    public final CoroutineDispatcher backgroundDispatcher;
    public final BasicAppIconLoader basicAppIconLoader;
    public final Context context;
    public final Provider iconFactoryProvider;

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

    /* renamed from: com.android.systemui.mediaprojection.appselector.data.BadgedAppIconLoader$loadIcon$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ ComponentName $componentName;
        final /* synthetic */ int $userId;
        final /* synthetic */ RecentTask.UserType $userType;
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(int i, ComponentName componentName, RecentTask.UserType userType, Continuation continuation) {
            super(2, continuation);
            this.$userId = i;
            this.$componentName = componentName;
            this.$userType = userType;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BadgedAppIconLoader.this.new AnonymousClass2(this.$userId, this.$componentName, this.$userType, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x006e  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0072 A[Catch: all -> 0x0020, TRY_ENTER, TryCatch #0 {all -> 0x0020, blocks: (B:6:0x001c, B:17:0x006a, B:21:0x0072, B:29:0x008f, B:30:0x0094, B:32:0x0096), top: B:42:0x001c }] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Exception {
            BadgedAppIconLoader badgedAppIconLoader;
            Throwable th;
            AutoCloseable autoCloseable;
            int i;
            RecentTask.UserType userType;
            IconFactory iconFactory;
            Drawable drawable;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = this.label;
            int i3 = 1;
            if (i2 != 0) {
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
                    drawable = (Drawable) obj;
                    if (drawable != null) {
                        AutoCloseableKt.closeFinally(autoCloseable, null);
                        return null;
                    }
                    UserHandle userHandleOf = UserHandle.of(i);
                    badgedAppIconLoader.getClass();
                    int i4 = WhenMappings.$EnumSwitchMapping$0[userType.ordinal()];
                    if (i4 == 1) {
                        i3 = 2;
                    } else if (i4 != 2) {
                        i3 = 3;
                        if (i4 != 3) {
                            if (i4 != 4) {
                                throw new NoWhenBranchMatchedException();
                            }
                            i3 = 0;
                        }
                    }
                    BaseIconFactory.IconOptions iconOptions = new BaseIconFactory.IconOptions();
                    iconOptions.mUserIconInfo = new UserIconInfo(userHandleOf, i3);
                    FastBitmapDrawable fastBitmapDrawableNewIcon$1 = iconFactory.createBadgedIconBitmap(drawable, iconOptions).newIcon$1(0, badgedAppIconLoader.context);
                    AutoCloseableKt.closeFinally(autoCloseable, null);
                    return fastBitmapDrawableNewIcon$1;
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        throw th;
                    } catch (Throwable th3) {
                        AutoCloseableKt.closeFinally(autoCloseable, th);
                        throw th3;
                    }
                }
            }
            ResultKt.throwOnFailure(obj);
            AutoCloseable autoCloseable2 = (AutoCloseable) BadgedAppIconLoader.this.iconFactoryProvider.get();
            badgedAppIconLoader = BadgedAppIconLoader.this;
            int i5 = this.$userId;
            ComponentName componentName = this.$componentName;
            RecentTask.UserType userType2 = this.$userType;
            try {
                IconFactory iconFactory2 = (IconFactory) autoCloseable2;
                BasicAppIconLoader basicAppIconLoader = badgedAppIconLoader.basicAppIconLoader;
                this.L$0 = autoCloseable2;
                this.L$1 = badgedAppIconLoader;
                this.L$2 = userType2;
                this.L$3 = iconFactory2;
                this.I$0 = i5;
                this.label = 1;
                BasicPackageManagerAppIconLoader basicPackageManagerAppIconLoader = (BasicPackageManagerAppIconLoader) basicAppIconLoader;
                basicPackageManagerAppIconLoader.getClass();
                Object objWithContext = BuildersKt.withContext(basicPackageManagerAppIconLoader.backgroundDispatcher, new BasicPackageManagerAppIconLoader$loadIcon$2(basicPackageManagerAppIconLoader, componentName, i5, null), this);
                if (objWithContext == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj = objWithContext;
                autoCloseable = autoCloseable2;
                i = i5;
                userType = userType2;
                iconFactory = iconFactory2;
                drawable = (Drawable) obj;
                if (drawable != null) {
                }
            } catch (Throwable th4) {
                th = th4;
                autoCloseable = autoCloseable2;
                throw th;
            }
        }
    }

    public BadgedAppIconLoader(BasicAppIconLoader basicAppIconLoader, CoroutineDispatcher coroutineDispatcher, Context context, Provider provider) {
        this.basicAppIconLoader = basicAppIconLoader;
        this.backgroundDispatcher = coroutineDispatcher;
        this.context = context;
        this.iconFactoryProvider = provider;
    }

    public final Object loadIcon(int i, RecentTask.UserType userType, ComponentName componentName, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AnonymousClass2(i, componentName, userType, null), continuation);
    }
}
