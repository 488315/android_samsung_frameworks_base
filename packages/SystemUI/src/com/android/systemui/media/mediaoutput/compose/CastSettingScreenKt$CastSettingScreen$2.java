package com.android.systemui.media.mediaoutput.compose;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.State;
import com.android.systemui.media.mediaoutput.compose.ext.TintDrawablePainter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

final class CastSettingScreenKt$CastSettingScreen$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ State $isSupportSpotifyChromecast$delegate;
    final /* synthetic */ MutableState $seamlessTransferApps$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CastSettingScreenKt$CastSettingScreen$2(Context context, State state, MutableState mutableState, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$isSupportSpotifyChromecast$delegate = state;
        this.$seamlessTransferApps$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CastSettingScreenKt$CastSettingScreen$2(this.$context, this.$isSupportSpotifyChromecast$delegate, this.$seamlessTransferApps$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CastSettingScreenKt$CastSettingScreen$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object failure;
        Object failure2;
        ActivityInfo[] activityInfoArr;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MutableState mutableState = this.$seamlessTransferApps$delegate;
        PackageManager packageManager = this.$context.getPackageManager();
        List<ResolveInfo> queryBroadcastReceivers = packageManager.queryBroadcastReceivers(new Intent("android.intent.action.MEDIA_BUTTON"), 128);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(queryBroadcastReceivers, 10));
        Iterator<T> it = queryBroadcastReceivers.iterator();
        while (it.hasNext()) {
            arrayList.add(((ResolveInfo) it.next()).activityInfo.packageName);
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (true) {
            int i = 0;
            if (!it2.hasNext()) {
                break;
            }
            Object next = it2.next();
            String str = (String) next;
            Intrinsics.checkNotNull(str);
            if (str.length() <= 0) {
                str = null;
            }
            if (str != null) {
                try {
                    int i2 = Result.$r8$clinit;
                    failure2 = packageManager.getPackageInfo(str, 2);
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
                    failure2 = new Result.Failure(th);
                }
                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure2);
                if (m2527exceptionOrNullimpl != null) {
                    m2527exceptionOrNullimpl.printStackTrace();
                }
                PackageInfo packageInfo = (PackageInfo) (failure2 instanceof Result.Failure ? null : failure2);
                if (packageInfo != null && (activityInfoArr = packageInfo.receivers) != null) {
                    int length = activityInfoArr.length;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        if (Intrinsics.areEqual(activityInfoArr[i].name, "androidx.mediarouter.media.MediaTransferReceiver")) {
                            arrayList2.add(next);
                            break;
                        }
                        i++;
                    }
                }
            }
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator it3 = arrayList2.iterator();
        while (it3.hasNext()) {
            String str2 = (String) it3.next();
            try {
                int i4 = Result.$r8$clinit;
                failure = packageManager.getApplicationInfo(str2, 0);
            } catch (Throwable th2) {
                int i5 = Result.$r8$clinit;
                failure = new Result.Failure(th2);
            }
            if (failure instanceof Result.Failure) {
                failure = null;
            }
            ApplicationInfo applicationInfo = (ApplicationInfo) failure;
            if (applicationInfo != null) {
                arrayList3.add(applicationInfo);
            }
        }
        ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it4 = arrayList3.iterator();
        while (it4.hasNext()) {
            ApplicationInfo applicationInfo2 = (ApplicationInfo) it4.next();
            TintDrawablePainter.Companion companion = TintDrawablePainter.Companion;
            Drawable applicationIcon = packageManager.getApplicationIcon(applicationInfo2);
            companion.getClass();
            arrayList4.add(new Pair(TintDrawablePainter.Companion.toConverter(applicationIcon), packageManager.getApplicationLabel(applicationInfo2).toString()));
        }
        if (Intrinsics.areEqual((Boolean) this.$isSupportSpotifyChromecast$delegate.getValue(), Boolean.FALSE)) {
            ArrayList arrayList5 = new ArrayList();
            Iterator it5 = arrayList4.iterator();
            while (it5.hasNext()) {
                Object next2 = it5.next();
                if (!Intrinsics.areEqual((String) ((Pair) next2).component2(), "Spotify")) {
                    arrayList5.add(next2);
                }
            }
            arrayList4 = arrayList5;
        }
        Object obj2 = arrayList4.isEmpty() ^ true ? arrayList4 : null;
        if (obj2 == null) {
            obj2 = EmptyList.INSTANCE;
        }
        mutableState.setValue(obj2);
        return Unit.INSTANCE;
    }
}
