package com.android.systemui.people.data.repository;

import android.app.people.PeopleSpaceTile;
import android.graphics.drawable.Icon;
import com.android.systemui.people.PeopleSpaceUtils;
import com.android.systemui.people.PeopleTileViewHelper;
import com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda0;
import com.android.systemui.people.data.model.PeopleTileModel;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda1;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda4;
import com.android.systemui.people.widget.PeopleTileKey;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.Intrinsics;

public final class PeopleTileRepositoryImpl implements PeopleTileRepository {
    public final PeopleSpaceWidgetManager peopleSpaceWidgetManager;

    public PeopleTileRepositoryImpl(PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        this.peopleSpaceWidgetManager = peopleSpaceWidgetManager;
    }

    public static PeopleTileModel toModel(PeopleSpaceTile peopleSpaceTile) {
        PeopleTileKey peopleTileKey = new PeopleTileKey(peopleSpaceTile);
        String obj = peopleSpaceTile.getUserName().toString();
        Icon userIcon = peopleSpaceTile.getUserIcon();
        Pattern pattern = PeopleTileViewHelper.DOUBLE_EXCLAMATION_PATTERN;
        return new PeopleTileModel(peopleTileKey, obj, userIcon, peopleSpaceTile.getStatuses() != null && peopleSpaceTile.getStatuses().stream().anyMatch(new PeopleTileViewHelper$$ExternalSyntheticLambda0(2)), peopleSpaceTile.isImportantConversation(), PeopleTileViewHelper.isDndBlockingTileData(peopleSpaceTile));
    }

    public final List priorityTiles() {
        PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.peopleSpaceWidgetManager;
        List<PeopleSpaceTile> sortedTiles = PeopleSpaceUtils.getSortedTiles(peopleSpaceWidgetManager.mIPeopleManager, peopleSpaceWidgetManager.mLauncherApps, peopleSpaceWidgetManager.mUserManager, peopleSpaceWidgetManager.mINotificationManager.getConversations(true).getList().stream().filter(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda4(1)).map(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda1(4)));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(sortedTiles, 10));
        for (PeopleSpaceTile peopleSpaceTile : sortedTiles) {
            Intrinsics.checkNotNull(peopleSpaceTile);
            arrayList.add(toModel(peopleSpaceTile));
        }
        return arrayList;
    }

    public final List recentTiles() {
        PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.peopleSpaceWidgetManager;
        List<PeopleSpaceTile> sortedTiles = PeopleSpaceUtils.getSortedTiles(peopleSpaceWidgetManager.mIPeopleManager, peopleSpaceWidgetManager.mLauncherApps, peopleSpaceWidgetManager.mUserManager, Stream.concat(peopleSpaceWidgetManager.mINotificationManager.getConversations(false).getList().stream().filter(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda4(2)).map(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda1(1)), peopleSpaceWidgetManager.mIPeopleManager.getRecentConversations().getList().stream().map(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda1(2))));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(sortedTiles, 10));
        for (PeopleSpaceTile peopleSpaceTile : sortedTiles) {
            Intrinsics.checkNotNull(peopleSpaceTile);
            arrayList.add(toModel(peopleSpaceTile));
        }
        return arrayList;
    }
}
