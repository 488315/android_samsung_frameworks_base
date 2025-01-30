package com.android.systemui.people.data.repository;

import android.app.people.IPeopleManager;
import android.app.people.PeopleSpaceTile;
import com.android.systemui.people.PeopleSpaceUtils;
import com.android.systemui.people.PeopleTileViewHelper;
import com.android.systemui.people.data.model.PeopleTileModel;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda2;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda4;
import com.android.systemui.people.widget.PeopleTileKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import kotlin.collections.CollectionsKt__IterablesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PeopleTileRepositoryImpl implements PeopleTileRepository {
    public final PeopleSpaceWidgetManager peopleSpaceWidgetManager;

    public PeopleTileRepositoryImpl(PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        this.peopleSpaceWidgetManager = peopleSpaceWidgetManager;
    }

    public static PeopleTileModel toModel(PeopleSpaceTile peopleSpaceTile) {
        return new PeopleTileModel(new PeopleTileKey(peopleSpaceTile), peopleSpaceTile.getUserName().toString(), peopleSpaceTile.getUserIcon(), PeopleTileViewHelper.getHasNewStory(peopleSpaceTile), peopleSpaceTile.isImportantConversation(), PeopleTileViewHelper.isDndBlockingTileData(peopleSpaceTile));
    }

    public final List priorityTiles() {
        PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.peopleSpaceWidgetManager;
        List sortedTiles = PeopleSpaceUtils.getSortedTiles(peopleSpaceWidgetManager.mIPeopleManager, peopleSpaceWidgetManager.mLauncherApps, peopleSpaceWidgetManager.mUserManager, peopleSpaceWidgetManager.mINotificationManager.getConversations(true).getList().stream().filter(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda2(1)).map(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda4(1)));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(sortedTiles, 10));
        Iterator it = sortedTiles.iterator();
        while (it.hasNext()) {
            arrayList.add(toModel((PeopleSpaceTile) it.next()));
        }
        return arrayList;
    }

    public final List recentTiles() {
        PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.peopleSpaceWidgetManager;
        Stream map = peopleSpaceWidgetManager.mINotificationManager.getConversations(false).getList().stream().filter(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda2(2)).map(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda4(2));
        IPeopleManager iPeopleManager = peopleSpaceWidgetManager.mIPeopleManager;
        List sortedTiles = PeopleSpaceUtils.getSortedTiles(iPeopleManager, peopleSpaceWidgetManager.mLauncherApps, peopleSpaceWidgetManager.mUserManager, Stream.concat(map, iPeopleManager.getRecentConversations().getList().stream().map(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda4(3))));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(sortedTiles, 10));
        Iterator it = sortedTiles.iterator();
        while (it.hasNext()) {
            arrayList.add(toModel((PeopleSpaceTile) it.next()));
        }
        return arrayList;
    }
}
