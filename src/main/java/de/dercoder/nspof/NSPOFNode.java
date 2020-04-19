package de.dercoder.nspof;

import com.google.common.base.Preconditions;

import java.util.List;

public final class NSPOFNode<T> {
    private final NSPOFNodeRegistry<T> nodeRegistry;
    private final NSPOFDatabaseRegistry<T> databaseRegistry;

    private NSPOFNode(
            NSPOFNodeRegistry<T> nodeRegistry,
            NSPOFDatabaseRegistry<T> databaseRegistry
    ) {
        this.nodeRegistry = nodeRegistry;
        this.databaseRegistry = databaseRegistry;
    }

    public void writeDatabaseEntry(T value) {
        Preconditions.checkNotNull(value);
        var master = masterDatabase();
        master.add(value);
    }

    public List<T> readDatabaseEntries() {
        var master = masterDatabase();
        return master.data();
    }

    private NSPOFDatabase<T> masterDatabase() {
        if (!databaseRegistry.hasMaster()) {
            determineDatabaseMaster();
        }
        return databaseRegistry.findMaster().get();
    }

    private void determineDatabaseMaster() {
        databaseRegistry.findAll().get(0)
                .setDatabaseRecognition(NSPOFDatabaseRecognition.MASTER);
    }

    public void close() {
        nodeRegistry.unregister(this);
    }

    public static <T> NSPOFNode<T> of(
            NSPOFNodeRegistry<T> nodeRegistry,
            NSPOFDatabaseRegistry<T> databaseRegistry
    ) {
        return new NSPOFNode<T>(nodeRegistry, databaseRegistry);
    }
}
