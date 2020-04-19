package de.dercoder.nspof;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;

public final class NSPOFDatabaseRegistry<T> {
    private final Set<NSPOFDatabase<T>> nspofDatabases;

    private NSPOFDatabaseRegistry(Set<NSPOFDatabase<T>> nspofDatabases) {
        this.nspofDatabases = nspofDatabases;
    }

    public void register(NSPOFDatabase<T> nspofDatabase) {
        Preconditions.checkNotNull(nspofDatabase);
        nspofDatabases.add(nspofDatabase);
    }

    public void unregister(NSPOFDatabase<T> nspofDatabase) {
        Preconditions.checkNotNull(nspofDatabase);
        nspofDatabases.remove(nspofDatabase);
    }

    public Optional<NSPOFDatabase<T>> findMaster() {
        return nspofDatabases.stream()
                .filter(nspofDatabase -> nspofDatabase.isMaster())
                .findFirst();
    }

    public List<NSPOFDatabase<T>> findSlaves() {
        return nspofDatabases.stream()
                .filter(nspofDatabase -> nspofDatabase.isSlave())
                .collect(Collectors.toList());
    }

    public boolean hasMaster() {
        return findMaster().isPresent();
    }

    public boolean contains(NSPOFDatabase nspofDatabase) {
        Preconditions.checkNotNull(nspofDatabase);
        return nspofDatabases.contains(nspofDatabase);
    }

    public List<NSPOFDatabase<T>> findAll() {
        return List.copyOf(nspofDatabases);
    }

    public static <T> NSPOFDatabaseRegistry<T> empty() {
        return new NSPOFDatabaseRegistry<T>(Sets.newHashSet());
    }
}
