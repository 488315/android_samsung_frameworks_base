package com.android.server.permission.access.appop;

import com.android.modules.utils.BinaryXmlPullParser;
import com.android.modules.utils.BinaryXmlSerializer;
import com.android.server.permission.access.AccessState;
import com.android.server.permission.access.MutableAccessState;
import com.android.server.permission.access.SchemePolicy;

public abstract class BaseAppOpPolicy extends SchemePolicy {
    public final BaseAppOpPersistence persistence;

    public BaseAppOpPolicy(BaseAppOpPersistence baseAppOpPersistence) {
        this.persistence = baseAppOpPersistence;
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final String getObjectScheme() {
        return "app-op";
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void parseUserState(
            BinaryXmlPullParser binaryXmlPullParser, MutableAccessState mutableAccessState, int i) {
        this.persistence.parseUserState(binaryXmlPullParser, mutableAccessState, i);
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void serializeUserState(
            BinaryXmlSerializer binaryXmlSerializer, AccessState accessState, int i) {
        this.persistence.serializeUserState(binaryXmlSerializer, accessState, i);
    }
}
