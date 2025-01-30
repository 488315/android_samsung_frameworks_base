package com.android.systemui.mediaprojection.appselector.data;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.launcher3.icons.IconFactory;
import com.android.systemui.shared.system.PackageManagerWrapper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.mediaprojection.appselector.data.IconLoaderLibAppIconLoader$loadIcon$2", m277f = "AppIconLoader.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class IconLoaderLibAppIconLoader$loadIcon$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ ComponentName $component;
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ IconLoaderLibAppIconLoader this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IconLoaderLibAppIconLoader$loadIcon$2(IconLoaderLibAppIconLoader iconLoaderLibAppIconLoader, ComponentName componentName, int i, Continuation<? super IconLoaderLibAppIconLoader$loadIcon$2> continuation) {
        super(2, continuation);
        this.this$0 = iconLoaderLibAppIconLoader;
        this.$component = componentName;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new IconLoaderLibAppIconLoader$loadIcon$2(this.this$0, this.$component, this.$userId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((IconLoaderLibAppIconLoader$loadIcon$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ActivityInfo activityInfo;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        AutoCloseable autoCloseable = (AutoCloseable) this.this$0.iconFactoryProvider.get();
        IconLoaderLibAppIconLoader iconLoaderLibAppIconLoader = this.this$0;
        ComponentName componentName = this.$component;
        int i = this.$userId;
        try {
            IconFactory iconFactory = (IconFactory) autoCloseable;
            iconLoaderLibAppIconLoader.packageManagerWrapper.getClass();
            try {
                activityInfo = PackageManagerWrapper.mIPackageManager.getActivityInfo(componentName, 128L, i);
            } catch (RemoteException e) {
                e.printStackTrace();
                activityInfo = null;
            }
            if (activityInfo == null) {
                AutoCloseableKt.closeFinally(autoCloseable, null);
                return null;
            }
            Drawable loadIcon = activityInfo.loadIcon(iconLoaderLibAppIconLoader.packageManager);
            if (loadIcon == null) {
                AutoCloseableKt.closeFinally(autoCloseable, null);
                return null;
            }
            UserHandle of = UserHandle.of(i);
            BaseIconFactory.IconOptions iconOptions = new BaseIconFactory.IconOptions();
            iconOptions.mUserHandle = of;
            FastBitmapDrawable newIcon = iconFactory.createBadgedIconBitmap(loadIcon, iconOptions).newIcon(iconLoaderLibAppIconLoader.context);
            AutoCloseableKt.closeFinally(autoCloseable, null);
            return newIcon;
        } finally {
        }
    }
}
