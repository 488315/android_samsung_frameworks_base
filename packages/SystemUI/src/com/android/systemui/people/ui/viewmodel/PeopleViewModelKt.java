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
import java.util.List;
import java.util.regex.Pattern;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class PeopleViewModelKt {
    public static final List PeopleViewModel$priorityTiles(PeopleTileRepository peopleTileRepository, Context context) {
        try {
            List priorityTiles = ((PeopleTileRepositoryImpl) peopleTileRepository).priorityTiles();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(priorityTiles, 10));
            ArrayList arrayList2 = (ArrayList) priorityTiles;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                arrayList.add(toViewModel((PeopleTileModel) obj, context));
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
            ArrayList arrayList2 = (ArrayList) recentTiles;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                arrayList.add(toViewModel((PeopleTileModel) obj, context));
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
        boolean z = peopleTileModel.hasNewStory;
        Icon icon = peopleTileModel.userIcon;
        PeopleTileKey peopleTileKey = peopleTileModel.key;
        Bitmap personIconBitmap = PeopleTileViewHelper.getPersonIconBitmap(context, dimension, z, icon, peopleTileKey.mPackageName, peopleTileKey.mUserId, peopleTileModel.isImportant, peopleTileModel.isDndBlocking);
        personIconBitmap.getClass();
        return new PeopleTileViewModel(peopleTileKey, personIconBitmap, peopleTileModel.username);
    }
}
