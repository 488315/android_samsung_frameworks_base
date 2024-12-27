package com.android.server.pm;

final class SystemDeleteException extends Exception {
    final PackageManagerException mReason;

    public SystemDeleteException(PackageManagerException packageManagerException) {
        this.mReason = packageManagerException;
    }
}
