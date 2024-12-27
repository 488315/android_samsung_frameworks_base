package com.android.systemui.people.data.repository;

import com.android.systemui.people.widget.PeopleSpaceWidgetManager;

public final class PeopleWidgetRepositoryImpl implements PeopleWidgetRepository {
    public final PeopleSpaceWidgetManager peopleSpaceWidgetManager;

    public PeopleWidgetRepositoryImpl(PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        this.peopleSpaceWidgetManager = peopleSpaceWidgetManager;
    }
}
