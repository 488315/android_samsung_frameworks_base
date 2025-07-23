package com.android.systemui.statusbar.notification;

import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class PhysicsPropertyAnimatorKt {
    public static final PropertyData obtainPropertyData(View view, PhysicsProperty physicsProperty) {
        PropertyData propertyData = (PropertyData) view.getTag(physicsProperty.tag);
        if (propertyData != null) {
            return propertyData;
        }
        PropertyData propertyData2 = new PropertyData(((Number) physicsProperty.property.get(view)).floatValue(), 0.0f, null, null, 0.0f, null, 56, null);
        view.setTag(physicsProperty.tag, propertyData2);
        return propertyData2;
    }
}
