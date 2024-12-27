package com.samsung.android.knox.analytics.database;

import android.net.Uri;

public final class Contract {
    public static final String AUTHORITY = "com.samsung.android.knox.analytics.provider";
    public static final Uri CONTENT_URI =
            Uri.parse("content://com.samsung.android.knox.analytics.provider");
    public static final String[] DEFAULT_SIZE = {String.valueOf(1000)};
    public static final int SIZE = 1000;

    public static class B2CFeatures {
        public static final String PATH = "package_feature_b2c";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(Contract.CONTENT_URI, PATH);

        public static class Field {
            public static final String FEATURE_NAME = "feature_name";
            public static final String PACKAGE_NAME = "packageName";
        }
    }

    public static class CompressedEvents {
        public static final String METHOD_PERFORM_COMPRESSED_EVENTS_TRANSACTION =
                "performCompressedEventsTransaction";
        public static final String PATH = "compressed_events";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(Contract.CONTENT_URI, PATH);

        public static class Field {
            public static final String BULK = "bulk";
            public static final String CONTENT = "content";
            public static final String ID = "id";
            public static final String LENGTH = "length";
            public static final String ORIGINAL_LENGTH = "original_length";
        }

        public static class Keys {
            public static final String CV = "cv";
            public static final String PLAIN_EVENTS_SIZE = "plainEventsSize";
        }
    }

    public static class DatabaseClean {
        public static final String METHOD = "databaseClean";
        public static final String PATH = "cleaned_events";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(Contract.CONTENT_URI, PATH);

        public static class Extra {
            public static final String DELETED_EVENTS_COUNT = "deletedEventsCount";
            public static final String DELETED_SIZE_BYTES = "deletedSizeBytes";
            public static final String TARGET_DB_SIZE = "targetDbSize";
        }
    }

    public static class DatabaseSize {
        public static final String PATH = "size";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(Contract.CONTENT_URI, PATH);
    }

    public static class Events {
        public static final String PATH = "events";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(Contract.CONTENT_URI, PATH);

        public static class Extra {
            public static final String EVENTS_LIST = "eventsList";
            public static final String INSERT_BULK_EVENTS = "insertBulkEvents";
            public static final String LAST_EVENT_ID = "lastEventId";
            public static final int MAX_CACHED_EVENTS = 100;
            public static final int SINGLE_EVENT = 1;
        }

        public static class Field {
            public static final String BULK = "bulk";
            public static final String DATA = "data";
            public static final String ID = "id";
            public static final String VERSIONING_ID = "vid";
        }

        public static class Projection {
            public static final String CHUNK_SIZE_ONLY_PLAIN_EVENTS = "chunkSizePlainEvents";
            public static final String COUNT_ONLY = "count";
            public static final String LAST_EVENT_ID_ONLY = "lastEventId";
        }

        public static class Selection {
            public static final String CHUNK_SIZE = "chunk_size";
            public static final String DELETE_BY_SIZE = "deleteChunkBySize";
            public static final String DELETE_BY_SIZE_ONLY_EVENTS =
                    "deleteChunkBySizeOnlyPlainEvents";
            public static final String DELETE_UNTIL_TARGET_DB_SIZE = "deleteUntilTargetDbSize";
            public static final String DELETE_UP_TO_ID = "deleteUpToId";
        }

        public static class Type {
            public static final int CLEAN = 0;
            public static final int NORMAL = 1;
        }
    }

    public static class FeaturesBlacklist {
        public static final String LEGACY_PATH = "features_blacklist";
        public static final String PATH = "feature_blocklist";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(Contract.CONTENT_URI, PATH);

        public static class Constant {
            public static final String ALL_FEATURES = "*";
            public static final String API_USAGE_FEATURE_NAME = "API_USAGE";
            public static final int DISABLE_TYPE_ALL = 0;
            public static final int DISABLE_TYPE_GET = 2;
            public static final int DISABLE_TYPE_NONE = 1;
        }

        public static class Field {
            public static final String EVENT = "event";
            public static final String FEATURE = "feature";
        }
    }

    public static class FeaturesWhitelist {
        public static final String PATH = "features_whitelist";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(Contract.CONTENT_URI, PATH);

        public static class Constant {
            public static final String API_USAGE_FEATURE_NAME = "API_USAGE";
            public static final int ENABLE_TYPE_ALL = 0;
            public static final int ENABLE_TYPE_GET = 2;
            public static final int ENABLE_TYPE_NONE = 1;
        }

        public static class Field {
            public static final String ENABLE_TYPE = "enable_type";
            public static final String FEATURE = "feature";
        }
    }

    public static class SyntheticKey {
        public static final String PATH = "synthetic_key";

        public static class Field {
            public static final String ROW_ID = "row_id";
        }
    }

    public static class Versioning {
        public static final String AUX_FIELD_EVENT_ID = "eventId";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(Contract.CONTENT_URI, "version");
        public static final String METHOD_NOTIFY_VERSIONING_COMPLETED = "notifyVersioningCompleted";
        public static final String PATH = "version";

        public static class Field {
            public static final String DATA = "data";
            public static final String ID = "id";
        }
    }
}
