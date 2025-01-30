package com.android.systemui.p016qs.pipeline.data.repository;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.p016qs.pipeline.shared.TileSpec;
import com.android.systemui.p016qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.retail.data.repository.RetailModeRepository;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TileSpecSettingsRepository implements TileSpecRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineDispatcher backgroundDispatcher;
    public final QSPipelineLogger logger;
    public final MutexImpl mutex;
    public final Resources resources;
    public final RetailModeRepository retailModeRepository;
    public final Lazy retailModeTiles$delegate;
    public final SecureSettings secureSettings;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public TileSpecSettingsRepository(SecureSettings secureSettings, Resources resources, QSPipelineLogger qSPipelineLogger, RetailModeRepository retailModeRepository, CoroutineDispatcher coroutineDispatcher) {
        this.secureSettings = secureSettings;
        this.resources = resources;
        this.logger = qSPipelineLogger;
        this.retailModeRepository = retailModeRepository;
        this.backgroundDispatcher = coroutineDispatcher;
        Symbol symbol = MutexKt.UNLOCK_FAIL;
        this.mutex = new MutexImpl(false);
        this.retailModeTiles$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$retailModeTiles$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                List<String> split$default = StringsKt__StringsKt.split$default(TileSpecSettingsRepository.this.resources.getString(R.string.quick_settings_tiles_retail_mode), new String[]{","}, 0, 6);
                TileSpec.Companion companion = TileSpec.Companion;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(split$default, 10));
                for (String str : split$default) {
                    companion.getClass();
                    arrayList.add(TileSpec.Companion.create(str));
                }
                ArrayList arrayList2 = new ArrayList();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Object next = it.next();
                    if (!(((TileSpec) next) instanceof TileSpec.Invalid)) {
                        arrayList2.add(next);
                    }
                }
                return arrayList2;
            }
        });
    }
}
