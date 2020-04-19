package de.dercoder.nspof;

import com.google.inject.Guice;
import com.google.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class NSPOFDatabaseTest {
    @Inject
    private NSPOFDatabaseFactory<String> databaseFactory;
    @Inject
    private NSPOFDatabaseRegistry databaseRegistry;

    @BeforeEach
    void initialize() {
        var nspofModule = NSPOFModule.<String>create();
        var injector = Guice.createInjector(nspofModule);
        injector.injectMembers(this);
    }

    @Test
    void testDatabaseCreation() {
        var database = databaseFactory.createDatabase();
        assertTrue(databaseRegistry.contains(database));
    }

    @Test
    void testDatabaseDependencies() {
        var masterDatabase = databaseFactory.createDatabase();
        masterDatabase.setDatabaseRecognition(NSPOFDatabaseRecognition.MASTER);
        var slaveDatabase = databaseFactory.createDatabase();
        masterDatabase.add("Test");
        assertEquals(1, slaveDatabase.data().size());
    }

    @Test
    void testDatabaseClosing() {
        var database = databaseFactory.createDatabase();
        database.close();
        assertEquals(0, databaseRegistry.findAll().size());
    }
}
