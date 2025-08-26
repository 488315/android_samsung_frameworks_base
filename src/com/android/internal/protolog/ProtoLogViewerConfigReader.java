package com.android.internal.protolog;

import android.util.LongSparseArray;
import android.util.proto.ProtoInputStream;
import com.android.internal.protolog.common.ILogger;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/* loaded from: classes4.dex */
public class ProtoLogViewerConfigReader {
    private final Map<String, Set<Long>> mGroupHashes = new TreeMap();
    private final LongSparseArray<String> mLogMessageMap = new LongSparseArray<>();
    private final ViewerConfigInputStreamProvider mViewerConfigInputStreamProvider;

    static /* synthetic */ void lambda$loadViewerConfig$0(String str) {
    }

    static /* synthetic */ void lambda$unloadViewerConfig$1(String str) {
    }

    public ProtoLogViewerConfigReader(ViewerConfigInputStreamProvider viewerConfigInputStreamProvider) {
        this.mViewerConfigInputStreamProvider = viewerConfigInputStreamProvider;
    }

    public String getViewerString(long j) {
        return this.mLogMessageMap.get(j);
    }

    public synchronized void loadViewerConfig(String[] strArr) {
        loadViewerConfig(strArr, new ILogger() { // from class: com.android.internal.protolog.ProtoLogViewerConfigReader$$ExternalSyntheticLambda1
            @Override // com.android.internal.protolog.common.ILogger
            public final void log(String str) {
                ProtoLogViewerConfigReader.lambda$loadViewerConfig$0(str);
            }
        });
    }

    public synchronized void loadViewerConfig(String[] strArr, ILogger iLogger) {
        for (String str : strArr) {
            if (!this.mGroupHashes.containsKey(str)) {
                try {
                    Map<Long, String> mapLoadViewerConfigMappingForGroup = loadViewerConfigMappingForGroup(str);
                    this.mGroupHashes.put(str, mapLoadViewerConfigMappingForGroup.keySet());
                    for (Long l : mapLoadViewerConfigMappingForGroup.keySet()) {
                        this.mLogMessageMap.put(l.longValue(), mapLoadViewerConfigMappingForGroup.get(l));
                    }
                    iLogger.log("Loaded " + this.mLogMessageMap.size() + " log definitions");
                } catch (IOException e) {
                    iLogger.log("Unable to load log definitions: IOException while processing viewer config" + e);
                }
            }
        }
    }

    public synchronized void unloadViewerConfig(String[] strArr) {
        unloadViewerConfig(strArr, new ILogger() { // from class: com.android.internal.protolog.ProtoLogViewerConfigReader$$ExternalSyntheticLambda0
            @Override // com.android.internal.protolog.common.ILogger
            public final void log(String str) {
                ProtoLogViewerConfigReader.lambda$unloadViewerConfig$1(str);
            }
        });
    }

    public synchronized void unloadViewerConfig(String[] strArr, ILogger iLogger) {
        for (String str : strArr) {
            if (this.mGroupHashes.containsKey(str)) {
                for (Long l : this.mGroupHashes.get(str)) {
                    iLogger.log("Unloading viewer config hash " + l);
                    this.mLogMessageMap.remove(l.longValue());
                }
                this.mGroupHashes.remove(str);
            }
        }
    }

    public boolean messageHashIsAvailableInFile(long j) throws IOException {
        AutoClosableProtoInputStream inputStream = this.mViewerConfigInputStreamProvider.getInputStream();
        try {
            ProtoInputStream protoInputStream = inputStream.get();
            while (protoInputStream.nextField() != -1) {
                if (protoInputStream.getFieldNumber() == 1) {
                    long jStart = protoInputStream.start(2246267895809L);
                    while (protoInputStream.nextField() != -1) {
                        if (protoInputStream.getFieldNumber() == 1 && protoInputStream.readLong(1125281431553L) == j) {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            return true;
                        }
                    }
                    protoInputStream.end(jStart);
                }
            }
            if (inputStream == null) {
                return false;
            }
            inputStream.close();
            return false;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private Map<Long, String> loadViewerConfigMappingForGroup(String str) throws IOException {
        long jLoadGroupId = loadGroupId(str);
        TreeMap treeMap = new TreeMap();
        AutoClosableProtoInputStream inputStream = this.mViewerConfigInputStreamProvider.getInputStream();
        try {
            ProtoInputStream protoInputStream = inputStream.get();
            while (true) {
                if (protoInputStream.nextField() == -1) {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    return treeMap;
                }
                if (protoInputStream.getFieldNumber() == 1) {
                    long jStart = protoInputStream.start(2246267895809L);
                    String string = null;
                    int i = 0;
                    long j = 0;
                    for (int i2 = -1; protoInputStream.nextField() != i2; i2 = -1) {
                        int fieldNumber = protoInputStream.getFieldNumber();
                        if (fieldNumber == 1) {
                            j = protoInputStream.readLong(1125281431553L);
                        } else if (fieldNumber == 2) {
                            string = protoInputStream.readString(1138166333442L);
                        } else if (fieldNumber == 4) {
                            i = protoInputStream.readInt(1155346202628L);
                        }
                    }
                    if (i == 0) {
                        throw new IOException("Failed to get group id");
                    }
                    if (j == 0) {
                        throw new IOException("Failed to get message id");
                    }
                    if (string == null) {
                        throw new IOException("Failed to get message string");
                    }
                    if (i == jLoadGroupId) {
                        treeMap.put(Long.valueOf(j), string);
                    }
                    protoInputStream.end(jStart);
                }
            }
        } catch (Throwable th) {
            if (inputStream == null) {
                throw th;
            }
            try {
                inputStream.close();
                throw th;
            } catch (Throwable th2) {
                th.addSuppressed(th2);
                throw th;
            }
        }
    }

    private long loadGroupId(String str) throws IOException {
        AutoClosableProtoInputStream inputStream = this.mViewerConfigInputStreamProvider.getInputStream();
        try {
            ProtoInputStream protoInputStream = inputStream.get();
            while (protoInputStream.nextField() != -1) {
                if (protoInputStream.getFieldNumber() == 2) {
                    long jStart = protoInputStream.start(2246267895810L);
                    long j = 0;
                    String string = null;
                    while (protoInputStream.nextField() != -1) {
                        int fieldNumber = protoInputStream.getFieldNumber();
                        if (fieldNumber == 1) {
                            j = protoInputStream.readInt(1155346202625L);
                        } else if (fieldNumber == 2) {
                            string = protoInputStream.readString(1138166333442L);
                        }
                    }
                    if (Objects.equals(string, str)) {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        return j;
                    }
                    protoInputStream.end(jStart);
                }
            }
            if (inputStream != null) {
                inputStream.close();
            }
            throw new RuntimeException("Group " + str + " not found in viewer config");
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
