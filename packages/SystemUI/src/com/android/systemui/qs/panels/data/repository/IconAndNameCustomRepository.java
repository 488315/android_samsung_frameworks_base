package com.android.systemui.qs.panels.data.repository;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.qs.panels.shared.model.EditTileData;
import com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepository;
import com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class IconAndNameCustomRepository {
    public final CoroutineContext backgroundContext;
    public final InstalledTilesComponentRepository installedTilesComponentRepository;
    public final UserTracker userTracker;

    /* renamed from: com.android.systemui.qs.panels.data.repository.IconAndNameCustomRepository$getCustomTileData$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return IconAndNameCustomRepository.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            List list;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            IconAndNameCustomRepository iconAndNameCustomRepository = IconAndNameCustomRepository.this;
            InstalledTilesComponentRepository installedTilesComponentRepository = iconAndNameCustomRepository.installedTilesComponentRepository;
            int userId = ((UserTrackerImpl) iconAndNameCustomRepository.userTracker).getUserId();
            InstalledTilesComponentRepositoryImpl installedTilesComponentRepositoryImpl = (InstalledTilesComponentRepositoryImpl) installedTilesComponentRepository;
            synchronized (installedTilesComponentRepositoryImpl.userMap) {
                list = (List) installedTilesComponentRepositoryImpl.getForUserLocked(userId).getValue();
            }
            PackageManager packageManager = ((UserTrackerImpl) IconAndNameCustomRepository.this.userTracker).getUserContext().getPackageManager();
            List<ServiceInfo> list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            for (ServiceInfo serviceInfo : list2) {
                TileSpec.Companion companion = TileSpec.Companion;
                ComponentName componentName = serviceInfo.getComponentName();
                companion.getClass();
                TileSpec.CustomTileSpec customTileSpecCreate = TileSpec.Companion.create(componentName);
                CharSequence charSequenceLoadLabel = serviceInfo.loadLabel(packageManager);
                Drawable drawableLoadIcon = serviceInfo.loadIcon(packageManager);
                arrayList.add(drawableLoadIcon != null ? new EditTileData(customTileSpecCreate, new Icon.Loaded(drawableLoadIcon, new ContentDescription.Loaded(charSequenceLoadLabel.toString()), null, 4, null), new Text.Loaded(charSequenceLoadLabel.toString()), new Text.Loaded(serviceInfo.applicationInfo.loadLabel(packageManager).toString()), TileCategory.PROVIDED_BY_APP) : null);
            }
            return CollectionsKt___CollectionsKt.filterNotNull(arrayList);
        }
    }

    public IconAndNameCustomRepository(InstalledTilesComponentRepository installedTilesComponentRepository, UserTracker userTracker, CoroutineContext coroutineContext) {
        this.installedTilesComponentRepository = installedTilesComponentRepository;
        this.userTracker = userTracker;
        this.backgroundContext = coroutineContext;
    }

    public final Object getCustomTileData(Continuation continuation) {
        return BuildersKt.withContext(this.backgroundContext, new AnonymousClass2(null), continuation);
    }
}
