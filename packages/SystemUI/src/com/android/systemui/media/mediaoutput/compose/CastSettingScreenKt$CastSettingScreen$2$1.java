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
import androidx.compose.ui.graphics.painter.Painter;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaCustom;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
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

/* loaded from: classes2.dex */
final class CastSettingScreenKt$CastSettingScreen$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ MutableState<List<Pair<Painter, String>>> $seamlessTransferApps$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CastSettingScreenKt$CastSettingScreen$2$1(Context context, MutableState<List<Pair<Painter, String>>> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$seamlessTransferApps$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CastSettingScreenKt$CastSettingScreen$2$1(this.$context, this.$seamlessTransferApps$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CastSettingScreenKt$CastSettingScreen$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v1, types: [kotlin.Result$Failure] */
    /* JADX WARN: Type inference failed for: r9v2, types: [kotlin.Result$Failure] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ApplicationInfo failure;
        PackageInfo failure2;
        ActivityInfo[] activityInfoArr;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MutableState<List<Pair<Painter, String>>> mutableState = this.$seamlessTransferApps$delegate;
        PackageManager packageManager = this.$context.getPackageManager();
        List<ResolveInfo> listQueryBroadcastReceivers = packageManager.queryBroadcastReceivers(new Intent("android.intent.action.MEDIA_BUTTON"), 128);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listQueryBroadcastReceivers, 10));
        Iterator it = listQueryBroadcastReceivers.iterator();
        while (it.hasNext()) {
            arrayList.add(((ResolveInfo) it.next()).activityInfo.packageName);
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            Object obj2 = arrayList.get(i2);
            i2++;
            String str = (String) obj2;
            str.getClass();
            if (str.length() <= 0) {
                str = null;
            }
            if (str != null) {
                try {
                    int i3 = Result.$r8$clinit;
                    failure2 = packageManager.getPackageInfo(str, 2);
                } catch (Throwable th) {
                    int i4 = Result.$r8$clinit;
                    failure2 = new Result.Failure(th);
                }
                Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(failure2);
                if (thM3442exceptionOrNullimpl != null) {
                    thM3442exceptionOrNullimpl.printStackTrace();
                }
                PackageInfo packageInfo = failure2 instanceof Result.Failure ? null : failure2;
                if (packageInfo != null && (activityInfoArr = packageInfo.receivers) != null) {
                    int length = activityInfoArr.length;
                    int i5 = 0;
                    while (true) {
                        if (i5 >= length) {
                            break;
                        }
                        if (Intrinsics.areEqual(activityInfoArr[i5].name, "androidx.mediarouter.media.MediaTransferReceiver")) {
                            arrayList2.add(obj2);
                            break;
                        }
                        i5++;
                    }
                }
            }
        }
        ArrayList arrayList3 = new ArrayList();
        int size2 = arrayList2.size();
        int i6 = 0;
        while (i6 < size2) {
            Object obj3 = arrayList2.get(i6);
            i6++;
            String str2 = (String) obj3;
            try {
                int i7 = Result.$r8$clinit;
                failure = packageManager.getApplicationInfo(str2, 0);
            } catch (Throwable th2) {
                int i8 = Result.$r8$clinit;
                failure = new Result.Failure(th2);
            }
            if (failure instanceof Result.Failure) {
                failure = null;
            }
            ApplicationInfo applicationInfo = failure;
            if (applicationInfo != null) {
                arrayList3.add(applicationInfo);
            }
        }
        ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
        int size3 = arrayList3.size();
        while (i < size3) {
            Object obj4 = arrayList3.get(i);
            i++;
            ApplicationInfo applicationInfo2 = (ApplicationInfo) obj4;
            TintDrawablePainter.Companion companion = TintDrawablePainter.Companion;
            Drawable applicationIcon = packageManager.getApplicationIcon(applicationInfo2);
            companion.getClass();
            arrayList4.add(new Pair(TintDrawablePainter.Companion.toConverter(applicationIcon), packageManager.getApplicationLabel(applicationInfo2).toString()));
        }
        List list = arrayList4.isEmpty() ? null : arrayList4;
        if (list != null) {
            MoSaLogging moSaLogging = MoSaLogging.INSTANCE;
            SaEvent.NumberOfApps numberOfApps = SaEvent.NumberOfApps.INSTANCE;
            SaCustom[] saCustomArr = {new SaCustom.Number(list.size())};
            moSaLogging.getClass();
            MoSaLogging.send(numberOfApps, saCustomArr);
        } else {
            list = EmptyList.INSTANCE;
        }
        mutableState.setValue(list);
        return Unit.INSTANCE;
    }
}
