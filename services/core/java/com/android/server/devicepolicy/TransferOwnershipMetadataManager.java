package com.android.server.devicepolicy;

import android.content.ComponentName;
import android.text.TextUtils;

import com.android.internal.util.Preconditions;
import com.android.modules.utils.TypedXmlPullParser;

import java.util.Objects;

public final class TransferOwnershipMetadataManager {
    static final String TAG_ADMIN_TYPE = "admin-type";
    static final String TAG_SOURCE_COMPONENT = "source-component";
    static final String TAG_TARGET_COMPONENT = "target-component";
    static final String TAG_USER_ID = "user-id";
    public final Injector mInjector;

    class Injector {}

    public final class Metadata {
        public final String adminType;
        public final ComponentName sourceComponent;
        public final ComponentName targetComponent;
        public final int userId;

        public Metadata(
                ComponentName componentName, ComponentName componentName2, int i, String str) {
            this.sourceComponent = componentName;
            this.targetComponent = componentName2;
            Objects.requireNonNull(componentName);
            Objects.requireNonNull(componentName2);
            Preconditions.checkStringNotEmpty(str);
            this.userId = i;
            this.adminType = str;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof Metadata)) {
                return false;
            }
            Metadata metadata = (Metadata) obj;
            return this.userId == metadata.userId
                    && this.sourceComponent.equals(metadata.sourceComponent)
                    && this.targetComponent.equals(metadata.targetComponent)
                    && TextUtils.equals(this.adminType, metadata.adminType);
        }

        public final int hashCode() {
            return this.adminType.hashCode()
                    + ((this.targetComponent.hashCode()
                                    + ((this.sourceComponent.hashCode() + ((this.userId + 31) * 31))
                                            * 31))
                            * 31);
        }
    }

    public TransferOwnershipMetadataManager(Injector injector) {
        this.mInjector = injector;
    }

    public static Metadata parseMetadataFile(TypedXmlPullParser typedXmlPullParser) {
        int depth = typedXmlPullParser.getDepth();
        String str = null;
        int i = 0;
        String str2 = null;
        String str3 = null;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1 && (next != 3 || typedXmlPullParser.getDepth() > depth)) {
                if (next != 3 && next != 4) {
                    String name = typedXmlPullParser.getName();
                    name.getClass();
                    switch (name) {
                        case "target-component":
                            typedXmlPullParser.next();
                            str2 = typedXmlPullParser.getText();
                            break;
                        case "user-id":
                            typedXmlPullParser.next();
                            i = Integer.parseInt(typedXmlPullParser.getText());
                            break;
                        case "source-component":
                            typedXmlPullParser.next();
                            str = typedXmlPullParser.getText();
                            break;
                        case "admin-type":
                            typedXmlPullParser.next();
                            str3 = typedXmlPullParser.getText();
                            break;
                    }
                }
            }
        }
        Objects.requireNonNull(str);
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        Objects.requireNonNull(str2);
        return new Metadata(unflattenFromString, ComponentName.unflattenFromString(str2), i, str3);
    }
}
