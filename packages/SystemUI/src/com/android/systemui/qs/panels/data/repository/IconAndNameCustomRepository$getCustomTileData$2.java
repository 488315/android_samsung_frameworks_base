package com.android.systemui.qs.panels.data.repository;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.panels.shared.model.EditTileData;
import com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepository;
import com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class IconAndNameCustomRepository$getCustomTileData$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ IconAndNameCustomRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IconAndNameCustomRepository$getCustomTileData$2(IconAndNameCustomRepository iconAndNameCustomRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = iconAndNameCustomRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new IconAndNameCustomRepository$getCustomTileData$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((IconAndNameCustomRepository$getCustomTileData$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        List list;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        IconAndNameCustomRepository iconAndNameCustomRepository = this.this$0;
        InstalledTilesComponentRepository installedTilesComponentRepository = iconAndNameCustomRepository.installedTilesComponentRepository;
        int userId = ((UserTrackerImpl) iconAndNameCustomRepository.userTracker).getUserId();
        InstalledTilesComponentRepositoryImpl installedTilesComponentRepositoryImpl = (InstalledTilesComponentRepositoryImpl) installedTilesComponentRepository;
        synchronized (installedTilesComponentRepositoryImpl.userMap) {
            list = (List) installedTilesComponentRepositoryImpl.getForUserLocked(userId).getValue();
        }
        PackageManager packageManager = ((UserTrackerImpl) this.this$0.userTracker).getUserContext().getPackageManager();
        List<ServiceInfo> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (ServiceInfo serviceInfo : list2) {
            TileSpec.Companion companion = TileSpec.Companion;
            ComponentName componentName = serviceInfo.getComponentName();
            companion.getClass();
            TileSpec.CustomTileSpec customTileSpec = new TileSpec.CustomTileSpec(CustomTile.toSpec(componentName), componentName);
            CharSequence loadLabel = serviceInfo.loadLabel(packageManager);
            Drawable loadIcon = serviceInfo.loadIcon(packageManager);
            arrayList.add(loadIcon != null ? new EditTileData(customTileSpec, new Icon.Loaded(loadIcon, new ContentDescription.Loaded(loadLabel.toString())), new Text.Loaded(loadLabel.toString()), new Text.Loaded(serviceInfo.applicationInfo.loadLabel(packageManager).toString())) : null);
        }
        return CollectionsKt___CollectionsKt.filterNotNull(arrayList);
    }
}
