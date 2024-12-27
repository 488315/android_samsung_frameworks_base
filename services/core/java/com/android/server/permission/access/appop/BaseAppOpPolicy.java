package com.android.server.permission.access.appop;

import com.android.modules.utils.BinaryXmlPullParser;
import com.android.modules.utils.BinaryXmlSerializer;
import com.android.server.permission.access.AccessState;
import com.android.server.permission.access.MutableAccessState;
import com.android.server.permission.access.SchemePolicy;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
