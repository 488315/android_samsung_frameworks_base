package com.android.systemui.people.ui.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.people.PeopleTileViewHelper;
import com.android.systemui.people.data.model.PeopleTileModel;
import com.android.systemui.people.data.repository.PeopleTileRepository;
import com.android.systemui.people.data.repository.PeopleTileRepositoryImpl;
import com.android.systemui.people.widget.PeopleTileKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class PeopleViewModelKt {
    public static final List PeopleViewModel$priorityTiles(PeopleTileRepository peopleTileRepository, Context context) {
        try {
            List priorityTiles = ((PeopleTileRepositoryImpl) peopleTileRepository).priorityTiles();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(priorityTiles, 10));
            Iterator it = priorityTiles.iterator();
            while (it.hasNext()) {
                arrayList.add(toViewModel((PeopleTileModel) it.next(), context));
            }
            return arrayList;
        } catch (Exception e) {
            Log.e("PeopleViewModel", "Couldn't retrieve priority conversations", e);
            return EmptyList.INSTANCE;
        }
    }

    public static final List PeopleViewModel$recentTiles(PeopleTileRepository peopleTileRepository, Context context) {
        try {
            List recentTiles = ((PeopleTileRepositoryImpl) peopleTileRepository).recentTiles();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(recentTiles, 10));
            Iterator it = recentTiles.iterator();
            while (it.hasNext()) {
                arrayList.add(toViewModel((PeopleTileModel) it.next(), context));
            }
            return arrayList;
        } catch (Exception e) {
            Log.e("PeopleViewModel", "Couldn't retrieve recent conversations", e);
            return EmptyList.INSTANCE;
        }
    }

    public static final PeopleTileViewModel toViewModel(PeopleTileModel peopleTileModel, Context context) {
        float f = context.getResources().getDisplayMetrics().density;
        Pattern pattern = PeopleTileViewHelper.DOUBLE_EXCLAMATION_PATTERN;
        int dimension = (int) (context.getResources().getDimension(R.dimen.avatar_size_for_medium) / f);
        Icon icon = peopleTileModel.userIcon;
        PeopleTileKey peopleTileKey = peopleTileModel.key;
        Bitmap personIconBitmap = PeopleTileViewHelper.getPersonIconBitmap(context, dimension, peopleTileModel.hasNewStory, icon, peopleTileKey.mPackageName, peopleTileKey.mUserId, peopleTileModel.isImportant, peopleTileModel.isDndBlocking);
        Intrinsics.checkNotNull(personIconBitmap);
        return new PeopleTileViewModel(peopleTileKey, personIconBitmap, peopleTileModel.username);
    }
}
