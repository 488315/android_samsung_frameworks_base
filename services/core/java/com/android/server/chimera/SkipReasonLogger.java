package com.android.server.chimera;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class SkipReasonLogger {
    public final Set[] mSkippedPids = new Set[Reason.values().length];
    public final SystemRepository mSystemRepository;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class Reason {
        public static final /* synthetic */ Reason[] $VALUES;
        public static final Reason ADJ_OR_PROC_STATE;
        public static final Reason CACC;
        public static final Reason CACHED_EMPTY;
        public static final Reason INTERVAL;
        public static final Reason LRU;
        public static final Reason PERSISTENT_OR_PROTECTED;
        public static final Reason PICKED;
        public static final Reason SERVICE;
        public static final Reason UID;
        public static final Reason VISIBLE_SCREEN;
        public static final Reason WAKELOCK;

        static {
            Reason reason = new Reason("LRU", 0);
            LRU = reason;
            Reason reason2 = new Reason("CACHED_EMPTY", 1);
            CACHED_EMPTY = reason2;
            Reason reason3 = new Reason("PERSISTENT_OR_PROTECTED", 2);
            PERSISTENT_OR_PROTECTED = reason3;
            Reason reason4 = new Reason("PICKED", 3);
            PICKED = reason4;
            Reason reason5 = new Reason("CACC", 4);
            CACC = reason5;
            Reason reason6 = new Reason("INTERVAL", 5);
            INTERVAL = reason6;
            Reason reason7 = new Reason("VISIBLE_SCREEN", 6);
            VISIBLE_SCREEN = reason7;
            Reason reason8 = new Reason("WAKELOCK", 7);
            WAKELOCK = reason8;
            Reason reason9 = new Reason("SERVICE", 8);
            SERVICE = reason9;
            Reason reason10 = new Reason("UID", 9);
            UID = reason10;
            Reason reason11 = new Reason("ADJ_OR_PROC_STATE", 10);
            ADJ_OR_PROC_STATE = reason11;
            $VALUES =
                    new Reason[] {
                        reason,
                        reason2,
                        reason3,
                        reason4,
                        reason5,
                        reason6,
                        reason7,
                        reason8,
                        reason9,
                        reason10,
                        reason11,
                        new Reason("KILL_ONLY_ONE_SVC_AT_A_TIME", 11),
                        new Reason("HAS_CONNECTION_PROVIDER", 12)
                    };
        }

        public static Reason valueOf(String str) {
            return (Reason) Enum.valueOf(Reason.class, str);
        }

        public static Reason[] values() {
            return (Reason[]) $VALUES.clone();
        }
    }

    public SkipReasonLogger(SystemRepository systemRepository) {
        this.mSystemRepository = systemRepository;
        for (int i = 0; i < Reason.values().length; i++) {
            this.mSkippedPids[i] = new HashSet();
        }
    }

    public final void mark(int i, Reason reason) {
        this.mSkippedPids[reason.ordinal()].add(Integer.valueOf(i));
    }

    public final void mark(ChimeraAppInfo chimeraAppInfo, Reason reason) {
        Iterator it = ((HashSet) chimeraAppInfo.getPidList()).iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            num.intValue();
            this.mSkippedPids[reason.ordinal()].add(num);
        }
    }

    public final void printLog(String str) {
        for (Reason reason : Reason.values()) {
            if (!this.mSkippedPids[reason.ordinal()].isEmpty()) {
                String name = reason.name();
                Set set = this.mSkippedPids[reason.ordinal()];
                StringBuilder sb = new StringBuilder("Skipped reason: ");
                sb.append(name.substring(0, 2));
                sb.append(", pids: ");
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    sb.append(((Integer) it.next()).intValue());
                    sb.append(" ");
                }
                String sb2 = sb.toString();
                this.mSystemRepository.getClass();
                SystemRepository.log(str, sb2);
            }
        }
    }
}
